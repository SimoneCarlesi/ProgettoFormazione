package it.perigea.formazione.aggregator.mongodb;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import it.perigea.formazione.aggregator.converter.DataConverter;
import it.perigea.formazione.aggregator.entity.SomministrationsEntity;
import it.perigea.formazione.aggregator.repository.SomministrationsRepository;
import it.perigea.formazione.comune.SomministrationsDto;


@Service
public class MongoDB {

	@Value("${topicName}")
	private String topicName;

	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Autowired
	private SomministrationsRepository sommRepository;

	public void insertListEntityToMongoDB( List<SomministrationsEntity> list) {
		sommRepository.saveAll(list);
	}
	
	public List<SomministrationsEntity> fromListDtoToListEntity(List<SomministrationsDto> listDto){
		List<SomministrationsEntity> listEntity=new ArrayList<>();
		DataConverter data= new DataConverter();
		for(SomministrationsDto sommDto : listDto) {
			SomministrationsEntity entity= new SomministrationsEntity();
			entity.setComune_dom(sommDto.getComune_dom());
			entity.setProvincia_dom(sommDto.getProvincia_dom());
			entity.setSigla(sommDto.getSigla());
			entity.setTot_dose1(sommDto.getTot_dose1());
			entity.setTot_dose2(sommDto.getTot_dose2());
			entity.setData(data.convert(sommDto.getData()));
			listEntity.add(entity);
		}
		return listEntity;
	}
}