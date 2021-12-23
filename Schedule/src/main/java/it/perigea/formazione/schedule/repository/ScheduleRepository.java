package it.perigea.formazione.schedule.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.perigea.formazione.schedule.entity.ScheduleEntity;




public interface ScheduleRepository extends JpaRepository<ScheduleEntity, Integer> {
	
}
