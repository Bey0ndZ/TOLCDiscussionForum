TOLCDiscussionForum
===================
1. Install STS (Comes with Maven plugin)
2. Create a folder in your workspace for the project
3. git init
4. git remote add origin <urlofrepo>
5. git pull origin master (Now you have a working copy of the master branch)
6. Install MySQL and MySQL Workbench
7. Copy the SQL script present in src/main/resources/dbscript.sql into your workbench and run the script
8. Change the DB password in spring-database.xml to your password which you set during MySQL installation
9. You can now register and test out modules

TODO (Dt. 11/17/2014)
Sumanth's tasks:
Immediate: 

1. Calendar - The personalEvents thing is not working. Fix it in the DAOImpl layer I guess. TODO. Utmost importance.

2. Create a new firepad for every discussion thread which is active

3. Try getting the username of the person editing the firebug space

4. Innovative features - Follow a person, 2nd degree connections, give stars to people - Following a person is done, with the exception of the button being shown for every log in. Now, in every thread post, see whether the person exists in the follow table and then send the email and update tickr on the person who is following this person. Should work both ways.

5. Spend the day fixing stuff instead of implementing new things.




 