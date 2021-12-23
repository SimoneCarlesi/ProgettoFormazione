package it.perigea.formazione.schedule.entity;



import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "schedule_process")
public class ScheduleEntity {

	@Id
	@Column(name = "id_schedule", updatable = false, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idSchedule;
	@Column(name = "api_method_name")
	private String APIMethodName;
	@Column(name = "status")
	private String status;
	@Column(name = "beginning_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date begginingDate;

	public Integer getIdSchedule() {
		return idSchedule;
	}

	public void setIdSchedule(Integer idSchedule) {
		this.idSchedule = idSchedule;
	}

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

	public void setBegginingDate(Date begginingDate) {
		this.begginingDate = begginingDate;
	}

}