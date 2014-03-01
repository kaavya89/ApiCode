package ZapposSrc;
import java.net.*;
import java.util.*;
import java.io.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.*;

public class ApiConnect {
	//constants and variables used.
	public static String url = "http://api.zappos.com/Search?limit=100&page=";
	public static final String key = "&key=a73121520492f88dc3d33daf2103d7574f1a3166";
	private JSONParser parser;
	private JSONObject json;
	private Object obj;
	private URL newconnection;
	private Scanner sc;
	private int num;
	private double sum;
	PrintWriter out;
	public ApiConnect(int num,double sum,PrintWriter out)
	{
		parser = new JSONParser();		
		this.num = num;
		this.sum=sum;
		this.out = out;
	}	
	public void main() {
		try
		{							
			String str;
			//Set this to 5. Can be changed. I did not want to waste the calls and hence set the value to 5. 
			//Get 5 responses and processes the data
			for(int i=0;i<5;i++)
			{
				str="";
				newconnection = new URL(url+Integer.toString(i+1)+key);
				sc = new Scanner(newconnection.openStream());	
				while(sc.hasNext())
				{
					str+=sc.nextLine();
				}	
				//If the response is not 401 then creates an object to Results class and the procedure starts otherwise it prints error.
				if(!str.contains("401"))
				{
					Results object = new Results(num,sum,out);				
					obj = parser.parse(str.toString());
					json = (JSONObject)obj;			
					object.setResponseList((JSONArray) json.get("results"));
					object.preprocess();
					object.print();
				}
				else
					out.println("Request cannot be processed at this moment. Please try after sometime");
			}
			}catch(Exception e){
			out.println(e.getMessage());
		}
	}
}
