_3D Random Number Generation Visualization_
------
__Description__  
This program is a test program I created to learn how to use and manipulate the LibGDX cameras.  

I needed something to actually see on the screen to make it more interesting to test the camera's mechanics.  
So I decided to visualize random movement of objects (in this case cubes) using Java's Random class.  
The specific movement code can be found in com.bigbass1997.test.Main, lines 109-122.  

*With my computer's hardware:*  
FPS is capped at 60 and holds there throughout the program's life.  
If FPS cap is removed (com.bigbass1997.test.desktop.DesktopLauncher, line 16, set to 0), FPS ranges from 60-100.  

__Camera Controls__  
W/S : Moves camera forward/backward  
A/D : Moves camera left/right  
Q/E : Rotates the camera counterclockwise/clockwise according to the direction camera is looking  
LSHIFT/SPACE : Moves camera down/up  
And you can click and drag (left mouse button) to rotate the camera in all ways.  

__License__  
Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License  
License summary and links found in LICENSE.md  
Full license found in LICENSE.txt

While this license was initially committed after creation of git repository, this license also applies to the following commits:  
37ceab6a32bfbe48d7ae95e666a06513dbedd528  
9271e67d310036c7e5782e8af8d62b7c6a3d6183  
946fb922cc92354013d8cdd5cd4ecdfda49f157e  
cde73454d0d59e0ff13e82f82cd8db6a5df5c9ea  
3c34639c4b7e0f9cb8c667ff6a89a23e4630fd85  

(If someone could figure out how to add the license and readme files to all commits mentioned, that would be great to know so I can remove this information.)