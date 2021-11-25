package node.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import node.entity.EsecutionEntity;

@Repository
public interface EsecutionRepository extends JpaRepository<EsecutionEntity,Long> { 
	
	

}
