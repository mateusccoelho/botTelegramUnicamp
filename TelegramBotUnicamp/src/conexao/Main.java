package conexao;

import org.json.JSONArray;
import org.json.JSONObject;
import java.net.URLEncoder;

import conexao.RequestMaker;

public class Main {

	public static void main(String[] args) throws Exception {
		
		String path = "/home/mateus/Unicamp/Extras/unicampServicos/token.txt";
		RequestMaker rm = new RequestMaker(path);
		String funcao = "getUpdates";
		String jsonUpdates = rm.doGet(funcao);
		System.out.println(jsonUpdates);
		
		JSONObject updatesObj = new JSONObject(jsonUpdates);
		if(updatesObj.getBoolean("ok") == false)
			System.out.println("Requisicao falhou.");
		else {
			JSONArray updatesArray = updatesObj.getJSONArray("result");
			if(updatesArray.length() == 0)
				System.out.println("Sem updates.");
			else {
				JSONObject update = updatesArray.getJSONObject(0);
				JSONObject message = update.getJSONObject("message");
				if(message.getString("text").equalsIgnoreCase("/help")) {
					funcao = "sendMessage?chat_id=" + message.getJSONObject("chat").getLong("id") + "&text=" + URLHelpMessage;
					rm.doGet(funcao);
				}
			}
		}
	}

}
