package hello;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class GreetingController {

    private static final String template = "Hello Hearti, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/greeting")
    public @ResponseBody Greeting greeting(
            @RequestParam(value="name", required=false, defaultValue="World") String name,
            HttpServletResponse response, HttpServletRequest request) {
    	
    	StringBuilder stringBuilder = new StringBuilder();  
        BufferedReader bufferedReader = null;  
    	
    	try {
    		
    		 InputStream inputStream = request.getInputStream(); 

    	        if (inputStream != null) {  
    	            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));  

    	            char[] charBuffer = new char[128];  
    	            int bytesRead = -1;  

    	            while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {  
    	                stringBuilder.append(charBuffer, 0, bytesRead);  
    	            }  
    	        } else {  
    	            stringBuilder.append("");  
    	        } 
		} catch (Exception e) {
			// TODO: handle exception
		}    	
    	
    	System.out.println("Request : "+ stringBuilder);
        return new Greeting(counter.incrementAndGet(),
                            String.format(template, name));
    }
}