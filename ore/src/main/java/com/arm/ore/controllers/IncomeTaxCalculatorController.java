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
        int income = request.getSalaryIncome() + request.getIncomeFromBank() + request.getInvestments();
        int deductions = request.getHra() + request.getEducationLoan() + request.getEmployeeNPS() +
                request.getEmployerNPS() + request.getHomeLoanLetOut() + request.getHomeLoanSelfOccupied() +
                request.getMedicalInsurancePremium() + request.getSavingsBankInterest() + request.getSection80C();
        Helper.computeSectionWiseDeduction(request);
        return "done";
    }
}
