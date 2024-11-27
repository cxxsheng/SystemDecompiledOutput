package com.android.server.profcollect;

/* loaded from: classes2.dex */
public final class ProfcollectForwardingService extends com.android.server.SystemService {
    private static final java.lang.String INTENT_UPLOAD_PROFILES = "com.android.server.profcollect.UPLOAD_PROFILES";
    private static com.android.server.profcollect.ProfcollectForwardingService sSelfService;
    private final com.android.server.profcollect.ProfcollectForwardingService.AppLaunchObserver mAppLaunchObserver;
    private final android.content.BroadcastReceiver mBroadcastReceiver;
    private final android.os.Handler mHandler;
    private com.android.server.profcollect.IProfCollectd mIProfcollect;
    private com.android.server.profcollect.IProviderStatusCallback mProviderStatusCallback;
    public static final java.lang.String LOG_TAG = "ProfcollectForwardingService";
    private static final boolean DEBUG = android.util.Log.isLoggable(LOG_TAG, 3);
    private static final long BG_PROCESS_INTERVAL = java.util.concurrent.TimeUnit.HOURS.toMillis(4);

    public ProfcollectForwardingService(android.content.Context context) {
        super(context);
        this.mHandler = new com.android.server.profcollect.ProfcollectForwardingService.ProfcollectdHandler(com.android.server.IoThread.getHandler().getLooper());
        this.mProviderStatusCallback = new com.android.server.profcollect.IProviderStatusCallback.Stub() { // from class: com.android.server.profcollect.ProfcollectForwardingService.1
            @Override // com.android.server.profcollect.IProviderStatusCallback
            public void onProviderReady() {
                com.android.server.profcollect.ProfcollectForwardingService.this.mHandler.sendEmptyMessage(1);
            }
        };
        this.mBroadcastReceiver = new android.content.BroadcastReceiver() { // from class: com.android.server.profcollect.ProfcollectForwardingService.2
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context2, android.content.Intent intent) {
                if (com.android.server.profcollect.ProfcollectForwardingService.INTENT_UPLOAD_PROFILES.equals(intent.getAction())) {
                    android.util.Log.d(com.android.server.profcollect.ProfcollectForwardingService.LOG_TAG, "Received broadcast to pack and upload reports");
                    com.android.server.profcollect.ProfcollectForwardingService.this.packAndUploadReport();
                }
            }
        };
        this.mAppLaunchObserver = new com.android.server.profcollect.ProfcollectForwardingService.AppLaunchObserver();
        if (sSelfService != null) {
            throw new java.lang.AssertionError("only one service instance allowed");
        }
        sSelfService = this;
        android.content.IntentFilter intentFilter = new android.content.IntentFilter();
        intentFilter.addAction(INTENT_UPLOAD_PROFILES);
        context.registerReceiver(this.mBroadcastReceiver, intentFilter, 4);
    }

    public static boolean enabled() {
        return android.provider.DeviceConfig.getBoolean("profcollect_native_boot", com.android.server.timezonedetector.ServiceConfigAccessor.PROVIDER_MODE_ENABLED, false) || android.os.SystemProperties.getBoolean("persist.profcollectd.enabled_override", false);
    }

    @Override // com.android.server.SystemService
    public void onStart() {
        if (DEBUG) {
            android.util.Log.d(LOG_TAG, "Profcollect forwarding service start");
        }
        connectNativeService();
    }

    @Override // com.android.server.SystemService
    public void onBootPhase(int i) {
        if (i != 1000 || this.mIProfcollect == null) {
            return;
        }
        com.android.internal.os.BackgroundThread.get().getThreadHandler().post(new java.lang.Runnable() { // from class: com.android.server.profcollect.ProfcollectForwardingService$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.profcollect.ProfcollectForwardingService.this.lambda$onBootPhase$0();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onBootPhase$0() {
        if (serviceHasSupportedTraceProvider()) {
            registerProviderStatusCallback();
        }
    }

    private void registerProviderStatusCallback() {
        if (this.mIProfcollect == null) {
            return;
        }
        try {
            this.mIProfcollect.registerProviderStatusCallback(this.mProviderStatusCallback);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(LOG_TAG, "Failed to register provider status callback: " + e.getMessage());
        }
    }

    private boolean serviceHasSupportedTraceProvider() {
        if (this.mIProfcollect == null) {
            return false;
        }
        try {
            return !this.mIProfcollect.get_supported_provider().isEmpty();
        } catch (android.os.RemoteException e) {
            android.util.Log.e(LOG_TAG, "Failed to get supported provider: " + e.getMessage());
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean tryConnectNativeService() {
        if (connectNativeService()) {
            return true;
        }
        this.mHandler.sendEmptyMessageDelayed(0, 5000L);
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean connectNativeService() {
        try {
            com.android.server.profcollect.IProfCollectd asInterface = com.android.server.profcollect.IProfCollectd.Stub.asInterface(android.os.ServiceManager.getServiceOrThrow("profcollectd"));
            asInterface.asBinder().linkToDeath(new com.android.server.profcollect.ProfcollectForwardingService.ProfcollectdDeathRecipient(), 0);
            this.mIProfcollect = asInterface;
            return true;
        } catch (android.os.ServiceManager.ServiceNotFoundException | android.os.RemoteException e) {
            android.util.Log.w(LOG_TAG, "Failed to connect profcollectd binder service.");
            return false;
        }
    }

    private class ProfcollectdHandler extends android.os.Handler {
        public static final int MESSAGE_BINDER_CONNECT = 0;
        public static final int MESSAGE_REGISTER_SCHEDULERS = 1;

        public ProfcollectdHandler(android.os.Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            switch (message.what) {
                case 0:
                    com.android.server.profcollect.ProfcollectForwardingService.this.connectNativeService();
                    return;
                case 1:
                    com.android.server.profcollect.ProfcollectForwardingService.this.registerObservers();
                    com.android.server.profcollect.ProfcollectForwardingService.ProfcollectBGJobService.schedule(com.android.server.profcollect.ProfcollectForwardingService.this.getContext());
                    return;
                default:
                    throw new java.lang.AssertionError("Unknown message: " + message);
            }
        }
    }

    private class ProfcollectdDeathRecipient implements android.os.IBinder.DeathRecipient {
        private ProfcollectdDeathRecipient() {
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            android.util.Log.w(com.android.server.profcollect.ProfcollectForwardingService.LOG_TAG, "profcollectd has died");
            com.android.server.profcollect.ProfcollectForwardingService.this.mIProfcollect = null;
            com.android.server.profcollect.ProfcollectForwardingService.this.tryConnectNativeService();
        }
    }

    public static class ProfcollectBGJobService extends android.app.job.JobService {
        private static final int JOB_IDLE_PROCESS = 260817;
        private static final android.content.ComponentName JOB_SERVICE_NAME = new android.content.ComponentName(com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME, com.android.server.profcollect.ProfcollectForwardingService.ProfcollectBGJobService.class.getName());

        public static void schedule(android.content.Context context) {
            ((android.app.job.JobScheduler) context.getSystemService(android.app.job.JobScheduler.class)).schedule(new android.app.job.JobInfo.Builder(JOB_IDLE_PROCESS, JOB_SERVICE_NAME).setRequiresDeviceIdle(true).setRequiresCharging(true).setPeriodic(com.android.server.profcollect.ProfcollectForwardingService.BG_PROCESS_INTERVAL).setPriority(100).build());
        }

        @Override // android.app.job.JobService
        public boolean onStartJob(android.app.job.JobParameters jobParameters) {
            if (com.android.server.profcollect.ProfcollectForwardingService.DEBUG) {
                android.util.Log.d(com.android.server.profcollect.ProfcollectForwardingService.LOG_TAG, "Starting background process job");
            }
            com.android.internal.os.BackgroundThread.get().getThreadHandler().post(new java.lang.Runnable() { // from class: com.android.server.profcollect.ProfcollectForwardingService$ProfcollectBGJobService$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.profcollect.ProfcollectForwardingService.ProfcollectBGJobService.lambda$onStartJob$0();
                }
            });
            return true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$onStartJob$0() {
            try {
                if (com.android.server.profcollect.ProfcollectForwardingService.sSelfService.mIProfcollect == null) {
                    return;
                }
                com.android.server.profcollect.ProfcollectForwardingService.sSelfService.mIProfcollect.process();
            } catch (android.os.RemoteException e) {
                android.util.Log.e(com.android.server.profcollect.ProfcollectForwardingService.LOG_TAG, "Failed to process profiles in background: " + e.getMessage());
            }
        }

        @Override // android.app.job.JobService
        public boolean onStopJob(android.app.job.JobParameters jobParameters) {
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void registerObservers() {
        com.android.internal.os.BackgroundThread.get().getThreadHandler().post(new java.lang.Runnable() { // from class: com.android.server.profcollect.ProfcollectForwardingService$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.profcollect.ProfcollectForwardingService.this.lambda$registerObservers$1();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$registerObservers$1() {
        registerAppLaunchObserver();
        registerDex2oatObserver();
        registerOTAObserver();
    }

    private void registerAppLaunchObserver() {
        ((com.android.server.wm.ActivityTaskManagerInternal) com.android.server.LocalServices.getService(com.android.server.wm.ActivityTaskManagerInternal.class)).getLaunchObserverRegistry().registerLaunchObserver(this.mAppLaunchObserver);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void traceOnAppStart(java.lang.String str) {
        if (this.mIProfcollect == null) {
            return;
        }
        if (java.util.concurrent.ThreadLocalRandom.current().nextInt(100) < android.provider.DeviceConfig.getInt("profcollect_native_boot", "applaunch_trace_freq", 2)) {
            if (DEBUG) {
                android.util.Log.d(LOG_TAG, "Tracing on app launch event: " + str);
            }
            com.android.internal.os.BackgroundThread.get().getThreadHandler().post(new java.lang.Runnable() { // from class: com.android.server.profcollect.ProfcollectForwardingService$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.profcollect.ProfcollectForwardingService.this.lambda$traceOnAppStart$2();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$traceOnAppStart$2() {
        try {
            this.mIProfcollect.trace_once("applaunch");
        } catch (android.os.RemoteException e) {
            android.util.Log.e(LOG_TAG, "Failed to initiate trace: " + e.getMessage());
        }
    }

    private class AppLaunchObserver extends com.android.server.wm.ActivityMetricsLaunchObserver {
        private AppLaunchObserver() {
        }

        @Override // com.android.server.wm.ActivityMetricsLaunchObserver
        public void onIntentStarted(android.content.Intent intent, long j) {
            com.android.server.profcollect.ProfcollectForwardingService.this.traceOnAppStart(intent.getPackage());
        }
    }

    private void registerDex2oatObserver() {
        com.android.server.art.ArtManagerLocal artManagerLocal = (com.android.server.art.ArtManagerLocal) com.android.server.LocalManagerRegistry.getManager(com.android.server.art.ArtManagerLocal.class);
        if (artManagerLocal == null) {
            android.util.Log.w(LOG_TAG, "Couldn't get ArtManagerLocal");
        } else {
            artManagerLocal.setBatchDexoptStartCallback(new com.android.server.SystemServerInitThreadPool$$ExternalSyntheticLambda0(), new com.android.server.art.ArtManagerLocal.BatchDexoptStartCallback() { // from class: com.android.server.profcollect.ProfcollectForwardingService$$ExternalSyntheticLambda2
                public final void onBatchDexoptStart(com.android.server.pm.PackageManagerLocal.FilteredSnapshot filteredSnapshot, java.lang.String str, java.util.List list, com.android.server.art.model.BatchDexoptParams.Builder builder, android.os.CancellationSignal cancellationSignal) {
                    com.android.server.profcollect.ProfcollectForwardingService.this.lambda$registerDex2oatObserver$3(filteredSnapshot, str, list, builder, cancellationSignal);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$registerDex2oatObserver$3(com.android.server.pm.PackageManagerLocal.FilteredSnapshot filteredSnapshot, java.lang.String str, java.util.List list, com.android.server.art.model.BatchDexoptParams.Builder builder, android.os.CancellationSignal cancellationSignal) {
        traceOnDex2oatStart();
    }

    private void traceOnDex2oatStart() {
        if (this.mIProfcollect == null) {
            return;
        }
        if (java.util.concurrent.ThreadLocalRandom.current().nextInt(100) < android.provider.DeviceConfig.getInt("profcollect_native_boot", "dex2oat_trace_freq", 25)) {
            if (DEBUG) {
                android.util.Log.d(LOG_TAG, "Tracing on dex2oat event");
            }
            com.android.internal.os.BackgroundThread.get().getThreadHandler().post(new java.lang.Runnable() { // from class: com.android.server.profcollect.ProfcollectForwardingService$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.profcollect.ProfcollectForwardingService.this.lambda$traceOnDex2oatStart$4();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$traceOnDex2oatStart$4() {
        try {
            java.lang.Thread.sleep(1000L);
            this.mIProfcollect.trace_once("dex2oat");
        } catch (android.os.RemoteException | java.lang.InterruptedException e) {
            android.util.Log.e(LOG_TAG, "Failed to initiate trace: " + e.getMessage());
        }
    }

    private void registerOTAObserver() {
        new android.os.UpdateEngine().bind(new android.os.UpdateEngineCallback() { // from class: com.android.server.profcollect.ProfcollectForwardingService.3
            public void onStatusUpdate(int i, float f) {
                if (com.android.server.profcollect.ProfcollectForwardingService.DEBUG) {
                    android.util.Log.d(com.android.server.profcollect.ProfcollectForwardingService.LOG_TAG, "Received OTA status update, status: " + i + ", percent: " + f);
                }
                if (i == 6) {
                    com.android.server.profcollect.ProfcollectForwardingService.this.packAndUploadReport();
                }
            }

            public void onPayloadApplicationComplete(int i) {
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void packAndUploadReport() {
        if (this.mIProfcollect == null) {
            return;
        }
        final android.content.Context context = getContext();
        com.android.internal.os.BackgroundThread.get().getThreadHandler().post(new java.lang.Runnable() { // from class: com.android.server.profcollect.ProfcollectForwardingService$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.profcollect.ProfcollectForwardingService.this.lambda$packAndUploadReport$5(context);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$packAndUploadReport$5(android.content.Context context) {
        int i;
        try {
            try {
                i = android.provider.Settings.Global.getInt(context.getContentResolver(), "multi_cb");
            } catch (android.provider.Settings.SettingNotFoundException e) {
                android.util.Log.i(LOG_TAG, "Usage setting not found: " + e.getMessage());
                i = -1;
            }
            java.lang.String str = this.mIProfcollect.report(i) + ".zip";
            if (!context.getResources().getBoolean(android.R.bool.config_powerDecoupleInteractiveModeFromDisplay)) {
                android.util.Log.i(LOG_TAG, "Upload is not enabled.");
            } else {
                context.sendBroadcast(new android.content.Intent().setPackage(com.android.server.vibrator.VibratorManagerService.VibratorManagerShellCommand.SHELL_PACKAGE_NAME).setAction("com.android.shell.action.PROFCOLLECT_UPLOAD").putExtra("filename", str));
            }
        } catch (android.os.RemoteException e2) {
            android.util.Log.e(LOG_TAG, "Failed to upload report: " + e2.getMessage());
        }
    }
}
