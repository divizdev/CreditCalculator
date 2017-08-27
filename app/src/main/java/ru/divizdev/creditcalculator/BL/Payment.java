package ru.divizdev.creditcalculator.BL;

/**
 * Created by diviz on 22.07.2017.
 * Платеж по кредиту
 */
public class Payment implements IPayment {

    private static IPayment _nullPayment = new Payment(0, 0, 0);
    private final double _balance;
    private final double _percent;
    private final double _debt;


    /**
     * @param balance - остаток кредита на начало месяца
     * @param percent - проценты
     * @param debt    - основной платеж
     */
    Payment(double balance, double percent, double debt) {

        _balance = balance;
        _percent = percent;
        _debt = debt;
    }

    public static IPayment getNullPayment(){
        return _nullPayment;
    }

    @Override
    public double getBalance() {
        return _balance;
    }

    @Override
    public double getPercent() {
        return _percent;
    }

    @Override
    public double getDebt() {
        return _debt;
    }

    @Override
    public double getAmount() {
        return _percent + _debt;
    }
}
