package passwordmanager;

import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import exceptions.ImageTooSmallException;
import exceptions.InvalidChecksumException;
import exceptions.UIDNotFoundException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Image;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JRadioButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.border.MatteBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;

public class GUI extends JFrame {

	private String chosenImagePath;
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel mainTitle;
	private JPanel panel;
	private JLabel chooseLabel;
	private JTextField imagePathField;
	private JLabel newPasswordLabel;
	private JButton randomPasswordButton;
	private JRadioButton aes;
	private JRadioButton plaintext;
	private JLabel passwordNameLabel;
	private JTextField passwordNameField;
	private JLabel noteLabel;
	private JTextArea noteText;
	private JButton browseButton;
	private JLabel imageShowcase;
	private JButton saveButton;
	private JButton abortButton;
	
	
	private JPanel panel_1;
	private JLabel hidePasswordLabel;
	private JPanel panel_2;
	private JLabel chooseLabel_1;
	private JTextField loadedPathField;
	private JLabel hiddenPasswordLabel;
	private JButton copyToClipboardButton;
	private JLabel passwordNameLabel_1;
	private JTextField passwordNameField_1;
	private JLabel noteLabel_1;
	private JTextArea noteText_1;
	private JButton browseButton_1;
	private JButton abortButton_1;
	private JLabel loadPasswordLabel;
	private JPasswordField passwordField_1;
	private JPasswordField passwordField;
	private JScrollPane scrollPane;
	private JScrollPane scrollPane_1;
	
