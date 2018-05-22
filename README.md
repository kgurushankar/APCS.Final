# APCS.Final
## By Keshav Gurushankar and Andrew Scott
## Summary:
Our goal is to create a top-down multiplayer shooter. The player has 2 dimensional movement dependent on their shooting direction, which can only be in one of the 4 cardinal directions from the player.

The game will be a team death match with 2 teams, ninjas vs pirates, who throw shuriken and shoot their guns respectively.

With enough servers, the game could become a multiplayer online system.

The primary feature of the game is the low latency multiplayer shooter experience. 

### Multiplayer Mode
* Two teams face off
* Host can choose to have AI, configure map size, and max number of players
* Lose by dying

### Singleplayer Mode
* Player vs Enviornment
* Play with variable number of AI players
* Score is the number of enemies killed before you die

## Instructions:
1. click and join a server, or the provided server
2. select your team
3. use WASD or arrow keys to move
4. use your mouse to fire
5. when you die, join another server, or try to get back on the one you were just playing on
## Features:
### Must Have:
* Map generation
* Single player PVE
* Picking teams
* enemies are controlled by AI
* Co-Op game with other players on local network
### Want-To-Have:
* AI poses an actual threat
* Variable render distances (visibilty/pre-loading) to dynamically deal with connection latency
* Team selection (change mid-game)
* 30+ fps (on LAN)
* Classes of enemies (variable weapons and stats)
### Stretch:
* PvP
* Powerups
* Automatic team balancing
* 60+ fps (on WAN and LAN)
* WAN connectivity
### Complete:
* Map generation
* Single player PVE
* Picking teams
* enemies are controlled by AI
* Co-Op game with other players on local network
* PvP

## Class List:
### Local
* Main
* DrawingSurface
* ClientConnection
* Game (Mainly used here)
* MenuBar
* Settings

### Both
* Player
* Animation
* Entity
* Projectile
* Map
* State
* Enemy
* FileIO
* Sendable (interface)

### Server
* Computor
* MapGenerator
* Message
* Server
* ServerMain
* ServerState
* MenuBar
* Settings

## Responsibility List:
### [@kgurushankar](https://github.com/kgurushankar) (Keshav Gurushankar)
* Networking (Latency reduction)
* Data Structures
* Map Generation
* Computor
* Initial config windows
### [@unkemptherald](https://github.com/unkemptherald) (Andrew Scott)
* Player
* User Interaction
* Physics
* Animation
### [@anantajit](https://github.com/anantajit)
* Feedback
### [@devdragon2875](https://github.com/devdragon2875)
* Feedback

## Dependencies 
* [Processing](https://processing.org)