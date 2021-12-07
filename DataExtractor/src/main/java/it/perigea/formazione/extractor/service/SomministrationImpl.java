package it.perigea.formazione.extractor.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import it.perigea.formazione.comune.SomministrationsDto;
import it.perigea.formazione.extractor.controller.SomministrationController;
import it.perigea.formazione.extractor.entity.AbbreviationsEntity;
import it.perigea.formazione.extractor.entity.ExecutionEntity;
import it.perigea.formazione.extractor.entity.ProcessEntity;
import it.perigea.formazione.extractor.manager.PFManager;
import it.perigea.formazione.extractor.model.AbbreviationsDto;
import it.perigea.formazione.extractor.model.ExecutionDto;
import it.perigea.formazione.extractor.model.ProcessDto;
import it.perigea.formazione.extractor.repository.AbbreviationsRepository;
import it.perigea.formazione.extractor.repository.ExecutionRepository;
import it.perigea.formazione.extractor.repository.ProcessRepository;

@Service
public class SomministrationImpl implements ServiceInterface {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SomministrationController.class);

	@Value("${lombardiaJson}")
	private String lombardiaJson;

	@Value("${province}")
	private String province;

	@Autowired
	public ProcessRepository processRepository;

	@Autowired
	public ExecutionRepository esecutionRepository;

	@Autowired
	public AbbreviationsRepository abbRepository;

	public PFManager manager = new PFManager();
	
	//scarica i dati sulle somministrazioni Lombardia
	@Override
	public String dataClient() throws Exception {
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target(lombardiaJson);
		Response response = target.request(MediaType.APPLICATION_JSON).get();
		String jsonString = response.readEntity(String.class);
		System.out.println(jsonString);
		return jsonString;
	}
	
	//controlla se il Json delle province è gia stato scaricato e se è gia presente
	//nel database locale
	@Override
	public List<AbbreviationsDto> checkProvince() throws Exception {
		List<AbbreviationsEntity> list = abbRepository.findAll();
		if (!(list.isEmpty())) {
			return manager.getAbbreviationsDtoListFromDB(list);
		} else {
			List<AbbreviationsDto> abbDTOList = provinceClient();
			return abbDTOList;
		}
	}
	
	//metodo per ottenere il dato Json manipolato
	@Override
	public List<SomministrationsDto> checkSomministration() throws Exception {
		LOGGER.trace("Entering method checkSomministration");
		String jsonString = dataClient();
		List<AbbreviationsDto> province = checkProvince();
		return manager.getModifiedDataList(jsonString, province);
	}
	
	//conversione dei dati Json in java
	@Override
	public List<SomministrationsDto> fromJsonToJava() throws Exception {
		List<SomministrationsDto> somministrations = checkSomministration();
		return somministrations;
	}
	
	//scarica i dati Json sulle province 
	@Override
	public List<AbbreviationsDto> provinceClient() throws Exception {
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target(province);
		List<AbbreviationsDto> list = target.request(MediaType.APPLICATION_JSON)
				.get(new GenericType<List<AbbreviationsDto>>() {
				});
		System.out.println(list);
		List<AbbreviationsEntity> abbEntityList = saveAbbreviationsListInDB(list);
		return list;
	}
	
	//Mostra tutte le esecuzioni presenti su DB locale
	@Override
	public List<ExecutionDto> getAllEsecutions() {
		List<ExecutionEntity> listEsecutionEntity = esecutionRepository.findAll();
		return manager.getExecutionDtoListFromDB(listEsecutionEntity);
	}
	
	//Mostra tutti i processi sulla base di una data passata per parametro
	@Override
	public List<ProcessDto> getProcessByDate(String data) throws ParseException {
		Date date = new SimpleDateFormat("dd/MM/yyyy").parse(data);
		List<ProcessEntity> list = processRepository.findByDateTime(date);
		return manager.getProcessDtoListFromDB(list);
	}
	
	//mostra tutti i processi presenti su DB locale
	@Override
	public List<ProcessDto> getAllProcess() {
		List<ProcessEntity> listProcessEntity = processRepository.findAll();
		return manager.getProcessDtoListFromDB(listProcessEntity);
	}
	
	//Riempie processi ed esecuzioni con le informazioni necessarie
	@Override
	public List<SomministrationsDto> runProcess() throws Exception {
		ProcessDto processDto = new ProcessDto();
		ExecutionDto exeDto = new ExecutionDto();
		Date date = new Date();
		processDto.setDateTime(date);
		long startTime = System.currentTimeMillis();
		List<SomministrationsDto> resultRequest = fromJsonToJava();
		long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime;
		exeDto.setTime(elapsedTime);
		exeDto.setResult("Success");
		processDto.setStatus("Ended");
		saveProcessDataInDB(processDto);
		saveExecutionDataInDB(exeDto);
		System.out.println("Status ending");
		return resultRequest;
	}
	
	//salva le esecuzioni su DB locale
	public ExecutionEntity saveExecutionDataInDB(ExecutionDto exeDto) {
		ExecutionEntity exeEntity = manager.getExecutionEntity(exeDto);
		esecutionRepository.save(exeEntity);
		return exeEntity;
	}
	
	//salva i processi su DB locale
	public ProcessEntity saveProcessDataInDB(ProcessDto procDto) {
		ProcessEntity procEntity = manager.getProcessEntity(procDto);
		processRepository.save(procEntity);
		return procEntity;
	}
	
	//salve le province su DB locale
	public List<AbbreviationsEntity> saveAbbreviationsListInDB(List<AbbreviationsDto> list) {
		List<AbbreviationsEntity> abbEntityList = manager.getAbbreviationsListForDB(list);
		 abbRepository.saveAll(abbEntityList);
		 return abbEntityList;
	}
}
