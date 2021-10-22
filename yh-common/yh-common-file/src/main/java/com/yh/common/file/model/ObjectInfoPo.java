package com.yh.common.file.model;

import lombok.Data;

/**
 * 文件系统上传成功后的对象信息
 *
 * @author yanghan
 * @date 2021/10/20
 */
@Data
public class ObjectInfoPo {
    /** 相对路径 */
    private String path;
    /** 唯一值 */
    private String hash;
}
