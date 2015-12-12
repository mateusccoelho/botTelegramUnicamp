package conexao;

import conexao.RequestMaker;

public class Main {

	public static void main(String[] args) throws Exception {
		
		String path = "/home/mateus/Unicamp/Extras/unicampServicos/token.txt";
		RequestMaker rm = new RequestMaker(path);
		String funcao = "sendMessage?chat_id=143999051&text=vao+se+foder+e+tudo+meu";
		String json = rm.doGet(funcao);
		System.out.println(json);
	}

}
