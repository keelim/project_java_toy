package ServerTest;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;

public class BankServer implements Serializable {
	private static final long serialVersionUID = 1L;
	public static void main(String[] args) {
		new BankServer();
	}
	private static Bank s_bank;
	private ServerSocket s;
	private Socket incoming;
	private ObjectInputStream readStream;
	private ObjectOutputStream writeStream;
	private Commandex readComm;
	private Commandex writeComm;
	static int requestedCommandValue;
	static String[] requestedCommandArgs;

	public BankServer() {
		s_bank = new Bank();
		/* test data */
		s_bank.addCustomer("°í°´1", "1", "111-1111", "female");
		s_bank.addCustomer("°í°´2", "2", "222-2222", "male");
		s_bank.addCustomer("°í°´3", "3", "333-3333", "female");
		s_bank.addCustomer("°í°´4", "4", "444-4444", "male");
		s_bank.addCustomer("°í°´5", "5", "555-5555", "female");
		s_bank.addCustomer("°í°´6", "6", "666-6666", "male");
		s_bank.addCustomer("°í°´7", "7", "777-7777", "female");
		s_bank.addCustomer("°í°´8", "8", "888-8888", "male");
		s_bank.addCustomer("°í°´9", "9", "999-9999", "female");

		double init_bal = 100.0;
		for (int i = 1; i <= s_bank.getNumOfCustomers(); i++) {
			s_bank.getCustomer(i - 1).addAccount(new CheckingAccount(
					"" + i + i + i + "-" + i + i + i + i + "-00" + (i - 1), init_bal + (i - 1) * 50));
			s_bank.getCustomer(i - 1).addAccount(new SavingsAccount("" + i + i + i + "-" + i + i + i + i + "-00" + (i),
					init_bal + (i * 50), 0. + (i + 1)));
		}

		try { //생성자 안에 서버를 실행하게 한다.
			s = new ServerSocket(12345);
			System.out.println("Server started");
			incoming = s.accept();
			if (incoming != null)
				System.out.println("Client connected...");

			readStream = new ObjectInputStream(incoming.getInputStream());
			writeStream = new ObjectOutputStream(incoming.getOutputStream());

			try {
				while (true) { // While handling 을 계속 실행을 한다.
					readComm = (Commandex) readStream.readObject();
					handling(readComm);
				}

			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

		} catch (IOException e) {
			System.out.println("Client disconnected...");
			// e.printStackTrace();
		} finally {
			try {
				readStream.close();
				writeStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public synchronized void handling(Commandex read) { // handling 을 이용하여 서버가 하는 일을 정할 것
		writeComm = new Commandex(0);
		requestedCommandValue = read.getCommandValue();
		requestedCommandArgs = read.getArgs();

		switch (read.getCommandValue()) {
		// ÀÔ±Ý
		case 10:

			s_bank.getCustomer(Integer.parseInt(requestedCommandArgs[0]))
					.getAccount(Integer.parseInt(requestedCommandArgs[1]))
					.deposit(Double.parseDouble(requestedCommandArgs[2]));
			writeComm.setStatus(1);
			writeComm.setBank(s_bank);
			break;

		// Ãâ±Ý
		case 20:
			s_bank.getCustomer(Integer.parseInt(requestedCommandArgs[0]))
					.getAccount(Integer.parseInt(requestedCommandArgs[1]))
					.withdraw(Double.parseDouble(requestedCommandArgs[2]));
			writeComm.setStatus(1);
			writeComm.setBank(s_bank);
			break;

		// °èÁÂÀÌÃ¼
		case 30:
			s_bank.getCustomer(Integer.parseInt(requestedCommandArgs[0]))
					.getAccount(Integer.parseInt(requestedCommandArgs[1]))
					.withdraw(Double.parseDouble(requestedCommandArgs[4]));
			s_bank.getCustomer(Integer.parseInt(requestedCommandArgs[2]))
					.getAccount(Integer.parseInt(requestedCommandArgs[3]))
					.deposit(Double.parseDouble(requestedCommandArgs[4]));
			writeComm.setStatus(1);
			writeComm.setBank(s_bank);
			break;

		// ÀºÇàÁ¤º¸
		case 99:
			writeComm.setStatus(1);
			writeComm.setBank(s_bank);
			break;
		}

		// Å¬¶óÀÌ¾ðÆ®¿¡°Ô Ã³¸®°á°ú¸¦ ¾Ë·ÁÁÜ
		try {
			writeStream.reset();
			writeStream.writeObject(writeComm);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


}
