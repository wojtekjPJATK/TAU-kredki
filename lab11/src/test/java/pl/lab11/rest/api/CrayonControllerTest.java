package pl.lab11.rest.api;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import pl.lab11.rest.domain.Crayon;
import pl.lab11.rest.service.CrayonFactory;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CrayonControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CrayonFactory service;

    @Test
    public void contextLoads() throws Exception {
        assertNotNull(mockMvc);
    }

    @Test
    public void greetingShouldReturnHelloMessage() throws Exception {
        this.mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Hello")));
    }

    @Test
    public void getAllShouldReturnEmptyResults() throws Exception {
        when(service.findAllCrayons()).thenReturn(new LinkedList<Crayon>());
        this.mockMvc.perform(get("/crayons"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    public void getAllShouldReturnSomeResults() throws Exception {
        List<Crayon> expectedResult = new LinkedList<Crayon>();
        Crayon c = new Crayon();
        c.setColor("Red");
        c.setId(1l);
        expectedResult.add(c);
        when(service.findAllCrayons()).thenReturn(expectedResult);
        this.mockMvc.perform(get("/crayons"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":1,\"color\":\"Red\"}]"));
    }

    @Test
    public void postNewCrayonTest() throws Exception {
        Crayon c = new Crayon();
        c.setColor("Red");
        when(service.addCrayon(c)).thenReturn(2l);
        this.mockMvc.perform(post("/crayons")
                    .content("{\"color\":\"Red\"}")
                    .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Red")))
                .andExpect(content().string(containsString("\"id\":2")));
        c.setId(2l);
        verify(service).addCrayon(c);
    }

    @Test
    public void deleteCrayonTest() throws Exception {
        Crayon c = new Crayon();
        c.setColor("Red");
        service.addCrayon(c);
        LinkedList<Crayon> crayons = new LinkedList<Crayon>();
        crayons.add(c);
        when(service.findCrayonsByColor("Red")).thenReturn(crayons);
        when(service.deleteCrayon(c)).thenReturn(true);
        this.mockMvc.perform(delete("/crayons/Red")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("OK")));
    }

    @Test
    public void putCrayonTest() throws Exception {
        Crayon c = new Crayon();
        c.setColor("Red");
        Crayon nc = new Crayon();
        nc.setColor("Blue");
        service.addCrayon(c);
        LinkedList<Crayon> crayons = new LinkedList<Crayon>();
        crayons.add(c);
        when(service.findCrayonsByColor("Red")).thenReturn(crayons);
        this.mockMvc.perform(put("/crayons/Red")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"color\":\"Blue\"}")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("Blue")));
    }

    @Test
    public void getCrayonByColorTest() throws Exception {
        Crayon c = new Crayon();
        c.setColor("Red");
        service.addCrayon(c);
        LinkedList<Crayon> crayons = new LinkedList<Crayon>();
        crayons.add(c);
        when(service.findCrayonsByColor("Red")).thenReturn(crayons);
        this.mockMvc.perform(get("/crayons/Red"))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("Red")));
    }
}