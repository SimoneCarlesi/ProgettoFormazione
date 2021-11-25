package node.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="processo")
public class ProcessEntity {
	
	@Id
	@Column(name="uuid_processo")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int uuid;
	
	@Column(name="data_ora")
	private Date dataOra;
	
	@Column(name="status")
	private String status;

	public int getUuid() {
		return uuid;
	}

	public void setUuid(int uuid) {
		this.uuid = uuid;
	}

	public Date getDataOra() {
		return dataOra;
	}

	public void setDataOra(Date dataOra) {
		this.dataOra = dataOra;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
}