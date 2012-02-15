package soa.com.oracle;

import oracle.soa.platform.component.spring.beans.ILoggerBean;

public class MathImpl implements IMath {
    private ILoggerBean loggerHelper;

    public double add(double num1, double num2) {
        loggerHelper.log("++++Adding "+num1+" and "+"num2"+" +++++");
        return num1 + num2;
    }

    public double subtract(double num1, double num2) {
        loggerHelper.log("++++Subtracting "+num1+" and "+"num2"+" +++++");
        return num1 - num2;
    }

    public double multiply(double num1, double num2) {
        loggerHelper.log("++++Multiplying "+num1+" and "+"num2"+" +++++");
        return num1 * num2;
    }

    public double divide(double num1, double num2) {
        loggerHelper.log("++++Dividing "+num1+" and "+"num2"+" +++++");
        return num1 / num2;
    }

    public void setLoggerHelper(ILoggerBean loggerHelper) {
        this.loggerHelper = loggerHelper;
    }

    public ILoggerBean getLoggerHelper() {
        return loggerHelper;
    }
}
