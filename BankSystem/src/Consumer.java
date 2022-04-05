//Design Programming Project 2 
//Brenda Garcia
//CSIS 3810 - Operating Systems Concepts 
//Dr. Gregory Simco
//Due: November 17th, 2021

import java.util.concurrent.Semaphore;

public class Consumer extends Thread{
	private double amount;
	private String name;
	BankAccount bankAccount;
	CreditCard creditAccount;
	private Semaphore semaphore = new Semaphore(1, true);

	//constructor for the amount being withdrawn from bank account
	Consumer(String name, BankAccount bankAccount,double amount){
		this.bankAccount = bankAccount;
		this.amount = amount;
		this.name = name;
	}

	//constructor for amount being charged to credit charged and maybe deposited in bank account (lottery case)
	Consumer(String name, BankAccount bankAccount, CreditCard creditAccount,double amount){
		this.bankAccount = bankAccount;
		this.creditAccount = creditAccount;
		this.amount = amount;
		this.name = name;
	}

	@Override
	public void run() { //run the thread
		try {
			System.out.println(name + " acquiring...");
			semaphore.acquire(); //acquire semaphore
			try {
				String consumer = name;

				Thread.sleep(1000); //sleep to simulate the way in which financial aid gets deposited first

				if (consumer == "Tuition" || consumer == "Books" || consumer == "Student Fees") {
					CollegeExpenses();
				} else if (consumer == "Dorm" || consumer == "Food Bill") {
					DormAndFood();
				} else if (consumer == "Laptop") {
					Thread.sleep(8625);
					System.out.println(consumer + " will add $" + amount + " to your credit card");
					creditAccount.useCreditCard(amount);
				} else if (consumer == "Lottery") {
					System.out.println(consumer + " will add $" + amount + " to your credit card");
					System.out.println("Waiting to see what you won...");
					creditAccount.useCreditCard(amount);
					Lottery();
				} else {
					System.out.println(consumer + " will withdraw $" + amount + " from the bank account");
					bankAccount.withdraw(amount);
				}
			} finally {
				System.out.println(name + " releasing...");
				semaphore.release(); //release semaphore
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void CollegeExpenses() { // this function will deduct tuition, books, and fees at the start of each semester. Will run 3 times.
		try {
			for (int i = 1; i <= 3; i++) {
				System.out.println(name + " will withdraw $" + amount + " from the bank account");
				bankAccount.withdraw(amount); //withdraw money from bank account
				Thread.sleep(6000); //sleep to simulate the length it takes for tuition, books, and fees to be charged each semester.
			}
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}

	}
	public void DormAndFood() { // this function will deduct dorm and food bills. Will run 5 times each semester.
		String monthCount = "";
		try {
			for (int i = 1; i <= 3; i++) {
				for (int j = 1; j <= 5; j++) {
					switch (j) {
					case 1:
						monthCount = "1st Month";
						break;
					case 2:
						monthCount = "2nd Month";
						break;
					case 3:
						monthCount = "3rd Month";
						break;
					case 4:
						monthCount = "4th Month";
						break;
					case 5:
						monthCount = "5th Month";
						break;
					default:
						monthCount = "Invalid Month"; // code will not reach this part
					}
					if (j == 1) { //the 1st month should get deducted along with the charges of the start of the semester, so no sleep!
						System.out.println(
								monthCount + ": " + name + " will withdraw $" + amount + " from the bank account");
						bankAccount.withdraw(amount);
					} else {
						try {
							Thread.sleep(1000); //sleep simulates the time span from month to month
							System.out.println(
									monthCount + ": " + name + " will withdraw $" + amount + " from the bank account");
							bankAccount.withdraw(amount);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
				Thread.sleep(2000);//sleep simulates the time span between the last month of the semester and the first month of charges.
			}
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}

	}

	//User decision
	public void Lottery() { // this function will deduct lottery ticket price, if user wins, the money will get deposited in his/her account.
		int min = 0; //the min amount is not winning anything
		int max = 2000; //the max amount is the maximum lottery prize

		int random_int = (int) Math.floor(Math.random() * (max - min + 1) + min); //random prize

		if (random_int == 0) //if user does not win anything with his lottery ticket
			System.out.println("Oh no! You did not win anything");
		else { //if user wins random prize, the money gets deposited
			System.out.println("Congrats! You just won $" + random_int);
			System.out.println("Your prize money will be deposited in your bank account");
			bankAccount.credit(random_int);

		}
	}
}
