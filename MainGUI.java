import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

/**
 * MainGUI class that provides a Swing-based graphical user interface for the Grading System.
 * Features dynamic themes and modern rounded styling.
 */
public class MainGUI {
    private JFrame frame;
    private JPanel panel;
    private QuizManager quizManager;

    private boolean isDarkMode = true;
    private Color bgColor;
    private Color cardColor;
    private Color textColor;
    private Color primaryBtnColor;
    private Color secondaryBtnColor;
    private Color cardBorderColor;
    private Color tfBgColor;
    private Color tfTextColor;

    private static final Font TITLE_FONT = new Font("Helvetica", Font.BOLD, 32);
    private static final Font HEADER_FONT = new Font("Helvetica", Font.BOLD, 20);
    private static final Font NORMAL_FONT = new Font("Helvetica", Font.PLAIN, 15);

    public MainGUI() {
        this.quizManager = new QuizManager();
        updateThemeColors();
    }

    private void updateThemeColors() {
        if (isDarkMode) {
            bgColor = new Color(18, 18, 18);
            cardColor = new Color(30, 30, 30);
            textColor = new Color(240, 240, 240);
            primaryBtnColor = new Color(99, 102, 241); // Indigo / Violet vibrant
            secondaryBtnColor = new Color(63, 63, 70); // Zinc 700
            cardBorderColor = new Color(45, 45, 45);
            tfBgColor = new Color(40, 40, 40);
            tfTextColor = new Color(240, 240, 240);
        } else {
            bgColor = new Color(248, 250, 252); // Slate 50
            cardColor = new Color(255, 255, 255);
            textColor = new Color(15, 23, 42); // Slate 900
            primaryBtnColor = new Color(79, 70, 229); // Indigo 600
            secondaryBtnColor = new Color(148, 163, 184); // Slate 400
            cardBorderColor = new Color(226, 232, 240); // Slate 200
            tfBgColor = new Color(255, 255, 255);
            tfTextColor = new Color(15, 23, 42);
        }
        if (frame != null) {
            frame.getContentPane().setBackground(bgColor);
            if (panel != null) panel.setBackground(bgColor);
        }
    }

