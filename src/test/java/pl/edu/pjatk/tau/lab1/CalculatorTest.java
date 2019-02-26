package pl.edu.pjatk.tau.lab1;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class CalculatorTest{
    Calculator calc;

    @Before
    public void init() {
        calc = new Calculator();
    }

    @Test
    public void calculatorExistCheck() {
        assertNotNull(calc);
    }

    @Test
    public void additionTestCheck() {
        assertEquals(4, calc.add(2, 2));
    }

    @Test
    public void sumCheck() {
        assertEquals(1.0, calc.sumOfTen(), 0.001);
    }
}