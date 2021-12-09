package tugasBesar.Gui;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class LoginPanel extends JPanel {
	private JPanel login;
	private JLabel lblUsername = new JLabel("Username:");
	private JLabel lblPassword = new JLabel("Password:");
	private final JButton btnLogin = new JButton("Login");
	private final JButton btnRegister = new JButton("Register");
	private final JButton btnSignUp = new JButton("Sign Up");
	private JTextField userName = new JTextField(10);
	private JPasswordField userPassword = new JPasswordField(10);
	private final JLabel lblLogin = new JLabel("");
	private final JPanel buttonPanel = new JPanel();
	private final JPanel usernamePanel = new JPanel();
	private final JPanel passwordPanel = new JPanel();
	private final JPanel AccountType = new JPanel();
	private final JRadioButton rdbtnAdmin = new JRadioButton("Admin");
	private final JRadioButton rdbtnUser = new JRadioButton("User");


	public LoginPanel() {
		login = new JPanel();
		addLoginComponents();
		login.setBackground(new Color(248, 247, 242));
		buttonPanel.setBackground(Color.RED);

		login.add(buttonPanel);
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		buttonPanel.add(btnLogin);
		buttonPanel.add(btnRegister);
	}

	private void addLoginComponents() {
		createUserNameInput();
		createPasswordInput();
		createLoginMessageLabel();
		createRegisterButton();
	}

	public void viewRegistration() {
		removeRegisterButton();
		createSignUpButton();
		createTypes();
	}

	public void viewLogin() {
		removeSignUpButton();
		lblLogin.setText("");
		createRegisterButton();
		removeTypes();
	}

	public void createUserNameInput() {
		login.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		usernamePanel.setBackground(Color.RED);

		login.add(usernamePanel);
		lblUsername.setForeground(Color.WHITE);
		usernamePanel.add(lblUsername);
		lblUsername.setHorizontalAlignment(SwingConstants.TRAILING);
		usernamePanel.add(userName);
	}

	public void createPasswordInput() {
		passwordPanel.setBackground(Color.RED);

		login.add(passwordPanel);
		lblPassword.setForeground(Color.WHITE);
		passwordPanel.add(lblPassword);
		lblPassword.setHorizontalAlignment(SwingConstants.TRAILING);
		passwordPanel.add(userPassword);
	}

	public void createLoginMessageLabel() {
		lblLogin.setHorizontalAlignment(SwingConstants.CENTER);
		login.add(lblLogin);
	}

	public void createRegisterButton() {
		btnLogin.setForeground(new Color(255, 255, 255));
		btnLogin.setBackground(new Color(0, 255, 51));
		buttonPanel.add(btnLogin);
		btnRegister.setForeground(new Color(255, 255, 255));
		btnRegister.setBackground(new Color(0, 255, 51));
		buttonPanel.add(btnRegister);
	}


	public void removeRegisterButton() {
		buttonPanel.remove(btnLogin);
		buttonPanel.remove(btnRegister);
	}

	public void createSignUpButton() {
		buttonPanel.add(btnSignUp);
		buttonPanel.add(btnLogin);
	}

	public void removeSignUpButton() {
		buttonPanel.remove(btnSignUp);
		buttonPanel.remove(btnLogin);
	}

	public void createTypes() {
		login.add(AccountType);
		rdbtnAdmin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				rdbtnUser.setSelected(false);
			}
		});
		rdbtnUser.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				rdbtnAdmin.setSelected(false);
			}
		});
		AccountType.add(rdbtnAdmin);
		AccountType.add(rdbtnUser);
		login.add(AccountType);
	}

	public void removeTypes() {
		login.remove(AccountType);
	}

	public JPanel getPanel() {
		return login;
	}

	public boolean isLoginPanelDisplayed() {
		if (login.getParent() == null)
			return false;
		else
			return true;
	}

	public boolean getLoginStatus() {
		if (btnSignUp.getParent() == null)
			return true;
		else
			return false;
	}

	void viewLogedIn(int AccountType) {
		if (AccountType == 1) {
		} else if (AccountType == 2) {
		}
	}

	public String getUserName() {
		return userName.getText();
	}

	public char[] getUserPassword() {
		return userPassword.getPassword();
	}

	public int getAccountType() {
		if (rdbtnAdmin.isSelected())
			return 2;
		else if (rdbtnUser.isSelected())
			return 1;
		else
			return 0;
	}

	public void loginMessage(String string) {
		lblLogin.setText(string);
	}

	// Action Listeners
	public void addLoginListener(ActionListener listenerForLogin) {
		btnLogin.addActionListener(listenerForLogin);
	}

	public void addRegisterListener(ActionListener listenerForRegister) {
		btnRegister.addActionListener(listenerForRegister);
	}

	public void addSignUpListener(ActionListener listenerForSignUp) {
		btnSignUp.addActionListener(listenerForSignUp);
	}

}
