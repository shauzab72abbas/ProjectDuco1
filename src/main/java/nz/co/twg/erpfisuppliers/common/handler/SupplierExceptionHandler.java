package nz.co.twg.erpfisuppliers.common.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
@Slf4j
public class SupplierExceptionHandler {

	@ExceptionHandler(value = SupplierException.class)
	protected ResponseEntity<ErrorResponseDto> handleApiError(SupplierException apiException, HttpServletRequest request) {
		ResponseStatus responseStatus = AnnotationUtils.findAnnotation(apiException.getClass(), ResponseStatus.class);
		HttpStatus status;
		if (responseStatus != null) {
			status = responseStatus.value();
		} else {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		ErrorResponseDto errorResponseDto = new ErrorResponseDto(apiException.getMessage());
		return new ResponseEntity<>(errorResponseDto, status);
	}

	@ExceptionHandler(value = SchemaCreationException.class)
	protected ResponseEntity<ErrorResponseDto> schemaCreationException(SchemaCreationException apiException, HttpServletRequest request) {
		ResponseStatus responseStatus = AnnotationUtils.findAnnotation(apiException.getClass(), ResponseStatus.class);
		HttpStatus status;
		if (responseStatus != null) {
			status = responseStatus.value();
		} else {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		ErrorResponseDto errorResponseDto = new ErrorResponseDto(apiException.getMessage());
		return new ResponseEntity<>(errorResponseDto, status);
	}

}