package com.mnlsolution.chime.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Member {

	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int memberSeq;
	
	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "age", nullable = false)
	private int age;

//	@Column(name = "regitime", nullable = false,  columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
	@Column(name = "regiTime", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")	
	@Temporal(TemporalType.TIMESTAMP)
//	@Temporal(TemporalType.DATE)
	private Date regiTime; 
	
	

	public Member() {
	}

	public Member(String name, int age) {
		super();
		this.name = name;
		this.age = age;
	}

	public int getMemberSeq() {
		return memberSeq;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
	
	

	public Date getRegiTime() {
		return regiTime;
	}

	public void setRegiTime(Date regitime) {
		this.regiTime = regitime;
	}

	@Override
	public String toString() {
		return "Member [memberSeq=" + memberSeq + ", name=" + name + ", age="
                + age + "]";
	}
	
	
	

}
