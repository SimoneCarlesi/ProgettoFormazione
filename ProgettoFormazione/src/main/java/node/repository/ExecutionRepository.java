package node.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import node.entity.ExecutionEntity;

@Repository
public interface ExecutionRepository extends JpaRepository<ExecutionEntity,Long> { 
	
	

}
