**About this Project**

The ArtRoom
Welcome to the GitHub repository for The ArtRoom, this project was developed during the NOVI Hogeschool education course for Full-Stack Developer.
This application is made as a final project. The project is a JAVA RESTfull backend for a (non-existent) art-gallery, its artists and its customers.

A 'the ArtRoom' frontend application was also made by me. Please find the repository here:
[https://github.com/basschoonens/frontend-the-art-room-eindopdracht](https://github.com/basschoonens/frontend-the-art-room-eindopdracht)

Installation


Download and install IntelliJ or any other appropriate IDE.


Install PGAdmin 4

Open de backend in IntelliJ IDEA:
Start IntelliJ IDEA en selecteer ‘Open’ om het uitgepakte zip-bestand map te openen als een bestaand project.

Configureer de PostgreSQL Database:
Zorg ervoor dat PostgreSQL is geïnstalleerd en draait op je systeem.
Maak een nieuwe database aan (bijvoorbeeld the_art_room_db) en onthoud de verbindingsgegevens (URL, gebruikersnaam, wachtwoord).

Pas de database-instellingen aan:
Ga naar src/main/resources/application.properties in IntelliJ IDEA.
Configureer de database-instellingen:
spring.datasource.url=jdbc:postgresql://localhost:5432/the_art_room_db spring.datasource.username=your_username spring.datasource.password=your_password

Build de backend:
Open de terminal in IntelliJ. Voer het volgende commando uit om de backend te builden en dependencies te downloaden:

MVN CLEAN INSTALL

Start de backend server:

Klik op de ‘Run’ knop in IntelliJ IDEA om de Spring Boot applicatie te starten.

De backend server zou nu moeten draaien op http://localhost:8080.
