package ru.divizdev.creditcalculator.BL;

/**
 * Created by diviz on 28.08.2017.
 */
public abstract class AbstractSeparateCalculation implements ISeparateCalculation {



    protected int getIndex() {
        int index = 0;
        ISeparateCalculation last = getLastCalculation();
        while (last != null) {
            index++;
            last = last.getLastCalculation();
        }
        return index;
    }

    protected double calcObligatoryPayment(OptionsCredit optionsCredit) {
        int months = optionsCredit.getMonths();
        double amountCredit = optionsCredit.getAmountCredit();
        double percentMonth = optionsCredit.getPercentMonth();
        return amountCredit * (percentMonth + percentMonth / (Math.pow((1 + percentMonth), months) - 1));
    }
}
