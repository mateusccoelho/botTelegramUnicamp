package conexao;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;
import commands.CardapioCommand;
import commands.Command;
import commands.HelpCommand;

public class UpdatesHandler {

	private RequestMaker rm;
	private ArrayList <Command> commandsArray;
	
	/* Cria lista de comandos. */
	public UpdatesHandler(RequestMaker rm) {
		this.rm = rm;
		commandsArray = new ArrayList <Command>();
		commandsArray.add(new HelpCommand(this.rm));
		commandsArray.add(new CardapioCommand(this.rm));
	}
	
	public boolean parseUpdates(JSONObject updatesObj) {
		
		/* Verifica se a requisicao foi feita com sucesso e se ha updates. */
		if(updatesObj.getBoolean("ok") == false) {
			System.out.println("Requisicao falhou.");
			return false;
		}
		else {
			JSONArray updatesArray = updatesObj.getJSONArray("result");
			if(updatesArray.length() == 0) {
				System.out.println("Sem updates.");
				return false;
			}
			else {
				/* Percorre lista de updates procurando por comandos. */
				for(int i = 0; i < updatesArray.length(); i++) {
					JSONObject message = updatesArray.getJSONObject(i).getJSONObject("message");
					/* Procura mensagens com textos e "/", que representam comandos. */
					if(message.has("text") == true) {
						String messageText = message.getString("text");
						if(messageText.charAt(0) == '/') {
							for(int j = 0; j < commandsArray.size(); j++)
								if(commandsArray.get(j).getType().equalsIgnoreCase(messageText))
									commandsArray.get(j).doAction(message);
						}
					}
				}
			}
		}
		return true;
	}
	
}
