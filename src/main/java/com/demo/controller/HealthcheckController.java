package com.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


class StatusCode{
    String status;
    String code;
    public StatusCode(String status,String code){
        this.status=status;
        this.code=code;
    }

}

@RestController
public class HealthcheckController {

    @GetMapping(value = "/healthcheck")
    public ResponseEntity<StatusCode> healthcheckOK(@RequestParam("format") String format) {
        StatusCode sc= new StatusCode("status","OK");
        System.out.println(format);
       return new ResponseEntity<StatusCode>(sc,HttpStatus.OK);
    }
}
