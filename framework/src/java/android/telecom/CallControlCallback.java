package android.telecom;

/* loaded from: classes3.dex */
public interface CallControlCallback {
    void onAnswer(int i, java.util.function.Consumer<java.lang.Boolean> consumer);

    void onCallStreamingStarted(java.util.function.Consumer<java.lang.Boolean> consumer);

    void onDisconnect(android.telecom.DisconnectCause disconnectCause, java.util.function.Consumer<java.lang.Boolean> consumer);

    void onSetActive(java.util.function.Consumer<java.lang.Boolean> consumer);

    void onSetInactive(java.util.function.Consumer<java.lang.Boolean> consumer);
}
