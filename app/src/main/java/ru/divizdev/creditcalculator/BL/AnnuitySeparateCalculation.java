package ru.divizdev.creditcalculator.BL;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class AnnuitySeparateCalculation implements ISeparateCalculation {


    private final OptionsCredit _optionsCredit;
    @Nullable
    private final ISeparateCalculation _lastCalculation;
    private final double _monthlyPayment;
    private IPayment _payment;

    public AnnuitySeparateCalculation(@NonNull ISeparateCalculation lastCalculation) {
        _lastCalculation = lastCalculation;
        _optionsCredit = lastCalculation.getOptionsCredit();

        double balance = _lastCalculation.getPayment().getBalance() - _lastCalculation.getPayment().getDebt();

        _monthlyPayment = _lastCalculation.getMonthlyPayment();
        _payment = calcPayment(balance, _monthlyPayment);
    }

    public AnnuitySeparateCalculation(OptionsCredit optionsCredit) {

        _lastCalculation = null;
        _optionsCredit = optionsCredit;
        _monthlyPayment = calcMonthlyPayment(_optionsCredit);

        _payment = calcPayment(_optionsCredit.getAmountCredit(), _monthlyPayment);
    }

    private IPayment calcPayment(double balance, double payment) {
        double percent = balance * getPercentMonth();
        double debt = payment - percent;
        return (new Payment(balance, percent, debt));
    }

    @Override
    public OptionsCredit getOptionsCredit() {
        return _optionsCredit;
    }

    @Override
    public int getMonths() {
        return _optionsCredit.getMonths();
    }

    @Override
    public double getPercentMonth() {
        return _optionsCredit.getPercentMonth();
    }

    @Override
    public ISeparateCalculation getLastCalculation() {
        return _lastCalculation;
    }

    @Override
    public IPayment getPayment() {
        return _payment;
    }

    @Override
    public double getMonthlyPayment() {
        return _monthlyPayment;
    }

    private double calcMonthlyPayment(OptionsCredit optionsCredit) {
        int months = optionsCredit.getMonths();
        double amountCredit = optionsCredit.getAmountCredit();
        double percentMonth = optionsCredit.getPercentMonth();
        return amountCredit * (percentMonth + percentMonth / (Math.pow((1 + percentMonth), months) - 1));
    }


}
