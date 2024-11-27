package android.tracing.perfetto;

/* loaded from: classes3.dex */
public class Producer {
    private static native void nativePerfettoProducerInit(int i);

    public static void init(android.tracing.perfetto.InitArguments initArguments) {
        nativePerfettoProducerInit(initArguments.backends);
    }
}
