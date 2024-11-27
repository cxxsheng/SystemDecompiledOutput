package com.android.server.tracing;

/* loaded from: classes2.dex */
public class TracingServiceProxy extends com.android.server.SystemService {
    private static final java.lang.String INTENT_ACTION_NOTIFY_SESSION_STOLEN = "com.android.traceur.NOTIFY_SESSION_STOLEN";
    private static final java.lang.String INTENT_ACTION_NOTIFY_SESSION_STOPPED = "com.android.traceur.NOTIFY_SESSION_STOPPED";
    private static final int MAX_CACHED_REPORTER_SERVICES = 8;
    private static final int MAX_FILE_SIZE_BYTES_TO_PIPE = 1024;
    private static final int REPORT_BEGIN = 1;
    private static final int REPORT_BIND_PERM_INCORRECT = 3;
    private static final int REPORT_SVC_COMM_ERROR = 5;
    private static final int REPORT_SVC_HANDOFF = 2;
    private static final int REPORT_SVC_PERM_MISSING = 4;
    private static final java.lang.String TAG = "TracingServiceProxy";
    private static final java.lang.String TRACING_APP_ACTIVITY = "com.android.traceur.StopTraceService";
    private static final java.lang.String TRACING_APP_PACKAGE_NAME = "com.android.traceur";
    public static final java.lang.String TRACING_SERVICE_PROXY_BINDER_NAME = "tracing.proxy";
    private final android.util.LruCache<android.content.ComponentName, com.android.internal.infra.ServiceConnector<android.os.IMessenger>> mCachedReporterServices;
    private final android.content.Context mContext;
    private final android.content.pm.PackageManager mPackageManager;
    private final android.tracing.ITracingServiceProxy.Stub mTracingServiceProxy;

    public TracingServiceProxy(android.content.Context context) {
        super(context);
        this.mTracingServiceProxy = new android.tracing.ITracingServiceProxy.Stub() { // from class: com.android.server.tracing.TracingServiceProxy.1
            public void notifyTraceSessionEnded(boolean z) {
                com.android.server.tracing.TracingServiceProxy.this.notifyTraceur(z);
            }

            public void reportTrace(@android.annotation.NonNull android.tracing.TraceReportParams traceReportParams) {
                com.android.server.tracing.TracingServiceProxy.this.reportTrace(traceReportParams);
            }
        };
        this.mContext = context;
        this.mPackageManager = context.getPackageManager();
        this.mCachedReporterServices = new android.util.LruCache<>(8);
    }

