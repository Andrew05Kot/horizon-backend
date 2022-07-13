package com.kot.horizon.api.v1.exceptions;

import java.util.stream.Collectors;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.server.ResponseStatusException;
import com.kot.horizon.common.exception.LocalizedException;
import com.kot.horizon.localization.LocalizationService;

@ControllerAdvice
public class APIExceptionsHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(APIExceptionsHandler.class);
	
	@Autowired
	private LocalizationService i18n;

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<ExceptionsMessageWrapper> handleUnsupportedMethods(
			HttpRequestMethodNotSupportedException ex) {

		ExceptionsMessageWrapper message = new ExceptionsMessageWrapper(ex.getLocalizedMessage());

		LOGGER.error(message.getReason(), ex);
		return new ResponseEntity<>(message, HttpStatus.METHOD_NOT_ALLOWED);
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ExceptionsMessageWrapper> handleMessageNotReadable(HttpMessageNotReadableException ex) {

		ExceptionsMessageWrapper message = new ExceptionsMessageWrapper("JSON syntax error " + ex.getCause().getMessage());

		LOGGER.error(message.getReason(), ex);
		return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ExceptionsMessageWrapper> handleAccessDeniedException (AccessDeniedException ex) {

		ExceptionsMessageWrapper message = new ExceptionsMessageWrapper(ex.getMessage());

		LOGGER.error(message.getReason(), ex);
		return new ResponseEntity<>(message, HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler(UnsupportedOperationException.class)
	public ResponseEntity<ExceptionsMessageWrapper> handleUnsupportedOperationException(UnsupportedOperationException ex) {

		ExceptionsMessageWrapper message = new ExceptionsMessageWrapper( "The operation is not supported" );

		LOGGER.error(message.getReason(), ex);
		return new ResponseEntity<>(message, HttpStatus.SERVICE_UNAVAILABLE);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ExceptionsMessageWrapper> handleMethodArgumentNotValidException(
			MethodArgumentNotValidException ex) {

		String message = ex.getBindingResult().getAllErrors()
			.stream().map( this :: getMessageFromObjectError )
			.collect( Collectors.joining( ", " ) );

		ExceptionsMessageWrapper messageWrapper = new ExceptionsMessageWrapper( message );

		LOGGER.error(messageWrapper.getReason(), ex);
		return new ResponseEntity<>(messageWrapper, HttpStatus.BAD_REQUEST);

	}

	private String getMessageFromObjectError( ObjectError objectError ) {
		StringBuilder stringBuilder = new StringBuilder(50);
		stringBuilder.append("'");
		if( objectError instanceof FieldError ) {
			FieldError fieldError = ( FieldError ) objectError;
			stringBuilder.append(fieldError.getField());
		} else {
			stringBuilder.append(objectError.getObjectName());
		}
		stringBuilder.append("' ").append(objectError.getDefaultMessage());
		return stringBuilder.toString();
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ExceptionsMessageWrapper> handleConstraintViolationException(
			ConstraintViolationException ex) {

		String message = ex.getConstraintViolations()
				.stream().map( ConstraintViolation :: getMessage )
				.collect( Collectors.joining( ", " ) );

		ExceptionsMessageWrapper messageWrapper = new ExceptionsMessageWrapper( message );

		LOGGER.error(messageWrapper.getReason(), ex);
		return new ResponseEntity<>(messageWrapper, HttpStatus.BAD_REQUEST);

	}

	@ExceptionHandler(ResponseStatusException.class)
	public ResponseEntity<ExceptionsMessageWrapper> handleBadRequests(ResponseStatusException ex) {

		ExceptionsMessageWrapper messageWrapper = new ExceptionsMessageWrapper(ex.getReason());

		LOGGER.error(messageWrapper.getReason(), ex);
		return new ResponseEntity<>(messageWrapper, ex.getStatus());
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<ExceptionsMessageWrapper> handleMethodArgumentTypeMismatch(
			MethodArgumentTypeMismatchException ex) {

		String message = ex.getRequiredType() == null ?
				"Bad request"
				: "Path variable must be of type " + ex.getRequiredType().getSimpleName();

		ExceptionsMessageWrapper messageWrapper = new ExceptionsMessageWrapper(message);

		LOGGER.error(messageWrapper.getReason(), ex);
		return new ResponseEntity<>(messageWrapper, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<ExceptionsMessageWrapper> handleBadCredentialsException(BadCredentialsException ex) {
		ExceptionsMessageWrapper messageWrapper = new ExceptionsMessageWrapper(ex.getMessage());

		LOGGER.error(messageWrapper.getReason(), ex);
		return new ResponseEntity<>(messageWrapper, HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(UsernameNotFoundException.class)
	public ResponseEntity<ExceptionsMessageWrapper> handleUsernameNotFoundException(UsernameNotFoundException ex) {
		ExceptionsMessageWrapper messageWrapper = new ExceptionsMessageWrapper(ex.getMessage());

		LOGGER.error(messageWrapper.getReason(), ex);
		return new ResponseEntity<>(messageWrapper, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(BindException.class)
	public ResponseEntity<ExceptionsMessageWrapper> handleBindException(BindException ex) {
		if ( !CollectionUtils.isEmpty( ex.getBindingResult().getAllErrors() ) ) {
			String message = ex.getBindingResult().getAllErrors().stream()
				.map( FieldError.class :: cast )
				.map( fieldError -> fieldError.getField() + " : " + fieldError.getDefaultMessage())
				.collect( Collectors.joining( ", " ) );
			if ( !message.isEmpty() ) {
				ExceptionsMessageWrapper messageWrapper = new ExceptionsMessageWrapper( message );
				LOGGER.error(messageWrapper.getReason(), ex);
				return new ResponseEntity<>(messageWrapper, HttpStatus.BAD_REQUEST);
			}
		}
		return handleInternalExceptions(ex);
	}

	@ExceptionHandler(MissingServletRequestPartException.class)
	public ResponseEntity< ExceptionsMessageWrapper > handleMissingServletRequestPartException(
			MissingServletRequestPartException exception ) {
		ExceptionsMessageWrapper message = new ExceptionsMessageWrapper( exception.getMessage() );

		LOGGER.error(message.getReason(), exception);
		return new ResponseEntity<>( message, HttpStatus.BAD_REQUEST );
	}

	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ResponseEntity< ExceptionsMessageWrapper > handleMissingServletRequestParameterException(
			MissingServletRequestParameterException exception ) {
		ExceptionsMessageWrapper message = new ExceptionsMessageWrapper( exception.getMessage() );

		LOGGER.error(message.getReason(), exception);
		return new ResponseEntity<>( message, HttpStatus.BAD_REQUEST );
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ExceptionsMessageWrapper> handleInternalExceptions( Exception ex) {

		ExceptionsMessageWrapper message = new ExceptionsMessageWrapper("Internal Server Error");

		LOGGER.error(ex.getMessage(), ex);
		return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(LocalizedException.class)
	public final ResponseEntity<LocalizedExceptionResponse> handleLocalizedException(LocalizedException ex) {
		LOGGER.error(ex.getReason());
		return new ResponseEntity<>(buildLocalizedExceptionResponse(ex), HttpStatus.LOCKED);
	}

	private LocalizedExceptionResponse buildLocalizedExceptionResponse(LocalizedException ex) {
		return new LocalizedExceptionResponse(ex.getReason(), i18n.translate( ex.getContentKey() ) );
	}

}
