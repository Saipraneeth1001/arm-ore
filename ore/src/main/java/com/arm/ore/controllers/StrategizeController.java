package com.arm.ore.controllers;


import com.arm.ore.helper.StrategyHelper;
import com.arm.ore.requests.IncomeTaxRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(value = "*")
@RestController
@RequestMapping("/v1/strategize")
public class StrategizeController {

    @PostMapping("/createStrategy")
    public List<String> getStrategy(@RequestBody IncomeTaxRequest request) {
        StrategyHelper helper = new StrategyHelper(request);
        return helper.getStrategy();
    }

}

