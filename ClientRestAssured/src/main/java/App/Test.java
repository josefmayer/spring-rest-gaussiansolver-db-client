package App;

import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;

/**
 * Created by Josef Mayer on 18.07.2017.
 */
public class Test {

    void testAll(){
        test1();
        test2();
        test3();
    }

    void test1(){
        when().
                get("/greeting").
        then().
                statusCode(200).
                body("id", equalTo(1)).
                body("content", equalTo("Hello, World!"));
    }

    void test2(){
        given().
                param("name", "Linda").
        when().
                get("/greeting").
        then().
                statusCode(200).
                body("id", equalTo(2)).
                body("content", equalTo("Hello, Linda!"));
    }

    void test3(){
        given().
                param("name", "Luke").
        when().
                get("/greeting").
        then().
                statusCode(200).
                body("id", equalTo(3)).
                body("content", equalTo("Hello, Luke!"));
    }

    void test4(){
        given().
                //param("name", "Luke").
                //param("inString","{\"a\":[2.0,4.0,5.0,-6.0],\"b\":[8.0,4.0]}");
                contentType("application/json").
                body("{\"a\":[2.0,4.0,5.0,-6.0],\"b\":[8.0,4.0]}").
        when().
                post("/").
        then().
                statusCode(200);
                //statusCode(200).
                //assertThat(ContentType.JSON);
                //body("result", equalTo("[2.0, 1.0]"));
                //body("content", equalTo("Hello, Luke!"));

    }

    void test7(){
        //given().
                //param("name", "Linda").
        when().
                get("/").
        then().
                statusCode(200);
        //body("result", equalTo("[1.0, 2.0]"));
        //body("content", equalTo("Hello, Linda!"));

    }


}
