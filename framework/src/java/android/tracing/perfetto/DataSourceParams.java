package android.tracing.perfetto;

/* loaded from: classes3.dex */
public class DataSourceParams {
    public static android.tracing.perfetto.DataSourceParams DEFAULTS = new android.tracing.perfetto.DataSourceParams(0);
    public static final int PERFETTO_DS_BUFFER_EXHAUSTED_POLICY_DROP = 0;
    public static final int PERFETTO_DS_BUFFER_EXHAUSTED_POLICY_STALL_AND_ABORT = 1;
    public final int bufferExhaustedPolicy;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface PerfettoDsBufferExhausted {
    }

    public DataSourceParams(int i) {
        this.bufferExhaustedPolicy = i;
    }
}
