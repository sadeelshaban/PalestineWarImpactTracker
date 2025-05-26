package First;

import javax.swing.*;
import java.awt.*;

/*
The welcome screen of the Palestine War Impact Tracker application.
This screen displays the project title, introduction, and navigation buttons.
 */
public class WelcomeScreen extends JFrame {
    private static final long serialVersionUID = 1L;

    public WelcomeScreen() {
        super("Palestine War Impact");

        // background image
        ImageIcon bgIcon = new ImageIcon(getClass().getResource("background.jpg"));
        JLabel backgroundLabel = new JLabel(bgIcon);
        backgroundLabel.setLayout(new BorderLayout());

        // Defining custom colors
        Color golden = new Color(255, 215, 0);       // Golden for titles and highlights
        Color overlayBlack = new Color(0, 0, 0, 150); // Transparent black for text background

        // Defining fonts
        Font titleFont = new Font("Times New Roman", Font.BOLD, 32);
        Font subtitleFont = new Font("Times New Roman", Font.PLAIN, 22);
        Font paragraphFont = new Font("Times New Roman", Font.PLAIN, 15);
        Font warFont = new Font("Times New Roman", Font.PLAIN, 17);

        // Title
        JLabel title = new JLabel("Palestine War Impact", SwingConstants.CENTER);
        title.setFont(titleFont);
        title.setForeground(golden);

        // Subtitle
        JLabel subtitle = new JLabel(
            "The Impact of the Israeli Occupation and Ongoing War on Palestine",
            SwingConstants.CENTER
        );
        subtitle.setFont(subtitleFont);
        subtitle.setForeground(golden);

        // War information paragraph
        JLabel warInfo = new JLabel("<html><center>The Israeli occupation and the war on Palestine intensified starting October 7, 2023.<br>"
                + "Since then, Gaza has been under relentless attacks, resulting in thousands of martyrs,<br>"
                + "the destruction of hospitals, schools, and homes, and the displacement of entire families.</center></html>", SwingConstants.CENTER);
        warInfo.setFont(warFont);
        warInfo.setOpaque(true);
        warInfo.setBackground(overlayBlack);
        warInfo.setForeground(Color.WHITE);

        // Intro paragraph
        JLabel intro = new JLabel("<html><center>The Palestine War Impact Tracker is an interactive platform designed to document and visualize<br>"
                + "the timeline of the war on Palestine, region by region, date by date.<br><br>"
                + "Since the beginning of the conflict, countless Palestinian lives have been affected, cities destroyed, and histories rewritten.<br>"
                + "This app aims to preserve these stories by showing what happened, where, and when — from the onset of the war until today.<br><br>"
                + "Our goal is to raise awareness, provide historical context, and support digital archiving<br>"
                + "of the devastating impact of war on Palestinian cities and people.<br><br>"
                + "<i>This application is for educational and humanitarian purposes only.<br>"
                + "We stand in solidarity with the people of Palestine.</i></center></html>", SwingConstants.CENTER);
        intro.setFont(paragraphFont);
        intro.setOpaque(true);
        intro.setBackground(overlayBlack);
        intro.setForeground(Color.WHITE);

        // Description paragraph
        JLabel description = new JLabel("<html><center>This application presents a humanitarian, educational, and healthcare overview<br>of the ongoing war on Gaza, West Bank, and Jerusalem.</center></html>", SwingConstants.CENTER);
        description.setFont(paragraphFont);
        description.setOpaque(true);
        description.setBackground(overlayBlack);
        description.setForeground(Color.WHITE);

        // Panel for text content
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setOpaque(false); // Transparent background
        textPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        // Add components to text panel
        for (JLabel lbl : new JLabel[]{title, subtitle, warInfo, intro, description}) {
            lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
            textPanel.add(lbl);
            textPanel.add(Box.createVerticalStrut(10)); // Add spacing
        }

        // Buttons
        JButton exitButton = new JButton("Exit");
        JButton nextButton = new JButton("Next");

        // Customize button appearance and hover effect
        for (JButton button : new JButton[]{exitButton, nextButton}) {
            button.setFont(paragraphFont);
            button.setFocusPainted(false);
            button.setBackground(new Color(60, 60, 60)); // Dark gray background
            button.setForeground(Color.WHITE);
            button.setCursor(new Cursor(Cursor.HAND_CURSOR));

            button.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    button.setBackground(golden); // Highlight on hover
                    button.setForeground(Color.BLACK);
                }

                @Override
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    button.setBackground(new Color(60, 60, 60)); // Revert background
                    button.setForeground(Color.WHITE);
                }
            });
        }

        // Button actions
        exitButton.addActionListener(e -> System.exit(0));
        nextButton.addActionListener(e -> {
            dispose();
            new MainScreen();  // Go directly to MainScreen on Next
        });

        // Panel for buttons
        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
        buttonPanel.add(exitButton, BorderLayout.WEST);
        buttonPanel.add(nextButton, BorderLayout.EAST);

        // Add panels to background
        backgroundLabel.add(textPanel, BorderLayout.CENTER);
        backgroundLabel.add(buttonPanel, BorderLayout.SOUTH);

        // Frame setup
        setContentPane(backgroundLabel);
        setSize(950, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
