package br.com.greenmile.desafio.config;

import lombok.Getter;


public class ErrorDto {
	
	@Getter
	private int code;
	
	@Getter
	private String msg;
	
	public ErrorDto(int httpCode, String msg) {
		this.code = httpCode;
		this.msg = msg;
	}

}
