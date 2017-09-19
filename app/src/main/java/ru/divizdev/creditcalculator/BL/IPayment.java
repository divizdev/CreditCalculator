package ru.divizdev.creditcalculator.BL;

/**
 * Created by diviz on 22.07.2017.
 */
public interface IPayment {

    /**
     *
     * @return Остаток кредита на начало периода
     */
    double getBalance();

    /**
     *
     * @return Проценты по кредиту в этом периоде
     */
    double getPercent();

    /**
     *
     * @return Основной долг в этом периоде
     */
    double getDebt();

    /**
     *
     * @return Сумма платежа
     */
    double getAmount();

    /**
     *
     * @return Обязательный платеж
     */
    double getObligatoryPayment();


}
