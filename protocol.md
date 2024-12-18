Este documento descreve o protocolo de comunicação utilizado entre o servidor e os filósofos no modelo do Problema dos Filósofos. O protocolo utiliza uma comunicação baseada em texto simples, onde o servidor e os filósofos trocam mensagens para coordenar as ações de pensar, pegar e liberar garfos.

Estrutura do Protocolo
A comunicação entre o servidor e os filósofos é feita via TCP/IP com o uso de Sockets. O servidor escuta conexões de filósofos na porta 12345. Cada filósofo se conecta ao servidor, envia comandos e recebe respostas conforme sua interação com o ambiente (pensar, pegar garfos, comer, etc.).

1. Conexão Inicial
Quando um filósofo se conecta ao servidor, a primeira mensagem que ele envia é o comando HELLO para iniciar a comunicação.

Comando Enviado (Filósofo → Servidor)

HELLO

Resposta do Servidor (Servidor → Filósofo)

HI <ID_DO_FILOSOFO>

ID_DO_FILOSOFO: Um identificador único atribuído pelo servidor ao filósofo.

Exemplo:
HI 1

2. Pensando
Quando o filósofo está pensando, ele envia o comando THINKING para o servidor. O servidor apenas confirma que o comando foi recebido.

Comando Enviado (Filósofo → Servidor)

THINKING

Resposta do Servidor (Servidor → Filósofo)

OK

3. Solicitação de Garfos
Quando o filósofo deseja comer, ele precisa solicitar os garfos. Ele envia o comando REQUEST_FORKS para o servidor.

Comando Enviado (Filósofo → Servidor)

REQUEST_FORKS

Resposta do Servidor (Servidor → Filósofo)

Se os garfos estiverem disponíveis, o servidor responde com GRANTED.
Se não for possível pegar ambos os garfos (por exemplo, se outro filósofo já estiver usando um deles), o servidor responde com DENIED.

Exemplos:

Garfos disponíveis:
GRANTED

Garfos não disponíveis:
DENIED

4. Comendo

Após receber a permissão para pegar os garfos, o filósofo pode comer. Durante esse tempo, ele não precisa enviar mensagens ao servidor.

O tempo de comer é simulado no código (geralmente 3 segundos).

5. Liberação de Garfos

Após terminar de comer, o filósofo deve liberar os garfos para que outros filósofos possam usá-los. Ele envia o comando RELEASE_FORKS para o servidor.

Comando Enviado (Filósofo → Servidor)

RELEASE_FORKS

Resposta do Servidor (Servidor → Filósofo)

OK

6. Comando Desconhecido
Caso o filósofo envie um comando que não seja reconhecido pelo servidor, este responderá com o comando UNKNOWN_COMMAND.

Comando Enviado (Filósofo → Servidor)

<comando_inválido>

Resposta do Servidor (Servidor → Filósofo)

UNKNOWN_COMMAND

Exemplo Completo de Interação
1.Conexão inicial:

Filósofo envia:
HELLO

Servidor responde:
HI 1

2.Filósofo pensando:

Filósofo envia:
THINKING

Servidor responde:
OK

3.Filósofo solicita garfos:

Filósofo envia:
REQUEST_FORKS

Servidor responde (caso os garfos estejam disponíveis):
GRANTED

4.Filósofo come:

Não há comunicação necessária durante o ato de comer.

5.Filósofo libera garfos:

Filósofo envia:
RELEASE_FORKS

Servidor responde:
OK


A sincronização entre os filósofos é garantida pelo uso de synchronized nos métodos pegar() e liberar() da classe Garfo, o que impede que dois filósofos peguem o mesmo garfo ao mesmo tempo.

Além disso, o servidor utiliza synchronized para garantir que as operações de pegar e liberar garfos sejam realizadas de maneira segura e não haja conflitos entre filósofos.

Considerações Finais
O servidor gerencia a alocação de garfos e a comunicação entre os filósofos de forma simples e eficiente.
O protocolo de comunicação é assíncrono e baseado em mensagens textuais simples, o que permite fácil interação via Telnet.
Filósofos podem ser simulados facilmente conectando múltiplas instâncias do cliente ao servidor.
Este protocolo visa simular a concorrência entre filósofos de forma controlada, utilizando a sincronização de garfos compartilhados, para garantir que todos possam pensar e comer de maneira ordenada.