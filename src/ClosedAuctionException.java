import java.io.Serializable;


public class ClosedAuctionException extends Exception implements Serializable {

	public ClosedAuctionException(String string) 
	{
		System.out.println(string);
	}

}
