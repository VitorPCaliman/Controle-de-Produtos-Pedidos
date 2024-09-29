# Controle de Pedidos e Produtos

![Controle de Pedidos](https://via.placeholder.com/800x200.png?text=Controle+de+Pedidos+e+Produtos)

![Java](https://img.shields.io/badge/Java-17-blue?logo=java&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.4-green?logo=spring&logoColor=white)
![Spring Data JPA](https://img.shields.io/badge/Spring%20Data%20JPA-3.3.4-green?logo=spring&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15.1-blue?logo=postgresql&logoColor=white)
![JUnit 5](https://img.shields.io/badge/JUnit-5.9.2-brightgreen?logo=junit5&logoColor=white)
![Mockito](https://img.shields.io/badge/Mockito-4.0.0-orange?logo=mockito&logoColor=white)

## Descrição

Este microserviço fornece uma API RESTful para o gerenciamento de pedidos e produtos em um sistema de controle. O objetivo é permitir operações de CRUD (Create, Read, Update, Delete) para pedidos e departamentos, com funcionalidades específicas de consulta e filtragem.

## Funcionalidades

### Pedidos
- **Criar Pedido**: Permite a criação de um novo pedido com múltiplos produtos.
- **Atualizar Pedido**: Atualiza um pedido existente, permitindo a adição/remover produtos e alteração de quantidade e valor.
- **Excluir Pedido**: Remove um pedido do sistema.
- **Consultar Pedido por ID**: Obtém detalhes de um pedido específico.
- **Consultar Pedidos por Período**: Lista todos os pedidos dentro de um intervalo de datas, incluindo detalhes dos produtos.

### Departamentos
- **Consultar Departamentos e Produtos**: Filtra departamentos por intervalo de códigos e retorna apenas aqueles que possuem produtos, ordenados por código e descrição.
- **Criar Departamento**: Permite a criação de um novo departamento.
- **Consultar Departamento por ID**: Obtém detalhes de um departamento específico.
- **Atualizar Departamento**: Permite a atualização de um departamento já existente.
- **Deletar Departamento**: Permite a exclusão de um departamento já existente.

  ### Produtos
- **Listar Produtos**: Permite a consulta de todos os produtos.
- **Buscar Produto por ID**: Obtém os detalhes de um dado produto.
- **Criar Produto**: Permite a criação de um novo produto.
- **Atualizar Produto**: Permite a atualização de um produto já existente.
- **Deletar Produto**: Permite a exclusão de um produto já existente.

## Tecnologias Utilizadas

- **Java 17**: Linguagem de programação principal.
- **Spring Boot**: Framework para desenvolvimento de aplicações Java.
- **Spring Data JPA**: Para a persistência de dados e interação com o banco de dados.
- **PostgreSQL**: Sistema de gerenciamento de banco de dados relacional.
- **JUnit 5**: Para testes unitários.
- **Mockito**: Para simulação de comportamentos em testes.

## Instalação

### Pré-requisitos

- Java 17
- Maven
- PostgreSQL

### Passos para Configuração

1. **Clone o Repositório**

   ```bash
   git clone https://github.com/VitorPCaliman/Controle-de-Produtos-Pedidos.git
   cd pedidos

2. **Configure o Banco de Dados**
   
   Crie um banco de dados PostgreSQL chamado pedidos_db utilizando o seguinte Script:
  
  ```sql
  -- Tabela de Produtos
      CREATE TABLE produto (
      id SERIAL PRIMARY KEY,
      codigo VARCHAR(50) NOT NULL,
      descricao VARCHAR(255) NOT NULL,
      preco DECIMAL(15, 2) NOT NULL
    );

-- Tabela de Pedidos
    CREATE TABLE pedido (
    id SERIAL PRIMARY KEY,
    numero VARCHAR(50) NOT NULL,
    data DATE NOT NULL
    );

-- Tabela de Departamentos
    CREATE TABLE departamento (
    id SERIAL PRIMARY KEY,
    codigo VARCHAR(50) NOT NULL,
    descricao VARCHAR(255) NOT NULL
    );

-- Tabela intermediária que relaciona os Produtos e Pedidos
    CREATE TABLE item_pedido (
    id SERIAL PRIMARY KEY,
    produto_id INTEGER NOT NULL,
    pedido_id INTEGER NOT NULL,
    quantidade INTEGER NOT NULL,
    valor_venda DECIMAL(15, 2) NOT NULL,
    valor_total DECIMAL(15, 2) NOT NULL,
    FOREIGN KEY (produto_id) REFERENCES produto(id),
    FOREIGN KEY (pedido_id) REFERENCES pedido(id)
    );
 ```
  e configure as credenciais no arquivo:

  ```properties
  spring.datasource.url=jdbc:postgresql://localhost:5432/pedidos_db
  spring.datasource.username=seu_usuario
  spring.datasource.password=sua_senha
  spring.jpa.hibernate.ddl-auto=update
  spring.jpa.show-sql=true
```

3. **Baixe as Dependências**

Execute o comando a seguir para baixar todas as dependências do projeto:

```bash
mvn clean install
```


4. **Executar os Testes**

```bash
mvn test
```

5. **Execute o Projeto**

Utilize o Maven para rodar a aplicação:

```bash
mvn spring-boot:run
```

6. **Acesse a API**

   A API estará disponível em:

```link
http://localhost:8080/
```


# Exemplos de Uso e Testes via Postman

Você pode testar os endpoints do microserviço usando o Postman. Abaixo estão exemplos de requisições para os principais endpoints.

### Departamentos:

#### 1. Criando Departamentos:

- **Método**: `POST`
- **URL**: `http://localhost:8080/departamentos`
- **Corpo**:
  ```json
  {
  "codigo": "DEP001",
  "descricao": "Eletrônicos"
  }

#### 2. Criando Departamentos:

- **Método**: `PUT`
- **URL**: `http://localhost:8080/departamentos/{id}`
- **Corpo**:
  ```json
  {
  "codigo": "DEP002",
  "descricao": "Móveis"
  }

#### 3. Buscando Departamento por ID:

- **Método**: `GET`
- **URL**: `http://localhost:8080/departamentos/{id}`

#### 4. Buscando Departamento por ID:

- **Método**: `DELETE`
- **URL**: `http://localhost:8080/departamentos/{id}`

#### 5. Buscando Departamento por Iintervalo de código:

- **Método**: `DELETE`
- **URL**: `http://localhost:8080/departamentos/{id}](http://localhost:8080/departamentos?codigoInicial=DEP001&codigoFinal=DEP005`

### Produtos:

#### 1. Criando Produtos:

- **Método**: `POST`
- **URL**: `http://localhost:8080/produtos`
- **Corpo**:
  ```json
  {
  "codigo": "PROD001",
  "descricao": "Smartphone",
  "preco": 1500.00,
  "departamento": {
    "id": 1
    }
  }

#### 2. Atualizando Produtos:

- **Método**: `PUT`
- **URL**: `http://localhost:8080/departamentos/{id}`
- **Corpo**:
  ```json
  {
  "codigo": "PROD001",
  "descricao": "Laptop",
  "preco": 3000.00,
  "departamento": {
    "id": 1
    }
  }

#### 3. Buscando Produtos por ID:

- **Método**: `GET`
- **URL**: `http://localhost:8080/produtos/{id}`


#### 4. Deletando Produtos por ID:

- **Método**: `DELETE`
- **URL**: `http://localhost:8080/produtos/{id}`

#### 4. Listando todos os Produtos:

- **Método**: `GET`
- **URL**: `http://localhost:8080/produtos`
  
### Pedidos:

#### 1. Criando Pedidos:

- **Método**: `POST`
- **URL**: `http://localhost:8080/pedidos`
- **Corpo**:
  ```json
  {
  "numero": "PED001",
  "data": "2023-09-28",
  "itens": [
    {
      "produto": {
        "id": 1
      },
      "quantidade": 2
      }
    ]
  }

#### 2. Atualizando Pedidos:

- **Método**: `POST`
- **URL**: `http://localhost:8080/pedidos`
- **Corpo**:
  ```json
  {
  "numero": "PED010",
  "data": "2023-09-28",
  "itens": [
    {
      "produto": {
        "id": 55
      },
      "quantidade": 3
      }
    ]
  }

#### 3. Buscando Pedidos por ID:

- **Método**: `DELETE`
- **URL**: `http://localhost:8080/pedidos/{id}`

#### 4. Deletando Produtos por ID:

- **Método**: `DELETE`
- **URL**: `http://localhost:8080/pedidos/{id}`

#### 5. Buscar Pedidos por Período:

- **Método**: `GET`
- **URL**: `/pedidos/periodo?dataInicial=2023-09-01&dataFinal=2023-09-30`


# Melhorias Futuras e Aplicações

### Ideias para Melhorias

- **Autenticação e Autorização**: Implementar segurança para a API usando Spring Security, permitindo que apenas usuários autenticados possam criar ou editar pedidos.
- **Pagination e Sorting**: Adicionar suporte a paginação e ordenação nas consultas de pedidos e departamentos, facilitando a navegação em grandes volumes de dados.
- **Documentação da API**: Usar ferramentas como Swagger ou OpenAPI para gerar uma documentação interativa da API.


### Áreas de Aplicação da API

- **E-commerce**: O microserviço pode ser integrado a plataformas de comércio eletrônico para gerenciar pedidos de clientes.
- **Gestão de Estoque**: Utilizar a API para controlar o estoque de produtos e relacioná-los a pedidos, facilitando a gestão de inventário.
- **Relatórios e Análises**: Implementar funcionalidades que permitam a geração de relatórios de vendas e estatísticas de pedidos ao longo do tempo.


