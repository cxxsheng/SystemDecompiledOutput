package android.os;

/* loaded from: classes3.dex */
public class TransactionTracker {
    private java.util.Map<java.lang.String, java.lang.Long> mTraces;

    private void resetTraces() {
        synchronized (this) {
            this.mTraces = new java.util.HashMap();
        }
    }

    TransactionTracker() {
        resetTraces();
    }

    public void addTrace(java.lang.Throwable th) {
        java.lang.String stackTraceString = android.util.Log.getStackTraceString(th);
        synchronized (this) {
            if (this.mTraces.containsKey(stackTraceString)) {
                this.mTraces.put(stackTraceString, java.lang.Long.valueOf(this.mTraces.get(stackTraceString).longValue() + 1));
            } else {
                this.mTraces.put(stackTraceString, 1L);
            }
        }
    }

    public void writeTracesToFile(android.os.ParcelFileDescriptor parcelFileDescriptor) {
        if (this.mTraces.isEmpty()) {
            return;
        }
        com.android.internal.util.FastPrintWriter fastPrintWriter = new com.android.internal.util.FastPrintWriter(new java.io.FileOutputStream(parcelFileDescriptor.getFileDescriptor()));
        synchronized (this) {
            for (java.lang.String str : this.mTraces.keySet()) {
                fastPrintWriter.println("Count: " + this.mTraces.get(str));
                fastPrintWriter.println("Trace: " + str);
                fastPrintWriter.println();
            }
        }
        fastPrintWriter.flush();
    }

    public void clearTraces() {
        resetTraces();
    }
}
