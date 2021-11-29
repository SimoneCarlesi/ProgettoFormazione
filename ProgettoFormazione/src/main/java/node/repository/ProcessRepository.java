package node.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import node.entity.ProcessEntity;

@Repository
public interface ProcessRepository extends JpaRepository<ProcessEntity,Long> { 
	
	public List<ProcessEntity> findByDateTime(Date date);

}
