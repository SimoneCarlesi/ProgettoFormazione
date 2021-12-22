package it.perigea.formazione.aggregator.mongodb;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import it.perigea.formazione.aggregator.converter.DataConverter;
import it.perigea.formazione.aggregator.converter.ObjectIdConverter;
import it.perigea.formazione.aggregator.entity.ClinicalStatusEntity;
import it.perigea.formazione.aggregator.entity.SomministrationsEntity;
import it.perigea.formazione.aggregator.repository.ClinicalStatusRepository;
import it.perigea.formazione.aggregator.repository.SomministrationsRepository;
import it.perigea.formazione.comune.ClinicalStatusDto;
import it.perigea.formazione.comune.SomministrationsDto;


@Service
public class MongoDB {

	@Value("${topicName}")
	private String topicName;

	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Autowired
	private SomministrationsRepository sommRepository;
	
	@Autowired
	private ClinicalStatusRepository clinicalRepository;

	public void insertListEntityToMongoDB( List<SomministrationsEntity> list) {
		sommRepository.saveAll(list);
	}
	
	public List<SomministrationsEntity> fromListDtoToListEntity(List<SomministrationsDto> listDto){
		List<SomministrationsEntity> listEntity=new ArrayList<>();
		DataConverter data=new DataConverter();
		for(SomministrationsDto sommDto : listDto) {
			SomministrationsEntity entity= new SomministrationsEntity();
			entity.setCodistatComuneDom(sommDto.getCodistatComuneDom());
			entity.setComuneDom(sommDto.getComuneDom());
			entity.setProvinciaDom(sommDto.getProvinciaDom());
			entity.setSigla(sommDto.getSigla());
			entity.setTotDose1(sommDto.getTotDose1());
			entity.setTotDose2(sommDto.getTotDose2());
			entity.setData(data.convert(sommDto.getData()));
			entity.setDate(sommDto.getDate());
			listEntity.add(entity);
		}
		return listEntity;
	}
	
//	metodo per l'inserimento dei dati relativi agli stati clinici a DB
	public void insertClinicalListEntityToMongoDB( List<ClinicalStatusEntity> list) {
		clinicalRepository.saveAll(list);
	}
	
//	metodo per la conversione dei Dto in Entity stati clinici
	public List<ClinicalStatusEntity> fromClinicalListDtoToClinicalListEntity(List<ClinicalStatusDto> listDto){
		List<ClinicalStatusEntity> listEntity=new ArrayList<>();
		DataConverter data=new DataConverter();
		for(ClinicalStatusDto clinicalDto : listDto) {
			ClinicalStatusEntity entity= new ClinicalStatusEntity();
			//			entity.setDataInizioSintomi(clinicalDto.getDataInizioSintomi());
			entity.setDataInizioSintomi(clinicalDto.getDataInizioSintomi());
			entity.setCasiTotali(clinicalDto.getCasiTotali());
			entity.setNessunoStatoClinico(clinicalDto.getNessunoStatoClinico());
			entity.setGuariti(clinicalDto.getGuariti());
			entity.setDeceduti(clinicalDto.getDeceduti());
			entity.setAsintomatici(clinicalDto.getAsintomatici());
			entity.setConSintomi((clinicalDto.getConSintomi()));
			entity.setConSintomiGuariti(clinicalDto.getConSintomiGuariti());
			entity.setConSintomiDecessi(clinicalDto.getConSintomiDecessi());
			entity.setAsintomaticiGuariti(clinicalDto.getAsintomaticiGuariti());
			entity.setAsintomaticiDeceduti(clinicalDto.getAsintomaticiDeceduti());
			entity.setData(data.convert(clinicalDto.getData()));
			entity.setDate(clinicalDto.getDate());
			listEntity.add(entity);
		}
		return listEntity;
	}
	
	public void dataController(String date) {
		List<SomministrationsEntity> entityList = sommRepository.findByDate(date);
		if (!(entityList.isEmpty())) {
			sommRepository.deleteAll(entityList);
		}
	}
	
//	metodo per la cancellazione dei dati relativi agli stati clinici in base alla data di input
	public void deleteClinicalData(String date) {
		List<ClinicalStatusEntity> entityList = clinicalRepository.findByDate(date);
		if (!(entityList.isEmpty())) {
			clinicalRepository.deleteAll(entityList);
		}
	}
}