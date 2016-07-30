CREATE TABLE class_course
(
    id BIGINT(12) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增id',
    sid BIGINT(12) NOT NULL COMMENT '学校id',
    cid BIGINT(12) NOT NULL COMMENT '班级id',
    date DATETIME COMMENT '日期',
    course_id1 BIGINT(12) COMMENT '课程1id',
    course_name1 VARCHAR(50) COMMENT '课程1名称',
    tid1 BIGINT(12) COMMENT '教师1id',
    t_name1 VARCHAR(50) COMMENT '教师1姓名',
    course_id2 BIGINT(12) COMMENT '课程2id',
    course_name2 VARCHAR(50) COMMENT '课程2名称',
    tid2 BIGINT(12) COMMENT '教师2id',
    t_name2 VARCHAR(50) COMMENT '教师2姓名',
    course_id3 BIGINT(12) COMMENT '课程3id',
    course_name3 VARCHAR(50) COMMENT '课程3名称',
    tid3 BIGINT(12) COMMENT '教师3id',
    t_name3 VARCHAR(50) COMMENT '教师3姓名',
    course_id4 BIGINT(12) COMMENT '课程4id',
    course_name4 VARCHAR(50) COMMENT '课程4名称',
    tid4 BIGINT(12) COMMENT '教师4id',
    t_name4 VARCHAR(50) COMMENT '教师4姓名',
    course_id5 BIGINT(12) COMMENT '课程5id',
    course_name5 VARCHAR(50) COMMENT '课程5名称',
    tid5 BIGINT(12) COMMENT '教师5id',
    t_name5 VARCHAR(50) COMMENT '教师5姓名',
    course_id6 BIGINT(12) COMMENT '课程6id',
    course_name6 VARCHAR(50) COMMENT '课程6名称',
    tid6 BIGINT(12) COMMENT '教师6id',
    t_name6 VARCHAR(50) COMMENT '教师6姓名',
    course_id7 BIGINT(12) COMMENT '课程7id',
    course_name7 VARCHAR(50) COMMENT '课程7名称',
    tid7 BIGINT(12) COMMENT '教师7id',
    t_name7 VARCHAR(50) COMMENT '教师7姓名',
    course_id8 BIGINT(12) COMMENT '课程8id',
    course_name8 VARCHAR(50) COMMENT '课程8名称',
    tid8 BIGINT(12) COMMENT '教师8id',
    t_name8 VARCHAR(50) COMMENT '教师8姓名',
    course_id9 BIGINT(12) COMMENT '课程9id',
    course_name9 VARCHAR(50) COMMENT '课程9名称',
    tid9 BIGINT(12) COMMENT '教师9id',
    t_name9 VARCHAR(50) COMMENT '教师9姓名',
    course_id10 BIGINT(12) COMMENT '课程10id',
    course_name10 VARCHAR(50) COMMENT '课程10名称',
    tid10 BIGINT(12) COMMENT '教师10id',
    t_name10 VARCHAR(50) COMMENT '教师10姓名',
    course_id11 BIGINT(12) COMMENT '课程11id',
    course_name11 VARCHAR(50) COMMENT '课程11名称',
    tid11 BIGINT(12) COMMENT '教师11id',
    t_name11 VARCHAR(50) COMMENT '教师11姓名',
    course_id12 BIGINT(12) COMMENT '课程12id',
    course_name12 VARCHAR(50) COMMENT '课程12名称',
    tid12 BIGINT(12) COMMENT '教师12id',
    t_name12 VARCHAR(50) COMMENT '教师12姓名',
    course_id13 BIGINT(12) COMMENT '课程13id',
    course_name13 VARCHAR(50) COMMENT '课程13名称',
    tid13 BIGINT(12) COMMENT '教师13id',
    t_name13 VARCHAR(50) COMMENT '教师13姓名',
    course_id14 BIGINT(12) COMMENT '课程14id',
    course_name14 VARCHAR(50) COMMENT '课程14名称',
    tid14 BIGINT(12) COMMENT '教师14id',
    t_name14 VARCHAR(50) COMMENT '教师14姓名',
    course_id15 BIGINT(12) COMMENT '课程15id',
    course_name15 VARCHAR(50) COMMENT '课程15名称',
    tid15 BIGINT(12) COMMENT '教师15id',
    t_name15 BIGINT(50) COMMENT '教师15姓名',
    is_use TINYINT(1) COMMENT '是否启用',
    create_time VARCHAR(24) COMMENT '创建时间',
    creator BIGINT(12) COMMENT '创建者',
    update_time VARCHAR(24) COMMENT '更新时间',
    updator BIGINT(12) COMMENT '最后更新者',
PRIMARY KEY (id)
) COMMENT '班级课程教师关联表'
;
