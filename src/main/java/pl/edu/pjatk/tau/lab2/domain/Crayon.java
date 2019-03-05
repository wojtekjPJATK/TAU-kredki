package pl.edu.pjatk.tau.lab2.domain;

public class Crayon {
    private long id;
    private String color;

    public void setId(long i) {
        id  = i;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public long getId() {
        return id;
    }

    public String getColor() {
        return color;
    }
}