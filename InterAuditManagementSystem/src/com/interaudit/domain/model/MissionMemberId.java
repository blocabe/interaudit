package com.interaudit.domain.model;

import java.io.Serializable;


public class MissionMemberId implements Serializable{
	public MissionMemberId(Long memberId, Long missionId) {
		super();
		this.memberId = memberId;
		this.missionId = missionId;
	}
	public MissionMemberId() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long memberId;
	private Long missionId;
	public Long getMemberId() {
		return memberId;
	}
	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
	public Long getMissionId() {
		return missionId;
	}
	public void setMissionId(Long missionId) {
		this.missionId = missionId;
	}
}
