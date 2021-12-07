package it.perigea.formazione.comune.dto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import it.perigea.formazione.comune.dto.SomministrationsEntity;

@Repository
public interface SomministrationsRepository extends JpaRepository<SomministrationsEntity,Long> { 
	

}
