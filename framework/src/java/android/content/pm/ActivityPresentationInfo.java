package android.content.pm;

/* loaded from: classes.dex */
public final class ActivityPresentationInfo {
    public final android.content.ComponentName componentName;
    public final int displayId;
    public final int taskId;

    public ActivityPresentationInfo(int i, int i2, android.content.ComponentName componentName) {
        this.taskId = i;
        this.displayId = i2;
        this.componentName = componentName;
    }
}
