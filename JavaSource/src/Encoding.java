import java.math.BigInteger;

/**
 * Created by stfcr on 2/27/2017.
 */
public class Encoding {
    private BigInteger []y;
    private BigInteger []a;
    private BigInteger m,p,k,n,s;
    public Encoding(BigInteger m,BigInteger p,BigInteger k,BigInteger n,BigInteger s){
        this.m=m;
        this.p=p;
        this.k=k;
        this.n=n;
        this.s=s;
    }
    private void setA(){
        a=m.divideAndRemainder(p);
        while(a[0].compareTo(p)==1)
            shifToLeftAndAddNew(p);
        for(int i=0;i<a.length;i++)
            System.out.println(a[i]);
    }

    public void setY(){
        setA();
        y=new BigInteger[n.intValue()];
        BigInteger iterator=new BigInteger("0");
        while(iterator.compareTo(n)==-1){
            y[iterator.intValue()]=calculateP(iterator.add(new BigInteger("1")));
            iterator=iterator.add(new BigInteger("1"));
        }
        for (int i=0;i<y.length;i++)
            System.out.println(y[i]);
    }

    private BigInteger calculateP(BigInteger x){
        BigInteger rez=new BigInteger("0");
        for(int i=0;i<k.intValue()-1;i++){
            rez=rez.add(a[i].multiply(x.pow(k.intValue()-1-i)));
        }
        return rez.divideAndRemainder(p)[1];
    }

    public void shifToLeftAndAddNew(BigInteger p){
        BigInteger [] rest=new BigInteger[a.length];
        for(int i=0;i<a.length;i++)
            rest[i]=a[i];
        a=new BigInteger[rest.length+1];
        BigInteger []impartire2=rest[0].divideAndRemainder(p);
        a[0]=impartire2[0];
        a[1]=impartire2[1];
        for(int i=1;i<rest.length;i++)
            a[i+1]=rest[i];

    }

}
