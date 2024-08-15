# Projeto de API com Spring Boot e H2 Database

## Sobre o Projeto
Este projeto é um sistema de plataforma de envio e recebimento de transferências, onde nele também há um sistema de autenticação completo para que assim seja possível realizar as operações.

# Sobre o funcionamento da API:
Para que esta API funcione corretamente na sua máquina você deve realizar as configurações corretas do banco de dados mysql no "application.properties":

- **Banco de Dados MySQL:**
   - Configure o acesso ao seu banco de dados MySQL no arquivo `application.properties`.
   - Exemplo:
     ```properties
        spring.datasource.url=jdbc:h2:mem:db_transaction
        spring.datasource.driverClassName=org.h2.Driver
        spring.datasource.username=admin
        spring.datasource.password=3301
        spring.h2.console.enabled=true
        spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
        spring.jpa.hibernate.ddl-auto=update
        spring.jpa.show-sql=true
        logging.level.org.springframework.security=DEBUG
        logging.level.org.springframework.web=DEBUG
     ```

# Sobre o envio de requisição:
As requisições devem ser feitas nos seguintes formatos JSON:

## Requisição para cadastro de usuário:
```json
{
    "firstName": "user_firstname",
    "lastName": "user_lastname",
    "document": "123.456.789-09",
    "email": "user@exemple.com",
    "password": "123user",
    "balance": 1000,
    "userType": "COMMON"
}
```

## Requisição para efetuar login:
```json
{
    "email": "user@exemple.com",
    "password": "123user"
}
```

## Requisição para realizar transação:
```json
{
    "amount": 250,
    "senderId": 1,
    "receiverId": 2
}
```

# Obs:
- Todos os usuários por padrão são salvo no banco de dados com a role de ADMIN:

```java
public User(UserRequest data){
        this.firstName = data.firstName();
        this.lastName = data.lastName();
        this.document = data.document();
        this.email = data.email();
        this.password = data.password();
        this.balance = data.balance();
        this.userType = data.userType();
        this.role = UserRole.ADMIN; //Para mudar é só trocar UserRole.ADMIN para UserRole.USER
}
```
Nota: Em uma aplicação real, não é recomendado salvar os usários como administradores, pois eles terão o poder de adicionar, deletar e atualizar produtos e categorias.
Coloquei ADMIN como padrão por ser apenas um projeto individual e para facilitar a vida quem quer usar e testar a aplicação de forma completa, realizando coisas que só o administrador poderia fazer.
