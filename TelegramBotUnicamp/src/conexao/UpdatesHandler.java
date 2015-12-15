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
	
	public UpdatesHandler(RequestMaker rm) {
		this.rm = rm;
		commandsArray = new ArrayList <Command>();
		commandsArray.add(new HelpCommand(this.rm));
		commandsArray.add(new CardapioCommand(this.rm));
	}
	
	public boolean parseUpdates(JSONObject updatesObj) {
		
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
				
				for(int i = 0; i < updatesArray.length(); i++) {
					JSONObject update = updatesArray.getJSONObject(i);
					for(int j = 0; j < commandsArray.size(); j++) {
						JSONObject message = update.getJSONObject("message");
						// Protecao contra coisas que nao sejam texto.
						if(message.has("text") == false)
							continue;
						else {
							String messageText = message.getString("text");
							if(messageText.charAt(0) != '/')
								continue;
							if(commandsArray.get(j).getType().equalsIgnoreCase(messageText));
								commandsArray.get(j).doAction(message);
						}
					}
				}
			}
		}
		return true;
	}
	
}
