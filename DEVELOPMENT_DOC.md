# 项目说明



## 服务介绍


### 网关服务

- 参考服务：**yh-gateway**
- 已集成认证鉴权，结合认证服务【**yh-auth-server**】使用
- 已集成统一swagger文档，从注册中心读取服务分组，或自定义分组，见application配置




### 认证鉴权服务

- 参考服务：**yh-auth-server**

- 认证鉴权例子（非网关认证鉴权）：**yh-auth-client-demo**

- 认证鉴权例子（网关认证鉴权）：**yh-user-server**

  


### 代码生成器

- 启动【**yh-generator**】服务，访问Swagger地址

- 支持数据库：MySQL、PostgreSQL

- 修改application的数据库源配置

- 默认配置文件参考`generator.properties`，接口请求参数支持覆盖

- 生成的模板代码，依赖于

  ```xml
  <dependencies>
      <dependency>
          <groupId>com.yh.cloud</groupId>
          <artifactId>yh-common-web</artifactId>
      </dependency>
      <dependency>
          <groupId>com.yh.cloud</groupId>
          <artifactId>yh-common-db</artifactId>
      </dependency>
      <dependency>
          <groupId>com.yh.cloud</groupId>
          <artifactId>yh-common-base</artifactId>
      </dependency>
      <!-- 数据库驱动依赖自行添加 -->
  </dependencies>
  ```

- 应用，mybatis-plus的配置

    ```java
    @Configuration
    @MapperScan(value = {"com.yh.cloud.xx.mapper"})
    public class MyBatisPlusConfig {
    }
    ```



### 用户服务

- 参考服务：**yh-user-server**，基础服务，比如用户、角色、组织等基本信息
- 服务模块：**yh-user-api**，提供公共接口给其他服务调用




### 文件服务

- 参考服务：**yh-file**，作为文件读写的代理转发服务，不直接与文件系统交互
- 文件系统的封装操作说明，见[yh-common-file](./yh-common/yh-common-file/README.md)
- 在**yh-common-file**的基础上，扩展实现一种文件存储方式，即数据库存储，`database`
- 引入操作日志依赖包[yh-common-log](./yh-common/yh-common-log/README.md)，以删除操作为例子




### 系统服务

- 参考服务：**yh-sys**，作为系统通用接口的服务，比如数据字典之类




## 服务运行

### 配置Hosts

```python
127.0.0.1 yh-eureka
127.0.0.1 yh-gateway
```



### 服务启动

依次启动

