package it.perigea.formazione.aggregator.controller;

import java.text.ParseException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import it.perigea.formazione.aggregator.entity.ClinicalStatusEntity;
import it.perigea.formazione.aggregator.kafka.KafkaService;
import it.perigea.formazione.aggregator.mongodb.MongoDB;
//import it.perigea.formazione.aggregator.repository.ClinicalStatusRepository;
import it.perigea.formazione.aggregator.service.ClinicalStatusImpl;

@RestController
@RequestMapping("/StatiClinici")
public class ClinicalStatusController {

	@Value("${ClinicalTopicName}")
	private String ClinicalTopicName;

	@Autowired
	private KafkaService kafkaService;

	@Autowired
	private ClinicalStatusImpl clinicalService;

	@Autowired
	private MongoDB mongo;


	//	chiamata REST per il consumer relativo agli stati clinici
	@PostMapping(value = "/clinicalConsumer")
	@ResponseBody
	public void fromTopicToJava() throws Exception {
		ZonedDateTime zonedDateTimeNow = ZonedDateTime.now(ZoneId.of("UTC"));
		String dataString = (zonedDateTimeNow.getDayOfMonth() + "/" + zonedDateTimeNow.getMonthValue() + "/"
				+ zonedDateTimeNow.getYear());
		mongo.deleteClinicalData(dataString);
		kafkaService.consumeClinicalMessages(ClinicalTopicName);
	}

	//	chiamata REST per eliminare i duplicati
	@GetMapping(value="/deleteClinicalMessages")
	public ResponseEntity<String> deleteDuplicate(@RequestParam String date){
		mongo.deleteClinicalData(date);
		return ResponseEntity.ok()
				.body("Messaggi cancellati");
	}

	//	chiamata REST che mi restituisce il conteggio degli stati per un dato intervallo di date
	@GetMapping(value="/CountForEachStateInADateRange")
	public ResponseEntity <ClinicalStatusEntity> CountForEachStateInADateRange(@RequestParam String dataInizio, @RequestParam String dataFine) throws ParseException{
		ClinicalStatusEntity resultRequest = clinicalService.CountForEachStateInADateRange(dataInizio, dataFine);
		return ResponseEntity.ok().body(resultRequest);
	}
}
