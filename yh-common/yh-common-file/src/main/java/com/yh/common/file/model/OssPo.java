package com.yh.common.file.model;

import lombok.Data;

/**
 * OSS存储对象服务的配置信息
 *
 * @author yanghan
 * @date 2021/10/20
 */
@Data
public class OssPo {
    /** 是否注入Spring容器 */
    private Boolean enabled = false;
    /** 域名 */
    private String endpoint;
    /** 访问KEY */
    private String accessKey;
    /** 访问密钥 */
    private String secretKey;
    /** 桶 */
    private String bucketName;
}
