package android.telecom;

/* loaded from: classes3.dex */
public interface CallEventCallback {
    void onAvailableCallEndpointsChanged(java.util.List<android.telecom.CallEndpoint> list);

    void onCallEndpointChanged(android.telecom.CallEndpoint callEndpoint);

    void onCallStreamingFailed(int i);

    void onEvent(java.lang.String str, android.os.Bundle bundle);

    void onMuteStateChanged(boolean z);

    default void onVideoStateChanged(int i) {
    }
}
