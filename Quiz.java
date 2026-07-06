import java.io.*;
import java.util.ArrayList;

/**
 * Quiz class that manages a collection of questions and handles
 * grading and persistence to disk.
 */
public class Quiz implements Serializable {
    private static final long serialVersionUID = 1L;

    /** Directory where quizzes and grade history are stored. */
    public static final String DATA_DIR = "data";

    private String title;
    private ArrayList<Question> questions;
    private double score;
    private ArrayList<String> userAnswers;

    /**
     * Default constructor initializing the ArrayList and score.
     */
    public Quiz() {
        this.questions = new ArrayList<>();
        this.userAnswers = new ArrayList<>();
        this.score = 0.0;
        this.title = "";
    }

    /**
     * Adds a question to the quiz.
     *
     * @param q the Question to add
     */
    public void addQuestion(Question q) {
        questions.add(q);
    }

    /**
     * Grades the quiz by comparing user answers to correct answers.
     * Calls gradeQuestion() on each question and sums the points.
     *
     * @return the total score for the quiz
     */
    public double gradeQuiz() {
        score = 0.0;

        if (userAnswers.size() != questions.size()) {
            return 0.0;
        }

        for (int i = 0; i < questions.size(); i++) {
            if (questions.get(i).gradeQuestion(userAnswers.get(i))) {
                score += questions.get(i).getPoints();
            }
        }

        return score;
    }

    /**
     * Sums the points of all questions in the quiz.
     *
     * @return the maximum possible score
     */
    public double getTotalPoints() {
        double total = 0.0;
        for (Question q : questions) {
            total += q.getPoints();
        }
        return total;
    }

    /**
     * Saves the quiz to a .dat file in the data directory using Java
     * serialization. The filename is derived from the quiz title.
     *
     * @return true if the quiz was saved successfully, false otherwise
     */
    public boolean saveQuiz() {
        File dir = new File(DATA_DIR);
        if (!dir.exists() && !dir.mkdirs()) {
            return false;
        }

        File file = new File(dir, fileNameForTitle(title));
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(this);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * Loads a quiz from a .dat file using Java deserialization.
     *
     * @param file the file to load the quiz from
     * @return the deserialized Quiz object, or null if an error occurs
     */
    public static Quiz loadQuiz(File file) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (Quiz) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }

    /**
     * Loads every saved quiz from the data directory.
     *
     * @return the list of quizzes found on disk (empty if none)
     */
    public static ArrayList<Quiz> loadAllQuizzes() {
        ArrayList<Quiz> quizzes = new ArrayList<>();
        File[] files = new File(DATA_DIR).listFiles((dir, name) -> name.endsWith(".dat"));
        if (files == null) {
            return quizzes;
        }
        for (File file : files) {
            Quiz quiz = loadQuiz(file);
            if (quiz != null) {
                quizzes.add(quiz);
            }
        }
        return quizzes;
    }

    /**
     * Converts a quiz title into a safe .dat filename.
     *
     * @param title the quiz title
     * @return the sanitized filename
     */
    private static String fileNameForTitle(String title) {
        String base = title.trim().replaceAll("[^a-zA-Z0-9-_ ]", "").replaceAll("\\s+", "_");
        if (base.isEmpty()) {
            base = "quiz";
        }
        return base + ".dat";
    }

    // Getters and Setters

    /**
     * Gets the quiz title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the quiz title.
     *
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the list of questions.
     *
     * @return the ArrayList of questions
     */
    public ArrayList<Question> getQuestions() {
        return questions;
    }

    /**
     * Sets the list of questions.
     *
     * @param questions the ArrayList of questions to set
     */
    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }

    /**
     * Gets the current score.
     *
     * @return the score
     */
    public double getScore() {
        return score;
    }

    /**
     * Sets the score.
     *
     * @param score the score to set
     */
    public void setScore(double score) {
        this.score = score;
    }

    /**
     * Gets the user answers collected during the quiz.
     *
     * @return the ArrayList of user answers
     */
    public ArrayList<String> getUserAnswers() {
        return userAnswers;
    }

    /**
     * Sets the user answers.
     *
     * @param userAnswers the ArrayList of user answers to set
     */
    public void setUserAnswers(ArrayList<String> userAnswers) {
        this.userAnswers = userAnswers;
    }
}
