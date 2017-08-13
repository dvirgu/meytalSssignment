import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.StreamCorruptedException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.w3c.dom.events.EventException;

import com.sun.javafx.event.EventDispatchTreeImpl;

public class ContactsManagerFrame extends JFrame {
	public static final int SIZE_OF_UI_FIELD = 12;
	// Text Fields
	private JTextField jtFirstName = new JTextField(SIZE_OF_UI_FIELD);
	private JTextField jtLastName = new JTextField(SIZE_OF_UI_FIELD);
	private JTextField jtPhoneNamber = new JTextField(SIZE_OF_UI_FIELD);
	private JTextField jtFilePath = new JTextField(SIZE_OF_UI_FIELD);
	// Buttons
	private JButton jbtCreate = new JButton("Create");
	private JButton jbtUpdate = new JButton("Update");
	private JButton jbtNext = new JButton(">");
	private JButton jbtPrevious = new JButton("<");
	private JButton jbtFirst = new JButton("First");
	private JButton jbtLast = new JButton("Last");
	private JButton jbtEditContact = new JButton("Edit Contact");
	private JButton jbtExport = new JButton("Export");
	private JButton jbtLoadFile = new JButton("LoadFile");
	private String[] formats = { "txt", "obj.dat", "byte.dat" };
	private JComboBox jcExportFormat = new JComboBox(formats);
	private JLabel jlFirstName = new JLabel(" ");
	private JLabel jlLastName = new JLabel(" ");;
	private JLabel jlPhoneNumber = new JLabel(" ");;
	private String[] contactUiData;
	private static final long serialVersionUID = 1L;
	private boolean editButtonClicked = false;
	private ContactsManager cm;

