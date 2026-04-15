# AnalisysService

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/postgresql-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)

`AnalisysService` é um serviço especializado em realizar análises financeiras automatizadas para ativos de ações (stocks). O sistema processa dados históricos de preços, fundamentos financeiros e métricas de valuation para gerar sinais de investimento baseados em indicadores técnicos e fundamentais.

## 🚀 Funcionalidades

- **Análise Técnica:** Cálculo de indicadores como RSI (Relative Strength Index) e Médias Móveis (MA50, MA200).
- **Análise de Valuation:** Avaliação de múltiplos como P/E (Preço/Lucro), EV/EBITDA, PEG Ratio e FCF Yield.
- **Análise de Fundamentos:** Monitoramento de crescimento de receita, margem líquida, ROE, ROIC, endividamento (Debt/Equity) e Fluxo de Caixa Livre.
- **Geração de Sinais:** Transformação de métricas quantitativas em sinais qualitativos (ex: sinais de tendência, sinais de valuation barato/caro).
- **Persistência de Snapshots:** Armazenamento histórico de todos os estados de análise para auditoria e análise temporal.

## 🛠️ Tecnologias Utilizadas

- **Linguagem:** [Java 21](https://www.oracle.com/java/)
- **Framework Principal:** [Spring Boot 4.0.5](https://spring.io/projects/spring-boot)
- **Persistência:** [Spring Data JPA](https://spring.io/projects/spring-data-jpa) com banco de dados [PostgreSQL](https://www.postgresql.org/)
- **Mapeamento de Objetos:** [MapStruct](https://mapstruct.org/)
- **Redução de Boilerplate:** [Lombok](https://projectlombok.org/)
- **Documentação de API:** [SpringDoc OpenAPI (Swagger UI)](https://springdoc.org/)

## 🏗️ Arquitetura

O projeto segue os princípios da **Clean Architecture**, garantindo que a lógica de negócio seja independente de frameworks e detalhes de infraestrutura.

