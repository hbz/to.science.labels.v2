package de.hbz.nrw.to.science.labels.v2.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.hbz.nrw.to.science.labels.v2.entity.Label;


@Repository
@Transactional
public interface LabelsRepository extends JpaRepository<Label, Integer> {
	
//	@Query("SELECT p FROM Label p WHERE CONCAT(p.id, '') LIKE %?1%"
//			+ " OR p.group LIKE %?1%"
//            + " OR p.labelStr LIKE %?1%"
//            + " OR p.jsonConf.name LIKE %?1%"
//            + " OR p.jsonConf.uri LIKE %?1%"
//            + " OR p.jsonConf.type LIKE %?1%"
//            + " OR p.jsonConf.container LIKE %?1%"
//            + " OR p.comment LIKE %?1%")
//    public Page<Label> searchAllFieldsByKeyword(String keyword, Pageable pageable);
	
	public Label findByGroupAndJsonConfName(String group, String name);
}
