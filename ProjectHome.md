This repository has sample code examples of a number of J2EE technologies. Please TortoiseSVN tool for subversion. Here are some faqs:

1. How to relocate your project to a new repository?

Assuming you have files in your local working folder that has been previously checked-out from a previous repository. Since you created a new repository, I guess you have to import your working copy into the new repository.

Using TortoiseSVN, you can export your working copy to a different folder (so that you only have your source files, without the .svn folders). Right click the local folder and choose TortoiseSVN .. Export. It will export all the contents of this folder to a new location on your machine without svn folder. Then you can import that folder into /trunk of your new repository. Right click the new folder and choose TortoiseSVN .. Import to import the folder into the new repository.