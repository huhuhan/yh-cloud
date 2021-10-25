package com.yh.cloud.file.service.impl;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.yh.cloud.base.constant.BaseConstant;
import com.yh.common.file.model.ObjectInfoPo;
import com.yh.common.file.uploader.AbstractUploader;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;


/**
 * 扩展：数据库表存储文件二进制流
 *
 * @author yanghan
 * @date 2021/8/9
 */
@Service
public class DatabaseUploader extends AbstractUploader {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public static final String TYPE = "database";

    @Override
    public String type() {
        return TYPE;
    }

    @Override
    @SneakyThrows
    public ObjectInfoPo upload(MultipartFile file) {
        String id = IdWorker.get32UUID();
        jdbcTemplate.update("insert into " + BaseConstant.GLOBAL_PREFIX + "sys_db_uploader values (?,?)", id, file.getBytes());
        // 构建对象
        ObjectInfoPo objectInfoPo = new ObjectInfoPo();
        objectInfoPo.setPath(id);
        objectInfoPo.setHash(super.getHash(file));
        return objectInfoPo;
    }

    @Override
    public byte[] take(String path) {
        List<Map<String, Object>> mapList = jdbcTemplate.queryForList("select * from " + BaseConstant.GLOBAL_PREFIX + "sys_db_uploader where id_ = ?", path);
        return (byte[]) mapList.get(0).get("bytes_");
    }

    @Override
    public boolean remove(String path, String hash) {
        return jdbcTemplate.update("delete from " + BaseConstant.GLOBAL_PREFIX + "sys_db_uploader where id_ = ?", path) > 0;
    }

}
