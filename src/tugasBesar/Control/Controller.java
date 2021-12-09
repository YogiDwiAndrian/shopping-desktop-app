package tugasBesar.Control;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import tugasBesar.Data.Model;
import tugasBesar.Gui.CheckoutPanel;
import tugasBesar.Gui.FinancePanel;
import tugasBesar.Gui.FooterPanel;
import tugasBesar.Gui.LoginPanel;
import tugasBesar.Gui.StorePanel;
import tugasBesar.Gui.View;

public class Controller {
	private Model model;
	private View view;
	private LoginPanel login;
	private StorePanel store;

	
	public Controller(Model model, View view) {
		this.model = model;
		this.view = view;
		login = new LoginPanel();
		view.addPanel(login.getPanel());

		login.addLoginListener(new LoginListener());
		login.addRegisterListener(new RegisterListener());
		login.addSignUpListener(new SignUpListener());
		view.viewRefresh();
	}

	// <----------Login Listeners---------->
	class LoginListener implements ActionListener {
		public void actionPerformed(ActionEvent a) {
			if (login.getLoginStatus()) {
				if (model.loginUser(login.getUserName(), login.getUserPassword())) {
					store = new StorePanel(model.getAccountTypeString());
					store.getNav().addStoreListener(new StoreListener());
					store.getNav().addCartListener(new CartListener());
					store.getNav().addLogoutListener(new LogoutListener());
					store.getFooter().addCheckoutListener(new CheckoutListener());
					store.getFooter().completeTransactionListener(new CompleteTransactionListener());
					model.setAccountUsername(login.getUserName());
					view.removePanel(login.getPanel());
					view.addPanel(store.getPanel());
					if(model.getAccountTypeString().equals("Admin")){
						store.getNav().addAdminListener(new AdminListener());
						store.getNav().addFinanceListener(new FinanceListener());
						store.viewAdmin(model.getCSV("products.csv"), createStockIncrementListeners(model.getCSV("products.csv").size()), createStockDecrementListeners(model.getCSV("products.csv").size()));
						store.getNav().addWelcomeMessage(("Welcome " + model.getAccountTypeString() + " " + model.getAccountUsername()));
						store.getFooter();
						FooterPanel.setTotalText(model.getCartTotal());
						view.viewRefresh();
					}else{
						store.viewProducts(model.getCSV("products.csv"),
								createBuyNowListeners(model.getCSV("products.csv").size()));
						store.getNav().addWelcomeMessage(
								("Welcome " + model.getAccountTypeString() + " " + model.getAccountUsername()));
						view.viewRefresh();
					}
				} else {
					login.loginMessage("No account found. Please Try again.");
				}
			} else {
				login.viewLogin();
				view.viewRefresh();
			}
		}
	}

	// <--------Login Page Listeners------->
	class RegisterListener implements ActionListener {
		public void actionPerformed(ActionEvent a) {
			displayRegistration();
		}
	}

	class SignUpListener implements ActionListener {
		public void actionPerformed(ActionEvent a) {
			if (model.signUpUser(login.getUserName(), login.getUserPassword(), login.getAccountType())) {
				login.loginMessage("Signup Successful");
			} else {
				login.loginMessage("Please try different credentials");
			}
		}
	}

	// <--------Navigation Listeners------->
	class StoreListener implements ActionListener {
		public void actionPerformed(ActionEvent a) {
			displayStore();
		}
	}
	
	class AdminListener implements ActionListener {
		public void actionPerformed(ActionEvent a) {
			displayAdmin();
		}
	}
	
	class FinanceListener implements ActionListener {
		public void actionPerformed(ActionEvent a) {
			displayFinance();
		}
	}

	class CartListener implements ActionListener {
		public void actionPerformed(ActionEvent a) {
			displayCart();
		}
	}

	class LogoutListener implements ActionListener {
		public void actionPerformed(ActionEvent a) {
			displayLogin();
		}
	}

	public class BuyNowListener implements ActionListener {
		private String ID;
		private String Name;
		private String Type;
		private String Price;
		private String InvoicePrice;

		public BuyNowListener(String id, String name, String type, String price, String invoiceprice) {
			ID = id;
			Name = name;
			Type = type;
			Price = price;
			InvoicePrice = invoiceprice;
		}

		public void actionPerformed(ActionEvent e) {
			if(model.getStock(Name) == 0){
			}else{
				model.cartAdd(ID, Name, Type, Price, InvoicePrice);
				store.viewProducts(model.getCSV("products.csv"),
				createBuyNowListeners(model.getCSV("products.csv").size()));
				FooterPanel.setTotalText(model.getCartTotal());
				view.viewRefresh();
			}
		}
	}

	public class IncrementListener implements ActionListener {
		private String ID;
		private String Name;
		private String Type;
		private String Price;
		private String InvoicePrice;
		

