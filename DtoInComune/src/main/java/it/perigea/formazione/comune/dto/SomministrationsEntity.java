package it.perigea.formazione.comune.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="")
public class SomministrationsEntity {
	
	@Id
	@Column(name="id")
	private int id;
	
	@Column(name="data")
	private Date data;
	
	@Column(name="codice_comune")
	private int codistat_comune_dom;
	
    @Column(name="comune_dom")
    private String comune_dom;
     
    @Column(name="provincia_dom")
    private String provincia_dom;
    
    @Column(name="sigla")
    private String sigla;
    
    @Column (name="totdose1")
    private int tot_dose1;
    
    @Column (name="totdose2")
    private int tot_dose2;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCodistat_comune_dom() {
		return codistat_comune_dom;
	}

	public void setCodistat_comune_dom(int codistat_comune_dom) {
		this.codistat_comune_dom = codistat_comune_dom;
	}

	public String getComune_dom() {
		return comune_dom;
	}

	public void setComune_dom(String comune_dom) {
		this.comune_dom = comune_dom;
	}

	public String getProvincia_dom() {
		return provincia_dom;
	}

	public void setProvincia_dom(String provincia_dom) {
		this.provincia_dom = provincia_dom;
	}

	public int getTot_dose1() {
		return tot_dose1;
	}

	public void setTot_dose1(int tot_dose1) {
		this.tot_dose1 = tot_dose1;
	}

	public int getTot_dose2() {
		return tot_dose2;
	}

	public void setTot_dose2(int tot_dose2) {
		this.tot_dose2 = tot_dose2;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}
    
	
    
    
}