package it.perigea.formazione.schedule.dto;

public class URLDto {
	private String APIMethodName;
	private String URLString;
	private String returnType;
	

	public String getReturnType() {
		return returnType;
	}

	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}

	public String getAPIMethodName() {
		return APIMethodName;
	}

	public void setAPIMethodName(String aPIMethodName) {
		APIMethodName = aPIMethodName;
	}

	public String getURLString() {
		return URLString;
	}

	public void setURLString(String uRLString) {
		URLString = uRLString;
	}

}