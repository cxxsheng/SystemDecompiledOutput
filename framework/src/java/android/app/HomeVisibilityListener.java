package android.app;

@android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
/* loaded from: classes.dex */
public abstract class HomeVisibilityListener {
    private android.app.ActivityTaskManager mActivityTaskManager;
    private java.util.concurrent.Executor mExecutor;
    boolean mIsHomeActivityVisible;
    private int mMaxScanTasksForHomeVisibility;
    android.app.IProcessObserver.Stub mObserver = new android.app.HomeVisibilityListener.AnonymousClass1();
    private static final java.lang.String TAG = android.app.HomeVisibilityListener.class.getSimpleName();
    private static final boolean DBG = android.util.Log.isLoggable(TAG, 3);

    public abstract void onHomeVisibilityChanged(boolean z);

    void init(android.content.Context context, java.util.concurrent.Executor executor) {
        this.mActivityTaskManager = android.app.ActivityTaskManager.getInstance();
        this.mExecutor = executor;
        this.mMaxScanTasksForHomeVisibility = context.getResources().getInteger(com.android.internal.R.integer.config_maxScanTasksForHomeVisibility);
        this.mIsHomeActivityVisible = isHomeActivityVisible();
    }

    /* renamed from: android.app.HomeVisibilityListener$1, reason: invalid class name */
    class AnonymousClass1 extends android.app.IProcessObserver.Stub {
        AnonymousClass1() {
        }

        @Override // android.app.IProcessObserver
        public void onProcessStarted(int i, int i2, int i3, java.lang.String str, java.lang.String str2) {
        }

        @Override // android.app.IProcessObserver
        public void onForegroundActivitiesChanged(int i, int i2, boolean z) {
            refreshHomeVisibility();
        }

        @Override // android.app.IProcessObserver
        public void onForegroundServicesChanged(int i, int i2, int i3) {
        }

        @Override // android.app.IProcessObserver
        public void onProcessDied(int i, int i2) {
            refreshHomeVisibility();
        }

        private void refreshHomeVisibility() {
            boolean isHomeActivityVisible = android.app.HomeVisibilityListener.this.isHomeActivityVisible();
            if (android.app.HomeVisibilityListener.this.mIsHomeActivityVisible != isHomeActivityVisible) {
                android.app.HomeVisibilityListener.this.mIsHomeActivityVisible = isHomeActivityVisible;
                android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.app.HomeVisibilityListener$1$$ExternalSyntheticLambda0
                    @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                    public final void runOrThrow() {
                        android.app.HomeVisibilityListener.AnonymousClass1.this.lambda$refreshHomeVisibility$1();
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$refreshHomeVisibility$1() throws java.lang.Exception {
            android.app.HomeVisibilityListener.this.mExecutor.execute(new java.lang.Runnable() { // from class: android.app.HomeVisibilityListener$1$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    android.app.HomeVisibilityListener.AnonymousClass1.this.lambda$refreshHomeVisibility$0();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$refreshHomeVisibility$0() {
            android.app.HomeVisibilityListener.this.onHomeVisibilityChanged(android.app.HomeVisibilityListener.this.mIsHomeActivityVisible);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isHomeActivityVisible() {
        java.util.List<android.app.ActivityManager.RunningTaskInfo> tasks = this.mActivityTaskManager.getTasks(this.mMaxScanTasksForHomeVisibility, true, false, 0);
        if (tasks == null || tasks.isEmpty()) {
            return false;
        }
        int size = tasks.size();
        for (int i = 0; i < size; i++) {
            android.app.ActivityManager.RunningTaskInfo runningTaskInfo = tasks.get(i);
            if (DBG) {
                android.util.Log.d(TAG, "Task#" + i + ": activity=" + runningTaskInfo.topActivity + ", visible=" + runningTaskInfo.isVisible() + ", flg=" + java.lang.Integer.toHexString(runningTaskInfo.baseIntent.getFlags()) + ", type=" + runningTaskInfo.getActivityType());
            }
            if (runningTaskInfo.isVisible() && runningTaskInfo.getActivityType() == 2) {
                return true;
            }
        }
        return false;
    }
}
