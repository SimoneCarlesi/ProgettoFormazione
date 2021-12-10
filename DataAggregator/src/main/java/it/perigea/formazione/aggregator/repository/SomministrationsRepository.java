package it.perigea.formazione.aggregator.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import it.perigea.formazione.aggregator.entity.SomministrationsEntity;


@Repository
public interface SomministrationsRepository extends MongoRepository<SomministrationsEntity, Long> {
	
	
	public List<SomministrationsEntity> findBydata(Date date);
	
	public List<SomministrationsEntity> findBytotDose1();
	
	public List<SomministrationsEntity> findBytotDose2();
	
	public List<SomministrationsEntity> findByDate(String date);
	
	public List<SomministrationsEntity> findByData(Date data);
	
	public List<SomministrationsEntity> findByprovinciaDom(String province);
	
	
}
