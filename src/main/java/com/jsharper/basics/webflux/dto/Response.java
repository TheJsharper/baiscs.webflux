package com.jsharper.basics.webflux.dto;

import java.util.Date;

import lombok.Data;

@Data
//@ToString
@SuppressWarnings("unused")
public class Response {

	private Date date = new Date();
	private int output;

	public Response(int output) {
		this.output = output;
	}

	public Response() {
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getOutput() {
		return output;
	}

	public void setOutput(int output) {
		this.output = output;
	}

	@Override
	public String toString() {
		return "Response [date=" + date + ", output=" + output + "]";
	}

}
