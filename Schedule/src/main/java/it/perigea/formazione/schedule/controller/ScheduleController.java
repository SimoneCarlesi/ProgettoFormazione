package it.perigea.formazione.schedule.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.perigea.formazione.comune.AbbreviationsDto;
import it.perigea.formazione.comune.ExecutionDto;
import it.perigea.formazione.comune.ProcessDto;
import it.perigea.formazione.comune.SomministrationsDto;
import it.perigea.formazione.schedule.dto.InputParamsDto;
import it.perigea.formazione.schedule.dto.URLDto;
import it.perigea.formazione.schedule.entity.URLEntity;
import it.perigea.formazione.schedule.service.DataAggregatorFeignInterface;
import it.perigea.formazione.schedule.service.DataExtractorFeignInterface;
import it.perigea.formazione.schedule.service.RestScheduler;

@RestController
@RequestMapping("/ScheduleController")
public class ScheduleController {

	@Autowired
	private DataExtractorFeignInterface feignExtractor;

	@Autowired
	private DataAggregatorFeignInterface feignAggregator;

	@Autowired
	private RestScheduler scheduler;

	// chiamate rest relative allo scheduler

	@PostMapping(value = "/startScheduler")
	public String startSchedule(@RequestBody InputParamsDto params) {
		scheduler.startSchedule(params);
		return "la schedule è partita";
	}

	@PostMapping(value = "/stopSchedule")
	public String stopSchedule(@RequestBody String APIMethodName) {
		scheduler.stopSchedule(APIMethodName);
		return "la schedule è stata interrotta";
	}

	@PostMapping(value = "/modifyScheduleTrigger")
	public String modifyScheduleTrigger(@RequestBody String cronTriggerString) {
		scheduler.modifyScheduleTrigger(cronTriggerString);
		return ("il trigger di esecuzione è stato modificato: " + cronTriggerString);
	}

	@GetMapping(value = "/stopScheduler")
	public String stopScheduler() {
		scheduler.stopScheduler();
		return ("Lo scheduler si è arrestato correttamente");
	}

	@PostMapping(value = "/insertAPIMethodURL")
	public String insertAPIMethodURL(@RequestBody URLDto urlDto) {
		URLEntity url = scheduler.insertURL(urlDto);
		return "URL inserito con successo: " + url.getURLString();
	}

	// chiamate rest relative alle interfacce feign

	@GetMapping(value = "/feign/extractor")
	public List<SomministrationsDto> extractor() {
		return feignExtractor.postJsonMessage();
	}

	@GetMapping(value = "/feign/provinceExtractor")
	public List<AbbreviationsDto> provinceExtractor() {
		return feignExtractor.provinceClient();
	}

	@GetMapping(value = "/feign/processExtractor")
	public List<ProcessDto> getProcess() {
		return feignExtractor.getAllProcess();
	}

	@GetMapping(value = "/feign/executionsExtractor")
	public List<ExecutionDto> getExecutions() {
		return feignExtractor.getAllExecutions();
	}

	@GetMapping(value = "/feign/somministrationsBySingleDose")
	public String getSingleDoses() {
		return feignAggregator.getSomministrationsBySingleDose();
	}

	@GetMapping(value = "/feign/somministrationsByDoubleDose")
	public String getDoubleDoses() {
		return feignAggregator.getSomministrationsByDoubleDose();
	}

	@GetMapping(value = "/feign/singleDosesForProvince/{province}")
	public String getSingleDosesForProvince(@RequestParam("province") String province) {
		return feignAggregator.getSomministrationsBySingleDoseForProvince(province);
	}

	@GetMapping(value = "/feign/doubleDosesForProvince/{province}")
	public String getDoubleDosesForProvince(@RequestParam("province") String province) {
		return feignAggregator.getSomministrationsByDoubleDoseForProvince(province);
	}


	// http://localhost:9093/ScheduleController/feign/doubleDosesForProvince/{province}

}