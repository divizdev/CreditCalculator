package ru.divizdev.creditcalculator.BL;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by diviz on 22.07.2017.
 * Расчет аннуитетного платежа
 */
public class AnnuityCalculation implements ICalculation {


    private final OptionsCredit _optionsCredit;
    private final List<ISeparateCalculation> _paymentList;


    public AnnuityCalculation(OptionsCredit optionsCredit) {
        _optionsCredit = optionsCredit;


        _paymentList = new ArrayList<>(_optionsCredit.getMonths());

        ISeparateCalculation lastCalculation = new AnnuitySeparateCalculation(_optionsCredit);
        _paymentList.add(lastCalculation);

        for (int i = 1; i < _optionsCredit.getMonths(); i++) {

            _paymentList.add(new AnnuitySeparateCalculation(lastCalculation));
            lastCalculation = _paymentList.get(i);

        }

    }


    @Override
    public double getOverpayment() {

        double overpayment = 0;

        for (ISeparateCalculation separateCalculation : _paymentList) {
            overpayment += separateCalculation.getPayment().getPercent();
        }

        return overpayment;
    }

    @Override
    public IPayment getPayment(int index) {
        if (isCorrectIndex(index)) {
            return _paymentList.get(index).getPayment();
        }
        return Payment.getNullPayment();
    }

    private boolean isCorrectIndex(int index) {
        return index >= 0 && index < _optionsCredit.getMonths();
    }

    /**
     * Установить переплату
     *
     * @param index         - месяц
     * @param payment       - общая сумма платежа
     * @param typeRepayment - тип переплаты
     */
    @Override
    public void setRepayment(int index, double payment, TypeRepayment typeRepayment) {
        ISeparateCalculation newSeparateCalculation;
        if (isCorrectIndex(index)) {
            newSeparateCalculation = getSeparationCalculation(index, payment, typeRepayment);

            _paymentList.set(index, newSeparateCalculation);

            if (index + 1 < _paymentList.size()) {

                _paymentList.get(index + 1).recalc(newSeparateCalculation);

                for (int i = index + 2; i < _paymentList.size(); i++) {
                    _paymentList.get(i).recalc();
                }
            }
        }
    }

    private ISeparateCalculation getSeparationCalculation(int index, double payment, TypeRepayment typeRepayment) {
        ISeparateCalculation indexSeparateCalculation = _paymentList.get(index);
        switch (typeRepayment) {

            case DecreaseTerm:
                return new AnnuitySeparateCalculationDecreaseTerm(indexSeparateCalculation, payment);
            case DecreasePayment:
                return new AnnuitySeparateCalculationDecreasePayment(indexSeparateCalculation, payment);
        }

        return null;
    }
}
