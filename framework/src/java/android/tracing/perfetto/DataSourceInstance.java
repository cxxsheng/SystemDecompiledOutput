package android.tracing.perfetto;

/* loaded from: classes3.dex */
public abstract class DataSourceInstance implements java.lang.AutoCloseable {
    private final android.tracing.perfetto.DataSource mDataSource;
    private final int mInstanceIndex;

    public DataSourceInstance(android.tracing.perfetto.DataSource dataSource, int i) {
        this.mDataSource = dataSource;
        this.mInstanceIndex = i;
    }

    protected void onStart(android.tracing.perfetto.StartCallbackArguments startCallbackArguments) {
    }

    protected void onFlush(android.tracing.perfetto.FlushCallbackArguments flushCallbackArguments) {
    }

    protected void onStop(android.tracing.perfetto.StopCallbackArguments stopCallbackArguments) {
    }

    @Override // java.lang.AutoCloseable
    public final void close() {
        release();
    }

    public void release() {
        this.mDataSource.releaseDataSourceInstance(this.mInstanceIndex);
    }

    public final int getInstanceIndex() {
        return this.mInstanceIndex;
    }
}
