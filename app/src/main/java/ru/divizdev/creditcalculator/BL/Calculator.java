package ru.divizdev.creditcalculator.BL;

/**
 * Created by diviz on 22.07.2017.
 */
public class Calculator {
    public ICalculation calculation(int months, int interestRate, int amountCredit) {
        OptionsCredit optionsCredit = new OptionsCredit(months, interestRate, amountCredit);
        return new AnnuityCalculation(optionsCredit);
    }

    public ICalculation calculation(OptionsCredit optionsCredit) {
        return new AnnuityCalculation(optionsCredit);
    }
}
