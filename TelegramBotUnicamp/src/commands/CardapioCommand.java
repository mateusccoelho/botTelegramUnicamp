package commands;

import java.net.URLEncoder;
import java.sql.Date;
import java.util.Calendar;
import java.util.TimeZone;

import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import conexao.RequestMaker;

public class CardapioCommand extends Command {

	CardapioCommand(RequestMaker rm) {
		super(rm);
	}

	@Override
	public void doAction(JSONObject message) {
		
		Calendar c = Calendar.getInstance();
		c.setTime(new Date(message.getLong("date") * 1000));
		c.setTimeZone(TimeZone.getDefault());
		c.setFirstDayOfWeek(Calendar.SUNDAY);
		int diaSemana = c.get(Calendar.DAY_OF_WEEK);
		
		if(diaSemana == Calendar.SUNDAY || diaSemana == Calendar.SATURDAY) {
			String errorMessage = "Hoje é fim de semana!";
			String URLErrorMessage = URLEncoder.encode(errorMessage, "UTF-8");
			
			String funcao = "sendMessage?chat_id=" + message.getJSONObject("chat").getLong("id") + "&text=" + URLErrorMessage;
			rm.doGet(true, funcao);
			return;
		}
		else {
			int diaMes = c.get(Calendar.DAY_OF_MONTH);
			int ano = c.get(Calendar.YEAR);
			int mes = c.get(Calendar.MONTH) + 1;
			String prefeituraRequest = "http://legiao.prefeitura.unicamp.br/cardapio.php?d=" + ano + "-" + mes + "-" + diaMes;
			String html = rm.doGet(false, prefeituraRequest);
			Document doc = Jsoup.parse(html);
			
		}
			
	}

}
