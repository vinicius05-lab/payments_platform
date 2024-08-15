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

# Sobre as rotas da API:

### Cadastro de Usuário

- **Rota:** `POST /users/register`
- **Descrição:** Cadastra um novo usuário.

### Login de Usuário

- **Rota:** `POST /login`
- **Descrição:** Realiza o login do usuário, retornando um token JWT.

### Envio de Transferências

- **Rota:** `POST /transactions`
- **Descrição:** Realiza a transferência de um usuário para o outro.
- **Permissão:** Para ter acesso a esta rota o usuário deve está autenticado.

### Listagem de Usuários

- **Rota:** `GET /users`
- **Descrição:** Retorna a lista de todos os usuários cadastrados. Apenas usuários com a role `ADMIN` podem acessar esta rota.
- **Permissão:** Para ter acesso a esta rota o usuário deve ser um administrador.
- **Exemplo de Resposta:**

```json
   [
    {
        "id": 1,
        "firstName": "João",
        "lastName": "Silva",
        "document": "123.456.789-09",
        "email": "joao.silva@example.com",
        "password": "$2a$10$wfi88j8YxRNbVctu.48bXeASyL4eqVy38cbfvLQJZnXUq17anWD0q",
        "balance": 1250.00,
        "userType": "COMMON",
        "role": "ADMIN",
        "authorities": [
            {
                "authority": "ROLE_ADMIN"
            },
            {
                "authority": "ROLE_USER"
            }
        ],
        "username": "joao.silva@example.com",
        "accountNonExpired": true,
        "accountNonLocked": true,
        "credentialsNonExpired": true,
        "enabled": true
    },
    {
        "id": 2,
        "firstName": "Guilherme",
        "lastName": "Silva",
        "document": "987.654.321-00",
        "email": "gui@example.com",
        "password": "$2a$10$0xj7ytr8GFW4Mgu75p9GyOkYFXq/LXqBYS2s77hsQzt4DXOO9Rmka",
        "balance": 500.00,
        "userType": "COMMON",
        "role": "ADMIN",
        "authorities": [
            {
                "authority": "ROLE_ADMIN"
            },
            {
                "authority": "ROLE_USER"
            }
        ],
        "username": "gui@example.com",
        "accountNonExpired": true,
        "accountNonLocked": true,
        "credentialsNonExpired": true,
        "enabled": true
    }
]

```
### Exibição de Usuário por ID

- **Rota:** `GET /users/{id}`
- **Descrição:** Retorna os detalhes de um usuário específico, identificado pelo ID. Apenas usuários com a role `ADMIN` podem acessar esta rota.
- **Permissão:** Para ter acesso a esta rota o usuário deve ser um administrador.
- **Exemplo de Resposta:**

```json
{
    "id": 1,
    "firstName": "João",
    "lastName": "Silva",
    "document": "123.456.789-09",
    "email": "joao.silva@example.com",
    "password": "$2a$10$wfi88j8YxRNbVctu.48bXeASyL4eqVy38cbfvLQJZnXUq17anWD0q",
    "balance": 1250.00,
    "userType": "COMMON",
    "role": "ADMIN",
    "authorities": [
        {
            "authority": "ROLE_ADMIN"
        },
        {
            "authority": "ROLE_USER"
        }
    ],
    "username": "joao.silva@example.com",
    "accountNonExpired": true,
    "accountNonLocked": true,
    "credentialsNonExpired": true,
    "enabled": true
}

```

### Listar Transações do Usuário Autenticado

- **Rota:** `GET /users/my-transactions`
- **Descrição:** Retorna todas as transações realizadas pelo usuário autenticado.
- **Permissão:** Para ter acesso a esta rota o usuário deve está autenticado.
- **Exemplo de Resposta:**
  
```json
[
    {
        "id": 1,
        "amount": 250.00,
        "sender": {
            "id": 2,
            "firstName": "Guilherme",
            "lastName": "Silva",
            "document": "987.654.321-00",
            "email": "gui@example.com",
            "password": "$2a$10$0xj7ytr8GFW4Mgu75p9GyOkYFXq/LXqBYS2s77hsQzt4DXOO9Rmka",
            "balance": 500.00,
            "userType": "COMMON",
            "role": "ADMIN",
            "authorities": [
                {
                    "authority": "ROLE_ADMIN"
                },
                {
                    "authority": "ROLE_USER"
                }
            ],
            "username": "gui@example.com",
            "accountNonExpired": true,
            "accountNonLocked": true,
            "credentialsNonExpired": true,
            "enabled": true
        },
        "receiver": {
            "id": 1,
            "firstName": "João",
            "lastName": "Silva",
            "document": "123.456.789-09",
            "email": "joao.silva@example.com",
            "password": "$2a$10$wfi88j8YxRNbVctu.48bXeASyL4eqVy38cbfvLQJZnXUq17anWD0q",
            "balance": 1250.00,
            "userType": "COMMON",
            "role": "ADMIN",
            "authorities": [
                {
                    "authority": "ROLE_ADMIN"
                },
                {
                    "authority": "ROLE_USER"
                }
            ],
            "username": "joao.silva@example.com",
            "accountNonExpired": true,
            "accountNonLocked": true,
            "credentialsNonExpired": true,
            "enabled": true
        },
        "transactionDate": "2024-08-15T18:05:30.063451"
    }
]

```

### Listar Transações Recebidas pelo Usuário Autenticado

- **Rota:** `GET /users/received-transactions`
- **Descrição:** Retorna todas as transações recebidas pelo usuário autenticado.
- **Permissão:** Para ter acesso a esta rota o usuário deve está autenticado.
- **Exemplo de Resposta:**

```json
[
    {
        "id": 1,
        "amount": 250.00,
        "sender": {
            "id": 2,
            "firstName": "Guilherme",
            "lastName": "Silva",
            "document": "987.654.321-00",
            "email": "gui@example.com",
            "password": "$2a$10$0xj7ytr8GFW4Mgu75p9GyOkYFXq/LXqBYS2s77hsQzt4DXOO9Rmka",
            "balance": 500.00,
            "userType": "COMMON",
            "role": "ADMIN",
            "authorities": [
                {
                    "authority": "ROLE_ADMIN"
                },
                {
                    "authority": "ROLE_USER"
                }
            ],
            "username": "gui@example.com",
            "accountNonExpired": true,
            "accountNonLocked": true,
            "credentialsNonExpired": true,
            "enabled": true
        },
        "receiver": {
            "id": 1,
            "firstName": "João",
            "lastName": "Silva",
            "document": "123.456.789-09",
            "email": "joao.silva@example.com",
            "password": "$2a$10$wfi88j8YxRNbVctu.48bXeASyL4eqVy38cbfvLQJZnXUq17anWD0q",
            "balance": 1250.00,
            "userType": "COMMON",
            "role": "ADMIN",
            "authorities": [
                {
                    "authority": "ROLE_ADMIN"
                },
                {
                    "authority": "ROLE_USER"
                }
            ],
            "username": "joao.silva@example.com",
            "accountNonExpired": true,
            "accountNonLocked": true,
            "credentialsNonExpired": true,
            "enabled": true
        },
        "transactionDate": "2024-08-15T18:05:30.063451"
    }
]

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
