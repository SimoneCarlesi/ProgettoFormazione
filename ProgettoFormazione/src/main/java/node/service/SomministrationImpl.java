package node.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import node.controller.SomministrationController;
import node.entity.EsecutionEntity;
import node.entity.ProcessEntity;
import node.entity.SiglaEntity;
import node.entity.SomministrationEntity;
import node.model.EsecutionDTO;
import node.model.ProcessDTO;
import node.model.SiglaDTO;
import node.model.SomministrationDTO;
import node.repository.EsecutionRepository;
import node.repository.ProcessRepository;
import node.repository.SiglaRepository;
import node.repository.SomministrationRepository;

@Service
public class SomministrationImpl implements ServiceInterface {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SomministrationController.class);

	@Value("${lombardiaJson}")
	private String lombardiaJson;

	@Value("${province}")
	private String province;

	@Autowired
	private SomministrationRepository sommRepository;

	@Autowired
	private ProcessRepository processRepository;

	@Autowired
	private EsecutionRepository esecutionRepository;
	
	@Autowired
	private SiglaRepository siglaRepository;

	@Override
	public List<ProcessDTO> getAllProcess() {

		// JEE STYLE CON HIBERNATE E NAMEDQUERY
		// em.createNamedQuery(Padrone.NAMED_QUERY_ALL, Padrone.class)
		// .getResultList();

		// SPRING REPOSITORY
		List<ProcessEntity> listProcessEntity = processRepository.findAll();

		return listProcessEntity.stream().map(entity -> {
			ProcessDTO dto = new ProcessDTO();
			dto.setUuid(entity.getUuid());
			dto.setDataOra(entity.getDataOra());
			dto.setStatus(entity.getStatus());
			return dto;
		}).collect(Collectors.toList());
	}

	public List<ProcessDTO> getProcessByDate(String data) throws ParseException {
		Date date = new SimpleDateFormat("dd/MM/yyyy").parse(data);
		List<ProcessEntity> lista = processRepository.findByDataOra(date);
		return lista.stream().map(entity -> {
			ProcessDTO dto = new ProcessDTO();
			dto.setUuid(entity.getUuid());
			dto.setDataOra(entity.getDataOra());
			dto.setStatus(entity.getStatus());
			return dto;
		}).collect(Collectors.toList());
	}

	@Override
	public List<EsecutionDTO> getAllEsecutions() {

		// JEE STYLE CON HIBERNATE E NAMEDQUERY
		// em.createNamedQuery(Padrone.NAMED_QUERY_ALL, Padrone.class)
		// .getResultList();

		// SPRING REPOSITORY
		List<EsecutionEntity> listEsecutionEntity = esecutionRepository.findAll();

		return listEsecutionEntity.stream().map(entity -> {
			EsecutionDTO dto = new EsecutionDTO();
			dto.setId(entity.getId());
			dto.setTempo(entity.getTempo());
			dto.setEsito(entity.getEsito());
			return dto;
		}).collect(Collectors.toList());
	}

	public void saveSomministration(SomministrationEntity somm) {
		sommRepository.save(somm);
	}

	public String somministrationClient() throws Exception {
		// come faccio a fare una chiamata Rest dall'interno della mia applicazione
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target(lombardiaJson);
		Response response = target.request(MediaType.APPLICATION_JSON).get();
		String responseString = response.readEntity(String.class);
		System.out.println(responseString);
		return responseString;

	}

	// @ TODO far tornare una lista al posto di una stringa, cambiare il nome del
	// metodo in "provinceClient"
//	public String provinceClient() throws Exception {
//		// come faccio a fare una chiamata Rest dall'interno della mia applicazione
//		ResteasyClient client = new ResteasyClientBuilder().build();
//		ResteasyWebTarget target = client.target(province);
//		Response response = target.request(MediaType.APPLICATION_JSON).get();
//		String responseString = response.readEntity(String.class);
//		System.out.println(responseString);
//		return responseString;
//	}
//	
	public List<SiglaDTO> provinceClient() throws Exception {
		// come faccio a fare una chiamata Rest dall'interno della mia applicazione
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target(province);
		List<SiglaDTO> list = target.request(MediaType.APPLICATION_JSON).get(new GenericType<List<SiglaDTO>>() {});
		System.out.println(list);
		return list;
	}
	
