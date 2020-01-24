# Design File - CS308 Breakout

## Name: Achintya Kumar

##### Project Roles: Achintya Kumar - All roles (individual project)

### Project Design Goals

The design goal of this project is to create a 2D Breakout game using Java and
OpenJFX. The game should have some sort of ball, paddle, and brick interaction
where the player controls a game paddle to hit bricks with a game ball. There
will be some power ups that help the player in the game as well as cheat code
functionality that also help the player when hitting specific keys. A player
typically loses lives when the ball goes below a certain section of the screen
and gains points by hitting bricks. In addition to the functionality requirements,
the goal was to create a well designed project that had clean readable code,
avoided code smells (code duplication, long methods, etc), and used multiple 
classes with useful purposes that divided the code base. Some new features 
I wanted to make easy to add included: new types of bricks, new levels, and 
new power ups. 

### High level design

I believe the design of this program decently followed object oriented principles
as each class has its own purpose and common data/functions are moved up to
parent classes. The Sprite class and its children (Ball, GamePaddle, MultiBrick, 
and PowerUp) are good examples of code that I designed to be have inheritance. 
The Sprite class represents anything that is moving on the screen and its child
classes add functionality specific to each of them in order to reduce code 
duplication. Sprite extends ImageView as all the objects on my screen are 
represented by images. The Level class and level generator function are 
especially good examples how I designed this program to be modular. This 
class can handle any level txt file input as long as its formatted properly 
using the key letters and spaces. Assuming we follow the standard level data 
design, to extend the game and add a level it is simply a matter of adding 
the level text file and a single else if clause in the updateLevelOrWinState
function. I also had a class dedicate to displaying screen elements called
UIElements. This class has methods that displayed all the things on the 
screen and I think is a good example of how a class should have its own
complete purpose. The PowerUp class provides a template for each power up
and extends Sprite like many other classes. MultiBrick is a highly modular
class and represents all the types of bricks on my screen including the 
life brick, regular brick, many hit brick and bomb brick. The GameStateUpdate
class combined all the previously listed classes and is the main starting
point of the program. It manages the state of the game including updating
score, lives, collision physics, and win state.

### Assumptions or decisions regarding required features

One assumption I had was regarding the required feature about reading the 
levels from the text file. I am assuming that any new levels would be created
in the same specified format so because my program does not handle any cases
in which they are not. Additionally, I made the decision to represent all
of the things I display in my screen as an ImageView. This contributed greatly
to the required feature of having all the game elements specified as well as
the required feature of having multiple classes. This was because I was able
to define a parent class depending on ImageView and since all my game objects
were ImageViews, they were able to extend their parent in a clean hierarchy
easily. Also I assumed that since graphics quality was not a required metric,
that simple text on a screen would be sufficient for splash screens to convey
information. This allowed me more time to focus on required features.

### Describe how to add new features to your code

Adding a new level to my code is very easy. All you need to do is write
a text file in the specified format where certain letters represent certain
blocks. There you need to update one function in GameStateUpdate that checks
which level to move forward to. To do this you need to need to add a case to 
an if statement. If you want to add a new power up to my code you need to
update the RandomlyGeneratePowerUp function in GameStateUpdate to assign it
a probability of being generated - this would be a new else if case. Then
in the updatePowerUpState function you need to add a switch case to add the
game logic for if there is a collision between the paddle and the power up.
Adding new bricks is also not very hard. The MultiBrick class is very extensible
and to create a new type of brick one must just define a new if statement case
in the levels class to handle when adding it to the level text file. Then
in the GameStateUpdate class there needs to be a new if case in the 
updateBallStateFromBricks function to define the functionality of the new brick
when a collision occurs. To add new a new splash screen you must create a new 
function that returns a scene in the UIElements so that in GameStateUpdate
you can call setScene to change the display. 