package com.mnlsolution.chime.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mnlsolution.chime.member.Member;

public interface MemberRepository  extends JpaRepository<Member, Integer> {

	List<Member> findByName(String name);

	
/*	@Query("select m from member m where m.name= :name") 	
	List<Member> findByNameSrc(@Param("name") String name, Pageable pageable);  
*/

}
