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
    private BigInteger k;
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
            k=new BigInteger(rd.readLine());
            String ys=rd.readLine();
            String []arr=ys.split(" ");
            y=new BigInteger[arr.length];
            for(int i=0;i<arr.length;i++)
                y[i]=new BigInteger(arr[i]);
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
        prettyPrint(z);
        bkt(z,k.intValue(),0,new BigInteger[k.intValue()]);
    }

    public void bkt(BigInteger [] Z,int len,int startPosition,BigInteger []result)
    {
        if(len==0){
            prettyPrint(result);
        }
        else
            for(int i=startPosition;i<=Z.length-len;i++)
            {
                result[result.length-len]=Z[i];
                bkt(Z,len-1,i+1,result);
            }
    }

    public void prettyPrint(BigInteger []aray){
        for(int i=0;i<aray.length;i++)
            System.out.printf("%d ",aray[i]);
        System.out.println();
    }

    public BigInteger fc0(){
        return new BigInteger("0");
    }

}
