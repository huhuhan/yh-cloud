## 使用说明

- maven执行`install`命令

- 引入本模块依赖包

- 配置如下

    ```yaml
    yh:
      audit-log:
        # 默认不开启
        enabled: false
        # 默认log，可选log、db
        log-type: log
        # 可选，从请求头对象中获取当前用户信息，可更改键的名称，默认如下
        header-key:
          user-id: yh-user-id
          user-name: yh-user-name
        # 可选，选择db模式，可选配置单独的数据库源，否则取项目中BeanId为datasource的默认数据库源
        datasource:
          driver-class-name: com.mysql.cj.jdbc.Driver
          jdbc-url: jdbc:mysql://localhost:3306/test1?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&useSSL=false&zeroDateTimeBehavior=convertToNull&serverTimezone=Asia/Shanghai
          username: root
          password: yanghan
    ```
    log模式，日志级别是`debug`，记得开启
    
    db模式的数据库表参考如下，会自动创建
    ```sql
    CREATE TABLE IF NOT EXISTS `operation_logger` (
      `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
      `application_name` varchar(32) DEFAULT NULL COMMENT '应用名',
      `class_name` varchar(128) NOT NULL COMMENT '类名',
      `method_name` varchar(64) NOT NULL COMMENT '方法名',
      `user_id` varchar(32) DEFAULT NULL COMMENT '用户id',
      `user_name` varchar(64) DEFAULT NULL COMMENT '用户名',
      `operation` varchar(1024) NOT NULL COMMENT '操作信息',
      `operation_time` varchar(32) NOT NULL COMMENT '操作时间',
      PRIMARY KEY (`id`) USING BTREE
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
    ```
      
- 代码例子

    在controller接口上加上`com.yh.common.log.annotation.AuditLog`注解，例子如下
    ```java
        @PostMapping(value = "/demo")
        @AuditLog(operation = "'文字说明：' + #pageQueryVo.pageNum")
        public ReturnWrapper demo(@RequestBody PageQueryVo pageQueryVo) {
            return ReturnWrapMapper.ok();
        }
    ```
    operation，字符串，通过spring的SpEL解析参数
    
  