package com.accelbyte.test.analytics.model;

import java.util.Date;

public class LogDetail {

	private String ip;
	private String userIdentifier;
	private String userName;
	private Date dateCreated;
	private String httpMethod;
	private String httpPath;
	private String httpVersion;
	private Integer httpStatusCode;
	private String additionalInfo;
	public LogDetail(String ip, String userIdentifier, String userName, Date dateCreated, String httpMethod,
			String httpPath, String httpVersion, Integer httpStatusCode, String additionalInfo) {
		this.ip = ip;
		this.userIdentifier = userIdentifier;
		this.userName = userName;
		this.dateCreated = dateCreated;
		this.httpMethod = httpMethod;
		this.httpPath = httpPath;
		this.httpVersion = httpVersion;
		this.httpStatusCode = httpStatusCode;
		this.additionalInfo = additionalInfo;
	}
	public LogDetail(){
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getUserIdentifier() {
		return userIdentifier;
	}
	public void setUserIdentifier(String userIdentifier) {
		this.userIdentifier = userIdentifier;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Date getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	public String getHttpMethod() {
		return httpMethod;
	}
	public void setHttpMethod(String httpMethod) {
		this.httpMethod = httpMethod;
	}
	public String getHttpPath() {
		return httpPath;
	}
	public void setHttpPath(String httpPath) {
		this.httpPath = httpPath;
	}
	public String getHttpVersion() {
		return httpVersion;
	}
	public void setHttpVersion(String httpVersion) {
		this.httpVersion = httpVersion;
	}
	public Integer getHttpStatusCode() {
		return httpStatusCode;
	}
	public void setHttpStatusCode(Integer httpStatusCode) {
		this.httpStatusCode = httpStatusCode;
	}
	public String getAdditionalInfo() {
		return additionalInfo;
	}
	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}
	@Override
	public String toString() {
		return "LogDetail [ip=" + ip + ", userIdentifier=" + userIdentifier + ", userName=" + userName
				+ ", dateCreated=" + dateCreated + ", httpMethod=" + httpMethod + ", httpPath=" + httpPath
				+ ", httpVersion=" + httpVersion + ", httpStatusCode=" + httpStatusCode + ", additionalInfo=" + additionalInfo
				+ "]";
	}
	
}
