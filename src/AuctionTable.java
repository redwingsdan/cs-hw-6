/**
 * Dan Peterson
 * 109091561
 * daniel.peterson@stonybrook.edu
 * Homework #6
 * CSE 214 Recitation #5
 * Sun Lin
 * @author Dan
 */

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Enumeration;
import java.util.Hashtable;

import org.apache.commons.lang3.ArrayUtils;

import big.data.DataSource;

/**
 * The AuctionTable class creates a HashTable to hold all of the Auction objects. Each object has a unique key which
 * is given by the Auction's identification number. The initial Auctions will be given by an XML file which is read
 * by the function buildFromURL, which parses the XML data. There are functions to place Auctions and get Auctions 
 * from the table, print all the Auctions from the table, remove all Auctions with a time remaining of 0 hours, and 
 * a function to decrement time from all of the Auctions in the table. When the program ends, the AuctionTable is 
 * saved as a file and is able to be loaded in when the program is run again. This way, all Auctions and their status'
 * are persistent.
 */

public class AuctionTable implements Serializable{
	
	
	static Hashtable<String, Auction> auctions = new Hashtable<String, Auction>();
	
	public AuctionTable()
	{
		AuctionTable.auctions = new Hashtable<String, Auction>();
	}
	
	/**
	 * Creates an instance of <code>AuctionTable</code> as a HashTable - Constructor
	 * @param str = Determines the length of the Hashtable - String []
	 */
	public AuctionTable(String[] str)
	{
		Hashtable<Object, Object> auctions2 = new Hashtable<Object, Object>(str.length);
	}

	/**
	 * Reads a URL and then parses through the data in order to place relevant information into the Hashtable.
	 * @param URL = The URL from which the data will be read - String
	 * @return auctiontable = The Hashtable which holds the parsed data from the URL - AuctionTable
	 * @throws IllegalArgumentException - Indicates that the URL does not contain valid data or does not exist.
	 */
	public static AuctionTable buildFromURL(String URL) throws IllegalArgumentException
	{
		DataSource ds = DataSource.connect(URL).load();
		
		AuctionTable auctiontable = new AuctionTable();
		String[] str = ds.fetchStringArray("listing/seller_info/seller_name");
		String[] str2 = ds.fetchStringArray("listing/auction_info/current_bid");
		String[] str3 = ds.fetchStringArray("listing/auction_info/time_left");
		String[] str4 = ds.fetchStringArray("listing/auction_info/high_bidder/bidder_name");
		String[] str5 = ds.fetchStringArray("listing/auction_info/id_num");
		String[] str7 = ds.fetchStringArray("listing/item_info/memory");
		String[] str8 = ds.fetchStringArray("listing/item_info/hard_drive");
		String[] str9 = ds.fetchStringArray("listing/item_info/cpu");
		//System.out.println("WE MADE IT HERE");
		Boolean flag = false;
		Auction a = null;
		int[] three = new int[str.length];
		double[] two = new double[str.length];
		//String[] str10= ds.fetchStringArray("listing/item_info/brand");
		//String[] str6 = (String[]) ArrayUtils.addAll(str7, str8, str9);
		//PARSE STRING
		
		if(ds.toString().contains(".xml") || ds.toString().contains(".com"))
		{
			for(int i = 0; i < str.length; i++)
			{
				String[] hrs = str3[i].split(" ");
				if(hrs[i].contains("day"))
				{
					three[i] = three[i] + Integer.parseInt(hrs[i-1]) * 24;
				}
				if(hrs[i].contains("hour") || hrs[i].contains("hrs"))
				{
					three[i] = three[i] + Integer.parseInt(hrs[i-1]);
				}
			}
			for(int i = 0; i < str.length; i++)
			{
			    String bid = str2[i].substring(1,str2[i].length()-1);
	            String[] bid2 = bid.split(",");
	            String currentbid = "";
	            try{
	            currentbid = currentbid + bid2[i];
	            }
	            catch(Exception e)
	            {
	            	
	            }
	            try{
	            two[i] = Double.parseDouble(currentbid);
	            }
	            catch(Exception e)
	            {
	            	two[i] = 0.00;
	            }
			}
			
			for(int i = 0; i < str7.length; i++)
			{
				String str6 = str7[i] + " " + str8[i] + " " + str9[i];
				//three = Integer.parseInt(str3[i]);
				if(str6 == null)
				{
					str6 = "N/A";
				}
				String[] str10 = null;
				try
				{
				str10[i] = str6;
				}
				catch(NullPointerException e)
				{
					a = new Auction(str[i], two[i], three[i], str4[i], "N/A", str5[i]);
					flag =true;
				}
				if(flag == false)
				{
					a = new Auction(str[i], two[i], three[i], str4[i], str10[i], str5[i]);
				}
				try
				{
				auctiontable.putAuction(a.getAuctionID(), a);
				}
				catch(Exception e)
				{
					
				}
				auctions.put(a.getAuctionID(), a);
			}
		}
		else
		{
			throw new IllegalArgumentException("Invalid file name.");
		}
		return auctiontable;
	}
	
