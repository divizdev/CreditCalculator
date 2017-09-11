package ru.divizdev.creditcalculator.BL;

/**
 * Created by diviz on 27.08.2017.
 */
public class AnnuitySeparateCalculationDecreasePayment extends AbstractSeparateCalculation {

    private final ISeparateCalculation _separateCalculation;



    public AnnuitySeparateCalculationDecreasePayment(ISeparateCalculation separateCalculation, double payment) {


        _separateCalculation = separateCalculation;
        int index = getIndex();


        double percent = _separateCalculation.getPayment().getBalance() * _separateCalculation.getOptionsCredit().getPercentMonth();
        double debt = payment - percent;


        OptionsCredit optionsCredit = new OptionsCredit(_separateCalculation.getOptionsCredit().getMonths() - index - 1,
                _separateCalculation.getOptionsCredit().getInterestRate(), _separateCalculation.getPayment().getBalance() - debt);

        double monthlyPayment = calcObligatoryPayment(optionsCredit);


        _payment = new Payment(_separateCalculation.getPayment().getBalance(), percent, debt, monthlyPayment);

    }
    @Override
    public OptionsCredit getOptionsCredit(){
        return _separateCalculation.getOptionsCredit();
    }

    @Override
    public void recalc() {

    }

    @Override
    public void recalc(ISeparateCalculation lastCalculation) {

    }


    @Override
    public ISeparateCalculation getLastCalculation() {
        return _separateCalculation.getLastCalculation();
    }


}
