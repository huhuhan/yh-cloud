## 项目更新日志


### 待发布

- 新增yh-sys，系统通用服务，以数据字典为例
- 新增yh-common-file，文件系统读写的通用依赖包，支持本地、阿里云、MinIO、数据库等多种存储方式，可自定义扩展
- 新增yh-file，文件系统服务，并扩展一种文件读写方式，数据库存储方式


### v1.1

- 新增yh-generator模块，模板配置的代码生成器，自动生成增删改查接口
- 新增yh-user模块
  - yh-user-server：基于代码生成器创建的新服务，供集成参考
  - yh-user-api：作为提供其他服务调用的API包。例子，替换yh-auth-server的demo-user-api引用



### v1.0

- 项目结构调整、版本升级更新
- 扩展公共模块：
  - yh-common-auth：认证相关，支持WebMVC和WebFlux的认证鉴权
  - yh-common-db：数据库相关，支持多租户动态路由数据库
  - yh-common-web：Web服务相关，除去Eureka-Client依赖
  - yh-common-ribbon：openFeign、Hystrix、Ribbon相关依赖
- 认证鉴权模块：yh-auth
- 新增服务健康监控：yh-actuator
- 网关集成认证模块





### v0.2

即tag版本：[activiti5](https://github.com/huhuhan/yh-cloud/releases/tag/activiti5)

工作流5.22版本，集成独立服务、api服务  
参考[咖啡兔的工作流Demo](https://github.com/henryyan/kft-activiti-demo)  
涉及模块：

- yh-activiti-leave：具体业务项目，例子：请假模块
- yh-activiti5：流程项目
- yh-activiti-api：流程对外接口实体封装包
- yh-common-base
- yh-common-web
- yh-eureka





### v0.1

即tag版本：[oauth2](https://github.com/huhuhan/yh-cloud/releases/tag/oauth2)

Oauth2的4种认证方式和资源服务认证  

涉及模块：

- yh-auth-server
- yh-auth-client-demo
- yh-eureka
- yh-common-web