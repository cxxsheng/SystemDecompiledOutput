package com.android.server.pm;

/* loaded from: classes2.dex */
public class DynamicCodeLoggingService extends android.app.job.JobService {
    private static final int AUDIT_AVC = 1400;
    private static final int AUDIT_WATCHING_JOB_ID = 203142925;
    private static final java.lang.String AVC_PREFIX = "type=1400 ";
    private static final boolean DEBUG = false;
    private static final int IDLE_LOGGING_JOB_ID = 2030028;
    private static final java.lang.String TAG = com.android.server.pm.DynamicCodeLoggingService.class.getName();
    private static final long IDLE_LOGGING_PERIOD_MILLIS = java.util.concurrent.TimeUnit.DAYS.toMillis(1);
    private static final long AUDIT_WATCHING_PERIOD_MILLIS = java.util.concurrent.TimeUnit.HOURS.toMillis(2);
    private static final java.util.regex.Pattern EXECUTE_NATIVE_AUDIT_PATTERN = java.util.regex.Pattern.compile(".*\\bavc: +granted +\\{ execute(?:_no_trans|) \\} .*\\bpath=(?:\"([^\" ]*)\"|([0-9A-F]+)) .*\\bscontext=u:r:untrusted_app(?:_25|_27)?:.*\\btcontext=u:object_r:app_data_file:.*\\btclass=file\\b.*");
    private volatile boolean mIdleLoggingStopRequested = false;
    private volatile boolean mAuditWatchingStopRequested = false;

    public static void schedule(android.content.Context context) {
        android.content.ComponentName componentName = new android.content.ComponentName(com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME, com.android.server.pm.DynamicCodeLoggingService.class.getName());
        android.app.job.JobScheduler jobScheduler = (android.app.job.JobScheduler) context.getSystemService("jobscheduler");
        jobScheduler.schedule(new android.app.job.JobInfo.Builder(IDLE_LOGGING_JOB_ID, componentName).setRequiresDeviceIdle(true).setRequiresCharging(true).setPeriodic(IDLE_LOGGING_PERIOD_MILLIS).build());
        jobScheduler.schedule(new android.app.job.JobInfo.Builder(AUDIT_WATCHING_JOB_ID, componentName).setRequiresDeviceIdle(true).setRequiresBatteryNotLow(true).setPeriodic(AUDIT_WATCHING_PERIOD_MILLIS).build());
    }

    @Override // android.app.job.JobService
    public boolean onStartJob(android.app.job.JobParameters jobParameters) {
        switch (jobParameters.getJobId()) {
            case IDLE_LOGGING_JOB_ID /* 2030028 */:
                this.mIdleLoggingStopRequested = false;
                new com.android.server.pm.DynamicCodeLoggingService.IdleLoggingThread(jobParameters).start();
                return true;
            case AUDIT_WATCHING_JOB_ID /* 203142925 */:
                this.mAuditWatchingStopRequested = false;
                new com.android.server.pm.DynamicCodeLoggingService.AuditWatchingThread(jobParameters).start();
                return true;
            default:
                return false;
        }
    }

