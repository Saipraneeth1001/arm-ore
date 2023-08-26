package com.arm.ore;

import com.arm.ore.constants.OreConstants;
import com.arm.ore.requests.IncomeTaxRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Helper {

    public static void computeSectionWiseDeduction(IncomeTaxRequest request) {

        section80C(request);
        section80EEA(request);
        standardDeduction(request);
        sectionNPS(request);
        section80D(request);
        section80E(request);
//        section80GGC(request);
        sectionHRA(request);
        section80TTAB(request);

    }

    private static void section80D(IncomeTaxRequest request) {
        int totalSalary = request.getSalaryIncome();
        int deduction = request.getMedicalInsurancePremium();
        log.info("Maximum limit that can be exempted with medical premium is {}", OreConstants.SECTION_80D_PARENTS_LIMIT);
        totalSalary = totalSalary - deduction;
    }

    private static void sectionNPS(IncomeTaxRequest request) {
        int totalSalary = request.getSalaryIncome();
        if (request.getEmployerNPS() != 0) {
            log.info("if your employer is contributing to NPS, you can claim a deduction that shouldn't exceed 10% of your basic salary");
            log.info("10% of your basic salary is {}", 0.1 * totalSalary); // TODO replace with basic salary
            totalSalary = (int) (totalSalary - (0.1 * totalSalary));
        }
        if (request.getEmployeeNPS() != 0) {
            log.info("you can claim deduction under this section only if you have NPS Tier-1 Account");
            log.info("you can claim deduction of {}", OreConstants.EMPLOYEE_NPS_LIMIT);
            totalSalary = totalSalary - OreConstants.EMPLOYEE_NPS_LIMIT;

        }
    }

    private static void standardDeduction(IncomeTaxRequest request) {
        int totalSalary = request.getSalaryIncome();
        log.info("every employee gets a standard deduction of rupees {}", OreConstants.STANDARD_DEDUCTION);
        totalSalary = totalSalary - OreConstants.STANDARD_DEDUCTION;
    }

    private static void section80EEA(IncomeTaxRequest request) {
        int totalSalary = request.getSalaryIncome();
        log.info("you can claim a maximum deduction of {}, on interest paid on the home loan ", OreConstants.SECTION_80EEA_LIMIT);
        totalSalary = totalSalary - OreConstants.SECTION_80EEA_LIMIT;

    }

    private static void section80C(IncomeTaxRequest request) {
        int totalSalary = request.getSalaryIncome();
        log.info("you can claim a maximum deduction of {} under section80C ", OreConstants.SECTION_80C_LIMIT);
        totalSalary = totalSalary - OreConstants.SECTION_80C_LIMIT;
    }

    private static void section80E(IncomeTaxRequest request) {
        int totalSalary = request.getSalaryIncome();
        log.info("The interest paid on education is entirely tax free, so you can claim an amount of {} ", request.getEducationLoan());
        totalSalary = totalSalary - request.getEducationLoan();
    }

    //TODO add a field in ui to get the political party donation details.
//    private static void section80GGC(IncomeTaxRequest request) {
//        int totalSalary = request.getSalaryIncome();
//        log.info("The interest paid on education is entirely tax free, so you can claim an amount of {} ", request.getEducationLoan());
//        totalSalary = totalSalary - request.getEducationLoan();
//    }

    private static void sectionHRA(IncomeTaxRequest request) {
        int totalSalary = request.getSalaryIncome();
        log.info("you can claim an HRA of {} ", request.getHra());
        totalSalary = totalSalary - request.getHra();
    }

    private static void section80TTAB(IncomeTaxRequest request) {
        int totalSalary = request.getSalaryIncome();
        log.info("you can claim a maximum deduction of {} ", OreConstants.SECTION_80TTA);
        totalSalary = totalSalary - OreConstants.SECTION_80TTA;
    }
}
