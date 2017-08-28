package ru.divizdev.creditcalculator.BL;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class AnnuitySeparateCalculation extends AbstractSeparateCalculation {


    @Nullable
    private final ISeparateCalculation _lastCalculation;
    private final double _monthlyPayment;

    protected OptionsCredit _optionsCredit;
    protected IPayment _payment;

    protected AnnuitySeparateCalculation() {
        _optionsCredit = null;
        _lastCalculation = null;
        _monthlyPayment = 0;

    }

    public AnnuitySeparateCalculation(@NonNull ISeparateCalculation lastCalculation) {

        _lastCalculation = lastCalculation;
        _optionsCredit = lastCalculation.getOptionsCredit();

        double balance = _lastCalculation.getPayment().getBalance() - _lastCalculation.getPayment().getDebt();

        _monthlyPayment = _lastCalculation.getPayment().getObligatoryPayment();
        _payment = calcPayment(balance, _monthlyPayment);
    }

    public AnnuitySeparateCalculation(OptionsCredit optionsCredit) {

        _lastCalculation = null;
        _optionsCredit = optionsCredit;
        _monthlyPayment = calcObligatoryPayment(_optionsCredit);

        _payment = calcPayment(_optionsCredit.getAmountCredit(), _monthlyPayment);
    }

    private IPayment calcPayment(double balance, double payment) {
        double percent = balance * getOptionsCredit().getPercentMonth();
        double debt = payment - percent;
        return (new Payment(balance, percent, debt));
    }

    @Override
    public OptionsCredit getOptionsCredit() {
        return _optionsCredit;
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
