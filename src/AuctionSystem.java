/**
 * Dan Peterson
 * 109091561
 * daniel.peterson@stonybrook.edu
 * Homework #6
 * CSE 214 Recitation #5
 * Sun Lin
 * @author Dan
 */

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Hashtable;
import java.util.Scanner;


public class AuctionSystem implements Serializable{

	public static void main(String[] args)
	{
		AuctionTable a = new AuctionTable();
		String username = null;
		String choice = null;
		boolean flag = false;
		String url = null;
		int hours = 0;
		Hashtable b = null;
		
		FileInputStream fileIn= null;
	       try {
			fileIn = new FileInputStream("auction.obj");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("File not found. Using new AuctionTable.");
		}
	       ObjectInputStream in = null;
	       try {
			in = new ObjectInputStream(fileIn);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			System.out.println("File not found. Using new AuctionTable.");
		}
	       try {
			b = (Hashtable) in.readObject();
			System.out.println("AuctionTable loaded in.");
			//a = b;
			AuctionTable.auctions = b;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("File not found. Using new AuctionTable.");
		}
		
		System.out.print("Enter a username: ");
		Scanner input = new Scanner(System.in);
		Scanner input2 = new Scanner(System.in);
		Scanner input3 = new Scanner(System.in);
		username = input.nextLine();
		System.out.print("\nMenu:\n\t(D) - Import Data from URL\n\t(A) - Create a New Auction");
		System.out.print("\n\t(B) - Bid on an Item\n\t(I) - Get Info on Auction\n\t(P) - Print All Auctions");
		System.out.print("\n\t(R) - Remove Expired Auctions\n\t(T) - Let Time Pass\n\t(Q) - Quit\n");
		while(flag == false)
		{
			System.out.print("Please select an option: ");
			choice = input.nextLine();
			switch(choice.toLowerCase())
			{
			case "d":
				System.out.print("Enter a URL: ");
				url = input.nextLine();
				System.out.println("Loading...");
			//	try
			//	{
					AuctionTable.buildFromURL(url);
					System.out.println("Auction data loaded successfully!");
			//	}
			//	catch(Exception e)
			//	{
					System.out.println(url + " does not exist or could not be read.");
			//	}
				break;
				
			case "a":
				System.out.println("Creating new Auction as " + username);
				System.out.print("Please enter an Auction ID: ");
				while(!input2.hasNextInt())
				{
					System.out.println("Invalid input, try again.");
					System.out.print("Please enter an Auction ID: ");
					input2.nextLine();
				}
				int id = input2.nextInt();
				String idNum = id + "";
				System.out.print("Please enter an Auction time (hours): ");
				while(!input2.hasNextInt())
				{
					System.out.println("Invalid input, try again.");
					System.out.print("Please enter an Auction time (hours): ");
					input2.nextLine();
				}
				int time = input2.nextInt();
				System.out.print("Please enter some Item Info: ");
				String info = input.nextLine();
				Auction auction = new Auction(username, 0.00, time, "N/A", info, idNum);
				a.putAuction(auction.getAuctionID(), auction);
				System.out.println("Auction " + idNum + " inserted into table.");
				break;
				
			case "b":
				System.out.print("Please enter an Auction ID: ");
				while(!input2.hasNextInt())
				{
					System.out.println("Invalid input, try again.");
					System.out.print("Please enter an Auction ID: ");
					input2.nextLine();
				}
				int id1 = input2.nextInt();
				String idNum1 = id1 + "";
				try{
					Auction x = a.getAuction(idNum1);
					if(x.getTimeRemaining() > 0)
					{
						System.out.println("Auction " + idNum1 + " is OPEN\n\tCurrent Bid: $ " + x.getCurrentBid());
						System.out.print("\nWhat would you like to bid?: ");
						while(!input3.hasNextDouble())
						{
							System.out.println("Invalid input, try again.");
							System.out.print("\nWhat would you like to bid?: ");
							input3.nextLine();
						}
						double bid = input3.nextDouble();
						if(bid > x.getCurrentBid())
						{
							try 
							{
								a.getAuction(idNum1).newBid(username, bid);
							} 
							catch (ClosedAuctionException e) 
							{	
								e.getMessage();
								break;
							}
							System.out.print("Bid accepted.");
						}
						else
						{
							System.out.print("Bid is lower than current bid. Bid not accepted.");
						}
					}
					else
					{
						System.out.println("Auction " + idNum1 + " is CLOSED");
					}
				}
				catch(NullPointerException e)
				{
					System.out.print(e.getMessage());
					//System.out.println("Auction " + idNum1 + " is CLOSED");
				}
				break;
				
			case "i":
				System.out.print("Please enter an Auction ID: ");
				while(!input2.hasNextInt())
				{
					System.out.println("Invalid input, try again.");
					System.out.print("Please enter an Auction ID: ");
					input2.nextLine();
				}
				int id2 = input2.nextInt();
				String idNum2 = id2 + "";
				try
				{
					Auction x = a.getAuction(idNum2);
					if(x != null)
					{
						System.out.println("Auction " + a.getAuction(idNum2).getAuctionID() + ":");
						System.out.println("\tSeller: " + a.getAuction(idNum2).getSellerName());
						System.out.println("\tBuyer: " + a.getAuction(idNum2).getBuyerName());
						System.out.println("\tTime: " + a.getAuction(idNum2).getTimeRemaining());
						System.out.println("\tInfo: " + a.getAuction(idNum2).getItemInfo());
						
					}
				}
				catch(Exception e)
				{
					System.out.println(e.getMessage());
				}
				break;
				
			case "p":
				a.printTable();
				break;
				
			case "r":
				System.out.println("Removing expired auctions...");
				a.removeExpiredAuctions();
				System.out.println("All expired auctions removed.");
				break;
				
			case "t":
				System.out.print("How many hours should pass: ");
				while(!input2.hasNextInt())
				{
					System.out.println("Invalid input, try again.");
					System.out.print("Please enter an Auction ID: ");
					input2.nextLine();
				}
				hours = input2.nextInt();
				try
				{
					a.letTimePass(hours);
					System.out.println("Time passing...");
					System.out.println("Auction times updated.");
				}
				catch(IllegalArgumentException e)
				{
					System.out.println("Could not decrement time.");
				}
				break;
				
			case "q":
				flag = true;
				break;
			default:
					
				System.out.println("Invalid input, please try again.");	
				break;
			}
			
			System.out.println();
		}
			//a = (AuctionTable)AuctionTable.auctions;
	       FileOutputStream fileOut = null;
	       try {
			fileOut = new FileOutputStream("auction.obj");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	       ObjectOutputStream out = null;
	       try {
			out = new ObjectOutputStream(fileOut);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	       try {
			out.writeObject(AuctionTable.auctions);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	       System.out.printf("Serialized data is saved in auction.obj\n");
		
		System.out.println("Program terminating successfully");
	}
	
}
