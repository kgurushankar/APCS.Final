# APCS.Final
## By Keshav Gurushankar and Andrew Scott
## Summary:
Our goal is to create a top-down multiplayer shooter. The player has 2 dimensional movement independent of it's shooting direction, which can only be in one of the 4 cardinal directions from the player.

The game will be a team death match with 2 teams, ninjas vs pirates, who shoot shuriken and their guns respectively.
with enough servers, the game could become a multiplayer online system.

The primary feature of the game is the low latency multiplayer shooter experience. 

### Multiplayer Mode
* Two teams face off
* Host can choose to have AI, configure map size, and max number of players
* Lose by total number of Deaths across the team exceeding a certain number

### Singleplayer Mode
* Can choose how many players on each team to manage difficulty
* Play with AI with variable difficulty levels
* Score is the KD ratio of the player

## Instructions:
1. click and join a server, or the provided server
2. select your team
3. use WASD or arrow keys to move
4. use your mouse to fire
5. press space to respawn once dead
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

## Class List:
### Local
* Main
* DrawingSurface
* ClientConnection
* Game (Mainly used here)

### Both
* Player
* Animation
* Entity
* Projectile
* Map
* State
* Enemy

### Server
* Computor
* Config
* DrawMap
* MapGenerator
* Message
* Server
* ServerMain
* Server State

## Responsibility List:
### [@kgurushankar](https://github.com/kgurushankar) (Keshav Gurushankar)
* Networking (Latency reduction)
* Data Structures
* Map Generation
* Computor
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