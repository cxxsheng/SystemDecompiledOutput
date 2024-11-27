package android.window;

/* loaded from: classes4.dex */
public class WindowOnBackInvokedDispatcher implements android.window.OnBackInvokedDispatcher {
    private static final boolean ALWAYS_ENFORCE_PREDICTIVE_BACK;
    private static final boolean ENABLE_PREDICTIVE_BACK;
    private static final boolean PREDICTIVE_BACK_FALLBACK_WINDOW_ATTRIBUTE;
    private static final java.lang.String TAG = "WindowOnBackDispatcher";
    private android.window.WindowOnBackInvokedDispatcher.Checker mChecker;
    private android.window.ImeOnBackInvokedDispatcher mImeDispatcher;
    private android.view.IWindow mWindow;
    private android.view.IWindowSession mWindowSession;
    private final java.util.HashMap<android.window.OnBackInvokedCallback, java.lang.Integer> mAllCallbacks = new java.util.HashMap<>();
    public final java.util.TreeMap<java.lang.Integer, java.util.ArrayList<android.window.OnBackInvokedCallback>> mOnBackInvokedCallbacks = new java.util.TreeMap<>();
    private final android.window.BackProgressAnimator mProgressAnimator = new android.window.BackProgressAnimator();
    private boolean mIsDispatching = false;

    static {
        ENABLE_PREDICTIVE_BACK = android.os.SystemProperties.getInt("persist.wm.debug.predictive_back", 1) != 0;
        ALWAYS_ENFORCE_PREDICTIVE_BACK = android.os.SystemProperties.getInt("persist.wm.debug.predictive_back_always_enforce", 0) != 0;
        PREDICTIVE_BACK_FALLBACK_WINDOW_ATTRIBUTE = android.os.SystemProperties.getInt("persist.wm.debug.predictive_back_fallback_window_attribute", 0) != 0;
    }

    public WindowOnBackInvokedDispatcher(android.content.Context context) {
        this.mChecker = new android.window.WindowOnBackInvokedDispatcher.Checker(context);
    }

    public void attachToWindow(android.view.IWindowSession iWindowSession, android.view.IWindow iWindow) {
        this.mWindowSession = iWindowSession;
        this.mWindow = iWindow;
        if (!this.mAllCallbacks.isEmpty()) {
            setTopOnBackInvokedCallback(getTopCallback());
        }
    }

    public void detachFromWindow() {
        clear();
        this.mWindow = null;
        this.mWindowSession = null;
    }

    @Override // android.window.OnBackInvokedDispatcher
    public void registerOnBackInvokedCallback(int i, android.window.OnBackInvokedCallback onBackInvokedCallback) {
        if (this.mChecker.checkApplicationCallbackRegistration(i, onBackInvokedCallback)) {
            registerOnBackInvokedCallbackUnchecked(onBackInvokedCallback, i);
        }
    }

    public void registerOnBackInvokedCallbackUnchecked(android.window.OnBackInvokedCallback onBackInvokedCallback, int i) {
        if (this.mImeDispatcher != null) {
            this.mImeDispatcher.registerOnBackInvokedCallback(i, onBackInvokedCallback);
            return;
        }
        if (!this.mOnBackInvokedCallbacks.containsKey(java.lang.Integer.valueOf(i))) {
            this.mOnBackInvokedCallbacks.put(java.lang.Integer.valueOf(i), new java.util.ArrayList<>());
        }
        java.util.ArrayList<android.window.OnBackInvokedCallback> arrayList = this.mOnBackInvokedCallbacks.get(java.lang.Integer.valueOf(i));
        if (this.mAllCallbacks.containsKey(onBackInvokedCallback)) {
            this.mOnBackInvokedCallbacks.get(this.mAllCallbacks.get(onBackInvokedCallback)).remove(onBackInvokedCallback);
        }
        android.window.OnBackInvokedCallback topCallback = getTopCallback();
        arrayList.add(onBackInvokedCallback);
        this.mAllCallbacks.put(onBackInvokedCallback, java.lang.Integer.valueOf(i));
        if (topCallback == null || (topCallback != onBackInvokedCallback && this.mAllCallbacks.get(topCallback).intValue() <= i)) {
            setTopOnBackInvokedCallback(onBackInvokedCallback);
        }
    }

