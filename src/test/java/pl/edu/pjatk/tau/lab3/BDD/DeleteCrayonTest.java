package pl.edu.pjatk.tau.lab3.BDD;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.junit.Assert;
import pl.edu.pjatk.tau.lab3.dao.CrayonsDBDAO;
import pl.edu.pjatk.tau.lab3.domain.Crayon;

import java.util.List;

public class DeleteCrayonTest {
    private CrayonsDBDAO dao;
    private List<Crayon> crayons;
    private Crayon figure;

    @Given("Someone wants to delete crayon from the db")
    public void someonewantsToDeleteCrayon(){
        dao = new CrayonsDBDAO();
        for(long i = 0; i<10; i++){
            Crayon c = new Crayon();
            c.setId(i);
            c.setColor("Red");
            dao.addCrayon(c);
        }
        Crayon c = new Crayon();
        c.setId(11L);
        c.setColor("Blue");
        dao.addCrayon(c);
    }

    @When("Someone click delete button under the $id crayon")
    public void someoneRemovesCrayon(Long id){
        Crayon c = new Crayon();
        c.setId(id);
        c.setColor("Red");
        dao.deleteCrayon(c);
    }

    @Then("Then  crayon has been successfully deleted")
    public void someoneDeletedCrayon(){
        Assert.assertEquals(9, dao.getAllCrayons().size());
    }
}
