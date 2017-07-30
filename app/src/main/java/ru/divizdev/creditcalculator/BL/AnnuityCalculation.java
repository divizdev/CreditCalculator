package ru.divizdev.creditcalculator.BL;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by diviz on 22.07.2017.
 */
public class AnnuityCalculation implements  ICalculation{
    private final double _overpayment;
    private double _monthlyPayment;
    private List<IPayment> _paymentList;
    private final IPayment _nullPayment = new Payment(0, 0 , 0);


    public AnnuityCalculation(int months, int interestRate, int amountCredit) {
        double percentMonth = interestRate / 12f / 100f;
        _monthlyPayment = amountCredit * (percentMonth + percentMonth / ( Math.pow((1 + percentMonth), months) - 1));
        _overpayment =  _monthlyPayment * months - amountCredit;

        _paymentList = new ArrayList<>(months);

        double remainder = amountCredit;

        for (int i = 0; i < months; i++) {

            double percent = remainder * percentMonth;
            double debt = ( _monthlyPayment -  percent);

            _paymentList.add(new Payment(remainder, percent, debt));

            remainder = remainder - debt;
        }
    }

    public double getMonthlyPayment() {
        return _monthlyPayment;
    }

    @Override
    public double getOverpayment() {
        return _overpayment;
    }

    @Override
    public IPayment getPayment(int index) {
        if (isCorrectIndex(index)) {
            return _paymentList.get(index);
        }
        return _nullPayment;
    }
    private boolean isCorrectIndex(int index){
        return index>=0 && index < _paymentList.size();
    }

    @Override
    public void setRepayment(int index, double payment, TypeRepayment decreasePayment) {
        if (isCorrectIndex(index)) {

            IPayment lastPayment = getPayment(index);
            double delta = payment - lastPayment.getAmount();
            _paymentList.set(index, new Payment(lastPayment.getBalance() - delta,
                    lastPayment.getPercent(),
                    lastPayment.getDebt() + delta));
        }
    }
}
