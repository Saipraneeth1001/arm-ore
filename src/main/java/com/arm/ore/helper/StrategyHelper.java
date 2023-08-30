package com.arm.ore.helper;

import com.arm.ore.requests.DeductionsComponent;
import com.arm.ore.requests.IncomeTaxRequest;
import com.arm.ore.requests.SalaryComponent;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class StrategyHelper {

    SalaryComponent salaryComponent;
    DeductionsComponent deductionsComponent;
    int totalSalary;
    List<String> list;
    public StrategyHelper(IncomeTaxRequest request) {
        this.salaryComponent = request.getData().getSalaryComponent();
        this.deductionsComponent = request.getData().getDeductionsComponent();
        this.totalSalary = salaryComponent.getSalaryIncome() + salaryComponent.getIncomeFromBank()
                + salaryComponent.getInvestments();
        list = new ArrayList<>();
    }

    public List<String> getStrategy() {

        if (totalSalary < 750000) {
            log.info("totalSalary: {}, no need", totalSalary);
            list.add("As your salary is less than 750000, please opt for new tax regime.");
            list.add("no further analysis required");
            return list;
        }
        list.add("Total Taxable Salary before deductions: "+ totalSalary);
        standardDeduction();
        section80C();
        hra();
        section80D();
        section80TTA();
        section80EEA();
        section80E();
        lta();
        list.add(" Now the total taxable salary is: "+totalSalary);
        return list;

    }

    private void lta() {
        StringBuilder result = new StringBuilder("");
        result.append(" if you have any Leave travel allowance provided by your company then")
                .append(" you can claim deduction on the amount given by the company")
                .append(" please subtract the Leave travel allowance given by your company from")
                .append(" the taxable salary");
    }

    private void section80E() {
        StringBuilder result = new StringBuilder("");
        result.append("interest on education loan if any: ")
                .append(totalSalary - deductionsComponent.getEducationLoan());
//        result.append("if you are paying any interest on the education loan, then you can claim")
//                .append(" entire interest is tax free upto 8 years")
//                .append(" this strategy assumes that you don't have any loan")
//                .append(" so not subtracting any value from the taxable salary")
//                .append(" if you are paying any interest then please subtract the interest value from")
//                .append(" the taxable salary");
        totalSalary -= deductionsComponent.getEducationLoan();
        list.add(result.toString());
    }

    //TODO change the conditions properly
    private void section80EEA() {
        StringBuilder result = new StringBuilder("");
        result.append("interest on home loan, if any: ")
                .append(totalSalary - deductionsComponent.getHomeLoanLetOut());
//        result.append("if you are paying any interest on the home loan, then you can claim")
//                .append(" a maximum deduction of rupees 150000 to 200000")
//                .append(" this strategy assumes that you don't have any loan")
//                .append(" so not subtracting any value from the taxable salary")
//                .append(" if you are paying any interest then please subtract the interest value from")
//                .append(" the taxable salary and it shouldn't be more than 150000");
        totalSalary -= deductionsComponent.getHomeLoanLetOut();
        list.add(result.toString());

    }

    private void section80TTA() {
        StringBuilder result = new StringBuilder("");
        result.append("savings bank interest <= 10k")
                .append(" - adding value provided by you: ")
                .append(totalSalary - deductionsComponent.getSavingsBankInterest());
//        result.append("The maximum amount you can claim as savings bank interest is 10000")
//                .append(" you might not have received an interest of rupees 10000 or you might have received even more")
//                .append(" that totally depends on your bank, you can find the value in form 16")
//                .append(" for the sake of this strategy let's substitute the value of rupees 10000 as")
//                .append(" interest from savings bank account")
//                .append(" this reduces your taxable salary to: ")
//                .append(totalSalary - 10000);
        totalSalary -= deductionsComponent.getSavingsBankInterest();
        list.add(result.toString());
    }

    private void section80D() {
        StringBuilder result = new StringBuilder("");

        if (deductionsComponent.getMedicalInsurancePremium() != 0) {
            result.append("Under section80D: ")
                    .append(totalSalary - deductionsComponent.getMedicalInsurancePremium());
            totalSalary -= deductionsComponent.getMedicalInsurancePremium();
        } else {
            result.append("Under section80D, ")
                    .append("as you not provided any value, ")
                    .append("taking defaults: ")
                    .append(totalSalary - 5000);
            totalSalary -= 5000;

        }
//        result.append("You can claim a max medical insurance premium of rupees 25000")
//                .append(" if your dependent parents age is greater than 60 years")
//                .append(" then you can claim a maximum of 75000")
//                .append(" if your dependents are not senior citizens i.e age < 60 years")
//                .append(" maximum you can claim is 50000")
//                .append(" if you don't have any medical insurance, then you can still claim an amount")
//                .append(" of rupees 5000")
//                .append(" for the sake of strategy, taking 5000 as the default value")
//                .append(" this makes your taxable salary go down to ")
//                .append(totalSalary - 5000);
        list.add(result.toString());

    }

    private void hra() {
        StringBuilder result = new StringBuilder("");

        if (deductionsComponent.getHra() != 0) {
            result.append("HRA - Provided by you: ")
                    .append(totalSalary - deductionsComponent.getHra());
            totalSalary -= deductionsComponent.getHra();
        } else {
            result.append("Adding a default HRA Component of 96000")
                    .append(" -> Taxable Salary: ")
                    .append(totalSalary - 96000);
            totalSalary -= 96000;
        }
//        result.append(" HRA is a huge component to reduce your taxable income")
//                .append(" use our hra calculator to find the exempted hra")
//                .append(" which you can subtract from your taxable income")
//                .append(" for the sake of the strategy we are taking an amount of 96000 per year as HRA")
//                .append(" and using it for calculation").append(" this reduces your taxable income to ")
//                .append(totalSalary - 96000);
        list.add(result.toString());
    }

    private void section80C() {
        StringBuilder result = new StringBuilder("");
        result.append("Under section 80C, you can claim a max deduction of 150000: ")
//                .append(" Your PF already will be already added under section80C")
//                .append(" So find some investments like ELSS, LIC, PPF")
//                .append(" which when added can be totaled to 150000")
//                .append(" this reduces your taxable income to: ")
                .append(totalSalary - 150000);
        totalSalary -= 150000;
        list.add(result.toString());
    }

    private void standardDeduction() {
        StringBuilder result = new StringBuilder("");
//        result.append("Every employee can claim a standard deduction of 50000")
//                .append(" This can reduce your taxable income to: ")
        result.append("Standard Deduction: ")
                .append(totalSalary - 50000);
        totalSalary -= 50000;
        list.add(result.toString());
    }

    public double CalculateHRA() {
//        Actual rent paid minus 10% of the basic salary, or
//        Actual HRA offered by the employer, or
//        50% of basic salary when residential house is situated in Mumbai, Delhi, Chennai or Kolkata;
//        40% of basic salary when residential house is situated elsewhere

        double hra = 0.00;
        // hra is minimum of all the options above.
        return hra;

    }


}
