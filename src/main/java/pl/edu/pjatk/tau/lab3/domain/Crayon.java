package pl.edu.pjatk.tau.lab3.domain;

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

    @Override
	public boolean equals(Object o) {
		Crayon other = (Crayon) o;
		return other.getColor() == this.getColor() && other.getId() == this.getId();
}
}