package node.controller;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import node.kafka.KafkaService;
import node.model.EsecutionDTO;
import node.model.ProcessDTO;
import node.model.SiglaDTO;
//import node.kafka.KafkaService;
import node.model.SomministrationDTO;
import node.service.ServiceInterface;

@RestController
@RequestMapping("/Vaccini")
public class SomministrationController {

	private static final Logger LOGGER = LoggerFactory.getLogger(SomministrationController.class);
	
	@Value("${spring.application.name}")
	private String appName;
	
	@Value("${topicName}")
	private String topicName;

	@Autowired
	private ServiceInterface sommService;
	
	@Autowired
	private KafkaService kafkaService;
	
	@PostMapping(value="/postSomm")
	@ResponseBody
	public ResponseEntity<List<SomministrationDTO>> postJsonMessage() throws Exception {
		List<SomministrationDTO> listSommDto=sommService.fromJsonToJava();
		kafkaService.sendMessage(topicName,listSommDto);
		return ResponseEntity.ok().body(listSommDto);
	}
	
	
	// JEE STYLE CON EJB
	// creare il metodo per ottenere TUTTI i gatti
	//@GET
	//@Produces(MediaType.APPLICATION_JSON) //formato di dato
	//@Path("allGatti") //variabile {}

	// SPRING
	/**
	 * Get list of roles
	 * @return list of Ruoli existing on DB
	 */
	@GetMapping("/getListProcess")
	public ResponseEntity<List<ProcessDTO>> getAllProcess() {
		//log.info("getListRuoli - START");
		List<ProcessDTO> response =  sommService.getAllProcess();
		//log.info("getListRuoli - response of count : {}", response.size());


		/*return Optional
				.ofNullable(response)
				.map( list -> ResponseEntity.ok().body(list))  
				.orElseGet( () -> ResponseEntity.notFound().build() );
		 */
		if(!response.isEmpty()) {
			return ResponseEntity.ok().body(response);
		} else {
			return ResponseEntity.notFound().build(); 
		}
	}
	
	@GetMapping("/getProcessByDate")
	@ResponseBody
	public ResponseEntity<List<ProcessDTO>> getProcessByDate(@RequestParam String date) throws ParseException  {
		List<ProcessDTO> resultRequest = sommService.getProcessByDate(date);
		return ResponseEntity.ok().body(resultRequest);
	}
	
	@GetMapping("/getListEsecutions")
	public ResponseEntity<List<EsecutionDTO>> getAllEsecutions() {
		//log.info("getListRuoli - START");
		List<EsecutionDTO> response =  sommService.getAllEsecutions();
		//log.info("getListRuoli - response of count : {}", response.size());


		/*return Optional
				.ofNullable(response)
				.map( list -> ResponseEntity.ok().body(list))  
				.orElseGet( () -> ResponseEntity.notFound().build() );
		 */
		if(!response.isEmpty()) {
			return ResponseEntity.ok().body(response);
		} else {
			return ResponseEntity.notFound().build(); 
		}
	}
	
	
	// fai una get di un servizio esterno
	@GetMapping("/somministrationClient")
	public ResponseEntity<String> externalServiceCall() {
		//???
		// eseguire una get di un servizio esterno, scaricare i dati e salvarli nel mio DB

		try {
			String resultRequest = sommService.somministrationClient();
			return ResponseEntity.ok().body(resultRequest);
		} catch (Exception exc) {
			return ResponseEntity.internalServerError().body(exc.getMessage());
		}
	}
	
	// fai una get di un servizio esterno di un altro json per la sigla
	//@ TODO controllare exceptionHandler
		@GetMapping("/provinceClient")
		public ResponseEntity externalServiceCallFromAbbrevation() {
			//???
			// eseguire una get di un servizio esterno, scaricare i dati e salvarli nel mio DB
			try {
				List<SiglaDTO> resultRequest = sommService.provinceClient();
				return ResponseEntity.ok().body(resultRequest);
			} catch (Exception exc) {//@ TODO generic response
				return ResponseEntity.internalServerError().body(exc.getMessage());
			}
		}

	// fai una get di un servizio esterno E trasformalo in java
	@GetMapping("/fromJsonToJava")
	public ResponseEntity<String> fromJsonToJava() {
		Date data= new Date();
		//???
		// eseguire una get di un servizio esterno, scaricare i dati e salvarli nel mio DB
		try {
			long startTime = System.currentTimeMillis();
			List<SomministrationDTO> resultRequest = sommService.fromJsonToJava();
			long stopTime = System.currentTimeMillis();
			long elapsedTime = stopTime - startTime;
			System.out.println("Status ending");
			return ResponseEntity.ok().body("Status success. " + "Data e ora del processo: " + data +  "; Tempo del processo:" + elapsedTime);
		} catch (Exception exc) {
			return ResponseEntity.ok().body("Status failure.");
		}
	}
	
}