    @Override // android.app.job.JobService
    public boolean onStopJob(android.app.job.JobParameters jobParameters) {
        switch (jobParameters.getJobId()) {
            case IDLE_LOGGING_JOB_ID /* 2030028 */:
                this.mIdleLoggingStopRequested = true;
                break;
            case AUDIT_WATCHING_JOB_ID /* 203142925 */:
                this.mAuditWatchingStopRequested = true;
                break;
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static com.android.server.pm.dex.DynamicCodeLogger getDynamicCodeLogger() {
        return ((android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class)).getDynamicCodeLogger();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void syncDataFromArtService(com.android.server.pm.dex.DynamicCodeLogger dynamicCodeLogger) {
        com.android.server.art.DexUseManagerLocal dexUseManagerLocal = com.android.server.pm.DexOptHelper.getDexUseManagerLocal();
        if (dexUseManagerLocal == null) {
            return;
        }
        com.android.server.pm.PackageManagerLocal packageManagerLocal = (com.android.server.pm.PackageManagerLocal) com.android.server.LocalManagerRegistry.getManager(com.android.server.pm.PackageManagerLocal.class);
        java.util.Objects.requireNonNull(packageManagerLocal);
        com.android.server.pm.PackageManagerLocal.UnfilteredSnapshot withUnfilteredSnapshot = packageManagerLocal.withUnfilteredSnapshot();
        try {
            for (java.lang.String str : withUnfilteredSnapshot.getPackageStates().keySet()) {
                for (com.android.server.art.model.DexContainerFileUseInfo dexContainerFileUseInfo : dexUseManagerLocal.getSecondaryDexContainerFileUseInfo(str)) {
                    java.util.Iterator it = dexContainerFileUseInfo.getLoadingPackages().iterator();
                    while (it.hasNext()) {
                        dynamicCodeLogger.recordDex(dexContainerFileUseInfo.getUserHandle().getIdentifier(), dexContainerFileUseInfo.getDexContainerFile(), str, (java.lang.String) it.next());
                    }
                }
            }
            withUnfilteredSnapshot.close();
        } catch (java.lang.Throwable th) {
            if (withUnfilteredSnapshot != null) {
                try {
                    withUnfilteredSnapshot.close();
                } catch (java.lang.Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    private class IdleLoggingThread extends java.lang.Thread {
        private final android.app.job.JobParameters mParams;

        IdleLoggingThread(android.app.job.JobParameters jobParameters) {
            super("DynamicCodeLoggingService_IdleLoggingJob");
            this.mParams = jobParameters;
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            com.android.server.pm.dex.DynamicCodeLogger dynamicCodeLogger = com.android.server.pm.DynamicCodeLoggingService.getDynamicCodeLogger();
            com.android.server.pm.DynamicCodeLoggingService.syncDataFromArtService(dynamicCodeLogger);
            for (java.lang.String str : dynamicCodeLogger.getAllPackagesWithDynamicCodeLoading()) {
                if (com.android.server.pm.DynamicCodeLoggingService.this.mIdleLoggingStopRequested) {
                    android.util.Log.w(com.android.server.pm.DynamicCodeLoggingService.TAG, "Stopping IdleLoggingJob run at scheduler request");
                    return;
                }
                dynamicCodeLogger.logDynamicCodeLoading(str);
            }
            com.android.server.pm.DynamicCodeLoggingService.this.jobFinished(this.mParams, false);
        }
    }

    private class AuditWatchingThread extends java.lang.Thread {
        private final android.app.job.JobParameters mParams;

        AuditWatchingThread(android.app.job.JobParameters jobParameters) {
            super("DynamicCodeLoggingService_AuditWatchingJob");
            this.mParams = jobParameters;
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            if (processAuditEvents()) {
                com.android.server.pm.DynamicCodeLoggingService.this.jobFinished(this.mParams, false);
            }
        }

        private boolean processAuditEvents() {
            try {
                int[] iArr = {android.util.EventLog.getTagCode("auditd")};
                if (iArr[0] == -1) {
                    return true;
                }
                com.android.server.pm.dex.DynamicCodeLogger dynamicCodeLogger = com.android.server.pm.DynamicCodeLoggingService.getDynamicCodeLogger();
                java.util.ArrayList arrayList = new java.util.ArrayList();
                android.util.EventLog.readEvents(iArr, arrayList);
                java.util.regex.Matcher matcher = com.android.server.pm.DynamicCodeLoggingService.EXECUTE_NATIVE_AUDIT_PATTERN.matcher("");
                for (int i = 0; i < arrayList.size(); i++) {
                    if (com.android.server.pm.DynamicCodeLoggingService.this.mAuditWatchingStopRequested) {
                        android.util.Log.w(com.android.server.pm.DynamicCodeLoggingService.TAG, "Stopping AuditWatchingJob run at scheduler request");
                        return false;
                    }
                    android.util.EventLog.Event event = (android.util.EventLog.Event) arrayList.get(i);
                    int uid = event.getUid();
                    if (android.os.Process.isApplicationUid(uid)) {
                        java.lang.Object data = event.getData();
                        if (data instanceof java.lang.String) {
                            java.lang.String str = (java.lang.String) data;
                            if (str.startsWith(com.android.server.pm.DynamicCodeLoggingService.AVC_PREFIX)) {
                                matcher.reset(str);
                                if (matcher.matches()) {
                                    java.lang.String group = matcher.group(1);
                                    if (group == null) {
                                        group = com.android.server.pm.DynamicCodeLoggingService.unhex(matcher.group(2));
                                    }
                                    dynamicCodeLogger.recordNative(uid, group);
                                }
                            }
                        }
                    }
                }
                return true;
            } catch (java.lang.Exception e) {
                android.util.Log.e(com.android.server.pm.DynamicCodeLoggingService.TAG, "AuditWatchingJob failed", e);
                return true;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static java.lang.String unhex(java.lang.String str) {
        if (str == null || str.length() == 0) {
            return "";
        }
        return new java.lang.String(libcore.util.HexEncoding.decode(str, false));
    }
}
