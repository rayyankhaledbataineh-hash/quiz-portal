# Quiz Portal

A Canvas LMS-style quiz application built in Java Swing. Instructors create and publish quizzes with multiple question types; students take them and get graded instantly. Quizzes and grade history persist to disk between sessions.

Built as a project for my Programming 2 (OOP) course.

## Screenshots

<img width="1470" height="956" alt="Screenshot 2026-07-05 at 11 32 05 PM" src="https://github.com/user-attachments/assets/a115b274-7921-4d6c-9163-a0d83fdf00b9" />

## Features

- **Instructor dashboard** — create quizzes, add questions, review every student submission
- **Three question types** — multiple choice (up to 26 options), true/false, and fill-in-the-blank
- **Student dashboard** — pick any published quiz, take it, and get an instant score with percentage
- **Persistent storage** — quizzes are serialized to `data/*.dat` and grade history to `data/grades.txt`, so everything survives restarts
- **Forgiving grading** — answers are graded case-insensitively and ignore punctuation; true/false accepts `A`/`B`, `T`/`F`, or `true`/`false`
- **Custom UI** — hand-painted rounded buttons, cards, and text fields with dark/light theme switching

## Running

Requires a JDK (11+).

```sh
javac *.java
java Main
```

## Design

The project is organized around a small OOP core:

| Class | Role |
|---|---|
| `Gradable` | Interface defining the grading contract (`gradeQuestion`, `getCorrectAnswer`) |
| `Question` | Abstract base class — text, points, correct answer, and default grading logic |
| `MCQ`, `TrueFalse`, `FillBlank` | Concrete question types, each overriding creation and grading behavior |
| `Quiz` | A titled collection of questions; handles grading and serialization to disk |
| `QuizManager` | Session coordinator — loads saved quizzes and grades at startup, records new results |
| `MainGUI` | Swing interface with custom-rendered components and theming |
| `Main` | Entry point |

Question types are handled polymorphically: the quiz grades every question through the `Gradable` interface without knowing its concrete type, and each subclass supplies its own dialog-driven creation flow.

## Possible future work

- Per-question answer review for instructors
- Radio buttons / dropdowns for MCQ and true/false answers instead of free text
- Editing or deleting previously published quizzes
