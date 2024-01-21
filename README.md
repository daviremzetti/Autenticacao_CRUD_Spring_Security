# Autenticacao_CRUD_Spring_Security
API para autenticar usuários, autorizar conforme a role: cadastrar e listar usuários e produtos para ROLE ADMIN e somente listar produtos para ROLE USER.
Tecnologias:
- JAVA
- MAVEN
- SPRING SECURITY
- SPRING DATA
- SPRING VALIDATION
- LOMBOK
- JWT AUTHENTICATION
- MYSQL
Para o front end:
- THYMELEAF
- BOOTSTRAP

Para acessar testar a autenticação e a validação do usuário via navegador:
Acessar: http://localhost:8080/autenticacao
Fazer login com usuários previamente cadastrados:

Login: ADMIN
Senha: ADMIN

ou 

Login: USER
Senha: USER


Para testar via API cliente:

FAZER LOGIN:
Requisitar: http://localhost:8080/autenticacao
Fazer login com usuários previamente cadastrados:

Login: ADMIN
Senha: ADMIN

ou 

Login: USER
Senha: USER


CADASTRAR NOVO USUÁRIO:
Inserir bearer token de autenticação recebido no login
Requisitar: http://localhost:8080/auth/registrar
Enviar JSON:
{
 'nome':'nome do novo usuário',
 'login': 'login do novo usuário',
 'role': 'role do novo usuário',
 'senha': 'senha do novo usuário'
}


LISTAR USUÁRIOS:
Inserir bearer token de autenticação recebido no login
Requisitar: http://localhost:8080/auth/listar


CADASTRAR NOVO PRODUTO:
Inserir bearer token de autenticação recebido no login
Requisitar: http://localhost:8080/produto/cadastrar
Enviar JSON:
{
 'nome':'nome do novo produto',
 'valor': 'valor do novo produto',
}

LISTAR PRODUTOS:
Inserir bearer token de autenticação recebido no login
Requisitar: http://localhost:8080/produto/listar

Segue anexo arquivo do banco de dados, com nome script.sql, com usuários previamente cadastrados.
 
