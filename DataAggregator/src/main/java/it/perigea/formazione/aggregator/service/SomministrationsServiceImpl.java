package it.perigea.formazione.aggregator.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import it.perigea.formazione.aggregator.entity.SomministrationsEntity;
import it.perigea.formazione.aggregator.kafka.KafkaService;
import it.perigea.formazione.aggregator.mongodb.MongoDB;
import it.perigea.formazione.aggregator.repository.SomministrationsRepository;
import it.perigea.formazione.comune.SomministrationsDto;



@Service
public class SomministrationsServiceImpl implements SomministrationsService{
	
	@Autowired
	private SomministrationsRepository sommRepository;
	
	@Autowired 
	private SomministrationsService sommService;
	
	
	@Autowired
	private MongoDB mongo;
	
	@Value("${topicName}")
	private String topicName;
	
	@Autowired
	private KafkaService kafkaService;
	
	
	
	//@ TODO "aggregation" per fare filtri, sommatorie. Controllare
	
	//Metodo che restituisce il totale della prima dose effettuate in tutta la Lombardia
	@Override
	public int getSomministrationsBySingleDose() {
		List<SomministrationsEntity> list = sommRepository.findBytotDose1();
		int somma=0;
		for(SomministrationsEntity somm: list) {
			somma=somma+somm.getTotDose1();
		}
		return somma;
	}
	
	// Metodo che restituisce il totale della prima dose effettuate in tutta la
	// Lombardia
	@Override
	public int getSomministrationsByDoubleDose() {
		List<SomministrationsEntity> list = sommRepository.findBytotDose2();
		int somma = 0;
		for (SomministrationsEntity somm : list) {
			somma = somma + somm.getTotDose2();
		}
		return somma;
	}
	
	//Metodo che restituisce il totale della prima dose per una data provincia
	@Override
	public int getSomministrationsBySingleDoseAndProvince(String province) {
		List<SomministrationsEntity> list = sommRepository.findByprovinciaDom(province.toUpperCase());
		int somma = 0;
		for (SomministrationsEntity somm : list) {
			if(somm.getProvinciaDom().equalsIgnoreCase(province)) {
				somma=somma+somm.getTotDose1();
			}
		}
		return somma;
	}
	
	
	// Metodo che restituisce il totale della seconda dose per una data provincia
	@Override
	public int getSomministrationsByDoubleDoseAndProvince(String province) {
		List<SomministrationsEntity> list = sommRepository.findByprovinciaDom(province.toUpperCase());
		int somma = 0;
		for (SomministrationsEntity somm : list) {
			if (somm.getProvinciaDom().equalsIgnoreCase(province)) {
				somma = somma + somm.getTotDose2();
			}
		}
		return somma;
	}
	
	//Metodo che restituisce la lista ordinata di doppia dose in Lombardia
	@Override
	public List<SomministrationsEntity> fromHigherDoseToLowerDoseInLombardia(){
		List<SomministrationsEntity> list = sommRepository.findAll(Sort.by(Sort.Direction.DESC, "totDose2"));
		return list;
	}
	
	
	// Metodo che restituisce la lista ordinata di doppia dose in una data provincia
	@Override
	public List<SomministrationsEntity> fromHigherDoseToLowerDoseInProvince(String province) {
		List<SomministrationsEntity> list = sommRepository.findByprovinciaDom(province.toUpperCase());
		list.sort(Comparator.comparing(SomministrationsEntity::getTotDose2).reversed());
		return list;
	}
	
	//Trovare il comune con più vaccinati in singola dose
	@Override
	public SomministrationsEntity findComuneWithMoreVaccinatedSingleDose(){
		List<SomministrationsEntity> list = sommRepository.findAll(Sort.by(Sort.Direction.DESC, "totDose1"));
		return list.get(0);
	}
	
	
	// Trovare il comune con più vaccinati in doppia dose
	@Override
	public SomministrationsEntity findComuneWithMoreVaccinatedDoubleDose() {
		List<SomministrationsEntity> list = sommRepository.findAll(Sort.by(Sort.Direction.DESC, "totDose2"));
		return list.get(0);
	}
	
	//Trovare il comune con meno vaccinati in singola dose
	@Override
	public SomministrationsEntity findComuneWithLessVaccinatedSingleDose() {
		List<SomministrationsEntity> list = sommRepository.findAll(Sort.by(Sort.Direction.ASC, "totDose1"));
		return list.get(0);
	}
	
	//Trovare il comune con meno vaccinati in doppia dose
	@Override
	public SomministrationsEntity findComuneWithLessVaccinatedDoubleDose() {
		List<SomministrationsEntity> list = sommRepository.findAll(Sort.by(Sort.Direction.ASC, "totDose2"));
		return list.get(0);
	}
	
	//Trovare il comune con più vaccinati in singola dose per una data provincia
	@Override
	public SomministrationsEntity findComuneWithMoreVaccinatedSingleDoseWithProvince(String province){
		List<SomministrationsEntity> list = sommRepository.findByprovinciaDom(province.toUpperCase());
		list.sort(Comparator.comparing(SomministrationsEntity::getTotDose1).reversed());
		return list.get(0);
	}
	
	// Trovare il comune con più vaccinati in doppia dose per una data provincia
	@Override
	public SomministrationsEntity findComuneWithMoreVaccinatedDoubleDoseWithProvince(String province) {
		List<SomministrationsEntity> list = sommRepository.findByprovinciaDom(province.toUpperCase());
		list.sort(Comparator.comparing(SomministrationsEntity::getTotDose2).reversed());
		return list.get(0);
	}
	
	
}
