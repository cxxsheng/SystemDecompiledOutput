package android.tracing.perfetto;

/* loaded from: classes3.dex */
public class InitArguments {
    public static final int PERFETTO_BACKEND_IN_PROCESS = 1;
    public static final int PERFETTO_BACKEND_SYSTEM = 2;
    public final int backends;
    public static android.tracing.perfetto.InitArguments DEFAULTS = new android.tracing.perfetto.InitArguments(2);
    public static android.tracing.perfetto.InitArguments TESTING = new android.tracing.perfetto.InitArguments(1);

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface PerfettoBackend {
    }

    public InitArguments(int i) {
        this.backends = i;
    }
}
