package com.company.recordstoreapi.Controller;

import com.company.recordstoreapi.model.Record;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(RecordStoreController.class)
public class RecordStoreControllerTest {
    //wiring to MockMvc object
    @Autowired
    private MockMvc mockMvc;

    // objectMapper used to convert to Java Objects to JSON and vice versa
    private ObjectMapper mapper = new ObjectMapper();

    //a
    private List<Record> recordList;

    @Before
    public void setUp(){

    }
    // testing http get method uri/records
    @Test
    public void shouldReturnAllRecordsInCollection() throws Exception {

        // ARRANGE
        // Convert Java object to JSON
        // THIS IS WHAT IS EXPECTED 
        String outputJson = mapper.writeValueAsString(recordList);

        // ACT
        mockMvc.perform(get("/records"))                // Perform the GET request
                .andDo(print())                          // Print results to console
                .andExpect(status().isOk());              // ASSERT (status code is 200)
    }
    // Testing POST /records
    @Test
    public void shouldReturnNewRecordOnPostRequest() throws Exception {

        // ARRANGE
        Record inputRecord = new Record();
        inputRecord.setArtist("Bruce Springsteen");
        inputRecord.setAlbum("The River");
        inputRecord.setYear("1990");

        // Convert Java Object to JSON
        String inputJson = mapper.writeValueAsString(inputRecord);

        Record outputRecord = new Record();
        outputRecord.setArtist("Bruce Springsteen");
        outputRecord.setAlbum("The River");
        outputRecord.setYear("1990");
        outputRecord.setId(6);

        String outputJson = mapper.writeValueAsString(outputRecord);

        // ACT
        mockMvc.perform(
                        post("/records")                            // Perform the POST request
                                .content(inputJson)                       // Set the request body
                                .contentType(MediaType.APPLICATION_JSON)  // Tell the server it's in JSON format
                )
                .andDo(print())                                // Print results to console
                .andExpect(status().isCreated());              // ASSERT (status code is 201)
    }
    // Testing GET record/{id}
    @Test
    public void shouldReturnRecordById() throws Exception {

        Record outputRecord = new Record();
        outputRecord.setArtist("Billy Joel");
        outputRecord.setAlbum("The Stranger");
        outputRecord.setYear("1977");
        outputRecord.setId(2);

        String outputJson = mapper.writeValueAsString(outputRecord);

        mockMvc.perform(get("/records/2"))
                .andDo(print())
                .andExpect(status().isOk());
                //.andExpect(content().json(outputJson));
    }
    // Testing PUT /records/{id}
    @Test
    public void shouldUpdateByIdAndReturn204StatusCode() throws Exception {
        // This method returns nothing, so we're just checking for correct status code
        // In this case, code 204, which indicates No Content
        // Arrange
        Record inputRecord = new Record();
        inputRecord.setArtist("William Joel");
        inputRecord.setAlbum("The Stranger");
        inputRecord.setYear("1977");
        inputRecord.setId(2);
        // controller is expecting JSON to come in
        String inputJson = mapper.writeValueAsString(inputRecord);

        mockMvc.perform(
                        put("/records/2")
                                .content(inputJson)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}