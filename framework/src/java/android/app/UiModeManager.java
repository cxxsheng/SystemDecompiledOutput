package android.app;

/* loaded from: classes.dex */
public class UiModeManager {

    @android.annotation.SystemApi
    public static final java.lang.String ACTION_ENTER_CAR_MODE_PRIORITIZED = "android.app.action.ENTER_CAR_MODE_PRIORITIZED";

    @android.annotation.SystemApi
    public static final java.lang.String ACTION_EXIT_CAR_MODE_PRIORITIZED = "android.app.action.EXIT_CAR_MODE_PRIORITIZED";

    @android.annotation.SystemApi
    public static final int DEFAULT_PRIORITY = 0;
    public static final int DISABLE_CAR_MODE_ALL_PRIORITIES = 2;
    public static final int DISABLE_CAR_MODE_GO_HOME = 1;
    public static final int ENABLE_CAR_MODE_ALLOW_SLEEP = 2;
    public static final int ENABLE_CAR_MODE_GO_CAR_HOME = 1;

    @android.annotation.SystemApi
    public static final java.lang.String EXTRA_CALLING_PACKAGE = "android.app.extra.CALLING_PACKAGE";

    @android.annotation.SystemApi
    public static final java.lang.String EXTRA_PRIORITY = "android.app.extra.PRIORITY";
    public static final int MODE_ATTENTION_THEME_OVERLAY_DAY = 1002;
    public static final int MODE_ATTENTION_THEME_OVERLAY_NIGHT = 1001;
    public static final int MODE_ATTENTION_THEME_OVERLAY_OFF = 1000;
    public static final int MODE_ATTENTION_THEME_OVERLAY_UNKNOWN = -1;
    public static final int MODE_NIGHT_AUTO = 0;
    public static final int MODE_NIGHT_CUSTOM = 3;

    @android.annotation.SystemApi
    public static final int MODE_NIGHT_CUSTOM_TYPE_BEDTIME = 1;

    @android.annotation.SystemApi
    public static final int MODE_NIGHT_CUSTOM_TYPE_SCHEDULE = 0;

    @android.annotation.SystemApi
    public static final int MODE_NIGHT_CUSTOM_TYPE_UNKNOWN = -1;
    public static final int MODE_NIGHT_NO = 1;
    public static final int MODE_NIGHT_YES = 2;
    private static final java.lang.String NIGHT_MODE_API = "getNightMode";

    @android.annotation.SystemApi
    public static final int PROJECTION_TYPE_ALL = -1;

    @android.annotation.SystemApi
    public static final int PROJECTION_TYPE_AUTOMOTIVE = 1;

