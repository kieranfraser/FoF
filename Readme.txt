Fortress of Flags: A.I. Sandbox for Developers

This version of Fortress of Flags is designed to interface with an external 
Control Harnesses executable. The control harness can be written in any language. 
The control harness needs to be able to do three things:

1) Create a config.ini file in the target directory of the Fortress of Flags 
3D game engine (FoF3DGE) and launch the FoFAIS.exe program.

2) Scan the FoF3DGE target directory for the existence of a file called 
AIDataInput.xml and import it for its own use.

3) Make decisions based on the AIDataInput.xml contents and write a 
AICommand.xml file to the FoF3DGE target directory with the correct instructions 
for the 'Red' flag army to follow. Then return to step 2.

YOUR GOAL IS TO CREATE YOUR OWN CONTROL HARNESS!

Starter Kit Included:
An example VB6 Control Harness is included named 'ExampleControlHarness.exe' 
Its source files are:

ControlHarness.vbp
ControlHarness.vbw
ControlHarness.frx
ControlHarness.frm

It is well commented so feel free to reuse any code within it that you wish. 
This is just a simple program that allows you to simulate the A.I. moves by 
issuing manual moves using its map viewer interface which will generate the 
output AICommand.xml file matching your orders. Since I wrote the original 
A.I. embedded in the first Fortress of Flags game I just decided to do a 
simulation tool instead for learning purposes. I don't want to cloud your 
designs with my pre-conceived notions about how you might go about developing one.

You have my permission to create any type of control harness you wish as long 
as it does no harm to any computer it's installed on or interfaces with. Players 
who share control harnesses are advised to scan them for viruses and be weary of 
control harness EXE's that aren't written by a trusted source. It is recommended 
that control harnesses be shared with both the executable files and their source. 
Weary users should review the shared source and recompile it on their own systems 
to be safe. Developers are welcome to create harnesses that interface with web 
services if you wish to protect your A.I. logic routines but the installed client 
interface should be distributed with its source.

SCHOOLS AND TEACHERS ARE WELCOME TO USE THIS AS An EDUCATIONAL TOOL! 
COMPETITION IS ENCOURAGED!

Technical notes about the Control.ini file:
This is a three line text file.
Line 1 is the location of the Map file.
Line 2 is the location of the SAV file (saved game to resume) Otherwise this line 
should say 'None'.
Line 3 is a 1 or a 0. 
1 forces the FoF3DGE to launch in full screen mode. 
0 is windowed mode (640x480).

Example file contents:
-----------------------
Narrow Pass.map
None
 0 
-----------------------

Technical notes about the AIDataInput.xml file:
This FoF3DGE generated file is created after every Blue turn or in cases of 
error after a failed Red turn. Under the xml follows this format 
(see note D to see how you can create an example file): 
-----------------------
<Root>
 <LastMove Success='True'/>
 <MapGrid>
      <Map X='1' Y='1' Terrain='Stone' FlagID='0' Army='None' Rank='None' Revealed='False'/>
      ...
      <Map X='80' Y='40' Terrain='Stone' FlagID='0' Army='None' Rank='None' Revealed='False'/>
 </MapGrid>
</Root>
-----------------------

The LastMove node will have Success=True is the game just started or after a 
valid Red army command is issued. The MapGrid node contains a Map node for every 
square on the 80x40 FoF3DGE battlefield (3200 squares). The first square X=1, Y=1 is 
the bottom right square on the Blue army end of the battlefield and the last entry 
X=80, Y=40 is the top left corner of the battlefield as viewed from the Blue armies 
end of the battlefield. The FlagID attribute contains the FoF3DGE key. Use this when 
issuing commands to the game engine. The Terrain attribute will be one of four types 
Grass, Rock, Water or Stone. Stone squares cannot contain flags and create battlefield 
boundaries and obstacles. Rock and Water allow some pieces to use them and grass allows 
all player flags to use it (see original Fortress of Flags game documentation for 
details). The Army attribute is either Red or Blue. The Rank attribute gives you the 
flag type for all Red army flags and Blue army flags that have been revealed. The 
Revealed attribute is most useful to help your A.I. determine if the player is aware 
of your flags rank.


Technical notes about the AICommand.xml file:
This is the file your Harness should be designed to issue. It is a SINGLE line XML 
file that must contain the same positioning as the example (the FoF3DGE is limited in its xml 
parsing options):
-----------------------
<Root><Command FlagID='XXXX' Direction='X' SerpentJump='XX'/></Root>
-----------------------

The 4 character FlagID attribute comes from the key provided by the AIDataInput.xml Map tag. 
The 1 character Direction should be one of the following N for North, S for South, E for East 
or W for West.
The 2 character SerpentJump attribute is how many spaces you want the flag to move if it is 
a serpent, non serpent flags will ignore this attribute. Non Serpent flags can simply use 00 
or 01 but anything is accepted as long as it's two characters.


Other notes:
a) By default the 'Blue' flag army is controlled by the player using the FoF3DGE and the 
Control Harness controls the 'Red' flag army.

b) Both the FoF3DGE and the example control harness create their XML files ad TMP files and 
rename then once written. This minimizes the potential for the programs to both try to use 
the file at the same time causing lock conditions which could result in an crash/error state.

c) The flag artwork has been separated from the FoF3DGE allowing developers to create 
custom interfaces using the  provided artwork or create custom game pieces giving the game 
a new unique look. John Reder and Tactical Neuronics assume no responsibility for 
inappropriate content generated by foF A.I. Sandbox developers. Please review the image 
contents provided by independent players/developers before using.

d) To generate a copy of the AIDataInput.xml file launch the FoF3DGE and make a Blue flag 
move (click on its center pole mark and use the one of the W S A D keys to move it. 
Close the FoF3DGE exe and locate the AIDataInput.xml file in its target directory.

e) The first release of the FoF A.I. Sandbox is v2.0.0.

f) Report all bugs and enhancements to the 'Community' Player Forum link on the 
TacticalNeuronics.com web site or E-mail to John.Reder@TacticalNeuronics.com


