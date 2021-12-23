package it.perigea.formazione.schedule.service;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

import it.perigea.formazione.comune.AbbreviationsDto;
import it.perigea.formazione.comune.ExecutionDto;
import it.perigea.formazione.comune.ProcessDto;
import it.perigea.formazione.comune.SomministrationsDto;



@FeignClient(name = "data-extractor-service", url = "http://localhost:9091")
public interface DataExtractorFeignInterface {

	@RequestMapping("/Vaccini/postSomm")
	public List<SomministrationsDto> postJsonMessage();

	@RequestMapping("/Vaccini/provinceExtractor")
	public List<AbbreviationsDto> provinceClient();

	@RequestMapping("/Vaccini/getListProcess")
	public List<ProcessDto> getAllProcess();

	@RequestMapping("/Vaccini/getListExecutions")
	public List<ExecutionDto> getAllExecutions();

}