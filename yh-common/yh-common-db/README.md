## 使用说明

- maven执行`install`命令

- 引入本模块依赖包

- 配置如下

    ```yaml
    spring:
      datasource:
        # 开启多数据库源
        dynamic-yh:
          enabled: true
          # 主数据库源
          primary: master
          datasource:
            master:
              source-type: com.alibaba.druid.pool.DruidDataSource
              url: jdbc:mysql://127.0.0.1:3306/test_druid?useUnicode=true&autoReconnect=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai
              username: root
              password: yanghan
              driver-class-name: com.mysql.cj.jdbc.Driver
            # 其他数据库源
    ```  

