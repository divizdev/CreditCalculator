package ru.divizdev.creditcalculator.BL;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class AnnuitySeparateCalculation extends AbstractSeparateCalculation {


    protected OptionsCredit _optionsCredit;
    protected IPayment _payment;
    @Nullable
    private ISeparateCalculation _lastCalculation;

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

        if (balance < 1e-3) {
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

    @Override
    public ISeparateCalculation getLastCalculation() {
        return _lastCalculation;
    }

    @Override
    public IPayment getPayment() {
        return _payment;
    }


}
