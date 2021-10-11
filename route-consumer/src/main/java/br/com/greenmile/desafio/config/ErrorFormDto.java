package br.com.greenmile.desafio.config;

import lombok.Getter;


public class ErrorFormDto {
	
	@Getter
	private String field;
	
	@Getter
	private String msg;
	
	public ErrorFormDto(String field, String msg) {
		this.field = field;
		this.msg = msg;
	}

}
