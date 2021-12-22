package it.perigea.formazione.aggregator.repository;

import java.util.Date;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import it.perigea.formazione.aggregator.entity.ClinicalStatusEntity;

@Repository
public interface ClinicalStatusRepository extends MongoRepository<ClinicalStatusEntity, Long> {

	List<ClinicalStatusEntity> findByDate(String date);

	List<ClinicalStatusEntity> findAllByDataInizioSintomiBetween(Date startDate, Date endDate);

	List<ClinicalStatusEntity> findAllByDataInizioSintomiLessThanEqualAndDataInizioSintomiGreaterThanEqual(Date startDate, Date endDate);

}