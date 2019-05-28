package pl.lab11.rest.api;

import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.lab11.rest.domain.Crayon;
import pl.lab11.rest.domain.Person;
import pl.lab11.rest.service.CrayonFactory;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import javax.xml.ws.http.HTTPException;

@RestController
public class CrayonController {

    @Autowired
    CrayonFactory crayonFactory;

    @RequestMapping("/crayons/{}/{}")
    public java.util.List<Crayon> getCrayons() {
        List<Crayon> crayons = new LinkedList<>();
        for (Crayon c : crayonFactory.findAllCrayons(0, 2)) {
            crayons.add(c.myClone());
        }
        return crayons;
    }

    @RequestMapping("/crayons/{start}/{max}")
    public java.util.List<Crayon> getCrayons(@PathVariable("start") int start, @PathVariable("max") int max) {
        List<Crayon> crayons = new LinkedList<>();
        for (Crayon c : crayonFactory.findAllCrayons(start, max)) {
            crayons.add(c.myClone());
        }
        return crayons;
    }
    
    @RequestMapping(value = "/crayons",method = RequestMethod.POST)
    public Crayon addCrayon(@RequestBody Crayon ncrayon) {
        ncrayon.setId(crayonFactory.addCrayon(ncrayon));
        return ncrayon;
    }

    @RequestMapping(value = "/crayons/{color}",method = RequestMethod.PUT, produces=MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Crayon updateCrayon(@RequestBody Crayon ncrayon, @PathVariable("color") String color) throws NotFoundException{
        Crayon c = crayonFactory.findCrayonsByColor(color).get(0);
        if (c == null) throw new NotFoundException();
        c.setColor(ncrayon.getColor());
        crayonFactory.updateCrayon(c);
        return c;
    }

    @RequestMapping("/")
    public String index() {
        return "Hello world!";
    }

    @RequestMapping(value = "/crayons/{color}", produces = MediaType.APPLICATION_JSON_VALUE, method=RequestMethod.GET)
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

    @RequestMapping(value = "/crayons/{color}", method = RequestMethod.DELETE)
    @ResponseBody
    public String deleteCrayon(@PathVariable("color") String color) throws SQLException {
        crayonFactory.deleteCrayon(crayonFactory.findCrayonsByColor(color).get(0));
        return "OK";
    }

    @RequestMapping(value = "/crayons/byworker/{creator}", method = RequestMethod.GET)
    @ResponseBody
    public List<Crayon> getCrayonsByCreator(@PathVariable("creator") String creator) throws SQLException {
        Person p = crayonFactory.findWorker(creator);
        List<Crayon> crayons = crayonFactory.findCrayonsByCreator(p);
        return crayons;
    }

    @RequestMapping(value = "/crayons/{color}/{creator}", method = RequestMethod.GET)
    @ResponseBody
    public Crayon transferCrayon(@PathVariable("creator") String creator, @PathVariable("color") String color) throws SQLException {
        Person p = crayonFactory.findWorker(creator);
        Crayon c = crayonFactory.findCrayonsByColor(color).get(0);
        crayonFactory.transferCrayonToAnotherCreator(c, p);
        return c;
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Crayon getCrayonByID(@PathVariable("id") String id) throws SQLException {
        List<Crayon> crayons = crayonFactory.findAllCrayons();
        for(Crayon c: crayons) {
            if(c.getId() == Long.parseLong(id)) return c;
        }
        throw new HTTPException(404);
    }

}
