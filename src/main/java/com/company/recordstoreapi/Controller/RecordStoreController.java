package com.company.recordstoreapi.Controller;

import com.company.recordstoreapi.model.Record;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class RecordStoreController {
    private List<Record> recordList;

    private static int idCounter = 1;

    public RecordStoreController(){
        recordList= new ArrayList<>();
        // Pretending we have a database
        // idCounter++ says after utilize the current value bump up the value
        recordList.add(new Record("The Beach Boys", "Pet Sounds", "1968", idCounter++));
        recordList.add(new Record("Billy Joel", "The Stranger", "1977", idCounter++));
        recordList.add(new Record("The Beatles", "Revolver", "1964", idCounter++));
        recordList.add(new Record("Kanye West", "My Beautiful Dark Twisted Fantasy", "2008", idCounter++));
        recordList.add(new Record("Sturgill Simpson", "Metamodern Sounds in Country Music", "2010", idCounter++));
    }
    // THIS POST request takes in a JSON but RequestBody ANNOTATION handles the logic to convert JSON
    // to a Java object.
    @RequestMapping(value = "/records", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public Record createRecord(@RequestBody @Valid Record record) {

        record.setId(idCounter++);
        recordList.add(record);
        // RequestBody annotation allows the method returns this record in JSON
        return record;
    }
    //GET localhost:8080 / records
    @RequestMapping(value = "/records", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public List<Record> getAllRecords(@RequestParam(required=false) String artist, @RequestParam(required=false) String year){
        List<Record> returnList = new ArrayList<>();
        if(artist != null){
            for(Record record: recordList){
                if(record.getArtist().contains(artist)){
                    returnList.add(record);
                }
            }
        }
        else if(year != null){
            for(Record record: recordList){
                if(record.getYear().contains(year)){
                    returnList.add(record);
                }
            }
        }
        else{
            returnList = recordList;
        }


        return returnList;
    }
    @RequestMapping(value = "/records/{id}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public Record getRecordById(@PathVariable int id){
        Record foundRecord = null;
        for(Record record : recordList) {
            if(record.getId() == id) {
                foundRecord = record;
                break;
            }
        }

        return foundRecord;
    }
    //PUT localhost:8080 /records/{id}
    @RequestMapping(value = "/records/{id}", method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void updateRecordById(@PathVariable int id, @RequestBody Record record)
    {
        int index= -1;
        for(int i=0; i <recordList.size(); i++){
            if(recordList.get(i).getId() == id){
                index= i;
                break;
            }
        }
        if(index>=0)
        {
            recordList.set(index, record);
        }
    }
    //DELETE localhost:8080 /records/{id}
    @RequestMapping(value = "/records/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteRecordById(@PathVariable int id){
        int index= -1;
        for(int i=0; i <recordList.size(); i++){
            if(recordList.get(i).getId() == id){
                index= i;
                break;
            }
        }
        if(index>=0)
        {
            recordList.remove(index);
        }
    }
}
