package android.service.autofill.augmented;

/* loaded from: classes3.dex */
public final class Helper {
    private static final com.android.internal.logging.MetricsLogger sMetricsLogger = new com.android.internal.logging.MetricsLogger();

    public static void logResponse(int i, java.lang.String str, android.content.ComponentName componentName, int i2, long j) {
        sMetricsLogger.write(new android.metrics.LogMaker(com.android.internal.logging.nano.MetricsProto.MetricsEvent.AUTOFILL_AUGMENTED_RESPONSE).setType(i).setComponentName(new android.content.ComponentName(componentName.getPackageName(), "")).addTaggedData(com.android.internal.logging.nano.MetricsProto.MetricsEvent.FIELD_AUTOFILL_SESSION_ID, java.lang.Integer.valueOf(i2)).addTaggedData(908, str).addTaggedData(com.android.internal.logging.nano.MetricsProto.MetricsEvent.FIELD_AUTOFILL_DURATION, java.lang.Long.valueOf(j)));
    }

    private Helper() {
        throw new java.lang.UnsupportedOperationException("contains only static methods");
    }
}
