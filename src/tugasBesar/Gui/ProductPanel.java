package tugasBesar.Gui;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import tugasBesar.Control.Controller;

public class ProductPanel extends JPanel {
	private JPanel product;
	private String Name;
	private String Description;
	int Quantity;
	float Invoiceprice;
	float Sellingprice;
	private JButton buynow;
	private JButton increment;
	private JButton decrement;
	private JButton stockincrement;
	private JButton stockdecrement;
	private Controller.IncrementListener IncrementListener;
	private Controller.DecrementListener DecrementListener;
	private Controller.StockIncrementListener StockIncrementListener;
	private Controller.StockDecrementListener StockDecrementListener;
	private Controller.BuyNowListener BuyNowListener;


	public ProductPanel(String id, String type, String quantity, String invoiceprice, String sellingprice, String name,
			String description, Controller.BuyNowListener buyNowListener) {
		Name = name;
		Description = description;
		String.valueOf(id);
		Quantity = Integer.parseInt(quantity);
		Invoiceprice = Float.parseFloat(invoiceprice);
		Sellingprice = Float.parseFloat(sellingprice);
		BuyNowListener = buyNowListener;
		createStoreItem();
	}

	public ProductPanel(String id, String name, String type, String sellingprice, String invoiceprice, String quantity,
			Controller.IncrementListener incrementListener, Controller.DecrementListener decrementListener) {
		Name = name;
		String.valueOf(id);
		Quantity = Integer.parseInt(quantity);
		Sellingprice = Float.parseFloat(sellingprice);
		IncrementListener = incrementListener;
		DecrementListener = decrementListener;

		createCheckoutComponents();
	}

	public ProductPanel(String id, String type, String quantity, String invoiceprice, String sellingprice, String name,
			String description, Controller.StockIncrementListener incrementListener,
			Controller.StockDecrementListener decrementListener) {
		Name = name;
		String.valueOf(id);
		Quantity = Integer.parseInt(quantity);
		Sellingprice = Float.parseFloat(sellingprice);
		StockIncrementListener = incrementListener;
		StockDecrementListener = decrementListener;

		createAdminComponents();
	}

	public JPanel getPanel() {
		return product;
	}

	public JLabel prodImage(String imageLocation) {
		JLabel image = new JLabel();
		image.setIcon(new ImageIcon(imageLocation));
		return image;
	}

	public JLabel prodDescription(String description) {
		JLabel prodDescription = new JLabel(description);
		return prodDescription;
	}

	private void createCheckoutComponents() {
		product = new JPanel(new BorderLayout());
		product.setName(Name);
		product.setBackground(new Color(235, 232, 217));
		product.setBorder(new EmptyBorder(10, 5, 10, 5));

		product.add(prodImage("img//" + Name + ".png"), BorderLayout.WEST);
		product.add(new JLabel(Name), BorderLayout.NORTH);

		JPanel checkout = new JPanel(new BorderLayout());
		JPanel checkoutButtons = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JPanel checkoutInfo = new JPanel(new FlowLayout());
		checkoutInfo.add(new JLabel("$" + Sellingprice));
		if (Quantity == 0) {
			checkoutInfo.add(new JLabel("REMOVE?"));
		} else {
			checkoutInfo.add(new JLabel("Amount: " + Quantity));
		}
		checkoutButtons.add(incrementCartButton(IncrementListener));
		checkoutButtons.add(decrementCartButton(DecrementListener));
		checkout.add(checkoutInfo, BorderLayout.NORTH);
		checkout.add(checkoutButtons, BorderLayout.SOUTH);
		product.setBackground(new Color(235, 232, 217));
		product.add(checkout, BorderLayout.EAST);
	}

	private void createAdminComponents() {
		product = new JPanel(new BorderLayout());
		product.setName(Name);
		product.setBackground(new Color(235, 232, 217));
		product.setBorder(new EmptyBorder(10, 5, 10, 5));

		product.add(prodImage("img//" + Name + ".png"), BorderLayout.WEST);
		product.add(new JLabel(Name), BorderLayout.NORTH);

		JPanel checkout = new JPanel(new BorderLayout());
		JPanel checkoutButtons = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JPanel checkoutInfo = new JPanel(new FlowLayout());
		checkoutInfo.add(new JLabel("$" + Sellingprice));
		if (Quantity == 0) {
			checkoutInfo.add(new JLabel("REMOVE?"));
		} else {
			checkoutInfo.add(new JLabel("Amount: " + Quantity));
		}
		checkoutButtons.add(incrementCartButton(StockIncrementListener));
		checkoutButtons.add(decrementCartButton(StockDecrementListener));
		checkout.add(checkoutInfo, BorderLayout.NORTH);
		checkout.add(checkoutButtons, BorderLayout.SOUTH);
		product.setBackground(new Color(235, 232, 217));
		product.add(checkout, BorderLayout.EAST);
	}

	private void createStoreItem() {
		product = new JPanel(new BorderLayout());
		product.setName(Name);
		product.setBackground(new Color(235, 232, 217));
		product.setBorder(new EmptyBorder(10, 5, 10, 5));

		product.add(prodImage("img//" + Name + ".png"), BorderLayout.WEST);
		product.add(new JLabel(Name), BorderLayout.NORTH);

		JPanel store = new JPanel(new BorderLayout());
		JPanel checkoutInfo = new JPanel(new FlowLayout());
		JPanel prodDesc = new JPanel(new FlowLayout());
		store.add(buyNowButton(BuyNowListener), BorderLayout.SOUTH);
		if (Quantity == 0) {
			checkoutInfo.add(new JLabel("OUT OF STOCK"));
		} else {
			checkoutInfo.add(new JLabel("Stock: " + Quantity));
			checkoutInfo.add(new JLabel("$" + Sellingprice));
		}
		prodDesc.add(prodDescription(Description));
		store.add(checkoutInfo, BorderLayout.NORTH);
		store.add(prodDesc, BorderLayout.CENTER);
		store.setBackground(new Color(235, 232, 217));
		product.add(store, BorderLayout.EAST);
	}

	public JButton buyNowButton(ActionListener buynowbutton) {
		buynow = new JButton("Buy Now");
		buynow.addActionListener(buynowbutton);
		return buynow;
	}

	public JButton incrementCartButton(ActionListener incrementButton) {
		increment = new JButton("^");
		increment.addActionListener(incrementButton);
		return increment;
	}

	public JButton decrementCartButton(ActionListener decrementButton) {
		decrement = new JButton("v");
		decrement.addActionListener(decrementButton);
		return decrement;
	}

	public JButton incrementStockButton(ActionListener incrementButton) {
		stockincrement = new JButton("^");
		stockincrement.addActionListener(incrementButton);
		return stockincrement;
	}

	public JButton decrementStockButton(ActionListener decrementButton) {
		stockdecrement = new JButton("v");
		stockdecrement.addActionListener(decrementButton);
		return stockdecrement;
	}

	// Action Listeners
	public void addBuyNowListener(ActionListener listenerforBuyNow) {
		buynow.addActionListener(listenerforBuyNow);
	}

	public void addIncrementListener(ActionListener listenerForIncrement) {
		increment.addActionListener(listenerForIncrement);
	}

}
