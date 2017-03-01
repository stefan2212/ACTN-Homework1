import java.math.BigInteger;

/**
 * Created by stfcr on 2/27/2017.
 */
        public class Main {
            public static void main(String[] args) {
                Input input=new Input(new BigInteger("29"));
                Decoding out=new Decoding("vectorY.txt");
                out.readFromTheFile();
                out.pickZ();
    }
}
