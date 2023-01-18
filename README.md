Projeto básico de agência bancária, que realiza cadastro de contas, depósitos, saques, transferências e listagem de todas as contas. 
Utilizando POO, DAO e JDBC para conectar com banco de dados MYSQL.




Abaixo o script das tabelas do banco de dados:


CREATE TABLE holder (
  id INT NOT NULL PRIMARY KEY AUTO_INCREMENT ,
  name VARCHAR(100) ,
  document VARCHAR(12) ,
  birthDate DATE
);


CREATE TABLE account (
	id INT NOT NULL PRIMARY KEY AUTO_INCREMENT ,
  number VARCHAR(8) ,
  balance DOUBLE ,
  holder INT NOT NULL ,
  FOREIGN KEY (holder) REFERENCES holder (id)
);
