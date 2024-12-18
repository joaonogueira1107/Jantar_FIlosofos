Introdução

Este é uma implementação simples do Problema dos Filósofos usando Sockets em Java. O objetivo é simular a interação entre filósofos que alternam entre pensar e comer, utilizando garfos compartilhados. O servidor gerencia as conexões dos filósofos e a sincronização dos garfos. Neste código, você pode usar o Telnet para se conectar ao servidor e simular o comportamento dos filósofos.

---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

Componentes
1. Server.java
Este é o servidor principal que gerencia a comunicação com os filósofos. Ele cria os garfos e registra os filósofos. Cada filósofo é identificado por um ID único.

PORT: A porta na qual o servidor escuta as conexões (por padrão, 12345).
Garfo: A classe que representa um garfo, que pode ser pegado e liberado por filósofos.
PhilosopherData: Armazena os dados do filósofo, como número de pensamentos e refeições.
ClientHandler: A classe que gerencia cada conexão de um filósofo. Ela processa as mensagens recebidas e lida com as requisições de pegar e liberar garfos.

2. Filosofo.java
Este é o código do filósofo que tenta interagir com o servidor, pedindo garfos para comer e informando quando está pensando ou comendo.

TELNET: Você pode usar o Telnet para simular múltiplos filósofos se conectando ao servidor.
Comando de interação: Envia comandos como HELLO, THINKING, REQUEST_FORKS, e RELEASE_FORKS para o servidor.

3. Garfo.java
Representa um garfo, que pode ser pego ou liberado. Cada garfo é compartilhado entre dois filósofos.

Método pegar(): Tenta pegar o garfo. Se ele já está em uso, retorna false.
Método liberar(): Libera o garfo.

-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

Como Executar
Passo 1: Configurar o Servidor
Abra um terminal e navegue até o diretório onde os arquivos .java estão localizados.

Compile o código Java usando o comando:

javac Server.java Filosofo.java Garfo.java


Execute o servidor:

java Server

O servidor agora estará esperando por conexões na porta 12345.

Passo 2: Conectar ao Servidor com Telnet

Abra outro terminal e use o Telnet para conectar ao servidor na porta 12345:

telnet localhost 12345

O servidor deve responder com:

HELLO

Agora você está conectado como um filósofo e pode interagir com o servidor. Tente os seguintes comandos:

HELLO: Conecta o filósofo ao servidor (o servidor responderá com o ID do filósofo).
THINKING: Informa que o filósofo está pensando.
REQUEST_FORKS: Solicita os garfos. O servidor responderá com GRANTED se os garfos estiverem disponíveis ou DENIED caso contrário.
RELEASE_FORKS: Libera os garfos após o filósofo terminar de comer.


Passo 3: Simular Filósofos

Você pode abrir várias conexões Telnet (ou várias instâncias do programa Filosofo) para simular filósofos competindo pelos garfos.
Cada filósofo vai tentar pensar, pegar os garfos, comer, e depois liberar os garfos.

Comandos
HELLO: Conecta um novo filósofo ao servidor. O servidor responderá com um ID único para o filósofo.
THINKING: Informa ao servidor que o filósofo está pensando.
REQUEST_FORKS: Solicita os garfos para comer.
RELEASE_FORKS: Libera os garfos após comer.

Observações
O servidor usa synchronized para garantir que dois filósofos não peguem o mesmo garfo ao mesmo tempo.
A interação entre os filósofos é gerida pelo servidor, que mantém o controle de quem está usando os garfos e quando eles são liberados.
Cada filósofo tem uma chance aleatória de pensar e comer, criando uma simulação interessante do comportamento dos filósofos.

Problemas Comuns
Erro de Conexão: Se você não conseguir se conectar ao servidor usando Telnet, verifique se o servidor está realmente em execução na porta 12345.
Comando Desconhecido: Se você enviar um comando inválido, o servidor responderá com UNKNOWN_COMMAND. Certifique-se de que os comandos estão corretos.
Divirta-se simulando filósofos!