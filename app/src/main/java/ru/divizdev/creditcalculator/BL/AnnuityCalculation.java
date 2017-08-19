package ru.divizdev.creditcalculator.BL;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by diviz on 22.07.2017.
 */
public class AnnuityCalculation implements ICalculation {

    private final IPayment _nullPayment = new Payment(0, 0, 0);
    private final int _interestRate;
    private List<IPayment> _paymentList;
    private int _months;


    public AnnuityCalculation(int months, int interestRate, double amountCredit) {
        _interestRate = interestRate;
        double percentMonth = interestRate / 12f / 100f;
        double monthlyPayment = calcMonthlyPayment(months, amountCredit, percentMonth);
        _months = months;
        _paymentList = new ArrayList<>(months);

        double remainder = amountCredit;

        for (int i = 0; i < _months; i++) {

            double percent = remainder * percentMonth;
            double debt = (monthlyPayment - percent);

            _paymentList.add(new Payment(remainder, percent, debt));

            remainder = remainder - debt;
        }
    }

    private double calcMonthlyPayment(int months, double amountCredit, double percentMonth) {
        return amountCredit * (percentMonth + percentMonth / (Math.pow((1 + percentMonth), months) - 1));
    }


    @Override
    public double getOverpayment() {

        double overpayment = 0;

        for (IPayment payment : _paymentList) {
            overpayment += payment.getPercent();
        }

        return overpayment;
    }

    @Override
    public IPayment getPayment(int index) {
        if (isCorrectIndex(index)) {
            return _paymentList.get(index);
        }
        return _nullPayment;
    }

    private boolean isCorrectIndex(int index) {
        return index >= 0 && index < _months;
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
        if (isCorrectIndex(index)) {
            switch (typeRepayment) {
                case DecreaseTerm:
                    setDecreaseTerm(index, payment);
                    break;
                case DecreasePayment:
                    setDecreasePayment(index, payment);
                    break;
            }
        }
    }

    /**
     * Переплата с уменьшением срока
     *
     * @param index   месяц, <b>нет</b> проверки на границы
     * @param payment общая сумма платежа за месяц
     */
    private void setDecreaseTerm(int index, double payment) {
        IPayment lastPayment = getPayment(index);
        double delta = payment - lastPayment.getAmount();


        _paymentList.set(index, new Payment(lastPayment.getBalance(), lastPayment.getPercent(),
                lastPayment.getDebt() + delta));

        double newBalance = lastPayment.getBalance() - (lastPayment.getDebt() + delta);
        double percentMonth = _interestRate / 12f / 100f;
        double monthlyPayment = lastPayment.getAmount();

        for (int i = index + 1; i < _months; i++) {
            double percent = newBalance * percentMonth;
            double debt = monthlyPayment - percent;
            if (debt > newBalance) {
                debt = newBalance;
            }
            _paymentList.set(i, new Payment(newBalance, percent, debt));
            newBalance = newBalance - debt;
        }


    }

    /**
     * Переплата с уменьшением платежа
     *
     * @param index   месяц, <b>нет</b> проверки на границы
     * @param payment общая сумма платежа за месяц
     */
    private void setDecreasePayment(int index, double payment) {
        IPayment lastPayment = getPayment(index);
        double delta = payment - lastPayment.getAmount();
        _paymentList.set(index, new Payment(lastPayment.getBalance(),
                lastPayment.getPercent(),
                lastPayment.getDebt() + delta));

        double percentMonth = _interestRate / 12f / 100f;
        double newBalance = (lastPayment.getBalance() - (lastPayment.getDebt() + delta));
        double monthlyPayment = calcMonthlyPayment(_months - index - 1, newBalance, percentMonth);

        for (int i = index + 1; i < _months; i++) {
            double percent = newBalance * percentMonth;
            double debt = monthlyPayment - percent;
            _paymentList.set(i, new Payment(newBalance, percent, debt));
            newBalance -= debt;
        }


    }
}
