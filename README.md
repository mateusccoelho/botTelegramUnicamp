# Bot Telegram Unicamp
Bot para o aplicativo Telegram que dá informações sobre a unicamp.

### Funcionalidades
- /cardapio: exibe o cardápio dos restaurantes universitários da Unicamp.
- /tempo: mostra o clima em Barão Geraldo de acordo com o Centro de Pesquisas Meteorológicas e Climáticas Aplicadas à Agricultura.

### Especificações
- Implementado em Java SE versão 7.
- O bot só funciona se o programa estiver rodando. alguem arranja um servidor pls

### Para fazer
- Implementar tratamento da página HTML do cardápio quando este não estiver disponível.
- Formatar o texto do cardápio para ficar bonitinho na mensagem.
- Adicionar parametros no comando /cardapio que informam o dia do cardápio e qual o tipo (almoço, vegetariano, jantar).
- Implementar completamente o comando /tempo usando a biblioteca Jsoup de forma parecida que foi utilizada no /cardapio.
- Implementar qualquer outra coisa que seja legal. 

### Bibliotecas usadas
- [JSON in Java](http://www.json.org/java/index.html "JSON in Java"): já está adicionado ao projeto.
- [jsoup: Java HTML Parser](http://jsoup.org/ "jsoup: Java HTML Parser"): talvez seja preciso baixar o .jar e adicionar no eclipse (não tenho certeza).

### Sobre o bot
Criado usando a API provida pelo Telegram. Ela permite mandar mensagens de todos os tipo, inclusive com imagens, gifs, links e arquivos. A API tem uma lista de métodos que podem ser chamados usando requerimentos HTTPS e uma lista de objetos com especificações dos seus atributos e o que é obrigatório ou não. Os requerimentos só podem ser feitos com uma token (um tipo de senha) que não pode ser distribuída por aí.
[Telegram API](https://core.telegram.org/bots/api "Telegram API").