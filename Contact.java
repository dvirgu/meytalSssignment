import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.io.Serializable;

public class Contact implements IContact {
	public static final int SIZE_OF_FIELD = 10;
	public static final int NUM_OF_FIELDS = 4;
	public static final int SIZE_OF_BYTE = 2;
	private static int counter = 1;
	private int id;
	private String firstName;
	private String lastName;
	private String phoneNumber;

	public Contact(String firstName, String lastName, String phoneNumber) {
		super();
		id = counter++;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public static void setCounterId(int counter) {
		Contact.counter = counter;
	}

	public static int getCounterId() {
		return counter;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Override
	public void writeObject(RandomAccessFile randomAccessFile) throws IOException {
		randomAccessFile.seek(randomAccessFile.length());
		FixedLengthStringIO.writeFixedLengthString("  " + this.getId(), SIZE_OF_FIELD, randomAccessFile);
		FixedLengthStringIO.writeFixedLengthString(this.getFirstName(), SIZE_OF_FIELD, randomAccessFile);
		FixedLengthStringIO.writeFixedLengthString(this.getLastName(), SIZE_OF_FIELD, randomAccessFile);
		FixedLengthStringIO.writeFixedLengthString(this.getPhoneNumber(), SIZE_OF_FIELD, randomAccessFile);

	}

	public void updatObject(RandomAccessFile randomAccessFile) throws IOException {
		FixedLengthStringIO.writeFixedLengthString(this.getFirstName(), SIZE_OF_FIELD, randomAccessFile);
		FixedLengthStringIO.writeFixedLengthString(this.getLastName(), SIZE_OF_FIELD, randomAccessFile);
		FixedLengthStringIO.writeFixedLengthString(this.getPhoneNumber(), SIZE_OF_FIELD, randomAccessFile);

	}

	@Override
	public void export(String format, File file) throws IOException {
		switch (format) {

		case "txt":
			PrintWriter pw = new PrintWriter(file);
			pw.println(this.id);
			pw.println(this.firstName);
			pw.println(this.lastName);
			pw.println(this.phoneNumber);
			pw.close();
			break;

		case "obj.dat":
			ObjectOutputStream outputObject = new ObjectOutputStream(new FileOutputStream(file));
			outputObject.writeUTF(this.id + "");
			outputObject.writeUTF(this.firstName);
			outputObject.writeUTF(this.lastName);
			outputObject.writeUTF(this.phoneNumber);

			outputObject.close();
			break;
		case "byte.dat":
			DataOutputStream outputData = new DataOutputStream(new FileOutputStream(file));
			outputData.writeUTF(this.id + "");
			outputData.writeUTF(this.firstName);
			outputData.writeUTF(this.lastName);
			outputData.writeUTF(this.phoneNumber);

			outputData.close();

			break;
		default:
			break;
		}

	}

	@Override
	public String[] getUiData() {
		String[] objctData = { getFirstName(), getLastName(), getPhoneNumber() };
		return objctData;
	}

	public static int getObjectSize() {
		int objectSize = NUM_OF_FIELDS * SIZE_OF_FIELD * SIZE_OF_BYTE;
		return objectSize;
	}

	public static int getFieldSize() {
		int fieldSize = SIZE_OF_FIELD;
		return fieldSize;
	}

}
