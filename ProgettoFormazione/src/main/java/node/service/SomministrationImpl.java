package node.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

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

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import node.controller.SomministrationController;
import node.entity.ExecutionEntity;
import node.entity.ProcessEntity;
import node.entity.AbbreviationsEntity;
import node.entity.SomministrationsEntity;
import node.manager.PFManager;
import node.model.ExecutionDto;
import node.model.ProcessDto;
import node.model.AbbreviationsDto;
import node.model.SomministrationsDto;
import node.repository.ExecutionRepository;
import node.repository.ProcessRepository;
import node.repository.AbbreviationsRepository;
import node.repository.SomministrationsRepository;

@Service
public class SomministrationImpl implements ServiceInterface {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SomministrationController.class);

	@Value("${lombardiaJson}")
	private String lombardiaJson;

	@Value("${province}")
	private String province;

	@Autowired
	public SomministrationsRepository sommRepository;

	@Autowired
	public ProcessRepository processRepository;

	@Autowired
	public ExecutionRepository esecutionRepository;

	@Autowired
	public AbbreviationsRepository abbRepository;

	public PFManager manager = new PFManager();

	@Override
	public String dataClient() throws Exception {
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target(lombardiaJson);
		Response response = target.request(MediaType.APPLICATION_JSON).get();
		String jsonString = response.readEntity(String.class);
		System.out.println(jsonString);
		return jsonString;
	}

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

	@Override
	public List<SomministrationsDto> checkSomministration() throws Exception {
		LOGGER.trace("Entering method checkSomministration");
		String jsonString = dataClient();
		List<AbbreviationsDto> province = checkProvince();
		return manager.getModifiedDataList(jsonString, province);
	}

	@Override
	public List<SomministrationsDto> fromJsonToJava() throws Exception {
		List<SomministrationsDto> somministrations = checkSomministration();
		return somministrations;
	}

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

	@Override
	public List<ExecutionDto> getAllEsecutions() {
		List<ExecutionEntity> listEsecutionEntity = esecutionRepository.findAll();
		return manager.getExecutionDtoListFromDB(listEsecutionEntity);
	}

	@Override
	public List<ProcessDto> getProcessByDate(String data) throws ParseException {
		Date date = new SimpleDateFormat("dd/MM/yyyy").parse(data);
		List<ProcessEntity> list = processRepository.findByDateTime(date);
		return manager.getProcessDtoListFromDB(list);
	}

	@Override
	public List<ProcessDto> getAllProcess() {
		List<ProcessEntity> listProcessEntity = processRepository.findAll();
		return manager.getProcessDtoListFromDB(listProcessEntity);
	}

	@Override
	public List<SomministrationsDto> runProcess() throws Exception {
		ProcessDto processDto = new ProcessDto();
		ExecutionDto exeDto = new ExecutionDto();
		Random i = new Random();
		int uuid = i.nextInt();
		processDto.setUuid(uuid);
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
	
	public ExecutionEntity saveExecutionDataInDB(ExecutionDto exeDto) {
		ExecutionEntity exeEntity = manager.getExecutionEntity(exeDto);
		esecutionRepository.save(exeEntity);
		return exeEntity;
	}

	public ProcessEntity saveProcessDataInDB(ProcessDto procDto) {
		ProcessEntity procEntity = manager.getProcessEntity(procDto);
		processRepository.save(procEntity);
		return procEntity;
	}
	
	public List<AbbreviationsEntity> saveAbbreviationsListInDB(List<AbbreviationsDto> list) {
		List<AbbreviationsEntity> abbEntityList = manager.getAbbreviationsListForDB(list);
		 abbRepository.saveAll(abbEntityList);
		 return abbEntityList;
	}
}
