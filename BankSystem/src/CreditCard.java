//Design Programming Project 2 
//Brenda Garcia
//CSIS 3810 - Operating Systems Concepts 
//Dr. Gregory Simco
//Due: November 17th, 2021

import java.util.concurrent.Semaphore;

public class CreditCard {
	double outstandingBalance = 0;
	final double creditLimit = 1500; //random CC limit to spend
	private Semaphore semaphore = new Semaphore(1, true);


	public CreditCard(double o_bal) {
		this.outstandingBalance = o_bal;
	}

	public double getOutstandingBalance() {
		return this.outstandingBalance;
	}

	public void setOutstandingBalance(double o_bal) {
		this.outstandingBalance = o_bal;
	}

	public double availableCredit() {
		double temp = getOutstandingBalance();
		double credit = creditLimit - temp;
		return credit;
	}

	public void useCreditCard(double amount) { //spend money using credit card
		try {
			semaphore.acquire();
			double temp = getOutstandingBalance();
			temp += amount;

			if(temp > creditLimit)
			{
				System.out.println("Not enough credit! Outstanding balance in credit card is : " + outstandingBalance + " and the amount being spent is " + amount);
				return;
			}
			setOutstandingBalance(temp);
			System.out.println("After paying with credit card, current outstanding balace = $" + temp);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		semaphore.release();
		
	}

	public void payCreditCard(double amount) { //pay the outstanding balance in credit card
		try {
			semaphore.acquire();
			double temp = getOutstandingBalance();
			temp -= amount;

			if(temp == 0)
				System.out.println("After paying credit card, remaining balace = $" + temp + ". Your credit card is paid off!");
			else
				System.out.println("After paying credit card, remaining balace = $" + temp);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		semaphore.release();
	}
}
