package net.hcl.ws;

import javax.jws.WebService;
import javax.jws.WebMethod;

@WebService
public interface OrderSystem {
	@WebMethod 
	public String createOrder(Order o);
	
	@WebMethod 
	public String updateOrder(Order o);
	
	@WebMethod 
	public String cancelOrder(int oId);
	

}
