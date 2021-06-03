package com.yh.cloud.generator.utils;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Console;
import cn.hutool.core.util.StrUtil;
import cn.hutool.setting.dialect.Props;
import com.yh.cloud.generator.model.emun.GeneratorConstant;
import com.yh.cloud.generator.model.emun.DataType;
import com.yh.cloud.generator.model.entity.ColumnEntity;
import com.yh.cloud.generator.model.entity.TableEntity;
import com.yh.cloud.generator.model.po.GeneratorPo;
import com.yh.cloud.generator.model.po.MySqlColumnPo;
import com.yh.cloud.generator.model.po.MySqlTablePo;
import org.apache.commons.io.IOUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import java.io.File;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.sql.Struct;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 代码生成器工具类
 * @author yanghan
 * @date 2021/6/2
 */
public class GenUtils {
    private GenUtils() {
        throw new IllegalStateException("Utility class");
    }

    private static List<String> getTemplates() {
        List<String> templates = new ArrayList<>();
        templates.add(GeneratorConstant.TEMPLATE_PATH + GeneratorConstant.FILE_NAME_ENTITY);
        templates.add(GeneratorConstant.TEMPLATE_PATH + GeneratorConstant.FILE_NAME_MAPPER);
        templates.add(GeneratorConstant.TEMPLATE_PATH + GeneratorConstant.FILE_NAME_MAPPER_XML);
        templates.add(GeneratorConstant.TEMPLATE_PATH + GeneratorConstant.FILE_NAME_SERVICE);
        templates.add(GeneratorConstant.TEMPLATE_PATH + GeneratorConstant.FILE_NAME_SERVICE_IMPL);
        templates.add(GeneratorConstant.TEMPLATE_PATH + GeneratorConstant.FILE_NAME_CONTROLLER);
        templates.add(GeneratorConstant.TEMPLATE_PATH + GeneratorConstant.FILE_NAME_QUERY);
        return templates;
    }

