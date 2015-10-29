package com.mnlsolution.chime.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mnlsolution.chime.member.Member;

public interface MemberRepository  extends JpaRepository<Member, Integer> {
	

}
