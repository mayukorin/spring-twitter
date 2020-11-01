package com.example.demo.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.component.DoramaComponent;
import com.example.demo.repository.DoramaRepository;

public class UniqueDoramaValidator implements ConstraintValidator<UniqueDorama,String>  {


	private final DoramaRepository DoramaRepository;

	@Autowired
	DoramaComponent targetDoramaComponent;


	public UniqueDoramaValidator() {
		this.DoramaRepository = null;
	}

	@Autowired
	public UniqueDoramaValidator(DoramaRepository doramaRepository) {
		this.DoramaRepository = doramaRepository;
	}

	@Override
	public boolean isValid(String value,ConstraintValidatorContext context) {


		if (DoramaRepository != null && DoramaRepository.searchDoramaByName(value) != null && (targetDoramaComponent.getDorama() == null )) {
			return false;
		}

		return true;
	}
}
