package service.codefirst;

import javax.jws.WebService;

import common.codefirst.IMath;

@WebService(endpointInterface = "common.codefirst.IMath", serviceName = "MathService", portName = "MathServicePort", targetNamespace = "www.ksu.edu.sa/math")
public class MathImpl implements IMath {

	@Override
	public double add(double num1, double num2) {
		// TODO Auto-generated method stub
		return num1 + num2;
	}

}
