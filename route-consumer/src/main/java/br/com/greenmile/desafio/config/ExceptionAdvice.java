package br.com.greenmile.desafio.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.greenmile.desafio.exception.CoordinateDeliveryNotFoundException;
import br.com.greenmile.desafio.exception.RoutePlanNotFoundException;

@RestControllerAdvice
public class ExceptionAdvice {
	
	@Autowired
	private MessageSource messageSource;
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public List<ErrorFormDto> methodArgumentNotValidHandler(MethodArgumentNotValidException exp){
		List<ErrorFormDto> listErrors = new ArrayList<>();
		
		List<FieldError> fieldErrors = exp.getBindingResult().getFieldErrors();
		
		fieldErrors.forEach(e ->{
			String mensagem = messageSource.getMessage(e, LocaleContextHolder.getLocale());
			ErrorFormDto erro = new ErrorFormDto(e.getField(), mensagem);
			listErrors.add(erro);
		});
		
		return listErrors;
		
	}	
	
	@ExceptionHandler(CoordinateDeliveryNotFoundException.class)
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public ErrorDto CoordinateDeliveryNotFoundHandler(CoordinateDeliveryNotFoundException exp) {
		return new ErrorDto(HttpStatus.NO_CONTENT.value(), exp.getMessage());
	}
	
	@ExceptionHandler(RoutePlanNotFoundException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public ErrorDto RoutePlanNotFoundHanler(RoutePlanNotFoundException exp) {
		return new ErrorDto(HttpStatus.BAD_REQUEST.value(), exp.getMessage());
	}

}
