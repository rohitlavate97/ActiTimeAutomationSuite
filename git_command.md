create a new repository on the command line
echo "# ActiTimeAutomationSuite" >> README.md
git init
git add README.md
git commit -m "first commit"
git branch -M main
git remote add origin https://github.com/rohitlavate97/ActiTimeAutomationSuite.git
git push -u origin main

(OR)

push an existing repository from the command line
git remote add origin https://github.com/rohitlavate97/ActiTimeAutomationSuite.git
git branch -M main
git push -u origin main

maven commands
--------------
right click on pom.xml --> run as maven clean first time.
now run clean install compile test --->click on maven build and in goals specify this command 'clean install compile test' --->click on run