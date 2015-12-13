package commands;

import org.json.JSONObject;

import conexao.RequestMaker;

public abstract class Command {

	private RequestMaker rm;
	
	Command(RequestMaker rm) {
		this.rm = rm; 
	}
	
	public abstract void doAction(JSONObject message);
}
