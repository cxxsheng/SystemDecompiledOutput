package android.view;

/* loaded from: classes4.dex */
public class SoundEffectConstants {
    public static final int CLICK = 0;
    public static final int NAVIGATION_DOWN = 4;
    public static final int NAVIGATION_LEFT = 1;
    public static final int NAVIGATION_REPEAT_DOWN = 8;
    public static final int NAVIGATION_REPEAT_LEFT = 5;
    public static final int NAVIGATION_REPEAT_RIGHT = 7;
    public static final int NAVIGATION_REPEAT_UP = 6;
    public static final int NAVIGATION_RIGHT = 3;
    public static final int NAVIGATION_UP = 2;
    private static final java.util.Random NAVIGATION_REPEAT_RANDOMIZER = new java.util.Random();
    private static int sLastNavigationRepeatSoundEffectId = -1;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface NavigationSoundEffect {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface SoundEffect {
    }

    private SoundEffectConstants() {
    }

    public static int getContantForFocusDirection(int i) {
        switch (i) {
            case 1:
            case 33:
                return 2;
            case 2:
            case 130:
                return 4;
            case 17:
                return 1;
            case 66:
                return 3;
            default:
                throw new java.lang.IllegalArgumentException("direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT, FOCUS_FORWARD, FOCUS_BACKWARD}.");
        }
    }

    public static int getConstantForFocusDirection(int i, boolean z) {
        if (z) {
            switch (i) {
                case 1:
                case 33:
                    return 6;
                case 2:
                case 130:
                    return 8;
                case 17:
                    return 5;
                case 66:
                    return 7;
                default:
                    throw new java.lang.IllegalArgumentException("direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT, FOCUS_FORWARD, FOCUS_BACKWARD}.");
            }
        }
        return getContantForFocusDirection(i);
    }

    public static boolean isNavigationRepeat(int i) {
        return i == 8 || i == 5 || i == 7 || i == 6;
    }

    public static int nextNavigationRepeatSoundEffectId() {
        int nextInt = NAVIGATION_REPEAT_RANDOMIZER.nextInt(3);
        if (nextInt >= sLastNavigationRepeatSoundEffectId) {
            nextInt++;
        }
        sLastNavigationRepeatSoundEffectId = nextInt;
        return android.media.AudioManager.getNthNavigationRepeatSoundEffect(nextInt);
    }
}
