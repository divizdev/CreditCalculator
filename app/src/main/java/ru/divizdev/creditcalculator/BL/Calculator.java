package ru.divizdev.creditcalculator.BL;

/**
 * Created by diviz on 22.07.2017.
 */
public class Calculator {
    public ICalculation calculation(int months, int interestRate, int amountCredit) {
        return new AnnuityCalculation(months, interestRate, amountCredit);
    }
}
