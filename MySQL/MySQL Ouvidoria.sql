create database ouvidoria;

create table manifestacoes (

	id int primary key auto_increment,
   
	texto varchar(100),
    tipo varchar(15),
    nome varchar(50),
    cpf varchar(12),
    email varchar(30)
    
);
