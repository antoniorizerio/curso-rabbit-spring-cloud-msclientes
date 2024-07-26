package com.rizerio.springproject.msclientes.application.controller.exception;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StandardError implements Serializable {

	private static final long serialVersionUID = -6005325857449941016L;
	
	private Long timestamp;
	
	private Integer status;
	
	private String error;
	private String message;
	private String path;

}
