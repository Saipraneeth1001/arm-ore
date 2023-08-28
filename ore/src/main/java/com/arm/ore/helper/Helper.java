package com.arm.ore.helper;

import com.arm.ore.constants.OreConstants;
import com.arm.ore.requests.DeductionsComponent;
import com.arm.ore.requests.IncomeTaxRequest;
import com.arm.ore.requests.SalaryComponent;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Helper {

    SalaryComponent salaryComponent;
    DeductionsComponent deductionsComponent;
    int totalSalary;
    StringBuilder result = new StringBuilder();

    public Helper(IncomeTaxRequest incomeTaxRequest) {
        this.salaryComponent = incomeTaxRequest.getData().getSalaryComponent();
        this.deductionsComponent = incomeTaxRequest.getData().getDeductionsComponent();
        totalSalary = salaryComponent.getSalaryIncome() + salaryComponent.getInvestments() +
                salaryComponent.getIncomeFromBank();
    }

    public String computeSectionWiseDeduction(IncomeTaxRequest incomeTaxRequest) {

        if (totalSalary <= OreConstants.NO_TAX_LIMIT) {
            return "OPT FOR NEW TAX REGIME, YOU DON'T HAVE TO PAY ANY TAXES";
        }

        DeductionsComponent request = incomeTaxRequest.getData().getDeductionsComponent();
        section80C(request);
        section80EEA(request);
        standardDeduction(request);
        sectionNPS(request);
        section80D(request);
        section80E(request);
        section80GGC(request);
        sectionHRA(request);
        section80TTAB(request);
        return result.toString();

    }

    private void section80D(DeductionsComponent request) {
        if (deductionsComponent.getMedicalInsurancePremium() != 0) {
            result.append("You have a medical insurance premium of rupees: ").append(deductionsComponent.getMedicalInsurancePremium())
                    .append("The maximum that can be claimed under section80D is: ").append(OreConstants.SECTION_80D_LIMIT)
                    .append("Your current taxable salary is: ").append(totalSalary)
                    .append("subtracting your medical insurance premium it now becomes: ")
                    .append(totalSalary - deductionsComponent.getMedicalInsurancePremium())
                    .append("<end>");
            totalSalary = totalSalary - deductionsComponent.getMedicalInsurancePremium();
        }
    }

    private void sectionNPS(DeductionsComponent request) {
        if (request.getEmployerNPS() != 0) {

            result.append("if your employer is contributing to NPS, you can claim a deduction that shouldn't exceed 10% of your basic salary, which is: ")
            .append(0.1 * salaryComponent.getBasicSalary());
            result.append("Employer contribution to NPS in your case is: ")
                    .append(deductionsComponent.getEmployerNPS());
            int nps = (int) (0.1 * salaryComponent.getBasicSalary());
            if (deductionsComponent.getEmployerNPS() < nps) {
                result.append("employer contribution is less than 10% of basic salary")
                        .append("so in your case taxable salary becomes:")
                        .append(totalSalary - deductionsComponent.getEmployerNPS());
            } else {
                result.append("employer contribution is greater than 10% of basic salary")
                        .append("so in your case taxable salary becomes:")
                        .append(totalSalary - nps);
            }
            result.append("<end>");
        }
        if (request.getEmployeeNPS() != 0) {
            int deduction = Math.min(deductionsComponent.getEmployeeNPS(), OreConstants.EMPLOYEE_NPS_LIMIT);
            result.append("you can claim deduction under this section only if you have NPS Tier-1 Account")
            .append("you can claim maximum deduction of ").append(OreConstants.EMPLOYEE_NPS_LIMIT)
            .append("in your case it is: ").append(deduction).append("total Salary becomes: ")
                    .append(totalSalary - deduction).append("<end>");
            totalSalary = totalSalary - deduction;

        }
    }

    private void standardDeduction(DeductionsComponent request) {
        result.append("every employee gets a standard deduction of rupees")
                .append(OreConstants.STANDARD_DEDUCTION)
        .append("now taxable income becomes: ")
                .append(totalSalary - OreConstants.STANDARD_DEDUCTION)
                .append("<end>");
        totalSalary = totalSalary - OreConstants.STANDARD_DEDUCTION;
    }

    private void section80EEA(DeductionsComponent request) {
        if (deductionsComponent.getHomeLoanSelfOccupied() != 0 ||
                deductionsComponent.getHomeLoanLetOut() != 0) {
            int deduction = Math.min(OreConstants.SECTION_80EEA_LIMIT, deductionsComponent.getHomeLoanLetOut());
            result.append("you can claim a maximum deduction of")
                    .append(OreConstants.SECTION_80EEA_LIMIT)
                    .append("on interest paid on the home loan ")
                    .append("now after subtracting whatever applies for you, your taxable salary becomes")
                    .append(totalSalary - deduction).append("<end>")
            ;
            totalSalary = totalSalary - deduction;
        }
    }

    private void section80C(DeductionsComponent request) {
        if (deductionsComponent.getSection80C() != 0) {
            result.append("you can claim a maximum deduction of ")
                    .append(OreConstants.SECTION_80C_LIMIT)
                    .append("under section80C ")
                    .append("now your taxable salary is: ")
                    .append(totalSalary - OreConstants.SECTION_80C_LIMIT)
                    .append("<end>");

            totalSalary = totalSalary - OreConstants.SECTION_80C_LIMIT;
        }
    }

    private void section80E(DeductionsComponent request) {
        if (deductionsComponent.getEducationLoan() != 0) {
            result.append("The interest paid on education loan is entirely tax free, so you can claim an amount of")
                    .append(deductionsComponent.getEducationLoan())
                    .append("now your taxable salary is: ")
                    .append(totalSalary - deductionsComponent.getEducationLoan())
                    .append("<end>");
            totalSalary = totalSalary - deductionsComponent.getEducationLoan();
        }
    }


    private void section80GGC(DeductionsComponent request) {
        if (deductionsComponent.getDonationsToPoliticalParty() != 0) {
            result.append("The interest paid on education is entirely tax free, so you can claim an amount of ")
                    .append(deductionsComponent.getDonationsToPoliticalParty())
                    .append("now your taxable salary is: ")
                    .append(totalSalary - deductionsComponent.getDonationsToPoliticalParty());
            result.append("<end>");
            totalSalary = totalSalary - deductionsComponent.getDonationsToPoliticalParty();
        }
    }

    private void sectionHRA(DeductionsComponent request) {
        if (deductionsComponent.getHra() != 0) {
            result.append("you can claim an HRA of:").append(deductionsComponent.getHra())
                    .append("now your taxable salary is: ")
                    .append(totalSalary - deductionsComponent.getHra())
                    .append("<hra>");
            totalSalary = totalSalary - deductionsComponent.getHra();
        }
    }

    private void section80TTAB(DeductionsComponent request) {
        if (deductionsComponent.getSavingsBankInterest() != 0) {
            int deduction = Math.min(deductionsComponent.getSavingsBankInterest(), OreConstants.SECTION_80TTA);
            result.append("you can claim a maximum deduction of ")
                    .append(OreConstants.SECTION_80TTA)
                    .append("in your case, your taxable salary now becomes")
                    .append(totalSalary - deduction);
            totalSalary = totalSalary - deduction;
        }
    }
}
