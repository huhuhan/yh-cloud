package com.yh.common.log.service.impl;

import cn.hutool.core.util.StrUtil;
import com.yh.common.log.config.AuditLogProperties;
import com.yh.common.log.model.enrity.Audit;
import com.yh.common.log.model.properties.DbAuditPo;
import com.yh.common.log.service.IAuditService;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Async;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;


/**
 * 存储数据库
 *
 * @author yanghan
 * @date 2022/1/4
 */
@Slf4j
public class DbAuditServiceImpl implements IAuditService {
    private static final String INSERT_SQL = " insert into operation_logger " +
            " (application_name, class_name, method_name, user_id, user_name,  operation, operation_time) " +
            " values (?,?,?,?,?,?,?)";

    private final JdbcTemplate jdbcTemplate;

    public DbAuditServiceImpl(AuditLogProperties auditLogProperties, DataSource dataSource) {
        DbAuditPo dbAuditPo = auditLogProperties.getDatasource();
        //优先使用配置的日志数据源，否则使用默认的数据源
        if (dbAuditPo != null && StrUtil.isNotEmpty(dbAuditPo.getJdbcUrl())) {
            dataSource = new HikariDataSource(dbAuditPo);
        }
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @PostConstruct
    public void init() {
        String sql = "CREATE TABLE IF NOT EXISTS `operation_logger`  (\n" +
                "  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',\n" +
                "  `application_name` varchar(32) DEFAULT NULL COMMENT '应用名',\n" +
                "  `class_name` varchar(128) NOT NULL COMMENT '类名',\n" +
                "  `method_name` varchar(64) NOT NULL COMMENT '方法名',\n" +
                "  `user_id` varchar(32) DEFAULT NULL COMMENT '用户id',\n" +
                "  `user_name` varchar(64) DEFAULT NULL COMMENT '用户名',\n" +
                "  `operation` varchar(1024) NOT NULL COMMENT '操作信息',\n" +
                "  `operation_time` varchar(32) NOT NULL COMMENT '操作时间',\n" +
                "  PRIMARY KEY (`id`) USING BTREE\n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;";
        this.jdbcTemplate.execute(sql);
    }

    @Async
    @Override
    public void todo(Audit audit) {
        this.jdbcTemplate.update(INSERT_SQL
                , audit.getApplicationName(), audit.getClassName(), audit.getMethodName()
                , audit.getUserId(), audit.getUserName()
                , audit.getOperation(), audit.getTimestamp());
    }
}
