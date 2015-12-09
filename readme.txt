
    
    <include layout="@layout/layout_colourpanel"
    	android:id="@+id/layout_colourpanel"
    	android:layout_gravity="center_vertical"
    	android:layout_width="wrap_content"
    	android:layout_height="wrap_content"
    	android:layout_alignParentleft="true"
    	android:layout_alignParentTop="true" />
    	
    <include layout="@layout/layout_shapespanel"
    	android:id="@+id/layout_shapspanel"
    	android:layout_width="wrap_content"
    	android:layout_height="wrap_content"
    	android:layout_alignParentleft="true"
    	android:layout_below="@+id/layout_courpanel" />
    	
    <include layout="@layout/layout_controlpanel"
    	android:id="@+id/layout_controlpanel"
    	android:layout_gravity="center_vertical"
    	android:layout_width="wrap_content"
    	android:layout_height='wrap_content"
    	android:layout_alignParentBottom="true"
    	android:layout_alignParentRight="true" />



20151209
I created a new project named lab03f

what i learnt today:

0. about TextView
	0. <TextView
			android:id="@+id/tvheader"
			android:layout_width="300dip"
			android:layout_height="wrap_content"
			android:background="#EC7703"
			android:textColor="#000000"
			android:text="  Edit Items"  
			android:background="@color/background"  />
	1. fill_parent has been depreciated since API 8
1. LinearLayout
	0. orientation:
		0.1 android:orientation="vertical"
			at this time, the height cannot be "match_parent"
		0.2 android:orientation="horizontal"
			at this time, the width cannot be "match_parent"
	1. gravity:
		1.1 android:gravity-->text
		1.2 android:layout_gravity--->layout
			1.2.1 android:layout_gravity="center_vertical" or "top" or"bottom"

	2. *android:layout_weight="1" means to have a proper width?
2. RelativeLayout
	0. set android:baselineAligned="false" to improve performance





3. GridLayout
	0.             <GridLayout
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                android:columnCount="2"
                android:rowCount="6" >
                
    1. columns and rows start from 0, so column01 and row01, should be the second row, second column
20151208

I created a new project named Lab2

What i learnt today:

0. when I want to draw on screen,
	
	0. I need to have a Canvas: Canvas canvas = new Canvas();
	1. I need a object to paint: Paint paint = new Paint();
	2. I can set the style of the pen, to fill the shape or just draw the frame
		2.1 paint.setStyle(Paint.Style.FILL)
		2.2 paint.setStyle(Paint.Style.STROKE)
		2.3 paint.setStrokewidth(3);
	3. drawdrawText by: canvas.drawText("welcome to Mobile Programming", 5, 10, paint);
	4. draw rectangle
		4.1 make a rectangle: RectF rectF = new RectF(widthOffset, heightOffset, widthOffset+40, heightOffset+40); 
		4.2 draw it by canvas.drawRect(rectF, paint);
		
1. what is a bitMap and how to use it?
	0. bitmap is like a image?
	1. first i have to put a image in my res/drawable folder,
	2. I get out this image by:	
		Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);  
	3. then, canvas.drawBitmap(bmp,x,y,null);
	4. I still don't know the difference between the code up, and this one--> canvas.drawBitmap(bmp, x, y, paint);  


2. I want to use onTouchListener
	0. have a class named ViewBlat.class, to implement this interface
	1. have a global parameter x,y as coordinate, also have a paint and canvas
	2. in the constructor, 		setOnTouchListener(this);
	3. in the onDraw method, get your bitmap and use canvas.drawBitmap() method to draw
	4. override the onTouch method, analyse the event. in my project, I can get the x,y coordinate from the event
		and draw something on the canvas.


20151206

problems I met today:

