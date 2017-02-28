import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Random;

/**
 * Created by stfcr on 2/28/2017.
 */
public class Decoding {
    private BigInteger p;
    private BigInteger []y;
    private BigInteger []z;
    private String filename;
    public Decoding(String s){
        this.filename=s;
    }
    public void readFromTheFile(){
        try {
            BufferedReader rd=new BufferedReader(new FileReader("vectorY.txt"));
            p=new BigInteger(rd.readLine());
            String ys=rd.readLine();
            String []arr=ys.split(" ");
            y=new BigInteger[arr.length];
            for(int i=0;i<arr.length;i++)
                y[i]=new BigInteger(arr[i]);
            for(int i=0;i<y.length;i++)
                System.out.println(y[i]);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void pickZ(){
        z=y;
        Random rand=new Random();
        z[1]=BigInteger.probablePrime(3,rand);
    }
}
