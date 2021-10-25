package com.yh.cloud.file.service;

import com.yh.cloud.file.model.entity.SysFile;
import com.yh.cloud.file.model.vo.SysFileQuery;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yh.common.db.service.ISuperService;
import org.springframework.web.multipart.MultipartFile;

/**
 * 系统附件表
 *
 * @author yanghan
 * @date 2021-10-20 11:15:16
 */
public interface ISysFileService extends ISuperService<SysFile> {
    /**
     * 列表
     *
     * @param sysFileQuery 查询对象
     * @return com.baomidou.mybatisplus.core.metadata.IPage
     * @author yanghan
     * @date 2021-10-20 11:15:16
     */
    IPage<SysFile> findList(SysFileQuery<SysFile> sysFileQuery);

    /**
     * 上传文件
     *
     * @param file {@link MultipartFile}
     * @param uploaderType 上传器类型
     * @return com.yh.cloud.file.model.entity.SysFile
     * @author yanghan
     * @date 2021/10/20
     */
    SysFile uploadFile(MultipartFile file, String uploaderType);

    /**
     * 下载文件，返回字节数据
     *
     * @param fileId 文件主键
     * @param uploaderType 上传器
     * @return byte[]
     * @author yanghan
     * @date 2021/10/20
     */
    byte[] download(String fileId, String uploaderType);

    /**
     * 删除文件
     *
     * @param fileId 文件主键
     * @param uploaderType  上传器
     * @return boolean
     * @author yanghan
     * @date 2021/10/20
     */
    boolean removeFile(Long fileId, String uploaderType);
}

