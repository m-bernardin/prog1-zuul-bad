Project: zuul-bad
Authors: Michael Kölling and David J. Barnes

This project is part of the material for the book

   Objects First with Java - A Practical Introduction using BlueJ
   Seventh edition
   David J. Barnes and Michael Kölling

This project is a simple framework for an adventure game. In this version,
it has a few rooms and the ability for a player to walk between these rooms.
That's all.

To start this application, create an instance of class "Game" and call its
"play" method.

This version of the game contains some very bad class design. It should NOT
be used as a basis for extending the project without fixing these design
problems. It serves as an example to discuss good and bad design (chapter 8
of the book).

Chapter 8 of the book contains a detailed description of the problems in this
project, and how to fix them.

The project 'zuul-better' contains a version of this project with better
designed class structure. It includes the fixes discussed in the book.

part i:
q1.
    - plays a text-based walking simulator
    - help, quit, go
    - help prints a list of commands, quit ends the game, and go accepts a second command word to determine which room to go to
    - 5; outside, pub, office, lab, theatre
    - [pub]<->[outside]<->[theatre]
                  ^
                  |
                  v
    - [office]<->[lab]
q2. 
    Game: manages game parts, creates the rooms
    Command: holds the users last command
    Room: holds data on a single room
    Parser: gets the users command
    CommandWords: holds the known command words and checks if a command is valid
q3. a dungeon crawler, with some sort of final challenge at the end of a series of puzzles, secret passages, and interactions with other characters
q4. see image in repo

q9. returns a Set which contains all the keys in a HashMap    

q13. the Room stored in currentRoom changes
q17. yes, because the processCommands method still checks each command by name

q18. it would probably better to just make the methods static, this would mean we wouldnt have to create anh object for the commandWords class to make it work, but it would mean that adding instance methods would be more difficult

part ii:
q19. wasnt aware we had to implement it the other way
q22. a model for how to implement interaction between the code and the user, and how to represent it, as the name suggets it implements a model (the way information is stored internally), a view (how the information is presented to the user), and the controller (how the two are linked together). its relevant to this project becuase there is a lot of complex internal information which must somehow be neatfully conveyed to the user. It could be implemented by implementing a view class which handles sending all messages to the user

