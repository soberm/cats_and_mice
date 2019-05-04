# cats_and_mice smalltalk

## how to import old project

check if MetaCello and Filetree are installed (or google it)
probably we have to use Squeak 5.1 

in the Monticello browser press +Repository, select //filetree (or sth like that), select the repo folder of the old project

select the new filetree imported repository on the right side, select Game, Select Game-MP.7, press Load, the same thing for snakes --> now the classes should be available in the browser

open a new Morphic project

copy the images provided in imgSmalltalk into the squeak installation folder, create a background image and most easily call it: bg-1-320x200.png , then open a workspace and run:

|game| 
game := GameWindow new.
game initialize.

game show: 1920@1080

you will have to select images for the players

finished, now we only have to rewrite it so work for our project