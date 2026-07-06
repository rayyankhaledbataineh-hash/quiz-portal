import javax.swing.JOptionPane;

/**
 * TrueFalse class that extends Question.
 * Represents a true/false question with options for True and False answers.
 */
public class TrueFalse extends Question {
    private String optionTrue;
    private String optionFalse;

    /**
     * Default constructor.
     */
    public TrueFalse() {
        super();
        this.optionTrue = "True";
        this.optionFalse = "False";
    }

    /**
     * Creates a true/false question using input dialogs.
     * Returns false as soon as the user cancels any dialog.
     */
    @Override
    public boolean createQuestion() {
        // Prompt for question text
        while (true) {
            String questionText = JOptionPane.showInputDialog("Enter the question text:");
            if (questionText == null) return false; // User closed dialog
            if (!questionText.trim().isEmpty()) {
                setQuestionText(questionText);
                break;
            }
            JOptionPane.showMessageDialog(null, "Question text cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
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

        // Set options (automatically True and False)
        this.optionTrue = "True";
        this.optionFalse = "False";

        // Prompt for correct answer with validation
        while (true) {
            String answer = JOptionPane.showInputDialog("Enter the correct answer (A for True, B for False):");
            if (answer == null) return false; // User closed dialog
            String cleaned = answer.trim().toUpperCase();
            if (cleaned.equals("A") || cleaned.equals("B")) {
                setCorrectAnswer(cleaned);
                break;
            }
            JOptionPane.showMessageDialog(null, "Please enter A (True) or B (False).", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return true;
    }

    @Override
    public boolean gradeQuestion(String answer) {
        if (answer == null || getCorrectAnswer() == null) return false;
        String cleanAnswer = answer.trim().toUpperCase().replaceAll("[^A-Z]", "");
        String correct = getCorrectAnswer().trim().toUpperCase().replaceAll("[^A-Z]", "");
        
        // Map common student inputs
        if (cleanAnswer.equals("T") || cleanAnswer.equals("TRUE")) cleanAnswer = "A";
        if (cleanAnswer.equals("F") || cleanAnswer.equals("FALSE")) cleanAnswer = "B";

        // Map teacher inputs if they didn't follow instructions
        if (correct.equals("T") || correct.equals("TRUE")) correct = "A";
        if (correct.equals("F") || correct.equals("FALSE")) correct = "B";

        return cleanAnswer.equals(correct);
    }

    /**
     * Gets the True option.
     *
     * @return the True option string
     */
    public String getOptionTrue() {
        return optionTrue;
    }

    /**
     * Gets the False option.
     *
     * @return the False option string
     */
    public String getOptionFalse() {
        return optionFalse;
    }

    /**
     * Sets the True option.
     *
     * @param optionTrue the True option string to set
     */
    public void setOptionTrue(String optionTrue) {
        this.optionTrue = optionTrue;
    }

    /**
     * Sets the False option.
     *
     * @param optionFalse the False option string to set
     */
    public void setOptionFalse(String optionFalse) {
        this.optionFalse = optionFalse;
    }
}
