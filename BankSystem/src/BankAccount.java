//Design Programming Project 2 
//Brenda Garcia
//CSIS 3810 - Operating Systems Concepts 
//Dr. Gregory Simco
//Due: November 17th, 2021

import java.util.concurrent.Semaphore;

public class BankAccount {
	double balance = 0;
	private Semaphore semaphore = new Semaphore(1, true);

	public BankAccount(double bal) {
		this.balance = bal;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}
	
	public void credit(double amount) { //deposit money
		try {
			semaphore.acquire();
			double temp = getBalance();
			temp += amount;
			setBalance(temp);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		semaphore.release();
	}

	public void withdraw(double amount) { //withdraw money
		try {
			semaphore.acquire();
			if (balance < amount) {
				System.out.println("Not enough funds! Balance in account is : " + balance
						+ " and the withdrawal being made is " + amount);
				return;
			}
			double temp = getBalance();
			temp -= amount;
			setBalance(temp);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		semaphore.release();

	}
}
