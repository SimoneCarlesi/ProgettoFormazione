package node.model;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;

public class SomministrationDTO {
	
	private int codistat_comune_dom;
	private String comune_dom;
	private String provincia_dom;
	private int tot_dose1;
	private int tot_dose2;
	private String sigla;
	private ZonedDateTime data;
	
	public SomministrationDTO() {
		
	}
	
	public SomministrationDTO(int codistat_comune_dom, String comune_dom, String provincia_dom, int tot_dose1,
			int tot_dose2, String sigla, ZonedDateTime data) {
		super();
		this.codistat_comune_dom = codistat_comune_dom;
		this.comune_dom = comune_dom;
		this.provincia_dom = provincia_dom;
		this.tot_dose1 = tot_dose1;
		this.tot_dose2 = tot_dose2;
		this.sigla = sigla;
		this.data = data;
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
