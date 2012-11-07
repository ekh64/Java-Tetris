2012 Update:
To compile, open command prompt and change to directory containing the program ("Java-Tetris-master" if downloaded from github)

$ javac Tetris.java
$ java Tetris

and the application should open and high score list should display in the command prompt.

Erica Hyman
12/17/07
ekhyman@gmail.com
Project 3: Tetris!

Description: Move the blocks and try to eliminate as many rows as possible before you reach the top. Then it's game over!

Complete with rotating blocks
7 random Tetrominos
High Score List!
5 Brutal Levels
Full Line Elimination!
Keyboard Control

Controls:
UP: rotate Tetrominos
LEFT: move Tetromino left
RIGHT: move Tetromino right
DOWN: move Tetromino down
P: pause the game/resume the game

Included Files:
highscores.txt
	- Stores the high scores list
Out.java
	-Sedgewicks Out class, used to write to highscores.txt
Tetris.java
	- The main Tetris applet, containing  timers and jpanels
Tetromino.java
	- Stores each Tetromino
README.txt
	- This file

NOTES: The highscore list will print out in the command prompt. This was a deliberate choice of mine.

In addition I chose not to have the ability to 'slide' the Tetrominos once they land, as some games have implemented.

I did not want to include "chain reactions" in this game.

I successfully implemented all parts as required on the sheet. I worked very hard to get to this point.
