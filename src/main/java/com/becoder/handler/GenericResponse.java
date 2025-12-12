package com.becoder.handler;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class GenericResponse {
	
//	private HttpStatus responseStatus;
//	private String status; //success , failed
//	private String message; //saved success
//	private Object data; //data
//
//	public ResponseEntity<?> create(){
//		Map<String,Object> map=new LinkedHashMap<>();
//		map.put("ststus",status);
//		map.put("message",message);
//
//		if(!ObjectUtils.isEmpty(data)) {
//			map.put("data",data);
//		}
//		return new ResponseEntity<>(map,responseStatus);
//
//
//	}
//

	private HttpStatus responseStatus;
	private String status;
	private String message;
	private Object data;

	// Getters and setters

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {
		private HttpStatus responseStatus;
		private String status;
		private String message;
		private Object data;

		public Builder responseStatus(HttpStatus responseStatus) {
			this.responseStatus = responseStatus;
			return this;
		}

		public Builder status(String status) {
			this.status = status;
			return this;
		}

		public Builder message(String message) {
			this.message = message;
			return this;
		}

		public Builder data(Object data) {
			this.data = data;
			return this;
		}

		public GenericResponse build() {
			GenericResponse response = new GenericResponse();
			response.responseStatus = this.responseStatus;
			response.status = this.status;
			response.message = this.message;
			response.data = this.data;
			return response;
		}
	}

	public ResponseEntity<?> create() {
		return new ResponseEntity<>(this, this.responseStatus);
	}

}
