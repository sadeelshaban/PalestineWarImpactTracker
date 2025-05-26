package First;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MainScreen extends JFrame {

    private static final long serialVersionUID = 1L;
    public static ArrayList<RegionData> regionDatabase = new ArrayList<>();

    private JComboBox<String> regionComboBox;
    private JComboBox<String> monthComboBox;
    private JSpinner yearSpinner;

    public MainScreen() {
        super("Palestine War Impact Tracker");

        ImageIcon bgIcon = new ImageIcon(getClass().getResource("background.jpg"));
        JLabel backgroundLabel = new JLabel(bgIcon);
        backgroundLabel.setLayout(new BorderLayout());

        String[] regions = {"Gaza", "West Bank", "Jerusalem"};
        String[] months = {"January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"};

        regionComboBox = new JComboBox<>(regions);
        regionComboBox.setPreferredSize(new Dimension(200, 35));
        regionComboBox.setBackground(new Color(255, 255, 255, 180));  
        regionComboBox.setForeground(Color.BLACK);
        regionComboBox.setFont(new Font("Times New Roman", Font.BOLD, 16));
        regionComboBox.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        regionComboBox.setOpaque(true);

        monthComboBox = new JComboBox<>(months);
        monthComboBox.setFont(new Font("Times New Roman", Font.PLAIN, 16));

        yearSpinner = new JSpinner(new SpinnerNumberModel(2023, 2000, 2100, 1));
        JSpinner.NumberEditor editor = new JSpinner.NumberEditor(yearSpinner, "#"); 
        yearSpinner.setEditor(editor);
        yearSpinner.setFont(new Font("Times New Roman", Font.PLAIN, 16));
 
        JButton addButton = new JButton("Add War Data");
        JButton viewButton = new JButton("View Data");
        JButton addPersonButton = new JButton("Add Person");
        JButton statsButton = new JButton("Impact Summary");
        JButton backButton = new JButton("Back");

        JButton[] buttons = {addButton, viewButton, addPersonButton, statsButton, backButton};
        for (JButton btn : buttons) {
            btn.setFont(new Font("Times New Roman", Font.BOLD, 16));
            btn.setBackground(new Color(70, 70, 70));
            btn.setForeground(Color.WHITE);
            btn.setFocusPainted(false);
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            btn.setMaximumSize(new Dimension(200, 40));
        }

         
        addButton.addActionListener(e -> {
            String region = (String) regionComboBox.getSelectedItem();
            String month = (String) monthComboBox.getSelectedItem();
            int year = (int) yearSpinner.getValue();
            new AddDataScreen(region, month, year);
        });

        viewButton.addActionListener(e -> new ViewDataScreen());
        addPersonButton.addActionListener(e -> new AddPersonScreen());
        statsButton.addActionListener(e -> new MostAffectedAreaScreen());
        backButton.addActionListener(e -> {
            dispose(); 
            new WelcomeScreen();   
        });

        // Panel رئيسي
        JPanel formPanel = new JPanel();
        formPanel.setOpaque(false);
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100));

        JLabel titleLabel = new JLabel("Select Region:");
        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        formPanel.add(titleLabel);
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(regionComboBox);
        formPanel.add(Box.createVerticalStrut(20));

        JLabel monthLabel = new JLabel("Select Month:");
        monthLabel.setFont(new Font("Times New Roman", Font.BOLD, 18));
        monthLabel.setForeground(Color.WHITE);
        monthLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        formPanel.add(monthLabel);
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(monthComboBox);
        formPanel.add(Box.createVerticalStrut(20));

        JLabel yearLabel = new JLabel("Select Year:");
        yearLabel.setFont(new Font("Times New Roman", Font.BOLD, 18));
        yearLabel.setForeground(Color.WHITE);
        yearLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        formPanel.add(yearLabel);
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(yearSpinner);
        formPanel.add(Box.createVerticalStrut(30));

        for (JButton btn : buttons) {
            formPanel.add(btn);
            formPanel.add(Box.createVerticalStrut(10));
        }

        backgroundLabel.add(formPanel, BorderLayout.CENTER);

        setContentPane(backgroundLabel);
        setSize(950, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
