# Quizzler

**Quizzler** est une application Android de quiz que j'ai développée dans un cadre **personnel**, dans le but d’enrichir ma culture générale via un jeu interactif. Elle s’appuie sur l’API **Open Trivia DB** pour générer dynamiquement des questions, et offre une expérience utilisateur simple et fluide.

## 🚀 Fonctionnalités

- Sélection d’une **catégorie de questions** (ex : science, histoire…)  
- Affichage de questions avec **choix multiples**, et validation de la réponse  
- Calcul et affichage du **score final** à la fin du quiz

## 🧱 Architecture & Stack technique

- **Langage** : Java  
- **Architecture** : **MVP**  
- **Composants Android** : ViewModel, Navigation Component  
- **API** : Intégration de l’API Open Trivia (requêtes HTTP, parsing JSON) via des identifiants API  
- **Persistance locale** : SQLite (base locale pour stocker des données si besoin)
---

---
## 🛠️ Installation & utilisation

Pour installer et lancer l’application sur un appareil Android :

1. Clone le projet depuis GitHub :  
   ```bash
   git clone https://github.com/AlexAlkhatib/Quizzler.git
````

2. Ouvre le projet avec **Android Studio**.
3. Assure-toi d’avoir installé le SDK Android approprié (API level), et que Gradle peut résoudre les dépendances.
4. Build l’application (`Build > Make Project`) et installe-la sur ton appareil ou un émulateur via `Run > Run 'app'`.
5. (Facultatif) Si l’API Open Trivia nécessite une clé ou une configuration, ajoute-la dans les **fichiers de configuration** (par exemple un `local.properties` ou un `secrets.xml`) — explique comment tu gères cela.
---

## 💡 Points techniques mis en avant

Ce projet met en lumière plusieurs compétences techniques clés :

* **Architecture MVP + séparation des responsabilités** : logique de présentation, données, et interface bien découplées
* **Appels API et parsing JSON** : récupération dynamique des questions via l’API Open Trivia
* **Gestion du cycle de vie Android** via ViewModel
* **Navigation fluide** avec Navigation Component
* **Persistance locale** avec SQLite
* **Qualité du code** : structure claire, code modulable, design orienté “bonnes pratiques”

---

## 🔭 Prochaines étapes / Améliorations possibles

* Ajouter un **timer** pour rendre le quiz plus dynamique
* Implémenter un **mode hors-ligne** en préchargeant des questions dans la base SQLite
* Ajouter des **niveaux de difficulté** ou des quiz thématiques avancés
* Enregistrer les **historique des scores** pour offrir des statistiques utilisateur
* Ajouter des **tests unitaires** ou des tests d’intégration pour certaines parties (parsing, logique de quiz…)

---

## 🧑‍💻 À propos de moi

* Nom : Alex Alkhatib
* Profil GitHub : [github.com/AlexAlkhatib](https://github.com/AlexAlkhatib)
* Intéressé par les postes Android / Java / Mobile development

---

## 📄 Licence

```
MIT License
Copyright (c) 2025 Alex Alkhatib
```
