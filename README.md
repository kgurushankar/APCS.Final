# APCS.Final
Our goal is to create a top-down multiplayer shooter. The player has 2 dimensional movement independent of it's shooting direction, which can only be in one of the 4 cardinal directions from the player.

The game will be a team death match with 2 teams, ninjas vs pirates, who shoot shuriken and their guns respectively.
with enough servers, the game could become a multiplayer online system.

The primary feature of the game is the low latency multiplayer shooter experience. 

## Instructions:
1. click and join a server, or the provided server
2. select your team
3. use WASD to move
4. use your mouse to change your facing direction and use left click to fire
5. press space to respawn once dead
## Features:
### Must Have:
Single player PVE where you pick a side and play against AI
### Want-To-Have:
Co-Op game with other players on local network
### Stretch:
PvP

## Class List:
**Local:** Player, Projectile, Obstacle, Map, Enemy, Entity, NetworkConnection
**Server:** NetworkConnection, MapGenerator

## Responsibility List:
### [@kgurushankar](https://github.com/kgurushankar)
* Networking (Latency reduction)
* Map Generation
### [@unkemptherald](https://github.com/unkemptherald)
* Player
* User Interaction
* Physics
