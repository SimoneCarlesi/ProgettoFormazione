package node.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import node.entity.AbbreviationsEntity;

@Repository
public interface AbbreviationsRepository extends JpaRepository<AbbreviationsEntity,Long> { 
	
	public List<AbbreviationsEntity> findByAbbreviation(String abbreviation);

}
