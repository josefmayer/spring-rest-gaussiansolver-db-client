package gaussiansolver.data;

import org.springframework.context.annotation.Configuration;

/**
 * Created by Josef Mayer on 23.06.2017.
 */

@Configuration
public class Result {

    public Result res(){
        return new Result();
    }


    private double [] result;

    public double[] getResult() {
        return result;
    }

    public void setResult(double[] result) {
        this.result = result;
    }


}