    @Override // android.window.OnBackInvokedDispatcher
    public void unregisterOnBackInvokedCallback(android.window.OnBackInvokedCallback onBackInvokedCallback) {
        if (this.mImeDispatcher != null) {
            this.mImeDispatcher.unregisterOnBackInvokedCallback(onBackInvokedCallback);
            return;
        }
        if (!this.mAllCallbacks.containsKey(onBackInvokedCallback)) {
            return;
        }
        android.window.OnBackInvokedCallback topCallback = getTopCallback();
        java.lang.Integer num = this.mAllCallbacks.get(onBackInvokedCallback);
        java.util.ArrayList<android.window.OnBackInvokedCallback> arrayList = this.mOnBackInvokedCallbacks.get(num);
        arrayList.remove(onBackInvokedCallback);
        if (arrayList.isEmpty()) {
            this.mOnBackInvokedCallbacks.remove(num);
        }
        this.mAllCallbacks.remove(onBackInvokedCallback);
        if (topCallback == onBackInvokedCallback) {
            sendCancelledIfInProgress(onBackInvokedCallback);
            setTopOnBackInvokedCallback(getTopCallback());
        }
    }

    public boolean isDispatching() {
        return this.mIsDispatching;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onStartDispatching() {
        this.mIsDispatching = true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onStopDispatching() {
        this.mIsDispatching = false;
    }

    private void sendCancelledIfInProgress(android.window.OnBackInvokedCallback onBackInvokedCallback) {
        boolean isBackAnimationInProgress = this.mProgressAnimator.isBackAnimationInProgress();
        if (isBackAnimationInProgress && (onBackInvokedCallback instanceof android.window.OnBackAnimationCallback)) {
            ((android.window.OnBackAnimationCallback) onBackInvokedCallback).onBackCancelled();
        } else {
            android.util.Log.w(TAG, "sendCancelIfRunning: isInProgress=" + isBackAnimationInProgress + "callback=" + onBackInvokedCallback);
        }
    }

    @Override // android.window.OnBackInvokedDispatcher
    public void registerSystemOnBackInvokedCallback(android.window.OnBackInvokedCallback onBackInvokedCallback) {
        registerOnBackInvokedCallbackUnchecked(onBackInvokedCallback, -1);
    }

    public void clear() {
        if (this.mImeDispatcher != null) {
            this.mImeDispatcher.clear();
            this.mImeDispatcher = null;
        }
        if (!this.mAllCallbacks.isEmpty()) {
            android.window.OnBackInvokedCallback topCallback = getTopCallback();
            if (topCallback != null) {
                sendCancelledIfInProgress(topCallback);
            } else {
                android.util.Log.e(TAG, "There is no topCallback, even if mAllCallbacks is not empty");
            }
            setTopOnBackInvokedCallback(null);
        }
        android.os.Handler main = android.os.Handler.getMain();
        android.window.BackProgressAnimator backProgressAnimator = this.mProgressAnimator;
        java.util.Objects.requireNonNull(backProgressAnimator);
        main.post(new android.window.ImeOnBackInvokedDispatcher$$ExternalSyntheticLambda0(backProgressAnimator));
        this.mAllCallbacks.clear();
        this.mOnBackInvokedCallbacks.clear();
    }

    private void setTopOnBackInvokedCallback(android.window.OnBackInvokedCallback onBackInvokedCallback) {
        android.window.IOnBackInvokedCallback onBackInvokedCallbackWrapper;
        android.window.OnBackInvokedCallbackInfo onBackInvokedCallbackInfo;
        if (this.mWindowSession == null || this.mWindow == null) {
            return;
        }
        if (onBackInvokedCallback == null) {
            onBackInvokedCallbackInfo = null;
        } else {
            try {
                int intValue = this.mAllCallbacks.get(onBackInvokedCallback).intValue();
                if (onBackInvokedCallback instanceof android.window.ImeOnBackInvokedDispatcher.ImeOnBackInvokedCallback) {
                    onBackInvokedCallbackWrapper = ((android.window.ImeOnBackInvokedDispatcher.ImeOnBackInvokedCallback) onBackInvokedCallback).getIOnBackInvokedCallback();
                } else {
                    onBackInvokedCallbackWrapper = new android.window.WindowOnBackInvokedDispatcher.OnBackInvokedCallbackWrapper(onBackInvokedCallback, this.mProgressAnimator, this);
                }
                onBackInvokedCallbackInfo = new android.window.OnBackInvokedCallbackInfo(onBackInvokedCallbackWrapper, intValue, onBackInvokedCallback instanceof android.window.OnBackAnimationCallback);
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "Failed to set OnBackInvokedCallback to WM. Error: " + e);
                return;
            }
        }
        this.mWindowSession.setOnBackInvokedCallbackInfo(this.mWindow, onBackInvokedCallbackInfo);
    }

    public android.window.OnBackInvokedCallback getTopCallback() {
        if (this.mAllCallbacks.isEmpty()) {
            return null;
        }
        java.util.Iterator<java.lang.Integer> it = this.mOnBackInvokedCallbacks.descendingKeySet().iterator();
        while (it.hasNext()) {
            java.util.ArrayList<android.window.OnBackInvokedCallback> arrayList = this.mOnBackInvokedCallbacks.get(it.next());
            if (!arrayList.isEmpty()) {
                return arrayList.get(arrayList.size() - 1);
            }
        }
        return null;
    }

    public void updateContext(android.content.Context context) {
        this.mChecker = new android.window.WindowOnBackInvokedDispatcher.Checker(context);
    }

    public boolean isOnBackInvokedCallbackEnabled() {
        return isOnBackInvokedCallbackEnabled(this.mChecker.getContext());
    }

    public void dump(java.lang.String str, final java.io.PrintWriter printWriter) {
        final java.lang.String str2 = str + "    ";
        printWriter.println(str + "WindowOnBackDispatcher:");
        if (this.mAllCallbacks.isEmpty()) {
            printWriter.println(str + "<None>");
            return;
        }
        printWriter.println(str2 + "Top Callback: " + getTopCallback());
        printWriter.println(str2 + "Callbacks: ");
        this.mAllCallbacks.forEach(new java.util.function.BiConsumer() { // from class: android.window.WindowOnBackInvokedDispatcher$$ExternalSyntheticLambda1
            @Override // java.util.function.BiConsumer
            public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                printWriter.println(str2 + "  Callback: " + ((android.window.OnBackInvokedCallback) obj) + " Priority=" + ((java.lang.Integer) obj2));
            }
        });
    }

