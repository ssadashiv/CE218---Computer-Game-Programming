package assignment2.utilities;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.IIOException;
import javax.imageio.ImageIO;

//Class which handles Image loading. I got this from the lab sessions.
class ImageManager {

    // this may need modifying
    private final static String path = "images/";
    private final static String ext = ".png";

    static Map<String, Image> images = new HashMap<>();

    public static Image getImage(String s) {
        return images.get(s);
    }

    static Image loadImage(String fname) throws IOException {
        BufferedImage img = null;
        try{
            img = ImageIO.read(new File(path + fname + ext));
        }catch (IIOException e){
            System.out.println("Error: "+ e.toString());
            System.out.println("filename="+fname);
        }

        images.put(fname, img);
        return img;
    }

    public static Image loadImage(String imName, String fname) throws IOException {
        BufferedImage img = null;
        img = ImageIO.read(new File(path + fname + ext));
        images.put(imName, img);
        return img;
    }

    public static void loadImages(String[] fNames) throws IOException {
        for (String s : fNames)
            loadImage(s);
    }

    public static void loadImages(Iterable<String> fNames) throws IOException {
        for (String s : fNames)
            loadImage(s);
    }

}