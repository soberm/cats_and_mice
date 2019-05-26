# Cats and Mice in Smalltalk

## Install

1. Install FileTree in Squeak

```Smalltalk
Installer ss3
    project: 'FileTree';
    install: 'ConfigurationOfFileTree'.
((Smalltalk at: #ConfigurationOfFileTree) project version: #'stable') load.
```
2. In the Monticello browser press +Repository, select filetree:// and select the repository folder.

3. Select the new filetree imported repository on the right side, select CatsAndMice and press Load.

4. Copy the images provided in images into the squeak installation folder.

## Run

Open a Transcript and run:

```
CatsAndMiceGame width: 1920 height: 1080 cats: 2 mice: 3 isCat: false.
```
