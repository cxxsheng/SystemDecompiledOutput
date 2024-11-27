package android.media;

/* loaded from: classes2.dex */
public abstract class AudioManagerInternal {

    public interface RingerModeDelegate {
        boolean canVolumeDownEnterSilent();

        int getRingerModeAffectedStreams(int i);

        int onSetRingerModeExternal(int i, int i2, java.lang.String str, int i3, android.media.VolumePolicy volumePolicy);

        int onSetRingerModeInternal(int i, int i2, java.lang.String str, int i3, android.media.VolumePolicy volumePolicy);
    }

    public abstract void addAssistantServiceUid(int i);

    public abstract int getRingerModeInternal();

    public abstract void removeAssistantServiceUid(int i);

    public abstract void setAccessibilityServiceUids(android.util.IntArray intArray);

    public abstract void setActiveAssistantServicesUids(android.util.IntArray intArray);

    public abstract void setInputMethodServiceUid(int i);

    public abstract void setRingerModeDelegate(android.media.AudioManagerInternal.RingerModeDelegate ringerModeDelegate);

    public abstract void setRingerModeInternal(int i, java.lang.String str);

    public abstract void silenceRingerModeInternal(java.lang.String str);

    public abstract void updateRingerModeAffectedStreamsInternal();
}
