package com.example.demo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;



@Controller
public class FormGenController {
	

	
	@Autowired
	FORMDAO fd;
	
	public static ArrayList list = new ArrayList();
	
	
	
	@RequestMapping("/")
	public String Form()
	{
		return "EnterJSON";
	}
	
	@RequestMapping("/submitsuccess")
	public String SubmitDone(HttpServletRequest req)
	{
		Random rand= new Random();
		FormPOJO fp= new FormPOJO();
		String str="";
		for(int i=0;i<list.size();i++)
		{
			str += list.get(i)+":"+req.getParameter((String)list.get(i))+ " ";
			System.out.println(str);
		}
		fp.setData(str);
		fp.setId(rand.nextLong());
		fd.save(fp);
		//System.out.println(list.get(0));
		//System.out.println(model.getAttribute((String)list.get(0)));
		return "Submit";
	}
	
	@RequestMapping("/form")
	public ModelAndView MakeForm(@RequestParam("jsonip") String JSONip) throws ParseException
	{
		list.clear();
		ModelAndView mv =new ModelAndView();	
		JSONParser parser = new JSONParser();
		JSONObject json = (JSONObject) parser.parse(JSONip);
		//System.out.println(json);
		//System.out.println(json.entrySet());
		Set set = json.entrySet();
		String newtext="";
		Iterator it = set.iterator();
	     while(it.hasNext()){
	    	 
	        newtext += DynamicFormResolver(it.next());
	     }
	     
	     String HTML = "<form action=\"/submitsuccess\">" + "\n" +
	 			newtext +
	 			"<input type=\"submit\" value=\"Submit\">" + "\n" +
	 			"</form>";
	    System.out.println(HTML);
	    mv.addObject("FormElements", HTML);
	    mv.setViewName("Form");
		return mv;
	}
	public String DynamicFormResolver(Object obj)
	{
		
		 String TextField = " <br> <label for=\"000\">000:</label> " + "\n" +
		   "<input type=\"text\" id=\"000\" name=\"000\"><br><br>";
		 String Checkbox = "<br><label for=\"111\">111:</label> <select name=\"111\" id=\"111\"> +222+ \"</select> ";
		  
		 
		String newline =" \" +\"\n\" + \"<option value=\\\"999\\\">999:</option> \\\" + \\\"\\n\\\" + ";
				    
		String newtext="";
		String newselect = "";
		String newreplace = "";
		
		String s1=obj.toString();
		String arr[]=s1.split("=");
		int flag=0;
		
		String arr2[];
		list.add(arr[0]);
		System.out.println("Field Name"+ arr[0]);
		if(arr[1].startsWith("["))
		{	
			flag=1;
			String s2=arr[1].substring(1, (arr[1].length()-1));
			arr2=s2.split(",");
			System.out.println(arr2);
			newselect = Checkbox.replaceAll("111", arr[0]);
			int l=arr2.length;
			for(int i=0;i<l;i++)
			{
				String s3=arr2[i].substring(1, (arr2[i].length()-1));
				newreplace+= newline.replaceAll("999",s3 );
			}
			
		}
		if(flag==0)
		{
			newtext += TextField.replaceAll("000", arr[0]);
			return newtext;
		}
		else
		{
			return newselect.replaceAll("222", newreplace);
		}
		
		
		

		
	}

}
