package com.yh.cloud.generator.model.emun;

/**
 * @author yanghan
 * @date 2021/6/3
 */
public interface GeneratorConstant {

    /***************数据库***************/
    String MYSQL_PRIMARY_KEY = "PRI";

    /******************************/
    /** 传参toTables的分隔符 */
    String TO_TABLES_SPLIT = "\\|";

    /** 默认配置文件 */
    String PROPERTIES_NAME = "generator.properties";
    /** 模板根目录 */
    String TEMPLATE_PATH = "template/";
    /****************模板文件**************/
    String FILE_NAME_ENTITY = "Entity.java.vm";
    String FILE_NAME_MAPPER = "Mapper.java.vm";
    String FILE_NAME_MAPPER_XML = "Mapper.xml.vm";
    String FILE_NAME_SERVICE = "Service.java.vm";
    String FILE_NAME_SERVICE_IMPL = "ServiceImpl.java.vm";
    String FILE_NAME_CONTROLLER = "Controller.java.vm";
    String FILE_NAME_QUERY = "Query.java.vm";

    /** 注释-日期格式 */
    String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
}
