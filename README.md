# 📦 project-mercantil

Este projeto segue os princípios da **Clean Architecture** combinados com **modularização** para garantir um código limpo, escalável e de fácil manutenção.

---

## 🧠 Visão Geral da Arquitetura

<img width="976" alt="Captura de Tela 2025-04-21 às 23 00 45" src="https://github.com/user-attachments/assets/8455e35c-1814-4c17-8cb7-1e470cde1ac3" />

---

## 🎯 Objetivo do App

O objetivo principal da aplicação é permitir o **login de um usuário** com os seguintes passos:

1. O usuário informa `usuário` e `senha`.
2. As credenciais são **criptografadas**.
3. As informações criptografadas são **validadas**.
4. O app exibe:
   - Uma **tela de sucesso**, caso o login seja válido.
   - Uma **tela de erro**, caso contrário.

---

## 🧱 Estrutura dos Módulos

### `feature-login`

Este módulo contém tudo relacionado ao fluxo de login:

- **presentation**: contém `ViewModels`, `StateFlow` e lida com a lógica de UI.
- **domain**: define os casos de uso (`use cases`) e entidades puras.
- **data**: contém os modelos e implementações necessárias para criptografia/validação.

### `core`

Módulo com utilitários e componentes reutilizáveis como:
- Extensões
- Configurações comuns
- Recursos compartilhados

### `app`

O módulo de entrada principal do projeto:
- Inicializa a aplicação
- Conecta os módulos e faz a **injeção de dependência**
- Define a navegação entre telas

---

## 🔄 Comunicação entre camadas

- `presentation` → depende de `domain`
- `domain` → depende de `data`
- `app` → injeta dependências e conecta os módulos
- `core` → fornece utilitários comuns compartilhados entre módulos

---

## ✅ Vantagens dessa abordagem

- **Baixo acoplamento** entre camadas
- **Alta coesão** nos módulos
- Facilita **testes unitários**
- Permite **escalabilidade** com novas features em novos módulos
- Facilita **reutilização** de regras de negócio em múltiplos pontos da app

---

## 🛠 Tecnologias e práticas utilizadas

- Kotlin
- Clean Architecture
- Modularização por feature
- ViewModel + StateFlow
- Testes Unitários
- Gradle Kotlin DSL

---
