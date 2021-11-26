package node.service;

import java.text.ParseException;
import java.util.List;

import node.model.EsecutionDTO;
import node.model.ProcessDTO;
import node.model.SiglaDTO;
import node.model.SomministrationDTO;

public interface ServiceInterface {
	
	public List<ProcessDTO> getAllProcess();
	
	public List<ProcessDTO> getProcessByDate(String date) throws ParseException;
	
	public List<EsecutionDTO> getAllEsecutions();

	public String somministrationClient() throws Exception;
	
	public List<SiglaDTO> provinceClient() throws Exception;

	public List<SomministrationDTO> fromJsonToJava() throws Exception;
	
	public List<SomministrationDTO> selectSomministration(); 
	
	public List<SiglaDTO> checkProvince() throws Exception;
	
	//public List<SomministrationDTO> findBySigla(String sigla);
}
