package com.yh.common.file.model;

import lombok.Data;

/**
 * 直接存储文件系统的配置信息
 *
 * @author yanghan
 * @date 2021/10/20
 */
@Data
public class DfsPo {
    /** 域名 */
    private String domain;
    /** 根目录 */
    private String path;
}
