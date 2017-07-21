package App;

/**
 * Created by Josef Mayer on 11.07.2017.
 */


import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
    public static void main (String [] args){

        //System.out.println("hello myModule");
        Test t = new Test();
        //t.testAll();
        t.test4();
        //t.test7();
    }

}
