package it.perigea.formazione.aggregator.entity;

import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document("clinical")
public class ClinicalStatusEntity {

	@Id
	@Field("_id")
	private ObjectId id1;

	@Field("inizio_sintomi")
	private Date dataInizioSintomi;

	@Field("casi_totali")
	private int casiTotali;

	@Field("nessuno_stato_clinico")
	private int nessunoStatoClinico;

	@Field("guariti")
	private int guariti;

	@Field("deceduti")
	private int deceduti;

	@Field("asintomatici")
	private int asintomatici;

	@Field("Sintomatici")
	private int conSintomi;

	@Field("sintomi-guariti")
	private int conSintomiGuariti;

	@Field("sintomi-deceduti")
	private int conSintomiDecessi;

	@Field("asintomatici-guariti")
	private int asintomaticiGuariti;

	@Field("Sintomatici-deceduti")
	private int asintomaticiDeceduti;

	@Field("data")
	private Date data;

	@Field("dataString")
	private String date;

	public ObjectId getId1() {
		return id1;
	}

	public void setId1(ObjectId id1) {
		this.id1 = id1;
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

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
}
