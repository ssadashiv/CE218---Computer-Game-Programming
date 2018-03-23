package assignment2.utilities;

import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.AudioInputStream;
import java.io.File;

// SoundManager for My Game

public class SoundManager {

    private static boolean thrusting = false;

    // this may need modifying
    private final static String path = "sounds/";

    // note: having too many clips open may cause
    // "LineUnavailableException: No Free Voices"
    private final static Clip[] bullets = new Clip[15];

  /*  public final static Clip bangLarge = getClip("thrust");
    public final static Clip bangMedium = getClip("bangMedium");
    public final static Clip bangSmall = getClip("bangSmall");
    public final static Clip beat1 = getClip("beat1");
    public final static Clip beat2 = getClip("beat2");
    public final static Clip extraShip = getClip("extraShip");
    public final static Clip fire = getClip("fire");
    public final static Clip saucerBig = getClip("saucerBig");
    public final static Clip saucerSmall = getClip("saucerSmall");
    public final static Clip thrust = getClip("thrust");
    */

     /*
    private final static Clip[] clips = {bangLarge, bangMedium, bangSmall, beat1, beat2,
            extraShip, fire, saucerBig, saucerSmall, thrust};

*/

    public static Clip
            bangLarge,
            bangMedium,
            bangSmall,
            beat1,
            beat2,
            extraShip,
            fire,
            saucerSmall,
            saucerBig,
            thrust;


    static {

        bangLarge = getClip("thrust");
        bangMedium = getClip("bangMedium");
        bangSmall = getClip("bangSmall");
        beat1 = getClip("beat1");
        beat2 = getClip("beat2");
        extraShip = getClip("extraShip");
        fire = getClip("fire");
        saucerBig = getClip("saucerBig");
        saucerSmall = getClip("saucerSmall");
        thrust = getClip("thrust");


    }
    // methods which do not modify any fields

    public static void play(Clip clip) {
        clip.setFramePosition(0);
        clip.start();
    }

    private static Clip getClip(String filename) {
        Clip clip = null;
        try {
            clip = AudioSystem.getClip();
            AudioInputStream sample = AudioSystem.getAudioInputStream(new File(path
                    + filename + ".wav"));
            clip.open(sample);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return clip;
    }

    // methods which modify (static) fields

   /* public static void fire() {
        // fire the n-th bullet and increment the index
        Clip clip = bullets[nBullet];
        clip.setFramePosition(0);
        clip.start();
        nBullet = (nBullet + 1) % bullets.length;
    }*/

    public static void startThrust() {
        if (!thrusting) {
            thrust.loop(-1);
            thrusting = true;
        }
    }

    public static void stopThrust() {
        if (thrusting) {
            thrust.loop(0);
            thrusting = false;
        }
    }

    // Custom methods playing a particular sound
    // Please add your own methods below
    public static void smallAsteroids() {
        play(bangSmall);
    }

    public static void mediumAsteroids() {
        play(bangMedium);
    }

    public static void largeAsteroids() {
        play(bangLarge);
    }

    public static void extraShip() {
        play(extraShip);
    }

    public static void saucerSmall() {
        play(saucerSmall);
    }
}