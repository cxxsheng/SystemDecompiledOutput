package com.android.server.soundtrigger;

/* loaded from: classes2.dex */
public class SoundTriggerService extends com.android.server.SystemService {
    private static final boolean DEBUG = true;
    private static final int SESSION_MAX_EVENT_SIZE = 128;
    private static final java.lang.String TAG = "SoundTriggerService";
    private android.app.AppOpsManager mAppOpsManager;
    private final android.content.Context mContext;
    private com.android.server.soundtrigger.SoundTriggerDbHelper mDbHelper;
    private final java.util.Deque<com.android.server.utils.EventLogger> mDetachedSessionEventLoggers;
    private final com.android.server.utils.EventLogger mDeviceEventLogger;
    private final com.android.server.soundtrigger.DeviceStateHandler mDeviceStateHandler;
    private final java.util.concurrent.Executor mDeviceStateHandlerExecutor;
    private final com.android.server.soundtrigger.SoundTriggerService.LocalSoundTriggerService mLocalSoundTriggerService;
    private final java.lang.Object mLock;
    private android.media.soundtrigger_middleware.ISoundTriggerMiddlewareService mMiddlewareService;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.ArrayMap<java.lang.String, com.android.server.soundtrigger.SoundTriggerService.NumOps> mNumOpsPerPackage;
    private android.content.pm.PackageManager mPackageManager;
    private com.android.server.soundtrigger.PhoneCallStateHandler mPhoneCallStateHandler;
    private final com.android.server.utils.EventLogger mServiceEventLogger;
    private final com.android.server.soundtrigger.SoundTriggerService.SoundTriggerServiceStub mServiceStub;
    private final java.util.Set<com.android.server.utils.EventLogger> mSessionEventLoggers;
    private java.util.concurrent.atomic.AtomicInteger mSessionIdCounter;
    private final com.android.server.soundtrigger.SoundTriggerService.SoundModelStatTracker mSoundModelStatTracker;

    class SoundModelStatTracker {
        private final java.util.TreeMap<java.util.UUID, com.android.server.soundtrigger.SoundTriggerService.SoundModelStatTracker.SoundModelStat> mModelStats = new java.util.TreeMap<>();

        private class SoundModelStat {
            long mStartCount = 0;
            long mTotalTimeMsec = 0;
            long mLastStartTimestampMsec = 0;
            long mLastStopTimestampMsec = 0;
            boolean mIsStarted = false;

            SoundModelStat() {
            }
        }

        SoundModelStatTracker() {
        }

