package com.yh.common.file.uploader;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.crypto.SecureUtil;
import lombok.SneakyThrows;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
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
    @SneakyThrows
    protected String getHash(MultipartFile file) {
        InputStream is = file.getInputStream();
        String hash = SecureUtil.md5(file.getInputStream());
        IoUtil.close(is);
        return hash;
    }
}
