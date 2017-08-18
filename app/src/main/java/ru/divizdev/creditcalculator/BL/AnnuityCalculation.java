package ru.divizdev.creditcalculator.BL;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by diviz on 22.07.2017.
 */
public class AnnuityCalculation implements ICalculation {
    private final double _overpayment;
    private final IPayment _nullPayment = new Payment(0, 0, 0);
    private List<IPayment> _paymentList;
    private int _months;
    private final int _interestRate;


    public AnnuityCalculation(int months, int interestRate, int amountCredit) {
        _interestRate = interestRate;
        double percentMonth = interestRate / 12f / 100f;
        double monthlyPayment = amountCredit * (percentMonth + percentMonth / (Math.pow((1 + percentMonth), months) - 1));
        _overpayment = monthlyPayment * months - amountCredit;
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
        double newBalance = lastPayment.getBalance() - delta;
        _paymentList.set(index, new Payment(newBalance, lastPayment.getPercent(),
                lastPayment.getDebt() + delta));


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
        _paymentList.set(index, new Payment(lastPayment.getBalance() - delta,
                lastPayment.getPercent(),
                lastPayment.getDebt() + delta));

        double percentMonth = _interestRate / 12f / 100f;
        double newBalance = (getPayment(index + 1).getBalance() - delta);
        double monthlyPayment = (newBalance * (percentMonth + percentMonth / (Math.pow((1 + percentMonth), _months - index) - 1)));

        for (int i = index + 1; i < _months; i++) {
            double percent = newBalance * percentMonth;
            _paymentList.set(i, new Payment(newBalance, percent, monthlyPayment - percent));
            newBalance -= monthlyPayment;
        }


    }
}
