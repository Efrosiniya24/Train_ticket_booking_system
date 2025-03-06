package org.project.trainticketbookingsystem.web;

import lombok.extern.slf4j.Slf4j;
import org.project.trainticketbookingsystem.exceptions.BookingException;
import org.project.trainticketbookingsystem.exceptions.CoachException;
import org.project.trainticketbookingsystem.exceptions.RouteException;
import org.project.trainticketbookingsystem.exceptions.RouteStationTimeException;
import org.project.trainticketbookingsystem.exceptions.SeatException;
import org.project.trainticketbookingsystem.exceptions.StationException;
import org.project.trainticketbookingsystem.exceptions.TrainException;
import org.project.trainticketbookingsystem.exceptions.UserException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(TrainException.class)
    public ResponseEntity<Object> handlerTrainException(TrainException e) {
        log.info(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @ExceptionHandler(UserException.class)
    public ResponseEntity<Object> handlerUserException(UserException e) {
        log.info(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @ExceptionHandler(StationException.class)
    public ResponseEntity<Object> handlerStationException(StationException e) {
        log.info(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @ExceptionHandler(SeatException.class)
    public ResponseEntity<Object> handlerSeatException(SeatException e) {
        log.info(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @ExceptionHandler(RouteStationTimeException.class)
    public ResponseEntity<Object> handlerRouteStationTimeException(RouteStationTimeException e) {
        log.info(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @ExceptionHandler(RouteException.class)
    public ResponseEntity<Object> handlerRouteException(RouteException e) {
        log.info(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @ExceptionHandler(CoachException.class)
    public ResponseEntity<Object> handlerCoachException(CoachException e) {
        log.info(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @ExceptionHandler(BookingException.class)
    public ResponseEntity<Object> handlerBookingException(BookingException e) {
        log.info(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }
}
