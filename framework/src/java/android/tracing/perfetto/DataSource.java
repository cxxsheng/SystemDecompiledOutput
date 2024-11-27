package android.tracing.perfetto;

/* loaded from: classes3.dex */
public abstract class DataSource<DataSourceInstanceType extends android.tracing.perfetto.DataSourceInstance, TlsStateType, IncrementalStateType> {
    protected final long mNativeObj;
    public final java.lang.String name;

    private static native long nativeCreate(android.tracing.perfetto.DataSource dataSource, java.lang.String str);

    private static native void nativeFlushAll(long j);

    private static native long nativeGetFinalizer();

    private static native android.tracing.perfetto.DataSourceInstance nativeGetPerfettoInstanceLocked(long j, int i);

    private static native void nativeRegisterDataSource(long j, int i);

    private static native void nativeReleasePerfettoInstanceLocked(long j, int i);

    private static native void nativeTrace(long j, android.tracing.perfetto.TraceFunction traceFunction);

    public abstract DataSourceInstanceType createInstance(android.util.proto.ProtoInputStream protoInputStream, int i);

    public DataSource(java.lang.String str) {
        this.name = str;
        this.mNativeObj = nativeCreate(this, str);
    }

    public final void trace(android.tracing.perfetto.TraceFunction<DataSourceInstanceType, TlsStateType, IncrementalStateType> traceFunction) {
        nativeTrace(this.mNativeObj, traceFunction);
    }

    public final void flush() {
        nativeFlushAll(this.mNativeObj);
    }

    public TlsStateType createTlsState(android.tracing.perfetto.CreateTlsStateArgs<DataSourceInstanceType> createTlsStateArgs) {
        return null;
    }

    protected IncrementalStateType createIncrementalState(android.tracing.perfetto.CreateIncrementalStateArgs<DataSourceInstanceType> createIncrementalStateArgs) {
        return null;
    }

    public void register(android.tracing.perfetto.DataSourceParams dataSourceParams) {
        nativeRegisterDataSource(this.mNativeObj, dataSourceParams.bufferExhaustedPolicy);
    }

    public DataSourceInstanceType getDataSourceInstanceLocked(int i) {
        return (DataSourceInstanceType) nativeGetPerfettoInstanceLocked(this.mNativeObj, i);
    }

    protected void releaseDataSourceInstance(int i) {
        nativeReleasePerfettoInstanceLocked(this.mNativeObj, i);
    }

    private DataSourceInstanceType createInstance(byte[] bArr, int i) {
        return createInstance(new android.util.proto.ProtoInputStream(bArr), i);
    }
}
