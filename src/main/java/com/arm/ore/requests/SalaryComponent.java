package com.arm.ore.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalaryComponent {
    int salaryIncome;
    int incomeFromBank;
    int investments;
    int basicSalary;

}
