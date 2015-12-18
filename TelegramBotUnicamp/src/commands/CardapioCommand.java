 package commands;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Date;
import java.util.Calendar;
import java.util.TimeZone;

import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import conexao.RequestMaker;

public class CardapioCommand extends Command {

	public CardapioCommand(RequestMaker rm) {
		super(rm);
		command = "/cardapio";
	}

	@Override
	public void doAction(JSONObject message) {
		
		System.out.println("Passei em cardapio");
		
		/* Monta um calendario a partir do codigo retirado da mensagem que esta codificado em UNIX time. */
		Calendar c = Calendar.getInstance();
		c.setTime(new Date(message.getLong("date") * 1000));
		c.setTimeZone(TimeZone.getDefault());
		c.setFirstDayOfWeek(Calendar.SUNDAY);
		
		/* Verifica se nao eh fim de semana. */
		int diaSemana = c.get(Calendar.DAY_OF_WEEK);
		if(diaSemana == Calendar.SUNDAY || diaSemana == Calendar.SATURDAY) {
			/* Manda mensagem avisando que eh fim de semana. */
			String errorMessage = "Hoje é fim de semana!";
			String URLErrorMessage = null;
			try {
				/* Converte a string para o formato UTF-8. */
				URLErrorMessage = URLEncoder.encode(errorMessage, "UTF-8");
				String funcao = "sendMessage?chat_id=" + message.getJSONObject("chat").getLong("id") + "&text=" + URLErrorMessage;
				rm.doGet(true, funcao);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		
			return;
		}
		else {
			/* Monta a data atual e faz o request do HTML do cardapio ao site da prefeitura. */
			int diaMes = c.get(Calendar.DAY_OF_MONTH);
			int ano = c.get(Calendar.YEAR);
			int mes = c.get(Calendar.MONTH) + 1;
			String prefeituraRequest = "http://legiao.prefeitura.unicamp.br/cardapio.php?d=" + ano + "-" + mes + "-" + diaMes;
			String html = rm.doGet(false, prefeituraRequest);
			/* Trata o HTML para a estrutura de uma arvore.  */
			Document doc = Jsoup.parse(html);
			
			// <tr> cria linha
			// <td> cria coluna
			
			/* Procura todas as tabelas de cardapio presentes no HTML, almoco, vegetariano e jantar. */
			Elements menuTable = doc.select("table.fundo_cardapio");
			/* Esse comando pega apenas o cardapio do almoco. */
			Element lunchTable = menuTable.get(0);
			// Trata tabela do almoco selecionando cada linha da tabela e colocando seu conteudo (texto)
			// em um string.
			Elements options = lunchTable.select("td");
			String finalText = "";
			for(int i = 0; i < options.size(); i++)
				finalText = finalText + options.get(i).text() + "\n";
			
			/* Manda uma mensagem com o cardapio porcamente formatado. */
			String URLFinalText = null;
			try {
				URLFinalText = URLEncoder.encode(finalText, "UTF-8");
				String funcao = "sendMessage?chat_id=" + message.getJSONObject("chat").getLong("id") + "&text=" + URLFinalText;
				rm.doGet(true, funcao);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
			
	}

}
