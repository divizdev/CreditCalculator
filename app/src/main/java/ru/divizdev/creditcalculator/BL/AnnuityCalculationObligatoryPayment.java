package ru.divizdev.creditcalculator.BL;

/**
 * Created by diviz on 28.08.2017.
 */
public class AnnuityCalculationObligatoryPayment implements ICalculationObligatoryPayment {

    @Override
    public double calcObligatoryPayment(OptionsCredit optionsCredit){
        int months = optionsCredit.getMonths();
        double amountCredit = optionsCredit.getAmountCredit();
        double percentMonth = optionsCredit.getPercentMonth();
        return amountCredit * (percentMonth + percentMonth / (Math.pow((1 + percentMonth), months) - 1));
    }

}
