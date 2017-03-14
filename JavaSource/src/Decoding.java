import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by stfcr on 2/28/2017.
 */
public class Decoding {
    private BigInteger p;
    private BigInteger k;
    private BigInteger[] y;
    private BigInteger[] z;
    private BigInteger[] yReconstructed;
    private String filename;
    private Utils u;
    private int stopRecursive=0;

    public Decoding(String s) {
        this.filename = s;
        u = new Utils();
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
    }

    public void bkt(BigInteger[] Z, int len, int startPosition, BigInteger[] result) {
        if (len == 0 && stopRecursive==0) {
            BigInteger[] A;
            BigInteger[] B;
            A = obtainA(result);
            B = A;
            if (fc0(result, A).intValue() == 0) {
                yReconstructed = new BigInteger[5];
                int l = 0;
                BigInteger x = new BigInteger("1");
                computePx(result, A, x);
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

    private BigInteger fc0(BigInteger[] vector, BigInteger[] A) {
        BigInteger numaratorMare = new BigInteger("0");
        BigInteger numitorMare = new BigInteger("1");
        BigInteger[] numaratori = new BigInteger[k.intValue()];
        BigInteger[] numitori = new BigInteger[k.intValue()];
        for (int i = 0; i < k.intValue(); i++) {
            numaratori[i] = new BigInteger("1");
            numitori[i] = new BigInteger("1");
        }
        for (int i = 0; i < k.intValue(); i++) {
            for (int j = 0; j < k.intValue(); j++) {
                if (i != j) {
                    BigInteger J = A[j];
                    BigInteger JminusI = A[j].subtract(A[i]);
                    if (JminusI.compareTo(new BigInteger("0")) == -1)
                        JminusI = invers(JminusI.abs());
                    numaratori[i] = numaratori[i].multiply(J);
                    numitori[i] = numitori[i].multiply(JminusI);
                    if (numaratori[i].compareTo(p) == 1 || numaratori[i].compareTo(p) == 0)
                        numaratori[i] = numaratori[i].divideAndRemainder(p)[1];
                    if (numitori[i].compareTo(p) == 1 || numitori[i].compareTo(p) == 0)
                        numitori[i] = numitori[i].divideAndRemainder(p)[1];
                }
            }
        }
        BigInteger[] numDiferit = new BigInteger[k.intValue()];
        for (int i = 0; i < k.intValue(); i++)
            numDiferit[i] = new BigInteger("1");
        for (int i = 0; i < k.intValue(); i++) {
            for (int j = 0; j < k.intValue(); j++) {
                if (j != i)
                    numDiferit[i] = numDiferit[i].multiply(numitori[j]);
            }
            numaratorMare = numaratorMare.add(vector[i].multiply(numDiferit[i]).multiply(numaratori[i]));
        }
        for (int i = 0; i < k.intValue(); i++) {
            numitorMare = numitorMare.multiply((numitori[i]));
        }

        numitorMare = numitorMare.divideAndRemainder(p)[1];
        numaratorMare = numaratorMare.divideAndRemainder(p)[1];
        if (numitorMare.compareTo(new BigInteger("0")) == 0 || numaratorMare.compareTo(new BigInteger("0")) == 0)
            return new BigInteger("0");
        return numaratorMare.multiply((numitorMare.modInverse(p)));
    }

    private BigInteger computePx(BigInteger[] vector, BigInteger[] A, BigInteger x) {
        ArrayList<ArrayList<BigInteger>> coeficientiToti = new ArrayList<ArrayList<BigInteger>>();
        ArrayList<BigInteger> numaratori = new ArrayList<BigInteger>();
        ArrayList<BigInteger> amplificat=new ArrayList<>();
        BigInteger numitori=new BigInteger("1");
        BigInteger [] numitoriDiferiti=new BigInteger[k.intValue()];
        for(int i=0;i<k.intValue();i++)
            numitoriDiferiti[i]=new BigInteger("1");
        BigInteger numDiferit=new BigInteger("1");
        for (int i = 0; i < k.intValue(); i++) {
            for (int j = 0; j < k.intValue(); j++) {
                if (i != j) {
                    BigInteger J = invers(A[j]);
                    BigInteger IminusJ = A[i].subtract(A[j]);
                    if(IminusJ.compareTo(new BigInteger("0"))==-1)
                        IminusJ=invers(IminusJ.abs());
                    numaratori.add(J);
                    numitori=(numitori.multiply(IminusJ)).mod(p);
                    numitoriDiferiti[i]=numitoriDiferiti[i].multiply(IminusJ);
                    if (numitoriDiferiti[i].compareTo(p) == 1 || numitoriDiferiti[i].compareTo(p) == 0)
                        numitoriDiferiti[i] = numitoriDiferiti[i].divideAndRemainder(p)[1];
                }
            }
            numaratori=u.computePartialPolynom(numaratori);
            for(int j=0;j<numaratori.size();j++)
            {
                numaratori.set(j,(numaratori.get(j).multiply(vector[i])).mod(p));
            }
            coeficientiToti.add(numaratori);
            numaratori=new ArrayList<BigInteger>();
        }
        for(int i=0;i<k.intValue();i++) {
            for (int j = 0; j < k.intValue(); j++)
                if (i != j)
                    numDiferit = numDiferit.multiply(numitoriDiferiti[j]);
            numDiferit=numDiferit.mod(p);
            amplificat.add(numDiferit);
            numDiferit=new BigInteger("1");
        }

        for(int i=0;i<k.intValue();i++)
            for(int j=0;j<k.intValue();j++)
                coeficientiToti.get(i).set(j,(coeficientiToti.get(i).get(j).multiply(amplificat.get(i))).mod(p));
        ArrayList<BigInteger>rezultat=new ArrayList<BigInteger>();
        for(int i=0;i<coeficientiToti.get(0).size();i++)
            rezultat.add(new BigInteger("0"));
        for (int i=0;i<coeficientiToti.size();i++)
            for(int j=0;j<coeficientiToti.get(i).size();j++)
            {
                rezultat.set(i,(rezultat.get(i).add(coeficientiToti.get(j).get(i))).mod(p));
            }
        numitori=numitori.modInverse(p);
        for(int i=0;i<rezultat.size();i++)
            rezultat.set(i,(rezultat.get(i).multiply(numitori)).mod(p));
        BigInteger reconstructie=new BigInteger("0");
        for(int i=0;i<k.intValue()-1;i++)
            reconstructie=reconstructie.add((rezultat.get(i).multiply((p.pow(k.intValue()-2-i)))));
        System.out.println(reconstructie);
        return new BigInteger("0");
    }

    private BigInteger invers(BigInteger x) {
        return p.subtract(x);
    }

    private BigInteger[] obtainA(BigInteger[] vector) {
        int l = 0;
        BigInteger[] result = new BigInteger[vector.length];
        for (int i = 0; i < z.length; i++)
            for (int j = 0; j < vector.length; j++) {
                if (z[i].compareTo(vector[j]) == 0 && l < vector.length) {
                    result[l] = new BigInteger(String.valueOf(i + 1));
                    l++;
                }
            }
        return result;
    }

}
