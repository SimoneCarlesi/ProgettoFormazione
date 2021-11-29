package node.service;

import java.text.ParseException;
import java.util.List;

import node.entity.ExecutionEntity;
import node.entity.ProcessEntity;
import node.entity.AbbreviationsEntity;
import node.model.ExecutionDto;
import node.model.ProcessDto;
import node.model.AbbreviationsDto;
import node.model.SomministrationsDto;

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
