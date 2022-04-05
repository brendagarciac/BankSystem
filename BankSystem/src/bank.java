//Design Programming Project 2 
//Brenda Garcia
//CSIS 3810 - Operating Systems Concepts 
//Dr. Gregory Simco
//Due: November 17th, 2021

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class bank {

	static double balance = 0; //the bank account starts out having $0 dollars
	static double outstandingBalance = 0; //the credit card starts out empty

	public static void main(String[] args) throws InterruptedException {

		//create a list of threads where all threads used in the system will be add
	    List<Thread> threadList = new ArrayList<Thread>();

	    //make an instance of each class
	    BankAccount bankAccount = new BankAccount(balance);
		CreditCard creditAccount = new CreditCard(outstandingBalance);

		//make an instance of Producer class for each thread, passing in the account
		//and the price of the amount that will be deposited into the account
		Producer finAid = new Producer("Financial Aid", bankAccount, 24310.0);
		Producer job = new Producer("Job", bankAccount, 972.0);
		Producer birthdayGift = new Producer("Birthday Gift", bankAccount, 100.00);
		Producer foundMoney = new Producer("Found Money", bankAccount, 50.00);

		//make an instance of Consumer class for each thread, passing in the account
		//and the price of the amount that will be charged to the account
		Consumer tuition = new Consumer("Tuition", bankAccount, 15380.0);
		Consumer books = new Consumer("Books", bankAccount, 1240.0);
		Consumer student_fees = new Consumer("Student Fees", bankAccount, 500.0);
		Consumer dorm = new Consumer("Dorm", bankAccount, 1178.0);
		Consumer foodBill = new Consumer("Food Bill", bankAccount, 260.0);

		//this time, the consumer is charging the credit card
		Consumer laptop = new Consumer("Laptop", bankAccount, creditAccount, 1300.0);
		Consumer lottery = new Consumer("Lottery", bankAccount, creditAccount, 30.0);

		//start each thread, and add it to the thread list

		finAid.start();
	    threadList.add(finAid);

		tuition.start();
	    threadList.add(tuition);

		books.start();
	    threadList.add(books);

		student_fees.start();
	    threadList.add(student_fees);

	    dorm.start();
	    threadList.add(dorm);

	    foodBill.start();
	    threadList.add(foodBill);

	    job.start();
	    threadList.add(job);

	    birthdayGift.start();
	    threadList.add(birthdayGift);

	    foundMoney.start();
	    threadList.add(foundMoney);

	    laptop.start();
	    threadList.add(laptop);

	    for(Thread t : threadList) {
	          // waits for this thread to die
	          t.join();
	    }

	    //User interaction at the end of academic year that asks for them to enter
	    //whether they want to play the lottery or not
		Scanner input = new Scanner(System.in);
		System.out.println("\nIt is the end of a stressful academic year and you did great!");
		System.out.println("So you are feeling lucky...");
		System.out.println("Do you want to buy a $30 lottery ticket with your credit card? Enter Y/N");

		String userInput = input.nextLine();

		//input validation for user choice
		while(userInput.equals("Y") == false && userInput.equals("N")== false) {
			System.out.println("Wrong input! Enter Y or N");
			userInput = input.next();
		}

		//If user decided to play, run the thread
		if (userInput.equals("Y")) {
			lottery.start();
			threadList.add(lottery);
			lottery.join();
		}

		input.close();

		//Print a final message that lets the user know information about the status of each account
		System.out.println("\nFinal Account Balance: $" + bankAccount.getBalance());
		System.out.println("\nFinal Credit Account Outstanding Balance: $" + creditAccount.getOutstandingBalance());
		System.out.println("\nYou have $" + creditAccount.availableCredit() + " available to spend with your credit card since credit limit is $" + creditAccount.creditLimit);

	}

}
