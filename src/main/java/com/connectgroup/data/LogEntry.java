package com.connectgroup.data;

import java.time.LocalDateTime;
import java.util.Objects;


public class LogEntry {

	private LocalDateTime requestTimeStamp;
	private String countryCode;
	private Long responseTime;

	public LocalDateTime getRequestTimeStamp() {
		return requestTimeStamp;
	}

	public void setRequestTimeStamp(LocalDateTime requestTimeStamp) {
		this.requestTimeStamp = requestTimeStamp;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public Long getResponseTime() {
		return responseTime;
	}

	public void setResponseTime(Long responseTime) {
		this.responseTime = responseTime;
	}

	@Override
	public int hashCode() {
		return Objects.hash(requestTimeStamp, countryCode, responseTime);
	}

	@Override
	public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        LogEntry logEntry = (LogEntry) obj;
        return Objects.equals(requestTimeStamp, logEntry.requestTimeStamp) && Objects.equals(countryCode, logEntry.countryCode) && Objects.equals(responseTime, logEntry.responseTime);
    
	}

	@Override
	public String toString() {
		return "Data [requestTimeStamp=" + requestTimeStamp + ", countryCode=" + countryCode + ", responseTime="
				+ responseTime + "]";
	}
  
}
