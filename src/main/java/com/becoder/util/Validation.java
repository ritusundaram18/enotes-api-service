package com.becoder.util;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import com.becoder.dto.CategoryDto;
import com.becoder.exception.ValidationException;

@Component
public class Validation {
	public void categoryValidation(CategoryDto categoryDto) {
		Map<String, Object> error = new LinkedHashMap<>();

		if (ObjectUtils.isEmpty(categoryDto)) {
			throw new IllegalArgumentException("category Object/JSON should not be null or empty");
//			error
		} else {
			// validation name filed
			if (ObjectUtils.isEmpty(categoryDto.getName())) {
//				throw new IllegalArgumentException("name field is empty or null");
				error.put("name", "name field is empty or null");
			} else {
				if (categoryDto.getName().length() < 10) {
					error.put("name", "name length min 3");
				}
				if (categoryDto.getName().length() > 100) {
					error.put("name", "name length min 10");
				}
			}
		}

		// validation description
		if (ObjectUtils.isEmpty(categoryDto)) {
			throw new IllegalArgumentException("category Object/JSON should not be null or empty");
//				error
		}

		// isActive
		if (ObjectUtils.isEmpty(categoryDto.getIsActive())) {
//					throw new IllegalArgumentException("name field is empty or null");
			error.put("IsActive", "IsActive field is empty or null");
		} else {
			if (categoryDto.getIsActive() != Boolean.TRUE.booleanValue() && categoryDto.getIsActive() != Boolean.FALSE.booleanValue()) {
				error.put("IsActive", "invalid value isActive field");
			}
		}
		if (!error.isEmpty()) {
			throw new ValidationException(error);
		}
	}
}
