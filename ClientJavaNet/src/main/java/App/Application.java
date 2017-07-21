package App;

/**
 * Created by Josef Mayer on 11.07.2017.
 */


import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
    public static void main (String [] args){

        NetClient netClient = new NetClient();

        String postData = "{\"a\":[2.0,4.0,5.0,-6.0],\"b\":[8.0,4.0]}";
        netClient.setPOSTConnection(postData);

        netClient.setGETConnection();

    }

}
