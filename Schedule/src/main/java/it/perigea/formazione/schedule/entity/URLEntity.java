package it.perigea.formazione.schedule.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "url_table")
public class URLEntity {

	@Id
	@Column(name = "id_method", updatable = false, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idMethod;
	@Column(name = "api_method_name")
	private String APIMethodName;
	@Column(name = "url_string")
	private String URLString;
	@Column(name = "type_of_return")
	private String returnType;

	public Integer getIdMethod() {
		return idMethod;
	}

	public void setIdMethod(Integer idMethod) {
		this.idMethod = idMethod;
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

	public void setURLString(String URLString) {
		this.URLString = URLString;
	}

	public String getReturnType() {
		return returnType;
	}

	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}

}