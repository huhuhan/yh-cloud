package com.yh.common.file.uploader;

import com.yh.common.file.model.ObjectInfoPo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 上传器，统一接口
 *
 * @author yanghan
 * @date 2021/8/9
 */
public interface IUploader {

    /**
     * 存储方式
     *
     * @return java.lang.String
     * @author yanghan
     * @date 2021/10/20
     */
    String type();

    /**
     * 上传文件
     *
     * @param file {@link MultipartFile}
     * @return com.yh.cloud.file.model.vo.ObjectInfoVO
     * @author yanghan
     * @date 2021/10/20
     */
    ObjectInfoPo upload(MultipartFile file);

    /**
     * 下载文件，字节流
     *
     * @param path 文件路径，即保存时得到的路径
     * @return byte[]   字节数据
     * @author yanghan
     * @date 2021/10/20
     */
    byte[] take(String path);

    /**
     * 删除文件
     *
     * @param path 文件路径，即保存时得到的路径
     * @author yanghan
     * @date 2021/10/20
     */
    void remove(String path);
}
