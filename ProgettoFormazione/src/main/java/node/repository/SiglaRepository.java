package node.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import node.entity.SiglaEntity;

@Repository
public interface SiglaRepository extends JpaRepository<SiglaEntity,Long> { 
	
	public List<SiglaEntity> findBySigla(String sigla);

}
