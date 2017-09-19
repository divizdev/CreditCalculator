package ru.divizdev.creditcalculator.BL;

/**
 * Created by diviz on 28.08.2017.
 */
public class AnnuitySeparateCalculationDecreaseTerm extends AbstractSeparateCalculation {

    private final ISeparateCalculation _separateCalculation;
    private final double _repayment;


    public AnnuitySeparateCalculationDecreaseTerm(ISeparateCalculation separateCalculation, double payment) {
        _separateCalculation = separateCalculation;
        _repayment = payment;

        calc();
    }

    private void calc() {
        double percent = _separateCalculation.getPayment().getBalance() * getOptionsCredit().getPercentMonth();
        double debt = _repayment - percent;
        _payment = new Payment(_separateCalculation.getPayment().getBalance(), percent, debt, _separateCalculation.getPayment().getObligatoryPayment() );
    }

    @Override
    public OptionsCredit getOptionsCredit(){
        return _separateCalculation.getOptionsCredit();
    }



    @Override
    public void recalc() {
        calc();
    }

    @Override
    public void recalc(ISeparateCalculation lastCalculation) {
        _separateCalculation.recalc(lastCalculation);
        calc();
    }


    @Override
    public ISeparateCalculation getLastCalculation() {
        return _separateCalculation.getLastCalculation();
    }
}
