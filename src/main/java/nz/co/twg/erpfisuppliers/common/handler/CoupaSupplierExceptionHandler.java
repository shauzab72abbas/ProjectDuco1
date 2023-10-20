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
 public class CoupaSupplierExceptionHandler {

    @ExceptionHandler(value = CoupaSupplierException.class)
    protected ResponseEntity<ErrorResponseDto> handleApiError(CoupaSupplierException apiException,
 HttpServletRequest request) {
        ResponseStatus responseStatus = AnnotationUtils.findAnnotation(apiException.getClass(),
 ResponseStatus.class);
        HttpStatus status;
        if (responseStatus != null) {
            status = responseStatus.value();
        } else {
            status = HttpStatus.BAD_REQUEST;
        }

        ErrorResponseDto errorResponseDto = new ErrorResponseDto(apiException.getMessage());
        return new ResponseEntity<>(errorResponseDto, status);
    }

    @ExceptionHandler(value = SchemaValidationException.class)
    protected ResponseEntity<ErrorResponseDto> schemaValidationException(SchemaValidationException
 apiException, HttpServletRequest request) {
        ResponseStatus responseStatus = AnnotationUtils.findAnnotation(apiException.getClass(),
 ResponseStatus.class);
        HttpStatus status;
        if (responseStatus != null) {
            status = responseStatus.value();
        } else {
            status = HttpStatus.BAD_REQUEST;
        }

       ErrorResponseDto errorResponseDto = new ErrorResponseDto(apiException.getMessage());
        return new ResponseEntity<>(errorResponseDto, status);
    }

 }
