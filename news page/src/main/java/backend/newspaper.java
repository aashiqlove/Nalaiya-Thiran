package backend;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.opencsv.CSVWriter;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class newspaper {
	
   public static void main(String[] args) {
	   ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

       Runnable task = () -> {
    	   boolean a=true;
       	int i=1;
       	List <String[]> news =new LinkedList<>();
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
                          news.add(row);
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
       	 StringBuilder htmlContent = new StringBuilder();
            htmlContent.append("<html>");
            htmlContent.append("<head><title>Today's News</title></head>");
            htmlContent.append("<body>");
            htmlContent.append("<h1>Today's News</h1>");
            htmlContent.append("<table border='1'>");
            htmlContent.append("<tr><th>Date</th><th>Heading</th><th>Content</th></tr>");

            for (String[] row : news) {
                String articleUrl = row[0];
                String img = row[1];
                String date = row[2];
                String heading = row[3];
                String content = row[4];

                htmlContent.append("<tr>");
                htmlContent.append("<td>").append(date).append("</td>");
                htmlContent.append("<td>").append("<a href='").append(articleUrl).append("'>").append("<img src='").append(img).append("'style='width:250px;height:200px'><p>").append(heading).append("</p>").append("</a></td>");
                
                htmlContent.append("<td>").append(content).append("</td>");
                htmlContent.append("</tr>");
            }

            htmlContent.append("</table>");
            htmlContent.append("</body>");
            htmlContent.append("</html>");

            // Write HTML content to a file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("C:/Users/Aashik Ali/eclipse-workspace1/news page/src/main/webapp/news.html"))) {
                writer.write(htmlContent.toString());
                System.out.println("HTML file generated successfully!");
            } catch (IOException e) {
                e.printStackTrace();
            }
     
       };
       
       int periodInHours = 5;
       executor.scheduleAtFixedRate(task, 0, periodInHours, TimeUnit.SECONDS);
       
       }

       
       
    }
    



