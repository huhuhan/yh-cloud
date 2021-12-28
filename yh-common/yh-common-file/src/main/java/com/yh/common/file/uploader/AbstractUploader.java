package com.yh.common.file.uploader;

import cn.hutool.core.date.DateUtil;
import cn.hutool.crypto.digest.DigestUtil;
import lombok.SneakyThrows;

import java.util.Date;

/**
 * 抽象公共方法
 *
 * @author yanghan
 * @date 2021/8/9
 */
public abstract class AbstractUploader implements IUploader {

    /**
     * 构建文件相对路径，以时间天为单位
     *
     * @param basePath 根目录
     * @param fileName 文件名
     * @return String
     */
    protected String getPath(String basePath, String fileName) {
        return basePath + DateUtil.format(new Date(), "yyyyMMdd") + "/" + fileName;
    }

    /**
     * 构建文件哈希值，相同文件哈希值相同
     *
     * @param file 文件
     * @return String
     */
    @Override
    @SneakyThrows
    public String getHash(byte[] file) {
        return DigestUtil.md5Hex(file);
    }
}
