package ru.divizdev.creditcalculator.BL;

import android.support.annotation.Nullable;

/**
 * Created by diviz on 28.08.2017.
 */
public abstract class AbstractSeparateCalculation implements ISeparateCalculation {

    protected OptionsCredit _optionsCredit;
    protected IPayment _payment;
    @Nullable
    protected ISeparateCalculation _lastCalculation;


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

    @Override
    public ISeparateCalculation getLastCalculation() {
        return _lastCalculation;
    }

    @Override
    public IPayment getPayment() {
        return _payment;
    }

}