    /**
     * 没传的参数，默认取generator.properties的值
     */
    public static GeneratorPo checkGeneratorPo(GeneratorPo params) throws Exception {
        Props props = new Props(GeneratorConstant.PROPERTIES_NAME);
        GeneratorPo result = props.toBean(GeneratorPo.class);
        // 覆盖
        BeanUtil.copyProperties(params, result, CopyOptions.create().ignoreNullValue());
        Console.log("GeneratorPo: {}", result.toString());

        Map<String, Object> map  = BeanUtil.beanToMap(result);
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if(null == entry.getValue()){
                throw new Exception(String.format("【%s】不能为空！", entry.getKey()));
            }
        }
        return result;
    }

    /**
     * 列名转换成Java属性名
     */
    private static String columnToJava(String columnName) {
        return StrUtil.upperFirst(StrUtil.toCamelCase(columnName));
    }

    /**
     * 表名转换成Java类名
     */
    private static String tableToJava(String tableName, String tablePrefix) {
        if (StrUtil.isNotBlank(tablePrefix) && StrUtil.contains(tableName, tablePrefix)) {
            tableName = StrUtil.subAfter(tableName, tablePrefix, false);
        }
        return columnToJava(tableName);
    }

    /**
     * 获取文件名
     */
    private static String getFileName(String template, String className, String packageName, String moduleName) {
        String packagePath = "main" + File.separator + "java" + File.separator;
        if (StrUtil.isNotBlank(packageName)) {
            packagePath += packageName.replace(".", File.separator) + File.separator + moduleName + File.separator;
        }

        if (template.contains(GeneratorConstant.FILE_NAME_ENTITY)) {
            return packagePath + "model" + File.separator + "entity" + File.separator + className + ".java";
        }

        if (template.contains(GeneratorConstant.FILE_NAME_QUERY)) {
            return packagePath + "model" + File.separator + "vo" + File.separator + className + "Query.java";
        }

        if (template.contains(GeneratorConstant.FILE_NAME_MAPPER)) {
            return packagePath + "mapper" + File.separator + className + "Mapper.java";
        }

        if (template.contains(GeneratorConstant.FILE_NAME_SERVICE)) {
            return packagePath + "service" + File.separator + "I" + className + "Service.java";
        }

        if (template.contains(GeneratorConstant.FILE_NAME_SERVICE_IMPL)) {
            return packagePath + "service" + File.separator + "impl" + File.separator + className + "ServiceImpl.java";
        }

        if (template.contains(GeneratorConstant.FILE_NAME_CONTROLLER)) {
            return packagePath + "controller" + File.separator + className + "Controller.java";
        }

        if (template.contains(GeneratorConstant.FILE_NAME_MAPPER_XML)) {
            return "main" + File.separator + "resources" + File.separator + "mapper" + File.separator + className + "Mapper.xml";
        }

        return null;
    }

    /**
     * 生成代码，导出zip
     *
     * @param generatorPo
     * @param table
     * @param columns
     * @param zip
     */
    public static void generatorCode(GeneratorPo generatorPo, MySqlTablePo table, List<MySqlColumnPo> columns, ZipOutputStream zip) {
        //表信息
        TableEntity tableEntity = new TableEntity();
        tableEntity.setTableName(table.getTableName());
        tableEntity.setComments(table.getTableComment());
        //表名转换成Java类名
        String className = tableToJava(tableEntity.getTableName(), generatorPo.getTablePrefixValue());
        tableEntity.setClassName(className);
        tableEntity.setClassname(StrUtil.lowerFirst(className));

        List<ColumnEntity> columnList = new ArrayList<>();
        boolean hasBigDecimal = false;
        for (MySqlColumnPo column : columns) {
            //列信息
            ColumnEntity columnEntity = new ColumnEntity();
            columnEntity.setColumnName(column.getColumnName());
            columnEntity.setDataType(column.getDataType());
            columnEntity.setComments(column.getColumnComment());
            columnEntity.setExtra(column.getExtra());
            //列名转换成Java属性名
            String attrName = columnToJava(columnEntity.getColumnName());
            columnEntity.setAttrName(attrName);
            columnEntity.setAttrname(StrUtil.lowerFirst(attrName));
            //列的数据类型，转换成Java类型
            String attrType = DataType.getJavaType(columnEntity.getDataType());
            columnEntity.setAttrType(attrType);
            // 是否包含BigDecimal类型
            if (!hasBigDecimal && DataType.MYSQL_DECIMAL.getJavaType().equals(attrType)) {
                hasBigDecimal = true;
            }
            //是否主键
            if (GeneratorConstant.MYSQL_PRIMARY_KEY.equalsIgnoreCase(column.getColumnKey()) && tableEntity.getPk() == null) {
                tableEntity.setPk(columnEntity);
            }
            columnList.add(columnEntity);
        }
        tableEntity.setColumns(columnList);
        // 默认取第一个字段为主键
        if (tableEntity.getPk() == null) {
            tableEntity.setPk(tableEntity.getColumns().get(0));
        }

        //设置velocity资源加载器
        Properties prop = new Properties();
        prop.put("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        Velocity.init(prop);
        //封装模板数据
        Map<String, Object> map = new HashMap<>(16);
        map.put("tableName", tableEntity.getTableName());
        map.put("comments", tableEntity.getComments());
        map.put("pk", tableEntity.getPk());
        map.put("className", tableEntity.getClassName());
        map.put("classname", tableEntity.getClassname());
        map.put("pathName", tableEntity.getClassname().toLowerCase());
        map.put("columns", tableEntity.getColumns());
        map.put("hasBigDecimal", hasBigDecimal);
        map.put("package", generatorPo.getPackageValue());
        map.put("moduleName", generatorPo.getModuleValue());
        map.put("author", generatorPo.getAuthorValue());
        map.put("email", generatorPo.getEmailValue());
        map.put("datetime", DateUtil.format(new Date(), GeneratorConstant.DATETIME_FORMAT));
        map.put("hasSuperEntity", generatorPo.getHasSuperObj());
        VelocityContext context = new VelocityContext(map);
        //获取模板列表
        List<String> templates = getTemplates();
        for (String template : templates) {
            //渲染模板
            try (
                    StringWriter sw = new StringWriter()
            ) {
                Template tpl = Velocity.getTemplate(template, "UTF-8");
                tpl.merge(context, sw);

                String fileName = getFileName(template, tableEntity.getClassName(), generatorPo.getPackageValue(), generatorPo.getModuleValue());
                if (StrUtil.isBlank(fileName)) {
                    throw new Exception("模板文件路径异常");
                }
                //添加到zip
                zip.putNextEntry(new ZipEntry(fileName));
                IOUtils.write(sw.toString(), zip, StandardCharsets.UTF_8);
                zip.closeEntry();
            } catch (Exception e) {
                Console.error("generatorCode-error", e);
            }
        }
    }
}
