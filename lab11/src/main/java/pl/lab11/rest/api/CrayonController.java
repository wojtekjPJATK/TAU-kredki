package pl.lab11.rest.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.lab11.rest.domain.Crayon;
import pl.lab11.rest.service.CrayonFactory;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

@RestController
public class CrayonController {

    @Autowired
    CrayonFactory crayonFactory;

    @RequestMapping("/crayons")
    public java.util.List<Crayon> getCrayons() {
        List<Crayon> crayons = new LinkedList<>();
        for (Crayon c : crayonFactory.findAllCrayons()) {
            crayons.add(c.myClone());
        }
        return crayons;
    }
    
    @RequestMapping(value = "/crayons",method = RequestMethod.POST)
    public Crayon addCrayon(@RequestBody Crayon ncrayon) {
        ncrayon.setId(crayonFactory.addCrayon(ncrayon));
        return ncrayon;
    }

    @RequestMapping("/")
    public String index() {
        return "Hello world!";
    }

    @RequestMapping(value = "/crayons/{color}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Crayon getCrayon(@PathVariable("color") String color) throws SQLException {
        return crayonFactory.findCrayonsByColor(color).get(0);
    }

    @RequestMapping(value = "/crayons", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Crayon> getCrayons(@RequestParam(value = "filter", required = false) String f) throws SQLException {
        List<Crayon> crayons = new LinkedList<Crayon>();
        for (Crayon c : crayonFactory.findAllCrayons()) {
            if (f == null) {
                crayons.add(c.myClone());
            } else if (c.getColor().contains(f)) {
                crayons.add(c.myClone());
            }
        }
        return crayons;
    }

    @RequestMapping(value = "/crayons/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public String deleteCrayon(@PathVariable("color") String color) throws SQLException {
        crayonFactory.deleteCrayon(crayonFactory.findCrayonsByColor(color).get(0));
        return "OK";
    }

}
