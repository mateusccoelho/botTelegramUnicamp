package conexao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.io.InputStream;
import java.util.Scanner;

public class RequestMaker {

	private HttpURLConnection connection;
	private URL url;
	private String token;
	
	RequestMaker(String tokenPath) {

		try {
			FileInputStream fr = new FileInputStream(new File(tokenPath));
			
			Scanner s = new Scanner(fr).useDelimiter("\\A");
			
			if(s.hasNext() == true)
				this.token = s.next();
			else
				this.token = "";
			
			System.out.println(this.token);
			
			try {
				fr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}
	
	public String doGet(boolean isTelegram, String function) {
		
		String content = null, adress;
		
		if(isTelegram == true)
			adress = "https://api.telegram.org/bot" + this.token + "/" + function;
		else
			adress = function;
		
		// Abre a conexao
		try {
			url = new URL(adress);
			
			try {
				connection = (HttpURLConnection) url.openConnection();
				
				// Seta caracteristicas do requerimento
				connection.setRequestProperty("Request-Method", "GET");
				connection.setDoInput(true);  
				connection.setDoOutput(false); 
				
				// Pega a instancia da inputstream
				InputStream is;
				is = (InputStream) connection.getInputStream();
					
				// Quebra o conteudo da stream de acordo com o token \\A, o qual na pratica
				// divide todo o texto em uma soh palavra.
				Scanner s = new Scanner(is).useDelimiter("\\A");
					
				// Verifica se o conteudo da stream eh nulo
				if(s.hasNext() == true)
					content = s.next();
				else
					content = "";
				
				is.close();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		return content;
		
	}
	
	public String doPost(String function) {
		
		String json = null, adress;
		adress = "https://api.telegram.org/bot" + this.token;
		
		return json;
	}
	
}
