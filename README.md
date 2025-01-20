Piano Tiles Pro is a rythme game that is played on a PC or laptop.

**Table of Contents:**

1. How to Play

2. Judgement Rules

3. Level Specification

4. Keyboard Shortcuts in Different Panels

5. User System

6. Other Notes

7. Author Credits

********************************************

**How to Play**

The main game consists of 8 lanes and UI is shown with the following picture

Each lane has a specific key to trigger a pressed event.



Note that although in the same lane, the notes that land on the bottom judgement line does NOT have the same trigger key as the notes that rise to the upper judgement line。

For the bottom 8 lanes, from left to right, is A,S,D,F,J,K,L,and the semicolon key

For the skyline

Also, the keys to trigger hold notes need to be held until the hold note completly disappears.

Finally, if notes needs to be tapped at the same time they will have a yellow outline.

*********************************

**Judgement Rule**

Define T as the absolute value of the time interval between the key event is triggered to the note's time that it lands on the judgement line

Every tap note has 4 different judgement states

1. **Perfect**:  T<=90ms (5.4 frames at 60FPS)

2. **Good**: 90ms<T<=160ms(5.4 frames to 9.6 frames at 60FPS)

3. **Bad**: 160ms<T<=200ms(9.6f frames to 12 frames at 60FPS)

4. **Miss**: T>200ms (over 12 frames at 60FPS)

Except that the hold notes does not have a **Bad** judgement state, which means that miss for hold notes is T>160ms (12 frames at 60FPS), the other judgement rules are the same as tap notes.

The **Worst Hit** of a game is defined as the largest T value



For each note played, you get an **Accuracy** (acc) for that note. The accuracy threasholds are:

1. **Perfect** ==> 100% acc

2. **Good** ==> 65% acc

3. **Bad/Miss** ==> 0% acc

By taking the average of the accuracy of every note played, you get the overall accuracy of the chart.



The **Score** is calculated differetly.

Let M denote the number of notes in the largest interval in the game for which no notes have a judgement state of **Bad** or **Miss**

Let N denote the total number of notes in the game

Then the final score is equal to overall_acc * 900000+M/N * 100000

Depending on the final score, you can get these different **Grades** in the game:

1. **F**: score<700000

2. **C**: 700000<=score<820000

3. **B**: 820000<=score<880000

4. **A**: 880000<=score<920000

5. **S**: 920000<=score<960000

6. **V**: 960000<=score<1000000

If no notes in the game are **Bad** or **Miss**, and there is at least one **Good**, then the grade will have a blue color. We call this state **Full Combo (FC)**

Otherwise, the color of the grade will be white

There are two other grades that you can get by playing every note with a **Perfect** judgement (we call this **All Perfect (AP)**):

1. **P**: green, denote that score=1000000, acc=100% and 20ms (1.2 frames at 60FPS) <=**Worst Hit**<=90ms

2. **T**: magenta, denote that score=1000000, acc=100% and Worst Hit**<=20ms (1.2 frames at 60FPS)

************

**Level Specification**

In this game, there are multiple sets of songs that are called **Collections**

Each collection has multiple **Songs**, which contains a variety of different levels of **Chart**

A **Chart** is a .txt file that encodes every position-time function of every note within the game



Each **Song** is guarenteed to have 3 different levels: **Basic (BS)**, **Medium (MD)** and **Advanced (AV)**

Some songs have an optional fourth level called **Legendary (LG)**

The level of the song can range between 1.0 and 16.9, with 1.0 being the easiest and 16.9 being the hardest

****************************

**Keyboard Shortcuts in Different Panels**

1. Welcome Page: 
   
   - Enter: enter the game

2. Collection Selection Page:

   
   - left/right arrow keys: move left/right
   
   - Backspace: go back to Welcome Page
   
   - Enter: enter the Collection displayed on the screen
   
   - S: go to Settings
   
   - U: go to User Selection Page

3. Song Selection Page:
   
   - Backspace: go back to Welcome Page
   - Enter: play the chart with the level and song displayed on the screen
   - S: go to Settings
   - U: go to User Selection Page
   - up/down arrow keys: move up/down the song menu
   - left/right arrow keys: move left/right on the level selection menu
   - Ctrl+A: switch to Autoplay (view what the chart looks like when a grade of **T** is achieved)

4. Chart (main game UI):
   
   - Space: pause/go
   
   - Backspace: go back
   
   - Ctrl+R: restart
   
   - - Side note: you have to pause the game first for go back and restart options to be enabled

5. Result Page:
   
   - Backspace: go back to Song Selection Page
   
   - Ctrl+R: restart the chart

6. Settings:
   
   - Backspace: go back to where you entered the Settings page
   
   - PageUp/PageDown: switch between Hints page and Time page
   
   - Within Hint page:
     
     * S: enable/disable sync notes
     
     * F: enable/disable FC/AP indicator
     
     * H: enable/disable left/right hand hints
   
   - Within Time page: 
     
     - left/right arrow keys: adjust music delay
     
     - Shift+left/right arrow keys: adjust tolerance
     
     - Ctrl+R: reset user data

7. User Selection: 
   
   - Backspace: go back to where you entered the User Selction page
   
   - left/right arrow keys: switch user
   
   - Enter: select the user displayed on the screen or add new user (depending on where you're at in User Selection)

****************************

**User System**

The game has a very basic user system.

All user data is stored in a folder called **Users** in the same directory of the application

On the first time you start the game, it will run a simple check to see that all the charts exists and are not corrupted

Then it will create the **Users** folder for you and generate a user called **Userxxxxxxxxxx** where **x** represents a digit between 0 and 9

After you close the application, a **Userxxxxxxxxxx.ptpuser** file will appear in the **Users** folder

You can change the filename to change the username.

Every time you close the application your data is saved in that .ptpuser file



If you choose to reset your userdata in the Settings page, you will find a file that ends in ".userarchive" with the same username within the **Users** folder

That is your data before it was reset. You can change the file type back to .ptpuser to revert the changes, or you can delete it permenently.

******************

**Other Notes**

1. There is also a **ptp_settings** file within the directory of the application. It has some general information about who is the last person that logged in the game and where was the person at in the game, as well as if the game was started for the first time. You shouldn't delete it, though deleting it will not cause a very big difference. It will be regenerated everytime you close the application

2. When in the game, you might see a **.refresh** file within the application directory. Ignore that. It is just a buffer for force refreshing the audio file and should be deleted every time it you exit a game or exit the application. If not, you can delete it yourself. It is not a big deal.

3. The only song that can be played is the *Twinkle Twinkle Little Star*, the last song in the Collection *Faraway Stars*, which has all 4 levels. More songs will be added in the future. Remember that this is still just a testing version.

*************

**Author Credits**

This application is created solely by Boyan Hu. Please acknowledge him if needed to cite the code inside this application



Boyan Hu.

2025.1.19
