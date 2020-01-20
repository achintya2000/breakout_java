Game - Breakout
====

This project implements the game of Breakout.

Name: Achintya Kumar

### Timeline

Start Date: Jan 11, 2020

Finish Date: Jan 19, 2020

Hours Spent: ~35

### Resources Used
- https://www.tutorialspoint.com/javafx/index.htm
- Stack Overflow
- JavaFx Javadoc
- https://www.youtube.com/watch?v=FVo1fm52hz0 (JavaFx Game for Beginners)
- geeks for geeks java tutorials

### Running the Program

Main class: GameStateUpdate

Data files needed: 
- Level Files: level1.txt, level2.txt, level3.txt (Letters that represented
 brick types are split by spaces so a function can generate those specified
 bricks into an nx5 grid)
- Score File: highScore.txt (Stores highest score)
- Image gif files are used for the in-game graphics. brick1.gif, brick2.gif,
brick4.gif, brick10.gif, paddle.gif, paddle_long.gif, sizepower.gif,
pointspower.gif, extraballpower.gif
- Audio file: pong_beep.wav sound plays when ball hits paddle

Key/Mouse inputs: 
- Left and right arrow keys to move paddle left and right.
- Escape key exits application.

Cheat keys: 
- Number Keys 1-3 start their specific levels.
- S - Super ball. Makes all bricks take 1 hit to kill.
- B - Bomb defusal. Removes all bomb bricks from the level
- L - Add lives to the player
- G - God mode. Add 1 million lives and S & B cheats.
- Z - Zoom. Increases paddle movement speed.
- W - I win button. Automatically registers as win and updates high score.

Known Bugs:
- Hitting a brick at a specific angle on the left or right side will cause 
the ball to reverse direction in the y axis instead of x axis.
- No crashes or other bugs observed.

Extra credit: 
- High score mechanic implemented so users will have a motivation
to keep playing. 
- Sound effect implemented when the ball hits the paddle.

### Notes/Assumptions
I believe the design of this program follows good object oriented principles
as each class has its own purpose and common data/functions are moved up to
parent classes. The level class and level generator function are especially
good examples how I designed this program to be modular. This class can handle
any level txt file input as long as its formatted properly using the key letters
and spaces. Assuming we follow the standard level data design, to extend the
game and add a level it is simply a matter of adding the level text file and
a single else if clause in the updateLevelOrWinState function. Everything else
will be handled by program. One note to mention is that static variables were
used in the UIElements class. It made sense to do this because those object
variable are independent of object. The value of the variable, which is the 
Text object, is used in multiple other classes and is needed to update the
score or lives of the player. We assume they are common to all instances of 
the class and in fact save memory. A third thing to note is the use class
hierarchy where parent Sprite extends ImageView and it represents all the 
pictures in the screen. Other objects extend this class and their specific
functionality.

### Impressions
This project was challenging to complete - mainly due to the limited time 
frame and the fact that I have never written code that was evaluated by
design and not functionality. I did really appreciate the lab that we did
because it gave us a lot of example code to get us started and images to use
in our game. In the future I think maybe a more concrete intro to JavaFx
would be useful. I think if some design tips or recommended design structures 
for the game were offered, it would be very useful. What is extremely frustrating
is having to write so much in the javadoc. The requirement to write purpose, 
assumptions, an example of how to use it, and dependencies for classes and 
methods is a lot and could be toned down.
