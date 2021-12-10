package it.perigea.formazione.aggregator.entity;

import java.util.Date;


import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;



@Document("somministrations")
public class SomministrationsEntity {
	
	@Id
	@Field("_id")
	private ObjectId id1;
	
	@Field("codice")
	private int codistatComuneDom;
	
	@Field("dataDate")
	private Date data;
	
	@Field("dataString")
	private String date;
	
	@Field("comune")
    private String comuneDom;
		
	@Field("provincia")
    private String provinciaDom;
	
	@Field("sigla")
    private String sigla;
	
	@Field("dose1")
    private int totDose1;
	
	@Field("dose2")
    private int totDose2;

    
    
	public ObjectId getId1() {
		return id1;
	}

	public void setId1(ObjectId id1) {
		this.id1 = id1;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public int getCodistatComuneDom() {
		return codistatComuneDom;
	}

	public void setCodistatComuneDom(int codistatComuneDom) {
		this.codistatComuneDom = codistatComuneDom;
	}

	public String getComuneDom() {
		return comuneDom;
	}

	public void setComuneDom(String comuneDom) {
		this.comuneDom = comuneDom;
	}

	public String getProvinciaDom() {
		return provinciaDom;
	}

	public void setProvinciaDom(String provinciaDom) {
		this.provinciaDom = provinciaDom;
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public int getTotDose1() {
		return totDose1;
	}

	public void setTotDose1(int totDose1) {
		this.totDose1 = totDose1;
	}

	public int getTotDose2() {
		return totDose2;
	}

	public void setTotDose2(int totDose2) {
		this.totDose2 = totDose2;
	}


	
}