# Quiz Portal

A Canvas LMS-style quiz application built in Java Swing. Instructors create and publish quizzes with multiple question types; students take them and get graded instantly. Quizzes and grade history persist to disk between sessions.

Built as a project for my Programming 2 (OOP) course.

## Screenshots

<img width="888" height="697" alt="Screenshot 2026-07-05 at 11 49 37 PM" src="https://github.com/user-attachments/assets/346cc94c-df6d-416e-9c5d-3bb92da83f22" />

<img width="895" height="694" alt="Screenshot 2026-07-05 at 11 51 43 PM" src="https://github.com/user-attachments/assets/af875712-efc6-4013-b4fa-c65627d8034b" />

<img width="889" height="685" alt="Screenshot 2026-07-05 at 11 54 55 PM" src="https://github.com/user-attachments/assets/a4a1e2f7-b894-425a-a6e3-d6b199803d19" />

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