    public void launchGUI() {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            // Ignore
        }
        frame = new JFrame("Quiz Portal");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 700);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(bgColor);
        frame.setVisible(true);

        showMainMenu();
    }

    private String formatDouble(double d) {
        if (d == (long) d)
            return String.format("%d", (long) d);
        else
            return String.format("%s", d);
    }

    // Custom Rounded Button Factory
    private JButton createRoundedButton(String text) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (getModel().isPressed()) {
                    g2.setColor(getBackground().darker());
                } else if (getModel().isRollover()) {
                    g2.setColor(getBackground().brighter());
                } else {
                    g2.setColor(getBackground());
                }
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                super.paintComponent(g);
                g2.dispose();
            }
        };
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(false);
        return btn;
    }

    // Custom Rounded Panel Factory
    private JPanel createRoundedPanel() {
        JPanel pnl = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                g2.dispose();
            }
            @Override
            protected void paintBorder(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(cardBorderColor);
                g2.setStroke(new BasicStroke(1.2f));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);
                g2.dispose();
            }
        };
        pnl.setOpaque(false);
        return pnl;
    }

    // Custom Rounded Text Field Factory
    private JTextField createRoundedTextField(int columns) {
        JTextField tf = new JTextField(columns) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 16, 16);
                super.paintComponent(g);
                g2.dispose();
            }
            @Override
            protected void paintBorder(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(cardBorderColor);
                g2.setStroke(new BasicStroke(1.2f));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 16, 16);
                g2.dispose();
            }
        };
        tf.setOpaque(false);
        return tf;
    }

    private void styleButton(JButton btn) {
        btn.setBackground(primaryBtnColor);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Helvetica", Font.BOLD, 15));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createEmptyBorder(12, 24, 12, 24));
    }

    private void styleLabel(JLabel lbl, Font font) {
        lbl.setForeground(textColor);
        lbl.setFont(font);
    }

    private void clearAndRefresh() {
        panel = new JPanel();
        panel.setBackground(bgColor);
    }

    private void showMainMenu() {
        clearAndRefresh();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Quiz Portal", JLabel.CENTER);
        styleLabel(titleLabel, TITLE_FONT);
        panel.add(titleLabel, gbc);

        JButton teacherButton = createRoundedButton("Teacher Dashboard");
        styleButton(teacherButton);
        teacherButton.addActionListener(e -> showTeacherPanel());
        panel.add(teacherButton, gbc);

        JButton studentButton = createRoundedButton("Student Dashboard");
        styleButton(studentButton);
        studentButton.addActionListener(e -> showStudentPanel());
        panel.add(studentButton, gbc);

        // Theme Toggle
        JButton themeButton = createRoundedButton(isDarkMode ? "Switch to Light Mode" : "Switch to Dark Mode");
        styleButton(themeButton);
        themeButton.setBackground(secondaryBtnColor);
        themeButton.addActionListener(e -> {
            isDarkMode = !isDarkMode;
            updateThemeColors();
            showMainMenu(); // refresh
        });
        panel.add(themeButton, gbc);

        JButton exitButton = createRoundedButton("Exit Portal");
        styleButton(exitButton);
        exitButton.setBackground(new Color(220, 38, 38)); // Red-600
        exitButton.addActionListener(e -> System.exit(0));
        panel.add(exitButton, gbc);

        frame.setContentPane(panel);
        frame.revalidate();
        frame.repaint();
    }

    private void showTeacherPanel() {
        clearAndRefresh();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Instructor Dashboard", JLabel.CENTER);
        styleLabel(titleLabel, TITLE_FONT);
        panel.add(titleLabel, gbc);

        JButton createQuizButton = createRoundedButton("Create a New Quiz");
        styleButton(createQuizButton);
        createQuizButton.addActionListener(e -> {
            Quiz oldQuiz = quizManager.getCurrentQuiz();
            quizManager.createQuiz();
            Quiz newQuiz = quizManager.getCurrentQuiz();
            if (newQuiz != null && newQuiz != oldQuiz) {
                showQuizCreationStep();
            }
        });
        panel.add(createQuizButton, gbc);

        JButton viewResponsesButton = createRoundedButton("View Student Responses");
        styleButton(viewResponsesButton);
        viewResponsesButton.addActionListener(e -> {
            ArrayList<String> grades = quizManager.getPastGrades();
            if (grades.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "No student responses recorded yet.", "Student Responses", JOptionPane.INFORMATION_MESSAGE);
            } else {
                StringBuilder sb = new StringBuilder("Student Responses (Newest to Oldest):\n\n");
                for (int i = grades.size() - 1; i >= 0; i--) {
                    sb.append("• ").append(grades.get(i)).append("\n\n");
                }
                JOptionPane.showMessageDialog(frame, sb.toString(), "Student Responses", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        panel.add(viewResponsesButton, gbc);

        JButton backButton = createRoundedButton("Back to Home");
        styleButton(backButton);
        backButton.setBackground(secondaryBtnColor);
        backButton.addActionListener(e -> showMainMenu());
        panel.add(backButton, gbc);

        frame.setContentPane(panel);
        frame.revalidate();
        frame.repaint();
    }

    private void showQuizCreationStep() {
        clearAndRefresh();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 15, 10, 15);
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Quiz currentQuiz = quizManager.getCurrentQuiz();
        JLabel titleLabel = new JLabel("Editing: " + (currentQuiz != null ? currentQuiz.getTitle() : ""), JLabel.CENTER);
        styleLabel(titleLabel, TITLE_FONT);
        panel.add(titleLabel, gbc);

        int questionCount = currentQuiz != null ? currentQuiz.getQuestions().size() : 0;
        JLabel countLabel = new JLabel(questionCount + (questionCount == 1 ? " question" : " questions") + " so far", JLabel.CENTER);
        styleLabel(countLabel, NORMAL_FONT);
        panel.add(countLabel, gbc);

        JButton addQuestionButton = createRoundedButton("+ Add Question");
        styleButton(addQuestionButton);
        addQuestionButton.addActionListener(e -> showAddQuestionPage());
        panel.add(addQuestionButton, gbc);

        JButton doneButton = createRoundedButton("Save & Publish Quiz");
        styleButton(doneButton);
        doneButton.setBackground(new Color(16, 185, 129)); // Emerald-500
        doneButton.addActionListener(e -> {
            Quiz current = quizManager.getCurrentQuiz();
            if (current != null && current.getQuestions().isEmpty()) {
                int confirm = JOptionPane.showConfirmDialog(frame, 
                    "This quiz has no questions! Are you sure you want to publish it?", 
                    "Empty Quiz Warning", 
                    JOptionPane.YES_NO_OPTION, 
                    JOptionPane.WARNING_MESSAGE);
                if (confirm != JOptionPane.YES_OPTION) {
                    return; // Return to editing
                }
            }
            quizManager.saveQuizToFile();
            showMainMenu();
        });
        panel.add(doneButton, gbc);

        frame.setContentPane(panel);
        frame.revalidate();
        frame.repaint();
    }

    private void showAddQuestionPage() {
        clearAndRefresh();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 15, 10, 15);
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Select Question Type", JLabel.CENTER);
        styleLabel(titleLabel, TITLE_FONT);
        panel.add(titleLabel, gbc);

        JButton tfButton = createRoundedButton("True / False");
        styleButton(tfButton);
        tfButton.addActionListener(e -> {
            quizManager.addTrueFalseQuestion();
            showQuizCreationStep();
        });
        panel.add(tfButton, gbc);

        JButton fbButton = createRoundedButton("Fill in the Blank");
        styleButton(fbButton);
        fbButton.addActionListener(e -> {
            quizManager.addFillBlankQuestion();
            showQuizCreationStep();
        });
        panel.add(fbButton, gbc);

        JButton mcqButton = createRoundedButton("Multiple Choice");
        styleButton(mcqButton);
        mcqButton.addActionListener(e -> {
            quizManager.addMCQQuestion();
            showQuizCreationStep();
        });
        panel.add(mcqButton, gbc);

        JButton cancelButton = createRoundedButton("Cancel");
        styleButton(cancelButton);
        cancelButton.setBackground(secondaryBtnColor);
        cancelButton.addActionListener(e -> showQuizCreationStep());
        panel.add(cancelButton, gbc);

        frame.setContentPane(panel);
        frame.revalidate();
        frame.repaint();
    }

    private void showStudentPanel() {
        clearAndRefresh();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Student Dashboard", JLabel.CENTER);
        styleLabel(titleLabel, TITLE_FONT);
        panel.add(titleLabel, gbc);

        JButton selectQuizButton = createRoundedButton("Available Quizzes");
        styleButton(selectQuizButton);
        selectQuizButton.addActionListener(e -> {
            if (quizManager.selectQuiz()) {
                String studentName;
                while (true) {
                    studentName = JOptionPane.showInputDialog(frame, "Please enter your name:");
                    if (studentName == null) return; // Student canceled
                    if (!studentName.trim().isEmpty()) {
                        break;
                    }
                    JOptionPane.showMessageDialog(frame, "Name cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
                }
                showQuizScreen(studentName);
            }
        });
        panel.add(selectQuizButton, gbc);

        JButton viewScoreButton = createRoundedButton("View My Grades");
        styleButton(viewScoreButton);
        viewScoreButton.addActionListener(e -> {
            ArrayList<String> grades = quizManager.getPastGrades();
            if (grades.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "No grades available. Take a quiz first.", "Grades", JOptionPane.INFORMATION_MESSAGE);
            } else {
                StringBuilder sb = new StringBuilder("Past Grades:\n\n");
                for (int i = grades.size() - 1; i >= 0; i--) {
                    sb.append(grades.get(i)).append("\n");
                }
                JOptionPane.showMessageDialog(frame, sb.toString(), "Grades", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        panel.add(viewScoreButton, gbc);

        JButton backButton = createRoundedButton("Back to Home");
        styleButton(backButton);
        backButton.setBackground(secondaryBtnColor);
        backButton.addActionListener(e -> showMainMenu());
        panel.add(backButton, gbc);

        frame.setContentPane(panel);
        frame.revalidate();
        frame.repaint();
    }

    private void showQuizScreen(String studentName) {
        Quiz currentQuiz = quizManager.getCurrentQuiz();
        if (currentQuiz == null) {
            JOptionPane.showMessageDialog(frame, "No quiz selected.", "Error", JOptionPane.ERROR_MESSAGE);
            showStudentPanel();
            return;
        }

        clearAndRefresh();
        panel.setLayout(new BorderLayout());
        
        // Header
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        headerPanel.setBackground(bgColor);
        headerPanel.setBorder(new EmptyBorder(20, 30, 10, 30));
        JLabel titleLabel = new JLabel(currentQuiz.getTitle());
        styleLabel(titleLabel, TITLE_FONT);
        headerPanel.add(titleLabel);
        panel.add(headerPanel, BorderLayout.NORTH);

        // Questions Area
        JPanel questionsPanel = new JPanel();
        questionsPanel.setLayout(new BoxLayout(questionsPanel, BoxLayout.Y_AXIS));
        questionsPanel.setBackground(bgColor);
        questionsPanel.setBorder(new EmptyBorder(10, 30, 20, 30));

        ArrayList<Question> questions = currentQuiz.getQuestions();
        ArrayList<JTextField> answerFields = new ArrayList<>();

        for (int i = 0; i < questions.size(); i++) {
            Question question = questions.get(i);
            
            // Question Card (Rounded Panel)
            JPanel cardPanel = createRoundedPanel();
            cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
            cardPanel.setBackground(cardColor);
            cardPanel.setBorder(new EmptyBorder(20, 25, 20, 25)); // Inner padding

            JLabel qTitle = new JLabel("Question " + (i + 1) + " (" + formatDouble(question.getPoints()) + " pts)");
            styleLabel(qTitle, HEADER_FONT);
            qTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
            cardPanel.add(qTitle);
            cardPanel.add(Box.createVerticalStrut(10));

            JLabel qLabel = new JLabel("<html><body><p style='width: 500px;'>" + question.getQuestionText() + "</p></body></html>");
            styleLabel(qLabel, NORMAL_FONT);
            qLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            cardPanel.add(qLabel);
            cardPanel.add(Box.createVerticalStrut(15));

            if (question instanceof TrueFalse) {
                JLabel opt = new JLabel("A) True    B) False");
                styleLabel(opt, NORMAL_FONT);
                opt.setAlignmentX(Component.LEFT_ALIGNMENT);
                cardPanel.add(opt);
            } else if (question instanceof MCQ) {
                MCQ mcq = (MCQ) question;
                for (int j = 0; j < mcq.getNumChoices(); j++) {
                    char label = (char) ('A' + j);
                    JLabel opt = new JLabel(label + ") " + mcq.getChoices()[j]);
                    styleLabel(opt, NORMAL_FONT);
                    opt.setAlignmentX(Component.LEFT_ALIGNMENT);
                    cardPanel.add(opt);
                }
            }

            cardPanel.add(Box.createVerticalStrut(20));
            JPanel answerContainer = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
            answerContainer.setBackground(cardColor);
            answerContainer.setAlignmentX(Component.LEFT_ALIGNMENT);

            JLabel ansLabel = new JLabel("Your Answer:  ");
            styleLabel(ansLabel, NORMAL_FONT);
            ansLabel.setFont(new Font("Helvetica", Font.BOLD, 15));
            answerContainer.add(ansLabel);

            JTextField answerField = createRoundedTextField(20);
            answerField.setFont(NORMAL_FONT);
            answerField.setBackground(tfBgColor);
            answerField.setForeground(tfTextColor);
            answerField.setCaretColor(textColor);
            answerField.setBorder(new EmptyBorder(8, 12, 8, 12));
            answerFields.add(answerField);
            answerContainer.add(answerField);

            cardPanel.add(answerContainer);

            questionsPanel.add(cardPanel);
            questionsPanel.add(Box.createVerticalStrut(25));
        }

        // Footer buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(bgColor);
        buttonPanel.setBorder(new EmptyBorder(10, 30, 30, 30));

        JButton cancelButton = createRoundedButton("Cancel");
        styleButton(cancelButton);
        cancelButton.setBackground(secondaryBtnColor);
        cancelButton.addActionListener(e -> showStudentPanel());
        buttonPanel.add(cancelButton);

        JButton submitButton = createRoundedButton("Submit Quiz");
        styleButton(submitButton);
        submitButton.addActionListener(e -> {
            Quiz quiz = quizManager.getCurrentQuiz();
            if (quiz != null) {
                ArrayList<String> userAnswers = new ArrayList<>();
                for (JTextField field : answerFields) {
                    userAnswers.add(field.getText().trim());
                }
                quiz.setUserAnswers(userAnswers);

                double score = quiz.gradeQuiz();
                double totalPoints = quiz.getTotalPoints();
                String percentageStr = "0%";
                if (totalPoints > 0) {
                    double percentage = (score / totalPoints) * 100;
                    percentageStr = String.format("%.0f%%", percentage);
                }

                String resultStr = String.format("Student: %s | Quiz: %s - Score: %s / %s (%s)", studentName, quiz.getTitle(), formatDouble(score), formatDouble(totalPoints), percentageStr);
                quizManager.recordGrade(resultStr);

                JOptionPane.showMessageDialog(frame, 
                    "Quiz Complete!\nScore: " + formatDouble(score) + " / " + formatDouble(totalPoints) + " (" + percentageStr + ")", 
                    "Submission Successful", 
                    JOptionPane.INFORMATION_MESSAGE);
            }
            showStudentPanel();
        });
        buttonPanel.add(submitButton);

        JScrollPane scrollPane = new JScrollPane(questionsPanel, 
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(null); // Remove default scrollpane border
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); // smoother scrolling
        scrollPane.setBackground(bgColor);
        scrollPane.getViewport().setBackground(bgColor);
        
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        frame.setContentPane(panel);
        frame.revalidate();
        frame.repaint();
    }
}