package com.ip.ranges.task.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class RegionNotFoundException extends Exception {
  public RegionNotFoundException(String message) {
    super(message);
  }
}
