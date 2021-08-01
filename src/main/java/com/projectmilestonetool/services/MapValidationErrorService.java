package com.projectmilestonetool.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

@Service
public class MapValidationErrorService {

	public ResponseEntity<?> mapValidationService(BindingResult result) {
		if (result.hasErrors()) {
			Map<String, String> errorMap = new HashMap<>();
			for (FieldError fielderror : result.getFieldErrors()) {
				errorMap.put(fielderror.getField(), fielderror.getDefaultMessage());
			}
			System.out.println("****************map validation errorfield called ***************************");
			return new ResponseEntity<Map<String, String>>(errorMap, HttpStatus.BAD_REQUEST);
		}
		return null;
	}

}
