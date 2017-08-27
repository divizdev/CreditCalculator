package ru.divizdev.creditcalculator.BL;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class AnnuitySeparateCalculation implements ISeparateCalculation {


    private final OptionsCredit _optionsCredit;
    @Nullable
    private final ISeparateCalculation _lastCalculation;
    private IPayment _payment;

    public AnnuitySeparateCalculation(@NonNull ISeparateCalculation lastCalculation) {
        _lastCalculation = lastCalculation;
        _optionsCredit = lastCalculation.getOptionsCredit();

        double balance = _lastCalculation.getPayment().getBalance() - _lastCalculation.getPayment().getDebt();


        _payment = calcPayment(balance, _lastCalculation.getPayment().getAmount());
    }

    public AnnuitySeparateCalculation(OptionsCredit optionsCredit) {

        _lastCalculation = null;
        _optionsCredit = optionsCredit;

        double payment = calcMonthlyPayment(_optionsCredit);

        _payment = calcPayment(_optionsCredit.getAmountCredit(), payment);
    }

    private IPayment calcPayment(double balance, double payment) {
        double percent = balance * getPercentMonth();
        double debt = payment - percent;
        return(new Payment(balance, percent, debt));
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

    private double calcMonthlyPayment(OptionsCredit optionsCredit) {
        int months = optionsCredit.getMonths();
        double amountCredit = optionsCredit.getAmountCredit();
        double percentMonth = optionsCredit.getPercentMonth();
        return amountCredit * (percentMonth + percentMonth / (Math.pow((1 + percentMonth), months) - 1));
    }


}
