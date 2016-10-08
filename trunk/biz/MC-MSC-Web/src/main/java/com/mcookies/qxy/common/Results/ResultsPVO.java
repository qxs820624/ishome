package com.mcookies.qxy.common.Results;

import javax.inject.Named;
 
@Named
/** 成绩表*/
public class ResultsPVO extends ResultsDBO{
    /** 
     * 班级名称
     */
    private String className = null;
    /** 
     *  学生成绩总分
     */
    private Integer totalPoints = null;	
    /** 
     * 德育班级给合排名
     */
    private String ranking = null;
    /** 
     * 德育班级课程分数
     */
    private String courseScore = null;
	/**
	 * 学号
	 */
	private Long number = null;
	
    /** 
     * 学生id
     */
    private Long studentId = null;
 
    /** 
     * 学生姓名
     */
    private String studentName = null;
	
    /** 
     * 成绩标签名称
     */
    private String resultsTagName = null;
    /** 
     * 德育班级课程排名
     */
    private String courseRanking = null;
   
    /** 
     * 德育班级课程名称
     */
    private String courseName = null;
    /** 
     * 学期Id
     */
    private Long termId = null;
    /** 
     * 老师Id
     */
    private Long tid = null;
    /** 
     * 老师Id
     */
    private Long resultsTagId = null;
    /** 
     * 德育年级id
     */
    private Long gradeId = null;
    /** 
     * 德育班级名称
     */
    private String gradeName = null;
    
    
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public Integer getTotalPoints() {
		return totalPoints;
	}
	public void setTotalPoints(Integer totalPoints) {
		this.totalPoints = totalPoints;
	}
	public String getRanking() {
		return ranking;
	}
	public void setRanking(String ranking) {
		this.ranking = ranking;
	}
	public Long getNumber() {
		return number;
	}
	public void setNumber(Long number) {
		this.number = number;
	}
	public String getResultsTagName() {
		return resultsTagName;
	}
	public void setResultsTagName(String resultsTagName) {
		this.resultsTagName = resultsTagName;
	}
	public Long getStudentId() {
		return studentId;
	}
	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getCourseRanking() {
		return courseRanking;
	}
	public void setCourseRanking(String courseRanking) {
		this.courseRanking = courseRanking;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getCourseScore() {
		return courseScore;
	}
	public void setCourseScore(String courseScore) {
		this.courseScore = courseScore;
	}
	public Long getTermId() {
		return termId;
	}
	public void setTermId(Long termId) {
		this.termId = termId;
	}
	public Long getTid() {
		return tid;
	}
	public void setTid(Long tid) {
		this.tid = tid;
	}
	public Long getGradeId() {
		return gradeId;
	}
	public void setGradeId(Long gradeId) {
		this.gradeId = gradeId;
	}
	public String getGradeName() {
		return gradeName;
	}
	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}
	public Long getResultsTagId() {
		return resultsTagId;
	}
	public void setResultsTagId(Long resultsTagId) {
		this.resultsTagId = resultsTagId;
	}

	
}