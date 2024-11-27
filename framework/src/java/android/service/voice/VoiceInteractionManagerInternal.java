package android.service.voice;

/* loaded from: classes3.dex */
public abstract class VoiceInteractionManagerInternal {

    public interface WearableHotwordDetectionCallback {
        void onDetected();

        void onError(java.lang.String str);

        void onRejected();
    }

    public abstract android.service.voice.VoiceInteractionManagerInternal.HotwordDetectionServiceIdentity getHotwordDetectionServiceIdentity();

    public abstract java.lang.String getVoiceInteractorPackageName(android.os.IBinder iBinder);

    public abstract boolean hasActiveSession(java.lang.String str);

    public abstract void onPreCreatedUserConversion(int i);

    public abstract void startListeningFromWearable(android.os.ParcelFileDescriptor parcelFileDescriptor, android.media.AudioFormat audioFormat, android.os.PersistableBundle persistableBundle, android.content.ComponentName componentName, int i, android.service.voice.VoiceInteractionManagerInternal.WearableHotwordDetectionCallback wearableHotwordDetectionCallback);

    public abstract void startLocalVoiceInteraction(android.os.IBinder iBinder, java.lang.String str, android.os.Bundle bundle);

    public abstract void stopLocalVoiceInteraction(android.os.IBinder iBinder);

    public abstract boolean supportsLocalVoiceInteraction();

    public static class HotwordDetectionServiceIdentity {
        private final int mIsolatedUid;
        private final int mOwnerUid;

        public HotwordDetectionServiceIdentity(int i, int i2) {
            this.mIsolatedUid = i;
            this.mOwnerUid = i2;
        }

        public int getIsolatedUid() {
            return this.mIsolatedUid;
        }

        public int getOwnerUid() {
            return this.mOwnerUid;
        }
    }
}
