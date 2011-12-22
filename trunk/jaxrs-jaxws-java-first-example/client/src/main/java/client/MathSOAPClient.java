package client;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import common.codefirst.IMath;

public class MathSOAPClient {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"client-beans.xml"});
		IMath clientIMath = (IMath)context.getBean("client");
		System.out.println("++++++++++++++++++++++++");
		System.out.println("The SUM is :"+clientIMath.add(9, 34));
		System.out.println("++++++++++++++++++++++++");
        
	}
}
