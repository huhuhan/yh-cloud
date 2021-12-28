## 使用说明

- 引入依赖包
- 文件系统操作接口封装，支持本地、minio、aliyun
- 配置如下

    ```yaml
    yh:
      uploader:
        enabled: true
        default-type: ordinary
        ordinary:
          path: /user/yh_upload
          domain: http://127.0.0.1:9031
        minio:
          enabled: true
          endpoint: http://xxx
          access-key:
          secret-key:
          bucket-name: testdemo
        aliyun:
          enabled: false
          endpoint: http://xxx
          access-key:
          secret-key:
          bucket-name: testdemo
    ```