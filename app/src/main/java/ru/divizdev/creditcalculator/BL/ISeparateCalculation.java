package ru.divizdev.creditcalculator.BL;

public interface ISeparateCalculation {
    int getMonths();

    double getPercentMonth();

    ISeparateCalculation getLastCalculation();

    IPayment getPayment();

    OptionsCredit getOptionsCredit();
}
