package android.window;

/* loaded from: classes4.dex */
public class DisplayAreaOrganizer extends android.window.WindowOrganizer {
    public static final int FEATURE_DEFAULT_TASK_CONTAINER = 1;
    public static final int FEATURE_FULLSCREEN_MAGNIFICATION = 5;
    public static final int FEATURE_HIDE_DISPLAY_CUTOUT = 6;
    public static final int FEATURE_IME = 8;
    public static final int FEATURE_IME_PLACEHOLDER = 7;
    public static final int FEATURE_ONE_HANDED = 3;
    public static final int FEATURE_ROOT = 0;
    public static final int FEATURE_RUNTIME_TASK_CONTAINER_FIRST = 20002;
    public static final int FEATURE_SYSTEM_FIRST = 0;
    public static final int FEATURE_SYSTEM_LAST = 10000;
    public static final int FEATURE_UNDEFINED = -1;
    public static final int FEATURE_VENDOR_FIRST = 10001;
    public static final int FEATURE_VENDOR_LAST = 20001;
    public static final int FEATURE_WINDOWED_MAGNIFICATION = 4;
    public static final int FEATURE_WINDOW_TOKENS = 2;
    public static final java.lang.String KEY_ROOT_DISPLAY_AREA_ID = "root_display_area_id";
    private final java.util.concurrent.Executor mExecutor;
    private final android.window.IDisplayAreaOrganizer mInterface = new android.window.DisplayAreaOrganizer.AnonymousClass1();

    public DisplayAreaOrganizer(java.util.concurrent.Executor executor) {
        this.mExecutor = executor;
    }

    public java.util.concurrent.Executor getExecutor() {
        return this.mExecutor;
    }

    public java.util.List<android.window.DisplayAreaAppearedInfo> registerOrganizer(int i) {
        try {
            return getController().registerOrganizer(this.mInterface, i).getList();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void unregisterOrganizer() {
        try {
            getController().unregisterOrganizer(this.mInterface);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.window.DisplayAreaAppearedInfo createTaskDisplayArea(int i, int i2, java.lang.String str) {
        try {
            return getController().createTaskDisplayArea(this.mInterface, i, i2, str);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void deleteTaskDisplayArea(android.window.WindowContainerToken windowContainerToken) {
        try {
            getController().deleteTaskDisplayArea(windowContainerToken);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void onDisplayAreaAppeared(android.window.DisplayAreaInfo displayAreaInfo, android.view.SurfaceControl surfaceControl) {
    }

    public void onDisplayAreaVanished(android.window.DisplayAreaInfo displayAreaInfo) {
    }

    public void onDisplayAreaInfoChanged(android.window.DisplayAreaInfo displayAreaInfo) {
    }

    /* renamed from: android.window.DisplayAreaOrganizer$1, reason: invalid class name */
    class AnonymousClass1 extends android.window.IDisplayAreaOrganizer.Stub {
        AnonymousClass1() {
        }

        @Override // android.window.IDisplayAreaOrganizer
        public void onDisplayAreaAppeared(final android.window.DisplayAreaInfo displayAreaInfo, final android.view.SurfaceControl surfaceControl) {
            android.window.DisplayAreaOrganizer.this.mExecutor.execute(new java.lang.Runnable() { // from class: android.window.DisplayAreaOrganizer$1$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    android.window.DisplayAreaOrganizer.AnonymousClass1.this.lambda$onDisplayAreaAppeared$0(displayAreaInfo, surfaceControl);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onDisplayAreaAppeared$0(android.window.DisplayAreaInfo displayAreaInfo, android.view.SurfaceControl surfaceControl) {
            android.window.DisplayAreaOrganizer.this.onDisplayAreaAppeared(displayAreaInfo, surfaceControl);
        }

        @Override // android.window.IDisplayAreaOrganizer
        public void onDisplayAreaVanished(final android.window.DisplayAreaInfo displayAreaInfo) {
            android.window.DisplayAreaOrganizer.this.mExecutor.execute(new java.lang.Runnable() { // from class: android.window.DisplayAreaOrganizer$1$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    android.window.DisplayAreaOrganizer.AnonymousClass1.this.lambda$onDisplayAreaVanished$1(displayAreaInfo);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onDisplayAreaVanished$1(android.window.DisplayAreaInfo displayAreaInfo) {
            android.window.DisplayAreaOrganizer.this.onDisplayAreaVanished(displayAreaInfo);
        }

        @Override // android.window.IDisplayAreaOrganizer
        public void onDisplayAreaInfoChanged(final android.window.DisplayAreaInfo displayAreaInfo) {
            android.window.DisplayAreaOrganizer.this.mExecutor.execute(new java.lang.Runnable() { // from class: android.window.DisplayAreaOrganizer$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.window.DisplayAreaOrganizer.AnonymousClass1.this.lambda$onDisplayAreaInfoChanged$2(displayAreaInfo);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onDisplayAreaInfoChanged$2(android.window.DisplayAreaInfo displayAreaInfo) {
            android.window.DisplayAreaOrganizer.this.onDisplayAreaInfoChanged(displayAreaInfo);
        }
    }

    private android.window.IDisplayAreaOrganizerController getController() {
        try {
            return getWindowOrganizerController().getDisplayAreaOrganizerController();
        } catch (android.os.RemoteException e) {
            return null;
        }
    }
}
