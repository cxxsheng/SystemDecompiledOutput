package com.android.server.appop;

/* loaded from: classes.dex */
public class AppOpsService extends com.android.internal.app.IAppOpsService.Stub {
    private static final int CURRENT_VERSION = 1;
    static final boolean DEBUG = false;
    private static final int MAX_UNFORWARDED_OPS = 10;
    private static final int MAX_UNUSED_POOLED_OBJECTS = 3;
    private static final int RARELY_USED_PACKAGES_INITIALIZATION_DELAY_MILLIS = 300000;
    static final java.lang.String TAG = "AppOps";
    private static final int UID_ANY = -2;
    static final long WRITE_DELAY = 1800000;

    @com.android.internal.annotations.VisibleForTesting
    com.android.server.appop.AppOpsCheckingServiceInterface mAppOpsCheckingService;

    @com.android.internal.annotations.VisibleForTesting
    com.android.server.appop.AppOpsRestrictions mAppOpsRestrictions;

    @com.android.internal.annotations.GuardedBy({"this"})
    private android.app.RuntimeAppOpAccessMessage mCollectedRuntimePermissionMessage;

    @com.android.internal.annotations.VisibleForTesting
    final com.android.server.appop.AppOpsService.Constants mConstants;
    final android.content.Context mContext;
    boolean mFastWriteScheduled;
    final android.os.Handler mHandler;

    @com.android.internal.annotations.GuardedBy({"this"})
    private float mMessagesCollectedCount;

    @android.annotation.Nullable
    private final java.io.File mNoteOpCallerStacktracesFile;

    @android.annotation.Nullable
    private android.content.pm.PackageManagerInternal mPackageManagerInternal;

    @android.annotation.Nullable
    private com.android.server.pm.PackageManagerLocal mPackageManagerLocal;
    android.util.SparseIntArray mProfileOwners;
    final android.util.AtomicFile mRecentAccessesFile;

    @com.android.internal.annotations.GuardedBy({"this"})
    private int mSamplingStrategy;
    private android.hardware.SensorPrivacyManager mSensorPrivacyManager;
    final android.util.AtomicFile mStorageFile;
    private com.android.server.appop.AppOpsUidStateTracker mUidStateTracker;

    @com.android.internal.annotations.GuardedBy({"this"})
    private boolean mUidStatesInitialized;

    @android.annotation.Nullable
    private com.android.server.pm.UserManagerInternal mUserManagerInternal;

    @android.annotation.Nullable
    private com.android.server.companion.virtual.VirtualDeviceManagerInternal mVirtualDeviceManagerInternal;
    boolean mWriteNoteOpsScheduled;
    boolean mWriteScheduled;
    private static final int[] OPS_RESTRICTED_ON_SUSPEND = {28, 27, 26, 3};
    private static final int[] NON_PACKAGE_UIDS = {0, 1001, 1002, com.android.server.usb.descriptors.UsbTerminalTypes.TERMINAL_BIDIR_SKRPHONE, 1073, 2000};
    private final android.util.ArraySet<com.android.server.appop.AppOpsService.NoteOpTrace> mNoteOpCallerStacktraces = new android.util.ArraySet<>();

    @com.android.internal.annotations.GuardedBy({"this"})
    final com.android.server.appop.AttributedOp.OpEventProxyInfoPool mOpEventProxyInfoPool = new com.android.server.appop.AttributedOp.OpEventProxyInfoPool(3);

    @com.android.internal.annotations.GuardedBy({"this"})
    final com.android.server.appop.AttributedOp.InProgressStartOpEventPool mInProgressStartOpEventPool = new com.android.server.appop.AttributedOp.InProgressStartOpEventPool(this.mOpEventProxyInfoPool, 3);
    private final com.android.server.appop.AppOpsService.AppOpsManagerInternalImpl mAppOpsManagerInternal = new com.android.server.appop.AppOpsService.AppOpsManagerInternalImpl();

    @android.annotation.Nullable
    private final android.app.admin.DevicePolicyManagerInternal dpmi = (android.app.admin.DevicePolicyManagerInternal) com.android.server.LocalServices.getService(android.app.admin.DevicePolicyManagerInternal.class);
    private final android.util.SparseArray<java.lang.String> mKnownDeviceIds = new android.util.SparseArray<>();
    private final com.android.internal.compat.IPlatformCompat mPlatformCompat = com.android.internal.compat.IPlatformCompat.Stub.asInterface(android.os.ServiceManager.getService("platform_compat"));

    @com.android.internal.annotations.GuardedBy({"this"})
    private final android.util.ArrayMap<android.util.Pair<java.lang.String, java.lang.Integer>, android.os.RemoteCallbackList<com.android.internal.app.IAppOpsAsyncNotedCallback>> mAsyncOpWatchers = new android.util.ArrayMap<>();

    @com.android.internal.annotations.GuardedBy({"this"})
    private final android.util.ArrayMap<android.util.Pair<java.lang.String, java.lang.Integer>, java.util.ArrayList<android.app.AsyncNotedAppOp>> mUnforwardedAsyncNotedOps = new android.util.ArrayMap<>();
    private final android.util.SparseArray<android.util.ArraySet<com.android.server.appop.OnOpModeChangedListener>> mOpModeWatchers = new android.util.SparseArray<>();
    private final android.util.ArrayMap<java.lang.String, android.util.ArraySet<com.android.server.appop.OnOpModeChangedListener>> mPackageModeWatchers = new android.util.ArrayMap<>();
    final java.lang.Runnable mWriteRunner = new java.lang.Runnable() { // from class: com.android.server.appop.AppOpsService.1
        @Override // java.lang.Runnable
        public void run() {
            synchronized (com.android.server.appop.AppOpsService.this) {
                com.android.server.appop.AppOpsService.this.mWriteScheduled = false;
                com.android.server.appop.AppOpsService.this.mFastWriteScheduled = false;
                new android.os.AsyncTask<java.lang.Void, java.lang.Void, java.lang.Void>() { // from class: com.android.server.appop.AppOpsService.1.1
                    /* JADX INFO: Access modifiers changed from: protected */
                    @Override // android.os.AsyncTask
                    public java.lang.Void doInBackground(java.lang.Void... voidArr) {
                        com.android.server.appop.AppOpsService.this.writeRecentAccesses();
                        return null;
                    }
                }.executeOnExecutor(android.os.AsyncTask.THREAD_POOL_EXECUTOR, null);
            }
        }
    };

    @com.android.internal.annotations.GuardedBy({"this"})
    @com.android.internal.annotations.VisibleForTesting
    final android.util.SparseArray<com.android.server.appop.AppOpsService.UidState> mUidStates = new android.util.SparseArray<>();

    @android.annotation.NonNull
    volatile com.android.server.appop.HistoricalRegistry mHistoricalRegistry = new com.android.server.appop.HistoricalRegistry(this);
    private final android.util.ArrayMap<android.os.IBinder, com.android.server.appop.AppOpsService.ClientUserRestrictionState> mOpUserRestrictions = new android.util.ArrayMap<>();
    private final android.util.ArrayMap<android.os.IBinder, com.android.server.appop.AppOpsService.ClientGlobalRestrictionState> mOpGlobalRestrictions = new android.util.ArrayMap<>();
    private volatile com.android.server.appop.AppOpsService.CheckOpsDelegateDispatcher mCheckOpsDelegateDispatcher = new com.android.server.appop.AppOpsService.CheckOpsDelegateDispatcher(null, null);
    private final android.util.SparseArray<int[]> mSwitchedOps = new android.util.SparseArray<>();

    @com.android.internal.annotations.GuardedBy({"this"})
    private java.lang.String mSampledPackage = null;

    @com.android.internal.annotations.GuardedBy({"this"})
    private int mSampledAppOpCode = -1;

    @com.android.internal.annotations.GuardedBy({"this"})
    private int mAcceptableLeftDistance = 0;

    @com.android.internal.annotations.GuardedBy({"this"})
    private android.util.ArraySet<java.lang.String> mRarelyUsedPackages = new android.util.ArraySet<>();

    @com.android.internal.annotations.GuardedBy({"this"})
    private com.android.internal.app.IAppOpsCallback mIgnoredCallback = null;
    final android.util.ArrayMap<android.os.IBinder, com.android.server.appop.AppOpsService.ModeCallback> mModeWatchers = new android.util.ArrayMap<>();
    final android.util.ArrayMap<android.os.IBinder, android.util.SparseArray<com.android.server.appop.AppOpsService.ActiveCallback>> mActiveWatchers = new android.util.ArrayMap<>();
    final android.util.ArrayMap<android.os.IBinder, android.util.SparseArray<com.android.server.appop.AppOpsService.StartedCallback>> mStartedWatchers = new android.util.ArrayMap<>();
    final android.util.ArrayMap<android.os.IBinder, android.util.SparseArray<com.android.server.appop.AppOpsService.NotedCallback>> mNotedWatchers = new android.util.ArrayMap<>();
    final com.android.server.appop.AudioRestrictionManager mAudioRestrictionManager = new com.android.server.appop.AudioRestrictionManager();
    private android.content.BroadcastReceiver mOnPackageUpdatedReceiver = new android.content.BroadcastReceiver() { // from class: com.android.server.appop.AppOpsService.3
        @Override // android.content.BroadcastReceiver
        public void onReceive(android.content.Context context, android.content.Intent intent) {
            com.android.server.pm.pkg.AndroidPackage androidPackage;
            java.lang.String action = intent.getAction();
            java.lang.String encodedSchemeSpecificPart = intent.getData().getEncodedSchemeSpecificPart();
            int intExtra = intent.getIntExtra("android.intent.extra.UID", -1);
            if (action.equals("android.intent.action.PACKAGE_ADDED") && !intent.getBooleanExtra("android.intent.extra.REPLACING", false)) {
                boolean isSamplingTarget = com.android.server.appop.AppOpsService.this.isSamplingTarget(com.android.server.appop.AppOpsService.this.getPackageManagerInternal().getPackageInfo(encodedSchemeSpecificPart, 4096L, android.os.Process.myUid(), android.os.UserHandle.getUserId(intExtra)));
                synchronized (com.android.server.appop.AppOpsService.this) {
                    if (isSamplingTarget) {
                        try {
                            com.android.server.appop.AppOpsService.this.mRarelyUsedPackages.add(encodedSchemeSpecificPart);
                        } finally {
                        }
                    }
                    com.android.server.appop.AppOpsService.UidState uidStateLocked = com.android.server.appop.AppOpsService.this.getUidStateLocked(intExtra, true);
                    if (!uidStateLocked.pkgOps.containsKey(encodedSchemeSpecificPart)) {
                        uidStateLocked.pkgOps.put(encodedSchemeSpecificPart, new com.android.server.appop.AppOpsService.Ops(encodedSchemeSpecificPart, uidStateLocked));
                    }
                    com.android.server.appop.AppOpsService.this.createSandboxUidStateIfNotExistsForAppLocked(intExtra, null);
                }
                return;
            }
            if (action.equals("android.intent.action.PACKAGE_REMOVED") && !intent.hasExtra("android.intent.extra.REPLACING")) {
                synchronized (com.android.server.appop.AppOpsService.this) {
                    com.android.server.appop.AppOpsService.this.packageRemovedLocked(intExtra, encodedSchemeSpecificPart);
                }
            } else {
                if (!action.equals("android.intent.action.PACKAGE_REPLACED") || (androidPackage = com.android.server.appop.AppOpsService.this.getPackageManagerInternal().getPackage(encodedSchemeSpecificPart)) == null) {
                    return;
                }
                synchronized (com.android.server.appop.AppOpsService.this) {
                    com.android.server.appop.AppOpsService.this.refreshAttributionsLocked(androidPackage, intExtra);
                }
            }
        }
    };

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: -$$Nest$mnoteProxyOperationImpl, reason: not valid java name */
    public static /* bridge */ /* synthetic */ android.app.SyncNotedAppOp m1641$$Nest$mnoteProxyOperationImpl(com.android.server.appop.AppOpsService appOpsService, int i, android.content.AttributionSource attributionSource, boolean z, java.lang.String str, boolean z2, boolean z3) {
        return appOpsService.noteProxyOperationImpl(i, attributionSource, z, str, z2, z3);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: -$$Nest$mstartOperationImpl, reason: not valid java name */
    public static /* bridge */ /* synthetic */ android.app.SyncNotedAppOp m1649$$Nest$mstartOperationImpl(com.android.server.appop.AppOpsService appOpsService, android.os.IBinder iBinder, int i, int i2, java.lang.String str, java.lang.String str2, int i3, boolean z, boolean z2, java.lang.String str3, boolean z3, int i4, int i5) {
        return appOpsService.startOperationImpl(iBinder, i, i2, str, str2, i3, z, z2, str3, z3, i4, i5);
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public com.android.server.appop.AppOpsUidStateTracker getUidStateTracker() {
        if (this.mUidStateTracker == null) {
            this.mUidStateTracker = new com.android.server.appop.AppOpsUidStateTrackerImpl((android.app.ActivityManagerInternal) com.android.server.LocalServices.getService(android.app.ActivityManagerInternal.class), this.mHandler, new java.util.concurrent.Executor() { // from class: com.android.server.appop.AppOpsService$$ExternalSyntheticLambda6
                @Override // java.util.concurrent.Executor
                public final void execute(java.lang.Runnable runnable) {
                    com.android.server.appop.AppOpsService.this.lambda$getUidStateTracker$0(runnable);
                }
            }, com.android.internal.os.Clock.SYSTEM_CLOCK, this.mConstants);
            this.mUidStateTracker.addUidStateChangedCallback(new android.os.HandlerExecutor(this.mHandler), new com.android.server.appop.AppOpsUidStateTracker.UidStateChangedCallback() { // from class: com.android.server.appop.AppOpsService$$ExternalSyntheticLambda7
                @Override // com.android.server.appop.AppOpsUidStateTracker.UidStateChangedCallback
                public final void onUidStateChanged(int i, int i2, boolean z) {
                    com.android.server.appop.AppOpsService.this.onUidStateChanged(i, i2, z);
                }
            });
        }
        return this.mUidStateTracker;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getUidStateTracker$0(java.lang.Runnable runnable) {
        synchronized (this) {
            runnable.run();
        }
    }

    final class Constants extends android.database.ContentObserver {
        public long BG_STATE_SETTLE_TIME;
        public long FG_SERVICE_STATE_SETTLE_TIME;
        public long TOP_STATE_SETTLE_TIME;
        private final android.util.KeyValueListParser mParser;
        private android.content.ContentResolver mResolver;

        public Constants(android.os.Handler handler) {
            super(handler);
            this.mParser = new android.util.KeyValueListParser(',');
            updateConstants();
        }

        public void startMonitoring(android.content.ContentResolver contentResolver) {
            this.mResolver = contentResolver;
            this.mResolver.registerContentObserver(android.provider.Settings.Global.getUriFor("app_ops_constants"), false, this);
            updateConstants();
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z, android.net.Uri uri) {
            updateConstants();
        }

        private void updateConstants() {
            java.lang.String string = this.mResolver != null ? android.provider.Settings.Global.getString(this.mResolver, "app_ops_constants") : "";
            synchronized (com.android.server.appop.AppOpsService.this) {
                try {
                    this.mParser.setString(string);
                } catch (java.lang.IllegalArgumentException e) {
                    android.util.Slog.e(com.android.server.appop.AppOpsService.TAG, "Bad app ops settings", e);
                }
                this.TOP_STATE_SETTLE_TIME = this.mParser.getDurationMillis("top_state_settle_time", 5000L);
                this.FG_SERVICE_STATE_SETTLE_TIME = this.mParser.getDurationMillis("fg_service_state_settle_time", 5000L);
                this.BG_STATE_SETTLE_TIME = this.mParser.getDurationMillis("bg_state_settle_time", 1000L);
            }
        }

        void dump(java.io.PrintWriter printWriter) {
            printWriter.println("  Settings:");
            printWriter.print("    ");
            printWriter.print("top_state_settle_time");
            printWriter.print("=");
            android.util.TimeUtils.formatDuration(this.TOP_STATE_SETTLE_TIME, printWriter);
            printWriter.println();
            printWriter.print("    ");
            printWriter.print("fg_service_state_settle_time");
            printWriter.print("=");
            android.util.TimeUtils.formatDuration(this.FG_SERVICE_STATE_SETTLE_TIME, printWriter);
            printWriter.println();
            printWriter.print("    ");
            printWriter.print("bg_state_settle_time");
            printWriter.print("=");
            android.util.TimeUtils.formatDuration(this.BG_STATE_SETTLE_TIME, printWriter);
            printWriter.println();
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    final class UidState {

        @android.annotation.NonNull
        public final android.util.ArrayMap<java.lang.String, com.android.server.appop.AppOpsService.Ops> pkgOps = new android.util.ArrayMap<>();
        public final int uid;

        public UidState(int i) {
            this.uid = i;
        }

        public void clear() {
            com.android.server.appop.AppOpsService.this.mAppOpsCheckingService.removeUid(this.uid);
            for (int i = 0; i < this.pkgOps.size(); i++) {
                com.android.server.appop.AppOpsService.this.mAppOpsCheckingService.removePackage(this.pkgOps.keyAt(i), android.os.UserHandle.getUserId(this.uid));
            }
        }

        int evalMode(int i, int i2) {
            return com.android.server.appop.AppOpsService.this.getUidStateTracker().evalMode(this.uid, i, i2);
        }

        public int getState() {
            return com.android.server.appop.AppOpsService.this.getUidStateTracker().getUidState(this.uid);
        }

        public void dump(java.io.PrintWriter printWriter, long j) {
            com.android.server.appop.AppOpsService.this.getUidStateTracker().dumpUidState(printWriter, this.uid, j);
        }
    }

    static final class Ops extends android.util.SparseArray<com.android.server.appop.AppOpsService.Op> {

        @android.annotation.Nullable
        android.app.AppOpsManager.RestrictionBypass bypass;
        final java.lang.String packageName;
        final com.android.server.appop.AppOpsService.UidState uidState;

        @android.annotation.NonNull
        final android.util.ArraySet<java.lang.String> knownAttributionTags = new android.util.ArraySet<>();

        @android.annotation.NonNull
        final android.util.ArraySet<java.lang.String> validAttributionTags = new android.util.ArraySet<>();

        Ops(java.lang.String str, com.android.server.appop.AppOpsService.UidState uidState) {
            this.packageName = str;
            this.uidState = uidState;
        }
    }

    private static final class PackageVerificationResult {
        final android.app.AppOpsManager.RestrictionBypass bypass;
        final boolean isAttributionTagValid;

        PackageVerificationResult(android.app.AppOpsManager.RestrictionBypass restrictionBypass, boolean z) {
            this.bypass = restrictionBypass;
            this.isAttributionTagValid = z;
        }
    }

    final class Op {
        final android.util.ArrayMap<java.lang.String, android.util.ArrayMap<java.lang.String, com.android.server.appop.AttributedOp>> mDeviceAttributedOps = new android.util.ArrayMap<>(1);
        int op;

        @android.annotation.NonNull
        final java.lang.String packageName;
        int uid;
        final com.android.server.appop.AppOpsService.UidState uidState;

        Op(com.android.server.appop.AppOpsService.UidState uidState, java.lang.String str, int i, int i2) {
            this.op = i;
            this.uid = i2;
            this.uidState = uidState;
            this.packageName = str;
            this.mDeviceAttributedOps.put("default:0", new android.util.ArrayMap<>());
        }

        void removeAttributionsWithNoTime() {
            for (int size = this.mDeviceAttributedOps.size() - 1; size >= 0; size--) {
                android.util.ArrayMap<java.lang.String, com.android.server.appop.AttributedOp> valueAt = this.mDeviceAttributedOps.valueAt(size);
                for (int size2 = valueAt.size() - 1; size2 >= 0; size2--) {
                    if (!valueAt.valueAt(size2).hasAnyTime()) {
                        valueAt.removeAt(size2);
                    }
                }
                if (!java.util.Objects.equals("default:0", this.mDeviceAttributedOps.keyAt(size)) && valueAt.isEmpty()) {
                    this.mDeviceAttributedOps.removeAt(size);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        @android.annotation.NonNull
        public com.android.server.appop.AttributedOp getOrCreateAttribution(@android.annotation.NonNull com.android.server.appop.AppOpsService.Op op, @android.annotation.Nullable java.lang.String str, java.lang.String str2) {
            android.util.ArrayMap<java.lang.String, com.android.server.appop.AttributedOp> arrayMap = this.mDeviceAttributedOps.get(str2);
            if (arrayMap == null) {
                arrayMap = new android.util.ArrayMap<>();
                this.mDeviceAttributedOps.put(str2, arrayMap);
            }
            com.android.server.appop.AttributedOp attributedOp = arrayMap.get(str);
            if (attributedOp == null) {
                com.android.server.appop.AttributedOp attributedOp2 = new com.android.server.appop.AttributedOp(com.android.server.appop.AppOpsService.this, str, str2, op);
                arrayMap.put(str, attributedOp2);
                return attributedOp2;
            }
            return attributedOp;
        }

        @android.annotation.NonNull
        android.app.AppOpsManager.OpEntry createEntryLocked(java.lang.String str) {
            android.util.ArrayMap<java.lang.String, com.android.server.appop.AttributedOp> arrayMap = this.mDeviceAttributedOps.get(str);
            if (arrayMap == null) {
                arrayMap = new android.util.ArrayMap<>();
            }
            android.util.ArrayMap arrayMap2 = new android.util.ArrayMap(arrayMap.size());
            for (int i = 0; i < arrayMap.size(); i++) {
                arrayMap2.put(arrayMap.keyAt(i), arrayMap.valueAt(i).createAttributedOpEntryLocked());
            }
            return new android.app.AppOpsManager.OpEntry(this.op, com.android.server.appop.AppOpsService.this.mAppOpsCheckingService.getPackageMode(this.packageName, this.op, android.os.UserHandle.getUserId(this.uid)), arrayMap2);
        }

        @android.annotation.NonNull
        android.app.AppOpsManager.OpEntry createSingleAttributionEntryLocked(@android.annotation.Nullable java.lang.String str) {
            android.util.ArrayMap<java.lang.String, com.android.server.appop.AttributedOp> arrayMap = this.mDeviceAttributedOps.get("default:0");
            if (arrayMap == null) {
                arrayMap = new android.util.ArrayMap<>();
            }
            android.util.ArrayMap arrayMap2 = new android.util.ArrayMap(1);
            if (arrayMap.get(str) != null) {
                arrayMap2.put(str, arrayMap.get(str).createAttributedOpEntryLocked());
            }
            return new android.app.AppOpsManager.OpEntry(this.op, com.android.server.appop.AppOpsService.this.mAppOpsCheckingService.getPackageMode(this.packageName, this.op, android.os.UserHandle.getUserId(this.uid)), arrayMap2);
        }

        boolean isRunning() {
            for (int i = 0; i < this.mDeviceAttributedOps.size(); i++) {
                android.util.ArrayMap<java.lang.String, com.android.server.appop.AttributedOp> valueAt = this.mDeviceAttributedOps.valueAt(i);
                for (int i2 = 0; i2 < valueAt.size(); i2++) {
                    if (valueAt.valueAt(i2).isRunning()) {
                        return true;
                    }
                }
            }
            return false;
        }
    }

    final class ModeCallback extends com.android.server.appop.OnOpModeChangedListener implements android.os.IBinder.DeathRecipient {
        public static final int ALL_OPS = -2;
        private final com.android.internal.app.IAppOpsCallback mCallback;

        ModeCallback(com.android.internal.app.IAppOpsCallback iAppOpsCallback, int i, int i2, int i3, int i4, int i5) {
            super(i, i2, i3, i4, i5);
            this.mCallback = iAppOpsCallback;
            try {
                this.mCallback.asBinder().linkToDeath(this, 0);
            } catch (android.os.RemoteException e) {
            }
        }

        @Override // com.android.server.appop.OnOpModeChangedListener
        public java.lang.String toString() {
            java.lang.StringBuilder sb = new java.lang.StringBuilder(128);
            sb.append("ModeCallback{");
            sb.append(java.lang.Integer.toHexString(java.lang.System.identityHashCode(this)));
            sb.append(" watchinguid=");
            android.os.UserHandle.formatUid(sb, getWatchingUid());
            sb.append(" flags=0x");
            sb.append(java.lang.Integer.toHexString(getFlags()));
            switch (getWatchedOpCode()) {
                case -2:
                    sb.append(" op=(all)");
                    break;
                case -1:
                    break;
                default:
                    sb.append(" op=");
                    sb.append(android.app.AppOpsManager.opToName(getWatchedOpCode()));
                    break;
            }
            sb.append(" from uid=");
            android.os.UserHandle.formatUid(sb, getCallingUid());
            sb.append(" pid=");
            sb.append(getCallingPid());
            sb.append('}');
            return sb.toString();
        }

        void unlinkToDeath() {
            this.mCallback.asBinder().unlinkToDeath(this, 0);
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            com.android.server.appop.AppOpsService.this.stopWatchingMode(this.mCallback);
        }

        @Override // com.android.server.appop.OnOpModeChangedListener
        public void onOpModeChanged(int i, int i2, java.lang.String str) throws android.os.RemoteException {
            throw new java.lang.IllegalStateException("unimplemented onOpModeChanged method called for op: " + i + " uid: " + i2 + " packageName: " + str);
        }

        @Override // com.android.server.appop.OnOpModeChangedListener
        public void onOpModeChanged(int i, int i2, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            this.mCallback.opChanged(i, i2, str, str2);
        }
    }

    final class ActiveCallback implements android.os.IBinder.DeathRecipient {
        final com.android.internal.app.IAppOpsActiveCallback mCallback;
        final int mCallingPid;
        final int mCallingUid;
        final int mWatchingUid;

        ActiveCallback(com.android.internal.app.IAppOpsActiveCallback iAppOpsActiveCallback, int i, int i2, int i3) {
            this.mCallback = iAppOpsActiveCallback;
            this.mWatchingUid = i;
            this.mCallingUid = i2;
            this.mCallingPid = i3;
            try {
                this.mCallback.asBinder().linkToDeath(this, 0);
            } catch (android.os.RemoteException e) {
            }
        }

        public java.lang.String toString() {
            java.lang.StringBuilder sb = new java.lang.StringBuilder(128);
            sb.append("ActiveCallback{");
            sb.append(java.lang.Integer.toHexString(java.lang.System.identityHashCode(this)));
            sb.append(" watchinguid=");
            android.os.UserHandle.formatUid(sb, this.mWatchingUid);
            sb.append(" from uid=");
            android.os.UserHandle.formatUid(sb, this.mCallingUid);
            sb.append(" pid=");
            sb.append(this.mCallingPid);
            sb.append('}');
            return sb.toString();
        }

        void destroy() {
            this.mCallback.asBinder().unlinkToDeath(this, 0);
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            com.android.server.appop.AppOpsService.this.stopWatchingActive(this.mCallback);
        }
    }

    final class StartedCallback implements android.os.IBinder.DeathRecipient {
        final com.android.internal.app.IAppOpsStartedCallback mCallback;
        final int mCallingPid;
        final int mCallingUid;
        final int mWatchingUid;

        StartedCallback(com.android.internal.app.IAppOpsStartedCallback iAppOpsStartedCallback, int i, int i2, int i3) {
            this.mCallback = iAppOpsStartedCallback;
            this.mWatchingUid = i;
            this.mCallingUid = i2;
            this.mCallingPid = i3;
            try {
                this.mCallback.asBinder().linkToDeath(this, 0);
            } catch (android.os.RemoteException e) {
            }
        }

        public java.lang.String toString() {
            java.lang.StringBuilder sb = new java.lang.StringBuilder(128);
            sb.append("StartedCallback{");
            sb.append(java.lang.Integer.toHexString(java.lang.System.identityHashCode(this)));
            sb.append(" watchinguid=");
            android.os.UserHandle.formatUid(sb, this.mWatchingUid);
            sb.append(" from uid=");
            android.os.UserHandle.formatUid(sb, this.mCallingUid);
            sb.append(" pid=");
            sb.append(this.mCallingPid);
            sb.append('}');
            return sb.toString();
        }

        void destroy() {
            this.mCallback.asBinder().unlinkToDeath(this, 0);
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            com.android.server.appop.AppOpsService.this.stopWatchingStarted(this.mCallback);
        }
    }

    final class NotedCallback implements android.os.IBinder.DeathRecipient {
        final com.android.internal.app.IAppOpsNotedCallback mCallback;
        final int mCallingPid;
        final int mCallingUid;
        final int mWatchingUid;

        NotedCallback(com.android.internal.app.IAppOpsNotedCallback iAppOpsNotedCallback, int i, int i2, int i3) {
            this.mCallback = iAppOpsNotedCallback;
            this.mWatchingUid = i;
            this.mCallingUid = i2;
            this.mCallingPid = i3;
            try {
                this.mCallback.asBinder().linkToDeath(this, 0);
            } catch (android.os.RemoteException e) {
            }
        }

        public java.lang.String toString() {
            java.lang.StringBuilder sb = new java.lang.StringBuilder(128);
            sb.append("NotedCallback{");
            sb.append(java.lang.Integer.toHexString(java.lang.System.identityHashCode(this)));
            sb.append(" watchinguid=");
            android.os.UserHandle.formatUid(sb, this.mWatchingUid);
            sb.append(" from uid=");
            android.os.UserHandle.formatUid(sb, this.mCallingUid);
            sb.append(" pid=");
            sb.append(this.mCallingPid);
            sb.append('}');
            return sb.toString();
        }

        void destroy() {
            this.mCallback.asBinder().unlinkToDeath(this, 0);
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            com.android.server.appop.AppOpsService.this.stopWatchingNoted(this.mCallback);
        }
    }

    static void onClientDeath(@android.annotation.NonNull com.android.server.appop.AttributedOp attributedOp, @android.annotation.NonNull android.os.IBinder iBinder) {
        attributedOp.onClientDeath(iBinder);
    }

    private void readNoteOpCallerStackTraces() {
        try {
            if (!this.mNoteOpCallerStacktracesFile.exists()) {
                this.mNoteOpCallerStacktracesFile.createNewFile();
                return;
            }
            java.util.Scanner scanner = new java.util.Scanner(this.mNoteOpCallerStacktracesFile);
            try {
                scanner.useDelimiter("\\},");
                while (scanner.hasNext()) {
                    this.mNoteOpCallerStacktraces.add(com.android.server.appop.AppOpsService.NoteOpTrace.fromJson(scanner.next()));
                }
                scanner.close();
            } finally {
            }
        } catch (java.lang.Exception e) {
            android.util.Slog.e(TAG, "Cannot parse traces noteOps", e);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    public AppOpsService(java.io.File file, java.io.File file2, android.os.Handler handler, android.content.Context context) {
        this.mContext = context;
        this.mKnownDeviceIds.put(0, "default:0");
        for (int i = 0; i < 148; i++) {
            int opToSwitch = android.app.AppOpsManager.opToSwitch(i);
            this.mSwitchedOps.put(opToSwitch, com.android.internal.util.ArrayUtils.appendInt(this.mSwitchedOps.get(opToSwitch), i));
        }
        if (android.permission.PermissionManager.USE_ACCESS_CHECKING_SERVICE) {
            this.mAppOpsCheckingService = new com.android.server.appop.AppOpsCheckingServiceTracingDecorator((com.android.server.appop.AppOpsCheckingServiceInterface) com.android.server.LocalServices.getService(com.android.server.appop.AppOpsCheckingServiceInterface.class));
        } else {
            this.mAppOpsCheckingService = new com.android.server.appop.AppOpsCheckingServiceTracingDecorator(new com.android.server.appop.AppOpsCheckingServiceImpl(file2, this, handler, context, this.mSwitchedOps));
        }
        this.mAppOpsCheckingService.addAppOpsModeChangedListener(new com.android.server.appop.AppOpsService.AnonymousClass2());
        this.mAppOpsRestrictions = new com.android.server.appop.AppOpsRestrictionsImpl(context, handler, new com.android.server.appop.AppOpsRestrictions.AppOpsRestrictionRemovedListener() { // from class: com.android.server.appop.AppOpsService$$ExternalSyntheticLambda12
            @Override // com.android.server.appop.AppOpsRestrictions.AppOpsRestrictionRemovedListener
            public final void onAppOpsRestrictionRemoved(int i2) {
                com.android.server.appop.AppOpsService.this.lambda$new$1(i2);
            }
        });
        com.android.server.LockGuard.installLock(this, 0);
        this.mStorageFile = new android.util.AtomicFile(file2, "appops_legacy");
        this.mRecentAccessesFile = new android.util.AtomicFile(file, "appops_accesses");
        this.mNoteOpCallerStacktracesFile = null;
        this.mHandler = handler;
        this.mConstants = new com.android.server.appop.AppOpsService.Constants(this.mHandler);
        readRecentAccesses();
        this.mAppOpsCheckingService.readState();
    }

    /* renamed from: com.android.server.appop.AppOpsService$2, reason: invalid class name */
    class AnonymousClass2 implements com.android.server.appop.AppOpsCheckingServiceInterface.AppOpsModeChangedListener {
        AnonymousClass2() {
        }

        @Override // com.android.server.appop.AppOpsCheckingServiceInterface.AppOpsModeChangedListener
        public void onUidModeChanged(int i, int i2, int i3, java.lang.String str) {
            com.android.server.appop.AppOpsService.this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.QuintConsumer() { // from class: com.android.server.appop.AppOpsService$2$$ExternalSyntheticLambda1
                public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3, java.lang.Object obj4, java.lang.Object obj5) {
                    ((com.android.server.appop.AppOpsService) obj).notifyOpChangedForAllPkgsInUid(((java.lang.Integer) obj2).intValue(), ((java.lang.Integer) obj3).intValue(), ((java.lang.Boolean) obj4).booleanValue(), (java.lang.String) obj5);
                }
            }, com.android.server.appop.AppOpsService.this, java.lang.Integer.valueOf(i2), java.lang.Integer.valueOf(i), false, str));
        }

        @Override // com.android.server.appop.AppOpsCheckingServiceInterface.AppOpsModeChangedListener
        public void onPackageModeChanged(java.lang.String str, int i, int i2, int i3) {
            com.android.server.appop.AppOpsService.this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.QuintConsumer() { // from class: com.android.server.appop.AppOpsService$2$$ExternalSyntheticLambda0
                public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3, java.lang.Object obj4, java.lang.Object obj5) {
                    ((com.android.server.appop.AppOpsService) obj).notifyOpChangedForPkg((java.lang.String) obj2, ((java.lang.Integer) obj3).intValue(), ((java.lang.Integer) obj4).intValue(), ((java.lang.Integer) obj5).intValue());
                }
            }, com.android.server.appop.AppOpsService.this, str, java.lang.Integer.valueOf(i2), java.lang.Integer.valueOf(i3), java.lang.Integer.valueOf(i)));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$1(int i) {
        notifyWatchersOnDefaultDevice(i, -2);
    }

    public void publish() {
        android.os.ServiceManager.addService("appops", asBinder());
        com.android.server.LocalServices.addService(android.app.AppOpsManagerInternal.class, this.mAppOpsManagerInternal);
        com.android.server.LocalManagerRegistry.addManager(com.android.server.appop.AppOpsManagerLocal.class, new com.android.server.appop.AppOpsService.AppOpsManagerLocalImpl());
    }

    public void systemReady() {
        this.mVirtualDeviceManagerInternal = (com.android.server.companion.virtual.VirtualDeviceManagerInternal) com.android.server.LocalServices.getService(com.android.server.companion.virtual.VirtualDeviceManagerInternal.class);
        this.mAppOpsCheckingService.systemReady();
        initializeUidStates();
        this.mConstants.startMonitoring(this.mContext.getContentResolver());
        this.mHistoricalRegistry.systemReady(this.mContext.getContentResolver());
        android.content.IntentFilter intentFilter = new android.content.IntentFilter();
        intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
        intentFilter.addAction("android.intent.action.PACKAGE_REPLACED");
        intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
        intentFilter.addDataScheme(com.android.server.pm.Settings.ATTR_PACKAGE);
        this.mContext.registerReceiverAsUser(this.mOnPackageUpdatedReceiver, android.os.UserHandle.ALL, intentFilter, null, null);
        prepareInternalCallbacks();
        android.content.IntentFilter intentFilter2 = new android.content.IntentFilter();
        intentFilter2.addAction("android.intent.action.PACKAGES_UNSUSPENDED");
        intentFilter2.addAction("android.intent.action.PACKAGES_SUSPENDED");
        this.mContext.registerReceiverAsUser(new android.content.BroadcastReceiver() { // from class: com.android.server.appop.AppOpsService.4
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context, android.content.Intent intent) {
                java.util.Set<java.lang.String> arraySet;
                int[] intArrayExtra = intent.getIntArrayExtra("android.intent.extra.changed_uid_list");
                java.lang.String[] stringArrayExtra = intent.getStringArrayExtra("android.intent.extra.changed_package_list");
                for (int i : com.android.server.appop.AppOpsService.OPS_RESTRICTED_ON_SUSPEND) {
                    synchronized (com.android.server.appop.AppOpsService.this) {
                        try {
                            android.util.ArraySet arraySet2 = (android.util.ArraySet) com.android.server.appop.AppOpsService.this.mOpModeWatchers.get(i);
                            if (arraySet2 != null) {
                                for (int i2 = 0; i2 < intArrayExtra.length; i2++) {
                                    int i3 = intArrayExtra[i2];
                                    java.lang.String str = stringArrayExtra[i2];
                                    if (com.android.server.appop.AppOpsService.this.mVirtualDeviceManagerInternal != null) {
                                        arraySet = com.android.server.appop.AppOpsService.this.mVirtualDeviceManagerInternal.getAllPersistentDeviceIds();
                                    } else {
                                        arraySet = new android.util.ArraySet<>();
                                        arraySet.add("default:0");
                                    }
                                    java.util.Iterator<java.lang.String> it = arraySet.iterator();
                                    while (it.hasNext()) {
                                        com.android.server.appop.AppOpsService.this.notifyOpChanged((android.util.ArraySet<com.android.server.appop.OnOpModeChangedListener>) arraySet2, i, i3, str, it.next());
                                    }
                                }
                            }
                        } finally {
                        }
                    }
                }
            }
        }, android.os.UserHandle.ALL, intentFilter2, null, null);
        this.mHandler.postDelayed(new java.lang.Runnable() { // from class: com.android.server.appop.AppOpsService.5
            @Override // java.lang.Runnable
            public void run() {
                com.android.server.appop.AppOpsService.this.initializeRarelyUsedPackagesList(new android.util.ArraySet(com.android.server.appop.AppOpsService.this.getPackageListAndResample()));
            }
        }, com.android.server.backup.BackupAgentTimeoutParameters.DEFAULT_FULL_BACKUP_AGENT_TIMEOUT_MILLIS);
        getPackageManagerInternal().setExternalSourcesPolicy(new android.content.pm.PackageManagerInternal.ExternalSourcesPolicy() { // from class: com.android.server.appop.AppOpsService.6
            @Override // android.content.pm.PackageManagerInternal.ExternalSourcesPolicy
            public int getPackageTrustedToInstallApps(java.lang.String str, int i) {
                switch (com.android.server.appop.AppOpsService.this.checkOperation(66, i, str)) {
                    case 0:
                        return 0;
                    case 1:
                    default:
                        return 2;
                    case 2:
                        return 1;
                }
            }
        });
        this.mSensorPrivacyManager = android.hardware.SensorPrivacyManager.getInstance(this.mContext);
    }

    @com.android.internal.annotations.VisibleForTesting
    void prepareInternalCallbacks() {
        getUserManagerInternal().addUserLifecycleListener(new com.android.server.pm.UserManagerInternal.UserLifecycleListener() { // from class: com.android.server.appop.AppOpsService.7
            @Override // com.android.server.pm.UserManagerInternal.UserLifecycleListener
            public void onUserCreated(android.content.pm.UserInfo userInfo, java.lang.Object obj) {
                com.android.server.appop.AppOpsService.this.initializeUserUidStates(userInfo.id);
            }
        });
    }

    @com.android.internal.annotations.VisibleForTesting
    void initializeUidStates() {
        com.android.server.pm.UserManagerInternal userManagerInternal = getUserManagerInternal();
        synchronized (this) {
            try {
                android.util.SparseBooleanArray sparseBooleanArray = new android.util.SparseBooleanArray();
                for (int i : NON_PACKAGE_UIDS) {
                    if (!this.mUidStates.contains(i)) {
                        this.mUidStates.put(i, new com.android.server.appop.AppOpsService.UidState(i));
                    }
                    sparseBooleanArray.put(i, true);
                }
                int[] userIds = userManagerInternal.getUserIds();
                com.android.server.pm.PackageManagerLocal.UnfilteredSnapshot withUnfilteredSnapshot = getPackageManagerLocal().withUnfilteredSnapshot();
                try {
                    java.util.Map<java.lang.String, com.android.server.pm.pkg.PackageState> packageStates = withUnfilteredSnapshot.getPackageStates();
                    for (int i2 : userIds) {
                        initializeUserUidStatesLocked(i2, packageStates, sparseBooleanArray);
                    }
                    trimUidStatesLocked(sparseBooleanArray, packageStates);
                    this.mUidStatesInitialized = true;
                    withUnfilteredSnapshot.close();
                } finally {
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initializeUserUidStates(int i) {
        synchronized (this) {
            com.android.server.pm.PackageManagerLocal.UnfilteredSnapshot withUnfilteredSnapshot = getPackageManagerLocal().withUnfilteredSnapshot();
            try {
                initializeUserUidStatesLocked(i, withUnfilteredSnapshot.getPackageStates(), null);
                withUnfilteredSnapshot.close();
            } finally {
            }
        }
    }

    private void initializeUserUidStatesLocked(int i, java.util.Map<java.lang.String, com.android.server.pm.pkg.PackageState> map, android.util.SparseBooleanArray sparseBooleanArray) {
        for (java.util.Map.Entry<java.lang.String, com.android.server.pm.pkg.PackageState> entry : map.entrySet()) {
            com.android.server.pm.pkg.PackageState value = entry.getValue();
            if (!value.isApex()) {
                initializePackageUidStateLocked(i, value.getAppId(), entry.getKey(), sparseBooleanArray);
            }
        }
    }

    private void initializePackageUidStateLocked(int i, int i2, java.lang.String str, android.util.SparseBooleanArray sparseBooleanArray) {
        com.android.server.appop.AppOpsService.Ops ops;
        int uid = android.os.UserHandle.getUid(i, i2);
        if (sparseBooleanArray != null) {
            sparseBooleanArray.put(uid, true);
        }
        com.android.server.appop.AppOpsService.UidState uidStateLocked = getUidStateLocked(uid, true);
        com.android.server.appop.AppOpsService.Ops ops2 = uidStateLocked.pkgOps.get(str);
        if (ops2 != null) {
            ops = ops2;
        } else {
            com.android.server.appop.AppOpsService.Ops ops3 = new com.android.server.appop.AppOpsService.Ops(str, uidStateLocked);
            uidStateLocked.pkgOps.put(str, ops3);
            ops = ops3;
        }
        android.util.SparseIntArray nonDefaultPackageModes = this.mAppOpsCheckingService.getNonDefaultPackageModes(str, i);
        for (int i3 = 0; i3 < nonDefaultPackageModes.size(); i3++) {
            int keyAt = nonDefaultPackageModes.keyAt(i3);
            if (ops.indexOfKey(keyAt) < 0) {
                ops.put(keyAt, new com.android.server.appop.AppOpsService.Op(uidStateLocked, str, keyAt, uid));
            }
        }
        createSandboxUidStateIfNotExistsForAppLocked(uid, sparseBooleanArray);
    }

    private void trimUidStatesLocked(android.util.SparseBooleanArray sparseBooleanArray, java.util.Map<java.lang.String, com.android.server.pm.pkg.PackageState> map) {
        synchronized (this) {
            try {
                for (int size = this.mUidStates.size() - 1; size >= 0; size--) {
                    int keyAt = this.mUidStates.keyAt(size);
                    if (sparseBooleanArray.get(keyAt, false)) {
                        if (keyAt >= 10000) {
                            android.util.ArrayMap<java.lang.String, com.android.server.appop.AppOpsService.Ops> arrayMap = this.mUidStates.valueAt(size).pkgOps;
                            for (int size2 = arrayMap.size() - 1; size2 >= 0; size2--) {
                                java.lang.String keyAt2 = arrayMap.keyAt(size2);
                                if (!map.containsKey(keyAt2)) {
                                    arrayMap.removeAt(size2);
                                } else {
                                    com.android.server.pm.pkg.AndroidPackage androidPackage = map.get(keyAt2).getAndroidPackage();
                                    if (androidPackage != null) {
                                        refreshAttributionsLocked(androidPackage, keyAt);
                                    }
                                }
                            }
                            if (arrayMap.isEmpty()) {
                                this.mUidStates.removeAt(size);
                            }
                        }
                    } else {
                        this.mUidStates.removeAt(size);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"this"})
    public void refreshAttributionsLocked(com.android.server.pm.pkg.AndroidPackage androidPackage, int i) {
        com.android.server.appop.AppOpsService.Ops ops;
        java.lang.String packageName = androidPackage.getPackageName();
        android.util.ArrayMap arrayMap = new android.util.ArrayMap();
        android.util.ArraySet arraySet = new android.util.ArraySet();
        arraySet.add(null);
        if (androidPackage.getAttributions() != null) {
            int size = androidPackage.getAttributions().size();
            for (int i2 = 0; i2 < size; i2++) {
                com.android.internal.pm.pkg.component.ParsedAttribution parsedAttribution = (com.android.internal.pm.pkg.component.ParsedAttribution) androidPackage.getAttributions().get(i2);
                arraySet.add(parsedAttribution.getTag());
                int size2 = parsedAttribution.getInheritFrom().size();
                for (int i3 = 0; i3 < size2; i3++) {
                    arrayMap.put((java.lang.String) parsedAttribution.getInheritFrom().get(i3), parsedAttribution.getTag());
                }
            }
        }
        com.android.server.appop.AppOpsService.UidState uidState = this.mUidStates.get(i);
        if (uidState == null || (ops = uidState.pkgOps.get(packageName)) == null) {
            return;
        }
        ops.bypass = null;
        ops.knownAttributionTags.clear();
        int size3 = ops.size();
        for (int i4 = 0; i4 < size3; i4++) {
            com.android.server.appop.AppOpsService.Op valueAt = ops.valueAt(i4);
            for (int size4 = valueAt.mDeviceAttributedOps.size() - 1; size4 >= 0; size4--) {
                android.util.ArrayMap<java.lang.String, com.android.server.appop.AttributedOp> valueAt2 = valueAt.mDeviceAttributedOps.valueAt(size4);
                for (int size5 = valueAt2.size() - 1; size5 >= 0; size5--) {
                    java.lang.String keyAt = valueAt2.keyAt(size5);
                    if (!arraySet.contains(keyAt)) {
                        valueAt.getOrCreateAttribution(valueAt, (java.lang.String) arrayMap.get(keyAt), valueAt.mDeviceAttributedOps.keyAt(size4)).add(valueAt2.get(keyAt));
                        valueAt2.remove(keyAt);
                        scheduleFastWriteLocked();
                    }
                }
            }
        }
    }

    public void setAppOpsPolicy(@android.annotation.Nullable android.app.AppOpsManagerInternal.CheckOpsDelegate checkOpsDelegate) {
        com.android.server.appop.AppOpsService.CheckOpsDelegateDispatcher checkOpsDelegateDispatcher = this.mCheckOpsDelegateDispatcher;
        this.mCheckOpsDelegateDispatcher = new com.android.server.appop.AppOpsService.CheckOpsDelegateDispatcher(checkOpsDelegate, checkOpsDelegateDispatcher != null ? checkOpsDelegateDispatcher.mCheckOpsDelegate : null);
    }

    @com.android.internal.annotations.VisibleForTesting
    void packageRemoved(int i, java.lang.String str) {
        synchronized (this) {
            packageRemovedLocked(i, str);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"this"})
    public void packageRemovedLocked(int i, java.lang.String str) {
        com.android.server.appop.AppOpsService.UidState uidState = this.mUidStates.get(i);
        if (uidState == null) {
            return;
        }
        com.android.server.appop.AppOpsService.Ops remove = uidState.pkgOps.remove(str);
        this.mAppOpsCheckingService.removePackage(str, android.os.UserHandle.getUserId(i));
        if (remove != null) {
            scheduleFastWriteLocked();
            int size = remove.size();
            for (int i2 = 0; i2 < size; i2++) {
                com.android.server.appop.AppOpsService.Op valueAt = remove.valueAt(i2);
                for (int i3 = 0; i3 < valueAt.mDeviceAttributedOps.size(); i3++) {
                    android.util.ArrayMap<java.lang.String, com.android.server.appop.AttributedOp> valueAt2 = valueAt.mDeviceAttributedOps.valueAt(i3);
                    for (int i4 = 0; i4 < valueAt2.size(); i4++) {
                        com.android.server.appop.AttributedOp valueAt3 = valueAt2.valueAt(i4);
                        while (valueAt3.isRunning()) {
                            valueAt3.finished(valueAt3.mInProgressEvents.keyAt(0));
                        }
                        while (valueAt3.isPaused()) {
                            valueAt3.finished(valueAt3.mPausedInProgressEvents.keyAt(0));
                        }
                    }
                }
            }
        }
        this.mHandler.post(com.android.internal.util.function.pooled.PooledLambda.obtainRunnable(new com.android.internal.util.function.TriConsumer() { // from class: com.android.server.appop.AppOpsService$$ExternalSyntheticLambda15
            public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3) {
                ((com.android.server.appop.HistoricalRegistry) obj).clearHistory(((java.lang.Integer) obj2).intValue(), (java.lang.String) obj3);
            }
        }, this.mHistoricalRegistry, java.lang.Integer.valueOf(i), str));
    }

    public void uidRemoved(int i) {
        synchronized (this) {
            try {
                if (this.mUidStates.indexOfKey(i) >= 0) {
                    this.mUidStates.get(i).clear();
                    this.mUidStates.remove(i);
                    scheduleFastWriteLocked();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onUidStateChanged(int i, int i2, boolean z) {
        boolean z2;
        android.util.ArraySet<com.android.server.appop.OnOpModeChangedListener> arraySet;
        int i3;
        int i4;
        int i5;
        synchronized (this) {
            try {
                com.android.server.appop.AppOpsService.UidState uidStateLocked = getUidStateLocked(i, false);
                int i6 = 0;
                while (true) {
                    if (i6 >= this.mModeWatchers.size()) {
                        z2 = false;
                        break;
                    }
                    com.android.server.appop.AppOpsService.ModeCallback valueAt = this.mModeWatchers.valueAt(i6);
                    if (!valueAt.isWatchingUid(i) || (valueAt.getFlags() & 1) == 0) {
                        i6++;
                    } else {
                        z2 = true;
                        break;
                    }
                }
                if (uidStateLocked != null && z && z2) {
                    android.util.SparseBooleanArray sparseBooleanArray = new android.util.SparseBooleanArray();
                    android.util.SparseBooleanArray foregroundOps = this.mAppOpsCheckingService.getForegroundOps(i, "default:0");
                    for (int i7 = 0; i7 < foregroundOps.size(); i7++) {
                        sparseBooleanArray.put(foregroundOps.keyAt(i7), true);
                    }
                    java.lang.String[] packagesForUid = getPackagesForUid(i);
                    int userId = android.os.UserHandle.getUserId(i);
                    for (java.lang.String str : packagesForUid) {
                        android.util.SparseBooleanArray foregroundOps2 = this.mAppOpsCheckingService.getForegroundOps(str, userId);
                        for (int i8 = 0; i8 < foregroundOps2.size(); i8++) {
                            sparseBooleanArray.put(foregroundOps2.keyAt(i8), true);
                        }
                    }
                    for (int size = sparseBooleanArray.size() - 1; size >= 0; size--) {
                        if (sparseBooleanArray.valueAt(size)) {
                            int keyAt = sparseBooleanArray.keyAt(size);
                            if (this.mAppOpsCheckingService.getUidMode(uidStateLocked.uid, "default:0", keyAt) != android.app.AppOpsManager.opToDefaultMode(keyAt) && this.mAppOpsCheckingService.getUidMode(uidStateLocked.uid, "default:0", keyAt) == 4) {
                                this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.QuintConsumer() { // from class: com.android.server.appop.AppOpsService$$ExternalSyntheticLambda4
                                    public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3, java.lang.Object obj4, java.lang.Object obj5) {
                                        ((com.android.server.appop.AppOpsService) obj).notifyOpChangedForAllPkgsInUid(((java.lang.Integer) obj2).intValue(), ((java.lang.Integer) obj3).intValue(), ((java.lang.Boolean) obj4).booleanValue(), (java.lang.String) obj5);
                                    }
                                }, this, java.lang.Integer.valueOf(keyAt), java.lang.Integer.valueOf(uidStateLocked.uid), true, "default:0"));
                            } else if (!uidStateLocked.pkgOps.isEmpty() && (arraySet = this.mOpModeWatchers.get(keyAt)) != null) {
                                int size2 = arraySet.size() - 1;
                                while (size2 >= 0) {
                                    com.android.server.appop.OnOpModeChangedListener valueAt2 = arraySet.valueAt(size2);
                                    if ((valueAt2.getFlags() & 1) == 0) {
                                        i3 = size2;
                                    } else if (!valueAt2.isWatchingUid(uidStateLocked.uid)) {
                                        i3 = size2;
                                    } else {
                                        int size3 = uidStateLocked.pkgOps.size() - 1;
                                        while (size3 >= 0) {
                                            com.android.server.appop.AppOpsService.Op op = uidStateLocked.pkgOps.valueAt(size3).get(keyAt);
                                            if (op == null) {
                                                i4 = size3;
                                                i5 = size2;
                                            } else if (this.mAppOpsCheckingService.getPackageMode(op.packageName, op.op, android.os.UserHandle.getUserId(op.uid)) != 4) {
                                                i4 = size3;
                                                i5 = size2;
                                            } else {
                                                i4 = size3;
                                                i5 = size2;
                                                this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.server.appop.AppOpsService$$ExternalSyntheticLambda5(), this, arraySet.valueAt(size2), java.lang.Integer.valueOf(keyAt), java.lang.Integer.valueOf(uidStateLocked.uid), uidStateLocked.pkgOps.keyAt(size3), "default:0"));
                                            }
                                            size3 = i4 - 1;
                                            size2 = i5;
                                        }
                                        i3 = size2;
                                    }
                                    size2 = i3 - 1;
                                }
                            }
                        }
                    }
                }
                if (uidStateLocked != null) {
                    int size4 = uidStateLocked.pkgOps.size();
                    for (int i9 = 0; i9 < size4; i9++) {
                        com.android.server.appop.AppOpsService.Ops valueAt3 = uidStateLocked.pkgOps.valueAt(i9);
                        int size5 = valueAt3.size();
                        for (int i10 = 0; i10 < size5; i10++) {
                            com.android.server.appop.AppOpsService.Op valueAt4 = valueAt3.valueAt(i10);
                            for (int i11 = 0; i11 < valueAt4.mDeviceAttributedOps.size(); i11++) {
                                android.util.ArrayMap<java.lang.String, com.android.server.appop.AttributedOp> valueAt5 = valueAt4.mDeviceAttributedOps.valueAt(i11);
                                for (int i12 = 0; i12 < valueAt5.size(); i12++) {
                                    valueAt5.valueAt(i12).onUidStateChanged(i2);
                                }
                            }
                        }
                    }
                }
            } finally {
            }
        }
    }

    public void updateUidProcState(int i, int i2, int i3) {
        synchronized (this) {
            getUidStateTracker().updateUidProcState(i, i2, i3);
        }
    }

    public void shutdown() {
        boolean z;
        android.util.Slog.w(TAG, "Writing app ops before shutdown...");
        synchronized (this) {
            try {
                z = false;
                if (this.mWriteScheduled) {
                    this.mWriteScheduled = false;
                    this.mFastWriteScheduled = false;
                    this.mHandler.removeCallbacks(this.mWriteRunner);
                    z = true;
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        if (z) {
            writeRecentAccesses();
        }
        this.mAppOpsCheckingService.shutdown();
        this.mHistoricalRegistry.shutdown();
    }

    private java.util.ArrayList<android.app.AppOpsManager.OpEntry> collectOps(com.android.server.appop.AppOpsService.Ops ops, int[] iArr, java.lang.String str) {
        int i = 0;
        boolean z = this.mContext.checkPermission("android.permission.GET_APP_OPS_STATS", android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid()) == 0;
        if (iArr == null) {
            java.util.ArrayList<android.app.AppOpsManager.OpEntry> arrayList = new java.util.ArrayList<>();
            while (i < ops.size()) {
                com.android.server.appop.AppOpsService.Op valueAt = ops.valueAt(i);
                if (!android.app.AppOpsManager.opRestrictsRead(valueAt.op) || z) {
                    arrayList.add(getOpEntryForResult(valueAt, str));
                }
                i++;
            }
            return arrayList;
        }
        java.util.ArrayList<android.app.AppOpsManager.OpEntry> arrayList2 = null;
        while (i < iArr.length) {
            com.android.server.appop.AppOpsService.Op op = ops.get(iArr[i]);
            if (op != null) {
                if (arrayList2 == null) {
                    arrayList2 = new java.util.ArrayList<>();
                }
                if (!android.app.AppOpsManager.opRestrictsRead(op.op) || z) {
                    arrayList2.add(getOpEntryForResult(op, str));
                }
            }
            i++;
        }
        return arrayList2;
    }

    @android.annotation.Nullable
    private java.util.ArrayList<android.app.AppOpsManager.OpEntry> collectUidOps(@android.annotation.NonNull com.android.server.appop.AppOpsService.UidState uidState, @android.annotation.Nullable int[] iArr) {
        int size;
        android.util.SparseIntArray nonDefaultUidModes = this.mAppOpsCheckingService.getNonDefaultUidModes(uidState.uid, "default:0");
        java.util.ArrayList<android.app.AppOpsManager.OpEntry> arrayList = null;
        if (nonDefaultUidModes == null || (size = nonDefaultUidModes.size()) == 0) {
            return null;
        }
        int i = 0;
        if (iArr == null) {
            java.util.ArrayList<android.app.AppOpsManager.OpEntry> arrayList2 = new java.util.ArrayList<>();
            while (i < size) {
                int keyAt = nonDefaultUidModes.keyAt(i);
                arrayList2.add(new android.app.AppOpsManager.OpEntry(keyAt, nonDefaultUidModes.get(keyAt), java.util.Collections.emptyMap()));
                i++;
            }
            return arrayList2;
        }
        while (i < iArr.length) {
            int i2 = iArr[i];
            if (nonDefaultUidModes.indexOfKey(i2) >= 0) {
                if (arrayList == null) {
                    arrayList = new java.util.ArrayList<>();
                }
                arrayList.add(new android.app.AppOpsManager.OpEntry(i2, nonDefaultUidModes.get(i2), java.util.Collections.emptyMap()));
            }
            i++;
        }
        return arrayList;
    }

    @android.annotation.NonNull
    private static android.app.AppOpsManager.OpEntry getOpEntryForResult(@android.annotation.NonNull com.android.server.appop.AppOpsService.Op op, java.lang.String str) {
        return op.createEntryLocked(str);
    }

    public java.util.List<android.app.AppOpsManager.PackageOps> getPackagesForOps(int[] iArr) {
        return getPackagesForOpsForDevice(iArr, "default:0");
    }

    public java.util.List<android.app.AppOpsManager.PackageOps> getPackagesForOpsForDevice(int[] iArr, @android.annotation.NonNull java.lang.String str) {
        int callingUid = android.os.Binder.getCallingUid();
        java.util.ArrayList arrayList = null;
        boolean z = this.mContext.checkPermission("android.permission.GET_APP_OPS_STATS", android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), null) == 0;
        synchronized (this) {
            try {
                int size = this.mUidStates.size();
                for (int i = 0; i < size; i++) {
                    com.android.server.appop.AppOpsService.UidState valueAt = this.mUidStates.valueAt(i);
                    if (!valueAt.pkgOps.isEmpty() && (z || callingUid == valueAt.uid)) {
                        android.util.ArrayMap<java.lang.String, com.android.server.appop.AppOpsService.Ops> arrayMap = valueAt.pkgOps;
                        int size2 = arrayMap.size();
                        for (int i2 = 0; i2 < size2; i2++) {
                            com.android.server.appop.AppOpsService.Ops valueAt2 = arrayMap.valueAt(i2);
                            java.util.ArrayList<android.app.AppOpsManager.OpEntry> collectOps = collectOps(valueAt2, iArr, str);
                            if (collectOps != null) {
                                if (arrayList == null) {
                                    arrayList = new java.util.ArrayList();
                                }
                                arrayList.add(new android.app.AppOpsManager.PackageOps(valueAt2.packageName, valueAt2.uidState.uid, collectOps));
                            }
                        }
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return arrayList;
    }

    public java.util.List<android.app.AppOpsManager.PackageOps> getOpsForPackage(int i, java.lang.String str, int[] iArr) {
        enforceGetAppOpsStatsPermissionIfNeeded(i, str);
        java.lang.String resolvePackageName = android.app.AppOpsManager.resolvePackageName(i, str);
        if (resolvePackageName == null) {
            return java.util.Collections.emptyList();
        }
        synchronized (this) {
            try {
                com.android.server.appop.AppOpsService.Ops opsLocked = getOpsLocked(i, resolvePackageName, null, false, null, false);
                if (opsLocked == null) {
                    return null;
                }
                java.util.ArrayList<android.app.AppOpsManager.OpEntry> collectOps = collectOps(opsLocked, iArr, "default:0");
                if (collectOps == null || collectOps.size() == 0) {
                    return null;
                }
                java.util.ArrayList arrayList = new java.util.ArrayList();
                arrayList.add(new android.app.AppOpsManager.PackageOps(opsLocked.packageName, opsLocked.uidState.uid, collectOps));
                return arrayList;
            } finally {
            }
        }
    }

    private void enforceGetAppOpsStatsPermissionIfNeeded(int i, java.lang.String str) {
        int callingPid = android.os.Binder.getCallingPid();
        if (callingPid == android.os.Process.myPid()) {
            return;
        }
        int callingUid = android.os.Binder.getCallingUid();
        if (i == callingUid && str != null && checkPackage(i, str) == 0) {
            return;
        }
        this.mContext.enforcePermission("android.permission.GET_APP_OPS_STATS", callingPid, callingUid, null);
    }

    private void ensureHistoricalOpRequestIsValid(int i, java.lang.String str, java.lang.String str2, java.util.List<java.lang.String> list, int i2, long j, long j2, int i3) {
        if ((i2 & 1) != 0) {
            com.android.internal.util.Preconditions.checkArgument(i != -1);
        } else {
            com.android.internal.util.Preconditions.checkArgument(i == -1);
        }
        if ((i2 & 2) != 0) {
            java.util.Objects.requireNonNull(str);
        } else {
            com.android.internal.util.Preconditions.checkArgument(str == null);
        }
        if ((i2 & 4) == 0) {
            com.android.internal.util.Preconditions.checkArgument(str2 == null);
        }
        if ((i2 & 8) != 0) {
            java.util.Objects.requireNonNull(list);
        } else {
            com.android.internal.util.Preconditions.checkArgument(list == null);
        }
        com.android.internal.util.Preconditions.checkFlagsArgument(i2, 15);
        com.android.internal.util.Preconditions.checkArgumentNonnegative(j);
        com.android.internal.util.Preconditions.checkArgument(j2 > j);
        com.android.internal.util.Preconditions.checkFlagsArgument(i3, 31);
    }

    /* JADX WARN: Removed duplicated region for block: B:36:0x00ba  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x00ce  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x00d9  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x00e7  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x00d5  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x00c8  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0042  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void getHistoricalOps(int i, java.lang.String str, java.lang.String str2, java.util.List<java.lang.String> list, int i2, int i3, long j, long j2, int i4, final android.os.RemoteCallback remoteCallback) {
        boolean z;
        java.lang.String[] strArr;
        java.util.Set set;
        java.lang.String[] strArr2;
        android.content.pm.PackageManager packageManager = this.mContext.getPackageManager();
        ensureHistoricalOpRequestIsValid(i, str, str2, list, i3, j, j2, i4);
        java.util.Objects.requireNonNull(remoteCallback, "callback cannot be null");
        android.app.ActivityManagerInternal activityManagerInternal = (android.app.ActivityManagerInternal) com.android.server.LocalServices.getService(android.app.ActivityManagerInternal.class);
        if ((i3 & 1) != 0 && i == android.os.Binder.getCallingUid()) {
            z = true;
            if (!z) {
                boolean z2 = activityManagerInternal.getInstrumentationSourceUid(android.os.Binder.getCallingUid()) != -1;
                boolean z3 = android.os.Binder.getCallingPid() == android.os.Process.myPid();
                try {
                    boolean z4 = packageManager.getPackageUidAsUser(this.mContext.getPackageManager().getPermissionControllerPackageName(), 0, android.os.UserHandle.getUserId(android.os.Binder.getCallingUid())) == android.os.Binder.getCallingUid();
                    boolean z5 = this.mContext.checkPermission("android.permission.GET_HISTORICAL_APP_OPS_STATS", android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid()) == 0;
                    if (!z3 && !z2 && !z4 && !z5) {
                        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.appop.AppOpsService$$ExternalSyntheticLambda16
                            @Override // java.lang.Runnable
                            public final void run() {
                                com.android.server.appop.AppOpsService.lambda$getHistoricalOps$2(remoteCallback);
                            }
                        });
                        return;
                    }
                    this.mContext.enforcePermission("android.permission.GET_APP_OPS_STATS", android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), "getHistoricalOps");
                } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                    return;
                }
            }
            if (list != null) {
                strArr = null;
            } else {
                strArr = (java.lang.String[]) list.toArray(new java.lang.String[list.size()]);
            }
            if ((i2 & 4) != 0) {
                set = null;
            } else {
                set = android.permission.PermissionManager.getIndicatorExemptedPackages(this.mContext);
            }
            if (set == null) {
                strArr2 = (java.lang.String[]) set.toArray(new java.lang.String[set.size()]);
            } else {
                strArr2 = null;
            }
            this.mHandler.post(com.android.internal.util.function.pooled.PooledLambda.obtainRunnable(new com.android.internal.util.function.DodecConsumer() { // from class: com.android.server.appop.AppOpsService$$ExternalSyntheticLambda17
                public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3, java.lang.Object obj4, java.lang.Object obj5, java.lang.Object obj6, java.lang.Object obj7, java.lang.Object obj8, java.lang.Object obj9, java.lang.Object obj10, java.lang.Object obj11, java.lang.Object obj12) {
                    ((com.android.server.appop.HistoricalRegistry) obj).getHistoricalOps(((java.lang.Integer) obj2).intValue(), (java.lang.String) obj3, (java.lang.String) obj4, (java.lang.String[]) obj5, ((java.lang.Integer) obj6).intValue(), ((java.lang.Integer) obj7).intValue(), ((java.lang.Long) obj8).longValue(), ((java.lang.Long) obj9).longValue(), ((java.lang.Integer) obj10).intValue(), (java.lang.String[]) obj11, (android.os.RemoteCallback) obj12);
                }
            }, this.mHistoricalRegistry, java.lang.Integer.valueOf(i), str, str2, strArr, java.lang.Integer.valueOf(i2), java.lang.Integer.valueOf(i3), java.lang.Long.valueOf(j), java.lang.Long.valueOf(j2), java.lang.Integer.valueOf(i4), strArr2, remoteCallback).recycleOnUse());
        }
        z = false;
        if (!z) {
        }
        if (list != null) {
        }
        if ((i2 & 4) != 0) {
        }
        if (set == null) {
        }
        this.mHandler.post(com.android.internal.util.function.pooled.PooledLambda.obtainRunnable(new com.android.internal.util.function.DodecConsumer() { // from class: com.android.server.appop.AppOpsService$$ExternalSyntheticLambda17
            public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3, java.lang.Object obj4, java.lang.Object obj5, java.lang.Object obj6, java.lang.Object obj7, java.lang.Object obj8, java.lang.Object obj9, java.lang.Object obj10, java.lang.Object obj11, java.lang.Object obj12) {
                ((com.android.server.appop.HistoricalRegistry) obj).getHistoricalOps(((java.lang.Integer) obj2).intValue(), (java.lang.String) obj3, (java.lang.String) obj4, (java.lang.String[]) obj5, ((java.lang.Integer) obj6).intValue(), ((java.lang.Integer) obj7).intValue(), ((java.lang.Long) obj8).longValue(), ((java.lang.Long) obj9).longValue(), ((java.lang.Integer) obj10).intValue(), (java.lang.String[]) obj11, (android.os.RemoteCallback) obj12);
            }
        }, this.mHistoricalRegistry, java.lang.Integer.valueOf(i), str, str2, strArr, java.lang.Integer.valueOf(i2), java.lang.Integer.valueOf(i3), java.lang.Long.valueOf(j), java.lang.Long.valueOf(j2), java.lang.Integer.valueOf(i4), strArr2, remoteCallback).recycleOnUse());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$getHistoricalOps$2(android.os.RemoteCallback remoteCallback) {
        remoteCallback.sendResult(new android.os.Bundle());
    }

    public void getHistoricalOpsFromDiskRaw(int i, java.lang.String str, java.lang.String str2, java.util.List<java.lang.String> list, int i2, int i3, long j, long j2, int i4, android.os.RemoteCallback remoteCallback) {
        java.lang.String[] strArr;
        java.util.Set set;
        java.lang.String[] strArr2;
        ensureHistoricalOpRequestIsValid(i, str, str2, list, i3, j, j2, i4);
        java.util.Objects.requireNonNull(remoteCallback, "callback cannot be null");
        this.mContext.enforcePermission("android.permission.MANAGE_APPOPS", android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), "getHistoricalOps");
        if (list == null) {
            strArr = null;
        } else {
            strArr = (java.lang.String[]) list.toArray(new java.lang.String[list.size()]);
        }
        if ((i2 & 4) == 0) {
            set = null;
        } else {
            set = android.permission.PermissionManager.getIndicatorExemptedPackages(this.mContext);
        }
        if (set != null) {
            strArr2 = (java.lang.String[]) set.toArray(new java.lang.String[set.size()]);
        } else {
            strArr2 = null;
        }
        this.mHandler.post(com.android.internal.util.function.pooled.PooledLambda.obtainRunnable(new com.android.internal.util.function.DodecConsumer() { // from class: com.android.server.appop.AppOpsService$$ExternalSyntheticLambda0
            public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3, java.lang.Object obj4, java.lang.Object obj5, java.lang.Object obj6, java.lang.Object obj7, java.lang.Object obj8, java.lang.Object obj9, java.lang.Object obj10, java.lang.Object obj11, java.lang.Object obj12) {
                ((com.android.server.appop.HistoricalRegistry) obj).getHistoricalOpsFromDiskRaw(((java.lang.Integer) obj2).intValue(), (java.lang.String) obj3, (java.lang.String) obj4, (java.lang.String[]) obj5, ((java.lang.Integer) obj6).intValue(), ((java.lang.Integer) obj7).intValue(), ((java.lang.Long) obj8).longValue(), ((java.lang.Long) obj9).longValue(), ((java.lang.Integer) obj10).intValue(), (java.lang.String[]) obj11, (android.os.RemoteCallback) obj12);
            }
        }, this.mHistoricalRegistry, java.lang.Integer.valueOf(i), str, str2, strArr, java.lang.Integer.valueOf(i2), java.lang.Integer.valueOf(i3), java.lang.Long.valueOf(j), java.lang.Long.valueOf(j2), java.lang.Integer.valueOf(i4), strArr2, remoteCallback).recycleOnUse());
    }

    public void reloadNonHistoricalState() {
        this.mContext.enforcePermission("android.permission.MANAGE_APPOPS", android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), "reloadNonHistoricalState");
        this.mAppOpsCheckingService.writeState();
        this.mAppOpsCheckingService.readState();
    }

    @com.android.internal.annotations.VisibleForTesting
    void readState() {
        this.mAppOpsCheckingService.readState();
    }

    public java.util.List<android.app.AppOpsManager.PackageOps> getUidOps(int i, int[] iArr) {
        this.mContext.enforcePermission("android.permission.GET_APP_OPS_STATS", android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), null);
        synchronized (this) {
            try {
                com.android.server.appop.AppOpsService.UidState uidStateLocked = getUidStateLocked(i, false);
                if (uidStateLocked == null) {
                    return null;
                }
                java.util.ArrayList<android.app.AppOpsManager.OpEntry> collectUidOps = collectUidOps(uidStateLocked, iArr);
                if (collectUidOps == null) {
                    return null;
                }
                java.util.ArrayList arrayList = new java.util.ArrayList();
                arrayList.add(new android.app.AppOpsManager.PackageOps((java.lang.String) null, uidStateLocked.uid, collectUidOps));
                return arrayList;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void pruneOpLocked(com.android.server.appop.AppOpsService.Op op, int i, java.lang.String str) {
        com.android.server.appop.AppOpsService.Ops opsLocked;
        com.android.server.appop.AppOpsService.UidState uidState;
        android.util.ArrayMap<java.lang.String, com.android.server.appop.AppOpsService.Ops> arrayMap;
        op.removeAttributionsWithNoTime();
        if (op.mDeviceAttributedOps.isEmpty() && (opsLocked = getOpsLocked(i, str, null, false, null, false)) != null) {
            opsLocked.remove(op.op);
            this.mAppOpsCheckingService.setPackageMode(str, op.op, android.app.AppOpsManager.opToDefaultMode(op.op), android.os.UserHandle.getUserId(op.uid));
            if (opsLocked.size() <= 0 && (arrayMap = (uidState = opsLocked.uidState).pkgOps) != null) {
                arrayMap.remove(opsLocked.packageName);
                this.mAppOpsCheckingService.removePackage(opsLocked.packageName, android.os.UserHandle.getUserId(uidState.uid));
            }
        }
    }

    private void enforceManageAppOpsModes(int i, int i2, int i3) {
        if (i == android.os.Process.myPid()) {
            return;
        }
        int userId = android.os.UserHandle.getUserId(i2);
        synchronized (this) {
            try {
                if (this.mProfileOwners == null || this.mProfileOwners.get(userId, -1) != i2 || i3 < 0 || userId != android.os.UserHandle.getUserId(i3)) {
                    this.mContext.enforcePermission("android.permission.MANAGE_APP_OPS_MODES", android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), null);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void setUidMode(int i, int i2, int i3) {
        setUidMode(i, i2, i3, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setUidMode(int i, int i2, int i3, @android.annotation.Nullable com.android.internal.app.IAppOpsCallback iAppOpsCallback) {
        int i4;
        enforceManageAppOpsModes(android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), i2);
        verifyIncomingOp(i);
        int opToSwitch = android.app.AppOpsManager.opToSwitch(i);
        if (iAppOpsCallback == null) {
            updatePermissionRevokedCompat(i2, opToSwitch, i3);
        }
        synchronized (this) {
            try {
                int opToDefaultMode = android.app.AppOpsManager.opToDefaultMode(opToSwitch);
                boolean z = false;
                com.android.server.appop.AppOpsService.UidState uidStateLocked = getUidStateLocked(i2, false);
                if (uidStateLocked == null) {
                    if (i3 == opToDefaultMode) {
                        return;
                    }
                    if (i2 >= 10000) {
                        android.util.Slog.e(TAG, "Trying to set mode for unknown uid " + i2 + ".");
                    }
                    uidStateLocked = new com.android.server.appop.AppOpsService.UidState(i2);
                    this.mUidStates.put(i2, uidStateLocked);
                }
                if (this.mAppOpsCheckingService.getUidMode(uidStateLocked.uid, "default:0", opToSwitch) != android.app.AppOpsManager.opToDefaultMode(opToSwitch)) {
                    i4 = this.mAppOpsCheckingService.getUidMode(uidStateLocked.uid, "default:0", opToSwitch);
                } else {
                    i4 = 3;
                }
                this.mIgnoredCallback = iAppOpsCallback;
                if (this.mAppOpsCheckingService.setUidMode(uidStateLocked.uid, "default:0", opToSwitch, i3)) {
                    if (i3 != 2 && i3 != i4) {
                        if (i3 == 1) {
                            z = true;
                        }
                        updateStartedOpModeForUidForDefaultDeviceLocked(opToSwitch, z, i2);
                    }
                    notifyStorageManagerOpModeChangedSync(opToSwitch, i2, null, i3, i4);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyOpChangedForAllPkgsInUid(int i, int i2, boolean z, java.lang.String str) {
        android.util.ArrayMap arrayMap;
        java.lang.String[] packagesForUid = getPackagesForUid(i2);
        synchronized (this) {
            try {
                android.util.ArraySet<com.android.server.appop.OnOpModeChangedListener> arraySet = this.mOpModeWatchers.get(i);
                android.util.ArrayMap arrayMap2 = null;
                if (arraySet != null) {
                    int size = arraySet.size();
                    for (int i3 = 0; i3 < size; i3++) {
                        com.android.server.appop.OnOpModeChangedListener valueAt = arraySet.valueAt(i3);
                        if (valueAt.isWatchingUid(i2) && (!z || (valueAt.getFlags() & 1) != 0)) {
                            android.util.ArraySet arraySet2 = new android.util.ArraySet();
                            java.util.Collections.addAll(arraySet2, packagesForUid);
                            if (arrayMap2 == null) {
                                arrayMap2 = new android.util.ArrayMap();
                            }
                            arrayMap2.put(valueAt, arraySet2);
                        }
                    }
                }
                arrayMap = arrayMap2;
                for (java.lang.String str2 : packagesForUid) {
                    android.util.ArraySet<com.android.server.appop.OnOpModeChangedListener> arraySet3 = this.mPackageModeWatchers.get(str2);
                    if (arraySet3 != null) {
                        if (arrayMap == null) {
                            arrayMap = new android.util.ArrayMap();
                        }
                        int size2 = arraySet3.size();
                        for (int i4 = 0; i4 < size2; i4++) {
                            com.android.server.appop.OnOpModeChangedListener valueAt2 = arraySet3.valueAt(i4);
                            if (!z || (valueAt2.getFlags() & 1) != 0) {
                                android.util.ArraySet arraySet4 = (android.util.ArraySet) arrayMap.get(valueAt2);
                                if (arraySet4 == null) {
                                    arraySet4 = new android.util.ArraySet();
                                    arrayMap.put(valueAt2, arraySet4);
                                }
                                arraySet4.add(str2);
                            }
                        }
                    }
                }
                if (arrayMap != null && this.mIgnoredCallback != null) {
                    arrayMap.remove(this.mModeWatchers.get(this.mIgnoredCallback.asBinder()));
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        if (arrayMap == null) {
            return;
        }
        for (int i5 = 0; i5 < arrayMap.size(); i5++) {
            com.android.server.appop.OnOpModeChangedListener onOpModeChangedListener = (com.android.server.appop.OnOpModeChangedListener) arrayMap.keyAt(i5);
            android.util.ArraySet arraySet5 = (android.util.ArraySet) arrayMap.valueAt(i5);
            if (arraySet5 == null) {
                this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.server.appop.AppOpsService$$ExternalSyntheticLambda5(), this, onOpModeChangedListener, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2), (java.lang.Object) null, str));
            } else {
                int size3 = arraySet5.size();
                for (int i6 = 0; i6 < size3; i6++) {
                    this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.server.appop.AppOpsService$$ExternalSyntheticLambda5(), this, onOpModeChangedListener, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2), (java.lang.String) arraySet5.valueAt(i6), str));
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyOpChangedForPkg(@android.annotation.NonNull java.lang.String str, int i, int i2, int i3) {
        android.util.ArraySet arraySet;
        android.util.ArraySet arraySet2;
        int packageUid;
        synchronized (this) {
            try {
                android.util.ArraySet<com.android.server.appop.OnOpModeChangedListener> arraySet3 = this.mOpModeWatchers.get(i);
                if (arraySet3 == null) {
                    arraySet = null;
                } else {
                    arraySet = new android.util.ArraySet();
                    arraySet.addAll((android.util.ArraySet) arraySet3);
                }
                android.util.ArraySet<com.android.server.appop.OnOpModeChangedListener> arraySet4 = this.mPackageModeWatchers.get(str);
                if (arraySet4 == null) {
                    arraySet2 = arraySet;
                } else {
                    if (arraySet == null) {
                        arraySet = new android.util.ArraySet();
                    }
                    arraySet.addAll((android.util.ArraySet) arraySet4);
                    arraySet2 = arraySet;
                }
                if (arraySet2 != null && this.mIgnoredCallback != null) {
                    arraySet2.remove(this.mModeWatchers.get(this.mIgnoredCallback.asBinder()));
                }
                packageUid = getPackageManagerInternal().getPackageUid(str, 4202496L, i3);
                com.android.server.appop.AppOpsService.Op opLocked = getOpLocked(i, packageUid, str, null, false, null, false);
                if (opLocked != null && i2 == android.app.AppOpsManager.opToDefaultMode(opLocked.op)) {
                    pruneOpLocked(opLocked, packageUid, str);
                }
                scheduleFastWriteLocked();
                if (i2 != 2) {
                    boolean z = true;
                    if (i2 != 1) {
                        z = false;
                    }
                    updateStartedOpModeForUidForDefaultDeviceLocked(i, z, packageUid);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        if (arraySet2 != null && packageUid != -1) {
            this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.HexConsumer() { // from class: com.android.server.appop.AppOpsService$$ExternalSyntheticLambda14
                public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3, java.lang.Object obj4, java.lang.Object obj5, java.lang.Object obj6) {
                    ((com.android.server.appop.AppOpsService) obj).notifyOpChanged((android.util.ArraySet<com.android.server.appop.OnOpModeChangedListener>) obj2, ((java.lang.Integer) obj3).intValue(), ((java.lang.Integer) obj4).intValue(), (java.lang.String) obj5, (java.lang.String) obj6);
                }
            }, this, arraySet2, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(packageUid), str, "default:0"));
        }
    }

    private void updatePermissionRevokedCompat(int i, int i2, int i3) {
        int i4;
        int[] iArr;
        int i5;
        java.lang.String str;
        java.lang.String str2;
        java.lang.String str3;
        long clearCallingIdentity;
        java.lang.String str4;
        android.content.pm.PackageManager packageManager = this.mContext.getPackageManager();
        if (packageManager == null) {
            return;
        }
        java.lang.String[] packagesForUid = packageManager.getPackagesForUid(i);
        if (com.android.internal.util.ArrayUtils.isEmpty(packagesForUid)) {
            return;
        }
        int i6 = 0;
        java.lang.String str5 = packagesForUid[0];
        int[] iArr2 = this.mSwitchedOps.get(i2);
        int length = iArr2.length;
        int i7 = 0;
        while (i7 < length) {
            java.lang.String opToPermission = android.app.AppOpsManager.opToPermission(iArr2[i7]);
            if (opToPermission == null) {
                i4 = i7;
                iArr = iArr2;
                i5 = length;
            } else if (packageManager.checkPermission(opToPermission, str5) != 0) {
                i4 = i7;
                iArr = iArr2;
                i5 = length;
            } else {
                try {
                    android.content.pm.PermissionInfo permissionInfo = packageManager.getPermissionInfo(opToPermission, i6);
                    if (!permissionInfo.isRuntime()) {
                        i4 = i7;
                        iArr = iArr2;
                        i5 = length;
                    } else {
                        boolean z = true;
                        int i8 = getPackageManagerInternal().getUidTargetSdkVersion(i) >= 23 ? 1 : i6;
                        android.os.UserHandle userHandleForUid = android.os.UserHandle.getUserHandleForUid(i);
                        if (permissionInfo.backgroundPermission != null) {
                            if (packageManager.checkPermission(permissionInfo.backgroundPermission, str5) != 0) {
                                str = ", permission=";
                                i4 = i7;
                                iArr = iArr2;
                                i5 = length;
                                str2 = ", switchCode=";
                                str3 = ", mode=";
                            } else {
                                boolean z2 = i3 != 0;
                                if (!z2 || i8 == 0) {
                                    str4 = ", switchCode=";
                                    i4 = i7;
                                } else {
                                    i4 = i7;
                                    java.lang.StringBuilder sb = new java.lang.StringBuilder();
                                    sb.append("setUidMode() called with a mode inconsistent with runtime permission state, this is discouraged and you should revoke the runtime permission instead: uid=");
                                    sb.append(i);
                                    sb.append(", switchCode=");
                                    sb.append(i2);
                                    sb.append(", mode=");
                                    sb.append(i3);
                                    sb.append(", permission=");
                                    str4 = ", switchCode=";
                                    sb.append(permissionInfo.backgroundPermission);
                                    android.util.Slog.w(TAG, sb.toString());
                                }
                                clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                                try {
                                    iArr = iArr2;
                                    str2 = str4;
                                    i5 = length;
                                    str3 = ", mode=";
                                    str = ", permission=";
                                    packageManager.updatePermissionFlags(permissionInfo.backgroundPermission, str5, 8, z2 ? 8 : 0, userHandleForUid);
                                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                                } finally {
                                }
                            }
                            if (i3 == 0 || i3 == 4) {
                                z = false;
                            }
                        } else {
                            str = ", permission=";
                            i4 = i7;
                            iArr = iArr2;
                            i5 = length;
                            str2 = ", switchCode=";
                            str3 = ", mode=";
                            if (i3 == 0) {
                                z = false;
                            }
                        }
                        if (z && i8 != 0) {
                            android.util.Slog.w(TAG, "setUidMode() called with a mode inconsistent with runtime permission state, this is discouraged and you should revoke the runtime permission instead: uid=" + i + str2 + i2 + str3 + i3 + str + opToPermission);
                        }
                        clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                        try {
                            packageManager.updatePermissionFlags(opToPermission, str5, 8, z ? 8 : 0, userHandleForUid);
                        } finally {
                        }
                    }
                } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                    i4 = i7;
                    iArr = iArr2;
                    i5 = length;
                    e.printStackTrace();
                }
            }
            i7 = i4 + 1;
            length = i5;
            iArr2 = iArr;
            i6 = 0;
        }
    }

    private void notifyStorageManagerOpModeChangedSync(int i, int i2, @android.annotation.NonNull java.lang.String str, int i3, int i4) {
        android.os.storage.StorageManagerInternal storageManagerInternal = (android.os.storage.StorageManagerInternal) com.android.server.LocalServices.getService(android.os.storage.StorageManagerInternal.class);
        if (storageManagerInternal != null) {
            storageManagerInternal.onAppOpsChanged(i, i2, str, i3, i4);
        }
    }

    public void setMode(int i, int i2, @android.annotation.NonNull java.lang.String str, int i3) {
        setMode(i, i2, str, i3, null);
    }

    void setMode(int i, int i2, @android.annotation.NonNull java.lang.String str, int i3, @android.annotation.Nullable com.android.internal.app.IAppOpsCallback iAppOpsCallback) {
        int i4;
        enforceManageAppOpsModes(android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), i2);
        verifyIncomingOp(i);
        if (!isIncomingPackageValid(str, android.os.UserHandle.getUserId(i2))) {
            return;
        }
        int opToSwitch = android.app.AppOpsManager.opToSwitch(i);
        try {
            com.android.server.appop.AppOpsService.PackageVerificationResult verifyAndGetBypass = verifyAndGetBypass(i2, str, null);
            synchronized (this) {
                try {
                    com.android.server.appop.AppOpsService.Op opLocked = getOpLocked(opToSwitch, i2, str, null, false, verifyAndGetBypass.bypass, true);
                    if (opLocked == null || this.mAppOpsCheckingService.getPackageMode(opLocked.packageName, opLocked.op, android.os.UserHandle.getUserId(opLocked.uid)) == i3) {
                        i4 = 3;
                    } else {
                        int packageMode = this.mAppOpsCheckingService.getPackageMode(opLocked.packageName, opLocked.op, android.os.UserHandle.getUserId(opLocked.uid));
                        this.mIgnoredCallback = iAppOpsCallback;
                        this.mAppOpsCheckingService.setPackageMode(opLocked.packageName, opLocked.op, i3, android.os.UserHandle.getUserId(opLocked.uid));
                        i4 = packageMode;
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            notifyStorageManagerOpModeChangedSync(opToSwitch, i2, str, i3, i4);
        } catch (java.lang.SecurityException e) {
            logVerifyAndGetBypassFailure(i2, e, "setMode");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyOpChanged(android.util.ArraySet<com.android.server.appop.OnOpModeChangedListener> arraySet, int i, int i2, java.lang.String str, java.lang.String str2) {
        for (int i3 = 0; i3 < arraySet.size(); i3++) {
            notifyOpChanged(arraySet.valueAt(i3), i, i2, str, str2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyOpChanged(com.android.server.appop.OnOpModeChangedListener onOpModeChangedListener, int i, int i2, java.lang.String str, java.lang.String str2) {
        java.util.Objects.requireNonNull(onOpModeChangedListener);
        if (i2 == -2 || onOpModeChangedListener.getWatchingUid() < 0 || onOpModeChangedListener.getWatchingUid() == i2) {
            for (int i3 : onOpModeChangedListener.getWatchedOpCode() == -2 ? this.mSwitchedOps.get(i) : onOpModeChangedListener.getWatchedOpCode() == -1 ? new int[]{i} : new int[]{onOpModeChangedListener.getWatchedOpCode()}) {
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                } catch (android.os.RemoteException e) {
                } catch (java.lang.Throwable th) {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    throw th;
                }
                if (shouldIgnoreCallback(i3, onOpModeChangedListener.getCallingPid(), onOpModeChangedListener.getCallingUid())) {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                } else {
                    onOpModeChangedListener.onOpModeChanged(i3, i2, str, str2);
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }
    }

    private static java.util.ArrayList<com.android.server.appop.AppOpsService.ChangeRec> addChange(java.util.ArrayList<com.android.server.appop.AppOpsService.ChangeRec> arrayList, int i, int i2, java.lang.String str, int i3) {
        boolean z = false;
        if (arrayList == null) {
            arrayList = new java.util.ArrayList<>();
        } else {
            int size = arrayList.size();
            int i4 = 0;
            while (true) {
                if (i4 >= size) {
                    break;
                }
                com.android.server.appop.AppOpsService.ChangeRec changeRec = arrayList.get(i4);
                if (changeRec.op != i || !changeRec.pkg.equals(str)) {
                    i4++;
                } else {
                    z = true;
                    break;
                }
            }
        }
        if (!z) {
            arrayList.add(new com.android.server.appop.AppOpsService.ChangeRec(i, i2, str, i3));
        }
        return arrayList;
    }

    private static java.util.HashMap<com.android.server.appop.OnOpModeChangedListener, java.util.ArrayList<com.android.server.appop.AppOpsService.ChangeRec>> addCallbacks(java.util.HashMap<com.android.server.appop.OnOpModeChangedListener, java.util.ArrayList<com.android.server.appop.AppOpsService.ChangeRec>> hashMap, int i, int i2, java.lang.String str, int i3, android.util.ArraySet<com.android.server.appop.OnOpModeChangedListener> arraySet) {
        if (arraySet == null) {
            return hashMap;
        }
        if (hashMap == null) {
            hashMap = new java.util.HashMap<>();
        }
        int size = arraySet.size();
        for (int i4 = 0; i4 < size; i4++) {
            com.android.server.appop.OnOpModeChangedListener valueAt = arraySet.valueAt(i4);
            java.util.ArrayList<com.android.server.appop.AppOpsService.ChangeRec> arrayList = hashMap.get(valueAt);
            java.util.ArrayList<com.android.server.appop.AppOpsService.ChangeRec> addChange = addChange(arrayList, i, i2, str, i3);
            if (addChange != arrayList) {
                hashMap.put(valueAt, addChange);
            }
        }
        return hashMap;
    }

    static final class ChangeRec {
        final int op;
        final java.lang.String pkg;
        final int previous_mode;
        final int uid;

        ChangeRec(int i, int i2, java.lang.String str, int i3) {
            this.op = i;
            this.uid = i2;
            this.pkg = str;
            this.previous_mode = i3;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:6:0x0036 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void resetAllModes(int i, java.lang.String str) {
        int packageUid;
        java.util.HashMap<com.android.server.appop.OnOpModeChangedListener, java.util.ArrayList<com.android.server.appop.AppOpsService.ChangeRec>> hashMap;
        java.util.ArrayList<com.android.server.appop.AppOpsService.ChangeRec> arrayList;
        java.util.Set<java.lang.String> arraySet;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        java.lang.String str2 = str;
        int callingPid = android.os.Binder.getCallingPid();
        int callingUid = android.os.Binder.getCallingUid();
        int handleIncomingUser = android.app.ActivityManager.handleIncomingUser(callingPid, callingUid, i, true, true, "resetAllModes", null);
        int i7 = -1;
        if (str2 != null) {
            try {
                packageUid = android.app.AppGlobals.getPackageManager().getPackageUid(str2, 8192L, handleIncomingUser);
            } catch (android.os.RemoteException e) {
            }
            enforceManageAppOpsModes(callingPid, callingUid, packageUid);
            java.util.ArrayList<com.android.server.appop.AppOpsService.ChangeRec> arrayList2 = new java.util.ArrayList<>();
            synchronized (this) {
                try {
                    int i8 = 1;
                    int size = this.mUidStates.size() - 1;
                    hashMap = null;
                    arrayList = arrayList2;
                    boolean z = false;
                    while (size >= 0) {
                        com.android.server.appop.AppOpsService.UidState valueAt = this.mUidStates.valueAt(size);
                        android.util.SparseIntArray nonDefaultUidModes = this.mAppOpsCheckingService.getNonDefaultUidModes(valueAt.uid, "default:0");
                        if (nonDefaultUidModes == null) {
                            i2 = packageUid;
                        } else if (valueAt.uid == packageUid || packageUid == i7) {
                            int size2 = nonDefaultUidModes.size() - i8;
                            while (size2 >= 0) {
                                int keyAt = nonDefaultUidModes.keyAt(size2);
                                if (!android.app.AppOpsManager.opAllowsReset(keyAt)) {
                                    i6 = packageUid;
                                } else {
                                    int valueAt2 = nonDefaultUidModes.valueAt(size2);
                                    i6 = packageUid;
                                    this.mAppOpsCheckingService.setUidMode(valueAt.uid, "default:0", keyAt, isUidOpGrantedByRole(valueAt.uid, keyAt) ? 0 : android.app.AppOpsManager.opToDefaultMode(keyAt));
                                    java.lang.String[] packagesForUid = getPackagesForUid(valueAt.uid);
                                    int length = packagesForUid.length;
                                    int i9 = 0;
                                    while (i9 < length) {
                                        java.lang.String str3 = packagesForUid[i9];
                                        java.lang.String[] strArr = packagesForUid;
                                        int i10 = length;
                                        int i11 = valueAt2;
                                        java.util.HashMap<com.android.server.appop.OnOpModeChangedListener, java.util.ArrayList<com.android.server.appop.AppOpsService.ChangeRec>> hashMap2 = hashMap;
                                        int i12 = keyAt;
                                        java.util.HashMap<com.android.server.appop.OnOpModeChangedListener, java.util.ArrayList<com.android.server.appop.AppOpsService.ChangeRec>> addCallbacks = addCallbacks(addCallbacks(hashMap2, keyAt, valueAt.uid, str3, i11, this.mOpModeWatchers.get(keyAt)), i12, valueAt.uid, str3, i11, this.mPackageModeWatchers.get(str3));
                                        arrayList = addChange(arrayList, i12, valueAt.uid, str3, i11);
                                        i9++;
                                        valueAt2 = i11;
                                        keyAt = i12;
                                        length = i10;
                                        hashMap = addCallbacks;
                                        packagesForUid = strArr;
                                    }
                                }
                                size2--;
                                packageUid = i6;
                            }
                            i2 = packageUid;
                        } else {
                            i2 = packageUid;
                        }
                        if (!valueAt.pkgOps.isEmpty() && (handleIncomingUser == -1 || handleIncomingUser == android.os.UserHandle.getUserId(valueAt.uid))) {
                            java.util.Iterator<java.util.Map.Entry<java.lang.String, com.android.server.appop.AppOpsService.Ops>> it = valueAt.pkgOps.entrySet().iterator();
                            while (it.hasNext()) {
                                java.util.Map.Entry<java.lang.String, com.android.server.appop.AppOpsService.Ops> next = it.next();
                                java.lang.String key = next.getKey();
                                if (str2 == null || str2.equals(key)) {
                                    com.android.server.appop.AppOpsService.Ops value = next.getValue();
                                    java.util.HashMap<com.android.server.appop.OnOpModeChangedListener, java.util.ArrayList<com.android.server.appop.AppOpsService.ChangeRec>> hashMap3 = hashMap;
                                    int size3 = value.size() - 1;
                                    while (size3 >= 0) {
                                        com.android.server.appop.AppOpsService.Op valueAt3 = value.valueAt(size3);
                                        if (shouldDeferResetOpToDpm(valueAt3.op)) {
                                            deferResetOpToDpm(valueAt3.op, str2, handleIncomingUser);
                                            i5 = handleIncomingUser;
                                        } else if (!android.app.AppOpsManager.opAllowsReset(valueAt3.op)) {
                                            i5 = handleIncomingUser;
                                        } else {
                                            int packageMode = this.mAppOpsCheckingService.getPackageMode(valueAt3.packageName, valueAt3.op, android.os.UserHandle.getUserId(valueAt3.uid));
                                            int opToDefaultMode = isPackageOpGrantedByRole(key, valueAt.uid, valueAt3.op) ? 0 : android.app.AppOpsManager.opToDefaultMode(valueAt3.op);
                                            if (packageMode == opToDefaultMode) {
                                                i5 = handleIncomingUser;
                                            } else {
                                                i5 = handleIncomingUser;
                                                this.mAppOpsCheckingService.setPackageMode(valueAt3.packageName, valueAt3.op, opToDefaultMode, android.os.UserHandle.getUserId(valueAt3.uid));
                                                int i13 = valueAt3.uidState.uid;
                                                hashMap3 = addCallbacks(addCallbacks(hashMap3, valueAt3.op, i13, key, packageMode, this.mOpModeWatchers.get(valueAt3.op)), valueAt3.op, i13, key, packageMode, this.mPackageModeWatchers.get(key));
                                                arrayList = addChange(arrayList, valueAt3.op, i13, key, packageMode);
                                                valueAt3.removeAttributionsWithNoTime();
                                                if (valueAt3.mDeviceAttributedOps.isEmpty()) {
                                                    value.removeAt(size3);
                                                }
                                                z = true;
                                            }
                                        }
                                        size3--;
                                        str2 = str;
                                        handleIncomingUser = i5;
                                    }
                                    int i14 = handleIncomingUser;
                                    if (value.size() == 0) {
                                        it.remove();
                                        this.mAppOpsCheckingService.removePackage(key, android.os.UserHandle.getUserId(valueAt.uid));
                                    }
                                    str2 = str;
                                    hashMap = hashMap3;
                                    handleIncomingUser = i14;
                                }
                            }
                            i3 = handleIncomingUser;
                            i4 = 1;
                            size--;
                            str2 = str;
                            i8 = i4;
                            handleIncomingUser = i3;
                            packageUid = i2;
                            i7 = -1;
                        }
                        i3 = handleIncomingUser;
                        i4 = 1;
                        size--;
                        str2 = str;
                        i8 = i4;
                        handleIncomingUser = i3;
                        packageUid = i2;
                        i7 = -1;
                    }
                    if (z) {
                        scheduleFastWriteLocked();
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            if (hashMap != null) {
                for (java.util.Map.Entry<com.android.server.appop.OnOpModeChangedListener, java.util.ArrayList<com.android.server.appop.AppOpsService.ChangeRec>> entry : hashMap.entrySet()) {
                    com.android.server.appop.OnOpModeChangedListener key2 = entry.getKey();
                    java.util.ArrayList<com.android.server.appop.AppOpsService.ChangeRec> value2 = entry.getValue();
                    for (int i15 = 0; i15 < value2.size(); i15++) {
                        com.android.server.appop.AppOpsService.ChangeRec changeRec = value2.get(i15);
                        if (this.mVirtualDeviceManagerInternal != null) {
                            arraySet = this.mVirtualDeviceManagerInternal.getAllPersistentDeviceIds();
                        } else {
                            arraySet = new android.util.ArraySet<>();
                            arraySet.add("default:0");
                        }
                        java.util.Iterator<java.lang.String> it2 = arraySet.iterator();
                        while (it2.hasNext()) {
                            this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.server.appop.AppOpsService$$ExternalSyntheticLambda5(), this, key2, java.lang.Integer.valueOf(changeRec.op), java.lang.Integer.valueOf(changeRec.uid), changeRec.pkg, it2.next()));
                        }
                    }
                }
            }
            int size4 = arrayList.size();
            for (int i16 = 0; i16 < size4; i16++) {
                com.android.server.appop.AppOpsService.ChangeRec changeRec2 = arrayList.get(i16);
                notifyStorageManagerOpModeChangedSync(changeRec2.op, changeRec2.uid, changeRec2.pkg, android.app.AppOpsManager.opToDefaultMode(changeRec2.op), changeRec2.previous_mode);
            }
            return;
        }
        packageUid = -1;
        enforceManageAppOpsModes(callingPid, callingUid, packageUid);
        java.util.ArrayList<com.android.server.appop.AppOpsService.ChangeRec> arrayList22 = new java.util.ArrayList<>();
        synchronized (this) {
        }
    }

    private boolean isUidOpGrantedByRole(int i, int i2) {
        if (!android.app.AppOpsManager.opIsUidAppOpPermission(i2)) {
            return false;
        }
        android.content.pm.PackageManager packageManager = this.mContext.getPackageManager();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            java.lang.String str = (java.lang.String) com.android.internal.util.ArrayUtils.firstOrNull(com.android.internal.util.ArrayUtils.defeatNullable(packageManager.getPackagesForUid(i)));
            if (str == null) {
                return false;
            }
            return (packageManager.getPermissionFlags(android.app.AppOpsManager.opToPermission(i2), str, android.os.UserHandle.getUserHandleForUid(i)) & 32768) != 0;
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    private boolean isPackageOpGrantedByRole(@android.annotation.NonNull java.lang.String str, int i, int i2) {
        if (!android.app.AppOpsManager.opIsPackageAppOpPermission(i2)) {
            return false;
        }
        android.content.pm.PackageManager packageManager = this.mContext.getPackageManager();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            return (packageManager.getPermissionFlags(android.app.AppOpsManager.opToPermission(i2), str, android.os.UserHandle.getUserHandleForUid(i)) & 32768) != 0;
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    private boolean shouldDeferResetOpToDpm(int i) {
        return this.dpmi != null && this.dpmi.supportsResetOp(i);
    }

    private void deferResetOpToDpm(int i, java.lang.String str, int i2) {
        this.dpmi.resetOp(i, str, i2);
    }

    public void startWatchingMode(int i, java.lang.String str, com.android.internal.app.IAppOpsCallback iAppOpsCallback) {
        startWatchingModeWithFlags(i, str, 0, iAppOpsCallback);
    }

    public void startWatchingModeWithFlags(int i, java.lang.String str, int i2, com.android.internal.app.IAppOpsCallback iAppOpsCallback) {
        int opToSwitch;
        int i3;
        int callingUid = android.os.Binder.getCallingUid();
        int callingPid = android.os.Binder.getCallingPid();
        com.android.internal.util.Preconditions.checkArgumentInRange(i, -1, 147, "Invalid op code: " + i);
        if (iAppOpsCallback == null) {
            return;
        }
        boolean z = (str == null || filterAppAccessUnlocked(str, android.os.UserHandle.getUserId(callingUid))) ? false : true;
        synchronized (this) {
            if (i == -1) {
                opToSwitch = i;
            } else {
                try {
                    opToSwitch = android.app.AppOpsManager.opToSwitch(i);
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            if ((i2 & 2) == 0) {
                if (i == -1) {
                    i3 = -2;
                } else {
                    i3 = i;
                }
            } else {
                i3 = opToSwitch;
            }
            com.android.server.appop.AppOpsService.ModeCallback modeCallback = this.mModeWatchers.get(iAppOpsCallback.asBinder());
            if (modeCallback == null) {
                modeCallback = new com.android.server.appop.AppOpsService.ModeCallback(iAppOpsCallback, -1, i2, i3, callingUid, callingPid);
                this.mModeWatchers.put(iAppOpsCallback.asBinder(), modeCallback);
            }
            if (opToSwitch != -1) {
                android.util.ArraySet<com.android.server.appop.OnOpModeChangedListener> arraySet = this.mOpModeWatchers.get(opToSwitch);
                if (arraySet == null) {
                    arraySet = new android.util.ArraySet<>();
                    this.mOpModeWatchers.put(opToSwitch, arraySet);
                }
                arraySet.add(modeCallback);
            }
            if (z) {
                android.util.ArraySet<com.android.server.appop.OnOpModeChangedListener> arraySet2 = this.mPackageModeWatchers.get(str);
                if (arraySet2 == null) {
                    arraySet2 = new android.util.ArraySet<>();
                    this.mPackageModeWatchers.put(str, arraySet2);
                }
                arraySet2.add(modeCallback);
            }
        }
    }

    public void stopWatchingMode(com.android.internal.app.IAppOpsCallback iAppOpsCallback) {
        if (iAppOpsCallback == null) {
            return;
        }
        synchronized (this) {
            try {
                com.android.server.appop.AppOpsService.ModeCallback remove = this.mModeWatchers.remove(iAppOpsCallback.asBinder());
                if (remove != null) {
                    remove.unlinkToDeath();
                    for (int size = this.mOpModeWatchers.size() - 1; size >= 0; size--) {
                        android.util.ArraySet<com.android.server.appop.OnOpModeChangedListener> valueAt = this.mOpModeWatchers.valueAt(size);
                        valueAt.remove(remove);
                        if (valueAt.size() <= 0) {
                            this.mOpModeWatchers.removeAt(size);
                        }
                    }
                    for (int size2 = this.mPackageModeWatchers.size() - 1; size2 >= 0; size2--) {
                        android.util.ArraySet<com.android.server.appop.OnOpModeChangedListener> valueAt2 = this.mPackageModeWatchers.valueAt(size2);
                        valueAt2.remove(remove);
                        if (valueAt2.size() <= 0) {
                            this.mPackageModeWatchers.removeAt(size2);
                        }
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public android.app.AppOpsManagerInternal.CheckOpsDelegate getAppOpsServiceDelegate() {
        android.app.AppOpsManagerInternal.CheckOpsDelegate checkOpsDelegate;
        synchronized (this) {
            try {
                com.android.server.appop.AppOpsService.CheckOpsDelegateDispatcher checkOpsDelegateDispatcher = this.mCheckOpsDelegateDispatcher;
                checkOpsDelegate = checkOpsDelegateDispatcher != null ? checkOpsDelegateDispatcher.getCheckOpsDelegate() : null;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return checkOpsDelegate;
    }

    public void setAppOpsServiceDelegate(android.app.AppOpsManagerInternal.CheckOpsDelegate checkOpsDelegate) {
        synchronized (this) {
            try {
                com.android.server.appop.AppOpsService.CheckOpsDelegateDispatcher checkOpsDelegateDispatcher = this.mCheckOpsDelegateDispatcher;
                this.mCheckOpsDelegateDispatcher = new com.android.server.appop.AppOpsService.CheckOpsDelegateDispatcher(checkOpsDelegateDispatcher != null ? checkOpsDelegateDispatcher.mPolicy : null, checkOpsDelegate);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public int checkOperationRaw(int i, int i2, java.lang.String str, @android.annotation.Nullable java.lang.String str2) {
        return this.mCheckOpsDelegateDispatcher.checkOperation(i, i2, str, str2, 0, true);
    }

    public int checkOperationRawForDevice(int i, int i2, @android.annotation.Nullable java.lang.String str, @android.annotation.Nullable java.lang.String str2, int i3) {
        return this.mCheckOpsDelegateDispatcher.checkOperation(i, i2, str, str2, i3, true);
    }

    public int checkOperation(int i, int i2, java.lang.String str) {
        return this.mCheckOpsDelegateDispatcher.checkOperation(i, i2, str, null, 0, false);
    }

    public int checkOperationForDevice(int i, int i2, java.lang.String str, int i3) {
        return this.mCheckOpsDelegateDispatcher.checkOperation(i, i2, str, null, i3, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int checkOperationImpl(int i, int i2, java.lang.String str, @android.annotation.Nullable java.lang.String str2, int i3, boolean z) {
        verifyIncomingOp(i);
        if (!isValidVirtualDeviceId(i3)) {
            android.util.Slog.w(TAG, "checkOperationImpl returned MODE_IGNORED as virtualDeviceId " + i3 + " is invalid");
            return 1;
        }
        if (!isIncomingPackageValid(str, android.os.UserHandle.getUserId(i2))) {
            return android.app.AppOpsManager.opToDefaultMode(i);
        }
        java.lang.String resolvePackageName = android.app.AppOpsManager.resolvePackageName(i2, str);
        if (resolvePackageName == null) {
            return 1;
        }
        return checkOperationUnchecked(i, i2, resolvePackageName, str2, i3, z);
    }

    private int checkOperationUnchecked(int i, int i2, @android.annotation.NonNull java.lang.String str, @android.annotation.Nullable java.lang.String str2, int i3, boolean z) {
        int evalMode;
        try {
            com.android.server.appop.AppOpsService.PackageVerificationResult verifyAndGetBypass = verifyAndGetBypass(i2, str, null);
            if (isOpRestrictedDueToSuspend(i, str, i2)) {
                return 1;
            }
            synchronized (this) {
                try {
                    if (isOpRestrictedLocked(i2, i, str, str2, i3, verifyAndGetBypass.bypass, true)) {
                        return 1;
                    }
                    int opToSwitch = android.app.AppOpsManager.opToSwitch(i);
                    com.android.server.appop.AppOpsService.UidState uidStateLocked = getUidStateLocked(i2, false);
                    if (uidStateLocked != null && this.mAppOpsCheckingService.getUidMode(uidStateLocked.uid, getPersistentId(i3), opToSwitch) != android.app.AppOpsManager.opToDefaultMode(opToSwitch)) {
                        int uidMode = this.mAppOpsCheckingService.getUidMode(uidStateLocked.uid, getPersistentId(i3), opToSwitch);
                        if (!z) {
                            uidMode = uidStateLocked.evalMode(opToSwitch, uidMode);
                        }
                        return uidMode;
                    }
                    com.android.server.appop.AppOpsService.Op opLocked = getOpLocked(opToSwitch, i2, str, null, false, verifyAndGetBypass.bypass, false);
                    if (opLocked == null) {
                        return android.app.AppOpsManager.opToDefaultMode(opToSwitch);
                    }
                    if (z) {
                        evalMode = this.mAppOpsCheckingService.getPackageMode(opLocked.packageName, opLocked.op, android.os.UserHandle.getUserId(opLocked.uid));
                    } else {
                        evalMode = opLocked.uidState.evalMode(opLocked.op, this.mAppOpsCheckingService.getPackageMode(opLocked.packageName, opLocked.op, android.os.UserHandle.getUserId(opLocked.uid)));
                    }
                    return evalMode;
                } finally {
                }
            }
        } catch (java.lang.SecurityException e) {
            logVerifyAndGetBypassFailure(i2, e, "checkOperation");
            return android.app.AppOpsManager.opToDefaultMode(i);
        }
    }

    public int checkAudioOperation(int i, int i2, int i3, java.lang.String str) {
        return this.mCheckOpsDelegateDispatcher.checkAudioOperation(i, i2, i3, str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int checkAudioOperationImpl(int i, int i2, int i3, java.lang.String str) {
        int checkAudioOperation = this.mAudioRestrictionManager.checkAudioOperation(i, i2, i3, str);
        if (checkAudioOperation != 0) {
            return checkAudioOperation;
        }
        return checkOperation(i, i3, str);
    }

    public void setAudioRestriction(int i, int i2, int i3, int i4, java.lang.String[] strArr) {
        enforceManageAppOpsModes(android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), i3);
        verifyIncomingUid(i3);
        verifyIncomingOp(i);
        this.mAudioRestrictionManager.setZenModeAudioRestriction(i, i2, i3, i4, strArr);
        this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.server.appop.AppOpsService$$ExternalSyntheticLambda2(), this, java.lang.Integer.valueOf(i), -2));
    }

    public void setCameraAudioRestriction(int i) {
        enforceManageAppOpsModes(android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), -1);
        this.mAudioRestrictionManager.setCameraAudioRestriction(i);
        this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.server.appop.AppOpsService$$ExternalSyntheticLambda2(), this, 28, -2));
        this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.server.appop.AppOpsService$$ExternalSyntheticLambda2(), this, 3, -2));
    }

    public int checkPackage(int i, java.lang.String str) {
        java.util.Objects.requireNonNull(str);
        try {
            verifyAndGetBypass(i, str, null, null, true);
            if (resolveUid(str) != i) {
                if (isPackageExisted(str)) {
                    if (!filterAppAccessUnlocked(str, android.os.UserHandle.getUserId(i))) {
                        return 0;
                    }
                }
                return 2;
            }
            return 0;
        } catch (java.lang.SecurityException e) {
            return 2;
        }
    }

    private boolean isPackageExisted(java.lang.String str) {
        return getPackageManagerInternal().getPackageStateInternal(str) != null;
    }

    private boolean filterAppAccessUnlocked(java.lang.String str, int i) {
        return ((android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class)).filterAppAccess(str, android.os.Binder.getCallingUid(), i);
    }

    public android.app.SyncNotedAppOp noteProxyOperation(int i, android.content.AttributionSource attributionSource, boolean z, java.lang.String str, boolean z2, boolean z3) {
        return this.mCheckOpsDelegateDispatcher.noteProxyOperation(i, attributionSource, z, str, z2, z3);
    }

    public android.app.SyncNotedAppOp noteProxyOperationWithState(int i, android.content.AttributionSourceState attributionSourceState, boolean z, java.lang.String str, boolean z2, boolean z3) {
        return this.mCheckOpsDelegateDispatcher.noteProxyOperation(i, new android.content.AttributionSource(attributionSourceState), z, str, z2, z3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.app.SyncNotedAppOp noteProxyOperationImpl(int i, android.content.AttributionSource attributionSource, boolean z, java.lang.String str, boolean z2, boolean z3) {
        java.lang.String str2;
        java.lang.String str3;
        java.lang.String str4;
        java.lang.String str5;
        int i2;
        int i3;
        int uid = attributionSource.getUid();
        java.lang.String packageName = attributionSource.getPackageName();
        java.lang.String attributionTag = attributionSource.getAttributionTag();
        int nextUid = attributionSource.getNextUid();
        int deviceId = attributionSource.getDeviceId();
        java.lang.String nextPackageName = attributionSource.getNextPackageName();
        java.lang.String nextAttributionTag = attributionSource.getNextAttributionTag();
        verifyIncomingProxyUid(attributionSource);
        verifyIncomingOp(i);
        if (isValidVirtualDeviceId(deviceId)) {
            if (isIncomingPackageValid(nextPackageName, android.os.UserHandle.getUserId(nextUid))) {
                if (isIncomingPackageValid(packageName, android.os.UserHandle.getUserId(uid))) {
                    boolean z4 = z3 && isCallerAndAttributionTrusted(attributionSource);
                    java.lang.String resolvePackageName = android.app.AppOpsManager.resolvePackageName(uid, packageName);
                    if (resolvePackageName == null) {
                        return new android.app.SyncNotedAppOp(1, i, nextAttributionTag, nextPackageName);
                    }
                    boolean z5 = this.mContext.checkPermission("android.permission.UPDATE_APP_OPS_STATS", -1, uid) == 0 || (android.os.Binder.getCallingUid() == nextUid);
                    if (z4) {
                        str4 = nextAttributionTag;
                        str5 = nextPackageName;
                        i2 = deviceId;
                        i3 = nextUid;
                    } else {
                        int i4 = z5 ? 2 : 4;
                        i2 = deviceId;
                        i3 = nextUid;
                        android.app.SyncNotedAppOp noteOperationUnchecked = noteOperationUnchecked(i, uid, resolvePackageName, attributionTag, deviceId, -1, null, null, i4, !z5, "proxy " + str, z2);
                        if (noteOperationUnchecked.getOpMode() == 0) {
                            str4 = nextAttributionTag;
                            str5 = nextPackageName;
                        } else {
                            return new android.app.SyncNotedAppOp(noteOperationUnchecked.getOpMode(), i, nextAttributionTag, nextPackageName);
                        }
                    }
                    java.lang.String resolvePackageName2 = android.app.AppOpsManager.resolvePackageName(i3, str5);
                    if (resolvePackageName2 == null) {
                        return new android.app.SyncNotedAppOp(1, i, str4, str5);
                    }
                    return noteOperationUnchecked(i, i3, resolvePackageName2, str4, i2, uid, resolvePackageName, attributionTag, z5 ? 8 : 16, z, str, z2);
                }
                str2 = nextAttributionTag;
                str3 = nextPackageName;
            } else {
                str2 = nextAttributionTag;
                str3 = nextPackageName;
            }
            return new android.app.SyncNotedAppOp(2, i, str2, str3);
        }
        android.util.Slog.w(TAG, "noteProxyOperationImpl returned MODE_IGNORED as virtualDeviceId " + deviceId + " is invalid");
        return new android.app.SyncNotedAppOp(1, i, nextAttributionTag, nextPackageName);
    }

    public android.app.SyncNotedAppOp noteOperation(int i, int i2, java.lang.String str, java.lang.String str2, boolean z, java.lang.String str3, boolean z2) {
        return this.mCheckOpsDelegateDispatcher.noteOperation(i, i2, str, str2, 0, z, str3, z2);
    }

    public android.app.SyncNotedAppOp noteOperationForDevice(int i, int i2, @android.annotation.Nullable java.lang.String str, @android.annotation.Nullable java.lang.String str2, int i3, boolean z, java.lang.String str3, boolean z2) {
        return this.mCheckOpsDelegateDispatcher.noteOperation(i, i2, str, str2, i3, z, str3, z2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.app.SyncNotedAppOp noteOperationImpl(int i, int i2, @android.annotation.Nullable java.lang.String str, @android.annotation.Nullable java.lang.String str2, int i3, boolean z, @android.annotation.Nullable java.lang.String str3, boolean z2) {
        verifyIncomingUid(i2);
        verifyIncomingOp(i);
        if (!isValidVirtualDeviceId(i3)) {
            android.util.Slog.w(TAG, "checkOperationImpl returned MODE_IGNORED as virtualDeviceId " + i3 + " is invalid");
            return new android.app.SyncNotedAppOp(1, i, str2, str);
        }
        if (!isIncomingPackageValid(str, android.os.UserHandle.getUserId(i2))) {
            if (i == 111) {
                android.util.Slog.e(TAG, "noting OP_BLUETOOTH_CONNECT returned MODE_ERRORED as incoming package: " + str + " and uid: " + i2 + " is invalid");
            }
            return new android.app.SyncNotedAppOp(2, i, str2, str);
        }
        java.lang.String resolvePackageName = android.app.AppOpsManager.resolvePackageName(i2, str);
        if (resolvePackageName == null) {
            return new android.app.SyncNotedAppOp(1, i, str2, str);
        }
        return noteOperationUnchecked(i, i2, resolvePackageName, str2, i3, -1, null, null, 1, z, str3, z2);
    }

    private android.app.SyncNotedAppOp noteOperationUnchecked(int i, int i2, @android.annotation.NonNull java.lang.String str, @android.annotation.Nullable java.lang.String str2, int i3, int i4, java.lang.String str3, @android.annotation.Nullable java.lang.String str4, int i5, boolean z, @android.annotation.Nullable java.lang.String str5, boolean z2) {
        java.lang.String str6;
        java.lang.String str7;
        com.android.server.appop.AttributedOp attributedOp;
        java.lang.String str8;
        try {
            com.android.server.appop.AppOpsService.PackageVerificationResult verifyAndGetBypass = verifyAndGetBypass(i2, str, str2, str3);
            if (verifyAndGetBypass.isAttributionTagValid) {
                str6 = str2;
            } else {
                str6 = null;
            }
            if (str4 != null && !isAttributionTagDefined(str, str3, str4)) {
                str7 = null;
            } else {
                str7 = str4;
            }
            synchronized (this) {
                try {
                    java.lang.String str9 = str6;
                    com.android.server.appop.AppOpsService.Ops opsLocked = getOpsLocked(i2, str, str6, verifyAndGetBypass.isAttributionTagValid, verifyAndGetBypass.bypass, true);
                    if (opsLocked != null) {
                        com.android.server.appop.AppOpsService.Op opLocked = getOpLocked(opsLocked, i, i2, true);
                        com.android.server.appop.AttributedOp orCreateAttribution = opLocked.getOrCreateAttribution(opLocked, str9, getPersistentId(i3));
                        if (!orCreateAttribution.isRunning()) {
                            attributedOp = orCreateAttribution;
                        } else {
                            java.lang.StringBuilder sb = new java.lang.StringBuilder();
                            sb.append("Noting op not finished: uid ");
                            sb.append(i2);
                            sb.append(" pkg ");
                            sb.append(str);
                            sb.append(" code ");
                            sb.append(i);
                            sb.append(" startTime of in progress event=");
                            attributedOp = orCreateAttribution;
                            sb.append(orCreateAttribution.mInProgressEvents.valueAt(0).getStartTime());
                            android.util.Slog.w(TAG, sb.toString());
                        }
                        int opToSwitch = android.app.AppOpsManager.opToSwitch(i);
                        com.android.server.appop.AppOpsService.UidState uidState = opsLocked.uidState;
                        com.android.server.appop.AttributedOp attributedOp2 = attributedOp;
                        if (isOpRestrictedLocked(i2, i, str, str9, i3, verifyAndGetBypass.bypass, false)) {
                            attributedOp2.rejected(uidState.getState(), i5);
                            scheduleOpNotedIfNeededLocked(i, i2, str, str9, i3, i5, 1);
                            return new android.app.SyncNotedAppOp(1, i, str9, str);
                        }
                        if (this.mAppOpsCheckingService.getUidMode(uidState.uid, getPersistentId(i3), opToSwitch) == android.app.AppOpsManager.opToDefaultMode(opToSwitch)) {
                            com.android.server.appop.AppOpsService.Op opLocked2 = opToSwitch != i ? getOpLocked(opsLocked, opToSwitch, i2, true) : opLocked;
                            int evalMode = opLocked2.uidState.evalMode(opLocked2.op, this.mAppOpsCheckingService.getPackageMode(opLocked2.packageName, opLocked2.op, android.os.UserHandle.getUserId(opLocked2.uid)));
                            if (evalMode != 0) {
                                attributedOp2.rejected(uidState.getState(), i5);
                                scheduleOpNotedIfNeededLocked(i, i2, str, str9, i3, i5, evalMode);
                                if (i == 111 && evalMode == 2) {
                                    android.util.Slog.e(TAG, "noting OP_BLUETOOTH_CONNECT returned MODE_ERRORED as package mode is MODE_ERRORED");
                                }
                                return new android.app.SyncNotedAppOp(evalMode, i, str9, str);
                            }
                        } else {
                            int evalMode2 = uidState.evalMode(i, this.mAppOpsCheckingService.getUidMode(uidState.uid, getPersistentId(i3), opToSwitch));
                            if (evalMode2 != 0) {
                                attributedOp2.rejected(uidState.getState(), i5);
                                scheduleOpNotedIfNeededLocked(i, i2, str, str9, i3, i5, evalMode2);
                                if (i == 111 && evalMode2 == 2) {
                                    android.util.Slog.e(TAG, "noting OP_BLUETOOTH_CONNECT returned MODE_ERRORED as uid mode is MODE_ERRORED");
                                }
                                return new android.app.SyncNotedAppOp(evalMode2, i, str9, str);
                            }
                        }
                        scheduleOpNotedIfNeededLocked(i, i2, str, str9, i3, i5, 0);
                        attributedOp2.accessed(i4, str3, str7, uidState.getState(), i5);
                        if (z) {
                            collectAsyncNotedOp(i2, str, i, str9, i5, str5, z2);
                        }
                        return new android.app.SyncNotedAppOp(0, i, str9, str);
                    }
                    scheduleOpNotedIfNeededLocked(i, i2, str, str9, i3, i5, 1);
                    if (i != 111) {
                        str8 = str9;
                    } else {
                        java.lang.StringBuilder sb2 = new java.lang.StringBuilder();
                        sb2.append("noting OP_BLUETOOTH_CONNECT returned MODE_ERRORED as #getOpsLocked returned null for uid: ");
                        sb2.append(i2);
                        sb2.append(" packageName: ");
                        sb2.append(str);
                        sb2.append(" attributionTag: ");
                        str8 = str9;
                        sb2.append(str8);
                        sb2.append(" pvr.isAttributionTagValid: ");
                        sb2.append(verifyAndGetBypass.isAttributionTagValid);
                        sb2.append(" pvr.bypass: ");
                        sb2.append(verifyAndGetBypass.bypass);
                        android.util.Slog.e(TAG, sb2.toString());
                        android.util.Slog.e(TAG, "mUidStates.get(" + i2 + "): " + this.mUidStates.get(i2));
                    }
                    return new android.app.SyncNotedAppOp(2, i, str8, str);
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        } catch (java.lang.SecurityException e) {
            logVerifyAndGetBypassFailure(i2, e, "noteOperation");
            if (i == 111) {
                android.util.Slog.e(TAG, "noting OP_BLUETOOTH_CONNECT returned MODE_ERRORED as verifyAndGetBypass returned a SecurityException for package: " + str + " and uid: " + i2 + " and attributionTag: " + str2, e);
            }
            return new android.app.SyncNotedAppOp(2, i, str2, str);
        }
    }

    public void startWatchingActive(int[] iArr, com.android.internal.app.IAppOpsActiveCallback iAppOpsActiveCallback) {
        int i;
        android.util.SparseArray<com.android.server.appop.AppOpsService.ActiveCallback> sparseArray;
        int callingUid = android.os.Binder.getCallingUid();
        int callingPid = android.os.Binder.getCallingPid();
        if (this.mContext.checkCallingOrSelfPermission("android.permission.WATCH_APPOPS") == 0) {
            i = -1;
        } else {
            i = callingUid;
        }
        if (iArr != null) {
            com.android.internal.util.Preconditions.checkArrayElementsInRange(iArr, 0, 147, "Invalid op code in: " + java.util.Arrays.toString(iArr));
        }
        if (iAppOpsActiveCallback == null) {
            return;
        }
        synchronized (this) {
            try {
                android.util.SparseArray<com.android.server.appop.AppOpsService.ActiveCallback> sparseArray2 = this.mActiveWatchers.get(iAppOpsActiveCallback.asBinder());
                if (sparseArray2 != null) {
                    sparseArray = sparseArray2;
                } else {
                    android.util.SparseArray<com.android.server.appop.AppOpsService.ActiveCallback> sparseArray3 = new android.util.SparseArray<>();
                    this.mActiveWatchers.put(iAppOpsActiveCallback.asBinder(), sparseArray3);
                    sparseArray = sparseArray3;
                }
                com.android.server.appop.AppOpsService.ActiveCallback activeCallback = new com.android.server.appop.AppOpsService.ActiveCallback(iAppOpsActiveCallback, i, callingUid, callingPid);
                for (int i2 : iArr) {
                    sparseArray.put(i2, activeCallback);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void stopWatchingActive(com.android.internal.app.IAppOpsActiveCallback iAppOpsActiveCallback) {
        if (iAppOpsActiveCallback == null) {
            return;
        }
        synchronized (this) {
            try {
                android.util.SparseArray<com.android.server.appop.AppOpsService.ActiveCallback> remove = this.mActiveWatchers.remove(iAppOpsActiveCallback.asBinder());
                if (remove == null) {
                    return;
                }
                int size = remove.size();
                for (int i = 0; i < size; i++) {
                    remove.valueAt(i).destroy();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void startWatchingStarted(int[] iArr, @android.annotation.NonNull com.android.internal.app.IAppOpsStartedCallback iAppOpsStartedCallback) {
        int i;
        android.util.SparseArray<com.android.server.appop.AppOpsService.StartedCallback> sparseArray;
        int callingUid = android.os.Binder.getCallingUid();
        int callingPid = android.os.Binder.getCallingPid();
        if (this.mContext.checkCallingOrSelfPermission("android.permission.WATCH_APPOPS") == 0) {
            i = -1;
        } else {
            i = callingUid;
        }
        com.android.internal.util.Preconditions.checkArgument(!com.android.internal.util.ArrayUtils.isEmpty(iArr), "Ops cannot be null or empty");
        com.android.internal.util.Preconditions.checkArrayElementsInRange(iArr, 0, 147, "Invalid op code in: " + java.util.Arrays.toString(iArr));
        java.util.Objects.requireNonNull(iAppOpsStartedCallback, "Callback cannot be null");
        synchronized (this) {
            try {
                android.util.SparseArray<com.android.server.appop.AppOpsService.StartedCallback> sparseArray2 = this.mStartedWatchers.get(iAppOpsStartedCallback.asBinder());
                if (sparseArray2 != null) {
                    sparseArray = sparseArray2;
                } else {
                    android.util.SparseArray<com.android.server.appop.AppOpsService.StartedCallback> sparseArray3 = new android.util.SparseArray<>();
                    this.mStartedWatchers.put(iAppOpsStartedCallback.asBinder(), sparseArray3);
                    sparseArray = sparseArray3;
                }
                com.android.server.appop.AppOpsService.StartedCallback startedCallback = new com.android.server.appop.AppOpsService.StartedCallback(iAppOpsStartedCallback, i, callingUid, callingPid);
                for (int i2 : iArr) {
                    sparseArray.put(i2, startedCallback);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void stopWatchingStarted(com.android.internal.app.IAppOpsStartedCallback iAppOpsStartedCallback) {
        java.util.Objects.requireNonNull(iAppOpsStartedCallback, "Callback cannot be null");
        synchronized (this) {
            try {
                android.util.SparseArray<com.android.server.appop.AppOpsService.StartedCallback> remove = this.mStartedWatchers.remove(iAppOpsStartedCallback.asBinder());
                if (remove == null) {
                    return;
                }
                int size = remove.size();
                for (int i = 0; i < size; i++) {
                    remove.valueAt(i).destroy();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void startWatchingNoted(@android.annotation.NonNull int[] iArr, @android.annotation.NonNull com.android.internal.app.IAppOpsNotedCallback iAppOpsNotedCallback) {
        int i;
        android.util.SparseArray<com.android.server.appop.AppOpsService.NotedCallback> sparseArray;
        int callingUid = android.os.Binder.getCallingUid();
        int callingPid = android.os.Binder.getCallingPid();
        if (this.mContext.checkCallingOrSelfPermission("android.permission.WATCH_APPOPS") == 0) {
            i = -1;
        } else {
            i = callingUid;
        }
        com.android.internal.util.Preconditions.checkArgument(!com.android.internal.util.ArrayUtils.isEmpty(iArr), "Ops cannot be null or empty");
        com.android.internal.util.Preconditions.checkArrayElementsInRange(iArr, 0, 147, "Invalid op code in: " + java.util.Arrays.toString(iArr));
        java.util.Objects.requireNonNull(iAppOpsNotedCallback, "Callback cannot be null");
        synchronized (this) {
            try {
                android.util.SparseArray<com.android.server.appop.AppOpsService.NotedCallback> sparseArray2 = this.mNotedWatchers.get(iAppOpsNotedCallback.asBinder());
                if (sparseArray2 != null) {
                    sparseArray = sparseArray2;
                } else {
                    android.util.SparseArray<com.android.server.appop.AppOpsService.NotedCallback> sparseArray3 = new android.util.SparseArray<>();
                    this.mNotedWatchers.put(iAppOpsNotedCallback.asBinder(), sparseArray3);
                    sparseArray = sparseArray3;
                }
                com.android.server.appop.AppOpsService.NotedCallback notedCallback = new com.android.server.appop.AppOpsService.NotedCallback(iAppOpsNotedCallback, i, callingUid, callingPid);
                for (int i2 : iArr) {
                    sparseArray.put(i2, notedCallback);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void stopWatchingNoted(com.android.internal.app.IAppOpsNotedCallback iAppOpsNotedCallback) {
        java.util.Objects.requireNonNull(iAppOpsNotedCallback, "Callback cannot be null");
        synchronized (this) {
            try {
                android.util.SparseArray<com.android.server.appop.AppOpsService.NotedCallback> remove = this.mNotedWatchers.remove(iAppOpsNotedCallback.asBinder());
                if (remove == null) {
                    return;
                }
                int size = remove.size();
                for (int i = 0; i < size; i++) {
                    remove.valueAt(i).destroy();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void collectAsyncNotedOp(final int i, @android.annotation.NonNull final java.lang.String str, final int i2, @android.annotation.Nullable final java.lang.String str2, int i3, @android.annotation.NonNull java.lang.String str3, boolean z) {
        android.app.AsyncNotedAppOp asyncNotedAppOp;
        android.os.RemoteCallbackList<com.android.internal.app.IAppOpsAsyncNotedCallback> remoteCallbackList;
        android.util.Pair<java.lang.String, java.lang.Integer> pair;
        java.util.Objects.requireNonNull(str3);
        int callingUid = android.os.Binder.getCallingUid();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            synchronized (this) {
                try {
                    android.util.Pair<java.lang.String, java.lang.Integer> asyncNotedOpsKey = getAsyncNotedOpsKey(str, i);
                    android.os.RemoteCallbackList<com.android.internal.app.IAppOpsAsyncNotedCallback> remoteCallbackList2 = this.mAsyncOpWatchers.get(asyncNotedOpsKey);
                    android.app.AsyncNotedAppOp asyncNotedAppOp2 = new android.app.AsyncNotedAppOp(i2, callingUid, str2, str3, java.lang.System.currentTimeMillis());
                    final boolean[] zArr = {false};
                    if ((i3 & 9) == 0 || !z) {
                        asyncNotedAppOp = asyncNotedAppOp2;
                        remoteCallbackList = remoteCallbackList2;
                        pair = asyncNotedOpsKey;
                    } else {
                        asyncNotedAppOp = asyncNotedAppOp2;
                        remoteCallbackList = remoteCallbackList2;
                        pair = asyncNotedOpsKey;
                        reportRuntimeAppOpAccessMessageAsyncLocked(i, str, i2, str2, str3);
                    }
                    if (remoteCallbackList != null) {
                        final android.app.AsyncNotedAppOp asyncNotedAppOp3 = asyncNotedAppOp;
                        remoteCallbackList.broadcast(new java.util.function.Consumer() { // from class: com.android.server.appop.AppOpsService$$ExternalSyntheticLambda13
                            @Override // java.util.function.Consumer
                            public final void accept(java.lang.Object obj) {
                                com.android.server.appop.AppOpsService.lambda$collectAsyncNotedOp$3(asyncNotedAppOp3, zArr, i2, str, i, str2, (com.android.internal.app.IAppOpsAsyncNotedCallback) obj);
                            }
                        });
                    }
                    if (!zArr[0]) {
                        java.util.ArrayList<android.app.AsyncNotedAppOp> arrayList = this.mUnforwardedAsyncNotedOps.get(pair);
                        if (arrayList == null) {
                            arrayList = new java.util.ArrayList<>(1);
                            this.mUnforwardedAsyncNotedOps.put(pair, arrayList);
                        }
                        arrayList.add(asyncNotedAppOp);
                        if (arrayList.size() > 10) {
                            arrayList.remove(0);
                        }
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$collectAsyncNotedOp$3(android.app.AsyncNotedAppOp asyncNotedAppOp, boolean[] zArr, int i, java.lang.String str, int i2, java.lang.String str2, com.android.internal.app.IAppOpsAsyncNotedCallback iAppOpsAsyncNotedCallback) {
        try {
            iAppOpsAsyncNotedCallback.opNoted(asyncNotedAppOp);
            zArr[0] = true;
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Could not forward noteOp of " + i + " to " + str + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + i2 + "(" + str2 + ")", e);
        }
    }

    @android.annotation.NonNull
    private android.util.Pair<java.lang.String, java.lang.Integer> getAsyncNotedOpsKey(@android.annotation.NonNull java.lang.String str, int i) {
        return new android.util.Pair<>(str, java.lang.Integer.valueOf(i));
    }

    public void startWatchingAsyncNoted(java.lang.String str, com.android.internal.app.IAppOpsAsyncNotedCallback iAppOpsAsyncNotedCallback) {
        java.util.Objects.requireNonNull(str);
        java.util.Objects.requireNonNull(iAppOpsAsyncNotedCallback);
        int callingUid = android.os.Binder.getCallingUid();
        final android.util.Pair<java.lang.String, java.lang.Integer> asyncNotedOpsKey = getAsyncNotedOpsKey(str, callingUid);
        verifyAndGetBypass(callingUid, str, null);
        synchronized (this) {
            try {
                android.os.RemoteCallbackList<com.android.internal.app.IAppOpsAsyncNotedCallback> remoteCallbackList = this.mAsyncOpWatchers.get(asyncNotedOpsKey);
                if (remoteCallbackList == null) {
                    remoteCallbackList = new android.os.RemoteCallbackList<com.android.internal.app.IAppOpsAsyncNotedCallback>() { // from class: com.android.server.appop.AppOpsService.8
                        @Override // android.os.RemoteCallbackList
                        public void onCallbackDied(com.android.internal.app.IAppOpsAsyncNotedCallback iAppOpsAsyncNotedCallback2) {
                            synchronized (com.android.server.appop.AppOpsService.this) {
                                try {
                                    if (getRegisteredCallbackCount() == 0) {
                                        com.android.server.appop.AppOpsService.this.mAsyncOpWatchers.remove(asyncNotedOpsKey);
                                    }
                                } catch (java.lang.Throwable th) {
                                    throw th;
                                }
                            }
                        }
                    };
                    this.mAsyncOpWatchers.put(asyncNotedOpsKey, remoteCallbackList);
                }
                remoteCallbackList.register(iAppOpsAsyncNotedCallback);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void stopWatchingAsyncNoted(java.lang.String str, com.android.internal.app.IAppOpsAsyncNotedCallback iAppOpsAsyncNotedCallback) {
        java.util.Objects.requireNonNull(str);
        java.util.Objects.requireNonNull(iAppOpsAsyncNotedCallback);
        int callingUid = android.os.Binder.getCallingUid();
        android.util.Pair<java.lang.String, java.lang.Integer> asyncNotedOpsKey = getAsyncNotedOpsKey(str, callingUid);
        verifyAndGetBypass(callingUid, str, null);
        synchronized (this) {
            try {
                android.os.RemoteCallbackList<com.android.internal.app.IAppOpsAsyncNotedCallback> remoteCallbackList = this.mAsyncOpWatchers.get(asyncNotedOpsKey);
                if (remoteCallbackList != null) {
                    remoteCallbackList.unregister(iAppOpsAsyncNotedCallback);
                    if (remoteCallbackList.getRegisteredCallbackCount() == 0) {
                        this.mAsyncOpWatchers.remove(asyncNotedOpsKey);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public java.util.List<android.app.AsyncNotedAppOp> extractAsyncOps(java.lang.String str) {
        java.util.ArrayList<android.app.AsyncNotedAppOp> remove;
        java.util.Objects.requireNonNull(str);
        int callingUid = android.os.Binder.getCallingUid();
        verifyAndGetBypass(callingUid, str, null);
        synchronized (this) {
            remove = this.mUnforwardedAsyncNotedOps.remove(getAsyncNotedOpsKey(str, callingUid));
        }
        return remove;
    }

    public android.app.SyncNotedAppOp startOperation(android.os.IBinder iBinder, int i, int i2, @android.annotation.Nullable java.lang.String str, @android.annotation.Nullable java.lang.String str2, boolean z, boolean z2, java.lang.String str3, boolean z3, int i3, int i4) {
        return this.mCheckOpsDelegateDispatcher.startOperation(iBinder, i, i2, str, str2, 0, z, z2, str3, z3, i3, i4);
    }

    public android.app.SyncNotedAppOp startOperationForDevice(android.os.IBinder iBinder, int i, int i2, @android.annotation.Nullable java.lang.String str, @android.annotation.Nullable java.lang.String str2, int i3, boolean z, boolean z2, java.lang.String str3, boolean z3, int i4, int i5) {
        return this.mCheckOpsDelegateDispatcher.startOperation(iBinder, i, i2, str, str2, i3, z, z2, str3, z3, i4, i5);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.app.SyncNotedAppOp startOperationImpl(@android.annotation.NonNull android.os.IBinder iBinder, int i, int i2, @android.annotation.Nullable java.lang.String str, @android.annotation.Nullable java.lang.String str2, int i3, boolean z, boolean z2, @android.annotation.NonNull java.lang.String str3, boolean z3, int i4, int i5) {
        int checkOperation;
        int checkOperation2;
        verifyIncomingUid(i2);
        verifyIncomingOp(i);
        if (isValidVirtualDeviceId(i3)) {
            if (!isIncomingPackageValid(str, android.os.UserHandle.getUserId(i2))) {
                return new android.app.SyncNotedAppOp(2, i, str2, str);
            }
            if (android.app.AppOpsManager.resolvePackageName(i2, str) == null) {
                return new android.app.SyncNotedAppOp(1, i, str2, str);
            }
            if ((i == 102 || i == 120 || i == 135) && (checkOperation = checkOperation(27, i2, str)) != 0) {
                return new android.app.SyncNotedAppOp(checkOperation, i, str2, str);
            }
            if (i == 134 && (checkOperation2 = checkOperation(26, i2, str)) != 0) {
                return new android.app.SyncNotedAppOp(checkOperation2, i, str2, str);
            }
            return startOperationUnchecked(iBinder, i, i2, str, str2, i3, -1, null, null, 1, z, z2, str3, z3, i4, i5);
        }
        android.util.Slog.w(TAG, "startOperationImpl returned MODE_IGNORED as virtualDeviceId " + i3 + " is invalid");
        return new android.app.SyncNotedAppOp(1, i, str2, str);
    }

    public android.app.SyncNotedAppOp startProxyOperation(@android.annotation.NonNull android.os.IBinder iBinder, int i, @android.annotation.NonNull android.content.AttributionSource attributionSource, boolean z, boolean z2, java.lang.String str, boolean z3, boolean z4, int i2, int i3, int i4) {
        return this.mCheckOpsDelegateDispatcher.startProxyOperation(iBinder, i, attributionSource, z, z2, str, z3, z4, i2, i3, i4);
    }

    public android.app.SyncNotedAppOp startProxyOperationWithState(@android.annotation.NonNull android.os.IBinder iBinder, int i, @android.annotation.NonNull android.content.AttributionSourceState attributionSourceState, boolean z, boolean z2, java.lang.String str, boolean z3, boolean z4, int i2, int i3, int i4) {
        return this.mCheckOpsDelegateDispatcher.startProxyOperation(iBinder, i, new android.content.AttributionSource(attributionSourceState), z, z2, str, z3, z4, i2, i3, i4);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:32:0x009d  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x00ba  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x00c0  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x009f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public android.app.SyncNotedAppOp startProxyOperationImpl(@android.annotation.NonNull android.os.IBinder iBinder, int i, @android.annotation.NonNull android.content.AttributionSource attributionSource, boolean z, boolean z2, java.lang.String str, boolean z3, boolean z4, int i2, int i3, int i4) {
        java.lang.String str2;
        int i5;
        boolean z5;
        java.lang.String resolvePackageName;
        int i6;
        int i7;
        java.lang.String str3;
        int i8;
        int uid = attributionSource.getUid();
        java.lang.String packageName = attributionSource.getPackageName();
        java.lang.String attributionTag = attributionSource.getAttributionTag();
        int nextUid = attributionSource.getNextUid();
        int deviceId = attributionSource.getDeviceId();
        java.lang.String nextPackageName = attributionSource.getNextPackageName();
        java.lang.String nextAttributionTag = attributionSource.getNextAttributionTag();
        verifyIncomingProxyUid(attributionSource);
        verifyIncomingOp(i);
        if (isValidVirtualDeviceId(deviceId)) {
            if (isIncomingPackageValid(packageName, android.os.UserHandle.getUserId(uid))) {
                if (isIncomingPackageValid(nextPackageName, android.os.UserHandle.getUserId(nextUid))) {
                    boolean isCallerAndAttributionTrusted = isCallerAndAttributionTrusted(attributionSource);
                    boolean z6 = isCallerAndAttributionTrusted && z4;
                    java.lang.String resolvePackageName2 = android.app.AppOpsManager.resolvePackageName(uid, packageName);
                    if (resolvePackageName2 == null) {
                        return new android.app.SyncNotedAppOp(1, i, nextAttributionTag, nextPackageName);
                    }
                    if (isCallerAndAttributionTrusted && i4 != -1 && ((i2 & 8) != 0 || (i3 & 8) != 0)) {
                        z5 = true;
                        boolean z7 = this.mContext.checkPermission("android.permission.UPDATE_APP_OPS_STATS", -1, uid) != 0 || (android.os.Binder.getCallingUid() != nextUid) || z5;
                        resolvePackageName = android.app.AppOpsManager.resolvePackageName(nextUid, nextPackageName);
                        if (resolvePackageName != null) {
                            return new android.app.SyncNotedAppOp(1, i, nextAttributionTag, nextPackageName);
                        }
                        int i9 = z7 ? 8 : 16;
                        if (z6) {
                            i6 = nextUid;
                            i7 = uid;
                            str3 = nextAttributionTag;
                            i8 = deviceId;
                        } else {
                            i6 = nextUid;
                            android.app.SyncNotedAppOp startOperationDryRun = startOperationDryRun(i, nextUid, resolvePackageName, nextAttributionTag, deviceId, resolvePackageName2, i9, z);
                            if (!shouldStartForMode(startOperationDryRun.getOpMode(), z)) {
                                return startOperationDryRun;
                            }
                            int i10 = z7 ? 2 : 4;
                            i8 = deviceId;
                            str3 = nextAttributionTag;
                            i7 = uid;
                            android.app.SyncNotedAppOp startOperationUnchecked = startOperationUnchecked(iBinder, i, uid, resolvePackageName2, attributionTag, i8, -1, null, null, i10, z, !z7, "proxy " + str, z3, i2, i4);
                            if (!shouldStartForMode(startOperationUnchecked.getOpMode(), z)) {
                                return startOperationUnchecked;
                            }
                        }
                        return startOperationUnchecked(iBinder, i, i6, resolvePackageName, str3, i8, i7, resolvePackageName2, attributionTag, i9, z, z2, str, z3, i3, i4);
                    }
                    z5 = false;
                    if (this.mContext.checkPermission("android.permission.UPDATE_APP_OPS_STATS", -1, uid) != 0) {
                    }
                    resolvePackageName = android.app.AppOpsManager.resolvePackageName(nextUid, nextPackageName);
                    if (resolvePackageName != null) {
                    }
                } else {
                    str2 = nextAttributionTag;
                    i5 = 2;
                }
            } else {
                str2 = nextAttributionTag;
                i5 = 2;
            }
            return new android.app.SyncNotedAppOp(i5, i, str2, nextPackageName);
        }
        android.util.Slog.w(TAG, "startProxyOperationImpl returned MODE_IGNORED as virtualDeviceId " + deviceId + " is invalid");
        return new android.app.SyncNotedAppOp(1, i, nextAttributionTag, nextPackageName);
    }

    private boolean shouldStartForMode(int i, boolean z) {
        return i == 0 || (i == 3 && z);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r14v5 */
    /* JADX WARN: Type inference failed for: r14v6, types: [int] */
    /* JADX WARN: Type inference failed for: r14v7 */
    private android.app.SyncNotedAppOp startOperationUnchecked(android.os.IBinder iBinder, int i, int i2, @android.annotation.NonNull java.lang.String str, @android.annotation.Nullable java.lang.String str2, int i3, int i4, java.lang.String str3, @android.annotation.Nullable java.lang.String str4, int i5, boolean z, boolean z2, @android.annotation.Nullable java.lang.String str5, boolean z3, int i6, int i7) {
        java.lang.String str6;
        java.lang.String str7;
        com.android.server.appop.AttributedOp attributedOp;
        com.android.server.appop.AppOpsService.Op op;
        java.lang.String str8;
        ?? r14;
        int i8;
        int i9;
        try {
            com.android.server.appop.AppOpsService.PackageVerificationResult verifyAndGetBypass = verifyAndGetBypass(i2, str, str2, str3);
            if (verifyAndGetBypass.isAttributionTagValid) {
                str6 = str2;
            } else {
                str6 = null;
            }
            if (str4 != null && !isAttributionTagDefined(str, str3, str4)) {
                str7 = null;
            } else {
                str7 = str4;
            }
            synchronized (this) {
                try {
                    java.lang.String str9 = str6;
                    com.android.server.appop.AppOpsService.Ops opsLocked = getOpsLocked(i2, str, str6, verifyAndGetBypass.isAttributionTagValid, verifyAndGetBypass.bypass, true);
                    if (opsLocked != null) {
                        com.android.server.appop.AppOpsService.Op opLocked = getOpLocked(opsLocked, i, i2, true);
                        com.android.server.appop.AttributedOp orCreateAttribution = opLocked.getOrCreateAttribution(opLocked, str9, getPersistentId(i3));
                        com.android.server.appop.AppOpsService.UidState uidState = opsLocked.uidState;
                        boolean isOpRestrictedLocked = isOpRestrictedLocked(i2, i, str, str9, i3, verifyAndGetBypass.bypass, false);
                        int opToSwitch = android.app.AppOpsManager.opToSwitch(i);
                        if (this.mAppOpsCheckingService.getUidMode(uidState.uid, getPersistentId(i3), opToSwitch) != android.app.AppOpsManager.opToDefaultMode(opToSwitch)) {
                            int evalMode = uidState.evalMode(i, this.mAppOpsCheckingService.getUidMode(uidState.uid, getPersistentId(i3), opToSwitch));
                            if (!shouldStartForMode(evalMode, z)) {
                                orCreateAttribution.rejected(uidState.getState(), i5);
                                scheduleOpStartedIfNeededLocked(i, i2, str, str9, i3, i5, evalMode, 0, i6, i7);
                                return new android.app.SyncNotedAppOp(evalMode, i, str9, str);
                            }
                            str8 = str9;
                            attributedOp = orCreateAttribution;
                        } else {
                            if (opToSwitch != i) {
                                attributedOp = orCreateAttribution;
                                op = getOpLocked(opsLocked, opToSwitch, i2, true);
                            } else {
                                attributedOp = orCreateAttribution;
                                op = opLocked;
                            }
                            int evalMode2 = op.uidState.evalMode(op.op, this.mAppOpsCheckingService.getPackageMode(op.packageName, op.op, android.os.UserHandle.getUserId(op.uid)));
                            if (evalMode2 == 0) {
                                str8 = str9;
                            } else if (z && evalMode2 == 3) {
                                str8 = str9;
                            } else {
                                attributedOp.rejected(uidState.getState(), i5);
                                scheduleOpStartedIfNeededLocked(i, i2, str, str9, i3, i5, evalMode2, 0, i6, i7);
                                return new android.app.SyncNotedAppOp(evalMode2, i, str9, str);
                            }
                        }
                        if (isOpRestrictedLocked) {
                            i8 = 1;
                            r14 = isOpRestrictedLocked;
                            attributedOp.createPaused(iBinder, i4, str3, str7, i3, uidState.getState(), i5, i6, i7);
                            i9 = 0;
                        } else {
                            r14 = isOpRestrictedLocked;
                            i8 = 1;
                            attributedOp.started(iBinder, i4, str3, str7, i3, uidState.getState(), i5, i6, i7);
                            i9 = 1;
                        }
                        scheduleOpStartedIfNeededLocked(i, i2, str, str8, i3, i5, r14 != 0 ? i8 : 0, i9, i6, i7);
                        if (z2 && r14 == 0) {
                            collectAsyncNotedOp(i2, str, i, str8, 1, str5, z3);
                        }
                        return new android.app.SyncNotedAppOp(r14, i, str8, str);
                    }
                    scheduleOpStartedIfNeededLocked(i, i2, str, str9, i3, i5, 1, 0, i6, i7);
                    return new android.app.SyncNotedAppOp(2, i, str9, str);
                } catch (android.os.RemoteException e) {
                    throw new java.lang.RuntimeException(e);
                } finally {
                }
            }
        } catch (java.lang.SecurityException e2) {
            logVerifyAndGetBypassFailure(i2, e2, "startOperation");
            return new android.app.SyncNotedAppOp(2, i, str2, str);
        }
    }

    private android.app.SyncNotedAppOp startOperationDryRun(int i, int i2, @android.annotation.NonNull java.lang.String str, @android.annotation.Nullable java.lang.String str2, int i3, java.lang.String str3, int i4, boolean z) {
        java.lang.String str4;
        com.android.server.appop.AppOpsService.Op op;
        try {
            com.android.server.appop.AppOpsService.PackageVerificationResult verifyAndGetBypass = verifyAndGetBypass(i2, str, str2, str3);
            if (verifyAndGetBypass.isAttributionTagValid) {
                str4 = str2;
            } else {
                str4 = null;
            }
            synchronized (this) {
                try {
                    com.android.server.appop.AppOpsService.Ops opsLocked = getOpsLocked(i2, str, str4, verifyAndGetBypass.isAttributionTagValid, verifyAndGetBypass.bypass, true);
                    if (opsLocked != null) {
                        com.android.server.appop.AppOpsService.Op opLocked = getOpLocked(opsLocked, i, i2, true);
                        com.android.server.appop.AppOpsService.UidState uidState = opsLocked.uidState;
                        boolean isOpRestrictedLocked = isOpRestrictedLocked(i2, i, str, str4, i3, verifyAndGetBypass.bypass, false);
                        int opToSwitch = android.app.AppOpsManager.opToSwitch(i);
                        if (this.mAppOpsCheckingService.getUidMode(uidState.uid, getPersistentId(i3), opToSwitch) != android.app.AppOpsManager.opToDefaultMode(opToSwitch)) {
                            int evalMode = uidState.evalMode(i, this.mAppOpsCheckingService.getUidMode(uidState.uid, getPersistentId(i3), opToSwitch));
                            if (!shouldStartForMode(evalMode, z)) {
                                return new android.app.SyncNotedAppOp(evalMode, i, str4, str);
                            }
                        } else {
                            if (opToSwitch != i) {
                                op = getOpLocked(opsLocked, opToSwitch, i2, true);
                            } else {
                                op = opLocked;
                            }
                            int evalMode2 = op.uidState.evalMode(op.op, this.mAppOpsCheckingService.getPackageMode(op.packageName, op.op, android.os.UserHandle.getUserId(op.uid)));
                            if (evalMode2 != 0 && (!z || evalMode2 != 3)) {
                                return new android.app.SyncNotedAppOp(evalMode2, i, str4, str);
                            }
                        }
                        return new android.app.SyncNotedAppOp(isOpRestrictedLocked ? 1 : 0, i, str4, str);
                    }
                    return new android.app.SyncNotedAppOp(2, i, str4, str);
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        } catch (java.lang.SecurityException e) {
            if (android.os.Process.isIsolated(i2)) {
                android.util.Slog.e(TAG, "Cannot startOperation: isolated process");
            } else {
                android.util.Slog.e(TAG, "Cannot startOperation", e);
            }
            return new android.app.SyncNotedAppOp(2, i, str2, str);
        }
    }

    public void finishOperation(android.os.IBinder iBinder, int i, int i2, java.lang.String str, java.lang.String str2) {
        this.mCheckOpsDelegateDispatcher.finishOperation(iBinder, i, i2, str, str2, 0);
    }

    public void finishOperationForDevice(android.os.IBinder iBinder, int i, int i2, @android.annotation.Nullable java.lang.String str, @android.annotation.Nullable java.lang.String str2, int i3) {
        this.mCheckOpsDelegateDispatcher.finishOperation(iBinder, i, i2, str, str2, i3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void finishOperationImpl(android.os.IBinder iBinder, int i, int i2, java.lang.String str, java.lang.String str2, int i3) {
        java.lang.String resolvePackageName;
        verifyIncomingUid(i2);
        verifyIncomingOp(i);
        if (!isValidVirtualDeviceId(i3)) {
            android.util.Slog.w(TAG, "finishOperationImpl was a no-op as virtualDeviceId " + i3 + " is invalid");
            return;
        }
        if (!isIncomingPackageValid(str, android.os.UserHandle.getUserId(i2)) || (resolvePackageName = android.app.AppOpsManager.resolvePackageName(i2, str)) == null) {
            return;
        }
        finishOperationUnchecked(iBinder, i, i2, resolvePackageName, str2, i3);
    }

    public void finishProxyOperation(@android.annotation.NonNull android.os.IBinder iBinder, int i, @android.annotation.NonNull android.content.AttributionSource attributionSource, boolean z) {
        this.mCheckOpsDelegateDispatcher.finishProxyOperation(iBinder, i, attributionSource, z);
    }

    public void finishProxyOperationWithState(@android.annotation.NonNull android.os.IBinder iBinder, int i, @android.annotation.NonNull android.content.AttributionSourceState attributionSourceState, boolean z) {
        this.mCheckOpsDelegateDispatcher.finishProxyOperation(iBinder, i, new android.content.AttributionSource(attributionSourceState), z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public java.lang.Void finishProxyOperationImpl(android.os.IBinder iBinder, int i, @android.annotation.NonNull android.content.AttributionSource attributionSource, boolean z) {
        java.lang.String resolvePackageName;
        int uid = attributionSource.getUid();
        java.lang.String packageName = attributionSource.getPackageName();
        java.lang.String attributionTag = attributionSource.getAttributionTag();
        int nextUid = attributionSource.getNextUid();
        int deviceId = attributionSource.getDeviceId();
        java.lang.String nextPackageName = attributionSource.getNextPackageName();
        java.lang.String nextAttributionTag = attributionSource.getNextAttributionTag();
        boolean z2 = z && isCallerAndAttributionTrusted(attributionSource);
        verifyIncomingProxyUid(attributionSource);
        verifyIncomingOp(i);
        if (!isValidVirtualDeviceId(deviceId)) {
            android.util.Slog.w(TAG, "finishProxyOperationImpl was a no-op as virtualDeviceId " + deviceId + " is invalid");
            return null;
        }
        if (!isIncomingPackageValid(packageName, android.os.UserHandle.getUserId(uid)) || !isIncomingPackageValid(nextPackageName, android.os.UserHandle.getUserId(nextUid)) || (resolvePackageName = android.app.AppOpsManager.resolvePackageName(uid, packageName)) == null) {
            return null;
        }
        if (!z2) {
            finishOperationUnchecked(iBinder, i, uid, resolvePackageName, attributionTag, deviceId);
        }
        java.lang.String resolvePackageName2 = android.app.AppOpsManager.resolvePackageName(nextUid, nextPackageName);
        if (resolvePackageName2 == null) {
            return null;
        }
        finishOperationUnchecked(iBinder, i, nextUid, resolvePackageName2, nextAttributionTag, deviceId);
        return null;
    }

    private void finishOperationUnchecked(android.os.IBinder iBinder, int i, int i2, java.lang.String str, java.lang.String str2, int i3) {
        try {
            com.android.server.appop.AppOpsService.PackageVerificationResult verifyAndGetBypass = verifyAndGetBypass(i2, str, str2);
            if (!verifyAndGetBypass.isAttributionTagValid) {
                str2 = null;
            }
            synchronized (this) {
                try {
                    com.android.server.appop.AppOpsService.Op opLocked = getOpLocked(i, i2, str, str2, verifyAndGetBypass.isAttributionTagValid, verifyAndGetBypass.bypass, true);
                    if (opLocked == null) {
                        android.util.Slog.e(TAG, "Operation not found: uid=" + i2 + " pkg=" + str + "(" + str2 + ") op=" + android.app.AppOpsManager.opToName(i));
                        return;
                    }
                    com.android.server.appop.AttributedOp attributedOp = opLocked.mDeviceAttributedOps.getOrDefault(getPersistentId(i3), new android.util.ArrayMap<>()).get(str2);
                    if (attributedOp == null) {
                        android.util.Slog.e(TAG, "Attribution not found: uid=" + i2 + " pkg=" + str + "(" + str2 + ") op=" + android.app.AppOpsManager.opToName(i));
                        return;
                    }
                    if (attributedOp.isRunning() || attributedOp.isPaused()) {
                        attributedOp.finished(iBinder);
                    } else {
                        android.util.Slog.e(TAG, "Operation not started: uid=" + i2 + " pkg=" + str + "(" + str2 + ") op=" + android.app.AppOpsManager.opToName(i));
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        } catch (java.lang.SecurityException e) {
            logVerifyAndGetBypassFailure(i2, e, "finishOperation");
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x0029, code lost:
    
        r2 = new android.util.ArraySet();
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    void scheduleOpActiveChangedIfNeededLocked(int i, int i2, @android.annotation.NonNull java.lang.String str, @android.annotation.Nullable java.lang.String str2, int i3, boolean z, int i4, int i5) {
        int size = this.mActiveWatchers.size();
        android.util.ArraySet arraySet = null;
        for (int i6 = 0; i6 < size; i6++) {
            com.android.server.appop.AppOpsService.ActiveCallback activeCallback = this.mActiveWatchers.valueAt(i6).get(i);
            if (activeCallback != null) {
                if (activeCallback.mWatchingUid >= 0 && activeCallback.mWatchingUid != i2) {
                }
                arraySet.add(activeCallback);
            }
        }
        if (arraySet == null) {
            return;
        }
        this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.DecConsumer() { // from class: com.android.server.appop.AppOpsService$$ExternalSyntheticLambda8
            public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3, java.lang.Object obj4, java.lang.Object obj5, java.lang.Object obj6, java.lang.Object obj7, java.lang.Object obj8, java.lang.Object obj9, java.lang.Object obj10) {
                ((com.android.server.appop.AppOpsService) obj).notifyOpActiveChanged((android.util.ArraySet) obj2, ((java.lang.Integer) obj3).intValue(), ((java.lang.Integer) obj4).intValue(), (java.lang.String) obj5, (java.lang.String) obj6, ((java.lang.Integer) obj7).intValue(), ((java.lang.Boolean) obj8).booleanValue(), ((java.lang.Integer) obj9).intValue(), ((java.lang.Integer) obj10).intValue());
            }
        }, this, arraySet, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2), str, str2, java.lang.Integer.valueOf(i3), java.lang.Boolean.valueOf(z), java.lang.Integer.valueOf(i4), java.lang.Integer.valueOf(i5)));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyOpActiveChanged(android.util.ArraySet<com.android.server.appop.AppOpsService.ActiveCallback> arraySet, int i, int i2, @android.annotation.NonNull java.lang.String str, @android.annotation.Nullable java.lang.String str2, int i3, boolean z, int i4, int i5) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            int size = arraySet.size();
            for (int i6 = 0; i6 < size; i6++) {
                com.android.server.appop.AppOpsService.ActiveCallback valueAt = arraySet.valueAt(i6);
                try {
                    try {
                        if (!shouldIgnoreCallback(i, valueAt.mCallingPid, valueAt.mCallingUid)) {
                            valueAt.mCallback.opActiveChanged(i, i2, str, str2, i3, z, i4, i5);
                        }
                    } catch (android.os.RemoteException e) {
                    }
                } catch (android.os.RemoteException e2) {
                }
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x002b, code lost:
    
        r2 = new android.util.ArraySet();
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    void scheduleOpStartedIfNeededLocked(int i, int i2, java.lang.String str, java.lang.String str2, int i3, int i4, int i5, int i6, int i7, int i8) {
        int size = this.mStartedWatchers.size();
        android.util.ArraySet arraySet = null;
        for (int i9 = 0; i9 < size; i9++) {
            com.android.server.appop.AppOpsService.StartedCallback startedCallback = this.mStartedWatchers.valueAt(i9).get(i);
            if (startedCallback != null) {
                if (startedCallback.mWatchingUid >= 0 && startedCallback.mWatchingUid != i2) {
                }
                arraySet.add(startedCallback);
            }
        }
        if (arraySet == null) {
            return;
        }
        this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.DodecConsumer() { // from class: com.android.server.appop.AppOpsService$$ExternalSyntheticLambda10
            public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3, java.lang.Object obj4, java.lang.Object obj5, java.lang.Object obj6, java.lang.Object obj7, java.lang.Object obj8, java.lang.Object obj9, java.lang.Object obj10, java.lang.Object obj11, java.lang.Object obj12) {
                ((com.android.server.appop.AppOpsService) obj).notifyOpStarted((android.util.ArraySet) obj2, ((java.lang.Integer) obj3).intValue(), ((java.lang.Integer) obj4).intValue(), (java.lang.String) obj5, (java.lang.String) obj6, ((java.lang.Integer) obj7).intValue(), ((java.lang.Integer) obj8).intValue(), ((java.lang.Integer) obj9).intValue(), ((java.lang.Integer) obj10).intValue(), ((java.lang.Integer) obj11).intValue(), ((java.lang.Integer) obj12).intValue());
            }
        }, this, arraySet, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2), str, str2, java.lang.Integer.valueOf(i3), java.lang.Integer.valueOf(i4), java.lang.Integer.valueOf(i5), java.lang.Integer.valueOf(i6), java.lang.Integer.valueOf(i7), java.lang.Integer.valueOf(i8)));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyOpStarted(android.util.ArraySet<com.android.server.appop.AppOpsService.StartedCallback> arraySet, int i, int i2, java.lang.String str, java.lang.String str2, int i3, int i4, int i5, int i6, int i7, int i8) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            int size = arraySet.size();
            for (int i9 = 0; i9 < size; i9++) {
                com.android.server.appop.AppOpsService.StartedCallback valueAt = arraySet.valueAt(i9);
                try {
                    try {
                        if (!shouldIgnoreCallback(i, valueAt.mCallingPid, valueAt.mCallingUid)) {
                            valueAt.mCallback.opStarted(i, i2, str, str2, i3, i4, i5, i6, i7, i8);
                        }
                    } catch (android.os.RemoteException e) {
                    }
                } catch (android.os.RemoteException e2) {
                }
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x0029, code lost:
    
        r2 = new android.util.ArraySet();
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void scheduleOpNotedIfNeededLocked(int i, int i2, java.lang.String str, java.lang.String str2, int i3, int i4, int i5) {
        int size = this.mNotedWatchers.size();
        android.util.ArraySet arraySet = null;
        for (int i6 = 0; i6 < size; i6++) {
            com.android.server.appop.AppOpsService.NotedCallback notedCallback = this.mNotedWatchers.valueAt(i6).get(i);
            if (notedCallback != null) {
                if (notedCallback.mWatchingUid >= 0 && notedCallback.mWatchingUid != i2) {
                }
                arraySet.add(notedCallback);
            }
        }
        if (arraySet == null) {
            return;
        }
        this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.NonaConsumer() { // from class: com.android.server.appop.AppOpsService$$ExternalSyntheticLambda3
            public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3, java.lang.Object obj4, java.lang.Object obj5, java.lang.Object obj6, java.lang.Object obj7, java.lang.Object obj8, java.lang.Object obj9) {
                ((com.android.server.appop.AppOpsService) obj).notifyOpChecked((android.util.ArraySet) obj2, ((java.lang.Integer) obj3).intValue(), ((java.lang.Integer) obj4).intValue(), (java.lang.String) obj5, (java.lang.String) obj6, ((java.lang.Integer) obj7).intValue(), ((java.lang.Integer) obj8).intValue(), ((java.lang.Integer) obj9).intValue());
            }
        }, this, arraySet, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2), str, str2, java.lang.Integer.valueOf(i3), java.lang.Integer.valueOf(i4), java.lang.Integer.valueOf(i5)));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyOpChecked(android.util.ArraySet<com.android.server.appop.AppOpsService.NotedCallback> arraySet, int i, int i2, java.lang.String str, java.lang.String str2, int i3, int i4, int i5) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            int size = arraySet.size();
            for (int i6 = 0; i6 < size; i6++) {
                com.android.server.appop.AppOpsService.NotedCallback valueAt = arraySet.valueAt(i6);
                try {
                    try {
                        if (!shouldIgnoreCallback(i, valueAt.mCallingPid, valueAt.mCallingUid)) {
                            valueAt.mCallback.opNoted(i, i2, str, str2, i3, i4, i5);
                        }
                    } catch (android.os.RemoteException e) {
                    }
                } catch (android.os.RemoteException e2) {
                }
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public int permissionToOpCode(java.lang.String str) {
        if (str == null) {
            return -1;
        }
        return android.app.AppOpsManager.permissionToOpCode(str);
    }

    public boolean shouldCollectNotes(int i) {
        com.android.internal.util.Preconditions.checkArgumentInRange(i, 0, 147, "opCode");
        if (android.app.AppOpsManager.shouldForceCollectNoteForOp(i)) {
            return true;
        }
        java.lang.String opToPermission = android.app.AppOpsManager.opToPermission(i);
        if (opToPermission == null) {
            return false;
        }
        try {
            android.content.pm.PermissionInfo permissionInfo = this.mContext.getPackageManager().getPermissionInfo(opToPermission, 0);
            return permissionInfo.getProtection() == 1 || (permissionInfo.getProtectionFlags() & 64) != 0;
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    private void verifyIncomingProxyUid(@android.annotation.NonNull android.content.AttributionSource attributionSource) {
        if (attributionSource.getUid() == android.os.Binder.getCallingUid() || android.os.Binder.getCallingPid() == android.os.Process.myPid() || attributionSource.isTrusted(this.mContext)) {
            return;
        }
        this.mContext.enforcePermission("android.permission.UPDATE_APP_OPS_STATS", android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), null);
    }

    private void verifyIncomingUid(int i) {
        if (i == android.os.Binder.getCallingUid() || android.os.Binder.getCallingPid() == android.os.Process.myPid()) {
            return;
        }
        this.mContext.enforcePermission("android.permission.UPDATE_APP_OPS_STATS", android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), null);
    }

    private boolean shouldIgnoreCallback(int i, int i2, int i3) {
        return android.app.AppOpsManager.opRestrictsRead(i) && this.mContext.checkPermission("android.permission.MANAGE_APPOPS", i2, i3) != 0;
    }

    private boolean isValidVirtualDeviceId(int i) {
        if (i == 0 || this.mVirtualDeviceManagerInternal == null) {
            return true;
        }
        if (this.mVirtualDeviceManagerInternal.isValidVirtualDeviceId(i)) {
            this.mKnownDeviceIds.put(i, this.mVirtualDeviceManagerInternal.getPersistentIdForDevice(i));
            return true;
        }
        return false;
    }

    private void verifyIncomingOp(int i) {
        if (i >= 0 && i < 148) {
            if (android.app.AppOpsManager.opRestrictsRead(i) && this.mContext.checkPermission("android.permission.MANAGE_APPOPS", android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid()) != 0 && this.mContext.checkPermission("android.permission.GET_APP_OPS_STATS", android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid()) != 0 && this.mContext.checkPermission("android.permission.MANAGE_APP_OPS_MODES", android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid()) != 0) {
                throw new java.lang.SecurityException("verifyIncomingOp: uid " + android.os.Binder.getCallingUid() + " does not have any of {MANAGE_APPOPS, GET_APP_OPS_STATS, MANAGE_APP_OPS_MODES}");
            }
            return;
        }
        throw new java.lang.IllegalArgumentException("Bad operation #" + i);
    }

    private boolean isIncomingPackageValid(@android.annotation.Nullable java.lang.String str, int i) {
        int callingUid = android.os.Binder.getCallingUid();
        if (str == null || isSpecialPackage(callingUid, str)) {
            return true;
        }
        if (!isPackageExisted(str)) {
            return false;
        }
        if (!getPackageManagerInternal().filterAppAccess(str, callingUid, i)) {
            return true;
        }
        android.util.Slog.w(TAG, str + " not found from " + callingUid);
        return false;
    }

    private boolean isSpecialPackage(int i, @android.annotation.Nullable java.lang.String str) {
        return i == 1000 || resolveUid(android.app.AppOpsManager.resolvePackageName(i, str)) != -1;
    }

    private boolean isCallerAndAttributionTrusted(@android.annotation.NonNull android.content.AttributionSource attributionSource) {
        return (attributionSource.getUid() != android.os.Binder.getCallingUid() && attributionSource.isTrusted(this.mContext)) || this.mContext.checkPermission("android.permission.UPDATE_APP_OPS_STATS", android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), null) == 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @android.annotation.Nullable
    public com.android.server.appop.AppOpsService.UidState getUidStateLocked(int i, boolean z) {
        com.android.server.appop.AppOpsService.UidState uidState = this.mUidStates.get(i);
        if (uidState == null) {
            if (!z) {
                return null;
            }
            com.android.server.appop.AppOpsService.UidState uidState2 = new com.android.server.appop.AppOpsService.UidState(i);
            this.mUidStates.put(i, uidState2);
            return uidState2;
        }
        return uidState;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void createSandboxUidStateIfNotExistsForAppLocked(int i, android.util.SparseBooleanArray sparseBooleanArray) {
        if (android.os.UserHandle.getAppId(i) < 10000) {
            return;
        }
        int sdkSandboxUid = android.os.Process.toSdkSandboxUid(i);
        if (sparseBooleanArray != null) {
            sparseBooleanArray.put(sdkSandboxUid, true);
        }
        getUidStateLocked(sdkSandboxUid, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateAppWidgetVisibility(android.util.SparseArray<java.lang.String> sparseArray, boolean z) {
        synchronized (this) {
            getUidStateTracker().updateAppWidgetVisibility(sparseArray, z);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @android.annotation.NonNull
    public android.content.pm.PackageManagerInternal getPackageManagerInternal() {
        if (this.mPackageManagerInternal == null) {
            this.mPackageManagerInternal = (android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class);
        }
        if (this.mPackageManagerInternal == null) {
            throw new java.lang.IllegalStateException("PackageManagerInternal not loaded");
        }
        return this.mPackageManagerInternal;
    }

    @android.annotation.NonNull
    private com.android.server.pm.PackageManagerLocal getPackageManagerLocal() {
        if (this.mPackageManagerLocal == null) {
            this.mPackageManagerLocal = (com.android.server.pm.PackageManagerLocal) com.android.server.LocalManagerRegistry.getManager(com.android.server.pm.PackageManagerLocal.class);
        }
        if (this.mPackageManagerLocal == null) {
            throw new java.lang.IllegalStateException("PackageManagerLocal not loaded");
        }
        return this.mPackageManagerLocal;
    }

    @android.annotation.NonNull
    private com.android.server.pm.UserManagerInternal getUserManagerInternal() {
        if (this.mUserManagerInternal == null) {
            this.mUserManagerInternal = (com.android.server.pm.UserManagerInternal) com.android.server.LocalServices.getService(com.android.server.pm.UserManagerInternal.class);
        }
        if (this.mUserManagerInternal == null) {
            throw new java.lang.IllegalStateException("UserManagerInternal not loaded");
        }
        return this.mUserManagerInternal;
    }

    private android.app.AppOpsManager.RestrictionBypass getBypassforPackage(@android.annotation.NonNull com.android.server.pm.pkg.PackageState packageState) {
        return new android.app.AppOpsManager.RestrictionBypass(packageState.getAppId() == 1000, packageState.isPrivileged(), this.mContext.checkPermission("android.permission.EXEMPT_FROM_AUDIO_RECORD_RESTRICTIONS", -1, packageState.getAppId()) == 0);
    }

    @android.annotation.NonNull
    private com.android.server.appop.AppOpsService.PackageVerificationResult verifyAndGetBypass(int i, java.lang.String str, @android.annotation.Nullable java.lang.String str2) {
        return verifyAndGetBypass(i, str, str2, null);
    }

    @android.annotation.NonNull
    private com.android.server.appop.AppOpsService.PackageVerificationResult verifyAndGetBypass(int i, java.lang.String str, @android.annotation.Nullable java.lang.String str2, @android.annotation.Nullable java.lang.String str3) {
        return verifyAndGetBypass(i, str, str2, str3, false);
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0045 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:81:0x01cd  */
    /* JADX WARN: Removed duplicated region for block: B:85:0x021e  */
    @android.annotation.NonNull
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private com.android.server.appop.AppOpsService.PackageVerificationResult verifyAndGetBypass(int i, java.lang.String str, @android.annotation.Nullable java.lang.String str2, @android.annotation.Nullable java.lang.String str3, boolean z) {
        int i2;
        int i3;
        int resolveUid;
        int i4;
        boolean z2;
        android.app.AppOpsManager.RestrictionBypass restrictionBypass;
        java.lang.String str4;
        boolean z3;
        boolean z4;
        com.android.server.appop.AppOpsService.Ops ops;
        if (i == 0) {
            return new com.android.server.appop.AppOpsService.PackageVerificationResult(null, true);
        }
        if (android.os.Process.isSdkSandboxUid(i)) {
            try {
                android.content.pm.PackageManager packageManager = this.mContext.getPackageManager();
                java.lang.String sdkSandboxPackageName = packageManager.getSdkSandboxPackageName();
                if (!java.util.Objects.equals(str, sdkSandboxPackageName)) {
                    i2 = i;
                } else {
                    i2 = packageManager.getPackageUidAsUser(sdkSandboxPackageName, android.content.pm.PackageManager.PackageInfoFlags.of(0L), android.os.UserHandle.getUserId(i));
                }
                i3 = i2;
            } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            synchronized (this) {
                try {
                    com.android.server.appop.AppOpsService.UidState uidState = this.mUidStates.get(i3);
                    if (uidState != null && !uidState.pkgOps.isEmpty() && (ops = uidState.pkgOps.get(str)) != null && ((str2 == null || ops.knownAttributionTags.contains(str2)) && ops.bypass != null)) {
                        return new com.android.server.appop.AppOpsService.PackageVerificationResult(ops.bypass, ops.validAttributionTags.contains(str2));
                    }
                    int callingUid = android.os.Binder.getCallingUid();
                    if (java.util.Objects.equals(str, com.android.server.vibrator.VibratorManagerService.VibratorManagerShellCommand.SHELL_PACKAGE_NAME)) {
                        resolveUid = 2000;
                    } else {
                        resolveUid = resolveUid(str);
                    }
                    if (resolveUid != -1) {
                        if (resolveUid != android.os.UserHandle.getAppId(i3)) {
                            if (!z) {
                                android.util.Slog.e(TAG, "Bad call made by uid " + callingUid + ". Package \"" + str + "\" does not belong to uid " + i3 + ".");
                            }
                            throw new java.lang.SecurityException("Specified package \"" + str + "\" under uid " + android.os.UserHandle.getAppId(i3) + " but it is not");
                        }
                        return new com.android.server.appop.AppOpsService.PackageVerificationResult(android.app.AppOpsManager.RestrictionBypass.UNRESTRICTED, true);
                    }
                    int userId = android.os.UserHandle.getUserId(i3);
                    long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                    try {
                        android.content.pm.PackageManagerInternal packageManagerInternal = (android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class);
                        com.android.server.pm.pkg.PackageStateInternal packageStateInternal = packageManagerInternal.getPackageStateInternal(str);
                        com.android.server.pm.pkg.AndroidPackage androidPackage = packageStateInternal == null ? null : packageStateInternal.getAndroidPackage();
                        if (androidPackage == null) {
                            i4 = resolveUid;
                            z2 = false;
                            restrictionBypass = null;
                        } else {
                            z2 = isAttributionInPackage(androidPackage, str2);
                            i4 = android.os.UserHandle.getUid(userId, packageStateInternal.getAppId());
                            restrictionBypass = getBypassforPackage(packageStateInternal);
                        }
                        if (!z2) {
                            boolean isAttributionInPackage = isAttributionInPackage(str3 != null ? packageManagerInternal.getPackage(str3) : null, str2);
                            if (androidPackage != null && isAttributionInPackage) {
                                str4 = "attributionTag " + str2 + " declared in manifest of the proxy package " + str3 + ", this is not advised";
                            } else if (androidPackage != null) {
                                str4 = "attributionTag " + str2 + " not declared in manifest of " + str;
                            } else {
                                str4 = "package " + str + " not found, can't check for attributionTag " + str2;
                            }
                            try {
                                z3 = isAttributionInPackage;
                                try {
                                    try {
                                        if (this.mPlatformCompat.isChangeEnabledByPackageName(151105954L, str, userId)) {
                                            if (this.mPlatformCompat.isChangeEnabledByUid(151105954L, callingUid)) {
                                                z4 = z3;
                                                android.util.Slog.e(TAG, str4);
                                                z2 = z4;
                                            }
                                        }
                                        android.util.Slog.e(TAG, str4);
                                        z2 = z4;
                                    } catch (android.os.RemoteException e2) {
                                        z2 = z4;
                                    }
                                    z4 = true;
                                } catch (android.os.RemoteException e3) {
                                    z2 = z3;
                                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                                    if (i4 == i3) {
                                    }
                                }
                            } catch (android.os.RemoteException e4) {
                                z3 = isAttributionInPackage;
                            }
                        }
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        if (i4 == i3) {
                            if (!z) {
                                android.util.Slog.e(TAG, "Bad call made by uid " + callingUid + ". Package \"" + str + "\" does not belong to uid " + i3 + ".");
                            }
                            throw new java.lang.SecurityException("Specified package \"" + str + "\" under uid " + i3 + " but it is not");
                        }
                        return new com.android.server.appop.AppOpsService.PackageVerificationResult(restrictionBypass, z2);
                    } catch (java.lang.Throwable th) {
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        throw th;
                    }
                } finally {
                }
            }
        }
        i3 = i;
        synchronized (this) {
        }
    }

    private boolean isAttributionInPackage(@android.annotation.Nullable com.android.server.pm.pkg.AndroidPackage androidPackage, @android.annotation.Nullable java.lang.String str) {
        if (androidPackage == null) {
            return false;
        }
        if (str == null) {
            return true;
        }
        if (androidPackage.getAttributions() != null) {
            int size = androidPackage.getAttributions().size();
            for (int i = 0; i < size; i++) {
                if (((com.android.internal.pm.pkg.component.ParsedAttribution) androidPackage.getAttributions().get(i)).getTag().equals(str)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isAttributionTagDefined(@android.annotation.Nullable java.lang.String str, @android.annotation.Nullable java.lang.String str2, @android.annotation.Nullable java.lang.String str3) {
        com.android.server.pm.pkg.AndroidPackage androidPackage;
        if (str == null) {
            return false;
        }
        if (str3 == null) {
            return true;
        }
        android.content.pm.PackageManagerInternal packageManagerInternal = (android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class);
        if (str2 != null && (androidPackage = packageManagerInternal.getPackage(str2)) != null && isAttributionInPackage(androidPackage, str3)) {
            return true;
        }
        return isAttributionInPackage(packageManagerInternal.getPackage(str), str3);
    }

    private void logVerifyAndGetBypassFailure(int i, @android.annotation.NonNull java.lang.SecurityException securityException, @android.annotation.NonNull java.lang.String str) {
        if (android.os.Process.isIsolated(i)) {
            android.util.Slog.e(TAG, "Cannot " + str + ": isolated UID");
            return;
        }
        if (android.os.UserHandle.getAppId(i) < 10000) {
            android.util.Slog.e(TAG, "Cannot " + str + ": non-application UID " + i);
            return;
        }
        android.util.Slog.e(TAG, "Cannot " + str, securityException);
    }

    private com.android.server.appop.AppOpsService.Ops getOpsLocked(int i, java.lang.String str, @android.annotation.Nullable java.lang.String str2, boolean z, @android.annotation.Nullable android.app.AppOpsManager.RestrictionBypass restrictionBypass, boolean z2) {
        com.android.server.appop.AppOpsService.UidState uidStateLocked = getUidStateLocked(i, false);
        if (uidStateLocked == null) {
            return null;
        }
        com.android.server.appop.AppOpsService.Ops ops = uidStateLocked.pkgOps.get(str);
        if (ops == null) {
            if (!z2) {
                return null;
            }
            ops = new com.android.server.appop.AppOpsService.Ops(str, uidStateLocked);
            uidStateLocked.pkgOps.put(str, ops);
        }
        if (z2) {
            if (restrictionBypass != null) {
                ops.bypass = restrictionBypass;
            }
            if (str2 != null) {
                ops.knownAttributionTags.add(str2);
                if (z) {
                    ops.validAttributionTags.add(str2);
                } else {
                    ops.validAttributionTags.remove(str2);
                }
            }
        }
        return ops;
    }

    private void scheduleWriteLocked() {
        if (!this.mWriteScheduled) {
            this.mWriteScheduled = true;
            this.mHandler.postDelayed(this.mWriteRunner, 1800000L);
        }
    }

    private void scheduleFastWriteLocked() {
        if (!this.mFastWriteScheduled) {
            this.mWriteScheduled = true;
            this.mFastWriteScheduled = true;
            this.mHandler.removeCallbacks(this.mWriteRunner);
            this.mHandler.postDelayed(this.mWriteRunner, com.android.server.job.controllers.JobStatus.DEFAULT_TRIGGER_UPDATE_DELAY);
        }
    }

    @android.annotation.Nullable
    private com.android.server.appop.AppOpsService.Op getOpLocked(int i, int i2, @android.annotation.NonNull java.lang.String str, @android.annotation.Nullable java.lang.String str2, boolean z, @android.annotation.Nullable android.app.AppOpsManager.RestrictionBypass restrictionBypass, boolean z2) {
        com.android.server.appop.AppOpsService.Ops opsLocked = getOpsLocked(i2, str, str2, z, restrictionBypass, z2);
        if (opsLocked == null) {
            return null;
        }
        return getOpLocked(opsLocked, i, i2, z2);
    }

    /*  JADX ERROR: JadxRuntimeException in pass: ConstructorVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Can't remove SSA var: r6v0 ??, still in use, count: 1, list:
          (r6v0 ?? I:java.lang.Object) from 0x0019: INVOKE (r8v0 ?? I:android.util.SparseArray), (r9v0 ?? I:int), (r6v0 ?? I:java.lang.Object) VIRTUAL call: android.util.SparseArray.put(int, java.lang.Object):void A[MD:(int, E):void (c)] (LINE:4698)
        	at jadx.core.utils.InsnRemover.removeSsaVar(InsnRemover.java:162)
        	at jadx.core.utils.InsnRemover.unbindResult(InsnRemover.java:127)
        	at jadx.core.utils.InsnRemover.lambda$unbindInsns$1(InsnRemover.java:99)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1597)
        	at jadx.core.utils.InsnRemover.unbindInsns(InsnRemover.java:98)
        	at jadx.core.utils.InsnRemover.perform(InsnRemover.java:73)
        	at jadx.core.dex.visitors.ConstructorVisitor.replaceInvoke(ConstructorVisitor.java:59)
        	at jadx.core.dex.visitors.ConstructorVisitor.visit(ConstructorVisitor.java:42)
        */
    private com.android.server.appop.AppOpsService.Op getOpLocked(
    /*  JADX ERROR: JadxRuntimeException in pass: ConstructorVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Can't remove SSA var: r6v0 ??, still in use, count: 1, list:
          (r6v0 ?? I:java.lang.Object) from 0x0019: INVOKE (r8v0 ?? I:android.util.SparseArray), (r9v0 ?? I:int), (r6v0 ?? I:java.lang.Object) VIRTUAL call: android.util.SparseArray.put(int, java.lang.Object):void A[MD:(int, E):void (c)] (LINE:4698)
        	at jadx.core.utils.InsnRemover.removeSsaVar(InsnRemover.java:162)
        	at jadx.core.utils.InsnRemover.unbindResult(InsnRemover.java:127)
        	at jadx.core.utils.InsnRemover.lambda$unbindInsns$1(InsnRemover.java:99)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1597)
        	at jadx.core.utils.InsnRemover.unbindInsns(InsnRemover.java:98)
        	at jadx.core.utils.InsnRemover.perform(InsnRemover.java:73)
        	at jadx.core.dex.visitors.ConstructorVisitor.replaceInvoke(ConstructorVisitor.java:59)
        */
    /*  JADX ERROR: Method generation error
        jadx.core.utils.exceptions.JadxRuntimeException: Code variable not set in r8v0 ??
        	at jadx.core.dex.instructions.args.SSAVar.getCodeVar(SSAVar.java:238)
        	at jadx.core.codegen.MethodGen.addMethodArguments(MethodGen.java:223)
        	at jadx.core.codegen.MethodGen.addDefinition(MethodGen.java:168)
        	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:401)
        	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:335)
        	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$3(ClassGen.java:301)
        	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:184)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1597)
        	at java.base/java.util.stream.SortedOps$RefSortingSink.end(SortedOps.java:395)
        	at java.base/java.util.stream.Sink$ChainedReference.end(Sink.java:261)
        */

    private boolean isOpRestrictedDueToSuspend(int i, java.lang.String str, int i2) {
        if (!com.android.internal.util.ArrayUtils.contains(OPS_RESTRICTED_ON_SUSPEND, i)) {
            return false;
        }
        return ((android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class)).isPackageSuspended(str, android.os.UserHandle.getUserId(i2));
    }

    private boolean isAutomotive() {
        return this.mContext.getPackageManager().hasSystemFeature("android.hardware.type.automotive");
    }

    private boolean isOpRestrictedLocked(int i, int i2, java.lang.String str, java.lang.String str2, int i3, @android.annotation.Nullable android.app.AppOpsManager.RestrictionBypass restrictionBypass, boolean z) {
        if (i3 != 0) {
            return false;
        }
        int size = this.mOpGlobalRestrictions.size();
        for (int i4 = 0; i4 < size; i4++) {
            if (this.mOpGlobalRestrictions.valueAt(i4).hasRestriction(i2)) {
                return true;
            }
        }
        if (i2 == 26 && isAutomotive()) {
            if (com.android.internal.camera.flags.Flags.cameraPrivacyAllowlist() && this.mSensorPrivacyManager.isCameraPrivacyEnabled(str)) {
                return true;
            }
        }
        int userId = android.os.UserHandle.getUserId(i);
        int size2 = this.mOpUserRestrictions.size();
        for (int i5 = 0; i5 < size2; i5++) {
            if (this.mOpUserRestrictions.valueAt(i5).hasRestriction(i2, str, str2, userId, z)) {
                android.app.AppOpsManager.RestrictionBypass opAllowSystemBypassRestriction = android.app.AppOpsManager.opAllowSystemBypassRestriction(i2);
                if (opAllowSystemBypassRestriction != null) {
                    synchronized (this) {
                        try {
                            if (opAllowSystemBypassRestriction.isSystemUid && restrictionBypass != null && restrictionBypass.isSystemUid) {
                                return false;
                            }
                            if (opAllowSystemBypassRestriction.isPrivileged && restrictionBypass != null && restrictionBypass.isPrivileged) {
                                return false;
                            }
                            if (opAllowSystemBypassRestriction.isRecordAudioRestrictionExcept && restrictionBypass != null && restrictionBypass.isRecordAudioRestrictionExcept) {
                                return false;
                            }
                        } finally {
                        }
                    }
                }
                return true;
            }
        }
        return false;
    }

    private void readRecentAccesses() {
        if (!this.mRecentAccessesFile.exists()) {
            readRecentAccesses(this.mStorageFile);
        } else {
            readRecentAccesses(this.mRecentAccessesFile);
        }
    }

    /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Can't find top splitter block for handler:B:29:0x009a
        	at jadx.core.utils.BlockUtils.getTopSplitterForHandler(BlockUtils.java:1179)
        	at jadx.core.dex.visitors.regions.maker.ExcHandlersRegionMaker.collectHandlerRegions(ExcHandlersRegionMaker.java:53)
        	at jadx.core.dex.visitors.regions.maker.ExcHandlersRegionMaker.process(ExcHandlersRegionMaker.java:38)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:27)
        */
    private void readRecentAccesses(android.util.AtomicFile r8) {
        /*
            Method dump skipped, instructions count: 458
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.appop.AppOpsService.readRecentAccesses(android.util.AtomicFile):void");
    }

    private void readPackage(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) throws java.lang.NumberFormatException, org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        java.lang.String attributeValue = typedXmlPullParser.getAttributeValue((java.lang.String) null, "n");
        int depth = typedXmlPullParser.getDepth();
        while (true) {
            int next = typedXmlPullParser.next();
            if (next != 1) {
                if (next == 3 && typedXmlPullParser.getDepth() <= depth) {
                    return;
                }
                if (next != 3 && next != 4) {
                    if (typedXmlPullParser.getName().equals(com.android.server.net.watchlist.WatchlistLoggingHandler.WatchlistEventKeys.UID)) {
                        readUid(typedXmlPullParser, attributeValue);
                    } else {
                        android.util.Slog.w(TAG, "Unknown element under <pkg>: " + typedXmlPullParser.getName());
                        com.android.internal.util.XmlUtils.skipCurrentTag(typedXmlPullParser);
                    }
                }
            } else {
                return;
            }
        }
    }

    private void readUid(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, java.lang.String str) throws java.lang.NumberFormatException, org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        com.android.server.appop.AppOpsService.UidState uidStateLocked = getUidStateLocked(typedXmlPullParser.getAttributeInt((java.lang.String) null, "n"), true);
        int depth = typedXmlPullParser.getDepth();
        while (true) {
            int next = typedXmlPullParser.next();
            if (next != 1) {
                if (next == 3 && typedXmlPullParser.getDepth() <= depth) {
                    return;
                }
                if (next != 3 && next != 4) {
                    if (typedXmlPullParser.getName().equals("op")) {
                        readOp(typedXmlPullParser, uidStateLocked, str);
                    } else {
                        android.util.Slog.w(TAG, "Unknown element under <pkg>: " + typedXmlPullParser.getName());
                        com.android.internal.util.XmlUtils.skipCurrentTag(typedXmlPullParser);
                    }
                }
            } else {
                return;
            }
        }
    }

    private void readAttributionOp(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, @android.annotation.NonNull com.android.server.appop.AppOpsService.Op op, @android.annotation.Nullable java.lang.String str) throws java.lang.NumberFormatException, java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        com.android.server.appop.AttributedOp attributedOp;
        long j;
        com.android.server.appop.AttributedOp orCreateAttribution = op.getOrCreateAttribution(op, str, "default:0");
        long attributeLong = typedXmlPullParser.getAttributeLong((java.lang.String) null, "n");
        int extractUidStateFromKey = android.app.AppOpsManager.extractUidStateFromKey(attributeLong);
        int extractFlagsFromKey = android.app.AppOpsManager.extractFlagsFromKey(attributeLong);
        long attributeLong2 = typedXmlPullParser.getAttributeLong((java.lang.String) null, "t", 0L);
        long attributeLong3 = typedXmlPullParser.getAttributeLong((java.lang.String) null, com.android.server.wm.ActivityTaskManagerService.DUMP_RECENTS_SHORT_CMD, 0L);
        long attributeLong4 = typedXmlPullParser.getAttributeLong((java.lang.String) null, "d", -1L);
        java.lang.String readStringAttribute = com.android.internal.util.XmlUtils.readStringAttribute(typedXmlPullParser, "pp");
        int attributeInt = typedXmlPullParser.getAttributeInt((java.lang.String) null, "pu", -1);
        java.lang.String readStringAttribute2 = com.android.internal.util.XmlUtils.readStringAttribute(typedXmlPullParser, "pc");
        if (attributeLong2 <= 0) {
            attributedOp = orCreateAttribution;
            j = attributeLong3;
        } else {
            attributedOp = orCreateAttribution;
            j = attributeLong3;
            orCreateAttribution.accessed(attributeLong2, attributeLong4, attributeInt, readStringAttribute, readStringAttribute2, extractUidStateFromKey, extractFlagsFromKey);
        }
        if (j > 0) {
            attributedOp.rejected(j, extractUidStateFromKey, extractFlagsFromKey);
        }
    }

    private void readOp(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, @android.annotation.NonNull com.android.server.appop.AppOpsService.UidState uidState, @android.annotation.NonNull java.lang.String str) throws java.lang.NumberFormatException, org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        com.android.server.appop.AppOpsService.Op op = new com.android.server.appop.AppOpsService.Op(uidState, str, typedXmlPullParser.getAttributeInt((java.lang.String) null, "n"), uidState.uid);
        int depth = typedXmlPullParser.getDepth();
        while (true) {
            int next = typedXmlPullParser.next();
            if (next == 1 || (next == 3 && typedXmlPullParser.getDepth() <= depth)) {
                break;
            }
            if (next != 3 && next != 4) {
                if (typedXmlPullParser.getName().equals("st")) {
                    readAttributionOp(typedXmlPullParser, op, com.android.internal.util.XmlUtils.readStringAttribute(typedXmlPullParser, "id"));
                } else {
                    android.util.Slog.w(TAG, "Unknown element under <op>: " + typedXmlPullParser.getName());
                    com.android.internal.util.XmlUtils.skipCurrentTag(typedXmlPullParser);
                }
            }
        }
        com.android.server.appop.AppOpsService.Ops ops = uidState.pkgOps.get(str);
        if (ops == null) {
            ops = new com.android.server.appop.AppOpsService.Ops(str, uidState);
            uidState.pkgOps.put(str, ops);
        }
        ops.put(op.op, op);
    }

    @com.android.internal.annotations.VisibleForTesting
    void writeRecentAccesses() {
        android.util.AtomicFile atomicFile;
        java.io.FileOutputStream fileOutputStream;
        java.io.FileOutputStream fileOutputStream2;
        java.lang.String packageName;
        int uid;
        android.app.AppOpsManager.AttributedOpEntry attributedOpEntry;
        java.lang.String str;
        android.util.ArraySet arraySet;
        java.lang.String str2;
        com.android.server.appop.AppOpsService appOpsService = this;
        android.util.AtomicFile atomicFile2 = appOpsService.mRecentAccessesFile;
        synchronized (atomicFile2) {
            try {
                try {
                    try {
                        java.io.FileOutputStream startWrite = appOpsService.mRecentAccessesFile.startWrite();
                        java.lang.String str3 = null;
                        java.util.List<android.app.AppOpsManager.PackageOps> packagesForOps = appOpsService.getPackagesForOps(null);
                        try {
                            com.android.modules.utils.TypedXmlSerializer resolveSerializer = android.util.Xml.resolveSerializer(startWrite);
                            resolveSerializer.startDocument((java.lang.String) null, true);
                            resolveSerializer.startTag((java.lang.String) null, "app-ops");
                            resolveSerializer.attributeInt((java.lang.String) null, "v", 1);
                            if (packagesForOps != null) {
                                java.lang.String str4 = null;
                                int i = 0;
                                while (i < packagesForOps.size()) {
                                    try {
                                        android.app.AppOpsManager.PackageOps packageOps = packagesForOps.get(i);
                                        if (!java.util.Objects.equals(packageOps.getPackageName(), str4)) {
                                            if (str4 != null) {
                                                resolveSerializer.endTag(str3, "pkg");
                                            }
                                            str4 = packageOps.getPackageName();
                                            if (str4 != null) {
                                                resolveSerializer.startTag(str3, "pkg");
                                                resolveSerializer.attribute(str3, "n", str4);
                                            }
                                        }
                                        resolveSerializer.startTag(str3, com.android.server.net.watchlist.WatchlistLoggingHandler.WatchlistEventKeys.UID);
                                        resolveSerializer.attributeInt(str3, "n", packageOps.getUid());
                                        java.util.List ops = packageOps.getOps();
                                        int i2 = 0;
                                        while (i2 < ops.size()) {
                                            android.app.AppOpsManager.OpEntry opEntry = (android.app.AppOpsManager.OpEntry) ops.get(i2);
                                            resolveSerializer.startTag(str3, "op");
                                            resolveSerializer.attributeInt(str3, "n", opEntry.getOp());
                                            if (opEntry.getMode() != android.app.AppOpsManager.opToDefaultMode(opEntry.getOp())) {
                                                resolveSerializer.attributeInt(str3, "m", opEntry.getMode());
                                            }
                                            java.util.Iterator it = opEntry.getAttributedOpEntries().keySet().iterator();
                                            while (it.hasNext()) {
                                                java.lang.String str5 = (java.lang.String) it.next();
                                                android.app.AppOpsManager.AttributedOpEntry attributedOpEntry2 = (android.app.AppOpsManager.AttributedOpEntry) opEntry.getAttributedOpEntries().get(str5);
                                                android.util.ArraySet collectKeys = attributedOpEntry2.collectKeys();
                                                int size = collectKeys.size();
                                                int i3 = 0;
                                                while (i3 < size) {
                                                    java.lang.String str6 = str4;
                                                    java.util.List list = ops;
                                                    long longValue = ((java.lang.Long) collectKeys.valueAt(i3)).longValue();
                                                    java.util.List<android.app.AppOpsManager.PackageOps> list2 = packagesForOps;
                                                    int extractUidStateFromKey = android.app.AppOpsManager.extractUidStateFromKey(longValue);
                                                    int i4 = size;
                                                    int extractFlagsFromKey = android.app.AppOpsManager.extractFlagsFromKey(longValue);
                                                    android.app.AppOpsManager.OpEntry opEntry2 = opEntry;
                                                    java.util.Iterator it2 = it;
                                                    long lastAccessTime = attributedOpEntry2.getLastAccessTime(extractUidStateFromKey, extractUidStateFromKey, extractFlagsFromKey);
                                                    atomicFile = atomicFile2;
                                                    try {
                                                        long lastRejectTime = attributedOpEntry2.getLastRejectTime(extractUidStateFromKey, extractUidStateFromKey, extractFlagsFromKey);
                                                        int i5 = i;
                                                        long lastDuration = attributedOpEntry2.getLastDuration(extractUidStateFromKey, extractUidStateFromKey, extractFlagsFromKey);
                                                        android.app.AppOpsManager.OpEventProxyInfo lastProxyInfo = attributedOpEntry2.getLastProxyInfo(extractUidStateFromKey, extractUidStateFromKey, extractFlagsFromKey);
                                                        if (lastAccessTime > 0 || lastRejectTime > 0 || lastDuration > 0 || lastProxyInfo != null) {
                                                            if (lastProxyInfo != null) {
                                                                try {
                                                                    packageName = lastProxyInfo.getPackageName();
                                                                    java.lang.String attributionTag = lastProxyInfo.getAttributionTag();
                                                                    uid = lastProxyInfo.getUid();
                                                                    attributedOpEntry = attributedOpEntry2;
                                                                    str = attributionTag;
                                                                } catch (java.io.IOException e) {
                                                                    e = e;
                                                                    appOpsService = this;
                                                                    fileOutputStream = startWrite;
                                                                    android.util.Slog.w(TAG, "Failed to write state, restoring backup.", e);
                                                                    appOpsService.mRecentAccessesFile.failWrite(fileOutputStream);
                                                                    appOpsService.mHistoricalRegistry.writeAndClearDiscreteHistory();
                                                                }
                                                            } else {
                                                                uid = -1;
                                                                attributedOpEntry = attributedOpEntry2;
                                                                packageName = null;
                                                                str = null;
                                                            }
                                                            arraySet = collectKeys;
                                                            fileOutputStream2 = startWrite;
                                                            try {
                                                                resolveSerializer.startTag((java.lang.String) null, "st");
                                                                if (str5 != null) {
                                                                    resolveSerializer.attribute((java.lang.String) null, "id", str5);
                                                                }
                                                                str2 = str5;
                                                                java.lang.String str7 = str;
                                                                resolveSerializer.attributeLong((java.lang.String) null, "n", longValue);
                                                                if (lastAccessTime > 0) {
                                                                    resolveSerializer.attributeLong((java.lang.String) null, "t", lastAccessTime);
                                                                }
                                                                if (lastRejectTime > 0) {
                                                                    resolveSerializer.attributeLong((java.lang.String) null, com.android.server.wm.ActivityTaskManagerService.DUMP_RECENTS_SHORT_CMD, lastRejectTime);
                                                                }
                                                                if (lastDuration > 0) {
                                                                    resolveSerializer.attributeLong((java.lang.String) null, "d", lastDuration);
                                                                }
                                                                if (packageName != null) {
                                                                    resolveSerializer.attribute((java.lang.String) null, "pp", packageName);
                                                                }
                                                                if (str7 != null) {
                                                                    resolveSerializer.attribute((java.lang.String) null, "pc", str7);
                                                                }
                                                                if (uid >= 0) {
                                                                    resolveSerializer.attributeInt((java.lang.String) null, "pu", uid);
                                                                }
                                                                resolveSerializer.endTag((java.lang.String) null, "st");
                                                            } catch (java.io.IOException e2) {
                                                                e = e2;
                                                                appOpsService = this;
                                                                fileOutputStream = fileOutputStream2;
                                                                android.util.Slog.w(TAG, "Failed to write state, restoring backup.", e);
                                                                appOpsService.mRecentAccessesFile.failWrite(fileOutputStream);
                                                                appOpsService.mHistoricalRegistry.writeAndClearDiscreteHistory();
                                                            }
                                                        } else {
                                                            fileOutputStream2 = startWrite;
                                                            str2 = str5;
                                                            attributedOpEntry = attributedOpEntry2;
                                                            arraySet = collectKeys;
                                                        }
                                                        i3++;
                                                        packagesForOps = list2;
                                                        str4 = str6;
                                                        ops = list;
                                                        size = i4;
                                                        opEntry = opEntry2;
                                                        it = it2;
                                                        atomicFile2 = atomicFile;
                                                        i = i5;
                                                        str5 = str2;
                                                        attributedOpEntry2 = attributedOpEntry;
                                                        collectKeys = arraySet;
                                                        startWrite = fileOutputStream2;
                                                    } catch (java.io.IOException e3) {
                                                        e = e3;
                                                        fileOutputStream2 = startWrite;
                                                        appOpsService = this;
                                                        fileOutputStream = fileOutputStream2;
                                                        android.util.Slog.w(TAG, "Failed to write state, restoring backup.", e);
                                                        appOpsService.mRecentAccessesFile.failWrite(fileOutputStream);
                                                        appOpsService.mHistoricalRegistry.writeAndClearDiscreteHistory();
                                                    }
                                                }
                                            }
                                            resolveSerializer.endTag((java.lang.String) null, "op");
                                            i2++;
                                            packagesForOps = packagesForOps;
                                            str4 = str4;
                                            ops = ops;
                                            atomicFile2 = atomicFile2;
                                            i = i;
                                            startWrite = startWrite;
                                            str3 = null;
                                        }
                                        resolveSerializer.endTag((java.lang.String) null, com.android.server.net.watchlist.WatchlistLoggingHandler.WatchlistEventKeys.UID);
                                        i++;
                                        appOpsService = this;
                                        packagesForOps = packagesForOps;
                                        str4 = str4;
                                        atomicFile2 = atomicFile2;
                                        startWrite = startWrite;
                                        str3 = null;
                                    } catch (java.io.IOException e4) {
                                        e = e4;
                                        atomicFile = atomicFile2;
                                    }
                                }
                                atomicFile = atomicFile2;
                                fileOutputStream2 = startWrite;
                                if (str4 != null) {
                                    resolveSerializer.endTag((java.lang.String) null, "pkg");
                                }
                            } else {
                                atomicFile = atomicFile2;
                                fileOutputStream2 = startWrite;
                            }
                            resolveSerializer.endTag((java.lang.String) null, "app-ops");
                            resolveSerializer.endDocument();
                            appOpsService = this;
                        } catch (java.io.IOException e5) {
                            e = e5;
                            atomicFile = atomicFile2;
                        }
                        try {
                            fileOutputStream = fileOutputStream2;
                            try {
                                appOpsService.mRecentAccessesFile.finishWrite(fileOutputStream);
                            } catch (java.io.IOException e6) {
                                e = e6;
                                android.util.Slog.w(TAG, "Failed to write state, restoring backup.", e);
                                appOpsService.mRecentAccessesFile.failWrite(fileOutputStream);
                                appOpsService.mHistoricalRegistry.writeAndClearDiscreteHistory();
                            }
                        } catch (java.io.IOException e7) {
                            e = e7;
                            fileOutputStream = fileOutputStream2;
                            android.util.Slog.w(TAG, "Failed to write state, restoring backup.", e);
                            appOpsService.mRecentAccessesFile.failWrite(fileOutputStream);
                            appOpsService.mHistoricalRegistry.writeAndClearDiscreteHistory();
                        }
                        appOpsService.mHistoricalRegistry.writeAndClearDiscreteHistory();
                    } catch (java.io.IOException e8) {
                        android.util.Slog.w(TAG, "Failed to write state: " + e8);
                    }
                } catch (java.lang.Throwable th) {
                    th = th;
                    android.util.AtomicFile atomicFile3 = atomicFile2;
                    throw th;
                }
            } catch (java.lang.Throwable th2) {
                th = th2;
                throw th;
            }
        }
    }

    static class Shell extends android.os.ShellCommand {
        static final android.os.Binder sBinder = new android.os.Binder();
        java.lang.String attributionTag;
        final com.android.internal.app.IAppOpsService mInterface;
        final com.android.server.appop.AppOpsService mInternal;
        int mode;
        java.lang.String modeStr;
        int nonpackageUid;
        int op;
        java.lang.String opStr;
        java.lang.String packageName;
        int packageUid;
        boolean targetsUid;
        int userId = 0;
        android.os.IBinder mToken = android.app.AppOpsManager.getClientId();

        Shell(com.android.internal.app.IAppOpsService iAppOpsService, com.android.server.appop.AppOpsService appOpsService) {
            this.mInterface = iAppOpsService;
            this.mInternal = appOpsService;
        }

        public int onCommand(java.lang.String str) {
            return com.android.server.appop.AppOpsService.onShellCommand(this, str);
        }

        public void onHelp() {
            com.android.server.appop.AppOpsService.dumpCommandHelp(getOutPrintWriter());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static int strOpToOp(java.lang.String str, java.io.PrintWriter printWriter) {
            try {
                return android.app.AppOpsManager.strOpToOp(str);
            } catch (java.lang.IllegalArgumentException e) {
                try {
                    return java.lang.Integer.parseInt(str);
                } catch (java.lang.NumberFormatException e2) {
                    try {
                        return android.app.AppOpsManager.strDebugOpToOp(str);
                    } catch (java.lang.IllegalArgumentException e3) {
                        printWriter.println("Error: " + e3.getMessage());
                        return -1;
                    }
                }
            }
        }

        static int strModeToMode(java.lang.String str, java.io.PrintWriter printWriter) {
            for (int length = android.app.AppOpsManager.MODE_NAMES.length - 1; length >= 0; length--) {
                if (android.app.AppOpsManager.MODE_NAMES[length].equals(str)) {
                    return length;
                }
            }
            try {
                return java.lang.Integer.parseInt(str);
            } catch (java.lang.NumberFormatException e) {
                printWriter.println("Error: Mode " + str + " is not valid");
                return -1;
            }
        }

        int parseUserOpMode(int i, java.io.PrintWriter printWriter) throws android.os.RemoteException {
            this.userId = -2;
            this.opStr = null;
            this.modeStr = null;
            while (true) {
                java.lang.String nextArg = getNextArg();
                if (nextArg == null) {
                    break;
                }
                if ("--user".equals(nextArg)) {
                    this.userId = android.os.UserHandle.parseUserArg(getNextArgRequired());
                } else if (this.opStr == null) {
                    this.opStr = nextArg;
                } else if (this.modeStr == null) {
                    this.modeStr = nextArg;
                    break;
                }
            }
            if (this.opStr == null) {
                printWriter.println("Error: Operation not specified.");
                return -1;
            }
            this.op = strOpToOp(this.opStr, printWriter);
            if (this.op < 0) {
                return -1;
            }
            if (this.modeStr != null) {
                int strModeToMode = strModeToMode(this.modeStr, printWriter);
                this.mode = strModeToMode;
                return strModeToMode < 0 ? -1 : 0;
            }
            this.mode = i;
            return 0;
        }

        /* JADX WARN: Code restructure failed: missing block: B:60:0x00d3, code lost:
        
            if (r10 >= r9.packageName.length()) goto L84;
         */
        /* JADX WARN: Code restructure failed: missing block: B:63:0x00db, code lost:
        
            r2 = java.lang.Integer.parseInt(r9.packageName.substring(1, r10));
            r7 = r9.packageName.charAt(r10);
            r10 = r10 + 1;
            r3 = r10;
         */
        /* JADX WARN: Code restructure failed: missing block: B:65:0x00ed, code lost:
        
            if (r3 >= r9.packageName.length()) goto L123;
         */
        /* JADX WARN: Code restructure failed: missing block: B:67:0x00f5, code lost:
        
            if (r9.packageName.charAt(r3) < '0') goto L121;
         */
        /* JADX WARN: Code restructure failed: missing block: B:69:0x00fd, code lost:
        
            if (r9.packageName.charAt(r3) > '9') goto L122;
         */
        /* JADX WARN: Code restructure failed: missing block: B:70:0x00ff, code lost:
        
            r3 = r3 + 1;
         */
        /* JADX WARN: Code restructure failed: missing block: B:72:0x0104, code lost:
        
            if (r3 <= r10) goto L84;
         */
        /* JADX WARN: Code restructure failed: missing block: B:76:0x010c, code lost:
        
            r10 = java.lang.Integer.parseInt(r9.packageName.substring(r10, r3));
         */
        /* JADX WARN: Code restructure failed: missing block: B:77:0x0112, code lost:
        
            if (r7 != 'a') goto L79;
         */
        /* JADX WARN: Code restructure failed: missing block: B:78:0x0114, code lost:
        
            r9.nonpackageUid = android.os.UserHandle.getUid(r2, r10 + 10000);
         */
        /* JADX WARN: Code restructure failed: missing block: B:82:0x0121, code lost:
        
            if (r7 != 's') goto L97;
         */
        /* JADX WARN: Code restructure failed: missing block: B:83:0x0123, code lost:
        
            r9.nonpackageUid = android.os.UserHandle.getUid(r2, r10);
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        int parseUserPackageOp(boolean z, java.io.PrintWriter printWriter) throws android.os.RemoteException {
            this.userId = -2;
            this.packageName = null;
            this.opStr = null;
            while (true) {
                java.lang.String nextArg = getNextArg();
                if (nextArg == null) {
                    break;
                }
                if ("--user".equals(nextArg)) {
                    this.userId = android.os.UserHandle.parseUserArg(getNextArgRequired());
                } else if ("--uid".equals(nextArg)) {
                    this.targetsUid = true;
                } else if ("--attribution".equals(nextArg)) {
                    this.attributionTag = getNextArgRequired();
                } else if (this.packageName == null) {
                    this.packageName = nextArg;
                } else if (this.opStr == null) {
                    this.opStr = nextArg;
                    break;
                }
            }
            if (this.packageName == null) {
                printWriter.println("Error: Package name not specified.");
                return -1;
            }
            if (this.opStr == null && z) {
                printWriter.println("Error: Operation not specified.");
                return -1;
            }
            if (this.opStr != null) {
                this.op = strOpToOp(this.opStr, printWriter);
                if (this.op < 0) {
                    return -1;
                }
            } else {
                this.op = -1;
            }
            if (this.userId == -2) {
                this.userId = android.app.ActivityManager.getCurrentUser();
            }
            this.nonpackageUid = -1;
            try {
                this.nonpackageUid = java.lang.Integer.parseInt(this.packageName);
            } catch (java.lang.NumberFormatException e) {
            }
            if (this.nonpackageUid == -1 && this.packageName.length() > 1 && this.packageName.charAt(0) == 'u' && this.packageName.indexOf(46) < 0) {
                int i = 1;
                while (i < this.packageName.length() && this.packageName.charAt(i) >= '0' && this.packageName.charAt(i) <= '9') {
                    i++;
                }
            }
            if (this.nonpackageUid != -1) {
                this.packageName = null;
            } else {
                this.packageUid = com.android.server.appop.AppOpsService.resolveUid(this.packageName);
                if (this.packageUid < 0) {
                    this.packageUid = android.app.AppGlobals.getPackageManager().getPackageUid(this.packageName, 8192L, this.userId);
                }
                if (this.packageUid < 0) {
                    printWriter.println("Error: No UID for " + this.packageName + " in user " + this.userId);
                    return -1;
                }
            }
            return 0;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void onShellCommand(java.io.FileDescriptor fileDescriptor, java.io.FileDescriptor fileDescriptor2, java.io.FileDescriptor fileDescriptor3, java.lang.String[] strArr, android.os.ShellCallback shellCallback, android.os.ResultReceiver resultReceiver) {
        new com.android.server.appop.AppOpsService.Shell(this, this).exec(this, fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, shellCallback, resultReceiver);
    }

    static void dumpCommandHelp(java.io.PrintWriter printWriter) {
        printWriter.println("AppOps service (appops) commands:");
        printWriter.println("  help");
        printWriter.println("    Print this help text.");
        printWriter.println("  start [--user <USER_ID>] [--attribution <ATTRIBUTION_TAG>] <PACKAGE | UID> <OP> ");
        printWriter.println("    Starts a given operation for a particular application.");
        printWriter.println("  stop [--user <USER_ID>] [--attribution <ATTRIBUTION_TAG>] <PACKAGE | UID> <OP> ");
        printWriter.println("    Stops a given operation for a particular application.");
        printWriter.println("  set [--user <USER_ID>] <[--uid] PACKAGE | UID> <OP> <MODE>");
        printWriter.println("    Set the mode for a particular application and operation.");
        printWriter.println("  get [--user <USER_ID>] [--attribution <ATTRIBUTION_TAG>] <PACKAGE | UID> [<OP>]");
        printWriter.println("    Return the mode for a particular application and optional operation.");
        printWriter.println("  query-op [--user <USER_ID>] <OP> [<MODE>]");
        printWriter.println("    Print all packages that currently have the given op in the given mode.");
        printWriter.println("  reset [--user <USER_ID>] [<PACKAGE>]");
        printWriter.println("    Reset the given application or all applications to default modes.");
        printWriter.println("  write-settings");
        printWriter.println("    Immediately write pending changes to storage.");
        printWriter.println("  read-settings");
        printWriter.println("    Read the last written settings, replacing current state in RAM.");
        printWriter.println("  options:");
        printWriter.println("    <PACKAGE> an Android package name or its UID if prefixed by --uid");
        printWriter.println("    <OP>      an AppOps operation.");
        printWriter.println("    <MODE>    one of allow, ignore, deny, or default");
        printWriter.println("    <USER_ID> the user id under which the package is installed. If --user is");
        printWriter.println("              not specified, the current user is assumed.");
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Multi-variable type inference failed */
    static int onShellCommand(com.android.server.appop.AppOpsService.Shell shell, java.lang.String str) {
        char c;
        java.util.List list;
        byte b;
        if (str == null) {
            return shell.handleDefaultCommands(str);
        }
        java.io.PrintWriter outPrintWriter = shell.getOutPrintWriter();
        java.io.PrintWriter errPrintWriter = shell.getErrPrintWriter();
        try {
            int i = 0;
            switch (str.hashCode()) {
                case -1703718319:
                    if (str.equals("write-settings")) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                case -1166702330:
                    if (str.equals("query-op")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case 102230:
                    if (str.equals("get")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 113762:
                    if (str.equals("set")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 3540994:
                    if (str.equals("stop")) {
                        c = 7;
                        break;
                    }
                    c = 65535;
                    break;
                case 108404047:
                    if (str.equals("reset")) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                case 109757538:
                    if (str.equals("start")) {
                        c = 6;
                        break;
                    }
                    c = 65535;
                    break;
                case 2085703290:
                    if (str.equals("read-settings")) {
                        c = 5;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            java.lang.String str2 = null;
            switch (c) {
                case 0:
                    int parseUserPackageOp = shell.parseUserPackageOp(true, errPrintWriter);
                    if (parseUserPackageOp < 0) {
                        return parseUserPackageOp;
                    }
                    java.lang.String nextArg = shell.getNextArg();
                    if (nextArg == null) {
                        errPrintWriter.println("Error: Mode not specified.");
                        return -1;
                    }
                    int strModeToMode = com.android.server.appop.AppOpsService.Shell.strModeToMode(nextArg, errPrintWriter);
                    if (strModeToMode < 0) {
                        return -1;
                    }
                    if (!shell.targetsUid && shell.packageName != null) {
                        shell.mInterface.setMode(shell.op, shell.packageUid, shell.packageName, strModeToMode);
                        return 0;
                    }
                    if (!shell.targetsUid || shell.packageName == null) {
                        shell.mInterface.setUidMode(shell.op, shell.nonpackageUid, strModeToMode);
                        return 0;
                    }
                    try {
                        shell.mInterface.setUidMode(shell.op, shell.mInternal.mContext.getPackageManager().getPackageUidAsUser(shell.packageName, shell.userId), strModeToMode);
                        return 0;
                    } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                        return -1;
                    }
                case 1:
                    int parseUserPackageOp2 = shell.parseUserPackageOp(false, errPrintWriter);
                    if (parseUserPackageOp2 < 0) {
                        return parseUserPackageOp2;
                    }
                    java.util.List arrayList = new java.util.ArrayList();
                    if (shell.packageName != null) {
                        java.util.List uidOps = shell.mInterface.getUidOps(shell.packageUid, shell.op != -1 ? new int[]{shell.op} : null);
                        if (uidOps != null) {
                            arrayList.addAll(uidOps);
                        }
                        java.util.List opsForPackage = shell.mInterface.getOpsForPackage(shell.packageUid, shell.packageName, shell.op != -1 ? new int[]{shell.op} : null);
                        if (opsForPackage != null) {
                            arrayList.addAll(opsForPackage);
                        }
                    } else {
                        arrayList = shell.mInterface.getUidOps(shell.nonpackageUid, shell.op != -1 ? new int[]{shell.op} : null);
                    }
                    if (arrayList == null || arrayList.size() <= 0) {
                        outPrintWriter.println("No operations.");
                        if (shell.op > -1 && shell.op < 148) {
                            outPrintWriter.println("Default mode: " + android.app.AppOpsManager.modeToName(android.app.AppOpsManager.opToDefaultMode(shell.op)));
                            return 0;
                        }
                        return 0;
                    }
                    long currentTimeMillis = java.lang.System.currentTimeMillis();
                    int i2 = 0;
                    while (i2 < arrayList.size()) {
                        android.app.AppOpsManager.PackageOps packageOps = (android.app.AppOpsManager.PackageOps) arrayList.get(i2);
                        if (packageOps.getPackageName() == null) {
                            outPrintWriter.print("Uid mode: ");
                        }
                        int i3 = i;
                        for (java.util.List ops = packageOps.getOps(); i3 < ops.size(); ops = list) {
                            android.app.AppOpsManager.OpEntry opEntry = (android.app.AppOpsManager.OpEntry) ops.get(i3);
                            outPrintWriter.print(android.app.AppOpsManager.opToName(opEntry.getOp()));
                            outPrintWriter.print(": ");
                            outPrintWriter.print(android.app.AppOpsManager.modeToName(opEntry.getMode()));
                            if (shell.attributionTag == null) {
                                if (opEntry.getLastAccessTime(31) == -1) {
                                    list = ops;
                                } else {
                                    outPrintWriter.print("; time=");
                                    list = ops;
                                    android.util.TimeUtils.formatDuration(currentTimeMillis - opEntry.getLastAccessTime(31), outPrintWriter);
                                    outPrintWriter.print(" ago");
                                }
                                if (opEntry.getLastRejectTime(31) != -1) {
                                    outPrintWriter.print("; rejectTime=");
                                    android.util.TimeUtils.formatDuration(currentTimeMillis - opEntry.getLastRejectTime(31), outPrintWriter);
                                    outPrintWriter.print(" ago");
                                }
                                if (opEntry.isRunning()) {
                                    outPrintWriter.print(" (running)");
                                } else if (opEntry.getLastDuration(31) != -1) {
                                    outPrintWriter.print("; duration=");
                                    android.util.TimeUtils.formatDuration(opEntry.getLastDuration(31), outPrintWriter);
                                }
                            } else {
                                list = ops;
                                android.app.AppOpsManager.AttributedOpEntry attributedOpEntry = (android.app.AppOpsManager.AttributedOpEntry) opEntry.getAttributedOpEntries().get(shell.attributionTag);
                                if (attributedOpEntry != null) {
                                    if (attributedOpEntry.getLastAccessTime(31) != -1) {
                                        outPrintWriter.print("; time=");
                                        android.util.TimeUtils.formatDuration(currentTimeMillis - attributedOpEntry.getLastAccessTime(31), outPrintWriter);
                                        outPrintWriter.print(" ago");
                                    }
                                    if (attributedOpEntry.getLastRejectTime(31) != -1) {
                                        outPrintWriter.print("; rejectTime=");
                                        android.util.TimeUtils.formatDuration(currentTimeMillis - attributedOpEntry.getLastRejectTime(31), outPrintWriter);
                                        outPrintWriter.print(" ago");
                                    }
                                    if (attributedOpEntry.isRunning()) {
                                        outPrintWriter.print(" (running)");
                                    } else if (attributedOpEntry.getLastDuration(31) != -1) {
                                        outPrintWriter.print("; duration=");
                                        android.util.TimeUtils.formatDuration(attributedOpEntry.getLastDuration(31), outPrintWriter);
                                    }
                                }
                            }
                            outPrintWriter.println();
                            i3++;
                        }
                        i2++;
                        i = 0;
                    }
                    return 0;
                case 2:
                    int parseUserOpMode = shell.parseUserOpMode(1, errPrintWriter);
                    if (parseUserOpMode >= 0) {
                        java.util.List packagesForOps = shell.mInterface.getPackagesForOps(new int[]{shell.op});
                        if (packagesForOps == null || packagesForOps.size() <= 0) {
                            outPrintWriter.println("No operations.");
                            return 0;
                        }
                        for (int i4 = 0; i4 < packagesForOps.size(); i4++) {
                            android.app.AppOpsManager.PackageOps packageOps2 = (android.app.AppOpsManager.PackageOps) packagesForOps.get(i4);
                            java.util.List ops2 = ((android.app.AppOpsManager.PackageOps) packagesForOps.get(i4)).getOps();
                            int i5 = 0;
                            while (true) {
                                if (i5 < ops2.size()) {
                                    android.app.AppOpsManager.OpEntry opEntry2 = (android.app.AppOpsManager.OpEntry) ops2.get(i5);
                                    if (opEntry2.getOp() != shell.op || opEntry2.getMode() != shell.mode) {
                                        i5++;
                                    } else {
                                        b = true;
                                    }
                                } else {
                                    b = false;
                                }
                            }
                            if (b != false) {
                                outPrintWriter.println(packageOps2.getPackageName());
                            }
                        }
                        return 0;
                    }
                    return parseUserOpMode;
                case 3:
                    int i6 = -2;
                    while (true) {
                        java.lang.String nextArg2 = shell.getNextArg();
                        if (nextArg2 != null) {
                            if ("--user".equals(nextArg2)) {
                                i6 = android.os.UserHandle.parseUserArg(shell.getNextArgRequired());
                            } else {
                                if (str2 != null) {
                                    errPrintWriter.println("Error: Unsupported argument: " + nextArg2);
                                    return -1;
                                }
                                str2 = nextArg2;
                            }
                        } else {
                            if (i6 == -2) {
                                i6 = android.app.ActivityManager.getCurrentUser();
                            }
                            shell.mInterface.resetAllModes(i6, str2);
                            outPrintWriter.print("Reset all modes for: ");
                            if (i6 == -1) {
                                outPrintWriter.print("all users");
                            } else {
                                outPrintWriter.print("user ");
                                outPrintWriter.print(i6);
                            }
                            outPrintWriter.print(", ");
                            if (str2 == null) {
                                outPrintWriter.println("all packages");
                            } else {
                                outPrintWriter.print("package ");
                                outPrintWriter.println(str2);
                            }
                            return 0;
                        }
                    }
                case 4:
                    shell.mInternal.enforceManageAppOpsModes(android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), -1);
                    long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                    try {
                        synchronized (shell.mInternal) {
                            shell.mInternal.mHandler.removeCallbacks(shell.mInternal.mWriteRunner);
                        }
                        shell.mInternal.writeRecentAccesses();
                        shell.mInternal.mAppOpsCheckingService.writeState();
                        outPrintWriter.println("Current settings written.");
                        return 0;
                    } finally {
                    }
                case 5:
                    shell.mInternal.enforceManageAppOpsModes(android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), -1);
                    long clearCallingIdentity2 = android.os.Binder.clearCallingIdentity();
                    try {
                        shell.mInternal.readRecentAccesses();
                        shell.mInternal.mAppOpsCheckingService.readState();
                        outPrintWriter.println("Last settings read.");
                        return 0;
                    } finally {
                    }
                case 6:
                    int parseUserPackageOp3 = shell.parseUserPackageOp(true, errPrintWriter);
                    if (parseUserPackageOp3 < 0) {
                        return parseUserPackageOp3;
                    }
                    if (shell.packageName != null) {
                        shell.mInterface.startOperation(shell.mToken, shell.op, shell.packageUid, shell.packageName, shell.attributionTag, true, true, "appops start shell command", true, 1, -1);
                        return 0;
                    }
                    return -1;
                case 7:
                    int parseUserPackageOp4 = shell.parseUserPackageOp(true, errPrintWriter);
                    if (parseUserPackageOp4 < 0) {
                        return parseUserPackageOp4;
                    }
                    if (shell.packageName != null) {
                        shell.mInterface.finishOperation(shell.mToken, shell.op, shell.packageUid, shell.packageName, shell.attributionTag);
                        return 0;
                    }
                    return -1;
                default:
                    return shell.handleDefaultCommands(str);
            }
        } catch (android.os.RemoteException e2) {
            outPrintWriter.println("Remote exception: " + e2);
            return -1;
        }
        outPrintWriter.println("Remote exception: " + e2);
        return -1;
    }

    private void dumpHelp(java.io.PrintWriter printWriter) {
        printWriter.println("AppOps service (appops) dump options:");
        printWriter.println("  -h");
        printWriter.println("    Print this help text.");
        printWriter.println("  --op [OP]");
        printWriter.println("    Limit output to data associated with the given app op code.");
        printWriter.println("  --mode [MODE]");
        printWriter.println("    Limit output to data associated with the given app op mode.");
        printWriter.println("  --package [PACKAGE]");
        printWriter.println("    Limit output to data associated with the given package name.");
        printWriter.println("  --attributionTag [attributionTag]");
        printWriter.println("    Limit output to data associated with the given attribution tag.");
        printWriter.println("  --include-discrete [n]");
        printWriter.println("    Include discrete ops limited to n per dimension. Use zero for no limit.");
        printWriter.println("  --watchers");
        printWriter.println("    Only output the watcher sections.");
        printWriter.println("  --history");
        printWriter.println("    Only output history.");
        printWriter.println("  --uid-state-changes");
        printWriter.println("    Include logs about uid state changes.");
    }

    private void dumpStatesLocked(@android.annotation.NonNull java.io.PrintWriter printWriter, @android.annotation.Nullable java.lang.String str, int i, long j, @android.annotation.NonNull com.android.server.appop.AppOpsService.Op op, long j2, @android.annotation.NonNull java.text.SimpleDateFormat simpleDateFormat, @android.annotation.NonNull java.util.Date date, @android.annotation.NonNull java.lang.String str2) {
        int i2;
        android.util.ArrayMap<java.lang.String, com.android.server.appop.AttributedOp> arrayMap = op.mDeviceAttributedOps.get("default:0");
        int size = arrayMap.size();
        int i3 = 0;
        while (i3 < size) {
            if ((i & 4) != 0 && !java.util.Objects.equals(arrayMap.keyAt(i3), str)) {
                i2 = i3;
                i3 = i2 + 1;
            }
            printWriter.print(str2 + arrayMap.keyAt(i3) + "=[\n");
            i2 = i3;
            dumpStatesLocked(printWriter, j, op, arrayMap.keyAt(i3), j2, simpleDateFormat, date, str2 + "  ");
            printWriter.print(str2 + "]\n");
            i3 = i2 + 1;
        }
    }

    private void dumpStatesLocked(@android.annotation.NonNull java.io.PrintWriter printWriter, long j, @android.annotation.NonNull com.android.server.appop.AppOpsService.Op op, @android.annotation.Nullable java.lang.String str, long j2, @android.annotation.NonNull java.text.SimpleDateFormat simpleDateFormat, @android.annotation.NonNull java.util.Date date, @android.annotation.NonNull java.lang.String str2) {
        java.lang.String str3;
        int i;
        android.app.AppOpsManager.AttributedOpEntry attributedOpEntry;
        java.lang.String str4;
        java.lang.String str5;
        java.lang.String str6;
        java.lang.String str7;
        java.lang.String str8;
        java.lang.String str9 = str2;
        android.app.AppOpsManager.AttributedOpEntry attributedOpEntry2 = (android.app.AppOpsManager.AttributedOpEntry) op.createSingleAttributionEntryLocked(str).getAttributedOpEntries().get(str);
        android.util.ArraySet collectKeys = attributedOpEntry2.collectKeys();
        int size = collectKeys.size();
        int i2 = 0;
        while (i2 < size) {
            long longValue = ((java.lang.Long) collectKeys.valueAt(i2)).longValue();
            int extractUidStateFromKey = android.app.AppOpsManager.extractUidStateFromKey(longValue);
            int extractFlagsFromKey = android.app.AppOpsManager.extractFlagsFromKey(longValue);
            int i3 = i2;
            long lastAccessTime = attributedOpEntry2.getLastAccessTime(extractUidStateFromKey, extractUidStateFromKey, extractFlagsFromKey);
            long lastRejectTime = attributedOpEntry2.getLastRejectTime(extractUidStateFromKey, extractUidStateFromKey, extractFlagsFromKey);
            android.util.ArraySet arraySet = collectKeys;
            int i4 = size;
            long lastDuration = attributedOpEntry2.getLastDuration(extractUidStateFromKey, extractUidStateFromKey, extractFlagsFromKey);
            android.app.AppOpsManager.OpEventProxyInfo lastProxyInfo = attributedOpEntry2.getLastProxyInfo(extractUidStateFromKey, extractUidStateFromKey, extractFlagsFromKey);
            if (lastProxyInfo == null) {
                str3 = null;
                i = -1;
                attributedOpEntry = attributedOpEntry2;
                str4 = null;
            } else {
                str3 = lastProxyInfo.getPackageName();
                java.lang.String attributionTag = lastProxyInfo.getAttributionTag();
                i = lastProxyInfo.getUid();
                attributedOpEntry = attributedOpEntry2;
                str4 = attributionTag;
            }
            java.lang.String str10 = str4;
            if (lastAccessTime <= 0) {
                str5 = "]";
                str6 = str10;
                str7 = ", attributionTag=";
            } else {
                printWriter.print(str9);
                printWriter.print("Access: ");
                printWriter.print(android.app.AppOpsManager.keyToString(longValue));
                printWriter.print(" ");
                date.setTime(lastAccessTime);
                printWriter.print(simpleDateFormat.format(date));
                printWriter.print(" (");
                android.util.TimeUtils.formatDuration(lastAccessTime - j2, printWriter);
                printWriter.print(")");
                if (lastDuration > 0) {
                    printWriter.print(" duration=");
                    android.util.TimeUtils.formatDuration(lastDuration, printWriter);
                }
                if (i < 0) {
                    str5 = "]";
                    str6 = str10;
                    str7 = ", attributionTag=";
                } else {
                    printWriter.print(" proxy[");
                    printWriter.print("uid=");
                    printWriter.print(i);
                    printWriter.print(", pkg=");
                    printWriter.print(str3);
                    str7 = ", attributionTag=";
                    printWriter.print(str7);
                    str6 = str10;
                    printWriter.print(str6);
                    str5 = "]";
                    printWriter.print(str5);
                }
                printWriter.println();
            }
            if (lastRejectTime <= 0) {
                str8 = str2;
            } else {
                str8 = str2;
                printWriter.print(str8);
                printWriter.print("Reject: ");
                printWriter.print(android.app.AppOpsManager.keyToString(longValue));
                date.setTime(lastRejectTime);
                printWriter.print(simpleDateFormat.format(date));
                printWriter.print(" (");
                android.util.TimeUtils.formatDuration(lastRejectTime - j2, printWriter);
                printWriter.print(")");
                if (i >= 0) {
                    printWriter.print(" proxy[");
                    printWriter.print("uid=");
                    printWriter.print(i);
                    printWriter.print(", pkg=");
                    printWriter.print(str3);
                    printWriter.print(str7);
                    printWriter.print(str6);
                    printWriter.print(str5);
                }
                printWriter.println();
            }
            i2 = i3 + 1;
            str9 = str8;
            collectKeys = arraySet;
            size = i4;
            attributedOpEntry2 = attributedOpEntry;
        }
        java.lang.String str11 = str9;
        com.android.server.appop.AttributedOp attributedOp = op.mDeviceAttributedOps.getOrDefault("default:0", new android.util.ArrayMap<>()).get(str);
        if (attributedOp.isRunning()) {
            int size2 = attributedOp.mInProgressEvents.size();
            long j3 = com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME;
            long j4 = 0;
            for (int i5 = 0; i5 < size2; i5++) {
                j3 = java.lang.Math.min(j3, attributedOp.mInProgressEvents.valueAt(i5).getStartElapsedTime());
                j4 = java.lang.Math.max(j4, r6.mNumUnfinishedStarts);
            }
            printWriter.print(str11 + "Running start at: ");
            android.util.TimeUtils.formatDuration(j - j3, printWriter);
            printWriter.println();
            if (j4 > 1) {
                printWriter.print(str11 + "startNesting=");
                printWriter.println(j4);
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:402:0x07f8, code lost:
    
        if (r0 != r41.mAppOpsCheckingService.getPackageMode(r6.packageName, r6.op, android.os.UserHandle.getUserId(r6.uid))) goto L405;
     */
    /* JADX WARN: Removed duplicated region for block: B:42:0x00c3  */
    /* JADX WARN: Removed duplicated region for block: B:433:0x0659  */
    /* JADX WARN: Removed duplicated region for block: B:448:0x0685  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x00ae A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:496:0x072e  */
    /* JADX WARN: Removed duplicated region for block: B:500:0x065c  */
    @dalvik.annotation.optimization.NeverCompile
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    protected void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        java.lang.String str;
        java.lang.String str2;
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        boolean z;
        boolean z2;
        boolean z3;
        boolean z4;
        boolean z5;
        boolean z6;
        java.text.SimpleDateFormat simpleDateFormat;
        int i6;
        java.lang.String str3;
        int i7;
        boolean z7;
        int i8;
        long j;
        java.text.SimpleDateFormat simpleDateFormat2;
        int i9;
        java.lang.String str4;
        int i10;
        boolean z8;
        boolean z9;
        boolean z10;
        boolean z11;
        boolean z12;
        android.util.ArrayMap<java.lang.String, com.android.server.appop.AppOpsService.Ops> arrayMap;
        boolean z13;
        boolean z14;
        boolean z15;
        boolean z16;
        android.util.ArrayMap<java.lang.String, com.android.server.appop.AppOpsService.Ops> arrayMap2;
        java.text.SimpleDateFormat simpleDateFormat3;
        int i11;
        long j2;
        int i12;
        java.lang.String str5;
        android.util.ArrayMap<java.lang.String, com.android.server.appop.AppOpsService.Ops> arrayMap3;
        int i13;
        boolean z17;
        int i14;
        long j3;
        com.android.server.appop.AppOpsService.Ops ops;
        int i15;
        java.lang.String str6;
        int packageMode;
        int i16;
        int i17;
        android.util.ArraySet<com.android.server.appop.OnOpModeChangedListener> arraySet;
        java.lang.String str7;
        int i18;
        int i19;
        int i20;
        if (com.android.internal.util.DumpUtils.checkDumpAndUsageStatsPermission(this.mContext, TAG, printWriter)) {
            java.lang.String str8 = null;
            if (strArr == null) {
                str = null;
                str2 = null;
                i = -1;
                i2 = -1;
                i3 = -1;
                i4 = 10;
                i5 = 0;
                z = false;
                z2 = false;
                z3 = false;
                z4 = false;
                z5 = false;
            } else {
                int i21 = -1;
                int i22 = -1;
                int i23 = 10;
                int i24 = 0;
                boolean z18 = false;
                boolean z19 = false;
                int i25 = 0;
                boolean z20 = false;
                boolean z21 = false;
                boolean z22 = false;
                java.lang.String str9 = null;
                int i26 = -1;
                while (i24 < strArr.length) {
                    java.lang.String str10 = strArr[i24];
                    java.lang.String str11 = str8;
                    if ("-h".equals(str10)) {
                        dumpHelp(printWriter);
                        return;
                    }
                    if ("-a".equals(str10)) {
                        z22 = true;
                        str8 = str11;
                    } else if ("--op".equals(str10)) {
                        i24++;
                        if (i24 >= strArr.length) {
                            printWriter.println("No argument for --op option");
                            return;
                        }
                        int strOpToOp = com.android.server.appop.AppOpsService.Shell.strOpToOp(strArr[i24], printWriter);
                        i25 |= 8;
                        if (strOpToOp >= 0) {
                            i22 = strOpToOp;
                            str8 = str11;
                        } else {
                            return;
                        }
                    } else if ("--package".equals(str10)) {
                        i24++;
                        if (i24 >= strArr.length) {
                            printWriter.println("No argument for --package option");
                            return;
                        }
                        java.lang.String str12 = strArr[i24];
                        int i27 = i25 | 2;
                        try {
                            str7 = str9;
                            i18 = i26;
                            i19 = i21;
                        } catch (android.os.RemoteException e) {
                            str7 = str9;
                            i18 = i26;
                            i19 = i21;
                        }
                        try {
                            i20 = android.app.AppGlobals.getPackageManager().getPackageUid(str12, 12591104L, 0);
                        } catch (android.os.RemoteException e2) {
                            i20 = i19;
                            if (i20 >= 0) {
                            }
                        }
                        if (i20 >= 0) {
                            printWriter.println("Unknown package: " + str12);
                            return;
                        }
                        i25 = i27 | 1;
                        i21 = android.os.UserHandle.getAppId(i20);
                        str8 = str12;
                        str9 = str7;
                        i26 = i18;
                    } else {
                        java.lang.String str13 = str9;
                        int i28 = i26;
                        int i29 = i21;
                        if ("--attributionTag".equals(str10)) {
                            i24++;
                            if (i24 >= strArr.length) {
                                printWriter.println("No argument for --attributionTag option");
                                return;
                            }
                            i25 |= 4;
                            str9 = strArr[i24];
                            str8 = str11;
                            i26 = i28;
                            i21 = i29;
                        } else if ("--mode".equals(str10)) {
                            i24++;
                            if (i24 >= strArr.length) {
                                printWriter.println("No argument for --mode option");
                                return;
                            }
                            int strModeToMode = com.android.server.appop.AppOpsService.Shell.strModeToMode(strArr[i24], printWriter);
                            if (strModeToMode >= 0) {
                                i26 = strModeToMode;
                                str8 = str11;
                                str9 = str13;
                                i21 = i29;
                            } else {
                                return;
                            }
                        } else if ("--watchers".equals(str10)) {
                            z19 = true;
                            str8 = str11;
                            str9 = str13;
                            i26 = i28;
                            i21 = i29;
                        } else if ("--include-discrete".equals(str10)) {
                            i24++;
                            if (i24 >= strArr.length) {
                                printWriter.println("No argument for --include-discrete option");
                                return;
                            }
                            try {
                                i23 = java.lang.Integer.valueOf(strArr[i24]).intValue();
                                z20 = true;
                                str8 = str11;
                                str9 = str13;
                                i26 = i28;
                                i21 = i29;
                            } catch (java.lang.NumberFormatException e3) {
                                printWriter.println("Wrong parameter: " + strArr[i24]);
                                return;
                            }
                        } else if ("--history".equals(str10)) {
                            z18 = true;
                            str8 = str11;
                            str9 = str13;
                            i26 = i28;
                            i21 = i29;
                        } else {
                            if (str10.length() > 0 && str10.charAt(0) == '-') {
                                printWriter.println("Unknown option: " + str10);
                                return;
                            }
                            if ("--uid-state-changes".equals(str10)) {
                                z21 = true;
                                str8 = str11;
                                str9 = str13;
                                i26 = i28;
                                i21 = i29;
                            } else {
                                printWriter.println("Unknown command: " + str10);
                                return;
                            }
                        }
                    }
                    i24++;
                }
                str2 = str9;
                i4 = i23;
                z4 = z21;
                z5 = z22;
                str = str8;
                i = i26;
                i3 = i21;
                z = z18;
                z2 = z19;
                i5 = i25;
                z3 = z20;
                i2 = i22;
            }
            java.text.SimpleDateFormat simpleDateFormat4 = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            java.util.Date date = new java.util.Date();
            synchronized (this) {
                try {
                    printWriter.println("Current AppOps Service state:");
                    if (!z && !z2) {
                        this.mConstants.dump(printWriter);
                    }
                    printWriter.println();
                    long currentTimeMillis = java.lang.System.currentTimeMillis();
                    long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
                    android.os.SystemClock.uptimeMillis();
                    if (i5 == 0 && i < 0 && this.mProfileOwners != null && !z2 && !z) {
                        printWriter.println("  Profile owners:");
                        for (int i30 = 0; i30 < this.mProfileOwners.size(); i30++) {
                            printWriter.print("    User #");
                            printWriter.print(this.mProfileOwners.keyAt(i30));
                            printWriter.print(": ");
                            android.os.UserHandle.formatUid(printWriter, this.mProfileOwners.valueAt(i30));
                            printWriter.println();
                        }
                        printWriter.println();
                    }
                    if (this.mOpModeWatchers.size() > 0 && !z) {
                        boolean z23 = false;
                        z6 = false;
                        for (int i31 = 0; i31 < this.mOpModeWatchers.size(); i31++) {
                            if (i2 < 0 || i2 == this.mOpModeWatchers.keyAt(i31)) {
                                android.util.ArraySet<com.android.server.appop.OnOpModeChangedListener> valueAt = this.mOpModeWatchers.valueAt(i31);
                                boolean z24 = z23;
                                boolean z25 = z6;
                                int i32 = 0;
                                boolean z26 = false;
                                while (i32 < valueAt.size()) {
                                    com.android.server.appop.OnOpModeChangedListener valueAt2 = valueAt.valueAt(i32);
                                    if (str == null) {
                                        arraySet = valueAt;
                                    } else {
                                        arraySet = valueAt;
                                        if (i3 != android.os.UserHandle.getAppId(valueAt2.getWatchingUid())) {
                                            i32++;
                                            valueAt = arraySet;
                                        }
                                    }
                                    if (!z24) {
                                        printWriter.println("  Op mode watchers:");
                                        z24 = true;
                                    }
                                    if (!z26) {
                                        printWriter.print("    Op ");
                                        printWriter.print(android.app.AppOpsManager.opToName(this.mOpModeWatchers.keyAt(i31)));
                                        printWriter.println(":");
                                        z26 = true;
                                    }
                                    printWriter.print("      #");
                                    printWriter.print(i32);
                                    printWriter.print(": ");
                                    printWriter.println(valueAt2);
                                    z25 = true;
                                    i32++;
                                    valueAt = arraySet;
                                }
                                z23 = z24;
                                z6 = z25;
                            }
                        }
                    } else {
                        z6 = false;
                    }
                    if (this.mPackageModeWatchers.size() > 0 && i2 < 0 && !z) {
                        boolean z27 = false;
                        for (int i33 = 0; i33 < this.mPackageModeWatchers.size(); i33++) {
                            if (str == null || str.equals(this.mPackageModeWatchers.keyAt(i33))) {
                                if (!z27) {
                                    printWriter.println("  Package mode watchers:");
                                    z27 = true;
                                }
                                printWriter.print("    Pkg ");
                                printWriter.print(this.mPackageModeWatchers.keyAt(i33));
                                printWriter.println(":");
                                android.util.ArraySet<com.android.server.appop.OnOpModeChangedListener> valueAt3 = this.mPackageModeWatchers.valueAt(i33);
                                for (int i34 = 0; i34 < valueAt3.size(); i34++) {
                                    printWriter.print("      #");
                                    printWriter.print(i34);
                                    printWriter.print(": ");
                                    printWriter.println(valueAt3.valueAt(i34));
                                }
                                z6 = true;
                            }
                        }
                    }
                    if (this.mModeWatchers.size() > 0 && i2 < 0 && !z) {
                        boolean z28 = false;
                        for (int i35 = 0; i35 < this.mModeWatchers.size(); i35++) {
                            com.android.server.appop.AppOpsService.ModeCallback valueAt4 = this.mModeWatchers.valueAt(i35);
                            if (str == null || i3 == android.os.UserHandle.getAppId(valueAt4.getWatchingUid())) {
                                if (!z28) {
                                    printWriter.println("  All op mode watchers:");
                                    z28 = true;
                                }
                                printWriter.print("    ");
                                printWriter.print(java.lang.Integer.toHexString(java.lang.System.identityHashCode(this.mModeWatchers.keyAt(i35))));
                                printWriter.print(": ");
                                printWriter.println(valueAt4);
                                z6 = true;
                            }
                        }
                    }
                    char c = ' ';
                    if (this.mActiveWatchers.size() > 0 && i < 0) {
                        int i36 = 0;
                        boolean z29 = false;
                        while (i36 < this.mActiveWatchers.size()) {
                            android.util.SparseArray<com.android.server.appop.AppOpsService.ActiveCallback> valueAt5 = this.mActiveWatchers.valueAt(i36);
                            if (valueAt5.size() > 0) {
                                com.android.server.appop.AppOpsService.ActiveCallback valueAt6 = valueAt5.valueAt(0);
                                if ((i2 < 0 || valueAt5.indexOfKey(i2) >= 0) && (str == null || i3 == android.os.UserHandle.getAppId(valueAt6.mWatchingUid))) {
                                    if (!z29) {
                                        printWriter.println("  All op active watchers:");
                                        z29 = true;
                                    }
                                    printWriter.print("    ");
                                    printWriter.print(java.lang.Integer.toHexString(java.lang.System.identityHashCode(this.mActiveWatchers.keyAt(i36))));
                                    printWriter.println(" ->");
                                    printWriter.print("        [");
                                    int size = valueAt5.size();
                                    int i37 = 0;
                                    while (i37 < size) {
                                        if (i37 > 0) {
                                            printWriter.print(c);
                                        }
                                        printWriter.print(android.app.AppOpsManager.opToName(valueAt5.keyAt(i37)));
                                        if (i37 < size - 1) {
                                            printWriter.print(',');
                                        }
                                        i37++;
                                        c = ' ';
                                    }
                                    printWriter.println("]");
                                    printWriter.print("        ");
                                    printWriter.println(valueAt6);
                                }
                            }
                            i36++;
                            c = ' ';
                        }
                        z6 = true;
                    }
                    if (this.mStartedWatchers.size() > 0 && i < 0) {
                        int size2 = this.mStartedWatchers.size();
                        boolean z30 = false;
                        int i38 = 0;
                        while (i38 < size2) {
                            android.util.SparseArray<com.android.server.appop.AppOpsService.StartedCallback> valueAt7 = this.mStartedWatchers.valueAt(i38);
                            if (valueAt7.size() > 0) {
                                com.android.server.appop.AppOpsService.StartedCallback valueAt8 = valueAt7.valueAt(0);
                                if ((i2 < 0 || valueAt7.indexOfKey(i2) >= 0) && (str == null || i3 == android.os.UserHandle.getAppId(valueAt8.mWatchingUid))) {
                                    if (!z30) {
                                        printWriter.println("  All op started watchers:");
                                        z30 = true;
                                    }
                                    printWriter.print("    ");
                                    printWriter.print(java.lang.Integer.toHexString(java.lang.System.identityHashCode(this.mStartedWatchers.keyAt(i38))));
                                    printWriter.println(" ->");
                                    printWriter.print("        [");
                                    int size3 = valueAt7.size();
                                    int i39 = 0;
                                    while (i39 < size3) {
                                        if (i39 <= 0) {
                                            i17 = size2;
                                        } else {
                                            i17 = size2;
                                            printWriter.print(' ');
                                        }
                                        printWriter.print(android.app.AppOpsManager.opToName(valueAt7.keyAt(i39)));
                                        if (i39 < size3 - 1) {
                                            printWriter.print(',');
                                        }
                                        i39++;
                                        size2 = i17;
                                    }
                                    i16 = size2;
                                    printWriter.println("]");
                                    printWriter.print("        ");
                                    printWriter.println(valueAt8);
                                    i38++;
                                    size2 = i16;
                                }
                            }
                            i16 = size2;
                            i38++;
                            size2 = i16;
                        }
                        z6 = true;
                    }
                    if (this.mNotedWatchers.size() > 0 && i < 0) {
                        boolean z31 = false;
                        for (int i40 = 0; i40 < this.mNotedWatchers.size(); i40++) {
                            android.util.SparseArray<com.android.server.appop.AppOpsService.NotedCallback> valueAt9 = this.mNotedWatchers.valueAt(i40);
                            if (valueAt9.size() > 0) {
                                com.android.server.appop.AppOpsService.NotedCallback valueAt10 = valueAt9.valueAt(0);
                                if ((i2 < 0 || valueAt9.indexOfKey(i2) >= 0) && (str == null || i3 == android.os.UserHandle.getAppId(valueAt10.mWatchingUid))) {
                                    if (!z31) {
                                        printWriter.println("  All op noted watchers:");
                                        z31 = true;
                                    }
                                    printWriter.print("    ");
                                    printWriter.print(java.lang.Integer.toHexString(java.lang.System.identityHashCode(this.mNotedWatchers.keyAt(i40))));
                                    printWriter.println(" ->");
                                    printWriter.print("        [");
                                    int size4 = valueAt9.size();
                                    for (int i41 = 0; i41 < size4; i41++) {
                                        if (i41 > 0) {
                                            printWriter.print(' ');
                                        }
                                        printWriter.print(android.app.AppOpsManager.opToName(valueAt9.keyAt(i41)));
                                        if (i41 < size4 - 1) {
                                            printWriter.print(',');
                                        }
                                    }
                                    printWriter.println("]");
                                    printWriter.print("        ");
                                    printWriter.println(valueAt10);
                                }
                            }
                        }
                        z6 = true;
                    }
                    if (this.mAudioRestrictionManager.hasActiveRestrictions() && i2 < 0 && str != null && i < 0 && !z2) {
                        z6 = this.mAudioRestrictionManager.dump(printWriter) || z6;
                    }
                    if (z6) {
                        printWriter.println();
                    }
                    int i42 = 0;
                    while (i42 < this.mUidStates.size()) {
                        com.android.server.appop.AppOpsService.UidState valueAt11 = this.mUidStates.valueAt(i42);
                        android.util.SparseIntArray nonDefaultUidModes = this.mAppOpsCheckingService.getNonDefaultUidModes(valueAt11.uid, "default:0");
                        android.util.ArrayMap<java.lang.String, com.android.server.appop.AppOpsService.Ops> arrayMap4 = valueAt11.pkgOps;
                        if (z2) {
                            z7 = z6;
                            i8 = i42;
                            j = elapsedRealtime;
                            simpleDateFormat2 = simpleDateFormat4;
                            i9 = i2;
                            str4 = str;
                            i10 = i3;
                        } else if (z) {
                            z7 = z6;
                            i8 = i42;
                            j = elapsedRealtime;
                            simpleDateFormat2 = simpleDateFormat4;
                            i9 = i2;
                            str4 = str;
                            i10 = i3;
                        } else {
                            if (i2 >= 0 || str != null || i >= 0) {
                                if (i2 >= 0 && (nonDefaultUidModes == null || nonDefaultUidModes.indexOfKey(i2) < 0)) {
                                    z8 = false;
                                    z9 = str != null || i3 == this.mUidStates.keyAt(i42);
                                    z10 = i >= 0;
                                    if (!z10 || nonDefaultUidModes == null) {
                                        z11 = z8;
                                        z12 = z9;
                                    } else {
                                        z11 = z8;
                                        int i43 = 0;
                                        while (true) {
                                            if (z10) {
                                                z12 = z9;
                                                break;
                                            }
                                            z12 = z9;
                                            if (i43 >= nonDefaultUidModes.size()) {
                                                break;
                                            }
                                            if (nonDefaultUidModes.valueAt(i43) == i) {
                                                z10 = true;
                                            }
                                            i43++;
                                            z9 = z12;
                                        }
                                    }
                                    if (arrayMap4 != null) {
                                        z7 = z6;
                                        arrayMap = arrayMap4;
                                        i8 = i42;
                                        simpleDateFormat2 = simpleDateFormat4;
                                        i10 = i3;
                                        z13 = z11;
                                        z14 = z12;
                                    } else {
                                        z13 = z11;
                                        int i44 = 0;
                                        while (true) {
                                            if (z13 && z12 && z10) {
                                                z7 = z6;
                                                arrayMap = arrayMap4;
                                                i8 = i42;
                                                simpleDateFormat2 = simpleDateFormat4;
                                                i10 = i3;
                                                break;
                                            }
                                            i8 = i42;
                                            if (i44 >= arrayMap4.size()) {
                                                z7 = z6;
                                                arrayMap = arrayMap4;
                                                simpleDateFormat2 = simpleDateFormat4;
                                                i10 = i3;
                                                break;
                                            }
                                            com.android.server.appop.AppOpsService.Ops valueAt12 = arrayMap4.valueAt(i44);
                                            if (!z13 && valueAt12 != null && valueAt12.indexOfKey(i2) >= 0) {
                                                z13 = true;
                                            }
                                            if (z10) {
                                                z15 = z6;
                                                z16 = z13;
                                                arrayMap2 = arrayMap4;
                                                simpleDateFormat3 = simpleDateFormat4;
                                                i11 = i3;
                                            } else {
                                                z16 = z13;
                                                int i45 = 0;
                                                while (true) {
                                                    if (z10) {
                                                        z15 = z6;
                                                        arrayMap2 = arrayMap4;
                                                        simpleDateFormat3 = simpleDateFormat4;
                                                        break;
                                                    }
                                                    simpleDateFormat3 = simpleDateFormat4;
                                                    if (i45 >= valueAt12.size()) {
                                                        z15 = z6;
                                                        arrayMap2 = arrayMap4;
                                                        break;
                                                    }
                                                    com.android.server.appop.AppOpsService.Op valueAt13 = valueAt12.valueAt(i45);
                                                    int i46 = i3;
                                                    boolean z32 = z6;
                                                    android.util.ArrayMap<java.lang.String, com.android.server.appop.AppOpsService.Ops> arrayMap5 = arrayMap4;
                                                    if (this.mAppOpsCheckingService.getPackageMode(valueAt13.packageName, valueAt13.op, android.os.UserHandle.getUserId(valueAt13.uid)) == i) {
                                                        z10 = true;
                                                    }
                                                    i45++;
                                                    simpleDateFormat4 = simpleDateFormat3;
                                                    i3 = i46;
                                                    z6 = z32;
                                                    arrayMap4 = arrayMap5;
                                                }
                                                i11 = i3;
                                            }
                                            if (!z12 && str.equals(valueAt12.packageName)) {
                                                z12 = true;
                                            }
                                            i44++;
                                            z13 = z16;
                                            i42 = i8;
                                            simpleDateFormat4 = simpleDateFormat3;
                                            i3 = i11;
                                            z6 = z15;
                                            arrayMap4 = arrayMap2;
                                        }
                                        z14 = z12;
                                    }
                                    if (z13 || !z14) {
                                        j = elapsedRealtime;
                                        i9 = i2;
                                        str4 = str;
                                    } else if (!z10) {
                                        j = elapsedRealtime;
                                        i9 = i2;
                                        str4 = str;
                                    }
                                }
                                z8 = true;
                                if (str != null) {
                                }
                                if (i >= 0) {
                                }
                                if (!z10) {
                                }
                                z11 = z8;
                                z12 = z9;
                                if (arrayMap4 != null) {
                                }
                                if (z13) {
                                }
                                j = elapsedRealtime;
                                i9 = i2;
                                str4 = str;
                            } else {
                                arrayMap = arrayMap4;
                                i8 = i42;
                                simpleDateFormat2 = simpleDateFormat4;
                                i10 = i3;
                            }
                            printWriter.print("  Uid ");
                            android.os.UserHandle.formatUid(printWriter, valueAt11.uid);
                            printWriter.println(":");
                            valueAt11.dump(printWriter, elapsedRealtime);
                            if (nonDefaultUidModes != null) {
                                int size5 = nonDefaultUidModes.size();
                                for (int i47 = 0; i47 < size5; i47++) {
                                    int keyAt = nonDefaultUidModes.keyAt(i47);
                                    int valueAt14 = nonDefaultUidModes.valueAt(i47);
                                    if ((i2 < 0 || i2 == keyAt) && (i < 0 || i == valueAt14)) {
                                        printWriter.print("      ");
                                        printWriter.print(android.app.AppOpsManager.opToName(keyAt));
                                        printWriter.print(": mode=");
                                        printWriter.println(android.app.AppOpsManager.modeToName(valueAt14));
                                    }
                                }
                            }
                            if (arrayMap == null) {
                                j = elapsedRealtime;
                                i9 = i2;
                                str4 = str;
                            } else {
                                int i48 = 0;
                                while (i48 < arrayMap.size()) {
                                    android.util.ArrayMap<java.lang.String, com.android.server.appop.AppOpsService.Ops> arrayMap6 = arrayMap;
                                    com.android.server.appop.AppOpsService.Ops valueAt15 = arrayMap6.valueAt(i48);
                                    if (str != null && !str.equals(valueAt15.packageName)) {
                                        arrayMap = arrayMap6;
                                        j2 = elapsedRealtime;
                                        i12 = i2;
                                        str5 = str;
                                    } else {
                                        boolean z33 = false;
                                        int i49 = 0;
                                        while (i49 < valueAt15.size()) {
                                            com.android.server.appop.AppOpsService.Op valueAt16 = valueAt15.valueAt(i49);
                                            int i50 = valueAt16.op;
                                            if (i2 >= 0 && i2 != i50) {
                                                arrayMap3 = arrayMap6;
                                                i13 = i49;
                                            } else {
                                                if (i >= 0) {
                                                    arrayMap3 = arrayMap6;
                                                    i13 = i49;
                                                } else {
                                                    arrayMap3 = arrayMap6;
                                                    i13 = i49;
                                                }
                                                if (z33) {
                                                    z17 = z33;
                                                } else {
                                                    printWriter.print("    Package ");
                                                    printWriter.print(valueAt15.packageName);
                                                    printWriter.println(":");
                                                    z17 = true;
                                                }
                                                printWriter.print("      ");
                                                printWriter.print(android.app.AppOpsManager.opToName(i50));
                                                printWriter.print(" (");
                                                printWriter.print(android.app.AppOpsManager.modeToName(this.mAppOpsCheckingService.getPackageMode(valueAt16.packageName, valueAt16.op, android.os.UserHandle.getUserId(valueAt16.uid))));
                                                int opToSwitch = android.app.AppOpsManager.opToSwitch(i50);
                                                if (opToSwitch != i50) {
                                                    printWriter.print(" / switch ");
                                                    printWriter.print(android.app.AppOpsManager.opToName(opToSwitch));
                                                    com.android.server.appop.AppOpsService.Op op = valueAt15.get(opToSwitch);
                                                    if (op == null) {
                                                        packageMode = android.app.AppOpsManager.opToDefaultMode(opToSwitch);
                                                    } else {
                                                        packageMode = this.mAppOpsCheckingService.getPackageMode(op.packageName, op.op, android.os.UserHandle.getUserId(op.uid));
                                                    }
                                                    printWriter.print("=");
                                                    printWriter.print(android.app.AppOpsManager.modeToName(packageMode));
                                                }
                                                printWriter.println("): ");
                                                i14 = i13;
                                                j3 = elapsedRealtime;
                                                ops = valueAt15;
                                                i15 = i2;
                                                str6 = str;
                                                dumpStatesLocked(printWriter, str2, i5, elapsedRealtime, valueAt16, currentTimeMillis, simpleDateFormat2, date, "        ");
                                                z33 = z17;
                                                i49 = i14 + 1;
                                                arrayMap6 = arrayMap3;
                                                elapsedRealtime = j3;
                                                valueAt15 = ops;
                                                i2 = i15;
                                                str = str6;
                                            }
                                            j3 = elapsedRealtime;
                                            ops = valueAt15;
                                            i15 = i2;
                                            str6 = str;
                                            i14 = i13;
                                            i49 = i14 + 1;
                                            arrayMap6 = arrayMap3;
                                            elapsedRealtime = j3;
                                            valueAt15 = ops;
                                            i2 = i15;
                                            str = str6;
                                        }
                                        arrayMap = arrayMap6;
                                        j2 = elapsedRealtime;
                                        i12 = i2;
                                        str5 = str;
                                    }
                                    i48++;
                                    elapsedRealtime = j2;
                                    i2 = i12;
                                    str = str5;
                                }
                                j = elapsedRealtime;
                                i9 = i2;
                                str4 = str;
                            }
                            z6 = true;
                            i42 = i8 + 1;
                            simpleDateFormat4 = simpleDateFormat2;
                            i3 = i10;
                            elapsedRealtime = j;
                            i2 = i9;
                            str = str4;
                        }
                        z6 = z7;
                        i42 = i8 + 1;
                        simpleDateFormat4 = simpleDateFormat2;
                        i3 = i10;
                        elapsedRealtime = j;
                        i2 = i9;
                        str = str4;
                    }
                    simpleDateFormat = simpleDateFormat4;
                    i6 = i2;
                    str3 = str;
                    i7 = i3;
                    if (z6) {
                        printWriter.println();
                    }
                    this.mAppOpsRestrictions.dumpRestrictions(printWriter, i6, str3, i >= 0 || z2 || z);
                    if (!z && !z2) {
                        printWriter.println();
                        if (this.mCheckOpsDelegateDispatcher.mPolicy != null && (this.mCheckOpsDelegateDispatcher.mPolicy instanceof com.android.server.policy.AppOpsPolicy)) {
                            ((com.android.server.policy.AppOpsPolicy) this.mCheckOpsDelegateDispatcher.mPolicy).dumpTags(printWriter);
                        } else {
                            printWriter.println("  AppOps policy not set.");
                        }
                    }
                    if (z5 || z4) {
                        printWriter.println();
                        printWriter.println("Uid State Changes Event Log:");
                        getUidStateTracker().dumpEvents(printWriter);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            if (z && !z2) {
                this.mHistoricalRegistry.dump("  ", printWriter, i7, str3, str2, i6, i5);
            }
            if (z3) {
                printWriter.println("Discrete accesses: ");
                this.mHistoricalRegistry.dumpDiscreteData(printWriter, i7, str3, str2, i5, i6, simpleDateFormat, date, "  ", i4);
            }
        }
    }

    public void setUserRestrictions(android.os.Bundle bundle, android.os.IBinder iBinder, int i) {
        checkSystemUid("setUserRestrictions");
        java.util.Objects.requireNonNull(bundle);
        java.util.Objects.requireNonNull(iBinder);
        for (int i2 = 0; i2 < 148; i2++) {
            java.lang.String opToRestriction = android.app.AppOpsManager.opToRestriction(i2);
            if (opToRestriction != null) {
                setUserRestrictionNoCheck(i2, bundle.getBoolean(opToRestriction, false), iBinder, i, null);
            }
        }
    }

    public void setUserRestriction(int i, boolean z, android.os.IBinder iBinder, int i2, android.os.PackageTagsList packageTagsList) {
        if (android.os.Binder.getCallingPid() != android.os.Process.myPid()) {
            this.mContext.enforcePermission("android.permission.MANAGE_APP_OPS_RESTRICTIONS", android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), null);
        }
        if (i2 != android.os.UserHandle.getCallingUserId() && this.mContext.checkCallingOrSelfPermission("android.permission.INTERACT_ACROSS_USERS_FULL") != 0 && this.mContext.checkCallingOrSelfPermission("android.permission.INTERACT_ACROSS_USERS") != 0) {
            throw new java.lang.SecurityException("Need INTERACT_ACROSS_USERS_FULL or INTERACT_ACROSS_USERS to interact cross user ");
        }
        verifyIncomingOp(i);
        java.util.Objects.requireNonNull(iBinder);
        setUserRestrictionNoCheck(i, z, iBinder, i2, packageTagsList);
    }

    private void setUserRestrictionNoCheck(int i, boolean z, android.os.IBinder iBinder, int i2, android.os.PackageTagsList packageTagsList) {
        synchronized (this) {
            try {
                com.android.server.appop.AppOpsService.ClientUserRestrictionState clientUserRestrictionState = this.mOpUserRestrictions.get(iBinder);
                if (clientUserRestrictionState == null) {
                    try {
                        clientUserRestrictionState = new com.android.server.appop.AppOpsService.ClientUserRestrictionState(iBinder);
                        this.mOpUserRestrictions.put(iBinder, clientUserRestrictionState);
                    } catch (android.os.RemoteException e) {
                        return;
                    }
                }
                if (clientUserRestrictionState.setRestriction(i, z, packageTagsList, i2)) {
                    this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.server.appop.AppOpsService$$ExternalSyntheticLambda2(), this, java.lang.Integer.valueOf(i), -2));
                    this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.QuadConsumer() { // from class: com.android.server.appop.AppOpsService$$ExternalSyntheticLambda11
                        public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3, java.lang.Object obj4) {
                            ((com.android.server.appop.AppOpsService) obj).updateStartedOpModeForUserForDefaultDevice(((java.lang.Integer) obj2).intValue(), ((java.lang.Boolean) obj3).booleanValue(), ((java.lang.Integer) obj4).intValue());
                        }
                    }, this, java.lang.Integer.valueOf(i), java.lang.Boolean.valueOf(z), java.lang.Integer.valueOf(i2)));
                }
                if (clientUserRestrictionState.isDefault()) {
                    this.mOpUserRestrictions.remove(iBinder);
                    clientUserRestrictionState.destroy();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateStartedOpModeForUserForDefaultDevice(int i, boolean z, int i2) {
        synchronized (this) {
            try {
                int size = this.mUidStates.size();
                for (int i3 = 0; i3 < size; i3++) {
                    int keyAt = this.mUidStates.keyAt(i3);
                    if (i2 == -1 || android.os.UserHandle.getUserId(keyAt) == i2) {
                        updateStartedOpModeForUidForDefaultDeviceLocked(i, z, keyAt);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void updateStartedOpModeForUidForDefaultDeviceLocked(int i, boolean z, int i2) {
        int packageMode;
        com.android.server.appop.AppOpsService.UidState uidState = this.mUidStates.get(i2);
        if (uidState == null) {
            return;
        }
        int size = uidState.pkgOps.size();
        for (int i3 = 0; i3 < size; i3++) {
            com.android.server.appop.AppOpsService.Ops valueAt = uidState.pkgOps.valueAt(i3);
            com.android.server.appop.AppOpsService.Op op = valueAt != null ? valueAt.get(i) : null;
            if (op != null && ((packageMode = this.mAppOpsCheckingService.getPackageMode(op.packageName, op.op, android.os.UserHandle.getUserId(op.uid))) == 0 || packageMode == 4)) {
                android.util.ArrayMap<java.lang.String, com.android.server.appop.AttributedOp> arrayMap = op.mDeviceAttributedOps.get("default:0");
                for (int i4 = 0; i4 < arrayMap.size(); i4++) {
                    com.android.server.appop.AttributedOp valueAt2 = arrayMap.valueAt(i4);
                    if (z && valueAt2.isRunning()) {
                        valueAt2.pause();
                    } else if (valueAt2.isPaused()) {
                        valueAt2.resume();
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyWatchersOnDefaultDevice(int i, int i2) {
        synchronized (this) {
            try {
                android.util.ArraySet<com.android.server.appop.OnOpModeChangedListener> arraySet = this.mOpModeWatchers.get(i);
                if (arraySet == null) {
                    return;
                }
                notifyOpChanged(arraySet, i, i2, (java.lang.String) null, "default:0");
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void removeUser(int i) throws android.os.RemoteException {
        checkSystemUid("removeUser");
        synchronized (this) {
            try {
                for (int size = this.mOpUserRestrictions.size() - 1; size >= 0; size--) {
                    this.mOpUserRestrictions.valueAt(size).removeUser(i);
                }
                removeUidsForUserLocked(i);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public boolean isOperationActive(int i, int i2, java.lang.String str) {
        java.lang.String resolvePackageName;
        if (android.os.Binder.getCallingUid() != i2 && this.mContext.checkCallingOrSelfPermission("android.permission.WATCH_APPOPS") != 0) {
            return false;
        }
        verifyIncomingOp(i);
        if (!isIncomingPackageValid(str, android.os.UserHandle.getUserId(i2)) || (resolvePackageName = android.app.AppOpsManager.resolvePackageName(i2, str)) == null) {
            return false;
        }
        synchronized (this) {
            try {
                com.android.server.appop.AppOpsService.Ops opsLocked = getOpsLocked(i2, resolvePackageName, null, false, null, false);
                if (opsLocked == null) {
                    return false;
                }
                com.android.server.appop.AppOpsService.Op op = opsLocked.get(i);
                if (op == null) {
                    return false;
                }
                return op.isRunning();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public boolean isProxying(int i, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2, int i2, @android.annotation.NonNull java.lang.String str3) {
        java.util.Objects.requireNonNull(str);
        java.util.Objects.requireNonNull(str3);
        long callingUid = android.os.Binder.getCallingUid();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            java.util.List<android.app.AppOpsManager.PackageOps> opsForPackage = getOpsForPackage(i2, str3, new int[]{i});
            boolean z = false;
            if (opsForPackage == null || opsForPackage.isEmpty()) {
                return false;
            }
            java.util.List ops = opsForPackage.get(0).getOps();
            if (ops.isEmpty()) {
                return false;
            }
            android.app.AppOpsManager.OpEntry opEntry = (android.app.AppOpsManager.OpEntry) ops.get(0);
            if (!opEntry.isRunning()) {
                return false;
            }
            android.app.AppOpsManager.OpEventProxyInfo lastProxyInfo = opEntry.getLastProxyInfo(24);
            if (lastProxyInfo != null && callingUid == lastProxyInfo.getUid() && str.equals(lastProxyInfo.getPackageName())) {
                if (java.util.Objects.equals(str2, lastProxyInfo.getAttributionTag())) {
                    z = true;
                }
            }
            return z;
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @android.annotation.EnforcePermission("android.permission.MANAGE_APPOPS")
    public void resetPackageOpsNoHistory(@android.annotation.NonNull java.lang.String str) {
        resetPackageOpsNoHistory_enforcePermission();
        synchronized (this) {
            try {
                int packageUid = this.mPackageManagerInternal.getPackageUid(str, 0L, android.os.UserHandle.getCallingUserId());
                if (packageUid == -1) {
                    return;
                }
                com.android.server.appop.AppOpsService.UidState uidState = this.mUidStates.get(packageUid);
                if (uidState == null) {
                    return;
                }
                com.android.server.appop.AppOpsService.Ops remove = uidState.pkgOps.remove(str);
                this.mAppOpsCheckingService.removePackage(str, android.os.UserHandle.getUserId(packageUid));
                if (remove != null) {
                    scheduleFastWriteLocked();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @android.annotation.EnforcePermission("android.permission.MANAGE_APPOPS")
    public void setHistoryParameters(int i, long j, int i2) {
        setHistoryParameters_enforcePermission();
        this.mHistoricalRegistry.setHistoryParameters(i, j, i2);
    }

    @android.annotation.EnforcePermission("android.permission.MANAGE_APPOPS")
    public void offsetHistory(long j) {
        offsetHistory_enforcePermission();
        this.mHistoricalRegistry.offsetHistory(j);
        this.mHistoricalRegistry.offsetDiscreteHistory(j);
    }

    @android.annotation.EnforcePermission("android.permission.MANAGE_APPOPS")
    public void addHistoricalOps(android.app.AppOpsManager.HistoricalOps historicalOps) {
        addHistoricalOps_enforcePermission();
        this.mHistoricalRegistry.addHistoricalOps(historicalOps);
    }

    @android.annotation.EnforcePermission("android.permission.MANAGE_APPOPS")
    public void resetHistoryParameters() {
        resetHistoryParameters_enforcePermission();
        this.mHistoricalRegistry.resetHistoryParameters();
    }

    @android.annotation.EnforcePermission("android.permission.MANAGE_APPOPS")
    public void clearHistory() {
        clearHistory_enforcePermission();
        this.mHistoricalRegistry.clearAllHistory();
    }

    @android.annotation.EnforcePermission("android.permission.MANAGE_APPOPS")
    public void rebootHistory(long j) {
        rebootHistory_enforcePermission();
        com.android.internal.util.Preconditions.checkArgument(j >= 0);
        this.mHistoricalRegistry.shutdown();
        if (j > 0) {
            android.os.SystemClock.sleep(j);
        }
        this.mHistoricalRegistry = new com.android.server.appop.HistoricalRegistry(this.mHistoricalRegistry);
        this.mHistoricalRegistry.systemReady(this.mContext.getContentResolver());
        this.mHistoricalRegistry.persistPendingHistory();
    }

    public com.android.internal.app.MessageSamplingConfig reportRuntimeAppOpAccessMessageAndGetConfig(java.lang.String str, android.app.SyncNotedAppOp syncNotedAppOp, java.lang.String str2) {
        int callingUid = android.os.Binder.getCallingUid();
        java.util.Objects.requireNonNull(str);
        synchronized (this) {
            try {
                switchPackageIfBootTimeOrRarelyUsedLocked(str);
                if (!str.equals(this.mSampledPackage)) {
                    return new com.android.internal.app.MessageSamplingConfig(-1, 0, java.time.Instant.now().plus(1L, (java.time.temporal.TemporalUnit) java.time.temporal.ChronoUnit.HOURS).toEpochMilli());
                }
                java.util.Objects.requireNonNull(syncNotedAppOp);
                java.util.Objects.requireNonNull(str2);
                reportRuntimeAppOpAccessMessageInternalLocked(callingUid, str, android.app.AppOpsManager.strOpToOp(syncNotedAppOp.getOp()), syncNotedAppOp.getAttributionTag(), str2);
                return new com.android.internal.app.MessageSamplingConfig(this.mSampledAppOpCode, this.mAcceptableLeftDistance, java.time.Instant.now().plus(1L, (java.time.temporal.TemporalUnit) java.time.temporal.ChronoUnit.HOURS).toEpochMilli());
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void reportRuntimeAppOpAccessMessageAsyncLocked(int i, @android.annotation.NonNull java.lang.String str, int i2, @android.annotation.Nullable java.lang.String str2, @android.annotation.NonNull java.lang.String str3) {
        switchPackageIfBootTimeOrRarelyUsedLocked(str);
        if (!java.util.Objects.equals(this.mSampledPackage, str)) {
            return;
        }
        reportRuntimeAppOpAccessMessageInternalLocked(i, str, i2, str2, str3);
    }

    private void reportRuntimeAppOpAccessMessageInternalLocked(int i, @android.annotation.NonNull java.lang.String str, int i2, @android.annotation.Nullable java.lang.String str2, @android.annotation.NonNull java.lang.String str3) {
        int leftCircularDistance = android.app.AppOpsManager.leftCircularDistance(i2, this.mSampledAppOpCode, 148);
        if (this.mAcceptableLeftDistance < leftCircularDistance && this.mSamplingStrategy != 4) {
            return;
        }
        if (this.mAcceptableLeftDistance > leftCircularDistance && this.mSamplingStrategy != 4) {
            this.mAcceptableLeftDistance = leftCircularDistance;
            this.mMessagesCollectedCount = com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;
        }
        this.mMessagesCollectedCount += 1.0f;
        if (java.util.concurrent.ThreadLocalRandom.current().nextFloat() <= 1.0f / this.mMessagesCollectedCount) {
            this.mCollectedRuntimePermissionMessage = new android.app.RuntimeAppOpAccessMessage(i, i2, str, str2, str3, this.mSamplingStrategy);
        }
    }

    @android.annotation.Nullable
    public android.app.RuntimeAppOpAccessMessage collectRuntimeAppOpAccessMessage() {
        android.app.RuntimeAppOpAccessMessage runtimeAppOpAccessMessage;
        boolean z = ((android.app.ActivityManagerInternal) com.android.server.LocalServices.getService(android.app.ActivityManagerInternal.class)).getInstrumentationSourceUid(android.os.Binder.getCallingUid()) != -1;
        if (!(android.os.Binder.getCallingPid() == android.os.Process.myPid()) && !z) {
            return null;
        }
        this.mContext.enforcePermission("android.permission.GET_APP_OPS_STATS", android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), null);
        synchronized (this) {
            runtimeAppOpAccessMessage = this.mCollectedRuntimePermissionMessage;
            this.mCollectedRuntimePermissionMessage = null;
        }
        this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.Consumer() { // from class: com.android.server.appop.AppOpsService$$ExternalSyntheticLambda9
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.appop.AppOpsService) obj).getPackageListAndResample();
            }
        }, this));
        return runtimeAppOpAccessMessage;
    }

    private void switchPackageIfBootTimeOrRarelyUsedLocked(@android.annotation.NonNull java.lang.String str) {
        if (this.mSampledPackage == null) {
            if (java.util.concurrent.ThreadLocalRandom.current().nextFloat() < 0.5f) {
                this.mSamplingStrategy = 3;
                resampleAppOpForPackageLocked(str, true);
                return;
            }
            return;
        }
        if (this.mRarelyUsedPackages.contains(str)) {
            this.mRarelyUsedPackages.remove(str);
            if (java.util.concurrent.ThreadLocalRandom.current().nextFloat() < 0.5f) {
                this.mSamplingStrategy = 2;
                resampleAppOpForPackageLocked(str, true);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public java.util.List<java.lang.String> getPackageListAndResample() {
        java.util.List<java.lang.String> packageNamesForSampling = getPackageNamesForSampling();
        synchronized (this) {
            resamplePackageAndAppOpLocked(packageNamesForSampling);
        }
        return packageNamesForSampling;
    }

    private void resamplePackageAndAppOpLocked(@android.annotation.NonNull java.util.List<java.lang.String> list) {
        if (list.isEmpty()) {
            return;
        }
        if (java.util.concurrent.ThreadLocalRandom.current().nextFloat() < 0.5f) {
            this.mSamplingStrategy = 1;
            resampleAppOpForPackageLocked(list.get(java.util.concurrent.ThreadLocalRandom.current().nextInt(list.size())), true);
        } else {
            this.mSamplingStrategy = 4;
            resampleAppOpForPackageLocked(list.get(java.util.concurrent.ThreadLocalRandom.current().nextInt(list.size())), false);
        }
    }

    private void resampleAppOpForPackageLocked(@android.annotation.NonNull java.lang.String str, boolean z) {
        this.mMessagesCollectedCount = com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;
        this.mSampledAppOpCode = z ? java.util.concurrent.ThreadLocalRandom.current().nextInt(148) : -1;
        this.mAcceptableLeftDistance = 147;
        this.mSampledPackage = str;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initializeRarelyUsedPackagesList(@android.annotation.NonNull final android.util.ArraySet<java.lang.String> arraySet) {
        ((android.app.AppOpsManager) this.mContext.getSystemService(android.app.AppOpsManager.class)).getHistoricalOps(new android.app.AppOpsManager.HistoricalOpsRequest.Builder(java.lang.Math.max(java.time.Instant.now().minus(7L, (java.time.temporal.TemporalUnit) java.time.temporal.ChronoUnit.DAYS).toEpochMilli(), 0L), com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME).setOpNames(getRuntimeAppOpsList()).setFlags(9).build(), android.os.AsyncTask.THREAD_POOL_EXECUTOR, new java.util.function.Consumer<android.app.AppOpsManager.HistoricalOps>() { // from class: com.android.server.appop.AppOpsService.9
            @Override // java.util.function.Consumer
            public void accept(android.app.AppOpsManager.HistoricalOps historicalOps) {
                int uidCount = historicalOps.getUidCount();
                for (int i = 0; i < uidCount; i++) {
                    android.app.AppOpsManager.HistoricalUidOps uidOpsAt = historicalOps.getUidOpsAt(i);
                    int packageCount = uidOpsAt.getPackageCount();
                    for (int i2 = 0; i2 < packageCount; i2++) {
                        java.lang.String packageName = uidOpsAt.getPackageOpsAt(i2).getPackageName();
                        if (arraySet.contains(packageName) && uidOpsAt.getPackageOpsAt(i2).getOpCount() != 0) {
                            arraySet.remove(packageName);
                        }
                    }
                }
                synchronized (this) {
                    try {
                        int size = com.android.server.appop.AppOpsService.this.mRarelyUsedPackages.size();
                        for (int i3 = 0; i3 < size; i3++) {
                            arraySet.add((java.lang.String) com.android.server.appop.AppOpsService.this.mRarelyUsedPackages.valueAt(i3));
                        }
                        com.android.server.appop.AppOpsService.this.mRarelyUsedPackages = arraySet;
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
            }
        });
    }

    private java.util.List<java.lang.String> getRuntimeAppOpsList() {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (int i = 0; i < 148; i++) {
            if (shouldCollectNotes(i)) {
                arrayList.add(android.app.AppOpsManager.opToPublicName(i));
            }
        }
        return arrayList;
    }

    @android.annotation.NonNull
    private java.util.List<java.lang.String> getPackageNamesForSampling() {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        android.content.pm.PackageManagerInternal packageManagerInternal = (android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class);
        java.util.Iterator<java.lang.String> it = packageManagerInternal.getPackageList().getPackageNames().iterator();
        while (it.hasNext()) {
            android.content.pm.PackageInfo packageInfo = packageManagerInternal.getPackageInfo(it.next(), 4096L, android.os.Process.myUid(), this.mContext.getUserId());
            if (isSamplingTarget(packageInfo)) {
                arrayList.add(packageInfo.packageName);
            }
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isSamplingTarget(@android.annotation.Nullable android.content.pm.PackageInfo packageInfo) {
        java.lang.String[] strArr;
        if (packageInfo == null || (strArr = packageInfo.requestedPermissions) == null) {
            return false;
        }
        for (java.lang.String str : strArr) {
            if (this.mContext.getPackageManager().getPermissionInfo(str, 0).getProtection() == 1) {
                return true;
            }
        }
        return false;
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    private void removeUidsForUserLocked(int i) {
        for (int size = this.mUidStates.size() - 1; size >= 0; size--) {
            if (android.os.UserHandle.getUserId(this.mUidStates.keyAt(size)) == i) {
                this.mUidStates.valueAt(size).clear();
                this.mUidStates.removeAt(size);
            }
        }
    }

    private void checkSystemUid(java.lang.String str) {
        if (android.os.Binder.getCallingUid() != 1000) {
            throw new java.lang.SecurityException(str + " must by called by the system");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static int resolveUid(java.lang.String str) {
        char c;
        if (str == null) {
            return -1;
        }
        switch (str.hashCode()) {
            case -1336564963:
                if (str.equals("dumpstate")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case -31178072:
                if (str.equals("cameraserver")) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case 3506402:
                if (str.equals("root")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 103772132:
                if (str.equals("media")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 109403696:
                if (str.equals("shell")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 1344606873:
                if (str.equals("audioserver")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
        }
        return -1;
    }

    /* JADX WARN: Removed duplicated region for block: B:5:0x0014  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0017 A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static java.lang.String[] getPackagesForUid(int i) {
        java.lang.String[] packagesForUid;
        if (android.app.AppGlobals.getPackageManager() != null) {
            try {
                packagesForUid = android.app.AppGlobals.getPackageManager().getPackagesForUid(i);
            } catch (android.os.RemoteException e) {
            }
            if (packagesForUid != null) {
                return libcore.util.EmptyArray.STRING;
            }
            return packagesForUid;
        }
        packagesForUid = null;
        if (packagesForUid != null) {
        }
    }

    @android.annotation.NonNull
    private java.lang.String getPersistentId(int i) {
        if (i == 0 || this.mVirtualDeviceManagerInternal == null) {
            return "default:0";
        }
        java.lang.String persistentIdForDevice = this.mVirtualDeviceManagerInternal.getPersistentIdForDevice(i);
        if (persistentIdForDevice == null) {
            persistentIdForDevice = this.mKnownDeviceIds.get(i);
        }
        if (persistentIdForDevice != null) {
            return persistentIdForDevice;
        }
        throw new java.lang.IllegalStateException("Requested persistentId for invalid virtualDeviceId: " + i);
    }

    private final class ClientUserRestrictionState implements android.os.IBinder.DeathRecipient {
        private final android.os.IBinder token;

        ClientUserRestrictionState(android.os.IBinder iBinder) throws android.os.RemoteException {
            iBinder.linkToDeath(this, 0);
            this.token = iBinder;
        }

        public boolean setRestriction(int i, boolean z, android.os.PackageTagsList packageTagsList, int i2) {
            return com.android.server.appop.AppOpsService.this.mAppOpsRestrictions.setUserRestriction(this.token, i2, i, z, packageTagsList);
        }

        public boolean hasRestriction(int i, java.lang.String str, java.lang.String str2, int i2, boolean z) {
            return com.android.server.appop.AppOpsService.this.mAppOpsRestrictions.getUserRestriction(this.token, i2, i, str, str2, z);
        }

        public void removeUser(int i) {
            com.android.server.appop.AppOpsService.this.mAppOpsRestrictions.clearUserRestrictions(this.token, java.lang.Integer.valueOf(i));
        }

        public boolean isDefault() {
            return !com.android.server.appop.AppOpsService.this.mAppOpsRestrictions.hasUserRestrictions(this.token);
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            synchronized (com.android.server.appop.AppOpsService.this) {
                com.android.server.appop.AppOpsService.this.mAppOpsRestrictions.clearUserRestrictions(this.token);
                com.android.server.appop.AppOpsService.this.mOpUserRestrictions.remove(this.token);
                destroy();
            }
        }

        public void destroy() {
            this.token.unlinkToDeath(this, 0);
        }
    }

    private final class ClientGlobalRestrictionState implements android.os.IBinder.DeathRecipient {
        final android.os.IBinder mToken;

        ClientGlobalRestrictionState(android.os.IBinder iBinder) throws android.os.RemoteException {
            iBinder.linkToDeath(this, 0);
            this.mToken = iBinder;
        }

        boolean setRestriction(int i, boolean z) {
            return com.android.server.appop.AppOpsService.this.mAppOpsRestrictions.setGlobalRestriction(this.mToken, i, z);
        }

        boolean hasRestriction(int i) {
            return com.android.server.appop.AppOpsService.this.mAppOpsRestrictions.getGlobalRestriction(this.mToken, i);
        }

        boolean isDefault() {
            return !com.android.server.appop.AppOpsService.this.mAppOpsRestrictions.hasGlobalRestrictions(this.mToken);
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            com.android.server.appop.AppOpsService.this.mAppOpsRestrictions.clearGlobalRestrictions(this.mToken);
            com.android.server.appop.AppOpsService.this.mOpGlobalRestrictions.remove(this.mToken);
            destroy();
        }

        void destroy() {
            this.mToken.unlinkToDeath(this, 0);
        }
    }

    private final class AppOpsManagerLocalImpl implements com.android.server.appop.AppOpsManagerLocal {
        private AppOpsManagerLocalImpl() {
        }

        @Override // com.android.server.appop.AppOpsManagerLocal
        public boolean isUidInForeground(int i) {
            boolean isUidInForeground;
            synchronized (com.android.server.appop.AppOpsService.this) {
                isUidInForeground = com.android.server.appop.AppOpsService.this.mUidStateTracker.isUidInForeground(i);
            }
            return isUidInForeground;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    final class AppOpsManagerInternalImpl extends android.app.AppOpsManagerInternal {
        private AppOpsManagerInternalImpl() {
        }

        public void setDeviceAndProfileOwners(android.util.SparseIntArray sparseIntArray) {
            synchronized (com.android.server.appop.AppOpsService.this) {
                com.android.server.appop.AppOpsService.this.mProfileOwners = sparseIntArray;
            }
        }

        public void updateAppWidgetVisibility(android.util.SparseArray<java.lang.String> sparseArray, boolean z) {
            com.android.server.appop.AppOpsService.this.updateAppWidgetVisibility(sparseArray, z);
        }

        public void setUidModeFromPermissionPolicy(int i, int i2, int i3, @android.annotation.Nullable com.android.internal.app.IAppOpsCallback iAppOpsCallback) {
            com.android.server.appop.AppOpsService.this.setUidMode(i, i2, i3, iAppOpsCallback);
        }

        public void setModeFromPermissionPolicy(int i, int i2, @android.annotation.NonNull java.lang.String str, int i3, @android.annotation.Nullable com.android.internal.app.IAppOpsCallback iAppOpsCallback) {
            com.android.server.appop.AppOpsService.this.setMode(i, i2, str, i3, iAppOpsCallback);
        }

        public void setGlobalRestriction(int i, boolean z, android.os.IBinder iBinder) {
            if (android.os.Binder.getCallingPid() != android.os.Process.myPid()) {
                throw new java.lang.SecurityException("Only the system can set global restrictions");
            }
            synchronized (com.android.server.appop.AppOpsService.this) {
                try {
                    com.android.server.appop.AppOpsService.ClientGlobalRestrictionState clientGlobalRestrictionState = (com.android.server.appop.AppOpsService.ClientGlobalRestrictionState) com.android.server.appop.AppOpsService.this.mOpGlobalRestrictions.get(iBinder);
                    if (clientGlobalRestrictionState == null) {
                        try {
                            clientGlobalRestrictionState = com.android.server.appop.AppOpsService.this.new ClientGlobalRestrictionState(iBinder);
                            com.android.server.appop.AppOpsService.this.mOpGlobalRestrictions.put(iBinder, clientGlobalRestrictionState);
                        } catch (android.os.RemoteException e) {
                            return;
                        }
                    }
                    if (clientGlobalRestrictionState.setRestriction(i, z)) {
                        com.android.server.appop.AppOpsService.this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.TriConsumer() { // from class: com.android.server.appop.AppOpsService$AppOpsManagerInternalImpl$$ExternalSyntheticLambda0
                            public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3) {
                                ((com.android.server.appop.AppOpsService) obj).notifyWatchersOnDefaultDevice(((java.lang.Integer) obj2).intValue(), ((java.lang.Integer) obj3).intValue());
                            }
                        }, com.android.server.appop.AppOpsService.this, java.lang.Integer.valueOf(i), -2));
                        com.android.server.appop.AppOpsService.this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.QuadConsumer() { // from class: com.android.server.appop.AppOpsService$AppOpsManagerInternalImpl$$ExternalSyntheticLambda1
                            public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3, java.lang.Object obj4) {
                                ((com.android.server.appop.AppOpsService) obj).updateStartedOpModeForUserForDefaultDevice(((java.lang.Integer) obj2).intValue(), ((java.lang.Boolean) obj3).booleanValue(), ((java.lang.Integer) obj4).intValue());
                            }
                        }, com.android.server.appop.AppOpsService.this, java.lang.Integer.valueOf(i), java.lang.Boolean.valueOf(z), -1));
                    }
                    if (clientGlobalRestrictionState.isDefault()) {
                        com.android.server.appop.AppOpsService.this.mOpGlobalRestrictions.remove(iBinder);
                        clientGlobalRestrictionState.destroy();
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public int getOpRestrictionCount(int i, android.os.UserHandle userHandle, java.lang.String str, java.lang.String str2) {
            int i2;
            synchronized (com.android.server.appop.AppOpsService.this) {
                try {
                    int size = com.android.server.appop.AppOpsService.this.mOpUserRestrictions.size();
                    i2 = 0;
                    for (int i3 = 0; i3 < size; i3++) {
                        if (((com.android.server.appop.AppOpsService.ClientUserRestrictionState) com.android.server.appop.AppOpsService.this.mOpUserRestrictions.valueAt(i3)).hasRestriction(i, str, str2, userHandle.getIdentifier(), false)) {
                            i2++;
                        }
                    }
                    int size2 = com.android.server.appop.AppOpsService.this.mOpGlobalRestrictions.size();
                    for (int i4 = 0; i4 < size2; i4++) {
                        if (((com.android.server.appop.AppOpsService.ClientGlobalRestrictionState) com.android.server.appop.AppOpsService.this.mOpGlobalRestrictions.valueAt(i4)).hasRestriction(i)) {
                            i2++;
                        }
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            return i2;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void writeNoteOps() {
        int i;
        synchronized (this) {
            this.mWriteNoteOpsScheduled = false;
        }
        synchronized (this.mNoteOpCallerStacktracesFile) {
            try {
                java.io.FileWriter fileWriter = new java.io.FileWriter(this.mNoteOpCallerStacktracesFile);
                try {
                    int size = this.mNoteOpCallerStacktraces.size();
                    for (i = 0; i < size; i++) {
                        fileWriter.write(this.mNoteOpCallerStacktraces.valueAt(i).asJson());
                        fileWriter.write(",");
                    }
                    fileWriter.close();
                } catch (java.lang.Throwable th) {
                    try {
                        fileWriter.close();
                    } catch (java.lang.Throwable th2) {
                        th.addSuppressed(th2);
                    }
                    throw th;
                }
            } catch (java.io.IOException e) {
                android.util.Slog.w(TAG, "Failed to load opsValidation file for FileWriter", e);
            }
        }
    }

    @com.android.internal.annotations.Immutable
    static class NoteOpTrace {
        static final java.lang.String OP = "op";
        static final java.lang.String PACKAGENAME = "packageName";
        static final java.lang.String STACKTRACE = "stackTrace";
        static final java.lang.String VERSION = "version";
        private final int mOp;

        @android.annotation.Nullable
        private final java.lang.String mPackageName;

        @android.annotation.NonNull
        private final java.lang.String mStackTrace;
        private final long mVersion;

        static com.android.server.appop.AppOpsService.NoteOpTrace fromJson(java.lang.String str) {
            try {
                org.json.JSONObject jSONObject = new org.json.JSONObject(str.concat("}"));
                return new com.android.server.appop.AppOpsService.NoteOpTrace(jSONObject.getString(STACKTRACE), jSONObject.getInt(OP), jSONObject.getString("packageName"), jSONObject.getLong(VERSION));
            } catch (org.json.JSONException e) {
                android.util.Slog.e(com.android.server.appop.AppOpsService.TAG, "Error constructing NoteOpTrace object JSON trace format incorrect", e);
                return null;
            }
        }

        NoteOpTrace(java.lang.String str, int i, java.lang.String str2, long j) {
            this.mStackTrace = str;
            this.mOp = i;
            this.mPackageName = str2;
            this.mVersion = j;
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            com.android.server.appop.AppOpsService.NoteOpTrace noteOpTrace = (com.android.server.appop.AppOpsService.NoteOpTrace) obj;
            if (this.mOp == noteOpTrace.mOp && this.mVersion == noteOpTrace.mVersion && this.mStackTrace.equals(noteOpTrace.mStackTrace) && java.util.Objects.equals(this.mPackageName, noteOpTrace.mPackageName)) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            return java.util.Objects.hash(this.mStackTrace, java.lang.Integer.valueOf(this.mOp), this.mPackageName, java.lang.Long.valueOf(this.mVersion));
        }

        public java.lang.String asJson() {
            return "{\"stackTrace\":\"" + this.mStackTrace.replace("\n", "\\n") + "\",\"" + OP + "\":" + this.mOp + ",\"packageName\":\"" + this.mPackageName + "\",\"" + VERSION + "\":" + this.mVersion + '}';
        }
    }

    public void collectNoteOpCallsForValidation(java.lang.String str, int i, java.lang.String str2, long j) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.Immutable
    final class CheckOpsDelegateDispatcher {

        @android.annotation.Nullable
        private final android.app.AppOpsManagerInternal.CheckOpsDelegate mCheckOpsDelegate;

        @android.annotation.Nullable
        private final android.app.AppOpsManagerInternal.CheckOpsDelegate mPolicy;

        CheckOpsDelegateDispatcher(@android.annotation.Nullable android.app.AppOpsManagerInternal.CheckOpsDelegate checkOpsDelegate, @android.annotation.Nullable android.app.AppOpsManagerInternal.CheckOpsDelegate checkOpsDelegate2) {
            this.mPolicy = checkOpsDelegate;
            this.mCheckOpsDelegate = checkOpsDelegate2;
        }

        @android.annotation.NonNull
        public android.app.AppOpsManagerInternal.CheckOpsDelegate getCheckOpsDelegate() {
            return this.mCheckOpsDelegate;
        }

        public int checkOperation(int i, int i2, java.lang.String str, @android.annotation.Nullable java.lang.String str2, int i3, boolean z) {
            if (this.mPolicy != null) {
                if (this.mCheckOpsDelegate != null) {
                    return this.mPolicy.checkOperation(i, i2, str, str2, i3, z, new com.android.internal.util.function.HexFunction() { // from class: com.android.server.appop.AppOpsService$CheckOpsDelegateDispatcher$$ExternalSyntheticLambda11
                        public final java.lang.Object apply(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3, java.lang.Object obj4, java.lang.Object obj5, java.lang.Object obj6) {
                            int checkDelegateOperationImpl;
                            checkDelegateOperationImpl = com.android.server.appop.AppOpsService.CheckOpsDelegateDispatcher.this.checkDelegateOperationImpl(((java.lang.Integer) obj).intValue(), ((java.lang.Integer) obj2).intValue(), (java.lang.String) obj3, (java.lang.String) obj4, ((java.lang.Integer) obj5).intValue(), ((java.lang.Boolean) obj6).booleanValue());
                            return java.lang.Integer.valueOf(checkDelegateOperationImpl);
                        }
                    });
                }
                return this.mPolicy.checkOperation(i, i2, str, str2, i3, z, new com.android.server.appop.AppOpsService$CheckOpsDelegateDispatcher$$ExternalSyntheticLambda12(com.android.server.appop.AppOpsService.this));
            }
            if (this.mCheckOpsDelegate != null) {
                return checkDelegateOperationImpl(i, i2, str, str2, i3, z);
            }
            return com.android.server.appop.AppOpsService.this.checkOperationImpl(i, i2, str, str2, i3, z);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public int checkDelegateOperationImpl(int i, int i2, java.lang.String str, @android.annotation.Nullable java.lang.String str2, int i3, boolean z) {
            return this.mCheckOpsDelegate.checkOperation(i, i2, str, str2, i3, z, new com.android.server.appop.AppOpsService$CheckOpsDelegateDispatcher$$ExternalSyntheticLambda12(com.android.server.appop.AppOpsService.this));
        }

        public int checkAudioOperation(int i, int i2, int i3, java.lang.String str) {
            if (this.mPolicy != null) {
                if (this.mCheckOpsDelegate != null) {
                    return this.mPolicy.checkAudioOperation(i, i2, i3, str, new com.android.internal.util.function.QuadFunction() { // from class: com.android.server.appop.AppOpsService$CheckOpsDelegateDispatcher$$ExternalSyntheticLambda8
                        public final java.lang.Object apply(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3, java.lang.Object obj4) {
                            int checkDelegateAudioOperationImpl;
                            checkDelegateAudioOperationImpl = com.android.server.appop.AppOpsService.CheckOpsDelegateDispatcher.this.checkDelegateAudioOperationImpl(((java.lang.Integer) obj).intValue(), ((java.lang.Integer) obj2).intValue(), ((java.lang.Integer) obj3).intValue(), (java.lang.String) obj4);
                            return java.lang.Integer.valueOf(checkDelegateAudioOperationImpl);
                        }
                    });
                }
                return this.mPolicy.checkAudioOperation(i, i2, i3, str, new com.android.server.appop.AppOpsService$CheckOpsDelegateDispatcher$$ExternalSyntheticLambda5(com.android.server.appop.AppOpsService.this));
            }
            if (this.mCheckOpsDelegate != null) {
                return checkDelegateAudioOperationImpl(i, i2, i3, str);
            }
            return com.android.server.appop.AppOpsService.this.checkAudioOperationImpl(i, i2, i3, str);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public int checkDelegateAudioOperationImpl(int i, int i2, int i3, java.lang.String str) {
            return this.mCheckOpsDelegate.checkAudioOperation(i, i2, i3, str, new com.android.server.appop.AppOpsService$CheckOpsDelegateDispatcher$$ExternalSyntheticLambda5(com.android.server.appop.AppOpsService.this));
        }

        public android.app.SyncNotedAppOp noteOperation(int i, int i2, java.lang.String str, java.lang.String str2, int i3, boolean z, java.lang.String str3, boolean z2) {
            if (this.mPolicy != null) {
                if (this.mCheckOpsDelegate == null) {
                    return this.mPolicy.noteOperation(i, i2, str, str2, i3, z, str3, z2, new com.android.server.appop.AppOpsService$CheckOpsDelegateDispatcher$$ExternalSyntheticLambda0(com.android.server.appop.AppOpsService.this));
                }
                return this.mPolicy.noteOperation(i, i2, str, str2, i3, z, str3, z2, new com.android.internal.util.function.OctFunction() { // from class: com.android.server.appop.AppOpsService$CheckOpsDelegateDispatcher$$ExternalSyntheticLambda2
                    public final java.lang.Object apply(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3, java.lang.Object obj4, java.lang.Object obj5, java.lang.Object obj6, java.lang.Object obj7, java.lang.Object obj8) {
                        android.app.SyncNotedAppOp noteDelegateOperationImpl;
                        noteDelegateOperationImpl = com.android.server.appop.AppOpsService.CheckOpsDelegateDispatcher.this.noteDelegateOperationImpl(((java.lang.Integer) obj).intValue(), ((java.lang.Integer) obj2).intValue(), (java.lang.String) obj3, (java.lang.String) obj4, ((java.lang.Integer) obj5).intValue(), ((java.lang.Boolean) obj6).booleanValue(), (java.lang.String) obj7, ((java.lang.Boolean) obj8).booleanValue());
                        return noteDelegateOperationImpl;
                    }
                });
            }
            if (this.mCheckOpsDelegate != null) {
                return noteDelegateOperationImpl(i, i2, str, str2, i3, z, str3, z2);
            }
            return com.android.server.appop.AppOpsService.this.noteOperationImpl(i, i2, str, str2, i3, z, str3, z2);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public android.app.SyncNotedAppOp noteDelegateOperationImpl(int i, int i2, @android.annotation.Nullable java.lang.String str, @android.annotation.Nullable java.lang.String str2, int i3, boolean z, @android.annotation.Nullable java.lang.String str3, boolean z2) {
            return this.mCheckOpsDelegate.noteOperation(i, i2, str, str2, i3, z, str3, z2, new com.android.server.appop.AppOpsService$CheckOpsDelegateDispatcher$$ExternalSyntheticLambda0(com.android.server.appop.AppOpsService.this));
        }

        public android.app.SyncNotedAppOp noteProxyOperation(int i, android.content.AttributionSource attributionSource, boolean z, @android.annotation.Nullable java.lang.String str, boolean z2, boolean z3) {
            if (this.mPolicy != null) {
                if (this.mCheckOpsDelegate != null) {
                    return this.mPolicy.noteProxyOperation(i, attributionSource, z, str, z2, z3, new com.android.internal.util.function.HexFunction() { // from class: com.android.server.appop.AppOpsService$CheckOpsDelegateDispatcher$$ExternalSyntheticLambda14
                        public final java.lang.Object apply(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3, java.lang.Object obj4, java.lang.Object obj5, java.lang.Object obj6) {
                            android.app.SyncNotedAppOp noteDelegateProxyOperationImpl;
                            noteDelegateProxyOperationImpl = com.android.server.appop.AppOpsService.CheckOpsDelegateDispatcher.this.noteDelegateProxyOperationImpl(((java.lang.Integer) obj).intValue(), (android.content.AttributionSource) obj2, ((java.lang.Boolean) obj3).booleanValue(), (java.lang.String) obj4, ((java.lang.Boolean) obj5).booleanValue(), ((java.lang.Boolean) obj6).booleanValue());
                            return noteDelegateProxyOperationImpl;
                        }
                    });
                }
                return this.mPolicy.noteProxyOperation(i, attributionSource, z, str, z2, z3, new com.android.server.appop.AppOpsService$CheckOpsDelegateDispatcher$$ExternalSyntheticLambda1(com.android.server.appop.AppOpsService.this));
            }
            if (this.mCheckOpsDelegate != null) {
                return noteDelegateProxyOperationImpl(i, attributionSource, z, str, z2, z3);
            }
            return com.android.server.appop.AppOpsService.this.noteProxyOperationImpl(i, attributionSource, z, str, z2, z3);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public android.app.SyncNotedAppOp noteDelegateProxyOperationImpl(int i, @android.annotation.NonNull android.content.AttributionSource attributionSource, boolean z, @android.annotation.Nullable java.lang.String str, boolean z2, boolean z3) {
            return this.mCheckOpsDelegate.noteProxyOperation(i, attributionSource, z, str, z2, z3, new com.android.server.appop.AppOpsService$CheckOpsDelegateDispatcher$$ExternalSyntheticLambda1(com.android.server.appop.AppOpsService.this));
        }

        public android.app.SyncNotedAppOp startOperation(android.os.IBinder iBinder, int i, int i2, @android.annotation.Nullable java.lang.String str, @android.annotation.NonNull java.lang.String str2, int i3, boolean z, boolean z2, @android.annotation.Nullable java.lang.String str3, boolean z3, int i4, int i5) {
            if (this.mPolicy != null) {
                return this.mCheckOpsDelegate != null ? this.mPolicy.startOperation(iBinder, i, i2, str, str2, i3, z, z2, str3, z3, i4, i5, new com.android.internal.util.function.DodecFunction() { // from class: com.android.server.appop.AppOpsService$CheckOpsDelegateDispatcher$$ExternalSyntheticLambda9
                    public final java.lang.Object apply(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3, java.lang.Object obj4, java.lang.Object obj5, java.lang.Object obj6, java.lang.Object obj7, java.lang.Object obj8, java.lang.Object obj9, java.lang.Object obj10, java.lang.Object obj11, java.lang.Object obj12) {
                        android.app.SyncNotedAppOp startDelegateOperationImpl;
                        startDelegateOperationImpl = com.android.server.appop.AppOpsService.CheckOpsDelegateDispatcher.this.startDelegateOperationImpl((android.os.IBinder) obj, ((java.lang.Integer) obj2).intValue(), ((java.lang.Integer) obj3).intValue(), (java.lang.String) obj4, (java.lang.String) obj5, ((java.lang.Integer) obj6).intValue(), ((java.lang.Boolean) obj7).booleanValue(), ((java.lang.Boolean) obj8).booleanValue(), (java.lang.String) obj9, ((java.lang.Boolean) obj10).booleanValue(), ((java.lang.Integer) obj11).intValue(), ((java.lang.Integer) obj12).intValue());
                        return startDelegateOperationImpl;
                    }
                }) : this.mPolicy.startOperation(iBinder, i, i2, str, str2, i3, z, z2, str3, z3, i4, i5, new com.android.server.appop.AppOpsService$CheckOpsDelegateDispatcher$$ExternalSyntheticLambda10(com.android.server.appop.AppOpsService.this));
            }
            if (this.mCheckOpsDelegate != null) {
                return startDelegateOperationImpl(iBinder, i, i2, str, str2, i3, z, z2, str3, z3, i4, i5);
            }
            return com.android.server.appop.AppOpsService.this.startOperationImpl(iBinder, i, i2, str, str2, i3, z, z2, str3, z3, i4, i5);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public android.app.SyncNotedAppOp startDelegateOperationImpl(android.os.IBinder iBinder, int i, int i2, @android.annotation.Nullable java.lang.String str, @android.annotation.Nullable java.lang.String str2, int i3, boolean z, boolean z2, java.lang.String str3, boolean z3, int i4, int i5) {
            return this.mCheckOpsDelegate.startOperation(iBinder, i, i2, str, str2, i3, z, z2, str3, z3, i4, i5, new com.android.server.appop.AppOpsService$CheckOpsDelegateDispatcher$$ExternalSyntheticLambda10(com.android.server.appop.AppOpsService.this));
        }

        public android.app.SyncNotedAppOp startProxyOperation(@android.annotation.NonNull android.os.IBinder iBinder, int i, @android.annotation.NonNull android.content.AttributionSource attributionSource, boolean z, boolean z2, java.lang.String str, boolean z3, boolean z4, int i2, int i3, int i4) {
            if (this.mPolicy != null) {
                if (this.mCheckOpsDelegate == null) {
                    return this.mPolicy.startProxyOperation(iBinder, i, attributionSource, z, z2, str, z3, z4, i2, i3, i4, new com.android.server.appop.AppOpsService$CheckOpsDelegateDispatcher$$ExternalSyntheticLambda7(com.android.server.appop.AppOpsService.this));
                }
                return this.mPolicy.startProxyOperation(iBinder, i, attributionSource, z, z2, str, z3, z4, i2, i3, i4, new com.android.internal.util.function.UndecFunction() { // from class: com.android.server.appop.AppOpsService$CheckOpsDelegateDispatcher$$ExternalSyntheticLambda6
                    public final java.lang.Object apply(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3, java.lang.Object obj4, java.lang.Object obj5, java.lang.Object obj6, java.lang.Object obj7, java.lang.Object obj8, java.lang.Object obj9, java.lang.Object obj10, java.lang.Object obj11) {
                        android.app.SyncNotedAppOp startDelegateProxyOperationImpl;
                        startDelegateProxyOperationImpl = com.android.server.appop.AppOpsService.CheckOpsDelegateDispatcher.this.startDelegateProxyOperationImpl((android.os.IBinder) obj, ((java.lang.Integer) obj2).intValue(), (android.content.AttributionSource) obj3, ((java.lang.Boolean) obj4).booleanValue(), ((java.lang.Boolean) obj5).booleanValue(), (java.lang.String) obj6, ((java.lang.Boolean) obj7).booleanValue(), ((java.lang.Boolean) obj8).booleanValue(), ((java.lang.Integer) obj9).intValue(), ((java.lang.Integer) obj10).intValue(), ((java.lang.Integer) obj11).intValue());
                        return startDelegateProxyOperationImpl;
                    }
                });
            }
            if (this.mCheckOpsDelegate != null) {
                return startDelegateProxyOperationImpl(iBinder, i, attributionSource, z, z2, str, z3, z4, i2, i3, i4);
            }
            return com.android.server.appop.AppOpsService.this.startProxyOperationImpl(iBinder, i, attributionSource, z, z2, str, z3, z4, i2, i3, i4);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public android.app.SyncNotedAppOp startDelegateProxyOperationImpl(@android.annotation.NonNull android.os.IBinder iBinder, int i, @android.annotation.NonNull android.content.AttributionSource attributionSource, boolean z, boolean z2, java.lang.String str, boolean z3, boolean z4, int i2, int i3, int i4) {
            return this.mCheckOpsDelegate.startProxyOperation(iBinder, i, attributionSource, z, z2, str, z3, z4, i2, i3, i4, new com.android.server.appop.AppOpsService$CheckOpsDelegateDispatcher$$ExternalSyntheticLambda7(com.android.server.appop.AppOpsService.this));
        }

        public void finishOperation(android.os.IBinder iBinder, int i, int i2, java.lang.String str, java.lang.String str2, int i3) {
            if (this.mPolicy != null) {
                if (this.mCheckOpsDelegate != null) {
                    this.mPolicy.finishOperation(iBinder, i, i2, str, str2, i3, new com.android.internal.util.function.HexConsumer() { // from class: com.android.server.appop.AppOpsService$CheckOpsDelegateDispatcher$$ExternalSyntheticLambda13
                        public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3, java.lang.Object obj4, java.lang.Object obj5, java.lang.Object obj6) {
                            com.android.server.appop.AppOpsService.CheckOpsDelegateDispatcher.this.finishDelegateOperationImpl((android.os.IBinder) obj, ((java.lang.Integer) obj2).intValue(), ((java.lang.Integer) obj3).intValue(), (java.lang.String) obj4, (java.lang.String) obj5, ((java.lang.Integer) obj6).intValue());
                        }
                    });
                    return;
                } else {
                    this.mPolicy.finishOperation(iBinder, i, i2, str, str2, i3, new com.android.server.appop.AppOpsService$CheckOpsDelegateDispatcher$$ExternalSyntheticLambda4(com.android.server.appop.AppOpsService.this));
                    return;
                }
            }
            if (this.mCheckOpsDelegate != null) {
                finishDelegateOperationImpl(iBinder, i, i2, str, str2, i3);
            } else {
                com.android.server.appop.AppOpsService.this.finishOperationImpl(iBinder, i, i2, str, str2, i3);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void finishDelegateOperationImpl(android.os.IBinder iBinder, int i, int i2, java.lang.String str, java.lang.String str2, int i3) {
            this.mCheckOpsDelegate.finishOperation(iBinder, i, i2, str, str2, i3, new com.android.server.appop.AppOpsService$CheckOpsDelegateDispatcher$$ExternalSyntheticLambda4(com.android.server.appop.AppOpsService.this));
        }

        public void finishProxyOperation(@android.annotation.NonNull android.os.IBinder iBinder, int i, @android.annotation.NonNull android.content.AttributionSource attributionSource, boolean z) {
            if (this.mPolicy != null) {
                if (this.mCheckOpsDelegate == null) {
                    this.mPolicy.finishProxyOperation(iBinder, i, attributionSource, z, new com.android.server.appop.AppOpsService$CheckOpsDelegateDispatcher$$ExternalSyntheticLambda3(com.android.server.appop.AppOpsService.this));
                    return;
                } else {
                    this.mPolicy.finishProxyOperation(iBinder, i, attributionSource, z, new com.android.internal.util.function.QuadFunction() { // from class: com.android.server.appop.AppOpsService$CheckOpsDelegateDispatcher$$ExternalSyntheticLambda15
                        public final java.lang.Object apply(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3, java.lang.Object obj4) {
                            java.lang.Void finishDelegateProxyOperationImpl;
                            finishDelegateProxyOperationImpl = com.android.server.appop.AppOpsService.CheckOpsDelegateDispatcher.this.finishDelegateProxyOperationImpl((android.os.IBinder) obj, ((java.lang.Integer) obj2).intValue(), (android.content.AttributionSource) obj3, ((java.lang.Boolean) obj4).booleanValue());
                            return finishDelegateProxyOperationImpl;
                        }
                    });
                    return;
                }
            }
            if (this.mCheckOpsDelegate != null) {
                finishDelegateProxyOperationImpl(iBinder, i, attributionSource, z);
            } else {
                com.android.server.appop.AppOpsService.this.finishProxyOperationImpl(iBinder, i, attributionSource, z);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public java.lang.Void finishDelegateProxyOperationImpl(@android.annotation.NonNull android.os.IBinder iBinder, int i, @android.annotation.NonNull android.content.AttributionSource attributionSource, boolean z) {
            this.mCheckOpsDelegate.finishProxyOperation(iBinder, i, attributionSource, z, new com.android.server.appop.AppOpsService$CheckOpsDelegateDispatcher$$ExternalSyntheticLambda3(com.android.server.appop.AppOpsService.this));
            return null;
        }
    }
}
