package it.perigea.formazione.comune;

import java.time.ZonedDateTime;
import java.util.Date;

import org.bson.types.ObjectId;

public class ClinicalStatusDto {

	private ObjectId id;
	private Date dataInizioSintomi;
	private int casiTotali;
	private int nessunoStatoClinico;
	private int guariti;
	private int deceduti;
	private int asintomatici;
	private int conSintomi;
	private int conSintomiGuariti;
	private int conSintomiDecessi;
	private int asintomaticiGuariti;
	private int asintomaticiDeceduti;
	private ZonedDateTime data;
	private String date;


	public ClinicalStatusDto() {

	}


	public ClinicalStatusDto(Date dataInizioSintomi, int casiTotali, int nessunoStatoClinico, int guariti, 
			int deceduti,int asintomatici, int conSintomi, int conSintomiGuariti, int conSintomiDecessi, 
			int asintomaticiGuariti, int asintomaticiDeceduti, ZonedDateTime data) {
		super();
		this.dataInizioSintomi = dataInizioSintomi;
		this.casiTotali = casiTotali;
		this.nessunoStatoClinico = nessunoStatoClinico;
		this.guariti = guariti;
		this.deceduti = deceduti;
		this.asintomatici = asintomatici;
		this.conSintomi = conSintomi;
		this.conSintomiGuariti = conSintomiGuariti;
		this.conSintomiDecessi = conSintomiDecessi;
		this.asintomaticiGuariti = asintomaticiGuariti;
		this.asintomaticiDeceduti = asintomaticiDeceduti;
		this.data = data;
	}


	public Date getDataInizioSintomi() {
		return dataInizioSintomi;
	}


	public void setDataInizioSintomi(Date dataInizioSintomi) {
		this.dataInizioSintomi = dataInizioSintomi;
	}


	public int getCasiTotali() {
		return casiTotali;
	}


	public void setCasiTotali(int casiTotali) {
		this.casiTotali = casiTotali;
	}


	public int getNessunoStatoClinico() {
		return nessunoStatoClinico;
	}


	public void setNessunoStatoClinico(int nessunoStatoClinico) {
		this.nessunoStatoClinico = nessunoStatoClinico;
	}


	public int getGuariti() {
		return guariti;
	}


	public void setGuariti(int guariti) {
		this.guariti = guariti;
	}


	public int getDeceduti() {
		return deceduti;
	}


	public void setDeceduti(int deceduti) {
		this.deceduti = deceduti;
	}


	public int getAsintomatici() {
		return asintomatici;
	}


	public void setAsintomatici(int asintomatici) {
		this.asintomatici = asintomatici;
	}


	public int getConSintomi() {
		return conSintomi;
	}


	public void setConSintomi(int conSintomi) {
		this.conSintomi = conSintomi;
	}


	public int getConSintomiGuariti() {
		return conSintomiGuariti;
	}


	public void setConSintomiGuariti(int conSintomiGuariti) {
		this.conSintomiGuariti = conSintomiGuariti;
	}


	public int getConSintomiDecessi() {
		return conSintomiDecessi;
	}


	public void setConSintomiDecessi(int conSintomiDecessi) {
		this.conSintomiDecessi = conSintomiDecessi;
	}


	public int getAsintomaticiGuariti() {
		return asintomaticiGuariti;
	}


	public void setAsintomaticiGuariti(int asintomaticiGuariti) {
		this.asintomaticiGuariti = asintomaticiGuariti;
	}


	public int getAsintomaticiDeceduti() {
		return asintomaticiDeceduti;
	}


	public void setAsintomaticiDeceduti(int asintomaticiDeceduti) {
		this.asintomaticiDeceduti = asintomaticiDeceduti;
	}


	public ZonedDateTime getData() {
		return data;
	}


	public void setData(ZonedDateTime data) {
		this.data = data;
	}


	public String getDate() {
		return date;
	}


	public void setDate(String date) {
		this.date = date;
	}


}