	public ContactsManagerFrame(ContactsManager cm) {
		this.cm = cm;
		//P1 : the Create Contacts Panel
		JPanel p1 = new JPanel();
		p1.setLayout(new GridLayout(4, 2));
		p1.add(new JLabel("First name:"));
		p1.add(jtFirstName);
		p1.add(new JLabel("Last name:"));
		p1.add(jtLastName);
		p1.add(new JLabel("Phone number:"));
		p1.add(jtPhoneNamber);
		p1.add(jbtCreate);
		p1.add(jbtUpdate);

		JPanel p2 = new JPanel();
		p2.setLayout(new GridLayout(4, 2));
		p2.add(new JLabel("First name"));
		p2.add(jlFirstName);
		p2.add(new JLabel("Last name"));
		p2.add(jlLastName);
		p2.add(new JLabel("Phone number"));
		p2.add(jlPhoneNumber);
		p2.add(jbtEditContact);

		JPanel p3 = new JPanel();
		p3.setLayout(new GridLayout(2, 1));
		p3.add(jbtPrevious);
		p3.add(jbtFirst);

		JPanel p4 = new JPanel();
		p4.setLayout(new GridLayout(2, 1));
		p4.add(jbtNext);
		p4.add(jbtLast);
		
		//P5 : the Manager Contacts Panel
		JPanel p5 = new JPanel();
		p5.setLayout(new BorderLayout());
		p5.add(p2, BorderLayout.CENTER);
		p5.add(p3, BorderLayout.WEST);
		p5.add(p4, (BorderLayout.EAST));

		JPanel p6 = new JPanel();
		p6.setLayout(new GridLayout(3, 1));
		p6.add(new JLabel("file path"));
		p6.add(jtFilePath);
		p6.add(jbtLoadFile);
		
		//P7 : the Export-Load Contacts Panel
		JPanel p7 = new JPanel();
		p7.setLayout(new BorderLayout());
		p7.add(jbtExport, BorderLayout.CENTER);
		p7.add(p6, BorderLayout.EAST);
		p7.add(jcExportFormat, BorderLayout.WEST);

		setLayout(new BorderLayout());
		add(p5, BorderLayout.CENTER);
		add(p1, BorderLayout.NORTH);
		add(p7, BorderLayout.SOUTH);

		jbtCreate.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				createContact();

			}

		});
		jbtPrevious.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				previousContact();

			}

		});
		jbtNext.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				nextContact();

			}

		});
		jbtFirst.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				getFirstContact();

			}
		});
		jbtLast.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				getLastContact();

			}
		});
		jbtEditContact.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				editContact();

			}
		});
		jbtUpdate.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				updatContact();

			}
		});
		jbtExport.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				exportContact();

			}
		});
		jbtLoadFile.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				loadFile();

			}
		});

		generalProperties();

	}

	public void createContact() {
		try {
			if (cm.lengthOfFile() == 0) { // if a new folder
				cm.writeContact(jtFirstName.getText(), jtLastName.getText(), jtPhoneNamber.getText());
				contactUiData = cm.readObject(0);
				setUiLabelData(contactUiData);

			} else {
				cm.writeContact(jtFirstName.getText(), jtLastName.getText(), jtPhoneNamber.getText());
				cm.goToPosition(cm.getCurrentPosition() - cm.getContactSize());
			}
		} catch (IOException ex) {
			System.out.println("Error: " + ex);
			ex.printStackTrace();
		}

	}

	public void previousContact() {
		try {

			if (cm.getCurrentPosition() - 2 * cm.getContactSize() >= 0) {
				contactUiData = cm.readObject(cm.getCurrentPosition() - 2 * cm.getContactSize());
				setUiLabelData(contactUiData);
			} else {
				System.out.println("No previous contacts in file");
			}

		} catch (IOException ex) {
			System.out.println("Error: " + ex);
			ex.printStackTrace();
		} catch (NullPointerException ex) {
			System.out.println("Null Pointer");
		}

	}

	public void getFirstContact() {
		try {
			if (cm.lengthOfFile() > 0)
				contactUiData = cm.readObject(0);
			setUiLabelData(contactUiData);
		} catch (IOException ex) {
			System.out.println("Error: " + ex);
			ex.printStackTrace();
		} catch (NullPointerException ex) {
			System.out.println("No Contacts in File");
		}

	}

	public void nextContact() {
		try {
			if (cm.getCurrentPosition() < cm.lengthOfFile()) {

				contactUiData = cm.readObject(cm.getCurrentPosition());
				setUiLabelData(contactUiData);
			} else {
				System.out.println("No more contacts in file");
			}
		} catch (IOException ex) {
			System.out.println("Error: " + ex);
			ex.printStackTrace();

		} catch (NullPointerException ex) {
			System.out.println("Null Pointer");
		}

	}

	public void getLastContact() {
		try {

			if (cm.lengthOfFile() > 0)

				contactUiData = cm.readObject(cm.lengthOfFile() - cm.getContactSize());
			setUiLabelData(contactUiData);
		} catch (IOException ex) {
			System.out.println("Error: " + ex);
			ex.printStackTrace();
		} catch (NullPointerException ex) {
			System.out.println("No Contacts in File");
		}

	}

	public void editContact() {
		editButtonClicked = true;
		try {

			contactUiData = cm.readObject(cm.getCurrentPosition() - cm.getContactSize());
			setUiTextField(contactUiData);
		} catch (IOException ex) {
			System.out.println("Error: " + ex);
			ex.printStackTrace();
		}

	}

	public void updatContact() {
		if (editButtonClicked == true) {
			try {
				cm.goToPosition(cm.getCurrentPosition() - cm.getContactSize());
				cm.updateContact(jtFirstName.getText(), jtLastName.getText(), jtPhoneNamber.getText());
				editButtonClicked = false;
				contactUiData = cm.readObject(cm.getCurrentPosition() - cm.getContactSize());
				setUiLabelData(contactUiData);
			} catch (IOException ex) {
				System.out.println("Error: " + ex);
				ex.printStackTrace();
			}
		}

	}

	public void exportContact() {
		String format = (String) jcExportFormat.getSelectedItem();
		try {
			String filePath = cm.exportContactAndGetFilePath(format, jlFirstName.getText(), jlLastName.getText(),
					jlPhoneNumber.getText());
			jtFilePath.setText(filePath);
		} catch (IOException ex) {
			System.out.println("Error: " + ex);
			ex.printStackTrace();
		}
	}

	public void loadFile() {
		String filePath = jtFilePath.getText();
		String format = (String) jcExportFormat.getSelectedItem();
		try {
			contactUiData = cm.loadContact(filePath, format);
			setUiLabelData(contactUiData);
		} catch (FileNotFoundException ex) {
			System.out.println("File Not Found");
			ex.printStackTrace();
		} catch (StreamCorruptedException ex) {
			System.out.println("Cannot load the filepath: " + jtFilePath.getText() + " Check selected item of format.");
		} catch (IOException ex) {
			System.out.println("Error: Check selected item of format" + ex);
			ex.printStackTrace();
		}

	}

	public void setUiTextField(String[] contactUiData) {
		jtFirstName.setText(contactUiData[0]);
		jtLastName.setText(contactUiData[1]);
		jtPhoneNamber.setText(contactUiData[2]);

	}

	public void setUiLabelData(String[] contactUiData) {
		jlFirstName.setText(contactUiData[0]);
		jlLastName.setText(contactUiData[1]);
		jlPhoneNumber.setText(contactUiData[2]);

	}

	public void generalProperties() {
		pack();
		setTitle("Contcts manager");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setAlwaysOnTop(true);
		setSize(400, 500);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public void init() {

		jlFirstName.setText("34234");
		jlLastName.setText("432424");
		jlPhoneNumber.setText("324234");

	}

}
