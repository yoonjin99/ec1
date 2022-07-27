package com.plateer.ec1.util;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.Set;
import java.util.stream.Collectors;

public class Validation {

    private Validator validator;

    public <T> void validate(T vo){
        Set<ConstraintViolation<T>> validVo = validator.validate(vo);
        if(!validVo.isEmpty()){
            String sb = validVo.stream().map(ConstraintViolation::getMessage).collect(Collectors.joining());
            throw new ConstraintViolationException("validation error : " + sb.toString(), validVo);
        }
    }
}
