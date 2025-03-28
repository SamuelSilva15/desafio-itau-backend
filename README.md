## Projeto de Gerenciamento de transação

Este projeto realiza a consulta, salvamento e processamento de estatésticas configuráveis de transações, utilizando tecnologias como Java (Spring Boot), Docker, Swagger, Prometheus e Grafana.

📌 Tecnologias Utilizadas

Backend: Java 11, Spring Boot

Armazenamento: Em memória

Monitoramento: Prometheus e Grafana

Autenticação: JWT, AWS IAM

Infraestrutura: Docker, Docker Compose

⚙️ Configuração do Ambiente

Pré-requisitos

Java 11 ou superior

Maven instalado

Docker e Docker Compose

🚀 Como Executar

1️⃣ Clonar o Repositório

git clone https://github.com/SamuelSilva15/desafio-itau-backend/

2️⃣ Construção do Backend

mvn clean install

3️⃣ Executar a Aplicação

Opção 1: Via Maven

mvn spring-boot:run


🛠️ Banco de Dados

Para o projeto foi exigido que não fosse utilizado nenhum banco de dados, apenas o processamento em memória.

✅ Testes

Testes Unitários e de Integração

mvn test