1. yh-eureka
2. yh-gateway
3. yh-actuator (可选）
4. yh-auth-server (可选，默认采用demo-user-api）
5. yh-user-server (可选，替代demo-user-api)



## 服务扩展


### 新建服务

#### 创建子模块

1. 新建Maven子模块（New Module）

2. 补充以下依赖

   ```xml
   <project>
       <dependencies>       
           <dependency>
               <groupId>com.yh.cloud</groupId>
               <artifactId>yh-common-base</artifactId>
           </dependency>
           <dependency>
               <groupId>com.yh.cloud</groupId>
               <artifactId>yh-common-web</artifactId>
           </dependency>
           <dependency>
               <groupId>com.yh.cloud</groupId>
               <artifactId>yh-common-db</artifactId>
           </dependency>  
           <!--可选-->
           <dependency>
               <groupId>com.yh.cloud</groupId>
               <artifactId>yh-common-ribbon</artifactId>
           </dependency>
           <!--可选-->
           <dependency>
               <groupId>com.yh.cloud</groupId>
               <artifactId>yh-common-auth</artifactId>
           </dependency>
       </dependencies>
   
       <build>
           <plugins>
               <plugin>
                   <groupId>org.springframework.boot</groupId>
                   <artifactId>spring-boot-maven-plugin</artifactId>
                   <configuration>
                       <!--可执行jar，要求有输入函数main-->
                       <executable>true</executable>
                   </configuration>
                   <executions>
                       <execution>
                           <goals>
                               <!--将其他依赖包也打包进去-->
                               <goal>repackage</goal>
                           </goals>
                       </execution>
                   </executions>
               </plugin>
           </plugins>
           <!--打包包名-->
           <finalName>${project.artifactId}</finalName>
       </build>
   </project>
   
   ```


#### 创建单独项目

1. 创建Maven项目（New Project），选择Maven

2. 引入**yh-cloud项目的yh-common**相关依赖包

3. 保证Spring Boot 、Cloud等等版本保持一致

   ```xml
   <!--依赖声明管理-->
   <dependencyManagement>
       <dependencies>
           <!--************************Spring******************-->
           <!--spring cloud 组件-->
           <dependency>
               <groupId>org.springframework.cloud</groupId>
               <artifactId>spring-cloud-dependencies</artifactId>
               <version>${spring.cloud.version}</version>
               <type>pom</type>
               <scope>import</scope>
           </dependency>
           <!--spring boot 组件-->
           <dependency>
               <groupId>org.springframework.boot</groupId>
               <artifactId>spring-boot-dependencies</artifactId>
               <version>${spring-boot.version}</version>
               <type>pom</type>
               <scope>import</scope>
           </dependency>
           <!--************************本项目******************-->
       </dependencies>
   </dependencyManagement>
   ```
   
   基本依赖版本号
       
   ```xml
   <!--版本号-->
   <properties>
       <!--基本构建编译信息-->
       <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
       <maven.compiler.source>1.8</maven.compiler.source>
       <maven.compiler.target>1.8</maven.compiler.target>
       <java.version>1.8</java.version>
       <!--自定义模块版本-->
       <yh-cloud.version>1.0-SNAPSHOT</yh-cloud.version>
       <yh-common.version>1.0-SNAPSHOT</yh-common.version>
       <!--第三方依赖版本-->
       <spring-boot.version>2.2.10.RELEASE</spring-boot.version>
       <spring.cloud.version>Hoxton.SR5</spring.cloud.version>
   </properties>
   ```

   


### 服务注册

引入依赖包
```xml
        <!--微服务 客户端-->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>
```
添加配置，建议命名为`bootstrap.yml`
```yaml
eureka:
  client:
    serviceUrl:
      defaultZone: http://yh:yh@sc-eureka:8761/eureka/
  instance:
    instance-id: ${spring.application.name}:${spring.cloud.client.ip-address}:${server.port}
```

> 依赖配置单独引用，便于后期更换其它注册中心


### 公共依赖包

- **yh-common**模块

- 每个依赖包有对应README的使用说明

- 单独维护的话，版本保证一致



## 服务部署

### 网络隔离模式

- 【网关服务】集成认证鉴权功能

- 【业务服务】不用集成认证鉴权功能

- 所有请求必须通过【网关服务】转发，外网仅开放【网关服务】端口。

- 获取令牌授权时，请求经【网关服务】转发至【认证鉴权服务】获取令牌

- 请求认证鉴权时，请求经【网关服务】直接认证鉴权，再转发【业务服务】

- 外网，无法直接请求【业务服务】，因为端口没开放

- 内网，可以直接请求【业务服务】，需要用【业务服务】自身端口

  

### 无网络隔离模式

- 【网关服务】无需集成认证鉴权功能

- 【业务服务】集成认证鉴权功能

- 所有服务开放外网端口，可直接访问

- 获取令牌授权时，请求经【网关服务】转发至【认证鉴权服务】获取令牌

- 请求【业务服务】，不管是【网关服务】转发还是自身端口访问，【业务服务】都会到【认证鉴权服务】认证鉴权，方式根据集成而定，比如远程调用、Redis调用等等

  

### 容器化

见[Dockerfile | docker-compose 典型案例参考 | 寒冷如铁 (github.io)](https://huhuhan.github.io/blog/views/docker/docker-demo.html#springcloud)

