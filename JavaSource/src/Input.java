import java.math.BigInteger;
import java.util.Random;

/**
 * Created by stfcr on 2/27/2017.
 */
public class Input {
    private BigInteger k;
    private BigInteger m;
    private BigInteger p;
    private BigInteger n;
    private BigInteger s;
    public Input(BigInteger m){
        Random rand=new Random();
        this.m=m;
        k=new BigInteger("3");
        s=new BigInteger("1");
        n=new BigInteger((String.valueOf(k.add(s.multiply(new BigInteger("2"))))));
        p=BigInteger.probablePrime(4,rand);
        p=new BigInteger("11");
        Encoding encode=new Encoding(m,p,k,n,s);
        encode.setY();
    }
}
