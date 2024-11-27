package android.telecom;

/* loaded from: classes3.dex */
public final class StreamingCallAdapter {
    private final com.android.internal.telecom.IStreamingCallAdapter mAdapter;

    public StreamingCallAdapter(com.android.internal.telecom.IStreamingCallAdapter iStreamingCallAdapter) {
        this.mAdapter = iStreamingCallAdapter;
    }

    public void setStreamingState(int i) {
        try {
            this.mAdapter.setStreamingState(i);
        } catch (android.os.RemoteException e) {
        }
    }
}
