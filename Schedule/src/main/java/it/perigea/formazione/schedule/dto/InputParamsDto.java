package it.perigea.formazione.schedule.dto;

public class InputParamsDto {
	private String APIMethodName;
	private String param;

	public String getAPIMethodName() {
		return APIMethodName;
	}

	public void setAPIMethodName(String aPIMethodName) {
		APIMethodName = aPIMethodName;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

}