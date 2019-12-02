package com.yh.cloud.activiti.leave.util;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.AbstractTemplateEngine;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * @author yanghan
 * @date 2019/10/21
 */
public class MpGenerator {

    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();
        String userDir = System.getProperty("user.dir");
        String projectPath = userDir + "/yh-activiti/yh-activiti-leave";

        /**
         * 全局配置
         */
        mpg.setGlobalConfig(
                new GlobalConfig()
                        //输出目录
                        .setOutputDir(projectPath + "/src/main/java")
                        .setOpen(false)
                        .setAuthor("yanghan")
                        .setSwagger2(true)
                // 自定义文件命名，注意 %s 会自动填充表实体属性！
                // .setEntityName("%sEntity");
                // .setMapperName("%sDao")
                // .setXmlName("%sDao")
                // .setServiceName("MP%sService")
                // .setServiceImplName("%sServiceDiy")
                // .setControllerName("%sAction")
        );

        /**
         * 数据源配置
         */
        mpg.setDataSource(
                new DataSourceConfig()
                        // 数据库类型
                        .setDbType(DbType.MYSQL)
                        //.setTypeConvert(new MySqlTypeConvert() {
                        //    // 自定义数据库表字段类型转换【可选】
                        //    @Override
                        //    public DbColumnType processTypeConvert(GlobalConfig globalConfig, String fieldType) {
                        //        System.out.println("转换类型：" + fieldType);
                        //        // if ( fieldType.toLowerCase().contains( "tinyint" ) ) {
                        //        //    return DbColumnType.BOOLEAN;
                        //        // }
                        //        return super.processTypeConvert(globalConfig, fieldType);
                        //    }
                        //})
                        .setDriverName("com.mysql.cj.jdbc.Driver")
                        .setUsername("root")
                        .setPassword("yanghan")
                        .setUrl("jdbc:mysql://localhost:3306/test_db?useUnicode=true&autoReconnect=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai")
        );

        /**
         * 包配置
         */
        mpg.setPackageInfo(
                new PackageConfig()
                        .setModuleName(scanner("模块名"))
                        .setParent("com.yh.cloud.activiti")
                        .setController("rest")
                        .setEntity("model.domain")
        );


        /**
         * 自定义配置
         */
        String templatePath = "/templates/mapper.xml";
        AbstractTemplateEngine templateEngine;
//        String templateSelected = scanner("模板引擎，0表示【Velocity】|1表示【Freemarker】");
        String templateSelected = "0";
        switch (templateSelected) {
            case "1":
                templatePath += ".ftl";
                templateEngine = new FreemarkerTemplateEngine();
                break;
            case "0":
            default:
                templatePath += ".vm";
                templateEngine = null;
                break;
        }
        mpg.setCfg(
                // 注入自定义配置，可以在 VM 中使用 cfg.abc 设置的值
                new InjectionConfig() {
                    @Override
                    public void initMap() {
                        // to do nothing
                       /* Map<String, Object> map = new HashMap<>();
                        map.put("abc", this.getConfig().getGlobalConfig().getAuthor() + "-mp");
                        this.setMap(map);*/
                    }
                }.setFileOutConfigList(
                        Collections.singletonList(
                                new FileOutConfig(templatePath) {
                                    @Override
                                    public String outputFile(TableInfo tableInfo) {
                                        // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                                        return projectPath + "/src/main/resources/mapper/" + mpg.getPackageInfo().getModuleName()
                                                + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
                                    }
                                }
                        )
                )
        );

        /**
         * 非默认引擎，需要指定
         */
        if (null != templateEngine) {
            mpg.setTemplateEngine(templateEngine);
        }

        /**
         * 配置模板
         */
        mpg.setTemplate(
                new TemplateConfig()
                        // 配置自定义输出模板
                        //指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
                        // templateConfig.setEntity("templates/entity2.java");
                        // templateConfig.setService();
                        // templateConfig.setController();
                        .setXml(null)
        );


        // 自定义需要填充的字段
        List<TableFill> tableFillList = new ArrayList<>();
        tableFillList.add(new TableFill("create_date", FieldFill.INSERT));
        tableFillList.add(new TableFill("update_date", FieldFill.INSERT_UPDATE));

        /**
         * 策略配置
         */
        mpg.setStrategy(
                new StrategyConfig()
                        //表名生成策略, 下划线
                        .setNaming(NamingStrategy.underline_to_camel)
                        .setColumnNaming(NamingStrategy.underline_to_camel)
                        .setSuperEntityClass("com.yh.cloud.activiti.leave.mp.MpBaseEntity")
                        //去掉父类公共字段
                        .setSuperEntityColumns("create_date","create_by","update_date","update_by")
                        .setTableFillList(tableFillList)
                        .setEntityLombokModel(true)
                        .setRestControllerStyle(true)
//                        .setSuperControllerClass("com.baomidou.ant.common.BaseController")
                        .setInclude(scanner("表名，多个英文逗号分割").split(","))
                        .setControllerMappingHyphenStyle(true)
                        .setTablePrefix(mpg.getPackageInfo().getModuleName() + "_")

        );
        mpg.execute();
    }

    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotEmpty(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }
}
