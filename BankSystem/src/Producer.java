//Design Programming Project 2 
//Brenda Garcia
//CSIS 3810 - Operating Systems Concepts 
//Dr. Gregory Simco
//Due: November 17th, 2021

import java.util.concurrent.Semaphore;

public class Producer extends Thread{
	private double amount = 0;
	private String producer;
	BankAccount bankAccount;
	private Semaphore semaphore = new Semaphore(1, true);
	
	//constructor that takes the name of the producer and the amount being deposited
	Producer(String producer, BankAccount bankAccount, double amount){
		this.bankAccount = bankAccount;
		this.amount = amount;
		this.producer = producer;
	}
	
	@Override
	public void run() { //run the thread
		try {			
			System.out.println(producer + " acquiring...");
			semaphore.acquire(); //acquire semaphore
			try {
				if (producer == "Financial Aid") {
					FinancialAid();
				}
				else if(producer == "Job") {
					Thread.sleep(1500);
					Job();
				}
				else if(producer == "Birthday Gift") {
					Thread.sleep(2625);
					System.out.println("It's your BIRTHDAY and your mom gifted you $" + amount);
					bankAccount.credit(amount);
				}
				else if(producer == "Found Money") {
					Thread.sleep(15625);
					System.out.println("You found money on the street! $" + amount + " will be deposited to your bank account");
					bankAccount.credit(amount);
				}
				else {
					System.out.println(producer  + " will deposit $" + amount + " into the bank account");
					bankAccount.credit(amount);
				}
			} finally {
				System.out.println(producer  + " releasing...");
				semaphore.release(); //release semaphore

			}
			
		}catch (InterruptedException e) {
			e.printStackTrace();
			
		}
	}
	public void FinancialAid() { //this function will deposit Financial aid to bank card
		String semester = "";
		
		for(int i = 1; i <= 3; i++) {
			try {
				
				switch(i) {
				case 1:
					semester = "Fall Semester";
					break;
				case 2:
					semester = "Winter Semester";
					break;
				case 3:
					semester = "Summer Semester";
					break;
				default:
					semester = "Invalid Semester"; //code should never reach this point
				}
				
				System.out.println("\n" + semester + ": " + producer  + " will deposit $" + amount + " into the bank account");
				bankAccount.credit(amount); //deposit amount to card
				Thread.sleep(6000); //simulates length of semester
				System.out.println("\nBank Account balance at the end of " + semester + " : $" + bankAccount.getBalance());
				

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void Job() { //this function will deposit money from job paycheck 2 times a month  
		String monthCount = "";
		String pay = "";
		
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

				for (int k = 1; k <= 2; k++) {
					switch (k) {
					case 1:
						pay = "1st paycheck of the month";
						break;
					case 2:
						pay = "2nd paycheck of the month";
						break;
					default:
						monthCount = "Invalid!"; // code will not reach this part
					}
					try {
						System.out.println(
								monthCount + ": " + producer + " " + pay + "will deposit $" + amount);
						bankAccount.credit(amount);
						Thread.sleep(250); //simulate time span between paychecks 

					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				try {
					Thread.sleep(500); //simulate time between last paycheck and the next paycheck of the next month
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			try {
				Thread.sleep(1000); //simulate time between last paycheck and the next paycheck of the semester
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
