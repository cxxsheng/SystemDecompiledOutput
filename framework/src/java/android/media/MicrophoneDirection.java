package android.media;

/* loaded from: classes2.dex */
public interface MicrophoneDirection {
    public static final int MIC_DIRECTION_AWAY_FROM_USER = 2;
    public static final int MIC_DIRECTION_EXTERNAL = 3;
    public static final int MIC_DIRECTION_TOWARDS_USER = 1;
    public static final int MIC_DIRECTION_UNSPECIFIED = 0;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface DirectionMode {
    }

    boolean setPreferredMicrophoneDirection(int i);

    boolean setPreferredMicrophoneFieldDimension(float f);
}
