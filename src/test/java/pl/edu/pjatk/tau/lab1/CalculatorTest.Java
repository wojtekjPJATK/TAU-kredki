packahe pl.edu.pjwstk.tau.lab1;

public class CalculatorTest() {
    Calculator calc;

    @before
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
        assertEquals(10.0, calc.sum(), 0.001);
    }
}