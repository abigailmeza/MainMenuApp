# Main Menu Application

## What is this project?
This project simulates the functions of the main-menu or start-up screen of a video-game. It allows users to create
new games, and both access and delete saved games.

## Interest Behind Project
Being an avid gamer my entire life, I've encountered all kinds of main title screens varying from 
minimalistic designs to ones who favoured interactivity and complexity. However, my personal favourite main menu screens
have always had two things in common: an **incredible graphical interface** and an **ease of navigation** between 
various players modes and game settings.

## User Stories
* As a user, I want to be able to create a new game and add it to a list of saved games
* As a user, I want to be able to delete a game from a list of saved games
* As a user, I want to be able to view my list of saved games
* As a user, I want to be able to save my list of saved games to file
* As a user, I want to be able to load my list of saved games from file

## Phase 4: Task 2
Test and design a class in your model package that is robust
##### Methods:
* Game (both constructors in Game class)
* addGame (in JsonReader class)

## Phase 4: Task 3
 If you had more time to work on the project, is there any refactoring
  that you would do to improve your design?
  * With more time, I would increase the cohesion of my ui package, specifically in the MainMenuGUI class.
   The class contains more than one single responsibility when it comes to creating the various panels and buttons
   for the JFrame.