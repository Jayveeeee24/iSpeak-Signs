# iSpeak-Signs

DEBUG NOTES

v1.2r2 4/26
- implemented changing of language (Filipino or English) (added new string resource)
- added user addition for first time user (name, avatar, preferred language) in the splash activity
- removed lock feature in video activity
- refactored DBHelper class (UserTable) to add user avatar and selected language
- added 2 categories for video phrases
- added new resources for the new categories
- added a new modifier for updating single data to add language as well as updating multiple data to add a dependency for new user
- refactored strings in about and search fragment (search query hint) to be dependent on string resource
- addded a new function in function helper class (setAppLocale)
- refactored MainActivity to include setting of app locale language upon use
- added pop ups for changing the language in the app and for adding new user upon first time use

v1.2r1 4/24
STRUCTURAL CHANGES
- shrinked app size with minifyEnabled and shrinkResources.
- removed viewBinding feature
- added aapt option not to compress tflite files
- added tensorflow dependencies
- added camera permissions in manifest file
- removed .so files for OpenCV for deprecated architectures (mips, x64, armeabi)

RECOGNITION
- implemented the main fsl recognition in recognize activity using OpenCV
- Camera using JavaCamera
- Switching of camera
- sign language recognition functions such as onResume, onPause, onDestroy, onCameraViewStarted, onCameraViewStopped, onCameraFrame, swapCamera
- added fix for camera where it crashes when memory is leaking
- PPS: fixed by adding a null check for mRgba in onCameraFrame
- the sign language model and recognition boosts up to 75%

OTHER FRAGMENTS
- removed hidden function in about fragment
- added layout for sign language recognition in recognizeActivity

OBJECT DETECTOR CLASS
- implemented the main sign language detection using object detector class
- implemented finger spelling by adding the letters in a textview
- fixed the camera where the memory is leaking when not releasing mat after use (99:103, 204:208)
- removed dialog in onEnterStartedState in CameraBridgeViewBase class


//THE ABOVE COMMITS CONTAINS THE SIGN LANGUAGE RECOGNITION IMPLEMENTATION

v1.1r10 4/14
- last commit before implementing sign language recognition
- changed profile editing name no. of characters to 10 from 8, and allow to have spaces


v1.1r9 4/11
- changed implementation of removing items in favorites (getAdapterPosition to getAbsoluteAdapterPosition)
- changed implementation of videoview in cvsu fragment to exoplayer
- fixed the bug of an external audio still playing when playing audio and video via MediaPlayer and ExoPlayer respectively in learn_word_item, learn_video_item and in video activity
- fixed index out of bound error in favorite recyclerview when removing an item

v1.1r8 4/9
- updated the learn video list for displaying video items
- added and implemented an videoActivity to fix issues with video fullscreen feature
- added a custom controller for activity video
- refactored and updated some fragment layout for code cleanup
- added vector icons for the video functions
- changed the learn video item fragment to implement a thumbnail display for video and on click functions
- implemented and initialized the recyclerview, adapter and item of learn video list recycler
- added styles for the videoActivity function

v1.1r7 4/9
- initialized video item fragment (design and backend)
- added favorite and learn fragment bg for toolbars
- added an imageview in main appbar for learn and fragment toolbar bg
- implemented the toolbar bg change in mainActivity 
- added a dummy resource for video demo in implementing video feature
- changed appbar bg colors

v1.1r6 4/5
- updated DBHelper to remove the data storage for daily login 
- added and updated db method call for multiple data update, single data update and get category
- refactored fragment layout for about, learn word list and video, and profile fragments
- added a function for getCategoryProgress for learn lists in functionHelper
- added and implemented database data dependency for learn word list and learn video list
- updated main Activity and splash activity to initialize username, longest streak and current streak
- updated the team mate description for about fragment
- implemented a data dependency in database for streak count in profile
- updated and removed some strings (to include item or streak count in strings and daily login strings respectively)

v1.1r5 4/4
- added and implemented dummy resource for playing an audio in category items
- updated the dbhelper to include the usertable
- added new credits and acknowledgements in about fragment as well as the descriptions of team members
- added the video category item in home screen (adapters, item and layout)
- implemented database dependent for username in home and profile screen

v1.1r4 4/2
- implemented data gather in database for home-learn, search-category fragment
- refactored some navigation in adapter list to include a try and catch block for error checking

v1.1r3 4/1
- initialized implementation of database with DBHelper
- implemented data gather in database for learn, profile and profile see more fragments
- refactored learn, profile and profile see more java fragment

v1.1r2 3/26
- implemented the popup dialog for logo and images in about fragment
- changed the implementation of day streaks from (Relative) to (Linear) layouts to automatically resize the view.
- implemented the navigation in the search items in search fragment
- implemented navigation for the ff fragment items: favorite phrase item,  

v1.1r1 3/25
- implemented search filter
- implemented see more function for category progress in profile fragment
- created the design for learn word list for items
- refactored list adapters files except home, favorite list adapter
-renamed project files and packages for list adapter for easy read

v1.0r29 3/23
- renamed layout, java project files and packages convention for easy navigation
- created the progress list in profile
- refactored learn main list recycler view for performance boost


v1.0r28 3/22
- renamed learn recycler adapters in home to be specific to home
- created the learn main list (both word and phrases)
- refactored category colors to fit for learn design
- created the renaming of user in profile screen

v1.0r27 3/19
- initialized profile fragment design 
- Finalized design implementation for fsl word of the day fragment (3/4 done), most functions left.
- Finalized design implementation for cvsu fragment (3/4) done, videoview left.
- Solved issue with favorite heart toggle

v1.0r26 3/18
- replaced and finalized the search view implementation
- conceptualized  the dynamic data change in a recycler view
- initialized design in learn fragment
- updated favoriteRecyclerAdapter "remove favorite" to include retrieving name of removed item
- refactored collecting of imageUrl and bgColors to loop process
- redesigned splash activity to disable night mode
- created and initialized design of about fragment
- updated strings for about fragment
- edited the version name to the debug alpha version
 

v1.0r25 3/16
- created search view v1 in action bar

v1.0r24 3/12
- created the favorite category items and adapter
- created profile and search fragments
- updated main activity, menus, navigations and other fragments


v1.0r23 3/09 (error commit name: git commit -a -m)
- created the recycler view for favorite fragment
- refactored favorite fragment

v1.0r22 3/07
- created a toggle for dark mode
- created all navigations except about page
- refactored layout files to fit for dark mode
- initialized shared prefs

v1.0r21 3/06
- added fragments for Mini game, cvsu m&v with their respective menus


v1.0r20 3/05
- edited animations and strings bundle arguments for fragment communication
- Refactored most of the classes (MainActivity, nav_home, SplashActivity, etc.)
- created a new fragment for learn category item

v1.0r19 3/02
- Initialized Video Category List
- Finalized app logo icon
- added category colors

v1.0r18 3/01
- Edited the app logo
- created the splash screen loading

v1.0r17 2/28
- Revised and Finalized Learn RecyclerView in home
- created a navigation for learn see more in home 
- imported some of the category logo for learn

v1.0r16 2/26
- FSL CardView in home
- fixed error on navigation between fragments
- with official keystore
- refactored the whole project
- with learn recycler view in home page






