CREATE TABLE oa_examine_result
(
    id BIGINT(12) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增id',
    sid BIGINT(12) NOT NULL COMMENT '学校id',
    tid BIGINT(12) NOT NULL COMMENT '审核人id',
    approval_information_id BIGINT(12) COMMENT '审核信息id',
    rule_id BIGINT(12) COMMENT '规则id',
    rule_status TINYINT(1) COMMENT '规则状态',
    result TINYINT(1) COMMENT '审核结果',
    time DATETIME COMMENT '审核时间',
    content VARCHAR(40) COMMENT '审核意见',
    is_use TINYINT(1) COMMENT '是否启用',
    create_time VARCHAR(24) COMMENT '创建时间',
    creator BIGINT(12) COMMENT '创建者',
    update_time VARCHAR(24) COMMENT '更新时间',
    updator BIGINT(12) COMMENT '最后更新者',
PRIMARY KEY (id)
) COMMENT 'OA审批结果表'
;
