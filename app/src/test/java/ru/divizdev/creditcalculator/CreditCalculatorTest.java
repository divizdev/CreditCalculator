package ru.divizdev.creditcalculator;

import org.junit.Before;
import org.junit.Test;
import ru.divizdev.creditcalculator.BL.Calculator;
import ru.divizdev.creditcalculator.BL.ICalculation;
import ru.divizdev.creditcalculator.BL.TypeRepayment;

import static org.junit.Assert.*;

/**
 * Created by diviz on 22.07.2017.
 */
public class CreditCalculatorTest {

//    Кредит в размере 100 000 р.
//    Процентная ставка 10%.
//    Сроком погашения кредита возьмём 6 месяцев.
//    Для начала рассчитаем ежемесячный платёж = 17 156,14

//    1 месяц
//    Проценты: 100000 * 0,1 / 12 = 833,33
//    Основной долг: 17156,14 – 833, 33 = 16322,81
//    2 месяц
//    Остаток кредита: 100000 – 16322,81 = 83677,19
//    Проценты: 83677,19 * 0,1/12 = 697,31
//    Основной долг: 17156,14 – 697,31 = 16458,83
//    3 месяц
//    Остаток кредита: 83677,19 — 16458,83 = 67218,36
//    Проценты: 67218,36 *0,1/12 = 560,15
//    Основной долг: 17156,14 – 560,15 = 16595,99
//    4 месяц
//    Остаток кредита: 67218,36 — 16595,99 = 50622,38
//    Проценты: 50622.38 * 0,1/12 = 421.85
//    Основной долг: 17156,14 – 421,85 = 16734,29
//    5 месяц
//    Остаток кредита: 50622,38 — 16734,29 = 33888,09
//    Проценты: 33888,09 * 0,1/12 = 282,40
//    Основной долг: 17156,14 – 282,40 = 16873,74
//    6 месяц
//    Остаток кредита: 33888.09 — 16873.74 = 17014,35
//    Проценты: 17014,35 * 0,1/12 = 141,79
//    Основной долг: 17156,14 – 141,79 = 17014,35



    private Calculator _calc;
    private ICalculation _calculation;

    @Before
    public void setUp(){
        _calc = new Calculator();

        _calculation = _calc.calculation(6, 10, 100000);
    }


    /**
     * Проверка ежемесячного платежа
     * @throws Exception
     */
    @Test
    public void testMonthlyPayment() throws Exception {

        ICalculation calculation;

        calculation = _calc.calculation(36, 20, 1000000);
        assertEquals(37163.58, calculation.getPayment(0).getAmount(), 2);
        assertEquals(17156.14, _calculation.getPayment(0).getAmount(),  2);

    }

    /**
     * Проверка переплаты
     * @throws Exception
     */
    @Test
    public void overpaymentCalculate() throws  Exception{

        assertEquals(2936.84, _calculation.getOverpayment(), 2);
    }

    /**
     * Тест остатка
     * @throws Exception
     */
    @Test
    public void testPaymentBalance() throws Exception{
        assertEquals(83677.19, _calculation.getPayment(1).getBalance(), 2);
        assertEquals(33888.09, _calculation.getPayment(4).getBalance(), 2);
        assertEquals(17014.35, _calculation.getPayment(5).getBalance(), 2);
    }


    /**
     * Тест выплачиваемых процентов
     * @throws Exception
     */
    @Test
    public void testPaymentPercent() throws Exception{
        assertEquals(697.31, _calculation.getPayment(1).getPercent(), 2);
        assertEquals(141.79, _calculation.getPayment(5).getPercent(), 2);

    }


    /**
     * Тест основной выплаты
     * @throws Exception
     */
    @Test
    public void testPaymentDebt() throws Exception{

        assertEquals(16458.83, _calculation.getPayment(1).getDebt(), 2);
    }

    /**
     * Тест нулевого платежа
     * @throws Exception
     */
    @Test
    public void testNullGetPayment() throws Exception{
        assertEquals(0, _calculation.getPayment(-1).getBalance(), 2);
        assertEquals(0, _calculation.getPayment(6).getDebt(), 2);
        assertEquals(0, _calculation.getPayment(7).getAmount(), 2);
        assertEquals(0, _calculation.getPayment(700).getPercent(), 2);

    }

    /**
     * Досрочное погашение, изменение размера платежа
     */
    @Test
    public void partiallyEarlyRepayment() throws  Exception{

        _calculation.setRepayment(2, 20000, TypeRepayment.DecreasePayment);

        assertEquals(20000, _calculation.getPayment(2).getAmount(), 2);
        assertEquals(47778.52, _calculation.getPayment(3).getBalance(), 2);

    }

    /**
     * Досрочное погашение уменьшаем срок кредита
     * @throws Exception
     */
    @Test
    public void testPartiallyEarlyRepaymentDecreaseTerm() throws Exception{

        _calculation.setRepayment(2, 33890.43, TypeRepayment.DecreaseTerm);

        assertEquals(33890.43, _calculation.getPayment(2).getAmount(), 2  );
        assertEquals(33330.28, _calculation.getPayment(2).getDebt(), 2);
        assertEquals(0 , _calculation.getPayment(5).getAmount(), 2);

    }

}

