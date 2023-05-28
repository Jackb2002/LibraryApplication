# **Library Application**

## **Purpose**

The Library Application is designed to manage users, items, and loaning activities in a library setting. It provides functionality for managing books, audiobooks, braille books, and films. The application allows users to perform tasks such as loaning and returning items, generating reports, managing users, and maintaining detailed information about each item.

## **Prerequisites**

To run the Library Application, ensure that the following prerequisites are met:
- Java OpenJDK 19.0.2 is installed on your system.
- The application is built using Gradle.
- JavaFX 
- SQLite, requiring the JDBC 3.41.2.1 connector.

## **Installation Steps**

To install the Library Application, follow these steps:
1. Download the application and the premade database file.
2. Place both files in a desired directory on your system.
3. If necessary, you can modify the premade database file by replacing it with your own information.

## **Configuration Requirements**

Before using the Library Application, make sure to perform the following configuration steps:
1. Remove the items from the premade database.
2. Populate the database with your own stock of items and user information.

## **Additional Details**

Here are some additional details to be aware of when using the Library Application:
- When printing reports or generating files, a text file will be dropped in the same directory as the program. Ensure that all users have read access to that directory.
- It's important to note that changes to items and inventory lists will not be immediately reflected in the database. Instead, they will be stored in memory until the program is shut down correctly. The database will be updated with these changes during the program's closing process.

## **Class Hierarchy**

The Library Application follows a class hierarchy, where the Item class applies to all items in the library, and other classes inherit from it to add more specific details.
