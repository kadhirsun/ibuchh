package net.hcl.ws;

import javax.jws.WebMethod;
import javax.jws.WebService;


@WebService
public class OrderSystemImpl implements OrderSystem {

	@WebMethod
	public String createOrder(Order o) {
		// TODO Auto-generated method stub
		String status = "Order has been created";
		return status;
	}

	@WebMethod
	public String updateOrder(Order o) {
		// TODO Auto-generated method stub
		String status = "Order has been updated";
		return status;
		
	}

	@WebMethod
	public String cancelOrder(int oId) {
		// TODO Auto-generated method stub
		String status = "Order has been cancelled";
		return status;
	}

}
