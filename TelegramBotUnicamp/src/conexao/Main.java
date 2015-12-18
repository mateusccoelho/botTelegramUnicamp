package conexao;

import org.json.JSONArray;
import org.json.JSONObject;
import java.net.URLEncoder;

import conexao.RequestMaker;

public class Main {

	public static void main(String[] args) throws Exception {
		
		/* updateOffset eh inicializado com 1 para receber todos os updates feitos. */
		long updateOffset = 1;
		/* Mudar o endereco para um arquivo contendo a token do bot. */
		String path = "/home/mateus/Unicamp/Extras/unicampServicos/token.txt";
		RequestMaker rm = new RequestMaker(path);
		
		while(true) {
			/* Monta a parte da URL que contem a funcao de receber requests. */
			String funcao = "getUpdates?offset=" + updateOffset + "&timeout=5";
			/* Chama funcao de fazer a requisicao. */
			String jsonUpdates = rm.doGet(true, funcao);
			System.out.println(jsonUpdates);
			/* Transforma o texto em objeto Json. */
			JSONObject updatesObj = new JSONObject(jsonUpdates);
			UpdatesHandler uh = new UpdatesHandler(rm);
			/* Chama funcao que trata os updates */
			boolean ok = uh.parseUpdates(updatesObj);
			
			/* Atualiza o offset para que o proximo request exiba apenas updates novos. */
			if(ok == true) {
				JSONArray array = updatesObj.getJSONArray("result");
				updateOffset = array.getJSONObject(array.length() - 1).getLong("update_id") + 1;
			}
			
		}
	}

}
