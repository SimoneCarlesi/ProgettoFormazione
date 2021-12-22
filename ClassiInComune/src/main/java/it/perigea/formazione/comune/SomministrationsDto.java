package it.perigea.formazione.comune;

import java.time.ZonedDateTime;
import org.bson.types.ObjectId;

public class SomministrationsDto {

	private ObjectId id;
	private int codistatComuneDom;
	private String comuneDom;
	private String provinciaDom;
	private int totDose1;
	private int totDose2;
	private String sigla;
	private ZonedDateTime data;
	private String date;

	public SomministrationsDto() {

	}


	public SomministrationsDto(int codistatComuneDom, String comuneDom, String provinciaDom, int totDose1, int totDose2,
			String sigla, ZonedDateTime data) {
		super();
		this.codistatComuneDom = codistatComuneDom;
		this.comuneDom = comuneDom;
		this.provinciaDom = provinciaDom;
		this.totDose1 = totDose1;
		this.totDose2 = totDose2;
		this.sigla = sigla;
		this.data = data;
	}




	public String getDate() {
		return date;
	}


	public void setDate(String date) {
		this.date = date;
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


	public String getSigla() {
		return sigla;
	}


	public void setSigla(String sigla) {
		this.sigla = sigla;
	}


	public ZonedDateTime getData() {
		return data;
	}


	public void setData(ZonedDateTime data) {
		this.data = data;
	}

}