		public IncrementListener(String id, String name, String type, String price, String invoiceprice) {
			ID = id;
			Name = name;
			Type = type;
			Price = price;
		}

		public void actionPerformed(ActionEvent e) {
			model.incrementStock(Name);
			model.cartAdd(ID, Name, Type, Price, InvoicePrice);
			store.viewCart(model.getCSV(model.getAccountCSVLocation()),
					createIncrementListeners(model.getCSV(model.getAccountCSVLocation()).size()),
					createDecrementListeners(model.getCSV(model.getAccountCSVLocation()).size()));
			FooterPanel.setTotalText(model.getCartTotal());
			view.viewRefresh();
		}
	}

	public class DecrementListener implements ActionListener {
		private String ID;
		private String Name;
		private String Type;
		private String Price;

		public DecrementListener(String id, String name, String type, String price) {
			ID = id;
			Name = name;
			Type = type;
			Price = price;
		}

		public void actionPerformed(ActionEvent e) {
			model.cartRemove(ID, Name, Type, Price);
			store.viewCart(model.getCSV(model.getAccountCSVLocation()),
					createIncrementListeners(model.getCSV(model.getAccountCSVLocation()).size()),
					createDecrementListeners(model.getCSV(model.getAccountCSVLocation()).size()));
			FooterPanel.setTotalText(model.getCartTotal());
			view.viewRefresh();
		}
	}

	public class StockIncrementListener implements ActionListener {
		private String Name;

		public StockIncrementListener(String id, String name, String type, String price) {
			Name = name;
		}

		public void actionPerformed(ActionEvent e) {
			model.incrementStock(Name);
			store.viewAdmin(model.getCSV("products.csv"),
					createStockIncrementListeners(model.getCSV("products.csv").size()),
					createStockDecrementListeners(model.getCSV("products.csv").size()));
			FooterPanel.setTotalText(model.getCartTotal());
			view.viewRefresh();
		}
	}

	public class StockDecrementListener implements ActionListener {
		private String Name;
		public StockDecrementListener(String id, String name, String type, String price) {
			Name = name;
		}

		public void actionPerformed(ActionEvent e) {
			model.decrementStock(Name);
			store.viewAdmin(model.getCSV("products.csv"),
					createStockIncrementListeners(model.getCSV("products.csv").size()),
					createStockDecrementListeners(model.getCSV("products.csv").size()));
			FooterPanel.setTotalText(model.getCartTotal());
			view.viewRefresh();
		}
	}
	
	// <--------Footer Listeners------->
	public class CheckoutListener implements ActionListener {
		public void actionPerformed(ActionEvent a) {
			displayCheckout();
			model.getCartTotal();
		}
	}
	
	public class CompleteTransactionListener implements ActionListener {
		public void actionPerformed(ActionEvent a) {
			completeTransaction();
		}
	}
	
	// <------Create Listener Arrays------>
	private BuyNowListener[] createBuyNowListeners(int amount) {
		BuyNowListener[] buynowlistenerarray = new BuyNowListener[amount];
		if (model.getCSV("products.csv").isEmpty())
			return null;
		else {
			int count = 0;
			for (String[] product : model.getCSV("products.csv")) {
				if(product[0].equals("ID")){
					continue;
				}
				++count;
				String name = product[5];
				String price = product[4];
				String invoiceprice = product[3];
				String type = product[1];
				String id = product[0];
				buynowlistenerarray[count] = new BuyNowListener(id, name, type, price, invoiceprice);
			}
			return buynowlistenerarray;
		}
	}

	private IncrementListener[] createIncrementListeners(int amount) {
		IncrementListener[] incrementlistenerarray = new IncrementListener[amount];
		if (model.getCSV(model.getAccountCSVLocation()).isEmpty())
			return null;
		else {
			int count = 0;
			for (String[] product : model.getCSV(model.getAccountCSVLocation())) {
				if(product[0].equals("ID")){
					continue;
				}
				String id = product[0];
				String name = product[1];
				String type = product[2];
				String price = product[3];
				String invoiceprice = product[4];
				incrementlistenerarray[count] = new IncrementListener(id, name, type, price, invoiceprice);
				++count;
			}
			return incrementlistenerarray;
		}
	}

	private DecrementListener[] createDecrementListeners(int amount) {
		DecrementListener[] decrementlistenerarray = new DecrementListener[amount];
		if (model.getCSV(model.getAccountCSVLocation()).isEmpty())
			return null;
		else {
			int count = 0;
			for (String[] product : model.getCSV(model.getAccountCSVLocation())) {
				if(product[0].equals("ID")){
					continue;
				}
				String id = product[0];
				String name = product[1];
				String type = product[2];
				String price = product[3];
				decrementlistenerarray[count] = new DecrementListener(id, name, type, price);
				++count;
			}
			return decrementlistenerarray;
		}
	}
	
