import java.io.Serializable;

/**
 * Abstract class representing a question in a quiz.
 * Implements the Gradable interface for grading functionality.
 */
public abstract class Question implements Gradable, Serializable {
    private static final long serialVersionUID = 1L;

    private String questionText;
    private double points;
    private String correctAnswer;

    /**
     * Default no-arg constructor.
     */
    public Question() {
        this.questionText = "";
        this.points = 0.0;
        this.correctAnswer = "";
    }

    /**
     * Parameterized constructor.
     *
     * @param text the question text
     * @param pts  the points for this question
     * @param ans  the correct answer
     */
    public Question(String text, double pts, String ans) {
        this.questionText = text;
        this.points = pts;
        this.correctAnswer = ans;
    }

    /**
     * Prompts the user through input dialogs to build the question.
     *
     * @return true if the question was fully built, false if the user canceled
     */
    public abstract boolean createQuestion();

    /**
     * Grades the provided answer against the correct answer (case-insensitive).
     *
     * @param answer the user's answer
     * @return true if the answer matches the correct answer (ignoring case), false otherwise
     */
    @Override
    public boolean gradeQuestion(String answer) {
        if (answer == null || correctAnswer == null) {
            return false;
        }
        String cleanAnswer = answer.trim().replaceAll("[^a-zA-Z0-9]", "");
        String cleanCorrect = correctAnswer.trim().replaceAll("[^a-zA-Z0-9]", "");
        return cleanAnswer.equalsIgnoreCase(cleanCorrect);
    }

    /**
     * Returns the correct answer.
     *
     * @return the correct answer string
     */
    @Override
    public String getCorrectAnswer() {
        return correctAnswer;
    }

    // Getters and Setters

    /**
     * Gets the question text.
     *
     * @return the question text
     */
    public String getQuestionText() {
        return questionText;
    }

    /**
     * Sets the question text.
     *
     * @param questionText the question text to set
     */
    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    /**
     * Gets the points for this question.
     *
     * @return the points
     */
    public double getPoints() {
        return points;
    }

    /**
     * Sets the points for this question.
     *
     * @param points the points to set
     */
    public void setPoints(double points) {
        this.points = points;
    }

    /**
     * Sets the correct answer.
     *
     * @param correctAnswer the correct answer to set
     */
    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
}
