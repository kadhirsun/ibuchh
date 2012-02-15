package soa.com.oracle;

public class MathImpl implements IMath {

    public double add(double n1, double n2) {
        return n1 + n2;
    }

    public double subtract(double n1, double n2) {
        return n1 - n2;
    }

    public double multiply(double n1, double n2) {
        return n1 * n2;
    }

    public double divide(double n1, double n2) {
        return n1 / n2;
    }
}
