package br.com.alura.forum.exception;

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

import br.com.alura.forum.dto.FormDto;

@RestControllerAdvice
public class FormException {

	@Autowired
	MessageSource messageSource;
	
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public List<FormDto> headle(MethodArgumentNotValidException exception) {
		List<FormDto> listaDto = new ArrayList<>();
		
		List<FieldError> campos = exception.getBindingResult().getFieldErrors();
		
		campos.forEach(campo -> {
			String mensagem = messageSource.getMessage(campo, LocaleContextHolder.getLocale());
			listaDto.add(new FormDto(campo.getField(), mensagem));
		});
		
		return listaDto;
	}
	
}
