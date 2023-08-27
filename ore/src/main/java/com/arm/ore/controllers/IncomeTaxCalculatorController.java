package com.arm.ore.controllers;


import com.arm.ore.Helper;
import com.arm.ore.requests.IncomeTaxRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@CrossOrigin(value = "*")
@RestController
@RequestMapping("/v1/income")
public class IncomeTaxCalculatorController {

    @PostMapping(value ="/tax")
    public String calculateTax(@RequestBody IncomeTaxRequest request) {
        log.info("income tax request {}", request);
        Helper helper = new Helper(request);
        String result = helper.computeSectionWiseDeduction(request);
        return result;
    }
}
