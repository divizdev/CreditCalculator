package ru.divizdev.creditcalculator.BL;

/**
 * Created by diviz on 28.08.2017.
 */
public class AnnuitySeparateCalculationDecreaseTerm extends AnnuitySeparateCalculation {

    private final ISeparateCalculation _separateCalculation;

    public AnnuitySeparateCalculationDecreaseTerm(ISeparateCalculation separateCalculation, double payment) {
        _separateCalculation = separateCalculation;

        double percent = separateCalculation.getPayment().getBalance() * getOptionsCredit().getPercentMonth();
        double debt = payment - percent;
        _payment = new Payment(_separateCalculation.getPayment().getBalance(), percent, debt, separateCalculation.getPayment().getObligatoryPayment() );
    }

    @Override
    public OptionsCredit getOptionsCredit(){
        return _separateCalculation.getOptionsCredit();
    }



    @Override
    public ISeparateCalculation getLastCalculation() {
        return _separateCalculation.getLastCalculation();
    }
}
