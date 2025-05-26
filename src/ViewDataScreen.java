package First;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ViewDataScreen extends JFrame {
    private static final long serialVersionUID = 1L;

    private JComboBox<String> regionComboBox;
    private JComboBox<String> monthComboBox;
    private JSpinner yearSpinner;
    private JTextArea resultArea;
    private JButton editButton;
    private JButton deleteButton;
    private JButton backButton;
    private RegionData currentData;

    public ViewDataScreen() {
        super("View War Data");

        String[] regions = {"Gaza", "West Bank", "East Jerusalem"};
        String[] months = {"January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"};

        // ComboBoxes
        regionComboBox = new JComboBox<>(regions);
        regionComboBox.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        regionComboBox.setBackground(new Color(255, 255, 255, 230));

        monthComboBox = new JComboBox<>(months);
        monthComboBox.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        monthComboBox.setBackground(new Color(255, 255, 255, 230));

        yearSpinner = new JSpinner(new SpinnerNumberModel(2023, 2000, 2100, 1));
        JSpinner.NumberEditor editor = new JSpinner.NumberEditor(yearSpinner, "#");
        yearSpinner.setEditor(editor);
        yearSpinner.setFont(new Font("Times New Roman", Font.PLAIN, 16));

        // Buttons
        JButton viewButton = new JButton("View Data");
        styleButton(viewButton, new Color(60, 60, 60));

        editButton = new JButton("Edit Data");
        styleButton(editButton, new Color(0, 102, 153));
        editButton.setEnabled(false);

        deleteButton = new JButton("Delete Data");
        styleButton(deleteButton, new Color(139, 0, 0));
        deleteButton.setEnabled(false);

        backButton = new JButton("Back");
        styleButton(backButton, new Color(90, 90, 90));

        // TextArea
        resultArea = new JTextArea(18, 50);
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        resultArea.setBackground(new Color(245, 245, 245));
        JScrollPane scrollPane = new JScrollPane(resultArea);

        // Actions
        viewButton.addActionListener(e -> displayData());

        editButton.addActionListener(e -> {
            if (currentData != null) {
                new AddDataScreen(currentData, () -> {
                    new AddPersonScreen();
                });
                dispose();   
            }
        });

        deleteButton.addActionListener(e -> {
            if (currentData != null) {
                int confirm = JOptionPane.showConfirmDialog(this,
                        "Are you sure you want to delete this data?",
                        "Confirm Deletion", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    MainScreen.regionDatabase.remove(currentData);
                    resultArea.setText("Data deleted successfully.");
                    editButton.setEnabled(false);
                    deleteButton.setEnabled(false);
                    currentData = null;
                }
            }
        });

        backButton.addActionListener(e -> {
            dispose();
            new MainScreen();
        });

        // Layout
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        formPanel.setBackground(new Color(240, 240, 255));

        formPanel.add(labelWithFont("Select Region:", 16));
        formPanel.add(Box.createVerticalStrut(5));
        formPanel.add(regionComboBox);

        formPanel.add(Box.createVerticalStrut(15));
        formPanel.add(labelWithFont("Select Month:", 16));
        formPanel.add(Box.createVerticalStrut(5));
        formPanel.add(monthComboBox);

        formPanel.add(Box.createVerticalStrut(15));
        formPanel.add(labelWithFont("Select Year:", 16));
        formPanel.add(Box.createVerticalStrut(5));
        formPanel.add(yearSpinner);

        formPanel.add(Box.createVerticalStrut(20));
        viewButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        formPanel.add(viewButton);

        formPanel.add(Box.createVerticalStrut(10));
        editButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        formPanel.add(editButton);

        formPanel.add(Box.createVerticalStrut(10));
        deleteButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        formPanel.add(deleteButton);

        formPanel.add(Box.createVerticalStrut(10));
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        formPanel.add(backButton);

        setLayout(new BorderLayout());
        add(formPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        setSize(750, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void styleButton(JButton btn, Color bg) {
        btn.setFont(new Font("Times New Roman", Font.BOLD, 15));
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setMaximumSize(new Dimension(200, 35));
    }

    private JLabel labelWithFont(String text, int size) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Times New Roman", Font.BOLD, size));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    private void displayData() {
        String region = (String) regionComboBox.getSelectedItem();
        String month = (String) monthComboBox.getSelectedItem();
        int year = (int) yearSpinner.getValue();

        currentData = null;

        for (RegionData data : MainScreen.regionDatabase) {
            if (data.getRegion().equals(region)
                    && data.getMonth().equals(month)
                    && data.getYear() == year) {

                currentData = data;

                StringBuilder sb = new StringBuilder();
                sb.append("Region: ").append(region).append("\n");
                sb.append("Date: ").append(month).append(" ").append(year).append("\n\n");

                WarStats stats = data.getWarStats();
                sb.append("Martyrs: ").append(stats.getMartyrs()).append("\n");
                sb.append("Wounded: ").append(stats.getWounded()).append("\n");
                sb.append("Prisoners: ").append(stats.getPrisoners()).append("\n\n");

                HealthCareImpact health = data.getHealthImpact();
                sb.append("Hospitals Destroyed: ").append(health.getHospitalsDestroyed()).append("\n");
                sb.append("Untreated Patients: ").append(health.getUntreatedPatients()).append("\n\n");

                EducationImpact edu = data.getEduImpact();
                sb.append("Schools Destroyed: ").append(edu.getSchoolsDestroyed()).append("\n");
                sb.append("Displaced Students: ").append(edu.getDisplacedStudents()).append("\n\n");

                sb.append("Border Status: ").append(data.getBorderStatus().getStatus()).append("\n\n");

                ArrayList<WarVictim> victims = data.getVictims();
                if (victims.isEmpty()) {
                    sb.append("No recorded individuals (martyrs, wounded, prisoners).\n");
                } else {
                    sb.append("Victim List:\n");
                    for (String type : new String[]{"Martyr", "Wounded", "Prisoner"}) {
                        sb.append(" - ").append(type).append("s:\n");
                        boolean found = false;
                        for (WarVictim v : victims) {
                            if (v.getType().equalsIgnoreCase(type)) {
                                sb.append("    • ").append(v.getSummary()).append("\n");
                                found = true;
                            }
                        }
                        if (!found) sb.append("    • None\n");
                        sb.append("\n");
                    }
                }

                resultArea.setText(sb.toString());
                editButton.setEnabled(true);
                deleteButton.setEnabled(true);
                return;
            }
        }

        resultArea.setText("No data found for this selection.");
        editButton.setEnabled(false);
        deleteButton.setEnabled(false);
    }
}
