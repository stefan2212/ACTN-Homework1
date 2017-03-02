import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Random;
import java.util.StringJoiner;

/**
 * Created by stfcr on 2/28/2017.
 */
public class Decoding {
    private BigInteger p;
    private BigInteger k;
    private BigInteger[] y;
    private BigInteger[] z;
    private String filename;

    public Decoding(String s) {
        this.filename = s;
    }

    public void readFromTheFile() {
        try {
            BufferedReader rd = new BufferedReader(new FileReader("vectorY.txt"));
            p = new BigInteger(rd.readLine());
            k = new BigInteger(rd.readLine());
            String ys = rd.readLine();
            String[] arr = ys.split(" ");
            y = new BigInteger[arr.length];
            for (int i = 0; i < arr.length; i++)
                y[i] = new BigInteger(arr[i]);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void pickZ() {
        z = y;
        Random rand = new Random();
        z[1] = BigInteger.probablePrime(3, rand);
        prettyPrint(z);
        bkt(z, k.intValue(), 0, new BigInteger[k.intValue()]);
    }

    public void bkt(BigInteger[] Z, int len, int startPosition, BigInteger[] result) {
        if (len == 0) {
            BigInteger [] A=new BigInteger[k.intValue()];
            A=obtainA(result);
            fc0(result,A);
        } else
            for (int i = startPosition; i <= Z.length - len; i++) {
                result[result.length - len] = Z[i];
                bkt(Z, len - 1, i + 1, result);
            }
    }

    public void prettyPrint(BigInteger[] aray) {
        for (int i = 0; i < aray.length; i++)
            System.out.printf("%d ", aray[i]);
        System.out.println();
    }

    private BigInteger fc0(BigInteger[] vector,BigInteger []A) {
        BigInteger suma=new BigInteger("0");
        BigInteger numaratorMare=new BigInteger("0");
        BigInteger numitorMare=new BigInteger("1");
        BigInteger [] numaratori=new BigInteger[k.intValue()];
        BigInteger [] numitori=new BigInteger[k.intValue()];
        for(int i=0;i<k.intValue();i++){
            numaratori[i]=new BigInteger("1");
            numitori[i]=new BigInteger("1");
        }
        for(int i=0;i<k.intValue();i++) {
            BigInteger produs=new BigInteger("1");
            for (int j = 0; j < k.intValue(); j++) {
                if(i!=j) {
                    BigInteger J = A[j];
                    BigInteger JminusI = A[j].subtract(A[i]);
                    numaratori[i] = numaratori[i].multiply(J);
                    numitori[i] = numitori[i].multiply(JminusI);
                }
            }
            BigInteger numDiferit=new BigInteger("1");
            for(int j=0;j<k.intValue();j++)
            {
                if(j!=i)
                numDiferit=numDiferit.multiply(numitori[j]);
            }
        numaratorMare=numaratorMare.add(z[A[i].intValue()-1].multiply(numDiferit));
        }
        for(int i=0;i<k.intValue();i++)
            if(numitori[i].compareTo(new BigInteger("0"))==1) {
                numitori[i]=invers(numitori[i]);
                numitorMare = numitorMare.multiply((numitori[i]));
            }
        numitorMare=numitorMare.divideAndRemainder(p)[1];
        return numaratorMare.multiply(numitorMare.modInverse(p));
    }

    private BigInteger invers(BigInteger x){
        BigInteger it=new BigInteger("1");
        while(it.compareTo(p)==-1)
        {
            BigInteger sum=new BigInteger("0");
            sum=x.add(it);
            if (sum.compareTo(p)==0)
                return sum;
            it=it.add(new BigInteger("1"));
        }
        return new BigInteger("0");
    }

    private BigInteger[] obtainA(BigInteger[] vector) {
        int l = 0;
        BigInteger[] result = new BigInteger[vector.length];
        for (int i = 0; i < z.length; i++)
            for (int j = 0; j < vector.length; j++) {
                if (z[i].compareTo(vector[j]) == 0 && l<vector.length) {
                    result[l] = new BigInteger(String.valueOf(i + 1));
                    l++;
                }
            }
        return result;
    }

}
