package android.media;

/* loaded from: classes2.dex */
public interface AudioRecordingMonitor {
    android.media.AudioRecordingConfiguration getActiveRecordingConfiguration();

    void registerAudioRecordingCallback(java.util.concurrent.Executor executor, android.media.AudioManager.AudioRecordingCallback audioRecordingCallback);

    void unregisterAudioRecordingCallback(android.media.AudioManager.AudioRecordingCallback audioRecordingCallback);
}
