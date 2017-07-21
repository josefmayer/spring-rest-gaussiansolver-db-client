package gaussiansolver.data;

import org.springframework.context.annotation.Configuration;

/**
 * Created by Josef Mayer on 20.06.2017.
 */

@Configuration
public class InData {

    public InData indat(){
        return new InData();
    }

    private double[] a;
    private double[] b;

    public double[] getA() {
        return a;
    }

    public double[] getB() {
        return b;
    }


}
