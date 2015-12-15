package commands;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import org.json.JSONObject;
import conexao.RequestMaker;

public class HelpCommand extends Command {

	public HelpCommand(RequestMaker rm) {
		super(rm);
		command = "/help";
	}

	@Override
	public void doAction(JSONObject message) {
		System.out.println("Passei em help.");
		
		String helpMessage = "Você pode usar os comandos:\n/cardapio - Mostra o cardápio de hoje do RU.\n/tempo - Mostra o tempo em Barão Geraldo.";
		String URLHelpMessage = null;
		try {
			URLHelpMessage = URLEncoder.encode(helpMessage, "UTF-8");
			String funcao = "sendMessage?chat_id=" + message.getJSONObject("chat").getLong("id") + "&text=" + URLHelpMessage;
			rm.doGet(true, funcao);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
	}

}
