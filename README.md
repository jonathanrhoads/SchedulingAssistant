# Scheduling Assistants 
This application's purpose is to allow the user to create and use customers, manage appointments, and have ease of use for multiple time zones and languages.

Author: Jonathan Rhoads

Version 1.0.0

Date: 2021-09-26

IntelliJ IDEA 2021.1.3 (Ultimate Edition)

Java SE 11.0.12

JavaFX SDK 11.0.2

MySql Connector 8.0.22

## Directions
##### Log in Screen
- From the log in screen enter the correct username and password combination. 
   - Username: test Password: test 
- You have the options to click exit here to quit the program or continue on with the Login button.
- You will receive an alert regarding any upcoming appointments, click ok to continue.

##### Main Menu
- The customers table is displayed on the top. Appointments on the bottom. 
- Each table has three options. Add, modify, delete. To modify or delete a selection must be made first. 
- The appointments table has the option to be filtered by all, week, and month. 
- There are three report options to view. Each view has a back button to return you to main menu.
    - Customer appointments counts by type and month. 
    - Each contact's schedule, ordered by the contact then their appointment's start times.
    - A report showing the total amount of customers and total amount of appointments.
- The log out button will log you out and take you back to the log in screen. 

##### Add/Modify Customers
- The user will provide input in all fields. 
- The country combo box will determine the contents of the division combo box.
- Once the form is complete, clicking Add will save the customer to the database and return the user to the main menu.
- The cancel button will not save the customer and return to the main menu.

##### Add/Modify Customers
- The user will provide input in all fields. 
- The date and time to be plugged in are in the users local time. That time will then convert to EST to ensure that the appointment will fall within normal business hours.
- Once the form is complete, clicking Add will save the appointment to the database and return the user to the main menu.
- The cancel button will not save the appointment and return to the main menu.

