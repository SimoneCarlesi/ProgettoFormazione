package it.perigea.formazione.aggregator.mongodb;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import it.perigea.formazione.aggregator.converter.DataConverter;
import it.perigea.formazione.aggregator.converter.ObjectIdConverter;
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
		DataConverter data=new DataConverter();
		for(SomministrationsDto sommDto : listDto) {
			SomministrationsEntity entity= new SomministrationsEntity();
			entity.setCodistatComuneDom(sommDto.getCodistatComuneDom());
			entity.setComuneDom(sommDto.getComuneDom());
			entity.setProvinciaDom(sommDto.getProvinciaDom());
			entity.setSigla(sommDto.getSigla());
			entity.setTotDose1(sommDto.getTotDose1());
			entity.setTotDose2(sommDto.getTotDose2());
			entity.setData(data.convert(sommDto.getData()));
			entity.setDate(sommDto.getDate());
			listEntity.add(entity);
		}
		return listEntity;
	}
	
	public void dataController(String date) {
		List<SomministrationsEntity> entityList = sommRepository.findByDate(date);
		if (!(entityList.isEmpty())) {
			sommRepository.deleteAll(entityList);
		}
	}
}