    @android.annotation.SystemApi
    public static final int PROJECTION_TYPE_NONE = 0;
    private static final java.lang.String TAG = "UiModeManager";
    private static android.app.UiModeManager.Globals sGlobals;
    private android.content.Context mContext;
    private final java.lang.Object mLock;
    private final android.os.IpcDataCache<java.lang.Void, java.lang.Integer> mNightModeCache;
    private final android.os.IpcDataCache.QueryHandler<java.lang.Void, java.lang.Integer> mNightModeQuery;
    private final android.app.UiModeManager.OnProjectionStateChangedListenerResourceManager mOnProjectionStateChangedListenerResourceManager;
    private final java.util.Map<android.app.UiModeManager.OnProjectionStateChangedListener, android.app.UiModeManager.InnerListener> mProjectionStateListenerMap;
    public static java.lang.String ACTION_ENTER_CAR_MODE = "android.app.action.ENTER_CAR_MODE";
    public static java.lang.String ACTION_EXIT_CAR_MODE = "android.app.action.EXIT_CAR_MODE";
    public static java.lang.String ACTION_ENTER_DESK_MODE = "android.app.action.ENTER_DESK_MODE";
    public static java.lang.String ACTION_EXIT_DESK_MODE = "android.app.action.EXIT_DESK_MODE";

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface AttentionModeThemeOverlayReturnType {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface AttentionModeThemeOverlayType {
    }

    public interface ContrastChangeListener {
        void onContrastChanged(float f);
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface DisableCarMode {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface EnableCarMode {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface NightMode {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface NightModeCustomReturnType {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface NightModeCustomType {
    }

    @android.annotation.SystemApi
    public interface OnProjectionStateChangedListener {
        void onProjectionStateChanged(int i, java.util.Set<java.lang.String> set);
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ProjectionType {
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class Globals extends android.app.IUiModeManagerCallback.Stub {
        private float mContrast;
        private final android.app.IUiModeManager mService;
        private final java.lang.Object mGlobalsLock = new java.lang.Object();
        private final android.util.ArrayMap<android.app.UiModeManager.ContrastChangeListener, java.util.concurrent.Executor> mContrastChangeListeners = new android.util.ArrayMap<>();

        Globals(android.app.IUiModeManager iUiModeManager) {
            this.mContrast = 0.0f;
            this.mService = iUiModeManager;
            try {
                this.mService.addCallback(this);
                this.mContrast = this.mService.getContrast();
            } catch (android.os.RemoteException e) {
                android.util.Log.e(android.app.UiModeManager.TAG, "Setup failed: UiModeManagerService is dead", e);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public float getContrast() {
            float f;
            synchronized (this.mGlobalsLock) {
                f = this.mContrast;
            }
            return f;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addContrastChangeListener(android.app.UiModeManager.ContrastChangeListener contrastChangeListener, java.util.concurrent.Executor executor) {
            synchronized (this.mGlobalsLock) {
                this.mContrastChangeListeners.put(contrastChangeListener, executor);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void removeContrastChangeListener(android.app.UiModeManager.ContrastChangeListener contrastChangeListener) {
            synchronized (this.mGlobalsLock) {
                this.mContrastChangeListeners.remove(contrastChangeListener);
            }
        }

        @Override // android.app.IUiModeManagerCallback
        public void notifyContrastChanged(final float f) {
            synchronized (this.mGlobalsLock) {
                if (java.lang.Math.abs(this.mContrast - f) < 1.0E-10d) {
                    return;
                }
                this.mContrast = f;
                this.mContrastChangeListeners.forEach(new java.util.function.BiConsumer() { // from class: android.app.UiModeManager$Globals$$ExternalSyntheticLambda1
                    @Override // java.util.function.BiConsumer
                    public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                        java.util.concurrent.Executor executor = (java.util.concurrent.Executor) obj2;
                        executor.execute(new java.lang.Runnable() { // from class: android.app.UiModeManager$Globals$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                android.app.UiModeManager.ContrastChangeListener.this.onContrastChanged(r2);
                            }
                        });
                    }
                });
            }
        }
    }

    public static class ContrastUtils {
        public static final float CONTRAST_DEFAULT_VALUE = 0.0f;
        public static final int CONTRAST_LEVEL_HIGH = 2;
        public static final int CONTRAST_LEVEL_MEDIUM = 1;
        public static final int CONTRAST_LEVEL_STANDARD = 0;
        private static final float CONTRAST_MAX_VALUE = 1.0f;
        private static final float CONTRAST_MIN_VALUE = -1.0f;

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        public @interface ContrastLevel {
        }

        private static java.util.stream.Stream<java.lang.Integer> allContrastLevels() {
            return java.util.stream.Stream.of((java.lang.Object[]) new java.lang.Integer[]{0, 1, 2});
        }

        public static int toContrastLevel(final float f) {
            if (f < -1.0f || f > 1.0f) {
                throw new java.lang.IllegalArgumentException("contrast values should be in [-1, 1]");
            }
            return allContrastLevels().min(java.util.Comparator.comparingDouble(new java.util.function.ToDoubleFunction() { // from class: android.app.UiModeManager$ContrastUtils$$ExternalSyntheticLambda1
                /*  JADX ERROR: JadxRuntimeException in pass: ModVisitor
                    jadx.core.utils.exceptions.JadxRuntimeException: Can't remove SSA var: r0v1 double, still in use, count: 1, list:
                      (r0v1 double) from 0x0008: RETURN (r0v1 double)
                    	at jadx.core.utils.InsnRemover.removeSsaVar(InsnRemover.java:162)
                    	at jadx.core.utils.InsnRemover.unbindResult(InsnRemover.java:127)
                    	at jadx.core.utils.InsnRemover.unbindInsn(InsnRemover.java:91)
                    	at jadx.core.utils.InsnRemover.addAndUnbind(InsnRemover.java:57)
                    	at jadx.core.dex.visitors.ModVisitor.removeStep(ModVisitor.java:452)
                    	at jadx.core.dex.visitors.ModVisitor.visit(ModVisitor.java:96)
                    */
                @Override // java.util.function.ToDoubleFunction
                public final double applyAsDouble(java.lang.Object r3) {
                    /*
                        r2 = this;
                        float r0 = r1
                        java.lang.Integer r3 = (java.lang.Integer) r3
                        double r0 = android.app.UiModeManager.ContrastUtils.lambda$toContrastLevel$0(r0, r3)
                        return r0
                    */
                    throw new UnsupportedOperationException("Method not decompiled: android.app.UiModeManager$ContrastUtils$$ExternalSyntheticLambda1.applyAsDouble(java.lang.Object):double");
                }
            })).orElseThrow().intValue();
        }

        public static float fromContrastLevel(final int i) {
            if (allContrastLevels().noneMatch(new java.util.function.Predicate() { // from class: android.app.UiModeManager$ContrastUtils$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    return android.app.UiModeManager.ContrastUtils.lambda$fromContrastLevel$1(i, (java.lang.Integer) obj);
                }
            })) {
                throw new java.lang.IllegalArgumentException("unrecognized contrast level: " + i);
            }
            return i / 2.0f;
        }

        static /* synthetic */ boolean lambda$fromContrastLevel$1(int i, java.lang.Integer num) {
            return num.intValue() == i;
        }
    }

    UiModeManager() throws android.os.ServiceManager.ServiceNotFoundException {
        this(null);
    }

    UiModeManager(android.content.Context context) throws android.os.ServiceManager.ServiceNotFoundException {
        this.mLock = new java.lang.Object();
        this.mProjectionStateListenerMap = new android.util.ArrayMap();
        this.mOnProjectionStateChangedListenerResourceManager = new android.app.UiModeManager.OnProjectionStateChangedListenerResourceManager();
        this.mNightModeQuery = new android.os.IpcDataCache.QueryHandler<java.lang.Void, java.lang.Integer>() { // from class: android.app.UiModeManager.1
            @Override // android.os.IpcDataCache.QueryHandler, android.app.PropertyInvalidatedCache.QueryHandler
            public java.lang.Integer apply(java.lang.Void r1) {
                return android.app.UiModeManager.this.getNightModeFromServer();
            }
        };
        this.mNightModeCache = new android.os.IpcDataCache<>(1, "system_server", NIGHT_MODE_API, "NightModeCache", this.mNightModeQuery);
        android.app.IUiModeManager asInterface = android.app.IUiModeManager.Stub.asInterface(android.os.ServiceManager.getServiceOrThrow(android.content.Context.UI_MODE_SERVICE));
        this.mContext = context;
        if (asInterface == null) {
            return;
        }
        synchronized (this.mLock) {
            if (sGlobals == null) {
                sGlobals = new android.app.UiModeManager.Globals(asInterface);
            }
        }
    }

    public void enableCarMode(int i) {
        enableCarMode(0, i);
    }

    @android.annotation.SystemApi
    public void enableCarMode(int i, int i2) {
        if (sGlobals != null) {
            try {
                sGlobals.mService.enableCarMode(i2, i, this.mContext == null ? null : this.mContext.getOpPackageName());
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void disableCarMode(int i) {
        if (sGlobals != null) {
            try {
                sGlobals.mService.disableCarModeByCallingPackage(i, this.mContext == null ? null : this.mContext.getOpPackageName());
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public int getCurrentModeType() {
        if (sGlobals != null) {
            try {
                return sGlobals.mService.getCurrentModeType();
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return 1;
    }

    public void setNightMode(int i) {
        if (sGlobals != null) {
            try {
                sGlobals.mService.setNightMode(i);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    @android.annotation.SystemApi
    public void setNightModeCustomType(int i) {
        if (sGlobals != null) {
            try {
                sGlobals.mService.setNightModeCustomType(i);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    @android.annotation.SystemApi
    public int getNightModeCustomType() {
        if (sGlobals != null) {
            try {
                return sGlobals.mService.getNightModeCustomType();
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return -1;
    }

    public void setAttentionModeThemeOverlay(int i) {
        if (sGlobals != null) {
            try {
                sGlobals.mService.setAttentionModeThemeOverlay(i);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public int getAttentionModeThemeOverlay() {
        if (sGlobals != null) {
            try {
                return sGlobals.mService.getAttentionModeThemeOverlay();
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return -1;
    }

    public void setApplicationNightMode(int i) {
        if (sGlobals != null) {
            try {
                sGlobals.mService.setApplicationNightMode(i);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public java.lang.Integer getNightModeFromServer() {
        try {
            if (sGlobals != null) {
                return java.lang.Integer.valueOf(sGlobals.mService.getNightMode());
            }
            return -1;
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static void invalidateNightModeCache() {
        android.os.IpcDataCache.invalidateCache("system_server", NIGHT_MODE_API);
    }

    public int getNightMode() {
        if (android.app.Flags.enableNightModeBinderCache()) {
            return this.mNightModeCache.query(null).intValue();
        }
        return getNightModeFromServer().intValue();
    }

    public boolean isUiModeLocked() {
        if (sGlobals != null) {
            try {
                return sGlobals.mService.isUiModeLocked();
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return true;
    }

    public boolean isNightModeLocked() {
        if (sGlobals != null) {
            try {
                return sGlobals.mService.isNightModeLocked();
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return true;
    }

    @android.annotation.SystemApi
    public boolean setNightModeActivatedForCustomMode(int i, boolean z) {
        if (sGlobals != null) {
            try {
                return sGlobals.mService.setNightModeActivatedForCustomMode(i, z);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return false;
    }

    public boolean setNightModeActivated(boolean z) {
        if (sGlobals != null) {
            try {
                return sGlobals.mService.setNightModeActivated(z);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return false;
    }

    public java.time.LocalTime getCustomNightModeStart() {
        if (sGlobals != null) {
            try {
                return java.time.LocalTime.ofNanoOfDay(sGlobals.mService.getCustomNightModeStart() * 1000);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return java.time.LocalTime.MIDNIGHT;
    }

    public void setCustomNightModeStart(java.time.LocalTime localTime) {
        if (sGlobals != null) {
            try {
                sGlobals.mService.setCustomNightModeStart(localTime.toNanoOfDay() / 1000);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public java.time.LocalTime getCustomNightModeEnd() {
        if (sGlobals != null) {
            try {
                return java.time.LocalTime.ofNanoOfDay(sGlobals.mService.getCustomNightModeEnd() * 1000);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return java.time.LocalTime.MIDNIGHT;
    }

    public void setCustomNightModeEnd(java.time.LocalTime localTime) {
        if (sGlobals != null) {
            try {
                sGlobals.mService.setCustomNightModeEnd(localTime.toNanoOfDay() / 1000);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    @android.annotation.SystemApi
    public boolean requestProjection(int i) {
        if (sGlobals != null) {
            try {
                return sGlobals.mService.requestProjection(new android.os.Binder(), i, this.mContext.getOpPackageName());
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return false;
    }

    @android.annotation.SystemApi
    public boolean releaseProjection(int i) {
        if (sGlobals != null) {
            try {
                return sGlobals.mService.releaseProjection(i, this.mContext.getOpPackageName());
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return false;
    }

    @android.annotation.SystemApi
    public java.util.Set<java.lang.String> getProjectingPackages(int i) {
        if (sGlobals != null) {
            try {
                return new android.util.ArraySet(sGlobals.mService.getProjectingPackages(i));
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return java.util.Set.of();
    }

    @android.annotation.SystemApi
    public int getActiveProjectionTypes() {
        if (sGlobals != null) {
            try {
                return sGlobals.mService.getActiveProjectionTypes();
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return 0;
    }

    @android.annotation.SystemApi
    public void addOnProjectionStateChangedListener(int i, java.util.concurrent.Executor executor, android.app.UiModeManager.OnProjectionStateChangedListener onProjectionStateChangedListener) {
        synchronized (this.mLock) {
            if (this.mProjectionStateListenerMap.containsKey(onProjectionStateChangedListener)) {
                android.util.Slog.i(TAG, "Attempted to add listener that was already added.");
                return;
            }
            if (sGlobals != null) {
                android.app.UiModeManager.InnerListener innerListener = new android.app.UiModeManager.InnerListener(executor, onProjectionStateChangedListener, this.mOnProjectionStateChangedListenerResourceManager);
                try {
                    sGlobals.mService.addOnProjectionStateChangedListener(innerListener, i);
                    this.mProjectionStateListenerMap.put(onProjectionStateChangedListener, innerListener);
                } catch (android.os.RemoteException e) {
                    this.mOnProjectionStateChangedListenerResourceManager.remove(innerListener);
                    throw e.rethrowFromSystemServer();
                }
            }
        }
    }

    @android.annotation.SystemApi
    public void removeOnProjectionStateChangedListener(android.app.UiModeManager.OnProjectionStateChangedListener onProjectionStateChangedListener) {
        synchronized (this.mLock) {
            android.app.UiModeManager.InnerListener innerListener = this.mProjectionStateListenerMap.get(onProjectionStateChangedListener);
            if (innerListener == null) {
                android.util.Slog.i(TAG, "Attempted to remove listener that was not added.");
                return;
            }
            if (sGlobals != null) {
                try {
                    sGlobals.mService.removeOnProjectionStateChangedListener(innerListener);
                } catch (android.os.RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
            this.mProjectionStateListenerMap.remove(onProjectionStateChangedListener);
            this.mOnProjectionStateChangedListenerResourceManager.remove(innerListener);
        }
    }

    private static class InnerListener extends android.app.IOnProjectionStateChangedListener.Stub {
        private final java.lang.ref.WeakReference<android.app.UiModeManager.OnProjectionStateChangedListenerResourceManager> mResourceManager;

        private InnerListener(java.util.concurrent.Executor executor, android.app.UiModeManager.OnProjectionStateChangedListener onProjectionStateChangedListener, android.app.UiModeManager.OnProjectionStateChangedListenerResourceManager onProjectionStateChangedListenerResourceManager) {
            onProjectionStateChangedListenerResourceManager.put(this, executor, onProjectionStateChangedListener);
            this.mResourceManager = new java.lang.ref.WeakReference<>(onProjectionStateChangedListenerResourceManager);
        }

        @Override // android.app.IOnProjectionStateChangedListener
        public void onProjectionStateChanged(int i, java.util.List<java.lang.String> list) {
            android.app.UiModeManager.OnProjectionStateChangedListenerResourceManager onProjectionStateChangedListenerResourceManager = this.mResourceManager.get();
            if (onProjectionStateChangedListenerResourceManager == null) {
                android.util.Slog.w(android.app.UiModeManager.TAG, "Can't execute onProjectionStateChanged, resource manager is gone.");
                return;
            }
            android.app.UiModeManager.OnProjectionStateChangedListener outerListener = onProjectionStateChangedListenerResourceManager.getOuterListener(this);
            java.util.concurrent.Executor executor = onProjectionStateChangedListenerResourceManager.getExecutor(this);
            if (outerListener == null || executor == null) {
                android.util.Slog.w(android.app.UiModeManager.TAG, "Can't execute onProjectionStatechanged, references are null.");
            } else {
                executor.execute(com.android.internal.util.function.pooled.PooledLambda.obtainRunnable(new com.android.internal.util.function.TriConsumer() { // from class: android.app.UiModeManager$InnerListener$$ExternalSyntheticLambda0
                    @Override // com.android.internal.util.function.TriConsumer
                    public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3) {
                        ((android.app.UiModeManager.OnProjectionStateChangedListener) obj).onProjectionStateChanged(((java.lang.Integer) obj2).intValue(), (android.util.ArraySet) obj3);
                    }
                }, outerListener, java.lang.Integer.valueOf(i), new android.util.ArraySet(list)).recycleOnUse());
            }
        }
    }

    private static class OnProjectionStateChangedListenerResourceManager {
        private final java.util.Map<android.app.UiModeManager.InnerListener, java.util.concurrent.Executor> mExecutorMap;
        private final java.util.Map<android.app.UiModeManager.InnerListener, android.app.UiModeManager.OnProjectionStateChangedListener> mOuterListenerMap;

        private OnProjectionStateChangedListenerResourceManager() {
            this.mOuterListenerMap = new android.util.ArrayMap(1);
            this.mExecutorMap = new android.util.ArrayMap(1);
        }

        void put(android.app.UiModeManager.InnerListener innerListener, java.util.concurrent.Executor executor, android.app.UiModeManager.OnProjectionStateChangedListener onProjectionStateChangedListener) {
            this.mOuterListenerMap.put(innerListener, onProjectionStateChangedListener);
            this.mExecutorMap.put(innerListener, executor);
        }

        void remove(android.app.UiModeManager.InnerListener innerListener) {
            this.mOuterListenerMap.remove(innerListener);
            this.mExecutorMap.remove(innerListener);
        }

        android.app.UiModeManager.OnProjectionStateChangedListener getOuterListener(android.app.UiModeManager.InnerListener innerListener) {
            return this.mOuterListenerMap.get(innerListener);
        }

        java.util.concurrent.Executor getExecutor(android.app.UiModeManager.InnerListener innerListener) {
            return this.mExecutorMap.get(innerListener);
        }
    }

    public float getContrast() {
        return sGlobals.getContrast();
    }

    public void addContrastChangeListener(java.util.concurrent.Executor executor, android.app.UiModeManager.ContrastChangeListener contrastChangeListener) {
        java.util.Objects.requireNonNull(executor);
        java.util.Objects.requireNonNull(contrastChangeListener);
        sGlobals.addContrastChangeListener(contrastChangeListener, executor);
    }

    public void removeContrastChangeListener(android.app.UiModeManager.ContrastChangeListener contrastChangeListener) {
        java.util.Objects.requireNonNull(contrastChangeListener);
        sGlobals.removeContrastChangeListener(contrastChangeListener);
    }
}