        public synchronized void onStart(java.util.UUID uuid) {
            try {
                com.android.server.soundtrigger.SoundTriggerService.SoundModelStatTracker.SoundModelStat soundModelStat = this.mModelStats.get(uuid);
                if (soundModelStat == null) {
                    soundModelStat = new com.android.server.soundtrigger.SoundTriggerService.SoundModelStatTracker.SoundModelStat();
                    this.mModelStats.put(uuid, soundModelStat);
                }
                if (soundModelStat.mIsStarted) {
                    android.util.Slog.w(com.android.server.soundtrigger.SoundTriggerService.TAG, "error onStart(): Model " + uuid + " already started");
                    return;
                }
                soundModelStat.mStartCount++;
                soundModelStat.mLastStartTimestampMsec = android.os.SystemClock.elapsedRealtime();
                soundModelStat.mIsStarted = true;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }

        public synchronized void onStop(java.util.UUID uuid) {
            com.android.server.soundtrigger.SoundTriggerService.SoundModelStatTracker.SoundModelStat soundModelStat = this.mModelStats.get(uuid);
            if (soundModelStat == null) {
                android.util.Slog.i(com.android.server.soundtrigger.SoundTriggerService.TAG, "error onStop(): Model " + uuid + " has no stats available");
                return;
            }
            if (!soundModelStat.mIsStarted) {
                android.util.Slog.w(com.android.server.soundtrigger.SoundTriggerService.TAG, "error onStop(): Model " + uuid + " already stopped");
                return;
            }
            soundModelStat.mLastStopTimestampMsec = android.os.SystemClock.elapsedRealtime();
            soundModelStat.mTotalTimeMsec += soundModelStat.mLastStopTimestampMsec - soundModelStat.mLastStartTimestampMsec;
            soundModelStat.mIsStarted = false;
        }

        public synchronized void dump(java.io.PrintWriter printWriter) {
            try {
                long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
                printWriter.println("Model Stats:");
                for (java.util.Map.Entry<java.util.UUID, com.android.server.soundtrigger.SoundTriggerService.SoundModelStatTracker.SoundModelStat> entry : this.mModelStats.entrySet()) {
                    java.util.UUID key = entry.getKey();
                    com.android.server.soundtrigger.SoundTriggerService.SoundModelStatTracker.SoundModelStat value = entry.getValue();
                    long j = value.mTotalTimeMsec;
                    if (value.mIsStarted) {
                        j += elapsedRealtime - value.mLastStartTimestampMsec;
                    }
                    printWriter.println(key + ", total_time(msec)=" + j + ", total_count=" + value.mStartCount + ", last_start=" + value.mLastStartTimestampMsec + ", last_stop=" + value.mLastStopTimestampMsec);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public SoundTriggerService(android.content.Context context) {
        super(context);
        this.mLock = new java.lang.Object();
        this.mServiceEventLogger = new com.android.server.utils.EventLogger(256, "Service");
        this.mDeviceEventLogger = new com.android.server.utils.EventLogger(256, "Device Event");
        this.mSessionEventLoggers = java.util.concurrent.ConcurrentHashMap.newKeySet(4);
        this.mDetachedSessionEventLoggers = new java.util.concurrent.LinkedBlockingDeque(4);
        this.mSessionIdCounter = new java.util.concurrent.atomic.AtomicInteger(0);
        this.mNumOpsPerPackage = new android.util.ArrayMap<>();
        this.mDeviceStateHandlerExecutor = java.util.concurrent.Executors.newSingleThreadExecutor();
        this.mContext = context;
        this.mServiceStub = new com.android.server.soundtrigger.SoundTriggerService.SoundTriggerServiceStub();
        this.mLocalSoundTriggerService = new com.android.server.soundtrigger.SoundTriggerService.LocalSoundTriggerService(context);
        this.mSoundModelStatTracker = new com.android.server.soundtrigger.SoundTriggerService.SoundModelStatTracker();
        this.mDeviceStateHandler = new com.android.server.soundtrigger.DeviceStateHandler(this.mDeviceStateHandlerExecutor, this.mDeviceEventLogger);
    }

    @Override // com.android.server.SystemService
    public void onStart() {
        publishBinderService("soundtrigger", this.mServiceStub);
        publishLocalService(com.android.server.SoundTriggerInternal.class, this.mLocalSoundTriggerService);
    }

    private boolean hasCalling() {
        return this.mContext.getPackageManager().hasSystemFeature("android.hardware.telephony.calling");
    }

    @Override // com.android.server.SystemService
    public void onBootPhase(int i) {
        android.util.Slog.d(TAG, "onBootPhase: " + i + " : " + isSafeMode());
        if (600 == i) {
            this.mDbHelper = new com.android.server.soundtrigger.SoundTriggerDbHelper(this.mContext);
            this.mAppOpsManager = (android.app.AppOpsManager) this.mContext.getSystemService(android.app.AppOpsManager.class);
            this.mPackageManager = this.mContext.getPackageManager();
            final android.os.PowerManager powerManager = (android.os.PowerManager) this.mContext.getSystemService(android.os.PowerManager.class);
            this.mContext.registerReceiver(new android.content.BroadcastReceiver() { // from class: com.android.server.soundtrigger.SoundTriggerService.1
                @Override // android.content.BroadcastReceiver
                public void onReceive(android.content.Context context, android.content.Intent intent) {
                    if (!"android.os.action.POWER_SAVE_MODE_CHANGED".equals(intent.getAction())) {
                        return;
                    }
                    com.android.server.soundtrigger.SoundTriggerService.this.mDeviceStateHandler.onPowerModeChanged(powerManager.getSoundTriggerPowerSaveMode());
                }
            }, new android.content.IntentFilter("android.os.action.POWER_SAVE_MODE_CHANGED"));
            this.mDeviceStateHandler.onPowerModeChanged(powerManager.getSoundTriggerPowerSaveMode());
            if (hasCalling()) {
                this.mPhoneCallStateHandler = new com.android.server.soundtrigger.PhoneCallStateHandler((android.telephony.SubscriptionManager) this.mContext.getSystemService(android.telephony.SubscriptionManager.class), (android.telephony.TelephonyManager) this.mContext.getSystemService(android.telephony.TelephonyManager.class), this.mDeviceStateHandler);
            }
        }
        this.mMiddlewareService = android.media.soundtrigger_middleware.ISoundTriggerMiddlewareService.Stub.asInterface(android.os.ServiceManager.waitForService("soundtrigger_middleware"));
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: listUnderlyingModuleProperties, reason: merged with bridge method [inline-methods] */
    public java.util.List<android.hardware.soundtrigger.SoundTrigger.ModuleProperties> lambda$newSoundTriggerHelper$2(android.media.permission.Identity identity) {
        android.media.permission.Identity identity2 = new android.media.permission.Identity();
        identity2.packageName = android.app.ActivityThread.currentOpPackageName();
        try {
            return (java.util.List) java.util.Arrays.stream(this.mMiddlewareService.listModulesAsMiddleman(identity2, identity)).map(new java.util.function.Function() { // from class: com.android.server.soundtrigger.SoundTriggerService$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final java.lang.Object apply(java.lang.Object obj) {
                    android.hardware.soundtrigger.SoundTrigger.ModuleProperties aidl2apiModuleDescriptor;
                    aidl2apiModuleDescriptor = android.hardware.soundtrigger.ConversionUtil.aidl2apiModuleDescriptor((android.media.soundtrigger_middleware.SoundTriggerModuleDescriptor) obj);
                    return aidl2apiModuleDescriptor;
                }
            }).collect(java.util.stream.Collectors.toList());
        } catch (android.os.RemoteException e) {
            throw new android.os.ServiceSpecificException(android.hardware.soundtrigger.SoundTrigger.STATUS_DEAD_OBJECT);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public com.android.server.soundtrigger.SoundTriggerHelper newSoundTriggerHelper(android.hardware.soundtrigger.SoundTrigger.ModuleProperties moduleProperties, com.android.server.utils.EventLogger eventLogger) {
        return newSoundTriggerHelper(moduleProperties, eventLogger, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public com.android.server.soundtrigger.SoundTriggerHelper newSoundTriggerHelper(android.hardware.soundtrigger.SoundTrigger.ModuleProperties moduleProperties, com.android.server.utils.EventLogger eventLogger, final boolean z) {
        int i;
        final android.media.permission.Identity identity = new android.media.permission.Identity();
        identity.packageName = android.app.ActivityThread.currentOpPackageName();
        final android.media.permission.Identity nonNull = android.media.permission.IdentityContext.getNonNull();
        java.util.List<android.hardware.soundtrigger.SoundTrigger.ModuleProperties> lambda$newSoundTriggerHelper$2 = lambda$newSoundTriggerHelper$2(nonNull);
        if (moduleProperties == null) {
            i = -1;
        } else {
            i = moduleProperties.getId();
        }
        if (i != -1 && !lambda$newSoundTriggerHelper$2.contains(moduleProperties)) {
            throw new java.lang.IllegalArgumentException("Invalid module properties");
        }
        final int i2 = i;
        return new com.android.server.soundtrigger.SoundTriggerHelper(this.mContext, eventLogger, new java.util.function.Function() { // from class: com.android.server.soundtrigger.SoundTriggerService$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                android.hardware.soundtrigger.SoundTriggerModule lambda$newSoundTriggerHelper$1;
                lambda$newSoundTriggerHelper$1 = com.android.server.soundtrigger.SoundTriggerService.this.lambda$newSoundTriggerHelper$1(i2, identity, nonNull, z, (android.hardware.soundtrigger.SoundTrigger.StatusListener) obj);
                return lambda$newSoundTriggerHelper$1;
            }
        }, i, new java.util.function.Supplier() { // from class: com.android.server.soundtrigger.SoundTriggerService$$ExternalSyntheticLambda2
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                java.util.List lambda$newSoundTriggerHelper$22;
                lambda$newSoundTriggerHelper$22 = com.android.server.soundtrigger.SoundTriggerService.this.lambda$newSoundTriggerHelper$2(nonNull);
                return lambda$newSoundTriggerHelper$22;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ android.hardware.soundtrigger.SoundTriggerModule lambda$newSoundTriggerHelper$1(int i, android.media.permission.Identity identity, android.media.permission.Identity identity2, boolean z, android.hardware.soundtrigger.SoundTrigger.StatusListener statusListener) {
        return new android.hardware.soundtrigger.SoundTriggerModule(this.mMiddlewareService, i, statusListener, android.os.Looper.getMainLooper(), identity, identity2, z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void detachSessionLogger(com.android.server.utils.EventLogger eventLogger) {
        if (!this.mSessionEventLoggers.remove(eventLogger)) {
            return;
        }
        while (!this.mDetachedSessionEventLoggers.offerFirst(eventLogger)) {
            this.mDetachedSessionEventLoggers.pollLast();
        }
    }

    class MyAppOpsListener implements android.app.AppOpsManager.OnOpChangedListener {
        private final java.util.function.Consumer<java.lang.Boolean> mOnOpModeChanged;
        private final android.media.permission.Identity mOriginatorIdentity;

        MyAppOpsListener(android.media.permission.Identity identity, java.util.function.Consumer<java.lang.Boolean> consumer) {
            java.util.Objects.requireNonNull(identity);
            this.mOriginatorIdentity = identity;
            java.util.Objects.requireNonNull(consumer);
            this.mOnOpModeChanged = consumer;
            try {
                int packageUid = com.android.server.soundtrigger.SoundTriggerService.this.mPackageManager.getPackageUid(this.mOriginatorIdentity.packageName, android.content.pm.PackageManager.PackageInfoFlags.of(4194304L));
                if (!android.os.UserHandle.isSameApp(packageUid, this.mOriginatorIdentity.uid)) {
                    throw new java.lang.SecurityException("Uid " + this.mOriginatorIdentity.uid + " attempted to spoof package name " + this.mOriginatorIdentity.packageName + " with uid: " + packageUid);
                }
            } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                throw new java.lang.SecurityException("Package name not found: " + this.mOriginatorIdentity.packageName);
            }
        }

        @Override // android.app.AppOpsManager.OnOpChangedListener
        public void onOpChanged(java.lang.String str, java.lang.String str2) {
            if (java.util.Objects.equals(str, "android:record_audio")) {
                this.mOnOpModeChanged.accept(java.lang.Boolean.valueOf(com.android.server.soundtrigger.SoundTriggerService.this.mAppOpsManager.checkOpNoThrow("android:record_audio", this.mOriginatorIdentity.uid, this.mOriginatorIdentity.packageName) == 0));
            }
        }

        void forceOpChangeRefresh() {
            onOpChanged("android:record_audio", this.mOriginatorIdentity.packageName);
        }
    }

    class SoundTriggerServiceStub extends com.android.internal.app.ISoundTriggerService.Stub {
        SoundTriggerServiceStub() {
        }

        public com.android.internal.app.ISoundTriggerSession attachAsOriginator(@android.annotation.NonNull android.media.permission.Identity identity, @android.annotation.NonNull android.hardware.soundtrigger.SoundTrigger.ModuleProperties moduleProperties, @android.annotation.NonNull android.os.IBinder iBinder) {
            int andIncrement = com.android.server.soundtrigger.SoundTriggerService.this.mSessionIdCounter.getAndIncrement();
            com.android.server.soundtrigger.SoundTriggerService.this.mServiceEventLogger.enqueue(new com.android.server.soundtrigger.SoundTriggerEvent.ServiceEvent(com.android.server.soundtrigger.SoundTriggerEvent.ServiceEvent.Type.ATTACH, identity.packageName + "#" + andIncrement));
            android.media.permission.SafeCloseable establishIdentityDirect = android.media.permission.PermissionUtil.establishIdentityDirect(identity);
            try {
                java.lang.StringBuilder sb = new java.lang.StringBuilder();
                sb.append("SoundTriggerSessionLogs for package: ");
                java.lang.String str = identity.packageName;
                java.util.Objects.requireNonNull(str);
                java.lang.String str2 = str;
                sb.append(str);
                sb.append("#");
                sb.append(andIncrement);
                sb.append(" - ");
                sb.append(identity.uid);
                sb.append("|");
                sb.append(identity.pid);
                com.android.server.utils.EventLogger eventLogger = new com.android.server.utils.EventLogger(128, sb.toString());
                com.android.server.soundtrigger.SoundTriggerService.SoundTriggerSessionStub soundTriggerSessionStub = com.android.server.soundtrigger.SoundTriggerService.this.new SoundTriggerSessionStub(iBinder, com.android.server.soundtrigger.SoundTriggerService.this.newSoundTriggerHelper(moduleProperties, eventLogger), eventLogger);
                if (establishIdentityDirect != null) {
                    establishIdentityDirect.close();
                }
                return soundTriggerSessionStub;
            } catch (java.lang.Throwable th) {
                if (establishIdentityDirect != null) {
                    try {
                        establishIdentityDirect.close();
                    } catch (java.lang.Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }

        public com.android.internal.app.ISoundTriggerSession attachAsMiddleman(@android.annotation.NonNull android.media.permission.Identity identity, @android.annotation.NonNull android.media.permission.Identity identity2, @android.annotation.NonNull android.hardware.soundtrigger.SoundTrigger.ModuleProperties moduleProperties, @android.annotation.NonNull android.os.IBinder iBinder) {
            int andIncrement = com.android.server.soundtrigger.SoundTriggerService.this.mSessionIdCounter.getAndIncrement();
            com.android.server.soundtrigger.SoundTriggerService.this.mServiceEventLogger.enqueue(new com.android.server.soundtrigger.SoundTriggerEvent.ServiceEvent(com.android.server.soundtrigger.SoundTriggerEvent.ServiceEvent.Type.ATTACH, identity.packageName + "#" + andIncrement));
            android.media.permission.SafeCloseable establishIdentityIndirect = android.media.permission.PermissionUtil.establishIdentityIndirect(com.android.server.soundtrigger.SoundTriggerService.this.mContext, "android.permission.SOUNDTRIGGER_DELEGATE_IDENTITY", identity2, identity);
            try {
                java.lang.StringBuilder sb = new java.lang.StringBuilder();
                sb.append("SoundTriggerSessionLogs for package: ");
                java.lang.String str = identity.packageName;
                java.util.Objects.requireNonNull(str);
                java.lang.String str2 = str;
                sb.append(str);
                sb.append("#");
                sb.append(andIncrement);
                sb.append(" - ");
                sb.append(identity.uid);
                sb.append("|");
                sb.append(identity.pid);
                com.android.server.utils.EventLogger eventLogger = new com.android.server.utils.EventLogger(128, sb.toString());
                com.android.server.soundtrigger.SoundTriggerService.SoundTriggerSessionStub soundTriggerSessionStub = com.android.server.soundtrigger.SoundTriggerService.this.new SoundTriggerSessionStub(iBinder, com.android.server.soundtrigger.SoundTriggerService.this.newSoundTriggerHelper(moduleProperties, eventLogger), eventLogger);
                if (establishIdentityIndirect != null) {
                    establishIdentityIndirect.close();
                }
                return soundTriggerSessionStub;
            } catch (java.lang.Throwable th) {
                if (establishIdentityIndirect != null) {
                    try {
                        establishIdentityIndirect.close();
                    } catch (java.lang.Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }

        public java.util.List<android.hardware.soundtrigger.SoundTrigger.ModuleProperties> listModuleProperties(@android.annotation.NonNull android.media.permission.Identity identity) {
            com.android.server.soundtrigger.SoundTriggerService.this.mServiceEventLogger.enqueue(new com.android.server.soundtrigger.SoundTriggerEvent.ServiceEvent(com.android.server.soundtrigger.SoundTriggerEvent.ServiceEvent.Type.LIST_MODULE, identity.packageName));
            android.media.permission.SafeCloseable establishIdentityDirect = android.media.permission.PermissionUtil.establishIdentityDirect(identity);
            try {
                java.util.List<android.hardware.soundtrigger.SoundTrigger.ModuleProperties> lambda$newSoundTriggerHelper$2 = com.android.server.soundtrigger.SoundTriggerService.this.lambda$newSoundTriggerHelper$2(identity);
                if (establishIdentityDirect != null) {
                    establishIdentityDirect.close();
                }
                return lambda$newSoundTriggerHelper$2;
            } catch (java.lang.Throwable th) {
                if (establishIdentityDirect != null) {
                    try {
                        establishIdentityDirect.close();
                    } catch (java.lang.Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }

        public void attachInjection(@android.annotation.NonNull android.media.soundtrigger_middleware.ISoundTriggerInjection iSoundTriggerInjection) {
            if (android.content.PermissionChecker.checkCallingPermissionForPreflight(com.android.server.soundtrigger.SoundTriggerService.this.mContext, "android.permission.MANAGE_SOUND_TRIGGER", (java.lang.String) null) != 0) {
                throw new java.lang.SecurityException();
            }
            try {
                android.media.soundtrigger_middleware.ISoundTriggerMiddlewareService.Stub.asInterface(android.os.ServiceManager.waitForService("soundtrigger_middleware")).attachFakeHalInjection(iSoundTriggerInjection);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public void setInPhoneCallState(boolean z) {
            android.util.Slog.i(com.android.server.soundtrigger.SoundTriggerService.TAG, "Overriding phone call state: " + z);
            com.android.server.soundtrigger.SoundTriggerService.this.mDeviceStateHandler.onPhoneCallStateChanged(z);
        }

        public void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
            if (com.android.internal.util.DumpUtils.checkDumpPermission(com.android.server.soundtrigger.SoundTriggerService.this.mContext, com.android.server.soundtrigger.SoundTriggerService.TAG, printWriter)) {
                printWriter.println("##Service-Wide logs:");
                com.android.server.soundtrigger.SoundTriggerService.this.mServiceEventLogger.dump(printWriter, "  ");
                printWriter.println("\n##Device state logs:");
                com.android.server.soundtrigger.SoundTriggerService.this.mDeviceStateHandler.dump(printWriter);
                com.android.server.soundtrigger.SoundTriggerService.this.mDeviceEventLogger.dump(printWriter, "  ");
                printWriter.println("\n##Active Session dumps:\n");
                java.util.Iterator it = com.android.server.soundtrigger.SoundTriggerService.this.mSessionEventLoggers.iterator();
                while (it.hasNext()) {
                    ((com.android.server.utils.EventLogger) it.next()).dump(printWriter, "  ");
                    printWriter.println("");
                }
                printWriter.println("##Detached Session dumps:\n");
                java.util.Iterator it2 = com.android.server.soundtrigger.SoundTriggerService.this.mDetachedSessionEventLoggers.iterator();
                while (it2.hasNext()) {
                    ((com.android.server.utils.EventLogger) it2.next()).dump(printWriter, "  ");
                    printWriter.println("");
                }
                printWriter.println("##Enrolled db dump:\n");
                com.android.server.soundtrigger.SoundTriggerService.this.mDbHelper.dump(printWriter);
                printWriter.println("\n##Sound Model Stats dump:\n");
                com.android.server.soundtrigger.SoundTriggerService.this.mSoundModelStatTracker.dump(printWriter);
            }
        }
    }

    class SoundTriggerSessionStub extends com.android.internal.app.ISoundTriggerSession.Stub {
        private final com.android.server.soundtrigger.SoundTriggerService.MyAppOpsListener mAppOpsListener;
        private final android.os.IBinder mClient;
        private final com.android.server.utils.EventLogger mEventLogger;
        private final com.android.server.soundtrigger.DeviceStateHandler.DeviceStateListener mListener;
        private final com.android.server.soundtrigger.SoundTriggerHelper mSoundTriggerHelper;
        private final java.util.TreeMap<java.util.UUID, android.hardware.soundtrigger.SoundTrigger.SoundModel> mLoadedModels = new java.util.TreeMap<>();
        private final java.lang.Object mCallbacksLock = new java.lang.Object();
        private final java.util.TreeMap<java.util.UUID, android.hardware.soundtrigger.IRecognitionStatusCallback> mCallbacks = new java.util.TreeMap<>();
        private final android.media.permission.Identity mOriginatorIdentity = android.media.permission.IdentityContext.getNonNull();

        SoundTriggerSessionStub(@android.annotation.NonNull android.os.IBinder iBinder, com.android.server.soundtrigger.SoundTriggerHelper soundTriggerHelper, com.android.server.utils.EventLogger eventLogger) {
            this.mSoundTriggerHelper = soundTriggerHelper;
            this.mClient = iBinder;
            this.mEventLogger = eventLogger;
            com.android.server.soundtrigger.SoundTriggerService.this.mSessionEventLoggers.add(this.mEventLogger);
            try {
                this.mClient.linkToDeath(new android.os.IBinder.DeathRecipient() { // from class: com.android.server.soundtrigger.SoundTriggerService$SoundTriggerSessionStub$$ExternalSyntheticLambda0
                    @Override // android.os.IBinder.DeathRecipient
                    public final void binderDied() {
                        com.android.server.soundtrigger.SoundTriggerService.SoundTriggerSessionStub.this.lambda$new$0();
                    }
                }, 0);
            } catch (android.os.RemoteException e) {
                lambda$new$0();
            }
            this.mListener = new com.android.server.soundtrigger.DeviceStateHandler.DeviceStateListener() { // from class: com.android.server.soundtrigger.SoundTriggerService$SoundTriggerSessionStub$$ExternalSyntheticLambda1
                @Override // com.android.server.soundtrigger.DeviceStateHandler.DeviceStateListener
                public final void onSoundTriggerDeviceStateUpdate(com.android.server.soundtrigger.DeviceStateHandler.SoundTriggerDeviceState soundTriggerDeviceState) {
                    com.android.server.soundtrigger.SoundTriggerService.SoundTriggerSessionStub.this.lambda$new$1(soundTriggerDeviceState);
                }
            };
            android.media.permission.Identity identity = this.mOriginatorIdentity;
            com.android.server.soundtrigger.SoundTriggerHelper soundTriggerHelper2 = this.mSoundTriggerHelper;
            java.util.Objects.requireNonNull(soundTriggerHelper2);
            this.mAppOpsListener = com.android.server.soundtrigger.SoundTriggerService.this.new MyAppOpsListener(identity, new com.android.server.soundtrigger.SoundTriggerService$LocalSoundTriggerService$SessionImpl$$ExternalSyntheticLambda2(soundTriggerHelper2));
            this.mAppOpsListener.forceOpChangeRefresh();
            com.android.server.soundtrigger.SoundTriggerService.this.mAppOpsManager.startWatchingMode("android:record_audio", this.mOriginatorIdentity.packageName, 1, this.mAppOpsListener);
            com.android.server.soundtrigger.SoundTriggerService.this.mDeviceStateHandler.registerListener(this.mListener);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$new$1(com.android.server.soundtrigger.DeviceStateHandler.SoundTriggerDeviceState soundTriggerDeviceState) {
            this.mSoundTriggerHelper.onDeviceStateChanged(soundTriggerDeviceState);
        }

        public int startRecognition(android.hardware.soundtrigger.SoundTrigger.GenericSoundModel genericSoundModel, android.hardware.soundtrigger.IRecognitionStatusCallback iRecognitionStatusCallback, android.hardware.soundtrigger.SoundTrigger.RecognitionConfig recognitionConfig, boolean z) {
            this.mEventLogger.enqueue(new com.android.server.soundtrigger.SoundTriggerEvent.SessionEvent(com.android.server.soundtrigger.SoundTriggerEvent.SessionEvent.Type.START_RECOGNITION, getUuid((android.hardware.soundtrigger.SoundTrigger.SoundModel) genericSoundModel)));
            android.media.permission.SafeCloseable create = android.media.permission.ClearCallingIdentityContext.create();
            try {
                enforceCallingPermission("android.permission.MANAGE_SOUND_TRIGGER");
                if (genericSoundModel == null) {
                    this.mEventLogger.enqueue(new com.android.server.soundtrigger.SoundTriggerEvent.SessionEvent(com.android.server.soundtrigger.SoundTriggerEvent.SessionEvent.Type.START_RECOGNITION, getUuid((android.hardware.soundtrigger.SoundTrigger.SoundModel) genericSoundModel), "Invalid sound model").printLog(2, com.android.server.soundtrigger.SoundTriggerService.TAG));
                    if (create != null) {
                        create.close();
                        return Integer.MIN_VALUE;
                    }
                    return Integer.MIN_VALUE;
                }
                if (z) {
                    enforceCallingPermission("android.permission.SOUND_TRIGGER_RUN_IN_BATTERY_SAVER");
                }
                int startGenericRecognition = this.mSoundTriggerHelper.startGenericRecognition(genericSoundModel.getUuid(), genericSoundModel, iRecognitionStatusCallback, recognitionConfig, z);
                if (startGenericRecognition == 0) {
                    com.android.server.soundtrigger.SoundTriggerService.this.mSoundModelStatTracker.onStart(genericSoundModel.getUuid());
                }
                if (create != null) {
                    create.close();
                }
                return startGenericRecognition;
            } catch (java.lang.Throwable th) {
                if (create != null) {
                    try {
                        create.close();
                    } catch (java.lang.Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }

        public int stopRecognition(android.os.ParcelUuid parcelUuid, android.hardware.soundtrigger.IRecognitionStatusCallback iRecognitionStatusCallback) {
            this.mEventLogger.enqueue(new com.android.server.soundtrigger.SoundTriggerEvent.SessionEvent(com.android.server.soundtrigger.SoundTriggerEvent.SessionEvent.Type.STOP_RECOGNITION, getUuid(parcelUuid)));
            android.media.permission.SafeCloseable create = android.media.permission.ClearCallingIdentityContext.create();
            try {
                enforceCallingPermission("android.permission.MANAGE_SOUND_TRIGGER");
                int stopGenericRecognition = this.mSoundTriggerHelper.stopGenericRecognition(parcelUuid.getUuid(), iRecognitionStatusCallback);
                if (stopGenericRecognition == 0) {
                    com.android.server.soundtrigger.SoundTriggerService.this.mSoundModelStatTracker.onStop(parcelUuid.getUuid());
                }
                if (create != null) {
                    create.close();
                }
                return stopGenericRecognition;
            } catch (java.lang.Throwable th) {
                if (create != null) {
                    try {
                        create.close();
                    } catch (java.lang.Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }

        public android.hardware.soundtrigger.SoundTrigger.GenericSoundModel getSoundModel(android.os.ParcelUuid parcelUuid) {
            android.media.permission.SafeCloseable create = android.media.permission.ClearCallingIdentityContext.create();
            try {
                enforceCallingPermission("android.permission.MANAGE_SOUND_TRIGGER");
                android.hardware.soundtrigger.SoundTrigger.GenericSoundModel genericSoundModel = com.android.server.soundtrigger.SoundTriggerService.this.mDbHelper.getGenericSoundModel(parcelUuid.getUuid());
                if (create != null) {
                    create.close();
                }
                return genericSoundModel;
            } catch (java.lang.Throwable th) {
                if (create != null) {
                    try {
                        create.close();
                    } catch (java.lang.Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }

        public void updateSoundModel(android.hardware.soundtrigger.SoundTrigger.GenericSoundModel genericSoundModel) {
            this.mEventLogger.enqueue(new com.android.server.soundtrigger.SoundTriggerEvent.SessionEvent(com.android.server.soundtrigger.SoundTriggerEvent.SessionEvent.Type.UPDATE_MODEL, getUuid((android.hardware.soundtrigger.SoundTrigger.SoundModel) genericSoundModel)));
            android.media.permission.SafeCloseable create = android.media.permission.ClearCallingIdentityContext.create();
            try {
                enforceCallingPermission("android.permission.MANAGE_SOUND_TRIGGER");
                com.android.server.soundtrigger.SoundTriggerService.this.mDbHelper.updateGenericSoundModel(genericSoundModel);
                if (create != null) {
                    create.close();
                }
            } catch (java.lang.Throwable th) {
                if (create != null) {
                    try {
                        create.close();
                    } catch (java.lang.Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }

        public void deleteSoundModel(android.os.ParcelUuid parcelUuid) {
            this.mEventLogger.enqueue(new com.android.server.soundtrigger.SoundTriggerEvent.SessionEvent(com.android.server.soundtrigger.SoundTriggerEvent.SessionEvent.Type.DELETE_MODEL, getUuid(parcelUuid)));
            android.media.permission.SafeCloseable create = android.media.permission.ClearCallingIdentityContext.create();
            try {
                enforceCallingPermission("android.permission.MANAGE_SOUND_TRIGGER");
                this.mSoundTriggerHelper.unloadGenericSoundModel(parcelUuid.getUuid());
                com.android.server.soundtrigger.SoundTriggerService.this.mSoundModelStatTracker.onStop(parcelUuid.getUuid());
                com.android.server.soundtrigger.SoundTriggerService.this.mDbHelper.deleteGenericSoundModel(parcelUuid.getUuid());
                if (create != null) {
                    create.close();
                }
            } catch (java.lang.Throwable th) {
                if (create != null) {
                    try {
                        create.close();
                    } catch (java.lang.Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }

        public int loadGenericSoundModel(android.hardware.soundtrigger.SoundTrigger.GenericSoundModel genericSoundModel) {
            this.mEventLogger.enqueue(new com.android.server.soundtrigger.SoundTriggerEvent.SessionEvent(com.android.server.soundtrigger.SoundTriggerEvent.SessionEvent.Type.LOAD_MODEL, getUuid((android.hardware.soundtrigger.SoundTrigger.SoundModel) genericSoundModel)));
            android.media.permission.SafeCloseable create = android.media.permission.ClearCallingIdentityContext.create();
            try {
                enforceCallingPermission("android.permission.MANAGE_SOUND_TRIGGER");
                if (genericSoundModel == null || genericSoundModel.getUuid() == null) {
                    this.mEventLogger.enqueue(new com.android.server.soundtrigger.SoundTriggerEvent.SessionEvent(com.android.server.soundtrigger.SoundTriggerEvent.SessionEvent.Type.LOAD_MODEL, getUuid((android.hardware.soundtrigger.SoundTrigger.SoundModel) genericSoundModel), "Invalid sound model").printLog(2, com.android.server.soundtrigger.SoundTriggerService.TAG));
                    if (create != null) {
                        create.close();
                        return Integer.MIN_VALUE;
                    }
                    return Integer.MIN_VALUE;
                }
                synchronized (com.android.server.soundtrigger.SoundTriggerService.this.mLock) {
                    try {
                        android.hardware.soundtrigger.SoundTrigger.SoundModel soundModel = this.mLoadedModels.get(genericSoundModel.getUuid());
                        if (soundModel != null && !soundModel.equals(genericSoundModel)) {
                            this.mSoundTriggerHelper.unloadGenericSoundModel(genericSoundModel.getUuid());
                            synchronized (this.mCallbacksLock) {
                                this.mCallbacks.remove(genericSoundModel.getUuid());
                            }
                        }
                        this.mLoadedModels.put(genericSoundModel.getUuid(), genericSoundModel);
                    } finally {
                    }
                }
                if (create != null) {
                    create.close();
                    return 0;
                }
                return 0;
            } catch (java.lang.Throwable th) {
                if (create != null) {
                    try {
                        create.close();
                    } catch (java.lang.Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }

        public int loadKeyphraseSoundModel(android.hardware.soundtrigger.SoundTrigger.KeyphraseSoundModel keyphraseSoundModel) {
            this.mEventLogger.enqueue(new com.android.server.soundtrigger.SoundTriggerEvent.SessionEvent(com.android.server.soundtrigger.SoundTriggerEvent.SessionEvent.Type.LOAD_MODEL, getUuid((android.hardware.soundtrigger.SoundTrigger.SoundModel) keyphraseSoundModel)));
            android.media.permission.SafeCloseable create = android.media.permission.ClearCallingIdentityContext.create();
            try {
                enforceCallingPermission("android.permission.MANAGE_SOUND_TRIGGER");
                if (keyphraseSoundModel == null || keyphraseSoundModel.getUuid() == null) {
                    this.mEventLogger.enqueue(new com.android.server.soundtrigger.SoundTriggerEvent.SessionEvent(com.android.server.soundtrigger.SoundTriggerEvent.SessionEvent.Type.LOAD_MODEL, getUuid((android.hardware.soundtrigger.SoundTrigger.SoundModel) keyphraseSoundModel), "Invalid sound model").printLog(2, com.android.server.soundtrigger.SoundTriggerService.TAG));
                    if (create != null) {
                        create.close();
                    }
                    return Integer.MIN_VALUE;
                }
                if (keyphraseSoundModel.getKeyphrases() == null || keyphraseSoundModel.getKeyphrases().length != 1) {
                    this.mEventLogger.enqueue(new com.android.server.soundtrigger.SoundTriggerEvent.SessionEvent(com.android.server.soundtrigger.SoundTriggerEvent.SessionEvent.Type.LOAD_MODEL, getUuid((android.hardware.soundtrigger.SoundTrigger.SoundModel) keyphraseSoundModel), "Only one keyphrase supported").printLog(2, com.android.server.soundtrigger.SoundTriggerService.TAG));
                    if (create != null) {
                        create.close();
                    }
                    return Integer.MIN_VALUE;
                }
                synchronized (com.android.server.soundtrigger.SoundTriggerService.this.mLock) {
                    try {
                        android.hardware.soundtrigger.SoundTrigger.SoundModel soundModel = this.mLoadedModels.get(keyphraseSoundModel.getUuid());
                        if (soundModel != null && !soundModel.equals(keyphraseSoundModel)) {
                            this.mSoundTriggerHelper.unloadKeyphraseSoundModel(keyphraseSoundModel.getKeyphrases()[0].getId());
                            synchronized (this.mCallbacksLock) {
                                this.mCallbacks.remove(keyphraseSoundModel.getUuid());
                            }
                        }
                        this.mLoadedModels.put(keyphraseSoundModel.getUuid(), keyphraseSoundModel);
                    } finally {
                    }
                }
                if (create != null) {
                    create.close();
                }
                return 0;
            } catch (java.lang.Throwable th) {
                if (create != null) {
                    try {
                        create.close();
                    } catch (java.lang.Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }

        public int startRecognitionForService(android.os.ParcelUuid parcelUuid, android.os.Bundle bundle, android.content.ComponentName componentName, android.hardware.soundtrigger.SoundTrigger.RecognitionConfig recognitionConfig) {
            android.hardware.soundtrigger.IRecognitionStatusCallback iRecognitionStatusCallback;
            this.mEventLogger.enqueue(new com.android.server.soundtrigger.SoundTriggerEvent.SessionEvent(com.android.server.soundtrigger.SoundTriggerEvent.SessionEvent.Type.START_RECOGNITION_SERVICE, getUuid(parcelUuid)));
            android.media.permission.SafeCloseable create = android.media.permission.ClearCallingIdentityContext.create();
            try {
                java.util.Objects.requireNonNull(parcelUuid);
                java.util.Objects.requireNonNull(componentName);
                java.util.Objects.requireNonNull(recognitionConfig);
                enforceCallingPermission("android.permission.MANAGE_SOUND_TRIGGER");
                enforceDetectionPermissions(componentName);
                android.hardware.soundtrigger.IRecognitionStatusCallback remoteSoundTriggerDetectionService = new com.android.server.soundtrigger.SoundTriggerService.SoundTriggerSessionStub.RemoteSoundTriggerDetectionService(parcelUuid.getUuid(), bundle, componentName, android.os.Binder.getCallingUserHandle(), recognitionConfig);
                synchronized (com.android.server.soundtrigger.SoundTriggerService.this.mLock) {
                    android.hardware.soundtrigger.SoundTrigger.GenericSoundModel genericSoundModel = (android.hardware.soundtrigger.SoundTrigger.SoundModel) this.mLoadedModels.get(parcelUuid.getUuid());
                    if (genericSoundModel == null) {
                        this.mEventLogger.enqueue(new com.android.server.soundtrigger.SoundTriggerEvent.SessionEvent(com.android.server.soundtrigger.SoundTriggerEvent.SessionEvent.Type.START_RECOGNITION_SERVICE, getUuid(parcelUuid), "Model not loaded").printLog(2, com.android.server.soundtrigger.SoundTriggerService.TAG));
                        if (create != null) {
                            create.close();
                        }
                        return Integer.MIN_VALUE;
                    }
                    synchronized (this.mCallbacksLock) {
                        iRecognitionStatusCallback = this.mCallbacks.get(parcelUuid.getUuid());
                    }
                    if (iRecognitionStatusCallback != null) {
                        this.mEventLogger.enqueue(new com.android.server.soundtrigger.SoundTriggerEvent.SessionEvent(com.android.server.soundtrigger.SoundTriggerEvent.SessionEvent.Type.START_RECOGNITION_SERVICE, getUuid(parcelUuid), "Model already running").printLog(2, com.android.server.soundtrigger.SoundTriggerService.TAG));
                        if (create != null) {
                            create.close();
                        }
                        return Integer.MIN_VALUE;
                    }
                    switch (genericSoundModel.getType()) {
                        case 1:
                            int startGenericRecognition = this.mSoundTriggerHelper.startGenericRecognition(genericSoundModel.getUuid(), genericSoundModel, remoteSoundTriggerDetectionService, recognitionConfig, false);
                            if (startGenericRecognition != 0) {
                                this.mEventLogger.enqueue(new com.android.server.soundtrigger.SoundTriggerEvent.SessionEvent(com.android.server.soundtrigger.SoundTriggerEvent.SessionEvent.Type.START_RECOGNITION_SERVICE, getUuid(parcelUuid), "Model start fail").printLog(2, com.android.server.soundtrigger.SoundTriggerService.TAG));
                                if (create != null) {
                                    create.close();
                                }
                                return startGenericRecognition;
                            }
                            synchronized (this.mCallbacksLock) {
                                this.mCallbacks.put(parcelUuid.getUuid(), remoteSoundTriggerDetectionService);
                            }
                            com.android.server.soundtrigger.SoundTriggerService.this.mSoundModelStatTracker.onStart(parcelUuid.getUuid());
                            if (create == null) {
                                return 0;
                            }
                            create.close();
                            return 0;
                        default:
                            this.mEventLogger.enqueue(new com.android.server.soundtrigger.SoundTriggerEvent.SessionEvent(com.android.server.soundtrigger.SoundTriggerEvent.SessionEvent.Type.START_RECOGNITION_SERVICE, getUuid(parcelUuid), "Unsupported model type").printLog(2, com.android.server.soundtrigger.SoundTriggerService.TAG));
                            if (create != null) {
                                create.close();
                            }
                            return Integer.MIN_VALUE;
                    }
                }
            } catch (java.lang.Throwable th) {
                if (create != null) {
                    try {
                        create.close();
                    } catch (java.lang.Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }

        public int stopRecognitionForService(android.os.ParcelUuid parcelUuid) {
            android.hardware.soundtrigger.IRecognitionStatusCallback iRecognitionStatusCallback;
            this.mEventLogger.enqueue(new com.android.server.soundtrigger.SoundTriggerEvent.SessionEvent(com.android.server.soundtrigger.SoundTriggerEvent.SessionEvent.Type.STOP_RECOGNITION_SERVICE, getUuid(parcelUuid)));
            android.media.permission.SafeCloseable create = android.media.permission.ClearCallingIdentityContext.create();
            try {
                enforceCallingPermission("android.permission.MANAGE_SOUND_TRIGGER");
                synchronized (com.android.server.soundtrigger.SoundTriggerService.this.mLock) {
                    android.hardware.soundtrigger.SoundTrigger.SoundModel soundModel = this.mLoadedModels.get(parcelUuid.getUuid());
                    if (soundModel == null) {
                        this.mEventLogger.enqueue(new com.android.server.soundtrigger.SoundTriggerEvent.SessionEvent(com.android.server.soundtrigger.SoundTriggerEvent.SessionEvent.Type.STOP_RECOGNITION_SERVICE, getUuid(parcelUuid), "Model not loaded").printLog(2, com.android.server.soundtrigger.SoundTriggerService.TAG));
                        if (create != null) {
                            create.close();
                        }
                        return Integer.MIN_VALUE;
                    }
                    synchronized (this.mCallbacksLock) {
                        iRecognitionStatusCallback = this.mCallbacks.get(parcelUuid.getUuid());
                    }
                    if (iRecognitionStatusCallback == null) {
                        this.mEventLogger.enqueue(new com.android.server.soundtrigger.SoundTriggerEvent.SessionEvent(com.android.server.soundtrigger.SoundTriggerEvent.SessionEvent.Type.STOP_RECOGNITION_SERVICE, getUuid(parcelUuid), "Model not running").printLog(2, com.android.server.soundtrigger.SoundTriggerService.TAG));
                        if (create != null) {
                            create.close();
                        }
                        return Integer.MIN_VALUE;
                    }
                    switch (soundModel.getType()) {
                        case 1:
                            int stopGenericRecognition = this.mSoundTriggerHelper.stopGenericRecognition(soundModel.getUuid(), iRecognitionStatusCallback);
                            if (stopGenericRecognition != 0) {
                                this.mEventLogger.enqueue(new com.android.server.soundtrigger.SoundTriggerEvent.SessionEvent(com.android.server.soundtrigger.SoundTriggerEvent.SessionEvent.Type.STOP_RECOGNITION_SERVICE, getUuid(parcelUuid), "Failed to stop model").printLog(2, com.android.server.soundtrigger.SoundTriggerService.TAG));
                                if (create != null) {
                                    create.close();
                                }
                                return stopGenericRecognition;
                            }
                            synchronized (this.mCallbacksLock) {
                                this.mCallbacks.remove(parcelUuid.getUuid());
                            }
                            com.android.server.soundtrigger.SoundTriggerService.this.mSoundModelStatTracker.onStop(parcelUuid.getUuid());
                            if (create == null) {
                                return 0;
                            }
                            create.close();
                            return 0;
                        default:
                            this.mEventLogger.enqueue(new com.android.server.soundtrigger.SoundTriggerEvent.SessionEvent(com.android.server.soundtrigger.SoundTriggerEvent.SessionEvent.Type.STOP_RECOGNITION_SERVICE, getUuid(parcelUuid), "Unknown model type").printLog(2, com.android.server.soundtrigger.SoundTriggerService.TAG));
                            if (create != null) {
                                create.close();
                            }
                            return Integer.MIN_VALUE;
                    }
                }
            } catch (java.lang.Throwable th) {
                if (create != null) {
                    try {
                        create.close();
                    } catch (java.lang.Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }

        public int unloadSoundModel(android.os.ParcelUuid parcelUuid) {
            int unloadKeyphraseSoundModel;
            this.mEventLogger.enqueue(new com.android.server.soundtrigger.SoundTriggerEvent.SessionEvent(com.android.server.soundtrigger.SoundTriggerEvent.SessionEvent.Type.UNLOAD_MODEL, getUuid(parcelUuid)));
            android.media.permission.SafeCloseable create = android.media.permission.ClearCallingIdentityContext.create();
            try {
                enforceCallingPermission("android.permission.MANAGE_SOUND_TRIGGER");
                synchronized (com.android.server.soundtrigger.SoundTriggerService.this.mLock) {
                    android.hardware.soundtrigger.SoundTrigger.KeyphraseSoundModel keyphraseSoundModel = (android.hardware.soundtrigger.SoundTrigger.SoundModel) this.mLoadedModels.get(parcelUuid.getUuid());
                    if (keyphraseSoundModel == null) {
                        this.mEventLogger.enqueue(new com.android.server.soundtrigger.SoundTriggerEvent.SessionEvent(com.android.server.soundtrigger.SoundTriggerEvent.SessionEvent.Type.UNLOAD_MODEL, getUuid(parcelUuid), "Model not loaded").printLog(2, com.android.server.soundtrigger.SoundTriggerService.TAG));
                        if (create != null) {
                            create.close();
                        }
                        return Integer.MIN_VALUE;
                    }
                    switch (keyphraseSoundModel.getType()) {
                        case 0:
                            unloadKeyphraseSoundModel = this.mSoundTriggerHelper.unloadKeyphraseSoundModel(keyphraseSoundModel.getKeyphrases()[0].getId());
                            break;
                        case 1:
                            unloadKeyphraseSoundModel = this.mSoundTriggerHelper.unloadGenericSoundModel(keyphraseSoundModel.getUuid());
                            break;
                        default:
                            this.mEventLogger.enqueue(new com.android.server.soundtrigger.SoundTriggerEvent.SessionEvent(com.android.server.soundtrigger.SoundTriggerEvent.SessionEvent.Type.UNLOAD_MODEL, getUuid(parcelUuid), "Unknown model type").printLog(2, com.android.server.soundtrigger.SoundTriggerService.TAG));
                            if (create != null) {
                                create.close();
                            }
                            return Integer.MIN_VALUE;
                    }
                    if (unloadKeyphraseSoundModel != 0) {
                        this.mEventLogger.enqueue(new com.android.server.soundtrigger.SoundTriggerEvent.SessionEvent(com.android.server.soundtrigger.SoundTriggerEvent.SessionEvent.Type.UNLOAD_MODEL, getUuid(parcelUuid), "Failed to unload model").printLog(2, com.android.server.soundtrigger.SoundTriggerService.TAG));
                        if (create != null) {
                            create.close();
                        }
                        return unloadKeyphraseSoundModel;
                    }
                    this.mLoadedModels.remove(parcelUuid.getUuid());
                    if (create != null) {
                        create.close();
                    }
                    return 0;
                }
            } catch (java.lang.Throwable th) {
                if (create != null) {
                    try {
                        create.close();
                    } catch (java.lang.Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }

        public boolean isRecognitionActive(android.os.ParcelUuid parcelUuid) {
            android.media.permission.SafeCloseable create = android.media.permission.ClearCallingIdentityContext.create();
            try {
                enforceCallingPermission("android.permission.MANAGE_SOUND_TRIGGER");
                synchronized (this.mCallbacksLock) {
                    if (this.mCallbacks.get(parcelUuid.getUuid()) != null) {
                        boolean isRecognitionRequested = this.mSoundTriggerHelper.isRecognitionRequested(parcelUuid.getUuid());
                        if (create != null) {
                            create.close();
                        }
                        return isRecognitionRequested;
                    }
                    if (create != null) {
                        create.close();
                        return false;
                    }
                    return false;
                }
            } catch (java.lang.Throwable th) {
                if (create != null) {
                    try {
                        create.close();
                    } catch (java.lang.Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }

        public int getModelState(android.os.ParcelUuid parcelUuid) {
            this.mEventLogger.enqueue(new com.android.server.soundtrigger.SoundTriggerEvent.SessionEvent(com.android.server.soundtrigger.SoundTriggerEvent.SessionEvent.Type.GET_MODEL_STATE, getUuid(parcelUuid)));
            android.media.permission.SafeCloseable create = android.media.permission.ClearCallingIdentityContext.create();
            try {
                enforceCallingPermission("android.permission.MANAGE_SOUND_TRIGGER");
                synchronized (com.android.server.soundtrigger.SoundTriggerService.this.mLock) {
                    android.hardware.soundtrigger.SoundTrigger.SoundModel soundModel = this.mLoadedModels.get(parcelUuid.getUuid());
                    int i = Integer.MIN_VALUE;
                    if (soundModel == null) {
                        this.mEventLogger.enqueue(new com.android.server.soundtrigger.SoundTriggerEvent.SessionEvent(com.android.server.soundtrigger.SoundTriggerEvent.SessionEvent.Type.GET_MODEL_STATE, getUuid(parcelUuid), "Model is not loaded").printLog(2, com.android.server.soundtrigger.SoundTriggerService.TAG));
                        if (create != null) {
                            create.close();
                        }
                        return Integer.MIN_VALUE;
                    }
                    switch (soundModel.getType()) {
                        case 1:
                            i = this.mSoundTriggerHelper.getGenericModelState(soundModel.getUuid());
                            break;
                        default:
                            this.mEventLogger.enqueue(new com.android.server.soundtrigger.SoundTriggerEvent.SessionEvent(com.android.server.soundtrigger.SoundTriggerEvent.SessionEvent.Type.GET_MODEL_STATE, getUuid(parcelUuid), "Unsupported model type").printLog(2, com.android.server.soundtrigger.SoundTriggerService.TAG));
                            break;
                    }
                    if (create != null) {
                        create.close();
                    }
                    return i;
                }
            } catch (java.lang.Throwable th) {
                if (create != null) {
                    try {
                        create.close();
                    } catch (java.lang.Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }

        @android.annotation.Nullable
        public android.hardware.soundtrigger.SoundTrigger.ModuleProperties getModuleProperties() {
            android.hardware.soundtrigger.SoundTrigger.ModuleProperties moduleProperties;
            this.mEventLogger.enqueue(new com.android.server.soundtrigger.SoundTriggerEvent.SessionEvent(com.android.server.soundtrigger.SoundTriggerEvent.SessionEvent.Type.GET_MODULE_PROPERTIES, null));
            android.media.permission.SafeCloseable create = android.media.permission.ClearCallingIdentityContext.create();
            try {
                enforceCallingPermission("android.permission.MANAGE_SOUND_TRIGGER");
                synchronized (com.android.server.soundtrigger.SoundTriggerService.this.mLock) {
                    moduleProperties = this.mSoundTriggerHelper.getModuleProperties();
                }
                if (create != null) {
                    create.close();
                }
                return moduleProperties;
            } catch (java.lang.Throwable th) {
                if (create != null) {
                    try {
                        create.close();
                    } catch (java.lang.Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }

        public int setParameter(android.os.ParcelUuid parcelUuid, @android.hardware.soundtrigger.ModelParams int i, int i2) {
            this.mEventLogger.enqueue(new com.android.server.soundtrigger.SoundTriggerEvent.SessionEvent(com.android.server.soundtrigger.SoundTriggerEvent.SessionEvent.Type.SET_PARAMETER, getUuid(parcelUuid)));
            android.media.permission.SafeCloseable create = android.media.permission.ClearCallingIdentityContext.create();
            try {
                enforceCallingPermission("android.permission.MANAGE_SOUND_TRIGGER");
                synchronized (com.android.server.soundtrigger.SoundTriggerService.this.mLock) {
                    android.hardware.soundtrigger.SoundTrigger.SoundModel soundModel = this.mLoadedModels.get(parcelUuid.getUuid());
                    if (soundModel == null) {
                        this.mEventLogger.enqueue(new com.android.server.soundtrigger.SoundTriggerEvent.SessionEvent(com.android.server.soundtrigger.SoundTriggerEvent.SessionEvent.Type.SET_PARAMETER, getUuid(parcelUuid), "Model not loaded").printLog(2, com.android.server.soundtrigger.SoundTriggerService.TAG));
                        int i3 = android.hardware.soundtrigger.SoundTrigger.STATUS_BAD_VALUE;
                        if (create != null) {
                            create.close();
                        }
                        return i3;
                    }
                    int parameter = this.mSoundTriggerHelper.setParameter(soundModel.getUuid(), i, i2);
                    if (create != null) {
                        create.close();
                    }
                    return parameter;
                }
            } catch (java.lang.Throwable th) {
                if (create != null) {
                    try {
                        create.close();
                    } catch (java.lang.Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }

        public int getParameter(@android.annotation.NonNull android.os.ParcelUuid parcelUuid, @android.hardware.soundtrigger.ModelParams int i) throws java.lang.UnsupportedOperationException, java.lang.IllegalArgumentException {
            int parameter;
            android.media.permission.SafeCloseable create = android.media.permission.ClearCallingIdentityContext.create();
            try {
                enforceCallingPermission("android.permission.MANAGE_SOUND_TRIGGER");
                synchronized (com.android.server.soundtrigger.SoundTriggerService.this.mLock) {
                    android.hardware.soundtrigger.SoundTrigger.SoundModel soundModel = this.mLoadedModels.get(parcelUuid.getUuid());
                    if (soundModel == null) {
                        throw new java.lang.IllegalArgumentException("sound model is not loaded");
                    }
                    parameter = this.mSoundTriggerHelper.getParameter(soundModel.getUuid(), i);
                }
                if (create != null) {
                    create.close();
                }
                return parameter;
            } catch (java.lang.Throwable th) {
                if (create != null) {
                    try {
                        create.close();
                    } catch (java.lang.Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }

        @android.annotation.Nullable
        public android.hardware.soundtrigger.SoundTrigger.ModelParamRange queryParameter(@android.annotation.NonNull android.os.ParcelUuid parcelUuid, @android.hardware.soundtrigger.ModelParams int i) {
            android.media.permission.SafeCloseable create = android.media.permission.ClearCallingIdentityContext.create();
            try {
                enforceCallingPermission("android.permission.MANAGE_SOUND_TRIGGER");
                synchronized (com.android.server.soundtrigger.SoundTriggerService.this.mLock) {
                    android.hardware.soundtrigger.SoundTrigger.SoundModel soundModel = this.mLoadedModels.get(parcelUuid.getUuid());
                    if (soundModel != null) {
                        android.hardware.soundtrigger.SoundTrigger.ModelParamRange queryParameter = this.mSoundTriggerHelper.queryParameter(soundModel.getUuid(), i);
                        if (create != null) {
                            create.close();
                        }
                        return queryParameter;
                    }
                    if (create != null) {
                        create.close();
                        return null;
                    }
                    return null;
                }
            } catch (java.lang.Throwable th) {
                if (create != null) {
                    try {
                        create.close();
                    } catch (java.lang.Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* renamed from: clientDied, reason: merged with bridge method [inline-methods] */
        public void lambda$new$0() {
            this.mEventLogger.enqueue(new com.android.server.soundtrigger.SoundTriggerEvent.SessionEvent(com.android.server.soundtrigger.SoundTriggerEvent.SessionEvent.Type.DETACH, null));
            com.android.server.soundtrigger.SoundTriggerService.this.mServiceEventLogger.enqueue(new com.android.server.soundtrigger.SoundTriggerEvent.ServiceEvent(com.android.server.soundtrigger.SoundTriggerEvent.ServiceEvent.Type.DETACH, this.mOriginatorIdentity.packageName, "Client died").printLog(2, com.android.server.soundtrigger.SoundTriggerService.TAG));
            detach();
        }

        private void detach() {
            if (this.mAppOpsListener != null) {
                com.android.server.soundtrigger.SoundTriggerService.this.mAppOpsManager.stopWatchingMode(this.mAppOpsListener);
            }
            com.android.server.soundtrigger.SoundTriggerService.this.mDeviceStateHandler.unregisterListener(this.mListener);
            this.mSoundTriggerHelper.detach();
            com.android.server.soundtrigger.SoundTriggerService.this.detachSessionLogger(this.mEventLogger);
        }

        private void enforceCallingPermission(java.lang.String str) {
            if (android.media.permission.PermissionUtil.checkPermissionForPreflight(com.android.server.soundtrigger.SoundTriggerService.this.mContext, this.mOriginatorIdentity, str) != 0) {
                throw new java.lang.SecurityException("Identity " + this.mOriginatorIdentity + " does not have permission " + str);
            }
        }

        private void enforceDetectionPermissions(android.content.ComponentName componentName) {
            if (com.android.server.soundtrigger.SoundTriggerService.this.mPackageManager.checkPermission("android.permission.CAPTURE_AUDIO_HOTWORD", componentName.getPackageName()) != 0) {
                throw new java.lang.SecurityException(componentName.getPackageName() + " does not have permission android.permission.CAPTURE_AUDIO_HOTWORD");
            }
        }

        private java.util.UUID getUuid(android.os.ParcelUuid parcelUuid) {
            if (parcelUuid != null) {
                return parcelUuid.getUuid();
            }
            return null;
        }

        private java.util.UUID getUuid(android.hardware.soundtrigger.SoundTrigger.SoundModel soundModel) {
            if (soundModel != null) {
                return soundModel.getUuid();
            }
            return null;
        }

        /* JADX INFO: Access modifiers changed from: private */
        class RemoteSoundTriggerDetectionService extends android.hardware.soundtrigger.IRecognitionStatusCallback.Stub implements android.content.ServiceConnection {
            private static final int MSG_STOP_ALL_PENDING_OPERATIONS = 1;

            @android.annotation.NonNull
            private final android.media.soundtrigger.ISoundTriggerDetectionServiceClient mClient;

            @com.android.internal.annotations.GuardedBy({"mRemoteServiceLock"})
            private boolean mDestroyOnceRunningOpsDone;

            @com.android.internal.annotations.GuardedBy({"mRemoteServiceLock"})
            private boolean mIsBound;

            @com.android.internal.annotations.GuardedBy({"mRemoteServiceLock"})
            private boolean mIsDestroyed;
            private final com.android.server.soundtrigger.SoundTriggerService.NumOps mNumOps;

            @com.android.internal.annotations.GuardedBy({"mRemoteServiceLock"})
            private int mNumTotalOpsPerformed;

            @android.annotation.Nullable
            private final android.os.Bundle mParams;

            @android.annotation.NonNull
            private final android.os.ParcelUuid mPuuid;

            @android.annotation.NonNull
            private final android.hardware.soundtrigger.SoundTrigger.RecognitionConfig mRecognitionConfig;

            @android.annotation.NonNull
            private final android.os.PowerManager.WakeLock mRemoteServiceWakeLock;

            @com.android.internal.annotations.GuardedBy({"mRemoteServiceLock"})
            @android.annotation.Nullable
            private android.media.soundtrigger.ISoundTriggerDetectionService mService;

            @android.annotation.NonNull
            private final android.content.ComponentName mServiceName;

            @android.annotation.NonNull
            private final android.os.UserHandle mUser;
            private final java.lang.Object mRemoteServiceLock = new java.lang.Object();

            @com.android.internal.annotations.GuardedBy({"mRemoteServiceLock"})
            private final java.util.ArrayList<com.android.server.soundtrigger.SoundTriggerService.Operation> mPendingOps = new java.util.ArrayList<>();

            @com.android.internal.annotations.GuardedBy({"mRemoteServiceLock"})
            private final android.util.ArraySet<java.lang.Integer> mRunningOpIds = new android.util.ArraySet<>();

            @android.annotation.NonNull
            private final android.os.Handler mHandler = new android.os.Handler(android.os.Looper.getMainLooper());

            public RemoteSoundTriggerDetectionService(@android.annotation.NonNull java.util.UUID uuid, @android.annotation.Nullable android.os.Bundle bundle, @android.annotation.NonNull android.content.ComponentName componentName, @android.annotation.NonNull android.os.UserHandle userHandle, @android.annotation.NonNull android.hardware.soundtrigger.SoundTrigger.RecognitionConfig recognitionConfig) {
                this.mPuuid = new android.os.ParcelUuid(uuid);
                this.mParams = bundle;
                this.mServiceName = componentName;
                this.mUser = userHandle;
                this.mRecognitionConfig = recognitionConfig;
                this.mRemoteServiceWakeLock = ((android.os.PowerManager) com.android.server.soundtrigger.SoundTriggerService.this.mContext.getSystemService("power")).newWakeLock(1, "RemoteSoundTriggerDetectionService " + this.mServiceName.getPackageName() + ":" + this.mServiceName.getClassName());
                synchronized (com.android.server.soundtrigger.SoundTriggerService.this.mLock) {
                    try {
                        com.android.server.soundtrigger.SoundTriggerService.NumOps numOps = (com.android.server.soundtrigger.SoundTriggerService.NumOps) com.android.server.soundtrigger.SoundTriggerService.this.mNumOpsPerPackage.get(this.mServiceName.getPackageName());
                        if (numOps == null) {
                            numOps = new com.android.server.soundtrigger.SoundTriggerService.NumOps();
                            com.android.server.soundtrigger.SoundTriggerService.this.mNumOpsPerPackage.put(this.mServiceName.getPackageName(), numOps);
                        }
                        this.mNumOps = numOps;
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
                this.mClient = new android.media.soundtrigger.ISoundTriggerDetectionServiceClient.Stub() { // from class: com.android.server.soundtrigger.SoundTriggerService.SoundTriggerSessionStub.RemoteSoundTriggerDetectionService.1
                    public void onOpFinished(int i) {
                        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                        try {
                            synchronized (com.android.server.soundtrigger.SoundTriggerService.SoundTriggerSessionStub.RemoteSoundTriggerDetectionService.this.mRemoteServiceLock) {
                                try {
                                    com.android.server.soundtrigger.SoundTriggerService.SoundTriggerSessionStub.RemoteSoundTriggerDetectionService.this.mRunningOpIds.remove(java.lang.Integer.valueOf(i));
                                    if (com.android.server.soundtrigger.SoundTriggerService.SoundTriggerSessionStub.RemoteSoundTriggerDetectionService.this.mRunningOpIds.isEmpty() && com.android.server.soundtrigger.SoundTriggerService.SoundTriggerSessionStub.RemoteSoundTriggerDetectionService.this.mPendingOps.isEmpty()) {
                                        if (com.android.server.soundtrigger.SoundTriggerService.SoundTriggerSessionStub.RemoteSoundTriggerDetectionService.this.mDestroyOnceRunningOpsDone) {
                                            com.android.server.soundtrigger.SoundTriggerService.SoundTriggerSessionStub.RemoteSoundTriggerDetectionService.this.destroy();
                                        } else {
                                            com.android.server.soundtrigger.SoundTriggerService.SoundTriggerSessionStub.RemoteSoundTriggerDetectionService.this.disconnectLocked();
                                        }
                                    }
                                } catch (java.lang.Throwable th2) {
                                    throw th2;
                                }
                            }
                        } finally {
                            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        }
                    }
                };
            }

            public boolean pingBinder() {
                return (this.mIsDestroyed || this.mDestroyOnceRunningOpsDone) ? false : true;
            }

            /* JADX INFO: Access modifiers changed from: private */
            @com.android.internal.annotations.GuardedBy({"mRemoteServiceLock"})
            public void disconnectLocked() {
                if (this.mService != null) {
                    try {
                        this.mService.removeClient(this.mPuuid);
                    } catch (java.lang.Exception e) {
                        android.util.Slog.e(com.android.server.soundtrigger.SoundTriggerService.TAG, this.mPuuid + ": Cannot remove client", e);
                        com.android.server.soundtrigger.SoundTriggerService.SoundTriggerSessionStub.this.mEventLogger.enqueue(new com.android.server.utils.EventLogger.StringEvent(this.mPuuid + ": Cannot remove client"));
                    }
                    this.mService = null;
                }
                if (this.mIsBound) {
                    com.android.server.soundtrigger.SoundTriggerService.this.mContext.unbindService(this);
                    this.mIsBound = false;
                    synchronized (com.android.server.soundtrigger.SoundTriggerService.SoundTriggerSessionStub.this.mCallbacksLock) {
                        this.mRemoteServiceWakeLock.release();
                    }
                }
            }

            /* JADX INFO: Access modifiers changed from: private */
            public void destroy() {
                com.android.server.soundtrigger.SoundTriggerService.SoundTriggerSessionStub.this.mEventLogger.enqueue(new com.android.server.utils.EventLogger.StringEvent(this.mPuuid + ": destroy"));
                synchronized (this.mRemoteServiceLock) {
                    disconnectLocked();
                    this.mIsDestroyed = true;
                }
                if (!this.mDestroyOnceRunningOpsDone) {
                    synchronized (com.android.server.soundtrigger.SoundTriggerService.SoundTriggerSessionStub.this.mCallbacksLock) {
                        com.android.server.soundtrigger.SoundTriggerService.SoundTriggerSessionStub.this.mCallbacks.remove(this.mPuuid.getUuid());
                    }
                }
            }

            /* JADX INFO: Access modifiers changed from: private */
            public void stopAllPendingOperations() {
                synchronized (this.mRemoteServiceLock) {
                    try {
                        if (this.mIsDestroyed) {
                            return;
                        }
                        if (this.mService != null) {
                            int size = this.mRunningOpIds.size();
                            for (int i = 0; i < size; i++) {
                                try {
                                    this.mService.onStopOperation(this.mPuuid, this.mRunningOpIds.valueAt(i).intValue());
                                } catch (java.lang.Exception e) {
                                    android.util.Slog.e(com.android.server.soundtrigger.SoundTriggerService.TAG, this.mPuuid + ": Could not stop operation " + this.mRunningOpIds.valueAt(i), e);
                                    com.android.server.soundtrigger.SoundTriggerService.SoundTriggerSessionStub.this.mEventLogger.enqueue(new com.android.server.utils.EventLogger.StringEvent(this.mPuuid + ": Could not stop operation " + this.mRunningOpIds.valueAt(i)));
                                }
                            }
                            this.mRunningOpIds.clear();
                        }
                        disconnectLocked();
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
            }

            private void bind() {
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    android.content.Intent intent = new android.content.Intent();
                    intent.setComponent(this.mServiceName);
                    android.content.pm.ResolveInfo resolveServiceAsUser = com.android.server.soundtrigger.SoundTriggerService.this.mContext.getPackageManager().resolveServiceAsUser(intent, 268435588, this.mUser.getIdentifier());
                    if (resolveServiceAsUser == null) {
                        android.util.Slog.w(com.android.server.soundtrigger.SoundTriggerService.TAG, this.mPuuid + ": " + this.mServiceName + " not found");
                        com.android.server.soundtrigger.SoundTriggerService.SoundTriggerSessionStub.this.mEventLogger.enqueue(new com.android.server.utils.EventLogger.StringEvent(this.mPuuid + ": " + this.mServiceName + " not found"));
                        return;
                    }
                    if ("android.permission.BIND_SOUND_TRIGGER_DETECTION_SERVICE".equals(resolveServiceAsUser.serviceInfo.permission)) {
                        this.mIsBound = com.android.server.soundtrigger.SoundTriggerService.this.mContext.bindServiceAsUser(intent, this, 67112961, this.mUser);
                        if (this.mIsBound) {
                            this.mRemoteServiceWakeLock.acquire();
                        } else {
                            android.util.Slog.w(com.android.server.soundtrigger.SoundTriggerService.TAG, this.mPuuid + ": Could not bind to " + this.mServiceName);
                            com.android.server.soundtrigger.SoundTriggerService.SoundTriggerSessionStub.this.mEventLogger.enqueue(new com.android.server.utils.EventLogger.StringEvent(this.mPuuid + ": Could not bind to " + this.mServiceName));
                        }
                        return;
                    }
                    android.util.Slog.w(com.android.server.soundtrigger.SoundTriggerService.TAG, this.mPuuid + ": " + this.mServiceName + " does not require android.permission.BIND_SOUND_TRIGGER_DETECTION_SERVICE");
                    com.android.server.soundtrigger.SoundTriggerService.SoundTriggerSessionStub.this.mEventLogger.enqueue(new com.android.server.utils.EventLogger.StringEvent(this.mPuuid + ": " + this.mServiceName + " does not require android.permission.BIND_SOUND_TRIGGER_DETECTION_SERVICE"));
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }

            /* JADX WARN: Code restructure failed: missing block: B:31:0x00ac, code lost:
            
                r8 = move-exception;
             */
            /* JADX WARN: Code restructure failed: missing block: B:32:0x00ae, code lost:
            
                android.util.Slog.e(com.android.server.soundtrigger.SoundTriggerService.TAG, r7.mPuuid + ": Could not run operation " + r1, r8);
                r7.this$1.mEventLogger.enqueue(new com.android.server.utils.EventLogger.StringEvent(r7.mPuuid + ": Could not run operation " + r1));
             */
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            private void runOrAddOperation(com.android.server.soundtrigger.SoundTriggerService.Operation operation) {
                synchronized (this.mRemoteServiceLock) {
                    try {
                        if (this.mIsDestroyed || this.mDestroyOnceRunningOpsDone) {
                            android.util.Slog.w(com.android.server.soundtrigger.SoundTriggerService.TAG, this.mPuuid + ": Dropped operation as already destroyed or marked for destruction");
                            com.android.server.soundtrigger.SoundTriggerService.SoundTriggerSessionStub.this.mEventLogger.enqueue(new com.android.server.utils.EventLogger.StringEvent(this.mPuuid + ":Dropped operation as already destroyed or marked for destruction"));
                            operation.drop();
                            return;
                        }
                        if (this.mService == null) {
                            this.mPendingOps.add(operation);
                            if (!this.mIsBound) {
                                bind();
                            }
                        } else {
                            long nanoTime = java.lang.System.nanoTime();
                            this.mNumOps.clearOldOps(nanoTime);
                            android.provider.Settings.Global.getInt(com.android.server.soundtrigger.SoundTriggerService.this.mContext.getContentResolver(), "max_sound_trigger_detection_service_ops_per_day", Integer.MAX_VALUE);
                            this.mNumOps.getOpsAdded();
                            this.mNumOps.addOp(nanoTime);
                            int i = this.mNumTotalOpsPerformed;
                            do {
                                this.mNumTotalOpsPerformed++;
                            } while (this.mRunningOpIds.contains(java.lang.Integer.valueOf(i)));
                            android.util.Slog.v(com.android.server.soundtrigger.SoundTriggerService.TAG, this.mPuuid + ": runOp " + i);
                            com.android.server.soundtrigger.SoundTriggerService.SoundTriggerSessionStub.this.mEventLogger.enqueue(new com.android.server.utils.EventLogger.StringEvent(this.mPuuid + ": runOp " + i));
                            operation.run(i, this.mService);
                            this.mRunningOpIds.add(java.lang.Integer.valueOf(i));
                            if (this.mPendingOps.isEmpty() && this.mRunningOpIds.isEmpty()) {
                                if (this.mDestroyOnceRunningOpsDone) {
                                    destroy();
                                } else {
                                    disconnectLocked();
                                }
                            } else {
                                this.mHandler.removeMessages(1);
                                this.mHandler.sendMessageDelayed(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.Consumer() { // from class: com.android.server.soundtrigger.SoundTriggerService$SoundTriggerSessionStub$RemoteSoundTriggerDetectionService$$ExternalSyntheticLambda0
                                    @Override // java.util.function.Consumer
                                    public final void accept(java.lang.Object obj) {
                                        ((com.android.server.soundtrigger.SoundTriggerService.SoundTriggerSessionStub.RemoteSoundTriggerDetectionService) obj).stopAllPendingOperations();
                                    }
                                }, this).setWhat(1), android.provider.Settings.Global.getLong(com.android.server.soundtrigger.SoundTriggerService.this.mContext.getContentResolver(), "sound_trigger_detection_service_op_timeout", com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME));
                            }
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
            }

            public void onKeyphraseDetected(android.hardware.soundtrigger.SoundTrigger.KeyphraseRecognitionEvent keyphraseRecognitionEvent) {
            }

            @android.annotation.NonNull
            private android.media.AudioRecord createAudioRecordForEvent(@android.annotation.NonNull android.hardware.soundtrigger.SoundTrigger.GenericRecognitionEvent genericRecognitionEvent) throws java.lang.IllegalArgumentException, java.lang.UnsupportedOperationException {
                android.media.AudioAttributes.Builder builder = new android.media.AudioAttributes.Builder();
                builder.setInternalCapturePreset(1999);
                android.media.AudioAttributes build = builder.build();
                android.media.AudioFormat captureFormat = genericRecognitionEvent.getCaptureFormat();
                com.android.server.soundtrigger.SoundTriggerService.SoundTriggerSessionStub.this.mEventLogger.enqueue(new com.android.server.utils.EventLogger.StringEvent("createAudioRecordForEvent"));
                return new android.media.AudioRecord.Builder().setAudioAttributes(build).setAudioFormat(new android.media.AudioFormat.Builder().setChannelMask(captureFormat.getChannelMask()).setEncoding(captureFormat.getEncoding()).setSampleRate(captureFormat.getSampleRate()).build()).setSessionId(genericRecognitionEvent.getCaptureSession()).build();
            }

            public void onGenericSoundTriggerDetected(final android.hardware.soundtrigger.SoundTrigger.GenericRecognitionEvent genericRecognitionEvent) {
                runOrAddOperation(new com.android.server.soundtrigger.SoundTriggerService.Operation(new java.lang.Runnable() { // from class: com.android.server.soundtrigger.SoundTriggerService$SoundTriggerSessionStub$RemoteSoundTriggerDetectionService$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.server.soundtrigger.SoundTriggerService.SoundTriggerSessionStub.RemoteSoundTriggerDetectionService.this.lambda$onGenericSoundTriggerDetected$0();
                    }
                }, new com.android.server.soundtrigger.SoundTriggerService.Operation.ExecuteOp() { // from class: com.android.server.soundtrigger.SoundTriggerService$SoundTriggerSessionStub$RemoteSoundTriggerDetectionService$$ExternalSyntheticLambda2
                    @Override // com.android.server.soundtrigger.SoundTriggerService.Operation.ExecuteOp
                    public final void run(int i, android.media.soundtrigger.ISoundTriggerDetectionService iSoundTriggerDetectionService) {
                        com.android.server.soundtrigger.SoundTriggerService.SoundTriggerSessionStub.RemoteSoundTriggerDetectionService.this.lambda$onGenericSoundTriggerDetected$1(genericRecognitionEvent, i, iSoundTriggerDetectionService);
                    }
                }, new java.lang.Runnable() { // from class: com.android.server.soundtrigger.SoundTriggerService$SoundTriggerSessionStub$RemoteSoundTriggerDetectionService$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.server.soundtrigger.SoundTriggerService.SoundTriggerSessionStub.RemoteSoundTriggerDetectionService.this.lambda$onGenericSoundTriggerDetected$2(genericRecognitionEvent);
                    }
                }));
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onGenericSoundTriggerDetected$0() {
                if (!this.mRecognitionConfig.allowMultipleTriggers) {
                    synchronized (com.android.server.soundtrigger.SoundTriggerService.SoundTriggerSessionStub.this.mCallbacksLock) {
                        com.android.server.soundtrigger.SoundTriggerService.SoundTriggerSessionStub.this.mCallbacks.remove(this.mPuuid.getUuid());
                    }
                    this.mDestroyOnceRunningOpsDone = true;
                }
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onGenericSoundTriggerDetected$1(android.hardware.soundtrigger.SoundTrigger.GenericRecognitionEvent genericRecognitionEvent, int i, android.media.soundtrigger.ISoundTriggerDetectionService iSoundTriggerDetectionService) throws android.os.RemoteException {
                iSoundTriggerDetectionService.onGenericRecognitionEvent(this.mPuuid, i, genericRecognitionEvent);
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onGenericSoundTriggerDetected$2(android.hardware.soundtrigger.SoundTrigger.GenericRecognitionEvent genericRecognitionEvent) {
                if (genericRecognitionEvent.isCaptureAvailable()) {
                    try {
                        android.media.AudioRecord createAudioRecordForEvent = createAudioRecordForEvent(genericRecognitionEvent);
                        createAudioRecordForEvent.startRecording();
                        createAudioRecordForEvent.release();
                    } catch (java.lang.IllegalArgumentException | java.lang.UnsupportedOperationException e) {
                        android.util.Slog.w(com.android.server.soundtrigger.SoundTriggerService.TAG, this.mPuuid + ": createAudioRecordForEvent(" + genericRecognitionEvent + "), failed to create AudioRecord");
                    }
                }
            }

            private void onError(final int i) {
                android.util.Slog.v(com.android.server.soundtrigger.SoundTriggerService.TAG, this.mPuuid + ": onError: " + i);
                com.android.server.soundtrigger.SoundTriggerService.SoundTriggerSessionStub.this.mEventLogger.enqueue(new com.android.server.utils.EventLogger.StringEvent(this.mPuuid + ": onError: " + i));
                runOrAddOperation(new com.android.server.soundtrigger.SoundTriggerService.Operation(new java.lang.Runnable() { // from class: com.android.server.soundtrigger.SoundTriggerService$SoundTriggerSessionStub$RemoteSoundTriggerDetectionService$$ExternalSyntheticLambda4
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.server.soundtrigger.SoundTriggerService.SoundTriggerSessionStub.RemoteSoundTriggerDetectionService.this.lambda$onError$3();
                    }
                }, new com.android.server.soundtrigger.SoundTriggerService.Operation.ExecuteOp() { // from class: com.android.server.soundtrigger.SoundTriggerService$SoundTriggerSessionStub$RemoteSoundTriggerDetectionService$$ExternalSyntheticLambda5
                    @Override // com.android.server.soundtrigger.SoundTriggerService.Operation.ExecuteOp
                    public final void run(int i2, android.media.soundtrigger.ISoundTriggerDetectionService iSoundTriggerDetectionService) {
                        com.android.server.soundtrigger.SoundTriggerService.SoundTriggerSessionStub.RemoteSoundTriggerDetectionService.this.lambda$onError$4(i, i2, iSoundTriggerDetectionService);
                    }
                }, null));
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onError$3() {
                synchronized (com.android.server.soundtrigger.SoundTriggerService.SoundTriggerSessionStub.this.mCallbacksLock) {
                    com.android.server.soundtrigger.SoundTriggerService.SoundTriggerSessionStub.this.mCallbacks.remove(this.mPuuid.getUuid());
                }
                this.mDestroyOnceRunningOpsDone = true;
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onError$4(int i, int i2, android.media.soundtrigger.ISoundTriggerDetectionService iSoundTriggerDetectionService) throws android.os.RemoteException {
                iSoundTriggerDetectionService.onError(this.mPuuid, i2, i);
            }

            public void onPreempted() {
                android.util.Slog.v(com.android.server.soundtrigger.SoundTriggerService.TAG, this.mPuuid + ": onPreempted");
                onError(Integer.MIN_VALUE);
            }

            public void onModuleDied() {
                android.util.Slog.v(com.android.server.soundtrigger.SoundTriggerService.TAG, this.mPuuid + ": onModuleDied");
                onError(android.hardware.soundtrigger.SoundTrigger.STATUS_DEAD_OBJECT);
            }

            public void onResumeFailed(int i) {
                android.util.Slog.v(com.android.server.soundtrigger.SoundTriggerService.TAG, this.mPuuid + ": onResumeFailed: " + i);
                onError(i);
            }

            public void onPauseFailed(int i) {
                android.util.Slog.v(com.android.server.soundtrigger.SoundTriggerService.TAG, this.mPuuid + ": onPauseFailed: " + i);
                onError(i);
            }

            public void onRecognitionPaused() {
            }

            public void onRecognitionResumed() {
            }

            @Override // android.content.ServiceConnection
            public void onServiceConnected(android.content.ComponentName componentName, android.os.IBinder iBinder) {
                android.util.Slog.v(com.android.server.soundtrigger.SoundTriggerService.TAG, this.mPuuid + ": onServiceConnected(" + iBinder + ")");
                com.android.server.soundtrigger.SoundTriggerService.SoundTriggerSessionStub.this.mEventLogger.enqueue(new com.android.server.utils.EventLogger.StringEvent(this.mPuuid + ": onServiceConnected(" + iBinder + ")"));
                synchronized (this.mRemoteServiceLock) {
                    try {
                        this.mService = android.media.soundtrigger.ISoundTriggerDetectionService.Stub.asInterface(iBinder);
                        try {
                            this.mService.setClient(this.mPuuid, this.mParams, this.mClient);
                            while (!this.mPendingOps.isEmpty()) {
                                runOrAddOperation(this.mPendingOps.remove(0));
                            }
                        } catch (java.lang.Exception e) {
                            android.util.Slog.e(com.android.server.soundtrigger.SoundTriggerService.TAG, this.mPuuid + ": Could not init " + this.mServiceName, e);
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
            }

            @Override // android.content.ServiceConnection
            public void onServiceDisconnected(android.content.ComponentName componentName) {
                android.util.Slog.v(com.android.server.soundtrigger.SoundTriggerService.TAG, this.mPuuid + ": onServiceDisconnected");
                com.android.server.soundtrigger.SoundTriggerService.SoundTriggerSessionStub.this.mEventLogger.enqueue(new com.android.server.utils.EventLogger.StringEvent(this.mPuuid + ": onServiceDisconnected"));
                synchronized (this.mRemoteServiceLock) {
                    this.mService = null;
                }
            }

            @Override // android.content.ServiceConnection
            public void onBindingDied(android.content.ComponentName componentName) {
                android.util.Slog.v(com.android.server.soundtrigger.SoundTriggerService.TAG, this.mPuuid + ": onBindingDied");
                com.android.server.soundtrigger.SoundTriggerService.SoundTriggerSessionStub.this.mEventLogger.enqueue(new com.android.server.utils.EventLogger.StringEvent(this.mPuuid + ": onBindingDied"));
                synchronized (this.mRemoteServiceLock) {
                    destroy();
                }
            }

            @Override // android.content.ServiceConnection
            public void onNullBinding(android.content.ComponentName componentName) {
                android.util.Slog.w(com.android.server.soundtrigger.SoundTriggerService.TAG, componentName + " for model " + this.mPuuid + " returned a null binding");
                com.android.server.soundtrigger.SoundTriggerService.SoundTriggerSessionStub.this.mEventLogger.enqueue(new com.android.server.utils.EventLogger.StringEvent(componentName + " for model " + this.mPuuid + " returned a null binding"));
                synchronized (this.mRemoteServiceLock) {
                    disconnectLocked();
                }
            }
        }
    }

    private static class NumOps {

        @com.android.internal.annotations.GuardedBy({"mLock"})
        private long mLastOpsHourSinceBoot;
        private final java.lang.Object mLock;

        @com.android.internal.annotations.GuardedBy({"mLock"})
        private int[] mNumOps;

        private NumOps() {
            this.mLock = new java.lang.Object();
            this.mNumOps = new int[24];
        }

        void clearOldOps(long j) {
            synchronized (this.mLock) {
                try {
                    long convert = java.util.concurrent.TimeUnit.HOURS.convert(j, java.util.concurrent.TimeUnit.NANOSECONDS);
                    if (this.mLastOpsHourSinceBoot != 0) {
                        long j2 = this.mLastOpsHourSinceBoot;
                        while (true) {
                            j2++;
                            if (j2 > convert) {
                                break;
                            } else {
                                this.mNumOps[(int) (j2 % 24)] = 0;
                            }
                        }
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        void addOp(long j) {
            synchronized (this.mLock) {
                long convert = java.util.concurrent.TimeUnit.HOURS.convert(j, java.util.concurrent.TimeUnit.NANOSECONDS);
                int[] iArr = this.mNumOps;
                int i = (int) (convert % 24);
                iArr[i] = iArr[i] + 1;
                this.mLastOpsHourSinceBoot = convert;
            }
        }

        int getOpsAdded() {
            int i;
            synchronized (this.mLock) {
                i = 0;
                for (int i2 = 0; i2 < 24; i2++) {
                    try {
                        i += this.mNumOps[i2];
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
            }
            return i;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class Operation {

        @android.annotation.Nullable
        private final java.lang.Runnable mDropOp;

        @android.annotation.NonNull
        private final com.android.server.soundtrigger.SoundTriggerService.Operation.ExecuteOp mExecuteOp;

        @android.annotation.Nullable
        private final java.lang.Runnable mSetupOp;

        /* JADX INFO: Access modifiers changed from: private */
        interface ExecuteOp {
            void run(int i, android.media.soundtrigger.ISoundTriggerDetectionService iSoundTriggerDetectionService) throws android.os.RemoteException;
        }

        private Operation(@android.annotation.Nullable java.lang.Runnable runnable, @android.annotation.NonNull com.android.server.soundtrigger.SoundTriggerService.Operation.ExecuteOp executeOp, @android.annotation.Nullable java.lang.Runnable runnable2) {
            this.mSetupOp = runnable;
            this.mExecuteOp = executeOp;
            this.mDropOp = runnable2;
        }

        private void setup() {
            if (this.mSetupOp != null) {
                this.mSetupOp.run();
            }
        }

        void run(int i, @android.annotation.NonNull android.media.soundtrigger.ISoundTriggerDetectionService iSoundTriggerDetectionService) throws android.os.RemoteException {
            setup();
            this.mExecuteOp.run(i, iSoundTriggerDetectionService);
        }

        void drop() {
            setup();
            if (this.mDropOp != null) {
                this.mDropOp.run();
            }
        }
    }

    public final class LocalSoundTriggerService implements com.android.server.SoundTriggerInternal {
        private final android.content.Context mContext;

        LocalSoundTriggerService(android.content.Context context) {
            this.mContext = context;
        }

        /* JADX INFO: Access modifiers changed from: private */
        class SessionImpl implements com.android.server.SoundTriggerInternal.Session {
            private final com.android.server.soundtrigger.SoundTriggerService.MyAppOpsListener mAppOpsListener;

            @android.annotation.NonNull
            private final android.os.IBinder mClient;
            private final com.android.server.utils.EventLogger mEventLogger;

            @android.annotation.NonNull
            private final com.android.server.soundtrigger.DeviceStateHandler.DeviceStateListener mListener;
            private final android.util.SparseArray<java.util.UUID> mModelUuid;
            private final android.media.permission.Identity mOriginatorIdentity;

            @android.annotation.NonNull
            private final com.android.server.soundtrigger.SoundTriggerHelper mSoundTriggerHelper;

            private SessionImpl(@android.annotation.NonNull com.android.server.soundtrigger.SoundTriggerHelper soundTriggerHelper, @android.annotation.NonNull android.os.IBinder iBinder, @android.annotation.NonNull com.android.server.utils.EventLogger eventLogger, @android.annotation.NonNull android.media.permission.Identity identity) {
                this.mModelUuid = new android.util.SparseArray<>(1);
                this.mSoundTriggerHelper = soundTriggerHelper;
                this.mClient = iBinder;
                this.mOriginatorIdentity = identity;
                this.mEventLogger = eventLogger;
                com.android.server.soundtrigger.SoundTriggerService.this.mSessionEventLoggers.add(this.mEventLogger);
                try {
                    this.mClient.linkToDeath(new android.os.IBinder.DeathRecipient() { // from class: com.android.server.soundtrigger.SoundTriggerService$LocalSoundTriggerService$SessionImpl$$ExternalSyntheticLambda0
                        @Override // android.os.IBinder.DeathRecipient
                        public final void binderDied() {
                            com.android.server.soundtrigger.SoundTriggerService.LocalSoundTriggerService.SessionImpl.this.lambda$new$0();
                        }
                    }, 0);
                } catch (android.os.RemoteException e) {
                    lambda$new$0();
                }
                this.mListener = new com.android.server.soundtrigger.DeviceStateHandler.DeviceStateListener() { // from class: com.android.server.soundtrigger.SoundTriggerService$LocalSoundTriggerService$SessionImpl$$ExternalSyntheticLambda1
                    @Override // com.android.server.soundtrigger.DeviceStateHandler.DeviceStateListener
                    public final void onSoundTriggerDeviceStateUpdate(com.android.server.soundtrigger.DeviceStateHandler.SoundTriggerDeviceState soundTriggerDeviceState) {
                        com.android.server.soundtrigger.SoundTriggerService.LocalSoundTriggerService.SessionImpl.this.lambda$new$1(soundTriggerDeviceState);
                    }
                };
                com.android.server.soundtrigger.SoundTriggerService soundTriggerService = com.android.server.soundtrigger.SoundTriggerService.this;
                android.media.permission.Identity identity2 = this.mOriginatorIdentity;
                com.android.server.soundtrigger.SoundTriggerHelper soundTriggerHelper2 = this.mSoundTriggerHelper;
                java.util.Objects.requireNonNull(soundTriggerHelper2);
                this.mAppOpsListener = soundTriggerService.new MyAppOpsListener(identity2, new com.android.server.soundtrigger.SoundTriggerService$LocalSoundTriggerService$SessionImpl$$ExternalSyntheticLambda2(soundTriggerHelper2));
                this.mAppOpsListener.forceOpChangeRefresh();
                com.android.server.soundtrigger.SoundTriggerService.this.mAppOpsManager.startWatchingMode("android:record_audio", this.mOriginatorIdentity.packageName, 1, this.mAppOpsListener);
                com.android.server.soundtrigger.SoundTriggerService.this.mDeviceStateHandler.registerListener(this.mListener);
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$new$1(com.android.server.soundtrigger.DeviceStateHandler.SoundTriggerDeviceState soundTriggerDeviceState) {
                this.mSoundTriggerHelper.onDeviceStateChanged(soundTriggerDeviceState);
            }

            @Override // com.android.server.SoundTriggerInternal.Session
            public int startRecognition(int i, android.hardware.soundtrigger.SoundTrigger.KeyphraseSoundModel keyphraseSoundModel, android.hardware.soundtrigger.IRecognitionStatusCallback iRecognitionStatusCallback, android.hardware.soundtrigger.SoundTrigger.RecognitionConfig recognitionConfig, boolean z) {
                this.mModelUuid.put(i, keyphraseSoundModel.getUuid());
                this.mEventLogger.enqueue(new com.android.server.soundtrigger.SoundTriggerEvent.SessionEvent(com.android.server.soundtrigger.SoundTriggerEvent.SessionEvent.Type.START_RECOGNITION, keyphraseSoundModel.getUuid()));
                return this.mSoundTriggerHelper.startKeyphraseRecognition(i, keyphraseSoundModel, iRecognitionStatusCallback, recognitionConfig, z);
            }

            @Override // com.android.server.SoundTriggerInternal.Session
            public synchronized int stopRecognition(int i, android.hardware.soundtrigger.IRecognitionStatusCallback iRecognitionStatusCallback) {
                this.mEventLogger.enqueue(new com.android.server.soundtrigger.SoundTriggerEvent.SessionEvent(com.android.server.soundtrigger.SoundTriggerEvent.SessionEvent.Type.STOP_RECOGNITION, this.mModelUuid.get(i)));
                return this.mSoundTriggerHelper.stopKeyphraseRecognition(i, iRecognitionStatusCallback);
            }

            @Override // com.android.server.SoundTriggerInternal.Session
            public android.hardware.soundtrigger.SoundTrigger.ModuleProperties getModuleProperties() {
                this.mEventLogger.enqueue(new com.android.server.soundtrigger.SoundTriggerEvent.SessionEvent(com.android.server.soundtrigger.SoundTriggerEvent.SessionEvent.Type.GET_MODULE_PROPERTIES, null));
                return this.mSoundTriggerHelper.getModuleProperties();
            }

            @Override // com.android.server.SoundTriggerInternal.Session
            public int setParameter(int i, @android.hardware.soundtrigger.ModelParams int i2, int i3) {
                this.mEventLogger.enqueue(new com.android.server.soundtrigger.SoundTriggerEvent.SessionEvent(com.android.server.soundtrigger.SoundTriggerEvent.SessionEvent.Type.SET_PARAMETER, this.mModelUuid.get(i)));
                return this.mSoundTriggerHelper.setKeyphraseParameter(i, i2, i3);
            }

            @Override // com.android.server.SoundTriggerInternal.Session
            public int getParameter(int i, @android.hardware.soundtrigger.ModelParams int i2) {
                return this.mSoundTriggerHelper.getKeyphraseParameter(i, i2);
            }

            @Override // com.android.server.SoundTriggerInternal.Session
            @android.annotation.Nullable
            public android.hardware.soundtrigger.SoundTrigger.ModelParamRange queryParameter(int i, @android.hardware.soundtrigger.ModelParams int i2) {
                return this.mSoundTriggerHelper.queryKeyphraseParameter(i, i2);
            }

            @Override // com.android.server.SoundTriggerInternal.Session
            public void detach() {
                detachInternal();
            }

            @Override // com.android.server.SoundTriggerInternal.Session
            public int unloadKeyphraseModel(int i) {
                this.mEventLogger.enqueue(new com.android.server.soundtrigger.SoundTriggerEvent.SessionEvent(com.android.server.soundtrigger.SoundTriggerEvent.SessionEvent.Type.UNLOAD_MODEL, this.mModelUuid.get(i)));
                return this.mSoundTriggerHelper.unloadKeyphraseSoundModel(i);
            }

            /* JADX INFO: Access modifiers changed from: private */
            /* renamed from: clientDied, reason: merged with bridge method [inline-methods] */
            public void lambda$new$0() {
                com.android.server.soundtrigger.SoundTriggerService.this.mServiceEventLogger.enqueue(new com.android.server.soundtrigger.SoundTriggerEvent.ServiceEvent(com.android.server.soundtrigger.SoundTriggerEvent.ServiceEvent.Type.DETACH, this.mOriginatorIdentity.packageName, "Client died").printLog(2, com.android.server.soundtrigger.SoundTriggerService.TAG));
                detachInternal();
            }

            private void detachInternal() {
                if (this.mAppOpsListener != null) {
                    com.android.server.soundtrigger.SoundTriggerService.this.mAppOpsManager.stopWatchingMode(this.mAppOpsListener);
                }
                this.mEventLogger.enqueue(new com.android.server.soundtrigger.SoundTriggerEvent.SessionEvent(com.android.server.soundtrigger.SoundTriggerEvent.SessionEvent.Type.DETACH, null));
                com.android.server.soundtrigger.SoundTriggerService.this.detachSessionLogger(this.mEventLogger);
                com.android.server.soundtrigger.SoundTriggerService.this.mDeviceStateHandler.unregisterListener(this.mListener);
                this.mSoundTriggerHelper.detach();
            }
        }

        @Override // com.android.server.SoundTriggerInternal
        public com.android.server.SoundTriggerInternal.Session attach(@android.annotation.NonNull android.os.IBinder iBinder, android.hardware.soundtrigger.SoundTrigger.ModuleProperties moduleProperties, boolean z) {
            android.media.permission.Identity nonNull = android.media.permission.IdentityContext.getNonNull();
            int andIncrement = com.android.server.soundtrigger.SoundTriggerService.this.mSessionIdCounter.getAndIncrement();
            com.android.server.soundtrigger.SoundTriggerService.this.mServiceEventLogger.enqueue(new com.android.server.soundtrigger.SoundTriggerEvent.ServiceEvent(com.android.server.soundtrigger.SoundTriggerEvent.ServiceEvent.Type.ATTACH, nonNull.packageName + "#" + andIncrement));
            com.android.server.utils.EventLogger eventLogger = new com.android.server.utils.EventLogger(128, "LocalSoundTriggerEventLogger for package: " + nonNull.packageName + "#" + andIncrement + " - " + nonNull.uid + "|" + nonNull.pid);
            return new com.android.server.soundtrigger.SoundTriggerService.LocalSoundTriggerService.SessionImpl(com.android.server.soundtrigger.SoundTriggerService.this.newSoundTriggerHelper(moduleProperties, eventLogger, z), iBinder, eventLogger, nonNull);
        }

        @Override // com.android.server.SoundTriggerInternal
        public java.util.List<android.hardware.soundtrigger.SoundTrigger.ModuleProperties> listModuleProperties(android.media.permission.Identity identity) {
            com.android.server.soundtrigger.SoundTriggerService.this.mServiceEventLogger.enqueue(new com.android.server.soundtrigger.SoundTriggerEvent.ServiceEvent(com.android.server.soundtrigger.SoundTriggerEvent.ServiceEvent.Type.LIST_MODULE, identity.packageName));
            android.media.permission.SafeCloseable establishIdentityDirect = android.media.permission.PermissionUtil.establishIdentityDirect(identity);
            try {
                java.util.List<android.hardware.soundtrigger.SoundTrigger.ModuleProperties> lambda$newSoundTriggerHelper$2 = com.android.server.soundtrigger.SoundTriggerService.this.lambda$newSoundTriggerHelper$2(identity);
                if (establishIdentityDirect != null) {
                    establishIdentityDirect.close();
                }
                return lambda$newSoundTriggerHelper$2;
            } catch (java.lang.Throwable th) {
                if (establishIdentityDirect != null) {
                    try {
                        establishIdentityDirect.close();
                    } catch (java.lang.Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }
    }
}
