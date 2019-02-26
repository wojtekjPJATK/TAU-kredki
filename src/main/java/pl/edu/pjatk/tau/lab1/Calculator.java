package pl.edu.pjatk.tau.lab1;


public class Calculator 
{
    public int add(int a, int b) {
        return a+b;
    }

    public Double sumOfTen() {
        Double sum = 0.0;
        for(int i = 0; i < 10; i++) {
            sum += 0.1;
        }
        return sum;
    }
}
