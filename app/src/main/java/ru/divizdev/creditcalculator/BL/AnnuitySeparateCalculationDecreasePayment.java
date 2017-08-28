package ru.divizdev.creditcalculator.BL;

/**
 * Created by diviz on 27.08.2017.
 */
public class AnnuitySeparateCalculationDecreasePayment extends AnnuitySeparateCalculation {

    private final ISeparateCalculation _separateCalculation;



    public AnnuitySeparateCalculationDecreasePayment(ISeparateCalculation separateCalculation, double payment) {


        _separateCalculation = separateCalculation;
        int index = getIndex();


        double percent = separateCalculation.getPayment().getBalance() * separateCalculation.getOptionsCredit().getPercentMonth();
        double debt = payment - percent;


        OptionsCredit optionsCredit = new OptionsCredit(separateCalculation.getOptionsCredit().getMonths() - index - 1,
                separateCalculation.getOptionsCredit().getInterestRate(), separateCalculation.getPayment().getBalance() - debt);

        double monthlyPayment = calcObligatoryPayment(optionsCredit);


        _payment = new Payment(separateCalculation.getPayment().getBalance(), percent, debt, monthlyPayment);

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
