package ru.divizdev.creditcalculator.BL;

public interface ISeparateCalculation {

    ISeparateCalculation getLastCalculation();

    IPayment getPayment();

    OptionsCredit getOptionsCredit();

    void recalc();

    void recalc(ISeparateCalculation lastCalculation);


}
