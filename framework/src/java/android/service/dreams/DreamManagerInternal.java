package android.service.dreams;

/* loaded from: classes3.dex */
public abstract class DreamManagerInternal {
    public abstract boolean canStartDreaming(boolean z);

    public abstract boolean isDreaming();

    public abstract void registerDreamManagerStateListener(android.service.dreams.DreamManagerInternal.DreamManagerStateListener dreamManagerStateListener);

    public abstract void requestDream();

    public abstract void startDream(boolean z, java.lang.String str);

    public abstract void stopDream(boolean z, java.lang.String str);

    public abstract void unregisterDreamManagerStateListener(android.service.dreams.DreamManagerInternal.DreamManagerStateListener dreamManagerStateListener);

    public interface DreamManagerStateListener {
        default void onKeepDreamingWhenUnpluggingChanged(boolean z) {
        }

        default void onDreamingStarted() {
        }

        default void onDreamingStopped() {
        }
    }
}
