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
public class Team {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int teamSeq;

	@Column(name = "teamName", nullable = false, length = 100)
	private String teamName;

	@Column(name = "teamCode", nullable = false, length = 5)
	private String teamCode;

	@Column(name = "regiTime", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@Temporal(TemporalType.TIMESTAMP)
	private Date regiTime;

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public String getTeamCode() {
		return teamCode;
	}

	public void setTeamCode(String teamCode) {
		this.teamCode = teamCode;
	}

	public Date getRegiTime() {
		return regiTime;
	}

	public void setRegiTime(Date regiTime) {
		this.regiTime = regiTime;
	}

}
