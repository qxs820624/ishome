package com.mcookies.qxy.base.SWorkRule;
import java.sql.Date;

import javax.inject.Named;

import org.isotope.jfp.framework.support.MyDataBaseObjectSupport;
 
@Named
/** 作息时间规则表*/
public class SWorkRuleDBO extends MyDataBaseObjectSupport
{
    /** 
     * 作息时间规则id
     */
    private Long workRuleId = null;
 
    /** 
     * 作息时间模板id
     */
    private Long workId = null;
 
    /** 
     * 学校id
     */
    private Long sid = null;
 
    /** 
     * 作息时间规则名称
     */
    private String ruleName = null;
 
    /** 
     * 规则对应时间段
     */
    private Integer ruleStage = null;
 
    /** 
     * 开始时间
     */
    private Date startTime = null;
 
    /** 
     * 结束时间
     */
    private Date endTime = null;
 
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
     * 获取作息时间规则id
     *
     * @return Work_rule_id 作息时间规则id
     */
    public Long getWorkRuleId() {
        return this.workRuleId;
    }
 
    /** 
     * 获取作息时间模板id
     *
     * @return Work_id 作息时间模板id
     */
    public Long getWorkId() {
        return this.workId;
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
     * 获取作息时间规则名称
     *
     * @return Rule_name 作息时间规则名称
     */
    public String getRuleName() {
        return this.ruleName;
    }
 
    /** 
     * 获取规则对应时间段
     *
     * @return Rule_stage 规则对应时间段
     */
    public Integer getRuleStage() {
        return this.ruleStage;
    }
 
    /** 
     * 获取开始时间
     *
     * @return Start_time 开始时间
     */
    public Date getStartTime() {
        return this.startTime;
    }
 
    /** 
     * 获取结束时间
     *
     * @return End_time 结束时间
     */
    public Date getEndTime() {
        return this.endTime;
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
     * 设置作息时间规则id
     *
     * @param Work_rule_id 作息时间规则id
     */
    public void setWorkRuleId(Long workruleid) {
        this.workRuleId = workruleid;
    }
 
    /** 
     * 设置作息时间模板id
     *
     * @param Work_id 作息时间模板id
     */
    public void setWorkId(Long workid) {
        this.workId = workid;
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
     * 设置作息时间规则名称
     *
     * @param Rule_name 作息时间规则名称
     */
    public void setRuleName(String rulename) {
        this.ruleName = rulename;
    }
 
    /** 
     * 设置规则对应时间段
     *
     * @param Rule_stage 规则对应时间段
     */
    public void setRuleStage(Integer rulestage) {
        this.ruleStage = rulestage;
    }
 
    /** 
     * 设置开始时间
     *
     * @param Start_time 开始时间
     */
    public void setStartTime(Date starttime) {
        this.startTime = starttime;
    }
 
    /** 
     * 设置结束时间
     *
     * @param End_time 结束时间
     */
    public void setEndTime(Date endtime) {
        this.endTime = endtime;
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
