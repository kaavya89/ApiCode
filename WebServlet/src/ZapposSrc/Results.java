package ZapposSrc;
import java.util.*;
import java.io.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Results {	
	private double sum;
	private int num;
	private JSONParser parser; Object object; JSONObject jsonobj; JSONArray responseArray;PrintWriter out;
	private static HashMap<Integer,Double> productPricePair = new HashMap<Integer,Double>();
	private static HashMap<Integer,String> productURLPair = new HashMap<Integer,String>();	
	private static int COUNTER=0;
	private static HashMap<Integer,ArrayList<String>> productURLs = new HashMap<Integer,ArrayList<String>>();
	private static ArrayList<Double> totalPrice = new ArrayList<Double>();
	public Results(int num,double sum,PrintWriter out)
	{		
		this.num = num;
		this.sum=sum;
		parser = new JSONParser();
		object = new Object();
		responseArray = new JSONArray();
		this.out=out;
	}
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
	public void print()
	{		
		int productcount=0;
		try{
		getallelements(new ArrayList<Double>(getList()),sum,"","");
		//Collections.sort(totalPrice);
		out.println("<html><head>"
				+ "<head><style> hr {color:sienna;} body {background-color:rgb(132,112,255);"
				+"font-size:medium;"
				+ "background-image:url('C:\\Users\\Kaavya Srinivasan\\workspace\\WebServlet\\src\\zappos_logo.gif');"
				+"background-repeat:no-repeat;"
				+"background-attachment:fixed;background-position:right bottom;}</style>");				
		if(totalPrice.size()==0)
			out.println("<h3>We are sorry, there are no suggested items in the price range your entered!!!</h3>");
		else
		{
			out.println("<h3>Below are the suggested products that fit your budget! Enjoy!!!!</h3></head><body>");
			out.println("<br><br><table border=\"3\" style=\"width:300px\" align=center>");
			out.println("<th>Product Combinations</th><th>Total Price</th>");
		
			for(int i=0;i<totalPrice.size();i++)
			{
				if(totalPrice.get(i)>=(sum-20.00)&&totalPrice.get(i)<=(sum+20.00))
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
			}}}catch(Exception e){e.printStackTrace();}
	}
	public void test()
	{
		int productcount=0;
		out.println("<html><head>"
				+ "<head><style> hr {color:sienna;} body {background-color:rgb(132,112,255);"
				+"font-size:medium;"
				+ "background-image:url('C:\\Users\\Kaavya Srinivasan\\workspace\\WebServlet\\src\\zappos_logo.gif');"
				+"background-repeat:no-repeat;"
				+"background-attachment:fixed;background-position:right bottom;}</style>"				
				+ "<h3>Below are the suggested products that fit your budget! Enjoy!!!!</h3></head><body>");
		out.println("<br><br><table border=\"3\" style=\"width:300px\" align=center>");
		out.println("<th>Product Combinations</th><th>Total Price</th>");
		
		for(int i=0;i<totalPrice.size();i++)
		{
			if(totalPrice.get(i)>=(sum-20.00)&&totalPrice.get(i)<=(sum+20.00))
			{
				out.println("<tr><td>");
				ArrayList<String> l= productURLs.get(i);
				//out.println("Size value is"+l.size());
				for(int j=0;j<l.size();j++)
				{
					out.println("<a href="+l.get(j)+">Product "+productcount+++"<br>");									
				}				
				out.println("</td><td>"+totalPrice.get(i).intValue()+"$</td></tr>");
			}
		}
		out.println("</body></html>");
	}
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
	public void testSet()
	{
		ArrayList <String> l = new ArrayList<String>();		
		l.add("http://www.zappos.com/product/8212178/color/168679");totalPrice.add(180.00);
		l.add("http://www.zappos.com/product/8248232/color/455932");totalPrice.add(180.00);
		l.add("http://www.zappos.com/product/8236935/color/183092");totalPrice.add(180.00);		
		productURLs.put(0, l);
		
	}
}
