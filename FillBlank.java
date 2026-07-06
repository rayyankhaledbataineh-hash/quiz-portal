import javax.swing.JOptionPane;

/**
 * FillBlank class that extends Question.
 * Represents a fill-in-the-blank question where users provide a word to complete a sentence.
 */
public class FillBlank extends Question {
    private String blankWord;

    /**
     * Default constructor.
     */
    public FillBlank() {
        super();
        this.blankWord = "";
    }

    /**
     * Creates a fill-in-the-blank question using input dialogs.
     * Returns false as soon as the user cancels any dialog.
     */
    @Override
    public boolean createQuestion() {
        // Prompt for question text (remind user to use ____ for the blank)
        while (true) {
            String questionText = JOptionPane.showInputDialog(
                "Enter the question text (use ____ for the blank):");
            if (questionText == null) return false; // User closed dialog
            if (!questionText.trim().isEmpty()) {
                setQuestionText(questionText);
                break;
            }
            JOptionPane.showMessageDialog(null, "Question text cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        // Prompt for the blank word (correct answer)
        while (true) {
            String word = JOptionPane.showInputDialog("Enter the word that fills the blank:");
            if (word == null) return false; // User closed dialog
            if (!word.trim().isEmpty()) {
                this.blankWord = word;
                setCorrectAnswer(word);
                break;
            }
            JOptionPane.showMessageDialog(null, "Answer word cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        // Prompt for points with validation
        while (true) {
            String pointsStr = JOptionPane.showInputDialog("Enter the points:");
            if (pointsStr == null) {
                return false; // User closed dialog
            }
            try {
                double points = Double.parseDouble(pointsStr);
                if (points <= 0) {
                    JOptionPane.showMessageDialog(null, "Points must be greater than 0.", "Error", JOptionPane.ERROR_MESSAGE);
                    continue;
                }
                setPoints(points);
                break;
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid number. Please enter a valid decimal or integer.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        return true;
    }

    /**
     * Grades the answer against the blank word (case-insensitive).
     *
     * @param answer the user's answer
     * @return true if the answer matches the blank word (ignoring case), false otherwise
     */
    @Override
    public boolean gradeQuestion(String answer) {
        if (answer == null || blankWord == null) {
            return false;
        }
        String cleanAnswer = answer.trim().replaceAll("[^a-zA-Z0-9]", "");
        String cleanCorrect = blankWord.trim().replaceAll("[^a-zA-Z0-9]", "");
        return cleanCorrect.equalsIgnoreCase(cleanAnswer);
    }

    /**
     * Gets the blank word (correct answer).
     *
     * @return the blank word
     */
    public String getBlankWord() {
        return blankWord;
    }

    /**
     * Sets the blank word (correct answer).
     *
     * @param blankWord the blank word to set
     */
    public void setBlankWord(String blankWord) {
        this.blankWord = blankWord;
    }
}
