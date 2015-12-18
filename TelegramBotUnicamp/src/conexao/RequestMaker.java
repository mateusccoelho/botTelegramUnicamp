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
	
	/* Recebe caminho do arquivo de texto que contem o token do bot. */
	public RequestMaker(String tokenPath) {

		try {
			/* Instancia uma inputstream do arquivo de texto. */
			FileInputStream fr = new FileInputStream(new File(tokenPath));
			/* Le o conteudo da stream com a classe Scanner. */
			Scanner s = new Scanner(fr).useDelimiter("\\A");
			
			/* Verifica se a stream nao esta vazia. */
			if(s.hasNext() == true)
				this.token = s.next();
			else
				this.token = "";
			
			try {
				fr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}
	
	/* Pode ser usada pra fazer requests ao telegram ou a outros sites.
	 * O parametro string sera o metodo de request ao telegram se o boolean for true.
	 * Caso boolean seja false, a string sera o endereco completo do requerimento. */
	public String doGet(boolean isTelegram, String function) {
		
		String content = null, adress;

		/* Monta o endereco da requisicao. */
		if(isTelegram == true)
			adress = "https://api.telegram.org/bot" + this.token + "/" + function;
		else
			adress = function;
		
		
		try {
			// Seta a classe URL com o endereco.
			url = new URL(adress);
			
			try {
				// Abre a conexao.
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
				Scanner s = new Scanner(is, "ISO-8859-1").useDelimiter("\\A");
					
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
