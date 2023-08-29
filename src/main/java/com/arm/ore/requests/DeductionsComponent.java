package com.arm.ore.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeductionsComponent {
    int hra;
    int homeLoanSelfOccupied;
    int homeLoanLetOut;
    int educationLoan;
    int employeeNPS;
    int employerNPS;
    int section80C;
    int medicalInsurancePremium;
    int savingsBankInterest;
    int donationsToPoliticalParty;
}
