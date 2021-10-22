package com.yh.common.file.config;

import com.yh.common.file.model.DfsPo;
import com.yh.common.file.model.OssPo;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author yanghan
 * @date 2021/10/20
 */
@Data
@ConfigurationProperties(prefix = UploaderProperties.PREFIX)
public class UploaderProperties {
    public static final String PREFIX = "yh.uploader";
    public static final String PREFIX_ORDINARY = "ordinary";
    public static final String PREFIX_ALIYUN = "aliyun";
    public static final String PREFIX_MINIO = "minio";
    private boolean enabled;
    /** 默认方式 */
    private String defaultType;
    /** 本地存储 */
    private DfsPo ordinary;
    /** 阿里云 */
    private OssPo aliyun;
    /** MinIO */
    private OssPo minio;

    @ConfigurationProperties(prefix = UploaderProperties.PREFIX + "." + UploaderProperties.PREFIX_ORDINARY)
    public DfsPo ordinary() {
        return new DfsPo();
    }

    @ConfigurationProperties(prefix = UploaderProperties.PREFIX + "." + UploaderProperties.PREFIX_ALIYUN)
    public OssPo aliyun() {
        return new OssPo();
    }

    @ConfigurationProperties(prefix = UploaderProperties.PREFIX + "." + UploaderProperties.PREFIX_MINIO)
    public OssPo minio() {
        return new OssPo();
    }
}
