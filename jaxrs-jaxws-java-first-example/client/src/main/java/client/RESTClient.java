/**
 * Copyright (C) 2010 Talend Inc. - www.talend.com
 */
package client;

import java.util.Map;
import java.util.Properties;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.soap.SOAPBinding;

import org.apache.cxf.jaxrs.client.JAXRSClientFactory;

import common.codefirst.IMath;

//import common.codefirst.HelloWorld;
//import common.codefirst.User;
//import common.codefirst.UserImpl;

/**
 * Example showing JAX-RS and JAX-WS proxies making calls to JAX-RS and JAX-WS
 * services by relying on the same shared code making remote invocations.
 */
public final class RESTClient {

	private static final String PORT_PROPERTY = "http.port";
	private static final int DEFAULT_PORT_VALUE = 8000;

	private static final String HTTP_PORT;
	static {
		Properties props = new Properties();
		try {
			props.load(RESTClient.class
					.getResourceAsStream("/client.properties"));
		} catch (Exception ex) {
			throw new RuntimeException(
					"client.properties resource is not available");
		}
		HTTP_PORT = props.getProperty(PORT_PROPERTY);
	}

	int port;

	public RESTClient() {
		this(getPort());
	}

	public RESTClient(int port) {
		this.port = port;
	}

	/*
	 * public void sayHelloRest() throws Exception { final String address =
	 * "http://localhost:" + port + "/services/rest-hello";
	 * 
	 * System.out.println("Using CXF JAX-RS proxy to invoke on HelloWorld service"
	 * );
	 * 
	 * //HelloWorld service = JAXRSClientFactory.create(address,
	 * HelloWorld.class); //useHelloService(service, "Barry"); }
	 */
	public void sayHelloSoap() throws Exception {
		final QName serviceName = new QName("www.ksu.edu.sa/math",
				"MathService");
		final QName portName = new QName("www.ksu.edu.sa/math",
				"MathServicePort");
		final String address = "http://localhost:" + port + "/services/iMathSOAP";

		//System.out
				//.println("Using JAX-WS proxy to invoke on HelloWorld service");

		Service service = Service.create(serviceName);
		service.addPort(portName, SOAPBinding.SOAP11HTTP_BINDING, address);
		IMath mathService = service.getPort(IMath.class);
		System.out.println("The SUM is :" + mathService.add(7, 34));
		// HelloWorld hw = service.getPort(HelloWorld.class);

		// useHelloService(hw, "Fred");
	}

	/*
	 * private void useHelloService(HelloWorld service, String user) {
	 * 
	 * System.out.println("Getting the list of existing users");
	 * printUsers(service.getUsers());
	 * 
	 * System.out.println("Asking the service to add a new user " + user +
	 * " and also say hi");
	 * 
	 * System.out.println(service.sayHi(user));
	 * System.out.println(service.sayHiToUser(new UserImpl(user)));
	 * 
	 * System.out.println("Getting the list of existing users"); Map<Integer,
	 * User> users = service.getUsers(); printUsers(users);
	 * 
	 * System.out.println("Echoing the list of existing users");
	 * printUsers(service.echoUsers(users));
	 * 
	 * }
	 */

	public static void main(String[] args) throws Exception {

		RESTClient client = new RESTClient();

		// uses CXF JAX-RS Proxy
		// client.sayHelloRest();

		System.out.println();

		// uses JAX-WS Client
		client.sayHelloSoap();
	}

	private static int getPort() {
		try {
			return Integer.valueOf(HTTP_PORT);
		} catch (NumberFormatException ex) {
			// ignore
		}
		return DEFAULT_PORT_VALUE;
	}
}
