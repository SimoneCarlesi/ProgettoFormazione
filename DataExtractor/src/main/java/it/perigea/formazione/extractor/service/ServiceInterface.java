package it.perigea.formazione.extractor.service;

import java.text.ParseException;
import java.util.List;

import it.perigea.formazione.comune.SomministrationsDto;
import it.perigea.formazione.extractor.entity.ExecutionEntity;
import it.perigea.formazione.extractor.entity.ProcessEntity;
import it.perigea.formazione.extractor.model.AbbreviationsDto;
import it.perigea.formazione.extractor.model.ExecutionDto;
import it.perigea.formazione.extractor.model.ProcessDto;


public interface ServiceInterface {
	
	public List<ProcessDto> getAllProcess();
	
	public List<ProcessDto> getProcessByDate(String date) throws ParseException;
	
	public List<ExecutionDto> getAllEsecutions();

	public String dataClient() throws Exception;
	
	public List<AbbreviationsDto> provinceClient() throws Exception;

	public List<SomministrationsDto> fromJsonToJava() throws Exception;
	
	public List<SomministrationsDto> checkSomministration() throws Exception;
	
	public List<AbbreviationsDto> checkProvince() throws Exception;
	
	public ProcessEntity saveProcessDataInDB(ProcessDto procDto);
	
	public ExecutionEntity saveExecutionDataInDB(ExecutionDto exeDto);
	
	public List<SomministrationsDto> runProcess() throws Exception;
	
}
