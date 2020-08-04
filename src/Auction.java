/**
 * Dan Peterson
 * 109091561
 * daniel.peterson@stonybrook.edu
 * Homework #6
 * CSE 214 Recitation #5
 * Sun Lin
 * @author Dan
 */

import java.io.Serializable;


/**
 * This class creates an object of the type Auction. This class gives each Auction object a seller, a buyer,
 * a bid amount, an amount of time for the auction, information of the auction, and an identification number.
 * The Auction can have a new bid added which will replace the buyer and the current bid if the new bid is greater 
 * than the current bid. There are also functions to print a string representation of the entire Auction as well as
 * decrement the time left for the Auction.
 */

public class Auction implements Serializable{

	private String sellerName;
	private double currentBid;
	private int timeRemaining;
	private String buyerName;
	private String itemInfo;
	private String auctionID;
	
	public Auction()
	{
		sellerName = null;
		currentBid = 0.0;
		timeRemaining = 0;
		buyerName = null;
		itemInfo = null;
		auctionID = null;
	}
	
	/**
	 * Creates an instance of <code>Auction</code> - Constructor
	 * @param sellerName = The name of the seller for this Auction - String
	 * @param currentBid = The highest bid which has been placed on this Auction - double
	 * @param timeRemaining = The time (in hours) until the Auction ends - int
	 * @param buyerName = The name of the buyer with the highest bid for this Auction - String
	 * @param itemInfo = The list of information given for this object - String
	 * @param auctionID = The identification number to specify which auction it is - String
	 */
	public Auction(String sellerName, double currentBid, int timeRemaining, String buyerName, String itemInfo, String auctionID)
	{
		this.sellerName = sellerName;
		this.currentBid = currentBid;
		this.timeRemaining = timeRemaining;
		this.buyerName = buyerName;
		this.itemInfo = itemInfo;
		this.auctionID = auctionID;
	}
	
	/**
	 * Returns the value of <code>sellerName</code> - String
	 * @return sellerName - String
	 */
	public String getSellerName()
	{
		return sellerName;
	}
	
	/**
	 * Returns the value of <code>currentBid</code> - double
	 * @return currentBid - double
	 */
	public double getCurrentBid()
	{
		return currentBid;
	}
	
	/**
	 * Returns the value of <code>timeRemaining</code> - int
	 * @return timeRemaining - int
	 */
	public int getTimeRemaining()
	{
		return timeRemaining;
	}
	
	/**
	 * Returns the value of <code>buyerName</code> - String
	 * @return buyerName - String
	 */
	public String getBuyerName()
	{
		return buyerName;
	}
	
	/**
	 * Returns the value of <code>itemInfo</code> - String
	 * @return itemInfo - String
	 */
	public String getItemInfo()
	{
		return itemInfo;
	}
	
	/**
	 * Returns the value of <code>auctionID</code> - String
	 * @return auctionID - String
	 */
	public String getAuctionID()
	{
		return auctionID;
	}
	
	/**
	 * Decrements the value of <code>timeRemaining</code> by the int value passed in.
	 * @param time = Time to be decremented from the timeRemaining value - int
	 */
	public void decrementTimeRemaining(int time) throws IllegalArgumentException
	{
			this.timeRemaining = this.timeRemaining - time;
	}
	
	/**
	 * Creates a new bid for the given Auction. If the bid is lower than the current bid, then the bid does not get replaced.
	 * @param bidderName = The name of the person bidding on the Auction - String
	 * @param bidAmt = The value of the new bid being placed - double
	 * @throws ClosedAuctionException - Indicates that the timeRemaining value for the auction is 0. No bids can be placed
	 */
	public void newBid(String bidderName, double bidAmt) throws ClosedAuctionException
	{
		if(timeRemaining > 0)
		{
			if(bidAmt > currentBid)
			{
				currentBid = bidAmt;
				buyerName = bidderName;
			}
			else
			{
				System.out.println("Current bid is greater than this bid.");
			}
		}
		else
		{
			throw new ClosedAuctionException("Auction is closed. Cannot place bid.");
		}
	}
	
	/**
	 * Returns the string representation of the current Auction - String
	 * @return msg1 - String
	 */
	public String toString()
	{
		//String msg = " Auction ID |      Bid   |          Seller        |         Buyer          |    Time    |  Item Info\n";
		//msg = msg + "===============================================================================================================================";
		String msg1 = " " + auctionID + " |  $ " + currentBid + "\t | " + sellerName + "\t\t |  " + buyerName + "\t\t | " + timeRemaining + " hours \t|  " + itemInfo; 
		return msg1;
	}
}
