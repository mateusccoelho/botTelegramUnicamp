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
		
		Calendar c = Calendar.getInstance();
		c.setTime(new Date(message.getLong("date") * 1000));
		c.setTimeZone(TimeZone.getDefault());
		c.setFirstDayOfWeek(Calendar.SUNDAY);
		int diaSemana = c.get(Calendar.DAY_OF_WEEK);
		
		if(diaSemana == Calendar.SUNDAY || diaSemana == Calendar.SATURDAY) {
			String errorMessage = "Hoje é fim de semana!";
			String URLErrorMessage = null;
			try {
				URLErrorMessage = URLEncoder.encode(errorMessage, "UTF-8");
				String funcao = "sendMessage?chat_id=" + message.getJSONObject("chat").getLong("id") + "&text=" + URLErrorMessage;
				rm.doGet(true, funcao);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		
			return;
		}
		else {
			int diaMes = c.get(Calendar.DAY_OF_MONTH);
			int ano = c.get(Calendar.YEAR);
			int mes = c.get(Calendar.MONTH) + 1;
			String prefeituraRequest = "http://legiao.prefeitura.unicamp.br/cardapio.php?d=" + ano + "-" + mes + "-" + diaMes;
			String html = rm.doGet(false, prefeituraRequest);
			Document doc = Jsoup.parse(html);
			
			// <tr> cria linha
			// <td> cria coluna
			
			Elements menuTable = doc.select("table.fundo_cardapio");
			Element lunchTable = menuTable.get(0);
			// Trata tabela do almoco
			Elements options = lunchTable.select("td");
			String finalText = "";
			for(int i = 0; i < options.size(); i++)
				finalText = finalText + options.get(i).text() + "\n";
			
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
