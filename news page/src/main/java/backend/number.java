package backend;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class number {
	public static int count=0;
	public static List <String[]> news =new LinkedList<>();
	public static void main(String[] args) throws IOException ,ParseException {
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
                   System.out.println(heading);
                   System.out.println(articleUrl);
                   Document articleDoc = Jsoup.connect(articleUrl).get();
                   Element contentdate=articleDoc.select(".entry-date").first();
                   String date =contentdate.text();
                   System.out.println(date);
                  
                 
                 
                	   String img =articleDoc.select(".galery-vw img").attr("src");                	   
                       Elements contentElement = articleDoc.select(".entry-content");                       
                       String content = contentElement != null ? contentElement.text() : "Content not available";
                       System.out.println(img);
                       System.out.println(content);
                       
                       System.out.println("-----------------------");
                       String [] row = {articleUrl,img,date,heading,content};
                       news.add(row);
                  
                  
               }

           } catch (IOException e) {
               e.printStackTrace();
           }

   		
		
	}

	}
	    
}