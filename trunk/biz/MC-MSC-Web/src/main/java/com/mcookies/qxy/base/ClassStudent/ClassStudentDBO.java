package com.mcookies.qxy.base.ClassStudent;
import java.sql.Date;

import javax.inject.Named;

import org.isotope.jfp.framework.support.MyDataBaseObjectSupport;
 
@Named
/** 班级学生关联表*/
public class ClassStudentDBO extends MyDataBaseObjectSupport
{
    /** 
     * 自增id
     */
    private Long id = null;
 
    /** 
     * 学校id
     */
    private Long sid = null;
 
    /** 
     * 班级id
     */
    private Long cid = null;
 
    /** 
     * 学生id
     */
    private Long studentId = null;
 
    /** 
     * 加入班级时间
     */
    private Date joinTime = null;
 
    /** 
     * 退出班级时间
     */
    private Date exitTime = null;
 
    /** 
     * 是否启用
     */
    private Integer isUse = null;
 
    /** 
     * 创建时间
     */
    private String createTime = null;
 
    /** 
     * 创建者
     */
    private Long creator = null;
 
    /** 
     * 更新时间
     */
    private String updateTime = null;
 
    /** 
     * 最后更新者
     */
    private Long updator = null;
 
    /** 
     * 获取自增id
     *
     * @return Id 自增id
     */
    public Long getId() {
        return this.id;
    }
 
    /** 
     * 获取学校id
     *
     * @return Sid 学校id
     */
    public Long getSid() {
        return this.sid;
    }
 
    /** 
     * 获取班级id
     *
     * @return Cid 班级id
     */
    public Long getCid() {
        return this.cid;
    }
 
    /** 
     * 获取学生id
     *
     * @return Student_id 学生id
     */
    public Long getStudentId() {
        return this.studentId;
    }
 
    /** 
     * 获取加入班级时间
     *
     * @return Join_time 加入班级时间
     */
    public Date getJoinTime() {
        return this.joinTime;
    }
 
    /** 
     * 获取退出班级时间
     *
     * @return Exit_time 退出班级时间
     */
    public Date getExitTime() {
        return this.exitTime;
    }
 
    /** 
     * 获取是否启用
     *
     * @return Is_use 是否启用
     */
    public Integer getIsUse() {
        return this.isUse;
    }
 
    /** 
     * 获取创建时间
     *
     * @return Create_time 创建时间
     */
    public String getCreateTime() {
        return this.createTime;
    }
 
    /** 
     * 获取创建者
     *
     * @return Creator 创建者
     */
    public Long getCreator() {
        return this.creator;
    }
 
    /** 
     * 获取更新时间
     *
     * @return Update_time 更新时间
     */
    public String getUpdateTime() {
        return this.updateTime;
    }
 
    /** 
     * 获取最后更新者
     *
     * @return Updator 最后更新者
     */
    public Long getUpdator() {
        return this.updator;
    }
 
    /** 
     * 设置自增id
     *
     * @param Id 自增id
     */
    public void setId(Long id) {
        this.id = id;
    }
 
    /** 
     * 设置学校id
     *
     * @param Sid 学校id
     */
    public void setSid(Long sid) {
        this.sid = sid;
    }
 
    /** 
     * 设置班级id
     *
     * @param Cid 班级id
     */
    public void setCid(Long cid) {
        this.cid = cid;
    }
 
    /** 
     * 设置学生id
     *
     * @param Student_id 学生id
     */
    public void setStudentId(Long studentid) {
        this.studentId = studentid;
    }
 
    /** 
     * 设置加入班级时间
     *
     * @param Join_time 加入班级时间
     */
    public void setJoinTime(Date jointime) {
        this.joinTime = jointime;
    }
 
    /** 
     * 设置退出班级时间
     *
     * @param Exit_time 退出班级时间
     */
    public void setExitTime(Date exittime) {
        this.exitTime = exittime;
    }
 
    /** 
     * 设置是否启用
     *
     * @param Is_use 是否启用
     */
    public void setIsUse(Integer isuse) {
        this.isUse = isuse;
    }
 
    /** 
     * 设置创建时间
     *
     * @param Create_time 创建时间
     */
    public void setCreateTime(String createtime) {
        this.createTime = createtime;
    }
 
    /** 
     * 设置创建者
     *
     * @param Creator 创建者
     */
    public void setCreator(Long creator) {
        this.creator = creator;
    }
 
    /** 
     * 设置更新时间
     *
     * @param Update_time 更新时间
     */
    public void setUpdateTime(String updatetime) {
        this.updateTime = updatetime;
    }
 
    /** 
     * 设置最后更新者
     *
     * @param Updator 最后更新者
     */
    public void setUpdator(Long updator) {
        this.updator = updator;
    }
 
}
