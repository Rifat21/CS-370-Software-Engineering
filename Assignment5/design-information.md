# Design Information

1. A list consisting of reminders the users want to be aware of. The application must allow users to add reminders to a list, delete reminders from a list, and edit the reminders in the list. 
-This is achieved by creating a list of Reminders, where Reminders is an entity in itself. This list is titled in the UML diagram as ReminderList. ReminderList has methods to add, delete and edit reminders. 

2. The application must contain a databse (DB) of reminders and corresponding data. 
-This is shown in the UML diagram by representing database and what data will be stored and in which tables. 

3. Users must be able to add reminder to a list by picking them from a hierachical list, where the first level is the reminder type(e.g. Appointment), and the second level is the name of the actual reminder (e.g., Dentist Appointment).
-In ReminderList, one of the Attributes of the list is the ReminderListType where we can just have lists of Reminders that are only Appointments. The Reminder class also has a "type" and "name" attribute for this purpose. 

4. Users must also be able to specify a reminder by typing it's name. In this case, the application must look in its DB for reminders with similar names and ask the user whether that is the item they intended to add. If a match (or nearby match) cannot be found, the application must ask the user to select a reminder type for the reminder or add a new one, and then save the new reminder, together with its type in the DB.
-To realize this requirement I have added the tables that may be used in the databases. When the user starts typing the application can search the database if such strings/reminders already exist, if so then it can suggest the reminders and save it thereafter. 

5. The reminders must be saved automatically and immediately after they are modified. 
-ReminderList has a method saveReminderList which is part of all of the methods in the list and it saves the list automatically as it is called after every method. 

6. Users must be able to check off reminders in the list (without deleting them).
-To realize this requirement I have added a method called check, which would represent a checkbox and the user can checkmark the box to show that the reminder has been recognized. This does not delete the reminder. 

7. Users must also be able to clear all the check-off marks in the reminder list at once. 
- The method uncheckAll in the ReminderList allows the user to uncheck all the reminders in the lits. 

8. Check-off makrks for the reminder list are persistent and must also be saved immediately. 
-SaveReminderList gets called after the invocation of each method in the ReminderList. Therefore, when its is checked or unchecked the status get's saved in the list. 

9. The application must present the reminders grouped by type. 
-To realize this requirement I have added ReminderListType as an attribute to ReminderList. Now it is possible to sort the list using the ReminderListType. 

10. The application must support multiple reminder lists at a time (e.g., “Weekly”, “Monthly”, “Kid’s Reminders”). Therefore, the application must provide the users with the ability to
create, (re)name, select, and delete reminder lists.
-This is done by creating a new class called ListManager. ListManager is a class of List of Reminders and has methods to create lists, rename lists, select lists, and delete lists. 

11. The application should have the option to set up reminder with day and time alert. If this option is slected allow option to repeat the behavior. 
-Alerts was added in the UML diagram to accomplish this requirement. Each reminder has an Alert which has a date and time attribute which can be set to alert on that time and day. It also has a repeatable boolean which recognizes if the alert is repeated or not. 

12. Extra Credit: Option to set up reminder based on location.

13. The User Interface (UI) must be intuitive and responsive. 
-Not considered because it does not affect the design directly. 
