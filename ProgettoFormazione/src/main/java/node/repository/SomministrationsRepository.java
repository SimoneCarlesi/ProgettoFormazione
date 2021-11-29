package node.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import node.entity.SomministrationsEntity;

@Repository
public interface SomministrationsRepository extends JpaRepository<SomministrationsEntity,Long> { 
	
	
	
}