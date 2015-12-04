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