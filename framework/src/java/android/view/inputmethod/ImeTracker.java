package android.view.inputmethod;

/* loaded from: classes4.dex */
public interface ImeTracker {
    public static final int ORIGIN_CLIENT = 5;
    public static final int ORIGIN_IME = 7;
    public static final int ORIGIN_SERVER = 6;
    public static final int ORIGIN_WM_SHELL = 8;
    public static final int PHASE_CLIENT_ANIMATION_CANCEL = 40;
    public static final int PHASE_CLIENT_ANIMATION_FINISHED_HIDE = 42;
    public static final int PHASE_CLIENT_ANIMATION_FINISHED_SHOW = 41;
    public static final int PHASE_CLIENT_ANIMATION_RUNNING = 39;
    public static final int PHASE_CLIENT_APPLY_ANIMATION = 32;
    public static final int PHASE_CLIENT_COLLECT_SOURCE_CONTROLS = 35;
    public static final int PHASE_CLIENT_CONTROL_ANIMATION = 33;
    public static final int PHASE_CLIENT_HANDLE_HIDE_INSETS = 31;
    public static final int PHASE_CLIENT_HANDLE_SHOW_INSETS = 30;
    public static final int PHASE_CLIENT_HIDE_INSETS = 29;
    public static final int PHASE_CLIENT_INSETS_CONSUMER_NOTIFY_HIDDEN = 38;
    public static final int PHASE_CLIENT_INSETS_CONSUMER_REQUEST_SHOW = 36;
    public static final int PHASE_CLIENT_REQUEST_IME_SHOW = 37;
    public static final int PHASE_CLIENT_SHOW_INSETS = 28;
    public static final int PHASE_CLIENT_VIEW_SERVED = 1;
    public static final int PHASE_IME_HIDE_SOFT_INPUT = 14;
    public static final int PHASE_IME_HIDE_WINDOW = 45;
    public static final int PHASE_IME_ON_SHOW_SOFT_INPUT_TRUE = 15;
    public static final int PHASE_IME_PRIVILEGED_OPERATIONS = 46;
    public static final int PHASE_IME_SHOW_SOFT_INPUT = 13;
    public static final int PHASE_IME_SHOW_WINDOW = 44;
    public static final int PHASE_IME_WRAPPER = 11;
    public static final int PHASE_IME_WRAPPER_DISPATCH = 12;
    public static final int PHASE_NOT_SET = 0;
    public static final int PHASE_SERVER_ACCESSIBILITY = 4;
    public static final int PHASE_SERVER_APPLY_IME_VISIBILITY = 17;
    public static final int PHASE_SERVER_CLIENT_FOCUSED = 3;
    public static final int PHASE_SERVER_CLIENT_KNOWN = 2;
    public static final int PHASE_SERVER_CURRENT_ACTIVE_IME = 47;
    public static final int PHASE_SERVER_HAS_IME = 9;
    public static final int PHASE_SERVER_HIDE_IMPLICIT = 6;
    public static final int PHASE_SERVER_HIDE_NOT_ALWAYS = 7;
    public static final int PHASE_SERVER_SHOULD_HIDE = 10;
    public static final int PHASE_SERVER_SYSTEM_READY = 5;
    public static final int PHASE_SERVER_WAIT_IME = 8;
    public static final int PHASE_WM_ABORT_SHOW_IME_POST_LAYOUT = 43;
    public static final int PHASE_WM_ANIMATION_CREATE = 26;
    public static final int PHASE_WM_ANIMATION_RUNNING = 27;
    public static final int PHASE_WM_HAS_IME_INSETS_CONTROL_TARGET = 20;
    public static final int PHASE_WM_REMOTE_INSETS_CONTROLLER = 25;
    public static final int PHASE_WM_REMOTE_INSETS_CONTROL_TARGET_HIDE_INSETS = 24;
    public static final int PHASE_WM_REMOTE_INSETS_CONTROL_TARGET_SHOW_INSETS = 23;
    public static final int PHASE_WM_SHOW_IME_READY = 19;
    public static final int PHASE_WM_SHOW_IME_RUNNER = 18;
    public static final int PHASE_WM_WINDOW_INSETS_CONTROL_TARGET_HIDE_INSETS = 22;
    public static final int PHASE_WM_WINDOW_INSETS_CONTROL_TARGET_SHOW_INSETS = 21;
    public static final int STATUS_CANCEL = 2;
    public static final int STATUS_FAIL = 3;
    public static final int STATUS_RUN = 1;
    public static final int STATUS_SUCCESS = 4;
    public static final int STATUS_TIMEOUT = 5;
    public static final java.lang.String TAG = "ImeTracker";
    public static final java.lang.String TOKEN_NONE = "TOKEN_NONE";
    public static final int TYPE_HIDE = 2;
    public static final int TYPE_SHOW = 1;
    public static final boolean DEBUG_IME_VISIBILITY = android.os.SystemProperties.getBoolean("persist.debug.imf_event", false);
    public static final android.view.inputmethod.ImeTracker LOGGER = new android.view.inputmethod.ImeTracker.AnonymousClass1();
    public static final android.view.inputmethod.ImeTracker.ImeJankTracker JANK_TRACKER = new android.view.inputmethod.ImeTracker.ImeJankTracker();
    public static final android.view.inputmethod.ImeTracker.ImeLatencyTracker LATENCY_TRACKER = new android.view.inputmethod.ImeTracker.ImeLatencyTracker();

