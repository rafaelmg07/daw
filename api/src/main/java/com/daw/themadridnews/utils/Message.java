package com.daw.themadridnews.utils;

import com.fasterxml.jackson.annotation.JsonView;

/*
 * IMPORTANTE:
 * Establecer primero el codigo del mensaje, antes que el propio mensaje
 * Asi se añadira "<b>Error: </b>" o "<b>OK: </b>" al inicio dependiendo del valor del codigo
 */
public class Message {
	public static interface Basic {}
	
	@JsonView(Basic.class) protected int code;
	@JsonView(Basic.class) protected String message;
	
	
	public Message() {
		this.code = 0;
		this.message = "";
	}
	
	public Message(int code, String message, String type) {
		this.code = code;
		setMessage(message);
	}
	
	public int getCode() {
		return code;
	}
	
	public void setCode(int code) {
		this.code = code;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "Message [code=" + code + ", message=" + message + "]";
	}
}
