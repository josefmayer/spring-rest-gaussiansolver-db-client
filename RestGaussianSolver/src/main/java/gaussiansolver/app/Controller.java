package gaussiansolver.app;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import gaussiansolver.data.InData;
import gaussiansolver.data.Result;
import gaussiansolver.dbClients.MySQLClient_2;
import gaussiansolver.parallelGaussian.ParallelGaussianEliminationNet_Rest;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;


@RestController
class Controller {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @Autowired
    Result result;

    @Autowired
    private ParallelGaussianEliminationNet_Rest pg;

    @Autowired
    private MySQLClient_2 dbClient;


    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return new Greeting(counter.incrementAndGet(),
                            String.format(template, name));
    }

    @RequestMapping("/greeting2")
    public String greeting2(@RequestParam(value="name", defaultValue="World") String name) {
        //return new Greeting(counter.incrementAndGet(), String.format(template, name));
        return "greeting2";
    }

    //**********************************
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String get1(){

        StringBuffer sb = new StringBuffer();

        List<Object> resultList;
        resultList = dbClient.getAll();
        for (Object obj : resultList){
            sb.append(obj);
            sb.append("\n");
        }
        //return "app\n";
        return sb.toString();
    }

    @RequestMapping(value = "/", method = RequestMethod.POST, consumes="application/json", produces="application/json")
    public Result post1(@RequestBody InData inData){

        // Gaussian Calculation
        String[] args = new String[1];
        args[0] = "2";

        Result result = new Result();
        pg.execGaussianElimination_2(args, inData, result);

        //storeInDataBase(inData, result);

        return result;
    }

    private void storeInDataBase(InData inData, Result result){
        // Java to Json
        ObjectMapper mapper = new ObjectMapper();
        String jsonStringInData = "";
        try {
            jsonStringInData = mapper.writeValueAsString(inData);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        String jsonStringResult = "";
        try {
            jsonStringResult = mapper.writeValueAsString(result.getResult());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        dbClient.insert(jsonStringInData, jsonStringResult);
    }


}
