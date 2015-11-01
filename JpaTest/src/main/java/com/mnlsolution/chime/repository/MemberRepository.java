package com.mnlsolution.chime.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mnlsolution.chime.domain.Member;
import com.mnlsolution.chime.domain.Team;

public interface MemberRepository  extends JpaRepository<Member, Integer> {

//	List<Member> findByName(String name);
	
	@Query("select m from Member m where m.name= :name order by m.name desc") 	
	List<Member> findByName(@Param("name") String name);  

	
// native qyuery 사용예시.. 	
//	@Query("select m.name, m. from Member m order by m.name desc")
	@Query(value="SELECT * FROM MEMBER WHERE NAME LIKE %?1%", nativeQuery = true)
	List<Member> findByCustomQueryName(String name);  

	
}
