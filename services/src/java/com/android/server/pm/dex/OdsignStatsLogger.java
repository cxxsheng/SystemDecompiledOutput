package com.android.server.pm.dex;

/* loaded from: classes2.dex */
public class OdsignStatsLogger {
    private static final java.lang.String COMPOS_METRIC_NAME = "comp_os_artifacts_check_record";
    private static final java.lang.String METRICS_FILE = "/data/misc/odsign/metrics/odsign-metrics.txt";
    private static final java.lang.String ODSIGN_METRIC_NAME = "odsign_record";
    private static final java.lang.String TAG = "OdsignStatsLogger";

    public static void triggerStatsWrite() {
        com.android.internal.os.BackgroundThread.getExecutor().execute(new java.lang.Runnable() { // from class: com.android.server.pm.dex.OdsignStatsLogger$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.pm.dex.OdsignStatsLogger.writeStats();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static void writeStats() {
        char c;
        try {
            java.lang.String readFileAsString = libcore.io.IoUtils.readFileAsString(METRICS_FILE);
            if (!new java.io.File(METRICS_FILE).delete()) {
                android.util.Slog.w(TAG, "Failed to delete metrics file");
            }
            for (java.lang.String str : readFileAsString.split("\n")) {
                java.lang.String[] split = str.split(" ");
                if (str.isEmpty() || split.length < 1) {
                    android.util.Slog.w(TAG, "Empty metrics line");
                } else {
                    java.lang.String str2 = split[0];
                    switch (str2.hashCode()) {
                        case 890271774:
                            if (str2.equals(ODSIGN_METRIC_NAME)) {
                                c = 1;
                                break;
                            }
                            c = 65535;
                            break;
                        case 1023928721:
                            if (str2.equals(COMPOS_METRIC_NAME)) {
                                c = 0;
                                break;
                            }
                            c = 65535;
                            break;
                        default:
                            c = 65535;
                            break;
                    }
                    switch (c) {
                        case 0:
                            if (split.length == 4) {
                                com.android.internal.art.ArtStatsLog.write(com.android.internal.art.ArtStatsLog.EARLY_BOOT_COMP_OS_ARTIFACTS_CHECK_REPORTED, split[1].equals("1"), split[2].equals("1"), split[3].equals("1"));
                                break;
                            } else {
                                android.util.Slog.w(TAG, "Malformed CompOS metrics line '" + str + "'");
                                break;
                            }
                        case 1:
                            if (split.length == 2) {
                                try {
                                    com.android.internal.art.ArtStatsLog.write(com.android.internal.art.ArtStatsLog.ODSIGN_REPORTED, java.lang.Integer.parseInt(split[1]));
                                    break;
                                } catch (java.lang.NumberFormatException e) {
                                    android.util.Slog.w(TAG, "Malformed odsign metrics line '" + str + "'");
                                    break;
                                }
                            } else {
                                android.util.Slog.w(TAG, "Malformed odsign metrics line '" + str + "'");
                                break;
                            }
                        default:
                            android.util.Slog.w(TAG, "Malformed metrics line '" + str + "'");
                            break;
                    }
                }
            }
        } catch (java.io.FileNotFoundException e2) {
        } catch (java.io.IOException e3) {
            android.util.Slog.w(TAG, "Reading metrics file failed", e3);
        }
    }
}
