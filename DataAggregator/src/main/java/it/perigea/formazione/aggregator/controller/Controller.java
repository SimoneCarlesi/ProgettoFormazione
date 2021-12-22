package it.perigea.formazione.aggregator.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import it.perigea.formazione.aggregator.entity.SomministrationsEntity;
import it.perigea.formazione.aggregator.kafka.KafkaService;
import it.perigea.formazione.aggregator.mongodb.MongoDB;
import it.perigea.formazione.aggregator.repository.SomministrationsRepository;
import it.perigea.formazione.aggregator.service.SomministrationsServiceImpl;
@RestController
@RequestMapping("/Vaccini")
public class Controller {

	
	@Value("${topicName}")
	private String topicName;
	
	@Autowired
	private KafkaService kafkaService;
	
	@Autowired
	private SomministrationsServiceImpl apiService;
	
	@Autowired
	private SomministrationsRepository sommRepository;
	
	@Autowired
	private MongoDB mongo;
	
	@PostMapping(value = "/consumer")
	@ResponseBody
	public void fromTopicToJava() throws Exception {
		kafkaService.consumeMessages(topicName);
	}
	
	
	@GetMapping(value="/deleteMessages")
	public ResponseEntity<String> deleteDuplicate(@RequestParam String date){
		mongo.dataController(date);
		return ResponseEntity.ok()
				.body("Messaggi cancellati");
	}
	
	
	//Metodo che restituisce il totale della prima dose effettuate in tutta la Lombardia
		@GetMapping(value= "/somministrationsBySingleDose")
		public ResponseEntity<Integer> getSomministrationsBySingleDose() {
			Integer resultRequest = apiService.getSomministrationsBySingleDose();
			return ResponseEntity.ok()
					.body(resultRequest);
		}

		// Metodo che restituisce il totale della seconda dose effettuate in tutta la
		// Lombardia
		@GetMapping(value= "/somministrationsByDoubleDose")
		public ResponseEntity<Integer> getSomministrationsByDoubleDose() {
			Integer resultRequest = apiService.getSomministrationsByDoubleDose();
			return ResponseEntity.ok()
					.body(resultRequest);
		}

		//Metodo che restituisce il totale della prima dose per una data provincia
		@GetMapping(value= "/somministrationsBySingleDoseAndProvince")
		public ResponseEntity<Integer> getSomministrationsBySingleDoseAndProvince(@RequestParam String provinciaDom) {
			Integer resultRequest = apiService.getSomministrationsBySingleDoseAndProvince(provinciaDom);
			return ResponseEntity.ok()
					.body(resultRequest);
		}
		
		@GetMapping(value = "/somministrationsByDoubleDoseForProvince")
		public ResponseEntity<Integer> getSomministrationsByDoubleDoseAndProvince(@RequestParam String provinciaDom) {
			Integer resultRequest = apiService.getSomministrationsByDoubleDoseAndProvince(provinciaDom);
			return ResponseEntity.ok()
					.body(resultRequest);

		}
		

		//Metodo che restituisce la lista ordinata di doppia dose in Lombardia
		@GetMapping(value= "/fromHigherDoseToLowerDoseInLombardia")
		public ResponseEntity<List<SomministrationsEntity>> fromHigherDoseToLowerDoseInLombardia(){
			List<SomministrationsEntity> resultRequest = apiService.fromHigherDoseToLowerDoseInLombardia();
			return ResponseEntity.ok().body(resultRequest);
		}


		// Metodo che restituisce la lista ordinata di doppia dose in una data provincia
		@GetMapping(value= "/fromHigherDoseToLowerDoseInProvince")
		public ResponseEntity<List<SomministrationsEntity>> fromHigherDoseToLowerDoseInProvince(@RequestParam String provinciaDom) {
			List<SomministrationsEntity> resultRequest = apiService.fromHigherDoseToLowerDoseInProvince(provinciaDom);
			return ResponseEntity.ok().body(resultRequest);
		}

		//Trovare il comune con più vaccinati in singola dose
		@GetMapping(value= "/findComuneWithMoreVaccinatedSingleDose")
		public ResponseEntity<SomministrationsEntity> findComuneWithMoreVaccinatedSingleDose(){
			SomministrationsEntity resultRequest = apiService.findComuneWithMoreVaccinatedSingleDose();
			return  ResponseEntity.ok().body(resultRequest);
		}


		// Trovare il comune con più vaccinati in doppia dose
		@GetMapping(value= "/findComuneWithMoreVaccinatedDoubleDose")
		public ResponseEntity<SomministrationsEntity>  findComuneWithMoreVaccinatedDoubleDose() {
			SomministrationsEntity resultRequest = apiService.findComuneWithMoreVaccinatedDoubleDose();
			return ResponseEntity.ok().body(resultRequest);
		}

		//Trovare il comune con meno vaccinati in singola dose
		@GetMapping(value= "/findComuneWithLessVaccinatedSingleDose")
		public ResponseEntity<SomministrationsEntity> findComuneWithLessVaccinatedSingleDose() {
			SomministrationsEntity resultRequest = apiService.findComuneWithLessVaccinatedSingleDose();
			return ResponseEntity.ok()
					.body(resultRequest);
		}

		//Trovare il comune con meno vaccinati in doppia dose
		@GetMapping(value= "/findComuneWithLessVaccinatedDoubleDose")
		public ResponseEntity<SomministrationsEntity> findComuneWithLessVaccinatedDoubleDose() {
			SomministrationsEntity resultRequest = apiService.findComuneWithLessVaccinatedDoubleDose();
			return ResponseEntity.ok()
					.body(resultRequest);
		}

		//Trovare il comune con più vaccinati in singola dose per una data provincia
		@GetMapping(value= "/findComuneWithMoreVaccinatedSingleDoseWithProvince")
		public ResponseEntity<SomministrationsEntity> findComuneWithMoreVaccinatedSingleDoseWithProvince(@RequestParam String provinciaDom){
			SomministrationsEntity resultRequest = apiService.findComuneWithMoreVaccinatedSingleDoseWithProvince(provinciaDom);
			return ResponseEntity.ok()
					.body(resultRequest);
		}
		

		// Trovare il comune con più vaccinati in doppia dose per una data provincia
		@GetMapping(value= "/findComuneWithMoreVaccinatedDoubleDoseWithProvince")
		public ResponseEntity<SomministrationsEntity> findComuneWithMoreVaccinatedDoubleDoseWithProvince(@RequestParam String provinciaDom) {
			SomministrationsEntity resultRequest = apiService.findComuneWithMoreVaccinatedDoubleDoseWithProvince(provinciaDom);
			return ResponseEntity.ok()
					.body(resultRequest);

		}
		
}
