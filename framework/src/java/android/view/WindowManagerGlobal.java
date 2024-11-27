package android.view;

/* loaded from: classes4.dex */
public final class WindowManagerGlobal {
    public static final int ADD_APP_EXITING = -4;
    public static final int ADD_BAD_APP_TOKEN = -1;
    public static final int ADD_BAD_SUBWINDOW_TOKEN = -2;
    public static final int ADD_DUPLICATE_ADD = -5;
    public static final int ADD_FLAG_ALWAYS_CONSUME_SYSTEM_BARS = 4;
    public static final int ADD_FLAG_APP_VISIBLE = 2;
    public static final int ADD_FLAG_IN_TOUCH_MODE = 1;
    public static final int ADD_INVALID_DISPLAY = -9;
    public static final int ADD_INVALID_TYPE = -10;
    public static final int ADD_INVALID_USER = -11;
    public static final int ADD_MULTIPLE_SINGLETON = -7;
    public static final int ADD_NOT_APP_TOKEN = -3;
    public static final int ADD_OKAY = 0;
    public static final int ADD_PERMISSION_DENIED = -8;
    public static final int ADD_STARTING_NOT_NEEDED = -6;
    public static final int RELAYOUT_INSETS_PENDING = 1;
    public static final int RELAYOUT_RES_CANCEL_AND_REDRAW = 16;
    public static final int RELAYOUT_RES_CONSUME_ALWAYS_SYSTEM_BARS = 8;
    public static final int RELAYOUT_RES_FIRST_TIME = 1;
    public static final int RELAYOUT_RES_SURFACE_CHANGED = 2;
    public static final int RELAYOUT_RES_SURFACE_RESIZED = 4;
    private static final java.lang.String TAG = "WindowManager";
    private static android.view.WindowManagerGlobal sDefaultWindowManager;
    private static android.view.IWindowManager sWindowManagerService;
    private static android.view.IWindowSession sWindowSession;
    private java.util.WeakHashMap<android.os.IBinder, android.view.WindowManagerGlobal.ProposedRotationListenerDelegate> mProposedRotationListenerMap;
    private java.lang.Runnable mSystemPropertyUpdater;
    private final java.lang.Object mLock = new java.lang.Object();
    private final java.util.ArrayList<android.view.View> mViews = new java.util.ArrayList<>();
    private final java.util.ArrayList<android.view.ViewRootImpl> mRoots = new java.util.ArrayList<>();
    private final java.util.ArrayList<android.view.WindowManager.LayoutParams> mParams = new java.util.ArrayList<>();
    private final android.util.ArraySet<android.view.View> mDyingViews = new android.util.ArraySet<>();
    private final java.util.ArrayList<android.view.ViewRootImpl> mWindowlessRoots = new java.util.ArrayList<>();
    private final android.view.WindowManagerGlobal.TrustedPresentationListener mTrustedPresentationListener = new android.view.WindowManagerGlobal.TrustedPresentationListener();
    private final android.util.SparseArray<android.view.WindowManagerGlobal.SurfaceControlInputReceiverInfo> mSurfaceControlInputReceivers = new android.util.SparseArray<>();

    private WindowManagerGlobal() {
    }

    public static void initialize() {
        getWindowManagerService();
    }

    public static android.view.WindowManagerGlobal getInstance() {
        android.view.WindowManagerGlobal windowManagerGlobal;
        synchronized (android.view.WindowManagerGlobal.class) {
            if (sDefaultWindowManager == null) {
                sDefaultWindowManager = new android.view.WindowManagerGlobal();
            }
            windowManagerGlobal = sDefaultWindowManager;
        }
        return windowManagerGlobal;
    }

    public static void setWindowManagerServiceForSystemProcess(android.view.IWindowManager iWindowManager) {
        sWindowManagerService = iWindowManager;
    }

