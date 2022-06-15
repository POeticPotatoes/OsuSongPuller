package me.poeticpotatoes.osupuller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import javax.imageio.ImageIO;

public class OsuPuller {
	
	private final static String wallpaperPath = "Wallpapers";
	private final static File wallpapers = new File(wallpaperPath);
	private static int count = 0;
	private static int minHeight = 1080, minWidth = 1920;

	public static void main(String[] args) throws IOException {
		
		if (!wallpapers.exists())
			wallpapers.mkdir();
		
		System.out.println(wallpapers.getAbsolutePath());
		
		if (args.length >= 2) {
			minWidth = Integer.parseInt(args[0]);
			minHeight = Integer.parseInt(args[1]);
		}
		
		System.out.println("Extracting all images with dimensions of at least " + minWidth + "x" + minHeight);
		
		open(Paths.get(""));
	}

	private static void open(Path path) throws IOException {
		
		System.out.println("Exploring " + path.toAbsolutePath());
		
		Stream<Path> s = Files.list(path);

		s.forEach(p -> {
			
			try {
			
				if (Files.isDirectory(p)) {
					open(p);
					return;
				}

				File f = p.toFile();
				
				if (!f.getName().endsWith(".jpg"))
					return;
			
				BufferedImage image = ImageIO.read(f);
				
				if (image.getWidth() < minWidth || image.getHeight() < minHeight) {
					System.out.println("Image " + f.getName() + " was too small! Skipping image.");
					return;
				}
				
				System.out.println("Copying " + f.getName() + " as " + count);

				File newImg = new File(wallpaperPath + "/" + count + ".jpg");
				ImageIO.write(image, "jpg", newImg);
				
				count++;

			} catch (Exception e) {}
		});
		
		s.close();
	}
}
