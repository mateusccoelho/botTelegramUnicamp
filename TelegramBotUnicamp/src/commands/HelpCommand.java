package commands;

import java.net.URLEncoder;
import org.json.JSONObject;
import conexao.RequestMaker;

public class HelpCommand extends Command {

	HelpCommand(RequestMaker rm) {
		super(rm);
	}

	@Override
	public void doAction(JSONObject message) {
		
		String helpMessage = "Você pode usar os comandos:\n/cardapio - Mostra o cardápio de hoje do RU.\n/tempo - Mostra o tempo em Barão Geraldo.";
		String URLHelpMessage = URLEncoder.encode(helpMessage, "UTF-8");
		
		String funcao = "sendMessage?chat_id=" + message.getJSONObject("chat").getLong("id") + "&text=" + URLHelpMessage;
		rm.doGet(true, funcao);
		
	}

}
