import javafx.util.converter.BigIntegerStringConverter;

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
    private BigInteger [] yReconstructed;
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
        z[1] = BigInteger.probablePrime(256, rand);
        prettyPrint(z);
        bkt(z, k.intValue(), 0, new BigInteger[k.intValue()]);
        prettyPrint(yReconstructed);
    }

    public void bkt(BigInteger[] Z, int len, int startPosition, BigInteger[] result) {
        if (len == 0) {
            BigInteger [] A;
            BigInteger [] B;
            A=obtainA(result);
            B=A;
            if(fc0(result,A).intValue()==0)
            {   yReconstructed=new BigInteger[5];
                int l=0;
                BigInteger x=new BigInteger("1");
                while (x.compareTo((k.add(new BigInteger("3"))))==-1) {
                    yReconstructed[l]=computePx(result,A,x);
                    x=x.add(new BigInteger("1"));
                    l++;
                }
            }


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
        BigInteger numaratorMare=new BigInteger("0");
        BigInteger numitorMare=new BigInteger("1");
        BigInteger [] numaratori=new BigInteger[k.intValue()];
        BigInteger [] numitori=new BigInteger[k.intValue()];
        for(int i=0;i<k.intValue();i++){
            numaratori[i]=new BigInteger("1");
            numitori[i]=new BigInteger("1");
        }
        for(int i=0;i<k.intValue();i++) {
            for (int j = 0; j < k.intValue(); j++) {
                if(i!=j) {
                    BigInteger J = A[j];
                    BigInteger JminusI = A[j].subtract(A[i]);
                    if (JminusI.compareTo(new BigInteger("0"))==-1)
                        JminusI=invers(JminusI.abs());
                    numaratori[i] = numaratori[i].multiply(J);
                    numitori[i] = numitori[i].multiply(JminusI);
                    if(numaratori[i].compareTo(p)==1 || numaratori[i].compareTo(p)==0)
                        numaratori[i]=numaratori[i].divideAndRemainder(p)[1];
                    if(numitori[i].compareTo(p)==1 || numitori[i].compareTo(p)==0)
                        numitori[i]=numitori[i].divideAndRemainder(p)[1];
                }
            }
        }
        BigInteger []numDiferit=new BigInteger[k.intValue()];
        for(int i=0;i<k.intValue();i++)
            numDiferit[i]=new BigInteger("1");
        for(int i=0;i<k.intValue();i++) {
            for (int j = 0; j < k.intValue(); j++) {
                if (j != i)
                    numDiferit[i] = numDiferit[i].multiply(numitori[j]);
            }
            numaratorMare = numaratorMare.add(vector[i].multiply(numDiferit[i]).multiply(numaratori[i]));
        }
        for(int i=0;i<k.intValue();i++) {
            numitorMare = numitorMare.multiply((numitori[i]));
        }

        numitorMare=numitorMare.divideAndRemainder(p)[1];
        numaratorMare=numaratorMare.divideAndRemainder(p)[1];
        if(numitorMare.compareTo(new BigInteger("0"))==0 || numaratorMare.compareTo(new BigInteger("0"))==0)
            return new BigInteger("0");
        return numaratorMare.multiply((numitorMare.modInverse(p)));
    }

    private BigInteger computePx(BigInteger[] vector,BigInteger []A,BigInteger x){
        BigInteger numaratorMare=new BigInteger("0");
        BigInteger numitorMare=new BigInteger("1");
        BigInteger [] numaratori=new BigInteger[k.intValue()];
        BigInteger [] numitori=new BigInteger[k.intValue()];
        for(int i=0;i<k.intValue();i++){
            numaratori[i]=new BigInteger("1");
            numitori[i]=new BigInteger("1");
        }
        for(int i=0;i<k.intValue();i++) {
            for (int j = 0; j < k.intValue(); j++) {
                if(i!=j) {
                    BigInteger J =x.subtract(A[j]);
                    BigInteger IminusJ = A[i].subtract(A[j]);
                    if(J.compareTo(new BigInteger("0"))==-1)
                        J=invers(J.abs());
                    if (IminusJ.compareTo(new BigInteger("0"))==-1)
                        IminusJ=invers(IminusJ.abs());
                    numaratori[i] = numaratori[i].multiply(J);
                    numitori[i] = numitori[i].multiply(IminusJ);
                    if(numaratori[i].compareTo(p)==1 || numaratori[i].compareTo(p)==0)
                        numaratori[i]=numaratori[i].divideAndRemainder(p)[1];
                    if(numitori[i].compareTo(p)==1 || numitori[i].compareTo(p)==0)
                        numitori[i]=numitori[i].divideAndRemainder(p)[1];
                }
            }
        }
        BigInteger []numDiferit=new BigInteger[k.intValue()];
        for(int i=0;i<k.intValue();i++)
            numDiferit[i]=new BigInteger("1");
        for(int i=0;i<k.intValue();i++) {
            for (int j = 0; j < k.intValue(); j++) {
                if (j != i)
                    numDiferit[i] = numDiferit[i].multiply(numitori[j]);
            }
            numaratorMare = numaratorMare.add(vector[i].multiply(numDiferit[i]).multiply(numaratori[i]));
        }
        for(int i=0;i<k.intValue();i++) {
            numitorMare = numitorMare.multiply((numitori[i]));
        }
        numitorMare=numitorMare.divideAndRemainder(p)[1];
        numaratorMare=numaratorMare.divideAndRemainder(p)[1];
        if(numitorMare.compareTo(new BigInteger("0"))==0 || numaratorMare.compareTo(new BigInteger("0"))==0)
            return new BigInteger("0");
        return (numaratorMare.multiply((numitorMare.modInverse(p)))).divideAndRemainder(p)[1];
    }

    private BigInteger invers(BigInteger x){
        return p.subtract(x);
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
