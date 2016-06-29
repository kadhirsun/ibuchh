package net.hcl.ws;

public class Order {

	private String customerId;			
	private String orderId;		
	private String prodName;			
	private String itemType;			
	private float price;		
	private int qty;			
	private String status;
	
	public Order()
	{
		//public default constructor
	}
	public Order(String customerId, String orderId, String prodName, String itemType, float price, int qty,
			String status) {
		super();
		this.customerId = customerId;
		this.orderId = orderId;
		this.prodName = prodName;
		this.itemType = itemType;
		this.price = price;
		this.qty = qty;
		this.status = status;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getProdName() {
		return prodName;
	}
	public void setProdName(String prodName) {
		this.prodName = prodName;
	}
	public String getItemType() {
		return itemType;
	}
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public int getQty() {
		return qty;
	}
	public void setQty(int qty) {
		this.qty = qty;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
