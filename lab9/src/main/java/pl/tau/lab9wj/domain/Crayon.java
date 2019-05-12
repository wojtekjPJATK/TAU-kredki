package pl.tau.lab9wj.domain;

import java.util.Date;

import javax.persistence.*;

@Entity(name = "Crayon")
@Table(name = "crayon")
@NamedQueries({
        @NamedQuery(name = "crayons.all", query = "Select c from Crayon c"),
        @NamedQuery(name = "crayons.findbyColor", query = "Select c from Crayon c where c.color like :givenColor"),
        @NamedQuery(name = "crayons.findByCreator", query="Select c from Crayon c where c.creator=:creator")
})
public class Crayon {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String color;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id")
    private Person creator;

    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    public Long getId() {
        return id;
    }

    public void setCreationDate(Date date) {
        this.creationDate = date;
    }

    public Date getTimestamp() {
        return this.creationDate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Person getCreator() {
        return creator;
    }

    public void setCreator(Person creator) {
        this.creator = creator;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String Color) {
        this.color = Color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof Crayon))
            return false;

        return id != null && id.equals(((Crayon) o).getId());
    }

    @Override
    public int hashCode() {
        return 1337;
    }
}
