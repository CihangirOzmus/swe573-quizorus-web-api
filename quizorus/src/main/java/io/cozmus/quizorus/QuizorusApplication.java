package io.cozmus.quizorus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class QuizorusApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuizorusApplication.class, args);
		//openIndexPage("http://localhost:8080/");
	}

//	private static void openIndexPage(String url) {
//		if(Desktop.isDesktopSupported()){
//			Desktop desktop = Desktop.getDesktop();
//			try {
//				desktop.browse(new URI(url));
//			} catch (IOException | URISyntaxException e) {
//				e.printStackTrace();
//			}
//		}else{
//			Runtime runtime = Runtime.getRuntime();
//			try {
//				runtime.exec("rundll32 url.dll,FileProtocolHandler " + url);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//	}

}