    @Override // com.android.server.SystemService
    public void onStart() {
        publishBinderService(TRACING_SERVICE_PROXY_BINDER_NAME, this.mTracingServiceProxy);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyTraceur(boolean z) {
        android.content.Intent intent = new android.content.Intent();
        try {
            intent.setClassName(this.mPackageManager.getPackageInfo(TRACING_APP_PACKAGE_NAME, 1048576).packageName, TRACING_APP_ACTIVITY);
            if (z) {
                intent.setAction(INTENT_ACTION_NOTIFY_SESSION_STOLEN);
            } else {
                intent.setAction(INTENT_ACTION_NOTIFY_SESSION_STOPPED);
            }
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                try {
                    this.mContext.startForegroundServiceAsUser(intent, android.os.UserHandle.SYSTEM);
                } catch (java.lang.RuntimeException e) {
                    android.util.Log.e(TAG, "Failed to notifyTraceSessionEnded", e);
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        } catch (android.content.pm.PackageManager.NameNotFoundException e2) {
            android.util.Log.e(TAG, "Failed to locate Traceur", e2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void reportTrace(@android.annotation.NonNull android.tracing.TraceReportParams traceReportParams) {
        com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.TRACING_SERVICE_REPORT_EVENT, 1, traceReportParams.uuidLsb, traceReportParams.uuidMsb);
        android.content.ComponentName componentName = new android.content.ComponentName(traceReportParams.reporterPackageName, traceReportParams.reporterClassName);
        if (!hasBindServicePermission(componentName)) {
            com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.TRACING_SERVICE_REPORT_EVENT, 3, traceReportParams.uuidLsb, traceReportParams.uuidMsb);
            return;
        }
        boolean hasPermission = hasPermission(componentName, "android.permission.DUMP");
        boolean hasPermission2 = hasPermission(componentName, "android.permission.PACKAGE_USAGE_STATS");
        if (!hasPermission || !hasPermission2) {
            com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.TRACING_SERVICE_REPORT_EVENT, 4, traceReportParams.uuidLsb, traceReportParams.uuidMsb);
            return;
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            reportTrace(getOrCreateReporterService(componentName), traceReportParams);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    private void reportTrace(@android.annotation.NonNull com.android.internal.infra.ServiceConnector<android.os.IMessenger> serviceConnector, @android.annotation.NonNull final android.tracing.TraceReportParams traceReportParams) {
        serviceConnector.post(new com.android.internal.infra.ServiceConnector.VoidJob() { // from class: com.android.server.tracing.TracingServiceProxy$$ExternalSyntheticLambda1
            public final void runNoResult(java.lang.Object obj) {
                com.android.server.tracing.TracingServiceProxy.lambda$reportTrace$0(traceReportParams, (android.os.IMessenger) obj);
            }
        }).whenComplete(new java.util.function.BiConsumer() { // from class: com.android.server.tracing.TracingServiceProxy$$ExternalSyntheticLambda2
            @Override // java.util.function.BiConsumer
            public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                com.android.server.tracing.TracingServiceProxy.lambda$reportTrace$1(traceReportParams, (java.lang.Void) obj, (java.lang.Throwable) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$reportTrace$0(android.tracing.TraceReportParams traceReportParams, android.os.IMessenger iMessenger) throws java.lang.Exception {
        if (traceReportParams.usePipeForTesting) {
            android.os.ParcelFileDescriptor[] createPipe = android.os.ParcelFileDescriptor.createPipe();
            android.os.ParcelFileDescriptor.AutoCloseInputStream autoCloseInputStream = new android.os.ParcelFileDescriptor.AutoCloseInputStream(traceReportParams.fd);
            try {
                android.os.ParcelFileDescriptor.AutoCloseOutputStream autoCloseOutputStream = new android.os.ParcelFileDescriptor.AutoCloseOutputStream(createPipe[1]);
                try {
                    byte[] readNBytes = autoCloseInputStream.readNBytes(1024);
                    if (readNBytes.length == 1024) {
                        throw new java.lang.IllegalArgumentException("Trace file too large when |usePipeForTesting| is set.");
                    }
                    autoCloseOutputStream.write(readNBytes);
                    autoCloseOutputStream.close();
                    autoCloseInputStream.close();
                    traceReportParams.fd = createPipe[0];
                } finally {
                }
            } catch (java.lang.Throwable th) {
                try {
                    autoCloseInputStream.close();
                } catch (java.lang.Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        }
        android.os.Message obtain = android.os.Message.obtain();
        obtain.what = 1;
        obtain.obj = traceReportParams;
        iMessenger.send(obtain);
        com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.TRACING_SERVICE_REPORT_EVENT, 2, traceReportParams.uuidLsb, traceReportParams.uuidMsb);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$reportTrace$1(android.tracing.TraceReportParams traceReportParams, java.lang.Void r7, java.lang.Throwable th) {
        if (th != null) {
            com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.TRACING_SERVICE_REPORT_EVENT, 5, traceReportParams.uuidLsb, traceReportParams.uuidMsb);
            android.util.Slog.e(TAG, "Failed to report trace", th);
        }
        try {
            traceReportParams.fd.close();
        } catch (java.io.IOException e) {
        }
    }

    private com.android.internal.infra.ServiceConnector<android.os.IMessenger> getOrCreateReporterService(@android.annotation.NonNull android.content.ComponentName componentName) {
        com.android.internal.infra.ServiceConnector<android.os.IMessenger> serviceConnector = this.mCachedReporterServices.get(componentName);
        if (serviceConnector == null) {
            android.content.Intent intent = new android.content.Intent();
            intent.setComponent(componentName);
            com.android.internal.infra.ServiceConnector<android.os.IMessenger> serviceConnector2 = new com.android.internal.infra.ServiceConnector.Impl<android.os.IMessenger>(this.mContext, intent, 33, this.mContext.getUser().getIdentifier(), new java.util.function.Function() { // from class: com.android.server.tracing.TracingServiceProxy$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final java.lang.Object apply(java.lang.Object obj) {
                    return android.os.IMessenger.Stub.asInterface((android.os.IBinder) obj);
                }
            }) { // from class: com.android.server.tracing.TracingServiceProxy.2
                private static final long DISCONNECT_TIMEOUT_MS = 15000;
                private static final long REQUEST_TIMEOUT_MS = 10000;

                protected long getAutoDisconnectTimeoutMs() {
                    return DISCONNECT_TIMEOUT_MS;
                }

                protected long getRequestTimeoutMs() {
                    return 10000L;
                }
            };
            this.mCachedReporterServices.put(intent.getComponent(), serviceConnector2);
            return serviceConnector2;
        }
        return serviceConnector;
    }

    private boolean hasPermission(@android.annotation.NonNull android.content.ComponentName componentName, @android.annotation.NonNull java.lang.String str) throws java.lang.SecurityException {
        if (this.mPackageManager.checkPermission(str, componentName.getPackageName()) != 0) {
            android.util.Slog.e(TAG, "Trace reporting service " + componentName.toShortString() + " does not have " + str + " permission");
            return false;
        }
        return true;
    }

    private boolean hasBindServicePermission(@android.annotation.NonNull android.content.ComponentName componentName) {
        try {
            android.content.pm.ServiceInfo serviceInfo = this.mPackageManager.getServiceInfo(componentName, 0);
            if (!"android.permission.BIND_TRACE_REPORT_SERVICE".equals(serviceInfo.permission)) {
                android.util.Slog.e(TAG, "Trace reporting service " + componentName.toShortString() + " does not request android.permission.BIND_TRACE_REPORT_SERVICE permission; instead requests " + serviceInfo.permission);
                return false;
            }
            return true;
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            android.util.Slog.e(TAG, "Trace reporting service " + componentName.toShortString() + " does not exist");
            return false;
        }
    }
}
