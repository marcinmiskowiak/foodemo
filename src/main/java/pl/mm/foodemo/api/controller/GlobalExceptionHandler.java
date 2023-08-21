package pl.mm.foodemo.api.controller;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;
import pl.mm.foodemo.domain.exception.EmptyOrderException;
import pl.mm.foodemo.domain.exception.NotFoundException;
import pl.mm.foodemo.domain.exception.UserRegistrationException;
import pl.mm.foodemo.domain.exception.WrongFileException;

import java.util.Optional;

@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    @ExceptionHandler({Exception.class})
    public ModelAndView handleException(Exception ex) {
        String message = String.format("Other exception occurred: [%s]", ex.getMessage());
        ex.printStackTrace();
        ModelAndView modelView = new ModelAndView("error");
        modelView.addObject("errorMessage", message);
        return modelView;
    }

    @ExceptionHandler({NotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView handleNotFoundException(Exception ex) {
        String message = String.format("Not Found [%s]", ex.getMessage());
        ex.printStackTrace();
        ModelAndView modelView = new ModelAndView("error");
        modelView.addObject("errorMessage", message);
        return modelView;
    }


    @ExceptionHandler({UserRegistrationException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ModelAndView handleUserRegistrationException(Exception ex) {
        String message = String.format("User Registration Exception [%s]", ex.getMessage());
        ex.printStackTrace();
        ModelAndView modelView = new ModelAndView("error");
        modelView.addObject("errorMessage", message);
        return modelView;
    }


    @ExceptionHandler({WrongFileException.class})
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    public ModelAndView handleWrongFileException(Exception ex) {
        String message = String.format("Wrong File [%s]", ex.getMessage());
        ex.printStackTrace();
        ModelAndView modelView = new ModelAndView("error");
        modelView.addObject("errorMessage", message);
        return modelView;
    }


    @ExceptionHandler({EmptyOrderException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ModelAndView handleEmptyOrderException(Exception ex) {
        String message = String.format("Empty Order Exception [%s]", ex.getMessage());
        ex.printStackTrace();
        ModelAndView modelView = new ModelAndView("error");
        modelView.addObject("errorMessage", message);
        return modelView;
    }

    @ExceptionHandler({ConstraintViolationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ModelAndView handleConstraintViolationException(Exception ex) {
        String message = String.format("Validation Exception [%s]", ex.getMessage());
        ex.printStackTrace();
        ModelAndView modelView = new ModelAndView("error");
        modelView.addObject("errorMessage", message);
        return modelView;
    }


    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ModelAndView handleBindException(BindException ex) {
        String message = String.format("Bad request for field: [%s], wrong value: [%s]",
                Optional.ofNullable(ex.getFieldError()).map(FieldError::getField).orElse(null),
                Optional.ofNullable(ex.getFieldError()).map(FieldError::getRejectedValue).orElse(null));
        ModelAndView modelView = new ModelAndView("error");
        modelView.addObject("errorMessage", message);
        return modelView;
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    @ResponseStatus(HttpStatus.PAYLOAD_TOO_LARGE)
    public ModelAndView handleMaxUploadSizeExceededException(MaxUploadSizeExceededException ex) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("errorMessage", "The uploaded image size exceeds the allowed limit (3MB).");
        return modelAndView;
    }



}
