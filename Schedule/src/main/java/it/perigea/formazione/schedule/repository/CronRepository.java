package it.perigea.formazione.schedule.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.perigea.formazione.schedule.entity.CronEntity;


@Repository
public interface CronRepository  extends JpaRepository<CronEntity,Integer>{
	
	public CronEntity findByIdCron(Integer idCron);
}
