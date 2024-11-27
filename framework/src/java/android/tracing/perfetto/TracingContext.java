package android.tracing.perfetto;

/* loaded from: classes3.dex */
public class TracingContext<DataSourceInstanceType extends android.tracing.perfetto.DataSourceInstance, TlsStateType, IncrementalStateType> {
    private final long mContextPtr;
    private final IncrementalStateType mIncrementalState;
    private final TlsStateType mTlsState;
    private final java.util.List<android.util.proto.ProtoOutputStream> mTracePackets = new java.util.ArrayList();

    private static native void nativeFlush(android.tracing.perfetto.TracingContext tracingContext, long j);

    private TracingContext(long j, TlsStateType tlsstatetype, IncrementalStateType incrementalstatetype) {
        this.mContextPtr = j;
        this.mTlsState = tlsstatetype;
        this.mIncrementalState = incrementalstatetype;
    }

    public android.util.proto.ProtoOutputStream newTracePacket() {
        android.util.proto.ProtoOutputStream protoOutputStream = new android.util.proto.ProtoOutputStream(0);
        this.mTracePackets.add(protoOutputStream);
        return protoOutputStream;
    }

    public void flush() {
        nativeFlush(this, this.mContextPtr);
    }

    public TlsStateType getCustomTlsState() {
        return this.mTlsState;
    }

    public IncrementalStateType getIncrementalState() {
        return this.mIncrementalState;
    }

    private byte[][] getAndClearAllPendingTracePackets() {
        byte[][] bArr = new byte[this.mTracePackets.size()][];
        for (int i = 0; i < this.mTracePackets.size(); i++) {
            bArr[i] = this.mTracePackets.get(i).getBytes();
        }
        this.mTracePackets.clear();
        return bArr;
    }
}
