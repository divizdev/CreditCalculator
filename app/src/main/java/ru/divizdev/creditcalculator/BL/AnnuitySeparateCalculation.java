package ru.divizdev.creditcalculator.BL;

import android.support.annotation.NonNull;

public class AnnuitySeparateCalculation extends AbstractSeparateCalculation {


    public static final double NIL = 1e-3;

    protected AnnuitySeparateCalculation() {
        _optionsCredit = null;
        _lastCalculation = null;

    }

    public AnnuitySeparateCalculation(@NonNull ISeparateCalculation lastCalculation) {

        _lastCalculation = lastCalculation;
        _optionsCredit = lastCalculation.getOptionsCredit();

        double balance = _lastCalculation.getPayment().getBalance() - _lastCalculation.getPayment().getDebt();

        double obligatoryPayment = _lastCalculation.getPayment().getObligatoryPayment();
        _payment = calcPayment(balance, obligatoryPayment);
    }

    public AnnuitySeparateCalculation(OptionsCredit optionsCredit) {

        _lastCalculation = null;
        _optionsCredit = optionsCredit;
        double obligatoryPayment = calcObligatoryPayment(_optionsCredit);

        _payment = calcPayment(_optionsCredit.getAmountCredit(), obligatoryPayment);
    }

    private IPayment calcPayment(double balance, double payment) {

        if (balance < NIL) {
            return Payment.getNullPayment();
        }
        double percent = balance * getOptionsCredit().getPercentMonth();
        double debt = payment - percent;
        return (new Payment(balance, percent, debt));
    }

    @Override
    public OptionsCredit getOptionsCredit() {
        return _optionsCredit;
    }

    @Override
    public void recalc() {
        if (_lastCalculation != null) {

            double balance = _lastCalculation.getPayment().getBalance() - _lastCalculation.getPayment().getDebt();

            double obligatoryPayment = _lastCalculation.getPayment().getObligatoryPayment();
            _payment = calcPayment(balance, obligatoryPayment);

        } else {
            double obligatoryPayment = calcObligatoryPayment(_optionsCredit);
            _payment = calcPayment(_optionsCredit.getAmountCredit(), obligatoryPayment);
        }
    }

    @Override
    public void recalc(ISeparateCalculation lastCalculation) {
        _lastCalculation = lastCalculation;
        recalc();
    }




}
