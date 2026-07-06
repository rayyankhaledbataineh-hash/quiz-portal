import javax.swing.JOptionPane;

/**
 * MCQ class that extends Question.
 * Represents a multiple-choice question with configurable answer choices.
 */
public class MCQ extends Question {
    private String[] choices;
    private int numChoices;

    /**
     * Default constructor.
     */
    public MCQ() {
        super();
        this.choices = new String[26]; // Max 26 choices (A-Z)
        this.numChoices = 0;
    }

    /**
     * Creates a multiple-choice question using input dialogs.
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

        // Prompt for number of choices with validation
        while (true) {
            String numChoicesStr = JOptionPane.showInputDialog("Enter the number of choices:");
            if (numChoicesStr == null) {
                return false; // User closed dialog
            }
            try {
                numChoices = Integer.parseInt(numChoicesStr);
                if (numChoices <= 0 || numChoices > 26) {
                    JOptionPane.showMessageDialog(null, "Number of choices must be between 1 and 26.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid number. Please enter a valid integer.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        // Initialize choices array
        choices = new String[numChoices];

        // Prompt for each choice
        for (int i = 0; i < numChoices; i++) {
            char choiceLabel = (char) ('A' + i);
            while (true) {
                String choice = JOptionPane.showInputDialog("Enter choice " + choiceLabel + ":");
                if (choice == null) return false; // User closed dialog
                if (!choice.trim().isEmpty()) {
                    choices[i] = choice;
                    break;
                }
                JOptionPane.showMessageDialog(null, "Choice text cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        // Prompt for correct answer letter with range validation
        char maxLetter = (char) ('A' + numChoices - 1);
        while (true) {
            String correctAnswer = JOptionPane.showInputDialog("Enter the correct answer letter (A - " + maxLetter + "):");
            if (correctAnswer == null) return false; // User closed dialog
            String cleaned = correctAnswer.trim().toUpperCase();
            if (cleaned.length() == 1 && cleaned.charAt(0) >= 'A' && cleaned.charAt(0) <= maxLetter) {
                setCorrectAnswer(cleaned);
                break;
            }
            JOptionPane.showMessageDialog(null, "Please enter a valid letter between A and " + maxLetter + ".", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return true;
    }

    /**
     * Grades the answer against the correct answer (case-insensitive).
     *
     * @param answer the user's answer
     * @return true if the answer matches the correct answer (ignoring case), false otherwise
     */
    @Override
    public boolean gradeQuestion(String answer) {
        if (answer == null || getCorrectAnswer() == null) {
            return false;
        }
        String cleanAnswer = answer.trim().replaceAll("[^a-zA-Z0-9]", "");
        String cleanCorrect = getCorrectAnswer().trim().replaceAll("[^a-zA-Z0-9]", "");
        return cleanCorrect.equalsIgnoreCase(cleanAnswer);
    }

    /**
     * Adds a choice to the choices array.
     *
     * @param choice the choice text to add
     */
    public void addChoice(String choice) {
        if (numChoices >= choices.length) {
            // Grow the array to accommodate the new choice
            String[] newChoices = new String[choices.length + 1];
            System.arraycopy(choices, 0, newChoices, 0, choices.length);
            choices = newChoices;
        }
        choices[numChoices] = choice;
        numChoices++;
    }

    /**
     * Gets the choices array.
     *
     * @return the choices array
     */
    public String[] getChoices() {
        return choices;
    }

    /**
     * Sets the choices array.
     *
     * @param choices the choices array to set
     */
    public void setChoices(String[] choices) {
        this.choices = choices;
    }

    /**
     * Gets the number of choices.
     *
     * @return the number of choices
     */
    public int getNumChoices() {
        return numChoices;
    }

    /**
     * Sets the number of choices.
     *
     * @param numChoices the number of choices to set
     */
    public void setNumChoices(int numChoices) {
        this.numChoices = numChoices;
    }
}
