package ru.divizdev.creditcalculator.BL;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by diviz on 22.07.2017.
 * Расчет аннуитетного платежа
 */
public class AnnuityCalculation implements ICalculation {

    private final IPayment _nullPayment = new Payment(0, 0, 0);

    private final OptionsCredit _optionsCredit;
    private final List<IPayment> _paymentList;




    public AnnuityCalculation(OptionsCredit optionsCredit) {
        _optionsCredit = optionsCredit;

 
        _paymentList = new ArrayList<>(_optionsCredit.getMonths());

        double balance = _optionsCredit.getAmountCredit();
        double monthlyPayment = calcMonthlyPayment(_optionsCredit.getMonths(), balance, _optionsCredit.getPercentMonth());

        for (int i = 0; i < _optionsCredit.getMonths(); i++) {

            double percent = balance * _optionsCredit.getPercentMonth();
            double debt = monthlyPayment - percent;

            if (debt > balance) {
                debt = balance;
            }

            _paymentList.add(new Payment(balance, percent, debt));

            balance -= debt;
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
        double monthlyPayment = lastPayment.getAmount();

        for (int i = index + 1; i < _optionsCredit.getMonths(); i++) {
            double percent = newBalance * _optionsCredit.getPercentMonth();
            double debt = monthlyPayment - percent;

            if (debt > newBalance) {
                debt = newBalance;
            }
            _paymentList.set(i, new Payment(newBalance, percent, debt));
            newBalance -= debt;
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

        double newBalance = (lastPayment.getBalance() - (lastPayment.getDebt() + delta));
        double monthlyPayment = calcMonthlyPayment(_optionsCredit.getMonths() - index - 1, newBalance, _optionsCredit.getPercentMonth());

        for (int i = index + 1; i < _optionsCredit.getMonths(); i++) {
            double percent = newBalance * _optionsCredit.getPercentMonth();
            double debt = monthlyPayment - percent;
            if (debt > newBalance) {
                debt = newBalance;
            }
            _paymentList.set(i, new Payment(newBalance, percent, debt));
            newBalance -= debt;
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
}
