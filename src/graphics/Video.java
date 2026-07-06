package graphics;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import javax.swing.*;
import java.io.File;

public class Video extends JFXPanel {
    private MediaPlayer mediaPlayer;
    private MediaView mediaView;
    private final int width;
    private final int height;

    /**
     * Constructor to initialize the video player dimensions and source file.
     * @param videoPath Path to the video file (e.g., "C:/videos/sample.mp4")
     * @param width Desired width of the video player
     * @param height Desired height of the video player
     */
    public Video(String videoPath, int width, int height) {
        this.width = width;
        this.height = height;

        // Set initial Swing properties
        this.setSize(width, height);
        this.setVisible(false); // Hidden until Play() is called

        // Safely initialize JavaFX components on the JavaFX Application Thread
        Platform.runLater(() -> {
            try {
                File file = new File(videoPath);
                Media media = new Media(file.toURI().toString());
                mediaPlayer = new MediaPlayer(media);
                mediaView = new MediaView(mediaPlayer);

                // Set dimensions
                mediaView.setFitWidth(width);
                mediaView.setFitHeight(height);

                // Create the scene graph
                Group root = new Group(mediaView);
                Scene scene = new Scene(root, width, height);
                this.setScene(scene);
            } catch (Exception e) {
                System.err.println("Error initializing JavaFX Media: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }

    /**
     * Positions the video component, makes it visible, and starts playback.
     * @param x The X coordinate relative to the parent Swing container
     * @param y The Y coordinate relative to the parent Swing container
     */
    public void Play(int x, int y) {
        // Swing operations should ideally happen on the Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(() -> {
            // Position the component in the Swing layout
            this.setLocation(x, y);
            this.setVisible(true);

            // Revalidate and repaint parent if necessary to ensure it renders
            if (getParent() != null) {
                getParent().revalidate();
                getParent().repaint();
            }

            // Trigger the video play on the JavaFX thread
            Platform.runLater(() -> {
                if (mediaPlayer != null) {
                    // Seek to the beginning if it was played before
                    mediaPlayer.seek(mediaPlayer.getStartTime()); 
                    mediaPlayer.play();
                }
            });
        });
    }

    /**
     * Optional helper to stop the video and hide the component.
     */
    public void Stop() {
        Platform.runLater(() -> {
            if (mediaPlayer != null) {
                mediaPlayer.stop();
            }
            SwingUtilities.invokeLater(() -> this.setVisible(false));
        });
    }
}