package it.perigea.formazione.schedule.dto;


import java.util.Date;

public class ScheduleDto {
	private String APIMethodName;
	private String status;
	private Date begginingDate;;

	

	public String getAPIMethodName() {
		return APIMethodName;
	}

	public void setAPIMethodName(String aPIMethodName) {
		APIMethodName = aPIMethodName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getBegginingDate() {
		return begginingDate;
	}

	public void setBegginingDate(Date date) {
		this.begginingDate = date;
	}

}