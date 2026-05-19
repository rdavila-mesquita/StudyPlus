# Study+ 📚

Aplicativo mobile desenvolvido para auxiliar estudantes na organização dos estudos, gerenciamento de assuntos e controle de foco utilizando a técnica Pomodoro.

## ✨ Funcionalidades

* 🔐 Autenticação de usuários com Firebase
* 📖 Cadastro e gerenciamento de assuntos de estudo
* ✏️ Edição e exclusão de assuntos
* ⏱️ Temporizador Pomodoro para sessões de foco
* 🎯 Controle de metas diárias de estudo
* 📊 Acompanhamento do progresso das metas
* 📱 Interface moderna utilizando Jetpack Compose

---

## 📸 Telas do Aplicativo

* Splash Screen
* Login e Cadastro
* Home
* Gerenciamento de Assuntos
* Temporizador Pomodoro
* Controle de Metas

---

## 🛠️ Tecnologias Utilizadas

### Mobile

* Kotlin
* Jetpack Compose
* Material 3
* Navigation Compose

### Backend e Serviços

* Firebase Authentication
* Firebase Firestore

### Arquitetura e Ferramentas

* Android Studio
* Gradle Kotlin DSL
* ViewModel

---

## 📂 Estrutura do Projeto

```bash
Study+
 ┣ app
 ┃ ┣ src/main/java/com/example/study
 ┃ ┃ ┣ activity
 ┃ ┃ ┣ navigation
 ┃ ┃ ┗ ui
 ┃ ┣ res
 ┃ ┗ AndroidManifest.xml
 ┣ gradle
 ┣ build.gradle.kts
 ┗ settings.gradle.kts
```

---

## ▶️ Como Executar o Projeto

### Pré-requisitos

* Android Studio instalado
* JDK 11+
* Conta Firebase configurada

### Passos

1. Clone o repositório:

```bash
git clone https://github.com/rdavila-mesquita/StudyPlus.git
```

2. Abra o projeto no Android Studio.

3. Configure o Firebase:

* Crie um projeto no Firebase
* Adicione o arquivo `google-services.json` na pasta:

```bash
app/
```

4. Execute o projeto em um emulador ou dispositivo físico.

---

## 🔥 Configuração do Firebase

O projeto utiliza:

* Firebase Authentication
* Cloud Firestore

Certifique-se de habilitar esses serviços no painel do Firebase.

---

## 📌 Objetivo do Projeto

O Study+ foi desenvolvido com foco em produtividade acadêmica, ajudando estudantes a:

* Organizar conteúdos
* Manter constância nos estudos
* Melhorar o foco
* Acompanhar metas diárias

---

