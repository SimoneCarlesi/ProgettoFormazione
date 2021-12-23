package it.perigea.formazione.schedule.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name = "cronTable")
public class CronEntity {

	@Id
	@Column(name = "id_cron_trigger", nullable = false)
	private Integer idCron;
	
	@Column(name = "cron_string")
	private String cronString;

	public Integer getIdCron() {
		return idCron;
	}

	public void setIdCron(Integer idCron) {
		this.idCron = idCron;
	}

	public String getCronString() {
		return cronString;
	}

	public void setCronString(String cronString) {
		this.cronString = cronString;
	}

}
