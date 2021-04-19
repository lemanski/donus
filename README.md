1 - rodar mvn clean install
2 - vai gerar o jar
3 - após rodar o java -jar ....jar
4 - a aplicação vai subir, porta 8080
5 - para acessar o banco de dados, acessar localhost:8080/h2
6 - no diretorio anterior ao do projeto será criado o arquivo do banco.
7 - usuario e senha é donus 
8 - todas as requisições:

--------------------------------------------------------------------------------------------------------------------------------------------------------
Abri a conta
POST > http://localhost:8080/v1/conta/abrirConta
{
    "correntista":{
    "cpf":"2",
    "nomeCompleto":"teste teste"
}}

--------------------------------------------------------------------------------------------------------------------------------------------------------
Deposita

POST > http://localhost:8080/v1/conta/deposito?valor=10&cpf=1

--------------------------------------------------------------------------------------------------------------------------------------------------------
Faz transferencia

POST > http://localhost:8080/v1/conta/transferencia?valor=5&cpfOrigem=2&cpfDestino=2

--------------------------------------------------------------------------------------------------------------------------------------------------------
Lista todas as contas

GET > http://localhost:8080/v1/conta/lista
