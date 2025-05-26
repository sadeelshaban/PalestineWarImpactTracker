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

        JLabel title = new JLabel("Palestine War Impact", SwingConstants.CENTER);
        title.setFont(UiTheme.TITLE);
        title.setForeground(UiTheme.GOLD_SOFT);

        JLabel subtitle = new JLabel(
            "<html><div style='text-align:center;'>The Impact of the Israeli Occupation and Ongoing War on Palestine</div></html>",
            SwingConstants.CENTER
        );
        subtitle.setFont(UiTheme.SUBTITLE);
        subtitle.setForeground(UiTheme.GOLD_SOFT);

        JEditorPane body = new JEditorPane("text/html",
                "<html><head><style>"
                        + "body { font-family: 'Segoe UI'; font-size: 14px; color: #FFFFFF; margin: 4px 18px; }"
                        + "p { text-align: center; margin-top: 0; margin-bottom: 14px; }"
                        + "</style></head><body>"
                        + "<p>The history of conflict and displacement in Palestine extends back decades, with 1948 marking a major turning point "
                        + "that deeply affected generations of Palestinians. Over the years, the region has experienced repeated periods of tension, "
                        + "displacement, and humanitarian crises.</p>"
                        + "<p>On October 7, 2023, the situation entered a particularly devastating phase, with widespread destruction and displacement "
                        + "across Gaza and severe impacts on civilian life and essential infrastructure, including homes, hospitals, schools, "
                        + "and other vital facilities.</p>"
                        + "<p>The Palestine War Impact Tracker is an interactive platform created to document and visualize these events through time and place. "
                        + "It focuses particularly on the period following October 7, 2023, while providing historical context for understanding the events "
                        + "within a longer timeline.</p>"
                        + "<p>The platform aims to preserve information about what happened, where, and when, supporting digital documentation, "
                        + "historical reference, and educational awareness.</p>"
                        + "<p><i>This application is intended for educational and humanitarian purposes only.</i></p>"
                        + "</body></html>");
        Color textBg = new Color(12, 12, 12);
        body.setEditable(false);
        body.setOpaque(true);
        body.setBackground(textBg);
        body.setBorder(BorderFactory.createEmptyBorder(8, 16, 4, 16));
        body.putClientProperty(JEditorPane.HONOR_DISPLAY_PROPERTIES, Boolean.TRUE);
        body.setFont(UiTheme.BODY);
        body.setForeground(Color.WHITE);

        JPanel heading = new JPanel();
        heading.setOpaque(false);
        heading.setLayout(new BoxLayout(heading, BoxLayout.Y_AXIS));
        heading.setBorder(BorderFactory.createEmptyBorder(28, 40, 8, 40));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        heading.add(title);
        heading.add(Box.createVerticalStrut(6));
        heading.add(subtitle);

        JScrollPane bodyScroll = new JScrollPane(body);
        bodyScroll.setOpaque(true);
        bodyScroll.setBackground(textBg);
        bodyScroll.getViewport().setOpaque(true);
        bodyScroll.getViewport().setBackground(textBg);
        bodyScroll.setBorder(BorderFactory.createEmptyBorder());
        bodyScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        bodyScroll.getVerticalScrollBar().setUnitIncrement(14);

        JPanel textCard = UiTheme.overlayCard();
        textCard.setLayout(new BorderLayout());
        textCard.add(bodyScroll, BorderLayout.CENTER);

        JPanel textWrap = new JPanel(new BorderLayout());
        textWrap.setOpaque(false);
        textWrap.setBorder(BorderFactory.createEmptyBorder(8, 48, 8, 48));
        textWrap.add(textCard, BorderLayout.CENTER);

        JButton exitButton = UiTheme.button("Exit", UiTheme.Kind.DARK);
        JButton nextButton = UiTheme.button("Next", UiTheme.Kind.GOLD);
        Dimension buttonSize = new Dimension(140, 42);
        exitButton.setPreferredSize(buttonSize);
        nextButton.setPreferredSize(buttonSize);

        exitButton.addActionListener(e -> System.exit(0));
        nextButton.addActionListener(e -> {
            dispose();
            new MainScreen();
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 14, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(8, 24, 22, 36));
        buttonPanel.add(exitButton);
        buttonPanel.add(nextButton);

        backgroundLabel.add(heading, BorderLayout.NORTH);
        backgroundLabel.add(textWrap, BorderLayout.CENTER);
        backgroundLabel.add(buttonPanel, BorderLayout.SOUTH);

        // Frame setup
        setContentPane(backgroundLabel);
        setSize(1000, 760);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
