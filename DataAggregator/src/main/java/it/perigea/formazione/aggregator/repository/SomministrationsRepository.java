package it.perigea.formazione.aggregator.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import it.perigea.formazione.aggregator.entity.SomministrationsEntity;


@Repository
public interface SomministrationsRepository extends MongoRepository<SomministrationsEntity, String> {
	
	public List<SomministrationsEntity> findBySingleDose();
	
	public List<SomministrationsEntity> findByDoubleDose();
	
	public List<SomministrationsEntity> findBySingleDoseAndProvince(String abbrevation);
	
	public List<SomministrationsEntity> findByDoubleDoseAndProvince(String abbrevation);
	
	public List<SomministrationsEntity> fromHigherDoseToLowerDose();
}