	private StockIncrementListener[] createStockIncrementListeners(int amount) {
		StockIncrementListener[] stockincrementlistenerarray = new StockIncrementListener[amount];
		if (model.getCSV("products.csv").isEmpty())
			return null;
		else {
			int count = 0;
			for (String[] product : model.getCSV("products.csv")) {
				if(product[0].equals("ID")){
					continue;
				}
				String name = product[5];
				String price = product[4];
				String type = product[1];
				String id = product[0];
				stockincrementlistenerarray[count] = new StockIncrementListener(id, name, type, price);
				++count;
			}
			return stockincrementlistenerarray;
		}
	}

	private StockDecrementListener[] createStockDecrementListeners(int amount) {
		StockDecrementListener[] stockdecrementlistenerarray = new StockDecrementListener[amount + 1];
		if (model.getCSV("products.csv").isEmpty())
			return null;
		else {
			int count = 0;
			for (String[] product : model.getCSV("products.csv")) {
				if(product[0].equals("ID")){
					continue;
				}
				String name = product[5];
				String price = product[4];
				String type = product[1];
				String id = product[0];
				stockdecrementlistenerarray[count] = new StockDecrementListener(id, name, type, price);
				++count;
			}
			return stockdecrementlistenerarray;
		}
	}
	
	// <----------Helper Functions---------->
	public void displayStore() {
		FooterPanel.setTotalText(model.getCartTotal());
		if (store.getCurrentView().equals("Store")) {
			System.out.println("You're already on the store page.");
		} else {
			store.getPanel().add(store);
			view.add(store.getPanel());
			store.removeProductsFromDisplay();
			store.viewProducts(model.getCSV("products.csv"),
					createBuyNowListeners(model.getCSV("products.csv").size()));
			FooterPanel.addCheckoutBtn();
			view.viewRefresh();
		}
	}
	
	/**
	 * Displays the cart
	 */
	public void displayCart() {
		if (store.getCurrentView().equals("Cart")) {
			System.out.println("You're already on the cart page.");
		} else {
			view.addPanel(store.getPanel());
			store.removeProductsFromDisplay();
			store.viewCart(model.getAccountCart(),
					createIncrementListeners(model.getCSV(model.getAccountCSVLocation()).size()),
					createDecrementListeners(model.getCSV(model.getAccountCSVLocation()).size()));
			FooterPanel.addCheckoutBtn();
			view.viewRefresh();
		}
	}
	

	public void displayAdmin() {
		if (store.getCurrentView().equals("Admin")) {
			System.out.println("You're already on the admin page.");
		} else {
			view.addPanel(store.getPanel());
			store.removeProductsFromDisplay();
			store.viewAdmin(model.getCSV("products.csv"),
					createStockIncrementListeners(model.getCSV("products.csv").size()),
					createStockDecrementListeners(model.getCSV("products.csv").size()));
			FooterPanel.addCheckoutBtn();
			view.viewRefresh();
		}
	}
	
	public void displayFinance() {
		if (store.getCurrentView().equals("Finance")) {
			System.out.println("You're already on the finance page.");
		} else {
			view.addPanel(store.getPanel());
			store.removeProductsFromDisplay();
			store.viewFinance(model.getCSV("sales.csv"));
			
			FooterPanel.addCheckoutBtn();
			FinancePanel.setRevenue(model.getSaleTotal());
			FinancePanel.setCost(model.getSaleCost());
			FinancePanel.setProfit(model.getSaleTotal() - model.getSaleCost());
			view.viewRefresh();
		}
	}
	
	public void displayCheckout(){ 
		store.removeProductsFromDisplay();
		store.viewCheckout(model.getCartTotal());
		store.getCheckout();
		store.getFooter();
		CheckoutPanel.setTotalText(model.getCartTotal());
		FooterPanel.addCompleteTransactionBtn(new CompleteTransactionListener());
		store.getFooter().removeCheckoutBtn();
		store.viewCheckout(model.getCartTotal());
		view.viewRefresh();
	}

	public void displayLogin() {
		if (login.isLoginPanelDisplayed() == false) {
			view.removePanel(store.getPanel());
			view.addPanel(login.getPanel());
			view.viewRefresh();
		}
	}

	public void completeTransaction(){
		store.getCheckout();
		model.completeTransaction(CheckoutPanel.getFirstName(), CheckoutPanel.getLastName(), CheckoutPanel.getCreditCard(), CheckoutPanel.getEmail(), CheckoutPanel.getAddress());
		model.clearCSV(model.getAccountCSVLocation());
		
		store.removeProductsFromDisplay();
		store.viewProducts(model.getCSV("products.csv"), createBuyNowListeners(model.getCSV("products.csv").size()));
		FooterPanel.addCheckoutBtn();
		FooterPanel.setTotalText(model.getCartTotal());
		view.viewRefresh();
	}
	
	public void displayRegistration() {
		if (login.getLoginStatus()) {
			login.viewRegistration();
			view.viewRefresh();
		}
	}
}
