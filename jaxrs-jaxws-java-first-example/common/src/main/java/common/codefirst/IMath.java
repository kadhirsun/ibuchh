package common.codefirst;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@WebService(targetNamespace = "www.ksu.edu.sa/math")
@Path("/")
public interface IMath {
	@WebMethod(operationName = "add")
	@Produces("text/plain")
	@Consumes("text/plain")
	@Path("/add")
	public double add(@WebParam(name="number1") double num1, @WebParam (name="number2")double num2);
}
