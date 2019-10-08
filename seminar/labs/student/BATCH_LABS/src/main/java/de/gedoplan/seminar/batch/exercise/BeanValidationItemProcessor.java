package de.gedoplan.seminar.batch.exercise;

import java.util.Set;

import javax.batch.api.chunk.ItemProcessor;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

@Named
public class BeanValidationItemProcessor implements ItemProcessor {

  @Inject
  Validator validator;

  @Override
  public Object processItem(Object item) throws Exception {
    Set<ConstraintViolation<Object>> constraintViolations = this.validator.validate(item);
    if (!constraintViolations.isEmpty()) {
      throw new ConstraintViolationException("Item is invalid: " + item, constraintViolations);
    }

    return item;
  }

}
