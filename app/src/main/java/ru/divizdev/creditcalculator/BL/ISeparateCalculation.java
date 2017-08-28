package ru.divizdev.creditcalculator.BL;

public interface ISeparateCalculation {

    ISeparateCalculation getLastCalculation();

    IPayment getPayment();

    OptionsCredit getOptionsCredit();


}
