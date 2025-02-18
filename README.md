# Quiz Project

This project is a Java-based quiz management system that allows users to create, store, and browse quiz questions. The application features a graphical user interface (GUI) for user interactions and uses file-based serialization for data persistence.

## Features

- **Create Quiz Questions:**  
  Create new questions with multiple answer options and assign points to each answer.
  
- **Browse & Filter Questions:**  
  View all stored questions, search by keywords, and filter by question type (e.g., single choice vs. multiple choice).
  
- **Quiz Generation:**  
  Select multiple questions to generate a quiz file (e.g., a text file with all selected questions).

## Project Structure

- **`gui` Package:**  
  Contains all graphical user interface classes.
  - `QuizmasterApp.java`: The main entry point of the application.
  - `QuizApp.java`: Provides a form for creating and saving new quiz questions.
  - `FrageListGUI.java`: Displays a list of quiz questions with search and filter options.

- **`data` Package:**  
  Contains classes for data persistence and management.
  - `FrageSerializer.java`: Handles serialization and deserialization of quiz questions.
  - `DataManager.java` (if applicable): Manages data access and provides additional utility methods.

- **`data/entities` Package:**  
  Contains the core entity classes.
  - `Frage.java`: Represents a quiz question.
  - `Thema.java`: Represents a topic.
  - `Kategorie.java`: Represents a category.

- **`gui/entities` Package:**  
  Contains GUI-specific helper classes (e.g., custom button implementations).

  ## Data Persistence

Quiz questions are saved to a file (e.g., `fragen.ser`) using Java's serialization mechanism. This allows you to persist data between sessions without relying on a database.
