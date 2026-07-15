package graphics;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;

/**
 * Simple wrapper around JavaFX's MediaPlayer for playing sound files.
 *
 * Usage:
 * Sound s = new Sound("path to file");
 * s.Play();
 * Thread.sleep(3000);
 * s.Stop();
 */
public class Sound {

	private MediaPlayer mediaPlayer;

	public Sound(String path) {
		File file = new File(path);
		Media media = new Media(file.toURI().toString());
		mediaPlayer = new MediaPlayer(media);

		// // Give the media a little time to load, same as your Video class does.
		// try {
		// Thread.sleep(200);
		// } catch (InterruptedException e) {
		// Thread.currentThread().interrupt();
		// }
	}

	public void Play() {
		mediaPlayer.play();
	}

	public void Stop() {
		mediaPlayer.stop();
	}
}
