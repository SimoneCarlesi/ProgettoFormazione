package it.perigea.formazione.extractor.manager;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.perigea.formazione.comune.SomministrationsDto;
import it.perigea.formazione.extractor.controller.SomministrationController;
import it.perigea.formazione.extractor.entity.AbbreviationsEntity;
import it.perigea.formazione.extractor.entity.ExecutionEntity;
import it.perigea.formazione.extractor.entity.ProcessEntity;
import it.perigea.formazione.extractor.model.AbbreviationsDto;
import it.perigea.formazione.extractor.model.ExecutionDto;
import it.perigea.formazione.extractor.model.ProcessDto;
import it.perigea.formazione.extractor.repository.AbbreviationsRepository;
import it.perigea.formazione.extractor.repository.ExecutionRepository;
import it.perigea.formazione.extractor.repository.ProcessRepository;

public class PFManager {

	private static final Logger LOGGER = LoggerFactory.getLogger(SomministrationController.class);

	@Autowired
	public ProcessRepository processRepository;

	@Autowired
	public ExecutionRepository esecutionRepository;

	@Autowired
	public AbbreviationsRepository abbRepository;

	public List<SomministrationsDto> getModifiedDataList(String jsonString, List<AbbreviationsDto> province)
			throws Exception {
		List<SomministrationsDto> listSommDto = new ArrayList<>();
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode jsonNode = objectMapper.readTree(jsonString);
		ZonedDateTime zonedDateTimeNow = ZonedDateTime.now(ZoneId.of("UTC"));
		jsonNode.forEach(data1Row -> {
			int extractedIstatCode = data1Row.get("codistat_comune_dom").asInt();
			String extractedMunicipality = data1Row.get("comune_dom").asText();
			String extractedProvince = data1Row.get("provincia_dom").asText();
			int extractedTotDose1 = data1Row.get("tot_dose1").asInt();
			int extractedTotDose2 = data1Row.get("tot_dose2").asInt();
			SomministrationsDto sommDto = new SomministrationsDto();
			sommDto.setCodistat_comune_dom(extractedIstatCode);
			sommDto.setComune_dom(extractedMunicipality);
			sommDto.setProvincia_dom(extractedProvince);
			sommDto.setTot_dose1(extractedTotDose1);
			sommDto.setTot_dose2(extractedTotDose2);
			for (AbbreviationsDto s : province) {
				if (sommDto.getProvincia_dom().equalsIgnoreCase(s.getNome())) {
					sommDto.setSigla(s.getSigla());
				} else {
				}
			}
			sommDto.setData(zonedDateTimeNow);
			listSommDto.add(sommDto);
		});
		LOGGER.info("List return correctly");
		return listSommDto;
	}

	public List<ExecutionDto> getExecutionDtoListFromDB(List<ExecutionEntity> list) {
		return list.stream().map(entity -> {
			ExecutionDto dto = new ExecutionDto();
			dto.setId(entity.getId());
			dto.setTime(entity.getTime());
			dto.setResult(entity.getResult());
			return dto;
		}).collect(Collectors.toList());
	}

	public List<ProcessDto> getProcessDtoListFromDB(List<ProcessEntity> list) {
		return list.stream().map(entity -> {
			ProcessDto dto = new ProcessDto();
			dto.setUuid(entity.getUuid());
			dto.setDateTime(entity.getDateTime());
			dto.setStatus(entity.getStatus());
			return dto;
		}).collect(Collectors.toList());
	}

	public List<AbbreviationsDto> getAbbreviationsDtoListFromDB(List<AbbreviationsEntity> list) {
		return list.stream().map(entity -> {
			AbbreviationsDto dto = new AbbreviationsDto();
			dto.setCodice(entity.getId());
			dto.setNome(entity.getProvince());
			dto.setRegione(entity.getProvince());
			dto.setSigla(entity.getAbbreviation());
			return dto;
		}).collect(Collectors.toList());
	}

	public List<AbbreviationsEntity> getAbbreviationsListForDB(List<AbbreviationsDto> list) {
		List<AbbreviationsEntity> abbEntityList = new ArrayList<>();
		for (AbbreviationsDto abbDto : list) {
			AbbreviationsEntity abbEntity = new AbbreviationsEntity();
			abbEntity.setId(abbDto.getCodice());
			abbEntity.setName(abbDto.getNome());
			abbEntity.setAbbreviation(abbDto.getSigla());
			abbEntity.setRegion(abbDto.getRegione());
			abbEntityList.add(abbEntity);
		}
		return abbEntityList;
	}
	
	public ProcessEntity getProcessEntity(ProcessDto procDto) {
		ProcessEntity procEntity = new ProcessEntity();
		procEntity.setDateTime(procDto.getDateTime());
		procEntity.setStatus(procDto.getStatus());
		return procEntity;
	}
	
	
	public ExecutionEntity getExecutionEntity(ExecutionDto exeDto) {
		ExecutionEntity exeEntity = new ExecutionEntity();
		exeEntity.setTime(exeDto.getTime());
		exeEntity.setResult(exeDto.getResult());
		return exeEntity;
	}

}