	/**
	 * Adds a new Auction to the AuctionTable with a given key.
	 * @param auctionId = The number used to identify the auction and acts as the key for the AuctionTable - String
	 * @param a = The Auction which is added to the Hashtable - Auction
	 * @throws IllegalArgumentException - Indicates that the provided auctionId currently exists within the AuctionTable.
	 */
	public void putAuction(String auctionId, Auction a) throws IllegalArgumentException
	{
		Boolean flag = false;
		try{
			if(auctions.containsKey(auctionId))
			{
				flag = true;
				throw new IllegalArgumentException();
			}
		}
		catch(NullPointerException e)
		{
			
		}
		if(flag == false)
		{
			auctions.put(auctionId, a);
		}
	}
	
	/**
	 * Retrieves an Auction from the Hashtable with the provided auctionID as the key
	 * @param auctionID = The number used to identify the auction and acts as the key for the AuctionTable - String
	 * @return x = The Auction which is identified by the given auctionID
	 */
	public Auction getAuction(String auctionID)
	{
		//search hash table for ID
		Auction x = (Auction) auctions.get(auctionID);
		if(x != null)
		{
			return x;
		}
		else
		{
			throw new NullPointerException("The value with the provided key does not exist.");
		}
	}
	
	/**
	 * Decrements the timeRemaining value from all of the Auctions. If the value of any goes below zero, the decrement will not take place
	 * @param numHours = The time which will be decremented from the timeRemaining value - int
	 * @throws IllegalArgumentException - Indicates that the decrementation of time will result in a negative time value
	 */
	public void letTimePass(int numHours) throws IllegalArgumentException
	{
		//Iterate through table
		Enumeration<Auction> e = auctions.elements();
		Auction x = new Auction();
		while(e.hasMoreElements() == true)
		{
			x = (Auction) e.nextElement();
			if((x.getTimeRemaining() - numHours) < 0)
			{
				throw new IllegalArgumentException();
			}
			else
			{
				x.decrementTimeRemaining(numHours);
			}
		}

	}
	
	/**
	 * Removes every Auction which has a timeRemaining value of 0.
	 */
	public void removeExpiredAuctions()
	{
		//Iterate through table
		Enumeration e = auctions.elements();
		Auction x = new Auction();
		while(e.hasMoreElements() == true)
		{
			x = (Auction) e.nextElement();
			if(x.getTimeRemaining() == 0)
			{
				auctions.remove(x.getAuctionID());
			}
		}
	}
	
	/**
	 * Prints a formatted table of all of the Auctions present in the HashTable.
	 */
	public void printTable()
	{
		String msg = " Auction ID |      Bid   |          Seller        |         Buyer          |    Time    |  Item Info\n";
		msg = msg + "===============================================================================================================================";
		//iterate through table
		System.out.println(msg);
		Enumeration e = auctions.elements();
		Auction x = new Auction();
		while(e.hasMoreElements() == true)
		{
			x = (Auction) e.nextElement();
			System.out.println(x.toString());
		}
	}
}
