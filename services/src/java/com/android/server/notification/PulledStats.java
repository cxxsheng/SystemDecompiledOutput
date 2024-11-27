package com.android.server.notification;

/* loaded from: classes2.dex */
public class PulledStats {
    static final java.lang.String TAG = "PulledStats";
    private long mTimePeriodEndMs;
    private final long mTimePeriodStartMs;
    private java.util.List<java.lang.String> mUndecoratedPackageNames = new java.util.ArrayList();

    public PulledStats(long j) {
        this.mTimePeriodStartMs = j;
        this.mTimePeriodEndMs = j;
    }

    android.os.ParcelFileDescriptor toParcelFileDescriptor(final int i) throws java.io.IOException {
        final android.os.ParcelFileDescriptor[] createPipe = android.os.ParcelFileDescriptor.createPipe();
        switch (i) {
            case 1:
                new java.lang.Thread("NotificationManager pulled metric output") { // from class: com.android.server.notification.PulledStats.1
                    @Override // java.lang.Thread, java.lang.Runnable
                    public void run() {
                        try {
                            android.os.ParcelFileDescriptor.AutoCloseOutputStream autoCloseOutputStream = new android.os.ParcelFileDescriptor.AutoCloseOutputStream(createPipe[1]);
                            android.util.proto.ProtoOutputStream protoOutputStream = new android.util.proto.ProtoOutputStream(autoCloseOutputStream);
                            com.android.server.notification.PulledStats.this.writeToProto(i, protoOutputStream);
                            protoOutputStream.flush();
                            autoCloseOutputStream.close();
                        } catch (java.io.IOException e) {
                            android.util.Slog.w(com.android.server.notification.PulledStats.TAG, "Failure writing pipe", e);
                        }
                    }
                }.start();
                break;
            default:
                android.util.Slog.w(TAG, "Unknown pulled stats request: " + i);
                break;
        }
        return createPipe[0];
    }

    public long endTimeMs() {
        return this.mTimePeriodEndMs;
    }

    public void dump(int i, java.io.PrintWriter printWriter, com.android.server.notification.NotificationManagerService.DumpFilter dumpFilter) {
        switch (i) {
            case 1:
                printWriter.print("  Packages with undecordated notifications (");
                printWriter.print(this.mTimePeriodStartMs);
                printWriter.print(" - ");
                printWriter.print(this.mTimePeriodEndMs);
                printWriter.println("):");
                if (this.mUndecoratedPackageNames.size() == 0) {
                    printWriter.println("    none");
                    break;
                } else {
                    for (java.lang.String str : this.mUndecoratedPackageNames) {
                        if (!dumpFilter.filtered || str.equals(dumpFilter.pkgFilter)) {
                            printWriter.println("    " + str);
                        }
                    }
                    break;
                }
                break;
            default:
                printWriter.println("Unknown pulled stats request: " + i);
                break;
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void writeToProto(int i, android.util.proto.ProtoOutputStream protoOutputStream) {
        switch (i) {
            case 1:
                for (java.lang.String str : this.mUndecoratedPackageNames) {
                    long start = protoOutputStream.start(2246267895809L);
                    protoOutputStream.write(1138166333441L, str);
                    protoOutputStream.end(start);
                }
                break;
            default:
                android.util.Slog.w(TAG, "Unknown pulled stats request: " + i);
                break;
        }
    }

    public void addUndecoratedPackage(java.lang.String str, long j) {
        this.mUndecoratedPackageNames.add(str);
        this.mTimePeriodEndMs = java.lang.Math.max(this.mTimePeriodEndMs, j);
    }
}
