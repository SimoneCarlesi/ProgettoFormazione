package node.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="checkprovince")
public class SiglaEntity {
	
	@Id
	@Column(name="codice")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int codice;
	
	@Column(name="provincia")
	private String provincia;
	
	@Column(name="sigla")
	private String sigla;
	
    @Column(name="regione")
    private String regione;

	public int getCodice() {
		return codice;
	}

	public void setCodice(int codice) {
		this.codice = codice;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setNome(String provincia) {
		this.provincia = provincia;
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public String getRegione() {
		return regione;
	}

	public void setRegione(String regione) {
		this.regione = regione;
	}
    
    
}
