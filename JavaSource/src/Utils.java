import java.math.BigInteger;
import java.util.ArrayList;


public class Utils
{   private BigInteger p = new BigInteger("11");
    public  ArrayList<BigInteger>computePartialPolynom(ArrayList<BigInteger>solutions)
    {
        int grade = solutions.size();

        ArrayList<BigInteger> initial = new ArrayList<BigInteger>();
        initial.add(BigInteger.valueOf(1));
        initial.add(solutions.get(0));

        for(int i=1;i<grade;i++)
        {
            ArrayList<BigInteger> currentPolynom = new ArrayList<BigInteger>();
            currentPolynom.add(BigInteger.valueOf(1));
            currentPolynom.add(solutions.get(i));
            initial = Utils.multiplyPolinoms(initial,currentPolynom, p);
        }

        return initial;
    }
    //  polinoame date prin coeficienti pozitivi de la primul la ultimul element din lista scade  gradul polinomului ,=> ultimul coeficient este termenul liber
    public static ArrayList<BigInteger>multiplyPolinoms(ArrayList<BigInteger> first, ArrayList<BigInteger> second, BigInteger p)
    {
        ArrayList<BigInteger> result = new ArrayList<BigInteger>();
        int gradeResult = first.size() - 1 + second.size() - 1;
        int gradeFirst = first.size()-1;
        int gradeSecond = second.size()-1;

        for(int i = 0;i<=gradeResult;i++)
            result.add(BigInteger.valueOf(0));

        for(int i=0;i<first.size();i++)
        {
            BigInteger currentCoef = first.get(i);
            gradeSecond = second.size()-1;

            for(int j=0;j<second.size();j++)
            {
                BigInteger resultedCoef = currentCoef.multiply(second.get(j)).mod(p);
                int gradeCoef =  gradeFirst + gradeSecond;
                result.set(gradeResult - gradeCoef, result.get(gradeResult-gradeCoef).add(resultedCoef).mod(p));
                gradeSecond -- ;
            }

            gradeFirst -- ;
        }
        return result;
    }
}
