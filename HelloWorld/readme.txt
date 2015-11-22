20151120

Today is the first that I learn to Android, before that I already read a book about Android but never write a code.
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