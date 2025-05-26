package First;

import javax.swing.*;
import java.awt.*;

public class AddDataScreen extends JFrame {
    private static final long serialVersionUID = 1L;

    // Callback that will run after saving
    private Runnable onSaveCallback;

    // Constructor for new data entry
    public AddDataScreen(String region, String month, int year) {
        this(new RegionData(region, year, month, new WarStats(0, 0, 0),
                new HealthCareImpact(0, 0),
                new EducationImpact(0, 0),
                new BorderStatus("Closed")));
    }

    // Constructor without callback
    public AddDataScreen(RegionData existingData) {
        this(existingData, null);
    }

    // Constructor with callback to run after saving
    public AddDataScreen(RegionData existingData, Runnable onSaveCallback) {
        super("Add/Edit War Data");
        this.onSaveCallback = onSaveCallback;

        String region = existingData.getRegion();
        String month = existingData.getMonth();
        int year = existingData.getYear();

        JLabel titleLabel = new JLabel("Enter War Impact Data for " + region + " (" + month + " " + year + ")");
        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Input fields
        JTextField martyrsField = new JTextField(String.valueOf(existingData.getWarStats().getMartyrs()), 10);
        JTextField woundedField = new JTextField(String.valueOf(existingData.getWarStats().getWounded()), 10);
        JTextField prisonersField = new JTextField(String.valueOf(existingData.getWarStats().getPrisoners()), 10);

        JTextField hospitalsField = new JTextField(String.valueOf(existingData.getHealthImpact().getHospitalsDestroyed()), 10);
        JTextField patientsField = new JTextField(String.valueOf(existingData.getHealthImpact().getUntreatedPatients()), 10);

        JTextField schoolsField = new JTextField(String.valueOf(existingData.getEduImpact().getSchoolsDestroyed()), 10);
        JTextField studentsField = new JTextField(String.valueOf(existingData.getEduImpact().getDisplacedStudents()), 10);

        String[] borderOptions = {"Open", "Closed", "Partially Open"};
        JComboBox<String> borderBox = new JComboBox<>(borderOptions);
        borderBox.setSelectedItem(existingData.getBorderStatus().getStatus());

        JButton confirmButton = new JButton("Confirm");
        confirmButton.setBackground(new Color(34, 139, 34));
        confirmButton.setForeground(Color.WHITE);
        confirmButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
        confirmButton.setFocusPainted(false);

        // Input layout
        JPanel inputPanel = new JPanel(new GridLayout(9, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        Font labelFont = new Font("Times New Roman", Font.PLAIN, 15);

        inputPanel.add(new JLabel("Martyrs:")).setFont(labelFont);
        inputPanel.add(martyrsField);
        inputPanel.add(new JLabel("Wounded:")).setFont(labelFont);
        inputPanel.add(woundedField);
        inputPanel.add(new JLabel("Prisoners:")).setFont(labelFont);
        inputPanel.add(prisonersField);
        inputPanel.add(new JLabel("Hospitals Destroyed:")).setFont(labelFont);
        inputPanel.add(hospitalsField);
        inputPanel.add(new JLabel("Untreated Patients:")).setFont(labelFont);
        inputPanel.add(patientsField);
        inputPanel.add(new JLabel("Schools Destroyed:")).setFont(labelFont);
        inputPanel.add(schoolsField);
        inputPanel.add(new JLabel("Displaced Students:")).setFont(labelFont);
        inputPanel.add(studentsField);
        inputPanel.add(new JLabel("Border Status:")).setFont(labelFont);
        inputPanel.add(borderBox);
        inputPanel.add(new JLabel());
        inputPanel.add(confirmButton);

        // Confirm action (with manual validation)
        confirmButton.addActionListener(e -> {
            String martyrsText = martyrsField.getText().trim();
            String woundedText = woundedField.getText().trim();
            String prisonersText = prisonersField.getText().trim();
            String hospitalsText = hospitalsField.getText().trim();
            String patientsText = patientsField.getText().trim();
            String schoolsText = schoolsField.getText().trim();
            String studentsText = studentsField.getText().trim();
            String status = (String) borderBox.getSelectedItem();

            if (!isValidInteger(martyrsText) || !isValidInteger(woundedText) || !isValidInteger(prisonersText)
                    || !isValidInteger(hospitalsText) || !isValidInteger(patientsText)
                    || !isValidInteger(schoolsText) || !isValidInteger(studentsText)) {
                JOptionPane.showMessageDialog(this, "Please enter valid whole numbers in all fields.",
                        "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Convert inputs
            int martyrs = Integer.parseInt(martyrsText);
            int wounded = Integer.parseInt(woundedText);
            int prisoners = Integer.parseInt(prisonersText);
            int hospitals = Integer.parseInt(hospitalsText);
            int patients = Integer.parseInt(patientsText);
            int schools = Integer.parseInt(schoolsText);
            int students = Integer.parseInt(studentsText);

            // Update data
            existingData.setWarStats(new WarStats(martyrs, wounded, prisoners));
            existingData.setHealthImpact(new HealthCareImpact(hospitals, patients));
            existingData.setEduImpact(new EducationImpact(schools, students));
            existingData.setBorderStatus(new BorderStatus(status));

            if (!MainScreen.regionDatabase.contains(existingData)) {
                MainScreen.regionDatabase.add(existingData);
            }

            JOptionPane.showMessageDialog(this, "Data saved successfully.");
            dispose();

            // Execute callback after save
            if (onSaveCallback != null) {
                onSaveCallback.run();
            }
        });

        // Frame setup
        setLayout(new BorderLayout());
        add(titleLabel, BorderLayout.NORTH);
        add(inputPanel, BorderLayout.CENTER);

        setSize(450, 480);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // Checks if a string is a valid positive integer
    private boolean isValidInteger(String text) {
        if (text == null || text.isEmpty()) return false;
        for (char c : text.toCharArray()) {
            if (!Character.isDigit(c)) return false;
        }
        return true;
    }
}
