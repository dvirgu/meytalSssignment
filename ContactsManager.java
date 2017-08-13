import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Scanner;

public class ContactsManager {
	private RandomAccessFile raf;

	public ContactsManager(String fileName) throws IOException {

		raf = new RandomAccessFile(fileName, "rw");

	}

	public RandomAccessFile getRaf() {
		return raf;
	}

	public void setRaf(RandomAccessFile raf) {
		this.raf = raf;
	}

	public void writeContact(String firstName, String lastName, String phoneNumber) throws IOException {
		Contact contact = new Contact(firstName, lastName, phoneNumber);
		contact.writeObject(raf);
	}

	public void updateContact(String firstName, String lastName, String phoneNumber) throws IOException {
		raf.seek(raf.getFilePointer() + Contact.getFieldSize() * 2); // after
																		// Id****
		Contact contact = new Contact(firstName, lastName, phoneNumber);
		contact.updatObject(raf);
	}

	public int getContactSize() {

		return Contact.getObjectSize();
	}

	public String[] readObject(long position) throws IOException {
		raf.seek(position + Contact.getFieldSize() * 2);
		String firstName = FixedLengthStringIO.readFixedLengthString(Contact.getFieldSize(), raf);
		String lastName = FixedLengthStringIO.readFixedLengthString(Contact.getFieldSize(), raf);
		String phoneNumber = FixedLengthStringIO.readFixedLengthString(Contact.getFieldSize(), raf);
		Contact.setCounterId(Contact.getCounterId() - 1);
		Contact contact = new Contact(firstName, lastName, phoneNumber);
		return contact.getUiData();
	}

	public String exportContactAndGetFilePath(String format, String firstName, String lastName, String phoneNumber)
			throws IOException {
		String id = "" + raf.getFilePointer() / (Contact.getObjectSize());
		String filePath = id + "." + format;
		Contact contact = new Contact(firstName, lastName, phoneNumber);
		contact.export(format, new File(filePath));

		return filePath;
	}

	public String[] loadContact(String filePath, String format) throws FileNotFoundException, IOException {
		String id;
		String firstName;
		String lastName;
		String phoneNumber;
		switch (format) {

		case "txt":
			File f = new File(filePath);
			Scanner s = new Scanner(f);
			id = s.nextLine();
			firstName = s.nextLine();
			lastName = s.nextLine();
			phoneNumber = s.nextLine();
			String[] contactTxtData = { firstName, lastName, phoneNumber };

			return contactTxtData;

		case "obj.dat":
			ObjectInputStream obInputStr = new ObjectInputStream(new FileInputStream(filePath));
			id = obInputStr.readUTF();
			firstName = obInputStr.readUTF();
			lastName = obInputStr.readUTF();
			phoneNumber = obInputStr.readUTF();
			String[] contactDataObj = { firstName, lastName, phoneNumber };
			return contactDataObj;

		case "byte.dat":
			DataInputStream datInputStr = new DataInputStream(new FileInputStream(filePath));
			id = datInputStr.readUTF();
			firstName = datInputStr.readUTF();
			lastName = datInputStr.readUTF();
			phoneNumber = datInputStr.readUTF();
			String[] contactDataInputStr = { firstName, lastName, phoneNumber };
			return contactDataInputStr;

		default:
			break;
		}
		return null;

	}

	public long getCurrentPosition() throws IOException {

		return raf.getFilePointer();
	}

	public void goToPosition(long position) throws IOException {
		raf.seek(position);

	}

	public long lengthOfFile() throws IOException {
		return raf.length();
	}

}
