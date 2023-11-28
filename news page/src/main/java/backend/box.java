package backend;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.print.DocFlavor.STRING;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class box {
//	public static List <String[]> news =new LinkedList<>();
	public static Map<Integer, String[]> news =new LinkedHashMap<>();
	public static int key =0;
	public static box obj;
	// month tamil
	public static String date(String a){
		String formattedDate="a";
		String tamilDate = a;
        SimpleDateFormat tamilDateFormat = new SimpleDateFormat("MMMM dd, yyyy HH:mm");
        HashMap<String, String> tamilToEnglishMonth = new HashMap<>();
        tamilToEnglishMonth.put("ஜனவரி", "January");
        tamilToEnglishMonth.put("பிப்ரவரி", "February");
        tamilToEnglishMonth.put("மார்ச்", "March");
        tamilToEnglishMonth.put("ஏப்ரல்", "April");
        tamilToEnglishMonth.put("மே", "May");
        tamilToEnglishMonth.put("ஜூன்", "June");
        tamilToEnglishMonth.put("ஜூலை", "July");
        tamilToEnglishMonth.put("ஆகஸ்ட்", "August");
        tamilToEnglishMonth.put("செப்டம்பர்", "September");
        tamilToEnglishMonth.put("அக்டோபர்", "October");
        tamilToEnglishMonth.put("நவம்பர்", "November");
        tamilToEnglishMonth.put("டிசம்பர்", "December");
        // Replace Tamil month names with English equivalents
        for (String month : tamilToEnglishMonth.keySet()) {
            tamilDate = tamilDate.replace(month, tamilToEnglishMonth.get(month));
        }

        SimpleDateFormat outputDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        try {
            Date date = tamilDateFormat.parse(tamilDate);
            formattedDate = outputDateFormat.format(date);
    System.out.println("asd"+formattedDate);
        } catch (ParseException e) {
            String b =e.toString();
            String c=b.substring(b.length()-23,b.length()-1);
            formattedDate=c;
        }
		
		return formattedDate;
        
	}
	
	
	
	//samayam news
	public static void samayam() {
		boolean a=true;
    	int i=1;
    	Date Date = new Date();
    	SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");  
        String today = dateFormat.format(Date);
        aa:
    	while (a) {
    	   String url = "https://tamil.samayam.com/latest-news/india-news/articlelist/45939413.cms?curpg="+i;
    	   i++;
           try {
               Document doc = Jsoup.connect(url).get();
               Elements articles = doc.select(".table_row");
               for (Element article : articles) {
            	   
                   String heading = article.select(".text_ellipsis").text();//heading...
                   String articleUrl = article.select("a").attr("href");//link...
                   Document articleDoc = Jsoup.connect(articleUrl).get();
                   Element contentdate=articleDoc.select(".time").first();
                   String date =contentdate.text();
                   String date1= date.substring(9,20);
                 
                   if(today.equals(date1)) {
                	   String img =articleDoc.select(".asleadimg").attr("src");                	   
                       Elements contentElement = articleDoc.select(".story-content");                       
                       String content = contentElement != null ? contentElement.text() : "Content not available";
                       
                       String [] row = {articleUrl,img,date,heading,content};
                       news.put(key,row);
                       key++;
                   }
                   else {
                   System.out.println("system over:"+date1);
                  break aa;
                   }
                  
               }

           } catch (IOException e) {
               e.printStackTrace();
           }	
	}
}
	
	
	
	//dinamalar news
	public static void dinamalar() {
		obj = new box();
		boolean a=true;
    	int i=1;
    	
    	Date Date = new Date();
    	SimpleDateFormat dateFormat = new SimpleDateFormat("MMMMM dd,yyyy");  
        String today = dateFormat.format(Date);
    	   String url = "https://www.dinamalar.com/tamil-news/89/latest-tamilnadu-news-in-tamil/tn-news/";
           try {
               Document doc = Jsoup.connect(url).get();
               Elements articles = doc.select(".entry-title");
               for (Element article : articles) {
                   String heading = article.select("a").text();//heading...
                   String articleUrl ="http:"+ article.select("a").attr("href");//link...
                   Document articleDoc = Jsoup.connect(articleUrl).get();
                   Element contentdate=article.select(".mvp-author-info-date").first();
                   String date =contentdate.text();
                   String date1= obj.date(date);
                  String subdate =date1.substring(0,date1.length()-6);
                   if(subdate.equals(today)) {
                	   String img =articleDoc.select("#clsclk img").attr("src");
                       Elements contentElement = articleDoc.select("#seladblock1 p");
                       String content = contentElement != null ? contentElement.text() : "Content not available";
                       
                       String [] row = {articleUrl,img,date,heading,content};
                       news.put(key,row);
                       key++;
                   }
                   else {
                   System.out.println("system over:"+subdate);
                   }
                  
               }

           } catch (IOException  e) {
               e.printStackTrace();
           }}
		

	
	
	
	//the hindu news
	public static void the_hindu() {
		boolean a=true;
    	int i=1;
    	Date Date = new Date();
    	SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM, yyyy");  
        String today = dateFormat.format(Date);
        aa:
    	while (a) {
    	   String url = "https://www.hindutamil.in/news/tamilnadu/"+i;
    	   i++;
           try {
               Document doc = Jsoup.connect(url).get();
               Elements articles = doc.select(".link-black");
               for (Element article : articles) {
                   String heading = article.select(".card-text").text();//heading...
                   String articleUrl = article.select("a").attr("href");//link...
                   Document articleDoc = Jsoup.connect(articleUrl).get();
                   Element contentdate=articleDoc.select(".date").first();
                   String date =contentdate.text();
                   String date1= date.substring(0,12);
                   if(today.equals(date1)) {
                	   String img =articleDoc.select(".img-responsive").attr("src");
                       Elements contentElement = articleDoc.select(".pgContent p");
                       String content = contentElement != null ? contentElement.text() : "Content not available";
                       String [] row = {articleUrl,img,date,heading,content};
                       news.put(key,row);
                       key++;
                   }
                   else {
                   System.out.println("system over:"+date1);
                  break aa;
                   }
                  
               }

           } catch (IOException e) {
               e.printStackTrace();
           }		
	}
	}
	
	
	
	
	//dinakaran news
	public static void dinakaran() {
		List<String> link =new LinkedList<>();
		link.add("https://www.dinakaran.com/category/news/tamilnadu_news/");
		link.add("https://www.dinakaran.com/category/news/india_news/");
		
		
		for (String row1 : link) {
		boolean a=true;
    	int i=1;
    	Date Date = new Date();
    	SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");  
        String today = dateFormat.format(Date);
    	   String url = row1;
    	   i++;
           try {
               Document doc = Jsoup.connect(url).get();
               Elements articles = doc.select(".grid-header-box");
               for (Element article : articles) {
            	   
                   String heading = article.select(".penci-entry-title").text();//heading...
                   String articleUrl =article.select(".penci-entry-title a").attr("href");//link...
                   Document articleDoc = Jsoup.connect(articleUrl).get();
                   Element contentdate=articleDoc.select(".entry-date").first();
                   String date =contentdate.text();      
                	   String img =articleDoc.select(".galery-vw img").attr("src");                	   
                       Elements contentElement = articleDoc.select(".entry-content");                       
                       String content = contentElement != null ? contentElement.text() : "Content not available";
                       String [] row = {articleUrl,img,date,heading,content};
                       news.put(key,row);
                       key++;
                  
               }

           } catch (IOException e) {
               e.printStackTrace();
           }

   		
		
	}

	}
	
	
	
	
	public static void main(String[] args) {
		

		obj = new box();
		
		ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
		 
	     Runnable task = () -> {
	    	 news.clear();
	    	 obj.samayam();
		     obj.dinamalar();
		     obj.the_hindu();
		     obj.dinakaran();
    	 StringBuilder htmlContent = new StringBuilder();
         htmlContent.append("<html>");
         htmlContent.append("<head><title>Today's News</title>");
         htmlContent.append("<link rel='stylesheet' type='text/css' href='style.css'></head>");
         htmlContent.append("<body>");
         htmlContent.append("<div id='outerlayer' ><header id='header'>Today's NEWS</header>");
         htmlContent.append("<div class='pg-body'>");
         for (Map.Entry<Integer, String[]> entry : news.entrySet()) {
 			Integer key1 = entry.getKey();
 			String[] val = entry.getValue();
         
             String articleUrl = val[0];
             String subimg = val[1].substring(0,6);
             String img ="";
             if (subimg.contains("https:")) {
				img=val[1];
			} else {
				img="https://akm-img-a-in.tosshub.com/indiatoday/images/story/202210/breaking_latest_news_1200x675_1-sixteen_nine_555.jpg?VersionId=bLdr13QKJ5KRZO3IAQuURXh0gZUgtbou";
			}
             String date = val[2];
             String heading = val[3];
             String content = val[4];

             htmlContent.append("<a href='news134.html' target=\"_blank\"><div  class='newsbox' value="+key1+">");
             htmlContent.append("<img src='"+img+"' style='width:280px;height:150px'>");
             htmlContent.append("<p class='heading'>"+heading+"</p>");
             htmlContent.append("<p class='date'>Last update:"+date+"</p>");
             htmlContent.append("<p id='content'>"+content+"</p>");
             htmlContent.append("</div></a>");
         }

         htmlContent.append("</div>");
         htmlContent.append("box.addEventListener('click', function () {\r\n"
         		+ "    const value = this.getAttribute('value');\r\n"
         		+ "    console.log('Clicked box value:', value); // Log the clicked box value\r\n"
         		+ "    \r\n"
         		+ "    const xhr = new XMLHttpRequest();\r\n"
         		+ "    xhr.onreadystatechange = function () {\r\n"
         		+ "        if (xhr.readyState === XMLHttpRequest.DONE) {\r\n"
         		+ "            if (xhr.status === 200) {\r\n"
         		+ "                console.log('Response from server:', xhr.responseText);\r\n"
         		+ "            } else {\r\n"
         		+ "                console.error('Error:', xhr.status);\r\n"
         		+ "            }\r\n"
         		+ "        }\r\n"
         		+ "    };\r\n"
         		+ "\r\n"
         		+ "    const url = '/news_page/myservlet?value=' + value;" 
         		+ "    console.log('Sending request to:', url); "
         		+ "    xhr.open('POST', url, true);\r\n"
         		+ "    xhr.send();\r\n"
         		+ "});\r\n"
         		+ "");
         htmlContent.append("</body>");
         htmlContent.append("</html>");

         // Write HTML content to a file
         try (BufferedWriter writer = new BufferedWriter(new FileWriter("C:/Users/Aashik Ali/eclipse-workspace1/news page/src/main/webapp/index.html"))) {
             writer.write(htmlContent.toString());
             System.out.println("HTML file generated successfully!");
         } catch (IOException e) {
             e.printStackTrace();
         }key=0;
         
         
	       };
	       
	       
	       int periodInHours = 3;
	       executor.scheduleAtFixedRate(task, 0, periodInHours, TimeUnit.MINUTES);

	}
}



// 
