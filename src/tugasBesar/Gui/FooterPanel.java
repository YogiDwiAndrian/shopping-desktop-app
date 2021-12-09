package tugasBesar.Gui;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import tugasBesar.Control.Controller;

public class FooterPanel extends JPanel {
	private static JPanel FooterPanel;
	private static JButton btnCheckout;
	private static JButton btnCompleteTransaction;
	private static JLabel carttotal;

	public FooterPanel() {
		FooterPanel = new JPanel();

		carttotal = new JLabel("");
		FooterPanel.add(carttotal);

		btnCheckout = new JButton("Checkout");
		FooterPanel.add(btnCheckout);

		btnCompleteTransaction = new JButton("Complete Purchase");
	}

	public void addCheckoutListener(Controller.CheckoutListener listener) {
		btnCheckout.addActionListener(listener);
	}

	public void completeTransactionListener(Controller.CompleteTransactionListener listner) {
		btnCompleteTransaction.addActionListener(listner);
	}

	public static void addCompleteTransactionBtn(Controller.CompleteTransactionListener completeTransactionListener) {
		FooterPanel.add(btnCompleteTransaction);
	}

	public void removeCompleteTransactionBtn() {
		FooterPanel.remove(btnCompleteTransaction);
	}

	public static void addCheckoutBtn() {
		FooterPanel.add(btnCheckout);
		FooterPanel.remove(btnCompleteTransaction);
	}

	public void removeCheckoutBtn() {
		FooterPanel.remove(btnCheckout);
		FooterPanel.add(btnCompleteTransaction);
	}

	public JPanel getPanel() {
		return FooterPanel;
	}

	public static void setTotalText(float cartTotal) {
		carttotal.setText("TOTAL: " + cartTotal);
	}
}
