package node.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import node.entity.SomministrationEntity;

@Repository
public interface SomministrationRepository extends JpaRepository<SomministrationEntity,Long> { 
	
	
	
}