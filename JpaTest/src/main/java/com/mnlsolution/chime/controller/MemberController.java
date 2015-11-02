package com.mnlsolution.chime.controller;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.persistence.EntityManager;

import org.apache.commons.net.ntp.TimeStamp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mnlsolution.chime.domain.Member;
import com.mnlsolution.chime.domain.QMember;
import com.mnlsolution.chime.domain.Team;
import com.mnlsolution.chime.repository.MemberRepository;
import com.mnlsolution.chime.repository.TeamRepository;
import com.mysema.query.jpa.impl.JPAQuery;

/**
 * @author ch.kwak (chkwak@mnlsolution.com)
 * @since 
 */
@Controller
@RequestMapping("/")
public class MemberController {
	
	private static final Logger log = LoggerFactory.getLogger(MemberController.class);

	

	@Autowired
	private MemberRepository repository;
	
	@Autowired
	private TeamRepository teamRepository;
	
	@Autowired
	private EntityManager entityManager;  
	
	
	@RequestMapping(value = "/")
	public @ResponseBody String home(Locale locale) {
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG,
				DateFormat.LONG, locale);
		String formattedDate = dateFormat.format(date);
		
		log.info("home Start.. 접속시간 : {}" , formattedDate);
		
		return "Home입니다.";
	}
	
	/**
	 * 
	 * 리스트 조회
	 * page=0&size=10&sort=name,desc 
	 * @return
	 */
	@RequestMapping(value = "/list", method=RequestMethod.GET)
	public @ResponseBody Page<Member> list(Pageable page) {
		log.info("***********list : {}", page);		
		
		
		Page<Member> list =  repository.findAll(page); 
		log.info("전체 카운트 {}", list.getSize());
		
		return list; 
	}
	

	/**
	 * 단순 전체 목록 조회 
	 * @return
	 */
	@RequestMapping(value = "/listfull", method=RequestMethod.GET)
	public @ResponseBody List<Member> list() {
		
		List<Member> list = repository.findAll();
		
		log.info("전체 카운트 {}", list.size());
		
		return list;   
	}
	
	
	
	/**
	 * 네임 목록 조회 
	 * @return
	 */
	@RequestMapping(value = "/listName", method=RequestMethod.GET)
	public @ResponseBody List<Member> listName(@RequestParam("name") String name) {
		
		 List<Member> member = (List<Member>) repository.findByName(name);
		
		return member;   
	}
	
	
	/**
	 * 네임 쿼리 조회 
	 * @param name
	 * @return
	 */
	@RequestMapping(value = "/listQuery", method=RequestMethod.GET)
	public @ResponseBody List<Member> listQuery(@RequestParam("name") String name) {
		
		log.info("**************{}", name);
		 List<Member> member = (List<Member>) repository.findByCustomQueryName(name);
		return member;   
	}
	
	
	/**
	 * QueryDSL 질의
	 * @return
	 */
	@RequestMapping(value = "/listQueryDSL", method=RequestMethod.GET)
	public @ResponseBody List<Member> listQueryDSL(@RequestParam("name") String name) {
		
		log.info("**************{}", name);
		
		JPAQuery query = new JPAQuery(entityManager); 
		QMember qM = QMember.member;
		
		// 일반 where 절 
//		List<Member> members = query.from(qM).orderBy(qM.age.desc()).list(qM);
		
		// like 절
//		List<Member> members = query.from(qM).where(qM.name.like("%" + name + "%")).orderBy(qM.regiTime.desc()).list(qM);
		List<Member> members = query.from(qM).where(qM.name.like("%" + name + "%")).orderBy(qM.regiTime.desc()).list(qM);
		
		// and 절
		List<Member> members2 = query.from(qM).where(  qM.name.eq("곽채화").and(qM.name.eq("홍길동"))).list(qM); 
		
		return members;   
	}
	
	

	@RequestMapping(value = "/team", method=RequestMethod.GET)
	public @ResponseBody Team temaQuery(@RequestParam("name") String name) {
		
		log.info("*************{}", name);  
		
		// 이름으로 검색
		Team team = (Team) teamRepository.findByTeamName(name);
		
		return team;   
	}
	

	/**
	 * 
	 * 지정 항목 조회 
	 * @return
	 */
	@RequestMapping(value = "/list/{memberId}", method=RequestMethod.GET)
	public @ResponseBody Member listOne(@PathVariable int memberId ) {
		log.info("***********listOne : {}", memberId);
		
		return repository.findOne(memberId); 
	}
	
	/**
	 * 
	 * 지정항목 삭제 
	 * @param memberId
	 * @return
	 */
	@RequestMapping(value = "/delete/{memberId}", method=RequestMethod.GET)
	public @ResponseBody  List<Member>  deleteOne(@PathVariable int memberId ) {
		log.info("*********** : deleteOne",  memberId);
		
		repository.delete(memberId);
		
		return repository.findAll();  
	}
	
	
	/**
	 * 지정항목 값 수정
	 * @param memberId
	 * @return
	 */
	@RequestMapping(value = "/modify/{memberId}", method=RequestMethod.GET)
	public @ResponseBody  List<Member>  modifyOne(@PathVariable int memberId ) {
		log.info("*********** : modifyOne",  memberId);
		
		Member member = repository.findOne(memberId);   
				
		member.setAge(100);
		member.setName("곽채화2");		
		
		return repository.findAll();  
	}
	
	
	
	/**
	 * 항목 추가 
	 * @param member
	 * @return
	 */
	@RequestMapping(value = "/addMember", method=RequestMethod.GET)
	public @ResponseBody List<Member> addMember(@RequestParam("name")String name, @RequestParam("age")int age) {

		Member member = new Member();
		
		Date date = new Date();
		
		member.setName(name);
		member.setAge(age);
		member.setRegiTime(date);		
		repository.save(member);

		return repository.findAll();
	}

	
	
	
	@ResponseBody
	@RequestMapping(value = "/jsonTest", method = RequestMethod.GET)
	public HashMap<String, Object> jsonTest(Locale locale) {

		log.info("jsonTest");

		// your logic

		HashMap<String, Object> subHashmap1 = new HashMap<String, Object>();

		subHashmap1.put("sub1Key", "sub1Value");
		subHashmap1.put("sub2Key", "sub2Value");
		subHashmap1.put("sub3Key", "sub3Value");

		HashMap<String, Object> subHashmap2 = new HashMap<String, Object>();

		subHashmap2.put("sub11Key", "한글 테스트");
		subHashmap2.put("sub21Key", "sub22Value");
		subHashmap2.put("sub31Key", "sub33alue");
		
		subHashmap1.put("sub4Key", subHashmap2);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG,
				DateFormat.LONG, locale);

		String formattedDate = dateFormat.format(date);

		
		HashMap<String, Object> hashmap = new HashMap<String, Object>();
		hashmap.put("ApplicatinKey", "0001");
		hashmap.put("readDate", formattedDate);
		
		hashmap.put("KEY2", "YES2");
		hashmap.put("KEY3", "YES3");
		hashmap.put("KEY4", subHashmap1);

		return hashmap;
	}

}