0. error message like:[2015-12-06 18:28:55 - Circle Animation] Circle Animation does not specify a android.test.InstrumentationTestRunner instrumentation or does not declare uses-library android.test.runner in its AndroidManifest.xml
	solved it by: go to 'run as--run config --unit test, delete test instance'
	
	according to stackoverflow:
		In the Run Configuration you may have Android JUnit Test, if there are any new launch configuration entries inside this, you delete it and then run your application it will run.
		NOTE - This is likely to be the solution if you tried to run the test case before adding the correct lines to the manifest as described in the answer from Josef. If you have done this, delete the configuration (which will be complaining that no instrumentation test runner has been specified in its header) and then run it as an Android Junit Test again and it will create a valid configuration picking up the correct stuff that you have added to the manifest (see Josef's answer for this).

	and, there is also another mistake can cause this error:
	
		You're probably missing the following in your AndroidManifest.xml:
		<instrumentation android:name="android.test.InstrumentationTestRunner"
			android:targetPackage="your.package"
			android:label="your tests label" />
		and
		<uses-library android:name="android.test.runner" />


20151206

problems I met today:

0. I coded according to the book, "fragmentbestpractice", when stalled on my phone, it quit immediately.

check: all the xml documents, corrected : a. Listview --> ListView
then: I can get into this app, however, when I click the title of news, this app quits unexpectedly.

I'll push this with a bug. may be fix it later. I still hold the opinion that there must be some mistake in xml docs.




20151205

problems I met today:

0 I modified  FragmentTest, and add an res/layout-large folder. there comes on error with it in the src/MainActivity.java, saying that "cannot find the 'right-layout'"
	solved it by changing the name to "right_fragment" as eclipse prompted.
	
	another problem comes, I cannot get into the app now. so I have to give up to test with it.


20151203

problems I met today:

0. I have a AVD, and when I'm run my app in the AVD, I got an error on the interface of AVD, said that "unfortunately, the program has stopped.
	in the logCat I can see the error message, which shows that: 
	12-03 21:11:44.652: E/AndroidRuntime(1468): Caused by: java.lang.NoClassDefFoundError: Class not found using the boot class loader; no stack available
	
 according to the stackoverflow, I knew that there could be something wrong with xml file and may be I made some typo on package's name.
			correct it and run again, problem is not solved.
			so, I think there might be something wrong with my ADT, so I created a new app that fits my 5.5 inch phone.
			when the app didn't have fragment, it works fine. However ,when I use fragment in the mainActivity, it cannot open anymore.
			
solve it by reCreating a app and install it on my android phone again. then it works well. don't know why, may be some typo.
		
		－-> now I'm quite sure that it caused by typo in activity_main.xml!!!	
			
			



20151202

things that I learnt today:

0. How to find the "draw9patch.bat" and how to open it?
	in terminal:$cd android-skds/tools 
				$ls  # can find it here
				$open draw9patch
	Also, there is an oline patcher: http://romannurik.github.io/AndroidAssetStudio/nine-patches.html

20151201

things I learnt:
0. customized icon should store in the res/drawable-hdpi 

1.how to new a class TitleLayout extends LinearLayout.
	add a new class in src/package


20151130

problems I met today:

0. my app quit unexpectly. Actually it happened before. I resoved it by recreating the project again. however, I don't know why it happens and how to solve it properly. the error code in logCat is:[MessageQ] ProcessNewMessage: [XTWiFi-PE] unknown deliver target [OS-Agent] 

	Solve it: i deleted the project again. However I have a better solution now.
				I created a new project. the minimum is level 19 (the same as my android phone) target is 22 and compile with   22. I tested with it and it works.
				
Things that I learnt today:

0. how to add title icon to the project

	I drawed some icons and put them into:workspace/UICustomViews/res/drawable-hdpi

1. how to add customized title into main layout?
	
	in the activity_main.xml, add: <include layout="@layout/title" />




20151129

problems I met today:

0. typo like: layout_toLeftOf I wrote like layout_toLeft
	solve it by: check again and again

What I learnt today:

0. four kinds of layout:
	LinearLayout: harizontal or vertial
	RelativeLayout: 
	FrameLayout: elements are to the left-top position, normally used in the "Fragements"
	TableLayout: elements are in lines and columns, normally used in the login window


20151128

problems I met today:

0. Error parsing XML: unbound prefix

	solve it: It's a typo, like "andorid" should be "android". May be I should have a typo list to check every time when I meet a problem.

progress of today

start to learn UI, like dialog, editText.



20151127

code optimization

0. in FirstActivity class, use code:
	SecondActivity.actionStart(FirstActivity.this, "data1", "data2");
	
	instead of:
	Intent intent = new Intent(FirstActivity.this,SecondActivity.class);
	startActivity(intent);		
1. add a class named activityCollector, which can quite the app without back every activity in the stack :
	
	public class ActivityCollector {
		public static List<Activity> activities = new ArrayList<Activity>();
		public static void addActivity(Activity activity){
			activities.add(activity);
		}
		public static void removeActivity(Activity activity){
			activities.remove(activity);
		}
		public static void finishAll(){
			for(Activity activity : activities){
				if(!activity.isFinishing()){
					activity.finish();
				}
			}
		}
	}
	



20151126

problems I met

0. Activity cannot be solve as an type

solve it: import android.app.Activity;

1. when set an onclick listener, I got a error message:
The method setOnClickListener(View.OnClickListener) in the type View is not applicable for the arguments (new 
	 OnClickListener(){})
	 
solve it by: use View.OnClickListener instead of OnClickListener

OR: import android.view.View.OnClickListener;



What I learned today:

0. CTL+SHIFT+O to fix all imports






20151125

problems I met today

0. in res/layout, i got an error of :
	Element type “Button” must be followed by either attribute specifications, “>” or “/>”
solve it:
	Project --> Clean... then choose your project






What i learned today:
0. getting rid of whitespace within elements

1. if I code like that, it's can be more clear:

	<Button android:id="@+id/btn2"
			android:layout_width="wrap_content"
			android:layout_height="wrap_content"
			android:text="@string/PNR"/>  <!-- just moved your end tag to this line -->


20151121

What's my problem today:

0. why my avd is so slow on my computer
1. how to use android 5.1.1 on my phone...


what I learned with android

0. if you have more than one project in you eclipse, you'd better close the projects that you're not working on by:
	right click->close project
1. how to 

learned about git:
0. I decide to modify all the files on the branch dev
1. if you move to a new local rep, you cannot push it anymore. 
	reason is that the relation between your rep and remote rep is not established
	a way to solve it is that : git branch --set-upstream branch_name origin/branch_name






20151120

Today is the first day that I learn to Android, before that I already read a book about Android but never write a code.
I will record how I learn it by using git hub.
It also will help me to use git hub...
PS, I know how to commit my work to git hub by using command line already :)

what I learned today:

0. Create my first application which named Hello world...
1. Familiar with the interface of Eclipse with Android project.
2. Familiar with the files on the left.
3. About git :
	3.1 how to initialize a git dir: 
		$git init
	3.2 how to delete a git dir.If you failed to initialize it, I mean you made some mistake and what to delete the dir and initialize it again: e.g., in git dir: 
		$rm -fr .git # means delete all files in .get dir
	3.3 push your dir to git hub with the name of mosswaytoAnDroid : 
		$ git remote add origin git@github.com:mossgreen/mosswaytoAnDroid.git
		However, before I push it, I added files to git dir and committed them all by:
			$git add *
			$git commit -m "this is my first line of code"
		I pushed with:
			$ git push -u origin master # I used -u this time to connect my local dir to the remote dir in git hub, I don't have to do this next time.

My questions:

0. the AVD(Android Virtual Devices) is super slow on my mac( mid 2015), I should talk it with sean, who is more advanced than me with Android.