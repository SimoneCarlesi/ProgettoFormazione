package it.perigea.formazione.aggregator.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import it.perigea.formazione.aggregator.entity.SomministrationsEntity;
import it.perigea.formazione.aggregator.repository.SomministrationsRepository;
import it.perigea.formazione.comune.SomministrationsDto;

public class APIService {
	
	@Autowired
	private SomministrationsRepository sommRepository;
	
	//Metodo che restituisce il totale della prima dose effettuate in tutta la Lombardia
	public int getSomministrationsBySingleDose() {
		List<SomministrationsEntity> list = sommRepository.findBySingleDose();
		int somma=0;
		for(SomministrationsEntity somm: list) {
			somma=somma+somm.getTot_dose1();
		}
		return somma;
	}
	
	// Metodo che restituisce il totale della prima dose effettuate in tutta la
	// Lombardia
	public int getSomministrationsByDoubleDose() {
		List<SomministrationsEntity> list = sommRepository.findBySingleDose();
		int somma = 0;
		for (SomministrationsEntity somm : list) {
			somma = somma + somm.getTot_dose2();
		}
		return somma;
	}
	
	//Metodo che restituisce il totale della prima dose per una data provincia
	public int getSomministrationsBySingleDoseAndProvince(String abbrevation) {
		List<SomministrationsEntity> list = sommRepository.findBySingleDoseAndProvince(abbrevation);
		int somma = 0;
		for (SomministrationsEntity somm : list) {
			if(somm.getSigla().equalsIgnoreCase(abbrevation)) {
				somma=somma+somm.getTot_dose1();
			}
		}
		return somma;
	}
	
	// Metodo che restituisce il totale della prima dose per una data provincia
	public int getSomministrationsByDoubleDoseAndProvince(String abbrevation) {
		List<SomministrationsEntity> list = sommRepository.findByDoubleDoseAndProvince(abbrevation);
		int somma = 0;
		for (SomministrationsEntity somm : list) {
			if (somm.getSigla().equalsIgnoreCase(abbrevation)) {
				somma = somma + somm.getTot_dose2();
			}
		}
		return somma;
	}
	
		
}