    public static android.view.IWindowManager getWindowManagerService() {
        android.view.IWindowManager iWindowManager;
        if (sWindowManagerService != null) {
            return sWindowManagerService;
        }
        synchronized (android.view.WindowManagerGlobal.class) {
            if (sWindowManagerService == null) {
                sWindowManagerService = android.view.IWindowManager.Stub.asInterface(android.os.ServiceManager.getService(android.content.Context.WINDOW_SERVICE));
                try {
                    if (sWindowManagerService != null) {
                        android.animation.ValueAnimator.setDurationScale(sWindowManagerService.getCurrentAnimatorScale());
                    }
                } catch (android.os.RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
            iWindowManager = sWindowManagerService;
        }
        return iWindowManager;
    }

    public static android.view.IWindowSession getWindowSession() {
        android.view.IWindowSession iWindowSession;
        synchronized (android.view.WindowManagerGlobal.class) {
            if (sWindowSession == null) {
                try {
                    android.view.inputmethod.InputMethodManager.ensureDefaultInstanceForDefaultDisplayIfNecessary();
                    sWindowSession = getWindowManagerService().openSession(new android.view.IWindowSessionCallback.Stub() { // from class: android.view.WindowManagerGlobal.1
                        @Override // android.view.IWindowSessionCallback
                        public void onAnimatorScaleChanged(float f) {
                            android.animation.ValueAnimator.setDurationScale(f);
                        }
                    });
                } catch (android.os.RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
            iWindowSession = sWindowSession;
        }
        return iWindowSession;
    }

    public static android.view.IWindowSession peekWindowSession() {
        android.view.IWindowSession iWindowSession;
        synchronized (android.view.WindowManagerGlobal.class) {
            iWindowSession = sWindowSession;
        }
        return iWindowSession;
    }

    public java.lang.String[] getViewRootNames() {
        java.lang.String[] strArr;
        synchronized (this.mLock) {
            int size = this.mRoots.size();
            int size2 = this.mWindowlessRoots.size();
            strArr = new java.lang.String[size + size2];
            for (int i = 0; i < size; i++) {
                strArr[i] = getWindowName(this.mRoots.get(i));
            }
            for (int i2 = 0; i2 < size2; i2++) {
                strArr[i2 + size] = getWindowName(this.mWindowlessRoots.get(i2));
            }
        }
        return strArr;
    }

    public java.util.ArrayList<android.view.ViewRootImpl> getRootViews(android.os.IBinder iBinder) {
        boolean z;
        java.util.ArrayList<android.view.ViewRootImpl> arrayList = new java.util.ArrayList<>();
        synchronized (this.mLock) {
            int size = this.mRoots.size();
            for (int i = 0; i < size; i++) {
                android.view.WindowManager.LayoutParams layoutParams = this.mParams.get(i);
                if (layoutParams.token != null) {
                    if (layoutParams.token != iBinder) {
                        if (layoutParams.type >= 1000 && layoutParams.type <= 1999) {
                            for (int i2 = 0; i2 < size; i2++) {
                                android.view.View view = this.mViews.get(i2);
                                android.view.WindowManager.LayoutParams layoutParams2 = this.mParams.get(i2);
                                if (layoutParams.token == view.getWindowToken() && layoutParams2.token == iBinder) {
                                    z = true;
                                    break;
                                }
                            }
                        }
                        z = false;
                        if (!z) {
                        }
                    }
                    arrayList.add(this.mRoots.get(i));
                }
            }
        }
        return arrayList;
    }

    public java.util.ArrayList<android.view.View> getWindowViews() {
        java.util.ArrayList<android.view.View> arrayList;
        synchronized (this.mLock) {
            arrayList = new java.util.ArrayList<>(this.mViews);
        }
        return arrayList;
    }

    public android.view.View getWindowView(android.os.IBinder iBinder) {
        synchronized (this.mLock) {
            int size = this.mViews.size();
            for (int i = 0; i < size; i++) {
                android.view.View view = this.mViews.get(i);
                if (view.getWindowToken() == iBinder) {
                    return view;
                }
            }
            return null;
        }
    }

    public android.view.View getRootView(java.lang.String str) {
        synchronized (this.mLock) {
            for (int size = this.mRoots.size() - 1; size >= 0; size--) {
                android.view.ViewRootImpl viewRootImpl = this.mRoots.get(size);
                if (str.equals(getWindowName(viewRootImpl))) {
                    return viewRootImpl.getView();
                }
            }
            for (int size2 = this.mWindowlessRoots.size() - 1; size2 >= 0; size2--) {
                android.view.ViewRootImpl viewRootImpl2 = this.mWindowlessRoots.get(size2);
                if (str.equals(getWindowName(viewRootImpl2))) {
                    return viewRootImpl2.getView();
                }
            }
            return null;
        }
    }

    public void addView(android.view.View view, android.view.ViewGroup.LayoutParams layoutParams, android.view.Display display, android.view.Window window, int i) {
        android.view.View view2;
        android.view.ViewRootImpl viewRootImpl;
        if (view == null) {
            throw new java.lang.IllegalArgumentException("view must not be null");
        }
        if (display == null) {
            throw new java.lang.IllegalArgumentException("display must not be null");
        }
        if (!(layoutParams instanceof android.view.WindowManager.LayoutParams)) {
            throw new java.lang.IllegalArgumentException("Params must be WindowManager.LayoutParams");
        }
        android.view.WindowManager.LayoutParams layoutParams2 = (android.view.WindowManager.LayoutParams) layoutParams;
        if (window != null) {
            window.adjustLayoutParamsForSubWindow(layoutParams2);
        } else {
            android.content.Context context = view.getContext();
            if (context != null && (context.getApplicationInfo().flags & 536870912) != 0) {
                layoutParams2.flags |= 16777216;
            }
        }
        synchronized (this.mLock) {
            if (this.mSystemPropertyUpdater == null) {
                this.mSystemPropertyUpdater = new java.lang.Runnable() { // from class: android.view.WindowManagerGlobal.2
                    @Override // java.lang.Runnable
                    public void run() {
                        synchronized (android.view.WindowManagerGlobal.this.mLock) {
                            for (int size = android.view.WindowManagerGlobal.this.mRoots.size() - 1; size >= 0; size--) {
                                ((android.view.ViewRootImpl) android.view.WindowManagerGlobal.this.mRoots.get(size)).loadSystemProperties();
                            }
                        }
                    }
                };
                android.os.SystemProperties.addChangeCallback(this.mSystemPropertyUpdater);
            }
            int i2 = 0;
            int findViewLocked = findViewLocked(view, false);
            if (findViewLocked >= 0) {
                if (this.mDyingViews.contains(view)) {
                    this.mRoots.get(findViewLocked).doDie();
                } else {
                    throw new java.lang.IllegalStateException("View " + view + " has already been added to the window manager.");
                }
            }
            android.view.IWindowSession iWindowSession = null;
            if (layoutParams2.type >= 1000 && layoutParams2.type <= 1999) {
                int size = this.mViews.size();
                view2 = null;
                for (int i3 = 0; i3 < size; i3++) {
                    if (this.mRoots.get(i3).mWindow.asBinder() == layoutParams2.token) {
                        view2 = this.mViews.get(i3);
                    }
                }
            } else {
                view2 = null;
            }
            if (layoutParams2.token != null && view2 == null) {
                while (true) {
                    if (i2 >= this.mWindowlessRoots.size()) {
                        break;
                    }
                    android.view.ViewRootImpl viewRootImpl2 = this.mWindowlessRoots.get(i2);
                    if (viewRootImpl2.getWindowToken() != layoutParams2.token) {
                        i2++;
                    } else {
                        iWindowSession = viewRootImpl2.getWindowSession();
                        break;
                    }
                }
            }
            if (iWindowSession == null) {
                viewRootImpl = new android.view.ViewRootImpl(view.getContext(), display);
            } else {
                viewRootImpl = new android.view.ViewRootImpl(view.getContext(), display, iWindowSession, new android.view.WindowlessWindowLayout());
            }
            view.setLayoutParams(layoutParams2);
            this.mViews.add(view);
            this.mRoots.add(viewRootImpl);
            this.mParams.add(layoutParams2);
            try {
                viewRootImpl.setView(view, layoutParams2, view2, i);
            } catch (java.lang.RuntimeException e) {
                if (findViewLocked < 0) {
                    findViewLocked = this.mViews.size() - 1;
                }
                if (findViewLocked >= 0) {
                    removeViewLocked(findViewLocked, true);
                }
                throw e;
            }
        }
    }

    public void updateViewLayout(android.view.View view, android.view.ViewGroup.LayoutParams layoutParams) {
        if (view == null) {
            throw new java.lang.IllegalArgumentException("view must not be null");
        }
        if (!(layoutParams instanceof android.view.WindowManager.LayoutParams)) {
            throw new java.lang.IllegalArgumentException("Params must be WindowManager.LayoutParams");
        }
        android.view.WindowManager.LayoutParams layoutParams2 = (android.view.WindowManager.LayoutParams) layoutParams;
        view.setLayoutParams(layoutParams2);
        synchronized (this.mLock) {
            int findViewLocked = findViewLocked(view, true);
            android.view.ViewRootImpl viewRootImpl = this.mRoots.get(findViewLocked);
            this.mParams.remove(findViewLocked);
            this.mParams.add(findViewLocked, layoutParams2);
            viewRootImpl.setLayoutParams(layoutParams2, false);
        }
    }

    public void removeView(android.view.View view, boolean z) {
        if (view == null) {
            throw new java.lang.IllegalArgumentException("view must not be null");
        }
        synchronized (this.mLock) {
            int findViewLocked = findViewLocked(view, true);
            android.view.View view2 = this.mRoots.get(findViewLocked).getView();
            removeViewLocked(findViewLocked, z);
            if (view2 != view) {
                throw new java.lang.IllegalStateException("Calling with view " + view + " but the ViewAncestor is attached to " + view2);
            }
        }
    }

    public void closeAll(android.os.IBinder iBinder, java.lang.String str, java.lang.String str2) {
        closeAllExceptView(iBinder, null, str, str2);
    }

    public void closeAllExceptView(android.os.IBinder iBinder, android.view.View view, java.lang.String str, java.lang.String str2) {
        synchronized (this.mLock) {
            int size = this.mViews.size();
            for (int i = 0; i < size; i++) {
                if ((view == null || this.mViews.get(i) != view) && (iBinder == null || this.mParams.get(i).token == iBinder)) {
                    android.view.ViewRootImpl viewRootImpl = this.mRoots.get(i);
                    if (str != null) {
                        android.view.WindowLeaked windowLeaked = new android.view.WindowLeaked(str2 + " " + str + " has leaked window " + viewRootImpl.getView() + " that was originally added here");
                        windowLeaked.setStackTrace(viewRootImpl.getLocation().getStackTrace());
                        android.util.Log.e(TAG, "", windowLeaked);
                    }
                    removeViewLocked(i, false);
                }
            }
        }
    }

    private void removeViewLocked(int i, boolean z) {
        android.view.ViewRootImpl viewRootImpl = this.mRoots.get(i);
        android.view.View view = viewRootImpl.getView();
        if (viewRootImpl != null) {
            viewRootImpl.getImeFocusController().onWindowDismissed();
        }
        boolean die = viewRootImpl.die(z);
        if (view != null) {
            view.assignParent(null);
            if (die) {
                this.mDyingViews.add(view);
            }
        }
    }

    void doRemoveView(android.view.ViewRootImpl viewRootImpl) {
        boolean isEmpty;
        synchronized (this.mLock) {
            int indexOf = this.mRoots.indexOf(viewRootImpl);
            if (indexOf >= 0) {
                this.mRoots.remove(indexOf);
                this.mParams.remove(indexOf);
                this.mDyingViews.remove(this.mViews.remove(indexOf));
            }
            isEmpty = this.mRoots.isEmpty();
        }
        if (isEmpty) {
            android.view.InsetsAnimationThread.release();
        }
    }

    private int findViewLocked(android.view.View view, boolean z) {
        int indexOf = this.mViews.indexOf(view);
        if (z && indexOf < 0) {
            throw new java.lang.IllegalArgumentException("View=" + view + " not attached to window manager");
        }
        return indexOf;
    }

    public void trimMemory(int i) {
        android.view.ThreadedRenderer.trimMemory(i);
    }

    public void trimCaches(int i) {
        android.view.ThreadedRenderer.trimCaches(i);
    }

    public void dumpGfxInfo(java.io.FileDescriptor fileDescriptor, java.lang.String[] strArr) {
        com.android.internal.util.FastPrintWriter fastPrintWriter = new com.android.internal.util.FastPrintWriter(new java.io.FileOutputStream(fileDescriptor));
        try {
            synchronized (this.mLock) {
                int size = this.mViews.size();
                fastPrintWriter.println("Profile data in ms:");
                for (int i = 0; i < size; i++) {
                    android.view.ViewRootImpl viewRootImpl = this.mRoots.get(i);
                    fastPrintWriter.printf("\n\t%s (visibility=%d)", getWindowName(viewRootImpl), java.lang.Integer.valueOf(viewRootImpl.getHostVisibility()));
                    android.view.ThreadedRenderer threadedRenderer = viewRootImpl.getView().mAttachInfo.mThreadedRenderer;
                    if (threadedRenderer != null) {
                        threadedRenderer.dumpGfxInfo(fastPrintWriter, fileDescriptor, strArr);
                    }
                }
                fastPrintWriter.println("\nView hierarchy:\n");
                android.view.ViewRootImpl.GfxInfo gfxInfo = new android.view.ViewRootImpl.GfxInfo();
                for (int i2 = 0; i2 < size; i2++) {
                    android.view.ViewRootImpl viewRootImpl2 = this.mRoots.get(i2);
                    android.view.ViewRootImpl.GfxInfo gfxInfo2 = viewRootImpl2.getGfxInfo();
                    gfxInfo.add(gfxInfo2);
                    fastPrintWriter.printf("  %s\n  %d views, %.2f kB of render nodes", getWindowName(viewRootImpl2), java.lang.Integer.valueOf(gfxInfo2.viewCount), java.lang.Float.valueOf(gfxInfo2.renderNodeMemoryUsage / 1024.0f));
                    fastPrintWriter.printf("\n\n", new java.lang.Object[0]);
                }
                fastPrintWriter.printf("\nTotal %-15s: %d\n", "ViewRootImpl", java.lang.Integer.valueOf(size));
                fastPrintWriter.printf("Total %-15s: %d\n", "attached Views", java.lang.Integer.valueOf(gfxInfo.viewCount));
                fastPrintWriter.printf("Total %-15s: %.2f kB (used) / %.2f kB (capacity)\n\n", "RenderNode", java.lang.Float.valueOf(gfxInfo.renderNodeMemoryUsage / 1024.0f), java.lang.Float.valueOf(gfxInfo.renderNodeMemoryAllocated / 1024.0f));
            }
        } finally {
            fastPrintWriter.flush();
        }
    }

    private static java.lang.String getWindowName(android.view.ViewRootImpl viewRootImpl) {
        return ((java.lang.Object) viewRootImpl.mWindowAttributes.getTitle()) + "/" + viewRootImpl.getClass().getName() + '@' + java.lang.Integer.toHexString(viewRootImpl.hashCode());
    }

    public void setStoppedState(android.os.IBinder iBinder, final boolean z) {
        java.util.ArrayList arrayList;
        synchronized (this.mLock) {
            arrayList = null;
            for (int size = this.mViews.size() - 1; size >= 0; size--) {
                if (iBinder == null || this.mParams.get(size).token == iBinder) {
                    android.view.ViewRootImpl viewRootImpl = this.mRoots.get(size);
                    if (viewRootImpl.mThread == java.lang.Thread.currentThread()) {
                        viewRootImpl.setWindowStopped(z);
                    } else {
                        if (arrayList == null) {
                            arrayList = new java.util.ArrayList();
                        }
                        arrayList.add(viewRootImpl);
                    }
                    setStoppedState(viewRootImpl.mAttachInfo.mWindowToken, z);
                }
            }
        }
        if (arrayList != null) {
            for (int size2 = arrayList.size() - 1; size2 >= 0; size2--) {
                final android.view.ViewRootImpl viewRootImpl2 = (android.view.ViewRootImpl) arrayList.get(size2);
                viewRootImpl2.mHandler.runWithScissors(new java.lang.Runnable() { // from class: android.view.WindowManagerGlobal$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.view.ViewRootImpl.this.setWindowStopped(z);
                    }
                }, 0L);
            }
        }
    }

    public void reportNewConfiguration(android.content.res.Configuration configuration) {
        synchronized (this.mLock) {
            int size = this.mViews.size();
            android.content.res.Configuration configuration2 = new android.content.res.Configuration(configuration);
            for (int i = 0; i < size; i++) {
                this.mRoots.get(i).requestUpdateConfiguration(configuration2);
            }
        }
    }

    public void changeCanvasOpacity(android.os.IBinder iBinder, boolean z) {
        if (iBinder == null) {
            return;
        }
        synchronized (this.mLock) {
            for (int size = this.mParams.size() - 1; size >= 0; size--) {
                if (this.mParams.get(size).token == iBinder) {
                    this.mRoots.get(size).changeCanvasOpacity(z);
                    return;
                }
            }
        }
    }

    public android.view.SurfaceControl mirrorWallpaperSurface(int i) {
        try {
            return getWindowManagerService().mirrorWallpaperSurface(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void registerProposedRotationListener(android.os.IBinder iBinder, java.util.concurrent.Executor executor, final java.util.function.IntConsumer intConsumer) {
        android.view.WindowManagerGlobal.ProposedRotationListenerDelegate proposedRotationListenerDelegate;
        synchronized (this.mLock) {
            if (this.mProposedRotationListenerMap == null) {
                this.mProposedRotationListenerMap = new java.util.WeakHashMap<>(1);
            }
            final android.view.WindowManagerGlobal.ProposedRotationListenerDelegate proposedRotationListenerDelegate2 = this.mProposedRotationListenerMap.get(iBinder);
            if (proposedRotationListenerDelegate2 != null) {
                proposedRotationListenerDelegate = proposedRotationListenerDelegate2;
            } else {
                java.util.WeakHashMap<android.os.IBinder, android.view.WindowManagerGlobal.ProposedRotationListenerDelegate> weakHashMap = this.mProposedRotationListenerMap;
                proposedRotationListenerDelegate = new android.view.WindowManagerGlobal.ProposedRotationListenerDelegate();
                weakHashMap.put(iBinder, proposedRotationListenerDelegate);
            }
            if (proposedRotationListenerDelegate.add(executor, intConsumer)) {
                if (proposedRotationListenerDelegate2 != null) {
                    executor.execute(new java.lang.Runnable() { // from class: android.view.WindowManagerGlobal$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            intConsumer.accept(proposedRotationListenerDelegate2.mLastRotation);
                        }
                    });
                    return;
                }
                try {
                    proposedRotationListenerDelegate.onRotationChanged(getWindowManagerService().registerProposedRotationListener(iBinder, proposedRotationListenerDelegate));
                } catch (android.os.RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
        }
    }

    public void unregisterProposedRotationListener(android.os.IBinder iBinder, java.util.function.IntConsumer intConsumer) {
        synchronized (this.mLock) {
            if (this.mProposedRotationListenerMap == null) {
                return;
            }
            android.view.WindowManagerGlobal.ProposedRotationListenerDelegate proposedRotationListenerDelegate = this.mProposedRotationListenerMap.get(iBinder);
            if (proposedRotationListenerDelegate == null) {
                return;
            }
            if (proposedRotationListenerDelegate.remove(intConsumer)) {
                this.mProposedRotationListenerMap.remove(iBinder);
                try {
                    getWindowManagerService().removeRotationWatcher(proposedRotationListenerDelegate);
                } catch (android.os.RemoteException e) {
                    e.rethrowFromSystemServer();
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class ProposedRotationListenerDelegate extends android.view.IRotationWatcher.Stub {
        int mLastRotation;
        private volatile android.view.WindowManagerGlobal.ProposedRotationListenerDelegate.ListenerWrapper[] mListenerArray;
        private final java.util.ArrayList<android.view.WindowManagerGlobal.ProposedRotationListenerDelegate.ListenerWrapper> mListeners;

        private ProposedRotationListenerDelegate() {
            this.mListeners = new java.util.ArrayList<>(1);
        }

        static class ListenerWrapper {
            final java.util.concurrent.Executor mExecutor;
            final java.lang.ref.WeakReference<java.util.function.IntConsumer> mListener;

            ListenerWrapper(java.util.concurrent.Executor executor, java.util.function.IntConsumer intConsumer) {
                this.mExecutor = executor;
                this.mListener = new java.lang.ref.WeakReference<>(intConsumer);
            }
        }

        boolean add(java.util.concurrent.Executor executor, java.util.function.IntConsumer intConsumer) {
            for (int size = this.mListeners.size() - 1; size >= 0; size--) {
                if (this.mListeners.get(size).mListener.get() == intConsumer) {
                    return false;
                }
            }
            this.mListeners.add(new android.view.WindowManagerGlobal.ProposedRotationListenerDelegate.ListenerWrapper(executor, intConsumer));
            this.mListenerArray = (android.view.WindowManagerGlobal.ProposedRotationListenerDelegate.ListenerWrapper[]) this.mListeners.toArray(new android.view.WindowManagerGlobal.ProposedRotationListenerDelegate.ListenerWrapper[0]);
            return true;
        }

        boolean remove(java.util.function.IntConsumer intConsumer) {
            int size = this.mListeners.size();
            do {
                size--;
                if (size < 0) {
                    return false;
                }
            } while (this.mListeners.get(size).mListener.get() != intConsumer);
            this.mListeners.remove(size);
            this.mListenerArray = (android.view.WindowManagerGlobal.ProposedRotationListenerDelegate.ListenerWrapper[]) this.mListeners.toArray(new android.view.WindowManagerGlobal.ProposedRotationListenerDelegate.ListenerWrapper[0]);
            return this.mListeners.isEmpty();
        }

        @Override // android.view.IRotationWatcher
        public void onRotationChanged(final int i) {
            this.mLastRotation = i;
            boolean z = false;
            for (android.view.WindowManagerGlobal.ProposedRotationListenerDelegate.ListenerWrapper listenerWrapper : this.mListenerArray) {
                final java.util.function.IntConsumer intConsumer = listenerWrapper.mListener.get();
                if (intConsumer != null) {
                    listenerWrapper.mExecutor.execute(new java.lang.Runnable() { // from class: android.view.WindowManagerGlobal$ProposedRotationListenerDelegate$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            intConsumer.accept(i);
                        }
                    });
                    z = true;
                }
            }
            if (!z) {
                try {
                    android.view.WindowManagerGlobal.getWindowManagerService().removeRotationWatcher(this);
                } catch (android.os.RemoteException e) {
                    e.rethrowFromSystemServer();
                }
            }
        }
    }

    public void registerTrustedPresentationListener(android.os.IBinder iBinder, android.window.TrustedPresentationThresholds trustedPresentationThresholds, java.util.concurrent.Executor executor, java.util.function.Consumer<java.lang.Boolean> consumer) {
        this.mTrustedPresentationListener.addListener(iBinder, trustedPresentationThresholds, consumer, executor);
    }

    public void unregisterTrustedPresentationListener(java.util.function.Consumer<java.lang.Boolean> consumer) {
        this.mTrustedPresentationListener.removeListener(consumer);
    }

    android.window.InputTransferToken registerBatchedSurfaceControlInputReceiver(int i, android.window.InputTransferToken inputTransferToken, android.view.SurfaceControl surfaceControl, android.view.Choreographer choreographer, final android.view.SurfaceControlInputReceiver surfaceControlInputReceiver) {
        android.os.Binder binder = new android.os.Binder();
        android.window.InputTransferToken inputTransferToken2 = new android.window.InputTransferToken();
        android.view.InputChannel inputChannel = new android.view.InputChannel();
        try {
            getWindowSession().grantInputChannel(i, surfaceControl, binder, inputTransferToken, 0, 0, 2, 0, null, inputTransferToken2, surfaceControl.getName(), inputChannel);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Failed to create input channel", e);
            e.rethrowAsRuntimeException();
        }
        synchronized (this.mSurfaceControlInputReceivers) {
            this.mSurfaceControlInputReceivers.put(surfaceControl.getLayerId(), new android.view.WindowManagerGlobal.SurfaceControlInputReceiverInfo(binder, new android.view.BatchedInputEventReceiver(inputChannel, choreographer.getLooper(), choreographer) { // from class: android.view.WindowManagerGlobal.3
                @Override // android.view.InputEventReceiver
                public void onInputEvent(android.view.InputEvent inputEvent) {
                    finishInputEvent(inputEvent, surfaceControlInputReceiver.onInputEvent(inputEvent));
                }
            }));
        }
        return inputTransferToken2;
    }

    /* JADX WARN: Removed duplicated region for block: B:9:0x003f A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    android.window.InputTransferToken registerUnbatchedSurfaceControlInputReceiver(int i, android.window.InputTransferToken inputTransferToken, android.view.SurfaceControl surfaceControl, android.os.Looper looper, final android.view.SurfaceControlInputReceiver surfaceControlInputReceiver) {
        android.view.InputChannel inputChannel;
        android.os.Binder binder = new android.os.Binder();
        android.window.InputTransferToken inputTransferToken2 = new android.window.InputTransferToken();
        android.view.InputChannel inputChannel2 = new android.view.InputChannel();
        try {
            inputChannel = inputChannel2;
        } catch (android.os.RemoteException e) {
            e = e;
            inputChannel = inputChannel2;
        }
        try {
            getWindowSession().grantInputChannel(i, surfaceControl, binder, inputTransferToken, 0, 0, 2, 0, null, inputTransferToken2, surfaceControl.getName(), inputChannel2);
        } catch (android.os.RemoteException e2) {
            e = e2;
            android.util.Log.e(TAG, "Failed to create input channel", e);
            e.rethrowAsRuntimeException();
            synchronized (this.mSurfaceControlInputReceivers) {
            }
        }
        synchronized (this.mSurfaceControlInputReceivers) {
            this.mSurfaceControlInputReceivers.put(surfaceControl.getLayerId(), new android.view.WindowManagerGlobal.SurfaceControlInputReceiverInfo(binder, new android.view.InputEventReceiver(inputChannel, looper) { // from class: android.view.WindowManagerGlobal.4
                @Override // android.view.InputEventReceiver
                public void onInputEvent(android.view.InputEvent inputEvent) {
                    finishInputEvent(inputEvent, surfaceControlInputReceiver.onInputEvent(inputEvent));
                }
            }));
        }
        return inputTransferToken2;
    }

    void unregisterSurfaceControlInputReceiver(android.view.SurfaceControl surfaceControl) {
        android.view.WindowManagerGlobal.SurfaceControlInputReceiverInfo removeReturnOld;
        synchronized (this.mSurfaceControlInputReceivers) {
            removeReturnOld = this.mSurfaceControlInputReceivers.removeReturnOld(surfaceControl.getLayerId());
        }
        if (removeReturnOld == null) {
            android.util.Log.w(TAG, "No registered input event receiver with sc: " + surfaceControl);
            return;
        }
        try {
            getWindowSession().remove(removeReturnOld.mClientToken);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Failed to remove input channel", e);
            e.rethrowAsRuntimeException();
        }
        removeReturnOld.mInputEventReceiver.dispose();
    }

    android.os.IBinder getSurfaceControlInputClientToken(android.view.SurfaceControl surfaceControl) {
        android.view.WindowManagerGlobal.SurfaceControlInputReceiverInfo surfaceControlInputReceiverInfo;
        synchronized (this.mSurfaceControlInputReceivers) {
            surfaceControlInputReceiverInfo = this.mSurfaceControlInputReceivers.get(surfaceControl.getLayerId());
        }
        if (surfaceControlInputReceiverInfo == null) {
            android.util.Log.w(TAG, "No registered input event receiver with sc: " + surfaceControl);
            return null;
        }
        return surfaceControlInputReceiverInfo.mClientToken;
    }

    boolean transferTouchGesture(android.window.InputTransferToken inputTransferToken, android.window.InputTransferToken inputTransferToken2) {
        try {
            return getWindowManagerService().transferTouchGesture(inputTransferToken, inputTransferToken2);
        } catch (android.os.RemoteException e) {
            e.rethrowAsRuntimeException();
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    final class TrustedPresentationListener extends android.window.ITrustedPresentationListener.Stub {
        private static int sId = 0;
        private final android.util.ArrayMap<java.util.function.Consumer<java.lang.Boolean>, android.util.Pair<java.lang.Integer, java.util.concurrent.Executor>> mListeners;
        private final java.lang.Object mTplLock;

        private TrustedPresentationListener() {
            this.mListeners = new android.util.ArrayMap<>();
            this.mTplLock = new java.lang.Object();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addListener(android.os.IBinder iBinder, android.window.TrustedPresentationThresholds trustedPresentationThresholds, java.util.function.Consumer<java.lang.Boolean> consumer, java.util.concurrent.Executor executor) {
            synchronized (this.mTplLock) {
                if (this.mListeners.containsKey(consumer)) {
                    android.util.Log.i(android.view.WindowManagerGlobal.TAG, "Updating listener " + consumer + " thresholds to " + trustedPresentationThresholds);
                    removeListener(consumer);
                }
                int i = sId;
                sId = i + 1;
                this.mListeners.put(consumer, new android.util.Pair<>(java.lang.Integer.valueOf(i), executor));
                try {
                    android.view.WindowManagerGlobal.getWindowManagerService().registerTrustedPresentationListener(iBinder, this, trustedPresentationThresholds, i);
                } catch (android.os.RemoteException e) {
                    e.rethrowFromSystemServer();
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void removeListener(java.util.function.Consumer<java.lang.Boolean> consumer) {
            synchronized (this.mTplLock) {
                android.util.Pair<java.lang.Integer, java.util.concurrent.Executor> remove = this.mListeners.remove(consumer);
                if (remove == null) {
                    android.util.Log.i(android.view.WindowManagerGlobal.TAG, "listener " + consumer + " does not exist.");
                    return;
                }
                try {
                    android.view.WindowManagerGlobal.getWindowManagerService().unregisterTrustedPresentationListener(this, remove.first.intValue());
                } catch (android.os.RemoteException e) {
                    e.rethrowFromSystemServer();
                }
            }
        }

        @Override // android.window.ITrustedPresentationListener
        public void onTrustedPresentationChanged(final int[] iArr, final int[] iArr2) {
            final java.util.ArrayList arrayList = new java.util.ArrayList();
            synchronized (this.mTplLock) {
                this.mListeners.forEach(new java.util.function.BiConsumer() { // from class: android.view.WindowManagerGlobal$TrustedPresentationListener$$ExternalSyntheticLambda0
                    @Override // java.util.function.BiConsumer
                    public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                        android.view.WindowManagerGlobal.TrustedPresentationListener.lambda$onTrustedPresentationChanged$4(iArr, arrayList, iArr2, (java.util.function.Consumer) obj, (android.util.Pair) obj2);
                    }
                });
            }
            for (int i = 0; i < arrayList.size(); i++) {
                ((java.lang.Runnable) arrayList.get(i)).run();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        static /* synthetic */ void lambda$onTrustedPresentationChanged$4(int[] iArr, java.util.ArrayList arrayList, int[] iArr2, final java.util.function.Consumer consumer, android.util.Pair pair) {
            java.lang.Integer num = (java.lang.Integer) pair.first;
            final java.util.concurrent.Executor executor = (java.util.concurrent.Executor) pair.second;
            for (int i : iArr) {
                if (num.intValue() == i) {
                    arrayList.add(new java.lang.Runnable() { // from class: android.view.WindowManagerGlobal$TrustedPresentationListener$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            executor.execute(new java.lang.Runnable() { // from class: android.view.WindowManagerGlobal$TrustedPresentationListener$$ExternalSyntheticLambda3
                                @Override // java.lang.Runnable
                                public final void run() {
                                    r1.accept(true);
                                }
                            });
                        }
                    });
                }
            }
            for (int i2 : iArr2) {
                if (num.intValue() == i2) {
                    arrayList.add(new java.lang.Runnable() { // from class: android.view.WindowManagerGlobal$TrustedPresentationListener$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            executor.execute(new java.lang.Runnable() { // from class: android.view.WindowManagerGlobal$TrustedPresentationListener$$ExternalSyntheticLambda4
                                @Override // java.lang.Runnable
                                public final void run() {
                                    r1.accept(false);
                                }
                            });
                        }
                    });
                }
            }
        }
    }

    public void addWindowlessRoot(android.view.ViewRootImpl viewRootImpl) {
        synchronized (this.mLock) {
            this.mWindowlessRoots.add(viewRootImpl);
        }
    }

    public void removeWindowlessRoot(android.view.ViewRootImpl viewRootImpl) {
        synchronized (this.mLock) {
            this.mWindowlessRoots.remove(viewRootImpl);
        }
    }

    public void setRecentsAppBehindSystemBars(boolean z) {
        try {
            getWindowManagerService().setRecentsAppBehindSystemBars(z);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private static class SurfaceControlInputReceiverInfo {
        final android.os.IBinder mClientToken;
        final android.view.InputEventReceiver mInputEventReceiver;

        private SurfaceControlInputReceiverInfo(android.os.IBinder iBinder, android.view.InputEventReceiver inputEventReceiver) {
            this.mClientToken = iBinder;
            this.mInputEventReceiver = inputEventReceiver;
        }
    }
}
