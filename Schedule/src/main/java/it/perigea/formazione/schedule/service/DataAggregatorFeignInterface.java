package it.perigea.formazione.schedule.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;



@FeignClient(name="data-aggregator-service",url="http://localhost:9095")
public interface DataAggregatorFeignInterface {
	
	@RequestMapping("/dataAggregator/somministrationsBySingleDose")
	public String getSomministrationsBySingleDose();
	
	@RequestMapping("/dataAggregator/somministrationsByDoubleDose")
	public String  getSomministrationsByDoubleDose();
	
	@RequestMapping("/dataAggregator/somministrationsBySingleDoseForProvince")
	public String getSomministrationsBySingleDoseForProvince(@RequestParam String province);
	
	@RequestMapping("/dataAggregator/somministrationsByDoubleDoseForProvince")
	public String getSomministrationsByDoubleDoseForProvince(@RequestParam String province);
	

}