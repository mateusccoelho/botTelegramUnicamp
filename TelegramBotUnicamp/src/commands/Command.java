package commands;

import org.json.JSONObject;

import conexao.RequestMaker;

public abstract class Command {

	protected RequestMaker rm;
	protected String command;
	
	public Command(RequestMaker rm) {
		this.rm = rm; 
	}
	
	public String getType() {
		return this.command;
	}
	
	public abstract void doAction(JSONObject message);
}