    static class OnBackInvokedCallbackWrapper extends android.window.IOnBackInvokedCallback.Stub {
        final android.window.WindowOnBackInvokedDispatcher.OnBackInvokedCallbackWrapper.CallbackRef mCallbackRef;
        private final android.window.WindowOnBackInvokedDispatcher mDispatcher;
        private final android.window.BackProgressAnimator mProgressAnimator;

        static class CallbackRef {
            final android.window.OnBackInvokedCallback mStrongRef;
            final java.lang.ref.WeakReference<android.window.OnBackInvokedCallback> mWeakRef;

            CallbackRef(android.window.OnBackInvokedCallback onBackInvokedCallback, boolean z) {
                if (z) {
                    this.mWeakRef = new java.lang.ref.WeakReference<>(onBackInvokedCallback);
                    this.mStrongRef = null;
                } else {
                    this.mStrongRef = onBackInvokedCallback;
                    this.mWeakRef = null;
                }
            }

            android.window.OnBackInvokedCallback get() {
                if (this.mStrongRef != null) {
                    return this.mStrongRef;
                }
                return this.mWeakRef.get();
            }
        }

        OnBackInvokedCallbackWrapper(android.window.OnBackInvokedCallback onBackInvokedCallback, android.window.BackProgressAnimator backProgressAnimator, android.window.WindowOnBackInvokedDispatcher windowOnBackInvokedDispatcher) {
            this.mCallbackRef = new android.window.WindowOnBackInvokedDispatcher.OnBackInvokedCallbackWrapper.CallbackRef(onBackInvokedCallback, true);
            this.mProgressAnimator = backProgressAnimator;
            this.mDispatcher = windowOnBackInvokedDispatcher;
        }

        OnBackInvokedCallbackWrapper(android.window.OnBackInvokedCallback onBackInvokedCallback, android.window.BackProgressAnimator backProgressAnimator, boolean z) {
            this.mCallbackRef = new android.window.WindowOnBackInvokedDispatcher.OnBackInvokedCallbackWrapper.CallbackRef(onBackInvokedCallback, z);
            this.mProgressAnimator = backProgressAnimator;
            this.mDispatcher = null;
        }

