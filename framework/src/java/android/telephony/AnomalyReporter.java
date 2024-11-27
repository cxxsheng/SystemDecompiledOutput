package android.telephony;

/* loaded from: classes3.dex */
public final class AnomalyReporter {
    private static final java.lang.String KEY_IS_TELEPHONY_ANOMALY_REPORT_ENABLED = "is_telephony_anomaly_report_enabled";
    private static final java.lang.String TAG = "AnomalyReporter";
    private static android.content.Context sContext = null;
    private static java.util.Map<java.util.UUID, java.lang.Integer> sEvents = new java.util.concurrent.ConcurrentHashMap();
    private static java.lang.String sDebugPackageName = null;

    private AnomalyReporter() {
    }

    public static void reportAnomaly(java.util.UUID uuid, java.lang.String str) {
        reportAnomaly(uuid, str, -1);
    }

    public static void reportAnomaly(java.util.UUID uuid, java.lang.String str, int i) {
        com.android.telephony.Rlog.i(TAG, "reportAnomaly: Received anomaly event report with eventId= " + uuid + " and description= " + str);
        if (sContext == null) {
            com.android.telephony.Rlog.w(TAG, "AnomalyReporter not yet initialized, dropping event=" + uuid);
            return;
        }
        com.android.internal.telephony.TelephonyStatsLog.write(461, i, uuid.getLeastSignificantBits(), uuid.getMostSignificantBits());
        try {
            if (android.provider.DeviceConfig.getBoolean(android.app.PropertyInvalidatedCache.MODULE_TELEPHONY, KEY_IS_TELEPHONY_ANOMALY_REPORT_ENABLED, false)) {
                java.lang.Integer valueOf = java.lang.Integer.valueOf(sEvents.containsKey(uuid) ? sEvents.get(uuid).intValue() + 1 : 1);
                sEvents.put(uuid, valueOf);
                if (valueOf.intValue() <= 1 && sDebugPackageName != null) {
                    android.content.Intent intent = new android.content.Intent(android.telephony.TelephonyManager.ACTION_ANOMALY_REPORTED);
                    intent.putExtra(android.telephony.TelephonyManager.EXTRA_ANOMALY_ID, new android.os.ParcelUuid(uuid));
                    if (str != null) {
                        intent.putExtra(android.telephony.TelephonyManager.EXTRA_ANOMALY_DESCRIPTION, str);
                    }
                    intent.setPackage(sDebugPackageName);
                    sContext.sendBroadcast(intent, android.Manifest.permission.READ_PRIVILEGED_PHONE_STATE);
                }
            }
        } catch (java.lang.Exception e) {
            com.android.telephony.Rlog.w(TAG, "Unable to read device config, dropping event=" + uuid);
        }
    }

    public static void initialize(android.content.Context context) {
        java.util.List<android.content.pm.ResolveInfo> queryBroadcastReceivers;
        if (context == null) {
            throw new java.lang.IllegalArgumentException("AnomalyReporter needs a non-null context.");
        }
        context.enforceCallingOrSelfPermission(android.Manifest.permission.MODIFY_PHONE_STATE, "This app does not have privileges to send debug events");
        sContext = context;
        android.content.pm.PackageManager packageManager = sContext.getPackageManager();
        if (packageManager == null || (queryBroadcastReceivers = packageManager.queryBroadcastReceivers(new android.content.Intent(android.telephony.TelephonyManager.ACTION_ANOMALY_REPORTED), android.os.BatteryStats.HistoryItem.MOST_INTERESTING_STATES)) == null || queryBroadcastReceivers.isEmpty()) {
            return;
        }
        if (queryBroadcastReceivers.size() > 1) {
            com.android.telephony.Rlog.e(TAG, "Multiple Anomaly Receivers installed.");
        }
        for (android.content.pm.ResolveInfo resolveInfo : queryBroadcastReceivers) {
            if (resolveInfo.activityInfo == null) {
                com.android.telephony.Rlog.w(TAG, "Found package without activity");
            } else if (packageManager.checkPermission(android.Manifest.permission.READ_PRIVILEGED_PHONE_STATE, resolveInfo.activityInfo.packageName) != 0) {
                com.android.telephony.Rlog.w(TAG, "Found package without proper permissions" + resolveInfo.activityInfo.packageName);
            } else {
                com.android.telephony.Rlog.d(TAG, "Found a valid package " + resolveInfo.activityInfo.packageName);
                sDebugPackageName = resolveInfo.activityInfo.packageName;
                return;
            }
        }
    }

    public static void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        if (sContext == null) {
            return;
        }
        com.android.internal.util.IndentingPrintWriter indentingPrintWriter = new com.android.internal.util.IndentingPrintWriter(printWriter, "  ");
        sContext.enforceCallingOrSelfPermission(android.Manifest.permission.DUMP, "Requires DUMP");
        indentingPrintWriter.println("Initialized=" + (sContext != null ? "Yes" : "No"));
        indentingPrintWriter.println("Debug Package=" + sDebugPackageName);
        indentingPrintWriter.println("Anomaly Counts:");
        indentingPrintWriter.increaseIndent();
        for (java.util.UUID uuid : sEvents.keySet()) {
            indentingPrintWriter.println(uuid + ": " + sEvents.get(uuid));
        }
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.flush();
    }
}
