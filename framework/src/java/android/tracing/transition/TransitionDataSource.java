package android.tracing.transition;

/* loaded from: classes3.dex */
public class TransitionDataSource extends android.tracing.perfetto.DataSource<android.tracing.perfetto.DataSourceInstance, java.lang.Void, java.lang.Void> {
    public static java.lang.String DATA_SOURCE_NAME = "com.android.wm.shell.transition";
    private final java.lang.Runnable mOnFlushStaticCallback;
    private final java.lang.Runnable mOnStartStaticCallback;
    private final java.lang.Runnable mOnStopStaticCallback;

    public TransitionDataSource(java.lang.Runnable runnable, java.lang.Runnable runnable2, java.lang.Runnable runnable3) {
        super(DATA_SOURCE_NAME);
        this.mOnStartStaticCallback = runnable;
        this.mOnFlushStaticCallback = runnable2;
        this.mOnStopStaticCallback = runnable3;
    }

    @Override // android.tracing.perfetto.DataSource
    public android.tracing.perfetto.DataSourceInstance createInstance(android.util.proto.ProtoInputStream protoInputStream, int i) {
        return new android.tracing.perfetto.DataSourceInstance(this, i) { // from class: android.tracing.transition.TransitionDataSource.1
            @Override // android.tracing.perfetto.DataSourceInstance
            protected void onStart(android.tracing.perfetto.StartCallbackArguments startCallbackArguments) {
                android.tracing.transition.TransitionDataSource.this.mOnStartStaticCallback.run();
            }

            @Override // android.tracing.perfetto.DataSourceInstance
            protected void onFlush(android.tracing.perfetto.FlushCallbackArguments flushCallbackArguments) {
                android.tracing.transition.TransitionDataSource.this.mOnFlushStaticCallback.run();
            }

            @Override // android.tracing.perfetto.DataSourceInstance
            protected void onStop(android.tracing.perfetto.StopCallbackArguments stopCallbackArguments) {
                android.tracing.transition.TransitionDataSource.this.mOnStopStaticCallback.run();
            }
        };
    }
}
