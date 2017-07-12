package com.engagetech.web.integration;

import com.engagetech.repository.ExpenseRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;

import static java.lang.String.format;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ExpenseControllerTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ExpenseRepository expenseRepository;

    private MockMvc mockMvc;

    @Before
    public void init(){
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void dtoShouldBeSavedAndFetched() throws Exception {

        //given
        ObjectMapper mapper = new ObjectMapper();

        String inputJson = mapper.writeValueAsString(
                ImmutableMap.builder()
                        .put("amount", "1200")
                        .put("reason", "consumerism")
                        .put("date", "11/12/2000")
                        .build());

        String resultJson = mapper.writeValueAsString(
                ImmutableMap.builder()
                        .put("entityId", 1)
                        .put("amount", BigDecimal.valueOf(1200).setScale(2))
                        .put("date", "11/12/2000")
                        .put("reason", "consumerism")
                        .put("vat", BigDecimal.valueOf(200).setScale(2))
                        .build());

        //when
        mockMvc.perform(post("/api/expenses")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(inputJson))
                .andExpect(status().isCreated());

        MvcResult mvcResult = mockMvc.perform(get("/api/expenses")
                                .accept(MediaType.APPLICATION_JSON_UTF8))
                                .andExpect(status().isOk())
                                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();

        //then
        assertThat(response, is(format("[%s]",resultJson)));
    }
}