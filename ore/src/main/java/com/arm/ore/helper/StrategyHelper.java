package com.arm.ore.helper;

import com.arm.ore.requests.IncomeTaxRequest;
import com.arm.ore.requests.SalaryComponent;

import java.util.ArrayList;
import java.util.List;

public class StrategyHelper {

    SalaryComponent salaryComponent;
    int totalSalary;
    List<String> list;
    public StrategyHelper(IncomeTaxRequest request) {
        this.salaryComponent = request.getData().getSalaryComponent();
        this.totalSalary = salaryComponent.getSalaryIncome() + salaryComponent.getIncomeFromBank()
                + salaryComponent.getInvestments();
        list = new ArrayList<>();
    }

    public List<String> getStrategy() {

        // start with standard deduction
        list.add("Here goes the analysis: ");
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
        result.append("if you are paying any interest on the education loan, then you can claim")
                .append(" entire interest is tax free upto 8 years")
                .append(" this strategy assumes that you don't have any loan")
                .append(" so not subtracting any value from the taxable salary")
                .append(" if you are paying any interest then please subtract the interest value from")
                .append(" the taxable salary");
        list.add(result.toString());
    }

    private void section80EEA() {
        StringBuilder result = new StringBuilder("");
        result.append("if you are paying any interest on the home loan, then you can claim")
                .append(" a maximum deduction of rupees 150000 to 200000")
                .append(" this strategy assumes that you don't have any loan")
                .append(" so not subtracting any value from the taxable salary")
                .append(" if you are paying any interest then please subtract the interest value from")
                .append(" the taxable salary and it shouldn't be more than 150000");
        list.add(result.toString());

    }

    private void section80TTA() {
        StringBuilder result = new StringBuilder("");
        result.append("The maximum amount you can claim as savings bank interest is 10000")
                .append(" you might not have received an interest of rupees 10000 or you might have received even more")
                .append(" that totally depends on your bank, you can find the value in form 16")
                .append(" for the sake of this strategy let's substitute the value of rupees 10000 as")
                .append(" interest from savings bank account")
                .append(" this reduces your taxable salary to: ")
                .append(totalSalary - 10000);
        totalSalary -= 10000;
        list.add(result.toString());
    }

    private void section80D() {
        StringBuilder result = new StringBuilder("");
        result.append("You can claim a max medical insurance premium of rupees 25000")
                .append(" if your dependent parents age is greater than 60 years")
                .append(" then you can claim a maximum of 75000")
                .append(" if your dependents are not senior citizens i.e age < 60 years")
                .append(" maximum you can claim is 50000")
                .append(" if you don't have any medical insurance, then you can still claim an amount")
                .append(" of rupees 5000")
                .append(" for the sake of strategy, taking 5000 as the default value")
                .append(" this makes your taxable salary go down to ")
                .append(totalSalary - 5000);
        totalSalary -= 5000;
        list.add(result.toString());

    }

    private void hra() {
        StringBuilder result = new StringBuilder("");
        result.append(" HRA is a huge component to reduce your taxable income")
                .append(" use our hra calculator to find the exempted hra")
                .append(" which you can subtract from your taxable income")
                .append(" for the sake of the strategy we are taking an amount of 96000 per year as HRA")
                .append(" and using it for calculation").append(" this reduces your taxable income to ")
                .append(totalSalary - 96000);
        totalSalary -= 96000;
        list.add(result.toString());
    }

    private void section80C() {
        StringBuilder result = new StringBuilder("");
        result.append("Under section 80C, you can claim a max deduction of 150000")
                .append(" Your PF already will be already added under section80C")
                .append(" So find some investments like ELSS, LIC, PPF")
                .append(" which when added can be totaled to 150000")
                .append(" this reduces your taxable income to: ")
                .append(totalSalary - 150000);
        totalSalary -= 150000;
        list.add(result.toString());
    }

    private void standardDeduction() {
        StringBuilder result = new StringBuilder("");
        result.append("Every employee can claim a standard deduction of 50000")
                .append(" This can reduce your taxable income to: ")
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
