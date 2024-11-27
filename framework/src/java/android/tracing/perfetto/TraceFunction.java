package android.tracing.perfetto;

/* loaded from: classes3.dex */
public interface TraceFunction<DataSourceInstanceType extends android.tracing.perfetto.DataSourceInstance, TlsStateType, IncrementalStateType> {
    void trace(android.tracing.perfetto.TracingContext<DataSourceInstanceType, TlsStateType, IncrementalStateType> tracingContext) throws java.io.IOException;
}
