package com.marco.marioni.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class ShipServiceAspect {

    @Before("execution(* com.marco.marioni.services.ShipService.findById(Integer)) && args(id)")
    public void logIfNegativeId(Integer id) {
        if (id < 0) {
            log.warn("Attempted to find a ship with a negative ID: {}", id);
        }
    }
}