    public interface InputMethodJankContext {
        android.content.Context getDisplayContext();

        java.lang.String getHostPackageName();

        android.view.SurfaceControl getTargetSurfaceControl();
    }

    public interface InputMethodLatencyContext {
        android.content.Context getAppContext();
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Origin {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Phase {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Status {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Type {
    }

    void onCancelled(android.view.inputmethod.ImeTracker.Token token, int i);

    void onFailed(android.view.inputmethod.ImeTracker.Token token, int i);

    void onHidden(android.view.inputmethod.ImeTracker.Token token);

    void onProgress(android.view.inputmethod.ImeTracker.Token token, int i);

    void onShown(android.view.inputmethod.ImeTracker.Token token);

    android.view.inputmethod.ImeTracker.Token onStart(java.lang.String str, int i, int i2, int i3, int i4, boolean z);

    void onTodo(android.view.inputmethod.ImeTracker.Token token, int i);

    default android.view.inputmethod.ImeTracker.Token onStart(int i, int i2, int i3, boolean z) {
        return onStart(android.os.Process.myProcessName(), android.os.Process.myUid(), i, i2, i3, z);
    }

    static boolean isFromUser(android.view.View view) {
        android.os.Handler handler;
        android.view.ViewRootImpl viewRootImpl;
        return (view == null || (handler = view.getHandler()) == null || handler.getLooper() == null || !handler.getLooper().isCurrentThread() || (viewRootImpl = view.getViewRootImpl()) == null || !viewRootImpl.isHandlingPointerEvent()) ? false : true;
    }

    static android.view.inputmethod.ImeTracker forLogging() {
        return LOGGER;
    }

    static android.view.inputmethod.ImeTracker.ImeJankTracker forJank() {
        return JANK_TRACKER;
    }

    static android.view.inputmethod.ImeTracker.ImeLatencyTracker forLatency() {
        return LATENCY_TRACKER;
    }

    /* renamed from: android.view.inputmethod.ImeTracker$1, reason: invalid class name */
    class AnonymousClass1 implements android.view.inputmethod.ImeTracker {
        private boolean mLogProgress;
        private boolean mLogStackTrace;

        AnonymousClass1() {
            reloadSystemProperties();
            android.os.SystemProperties.addChangeCallback(new java.lang.Runnable() { // from class: android.view.inputmethod.ImeTracker$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.view.inputmethod.ImeTracker.AnonymousClass1.this.reloadSystemProperties();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void reloadSystemProperties() {
            this.mLogProgress = android.os.SystemProperties.getBoolean("persist.debug.imetracker", false);
            this.mLogStackTrace = android.os.SystemProperties.getBoolean("persist.debug.imerequest.logstacktrace", false);
        }

        @Override // android.view.inputmethod.ImeTracker
        public android.view.inputmethod.ImeTracker.Token onStart(java.lang.String str, int i, int i2, int i3, int i4, boolean z) {
            android.view.inputmethod.ImeTracker.Token onStart = android.view.inputmethod.IInputMethodManagerGlobalInvoker.onStart(android.view.inputmethod.ImeTracker.Token.createTag(str), i, i2, i3, i4, z);
            android.util.Log.i(android.view.inputmethod.ImeTracker.TAG, onStart.mTag + ": onRequest" + (i2 == 1 ? "Show" : "Hide") + " at " + android.view.inputmethod.ImeTracker.Debug.originToString(i3) + " reason " + com.android.internal.inputmethod.InputMethodDebug.softInputDisplayReasonToString(i4) + " fromUser " + z, this.mLogStackTrace ? new java.lang.Throwable() : null);
            return onStart;
        }

        @Override // android.view.inputmethod.ImeTracker
        public void onProgress(android.view.inputmethod.ImeTracker.Token token, int i) {
            if (token == null) {
                return;
            }
            android.view.inputmethod.IInputMethodManagerGlobalInvoker.onProgress(token.mBinder, i);
            if (this.mLogProgress) {
                android.util.Log.i(android.view.inputmethod.ImeTracker.TAG, token.mTag + ": onProgress at " + android.view.inputmethod.ImeTracker.Debug.phaseToString(i));
            }
        }

        @Override // android.view.inputmethod.ImeTracker
        public void onFailed(android.view.inputmethod.ImeTracker.Token token, int i) {
            if (token == null) {
                return;
            }
            android.view.inputmethod.IInputMethodManagerGlobalInvoker.onFailed(token, i);
            android.util.Log.i(android.view.inputmethod.ImeTracker.TAG, token.mTag + ": onFailed at " + android.view.inputmethod.ImeTracker.Debug.phaseToString(i));
        }

        @Override // android.view.inputmethod.ImeTracker
        public void onTodo(android.view.inputmethod.ImeTracker.Token token, int i) {
            if (token == null) {
                return;
            }
            android.util.Log.i(android.view.inputmethod.ImeTracker.TAG, token.mTag + ": onTodo at " + android.view.inputmethod.ImeTracker.Debug.phaseToString(i));
        }

        @Override // android.view.inputmethod.ImeTracker
        public void onCancelled(android.view.inputmethod.ImeTracker.Token token, int i) {
            if (token == null) {
                return;
            }
            android.view.inputmethod.IInputMethodManagerGlobalInvoker.onCancelled(token, i);
            android.util.Log.i(android.view.inputmethod.ImeTracker.TAG, token.mTag + ": onCancelled at " + android.view.inputmethod.ImeTracker.Debug.phaseToString(i));
        }

        @Override // android.view.inputmethod.ImeTracker
        public void onShown(android.view.inputmethod.ImeTracker.Token token) {
            if (token == null) {
                return;
            }
            android.view.inputmethod.IInputMethodManagerGlobalInvoker.onShown(token);
            android.util.Log.i(android.view.inputmethod.ImeTracker.TAG, token.mTag + ": onShown");
        }

        @Override // android.view.inputmethod.ImeTracker
        public void onHidden(android.view.inputmethod.ImeTracker.Token token) {
            if (token == null) {
                return;
            }
            android.view.inputmethod.IInputMethodManagerGlobalInvoker.onHidden(token);
            android.util.Log.i(android.view.inputmethod.ImeTracker.TAG, token.mTag + ": onHidden");
        }
    }

    public static final class Token implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.view.inputmethod.ImeTracker.Token> CREATOR = new android.os.Parcelable.Creator<android.view.inputmethod.ImeTracker.Token>() { // from class: android.view.inputmethod.ImeTracker.Token.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.view.inputmethod.ImeTracker.Token createFromParcel(android.os.Parcel parcel) {
                return new android.view.inputmethod.ImeTracker.Token(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.view.inputmethod.ImeTracker.Token[] newArray(int i) {
                return new android.view.inputmethod.ImeTracker.Token[i];
            }
        };
        private static android.os.IBinder sEmptyBinder;
        private final android.os.IBinder mBinder;
        private final java.lang.String mTag;

        public Token(android.os.IBinder iBinder, java.lang.String str) {
            this.mBinder = iBinder;
            this.mTag = str;
        }

        private Token(android.os.Parcel parcel) {
            this.mBinder = parcel.readStrongBinder();
            this.mTag = parcel.readString8();
        }

        public android.os.IBinder getBinder() {
            return this.mBinder;
        }

        public java.lang.String getTag() {
            return this.mTag;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static java.lang.String createTag(java.lang.String str) {
            return str + ":" + java.lang.Integer.toHexString(java.util.concurrent.ThreadLocalRandom.current().nextInt());
        }

        public static android.view.inputmethod.ImeTracker.Token empty() {
            return empty(createTag(android.os.Process.myProcessName()));
        }

        static android.view.inputmethod.ImeTracker.Token empty(java.lang.String str) {
            return new android.view.inputmethod.ImeTracker.Token(getEmptyBinder(), str);
        }

        private static android.os.IBinder getEmptyBinder() {
            if (sEmptyBinder == null) {
                sEmptyBinder = new android.os.Binder();
            }
            return sEmptyBinder;
        }

        public java.lang.String toString() {
            return super.toString() + "(tag: " + this.mTag + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeStrongBinder(this.mBinder);
            parcel.writeString8(this.mTag);
        }
    }

    public static final class Debug {
        private static final java.util.Map<java.lang.Integer, java.lang.String> sTypes = getFieldMapping(android.view.inputmethod.ImeTracker.class, "TYPE_");
        private static final java.util.Map<java.lang.Integer, java.lang.String> sStatus = getFieldMapping(android.view.inputmethod.ImeTracker.class, "STATUS_");
        private static final java.util.Map<java.lang.Integer, java.lang.String> sOrigins = getFieldMapping(android.view.inputmethod.ImeTracker.class, "ORIGIN_");
        private static final java.util.Map<java.lang.Integer, java.lang.String> sPhases = getFieldMapping(android.view.inputmethod.ImeTracker.class, "PHASE_");

        public static java.lang.String typeToString(int i) {
            return sTypes.getOrDefault(java.lang.Integer.valueOf(i), "TYPE_" + i);
        }

        public static java.lang.String statusToString(int i) {
            return sStatus.getOrDefault(java.lang.Integer.valueOf(i), "STATUS_" + i);
        }

        public static java.lang.String originToString(int i) {
            return sOrigins.getOrDefault(java.lang.Integer.valueOf(i), "ORIGIN_" + i);
        }

        public static java.lang.String phaseToString(int i) {
            return sPhases.getOrDefault(java.lang.Integer.valueOf(i), "PHASE_" + i);
        }

        private static java.util.Map<java.lang.Integer, java.lang.String> getFieldMapping(java.lang.Class<?> cls, final java.lang.String str) {
            return (java.util.Map) java.util.Arrays.stream(cls.getDeclaredFields()).filter(new java.util.function.Predicate() { // from class: android.view.inputmethod.ImeTracker$Debug$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    boolean startsWith;
                    startsWith = ((java.lang.reflect.Field) obj).getName().startsWith(str);
                    return startsWith;
                }
            }).collect(java.util.stream.Collectors.toMap(new java.util.function.Function() { // from class: android.view.inputmethod.ImeTracker$Debug$$ExternalSyntheticLambda1
                @Override // java.util.function.Function
                public final java.lang.Object apply(java.lang.Object obj) {
                    int fieldValue;
                    fieldValue = android.view.inputmethod.ImeTracker.Debug.getFieldValue((java.lang.reflect.Field) obj);
                    return java.lang.Integer.valueOf(fieldValue);
                }
            }, new java.util.function.Function() { // from class: android.view.inputmethod.ImeTracker$Debug$$ExternalSyntheticLambda2
                @Override // java.util.function.Function
                public final java.lang.Object apply(java.lang.Object obj) {
                    return ((java.lang.reflect.Field) obj).getName();
                }
            }));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static int getFieldValue(java.lang.reflect.Field field) {
            try {
                return field.getInt(null);
            } catch (java.lang.IllegalAccessException e) {
                throw new java.lang.RuntimeException(e);
            }
        }
    }

    public static final class ImeJankTracker {
        private ImeJankTracker() {
        }

        public void onRequestAnimation(android.view.inputmethod.ImeTracker.InputMethodJankContext inputMethodJankContext, int i, boolean z) {
            int imeInsetsCujFromAnimation = getImeInsetsCujFromAnimation(i);
            if (inputMethodJankContext.getDisplayContext() == null || inputMethodJankContext.getTargetSurfaceControl() == null || imeInsetsCujFromAnimation == -1) {
                return;
            }
            com.android.internal.jank.InteractionJankMonitor.getInstance().begin(com.android.internal.jank.InteractionJankMonitor.Configuration.Builder.withSurface(imeInsetsCujFromAnimation, inputMethodJankContext.getDisplayContext(), inputMethodJankContext.getTargetSurfaceControl()).setTag(java.lang.String.format(java.util.Locale.US, "%d@%d@%s", java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(!z ? 1 : 0), inputMethodJankContext.getHostPackageName())));
        }

        public void onCancelAnimation(int i) {
            int imeInsetsCujFromAnimation = getImeInsetsCujFromAnimation(i);
            if (imeInsetsCujFromAnimation != -1) {
                com.android.internal.jank.InteractionJankMonitor.getInstance().cancel(imeInsetsCujFromAnimation);
            }
        }

        public void onFinishAnimation(int i) {
            int imeInsetsCujFromAnimation = getImeInsetsCujFromAnimation(i);
            if (imeInsetsCujFromAnimation != -1) {
                com.android.internal.jank.InteractionJankMonitor.getInstance().end(imeInsetsCujFromAnimation);
            }
        }

        private static int getImeInsetsCujFromAnimation(int i) {
            switch (i) {
                case 0:
                    return 80;
                case 1:
                    return 81;
                default:
                    return -1;
            }
        }
    }

    public static final class ImeLatencyTracker {
        private ImeLatencyTracker() {
        }

        private boolean shouldMonitorLatency(int i) {
            return i == 1 || i == 4 || i == 39 || i == 26 || i == 28 || i == 3 || i == 5;
        }

        public void onRequestShow(android.view.inputmethod.ImeTracker.Token token, int i, int i2, android.view.inputmethod.ImeTracker.InputMethodLatencyContext inputMethodLatencyContext) {
            if (shouldMonitorLatency(i2)) {
                com.android.internal.util.LatencyTracker.getInstance(inputMethodLatencyContext.getAppContext()).onActionStart(20, com.android.internal.inputmethod.InputMethodDebug.softInputDisplayReasonToString(i2));
            }
        }

        public void onRequestHide(android.view.inputmethod.ImeTracker.Token token, int i, int i2, android.view.inputmethod.ImeTracker.InputMethodLatencyContext inputMethodLatencyContext) {
            if (shouldMonitorLatency(i2)) {
                com.android.internal.util.LatencyTracker.getInstance(inputMethodLatencyContext.getAppContext()).onActionStart(21, com.android.internal.inputmethod.InputMethodDebug.softInputDisplayReasonToString(i2));
            }
        }

        public void onShowFailed(android.view.inputmethod.ImeTracker.Token token, int i, android.view.inputmethod.ImeTracker.InputMethodLatencyContext inputMethodLatencyContext) {
            onShowCancelled(token, i, inputMethodLatencyContext);
        }

        public void onHideFailed(android.view.inputmethod.ImeTracker.Token token, int i, android.view.inputmethod.ImeTracker.InputMethodLatencyContext inputMethodLatencyContext) {
            onHideCancelled(token, i, inputMethodLatencyContext);
        }

        public void onShowCancelled(android.view.inputmethod.ImeTracker.Token token, int i, android.view.inputmethod.ImeTracker.InputMethodLatencyContext inputMethodLatencyContext) {
            com.android.internal.util.LatencyTracker.getInstance(inputMethodLatencyContext.getAppContext()).lambda$onActionStart$1(20);
        }

        public void onHideCancelled(android.view.inputmethod.ImeTracker.Token token, int i, android.view.inputmethod.ImeTracker.InputMethodLatencyContext inputMethodLatencyContext) {
            com.android.internal.util.LatencyTracker.getInstance(inputMethodLatencyContext.getAppContext()).lambda$onActionStart$1(21);
        }

        public void onShown(android.view.inputmethod.ImeTracker.Token token, android.view.inputmethod.ImeTracker.InputMethodLatencyContext inputMethodLatencyContext) {
            com.android.internal.util.LatencyTracker.getInstance(inputMethodLatencyContext.getAppContext()).onActionEnd(20);
        }

        public void onHidden(android.view.inputmethod.ImeTracker.Token token, android.view.inputmethod.ImeTracker.InputMethodLatencyContext inputMethodLatencyContext) {
            com.android.internal.util.LatencyTracker.getInstance(inputMethodLatencyContext.getAppContext()).onActionEnd(21);
        }
    }
}
