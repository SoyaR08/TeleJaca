package com.AtencionSocioSanitaria.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class LlamadaId implements Serializable{
	
	private Integer callId;
	private Integer userId;
	private String  usernameId;
	private Date callDate;
	private Integer orden;
	public LlamadaId(Integer callId, Integer userId, String usernameId, Date callDate, Integer orden) {
		super();
		this.callId = callId;
		this.userId = userId;
		this.usernameId = usernameId;
		this.callDate = callDate;
		this.orden = orden;
	}
	public LlamadaId() {
		super();
	}
	public Integer getCallId() {
		return callId;
	}
	public void setCallId(Integer callId) {
		this.callId = callId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getUsernameId() {
		return usernameId;
	}
	public void setUsernameId(String usernameId) {
		this.usernameId = usernameId;
	}
	public Date getCallDate() {
		return callDate;
	}
	public void setCallDate(Date callDate) {
		this.callDate = callDate;
	}
	public Integer getOrden() {
		return orden;
	}
	public void setOrden(Integer orden) {
		this.orden = orden;
	}
	
}
