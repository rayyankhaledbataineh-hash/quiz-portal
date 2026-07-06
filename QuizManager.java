import javax.swing.JOptionPane;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

/**
 * QuizManager coordinates quizzes and grade history for the GUI.
 * Loads saved quizzes and past grades from disk at startup, tracks the
 * quiz currently being edited or taken, and persists new grade records.
 */
public class QuizManager {
    private static final String GRADES_FILE = Quiz.DATA_DIR + File.separator + "grades.txt";

    private Quiz currentQuiz;
    private final ArrayList<Quiz> savedQuizzes;
    private final ArrayList<String> pastGrades;

    /**
     * Creates a manager with all quizzes and grades previously saved to disk.
     */
    public QuizManager() {
        this.currentQuiz = null;
        this.savedQuizzes = Quiz.loadAllQuizzes();
        this.pastGrades = loadGrades();
    }

    /**
     * Allows a student to select a quiz from the saved quizzes via a list dialog.
     * Sets the selected quiz as currentQuiz.
     *
     * @return true if a quiz was selected, false otherwise
     */
    public boolean selectQuiz() {
        if (savedQuizzes.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No quizzes available.", "No Quizzes", JOptionPane.INFORMATION_MESSAGE);
            return false;
        }

        String[] quizTitles = new String[savedQuizzes.size()];
        for (int i = 0; i < savedQuizzes.size(); i++) {
            quizTitles[i] = savedQuizzes.get(i).getTitle();
        }

        String selected = (String) JOptionPane.showInputDialog(null,
            "Select a quiz:",
            "Available Quizzes",
            JOptionPane.PLAIN_MESSAGE,
            null,
            quizTitles,
            quizTitles[0]);

        if (selected != null) {
            for (Quiz quiz : savedQuizzes) {
                if (quiz.getTitle().equals(selected)) {
                    currentQuiz = quiz;
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Prompts for a quiz title, then creates a new quiz and sets it as
     * currentQuiz. Rejects empty and duplicate titles.
     */
    public void createQuiz() {
        String title;
        while (true) {
            title = JOptionPane.showInputDialog("Enter quiz title:");
            if (title == null) return; // User closed dialog
            title = title.trim();
            if (title.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Quiz title cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (findQuizByTitle(title) != null) {
                JOptionPane.showMessageDialog(null, "A quiz with that title already exists.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                break;
            }
        }

        currentQuiz = new Quiz();
        currentQuiz.setTitle(title);
        savedQuizzes.add(currentQuiz);
        JOptionPane.showMessageDialog(null,
            "Quiz '" + title + "' created successfully.",
            "Quiz Created",
            JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Adds a True/False question to the current quiz.
     */
    public void addTrueFalseQuestion() {
        if (currentQuiz == null) {
            JOptionPane.showMessageDialog(null, "Please create a quiz first.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        TrueFalse question = new TrueFalse();
        if (question.createQuestion()) {
            currentQuiz.addQuestion(question);
            JOptionPane.showMessageDialog(null,
                "True/False question added successfully.",
                "Question Added",
                JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Adds a Fill in the Blank question to the current quiz.
     */
    public void addFillBlankQuestion() {
        if (currentQuiz == null) {
            JOptionPane.showMessageDialog(null, "Please create a quiz first.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        FillBlank question = new FillBlank();
        if (question.createQuestion()) {
            currentQuiz.addQuestion(question);
            JOptionPane.showMessageDialog(null,
                "Fill in the Blank question added successfully.",
                "Question Added",
                JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Adds a Multiple Choice question to the current quiz.
     */
    public void addMCQQuestion() {
        if (currentQuiz == null) {
            JOptionPane.showMessageDialog(null, "Please create a quiz first.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        MCQ question = new MCQ();
        if (question.createQuestion()) {
            currentQuiz.addQuestion(question);
            JOptionPane.showMessageDialog(null,
                "Multiple Choice question added successfully.",
                "Question Added",
                JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Saves the current quiz to disk and reports the result in a dialog.
     */
    public void saveQuizToFile() {
        if (currentQuiz == null) {
            return;
        }
        if (currentQuiz.saveQuiz()) {
            JOptionPane.showMessageDialog(null,
                "Quiz saved successfully.",
                "Quiz Saved",
                JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null,
                "Failed to save quiz to disk.",
                "Save Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Records a grade result, adding it to the in-memory history and
     * appending it to the grades file so it survives restarts.
     *
     * @param record the formatted grade line to record
     */
    public void recordGrade(String record) {
        pastGrades.add(record);
        try {
            File dir = new File(Quiz.DATA_DIR);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            Files.write(Paths.get(GRADES_FILE),
                (record + System.lineSeparator()).getBytes(StandardCharsets.UTF_8),
                StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            // Grade stays in the in-memory history for this session
        }
    }

    /**
     * Reads the grade history file into a list, one record per line.
     *
     * @return the grade records found on disk (empty if none)
     */
    private static ArrayList<String> loadGrades() {
        ArrayList<String> grades = new ArrayList<>();
        File file = new File(GRADES_FILE);
        if (!file.exists()) {
            return grades;
        }
        try {
            grades.addAll(Files.readAllLines(file.toPath(), StandardCharsets.UTF_8));
        } catch (IOException e) {
            // Start with an empty history if the file can't be read
        }
        return grades;
    }

    /**
     * Finds a saved quiz by its title.
     *
     * @param title the title to search for
     * @return the matching Quiz, or null if none exists
     */
    private Quiz findQuizByTitle(String title) {
        for (Quiz quiz : savedQuizzes) {
            if (quiz.getTitle().equalsIgnoreCase(title)) {
                return quiz;
            }
        }
        return null;
    }

    /**
     * Gets the current quiz.
     *
     * @return the current Quiz object, or null if none is loaded
     */
    public Quiz getCurrentQuiz() {
        return currentQuiz;
    }

    /**
     * Gets the grade history, oldest first.
     *
     * @return the ArrayList of grade records
     */
    public ArrayList<String> getPastGrades() {
        return pastGrades;
    }
}
