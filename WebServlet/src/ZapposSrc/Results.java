package ZapposSrc;
import java.util.*;
import java.io.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Results {	
	private double sum;
	private int num;
	private JSONParser parser; Object object; JSONObject jsonobj; JSONArray responseArray;PrintWriter out;//JSon parsers to get the result in required format.
	private static HashMap<Integer,Double> productPricePair = new HashMap<Integer,Double>(); // Stores product-pricepair
	private static HashMap<Integer,String> productURLPair = new HashMap<Integer,String>();	//stores product-url pair
	private static int COUNTER=0;
	private static HashMap<Integer,ArrayList<String>> productURLs = new HashMap<Integer,ArrayList<String>>(); //Stores the final list of products in groups
	private static ArrayList<Double> totalPrice = new ArrayList<Double>();//the price of those product groups in total
	public Results(int num,double sum,PrintWriter out)
	{		
		this.num = num;
		this.sum=sum;
		parser = new JSONParser();
		object = new Object();
		responseArray = new JSONArray();
		this.out=out;
	}
	//getters and setters for products,urls,ids
	public HashMap<Integer,Double> getProdPricePair()
	{
		return productPricePair;
	}
	public HashMap<Integer,String> getProductUrlPair()
	{
		return productURLPair;
	}
	public void setResponseList(JSONArray str)
	{
		this.responseArray=str;		
	}
	public JSONArray getResponseList(JSONArray str)
	{
		return this.responseArray;		
	}
	public Collection<Double> getList()
	{
		Collection<Double> l;		
		l=productPricePair.values();
		return l;
	}
	public Collection<String> getProductURLS()
	{
		Collection<String> l;		
		l=productURLPair.values();
		return l;
	}
	//this string reduces the entire reponse to individual tags that are needed. productID, productURL,price.
	public void preprocess()
	{		
		String temp,temp2;
		try
		{
			for(int i=0;i<responseArray.size();i++)
			{				
				object =  parser.parse(responseArray.get(i).toString());
				jsonobj = (JSONObject)object;
				temp = jsonobj.get("productId").toString();			
				temp2=jsonobj.get("price").toString().substring(1).replaceAll(",", "");	
				if(Double.valueOf(temp2)<=this.sum)
				{
					productPricePair.put(Integer.valueOf(temp),Double.valueOf(temp2));					
					productURLPair.put(Integer.valueOf(temp),jsonobj.get("productUrl").toString());
				}			
			}			
		}catch(Exception e){e.printStackTrace();}
		
	}		
	//So this function calls a recursive function to compute the nearest value and prints out the combination
	public void print()
	{		
		int productcount=0;
		try{
		getallelements(new ArrayList<Double>(getList()),sum,"","");
		
		out.println("<html><head>"
				+ "<head><style> hr {color:sienna;} body {background-color:rgb(132,112,255);"
				+"font-size:medium;"				
				+"background-repeat:no-repeat;"
				+"background-attachment:fixed;background-position:right bottom;}</style>");				
		if(totalPrice.size()==0)//When there are no combinations possible
			out.println("<h3>We are sorry, there are no suggested items in the price range your entered!!!</h3>");
		else//when they are combinations possible
		{
			out.println("<h3>Below are the suggested products that fit your budget! Enjoy!!!!</h3></head><body>");
			out.println("<br><br><table border=\"3\" style=\"width:300px\" align=center>");
			out.println("<th>Product Combinations</th><th>Total Price</th>");
		
			for(int i=0;i<totalPrice.size();i++)
			{
				if(totalPrice.get(i)>=(sum-20.00)&&totalPrice.get(i)<=(sum+20.00))//I am having a window of 40$. So if 150 is entered , any amount from 130-170 would be printed
				{
					out.println("<tr><td>");
					ArrayList<String> l= productURLs.get(i);
					for(int j=0;j<l.size();j++)
					{
						productcount=productcount+1;
						out.println("<a href="+l.get(j)+">Product "+productcount+"<br>");									
					}				
					out.println("</td><td>"+totalPrice.get(i).intValue()+"$</td></tr>");
				}
			}out.println("</body></html>");}}catch(Exception e){e.printStackTrace();}
	}
	//Recursion function to compute the combinations.
	public void getallelements(List<Double> a,double sum,String results,String indexes)
	{				
		String result,index;
		double total=0;
		for(int i=0;i<a.size();i++)
		{
			double remain = sum-a.get(i);				
			result= a.get(i)+","+results;
			index=i+","+indexes;			
			String split[]=index.split(",");
			if(split.length>num)
				return;
			if(remain<=20)
			{							
				if(split.length==num)
				{
					ArrayList<String> temp=new ArrayList<String>();
					for(int j=0;j<split.length;j++)
					{									
						temp.add(new ArrayList<String>(getProductURLS()).get(Integer.valueOf(split[j])));
					}
					String split1[]=result.split(",");
					total=0;
					for(int k=0;k<split.length;k++)
					{
						total+=Double.valueOf(split1[k]);
					}
					productURLs.put(COUNTER++,temp);
					totalPrice.add(total);
				}
				return;
			}
			else
			{					
				List<Double> poss = a.subList(0,i);
				if(poss.size()>0)
				{
					getallelements(poss,remain,result,index);		
			    }				
			}
		}				
	}			
}
