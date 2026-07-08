package com.designpattern.model;

public class CartItem {
	private int cartItemId;
	private int cartId;
	private int menuId;
	private int quantity;

	// Extra fields for display
	private String itemName;
	private double price;
	private String imagePath;
    private int restaurantId;

	public CartItem() {}

	   public CartItem(int cartItemId, int cartId, int menuId, int quantity,
               String itemName, double price, String imagePath, int restaurantId) {
   this.cartItemId = cartItemId;
   this.cartId = cartId;
   this.menuId = menuId;
   this.quantity = quantity;
   this.itemName = itemName;
   this.price = price;
   this.imagePath = imagePath;
   this.restaurantId = restaurantId;
}

	// existing getters/setters...
	public int getCartItemId() { return cartItemId; }
	public void setCartItemId(int cartItemId) { this.cartItemId = cartItemId; }

	public int getCartId() { return cartId; }
	public void setCartId(int cartId) { this.cartId = cartId; }

	public int getMenuId() { return menuId; }
	public void setMenuId(int menuId) { this.menuId = menuId; }

	public int getQuantity() { return quantity; }
	public void setQuantity(int quantity) { this.quantity = quantity; }

	// new getters/setters
	public String getItemName() { return itemName; }
	public void setItemName(String itemName) { this.itemName = itemName; }

	public double getPrice() { return price; }
	public void setPrice(double price) { this.price = price; }

	public String getImagePath() { return imagePath; }
	public void setImagePath(String imagePath) { this.imagePath = imagePath; }
	
	public int getRestaurantId() { return restaurantId; }
    public void setRestaurantId(int restaurantId) { this.restaurantId = restaurantId; }

    public double getTotalPrice() { return price * quantity; }

    @Override
    public String toString() {
        return "CartItem [cartItemId=" + cartItemId + ", cartId=" + cartId +
               ", menuId=" + menuId + ", quantity=" + quantity +
               ", itemName=" + itemName + ", price=" + price +
               ", restaurantId=" + restaurantId + "]";
    }
}