//	public List<SiglaDTO> provinceClient() throws Exception {
//		// come faccio a fare una chiamata Rest dall'interno della mia applicazione
//		ResteasyClient client = new ResteasyClientBuilder().build();
//		ResteasyWebTarget target = client.target(province);
//		Response response = target.request(MediaType.APPLICATION_JSON).get();
//		ObjectMapper mapper = new ObjectMapper();
//		String json = response.readEntity(String.class);
//		List<SiglaDTO> list = Arrays.asList(mapper.readValue(json, SiglaDTO[].class));
//        System.out.println("\nJSON array to List of objects");
//        list.stream().forEach(x -> System.out.println(x));
//		return list;
//	}
	
	public List<SiglaDTO> checkProvince() throws Exception {
		List<SiglaEntity> lista = siglaRepository.findAll();
		if(!(lista.isEmpty())) {
			return lista.stream().map(entity -> {
				SiglaDTO dto = new SiglaDTO();
				dto.setCodice(entity.getCodice());
				dto.setProvincia(entity.getProvincia());
				dto.setRegione(entity.getRegione());
				dto.setSigla(entity.getSigla());
				return dto;
			}).collect(Collectors.toList());
		} else {			
			List<SiglaDTO> siglaDTOList=provinceClient();
			return siglaDTOList;
		}
	}
	
	public List<SomministrationDTO> checkSomministration() throws Exception{
		LOGGER.trace("Entering method checkSomministration");
		String response = somministrationClient();
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode jsonNode = objectMapper.readTree(response);
		final List<SomministrationDTO> listSommDto = new ArrayList<>();
		List<SiglaDTO> province=checkProvince();
		ZonedDateTime zonedDateTimeNow = ZonedDateTime.now(ZoneId.of("UTC"));
		jsonNode.forEach(data1Row -> {
			int codIstatEstratto = data1Row.get("codistat_comune_dom").asInt();
			String comuneEstratto = data1Row.get("comune_dom").asText();
			String provinciaEstratta = data1Row.get("provincia_dom").asText();
			int totDose1Estratto = data1Row.get("tot_dose1").asInt();
			int totDose2Estratto = data1Row.get("tot_dose2").asInt();
			SomministrationDTO sommDto = new SomministrationDTO();
			sommDto.setCodistat_comune_dom(codIstatEstratto);
			sommDto.setComune_dom(comuneEstratto);
			sommDto.setProvincia_dom(provinciaEstratta);
			sommDto.setTot_dose1(totDose1Estratto);
			sommDto.setTot_dose2(totDose2Estratto);
			for (SiglaDTO s : province) {
				if (sommDto.getProvincia_dom().equalsIgnoreCase(s.getNome())) {
					sommDto.setSigla(s.getSigla());
				} else {
					LOGGER.error("Sigla not found");
				}
			}
			sommDto.setData(zonedDateTimeNow);
			listSommDto.add(sommDto);
		});
		
		LOGGER.info("List return correctly");
		return listSommDto;
	}
	
	
	// @ TODO usare una mappa al posto di list, dare nomi alle variabili più
	// coerenti
	@Override
	public List<SomministrationDTO> fromJsonToJava() throws Exception {
		List<SomministrationDTO> somministrations=checkSomministration();
		return somministrations;
	}

	@Override
	public List<SomministrationDTO> selectSomministration() {
		// scarica dati
		List<SomministrationEntity> listEntities = sommRepository.findAll();

		// trasforma da entity a DTO
		List<SomministrationDTO> listSommDTO = listEntities.stream().map(
				// scorro ogni elemento (pairEntity)
				entity -> {
					// convert il pairEntity in DTO
					SomministrationDTO dto = new SomministrationDTO();
					dto.setCodistat_comune_dom(entity.getCodistat_comune_dom());
					dto.setComune_dom(entity.getComune_dom());
					dto.setProvincia_dom(entity.getProvincia_dom());
					dto.setTot_dose1(entity.getTot_dose1());
					dto.setTot_dose2(entity.getTot_dose2());
					return dto;
				}
		// lo trasforma da stream a collection
		).collect(Collectors.toList());

		return listSommDTO;
	}

}
