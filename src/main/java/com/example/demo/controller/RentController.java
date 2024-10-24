package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.service.RentService;

@Controller
@RequestMapping("/api/rents")
public class RentController {

    private RentService rentService;

    public RentController(RentService rentService){
        this.rentService = rentService;
    }

    /*@PutMapping("/{id}/return")
    public ResponseEntity<void> returnBook(@PathVariable Long id){
        Rent rent = 
    } */

}
