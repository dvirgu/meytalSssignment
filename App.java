// Meytal Guetta ID-313289845
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JFrame;

public class App {

	public static void main(String[] args) {
		ContactsManager cm;
		try {
			cm = new ContactsManager("contacts.dat");
			ContactsManagerFrame cmf = new ContactsManagerFrame(cm);
			cmf.init();
		} catch (FileNotFoundException e) {
			System.out.println("The file not found");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Input or Output operation is failed");
			e.printStackTrace();
		}
	}

}
