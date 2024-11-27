package android.content;

/* loaded from: classes.dex */
public interface ServiceConnection {
    void onServiceConnected(android.content.ComponentName componentName, android.os.IBinder iBinder);

    void onServiceDisconnected(android.content.ComponentName componentName);

    default void onBindingDied(android.content.ComponentName componentName) {
    }

    default void onNullBinding(android.content.ComponentName componentName) {
    }
}
