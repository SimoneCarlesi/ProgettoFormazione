package node.service;

import java.text.ParseException;
import java.util.Scanner;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import node.entity.EsecutionEntity;
import node.entity.ProcessEntity;
import node.entity.SomministrationEntity;
import node.model.EsecutionDTO;
import node.model.ProcessDTO;
import node.model.SiglaDTO;
import node.model.SomministrationDTO;
import node.repository.EsecutionRepository;
import node.repository.ProcessRepository;
import node.repository.SomministrationRepository;

@Service
public class SomministrationImpl implements ServiceInterface{
	
	@Value("${lombardiaJson}")
	private String lombardiaJson;
	
	@Value("${province}")
	private String province;
	
	@Autowired
	public SomministrationRepository sommRepository;
	
	@Autowired
	public ProcessRepository processRepository;
	
	@Autowired
	public EsecutionRepository esecutionRepository;
	
	
	@Override
	public List<ProcessDTO> getAllProcess() {
		
		// JEE STYLE CON HIBERNATE E NAMEDQUERY
		//em.createNamedQuery(Padrone.NAMED_QUERY_ALL, Padrone.class)
		//.getResultList();
		
		// SPRING REPOSITORY
		List<ProcessEntity> listProcessEntity = processRepository.findAll();
		
		return listProcessEntity.stream()
				.map(entity -> {
					ProcessDTO dto= new ProcessDTO();
					dto.setUuid(entity.getUuid());
					dto.setDataOra(entity.getDataOra());
					dto.setStatus(entity.getStatus());
					return dto;
				})
				.collect(Collectors.toList());
	}
	
	public List<ProcessDTO> getProcessByDate(String data) throws ParseException  {
		Date date=new SimpleDateFormat("dd/MM/yyyy").parse(data);
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
		//em.createNamedQuery(Padrone.NAMED_QUERY_ALL, Padrone.class)
		//.getResultList();
		
		// SPRING REPOSITORY
		List<EsecutionEntity> listEsecutionEntity = esecutionRepository.findAll();
		
		return listEsecutionEntity.stream()
				.map(entity -> {
					EsecutionDTO dto= new EsecutionDTO();
					dto.setId(entity.getId());
					dto.setTempo(entity.getTempo());
					dto.setEsito(entity.getEsito());
					return dto;
				})
				.collect(Collectors.toList());
	}
	
	
	
	public void saveSomministration(SomministrationEntity somm) {
		sommRepository.save(somm);
	}

		
	public String externalServiceCall() throws Exception {
		// come faccio a fare una chiamata Rest dall'interno della mia applicazione
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target(lombardiaJson);
		Response response = target.request(MediaType.APPLICATION_JSON).get();
		String responseString = response.readEntity(String.class);
		System.out.println(responseString);
		return responseString;
		
	}
	
	public String externalServiceCallFromAbbrevation() throws Exception {
		// come faccio a fare una chiamata Rest dall'interno della mia applicazione
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target(province);
		Response response = target.request(MediaType.APPLICATION_JSON).get();
		String responseString = response.readEntity(String.class);
		System.out.println(responseString);
		return responseString;
		
	}
	
		//@ TODO basta un solo object mapper, controllare
		@Override
		public List<SomministrationDTO> fromJsonToJava() throws Exception {
			String response2 = externalServiceCallFromAbbrevation();
			ObjectMapper objectMapper2 = new ObjectMapper();
			JsonNode jsonNode2 = objectMapper2.readTree(response2);
			List<SiglaDTO> sigle = new ArrayList<>();
			jsonNode2.forEach(dataRow -> {
				String siglaEstratta = dataRow.get("sigla").asText();
				String nomeEstratto = dataRow.get("nome").asText();
				SiglaDTO siglaDto = new SiglaDTO();
				siglaDto.setProvincia(nomeEstratto);
				siglaDto.setSigla(siglaEstratta);
				sigle.add(siglaDto);
			});
			String response = externalServiceCall();
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode jsonNode = objectMapper.readTree(response);	
			final List<SomministrationDTO> listSommDto = new ArrayList<>();
			//SimpleDateFormat data = new SimpleDateFormat();
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
				for(SiglaDTO s: sigle) {
					if(s.getProvincia().equals(provinciaEstratta)) {
						sommDto.setSigla(s.getSigla());
					}
				}
			//sommDto.setData(data);
			listSommDto.add(sommDto);

			});

			return listSommDto;
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
					SomministrationDTO dto= new SomministrationDTO();
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
