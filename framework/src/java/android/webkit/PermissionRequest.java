package android.webkit;

/* loaded from: classes4.dex */
public abstract class PermissionRequest {
    public static final java.lang.String RESOURCE_AUDIO_CAPTURE = "android.webkit.resource.AUDIO_CAPTURE";
    public static final java.lang.String RESOURCE_MIDI_SYSEX = "android.webkit.resource.MIDI_SYSEX";
    public static final java.lang.String RESOURCE_PROTECTED_MEDIA_ID = "android.webkit.resource.PROTECTED_MEDIA_ID";
    public static final java.lang.String RESOURCE_VIDEO_CAPTURE = "android.webkit.resource.VIDEO_CAPTURE";

    public abstract void deny();

    public abstract android.net.Uri getOrigin();

    public abstract java.lang.String[] getResources();

    public abstract void grant(java.lang.String[] strArr);
}
