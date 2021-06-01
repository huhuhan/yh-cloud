## 项目更新日志



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