package co.com.univision.widgets.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.CoreMatchers.notNullValue;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class UnivisionWidgetsControllerTest {

    MockMvc mockMvc;

    @Autowired
    protected WebApplicationContext wac;

    @Autowired
    UnivisionWidgetsController univisionWidgetsController;

    @BeforeAll
    public static void setup() throws Exception {

    }

    @Test
    public void whenFetchWidgetsAsync() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(this.univisionWidgetsController).build();
        MvcResult result = mockMvc.perform(get("/univision/widgets").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(request().asyncStarted())
                .andDo(print())
                .andReturn();
        mockMvc.perform(asyncDispatch(result))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].contents",notNullValue()));

    }

    @Test
    public void whenFetchWidgetsNewsAsync() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(this.univisionWidgetsController).build();
        MvcResult result = mockMvc.perform(get("/univision/widgets?path=news").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(request().asyncStarted())
                .andDo(print())
                .andReturn();
        mockMvc.perform(asyncDispatch(result))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].contents",notNullValue()));

    }


    @Test
    public void whenFetchWidgetsWithBadPathAsync() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(this.univisionWidgetsController).build();
        MvcResult result = mockMvc.perform(get("/univision/widgets?path=wrong").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(request().asyncStarted())
                .andDo(print())
                .andReturn();
    }
}