	private void setPreviewImage() {
		if(chosenImagePath == null) {
			imageShowcase.setIcon(null);
			return;
		}
	    ImageIcon icon = new ImageIcon(chosenImagePath);
	    Image scaledImage = icon.getImage().getScaledInstance(
	        imageShowcase.getWidth(), 
	        imageShowcase.getHeight(), 
	        Image.SCALE_SMOOTH
	    );
	    ImageIcon scaledIcon = new ImageIcon(scaledImage);
	    imageShowcase.setIcon(scaledIcon);
	}
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI frame = new GUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GUI() {
		setResizable(false);
		setTitle("Steganography Password Manager");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 600);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(112, 128, 144));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(getMainTitle());
		contentPane.add(getPanel());
		contentPane.add(getPanel_1());
		contentPane.add(getPanel_2());
	}

	private JLabel getMainTitle() {
		if (mainTitle == null) {
			mainTitle = new JLabel("Steganography Password Manager");
			mainTitle.setHorizontalAlignment(SwingConstants.CENTER);
			mainTitle.setFont(new Font("Bahnschrift", Font.PLAIN, 18));
			mainTitle.setBounds(338, 132, 310, 25);
		}
		return mainTitle;
	}
	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
			panel.setBounds(10, 45, 318, 508);
			panel.setLayout(null);
			panel.add(getChooseLabel());
			panel.add(getImagePathField());
			panel.add(getNewPasswordLabel());
			panel.add(getRandomPasswordButton());
			panel.add(getAes());
			panel.add(getPlaintext());
			panel.add(getPasswordNameLabel());
			panel.add(getPasswordNameField());
			panel.add(getNoteLabel());
			panel.add(getScrollPane());
			panel.add(getBrowseButton());
			panel.add(getSaveButton());
			panel.add(getAbortButton());
			panel.add(getHidePasswordLabel());
			panel.add(getPasswordField_2());
		}
		return panel;
	}
	private JLabel getChooseLabel() {
		if (chooseLabel == null) {
			chooseLabel = new JLabel("Choose image");
			chooseLabel.setBounds(10, 46, 159, 13);
		}
		return chooseLabel;
	}
	private JTextField getImagePathField() {
		if (imagePathField == null) {
			imagePathField = new JTextField();
			imagePathField.setEditable(false);
			imagePathField.setBounds(10, 66, 298, 19);
			imagePathField.setColumns(10);
		}
		return imagePathField;
	}
	private JLabel getNewPasswordLabel() {
		if (newPasswordLabel == null) {
			newPasswordLabel = new JLabel("New password:");
			newPasswordLabel.setBounds(10, 95, 204, 13);
		}
		return newPasswordLabel;
	}
	private JButton getRandomPasswordButton() {
		if (randomPasswordButton == null) {
			randomPasswordButton = new JButton("Generate random");
			randomPasswordButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String password = Passwords.generateSecurePassword();
					passwordField.setText(password);
				}
			});
			randomPasswordButton.setBounds(84, 147, 159, 21);
		}
		return randomPasswordButton;
	}
	private JRadioButton getAes() {
		if (aes == null) {
			aes = new JRadioButton("Use AES encryption");
			aes.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(aes.isSelected()) {
						plaintext.setSelected(false);
					}
				}
			});
			aes.setBounds(10, 376, 143, 21);
		}
		return aes;
	}
	private JRadioButton getPlaintext() {
		if (plaintext == null) {
			plaintext = new JRadioButton("Store plaintext");
			plaintext.setSelected(true);
			plaintext.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(plaintext.isSelected()) {
						aes.setSelected(false);
					}
				}
			});
			plaintext.setBounds(10, 399, 143, 21);
		}
		return plaintext;
	}
	private JLabel getPasswordNameLabel() {
		if (passwordNameLabel == null) {
			passwordNameLabel = new JLabel("Password name");
			passwordNameLabel.setBounds(10, 178, 294, 13);
		}
		return passwordNameLabel;
	}
	private JTextField getPasswordNameField() {
		if (passwordNameField == null) {
			passwordNameField = new JTextField();
			passwordNameField.setBounds(10, 201, 298, 19);
			passwordNameField.setColumns(10);
		}
		return passwordNameField;
	}
	private JLabel getNoteLabel() {
		if (noteLabel == null) {
			noteLabel = new JLabel("Additional note");
			noteLabel.setBounds(10, 230, 148, 13);
		}
		return noteLabel;
	}
	private JTextArea getNoteText() {
		if (noteText == null) {
			noteText = new JTextArea();
		}
		return noteText;
	}
	private JButton getBrowseButton() {
		if (browseButton == null) {
			browseButton = new JButton("Browse");
			browseButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JFileChooser fileChooser = new JFileChooser();
					fileChooser.setFileFilter(new FileNameExtensionFilter("Image Files", "jpg", "png", "bmp"));
					String userDownloads = System.getProperty("user.home") + File.separator + "Downloads";
					fileChooser.setCurrentDirectory(new File(userDownloads));
	                int result = fileChooser.showOpenDialog(null);

	                if (result == JFileChooser.APPROVE_OPTION) {
	                    File selectedFile = fileChooser.getSelectedFile();
	                    imagePathField.setText(selectedFile.getAbsolutePath());
	                    chosenImagePath = selectedFile.getAbsolutePath();
	                    setPreviewImage();
	                }
				}
			});
			browseButton.setBounds(223, 42, 85, 21);
		}
		return browseButton;
	}
	private JLabel getImageShowcase() {
		if (imageShowcase == null) {
			imageShowcase = new JLabel("Image Preview");
			imageShowcase.setFont(new Font("Agency FB", Font.PLAIN, 16));
			imageShowcase.setBounds(10, 10, 239, 164);
			imageShowcase.setHorizontalAlignment(SwingConstants.CENTER);
			imageShowcase.setVerticalAlignment(SwingConstants.CENTER);
		}
		return imageShowcase;
	}
	private JButton getSaveButton() {
		if (saveButton == null) {
			saveButton = new JButton("Save");
			saveButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					//ako su argumenti svi ispravni i sve kako treba
					if(chosenImagePath == null) {
						JOptionPane.showMessageDialog(null, "Image field cannot be empty", "Image not selected", JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					if(Passwords.isStrongPassword(new String(passwordField.getPassword()))) {
						PSE p = new PSE();
						try {
							p.hideData(
									chosenImagePath,
									passwordNameField.getText(),
									new String(passwordField.getPassword()),
									noteText.getText(),
									aes.isSelected());
							JOptionPane.showMessageDialog(null, "Password hidden in :" + chosenImagePath,"Hiding password successful", JOptionPane.INFORMATION_MESSAGE);
						} catch (ImageTooSmallException e1) {
							e1.printStackTrace();
							JOptionPane.showMessageDialog(null, e1.getMessage(), "Couldn't hide password", JOptionPane.ERROR_MESSAGE);
						}
						
					}else {
						JOptionPane.showMessageDialog(null, "Weak password error. Please enter strong password or auto generate\n\nrules: \n- at least 8 characters\n- 1 or more special character\n- 1 or more lowercase character\n- 1 or more uppercase character\n- 1 or more numbers.", "Couldn't hide password", JOptionPane.ERROR_MESSAGE);
					}
				}
			});
			saveButton.setBounds(105, 477, 85, 21);
		}
		return saveButton;
	}
	private JButton getAbortButton() {
		if (abortButton == null) {
			abortButton = new JButton("Abort");
			abortButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					noteText.setText(null);
					passwordField.setText(null);
					passwordNameField.setText(null);
					imagePathField.setText(null);
					aes.setSelected(false);
					plaintext.setSelected(true);
					chosenImagePath = null;
					setPreviewImage();
				}
			});
			abortButton.setBounds(10, 477, 85, 21);
		}
		return abortButton;
	}
	private JPanel getPanel_1() {
		if (panel_1 == null) {
			panel_1 = new JPanel();
			panel_1.setBackground(new Color(224, 255, 255));
			panel_1.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
			panel_1.setBounds(361, 173, 259, 184);
			panel_1.setLayout(null);
			panel_1.add(getImageShowcase());
		}
		return panel_1;
	}
	private JLabel getHidePasswordLabel() {
		if (hidePasswordLabel == null) {
			hidePasswordLabel = new JLabel("Hide password");
			hidePasswordLabel.setFont(new Font("Bahnschrift", Font.BOLD, 16));
			hidePasswordLabel.setBounds(84, 17, 148, 19);
			hidePasswordLabel.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return hidePasswordLabel;
	}
	private JPanel getPanel_2() {
		if (panel_2 == null) {
			panel_2 = new JPanel();
			panel_2.setLayout(null);
			panel_2.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
			panel_2.setBounds(658, 45, 318, 508);
			panel_2.add(getChooseLabel_1());
			panel_2.add(getLoadedPathField());
			panel_2.add(getHiddenPasswordLabel());
			panel_2.add(getCopyToClipboardButton());
			panel_2.add(getPasswordNameLabel_1());
			panel_2.add(getPasswordNameField_1());
			panel_2.add(getNoteLabel_1());
			panel_2.add(getScrollPane_1());
			panel_2.add(getBrowseButton_1());
			panel_2.add(getAbortButton_1());
			panel_2.add(getLoadPasswordLabel());
			panel_2.add(getPasswordField_1());
		}
		return panel_2;
	}
	private JLabel getChooseLabel_1() {
		if (chooseLabel_1 == null) {
			chooseLabel_1 = new JLabel("Choose image");
			chooseLabel_1.setBounds(10, 46, 159, 13);
		}
		return chooseLabel_1;
	}
	private JTextField getLoadedPathField() {
		if (loadedPathField == null) {
			loadedPathField = new JTextField();
			loadedPathField.setEditable(false);
			loadedPathField.setColumns(10);
			loadedPathField.setBounds(10, 66, 298, 19);
		}
		return loadedPathField;
	}
	private JLabel getHiddenPasswordLabel() {
		if (hiddenPasswordLabel == null) {
			hiddenPasswordLabel = new JLabel("Hidden password:");
			hiddenPasswordLabel.setBounds(10, 95, 204, 13);
		}
		return hiddenPasswordLabel;
	}
	private JButton getCopyToClipboardButton() {
		if (copyToClipboardButton == null) {
			copyToClipboardButton = new JButton("Copy to clipboard");
			copyToClipboardButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String loadedPassword = new String(passwordField_1.getPassword());
			        StringSelection stringSelection = new StringSelection(loadedPassword);
			        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			        clipboard.setContents(stringSelection, null);
				}
			});
			copyToClipboardButton.setBounds(84, 147, 159, 21);
		}
		return copyToClipboardButton;
	}
	private JLabel getPasswordNameLabel_1() {
		if (passwordNameLabel_1 == null) {
			passwordNameLabel_1 = new JLabel("Password name");
			passwordNameLabel_1.setBounds(10, 178, 294, 13);
		}
		return passwordNameLabel_1;
	}
	private JTextField getPasswordNameField_1() {
		if (passwordNameField_1 == null) {
			passwordNameField_1 = new JTextField();
			passwordNameField_1.setColumns(10);
			passwordNameField_1.setBounds(10, 201, 298, 19);
		}
		return passwordNameField_1;
	}
	private JLabel getNoteLabel_1() {
		if (noteLabel_1 == null) {
			noteLabel_1 = new JLabel("Additional note");
			noteLabel_1.setBounds(10, 230, 148, 13);
		}
		return noteLabel_1;
	}
	private JTextArea getNoteText_1() {
		if (noteText_1 == null) {
			noteText_1 = new JTextArea();
		}
		return noteText_1;
	}
	private JButton getBrowseButton_1() {
		if (browseButton_1 == null) {
			browseButton_1 = new JButton("Browse");
			browseButton_1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JFileChooser fileChooser = new JFileChooser();
					fileChooser.setFileFilter(new FileNameExtensionFilter("Image Files", "jpg", "png", "bmp"));
					String userDownloads = System.getProperty("user.home") + File.separator + "Downloads";
					fileChooser.setCurrentDirectory(new File(userDownloads));
	                int result = fileChooser.showOpenDialog(null);

	                if (result == JFileChooser.APPROVE_OPTION) {
	                    File selectedFile = fileChooser.getSelectedFile();
	                    loadedPathField.setText(selectedFile.getAbsolutePath());
	                    chosenImagePath = selectedFile.getAbsolutePath();
	                    setPreviewImage();
	                    PSE pse = new PSE();
	                    try {
							DataTransferObject dto = pse.loadData(chosenImagePath);
		                    passwordField_1.setText(dto.getPassword());
		                    passwordNameField_1.setText(dto.getName());
		                    noteText_1.setText(dto.getNote());
		                    JOptionPane.showMessageDialog(null, "Password loaded successfully", "Loading password successful", JOptionPane.INFORMATION_MESSAGE);
						} catch (InvalidChecksumException e1) {
							JOptionPane.showMessageDialog(null, "Original image was modified.", "Original data couldn't be retrieved", JOptionPane.ERROR_MESSAGE);
						} catch (UIDNotFoundException e2) {
							JOptionPane.showMessageDialog(null, "No hidden data detected.", "Original data couldn't be retrieved", JOptionPane.ERROR_MESSAGE);
						}
	                    
	                   
	                }
	            }
			});
			browseButton_1.setBounds(223, 42, 85, 21);
		}
		return browseButton_1;
	}
	private JButton getAbortButton_1() {
		if (abortButton_1 == null) {
			abortButton_1 = new JButton("Clear");
			abortButton_1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					noteText_1.setText(null);
					passwordField_1.setText(null);
					passwordNameField_1.setText(null);
					loadedPathField.setText(null);
					chosenImagePath = null;
					setPreviewImage();
				}
			});
			abortButton_1.setBounds(10, 477, 85, 21);
		}
		return abortButton_1;
	}
	private JLabel getLoadPasswordLabel() {
		if (loadPasswordLabel == null) {
			loadPasswordLabel = new JLabel("Load password");
			loadPasswordLabel.setHorizontalAlignment(SwingConstants.CENTER);
			loadPasswordLabel.setFont(new Font("Bahnschrift", Font.BOLD, 16));
			loadPasswordLabel.setBounds(84, 17, 148, 19);
		}
		return loadPasswordLabel;
	}
	private JPasswordField getPasswordField_1() {
		if (passwordField_1 == null) {
			passwordField_1 = new JPasswordField();
			passwordField_1.setBounds(10, 118, 298, 19);
			passwordField_1.putClientProperty("JPasswordField.cutCopyAllowed",true);
		}
		return passwordField_1;
	}
	private JPasswordField getPasswordField_2() {
		if (passwordField == null) {
			passwordField = new JPasswordField();
			passwordField.setBounds(10, 118, 298, 19);
			passwordField.putClientProperty("JPasswordField.cutCopyAllowed",true);
		}
		return passwordField;
	}
	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setBounds(10, 253, 294, 106);
			scrollPane.setViewportView(getNoteText());
		}
		return scrollPane;
	}
	private JScrollPane getScrollPane_1() {
		if (scrollPane_1 == null) {
			scrollPane_1 = new JScrollPane();
			scrollPane_1.setBounds(10, 253, 294, 106);
			scrollPane_1.setViewportView(getNoteText_1());
		}
		return scrollPane_1;
	}
}
