package conexao;

import org.json.JSONArray;
import org.json.JSONObject;
import java.net.URLEncoder;

import conexao.RequestMaker;

public class Main {

	public static void main(String[] args) throws Exception {
		
		long updateOffset = 1;
		String path = "/home/mateus/Unicamp/Extras/unicampServicos/token.txt";
		RequestMaker rm = new RequestMaker(path);
		String funcao = "getUpdates?offset=" + updateOffset;
		String jsonUpdates = rm.doGet(true, funcao);
		
		JSONObject updatesObj = new JSONObject(jsonUpdates);
		UpdatesHandler uh = new UpdatesHandler(rm);x
		boolean ok = uh.parseUpdates(updatesObj);
		
		JSONArray array = updatesObj.getJSONArray("result");
		updateOffset = array.getJSONObject(array.length() - 1).getLong("update_id") + 1;
	}

}
