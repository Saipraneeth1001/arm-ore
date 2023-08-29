package com.arm.ore.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IncomeRequestData {
    SalaryComponent salaryComponent;
    DeductionsComponent deductionsComponent;

}