        @Override // android.window.IOnBackInvokedCallback
        public void onBackStarted(final android.window.BackMotionEvent backMotionEvent) {
            android.os.Handler.getMain().post(new java.lang.Runnable() { // from class: android.window.WindowOnBackInvokedDispatcher$OnBackInvokedCallbackWrapper$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    android.window.WindowOnBackInvokedDispatcher.OnBackInvokedCallbackWrapper.this.lambda$onBackStarted$0(backMotionEvent);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onBackStarted$0(android.window.BackMotionEvent backMotionEvent) {
            if (this.mDispatcher != null) {
                this.mDispatcher.onStartDispatching();
            }
            final android.window.OnBackAnimationCallback backAnimationCallback = getBackAnimationCallback();
            if (backAnimationCallback != null) {
                this.mProgressAnimator.reset();
                backAnimationCallback.onBackStarted(new android.window.BackEvent(backMotionEvent.getTouchX(), backMotionEvent.getTouchY(), backMotionEvent.getProgress(), backMotionEvent.getSwipeEdge()));
                android.window.BackProgressAnimator backProgressAnimator = this.mProgressAnimator;
                java.util.Objects.requireNonNull(backAnimationCallback);
                backProgressAnimator.onBackStarted(backMotionEvent, new android.window.BackProgressAnimator.ProgressCallback() { // from class: android.window.WindowOnBackInvokedDispatcher$OnBackInvokedCallbackWrapper$$ExternalSyntheticLambda4
                    @Override // android.window.BackProgressAnimator.ProgressCallback
                    public final void onProgressUpdate(android.window.BackEvent backEvent) {
                        android.window.OnBackAnimationCallback.this.onBackProgressed(backEvent);
                    }
                });
            }
        }

        @Override // android.window.IOnBackInvokedCallback
        public void onBackProgressed(final android.window.BackMotionEvent backMotionEvent) {
            android.os.Handler.getMain().post(new java.lang.Runnable() { // from class: android.window.WindowOnBackInvokedDispatcher$OnBackInvokedCallbackWrapper$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    android.window.WindowOnBackInvokedDispatcher.OnBackInvokedCallbackWrapper.this.lambda$onBackProgressed$1(backMotionEvent);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onBackProgressed$1(android.window.BackMotionEvent backMotionEvent) {
            if (getBackAnimationCallback() != null) {
                this.mProgressAnimator.onBackProgressed(backMotionEvent);
            }
        }

        @Override // android.window.IOnBackInvokedCallback
        public void onBackCancelled() {
            android.os.Handler.getMain().post(new java.lang.Runnable() { // from class: android.window.WindowOnBackInvokedDispatcher$OnBackInvokedCallbackWrapper$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.window.WindowOnBackInvokedDispatcher.OnBackInvokedCallbackWrapper.this.lambda$onBackCancelled$3();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onBackCancelled$3() {
            if (this.mDispatcher != null) {
                this.mDispatcher.onStopDispatching();
            }
            this.mProgressAnimator.onBackCancelled(new java.lang.Runnable() { // from class: android.window.WindowOnBackInvokedDispatcher$OnBackInvokedCallbackWrapper$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    android.window.WindowOnBackInvokedDispatcher.OnBackInvokedCallbackWrapper.this.lambda$onBackCancelled$2();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onBackCancelled$2() {
            android.window.OnBackAnimationCallback backAnimationCallback = getBackAnimationCallback();
            if (backAnimationCallback != null) {
                backAnimationCallback.onBackCancelled();
            }
        }

        @Override // android.window.IOnBackInvokedCallback
        public void onBackInvoked() throws android.os.RemoteException {
            android.os.Handler.getMain().post(new java.lang.Runnable() { // from class: android.window.WindowOnBackInvokedDispatcher$OnBackInvokedCallbackWrapper$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    android.window.WindowOnBackInvokedDispatcher.OnBackInvokedCallbackWrapper.this.lambda$onBackInvoked$4();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onBackInvoked$4() {
            if (this.mDispatcher != null) {
                this.mDispatcher.onStopDispatching();
            }
            boolean isBackAnimationInProgress = this.mProgressAnimator.isBackAnimationInProgress();
            this.mProgressAnimator.reset();
            android.window.OnBackInvokedCallback onBackInvokedCallback = this.mCallbackRef.get();
            if (onBackInvokedCallback == null) {
                android.util.Log.d(android.window.WindowOnBackInvokedDispatcher.TAG, "Trying to call onBackInvoked() on a null callback reference.");
            } else if ((onBackInvokedCallback instanceof android.window.OnBackAnimationCallback) && !isBackAnimationInProgress) {
                android.util.Log.w(android.window.WindowOnBackInvokedDispatcher.TAG, "ProgressAnimator was not in progress, skip onBackInvoked().");
            } else {
                onBackInvokedCallback.onBackInvoked();
            }
        }

        private android.window.OnBackAnimationCallback getBackAnimationCallback() {
            android.window.OnBackInvokedCallback onBackInvokedCallback = this.mCallbackRef.get();
            if (onBackInvokedCallback instanceof android.window.OnBackAnimationCallback) {
                return (android.window.OnBackAnimationCallback) onBackInvokedCallback;
            }
            return null;
        }
    }

    public static boolean isOnBackInvokedCallbackEnabled(final android.content.Context context) {
        android.content.Context context2 = context;
        while ((context2 instanceof android.content.ContextWrapper) && !(context2 instanceof android.app.Activity)) {
            context2 = ((android.content.ContextWrapper) context2).getBaseContext();
        }
        return isOnBackInvokedCallbackEnabled(context2 instanceof android.app.Activity ? ((android.app.Activity) context2).getActivityInfo() : null, context2.getApplicationInfo(), new java.util.function.Supplier() { // from class: android.window.WindowOnBackInvokedDispatcher$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                return android.window.WindowOnBackInvokedDispatcher.lambda$isOnBackInvokedCallbackEnabled$1(android.content.Context.this);
            }
        });
    }

    static /* synthetic */ android.content.Context lambda$isOnBackInvokedCallbackEnabled$1(android.content.Context context) {
        return context;
    }

    @Override // android.window.OnBackInvokedDispatcher
    public void setImeOnBackInvokedDispatcher(android.window.ImeOnBackInvokedDispatcher imeOnBackInvokedDispatcher) {
        this.mImeDispatcher = imeOnBackInvokedDispatcher;
    }

    public boolean hasImeOnBackInvokedDispatcher() {
        return this.mImeDispatcher != null;
    }

    public static class Checker {
        private java.lang.ref.WeakReference<android.content.Context> mContext;

        public Checker(android.content.Context context) {
            this.mContext = new java.lang.ref.WeakReference<>(context);
        }

        public boolean checkApplicationCallbackRegistration(int i, android.window.OnBackInvokedCallback onBackInvokedCallback) {
            if (!android.window.WindowOnBackInvokedDispatcher.isOnBackInvokedCallbackEnabled(getContext()) && !(onBackInvokedCallback instanceof android.window.CompatOnBackInvokedCallback)) {
                android.util.Log.w(android.window.WindowOnBackInvokedDispatcher.TAG, "OnBackInvokedCallback is not enabled for the application.\nSet 'android:enableOnBackInvokedCallback=\"true\"' in the application manifest.");
                return false;
            }
            if (i < 0) {
                throw new java.lang.IllegalArgumentException("Application registered OnBackInvokedCallback cannot have negative priority. Priority: " + i);
            }
            java.util.Objects.requireNonNull(onBackInvokedCallback);
            return true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public android.content.Context getContext() {
            return this.mContext.get();
        }
    }

    public static boolean isOnBackInvokedCallbackEnabled(android.content.pm.ActivityInfo activityInfo, android.content.pm.ApplicationInfo applicationInfo, java.util.function.Supplier<android.content.Context> supplier) {
        if (!ENABLE_PREDICTIVE_BACK) {
            return false;
        }
        if (ALWAYS_ENFORCE_PREDICTIVE_BACK) {
            return true;
        }
        if (activityInfo != null && activityInfo.hasOnBackInvokedCallbackEnabled()) {
            return activityInfo.isOnBackInvokedCallbackEnabled();
        }
        boolean isOnBackInvokedCallbackEnabled = applicationInfo.isOnBackInvokedCallbackEnabled();
        if (isOnBackInvokedCallbackEnabled) {
            return true;
        }
        if (PREDICTIVE_BACK_FALLBACK_WINDOW_ATTRIBUTE) {
            android.content.Context context = supplier.get();
            if (context != null) {
                android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(new int[]{16843763});
                r2 = obtainStyledAttributes.getIndexCount() > 0 ? obtainStyledAttributes.getBoolean(0, true) : true;
                obtainStyledAttributes.recycle();
            }
            return r2;
        }
        return isOnBackInvokedCallbackEnabled;
    }
}
