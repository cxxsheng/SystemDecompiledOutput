package com.android.server.inputmethod;

/* loaded from: classes2.dex */
public final class InputMethodManagerService extends com.android.internal.view.IInputMethodManager.Stub implements android.os.Handler.Callback {
    static final boolean DEBUG = false;
    static final int FALLBACK_DISPLAY_ID = 0;
    private static final java.lang.String HANDLER_THREAD_NAME = "android.imms";
    private static final int MSG_DISPATCH_ON_INPUT_METHOD_LIST_UPDATED = 5010;
    private static final int MSG_FINISH_HANDWRITING = 1110;
    private static final int MSG_HARD_KEYBOARD_SWITCH_CHANGED = 4000;
    private static final int MSG_HIDE_ALL_INPUT_METHODS = 1035;
    private static final int MSG_NOTIFY_IME_UID_TO_AUDIO_SERVICE = 7000;
    private static final int MSG_PREPARE_HANDWRITING_DELEGATION = 1130;
    private static final int MSG_REMOVE_HANDWRITING_WINDOW = 1120;
    private static final int MSG_REMOVE_IME_SURFACE = 1060;
    private static final int MSG_REMOVE_IME_SURFACE_FROM_WINDOW = 1061;
    private static final int MSG_RESET_HANDWRITING = 1090;
    private static final int MSG_SET_INTERACTIVE = 3030;
    private static final int MSG_SHOW_IM_SUBTYPE_PICKER = 1;
    private static final int MSG_START_HANDWRITING = 1100;
    private static final int MSG_SYSTEM_UNLOCK_USER = 5000;
    private static final int MSG_UPDATE_IME_WINDOW_STATUS = 1070;
    private static final int NOT_A_SUBTYPE_ID = -1;
    public static final java.lang.String PROTO_ARG = "--proto";
    static final java.lang.String TAG = "InputMethodManagerService";
    private static final java.lang.String TAG_TRY_SUPPRESSING_IME_SWITCHER = "TrySuppressingImeSwitcher";
    private static final java.lang.Integer VIRTUAL_STYLUS_ID_FOR_TEST = 999999;
    private final android.app.ActivityManagerInternal mActivityManagerInternal;

    @android.annotation.NonNull
    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    private com.android.server.inputmethod.AdditionalSubtypeMap mAdditionalSubtypeMap;

    @android.annotation.Nullable
    private android.media.AudioManagerInternal mAudioManagerInternal;

    @android.annotation.NonNull
    private final com.android.server.inputmethod.AutofillSuggestionsController mAutofillController;
    int mBackDisposition;

    @android.annotation.NonNull
    private final com.android.server.inputmethod.InputMethodBindingController mBindingController;
    boolean mBoundToAccessibility;
    boolean mBoundToMethod;
    private final com.android.server.inputmethod.ClientController mClientController;
    final android.content.Context mContext;

    @android.annotation.Nullable
    private com.android.server.inputmethod.ClientState mCurClient;

    @android.annotation.Nullable
    android.view.inputmethod.EditorInfo mCurEditorInfo;
    android.os.IBinder mCurFocusedWindow;

    @android.annotation.Nullable
    com.android.server.inputmethod.ClientState mCurFocusedWindowClient;

    @android.annotation.Nullable
    android.view.inputmethod.EditorInfo mCurFocusedWindowEditorInfo;
    int mCurFocusedWindowSoftInputMode;

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    @android.annotation.Nullable
    private android.os.IBinder mCurHostInputToken;
    android.window.ImeOnBackInvokedDispatcher mCurImeDispatcher;
    com.android.internal.inputmethod.IRemoteInputConnection mCurInputConnection;
    private boolean mCurPerceptible;

    @android.annotation.Nullable
    com.android.internal.inputmethod.IRemoteAccessibilityInputConnection mCurRemoteAccessibilityInputConnection;

    @android.annotation.Nullable
    private android.view.inputmethod.ImeTracker.Token mCurStatsToken;

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    private int mCurTokenDisplayId;
    private android.view.inputmethod.InputMethodSubtype mCurrentSubtype;

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    private int mDeviceIdToShowIme;

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    private int mDisplayIdToShowIme;
    android.util.SparseArray<com.android.server.inputmethod.InputMethodManagerService.AccessibilitySessionState> mEnabledAccessibilitySessions;

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    com.android.server.inputmethod.InputMethodManagerService.SessionState mEnabledSession;
    private final android.os.Handler mHandler;

    @android.annotation.NonNull
    private com.android.server.inputmethod.HardwareKeyboardShortcutController mHardwareKeyboardShortcutController;

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    private final com.android.server.inputmethod.HandwritingModeController mHwController;

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    @android.annotation.Nullable
    private com.android.server.inputmethod.OverlayableSystemBooleanResourceWrapper mImeDrawsImeNavBarRes;

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    @android.annotation.Nullable
    java.util.concurrent.Future<?> mImeDrawsImeNavBarResLazyInitFuture;
    final com.android.server.inputmethod.ImePlatformCompatUtils mImePlatformCompatUtils;

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    private final java.util.WeakHashMap<android.os.IBinder, android.os.IBinder> mImeTargetWindowMap;

    @android.annotation.NonNull
    private final com.android.server.inputmethod.ImeTrackerService mImeTrackerService;
    int mImeWindowVis;
    boolean mInFullscreenMode;
    final com.android.server.input.InputManagerInternal mInputManagerInternal;
    final com.android.server.inputmethod.InputMethodDeviceConfigs mInputMethodDeviceConfigs;
    private final java.util.concurrent.CopyOnWriteArrayList<com.android.server.inputmethod.InputMethodManagerInternal.InputMethodListListener> mInputMethodListListeners;
    boolean mIsInteractive;
    android.os.IBinder mLastImeTargetWindow;
    private int mLastSwitchUserId;
    private lineageos.hardware.LineageHardwareManager mLineageHardware;
    private final com.android.server.inputmethod.InputMethodMenuController mMenuController;

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    private int mMethodMapUpdateCount;
    private final com.android.server.inputmethod.InputMethodManagerService.MyPackageMonitor mMyPackageMonitor;

    @android.annotation.NonNull
    private final java.lang.String[] mNonPreemptibleInputMethods;
    final android.content.pm.PackageManagerInternal mPackageManagerInternal;
    private final boolean mPreventImeStartupUnlessTextEditor;
    private final com.android.server.utils.PriorityDump.PriorityDumper mPriorityDumper;
    final android.content.res.Resources mRes;

    @android.annotation.NonNull
    private com.android.server.inputmethod.InputMethodSettings mSettings;
    final com.android.server.inputmethod.InputMethodManagerService.SettingsObserver mSettingsObserver;
    private boolean mShowOngoingImeSwitcherForPhones;
    private final java.lang.String mSlotIme;

    @android.annotation.NonNull
    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    private final com.android.server.inputmethod.InputMethodManagerService.SoftInputShowHideHistory mSoftInputShowHideHistory;

    @android.annotation.NonNull
    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    private final com.android.server.inputmethod.InputMethodManagerService.StartInputHistory mStartInputHistory;

    @android.annotation.Nullable
    private com.android.server.statusbar.StatusBarManagerInternal mStatusBarManagerInternal;

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    private android.util.IntArray mStylusIds;

    @android.annotation.NonNull
    private com.android.server.inputmethod.InputMethodSubtypeSwitchingController mSwitchingController;
    boolean mSystemReady;
    private final com.android.server.pm.UserManagerInternal mUserManagerInternal;

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    @android.annotation.Nullable
    private com.android.server.inputmethod.InputMethodManagerService.UserSwitchHandlerTask mUserSwitchHandlerTask;

    @android.annotation.Nullable
    private com.android.server.companion.virtual.VirtualDeviceManagerInternal mVdmInternal;

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    private final android.util.SparseArray<java.lang.String> mVirtualDeviceMethodMap;

    @android.annotation.NonNull
    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    private final com.android.server.inputmethod.DefaultImeVisibilityApplier mVisibilityApplier;

    @android.annotation.NonNull
    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    private final com.android.server.inputmethod.ImeVisibilityStateComputer mVisibilityStateComputer;
    final com.android.server.wm.WindowManagerInternal mWindowManagerInternal;

    @java.lang.FunctionalInterface
    interface ImeDisplayValidator {
        int getDisplayImePolicy(int i);
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    private @interface ShellCommandResult {
        public static final int FAILURE = -1;
        public static final int SUCCESS = 0;
    }

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    int getDisplayIdToShowImeLocked() {
        return this.mDisplayIdToShowIme;
    }

    static class SessionState {
        android.view.InputChannel mChannel;
        final com.android.server.inputmethod.ClientState mClient;
        final com.android.server.inputmethod.IInputMethodInvoker mMethod;
        com.android.internal.inputmethod.IInputMethodSession mSession;

        public java.lang.String toString() {
            return "SessionState{uid=" + this.mClient.mUid + " pid=" + this.mClient.mPid + " method=" + java.lang.Integer.toHexString(com.android.server.inputmethod.IInputMethodInvoker.getBinderIdentityHashCode(this.mMethod)) + " session=" + java.lang.Integer.toHexString(java.lang.System.identityHashCode(this.mSession)) + " channel=" + this.mChannel + "}";
        }

        SessionState(com.android.server.inputmethod.ClientState clientState, com.android.server.inputmethod.IInputMethodInvoker iInputMethodInvoker, com.android.internal.inputmethod.IInputMethodSession iInputMethodSession, android.view.InputChannel inputChannel) {
            this.mClient = clientState;
            this.mMethod = iInputMethodInvoker;
            this.mSession = iInputMethodSession;
            this.mChannel = inputChannel;
        }
    }

    static class AccessibilitySessionState {
        final com.android.server.inputmethod.ClientState mClient;
        final int mId;
        public com.android.internal.inputmethod.IAccessibilityInputMethodSession mSession;

        public java.lang.String toString() {
            return "AccessibilitySessionState{uid=" + this.mClient.mUid + " pid=" + this.mClient.mPid + " id=" + java.lang.Integer.toHexString(this.mId) + " session=" + java.lang.Integer.toHexString(java.lang.System.identityHashCode(this.mSession)) + "}";
        }

        AccessibilitySessionState(com.android.server.inputmethod.ClientState clientState, int i, com.android.internal.inputmethod.IAccessibilityInputMethodSession iAccessibilityInputMethodSession) {
            this.mClient = clientState;
            this.mId = i;
            this.mSession = iAccessibilityInputMethodSession;
        }
    }

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    @android.annotation.Nullable
    java.lang.String getSelectedMethodIdLocked() {
        return this.mBindingController.getSelectedMethodId();
    }

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    private void setSelectedMethodIdLocked(@android.annotation.Nullable java.lang.String str) {
        this.mBindingController.setSelectedMethodId(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    public int getSequenceNumberLocked() {
        return this.mBindingController.getSequenceNumber();
    }

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    private void advanceSequenceNumberLocked() {
        this.mBindingController.advanceSequenceNumber();
    }

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    @android.annotation.Nullable
    android.view.inputmethod.InputMethodInfo queryInputMethodForCurrentUserLocked(@android.annotation.NonNull java.lang.String str) {
        return this.mSettings.getMethodMap().get(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    @android.annotation.Nullable
    public java.lang.String getCurIdLocked() {
        return this.mBindingController.getCurId();
    }

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    private boolean hasConnectionLocked() {
        return this.mBindingController.hasMainConnection();
    }

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    @android.annotation.Nullable
    private android.content.Intent getCurIntentLocked() {
        return this.mBindingController.getCurIntent();
    }

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    @android.annotation.Nullable
    android.os.IBinder getCurTokenLocked() {
        return this.mBindingController.getCurToken();
    }

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    int getCurTokenDisplayIdLocked() {
        return this.mCurTokenDisplayId;
    }

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    void setCurTokenDisplayIdLocked(int i) {
        this.mCurTokenDisplayId = i;
    }

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    @android.annotation.Nullable
    com.android.server.inputmethod.IInputMethodInvoker getCurMethodLocked() {
        return this.mBindingController.getCurMethod();
    }

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    private int getCurMethodUidLocked() {
        return this.mBindingController.getCurMethodUid();
    }

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    private long getLastBindTimeLocked() {
        return this.mBindingController.getLastBindTime();
    }

    private static class StartInputInfo {
        private static final java.util.concurrent.atomic.AtomicInteger sSequenceNumber = new java.util.concurrent.atomic.AtomicInteger(0);
        final int mClientBindSequenceNumber;

        @android.annotation.NonNull
        final android.view.inputmethod.EditorInfo mEditorInfo;
        final int mImeDisplayId;

        @android.annotation.NonNull
        final java.lang.String mImeId;

        @android.annotation.NonNull
        final android.os.IBinder mImeToken;
        final int mImeUserId;
        final boolean mRestarting;
        final int mStartInputReason;
        final int mTargetDisplayId;
        final int mTargetUserId;

        @android.annotation.Nullable
        final android.os.IBinder mTargetWindow;
        final int mTargetWindowSoftInputMode;
        final int mSequenceNumber = sSequenceNumber.getAndIncrement();
        final long mTimestamp = android.os.SystemClock.uptimeMillis();
        final long mWallTime = java.lang.System.currentTimeMillis();

        StartInputInfo(int i, @android.annotation.NonNull android.os.IBinder iBinder, int i2, @android.annotation.NonNull java.lang.String str, int i3, boolean z, int i4, int i5, @android.annotation.Nullable android.os.IBinder iBinder2, @android.annotation.NonNull android.view.inputmethod.EditorInfo editorInfo, int i6, int i7) {
            this.mImeUserId = i;
            this.mImeToken = iBinder;
            this.mImeDisplayId = i2;
            this.mImeId = str;
            this.mStartInputReason = i3;
            this.mRestarting = z;
            this.mTargetUserId = i4;
            this.mTargetDisplayId = i5;
            this.mTargetWindow = iBinder2;
            this.mEditorInfo = editorInfo;
            this.mTargetWindowSoftInputMode = i6;
            this.mClientBindSequenceNumber = i7;
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    static final class SoftInputShowHideHistory {
        private static final java.util.concurrent.atomic.AtomicInteger sSequenceNumber = new java.util.concurrent.atomic.AtomicInteger(0);
        private final com.android.server.inputmethod.InputMethodManagerService.SoftInputShowHideHistory.Entry[] mEntries = new com.android.server.inputmethod.InputMethodManagerService.SoftInputShowHideHistory.Entry[16];
        private int mNextIndex = 0;

        SoftInputShowHideHistory() {
        }

        static final class Entry {

            @android.annotation.Nullable
            final com.android.server.inputmethod.ClientState mClientState;

            @android.annotation.Nullable
            final android.view.inputmethod.EditorInfo mEditorInfo;

            @android.annotation.NonNull
            final java.lang.String mFocusedWindowName;
            final int mFocusedWindowSoftInputMode;

            @android.annotation.Nullable
            final java.lang.String mImeControlTargetName;

            @android.annotation.Nullable
            final java.lang.String mImeSurfaceParentName;

            @android.annotation.Nullable
            final java.lang.String mImeTargetNameFromWm;
            final boolean mInFullscreenMode;
            final int mReason;

            @android.annotation.NonNull
            final java.lang.String mRequestWindowName;
            final int mSequenceNumber = com.android.server.inputmethod.InputMethodManagerService.SoftInputShowHideHistory.sSequenceNumber.getAndIncrement();
            final long mTimestamp = android.os.SystemClock.uptimeMillis();
            final long mWallTime = java.lang.System.currentTimeMillis();

            Entry(com.android.server.inputmethod.ClientState clientState, android.view.inputmethod.EditorInfo editorInfo, java.lang.String str, int i, int i2, boolean z, java.lang.String str2, @android.annotation.Nullable java.lang.String str3, @android.annotation.Nullable java.lang.String str4, @android.annotation.Nullable java.lang.String str5) {
                this.mClientState = clientState;
                this.mEditorInfo = editorInfo;
                this.mFocusedWindowName = str;
                this.mFocusedWindowSoftInputMode = i;
                this.mReason = i2;
                this.mInFullscreenMode = z;
                this.mRequestWindowName = str2;
                this.mImeControlTargetName = str3;
                this.mImeTargetNameFromWm = str4;
                this.mImeSurfaceParentName = str5;
            }
        }

        void addEntry(@android.annotation.NonNull com.android.server.inputmethod.InputMethodManagerService.SoftInputShowHideHistory.Entry entry) {
            this.mEntries[this.mNextIndex] = entry;
            this.mNextIndex = (this.mNextIndex + 1) % this.mEntries.length;
        }

        void dump(@android.annotation.NonNull java.io.PrintWriter printWriter, @android.annotation.NonNull java.lang.String str) {
            java.time.format.DateTimeFormatter withZone = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS", java.util.Locale.US).withZone(java.time.ZoneId.systemDefault());
            for (int i = 0; i < this.mEntries.length; i++) {
                com.android.server.inputmethod.InputMethodManagerService.SoftInputShowHideHistory.Entry entry = this.mEntries[(this.mNextIndex + i) % this.mEntries.length];
                if (entry != null) {
                    printWriter.print(str);
                    printWriter.println("SoftInputShowHide #" + entry.mSequenceNumber + ":");
                    printWriter.print(str);
                    printWriter.println("  time=" + withZone.format(java.time.Instant.ofEpochMilli(entry.mWallTime)) + " (timestamp=" + entry.mTimestamp + ")");
                    printWriter.print(str);
                    java.lang.StringBuilder sb = new java.lang.StringBuilder();
                    sb.append("  reason=");
                    sb.append(com.android.internal.inputmethod.InputMethodDebug.softInputDisplayReasonToString(entry.mReason));
                    printWriter.print(sb.toString());
                    printWriter.println(" inFullscreenMode=" + entry.mInFullscreenMode);
                    printWriter.print(str);
                    printWriter.println("  requestClient=" + entry.mClientState);
                    printWriter.print(str);
                    printWriter.println("  focusedWindowName=" + entry.mFocusedWindowName);
                    printWriter.print(str);
                    printWriter.println("  requestWindowName=" + entry.mRequestWindowName);
                    printWriter.print(str);
                    printWriter.println("  imeControlTargetName=" + entry.mImeControlTargetName);
                    printWriter.print(str);
                    printWriter.println("  imeTargetNameFromWm=" + entry.mImeTargetNameFromWm);
                    printWriter.print(str);
                    printWriter.println("  imeSurfaceParentName=" + entry.mImeSurfaceParentName);
                    printWriter.print(str);
                    printWriter.print("  editorInfo:");
                    if (entry.mEditorInfo != null) {
                        printWriter.print(" inputType=" + entry.mEditorInfo.inputType);
                        printWriter.print(" privateImeOptions=" + entry.mEditorInfo.privateImeOptions);
                        printWriter.println(" fieldId (viewId)=" + entry.mEditorInfo.fieldId);
                    } else {
                        printWriter.println(" null");
                    }
                    printWriter.print(str);
                    printWriter.println("  focusedWindowSoftInputMode=" + com.android.internal.inputmethod.InputMethodDebug.softInputModeToString(entry.mFocusedWindowSoftInputMode));
                }
            }
        }
    }

    private static final class StartInputHistory {
        private static final int ENTRY_SIZE_FOR_HIGH_RAM_DEVICE = 32;
        private static final int ENTRY_SIZE_FOR_LOW_RAM_DEVICE = 5;
        private final com.android.server.inputmethod.InputMethodManagerService.StartInputHistory.Entry[] mEntries;
        private int mNextIndex;

        private StartInputHistory() {
            this.mEntries = new com.android.server.inputmethod.InputMethodManagerService.StartInputHistory.Entry[getEntrySize()];
            this.mNextIndex = 0;
        }

        private static int getEntrySize() {
            if (android.app.ActivityManager.isLowRamDeviceStatic()) {
                return 5;
            }
            return 32;
        }

        private static final class Entry {
            int mClientBindSequenceNumber;

            @android.annotation.NonNull
            android.view.inputmethod.EditorInfo mEditorInfo;
            int mImeDisplayId;

            @android.annotation.NonNull
            java.lang.String mImeId;

            @android.annotation.NonNull
            java.lang.String mImeTokenString;
            int mImeUserId;
            boolean mRestarting;
            int mSequenceNumber;
            int mStartInputReason;
            int mTargetDisplayId;
            int mTargetUserId;
            int mTargetWindowSoftInputMode;

            @android.annotation.NonNull
            java.lang.String mTargetWindowString;
            long mTimestamp;
            long mWallTime;

            Entry(@android.annotation.NonNull com.android.server.inputmethod.InputMethodManagerService.StartInputInfo startInputInfo) {
                set(startInputInfo);
            }

            void set(@android.annotation.NonNull com.android.server.inputmethod.InputMethodManagerService.StartInputInfo startInputInfo) {
                this.mSequenceNumber = startInputInfo.mSequenceNumber;
                this.mTimestamp = startInputInfo.mTimestamp;
                this.mWallTime = startInputInfo.mWallTime;
                this.mImeUserId = startInputInfo.mImeUserId;
                this.mImeTokenString = java.lang.String.valueOf(startInputInfo.mImeToken);
                this.mImeDisplayId = startInputInfo.mImeDisplayId;
                this.mImeId = startInputInfo.mImeId;
                this.mStartInputReason = startInputInfo.mStartInputReason;
                this.mRestarting = startInputInfo.mRestarting;
                this.mTargetUserId = startInputInfo.mTargetUserId;
                this.mTargetDisplayId = startInputInfo.mTargetDisplayId;
                this.mTargetWindowString = java.lang.String.valueOf(startInputInfo.mTargetWindow);
                this.mEditorInfo = startInputInfo.mEditorInfo;
                this.mTargetWindowSoftInputMode = startInputInfo.mTargetWindowSoftInputMode;
                this.mClientBindSequenceNumber = startInputInfo.mClientBindSequenceNumber;
            }
        }

        void addEntry(@android.annotation.NonNull com.android.server.inputmethod.InputMethodManagerService.StartInputInfo startInputInfo) {
            int i = this.mNextIndex;
            if (this.mEntries[i] == null) {
                this.mEntries[i] = new com.android.server.inputmethod.InputMethodManagerService.StartInputHistory.Entry(startInputInfo);
            } else {
                this.mEntries[i].set(startInputInfo);
            }
            this.mNextIndex = (this.mNextIndex + 1) % this.mEntries.length;
        }

        void dump(@android.annotation.NonNull java.io.PrintWriter printWriter, @android.annotation.NonNull java.lang.String str) {
            java.time.format.DateTimeFormatter withZone = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS", java.util.Locale.US).withZone(java.time.ZoneId.systemDefault());
            for (int i = 0; i < this.mEntries.length; i++) {
                com.android.server.inputmethod.InputMethodManagerService.StartInputHistory.Entry entry = this.mEntries[(this.mNextIndex + i) % this.mEntries.length];
                if (entry != null) {
                    printWriter.print(str);
                    printWriter.println("StartInput #" + entry.mSequenceNumber + ":");
                    printWriter.print(str);
                    printWriter.println("  time=" + withZone.format(java.time.Instant.ofEpochMilli(entry.mWallTime)) + " (timestamp=" + entry.mTimestamp + ") reason=" + com.android.internal.inputmethod.InputMethodDebug.startInputReasonToString(entry.mStartInputReason) + " restarting=" + entry.mRestarting);
                    printWriter.print(str);
                    java.lang.StringBuilder sb = new java.lang.StringBuilder();
                    sb.append("  imeToken=");
                    sb.append(entry.mImeTokenString);
                    sb.append(" [");
                    sb.append(entry.mImeId);
                    sb.append("]");
                    printWriter.print(sb.toString());
                    printWriter.print(" imeUserId=" + entry.mImeUserId);
                    printWriter.println(" imeDisplayId=" + entry.mImeDisplayId);
                    printWriter.print(str);
                    printWriter.println("  targetWin=" + entry.mTargetWindowString + " [" + entry.mEditorInfo.packageName + "] targetUserId=" + entry.mTargetUserId + " targetDisplayId=" + entry.mTargetDisplayId + " clientBindSeq=" + entry.mClientBindSequenceNumber);
                    printWriter.print(str);
                    java.lang.StringBuilder sb2 = new java.lang.StringBuilder();
                    sb2.append("  softInputMode=");
                    sb2.append(com.android.internal.inputmethod.InputMethodDebug.softInputModeToString(entry.mTargetWindowSoftInputMode));
                    printWriter.println(sb2.toString());
                    printWriter.print(str);
                    printWriter.println("  inputType=0x" + java.lang.Integer.toHexString(entry.mEditorInfo.inputType) + " imeOptions=0x" + java.lang.Integer.toHexString(entry.mEditorInfo.imeOptions) + " fieldId=0x" + java.lang.Integer.toHexString(entry.mEditorInfo.fieldId) + " fieldName=" + entry.mEditorInfo.fieldName + " actionId=" + entry.mEditorInfo.actionId + " actionLabel=" + ((java.lang.Object) entry.mEditorInfo.actionLabel));
                }
            }
        }
    }

    class SettingsObserver extends android.database.ContentObserver {

        @android.annotation.NonNull
        java.lang.String mLastEnabled;
        boolean mRegistered;
        int mUserId;

        SettingsObserver(android.os.Handler handler) {
            super(handler);
            this.mRegistered = false;
            this.mLastEnabled = "";
        }

        @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
        public void registerContentObserverLocked(int i) {
            if (this.mRegistered && this.mUserId == i) {
                return;
            }
            android.content.ContentResolver contentResolver = com.android.server.inputmethod.InputMethodManagerService.this.mContext.getContentResolver();
            if (this.mRegistered) {
                com.android.server.inputmethod.InputMethodManagerService.this.mContext.getContentResolver().unregisterContentObserver(this);
                this.mRegistered = false;
            }
            if (this.mUserId != i) {
                this.mLastEnabled = "";
                this.mUserId = i;
            }
            contentResolver.registerContentObserver(android.provider.Settings.Secure.getUriFor("default_input_method"), false, this, i);
            contentResolver.registerContentObserver(android.provider.Settings.Secure.getUriFor("enabled_input_methods"), false, this, i);
            contentResolver.registerContentObserver(android.provider.Settings.Secure.getUriFor("selected_input_method_subtype"), false, this, i);
            contentResolver.registerContentObserver(android.provider.Settings.Secure.getUriFor("show_ime_with_hard_keyboard"), false, this, i);
            contentResolver.registerContentObserver(android.provider.Settings.Secure.getUriFor("accessibility_soft_keyboard_mode"), false, this, i);
            contentResolver.registerContentObserver(android.provider.Settings.Secure.getUriFor("stylus_handwriting_enabled"), false, this);
            if (com.android.server.inputmethod.InputMethodManagerService.this.mLineageHardware.isSupported(8)) {
                contentResolver.registerContentObserver(lineageos.providers.LineageSettings.System.getUriFor("high_touch_polling_rate_enable"), false, this, i);
            }
            if (com.android.server.inputmethod.InputMethodManagerService.this.mLineageHardware.isSupported(16)) {
                contentResolver.registerContentObserver(lineageos.providers.LineageSettings.System.getUriFor("high_touch_sensitivity_enable"), false, this, i);
            }
            if (com.android.server.inputmethod.InputMethodManagerService.this.mLineageHardware.isSupported(2048)) {
                contentResolver.registerContentObserver(lineageos.providers.LineageSettings.Secure.getUriFor("feature_touch_hovering"), false, this, i);
            }
            this.mRegistered = true;
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z, android.net.Uri uri) {
            android.net.Uri uriFor = android.provider.Settings.Secure.getUriFor("show_ime_with_hard_keyboard");
            android.net.Uri uriFor2 = android.provider.Settings.Secure.getUriFor("accessibility_soft_keyboard_mode");
            android.net.Uri uriFor3 = android.provider.Settings.Secure.getUriFor("stylus_handwriting_enabled");
            android.net.Uri uriFor4 = lineageos.providers.LineageSettings.System.getUriFor("high_touch_polling_rate_enable");
            android.net.Uri uriFor5 = lineageos.providers.LineageSettings.System.getUriFor("high_touch_sensitivity_enable");
            android.net.Uri uriFor6 = lineageos.providers.LineageSettings.Secure.getUriFor("feature_touch_hovering");
            synchronized (com.android.server.inputmethod.ImfLock.class) {
                try {
                    if (uriFor.equals(uri)) {
                        com.android.server.inputmethod.InputMethodManagerService.this.mMenuController.updateKeyboardFromSettingsLocked();
                    } else {
                        boolean equals = uriFor2.equals(uri);
                        boolean z2 = true;
                        if (equals) {
                            com.android.server.inputmethod.InputMethodManagerService.this.mVisibilityStateComputer.getImePolicy().setA11yRequestNoSoftKeyboard(android.provider.Settings.Secure.getIntForUser(com.android.server.inputmethod.InputMethodManagerService.this.mContext.getContentResolver(), "accessibility_soft_keyboard_mode", 0, this.mUserId));
                            if (com.android.server.inputmethod.InputMethodManagerService.this.mVisibilityStateComputer.getImePolicy().isA11yRequestNoSoftKeyboard()) {
                                com.android.server.inputmethod.InputMethodManagerService.this.hideCurrentInputLocked(com.android.server.inputmethod.InputMethodManagerService.this.mCurFocusedWindow, 0, 16);
                            } else if (com.android.server.inputmethod.InputMethodManagerService.this.isShowRequestedForCurrentWindow()) {
                                com.android.server.inputmethod.InputMethodManagerService.this.showCurrentInputLocked(com.android.server.inputmethod.InputMethodManagerService.this.mCurFocusedWindow, 1, 9);
                            }
                        } else if (uriFor3.equals(uri)) {
                            android.view.inputmethod.InputMethodManager.invalidateLocalStylusHandwritingAvailabilityCaches();
                        } else if (uriFor4.equals(uri)) {
                            com.android.server.inputmethod.InputMethodManagerService.this.updateTouchPollingRate();
                        } else if (uriFor5.equals(uri)) {
                            com.android.server.inputmethod.InputMethodManagerService.this.updateTouchSensitivity();
                        } else if (uriFor6.equals(uri)) {
                            com.android.server.inputmethod.InputMethodManagerService.this.updateTouchHovering();
                        } else {
                            java.lang.String enabledInputMethodsStr = com.android.server.inputmethod.InputMethodManagerService.this.mSettings.getEnabledInputMethodsStr();
                            if (this.mLastEnabled.equals(enabledInputMethodsStr)) {
                                z2 = false;
                            } else {
                                this.mLastEnabled = enabledInputMethodsStr;
                            }
                            com.android.server.inputmethod.InputMethodManagerService.this.updateInputMethodsFromSettingsLocked(z2);
                        }
                    }
                } finally {
                }
            }
        }

        public java.lang.String toString() {
            return "SettingsObserver{mUserId=" + this.mUserId + " mRegistered=" + this.mRegistered + " mLastEnabled=" + this.mLastEnabled + "}";
        }
    }

    private final class ImmsBroadcastReceiverForAllUsers extends android.content.BroadcastReceiver {
        private ImmsBroadcastReceiverForAllUsers() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(android.content.Context context, android.content.Intent intent) {
            if ("android.intent.action.CLOSE_SYSTEM_DIALOGS".equals(intent.getAction())) {
                android.content.BroadcastReceiver.PendingResult pendingResult = getPendingResult();
                if (pendingResult == null) {
                    return;
                }
                int sendingUserId = pendingResult.getSendingUserId();
                if (sendingUserId != -1 && sendingUserId != com.android.server.inputmethod.InputMethodManagerService.this.mSettings.getUserId()) {
                    return;
                }
                com.android.server.inputmethod.InputMethodManagerService.this.mMenuController.hideInputMethodMenu();
                return;
            }
            android.util.Slog.w(com.android.server.inputmethod.InputMethodManagerService.TAG, "Unexpected intent " + intent);
        }
    }

    void onActionLocaleChanged(@android.annotation.NonNull android.os.LocaleList localeList, @android.annotation.NonNull android.os.LocaleList localeList2) {
        synchronized (com.android.server.inputmethod.ImfLock.class) {
            try {
                if (this.mSystemReady) {
                    buildInputMethodListLocked(true);
                    resetDefaultImeLocked(this.mContext);
                    updateFromSettingsLocked(true);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    final class MyPackageMonitor extends com.android.internal.content.PackageMonitor {

        @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
        private final android.util.ArraySet<java.lang.String> mKnownImePackageNames = new android.util.ArraySet<>();
        private final java.util.ArrayList<java.lang.String> mChangedPackages = new java.util.ArrayList<>();
        private boolean mImePackageAppeared = false;

        MyPackageMonitor() {
        }

        @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
        void clearKnownImePackageNamesLocked() {
            this.mKnownImePackageNames.clear();
        }

        @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
        void addKnownImePackageNameLocked(@android.annotation.NonNull java.lang.String str) {
            this.mKnownImePackageNames.add(str);
        }

        @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
        private boolean isChangingPackagesOfCurrentUserLocked() {
            return getChangingUserId() == com.android.server.inputmethod.InputMethodManagerService.this.mSettings.getUserId();
        }

        public boolean onHandleForceStop(android.content.Intent intent, java.lang.String[] strArr, int i, boolean z) {
            synchronized (com.android.server.inputmethod.ImfLock.class) {
                try {
                    if (!isChangingPackagesOfCurrentUserLocked()) {
                        return false;
                    }
                    java.lang.String selectedInputMethod = com.android.server.inputmethod.InputMethodManagerService.this.mSettings.getSelectedInputMethod();
                    java.util.List<android.view.inputmethod.InputMethodInfo> methodList = com.android.server.inputmethod.InputMethodManagerService.this.mSettings.getMethodList();
                    int size = methodList.size();
                    if (selectedInputMethod != null) {
                        for (int i2 = 0; i2 < size; i2++) {
                            android.view.inputmethod.InputMethodInfo inputMethodInfo = methodList.get(i2);
                            if (inputMethodInfo.getId().equals(selectedInputMethod)) {
                                for (java.lang.String str : strArr) {
                                    if (inputMethodInfo.getPackageName().equals(str)) {
                                        if (!z) {
                                            return true;
                                        }
                                        com.android.server.inputmethod.InputMethodManagerService.this.resetSelectedInputMethodAndSubtypeLocked("");
                                        com.android.server.inputmethod.InputMethodManagerService.this.chooseNewDefaultIMELocked();
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                    return false;
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public void onBeginPackageChanges() {
            clearPackageChangeState();
        }

        public void onPackageAppeared(java.lang.String str, int i) {
            if (!this.mImePackageAppeared && !com.android.server.inputmethod.InputMethodManagerService.this.mContext.getPackageManager().queryIntentServicesAsUser(new android.content.Intent("android.view.InputMethod").setPackage(str), 512, getChangingUserId()).isEmpty()) {
                this.mImePackageAppeared = true;
            }
            this.mChangedPackages.add(str);
        }

        public void onPackageDisappeared(java.lang.String str, int i) {
            this.mChangedPackages.add(str);
        }

        public void onPackageModified(java.lang.String str) {
            this.mChangedPackages.add(str);
        }

        public void onPackagesSuspended(java.lang.String[] strArr) {
            for (java.lang.String str : strArr) {
                this.mChangedPackages.add(str);
            }
        }

        public void onPackagesUnsuspended(java.lang.String[] strArr) {
            for (java.lang.String str : strArr) {
                this.mChangedPackages.add(str);
            }
        }

        public void onPackageDataCleared(java.lang.String str, int i) {
            synchronized (com.android.server.inputmethod.ImfLock.class) {
                try {
                    java.util.ArrayList arrayList = new java.util.ArrayList();
                    for (android.view.inputmethod.InputMethodInfo inputMethodInfo : com.android.server.inputmethod.InputMethodManagerService.this.mSettings.getMethodList()) {
                        if (inputMethodInfo.getPackageName().equals(str)) {
                            arrayList.add(inputMethodInfo.getId());
                        }
                    }
                    com.android.server.inputmethod.AdditionalSubtypeMap cloneWithRemoveOrSelf = com.android.server.inputmethod.InputMethodManagerService.this.mAdditionalSubtypeMap.cloneWithRemoveOrSelf(arrayList);
                    if (cloneWithRemoveOrSelf != com.android.server.inputmethod.InputMethodManagerService.this.mAdditionalSubtypeMap) {
                        com.android.server.inputmethod.InputMethodManagerService.this.mAdditionalSubtypeMap = cloneWithRemoveOrSelf;
                        com.android.server.inputmethod.AdditionalSubtypeUtils.save(com.android.server.inputmethod.InputMethodManagerService.this.mAdditionalSubtypeMap, com.android.server.inputmethod.InputMethodManagerService.this.mSettings.getMethodMap(), com.android.server.inputmethod.InputMethodManagerService.this.mSettings.getUserId());
                    }
                    if (!arrayList.isEmpty()) {
                        this.mChangedPackages.add(str);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public void onFinishPackageChanges() {
            onFinishPackageChangesInternal();
            clearPackageChangeState();
        }

        private void clearPackageChangeState() {
            this.mChangedPackages.clear();
            this.mImePackageAppeared = false;
        }

        @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
        private boolean shouldRebuildInputMethodListLocked() {
            if (this.mImePackageAppeared) {
                return true;
            }
            int size = this.mChangedPackages.size();
            for (int i = 0; i < size; i++) {
                if (this.mKnownImePackageNames.contains(this.mChangedPackages.get(i))) {
                    return true;
                }
            }
            return false;
        }

        /* JADX WARN: Removed duplicated region for block: B:50:0x0151 A[Catch: all -> 0x0029, TryCatch #0 {all -> 0x0029, blocks: (B:4:0x0005, B:8:0x001c, B:9:0x0038, B:12:0x004b, B:15:0x005d, B:19:0x006c, B:21:0x0099, B:23:0x00d5, B:27:0x009f, B:29:0x00bb, B:31:0x00c5, B:35:0x00db, B:37:0x00e3, B:39:0x00eb, B:42:0x00f7, B:44:0x0100, B:46:0x0113, B:48:0x013c, B:50:0x0151, B:52:0x0168, B:53:0x016d, B:57:0x015a, B:66:0x016f, B:68:0x002c), top: B:3:0x0005 }] */
        /* JADX WARN: Removed duplicated region for block: B:52:0x0168 A[Catch: all -> 0x0029, TryCatch #0 {all -> 0x0029, blocks: (B:4:0x0005, B:8:0x001c, B:9:0x0038, B:12:0x004b, B:15:0x005d, B:19:0x006c, B:21:0x0099, B:23:0x00d5, B:27:0x009f, B:29:0x00bb, B:31:0x00c5, B:35:0x00db, B:37:0x00e3, B:39:0x00eb, B:42:0x00f7, B:44:0x0100, B:46:0x0113, B:48:0x013c, B:50:0x0151, B:52:0x0168, B:53:0x016d, B:57:0x015a, B:66:0x016f, B:68:0x002c), top: B:3:0x0005 }] */
        /* JADX WARN: Removed duplicated region for block: B:56:0x0158  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        private void onFinishPackageChangesInternal() {
            com.android.server.inputmethod.AdditionalSubtypeMap load;
            com.android.server.inputmethod.InputMethodSettings queryInputMethodServicesInternal;
            boolean z;
            android.view.inputmethod.InputMethodInfo inputMethodInfo;
            int isPackageDisappearing;
            android.content.pm.ServiceInfo serviceInfo;
            synchronized (com.android.server.inputmethod.ImfLock.class) {
                try {
                    int changingUserId = getChangingUserId();
                    boolean z2 = true;
                    boolean z3 = changingUserId == com.android.server.inputmethod.InputMethodManagerService.this.mSettings.getUserId();
                    if (z3) {
                        load = com.android.server.inputmethod.InputMethodManagerService.this.mAdditionalSubtypeMap;
                        queryInputMethodServicesInternal = com.android.server.inputmethod.InputMethodManagerService.this.mSettings;
                    } else {
                        load = com.android.server.inputmethod.AdditionalSubtypeUtils.load(changingUserId);
                        queryInputMethodServicesInternal = com.android.server.inputmethod.InputMethodManagerService.queryInputMethodServicesInternal(com.android.server.inputmethod.InputMethodManagerService.this.mContext, changingUserId, load, 0);
                    }
                    java.lang.String selectedInputMethod = queryInputMethodServicesInternal.getSelectedInputMethod();
                    java.util.List<android.view.inputmethod.InputMethodInfo> methodList = queryInputMethodServicesInternal.getMethodList();
                    int size = methodList.size();
                    android.view.inputmethod.InputMethodInfo inputMethodInfo2 = null;
                    for (int i = 0; i < size; i++) {
                        android.view.inputmethod.InputMethodInfo inputMethodInfo3 = methodList.get(i);
                        if (inputMethodInfo3.getId().equals(selectedInputMethod)) {
                            inputMethodInfo2 = inputMethodInfo3;
                        }
                        int isPackageDisappearing2 = isPackageDisappearing(inputMethodInfo3.getPackageName());
                        if (isPackageDisappearing2 == 2 || isPackageDisappearing2 == 3) {
                            android.util.Slog.i(com.android.server.inputmethod.InputMethodManagerService.TAG, "Input method uninstalled, disabling: " + inputMethodInfo3.getComponent());
                            if (z3) {
                                com.android.server.inputmethod.InputMethodManagerService.this.setInputMethodEnabledLocked(inputMethodInfo3.getId(), false);
                            } else {
                                queryInputMethodServicesInternal.buildAndPutEnabledInputMethodsStrRemovingId(new java.lang.StringBuilder(), queryInputMethodServicesInternal.getEnabledInputMethodsAndSubtypeList(), inputMethodInfo3.getId());
                            }
                        } else if (isPackageDisappearing2 == 1) {
                            android.util.Slog.i(com.android.server.inputmethod.InputMethodManagerService.TAG, "Input method reinstalling, clearing additional subtypes: " + inputMethodInfo3.getComponent());
                            load = load.cloneWithRemoveOrSelf(inputMethodInfo3.getId());
                            com.android.server.inputmethod.AdditionalSubtypeUtils.save(load, queryInputMethodServicesInternal.getMethodMap(), changingUserId);
                            if (z3) {
                                com.android.server.inputmethod.InputMethodManagerService.this.mAdditionalSubtypeMap = load;
                            }
                        }
                    }
                    if (z3 && shouldRebuildInputMethodListLocked()) {
                        com.android.server.inputmethod.InputMethodManagerService.this.buildInputMethodListLocked(false);
                        if (inputMethodInfo2 != null && ((isPackageDisappearing = isPackageDisappearing(inputMethodInfo2.getPackageName())) == 2 || isPackageDisappearing == 3)) {
                            try {
                                serviceInfo = com.android.server.inputmethod.InputMethodManagerService.getPackageManagerForUser(com.android.server.inputmethod.InputMethodManagerService.this.mContext, changingUserId).getServiceInfo(inputMethodInfo2.getComponent(), android.content.pm.PackageManager.ComponentInfoFlags.of(0L));
                            } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                                serviceInfo = null;
                            }
                            if (serviceInfo == null) {
                                android.util.Slog.i(com.android.server.inputmethod.InputMethodManagerService.TAG, "Current input method removed: " + selectedInputMethod);
                                com.android.server.inputmethod.InputMethodManagerService.this.updateSystemUiLocked(0, com.android.server.inputmethod.InputMethodManagerService.this.mBackDisposition);
                                if (!com.android.server.inputmethod.InputMethodManagerService.this.chooseNewDefaultIMELocked()) {
                                    android.util.Slog.i(com.android.server.inputmethod.InputMethodManagerService.TAG, "Unsetting current input method");
                                    com.android.server.inputmethod.InputMethodManagerService.this.resetSelectedInputMethodAndSubtypeLocked("");
                                    z = true;
                                    inputMethodInfo = null;
                                    if (inputMethodInfo != null) {
                                        z2 = com.android.server.inputmethod.InputMethodManagerService.this.chooseNewDefaultIMELocked();
                                    } else if (z || !isPackageModified(inputMethodInfo.getPackageName())) {
                                        z2 = z;
                                    }
                                    if (z2) {
                                        com.android.server.inputmethod.InputMethodManagerService.this.updateFromSettingsLocked(false);
                                    }
                                }
                            }
                        }
                        z = false;
                        inputMethodInfo = inputMethodInfo2;
                        if (inputMethodInfo != null) {
                        }
                        if (z2) {
                        }
                    }
                } finally {
                }
            }
        }
    }

    private static final class UserSwitchHandlerTask implements java.lang.Runnable {

        @android.annotation.Nullable
        com.android.server.inputmethod.IInputMethodClientInvoker mClientToBeReset;
        final com.android.server.inputmethod.InputMethodManagerService mService;
        final int mToUserId;

        UserSwitchHandlerTask(com.android.server.inputmethod.InputMethodManagerService inputMethodManagerService, int i, @android.annotation.Nullable com.android.server.inputmethod.IInputMethodClientInvoker iInputMethodClientInvoker) {
            this.mService = inputMethodManagerService;
            this.mToUserId = i;
            this.mClientToBeReset = iInputMethodClientInvoker;
        }

        @Override // java.lang.Runnable
        public void run() {
            synchronized (com.android.server.inputmethod.ImfLock.class) {
                try {
                    if (this.mService.mUserSwitchHandlerTask != this) {
                        return;
                    }
                    this.mService.switchUserOnHandlerLocked(this.mService.mUserSwitchHandlerTask.mToUserId, this.mClientToBeReset);
                    this.mService.mUserSwitchHandlerTask = null;
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    public static final class Lifecycle extends com.android.server.SystemService {
        private final com.android.server.inputmethod.InputMethodManagerService mService;

        public Lifecycle(android.content.Context context) {
            this(context, new com.android.server.inputmethod.InputMethodManagerService(context));
        }

        public Lifecycle(android.content.Context context, @android.annotation.NonNull com.android.server.inputmethod.InputMethodManagerService inputMethodManagerService) {
            super(context);
            this.mService = inputMethodManagerService;
        }

        @Override // com.android.server.SystemService
        public void onStart() {
            com.android.internal.view.IInputMethodManager.Stub stub;
            this.mService.publishLocalService();
            if (android.view.inputmethod.Flags.useZeroJankProxy()) {
                android.os.Handler handler = this.mService.mHandler;
                java.util.Objects.requireNonNull(handler);
                stub = new com.android.server.inputmethod.ZeroJankProxy(new com.android.server.devicepolicy.DevicePolicyManagerService$$ExternalSyntheticLambda208(handler), this.mService);
            } else {
                stub = this.mService;
            }
            publishBinderService("input_method", stub, false, 21);
        }

        @Override // com.android.server.SystemService
        public void onUserSwitching(@android.annotation.Nullable com.android.server.SystemService.TargetUser targetUser, @android.annotation.NonNull com.android.server.SystemService.TargetUser targetUser2) {
            synchronized (com.android.server.inputmethod.ImfLock.class) {
                this.mService.scheduleSwitchUserTaskLocked(targetUser2.getUserIdentifier(), null);
            }
        }

        @Override // com.android.server.SystemService
        public void onBootPhase(int i) {
            if (i == 550) {
                this.mService.systemRunning();
            }
        }

        @Override // com.android.server.SystemService
        public void onUserUnlocking(@android.annotation.NonNull com.android.server.SystemService.TargetUser targetUser) {
            com.android.server.inputmethod.SecureSettingsWrapper.onUserUnlocking(targetUser.getUserIdentifier());
            this.mService.mHandler.obtainMessage(5000, targetUser.getUserIdentifier(), 0).sendToTarget();
        }

        @Override // com.android.server.SystemService
        public void onUserStarting(com.android.server.SystemService.TargetUser targetUser) {
            com.android.server.inputmethod.SecureSettingsWrapper.onUserStarting(targetUser.getUserIdentifier());
        }
    }

    void onUnlockUser(int i) {
        synchronized (com.android.server.inputmethod.ImfLock.class) {
            try {
                if (i != this.mSettings.getUserId()) {
                    return;
                }
                this.mSettings = com.android.server.inputmethod.InputMethodSettings.createEmptyMap(i);
                if (this.mSystemReady) {
                    buildInputMethodListLocked(false);
                    updateInputMethodsFromSettingsLocked(true);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    void scheduleSwitchUserTaskLocked(int i, @android.annotation.Nullable com.android.server.inputmethod.IInputMethodClientInvoker iInputMethodClientInvoker) {
        if (this.mUserSwitchHandlerTask != null) {
            if (this.mUserSwitchHandlerTask.mToUserId == i) {
                this.mUserSwitchHandlerTask.mClientToBeReset = iInputMethodClientInvoker;
                return;
            }
            this.mHandler.removeCallbacks(this.mUserSwitchHandlerTask);
        }
        hideCurrentInputLocked(this.mCurFocusedWindow, 0, 10);
        com.android.server.inputmethod.InputMethodManagerService.UserSwitchHandlerTask userSwitchHandlerTask = new com.android.server.inputmethod.InputMethodManagerService.UserSwitchHandlerTask(this, i, iInputMethodClientInvoker);
        this.mUserSwitchHandlerTask = userSwitchHandlerTask;
        this.mHandler.post(userSwitchHandlerTask);
    }

    public InputMethodManagerService(android.content.Context context) {
        this(context, null, null);
    }

    @com.android.internal.annotations.VisibleForTesting
    InputMethodManagerService(android.content.Context context, @android.annotation.Nullable com.android.server.ServiceThread serviceThread, @android.annotation.Nullable com.android.server.inputmethod.InputMethodBindingController inputMethodBindingController) {
        com.android.server.ServiceThread serviceThread2;
        java.util.function.IntConsumer intConsumer;
        this.mAdditionalSubtypeMap = com.android.server.inputmethod.AdditionalSubtypeMap.EMPTY_MAP;
        byte b = 0;
        this.mAudioManagerInternal = null;
        this.mVdmInternal = null;
        this.mVirtualDeviceMethodMap = new android.util.SparseArray<>();
        this.mMethodMapUpdateCount = 0;
        this.mDisplayIdToShowIme = -1;
        this.mDeviceIdToShowIme = 0;
        this.mCurTokenDisplayId = -1;
        this.mEnabledAccessibilitySessions = new android.util.SparseArray<>();
        this.mIsInteractive = true;
        this.mBackDisposition = 0;
        this.mMyPackageMonitor = new com.android.server.inputmethod.InputMethodManagerService.MyPackageMonitor();
        this.mInputMethodListListeners = new java.util.concurrent.CopyOnWriteArrayList<>();
        this.mImeTargetWindowMap = new java.util.WeakHashMap<>();
        this.mStartInputHistory = new com.android.server.inputmethod.InputMethodManagerService.StartInputHistory();
        this.mSoftInputShowHideHistory = new com.android.server.inputmethod.InputMethodManagerService.SoftInputShowHideHistory();
        this.mPriorityDumper = new com.android.server.utils.PriorityDump.PriorityDumper() { // from class: com.android.server.inputmethod.InputMethodManagerService.4
            @Override // com.android.server.utils.PriorityDump.PriorityDumper
            public void dumpCritical(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr, boolean z) {
                if (z) {
                    dumpAsProtoNoCheck(fileDescriptor);
                } else {
                    com.android.server.inputmethod.InputMethodManagerService.this.dumpAsStringNoCheck(fileDescriptor, printWriter, strArr, true);
                }
            }

            @Override // com.android.server.utils.PriorityDump.PriorityDumper
            public void dumpHigh(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr, boolean z) {
                dumpNormal(fileDescriptor, printWriter, strArr, z);
            }

            @Override // com.android.server.utils.PriorityDump.PriorityDumper
            public void dumpNormal(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr, boolean z) {
                if (z) {
                    dumpAsProtoNoCheck(fileDescriptor);
                } else {
                    com.android.server.inputmethod.InputMethodManagerService.this.dumpAsStringNoCheck(fileDescriptor, printWriter, strArr, false);
                }
            }

            @Override // com.android.server.utils.PriorityDump.PriorityDumper
            public void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr, boolean z) {
                dumpNormal(fileDescriptor, printWriter, strArr, z);
            }

            private void dumpAsProtoNoCheck(java.io.FileDescriptor fileDescriptor) {
                android.util.proto.ProtoOutputStream protoOutputStream = new android.util.proto.ProtoOutputStream(fileDescriptor);
                long nanos = java.util.concurrent.TimeUnit.MILLISECONDS.toNanos(java.lang.System.currentTimeMillis()) - android.os.SystemClock.elapsedRealtimeNanos();
                protoOutputStream.write(1125281431553L, 4990904633914117449L);
                protoOutputStream.write(1125281431555L, nanos);
                long start = protoOutputStream.start(2246267895810L);
                protoOutputStream.write(1125281431553L, android.os.SystemClock.elapsedRealtimeNanos());
                protoOutputStream.write(1138166333442L, "InputMethodManagerService.mPriorityDumper#dumpAsProtoNoCheck");
                com.android.server.inputmethod.InputMethodManagerService.this.dumpDebug(protoOutputStream, 1146756268035L);
                protoOutputStream.end(start);
                protoOutputStream.flush();
            }
        };
        this.mContext = context;
        this.mRes = context.getResources();
        com.android.server.inputmethod.SecureSettingsWrapper.onStart(this.mContext);
        if (serviceThread != null) {
            serviceThread2 = serviceThread;
        } else {
            serviceThread2 = new com.android.server.ServiceThread(HANDLER_THREAD_NAME, -2, true);
        }
        serviceThread2.start();
        this.mHandler = android.os.Handler.createAsync(serviceThread2.getLooper(), this);
        com.android.server.inputmethod.SystemLocaleWrapper.onStart(context, new com.android.server.inputmethod.SystemLocaleWrapper.Callback() { // from class: com.android.server.inputmethod.InputMethodManagerService$$ExternalSyntheticLambda16
            @Override // com.android.server.inputmethod.SystemLocaleWrapper.Callback
            public final void onLocaleChanged(android.os.LocaleList localeList, android.os.LocaleList localeList2) {
                com.android.server.inputmethod.InputMethodManagerService.this.onActionLocaleChanged(localeList, localeList2);
            }
        }, this.mHandler);
        this.mImeTrackerService = new com.android.server.inputmethod.ImeTrackerService(serviceThread != null ? serviceThread.getLooper() : android.os.Looper.getMainLooper());
        this.mSettingsObserver = new com.android.server.inputmethod.InputMethodManagerService.SettingsObserver(this.mHandler);
        this.mWindowManagerInternal = (com.android.server.wm.WindowManagerInternal) com.android.server.LocalServices.getService(com.android.server.wm.WindowManagerInternal.class);
        this.mActivityManagerInternal = (android.app.ActivityManagerInternal) com.android.server.LocalServices.getService(android.app.ActivityManagerInternal.class);
        this.mPackageManagerInternal = (android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class);
        this.mInputManagerInternal = (com.android.server.input.InputManagerInternal) com.android.server.LocalServices.getService(com.android.server.input.InputManagerInternal.class);
        this.mImePlatformCompatUtils = new com.android.server.inputmethod.ImePlatformCompatUtils();
        this.mInputMethodDeviceConfigs = new com.android.server.inputmethod.InputMethodDeviceConfigs();
        this.mUserManagerInternal = (com.android.server.pm.UserManagerInternal) com.android.server.LocalServices.getService(com.android.server.pm.UserManagerInternal.class);
        this.mSlotIme = this.mContext.getString(android.R.string.sms_short_code_confirm_allow);
        this.mShowOngoingImeSwitcherForPhones = false;
        int currentUserId = this.mActivityManagerInternal.getCurrentUserId();
        this.mLastSwitchUserId = currentUserId;
        this.mSettings = com.android.server.inputmethod.InputMethodSettings.createEmptyMap(currentUserId);
        this.mAdditionalSubtypeMap = com.android.server.inputmethod.AdditionalSubtypeUtils.load(currentUserId);
        this.mSwitchingController = com.android.server.inputmethod.InputMethodSubtypeSwitchingController.createInstanceLocked(context, this.mSettings.getMethodMap(), currentUserId);
        this.mHardwareKeyboardShortcutController = new com.android.server.inputmethod.HardwareKeyboardShortcutController(this.mSettings.getMethodMap(), this.mSettings.getUserId());
        this.mMenuController = new com.android.server.inputmethod.InputMethodMenuController(this);
        this.mBindingController = inputMethodBindingController == null ? new com.android.server.inputmethod.InputMethodBindingController(this) : inputMethodBindingController;
        this.mAutofillController = new com.android.server.inputmethod.AutofillSuggestionsController(this);
        this.mVisibilityStateComputer = new com.android.server.inputmethod.ImeVisibilityStateComputer(this);
        this.mVisibilityApplier = new com.android.server.inputmethod.DefaultImeVisibilityApplier(this);
        this.mClientController = new com.android.server.inputmethod.ClientController(this.mPackageManagerInternal);
        synchronized (com.android.server.inputmethod.ImfLock.class) {
            this.mClientController.addClientControllerCallback(new com.android.server.inputmethod.ClientController.ClientControllerCallback() { // from class: com.android.server.inputmethod.InputMethodManagerService$$ExternalSyntheticLambda17
                @Override // com.android.server.inputmethod.ClientController.ClientControllerCallback
                public final void onClientRemoved(com.android.server.inputmethod.ClientState clientState) {
                    com.android.server.inputmethod.InputMethodManagerService.this.lambda$new$0(clientState);
                }
            });
        }
        this.mPreventImeStartupUnlessTextEditor = this.mRes.getBoolean(android.R.bool.config_preventImeStartupUnlessTextEditor);
        this.mNonPreemptibleInputMethods = this.mRes.getStringArray(android.R.array.config_network_type_tcp_buffers);
        if (!android.view.inputmethod.Flags.useHandwritingListenerForTooltype()) {
            intConsumer = null;
        } else {
            intConsumer = new java.util.function.IntConsumer() { // from class: com.android.server.inputmethod.InputMethodManagerService$$ExternalSyntheticLambda18
                @Override // java.util.function.IntConsumer
                public final void accept(int i) {
                    com.android.server.inputmethod.InputMethodManagerService.this.lambda$new$1(i);
                }
            };
        }
        this.mHwController = new com.android.server.inputmethod.HandwritingModeController(this.mContext, serviceThread2.getLooper(), new com.android.server.inputmethod.InputMethodManagerService.InkWindowInitializer(), intConsumer, new java.lang.Runnable() { // from class: com.android.server.inputmethod.InputMethodManagerService$$ExternalSyntheticLambda19
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.inputmethod.InputMethodManagerService.this.lambda$new$2();
            }
        });
        registerDeviceListenerAndCheckStylusSupport();
    }

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    int getCurrentImeUserIdLocked() {
        return this.mSettings.getUserId();
    }

    private final class InkWindowInitializer implements java.lang.Runnable {
        private InkWindowInitializer() {
        }

        @Override // java.lang.Runnable
        public void run() {
            synchronized (com.android.server.inputmethod.ImfLock.class) {
                try {
                    com.android.server.inputmethod.IInputMethodInvoker curMethodLocked = com.android.server.inputmethod.InputMethodManagerService.this.getCurMethodLocked();
                    if (curMethodLocked != null) {
                        curMethodLocked.initInkWindow();
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: onUpdateEditorToolType, reason: merged with bridge method [inline-methods] */
    public void lambda$new$1(int i) {
        synchronized (com.android.server.inputmethod.ImfLock.class) {
            try {
                com.android.server.inputmethod.IInputMethodInvoker curMethodLocked = getCurMethodLocked();
                if (curMethodLocked != null) {
                    curMethodLocked.updateEditorToolType(i);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: discardHandwritingDelegationText, reason: merged with bridge method [inline-methods] */
    public void lambda$new$2() {
        synchronized (com.android.server.inputmethod.ImfLock.class) {
            try {
                com.android.server.inputmethod.IInputMethodInvoker curMethodLocked = getCurMethodLocked();
                if (curMethodLocked != null) {
                    curMethodLocked.discardHandwritingDelegationText();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    private void resetDefaultImeLocked(android.content.Context context) {
        java.lang.String selectedMethodIdLocked = getSelectedMethodIdLocked();
        if (selectedMethodIdLocked != null && !this.mSettings.getMethodMap().get(selectedMethodIdLocked).isSystem()) {
            return;
        }
        java.util.ArrayList<android.view.inputmethod.InputMethodInfo> defaultEnabledImes = com.android.server.inputmethod.InputMethodInfoUtils.getDefaultEnabledImes(context, this.mSettings.getEnabledInputMethodList());
        if (defaultEnabledImes.isEmpty()) {
            android.util.Slog.i(TAG, "No default found");
        } else {
            setSelectedInputMethodAndSubtypeLocked(defaultEnabledImes.get(0), -1, false);
        }
    }

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    private void maybeInitImeNavbarConfigLocked(int i) {
        android.content.Context createContextAsUser;
        int profileParentId = this.mUserManagerInternal.getProfileParentId(i);
        if (this.mImeDrawsImeNavBarRes != null && this.mImeDrawsImeNavBarRes.getUserId() != profileParentId) {
            this.mImeDrawsImeNavBarRes.close();
            this.mImeDrawsImeNavBarRes = null;
        }
        if (this.mImeDrawsImeNavBarRes == null) {
            if (this.mContext.getUserId() == profileParentId) {
                createContextAsUser = this.mContext;
            } else {
                createContextAsUser = this.mContext.createContextAsUser(android.os.UserHandle.of(profileParentId), 0);
            }
            this.mImeDrawsImeNavBarRes = com.android.server.inputmethod.OverlayableSystemBooleanResourceWrapper.create(createContextAsUser, android.R.bool.config_hotspotNetworksEnabledForService, this.mHandler, new java.util.function.Consumer() { // from class: com.android.server.inputmethod.InputMethodManagerService$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.inputmethod.InputMethodManagerService.this.lambda$maybeInitImeNavbarConfigLocked$3((com.android.server.inputmethod.OverlayableSystemBooleanResourceWrapper) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$maybeInitImeNavbarConfigLocked$3(com.android.server.inputmethod.OverlayableSystemBooleanResourceWrapper overlayableSystemBooleanResourceWrapper) {
        synchronized (com.android.server.inputmethod.ImfLock.class) {
            try {
                if (overlayableSystemBooleanResourceWrapper == this.mImeDrawsImeNavBarRes) {
                    sendOnNavButtonFlagsChangedLocked();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @android.annotation.NonNull
    public static android.content.pm.PackageManager getPackageManagerForUser(@android.annotation.NonNull android.content.Context context, int i) {
        if (context.getUserId() == i) {
            return context.getPackageManager();
        }
        return context.createContextAsUser(android.os.UserHandle.of(i), 0).getPackageManager();
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    public void switchUserOnHandlerLocked(int i, com.android.server.inputmethod.IInputMethodClientInvoker iInputMethodClientInvoker) {
        com.android.server.inputmethod.ClientState client;
        maybeInitImeNavbarConfigLocked(i);
        this.mSettingsObserver.registerContentObserverLocked(i);
        this.mSettings = com.android.server.inputmethod.InputMethodSettings.createEmptyMap(i);
        this.mAdditionalSubtypeMap = com.android.server.inputmethod.AdditionalSubtypeUtils.load(i);
        boolean isEmpty = android.text.TextUtils.isEmpty(this.mSettings.getSelectedInputMethod());
        resetCurrentMethodAndClientLocked(6);
        buildInputMethodListLocked(isEmpty);
        if (android.text.TextUtils.isEmpty(this.mSettings.getSelectedInputMethod())) {
            resetDefaultImeLocked(this.mContext);
        }
        updateFromSettingsLocked(true);
        if (isEmpty) {
            com.android.server.inputmethod.InputMethodUtils.setNonSelectedSystemImesDisabledUntilUsed(getPackageManagerForUser(this.mContext, i), this.mSettings.getEnabledInputMethodList());
        }
        updateTouchPollingRate();
        updateTouchSensitivity();
        updateTouchHovering();
        this.mLastSwitchUserId = i;
        if (!this.mIsInteractive || iInputMethodClientInvoker == null || (client = this.mClientController.getClient(iInputMethodClientInvoker.asBinder())) == null) {
            return;
        }
        client.mClient.scheduleStartInputIfNecessary(this.mInFullscreenMode);
    }

    public void systemRunning() {
        synchronized (com.android.server.inputmethod.ImfLock.class) {
            try {
                if (!this.mSystemReady) {
                    this.mSystemReady = true;
                    final int userId = this.mSettings.getUserId();
                    this.mLineageHardware = lineageos.hardware.LineageHardwareManager.getInstance(this.mContext);
                    updateTouchPollingRate();
                    updateTouchSensitivity();
                    updateTouchHovering();
                    this.mStatusBarManagerInternal = (com.android.server.statusbar.StatusBarManagerInternal) com.android.server.LocalServices.getService(com.android.server.statusbar.StatusBarManagerInternal.class);
                    hideStatusBarIconLocked();
                    updateSystemUiLocked(this.mImeWindowVis, this.mBackDisposition);
                    this.mShowOngoingImeSwitcherForPhones = this.mRes.getBoolean(android.R.bool.lockscreen_isPortrait);
                    if (this.mShowOngoingImeSwitcherForPhones) {
                        this.mWindowManagerInternal.setOnHardKeyboardStatusChangeListener(new com.android.server.wm.WindowManagerInternal.OnHardKeyboardStatusChangeListener() { // from class: com.android.server.inputmethod.InputMethodManagerService$$ExternalSyntheticLambda2
                            @Override // com.android.server.wm.WindowManagerInternal.OnHardKeyboardStatusChangeListener
                            public final void onHardKeyboardStatusChange(boolean z) {
                                com.android.server.inputmethod.InputMethodManagerService.this.lambda$systemRunning$4(z);
                            }
                        });
                    }
                    this.mImeDrawsImeNavBarResLazyInitFuture = com.android.server.SystemServerInitThreadPool.submit(new java.lang.Runnable() { // from class: com.android.server.inputmethod.InputMethodManagerService$$ExternalSyntheticLambda3
                        @Override // java.lang.Runnable
                        public final void run() {
                            com.android.server.inputmethod.InputMethodManagerService.this.lambda$systemRunning$5(userId);
                        }
                    }, "Lazily initialize IMMS#mImeDrawsImeNavBarRes");
                    this.mMyPackageMonitor.register(this.mContext, (android.os.Looper) null, android.os.UserHandle.ALL, true);
                    this.mSettingsObserver.registerContentObserverLocked(userId);
                    android.content.IntentFilter intentFilter = new android.content.IntentFilter();
                    intentFilter.addAction("android.intent.action.CLOSE_SYSTEM_DIALOGS");
                    this.mContext.registerReceiverAsUser(new com.android.server.inputmethod.InputMethodManagerService.ImmsBroadcastReceiverForAllUsers(), android.os.UserHandle.ALL, intentFilter, null, null, 2);
                    buildInputMethodListLocked(!(android.text.TextUtils.isEmpty(this.mSettings.getSelectedInputMethod()) ^ true));
                    updateFromSettingsLocked(true);
                    com.android.server.inputmethod.InputMethodUtils.setNonSelectedSystemImesDisabledUntilUsed(getPackageManagerForUser(this.mContext, userId), this.mSettings.getEnabledInputMethodList());
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$systemRunning$4(boolean z) {
        this.mHandler.obtainMessage(MSG_HARD_KEYBOARD_SWITCH_CHANGED, z ? 1 : 0, 0).sendToTarget();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$systemRunning$5(int i) {
        synchronized (com.android.server.inputmethod.ImfLock.class) {
            try {
                this.mImeDrawsImeNavBarResLazyInitFuture = null;
                if (i != this.mSettings.getUserId()) {
                    return;
                }
                maybeInitImeNavbarConfigLocked(i);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    public boolean calledWithValidTokenLocked(@android.annotation.NonNull android.os.IBinder iBinder) {
        if (iBinder == null) {
            throw new java.security.InvalidParameterException("token must not be null.");
        }
        if (iBinder != getCurTokenLocked()) {
            android.util.Slog.e(TAG, "Ignoring " + android.os.Debug.getCaller() + " due to an invalid token. uid:" + android.os.Binder.getCallingUid() + " token:" + iBinder);
            return false;
        }
        return true;
    }

    @android.annotation.Nullable
    public android.view.inputmethod.InputMethodInfo getCurrentInputMethodInfoAsUser(int i) {
        android.view.inputmethod.InputMethodInfo queryDefaultInputMethodForUserIdLocked;
        if (android.os.UserHandle.getCallingUserId() != i) {
            this.mContext.enforceCallingOrSelfPermission("android.permission.INTERACT_ACROSS_USERS_FULL", null);
        }
        synchronized (com.android.server.inputmethod.ImfLock.class) {
            queryDefaultInputMethodForUserIdLocked = queryDefaultInputMethodForUserIdLocked(i);
        }
        return queryDefaultInputMethodForUserIdLocked;
    }

    @android.annotation.NonNull
    public java.util.List<android.view.inputmethod.InputMethodInfo> getInputMethodList(int i, int i2) {
        if (android.os.UserHandle.getCallingUserId() != i) {
            this.mContext.enforceCallingOrSelfPermission("android.permission.INTERACT_ACROSS_USERS_FULL", null);
        }
        synchronized (com.android.server.inputmethod.ImfLock.class) {
            try {
                int[] resolveUserId = com.android.server.inputmethod.InputMethodUtils.resolveUserId(i, this.mSettings.getUserId(), null);
                if (resolveUserId.length != 1) {
                    return java.util.Collections.emptyList();
                }
                int callingUid = android.os.Binder.getCallingUid();
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    return getInputMethodListLocked(resolveUserId[0], i2, callingUid);
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public java.util.List<android.view.inputmethod.InputMethodInfo> getEnabledInputMethodList(int i) {
        if (android.os.UserHandle.getCallingUserId() != i) {
            this.mContext.enforceCallingOrSelfPermission("android.permission.INTERACT_ACROSS_USERS_FULL", null);
        }
        synchronized (com.android.server.inputmethod.ImfLock.class) {
            try {
                int[] resolveUserId = com.android.server.inputmethod.InputMethodUtils.resolveUserId(i, this.mSettings.getUserId(), null);
                if (resolveUserId.length != 1) {
                    return java.util.Collections.emptyList();
                }
                int callingUid = android.os.Binder.getCallingUid();
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    return getEnabledInputMethodListLocked(resolveUserId[0], callingUid);
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public boolean isStylusHandwritingAvailableAsUser(int i, boolean z) {
        if (android.os.UserHandle.getCallingUserId() != i) {
            this.mContext.enforceCallingOrSelfPermission("android.permission.INTERACT_ACROSS_USERS_FULL", null);
        }
        synchronized (com.android.server.inputmethod.ImfLock.class) {
            try {
                boolean z2 = false;
                if (!isStylusHandwritingEnabled(this.mContext, i)) {
                    return false;
                }
                if (i == this.mSettings.getUserId()) {
                    if (this.mBindingController.supportsStylusHandwriting() && (!z || this.mBindingController.supportsConnectionlessStylusHandwriting())) {
                        z2 = true;
                    }
                    return z2;
                }
                com.android.server.inputmethod.InputMethodSettings queryMethodMapForUser = queryMethodMapForUser(i);
                android.view.inputmethod.InputMethodInfo inputMethodInfo = queryMethodMapForUser.getMethodMap().get(queryMethodMapForUser.getSelectedInputMethod());
                if (inputMethodInfo != null && inputMethodInfo.supportsStylusHandwriting() && (!z || inputMethodInfo.supportsConnectionlessStylusHandwriting())) {
                    z2 = true;
                }
                return z2;
            } finally {
            }
        }
    }

    private boolean isStylusHandwritingEnabled(@android.annotation.NonNull android.content.Context context, int i) {
        return android.provider.Settings.Secure.getIntForUser(context.getContentResolver(), "stylus_handwriting_enabled", 1, this.mUserManagerInternal.getProfileParentId(i)) != 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    public java.util.List<android.view.inputmethod.InputMethodInfo> getInputMethodListLocked(final int i, int i2, final int i3) {
        final com.android.server.inputmethod.InputMethodSettings queryInputMethodServicesInternal;
        if (i == this.mSettings.getUserId() && i2 == 0) {
            queryInputMethodServicesInternal = this.mSettings;
        } else {
            queryInputMethodServicesInternal = queryInputMethodServicesInternal(this.mContext, i, com.android.server.inputmethod.AdditionalSubtypeUtils.load(i), i2);
        }
        java.util.ArrayList arrayList = new java.util.ArrayList(queryInputMethodServicesInternal.getMethodList());
        arrayList.removeIf(new java.util.function.Predicate() { // from class: com.android.server.inputmethod.InputMethodManagerService$$ExternalSyntheticLambda7
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$getInputMethodListLocked$6;
                lambda$getInputMethodListLocked$6 = com.android.server.inputmethod.InputMethodManagerService.this.lambda$getInputMethodListLocked$6(i3, i, queryInputMethodServicesInternal, (android.view.inputmethod.InputMethodInfo) obj);
                return lambda$getInputMethodListLocked$6;
            }
        });
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$getInputMethodListLocked$6(int i, int i2, com.android.server.inputmethod.InputMethodSettings inputMethodSettings, android.view.inputmethod.InputMethodInfo inputMethodInfo) {
        return !canCallerAccessInputMethod(inputMethodInfo.getPackageName(), i, i2, inputMethodSettings);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    public java.util.List<android.view.inputmethod.InputMethodInfo> getEnabledInputMethodListLocked(final int i, final int i2) {
        final com.android.server.inputmethod.InputMethodSettings queryMethodMapForUser;
        java.util.ArrayList<android.view.inputmethod.InputMethodInfo> enabledInputMethodList;
        if (i == this.mSettings.getUserId()) {
            enabledInputMethodList = this.mSettings.getEnabledInputMethodList();
            queryMethodMapForUser = this.mSettings;
        } else {
            queryMethodMapForUser = queryMethodMapForUser(i);
            enabledInputMethodList = queryMethodMapForUser.getEnabledInputMethodList();
        }
        enabledInputMethodList.removeIf(new java.util.function.Predicate() { // from class: com.android.server.inputmethod.InputMethodManagerService$$ExternalSyntheticLambda13
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$getEnabledInputMethodListLocked$7;
                lambda$getEnabledInputMethodListLocked$7 = com.android.server.inputmethod.InputMethodManagerService.this.lambda$getEnabledInputMethodListLocked$7(i2, i, queryMethodMapForUser, (android.view.inputmethod.InputMethodInfo) obj);
                return lambda$getEnabledInputMethodListLocked$7;
            }
        });
        return enabledInputMethodList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$getEnabledInputMethodListLocked$7(int i, int i2, com.android.server.inputmethod.InputMethodSettings inputMethodSettings, android.view.inputmethod.InputMethodInfo inputMethodInfo) {
        return !canCallerAccessInputMethod(inputMethodInfo.getPackageName(), i, i2, inputMethodSettings);
    }

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    void performOnCreateInlineSuggestionsRequestLocked() {
        this.mAutofillController.performOnCreateInlineSuggestionsRequest();
    }

    void setCurHostInputToken(@android.annotation.NonNull android.os.IBinder iBinder, @android.annotation.Nullable android.os.IBinder iBinder2) {
        synchronized (com.android.server.inputmethod.ImfLock.class) {
            try {
                if (calledWithValidTokenLocked(iBinder)) {
                    this.mCurHostInputToken = iBinder2;
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public java.util.List<android.view.inputmethod.InputMethodSubtype> getEnabledInputMethodSubtypeList(java.lang.String str, boolean z, int i) {
        java.util.List<android.view.inputmethod.InputMethodSubtype> enabledInputMethodSubtypeListLocked;
        if (android.os.UserHandle.getCallingUserId() != i) {
            this.mContext.enforceCallingOrSelfPermission("android.permission.INTERACT_ACROSS_USERS_FULL", null);
        }
        synchronized (com.android.server.inputmethod.ImfLock.class) {
            try {
                int callingUid = android.os.Binder.getCallingUid();
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    enabledInputMethodSubtypeListLocked = getEnabledInputMethodSubtypeListLocked(str, z, i, callingUid);
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return enabledInputMethodSubtypeListLocked;
    }

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    private java.util.List<android.view.inputmethod.InputMethodSubtype> getEnabledInputMethodSubtypeListLocked(java.lang.String str, boolean z, int i, int i2) {
        android.view.inputmethod.InputMethodInfo inputMethodInfo;
        if (i == this.mSettings.getUserId()) {
            java.lang.String selectedMethodIdLocked = getSelectedMethodIdLocked();
            if (str == null && selectedMethodIdLocked != null) {
                inputMethodInfo = this.mSettings.getMethodMap().get(selectedMethodIdLocked);
            } else {
                inputMethodInfo = this.mSettings.getMethodMap().get(str);
            }
            if (inputMethodInfo == null || !canCallerAccessInputMethod(inputMethodInfo.getPackageName(), i2, i, this.mSettings)) {
                return java.util.Collections.emptyList();
            }
            return this.mSettings.getEnabledInputMethodSubtypeList(inputMethodInfo, z);
        }
        com.android.server.inputmethod.InputMethodSettings queryMethodMapForUser = queryMethodMapForUser(i);
        android.view.inputmethod.InputMethodInfo inputMethodInfo2 = queryMethodMapForUser.getMethodMap().get(str);
        if (inputMethodInfo2 == null) {
            return java.util.Collections.emptyList();
        }
        if (!canCallerAccessInputMethod(inputMethodInfo2.getPackageName(), i2, i, queryMethodMapForUser)) {
            return java.util.Collections.emptyList();
        }
        return queryMethodMapForUser.getEnabledInputMethodSubtypeList(inputMethodInfo2, z);
    }

    public void addClient(com.android.internal.inputmethod.IInputMethodClient iInputMethodClient, com.android.internal.inputmethod.IRemoteInputConnection iRemoteInputConnection, int i) {
        int callingUid = android.os.Binder.getCallingUid();
        int callingPid = android.os.Binder.getCallingPid();
        com.android.server.inputmethod.IInputMethodClientInvoker create = com.android.server.inputmethod.IInputMethodClientInvoker.create(iInputMethodClient, this.mHandler);
        synchronized (com.android.server.inputmethod.ImfLock.class) {
            this.mClientController.addClient(create, iRemoteInputConnection, i, callingUid, callingPid);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    /* renamed from: onClientRemoved, reason: merged with bridge method [inline-methods] */
    public void lambda$new$0(com.android.server.inputmethod.ClientState clientState) {
        clearClientSessionLocked(clientState);
        clearClientSessionForAccessibilityLocked(clientState);
        if (this.mCurClient == clientState) {
            hideCurrentInputLocked(this.mCurFocusedWindow, 0, 22);
            if (this.mBoundToMethod) {
                this.mBoundToMethod = false;
                com.android.server.inputmethod.IInputMethodInvoker curMethodLocked = getCurMethodLocked();
                if (curMethodLocked != null) {
                    curMethodLocked.unbindInput();
                    com.android.server.AccessibilityManagerInternal.get().unbindInput();
                }
            }
            this.mBoundToAccessibility = false;
            this.mCurClient = null;
            if (this.mCurFocusedWindowClient == clientState) {
                this.mCurFocusedWindowClient = null;
                this.mCurFocusedWindowEditorInfo = null;
            }
        }
    }

    @android.annotation.Nullable
    com.android.server.inputmethod.ClientState getClientState(com.android.internal.inputmethod.IInputMethodClient iInputMethodClient) {
        com.android.server.inputmethod.ClientState client;
        synchronized (com.android.server.inputmethod.ImfLock.class) {
            client = this.mClientController.getClient(iInputMethodClient.asBinder());
        }
        return client;
    }

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    void unbindCurrentClientLocked(int i) {
        if (this.mCurClient != null) {
            if (this.mBoundToMethod) {
                this.mBoundToMethod = false;
                com.android.server.inputmethod.IInputMethodInvoker curMethodLocked = getCurMethodLocked();
                if (curMethodLocked != null) {
                    curMethodLocked.unbindInput();
                }
            }
            this.mBoundToAccessibility = false;
            this.mCurClient.mClient.setActive(false, false);
            this.mCurClient.mClient.onUnbindMethod(getSequenceNumberLocked(), i);
            this.mCurClient.mSessionRequested = false;
            this.mCurClient.mSessionRequestedForAccessibility = false;
            this.mCurClient = null;
            android.view.inputmethod.ImeTracker.forLogging().onFailed(this.mCurStatsToken, 8);
            this.mCurStatsToken = null;
            this.mMenuController.hideInputMethodMenuLocked();
        }
    }

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    void onUnbindCurrentMethodByReset() {
        com.android.server.inputmethod.ImeVisibilityStateComputer.ImeTargetWindowState windowStateOrNull = this.mVisibilityStateComputer.getWindowStateOrNull(this.mCurFocusedWindow);
        if (windowStateOrNull != null && !windowStateOrNull.isRequestedImeVisible() && !this.mVisibilityStateComputer.isInputShown()) {
            this.mVisibilityApplier.applyImeVisibility(this.mCurFocusedWindow, createStatsTokenForFocusedClient(false, 50), 0);
        }
    }

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    boolean hasAttachedClient() {
        return this.mCurClient != null;
    }

    @com.android.internal.annotations.VisibleForTesting
    void setAttachedClientForTesting(@android.annotation.NonNull com.android.server.inputmethod.ClientState clientState) {
        synchronized (com.android.server.inputmethod.ImfLock.class) {
            this.mCurClient = clientState;
        }
    }

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    void clearInputShownLocked() {
        this.mVisibilityStateComputer.setInputShown(false);
    }

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    private boolean isInputShown() {
        return this.mVisibilityStateComputer.isInputShown();
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    public boolean isShowRequestedForCurrentWindow() {
        com.android.server.inputmethod.ImeVisibilityStateComputer.ImeTargetWindowState windowStateOrNull = this.mVisibilityStateComputer.getWindowStateOrNull(this.mCurFocusedWindow);
        return windowStateOrNull != null && windowStateOrNull.isRequestedImeVisible();
    }

    @android.annotation.NonNull
    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    com.android.internal.inputmethod.InputBindResult attachNewInputLocked(int i, boolean z) {
        if (!this.mBoundToMethod) {
            getCurMethodLocked().bindInput(this.mCurClient.mBinding);
            this.mBoundToMethod = true;
        }
        boolean z2 = !z;
        android.os.Binder binder = new android.os.Binder();
        com.android.server.inputmethod.InputMethodManagerService.StartInputInfo startInputInfo = new com.android.server.inputmethod.InputMethodManagerService.StartInputInfo(this.mSettings.getUserId(), getCurTokenLocked(), this.mCurTokenDisplayId, getCurIdLocked(), i, z2, android.os.UserHandle.getUserId(this.mCurClient.mUid), this.mCurClient.mSelfReportedDisplayId, this.mCurFocusedWindow, this.mCurEditorInfo, this.mCurFocusedWindowSoftInputMode, getSequenceNumberLocked());
        this.mImeTargetWindowMap.put(binder, this.mCurFocusedWindow);
        this.mStartInputHistory.addEntry(startInputInfo);
        if (this.mSettings.getUserId() == android.os.UserHandle.getUserId(this.mCurClient.mUid)) {
            this.mPackageManagerInternal.grantImplicitAccess(this.mSettings.getUserId(), null, android.os.UserHandle.getAppId(getCurMethodUidLocked()), this.mCurClient.mUid, true);
        }
        int inputMethodNavButtonFlagsLocked = getInputMethodNavButtonFlagsLocked();
        com.android.server.inputmethod.InputMethodManagerService.SessionState sessionState = this.mCurClient.mCurSession;
        setEnabledSessionLocked(sessionState);
        sessionState.mMethod.startInput(binder, this.mCurInputConnection, this.mCurEditorInfo, z2, inputMethodNavButtonFlagsLocked, this.mCurImeDispatcher);
        if (isShowRequestedForCurrentWindow()) {
            android.view.inputmethod.ImeTracker.Token createStatsTokenForFocusedClient = this.mCurStatsToken != null ? this.mCurStatsToken : createStatsTokenForFocusedClient(true, 2);
            this.mCurStatsToken = null;
            showCurrentInputLocked(this.mCurFocusedWindow, createStatsTokenForFocusedClient, this.mVisibilityStateComputer.getShowFlags(), 0, null, 2);
        }
        java.lang.String curIdLocked = getCurIdLocked();
        android.view.inputmethod.InputMethodInfo inputMethodInfo = this.mSettings.getMethodMap().get(curIdLocked);
        boolean z3 = inputMethodInfo != null && inputMethodInfo.suppressesSpellChecker();
        android.util.SparseArray<com.android.internal.inputmethod.IAccessibilityInputMethodSession> createAccessibilityInputMethodSessions = createAccessibilityInputMethodSessions(this.mCurClient.mAccessibilitySessions);
        if (this.mBindingController.supportsStylusHandwriting() && hasSupportedStylusLocked()) {
            this.mHwController.setInkWindowInitializer(new com.android.server.inputmethod.InputMethodManagerService.InkWindowInitializer());
        }
        return new com.android.internal.inputmethod.InputBindResult(0, sessionState.mSession, createAccessibilityInputMethodSessions, sessionState.mChannel != null ? sessionState.mChannel.dup() : null, curIdLocked, getSequenceNumberLocked(), z3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    public void attachNewAccessibilityLocked(int i, boolean z) {
        if (!this.mBoundToAccessibility) {
            com.android.server.AccessibilityManagerInternal.get().bindInput();
            this.mBoundToAccessibility = true;
        }
        if (i != 11) {
            setEnabledSessionForAccessibilityLocked(this.mCurClient.mAccessibilitySessions);
            com.android.server.AccessibilityManagerInternal.get().startInput(this.mCurRemoteAccessibilityInputConnection, this.mCurEditorInfo, !z);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.util.SparseArray<com.android.internal.inputmethod.IAccessibilityInputMethodSession> createAccessibilityInputMethodSessions(android.util.SparseArray<com.android.server.inputmethod.InputMethodManagerService.AccessibilitySessionState> sparseArray) {
        android.util.SparseArray<com.android.internal.inputmethod.IAccessibilityInputMethodSession> sparseArray2 = new android.util.SparseArray<>();
        if (sparseArray != null) {
            for (int i = 0; i < sparseArray.size(); i++) {
                sparseArray2.append(sparseArray.keyAt(i), sparseArray.valueAt(i).mSession);
            }
        }
        return sparseArray2;
    }

    @android.annotation.NonNull
    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    private com.android.internal.inputmethod.InputBindResult startInputUncheckedLocked(@android.annotation.NonNull com.android.server.inputmethod.ClientState clientState, com.android.internal.inputmethod.IRemoteInputConnection iRemoteInputConnection, @android.annotation.Nullable com.android.internal.inputmethod.IRemoteAccessibilityInputConnection iRemoteAccessibilityInputConnection, @android.annotation.NonNull android.view.inputmethod.EditorInfo editorInfo, int i, int i2, int i3, @android.annotation.NonNull android.window.ImeOnBackInvokedDispatcher imeOnBackInvokedDispatcher) {
        android.os.LocaleList preferredLocaleListForUid;
        com.android.server.inputmethod.ImeVisibilityStateComputer.ImeTargetWindowState windowStateOrNull = this.mVisibilityStateComputer.getWindowStateOrNull(this.mCurFocusedWindow);
        if (windowStateOrNull == null) {
            return com.android.internal.inputmethod.InputBindResult.NOT_IME_TARGET_WINDOW;
        }
        this.mDisplayIdToShowIme = this.mVisibilityStateComputer.computeImeDisplayId(windowStateOrNull, clientState.mSelfReportedDisplayId);
        java.lang.String selectedMethodIdLocked = getSelectedMethodIdLocked();
        java.lang.String computeCurrentDeviceMethodIdLocked = computeCurrentDeviceMethodIdLocked(selectedMethodIdLocked);
        if (computeCurrentDeviceMethodIdLocked == null) {
            this.mVisibilityStateComputer.getImePolicy().setImeHiddenByDisplayPolicy(true);
        } else if (!java.util.Objects.equals(computeCurrentDeviceMethodIdLocked, selectedMethodIdLocked)) {
            setInputMethodLocked(computeCurrentDeviceMethodIdLocked, -1, this.mDeviceIdToShowIme);
            selectedMethodIdLocked = computeCurrentDeviceMethodIdLocked;
        }
        if (this.mVisibilityStateComputer.getImePolicy().isImeHiddenByDisplayPolicy()) {
            hideCurrentInputLocked(this.mCurFocusedWindow, 0, 27);
            return com.android.internal.inputmethod.InputBindResult.NO_IME;
        }
        if (selectedMethodIdLocked == null) {
            return com.android.internal.inputmethod.InputBindResult.NO_IME;
        }
        if (this.mCurClient != clientState) {
            prepareClientSwitchLocked(clientState);
        }
        advanceSequenceNumberLocked();
        this.mCurClient = clientState;
        this.mCurInputConnection = iRemoteInputConnection;
        this.mCurRemoteAccessibilityInputConnection = iRemoteAccessibilityInputConnection;
        this.mCurImeDispatcher = imeOnBackInvokedDispatcher;
        if (this.mVdmInternal == null) {
            this.mVdmInternal = (com.android.server.companion.virtual.VirtualDeviceManagerInternal) com.android.server.LocalServices.getService(com.android.server.companion.virtual.VirtualDeviceManagerInternal.class);
        }
        if (this.mVdmInternal != null && editorInfo.hintLocales == null && (preferredLocaleListForUid = this.mVdmInternal.getPreferredLocaleListForUid(clientState.mUid)) != null) {
            editorInfo.hintLocales = preferredLocaleListForUid;
        }
        this.mCurEditorInfo = editorInfo;
        if (shouldPreventImeStartupLocked(selectedMethodIdLocked, i, i3)) {
            invalidateAutofillSessionLocked();
            this.mBindingController.unbindCurrentMethod();
            return com.android.internal.inputmethod.InputBindResult.NO_EDITOR;
        }
        if (isSelectedMethodBoundLocked()) {
            if (clientState.mCurSession != null) {
                clientState.mSessionRequestedForAccessibility = false;
                requestClientSessionForAccessibilityLocked(clientState);
                int i4 = i & 4;
                attachNewAccessibilityLocked(i2, i4 != 0);
                return attachNewInputLocked(i2, i4 != 0);
            }
            com.android.internal.inputmethod.InputBindResult tryReuseConnectionLocked = tryReuseConnectionLocked(clientState);
            if (tryReuseConnectionLocked != null) {
                return tryReuseConnectionLocked;
            }
        }
        this.mBindingController.unbindCurrentMethod();
        return this.mBindingController.bindCurrentMethod();
    }

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    private java.lang.String computeCurrentDeviceMethodIdLocked(java.lang.String str) {
        if (this.mVdmInternal == null) {
            this.mVdmInternal = (com.android.server.companion.virtual.VirtualDeviceManagerInternal) com.android.server.LocalServices.getService(com.android.server.companion.virtual.VirtualDeviceManagerInternal.class);
        }
        if (this.mVdmInternal == null || !android.companion.virtual.flags.Flags.vdmCustomIme()) {
            return str;
        }
        int i = this.mDeviceIdToShowIme;
        this.mDeviceIdToShowIme = this.mVdmInternal.getDeviceIdForDisplayId(this.mDisplayIdToShowIme);
        if (this.mDeviceIdToShowIme == 0) {
            if (i == 0) {
                return str;
            }
            java.lang.String selectedDefaultDeviceInputMethod = this.mSettings.getSelectedDefaultDeviceInputMethod();
            this.mSettings.putSelectedDefaultDeviceInputMethod(null);
            return selectedDefaultDeviceInputMethod;
        }
        java.lang.String str2 = this.mVirtualDeviceMethodMap.get(this.mDeviceIdToShowIme, str);
        if (java.util.Objects.equals(str2, str)) {
            return str;
        }
        if (!this.mSettings.getMethodMap().containsKey(str2)) {
            return null;
        }
        if (i == 0) {
            this.mSettings.putSelectedDefaultDeviceInputMethod(str);
        }
        return str2;
    }

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    void invalidateAutofillSessionLocked() {
        this.mAutofillController.invalidateAutofillSession();
    }

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    private boolean shouldPreventImeStartupLocked(@android.annotation.NonNull java.lang.String str, int i, int i2) {
        android.view.inputmethod.InputMethodInfo inputMethodInfo;
        return (!this.mPreventImeStartupUnlessTextEditor || isShowRequestedForCurrentWindow() || com.android.server.inputmethod.InputMethodUtils.isSoftInputModeStateVisibleAllowed(i2, i) || (inputMethodInfo = this.mSettings.getMethodMap().get(str)) == null || com.android.internal.util.ArrayUtils.contains(this.mNonPreemptibleInputMethods, inputMethodInfo.getPackageName())) ? false : true;
    }

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    private boolean isSelectedMethodBoundLocked() {
        java.lang.String curIdLocked = getCurIdLocked();
        return curIdLocked != null && curIdLocked.equals(getSelectedMethodIdLocked()) && this.mDisplayIdToShowIme == this.mCurTokenDisplayId;
    }

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    private void prepareClientSwitchLocked(com.android.server.inputmethod.ClientState clientState) {
        unbindCurrentClientLocked(1);
        if (this.mIsInteractive) {
            clientState.mClient.setActive(true, false);
        }
    }

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    @android.annotation.Nullable
    private com.android.internal.inputmethod.InputBindResult tryReuseConnectionLocked(@android.annotation.NonNull com.android.server.inputmethod.ClientState clientState) {
        if (hasConnectionLocked()) {
            if (getCurMethodLocked() != null) {
                requestClientSessionLocked(clientState);
                requestClientSessionForAccessibilityLocked(clientState);
                return new com.android.internal.inputmethod.InputBindResult(1, (com.android.internal.inputmethod.IInputMethodSession) null, (android.util.SparseArray) null, (android.view.InputChannel) null, getCurIdLocked(), getSequenceNumberLocked(), false);
            }
            long uptimeMillis = android.os.SystemClock.uptimeMillis() - getLastBindTimeLocked();
            if (uptimeMillis < com.android.server.backup.BackupAgentTimeoutParameters.DEFAULT_QUOTA_EXCEEDED_TIMEOUT_MILLIS) {
                return new com.android.internal.inputmethod.InputBindResult(2, (com.android.internal.inputmethod.IInputMethodSession) null, (android.util.SparseArray) null, (android.view.InputChannel) null, getCurIdLocked(), getSequenceNumberLocked(), false);
            }
            android.util.EventLog.writeEvent(com.android.server.EventLogTags.IMF_FORCE_RECONNECT_IME, getSelectedMethodIdLocked(), java.lang.Long.valueOf(uptimeMillis), 0);
            return null;
        }
        return null;
    }

    static int computeImeDisplayIdForTarget(int i, @android.annotation.NonNull com.android.server.inputmethod.InputMethodManagerService.ImeDisplayValidator imeDisplayValidator) {
        if (i == 0 || i == -1) {
            return 0;
        }
        int displayImePolicy = imeDisplayValidator.getDisplayImePolicy(i);
        if (displayImePolicy == 0) {
            return i;
        }
        return displayImePolicy == 2 ? -1 : 0;
    }

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    void initializeImeLocked(@android.annotation.NonNull com.android.server.inputmethod.IInputMethodInvoker iInputMethodInvoker, @android.annotation.NonNull android.os.IBinder iBinder) {
        iInputMethodInvoker.initializeInternal(iBinder, new com.android.server.inputmethod.InputMethodManagerService.InputMethodPrivilegedOperationsImpl(this, iBinder), getInputMethodNavButtonFlagsLocked());
    }

    void scheduleResetStylusHandwriting() {
        this.mHandler.obtainMessage(MSG_RESET_HANDWRITING).sendToTarget();
    }

    void schedulePrepareStylusHandwritingDelegation(int i, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2) {
        this.mHandler.obtainMessage(MSG_PREPARE_HANDWRITING_DELEGATION, i, 0, new android.util.Pair(str, str2)).sendToTarget();
    }

    void scheduleRemoveStylusHandwritingWindow() {
        this.mHandler.obtainMessage(MSG_REMOVE_HANDWRITING_WINDOW).sendToTarget();
    }

    void scheduleNotifyImeUidToAudioService(int i) {
        this.mHandler.removeMessages(MSG_NOTIFY_IME_UID_TO_AUDIO_SERVICE);
        this.mHandler.obtainMessage(MSG_NOTIFY_IME_UID_TO_AUDIO_SERVICE, i, 0).sendToTarget();
    }

    void onSessionCreated(com.android.server.inputmethod.IInputMethodInvoker iInputMethodInvoker, com.android.internal.inputmethod.IInputMethodSession iInputMethodSession, android.view.InputChannel inputChannel) {
        android.os.Trace.traceBegin(32L, "IMMS.onSessionCreated");
        try {
            synchronized (com.android.server.inputmethod.ImfLock.class) {
                if (this.mUserSwitchHandlerTask != null) {
                    inputChannel.dispose();
                    return;
                }
                com.android.server.inputmethod.IInputMethodInvoker curMethodLocked = getCurMethodLocked();
                if (curMethodLocked == null || iInputMethodInvoker == null || curMethodLocked.asBinder() != iInputMethodInvoker.asBinder() || this.mCurClient == null) {
                    inputChannel.dispose();
                    return;
                }
                clearClientSessionLocked(this.mCurClient);
                this.mCurClient.mCurSession = new com.android.server.inputmethod.InputMethodManagerService.SessionState(this.mCurClient, iInputMethodInvoker, iInputMethodSession, inputChannel);
                com.android.internal.inputmethod.InputBindResult attachNewInputLocked = attachNewInputLocked(10, true);
                attachNewAccessibilityLocked(10, true);
                if (attachNewInputLocked.method != null) {
                    this.mCurClient.mClient.onBindMethod(attachNewInputLocked);
                }
            }
        } finally {
            android.os.Trace.traceEnd(32L);
        }
    }

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    void resetSystemUiLocked() {
        this.mImeWindowVis = 0;
        this.mBackDisposition = 0;
        updateSystemUiLocked(this.mImeWindowVis, this.mBackDisposition);
        this.mCurTokenDisplayId = -1;
        this.mCurHostInputToken = null;
    }

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    void resetCurrentMethodAndClientLocked(int i) {
        setSelectedMethodIdLocked(null);
        onUnbindCurrentMethodByReset();
        this.mBindingController.unbindCurrentMethod();
        unbindCurrentClientLocked(i);
    }

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    void reRequestCurrentClientSessionLocked() {
        if (this.mCurClient != null) {
            clearClientSessionLocked(this.mCurClient);
            clearClientSessionForAccessibilityLocked(this.mCurClient);
            requestClientSessionLocked(this.mCurClient);
            requestClientSessionForAccessibilityLocked(this.mCurClient);
        }
    }

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    void requestClientSessionLocked(com.android.server.inputmethod.ClientState clientState) {
        if (!clientState.mSessionRequested) {
            android.view.InputChannel[] openInputChannelPair = android.view.InputChannel.openInputChannelPair(clientState.toString());
            final android.view.InputChannel inputChannel = openInputChannelPair[0];
            android.view.InputChannel inputChannel2 = openInputChannelPair[1];
            clientState.mSessionRequested = true;
            final com.android.server.inputmethod.IInputMethodInvoker curMethodLocked = getCurMethodLocked();
            try {
                curMethodLocked.createSession(inputChannel2, new com.android.internal.inputmethod.IInputMethodSessionCallback.Stub() { // from class: com.android.server.inputmethod.InputMethodManagerService.1
                    public void sessionCreated(com.android.internal.inputmethod.IInputMethodSession iInputMethodSession) {
                        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                        try {
                            com.android.server.inputmethod.InputMethodManagerService.this.onSessionCreated(curMethodLocked, iInputMethodSession, inputChannel);
                        } finally {
                            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        }
                    }
                });
            } finally {
                if (inputChannel2 != null) {
                    inputChannel2.dispose();
                }
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    void requestClientSessionForAccessibilityLocked(com.android.server.inputmethod.ClientState clientState) {
        if (!clientState.mSessionRequestedForAccessibility) {
            clientState.mSessionRequestedForAccessibility = true;
            android.util.ArraySet<java.lang.Integer> arraySet = new android.util.ArraySet<>();
            for (int i = 0; i < clientState.mAccessibilitySessions.size(); i++) {
                arraySet.add(java.lang.Integer.valueOf(clientState.mAccessibilitySessions.keyAt(i)));
            }
            com.android.server.AccessibilityManagerInternal.get().createImeSession(arraySet);
        }
    }

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    void clearClientSessionLocked(com.android.server.inputmethod.ClientState clientState) {
        finishSessionLocked(clientState.mCurSession);
        clientState.mCurSession = null;
        clientState.mSessionRequested = false;
    }

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    void clearClientSessionForAccessibilityLocked(com.android.server.inputmethod.ClientState clientState) {
        for (int i = 0; i < clientState.mAccessibilitySessions.size(); i++) {
            finishSessionForAccessibilityLocked(clientState.mAccessibilitySessions.valueAt(i));
        }
        clientState.mAccessibilitySessions.clear();
        clientState.mSessionRequestedForAccessibility = false;
    }

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    void clearClientSessionForAccessibilityLocked(com.android.server.inputmethod.ClientState clientState, int i) {
        com.android.server.inputmethod.InputMethodManagerService.AccessibilitySessionState accessibilitySessionState = clientState.mAccessibilitySessions.get(i);
        if (accessibilitySessionState != null) {
            finishSessionForAccessibilityLocked(accessibilitySessionState);
            clientState.mAccessibilitySessions.remove(i);
        }
    }

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    private void finishSessionLocked(com.android.server.inputmethod.InputMethodManagerService.SessionState sessionState) {
        if (sessionState != null) {
            if (sessionState.mSession != null) {
                try {
                    sessionState.mSession.finishSession();
                } catch (android.os.RemoteException e) {
                    android.util.Slog.w(TAG, "Session failed to close due to remote exception", e);
                    updateSystemUiLocked(0, this.mBackDisposition);
                }
                sessionState.mSession = null;
            }
            if (sessionState.mChannel != null) {
                sessionState.mChannel.dispose();
                sessionState.mChannel = null;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    public void finishSessionForAccessibilityLocked(com.android.server.inputmethod.InputMethodManagerService.AccessibilitySessionState accessibilitySessionState) {
        if (accessibilitySessionState != null && accessibilitySessionState.mSession != null) {
            try {
                accessibilitySessionState.mSession.finishSession();
            } catch (android.os.RemoteException e) {
                android.util.Slog.w(TAG, "Session failed to close due to remote exception", e);
            }
            accessibilitySessionState.mSession = null;
        }
    }

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    void clearClientSessionsLocked() {
        if (getCurMethodLocked() != null) {
            this.mClientController.forAllClients(new java.util.function.Consumer() { // from class: com.android.server.inputmethod.InputMethodManagerService$$ExternalSyntheticLambda11
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.inputmethod.InputMethodManagerService.this.lambda$clearClientSessionsLocked$8((com.android.server.inputmethod.ClientState) obj);
                }
            });
            finishSessionLocked(this.mEnabledSession);
            for (int i = 0; i < this.mEnabledAccessibilitySessions.size(); i++) {
                finishSessionForAccessibilityLocked(this.mEnabledAccessibilitySessions.valueAt(i));
            }
            this.mEnabledSession = null;
            this.mEnabledAccessibilitySessions.clear();
            scheduleNotifyImeUidToAudioService(-1);
        }
        hideStatusBarIconLocked();
        this.mInFullscreenMode = false;
        this.mWindowManagerInternal.setDismissImeOnBackKeyPressed(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$clearClientSessionsLocked$8(com.android.server.inputmethod.ClientState clientState) {
        clearClientSessionLocked(clientState);
        clearClientSessionForAccessibilityLocked(clientState);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateStatusIcon(@android.annotation.NonNull android.os.IBinder iBinder, java.lang.String str, int i) {
        android.content.pm.ApplicationInfo applicationInfo;
        java.lang.CharSequence charSequence;
        synchronized (com.android.server.inputmethod.ImfLock.class) {
            try {
                if (calledWithValidTokenLocked(iBinder)) {
                    long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                    try {
                        if (i == 0) {
                            hideStatusBarIconLocked();
                        } else if (str != null) {
                            android.content.pm.PackageManager packageManagerForUser = getPackageManagerForUser(this.mContext, this.mSettings.getUserId());
                            java.lang.String str2 = null;
                            try {
                                applicationInfo = packageManagerForUser.getApplicationInfo(str, android.content.pm.PackageManager.ApplicationInfoFlags.of(0L));
                            } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                                applicationInfo = null;
                            }
                            if (applicationInfo != null) {
                                charSequence = packageManagerForUser.getApplicationLabel(applicationInfo);
                            } else {
                                charSequence = null;
                            }
                            if (this.mStatusBarManagerInternal != null) {
                                com.android.server.statusbar.StatusBarManagerInternal statusBarManagerInternal = this.mStatusBarManagerInternal;
                                java.lang.String str3 = this.mSlotIme;
                                if (charSequence != null) {
                                    str2 = charSequence.toString();
                                }
                                statusBarManagerInternal.setIcon(str3, str, i, 0, str2);
                                this.mStatusBarManagerInternal.setIconVisibility(this.mSlotIme, true);
                            }
                        }
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    } catch (java.lang.Throwable th) {
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        throw th;
                    }
                }
            } catch (java.lang.Throwable th2) {
                throw th2;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    private void hideStatusBarIconLocked() {
        if (this.mStatusBarManagerInternal != null) {
            this.mStatusBarManagerInternal.setIconVisibility(this.mSlotIme, false);
        }
    }

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    private int getInputMethodNavButtonFlagsLocked() {
        if (this.mImeDrawsImeNavBarResLazyInitFuture != null) {
            com.android.internal.util.ConcurrentUtils.waitForFutureNoInterrupt(this.mImeDrawsImeNavBarResLazyInitFuture, "Waiting for the lazy init of mImeDrawsImeNavBarRes");
        }
        return ((this.mImeDrawsImeNavBarRes != null && this.mImeDrawsImeNavBarRes.get() && this.mWindowManagerInternal.hasNavigationBar(this.mCurTokenDisplayId != -1 ? this.mCurTokenDisplayId : 0)) ? 1 : 0) | (shouldShowImeSwitcherLocked(3) ? 2 : 0);
    }

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    private boolean shouldShowImeSwitcherLocked(int i) {
        if (!this.mShowOngoingImeSwitcherForPhones || this.mMenuController.getSwitchingDialogLocked() != null || !java.util.Objects.equals(getCurIdLocked(), getSelectedMethodIdLocked())) {
            return false;
        }
        if ((this.mWindowManagerInternal.isKeyguardShowingAndNotOccluded() && this.mWindowManagerInternal.isKeyguardSecure(this.mSettings.getUserId())) || (i & 1) == 0 || (i & 4) != 0) {
            return false;
        }
        if (this.mWindowManagerInternal.isHardKeyboardAvailable()) {
            return true;
        }
        if ((i & 2) == 0) {
            return false;
        }
        java.util.ArrayList<android.view.inputmethod.InputMethodInfo> enabledInputMethodListWithFilter = this.mSettings.getEnabledInputMethodListWithFilter(new java.util.function.Predicate() { // from class: com.android.server.inputmethod.InputMethodManagerService$$ExternalSyntheticLambda10
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                return ((android.view.inputmethod.InputMethodInfo) obj).shouldShowInInputMethodPicker();
            }
        });
        int size = enabledInputMethodListWithFilter.size();
        if (size > 2) {
            return true;
        }
        if (size < 1) {
            return false;
        }
        android.view.inputmethod.InputMethodSubtype inputMethodSubtype = null;
        android.view.inputmethod.InputMethodSubtype inputMethodSubtype2 = null;
        int i2 = 0;
        int i3 = 0;
        for (int i4 = 0; i4 < size; i4++) {
            java.util.List<android.view.inputmethod.InputMethodSubtype> enabledInputMethodSubtypeList = this.mSettings.getEnabledInputMethodSubtypeList(enabledInputMethodListWithFilter.get(i4), true);
            int size2 = enabledInputMethodSubtypeList.size();
            if (size2 == 0) {
                i2++;
            } else {
                for (int i5 = 0; i5 < size2; i5++) {
                    android.view.inputmethod.InputMethodSubtype inputMethodSubtype3 = enabledInputMethodSubtypeList.get(i5);
                    if (!inputMethodSubtype3.isAuxiliary()) {
                        i2++;
                        inputMethodSubtype = inputMethodSubtype3;
                    } else {
                        i3++;
                        inputMethodSubtype2 = inputMethodSubtype3;
                    }
                }
            }
        }
        if (i2 > 1 || i3 > 1) {
            return true;
        }
        if (i2 == 1 && i3 == 1) {
            return inputMethodSubtype == null || inputMethodSubtype2 == null || !((inputMethodSubtype.getLocale().equals(inputMethodSubtype2.getLocale()) || inputMethodSubtype2.overridesImplicitlyEnabledSubtype() || inputMethodSubtype.overridesImplicitlyEnabledSubtype()) && inputMethodSubtype.containsExtraValueKey(TAG_TRY_SUPPRESSING_IME_SWITCHER));
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setImeWindowStatus(@android.annotation.NonNull android.os.IBinder iBinder, int i, int i2) {
        int topFocusedDisplayId = this.mWindowManagerInternal.getTopFocusedDisplayId();
        synchronized (com.android.server.inputmethod.ImfLock.class) {
            try {
                if (calledWithValidTokenLocked(iBinder)) {
                    if (this.mCurTokenDisplayId == topFocusedDisplayId || this.mCurTokenDisplayId == 0) {
                        this.mImeWindowVis = i;
                        this.mBackDisposition = i2;
                        updateSystemUiLocked(i, i2);
                        boolean z = false;
                        switch (i2) {
                            case 1:
                                break;
                            case 2:
                                z = true;
                                break;
                            default:
                                if ((i & 2) != 0) {
                                    z = true;
                                    break;
                                }
                                break;
                        }
                        this.mWindowManagerInternal.setDismissImeOnBackKeyPressed(z);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void reportStartInput(@android.annotation.NonNull android.os.IBinder iBinder, android.os.IBinder iBinder2) {
        synchronized (com.android.server.inputmethod.ImfLock.class) {
            try {
                if (calledWithValidTokenLocked(iBinder)) {
                    android.os.IBinder iBinder3 = this.mImeTargetWindowMap.get(iBinder2);
                    if (iBinder3 != null) {
                        this.mWindowManagerInternal.updateInputMethodTargetWindow(iBinder, iBinder3);
                    }
                    this.mLastImeTargetWindow = iBinder3;
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void updateImeWindowStatus(boolean z) {
        synchronized (com.android.server.inputmethod.ImfLock.class) {
            try {
                if (z) {
                    updateSystemUiLocked(0, this.mBackDisposition);
                } else {
                    updateSystemUiLocked();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    void updateSystemUiLocked() {
        updateSystemUiLocked(this.mImeWindowVis, this.mBackDisposition);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0043 A[Catch: all -> 0x0037, TRY_LEAVE, TryCatch #0 {all -> 0x0037, blocks: (B:7:0x000b, B:9:0x000f, B:11:0x0013, B:12:0x001e, B:14:0x0026, B:18:0x003b, B:20:0x0043, B:26:0x001b), top: B:6:0x000b }] */
    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void updateSystemUiLocked(int i, int i2) {
        int i3;
        int i4;
        if (getCurTokenLocked() == null) {
            return;
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            if (!this.mCurPerceptible) {
                if ((i & 2) == 0) {
                    i3 = i;
                } else {
                    i3 = (i & (-3)) | 8;
                }
            } else {
                i3 = i & (-9);
            }
            if (this.mMenuController.getSwitchingDialogLocked() == null && java.util.Objects.equals(getCurIdLocked(), getSelectedMethodIdLocked())) {
                i4 = i2;
                boolean shouldShowImeSwitcherLocked = shouldShowImeSwitcherLocked(i3);
                if (this.mStatusBarManagerInternal != null) {
                    this.mStatusBarManagerInternal.setImeWindowStatus(this.mCurTokenDisplayId, getCurTokenLocked(), i3, i4, shouldShowImeSwitcherLocked);
                }
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
            i4 = 3;
            boolean shouldShowImeSwitcherLocked2 = shouldShowImeSwitcherLocked(i3);
            if (this.mStatusBarManagerInternal != null) {
            }
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    void updateFromSettingsLocked(boolean z) {
        updateInputMethodsFromSettingsLocked(z);
        this.mMenuController.updateKeyboardFromSettingsLocked();
    }

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    void updateInputMethodsFromSettingsLocked(boolean z) {
        android.content.pm.ApplicationInfo applicationInfo;
        if (z) {
            android.content.pm.PackageManager packageManagerForUser = getPackageManagerForUser(this.mContext, this.mSettings.getUserId());
            java.util.ArrayList<android.view.inputmethod.InputMethodInfo> enabledInputMethodList = this.mSettings.getEnabledInputMethodList();
            for (int i = 0; i < enabledInputMethodList.size(); i++) {
                android.view.inputmethod.InputMethodInfo inputMethodInfo = enabledInputMethodList.get(i);
                try {
                    applicationInfo = packageManagerForUser.getApplicationInfo(inputMethodInfo.getPackageName(), android.content.pm.PackageManager.ApplicationInfoFlags.of(32768L));
                } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                    applicationInfo = null;
                }
                if (applicationInfo != null && applicationInfo.enabledSetting == 4) {
                    packageManagerForUser.setApplicationEnabledSetting(inputMethodInfo.getPackageName(), 0, 1);
                }
            }
        }
        if (this.mDeviceIdToShowIme == 0) {
            java.lang.String string = com.android.server.inputmethod.SecureSettingsWrapper.getString("default_input_method", null, this.mSettings.getUserId());
            java.lang.String string2 = com.android.server.inputmethod.SecureSettingsWrapper.getString("default_device_input_method", null, this.mSettings.getUserId());
            if (string2 != null && !java.util.Objects.equals(string, string2)) {
                com.android.server.inputmethod.SecureSettingsWrapper.putString("default_input_method", string2, this.mSettings.getUserId());
                com.android.server.inputmethod.SecureSettingsWrapper.putString("default_device_input_method", null, this.mSettings.getUserId());
            }
        }
        java.lang.String selectedInputMethod = this.mSettings.getSelectedInputMethod();
        if (android.text.TextUtils.isEmpty(selectedInputMethod) && chooseNewDefaultIMELocked()) {
            selectedInputMethod = this.mSettings.getSelectedInputMethod();
        }
        if (!android.text.TextUtils.isEmpty(selectedInputMethod)) {
            try {
                setInputMethodLocked(selectedInputMethod, this.mSettings.getSelectedInputMethodSubtypeId(selectedInputMethod));
            } catch (java.lang.IllegalArgumentException e2) {
                android.util.Slog.w(TAG, "Unknown input method from prefs: " + selectedInputMethod, e2);
                resetCurrentMethodAndClientLocked(5);
            }
        } else {
            resetCurrentMethodAndClientLocked(4);
        }
        if (this.mSettings.getUserId() == this.mSwitchingController.getUserId()) {
            this.mSwitchingController.resetCircularListLocked(this.mSettings.getMethodMap());
        } else {
            this.mSwitchingController = com.android.server.inputmethod.InputMethodSubtypeSwitchingController.createInstanceLocked(this.mContext, this.mSettings.getMethodMap(), this.mSettings.getUserId());
        }
        if (this.mSettings.getUserId() == this.mHardwareKeyboardShortcutController.getUserId()) {
            this.mHardwareKeyboardShortcutController.reset(this.mSettings.getMethodMap());
        } else {
            this.mHardwareKeyboardShortcutController = new com.android.server.inputmethod.HardwareKeyboardShortcutController(this.mSettings.getMethodMap(), this.mSettings.getUserId());
        }
        sendOnNavButtonFlagsChangedLocked();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateTouchPollingRate() {
        if (!this.mLineageHardware.isSupported(8)) {
            return;
        }
        this.mLineageHardware.set(8, lineageos.providers.LineageSettings.System.getInt(this.mContext.getContentResolver(), "high_touch_polling_rate_enable", 0) == 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateTouchSensitivity() {
        if (!this.mLineageHardware.isSupported(16)) {
            return;
        }
        this.mLineageHardware.set(16, lineageos.providers.LineageSettings.System.getInt(this.mContext.getContentResolver(), "high_touch_sensitivity_enable", 0) == 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateTouchHovering() {
        if (!this.mLineageHardware.isSupported(2048)) {
            return;
        }
        this.mLineageHardware.set(2048, lineageos.providers.LineageSettings.Secure.getInt(this.mContext.getContentResolver(), "feature_touch_hovering", 0) == 1);
    }

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    private void notifyInputMethodSubtypeChangedLocked(int i, @android.annotation.NonNull android.view.inputmethod.InputMethodInfo inputMethodInfo, @android.annotation.Nullable android.view.inputmethod.InputMethodSubtype inputMethodSubtype) {
        com.android.internal.inputmethod.InputMethodSubtypeHandle inputMethodSubtypeHandle = null;
        if (inputMethodSubtype == null || !inputMethodSubtype.isSuitableForPhysicalKeyboardLayoutMapping()) {
            inputMethodSubtype = null;
        }
        if (inputMethodSubtype != null) {
            inputMethodSubtypeHandle = com.android.internal.inputmethod.InputMethodSubtypeHandle.of(inputMethodInfo, inputMethodSubtype);
        }
        this.mInputManagerInternal.onInputMethodSubtypeChangedForKeyboardLayoutMapping(i, inputMethodSubtypeHandle, inputMethodSubtype);
    }

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    void setInputMethodLocked(java.lang.String str, int i) {
        setInputMethodLocked(str, i, 0);
    }

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    void setInputMethodLocked(java.lang.String str, int i, int i2) {
        android.view.inputmethod.InputMethodSubtype inputMethodSubtype;
        android.view.inputmethod.InputMethodInfo inputMethodInfo = this.mSettings.getMethodMap().get(str);
        if (inputMethodInfo == null) {
            throw getExceptionForUnknownImeId(str);
        }
        if (str.equals(getSelectedMethodIdLocked())) {
            int userId = this.mSettings.getUserId();
            int subtypeCount = inputMethodInfo.getSubtypeCount();
            if (subtypeCount <= 0) {
                notifyInputMethodSubtypeChangedLocked(userId, inputMethodInfo, null);
                return;
            }
            android.view.inputmethod.InputMethodSubtype inputMethodSubtype2 = this.mCurrentSubtype;
            if (i >= 0 && i < subtypeCount) {
                inputMethodSubtype = inputMethodInfo.getSubtypeAt(i);
            } else {
                android.view.inputmethod.InputMethodSubtype currentInputMethodSubtypeLocked = getCurrentInputMethodSubtypeLocked();
                if (currentInputMethodSubtypeLocked != null) {
                    for (int i3 = 0; i3 < subtypeCount; i3++) {
                        if (java.util.Objects.equals(currentInputMethodSubtypeLocked, inputMethodInfo.getSubtypeAt(i3))) {
                            inputMethodSubtype = currentInputMethodSubtypeLocked;
                            i = i3;
                            break;
                        }
                    }
                }
                inputMethodSubtype = currentInputMethodSubtypeLocked;
                i = -1;
            }
            if (!java.util.Objects.equals(inputMethodSubtype, inputMethodSubtype2)) {
                setSelectedInputMethodAndSubtypeLocked(inputMethodInfo, i, true);
                com.android.server.inputmethod.IInputMethodInvoker curMethodLocked = getCurMethodLocked();
                if (curMethodLocked != null) {
                    updateSystemUiLocked(this.mImeWindowVis, this.mBackDisposition);
                    curMethodLocked.changeInputMethodSubtype(inputMethodSubtype);
                    return;
                }
                return;
            }
            return;
        }
        if (this.mDeviceIdToShowIme != 0 && i2 == 0) {
            this.mSettings.putSelectedDefaultDeviceInputMethod(str);
            return;
        }
        com.android.server.inputmethod.IInputMethodInvoker curMethodLocked2 = getCurMethodLocked();
        if (curMethodLocked2 != null) {
            curMethodLocked2.removeStylusHandwritingWindow();
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            setSelectedInputMethodAndSubtypeLocked(inputMethodInfo, i, false);
            setSelectedMethodIdLocked(str);
            if (this.mActivityManagerInternal.isSystemReady()) {
                android.content.Intent intent = new android.content.Intent("android.intent.action.INPUT_METHOD_CHANGED");
                intent.addFlags(536870912);
                intent.putExtra("input_method_id", str);
                this.mContext.sendBroadcastAsUser(intent, android.os.UserHandle.CURRENT);
            }
            unbindCurrentClientLocked(2);
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public boolean showSoftInput(com.android.internal.inputmethod.IInputMethodClient iInputMethodClient, android.os.IBinder iBinder, @android.annotation.NonNull android.view.inputmethod.ImeTracker.Token token, int i, int i2, android.os.ResultReceiver resultReceiver, int i3) {
        android.os.Trace.traceBegin(32L, "IMMS.showSoftInput");
        int callingUid = android.os.Binder.getCallingUid();
        com.android.internal.inputmethod.ImeTracing.getInstance().triggerManagerServiceDump("InputMethodManagerService#showSoftInput");
        synchronized (com.android.server.inputmethod.ImfLock.class) {
            try {
                if (!canInteractWithImeLocked(callingUid, iInputMethodClient, "showSoftInput", token)) {
                    android.view.inputmethod.ImeTracker.forLogging().onFailed(token, 3);
                    android.os.Trace.traceEnd(32L);
                    return false;
                }
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    return showCurrentInputLocked(iBinder, token, i, i2, resultReceiver, i3);
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    android.os.Trace.traceEnd(32L);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void startStylusHandwriting(com.android.internal.inputmethod.IInputMethodClient iInputMethodClient) {
        startStylusHandwriting(iInputMethodClient, false);
    }

    public void startConnectionlessStylusHandwriting(com.android.internal.inputmethod.IInputMethodClient iInputMethodClient, final int i, @android.annotation.Nullable android.view.inputmethod.CursorAnchorInfo cursorAnchorInfo, @android.annotation.Nullable final java.lang.String str, @android.annotation.Nullable final java.lang.String str2, @android.annotation.NonNull final com.android.internal.inputmethod.IConnectionlessHandwritingCallback iConnectionlessHandwritingCallback) throws android.os.RemoteException {
        com.android.internal.inputmethod.IConnectionlessHandwritingCallback iConnectionlessHandwritingCallback2;
        synchronized (com.android.server.inputmethod.ImfLock.class) {
            try {
                if (!this.mBindingController.supportsConnectionlessStylusHandwriting()) {
                    android.util.Slog.w(TAG, "Connectionless stylus handwriting mode unsupported by IME.");
                    iConnectionlessHandwritingCallback.onError(1);
                    return;
                }
                boolean z = (str == null || str2 == null) ? false : true;
                if (z) {
                    synchronized (com.android.server.inputmethod.ImfLock.class) {
                        if (!this.mClientController.verifyClientAndPackageMatch(iInputMethodClient, str2)) {
                            android.util.Slog.w(TAG, "startConnectionlessStylusHandwriting() fail");
                            iConnectionlessHandwritingCallback.onError(2);
                            throw new java.lang.IllegalArgumentException("Delegator doesn't match UID");
                        }
                    }
                    iConnectionlessHandwritingCallback2 = new com.android.internal.inputmethod.IConnectionlessHandwritingCallback.Stub() { // from class: com.android.server.inputmethod.InputMethodManagerService.2
                        public void onResult(java.lang.CharSequence charSequence) throws android.os.RemoteException {
                            synchronized (com.android.server.inputmethod.ImfLock.class) {
                                com.android.server.inputmethod.InputMethodManagerService.this.mHwController.prepareStylusHandwritingDelegation(i, str, str2, true);
                            }
                            iConnectionlessHandwritingCallback.onResult(charSequence);
                        }

                        public void onError(int i2) throws android.os.RemoteException {
                            iConnectionlessHandwritingCallback.onError(i2);
                        }
                    };
                } else {
                    iConnectionlessHandwritingCallback2 = iConnectionlessHandwritingCallback;
                }
                if (!startStylusHandwriting(iInputMethodClient, false, iConnectionlessHandwritingCallback2, cursorAnchorInfo, z)) {
                    iConnectionlessHandwritingCallback.onError(2);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void startStylusHandwriting(com.android.internal.inputmethod.IInputMethodClient iInputMethodClient, boolean z) {
        startStylusHandwriting(iInputMethodClient, z, null, null, false);
    }

    private boolean startStylusHandwriting(com.android.internal.inputmethod.IInputMethodClient iInputMethodClient, boolean z, com.android.internal.inputmethod.IConnectionlessHandwritingCallback iConnectionlessHandwritingCallback, android.view.inputmethod.CursorAnchorInfo cursorAnchorInfo, boolean z2) {
        android.os.Trace.traceBegin(32L, "IMMS.startStylusHandwriting");
        try {
            com.android.internal.inputmethod.ImeTracing.getInstance().triggerManagerServiceDump("InputMethodManagerService#startStylusHandwriting");
            int callingUid = android.os.Binder.getCallingUid();
            synchronized (com.android.server.inputmethod.ImfLock.class) {
                if (!z) {
                    try {
                        this.mHwController.clearPendingHandwritingDelegation();
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
                if (!canInteractWithImeLocked(callingUid, iInputMethodClient, "startStylusHandwriting", null)) {
                    return false;
                }
                if (!hasSupportedStylusLocked()) {
                    android.util.Slog.w(TAG, "No supported Stylus hardware found on device. Ignoring startStylusHandwriting()");
                    return false;
                }
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    if (!this.mBindingController.supportsStylusHandwriting()) {
                        android.util.Slog.w(TAG, "Stylus HW unsupported by IME. Ignoring startStylusHandwriting()");
                        return false;
                    }
                    java.util.OptionalInt currentRequestId = this.mHwController.getCurrentRequestId();
                    if (!currentRequestId.isPresent()) {
                        android.util.Slog.e(TAG, "Stylus handwriting was not initialized.");
                        return false;
                    }
                    if (!this.mHwController.isStylusGestureOngoing()) {
                        android.util.Slog.e(TAG, "There is no ongoing stylus gesture to start stylus handwriting.");
                        return false;
                    }
                    if (this.mHwController.hasOngoingStylusHandwritingSession()) {
                        android.util.Slog.e(TAG, "Stylus handwriting session is already ongoing. Ignoring startStylusHandwriting().");
                        return false;
                    }
                    com.android.server.inputmethod.IInputMethodInvoker curMethodLocked = getCurMethodLocked();
                    if (curMethodLocked == null) {
                        return false;
                    }
                    curMethodLocked.canStartStylusHandwriting(currentRequestId.getAsInt(), iConnectionlessHandwritingCallback, cursorAnchorInfo, z2);
                    android.os.Trace.traceEnd(32L);
                    return true;
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        } finally {
            android.os.Trace.traceEnd(32L);
        }
    }

    public void prepareStylusHandwritingDelegation(@android.annotation.NonNull com.android.internal.inputmethod.IInputMethodClient iInputMethodClient, int i, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2) {
        if (!isStylusHandwritingEnabled(this.mContext, i)) {
            android.util.Slog.w(TAG, "Can not prepare stylus handwriting delegation. Stylus handwriting pref is disabled for user: " + i);
            return;
        }
        synchronized (com.android.server.inputmethod.ImfLock.class) {
            if (!this.mClientController.verifyClientAndPackageMatch(iInputMethodClient, str2)) {
                android.util.Slog.w(TAG, "prepareStylusHandwritingDelegation() fail");
                throw new java.lang.IllegalArgumentException("Delegator doesn't match Uid");
            }
        }
        schedulePrepareStylusHandwritingDelegation(i, str, str2);
    }

    public void acceptStylusHandwritingDelegationAsync(@android.annotation.NonNull com.android.internal.inputmethod.IInputMethodClient iInputMethodClient, int i, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2, int i2, com.android.internal.inputmethod.IBooleanListener iBooleanListener) throws android.os.RemoteException {
        iBooleanListener.onResult(acceptStylusHandwritingDelegation(iInputMethodClient, i, str, str2, i2));
    }

    public boolean acceptStylusHandwritingDelegation(@android.annotation.NonNull com.android.internal.inputmethod.IInputMethodClient iInputMethodClient, int i, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2, int i2) {
        if (!isStylusHandwritingEnabled(this.mContext, i)) {
            android.util.Slog.w(TAG, "Can not accept stylus handwriting delegation. Stylus handwriting pref is disabled for user: " + i);
            return false;
        }
        if (!verifyDelegator(iInputMethodClient, str, str2, i2)) {
            return false;
        }
        synchronized (com.android.server.inputmethod.ImfLock.class) {
            try {
                if (this.mHwController.isDelegationUsingConnectionlessFlow()) {
                    com.android.server.inputmethod.IInputMethodInvoker curMethodLocked = getCurMethodLocked();
                    if (curMethodLocked == null) {
                        return false;
                    }
                    curMethodLocked.commitHandwritingDelegationTextIfAvailable();
                    this.mHwController.clearPendingHandwritingDelegation();
                } else {
                    startStylusHandwriting(iInputMethodClient, true);
                }
                return true;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private boolean verifyDelegator(@android.annotation.NonNull com.android.internal.inputmethod.IInputMethodClient iInputMethodClient, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2, int i) {
        synchronized (com.android.server.inputmethod.ImfLock.class) {
            try {
                if (!this.mClientController.verifyClientAndPackageMatch(iInputMethodClient, str)) {
                    android.util.Slog.w(TAG, "Delegate package does not belong to the same user. Ignoring startStylusHandwriting");
                    return false;
                }
                boolean z = (i & 1) != 0;
                if (!str2.equals(this.mHwController.getDelegatorPackageName()) && (!z || !this.mHwController.isDelegatorFromDefaultHomePackage())) {
                    android.util.Slog.w(TAG, "Delegator package does not match. Ignoring startStylusHandwriting");
                    return false;
                }
                if (str.equals(this.mHwController.getDelegatePackageName())) {
                    return true;
                }
                android.util.Slog.w(TAG, "Delegate package does not match. Ignoring startStylusHandwriting");
                return false;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void reportPerceptibleAsync(final android.os.IBinder iBinder, final boolean z) {
        android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.inputmethod.InputMethodManagerService$$ExternalSyntheticLambda15
            public final void runOrThrow() {
                com.android.server.inputmethod.InputMethodManagerService.this.lambda$reportPerceptibleAsync$9(iBinder, z);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$reportPerceptibleAsync$9(android.os.IBinder iBinder, boolean z) throws java.lang.Exception {
        java.util.Objects.requireNonNull(iBinder, "windowToken must not be null");
        synchronized (com.android.server.inputmethod.ImfLock.class) {
            try {
                if (this.mCurFocusedWindow != iBinder || this.mCurPerceptible == z) {
                    return;
                }
                this.mCurPerceptible = z;
                updateSystemUiLocked();
            } finally {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    public boolean showCurrentInputLocked(android.os.IBinder iBinder, int i, int i2) {
        return showCurrentInputLocked(iBinder, createStatsTokenForFocusedClient(true, i2), i, 0, null, i2);
    }

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    boolean showCurrentInputLocked(android.os.IBinder iBinder, @android.annotation.NonNull android.view.inputmethod.ImeTracker.Token token, int i, int i2, @android.annotation.Nullable android.os.ResultReceiver resultReceiver, int i3) {
        if (!this.mVisibilityStateComputer.onImeShowFlags(token, i)) {
            return false;
        }
        if (!this.mSystemReady) {
            android.view.inputmethod.ImeTracker.forLogging().onFailed(token, 5);
            return false;
        }
        android.view.inputmethod.ImeTracker.forLogging().onProgress(token, 5);
        this.mVisibilityStateComputer.requestImeVisibility(iBinder, true);
        this.mBindingController.setCurrentMethodVisible();
        com.android.server.inputmethod.IInputMethodInvoker curMethodLocked = getCurMethodLocked();
        android.view.inputmethod.ImeTracker.forLogging().onCancelled(this.mCurStatsToken, 8);
        if (curMethodLocked != null) {
            android.view.inputmethod.ImeTracker.forLogging().onProgress(token, 9);
            this.mCurStatsToken = null;
            if (i2 != 0) {
                curMethodLocked.updateEditorToolType(i2);
            }
            this.mVisibilityApplier.performShowIme(iBinder, token, this.mVisibilityStateComputer.getShowFlagsForInputMethodServiceOnly(), resultReceiver, i3);
            this.mVisibilityStateComputer.setInputShown(true);
            return true;
        }
        android.view.inputmethod.ImeTracker.forLogging().onProgress(token, 8);
        this.mCurStatsToken = token;
        return false;
    }

    public boolean hideSoftInput(com.android.internal.inputmethod.IInputMethodClient iInputMethodClient, android.os.IBinder iBinder, @android.annotation.NonNull android.view.inputmethod.ImeTracker.Token token, int i, android.os.ResultReceiver resultReceiver, int i2) {
        int callingUid = android.os.Binder.getCallingUid();
        com.android.internal.inputmethod.ImeTracing.getInstance().triggerManagerServiceDump("InputMethodManagerService#hideSoftInput");
        synchronized (com.android.server.inputmethod.ImfLock.class) {
            try {
                if (!canInteractWithImeLocked(callingUid, iInputMethodClient, "hideSoftInput", token)) {
                    if (isInputShown()) {
                        android.view.inputmethod.ImeTracker.forLogging().onFailed(token, 3);
                    } else {
                        android.view.inputmethod.ImeTracker.forLogging().onCancelled(token, 3);
                    }
                    return false;
                }
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    android.os.Trace.traceBegin(32L, "IMMS.hideSoftInput");
                    return hideCurrentInputLocked(iBinder, token, i, resultReceiver, i2);
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    android.os.Trace.traceEnd(32L);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @android.annotation.EnforcePermission("android.permission.TEST_INPUT_METHOD")
    public void hideSoftInputFromServerForTest() {
        super.hideSoftInputFromServerForTest_enforcePermission();
        synchronized (com.android.server.inputmethod.ImfLock.class) {
            hideCurrentInputLocked(this.mCurFocusedWindow, 0, 4);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    public boolean hideCurrentInputLocked(android.os.IBinder iBinder, int i, int i2) {
        return hideCurrentInputLocked(iBinder, createStatsTokenForFocusedClient(false, i2), i, null, i2);
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x001a, code lost:
    
        if ((r2.mImeWindowVis & 1) == 0) goto L12;
     */
    /* JADX WARN: Removed duplicated region for block: B:13:0x0027  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0034  */
    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    boolean hideCurrentInputLocked(android.os.IBinder iBinder, @android.annotation.NonNull android.view.inputmethod.ImeTracker.Token token, int i, @android.annotation.Nullable android.os.ResultReceiver resultReceiver, int i2) {
        boolean z;
        if (!this.mVisibilityStateComputer.canHideIme(token, i)) {
            return false;
        }
        if (getCurMethodLocked() != null) {
            z = true;
            if (!isInputShown()) {
            }
            this.mVisibilityStateComputer.requestImeVisibility(iBinder, false);
            if (!z) {
                android.view.inputmethod.ImeTracker.forLogging().onProgress(token, 10);
                this.mVisibilityApplier.performHideIme(iBinder, token, resultReceiver, i2);
            } else {
                android.view.inputmethod.ImeTracker.forLogging().onCancelled(token, 10);
            }
            this.mBindingController.setCurrentMethodNotVisible();
            this.mVisibilityStateComputer.clearImeShowFlags();
            android.view.inputmethod.ImeTracker.forLogging().onCancelled(this.mCurStatsToken, 8);
            this.mCurStatsToken = null;
            return z;
        }
        z = false;
        this.mVisibilityStateComputer.requestImeVisibility(iBinder, false);
        if (!z) {
        }
        this.mBindingController.setCurrentMethodNotVisible();
        this.mVisibilityStateComputer.clearImeShowFlags();
        android.view.inputmethod.ImeTracker.forLogging().onCancelled(this.mCurStatsToken, 8);
        this.mCurStatsToken = null;
        return z;
    }

    private boolean isImeClientFocused(android.os.IBinder iBinder, com.android.server.inputmethod.ClientState clientState) {
        return this.mWindowManagerInternal.hasInputMethodClientFocus(iBinder, clientState.mUid, clientState.mPid, clientState.mSelfReportedDisplayId) == 0;
    }

    public void startInputOrWindowGainedFocusAsync(int i, com.android.internal.inputmethod.IInputMethodClient iInputMethodClient, android.os.IBinder iBinder, int i2, int i3, int i4, @android.annotation.Nullable android.view.inputmethod.EditorInfo editorInfo, com.android.internal.inputmethod.IRemoteInputConnection iRemoteInputConnection, com.android.internal.inputmethod.IRemoteAccessibilityInputConnection iRemoteAccessibilityInputConnection, int i5, int i6, @android.annotation.NonNull android.window.ImeOnBackInvokedDispatcher imeOnBackInvokedDispatcher, int i7) {
    }

    @android.annotation.NonNull
    public com.android.internal.inputmethod.InputBindResult startInputOrWindowGainedFocus(int i, com.android.internal.inputmethod.IInputMethodClient iInputMethodClient, android.os.IBinder iBinder, int i2, int i3, int i4, @android.annotation.Nullable android.view.inputmethod.EditorInfo editorInfo, com.android.internal.inputmethod.IRemoteInputConnection iRemoteInputConnection, com.android.internal.inputmethod.IRemoteAccessibilityInputConnection iRemoteAccessibilityInputConnection, int i5, int i6, @android.annotation.NonNull android.window.ImeOnBackInvokedDispatcher imeOnBackInvokedDispatcher) {
        if (android.os.UserHandle.getCallingUserId() != i6) {
            this.mContext.enforceCallingOrSelfPermission("android.permission.INTERACT_ACROSS_USERS_FULL", null);
            if (editorInfo == null || editorInfo.targetInputMethodUser == null || editorInfo.targetInputMethodUser.getIdentifier() != i6) {
                throw new java.security.InvalidParameterException("EditorInfo#targetInputMethodUser must also be specified for cross-user startInputOrWindowGainedFocus()");
            }
        }
        if (iBinder == null) {
            android.util.Slog.e(TAG, "windowToken cannot be null.");
            return com.android.internal.inputmethod.InputBindResult.NULL;
        }
        if (!this.mUserManagerInternal.isUserRunning(i6)) {
            android.util.Slog.w(TAG, "User #" + i6 + " is not running.");
            return com.android.internal.inputmethod.InputBindResult.INVALID_USER;
        }
        try {
            android.os.Trace.traceBegin(32L, "IMMS.startInputOrWindowGainedFocus");
            com.android.internal.inputmethod.ImeTracing.getInstance().triggerManagerServiceDump("InputMethodManagerService#startInputOrWindowGainedFocus");
        } catch (java.lang.Throwable th) {
            th = th;
        }
        try {
            try {
                synchronized (com.android.server.inputmethod.ImfLock.class) {
                    try {
                        if (!this.mSystemReady) {
                            com.android.internal.inputmethod.InputBindResult inputBindResult = new com.android.internal.inputmethod.InputBindResult(8, (com.android.internal.inputmethod.IInputMethodSession) null, (android.util.SparseArray) null, (android.view.InputChannel) null, getSelectedMethodIdLocked(), getSequenceNumberLocked(), false);
                            android.os.Trace.traceEnd(32L);
                            return inputBindResult;
                        }
                        com.android.server.inputmethod.ClientState client = this.mClientController.getClient(iInputMethodClient.asBinder());
                        if (client == null) {
                            throw new java.lang.IllegalArgumentException("Unknown client " + iInputMethodClient.asBinder());
                        }
                        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                        try {
                            if (this.mUserSwitchHandlerTask != null) {
                                if (i6 == this.mUserSwitchHandlerTask.mToUserId) {
                                    scheduleSwitchUserTaskLocked(i6, client.mClient);
                                    com.android.internal.inputmethod.InputBindResult inputBindResult2 = com.android.internal.inputmethod.InputBindResult.USER_SWITCHING;
                                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                                    android.os.Trace.traceEnd(32L);
                                    return inputBindResult2;
                                }
                                for (int i7 : this.mUserManagerInternal.getProfileIds(this.mSettings.getUserId(), false)) {
                                    if (i7 == i6) {
                                        scheduleSwitchUserTaskLocked(i6, client.mClient);
                                        com.android.internal.inputmethod.InputBindResult inputBindResult3 = com.android.internal.inputmethod.InputBindResult.USER_SWITCHING;
                                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                                        android.os.Trace.traceEnd(32L);
                                        return inputBindResult3;
                                    }
                                }
                                com.android.internal.inputmethod.InputBindResult inputBindResult4 = com.android.internal.inputmethod.InputBindResult.INVALID_USER;
                                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                                android.os.Trace.traceEnd(32L);
                                return inputBindResult4;
                            }
                            try {
                                switch (this.mWindowManagerInternal.hasInputMethodClientFocus(iBinder, client.mUid, client.mPid, client.mSelfReportedDisplayId)) {
                                    case -3:
                                        com.android.internal.inputmethod.InputBindResult inputBindResult5 = com.android.internal.inputmethod.InputBindResult.INVALID_DISPLAY_ID;
                                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                                        android.os.Trace.traceEnd(32L);
                                        return inputBindResult5;
                                    case -2:
                                        android.util.Slog.e(TAG, "startInputOrWindowGainedFocusInternal: display ID mismatch.");
                                        com.android.internal.inputmethod.InputBindResult inputBindResult6 = com.android.internal.inputmethod.InputBindResult.DISPLAY_ID_MISMATCH;
                                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                                        android.os.Trace.traceEnd(32L);
                                        return inputBindResult6;
                                    case -1:
                                        com.android.internal.inputmethod.InputBindResult inputBindResult7 = com.android.internal.inputmethod.InputBindResult.NOT_IME_TARGET_WINDOW;
                                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                                        android.os.Trace.traceEnd(32L);
                                        return inputBindResult7;
                                    default:
                                        boolean shouldClearShowForcedFlag = this.mImePlatformCompatUtils.shouldClearShowForcedFlag(client.mUid);
                                        boolean z = this.mVisibilityStateComputer.mShowForced;
                                        if (this.mCurFocusedWindow != iBinder && z && shouldClearShowForcedFlag) {
                                            this.mVisibilityStateComputer.mShowForced = false;
                                        }
                                        int userId = this.mSettings.getUserId();
                                        if (i6 != userId) {
                                            if (com.android.internal.util.ArrayUtils.contains(this.mUserManagerInternal.getProfileIds(userId, false), i6)) {
                                                scheduleSwitchUserTaskLocked(i6, client.mClient);
                                                com.android.internal.inputmethod.InputBindResult inputBindResult8 = com.android.internal.inputmethod.InputBindResult.USER_SWITCHING;
                                                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                                                android.os.Trace.traceEnd(32L);
                                                return inputBindResult8;
                                            }
                                            android.util.Slog.w(TAG, "A background user is requesting window. Hiding IME.");
                                            android.util.Slog.w(TAG, "If you need to impersonate a foreground user/profile from a background user, use EditorInfo.targetInputMethodUser with INTERACT_ACROSS_USERS_FULL permission.");
                                            hideCurrentInputLocked(this.mCurFocusedWindow, 0, 11);
                                            com.android.internal.inputmethod.InputBindResult inputBindResult9 = com.android.internal.inputmethod.InputBindResult.INVALID_USER;
                                            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                                            android.os.Trace.traceEnd(32L);
                                            return inputBindResult9;
                                        }
                                        if (editorInfo != null && !com.android.server.inputmethod.InputMethodUtils.checkIfPackageBelongsToUid(this.mPackageManagerInternal, client.mUid, editorInfo.packageName)) {
                                            android.util.Slog.e(TAG, "Rejecting this client as it reported an invalid package name. uid=" + client.mUid + " package=" + editorInfo.packageName);
                                            com.android.internal.inputmethod.InputBindResult inputBindResult10 = com.android.internal.inputmethod.InputBindResult.INVALID_PACKAGE_NAME;
                                            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                                            android.os.Trace.traceEnd(32L);
                                            return inputBindResult10;
                                        }
                                        com.android.internal.inputmethod.InputBindResult startInputOrWindowGainedFocusInternalLocked = startInputOrWindowGainedFocusInternalLocked(i, iInputMethodClient, iBinder, i2, i3, i4, editorInfo, iRemoteInputConnection, iRemoteAccessibilityInputConnection, i5, i6, imeOnBackInvokedDispatcher, client);
                                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                                        if (startInputOrWindowGainedFocusInternalLocked != null) {
                                            android.os.Trace.traceEnd(32L);
                                            return startInputOrWindowGainedFocusInternalLocked;
                                        }
                                        android.util.Slog.wtf(TAG, "InputBindResult is @NonNull. startInputReason=" + com.android.internal.inputmethod.InputMethodDebug.startInputReasonToString(i) + " windowFlags=#" + java.lang.Integer.toHexString(i4) + " editorInfo=" + editorInfo);
                                        com.android.internal.inputmethod.InputBindResult inputBindResult11 = com.android.internal.inputmethod.InputBindResult.NULL;
                                        android.os.Trace.traceEnd(32L);
                                        return inputBindResult11;
                                }
                            } catch (java.lang.Throwable th2) {
                                th = th2;
                                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                                throw th;
                            }
                        } catch (java.lang.Throwable th3) {
                            th = th3;
                        }
                    } catch (java.lang.Throwable th4) {
                        th = th4;
                        throw th;
                    }
                }
            } catch (java.lang.Throwable th5) {
                th = th5;
            }
        } catch (java.lang.Throwable th6) {
            th = th6;
            android.os.Trace.traceEnd(32L);
            throw th;
        }
    }

    @android.annotation.NonNull
    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    private com.android.internal.inputmethod.InputBindResult startInputOrWindowGainedFocusInternalLocked(int i, com.android.internal.inputmethod.IInputMethodClient iInputMethodClient, @android.annotation.NonNull android.os.IBinder iBinder, int i2, int i3, int i4, android.view.inputmethod.EditorInfo editorInfo, com.android.internal.inputmethod.IRemoteInputConnection iRemoteInputConnection, @android.annotation.Nullable com.android.internal.inputmethod.IRemoteAccessibilityInputConnection iRemoteAccessibilityInputConnection, int i5, int i6, @android.annotation.NonNull android.window.ImeOnBackInvokedDispatcher imeOnBackInvokedDispatcher, @android.annotation.NonNull com.android.server.inputmethod.ClientState clientState) {
        boolean z = false;
        boolean z2 = true;
        boolean z3 = this.mCurFocusedWindow == iBinder;
        boolean z4 = (i2 & 2) != 0;
        com.android.server.inputmethod.ImeVisibilityStateComputer.ImeTargetWindowState imeTargetWindowState = new com.android.server.inputmethod.ImeVisibilityStateComputer.ImeTargetWindowState(i3, i4, !z3, z4, (i2 & 8) != 0, editorInfo != null ? editorInfo.getInitialToolType() : 0);
        this.mVisibilityStateComputer.setWindowState(iBinder, imeTargetWindowState);
        if (z3 && z4) {
            if (editorInfo != null) {
                return startInputUncheckedLocked(clientState, iRemoteInputConnection, iRemoteAccessibilityInputConnection, editorInfo, i2, i, i5, imeOnBackInvokedDispatcher);
            }
            return new com.android.internal.inputmethod.InputBindResult(4, (com.android.internal.inputmethod.IInputMethodSession) null, (android.util.SparseArray) null, (android.view.InputChannel) null, (java.lang.String) null, -1, false);
        }
        this.mCurFocusedWindow = iBinder;
        this.mCurFocusedWindowSoftInputMode = i3;
        this.mCurFocusedWindowClient = clientState;
        this.mCurFocusedWindowEditorInfo = editorInfo;
        this.mCurPerceptible = true;
        com.android.server.inputmethod.ImeVisibilityStateComputer.ImeVisibilityResult computeState = this.mVisibilityStateComputer.computeState(imeTargetWindowState, com.android.server.inputmethod.InputMethodUtils.isSoftInputModeStateVisibleAllowed(i5, i2));
        com.android.internal.inputmethod.InputBindResult inputBindResult = null;
        if (computeState != null) {
            switch (computeState.getReason()) {
                case 6:
                case 7:
                case 8:
                case 23:
                    if (editorInfo == null) {
                        z2 = false;
                        z = true;
                        break;
                    } else {
                        inputBindResult = startInputUncheckedLocked(clientState, iRemoteInputConnection, iRemoteAccessibilityInputConnection, editorInfo, i2, i, i5, imeOnBackInvokedDispatcher);
                        z = true;
                        break;
                    }
                default:
                    z2 = false;
                    break;
            }
            this.mVisibilityApplier.applyImeVisibility(this.mCurFocusedWindow, createStatsTokenForFocusedClient(z, computeState.getReason()), computeState.getState(), computeState.getReason());
            if (computeState.getReason() == 12 && clientState.mSelfReportedDisplayId != this.mCurTokenDisplayId) {
                this.mBindingController.unbindCurrentMethod();
            }
            z = z2;
        }
        if (!z) {
            if (editorInfo != null) {
                return startInputUncheckedLocked(clientState, iRemoteInputConnection, iRemoteAccessibilityInputConnection, editorInfo, i2, i, i5, imeOnBackInvokedDispatcher);
            }
            return com.android.internal.inputmethod.InputBindResult.NULL_EDITOR_INFO;
        }
        return inputBindResult;
    }

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    private boolean canInteractWithImeLocked(int i, com.android.internal.inputmethod.IInputMethodClient iInputMethodClient, java.lang.String str, @android.annotation.Nullable android.view.inputmethod.ImeTracker.Token token) {
        if (this.mCurClient == null || iInputMethodClient == null || this.mCurClient.mClient.asBinder() != iInputMethodClient.asBinder()) {
            com.android.server.inputmethod.ClientState client = this.mClientController.getClient(iInputMethodClient.asBinder());
            if (client != null) {
                android.view.inputmethod.ImeTracker.forLogging().onProgress(token, 2);
                if (!isImeClientFocused(this.mCurFocusedWindow, client)) {
                    android.util.Slog.w(TAG, java.lang.String.format("Ignoring %s of uid %d : %s", str, java.lang.Integer.valueOf(i), iInputMethodClient));
                    return false;
                }
            } else {
                android.view.inputmethod.ImeTracker.forLogging().onFailed(token, 2);
                throw new java.lang.IllegalArgumentException("unknown client " + iInputMethodClient.asBinder());
            }
        }
        android.view.inputmethod.ImeTracker.forLogging().onProgress(token, 3);
        return true;
    }

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    private boolean canShowInputMethodPickerLocked(com.android.internal.inputmethod.IInputMethodClient iInputMethodClient) {
        int callingUid = android.os.Binder.getCallingUid();
        if (this.mCurFocusedWindowClient == null || iInputMethodClient == null || this.mCurFocusedWindowClient.mClient.asBinder() != iInputMethodClient.asBinder()) {
            return this.mSettings.getUserId() == android.os.UserHandle.getUserId(callingUid) && getCurIntentLocked() != null && com.android.server.inputmethod.InputMethodUtils.checkIfPackageBelongsToUid(this.mPackageManagerInternal, callingUid, getCurIntentLocked().getComponent().getPackageName());
        }
        return true;
    }

    public void showInputMethodPickerFromClient(com.android.internal.inputmethod.IInputMethodClient iInputMethodClient, int i) {
        synchronized (com.android.server.inputmethod.ImfLock.class) {
            try {
                if (!canShowInputMethodPickerLocked(iInputMethodClient)) {
                    android.util.Slog.w(TAG, "Ignoring showInputMethodPickerFromClient of uid " + android.os.Binder.getCallingUid() + ": " + iInputMethodClient);
                    return;
                }
                this.mHandler.obtainMessage(1, i, this.mCurClient != null ? this.mCurClient.mSelfReportedDisplayId : 0).sendToTarget();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @android.annotation.EnforcePermission("android.permission.WRITE_SECURE_SETTINGS")
    public void showInputMethodPickerFromSystem(int i, int i2) {
        super.showInputMethodPickerFromSystem_enforcePermission();
        this.mHandler.obtainMessage(1, i, i2).sendToTarget();
    }

    @android.annotation.EnforcePermission("android.permission.TEST_INPUT_METHOD")
    public boolean isInputMethodPickerShownForTest() {
        boolean isisInputMethodPickerShownForTestLocked;
        super.isInputMethodPickerShownForTest_enforcePermission();
        synchronized (com.android.server.inputmethod.ImfLock.class) {
            isisInputMethodPickerShownForTestLocked = this.mMenuController.isisInputMethodPickerShownForTestLocked();
        }
        return isisInputMethodPickerShownForTestLocked;
    }

    @android.annotation.NonNull
    private static java.lang.IllegalArgumentException getExceptionForUnknownImeId(@android.annotation.Nullable java.lang.String str) {
        return new java.lang.IllegalArgumentException("Unknown id: " + str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setInputMethod(@android.annotation.NonNull android.os.IBinder iBinder, java.lang.String str) {
        int callingUid = android.os.Binder.getCallingUid();
        int userId = android.os.UserHandle.getUserId(callingUid);
        synchronized (com.android.server.inputmethod.ImfLock.class) {
            try {
                if (calledWithValidTokenLocked(iBinder)) {
                    android.view.inputmethod.InputMethodInfo inputMethodInfo = this.mSettings.getMethodMap().get(str);
                    if (inputMethodInfo == null || !canCallerAccessInputMethod(inputMethodInfo.getPackageName(), callingUid, userId, this.mSettings)) {
                        throw getExceptionForUnknownImeId(str);
                    }
                    setInputMethodWithSubtypeIdLocked(iBinder, str, -1);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setInputMethodAndSubtype(@android.annotation.NonNull android.os.IBinder iBinder, java.lang.String str, android.view.inputmethod.InputMethodSubtype inputMethodSubtype) {
        int callingUid = android.os.Binder.getCallingUid();
        int userId = android.os.UserHandle.getUserId(callingUid);
        synchronized (com.android.server.inputmethod.ImfLock.class) {
            try {
                if (calledWithValidTokenLocked(iBinder)) {
                    android.view.inputmethod.InputMethodInfo inputMethodInfo = this.mSettings.getMethodMap().get(str);
                    if (inputMethodInfo == null || !canCallerAccessInputMethod(inputMethodInfo.getPackageName(), callingUid, userId, this.mSettings)) {
                        throw getExceptionForUnknownImeId(str);
                    }
                    if (inputMethodSubtype != null) {
                        setInputMethodWithSubtypeIdLocked(iBinder, str, com.android.server.inputmethod.SubtypeUtils.getSubtypeIdFromHashCode(inputMethodInfo, inputMethodSubtype.hashCode()));
                    } else {
                        setInputMethod(iBinder, str);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean switchToPreviousInputMethod(@android.annotation.NonNull android.os.IBinder iBinder) {
        android.view.inputmethod.InputMethodInfo inputMethodInfo;
        java.util.ArrayList<android.view.inputmethod.InputMethodInfo> enabledInputMethodList;
        java.lang.String obj;
        android.view.inputmethod.InputMethodSubtype findLastResortApplicableSubtype;
        int hashCode;
        synchronized (com.android.server.inputmethod.ImfLock.class) {
            try {
                if (!calledWithValidTokenLocked(iBinder)) {
                    return false;
                }
                android.util.Pair<java.lang.String, java.lang.String> lastInputMethodAndSubtype = this.mSettings.getLastInputMethodAndSubtype();
                java.lang.String str = null;
                if (lastInputMethodAndSubtype != null) {
                    inputMethodInfo = this.mSettings.getMethodMap().get((java.lang.String) lastInputMethodAndSubtype.first);
                } else {
                    inputMethodInfo = null;
                }
                int i = -1;
                if (lastInputMethodAndSubtype != null && inputMethodInfo != null) {
                    boolean equals = inputMethodInfo.getId().equals(getSelectedMethodIdLocked());
                    int parseInt = java.lang.Integer.parseInt((java.lang.String) lastInputMethodAndSubtype.second);
                    if (this.mCurrentSubtype == null) {
                        hashCode = -1;
                    } else {
                        hashCode = this.mCurrentSubtype.hashCode();
                    }
                    if (!equals || parseInt != hashCode) {
                        str = (java.lang.String) lastInputMethodAndSubtype.first;
                        i = com.android.server.inputmethod.SubtypeUtils.getSubtypeIdFromHashCode(inputMethodInfo, parseInt);
                    }
                }
                if (android.text.TextUtils.isEmpty(str) && !com.android.server.inputmethod.InputMethodUtils.canAddToLastInputMethod(this.mCurrentSubtype) && (enabledInputMethodList = this.mSettings.getEnabledInputMethodList()) != null) {
                    int size = enabledInputMethodList.size();
                    if (this.mCurrentSubtype != null && !android.text.TextUtils.isEmpty(this.mCurrentSubtype.getLocale())) {
                        obj = this.mCurrentSubtype.getLocale();
                    } else {
                        obj = com.android.server.inputmethod.SystemLocaleWrapper.get(this.mSettings.getUserId()).get(0).toString();
                    }
                    for (int i2 = 0; i2 < size; i2++) {
                        android.view.inputmethod.InputMethodInfo inputMethodInfo2 = enabledInputMethodList.get(i2);
                        if (inputMethodInfo2.getSubtypeCount() > 0 && inputMethodInfo2.isSystem() && (findLastResortApplicableSubtype = com.android.server.inputmethod.SubtypeUtils.findLastResortApplicableSubtype(com.android.server.inputmethod.SubtypeUtils.getSubtypes(inputMethodInfo2), "keyboard", obj, true)) != null) {
                            str = inputMethodInfo2.getId();
                            i = com.android.server.inputmethod.SubtypeUtils.getSubtypeIdFromHashCode(inputMethodInfo2, findLastResortApplicableSubtype.hashCode());
                            if (findLastResortApplicableSubtype.getLocale().equals(obj)) {
                                break;
                            }
                        }
                    }
                }
                if (android.text.TextUtils.isEmpty(str)) {
                    return false;
                }
                setInputMethodWithSubtypeIdLocked(iBinder, str, i);
                return true;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean switchToNextInputMethod(@android.annotation.NonNull android.os.IBinder iBinder, boolean z) {
        synchronized (com.android.server.inputmethod.ImfLock.class) {
            try {
                if (!calledWithValidTokenLocked(iBinder)) {
                    return false;
                }
                return switchToNextInputMethodLocked(iBinder, z);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    private boolean switchToNextInputMethodLocked(@android.annotation.Nullable android.os.IBinder iBinder, boolean z) {
        com.android.server.inputmethod.InputMethodSubtypeSwitchingController.ImeSubtypeListItem nextInputMethodLocked = this.mSwitchingController.getNextInputMethodLocked(z, this.mSettings.getMethodMap().get(getSelectedMethodIdLocked()), this.mCurrentSubtype);
        if (nextInputMethodLocked == null) {
            return false;
        }
        setInputMethodWithSubtypeIdLocked(iBinder, nextInputMethodLocked.mImi.getId(), nextInputMethodLocked.mSubtypeId);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean shouldOfferSwitchingToNextInputMethod(@android.annotation.NonNull android.os.IBinder iBinder) {
        synchronized (com.android.server.inputmethod.ImfLock.class) {
            try {
                if (calledWithValidTokenLocked(iBinder)) {
                    return this.mSwitchingController.getNextInputMethodLocked(false, this.mSettings.getMethodMap().get(getSelectedMethodIdLocked()), this.mCurrentSubtype) != null;
                }
                return false;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public android.view.inputmethod.InputMethodSubtype getLastInputMethodSubtype(int i) {
        if (android.os.UserHandle.getCallingUserId() != i) {
            this.mContext.enforceCallingOrSelfPermission("android.permission.INTERACT_ACROSS_USERS_FULL", null);
        }
        synchronized (com.android.server.inputmethod.ImfLock.class) {
            try {
                if (this.mSettings.getUserId() == i) {
                    return this.mSettings.getLastInputMethodSubtype();
                }
                return queryMethodMapForUser(i).getLastInputMethodSubtype();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void setAdditionalInputMethodSubtypes(java.lang.String str, android.view.inputmethod.InputMethodSubtype[] inputMethodSubtypeArr, int i) {
        if (android.os.UserHandle.getCallingUserId() != i) {
            this.mContext.enforceCallingOrSelfPermission("android.permission.INTERACT_ACROSS_USERS_FULL", null);
        }
        int callingUid = android.os.Binder.getCallingUid();
        if (android.text.TextUtils.isEmpty(str) || inputMethodSubtypeArr == null) {
            return;
        }
        java.util.ArrayList<android.view.inputmethod.InputMethodSubtype> arrayList = new java.util.ArrayList<>();
        for (android.view.inputmethod.InputMethodSubtype inputMethodSubtype : inputMethodSubtypeArr) {
            if (!arrayList.contains(inputMethodSubtype)) {
                arrayList.add(inputMethodSubtype);
            } else {
                android.util.Slog.w(TAG, "Duplicated subtype definition found: " + inputMethodSubtype.getLocale() + ", " + inputMethodSubtype.getMode());
            }
        }
        synchronized (com.android.server.inputmethod.ImfLock.class) {
            try {
                if (this.mSystemReady) {
                    if (this.mSettings.getUserId() == i) {
                        com.android.server.inputmethod.AdditionalSubtypeMap newAdditionalSubtypeMap = this.mSettings.getNewAdditionalSubtypeMap(str, arrayList, this.mAdditionalSubtypeMap, this.mPackageManagerInternal, callingUid);
                        if (this.mAdditionalSubtypeMap == newAdditionalSubtypeMap) {
                            return;
                        }
                        com.android.server.inputmethod.AdditionalSubtypeUtils.save(newAdditionalSubtypeMap, this.mSettings.getMethodMap(), this.mSettings.getUserId());
                        this.mAdditionalSubtypeMap = newAdditionalSubtypeMap;
                        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                        try {
                            buildInputMethodListLocked(false);
                            return;
                        } finally {
                            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        }
                    }
                    com.android.server.inputmethod.AdditionalSubtypeMap load = com.android.server.inputmethod.AdditionalSubtypeUtils.load(i);
                    com.android.server.inputmethod.InputMethodSettings queryInputMethodServicesInternal = queryInputMethodServicesInternal(this.mContext, i, load, 0);
                    com.android.server.inputmethod.AdditionalSubtypeMap newAdditionalSubtypeMap2 = queryInputMethodServicesInternal.getNewAdditionalSubtypeMap(str, arrayList, load, this.mPackageManagerInternal, callingUid);
                    if (load != newAdditionalSubtypeMap2) {
                        com.android.server.inputmethod.AdditionalSubtypeUtils.save(newAdditionalSubtypeMap2, queryInputMethodServicesInternal.getMethodMap(), queryInputMethodServicesInternal.getUserId());
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void setExplicitlyEnabledInputMethodSubtypes(java.lang.String str, @android.annotation.NonNull int[] iArr, int i) {
        if (android.os.UserHandle.getCallingUserId() != i) {
            this.mContext.enforceCallingOrSelfPermission("android.permission.INTERACT_ACROSS_USERS_FULL", null);
        }
        int callingUid = android.os.Binder.getCallingUid();
        android.content.ComponentName unflattenFromString = str != null ? android.content.ComponentName.unflattenFromString(str) : null;
        if (unflattenFromString == null || !com.android.server.inputmethod.InputMethodUtils.checkIfPackageBelongsToUid(this.mPackageManagerInternal, callingUid, unflattenFromString.getPackageName())) {
            throw new java.lang.SecurityException("Calling UID=" + callingUid + " does not belong to imeId=" + str);
        }
        java.util.Objects.requireNonNull(iArr, "subtypeHashCodes must not be null");
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            synchronized (com.android.server.inputmethod.ImfLock.class) {
                try {
                    boolean z = this.mSettings.getUserId() == i;
                    com.android.server.inputmethod.InputMethodSettings queryMethodMapForUser = z ? this.mSettings : queryMethodMapForUser(i);
                    if (!queryMethodMapForUser.setEnabledInputMethodSubtypes(str, iArr)) {
                        return;
                    }
                    if (z) {
                        if (this.mSettingsObserver != null) {
                            this.mSettingsObserver.mLastEnabled = queryMethodMapForUser.getEnabledInputMethodsStr();
                        }
                        updateInputMethodsFromSettingsLocked(false);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @java.lang.Deprecated
    public int getInputMethodWindowVisibleHeight(@android.annotation.NonNull final com.android.internal.inputmethod.IInputMethodClient iInputMethodClient) {
        final int callingUid = android.os.Binder.getCallingUid();
        return ((java.lang.Integer) android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingSupplier() { // from class: com.android.server.inputmethod.InputMethodManagerService$$ExternalSyntheticLambda14
            public final java.lang.Object getOrThrow() {
                java.lang.Integer lambda$getInputMethodWindowVisibleHeight$10;
                lambda$getInputMethodWindowVisibleHeight$10 = com.android.server.inputmethod.InputMethodManagerService.this.lambda$getInputMethodWindowVisibleHeight$10(callingUid, iInputMethodClient);
                return lambda$getInputMethodWindowVisibleHeight$10;
            }
        })).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.lang.Integer lambda$getInputMethodWindowVisibleHeight$10(int i, com.android.internal.inputmethod.IInputMethodClient iInputMethodClient) throws java.lang.Exception {
        synchronized (com.android.server.inputmethod.ImfLock.class) {
            try {
                if (!canInteractWithImeLocked(i, iInputMethodClient, "getInputMethodWindowVisibleHeight", null)) {
                    return 0;
                }
                return java.lang.Integer.valueOf(this.mWindowManagerInternal.getInputMethodWindowVisibleHeight(this.mCurTokenDisplayId));
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @android.annotation.EnforcePermission("android.permission.INTERNAL_SYSTEM_WINDOW")
    public void removeImeSurface() {
        super.removeImeSurface_enforcePermission();
        this.mHandler.obtainMessage(MSG_REMOVE_IME_SURFACE).sendToTarget();
    }

    public void removeImeSurfaceFromWindowAsync(android.os.IBinder iBinder) {
        this.mHandler.obtainMessage(MSG_REMOVE_IME_SURFACE_FROM_WINDOW, iBinder).sendToTarget();
    }

    private void registerDeviceListenerAndCheckStylusSupport() {
        final android.hardware.input.InputManager inputManager = (android.hardware.input.InputManager) this.mContext.getSystemService(android.hardware.input.InputManager.class);
        android.util.IntArray stylusInputDeviceIds = getStylusInputDeviceIds(inputManager);
        if (stylusInputDeviceIds.size() > 0) {
            synchronized (com.android.server.inputmethod.ImfLock.class) {
                this.mStylusIds = new android.util.IntArray();
                this.mStylusIds.addAll(stylusInputDeviceIds);
            }
        }
        inputManager.registerInputDeviceListener(new android.hardware.input.InputManager.InputDeviceListener() { // from class: com.android.server.inputmethod.InputMethodManagerService.3
            @Override // android.hardware.input.InputManager.InputDeviceListener
            public void onInputDeviceAdded(int i) {
                android.view.InputDevice inputDevice = inputManager.getInputDevice(i);
                if (inputDevice != null && com.android.server.inputmethod.InputMethodManagerService.isStylusDevice(inputDevice)) {
                    add(i);
                }
            }

            @Override // android.hardware.input.InputManager.InputDeviceListener
            public void onInputDeviceRemoved(int i) {
                remove(i);
            }

            @Override // android.hardware.input.InputManager.InputDeviceListener
            public void onInputDeviceChanged(int i) {
                android.view.InputDevice inputDevice = inputManager.getInputDevice(i);
                if (inputDevice == null) {
                    return;
                }
                if (com.android.server.inputmethod.InputMethodManagerService.isStylusDevice(inputDevice)) {
                    add(i);
                } else {
                    remove(i);
                }
            }

            private void add(int i) {
                synchronized (com.android.server.inputmethod.ImfLock.class) {
                    com.android.server.inputmethod.InputMethodManagerService.this.addStylusDeviceIdLocked(i);
                }
            }

            private void remove(int i) {
                synchronized (com.android.server.inputmethod.ImfLock.class) {
                    com.android.server.inputmethod.InputMethodManagerService.this.removeStylusDeviceIdLocked(i);
                }
            }
        }, this.mHandler);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addStylusDeviceIdLocked(int i) {
        if (this.mStylusIds == null) {
            this.mStylusIds = new android.util.IntArray();
        } else if (this.mStylusIds.indexOf(i) != -1) {
            return;
        }
        android.util.Slog.d(TAG, "New Stylus deviceId" + i + " added.");
        this.mStylusIds.add(i);
        if (!this.mHwController.getCurrentRequestId().isPresent() && this.mBindingController.supportsStylusHandwriting()) {
            scheduleResetStylusHandwriting();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeStylusDeviceIdLocked(int i) {
        if (this.mStylusIds == null || this.mStylusIds.size() == 0) {
            return;
        }
        int indexOf = this.mStylusIds.indexOf(i);
        if (indexOf != -1) {
            this.mStylusIds.remove(indexOf);
            android.util.Slog.d(TAG, "Stylus deviceId: " + i + " removed.");
        }
        if (this.mStylusIds.size() == 0) {
            this.mHwController.reset();
            scheduleRemoveStylusHandwritingWindow();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isStylusDevice(android.view.InputDevice inputDevice) {
        return inputDevice.supportsSource(16386) || inputDevice.supportsSource(49154);
    }

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    private boolean hasSupportedStylusLocked() {
        return (this.mStylusIds == null || this.mStylusIds.size() == 0) ? false : true;
    }

    @android.annotation.EnforcePermission("android.permission.TEST_INPUT_METHOD")
    public void addVirtualStylusIdForTestSession(com.android.internal.inputmethod.IInputMethodClient iInputMethodClient) {
        super.addVirtualStylusIdForTestSession_enforcePermission();
        int callingUid = android.os.Binder.getCallingUid();
        synchronized (com.android.server.inputmethod.ImfLock.class) {
            try {
                if (canInteractWithImeLocked(callingUid, iInputMethodClient, "addVirtualStylusIdForTestSession", null)) {
                    long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                    try {
                        addStylusDeviceIdLocked(VIRTUAL_STYLUS_ID_FOR_TEST.intValue());
                    } finally {
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @android.annotation.EnforcePermission("android.permission.TEST_INPUT_METHOD")
    public void setStylusWindowIdleTimeoutForTest(com.android.internal.inputmethod.IInputMethodClient iInputMethodClient, long j) {
        super.setStylusWindowIdleTimeoutForTest_enforcePermission();
        int callingUid = android.os.Binder.getCallingUid();
        synchronized (com.android.server.inputmethod.ImfLock.class) {
            try {
                if (canInteractWithImeLocked(callingUid, iInputMethodClient, "setStylusWindowIdleTimeoutForTest", null)) {
                    long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                    try {
                        getCurMethodLocked().setStylusWindowIdleTimeoutForTest(j);
                    } finally {
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    private void removeVirtualStylusIdForTestSessionLocked() {
        removeStylusDeviceIdLocked(VIRTUAL_STYLUS_ID_FOR_TEST.intValue());
    }

    private static android.util.IntArray getStylusInputDeviceIds(android.hardware.input.InputManager inputManager) {
        android.view.InputDevice inputDevice;
        android.util.IntArray intArray = new android.util.IntArray();
        for (int i : inputManager.getInputDeviceIds()) {
            if (inputManager.isInputDeviceEnabled(i) && (inputDevice = inputManager.getInputDevice(i)) != null && isStylusDevice(inputDevice)) {
                intArray.add(i);
            }
        }
        return intArray;
    }

    public void startProtoDump(byte[] bArr, int i, java.lang.String str) {
        if (bArr == null && i != 2) {
            return;
        }
        com.android.internal.inputmethod.ImeTracing imeTracing = com.android.internal.inputmethod.ImeTracing.getInstance();
        if (!imeTracing.isAvailable() || !imeTracing.isEnabled()) {
            return;
        }
        android.util.proto.ProtoOutputStream protoOutputStream = new android.util.proto.ProtoOutputStream();
        switch (i) {
            case 0:
                long start = protoOutputStream.start(2246267895810L);
                protoOutputStream.write(1125281431553L, android.os.SystemClock.elapsedRealtimeNanos());
                protoOutputStream.write(1138166333442L, str);
                protoOutputStream.write(1146756268035L, bArr);
                protoOutputStream.end(start);
                break;
            case 1:
                long start2 = protoOutputStream.start(2246267895810L);
                protoOutputStream.write(1125281431553L, android.os.SystemClock.elapsedRealtimeNanos());
                protoOutputStream.write(1138166333442L, str);
                protoOutputStream.write(1146756268035L, bArr);
                protoOutputStream.end(start2);
                break;
            case 2:
                long start3 = protoOutputStream.start(2246267895810L);
                protoOutputStream.write(1125281431553L, android.os.SystemClock.elapsedRealtimeNanos());
                protoOutputStream.write(1138166333442L, str);
                dumpDebug(protoOutputStream, 1146756268035L);
                protoOutputStream.end(start3);
                break;
            default:
                return;
        }
        imeTracing.addToBuffer(protoOutputStream, i);
    }

    public boolean isImeTraceEnabled() {
        return com.android.internal.inputmethod.ImeTracing.getInstance().isEnabled();
    }

    @android.annotation.EnforcePermission("android.permission.CONTROL_UI_TRACING")
    public void startImeTrace() {
        super.startImeTrace_enforcePermission();
        com.android.internal.inputmethod.ImeTracing.getInstance().startTrace((java.io.PrintWriter) null);
        synchronized (com.android.server.inputmethod.ImfLock.class) {
            this.mClientController.forAllClients(new java.util.function.Consumer() { // from class: com.android.server.inputmethod.InputMethodManagerService$$ExternalSyntheticLambda9
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.inputmethod.InputMethodManagerService.lambda$startImeTrace$11((com.android.server.inputmethod.ClientState) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$startImeTrace$11(com.android.server.inputmethod.ClientState clientState) {
        clientState.mClient.setImeTraceEnabled(true);
    }

    @android.annotation.EnforcePermission("android.permission.CONTROL_UI_TRACING")
    public void stopImeTrace() {
        super.stopImeTrace_enforcePermission();
        com.android.internal.inputmethod.ImeTracing.getInstance().stopTrace((java.io.PrintWriter) null);
        synchronized (com.android.server.inputmethod.ImfLock.class) {
            this.mClientController.forAllClients(new java.util.function.Consumer() { // from class: com.android.server.inputmethod.InputMethodManagerService$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.inputmethod.InputMethodManagerService.lambda$stopImeTrace$12((com.android.server.inputmethod.ClientState) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$stopImeTrace$12(com.android.server.inputmethod.ClientState clientState) {
        clientState.mClient.setImeTraceEnabled(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
        synchronized (com.android.server.inputmethod.ImfLock.class) {
            try {
                long start = protoOutputStream.start(j);
                protoOutputStream.write(1138166333441L, getSelectedMethodIdLocked());
                protoOutputStream.write(1120986464258L, getSequenceNumberLocked());
                protoOutputStream.write(1138166333443L, java.util.Objects.toString(this.mCurClient));
                protoOutputStream.write(1138166333444L, this.mWindowManagerInternal.getWindowName(this.mCurFocusedWindow));
                protoOutputStream.write(1138166333445L, this.mWindowManagerInternal.getWindowName(this.mLastImeTargetWindow));
                protoOutputStream.write(1138166333446L, com.android.internal.inputmethod.InputMethodDebug.softInputModeToString(this.mCurFocusedWindowSoftInputMode));
                if (this.mCurEditorInfo != null) {
                    this.mCurEditorInfo.dumpDebug(protoOutputStream, 1146756268039L);
                }
                protoOutputStream.write(1138166333448L, getCurIdLocked());
                this.mVisibilityStateComputer.dumpDebug(protoOutputStream, j);
                protoOutputStream.write(1133871366157L, this.mInFullscreenMode);
                protoOutputStream.write(1138166333454L, java.util.Objects.toString(getCurTokenLocked()));
                protoOutputStream.write(1120986464271L, this.mCurTokenDisplayId);
                protoOutputStream.write(1133871366160L, this.mSystemReady);
                protoOutputStream.write(1120986464273L, this.mLastSwitchUserId);
                protoOutputStream.write(1133871366162L, hasConnectionLocked());
                protoOutputStream.write(1133871366163L, this.mBoundToMethod);
                protoOutputStream.write(1133871366164L, this.mIsInteractive);
                protoOutputStream.write(1120986464277L, this.mBackDisposition);
                protoOutputStream.write(1120986464278L, this.mImeWindowVis);
                protoOutputStream.write(1133871366167L, this.mMenuController.getShowImeWithHardKeyboard());
                protoOutputStream.end(start);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyUserAction(@android.annotation.NonNull android.os.IBinder iBinder) {
        synchronized (com.android.server.inputmethod.ImfLock.class) {
            try {
                if (getCurTokenLocked() != iBinder) {
                    return;
                }
                if (this.mSettings.getUserId() != this.mSwitchingController.getUserId()) {
                    return;
                }
                android.view.inputmethod.InputMethodInfo inputMethodInfo = this.mSettings.getMethodMap().get(getSelectedMethodIdLocked());
                if (inputMethodInfo != null) {
                    this.mSwitchingController.onUserActionLocked(inputMethodInfo, this.mCurrentSubtype);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void applyImeVisibility(android.os.IBinder iBinder, android.os.IBinder iBinder2, boolean z, @android.annotation.NonNull android.view.inputmethod.ImeTracker.Token token) {
        try {
            android.os.Trace.traceBegin(32L, "IMMS.applyImeVisibility");
            synchronized (com.android.server.inputmethod.ImfLock.class) {
                if (!calledWithValidTokenLocked(iBinder)) {
                    android.view.inputmethod.ImeTracker.forLogging().onFailed(token, 47);
                    return;
                }
                android.view.inputmethod.ImeTracker.forLogging().onProgress(token, 47);
                this.mVisibilityApplier.applyImeVisibility(this.mVisibilityStateComputer.getWindowTokenFrom(iBinder2), token, z ? 1 : 0);
            }
        } finally {
            android.os.Trace.traceEnd(32L);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void resetStylusHandwriting(int i) {
        synchronized (com.android.server.inputmethod.ImfLock.class) {
            try {
                java.util.OptionalInt currentRequestId = this.mHwController.getCurrentRequestId();
                if (!currentRequestId.isPresent() || currentRequestId.getAsInt() != i) {
                    android.util.Slog.w(TAG, "IME requested to finish handwriting with a mismatched requestId: " + i);
                }
                removeVirtualStylusIdForTestSessionLocked();
                scheduleResetStylusHandwriting();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    private void setInputMethodWithSubtypeIdLocked(android.os.IBinder iBinder, final java.lang.String str, int i) {
        if (iBinder == null) {
            if (this.mContext.checkCallingOrSelfPermission("android.permission.WRITE_SECURE_SETTINGS") != 0) {
                throw new java.lang.SecurityException("Using null token requires permission android.permission.WRITE_SECURE_SETTINGS");
            }
        } else {
            if (getCurTokenLocked() != iBinder) {
                android.util.Slog.w(TAG, "Ignoring setInputMethod of uid " + android.os.Binder.getCallingUid() + " token: " + iBinder);
                return;
            }
            if (this.mSettings.getMethodMap().get(str) != null && this.mSettings.getEnabledInputMethodListWithFilter(new java.util.function.Predicate() { // from class: com.android.server.inputmethod.InputMethodManagerService$$ExternalSyntheticLambda5
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    boolean lambda$setInputMethodWithSubtypeIdLocked$13;
                    lambda$setInputMethodWithSubtypeIdLocked$13 = com.android.server.inputmethod.InputMethodManagerService.lambda$setInputMethodWithSubtypeIdLocked$13(str, (android.view.inputmethod.InputMethodInfo) obj);
                    return lambda$setInputMethodWithSubtypeIdLocked$13;
                }
            }).isEmpty()) {
                throw new java.lang.IllegalStateException("Requested IME is not enabled: " + str);
            }
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            setInputMethodLocked(str, i);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$setInputMethodWithSubtypeIdLocked$13(java.lang.String str, android.view.inputmethod.InputMethodInfo inputMethodInfo) {
        return inputMethodInfo.getId().equals(str);
    }

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    void onShowHideSoftInputRequested(boolean z, android.os.IBinder iBinder, int i, @android.annotation.Nullable android.view.inputmethod.ImeTracker.Token token) {
        com.android.server.wm.WindowManagerInternal.ImeTargetInfo onToggleImeRequested = this.mWindowManagerInternal.onToggleImeRequested(z, this.mCurFocusedWindow, this.mVisibilityStateComputer.getWindowTokenFrom(iBinder), this.mCurTokenDisplayId);
        this.mSoftInputShowHideHistory.addEntry(new com.android.server.inputmethod.InputMethodManagerService.SoftInputShowHideHistory.Entry(this.mCurFocusedWindowClient, this.mCurFocusedWindowEditorInfo, onToggleImeRequested.focusedWindowName, this.mCurFocusedWindowSoftInputMode, i, this.mInFullscreenMode, onToggleImeRequested.requestWindowName, onToggleImeRequested.imeControlTargetName, onToggleImeRequested.imeLayerTargetName, onToggleImeRequested.imeSurfaceParentName));
        if (token != null) {
            this.mImeTrackerService.onImmsUpdate(token, onToggleImeRequested.requestWindowName);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void hideMySoftInput(@android.annotation.NonNull android.os.IBinder iBinder, @android.annotation.NonNull android.view.inputmethod.ImeTracker.Token token, int i, int i2) {
        try {
            android.os.Trace.traceBegin(32L, "IMMS.hideMySoftInput");
            synchronized (com.android.server.inputmethod.ImfLock.class) {
                if (!calledWithValidTokenLocked(iBinder)) {
                    android.view.inputmethod.ImeTracker.forLogging().onFailed(token, 47);
                    return;
                }
                android.view.inputmethod.ImeTracker.forLogging().onProgress(token, 47);
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    hideCurrentInputLocked(this.mLastImeTargetWindow, token, i, null, i2);
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        } finally {
            android.os.Trace.traceEnd(32L);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showMySoftInput(@android.annotation.NonNull android.os.IBinder iBinder, @android.annotation.NonNull android.view.inputmethod.ImeTracker.Token token, int i, int i2) {
        try {
            android.os.Trace.traceBegin(32L, "IMMS.showMySoftInput");
            synchronized (com.android.server.inputmethod.ImfLock.class) {
                if (!calledWithValidTokenLocked(iBinder)) {
                    android.view.inputmethod.ImeTracker.forLogging().onFailed(token, 47);
                    return;
                }
                android.view.inputmethod.ImeTracker.forLogging().onProgress(token, 47);
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    showCurrentInputLocked(this.mLastImeTargetWindow, token, i, 0, null, i2);
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        } finally {
            android.os.Trace.traceEnd(32L);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    com.android.server.inputmethod.ImeVisibilityApplier getVisibilityApplier() {
        com.android.server.inputmethod.DefaultImeVisibilityApplier defaultImeVisibilityApplier;
        synchronized (com.android.server.inputmethod.ImfLock.class) {
            defaultImeVisibilityApplier = this.mVisibilityApplier;
        }
        return defaultImeVisibilityApplier;
    }

    void onApplyImeVisibilityFromComputer(android.os.IBinder iBinder, @android.annotation.NonNull android.view.inputmethod.ImeTracker.Token token, @android.annotation.NonNull com.android.server.inputmethod.ImeVisibilityStateComputer.ImeVisibilityResult imeVisibilityResult) {
        synchronized (com.android.server.inputmethod.ImfLock.class) {
            this.mVisibilityApplier.applyImeVisibility(iBinder, token, imeVisibilityResult.getState(), imeVisibilityResult.getReason());
        }
    }

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    void setEnabledSessionLocked(com.android.server.inputmethod.InputMethodManagerService.SessionState sessionState) {
        if (this.mEnabledSession != sessionState) {
            if (this.mEnabledSession != null && this.mEnabledSession.mSession != null) {
                this.mEnabledSession.mMethod.setSessionEnabled(this.mEnabledSession.mSession, false);
            }
            this.mEnabledSession = sessionState;
            if (this.mEnabledSession != null && this.mEnabledSession.mSession != null) {
                this.mEnabledSession.mMethod.setSessionEnabled(this.mEnabledSession.mSession, true);
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    void setEnabledSessionForAccessibilityLocked(android.util.SparseArray<com.android.server.inputmethod.InputMethodManagerService.AccessibilitySessionState> sparseArray) {
        com.android.server.inputmethod.InputMethodManagerService.AccessibilitySessionState valueAt;
        com.android.server.inputmethod.InputMethodManagerService.AccessibilitySessionState valueAt2;
        android.util.SparseArray<com.android.internal.inputmethod.IAccessibilityInputMethodSession> sparseArray2 = new android.util.SparseArray<>();
        for (int i = 0; i < this.mEnabledAccessibilitySessions.size(); i++) {
            if (!sparseArray.contains(this.mEnabledAccessibilitySessions.keyAt(i)) && (valueAt2 = this.mEnabledAccessibilitySessions.valueAt(i)) != null) {
                sparseArray2.append(this.mEnabledAccessibilitySessions.keyAt(i), valueAt2.mSession);
            }
        }
        if (sparseArray2.size() > 0) {
            com.android.server.AccessibilityManagerInternal.get().setImeSessionEnabled(sparseArray2, false);
        }
        android.util.SparseArray<com.android.internal.inputmethod.IAccessibilityInputMethodSession> sparseArray3 = new android.util.SparseArray<>();
        for (int i2 = 0; i2 < sparseArray.size(); i2++) {
            if (!this.mEnabledAccessibilitySessions.contains(sparseArray.keyAt(i2)) && (valueAt = sparseArray.valueAt(i2)) != null) {
                sparseArray3.append(sparseArray.keyAt(i2), valueAt.mSession);
            }
        }
        if (sparseArray3.size() > 0) {
            com.android.server.AccessibilityManagerInternal.get().setImeSessionEnabled(sparseArray3, true);
        }
        this.mEnabledAccessibilitySessions = sparseArray;
    }

    @Override // android.os.Handler.Callback
    public boolean handleMessage(android.os.Message message) {
        boolean isInputShown;
        boolean z;
        switch (message.what) {
            case 1:
                int i = message.arg2;
                switch (message.arg1) {
                    case 0:
                        synchronized (com.android.server.inputmethod.ImfLock.class) {
                            isInputShown = isInputShown();
                        }
                        z = isInputShown;
                        break;
                    case 1:
                        z = true;
                        break;
                    case 2:
                        z = false;
                        break;
                    default:
                        android.util.Slog.e(TAG, "Unknown subtype picker mode = " + message.arg1);
                        return false;
                }
                synchronized (com.android.server.inputmethod.ImfLock.class) {
                    try {
                        boolean z2 = this.mWindowManagerInternal.isKeyguardLocked() && this.mWindowManagerInternal.isKeyguardSecure(this.mSettings.getUserId());
                        java.lang.String selectedInputMethod = this.mSettings.getSelectedInputMethod();
                        this.mMenuController.showInputMethodMenuLocked(z, i, selectedInputMethod, this.mSettings.getSelectedInputMethodSubtypeId(selectedInputMethod), com.android.server.inputmethod.InputMethodSubtypeSwitchingController.getSortedInputMethodAndSubtypeList(z, z2, true, this.mContext, this.mSettings.getMethodMap(), this.mSettings.getUserId()));
                    } finally {
                    }
                }
                return true;
            case MSG_HIDE_ALL_INPUT_METHODS /* 1035 */:
                synchronized (com.android.server.inputmethod.ImfLock.class) {
                    hideCurrentInputLocked(this.mCurFocusedWindow, 0, ((java.lang.Integer) message.obj).intValue());
                }
                return true;
            case MSG_REMOVE_IME_SURFACE /* 1060 */:
                synchronized (com.android.server.inputmethod.ImfLock.class) {
                    try {
                        if (this.mEnabledSession != null && this.mEnabledSession.mSession != null && !isShowRequestedForCurrentWindow()) {
                            this.mEnabledSession.mSession.removeImeSurface();
                        }
                    } catch (android.os.RemoteException e) {
                    }
                }
                return true;
            case MSG_REMOVE_IME_SURFACE_FROM_WINDOW /* 1061 */:
                android.os.IBinder iBinder = (android.os.IBinder) message.obj;
                synchronized (com.android.server.inputmethod.ImfLock.class) {
                    try {
                        if (iBinder == this.mCurFocusedWindow && this.mEnabledSession != null && this.mEnabledSession.mSession != null) {
                            this.mEnabledSession.mSession.removeImeSurface();
                        }
                    } catch (android.os.RemoteException e2) {
                    }
                }
                return true;
            case MSG_UPDATE_IME_WINDOW_STATUS /* 1070 */:
                updateImeWindowStatus(message.arg1 == 1);
                return true;
            case MSG_RESET_HANDWRITING /* 1090 */:
                synchronized (com.android.server.inputmethod.ImfLock.class) {
                    try {
                        if (this.mBindingController.supportsStylusHandwriting() && getCurMethodLocked() != null && hasSupportedStylusLocked()) {
                            android.util.Slog.d(TAG, "Initializing Handwriting Spy");
                            this.mHwController.initializeHandwritingSpy(this.mCurTokenDisplayId);
                        } else {
                            this.mHwController.reset();
                        }
                    } finally {
                    }
                }
                return true;
            case MSG_START_HANDWRITING /* 1100 */:
                synchronized (com.android.server.inputmethod.ImfLock.class) {
                    try {
                        com.android.server.inputmethod.IInputMethodInvoker curMethodLocked = getCurMethodLocked();
                        if (curMethodLocked == null || this.mCurFocusedWindow == null) {
                            return true;
                        }
                        com.android.server.inputmethod.HandwritingModeController.HandwritingSession startHandwritingSession = this.mHwController.startHandwritingSession(message.arg1, message.arg2, this.mBindingController.getCurMethodUid(), this.mCurFocusedWindow);
                        if (startHandwritingSession == null) {
                            android.util.Slog.e(TAG, "Failed to start handwriting session for requestId: " + message.arg1);
                            return true;
                        }
                        if (!curMethodLocked.startStylusHandwriting(startHandwritingSession.getRequestId(), startHandwritingSession.getHandwritingChannel(), startHandwritingSession.getRecordedEvents())) {
                            android.util.Slog.w(TAG, "Resetting handwriting mode.");
                            scheduleResetStylusHandwriting();
                        }
                        return true;
                    } finally {
                    }
                }
            case MSG_FINISH_HANDWRITING /* 1110 */:
                synchronized (com.android.server.inputmethod.ImfLock.class) {
                    try {
                        com.android.server.inputmethod.IInputMethodInvoker curMethodLocked2 = getCurMethodLocked();
                        if (curMethodLocked2 != null && this.mHwController.getCurrentRequestId().isPresent()) {
                            curMethodLocked2.finishStylusHandwriting();
                        }
                    } finally {
                    }
                }
                return true;
            case MSG_REMOVE_HANDWRITING_WINDOW /* 1120 */:
                synchronized (com.android.server.inputmethod.ImfLock.class) {
                    try {
                        com.android.server.inputmethod.IInputMethodInvoker curMethodLocked3 = getCurMethodLocked();
                        if (curMethodLocked3 != null) {
                            curMethodLocked3.removeStylusHandwritingWindow();
                        }
                    } finally {
                    }
                }
                return true;
            case MSG_PREPARE_HANDWRITING_DELEGATION /* 1130 */:
                synchronized (com.android.server.inputmethod.ImfLock.class) {
                    this.mHwController.prepareStylusHandwritingDelegation(message.arg1, (java.lang.String) ((android.util.Pair) message.obj).first, (java.lang.String) ((android.util.Pair) message.obj).second, false);
                }
                return true;
            case MSG_SET_INTERACTIVE /* 3030 */:
                handleSetInteractive(message.arg1 != 0);
                return true;
            case MSG_HARD_KEYBOARD_SWITCH_CHANGED /* 4000 */:
                this.mMenuController.handleHardKeyboardStatusChange(message.arg1 == 1);
                synchronized (com.android.server.inputmethod.ImfLock.class) {
                    sendOnNavButtonFlagsChangedLocked();
                }
                return true;
            case 5000:
                onUnlockUser(message.arg1);
                return true;
            case MSG_DISPATCH_ON_INPUT_METHOD_LIST_UPDATED /* 5010 */:
                final int i2 = message.arg1;
                final java.util.List list = (java.util.List) message.obj;
                this.mInputMethodListListeners.forEach(new java.util.function.Consumer() { // from class: com.android.server.inputmethod.InputMethodManagerService$$ExternalSyntheticLambda4
                    @Override // java.util.function.Consumer
                    public final void accept(java.lang.Object obj) {
                        ((com.android.server.inputmethod.InputMethodManagerInternal.InputMethodListListener) obj).onInputMethodListUpdated(list, i2);
                    }
                });
                return true;
            case MSG_NOTIFY_IME_UID_TO_AUDIO_SERVICE /* 7000 */:
                if (this.mAudioManagerInternal == null) {
                    this.mAudioManagerInternal = (android.media.AudioManagerInternal) com.android.server.LocalServices.getService(android.media.AudioManagerInternal.class);
                }
                if (this.mAudioManagerInternal != null) {
                    this.mAudioManagerInternal.setInputMethodServiceUid(message.arg1);
                }
                return true;
            default:
                return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onStylusHandwritingReady(int i, int i2) {
        this.mHandler.obtainMessage(MSG_START_HANDWRITING, i, i2).sendToTarget();
    }

    private void handleSetInteractive(boolean z) {
        synchronized (com.android.server.inputmethod.ImfLock.class) {
            try {
                this.mIsInteractive = z;
                updateSystemUiLocked(z ? this.mImeWindowVis : 0, this.mBackDisposition);
                if (this.mCurClient == null || this.mCurClient.mClient == null) {
                    return;
                }
                if (this.mImePlatformCompatUtils.shouldUseSetInteractiveProtocol(getCurMethodUidLocked())) {
                    com.android.server.inputmethod.ImeVisibilityStateComputer.ImeVisibilityResult onInteractiveChanged = this.mVisibilityStateComputer.onInteractiveChanged(this.mCurFocusedWindow, z);
                    if (onInteractiveChanged != null) {
                        this.mVisibilityApplier.applyImeVisibility(this.mCurFocusedWindow, null, onInteractiveChanged.getState(), onInteractiveChanged.getReason());
                    }
                    this.mCurClient.mClient.setInteractive(this.mIsInteractive, this.mInFullscreenMode);
                } else {
                    this.mCurClient.mClient.setActive(this.mIsInteractive, this.mInFullscreenMode);
                }
            } finally {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    public boolean chooseNewDefaultIMELocked() {
        android.view.inputmethod.InputMethodInfo mostApplicableDefaultIME = com.android.server.inputmethod.InputMethodInfoUtils.getMostApplicableDefaultIME(this.mSettings.getEnabledInputMethodList());
        if (mostApplicableDefaultIME != null) {
            resetSelectedInputMethodAndSubtypeLocked(mostApplicableDefaultIME.getId());
            return true;
        }
        return false;
    }

    @android.annotation.NonNull
    static com.android.server.inputmethod.InputMethodSettings queryInputMethodServicesInternal(android.content.Context context, int i, @android.annotation.NonNull com.android.server.inputmethod.AdditionalSubtypeMap additionalSubtypeMap, int i2) {
        android.content.Context createContextAsUser;
        if (context.getUserId() == i) {
            createContextAsUser = context;
        } else {
            createContextAsUser = context.createContextAsUser(android.os.UserHandle.of(i), 0);
        }
        int i3 = 268435456;
        switch (i2) {
            case 0:
                break;
            case 1:
                i3 = 786432;
                break;
            default:
                android.util.Slog.e(TAG, "Unknown directBootAwareness=" + i2 + ". Falling back to DirectBootAwareness.AUTO");
                break;
        }
        return com.android.server.inputmethod.InputMethodSettings.create(filterInputMethodServices(additionalSubtypeMap, com.android.server.inputmethod.InputMethodUtils.getEnabledInputMethodIdsForFiltering(context, i), createContextAsUser, createContextAsUser.getPackageManager().queryIntentServices(new android.content.Intent("android.view.InputMethod"), android.content.pm.PackageManager.ResolveInfoFlags.of(32896 | i3))), i);
    }

    @android.annotation.NonNull
    static com.android.server.inputmethod.InputMethodMap filterInputMethodServices(@android.annotation.NonNull com.android.server.inputmethod.AdditionalSubtypeMap additionalSubtypeMap, java.util.List<java.lang.String> list, android.content.Context context, java.util.List<android.content.pm.ResolveInfo> list2) {
        android.util.ArrayMap arrayMap = new android.util.ArrayMap();
        android.util.ArrayMap arrayMap2 = new android.util.ArrayMap(list2.size());
        for (int i = 0; i < list2.size(); i++) {
            android.content.pm.ResolveInfo resolveInfo = list2.get(i);
            android.content.pm.ServiceInfo serviceInfo = resolveInfo.serviceInfo;
            java.lang.String computeId = android.view.inputmethod.InputMethodInfo.computeId(resolveInfo);
            if ("android.permission.BIND_INPUT_METHOD".equals(serviceInfo.permission)) {
                try {
                    android.view.inputmethod.InputMethodInfo inputMethodInfo = new android.view.inputmethod.InputMethodInfo(context, resolveInfo, additionalSubtypeMap.get(computeId));
                    if (!inputMethodInfo.isVrOnly()) {
                        java.lang.String str = serviceInfo.packageName;
                        if (!serviceInfo.applicationInfo.isSystemApp() && !list.contains(inputMethodInfo.getId()) && ((java.lang.Integer) arrayMap.getOrDefault(str, 0)).intValue() >= 20) {
                        }
                        arrayMap.put(str, java.lang.Integer.valueOf(((java.lang.Integer) arrayMap.getOrDefault(str, 0)).intValue() + 1));
                        arrayMap2.put(inputMethodInfo.getId(), inputMethodInfo);
                    }
                } catch (java.lang.Exception e) {
                    android.util.Slog.wtf(TAG, "Unable to load input method " + computeId, e);
                }
            } else {
                android.util.Slog.w(TAG, "Skipping input method " + computeId + ": it does not require the permission android.permission.BIND_INPUT_METHOD");
            }
        }
        return com.android.server.inputmethod.InputMethodMap.of(arrayMap2);
    }

    /* JADX WARN: Removed duplicated region for block: B:33:0x00e0  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x010f  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x013d  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x0149  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x011b  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x00c4 A[LOOP:2: B:50:0x00c2->B:51:0x00c4, LOOP_END] */
    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    void buildInputMethodListLocked(boolean z) {
        boolean z2;
        int size;
        int i;
        java.lang.String selectedInputMethod;
        boolean z3;
        if (!this.mSystemReady) {
            android.util.Slog.e(TAG, "buildInputMethodListLocked is not allowed until system is ready");
            return;
        }
        this.mMethodMapUpdateCount++;
        this.mMyPackageMonitor.clearKnownImePackageNamesLocked();
        this.mSettings = queryInputMethodServicesInternal(this.mContext, this.mSettings.getUserId(), this.mAdditionalSubtypeMap, 0);
        java.util.List queryIntentServicesAsUser = this.mContext.getPackageManager().queryIntentServicesAsUser(new android.content.Intent("android.view.InputMethod"), 512, this.mSettings.getUserId());
        int size2 = queryIntentServicesAsUser.size();
        for (int i2 = 0; i2 < size2; i2++) {
            android.content.pm.ServiceInfo serviceInfo = ((android.content.pm.ResolveInfo) queryIntentServicesAsUser.get(i2)).serviceInfo;
            if ("android.permission.BIND_INPUT_METHOD".equals(serviceInfo.permission)) {
                this.mMyPackageMonitor.addKnownImePackageNameLocked(serviceInfo.packageName);
            }
        }
        if (!z) {
            java.util.ArrayList<android.view.inputmethod.InputMethodInfo> enabledInputMethodList = this.mSettings.getEnabledInputMethodList();
            int size3 = enabledInputMethodList.size();
            int i3 = 0;
            boolean z4 = false;
            while (true) {
                if (i3 >= size3) {
                    z3 = false;
                    break;
                }
                android.view.inputmethod.InputMethodInfo inputMethodInfo = enabledInputMethodList.get(i3);
                if (this.mSettings.getMethodMap().containsKey(inputMethodInfo.getId())) {
                    if (inputMethodInfo.isAuxiliaryIme()) {
                        z4 = true;
                    } else {
                        z3 = true;
                        z4 = true;
                        break;
                    }
                }
                i3++;
            }
            if (!z4) {
                resetSelectedInputMethodAndSubtypeLocked("");
                z = true;
                z2 = false;
            } else if (!z3) {
                z2 = true;
            }
            if (!z || z2) {
                java.util.ArrayList<android.view.inputmethod.InputMethodInfo> defaultEnabledImes = com.android.server.inputmethod.InputMethodInfoUtils.getDefaultEnabledImes(this.mContext, this.mSettings.getMethodList(), z2);
                size = defaultEnabledImes.size();
                for (i = 0; i < size; i++) {
                    setInputMethodEnabledLocked(defaultEnabledImes.get(i).getId(), true);
                }
            }
            selectedInputMethod = this.mSettings.getSelectedInputMethod();
            if (!android.text.TextUtils.isEmpty(selectedInputMethod)) {
                if (!this.mSettings.getMethodMap().containsKey(selectedInputMethod)) {
                    android.util.Slog.w(TAG, "Default IME is uninstalled. Choose new default IME.");
                    if (chooseNewDefaultIMELocked()) {
                        updateInputMethodsFromSettingsLocked(true);
                    }
                } else {
                    setInputMethodEnabledLocked(selectedInputMethod, true);
                }
            }
            updateDefaultVoiceImeIfNeededLocked();
            if (this.mSettings.getUserId() != this.mSwitchingController.getUserId()) {
                this.mSwitchingController.resetCircularListLocked(this.mSettings.getMethodMap());
            } else {
                this.mSwitchingController = com.android.server.inputmethod.InputMethodSubtypeSwitchingController.createInstanceLocked(this.mContext, this.mSettings.getMethodMap(), this.mSettings.getUserId());
            }
            if (this.mSettings.getUserId() != this.mHardwareKeyboardShortcutController.getUserId()) {
                this.mHardwareKeyboardShortcutController.reset(this.mSettings.getMethodMap());
            } else {
                this.mHardwareKeyboardShortcutController = new com.android.server.inputmethod.HardwareKeyboardShortcutController(this.mSettings.getMethodMap(), this.mSettings.getUserId());
            }
            sendOnNavButtonFlagsChangedLocked();
            this.mHandler.obtainMessage(MSG_DISPATCH_ON_INPUT_METHOD_LIST_UPDATED, this.mSettings.getUserId(), 0, this.mSettings.getMethodList()).sendToTarget();
        }
        z2 = false;
        if (!z) {
        }
        java.util.ArrayList<android.view.inputmethod.InputMethodInfo> defaultEnabledImes2 = com.android.server.inputmethod.InputMethodInfoUtils.getDefaultEnabledImes(this.mContext, this.mSettings.getMethodList(), z2);
        size = defaultEnabledImes2.size();
        while (i < size) {
        }
        selectedInputMethod = this.mSettings.getSelectedInputMethod();
        if (!android.text.TextUtils.isEmpty(selectedInputMethod)) {
        }
        updateDefaultVoiceImeIfNeededLocked();
        if (this.mSettings.getUserId() != this.mSwitchingController.getUserId()) {
        }
        if (this.mSettings.getUserId() != this.mHardwareKeyboardShortcutController.getUserId()) {
        }
        sendOnNavButtonFlagsChangedLocked();
        this.mHandler.obtainMessage(MSG_DISPATCH_ON_INPUT_METHOD_LIST_UPDATED, this.mSettings.getUserId(), 0, this.mSettings.getMethodList()).sendToTarget();
    }

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    void sendOnNavButtonFlagsChangedLocked() {
        com.android.server.inputmethod.IInputMethodInvoker curMethod = this.mBindingController.getCurMethod();
        if (curMethod == null) {
            return;
        }
        curMethod.onNavButtonFlagsChanged(getInputMethodNavButtonFlagsLocked());
    }

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    private void updateDefaultVoiceImeIfNeededLocked() {
        java.lang.String string = this.mContext.getString(android.R.string.config_systemSpeechRecognizer);
        java.lang.String defaultVoiceInputMethod = this.mSettings.getDefaultVoiceInputMethod();
        android.view.inputmethod.InputMethodInfo chooseSystemVoiceIme = com.android.server.inputmethod.InputMethodInfoUtils.chooseSystemVoiceIme(this.mSettings.getMethodMap(), string, defaultVoiceInputMethod);
        if (chooseSystemVoiceIme == null) {
            if (!android.text.TextUtils.isEmpty(defaultVoiceInputMethod)) {
                this.mSettings.putDefaultVoiceInputMethod("");
            }
        } else {
            if (android.text.TextUtils.equals(defaultVoiceInputMethod, chooseSystemVoiceIme.getId())) {
                return;
            }
            setInputMethodEnabledLocked(chooseSystemVoiceIme.getId(), true);
            this.mSettings.putDefaultVoiceInputMethod(chooseSystemVoiceIme.getId());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    public boolean setInputMethodEnabledLocked(java.lang.String str, boolean z) {
        if (z) {
            java.lang.String enabledInputMethodsStr = this.mSettings.getEnabledInputMethodsStr();
            java.lang.String concatEnabledImeIds = com.android.server.inputmethod.InputMethodUtils.concatEnabledImeIds(enabledInputMethodsStr, str);
            if (android.text.TextUtils.equals(enabledInputMethodsStr, concatEnabledImeIds)) {
                return true;
            }
            this.mSettings.putEnabledInputMethodsStr(concatEnabledImeIds);
            return false;
        }
        if (!this.mSettings.buildAndPutEnabledInputMethodsStrRemovingId(new java.lang.StringBuilder(), this.mSettings.getEnabledInputMethodsAndSubtypeList(), str)) {
            return false;
        }
        if (this.mDeviceIdToShowIme == 0) {
            if (str.equals(this.mSettings.getSelectedInputMethod()) && !chooseNewDefaultIMELocked()) {
                android.util.Slog.i(TAG, "Can't find new IME, unsetting the current input method.");
                resetSelectedInputMethodAndSubtypeLocked("");
            }
        } else if (str.equals(this.mSettings.getSelectedDefaultDeviceInputMethod())) {
            android.view.inputmethod.InputMethodInfo mostApplicableDefaultIME = com.android.server.inputmethod.InputMethodInfoUtils.getMostApplicableDefaultIME(this.mSettings.getEnabledInputMethodList());
            this.mSettings.putSelectedDefaultDeviceInputMethod(mostApplicableDefaultIME == null ? null : mostApplicableDefaultIME.getId());
        }
        return true;
    }

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    private void setSelectedInputMethodAndSubtypeLocked(android.view.inputmethod.InputMethodInfo inputMethodInfo, int i, boolean z) {
        this.mSettings.saveCurrentInputMethodAndSubtypeToHistory(getSelectedMethodIdLocked(), this.mCurrentSubtype);
        if (inputMethodInfo == null || i < 0) {
            this.mSettings.putSelectedSubtype(-1);
            this.mCurrentSubtype = null;
        } else if (i >= inputMethodInfo.getSubtypeCount()) {
            this.mSettings.putSelectedSubtype(-1);
            this.mCurrentSubtype = getCurrentInputMethodSubtypeLocked();
        } else {
            android.view.inputmethod.InputMethodSubtype subtypeAt = inputMethodInfo.getSubtypeAt(i);
            this.mSettings.putSelectedSubtype(subtypeAt.hashCode());
            this.mCurrentSubtype = subtypeAt;
        }
        notifyInputMethodSubtypeChangedLocked(this.mSettings.getUserId(), inputMethodInfo, this.mCurrentSubtype);
        if (!z) {
            this.mSettings.putSelectedInputMethod(inputMethodInfo != null ? inputMethodInfo.getId() : "");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    public void resetSelectedInputMethodAndSubtypeLocked(java.lang.String str) {
        java.lang.String lastSubtypeForInputMethod;
        this.mDeviceIdToShowIme = 0;
        int i = -1;
        this.mDisplayIdToShowIme = -1;
        this.mSettings.putSelectedDefaultDeviceInputMethod(null);
        android.view.inputmethod.InputMethodInfo inputMethodInfo = this.mSettings.getMethodMap().get(str);
        if (inputMethodInfo != null && !android.text.TextUtils.isEmpty(str) && (lastSubtypeForInputMethod = this.mSettings.getLastSubtypeForInputMethod(str)) != null) {
            try {
                i = com.android.server.inputmethod.SubtypeUtils.getSubtypeIdFromHashCode(inputMethodInfo, java.lang.Integer.parseInt(lastSubtypeForInputMethod));
            } catch (java.lang.NumberFormatException e) {
                android.util.Slog.w(TAG, "HashCode for subtype looks broken: " + lastSubtypeForInputMethod, e);
            }
        }
        setSelectedInputMethodAndSubtypeLocked(inputMethodInfo, i, false);
    }

    @android.annotation.Nullable
    public android.view.inputmethod.InputMethodSubtype getCurrentInputMethodSubtype(int i) {
        if (android.os.UserHandle.getCallingUserId() != i) {
            this.mContext.enforceCallingOrSelfPermission("android.permission.INTERACT_ACROSS_USERS_FULL", null);
        }
        synchronized (com.android.server.inputmethod.ImfLock.class) {
            try {
                if (this.mSettings.getUserId() == i) {
                    return getCurrentInputMethodSubtypeLocked();
                }
                return queryMethodMapForUser(i).getCurrentInputMethodSubtypeForNonCurrentUsers();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    android.view.inputmethod.InputMethodSubtype getCurrentInputMethodSubtypeLocked() {
        java.lang.String selectedMethodIdLocked = getSelectedMethodIdLocked();
        if (selectedMethodIdLocked == null) {
            return null;
        }
        boolean isSubtypeSelected = this.mSettings.isSubtypeSelected();
        android.view.inputmethod.InputMethodInfo inputMethodInfo = this.mSettings.getMethodMap().get(selectedMethodIdLocked);
        if (inputMethodInfo == null || inputMethodInfo.getSubtypeCount() == 0) {
            return null;
        }
        if (!isSubtypeSelected || this.mCurrentSubtype == null || !com.android.server.inputmethod.SubtypeUtils.isValidSubtypeId(inputMethodInfo, this.mCurrentSubtype.hashCode())) {
            int selectedInputMethodSubtypeId = this.mSettings.getSelectedInputMethodSubtypeId(selectedMethodIdLocked);
            if (selectedInputMethodSubtypeId == -1) {
                java.util.List<android.view.inputmethod.InputMethodSubtype> enabledInputMethodSubtypeList = this.mSettings.getEnabledInputMethodSubtypeList(inputMethodInfo, true);
                if (enabledInputMethodSubtypeList.size() != 1) {
                    if (enabledInputMethodSubtypeList.size() > 1) {
                        java.lang.String obj = com.android.server.inputmethod.SystemLocaleWrapper.get(this.mSettings.getUserId()).get(0).toString();
                        this.mCurrentSubtype = com.android.server.inputmethod.SubtypeUtils.findLastResortApplicableSubtype(enabledInputMethodSubtypeList, "keyboard", obj, true);
                        if (this.mCurrentSubtype == null) {
                            this.mCurrentSubtype = com.android.server.inputmethod.SubtypeUtils.findLastResortApplicableSubtype(enabledInputMethodSubtypeList, null, obj, true);
                        }
                    }
                } else {
                    this.mCurrentSubtype = enabledInputMethodSubtypeList.get(0);
                }
            } else {
                this.mCurrentSubtype = com.android.server.inputmethod.SubtypeUtils.getSubtypes(inputMethodInfo).get(selectedInputMethodSubtypeId);
            }
        }
        return this.mCurrentSubtype;
    }

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    private android.view.inputmethod.InputMethodInfo queryDefaultInputMethodForUserIdLocked(int i) {
        com.android.server.inputmethod.InputMethodSettings queryInputMethodServicesInternal;
        if (i == this.mSettings.getUserId()) {
            queryInputMethodServicesInternal = this.mSettings;
        } else {
            queryInputMethodServicesInternal = queryInputMethodServicesInternal(this.mContext, i, com.android.server.inputmethod.AdditionalSubtypeUtils.load(i), 0);
        }
        return queryInputMethodServicesInternal.getMethodMap().get(queryInputMethodServicesInternal.getSelectedInputMethod());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public com.android.server.inputmethod.InputMethodSettings queryMethodMapForUser(int i) {
        return queryInputMethodServicesInternal(this.mContext, i, com.android.server.inputmethod.AdditionalSubtypeUtils.load(i), 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    public boolean switchToInputMethodLocked(java.lang.String str, int i) {
        if (i == this.mSettings.getUserId()) {
            if (!this.mSettings.getMethodMap().containsKey(str) || !this.mSettings.getEnabledInputMethodList().contains(this.mSettings.getMethodMap().get(str))) {
                return false;
            }
            setInputMethodLocked(str, -1);
            return true;
        }
        com.android.server.inputmethod.InputMethodSettings queryMethodMapForUser = queryMethodMapForUser(i);
        if (!queryMethodMapForUser.getMethodMap().containsKey(str) || !queryMethodMapForUser.getEnabledInputMethodList().contains(queryMethodMapForUser.getMethodMap().get(str))) {
            return false;
        }
        queryMethodMapForUser.putSelectedInputMethod(str);
        queryMethodMapForUser.putSelectedSubtype(-1);
        return true;
    }

    private boolean canCallerAccessInputMethod(@android.annotation.NonNull java.lang.String str, int i, int i2, @android.annotation.NonNull com.android.server.inputmethod.InputMethodSettings inputMethodSettings) {
        java.lang.String selectedInputMethod = inputMethodSettings.getSelectedInputMethod();
        android.content.ComponentName convertIdToComponentName = selectedInputMethod != null ? com.android.server.inputmethod.InputMethodUtils.convertIdToComponentName(selectedInputMethod) : null;
        if (convertIdToComponentName != null && convertIdToComponentName.getPackageName().equals(str)) {
            return true;
        }
        return !this.mPackageManagerInternal.filterAppAccess(str, i, i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    public void switchKeyboardLayoutLocked(int i) {
        android.view.inputmethod.InputMethodInfo inputMethodInfo;
        android.view.inputmethod.InputMethodInfo inputMethodInfo2 = this.mSettings.getMethodMap().get(getSelectedMethodIdLocked());
        if (inputMethodInfo2 == null) {
            return;
        }
        com.android.internal.inputmethod.InputMethodSubtypeHandle onSubtypeSwitch = this.mHardwareKeyboardShortcutController.onSubtypeSwitch(com.android.internal.inputmethod.InputMethodSubtypeHandle.of(inputMethodInfo2, this.mCurrentSubtype), i > 0);
        if (onSubtypeSwitch == null || (inputMethodInfo = this.mSettings.getMethodMap().get(onSubtypeSwitch.getImeId())) == null) {
            return;
        }
        int subtypeCount = inputMethodInfo.getSubtypeCount();
        if (subtypeCount == 0) {
            if (onSubtypeSwitch.equals(com.android.internal.inputmethod.InputMethodSubtypeHandle.of(inputMethodInfo, (android.view.inputmethod.InputMethodSubtype) null))) {
                setInputMethodLocked(inputMethodInfo.getId(), -1);
            }
        } else {
            for (int i2 = 0; i2 < subtypeCount; i2++) {
                if (onSubtypeSwitch.equals(com.android.internal.inputmethod.InputMethodSubtypeHandle.of(inputMethodInfo, inputMethodInfo.getSubtypeAt(i2)))) {
                    setInputMethodLocked(inputMethodInfo.getId(), i2);
                    return;
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void publishLocalService() {
        com.android.server.LocalServices.addService(com.android.server.inputmethod.InputMethodManagerInternal.class, new com.android.server.inputmethod.InputMethodManagerService.LocalServiceImpl());
    }

    /* JADX INFO: Access modifiers changed from: private */
    final class LocalServiceImpl extends com.android.server.inputmethod.InputMethodManagerInternal {
        private LocalServiceImpl() {
        }

        @Override // com.android.server.inputmethod.InputMethodManagerInternal
        public void setInteractive(boolean z) {
            com.android.server.inputmethod.InputMethodManagerService.this.mHandler.obtainMessage(com.android.server.inputmethod.InputMethodManagerService.MSG_SET_INTERACTIVE, z ? 1 : 0, 0).sendToTarget();
        }

        @Override // com.android.server.inputmethod.InputMethodManagerInternal
        public void hideAllInputMethods(int i, int i2) {
            com.android.server.inputmethod.InputMethodManagerService.this.mHandler.removeMessages(com.android.server.inputmethod.InputMethodManagerService.MSG_HIDE_ALL_INPUT_METHODS);
            com.android.server.inputmethod.InputMethodManagerService.this.mHandler.obtainMessage(com.android.server.inputmethod.InputMethodManagerService.MSG_HIDE_ALL_INPUT_METHODS, java.lang.Integer.valueOf(i)).sendToTarget();
        }

        @Override // com.android.server.inputmethod.InputMethodManagerInternal
        public java.util.List<android.view.inputmethod.InputMethodInfo> getInputMethodListAsUser(int i) {
            java.util.List<android.view.inputmethod.InputMethodInfo> inputMethodListLocked;
            synchronized (com.android.server.inputmethod.ImfLock.class) {
                inputMethodListLocked = com.android.server.inputmethod.InputMethodManagerService.this.getInputMethodListLocked(i, 0, 1000);
            }
            return inputMethodListLocked;
        }

        @Override // com.android.server.inputmethod.InputMethodManagerInternal
        public java.util.List<android.view.inputmethod.InputMethodInfo> getEnabledInputMethodListAsUser(int i) {
            java.util.List<android.view.inputmethod.InputMethodInfo> enabledInputMethodListLocked;
            synchronized (com.android.server.inputmethod.ImfLock.class) {
                enabledInputMethodListLocked = com.android.server.inputmethod.InputMethodManagerService.this.getEnabledInputMethodListLocked(i, 1000);
            }
            return enabledInputMethodListLocked;
        }

        @Override // com.android.server.inputmethod.InputMethodManagerInternal
        public void onCreateInlineSuggestionsRequest(int i, com.android.internal.inputmethod.InlineSuggestionsRequestInfo inlineSuggestionsRequestInfo, com.android.internal.inputmethod.IInlineSuggestionsRequestCallback iInlineSuggestionsRequestCallback) {
            boolean isTouchExplorationEnabled = com.android.server.AccessibilityManagerInternal.get().isTouchExplorationEnabled(i);
            synchronized (com.android.server.inputmethod.ImfLock.class) {
                com.android.server.inputmethod.InputMethodManagerService.this.mAutofillController.onCreateInlineSuggestionsRequest(i, inlineSuggestionsRequestInfo, iInlineSuggestionsRequestCallback, isTouchExplorationEnabled);
            }
        }

        @Override // com.android.server.inputmethod.InputMethodManagerInternal
        public boolean switchToInputMethod(java.lang.String str, int i) {
            boolean switchToInputMethodLocked;
            synchronized (com.android.server.inputmethod.ImfLock.class) {
                switchToInputMethodLocked = com.android.server.inputmethod.InputMethodManagerService.this.switchToInputMethodLocked(str, i);
            }
            return switchToInputMethodLocked;
        }

        @Override // com.android.server.inputmethod.InputMethodManagerInternal
        public boolean setInputMethodEnabled(java.lang.String str, boolean z, int i) {
            synchronized (com.android.server.inputmethod.ImfLock.class) {
                try {
                    if (i == com.android.server.inputmethod.InputMethodManagerService.this.mSettings.getUserId()) {
                        if (!com.android.server.inputmethod.InputMethodManagerService.this.mSettings.getMethodMap().containsKey(str)) {
                            return false;
                        }
                        com.android.server.inputmethod.InputMethodManagerService.this.setInputMethodEnabledLocked(str, z);
                        return true;
                    }
                    com.android.server.inputmethod.InputMethodSettings queryMethodMapForUser = com.android.server.inputmethod.InputMethodManagerService.this.queryMethodMapForUser(i);
                    if (!queryMethodMapForUser.getMethodMap().containsKey(str)) {
                        return false;
                    }
                    if (z) {
                        java.lang.String enabledInputMethodsStr = queryMethodMapForUser.getEnabledInputMethodsStr();
                        java.lang.String concatEnabledImeIds = com.android.server.inputmethod.InputMethodUtils.concatEnabledImeIds(enabledInputMethodsStr, str);
                        if (!android.text.TextUtils.equals(enabledInputMethodsStr, concatEnabledImeIds)) {
                            queryMethodMapForUser.putEnabledInputMethodsStr(concatEnabledImeIds);
                        }
                    } else {
                        queryMethodMapForUser.buildAndPutEnabledInputMethodsStrRemovingId(new java.lang.StringBuilder(), queryMethodMapForUser.getEnabledInputMethodsAndSubtypeList(), str);
                    }
                    return true;
                } finally {
                }
            }
        }

        @Override // com.android.server.inputmethod.InputMethodManagerInternal
        public void setVirtualDeviceInputMethodForAllUsers(int i, @android.annotation.Nullable java.lang.String str) {
            com.android.internal.util.Preconditions.checkArgument(i != 0, android.text.TextUtils.formatSimple("DeviceId %d is not a virtual device id.", new java.lang.Object[]{java.lang.Integer.valueOf(i)}));
            synchronized (com.android.server.inputmethod.ImfLock.class) {
                try {
                    if (str == null) {
                        com.android.server.inputmethod.InputMethodManagerService.this.mVirtualDeviceMethodMap.remove(i);
                    } else {
                        if (com.android.server.inputmethod.InputMethodManagerService.this.mVirtualDeviceMethodMap.contains(i)) {
                            throw new java.lang.IllegalArgumentException("Virtual device " + i + " already has a custom input method component");
                        }
                        com.android.server.inputmethod.InputMethodManagerService.this.mVirtualDeviceMethodMap.put(i, str);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        @Override // com.android.server.inputmethod.InputMethodManagerInternal
        public void registerInputMethodListListener(com.android.server.inputmethod.InputMethodManagerInternal.InputMethodListListener inputMethodListListener) {
            com.android.server.inputmethod.InputMethodManagerService.this.mInputMethodListListeners.addIfAbsent(inputMethodListListener);
        }

        @Override // com.android.server.inputmethod.InputMethodManagerInternal
        public boolean transferTouchFocusToImeWindow(@android.annotation.NonNull android.os.IBinder iBinder, int i) {
            synchronized (com.android.server.inputmethod.ImfLock.class) {
                if (i != com.android.server.inputmethod.InputMethodManagerService.this.mCurTokenDisplayId || com.android.server.inputmethod.InputMethodManagerService.this.mCurHostInputToken == null) {
                    return false;
                }
                return com.android.server.inputmethod.InputMethodManagerService.this.mInputManagerInternal.transferTouchGesture(iBinder, com.android.server.inputmethod.InputMethodManagerService.this.mCurHostInputToken);
            }
        }

        @Override // com.android.server.inputmethod.InputMethodManagerInternal
        public void reportImeControl(@android.annotation.Nullable android.os.IBinder iBinder) {
            synchronized (com.android.server.inputmethod.ImfLock.class) {
                try {
                    if (com.android.server.inputmethod.InputMethodManagerService.this.mCurFocusedWindow != iBinder) {
                        com.android.server.inputmethod.InputMethodManagerService.this.mCurPerceptible = true;
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        @Override // com.android.server.inputmethod.InputMethodManagerInternal
        public void onImeParentChanged(int i) {
            synchronized (com.android.server.inputmethod.ImfLock.class) {
                try {
                    if (com.android.server.inputmethod.InputMethodManagerService.this.mLastImeTargetWindow != com.android.server.inputmethod.InputMethodManagerService.this.mCurFocusedWindow) {
                        com.android.server.inputmethod.InputMethodManagerService.this.mMenuController.hideInputMethodMenuLocked();
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        @Override // com.android.server.inputmethod.InputMethodManagerInternal
        public void removeImeSurface(int i) {
            com.android.server.inputmethod.InputMethodManagerService.this.mHandler.obtainMessage(com.android.server.inputmethod.InputMethodManagerService.MSG_REMOVE_IME_SURFACE).sendToTarget();
        }

        @Override // com.android.server.inputmethod.InputMethodManagerInternal
        public void updateImeWindowStatus(boolean z, int i) {
            com.android.server.inputmethod.InputMethodManagerService.this.mHandler.obtainMessage(com.android.server.inputmethod.InputMethodManagerService.MSG_UPDATE_IME_WINDOW_STATUS, z ? 1 : 0, 0).sendToTarget();
        }

        @Override // com.android.server.inputmethod.InputMethodManagerInternal
        public void onSessionForAccessibilityCreated(int i, com.android.internal.inputmethod.IAccessibilityInputMethodSession iAccessibilityInputMethodSession, int i2) {
            synchronized (com.android.server.inputmethod.ImfLock.class) {
                try {
                    if (com.android.server.inputmethod.InputMethodManagerService.this.mCurClient != null) {
                        com.android.server.inputmethod.InputMethodManagerService.this.clearClientSessionForAccessibilityLocked(com.android.server.inputmethod.InputMethodManagerService.this.mCurClient, i);
                        com.android.server.inputmethod.InputMethodManagerService.this.mCurClient.mAccessibilitySessions.put(i, new com.android.server.inputmethod.InputMethodManagerService.AccessibilitySessionState(com.android.server.inputmethod.InputMethodManagerService.this.mCurClient, i, iAccessibilityInputMethodSession));
                        com.android.server.inputmethod.InputMethodManagerService.this.attachNewAccessibilityLocked(11, true);
                        com.android.server.inputmethod.InputMethodManagerService.SessionState sessionState = com.android.server.inputmethod.InputMethodManagerService.this.mCurClient.mCurSession;
                        com.android.server.inputmethod.InputMethodManagerService.this.mCurClient.mClient.onBindAccessibilityService(new com.android.internal.inputmethod.InputBindResult(16, sessionState == null ? null : sessionState.mSession, com.android.server.inputmethod.InputMethodManagerService.this.createAccessibilityInputMethodSessions(com.android.server.inputmethod.InputMethodManagerService.this.mCurClient.mAccessibilitySessions), (android.view.InputChannel) null, com.android.server.inputmethod.InputMethodManagerService.this.getCurIdLocked(), com.android.server.inputmethod.InputMethodManagerService.this.getSequenceNumberLocked(), false), i);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        @Override // com.android.server.inputmethod.InputMethodManagerInternal
        public void unbindAccessibilityFromCurrentClient(final int i, int i2) {
            synchronized (com.android.server.inputmethod.ImfLock.class) {
                try {
                    if (com.android.server.inputmethod.InputMethodManagerService.this.mCurClient != null) {
                        com.android.server.inputmethod.InputMethodManagerService.this.mCurClient.mClient.onUnbindAccessibilityService(com.android.server.inputmethod.InputMethodManagerService.this.getSequenceNumberLocked(), i);
                    }
                    if (com.android.server.inputmethod.InputMethodManagerService.this.getCurMethodLocked() != null) {
                        com.android.server.inputmethod.InputMethodManagerService.this.mClientController.forAllClients(new java.util.function.Consumer() { // from class: com.android.server.inputmethod.InputMethodManagerService$LocalServiceImpl$$ExternalSyntheticLambda0
                            @Override // java.util.function.Consumer
                            public final void accept(java.lang.Object obj) {
                                com.android.server.inputmethod.InputMethodManagerService.LocalServiceImpl.this.lambda$unbindAccessibilityFromCurrentClient$0(i, (com.android.server.inputmethod.ClientState) obj);
                            }
                        });
                        com.android.server.inputmethod.InputMethodManagerService.AccessibilitySessionState accessibilitySessionState = com.android.server.inputmethod.InputMethodManagerService.this.mEnabledAccessibilitySessions.get(i);
                        if (accessibilitySessionState != null) {
                            com.android.server.inputmethod.InputMethodManagerService.this.finishSessionForAccessibilityLocked(accessibilitySessionState);
                            com.android.server.inputmethod.InputMethodManagerService.this.mEnabledAccessibilitySessions.remove(i);
                        }
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$unbindAccessibilityFromCurrentClient$0(int i, com.android.server.inputmethod.ClientState clientState) {
            com.android.server.inputmethod.InputMethodManagerService.this.clearClientSessionForAccessibilityLocked(clientState, i);
        }

        @Override // com.android.server.inputmethod.InputMethodManagerInternal
        public void maybeFinishStylusHandwriting() {
            com.android.server.inputmethod.InputMethodManagerService.this.mHandler.removeMessages(com.android.server.inputmethod.InputMethodManagerService.MSG_FINISH_HANDWRITING);
            com.android.server.inputmethod.InputMethodManagerService.this.mHandler.obtainMessage(com.android.server.inputmethod.InputMethodManagerService.MSG_FINISH_HANDWRITING).sendToTarget();
        }

        @Override // com.android.server.inputmethod.InputMethodManagerInternal
        public void onSwitchKeyboardLayoutShortcut(int i, int i2, android.os.IBinder iBinder) {
            synchronized (com.android.server.inputmethod.ImfLock.class) {
                com.android.server.inputmethod.InputMethodManagerService.this.switchKeyboardLayoutLocked(i);
            }
        }

        @Override // com.android.server.inputmethod.InputMethodManagerInternal
        public boolean isAnyInputConnectionActive() {
            return com.android.server.inputmethod.InputMethodManagerService.this.mCurInputConnection != null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public com.android.internal.inputmethod.IInputContentUriToken createInputContentUriToken(@android.annotation.Nullable android.os.IBinder iBinder, @android.annotation.Nullable android.net.Uri uri, @android.annotation.Nullable java.lang.String str) {
        if (iBinder == null) {
            throw new java.lang.NullPointerException("token");
        }
        if (str == null) {
            throw new java.lang.NullPointerException(com.android.server.pm.verify.domain.DomainVerificationLegacySettings.ATTR_PACKAGE_NAME);
        }
        if (uri == null) {
            throw new java.lang.NullPointerException("contentUri");
        }
        if (!com.android.server.wm.ActivityTaskManagerInternal.ASSIST_KEY_CONTENT.equals(uri.getScheme())) {
            throw new java.security.InvalidParameterException("contentUri must have content scheme");
        }
        synchronized (com.android.server.inputmethod.ImfLock.class) {
            try {
                int callingUid = android.os.Binder.getCallingUid();
                if (getSelectedMethodIdLocked() == null) {
                    return null;
                }
                if (getCurTokenLocked() != iBinder) {
                    android.util.Slog.e(TAG, "Ignoring createInputContentUriToken mCurToken=" + getCurTokenLocked() + " token=" + iBinder);
                    return null;
                }
                java.lang.String str2 = this.mCurEditorInfo != null ? this.mCurEditorInfo.packageName : null;
                if (!android.text.TextUtils.equals(str2, str)) {
                    android.util.Slog.e(TAG, "Ignoring createInputContentUriToken mCurEditorInfo.packageName=" + str2 + " packageName=" + str);
                    return null;
                }
                return new com.android.server.inputmethod.InputContentUriTokenHandler(android.content.ContentProvider.getUriWithoutUserId(uri), callingUid, str, android.content.ContentProvider.getUserIdFromUri(uri, android.os.UserHandle.getUserId(callingUid)), android.os.UserHandle.getUserId(this.mCurClient.mUid));
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void reportFullscreenMode(@android.annotation.NonNull android.os.IBinder iBinder, boolean z) {
        synchronized (com.android.server.inputmethod.ImfLock.class) {
            try {
                if (calledWithValidTokenLocked(iBinder)) {
                    if (this.mCurClient != null && this.mCurClient.mClient != null) {
                        this.mInFullscreenMode = z;
                        this.mCurClient.mClient.reportFullscreenMode(z);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    protected void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        if (com.android.internal.util.DumpUtils.checkDumpPermission(this.mContext, TAG, printWriter)) {
            com.android.server.utils.PriorityDump.dump(this.mPriorityDumper, fileDescriptor, printWriter, strArr);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dumpAsStringNoCheck(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr, boolean z) {
        com.android.server.inputmethod.ClientState clientState;
        com.android.server.inputmethod.ClientState clientState2;
        com.android.server.inputmethod.IInputMethodInvoker curMethodLocked;
        final android.util.PrintWriterPrinter printWriterPrinter = new android.util.PrintWriterPrinter(printWriter);
        synchronized (com.android.server.inputmethod.ImfLock.class) {
            try {
                printWriterPrinter.println("Current Input Method Manager state:");
                java.util.List<android.view.inputmethod.InputMethodInfo> methodList = this.mSettings.getMethodList();
                int size = methodList.size();
                printWriterPrinter.println("  Input Methods: mMethodMapUpdateCount=" + this.mMethodMapUpdateCount);
                for (int i = 0; i < size; i++) {
                    android.view.inputmethod.InputMethodInfo inputMethodInfo = methodList.get(i);
                    printWriterPrinter.println("  InputMethod #" + i + ":");
                    inputMethodInfo.dump(printWriterPrinter, "    ");
                }
                printWriterPrinter.println("  ClientStates:");
                this.mClientController.forAllClients(new java.util.function.Consumer() { // from class: com.android.server.inputmethod.InputMethodManagerService$$ExternalSyntheticLambda12
                    @Override // java.util.function.Consumer
                    public final void accept(java.lang.Object obj) {
                        com.android.server.inputmethod.InputMethodManagerService.lambda$dumpAsStringNoCheck$15(printWriterPrinter, (com.android.server.inputmethod.ClientState) obj);
                    }
                });
                printWriterPrinter.println("  mCurMethodId=" + getSelectedMethodIdLocked());
                clientState = this.mCurClient;
                printWriterPrinter.println("  mCurClient=" + clientState + " mCurSeq=" + getSequenceNumberLocked());
                java.lang.StringBuilder sb = new java.lang.StringBuilder();
                sb.append("  mCurPerceptible=");
                sb.append(this.mCurPerceptible);
                printWriterPrinter.println(sb.toString());
                printWriterPrinter.println("  mCurFocusedWindow=" + this.mCurFocusedWindow + " softInputMode=" + com.android.internal.inputmethod.InputMethodDebug.softInputModeToString(this.mCurFocusedWindowSoftInputMode) + " client=" + this.mCurFocusedWindowClient);
                clientState2 = this.mCurFocusedWindowClient;
                printWriterPrinter.println("  mCurId=" + getCurIdLocked() + " mHaveConnection=" + hasConnectionLocked() + " mBoundToMethod=" + this.mBoundToMethod + " mVisibleBound=" + this.mBindingController.isVisibleBound());
                java.lang.StringBuilder sb2 = new java.lang.StringBuilder();
                sb2.append("  mCurToken=");
                sb2.append(getCurTokenLocked());
                printWriterPrinter.println(sb2.toString());
                java.lang.StringBuilder sb3 = new java.lang.StringBuilder();
                sb3.append("  mCurTokenDisplayId=");
                sb3.append(this.mCurTokenDisplayId);
                printWriterPrinter.println(sb3.toString());
                printWriterPrinter.println("  mCurHostInputToken=" + this.mCurHostInputToken);
                printWriterPrinter.println("  mCurIntent=" + getCurIntentLocked());
                curMethodLocked = getCurMethodLocked();
                printWriterPrinter.println("  mCurMethod=" + getCurMethodLocked());
                printWriterPrinter.println("  mEnabledSession=" + this.mEnabledSession);
                this.mVisibilityStateComputer.dump(printWriter, "  ");
                printWriterPrinter.println("  mInFullscreenMode=" + this.mInFullscreenMode);
                printWriterPrinter.println("  mSystemReady=" + this.mSystemReady + " mInteractive=" + this.mIsInteractive);
                printWriterPrinter.println("  ENABLE_HIDE_IME_CAPTION_BAR=true");
                java.lang.StringBuilder sb4 = new java.lang.StringBuilder();
                sb4.append("  mSettingsObserver=");
                sb4.append(this.mSettingsObserver);
                printWriterPrinter.println(sb4.toString());
                java.lang.StringBuilder sb5 = new java.lang.StringBuilder();
                sb5.append("  mStylusIds=");
                sb5.append(this.mStylusIds != null ? java.util.Arrays.toString(this.mStylusIds.toArray()) : "");
                printWriterPrinter.println(sb5.toString());
                printWriterPrinter.println("  mSwitchingController:");
                this.mSwitchingController.dump(printWriterPrinter);
                printWriterPrinter.println("  mSettings:");
                this.mSettings.dump(printWriterPrinter, "    ");
                printWriterPrinter.println("  mStartInputHistory:");
                this.mStartInputHistory.dump(printWriter, "    ");
                printWriterPrinter.println("  mSoftInputShowHideHistory:");
                this.mSoftInputShowHideHistory.dump(printWriter, "    ");
                printWriterPrinter.println("  mImeTrackerService#History:");
                this.mImeTrackerService.dump(printWriter, "    ");
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        if (z) {
            return;
        }
        printWriterPrinter.println(" ");
        if (clientState != null) {
            printWriter.flush();
            try {
                com.android.internal.os.TransferPipe.dumpAsync(clientState.mClient.asBinder(), fileDescriptor, strArr);
            } catch (android.os.RemoteException | java.io.IOException e) {
                printWriterPrinter.println("Failed to dump input method client: " + e);
            }
        } else {
            printWriterPrinter.println("No input method client.");
        }
        if (clientState2 != null && clientState != clientState2) {
            printWriterPrinter.println(" ");
            printWriterPrinter.println("Warning: Current input method client doesn't match the last focused. window.");
            printWriterPrinter.println("Dumping input method client in the last focused window just in case.");
            printWriterPrinter.println(" ");
            printWriter.flush();
            try {
                com.android.internal.os.TransferPipe.dumpAsync(clientState2.mClient.asBinder(), fileDescriptor, strArr);
            } catch (android.os.RemoteException | java.io.IOException e2) {
                printWriterPrinter.println("Failed to dump input method client in focused window: " + e2);
            }
        }
        printWriterPrinter.println(" ");
        if (curMethodLocked != null) {
            printWriter.flush();
            try {
                com.android.internal.os.TransferPipe.dumpAsync(curMethodLocked.asBinder(), fileDescriptor, strArr);
                return;
            } catch (android.os.RemoteException | java.io.IOException e3) {
                printWriterPrinter.println("Failed to dump input method service: " + e3);
                return;
            }
        }
        printWriterPrinter.println("No input method service.");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$dumpAsStringNoCheck$15(android.util.Printer printer, com.android.server.inputmethod.ClientState clientState) {
        printer.println("  " + clientState + ":");
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("    client=");
        sb.append(clientState.mClient);
        printer.println(sb.toString());
        printer.println("    fallbackInputConnection=" + clientState.mFallbackInputConnection);
        printer.println("    sessionRequested=" + clientState.mSessionRequested);
        printer.println("    sessionRequestedForAccessibility=" + clientState.mSessionRequestedForAccessibility);
        printer.println("    curSession=" + clientState.mCurSession);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void onShellCommand(@android.annotation.Nullable java.io.FileDescriptor fileDescriptor, @android.annotation.Nullable java.io.FileDescriptor fileDescriptor2, @android.annotation.Nullable java.io.FileDescriptor fileDescriptor3, @android.annotation.NonNull java.lang.String[] strArr, @android.annotation.Nullable android.os.ShellCallback shellCallback, @android.annotation.NonNull android.os.ResultReceiver resultReceiver) throws android.os.RemoteException {
        int callingUid = android.os.Binder.getCallingUid();
        if (callingUid != 0 && callingUid != 2000) {
            if (resultReceiver != null) {
                resultReceiver.send(-1, null);
            }
            java.lang.String str = "InputMethodManagerService does not support shell commands from non-shell users. callingUid=" + callingUid + " args=" + java.util.Arrays.toString(strArr);
            if (android.os.Process.isCoreUid(callingUid)) {
                android.util.Slog.e(TAG, str);
                return;
            }
            throw new java.lang.SecurityException(str);
        }
        new com.android.server.inputmethod.InputMethodManagerService.ShellCommandImpl(this).exec(this, fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, shellCallback, resultReceiver);
    }

    private static final class ShellCommandImpl extends android.os.ShellCommand {

        @android.annotation.NonNull
        final com.android.server.inputmethod.InputMethodManagerService mService;

        ShellCommandImpl(com.android.server.inputmethod.InputMethodManagerService inputMethodManagerService) {
            this.mService = inputMethodManagerService;
        }

        public int onCommand(@android.annotation.Nullable java.lang.String str) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                return onCommandWithSystemIdentity(str);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        /* JADX WARN: Code restructure failed: missing block: B:39:0x006e, code lost:
        
            if (r8.equals("help") != false) goto L49;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        private int onCommandWithSystemIdentity(@android.annotation.Nullable java.lang.String str) {
            boolean z;
            java.lang.String emptyIfNull = android.text.TextUtils.emptyIfNull(str);
            char c = 2;
            switch (emptyIfNull.hashCode()) {
                case -1180406812:
                    if (emptyIfNull.equals("get-last-switch-user-id")) {
                        z = false;
                        break;
                    }
                    z = -1;
                    break;
                case -1067396926:
                    if (emptyIfNull.equals("tracing")) {
                        z = true;
                        break;
                    }
                    z = -1;
                    break;
                case 104385:
                    if (emptyIfNull.equals("ime")) {
                        z = 2;
                        break;
                    }
                    z = -1;
                    break;
                default:
                    z = -1;
                    break;
            }
            switch (z) {
                case false:
                    return this.mService.getLastSwitchUserId(this);
                case true:
                    return this.mService.handleShellCommandTraceInputMethod(this);
                case true:
                    java.lang.String emptyIfNull2 = android.text.TextUtils.emptyIfNull(getNextArg());
                    switch (emptyIfNull2.hashCode()) {
                        case -1298848381:
                            if (emptyIfNull2.equals("enable")) {
                                c = 4;
                                break;
                            }
                            c = 65535;
                            break;
                        case -1067396926:
                            if (emptyIfNull2.equals("tracing")) {
                                c = '\b';
                                break;
                            }
                            c = 65535;
                            break;
                        case 0:
                            if (emptyIfNull2.equals("")) {
                                c = 0;
                                break;
                            }
                            c = 65535;
                            break;
                        case 1499:
                            if (emptyIfNull2.equals("-h")) {
                                c = 1;
                                break;
                            }
                            c = 65535;
                            break;
                        case 113762:
                            if (emptyIfNull2.equals("set")) {
                                c = 6;
                                break;
                            }
                            c = 65535;
                            break;
                        case 3198785:
                            break;
                        case 3322014:
                            if (emptyIfNull2.equals("list")) {
                                c = 3;
                                break;
                            }
                            c = 65535;
                            break;
                        case 108404047:
                            if (emptyIfNull2.equals("reset")) {
                                c = 7;
                                break;
                            }
                            c = 65535;
                            break;
                        case 1671308008:
                            if (emptyIfNull2.equals("disable")) {
                                c = 5;
                                break;
                            }
                            c = 65535;
                            break;
                        default:
                            c = 65535;
                            break;
                    }
                    switch (c) {
                        case 0:
                        case 1:
                        case 2:
                            return onImeCommandHelp();
                        case 3:
                            return this.mService.handleShellCommandListInputMethods(this);
                        case 4:
                            return this.mService.handleShellCommandEnableDisableInputMethod(this, true);
                        case 5:
                            return this.mService.handleShellCommandEnableDisableInputMethod(this, false);
                        case 6:
                            return this.mService.handleShellCommandSetInputMethod(this);
                        case 7:
                            return this.mService.handleShellCommandResetInputMethod(this);
                        case '\b':
                            return this.mService.handleShellCommandTraceInputMethod(this);
                        default:
                            getOutPrintWriter().println("Unknown command: " + emptyIfNull2);
                            return -1;
                    }
                default:
                    return handleDefaultCommands(str);
            }
        }

        public void onHelp() {
            java.io.PrintWriter outPrintWriter = getOutPrintWriter();
            try {
                outPrintWriter.println("InputMethodManagerService commands:");
                outPrintWriter.println("  help");
                outPrintWriter.println("    Prints this help text.");
                outPrintWriter.println("  dump [options]");
                outPrintWriter.println("    Synonym of dumpsys.");
                outPrintWriter.println("  ime <command> [options]");
                outPrintWriter.println("    Manipulate IMEs.  Run \"ime help\" for details.");
                outPrintWriter.println("  tracing <command>");
                outPrintWriter.println("    start: Start tracing.");
                outPrintWriter.println("    stop : Stop tracing.");
                outPrintWriter.println("    help : Show help.");
                outPrintWriter.close();
            } catch (java.lang.Throwable th) {
                if (outPrintWriter != null) {
                    try {
                        outPrintWriter.close();
                    } catch (java.lang.Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }

        private int onImeCommandHelp() {
            android.util.IndentingPrintWriter indentingPrintWriter = new android.util.IndentingPrintWriter(getOutPrintWriter(), "  ", 100);
            try {
                indentingPrintWriter.println("ime <command>:");
                indentingPrintWriter.increaseIndent();
                indentingPrintWriter.println("list [-a] [-s]");
                indentingPrintWriter.increaseIndent();
                indentingPrintWriter.println("prints all enabled input methods.");
                indentingPrintWriter.increaseIndent();
                indentingPrintWriter.println("-a: see all input methods");
                indentingPrintWriter.println("-s: only a single summary line of each");
                indentingPrintWriter.decreaseIndent();
                indentingPrintWriter.decreaseIndent();
                indentingPrintWriter.println("enable [--user <USER_ID>] <ID>");
                indentingPrintWriter.increaseIndent();
                indentingPrintWriter.println("allows the given input method ID to be used.");
                indentingPrintWriter.increaseIndent();
                indentingPrintWriter.print("--user <USER_ID>: Specify which user to enable.");
                indentingPrintWriter.println(" Assumes the current user if not specified.");
                indentingPrintWriter.decreaseIndent();
                indentingPrintWriter.decreaseIndent();
                indentingPrintWriter.println("disable [--user <USER_ID>] <ID>");
                indentingPrintWriter.increaseIndent();
                indentingPrintWriter.println("disallows the given input method ID to be used.");
                indentingPrintWriter.increaseIndent();
                indentingPrintWriter.print("--user <USER_ID>: Specify which user to disable.");
                indentingPrintWriter.println(" Assumes the current user if not specified.");
                indentingPrintWriter.decreaseIndent();
                indentingPrintWriter.decreaseIndent();
                indentingPrintWriter.println("set [--user <USER_ID>] <ID>");
                indentingPrintWriter.increaseIndent();
                indentingPrintWriter.println("switches to the given input method ID.");
                indentingPrintWriter.increaseIndent();
                indentingPrintWriter.print("--user <USER_ID>: Specify which user to enable.");
                indentingPrintWriter.println(" Assumes the current user if not specified.");
                indentingPrintWriter.decreaseIndent();
                indentingPrintWriter.decreaseIndent();
                indentingPrintWriter.println("reset [--user <USER_ID>]");
                indentingPrintWriter.increaseIndent();
                indentingPrintWriter.println("reset currently selected/enabled IMEs to the default ones as if the device is initially booted with the current locale.");
                indentingPrintWriter.increaseIndent();
                indentingPrintWriter.print("--user <USER_ID>: Specify which user to reset.");
                indentingPrintWriter.println(" Assumes the current user if not specified.");
                indentingPrintWriter.decreaseIndent();
                indentingPrintWriter.decreaseIndent();
                indentingPrintWriter.decreaseIndent();
                indentingPrintWriter.close();
                return 0;
            } catch (java.lang.Throwable th) {
                try {
                    indentingPrintWriter.close();
                } catch (java.lang.Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getLastSwitchUserId(@android.annotation.NonNull android.os.ShellCommand shellCommand) {
        synchronized (com.android.server.inputmethod.ImfLock.class) {
            shellCommand.getOutPrintWriter().println(this.mLastSwitchUserId);
        }
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public int handleShellCommandListInputMethods(@android.annotation.NonNull android.os.ShellCommand shellCommand) {
        java.util.List<android.view.inputmethod.InputMethodInfo> enabledInputMethodListLocked;
        char c;
        int i = -2;
        boolean z = false;
        boolean z2 = false;
        while (true) {
            java.lang.String nextOption = shellCommand.getNextOption();
            if (nextOption != null) {
                switch (nextOption.hashCode()) {
                    case 1492:
                        if (nextOption.equals("-a")) {
                            c = 0;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1510:
                        if (nextOption.equals("-s")) {
                            c = 1;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1512:
                        if (nextOption.equals("-u")) {
                            c = 2;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1333469547:
                        if (nextOption.equals("--user")) {
                            c = 3;
                            break;
                        }
                        c = 65535;
                        break;
                    default:
                        c = 65535;
                        break;
                }
                switch (c) {
                    case 0:
                        z = true;
                        break;
                    case 1:
                        z2 = true;
                        break;
                    case 2:
                    case 3:
                        i = android.os.UserHandle.parseUserArg(shellCommand.getNextArgRequired());
                        break;
                }
            } else {
                synchronized (com.android.server.inputmethod.ImfLock.class) {
                    try {
                        int[] resolveUserId = com.android.server.inputmethod.InputMethodUtils.resolveUserId(i, this.mSettings.getUserId(), shellCommand.getErrPrintWriter());
                        final java.io.PrintWriter outPrintWriter = shellCommand.getOutPrintWriter();
                        try {
                            for (int i2 : resolveUserId) {
                                if (z) {
                                    enabledInputMethodListLocked = getInputMethodListLocked(i2, 0, 2000);
                                } else {
                                    enabledInputMethodListLocked = getEnabledInputMethodListLocked(i2, 2000);
                                }
                                if (resolveUserId.length > 1) {
                                    outPrintWriter.print("User #");
                                    outPrintWriter.print(i2);
                                    outPrintWriter.println(":");
                                }
                                for (android.view.inputmethod.InputMethodInfo inputMethodInfo : enabledInputMethodListLocked) {
                                    if (z2) {
                                        outPrintWriter.println(inputMethodInfo.getId());
                                    } else {
                                        outPrintWriter.print(inputMethodInfo.getId());
                                        outPrintWriter.println(":");
                                        java.util.Objects.requireNonNull(outPrintWriter);
                                        inputMethodInfo.dump(new android.util.Printer() { // from class: com.android.server.inputmethod.InputMethodManagerService$$ExternalSyntheticLambda8
                                            @Override // android.util.Printer
                                            public final void println(java.lang.String str) {
                                                outPrintWriter.println(str);
                                            }
                                        }, "  ");
                                    }
                                }
                            }
                            if (outPrintWriter != null) {
                                outPrintWriter.close();
                            }
                        } finally {
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
                return 0;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int handleShellCommandEnableDisableInputMethod(@android.annotation.NonNull android.os.ShellCommand shellCommand, boolean z) {
        boolean z2;
        int handleOptionsForCommandsThatOnlyHaveUserOption = handleOptionsForCommandsThatOnlyHaveUserOption(shellCommand);
        java.lang.String nextArgRequired = shellCommand.getNextArgRequired();
        java.io.PrintWriter outPrintWriter = shellCommand.getOutPrintWriter();
        try {
            java.io.PrintWriter errPrintWriter = shellCommand.getErrPrintWriter();
            try {
                synchronized (com.android.server.inputmethod.ImfLock.class) {
                    try {
                        z2 = false;
                        for (int i : com.android.server.inputmethod.InputMethodUtils.resolveUserId(handleOptionsForCommandsThatOnlyHaveUserOption, this.mSettings.getUserId(), shellCommand.getErrPrintWriter())) {
                            if (userHasDebugPriv(i, shellCommand)) {
                                z2 |= !handleShellCommandEnableDisableInputMethodInternalLocked(r2, nextArgRequired, z, outPrintWriter, errPrintWriter);
                            }
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
                if (errPrintWriter != null) {
                    errPrintWriter.close();
                }
                if (outPrintWriter != null) {
                    outPrintWriter.close();
                }
                return z2 ? -1 : 0;
            } finally {
            }
        } finally {
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private static int handleOptionsForCommandsThatOnlyHaveUserOption(android.os.ShellCommand shellCommand) {
        char c;
        while (true) {
            java.lang.String nextOption = shellCommand.getNextOption();
            if (nextOption != null) {
                switch (nextOption.hashCode()) {
                    case 1512:
                        if (nextOption.equals("-u")) {
                            c = 0;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1333469547:
                        if (nextOption.equals("--user")) {
                            c = 1;
                            break;
                        }
                        c = 65535;
                        break;
                    default:
                        c = 65535;
                        break;
                }
                switch (c) {
                    case 0:
                    case 1:
                        return android.os.UserHandle.parseUserArg(shellCommand.getNextArgRequired());
                }
            }
            return -2;
        }
    }

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    private boolean handleShellCommandEnableDisableInputMethodInternalLocked(int i, java.lang.String str, boolean z, java.io.PrintWriter printWriter, java.io.PrintWriter printWriter2) {
        boolean buildAndPutEnabledInputMethodsStrRemovingId;
        boolean z2;
        if (i == this.mSettings.getUserId()) {
            if (z && !this.mSettings.getMethodMap().containsKey(str)) {
                z2 = true;
                buildAndPutEnabledInputMethodsStrRemovingId = false;
            } else {
                buildAndPutEnabledInputMethodsStrRemovingId = setInputMethodEnabledLocked(str, z);
                z2 = false;
            }
        } else {
            com.android.server.inputmethod.InputMethodSettings queryMethodMapForUser = queryMethodMapForUser(i);
            if (z) {
                if (!queryMethodMapForUser.getMethodMap().containsKey(str)) {
                    z2 = true;
                    buildAndPutEnabledInputMethodsStrRemovingId = false;
                } else {
                    java.lang.String enabledInputMethodsStr = queryMethodMapForUser.getEnabledInputMethodsStr();
                    java.lang.String concatEnabledImeIds = com.android.server.inputmethod.InputMethodUtils.concatEnabledImeIds(enabledInputMethodsStr, str);
                    boolean equals = android.text.TextUtils.equals(enabledInputMethodsStr, concatEnabledImeIds);
                    if (!equals) {
                        queryMethodMapForUser.putEnabledInputMethodsStr(concatEnabledImeIds);
                    }
                    buildAndPutEnabledInputMethodsStrRemovingId = equals;
                    z2 = false;
                }
            } else {
                buildAndPutEnabledInputMethodsStrRemovingId = queryMethodMapForUser.buildAndPutEnabledInputMethodsStrRemovingId(new java.lang.StringBuilder(), queryMethodMapForUser.getEnabledInputMethodsAndSubtypeList(), str);
                z2 = false;
            }
        }
        if (z2) {
            printWriter2.print("Unknown input method ");
            printWriter2.print(str);
            printWriter2.println(" cannot be enabled for user #" + i);
            android.util.Slog.e(TAG, "\"ime enable " + str + "\" for user #" + i + " failed due to its unrecognized IME ID.");
            return false;
        }
        printWriter.print("Input method ");
        printWriter.print(str);
        printWriter.print(": ");
        printWriter.print(z == buildAndPutEnabledInputMethodsStrRemovingId ? "already " : "now ");
        printWriter.print(z ? com.android.server.timezonedetector.ServiceConfigAccessor.PROVIDER_MODE_ENABLED : com.android.server.timezonedetector.ServiceConfigAccessor.PROVIDER_MODE_DISABLED);
        printWriter.print(" for user #");
        printWriter.println(i);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int handleShellCommandSetInputMethod(@android.annotation.NonNull android.os.ShellCommand shellCommand) {
        boolean z;
        int handleOptionsForCommandsThatOnlyHaveUserOption = handleOptionsForCommandsThatOnlyHaveUserOption(shellCommand);
        java.lang.String nextArgRequired = shellCommand.getNextArgRequired();
        java.io.PrintWriter outPrintWriter = shellCommand.getOutPrintWriter();
        try {
            java.io.PrintWriter errPrintWriter = shellCommand.getErrPrintWriter();
            try {
                synchronized (com.android.server.inputmethod.ImfLock.class) {
                    try {
                        z = false;
                        for (int i : com.android.server.inputmethod.InputMethodUtils.resolveUserId(handleOptionsForCommandsThatOnlyHaveUserOption, this.mSettings.getUserId(), shellCommand.getErrPrintWriter())) {
                            if (userHasDebugPriv(i, shellCommand)) {
                                boolean z2 = !switchToInputMethodLocked(nextArgRequired, i);
                                if (z2) {
                                    errPrintWriter.print("Unknown input method ");
                                    errPrintWriter.print(nextArgRequired);
                                    errPrintWriter.print(" cannot be selected for user #");
                                    errPrintWriter.println(i);
                                    android.util.Slog.e(TAG, "\"ime set " + nextArgRequired + "\" for user #" + i + " failed due to its unrecognized IME ID.");
                                } else {
                                    outPrintWriter.print("Input method ");
                                    outPrintWriter.print(nextArgRequired);
                                    outPrintWriter.print(" selected for user #");
                                    outPrintWriter.println(i);
                                }
                                z |= z2;
                            }
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
                if (errPrintWriter != null) {
                    errPrintWriter.close();
                }
                if (outPrintWriter != null) {
                    outPrintWriter.close();
                }
                return z ? -1 : 0;
            } finally {
            }
        } catch (java.lang.Throwable th2) {
            if (outPrintWriter != null) {
                try {
                    outPrintWriter.close();
                } catch (java.lang.Throwable th3) {
                    th2.addSuppressed(th3);
                }
            }
            throw th2;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int handleShellCommandResetInputMethod(@android.annotation.NonNull android.os.ShellCommand shellCommand) {
        java.util.ArrayList<android.view.inputmethod.InputMethodInfo> arrayList;
        java.lang.String str;
        int handleOptionsForCommandsThatOnlyHaveUserOption = handleOptionsForCommandsThatOnlyHaveUserOption(shellCommand);
        synchronized (com.android.server.inputmethod.ImfLock.class) {
            try {
                final java.io.PrintWriter outPrintWriter = shellCommand.getOutPrintWriter();
                try {
                    for (int i : com.android.server.inputmethod.InputMethodUtils.resolveUserId(handleOptionsForCommandsThatOnlyHaveUserOption, this.mSettings.getUserId(), shellCommand.getErrPrintWriter())) {
                        if (userHasDebugPriv(i, shellCommand) && !"android.os.usertype.system.HEADLESS".equals(this.mUserManagerInternal.getUserInfo(i).userType)) {
                            if (i == this.mSettings.getUserId()) {
                                hideCurrentInputLocked(this.mCurFocusedWindow, 0, 15);
                                this.mBindingController.unbindCurrentMethod();
                                java.util.ArrayList<android.view.inputmethod.InputMethodInfo> enabledInputMethodList = this.mSettings.getEnabledInputMethodList();
                                java.util.ArrayList<android.view.inputmethod.InputMethodInfo> defaultEnabledImes = com.android.server.inputmethod.InputMethodInfoUtils.getDefaultEnabledImes(this.mContext, this.mSettings.getMethodList());
                                enabledInputMethodList.removeAll(defaultEnabledImes);
                                java.util.Iterator<android.view.inputmethod.InputMethodInfo> it = enabledInputMethodList.iterator();
                                while (it.hasNext()) {
                                    setInputMethodEnabledLocked(it.next().getId(), false);
                                }
                                java.util.Iterator<android.view.inputmethod.InputMethodInfo> it2 = defaultEnabledImes.iterator();
                                while (it2.hasNext()) {
                                    setInputMethodEnabledLocked(it2.next().getId(), true);
                                }
                                if (!chooseNewDefaultIMELocked()) {
                                    resetSelectedInputMethodAndSubtypeLocked(null);
                                }
                                updateInputMethodsFromSettingsLocked(true);
                                com.android.server.inputmethod.InputMethodUtils.setNonSelectedSystemImesDisabledUntilUsed(getPackageManagerForUser(this.mContext, this.mSettings.getUserId()), this.mSettings.getEnabledInputMethodList());
                                str = this.mSettings.getSelectedInputMethod();
                                arrayList = this.mSettings.getEnabledInputMethodList();
                            } else {
                                com.android.server.inputmethod.InputMethodSettings queryInputMethodServicesInternal = queryInputMethodServicesInternal(this.mContext, i, com.android.server.inputmethod.AdditionalSubtypeUtils.load(i), 0);
                                java.util.ArrayList<android.view.inputmethod.InputMethodInfo> defaultEnabledImes2 = com.android.server.inputmethod.InputMethodInfoUtils.getDefaultEnabledImes(this.mContext, queryInputMethodServicesInternal.getMethodList());
                                java.lang.String id = com.android.server.inputmethod.InputMethodInfoUtils.getMostApplicableDefaultIME(defaultEnabledImes2).getId();
                                int size = defaultEnabledImes2.size();
                                java.lang.String[] strArr = new java.lang.String[size];
                                for (int i2 = 0; i2 < size; i2++) {
                                    strArr[i2] = defaultEnabledImes2.get(i2).getId();
                                }
                                queryInputMethodServicesInternal.putEnabledInputMethodsStr(com.android.server.inputmethod.InputMethodUtils.concatEnabledImeIds(queryInputMethodServicesInternal.getEnabledInputMethodsStr(), strArr));
                                queryInputMethodServicesInternal.putSelectedInputMethod(id);
                                queryInputMethodServicesInternal.putSelectedDefaultDeviceInputMethod(null);
                                queryInputMethodServicesInternal.putSelectedSubtype(-1);
                                arrayList = defaultEnabledImes2;
                                str = id;
                            }
                            outPrintWriter.println("Reset current and enabled IMEs for user #" + i);
                            outPrintWriter.println("  Selected: " + str);
                            arrayList.forEach(new java.util.function.Consumer() { // from class: com.android.server.inputmethod.InputMethodManagerService$$ExternalSyntheticLambda6
                                @Override // java.util.function.Consumer
                                public final void accept(java.lang.Object obj) {
                                    com.android.server.inputmethod.InputMethodManagerService.lambda$handleShellCommandResetInputMethod$16(outPrintWriter, (android.view.inputmethod.InputMethodInfo) obj);
                                }
                            });
                        }
                    }
                    if (outPrintWriter != null) {
                        outPrintWriter.close();
                    }
                } finally {
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$handleShellCommandResetInputMethod$16(java.io.PrintWriter printWriter, android.view.inputmethod.InputMethodInfo inputMethodInfo) {
        printWriter.println("   Enabled: " + inputMethodInfo.getId());
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public int handleShellCommandTraceInputMethod(@android.annotation.NonNull android.os.ShellCommand shellCommand) {
        char c;
        java.lang.String nextArgRequired = shellCommand.getNextArgRequired();
        java.io.PrintWriter outPrintWriter = shellCommand.getOutPrintWriter();
        try {
            switch (nextArgRequired.hashCode()) {
                case -390772652:
                    if (nextArgRequired.equals("save-for-bugreport")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case 3540994:
                    if (nextArgRequired.equals("stop")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 109757538:
                    if (nextArgRequired.equals("start")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            switch (c) {
                case 0:
                    com.android.internal.inputmethod.ImeTracing.getInstance().startTrace(outPrintWriter);
                    break;
                case 1:
                    com.android.internal.inputmethod.ImeTracing.getInstance().stopTrace(outPrintWriter);
                    break;
                case 2:
                    com.android.internal.inputmethod.ImeTracing.getInstance().saveForBugreport(outPrintWriter);
                    if (outPrintWriter != null) {
                        outPrintWriter.close();
                    }
                    return 0;
                default:
                    outPrintWriter.println("Unknown command: " + nextArgRequired);
                    outPrintWriter.println("Input method trace options:");
                    outPrintWriter.println("  start: Start tracing");
                    outPrintWriter.println("  stop: Stop tracing");
                    outPrintWriter.close();
                    return -1;
            }
            if (outPrintWriter != null) {
                outPrintWriter.close();
            }
            final boolean isEnabled = com.android.internal.inputmethod.ImeTracing.getInstance().isEnabled();
            synchronized (com.android.server.inputmethod.ImfLock.class) {
                this.mClientController.forAllClients(new java.util.function.Consumer<com.android.server.inputmethod.ClientState>() { // from class: com.android.server.inputmethod.InputMethodManagerService.5
                    @Override // java.util.function.Consumer
                    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
                    public void accept(com.android.server.inputmethod.ClientState clientState) {
                        clientState.mClient.setImeTraceEnabled(isEnabled);
                    }
                });
            }
            return 0;
        } catch (java.lang.Throwable th) {
            if (outPrintWriter != null) {
                try {
                    outPrintWriter.close();
                } catch (java.lang.Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    private boolean userHasDebugPriv(int i, android.os.ShellCommand shellCommand) {
        if (this.mUserManagerInternal.hasUserRestriction("no_debugging_features", i)) {
            shellCommand.getErrPrintWriter().println("User #" + i + " is restricted with DISALLOW_DEBUGGING_FEATURES.");
            return false;
        }
        return true;
    }

    public com.android.internal.inputmethod.IImeTracker getImeTrackerService() {
        return this.mImeTrackerService;
    }

    @android.annotation.NonNull
    private android.view.inputmethod.ImeTracker.Token createStatsTokenForFocusedClient(boolean z, int i) {
        int i2;
        java.lang.String str;
        if (this.mCurFocusedWindowClient != null) {
            i2 = this.mCurFocusedWindowClient.mUid;
        } else {
            i2 = -1;
        }
        if (this.mCurFocusedWindowEditorInfo != null) {
            str = this.mCurFocusedWindowEditorInfo.packageName;
        } else {
            str = "uid(" + i2 + ")";
        }
        return android.view.inputmethod.ImeTracker.forLogging().onStart(str, i2, z ? 1 : 2, 6, i, false);
    }

    private static final class InputMethodPrivilegedOperationsImpl extends com.android.internal.inputmethod.IInputMethodPrivilegedOperations.Stub {
        private final com.android.server.inputmethod.InputMethodManagerService mImms;

        @android.annotation.NonNull
        private final android.os.IBinder mToken;

        InputMethodPrivilegedOperationsImpl(com.android.server.inputmethod.InputMethodManagerService inputMethodManagerService, @android.annotation.NonNull android.os.IBinder iBinder) {
            this.mImms = inputMethodManagerService;
            this.mToken = iBinder;
        }

        public void setImeWindowStatusAsync(int i, int i2) {
            this.mImms.setImeWindowStatus(this.mToken, i, i2);
        }

        public void reportStartInputAsync(android.os.IBinder iBinder) {
            this.mImms.reportStartInput(this.mToken, iBinder);
        }

        public void createInputContentUriToken(android.net.Uri uri, java.lang.String str, com.android.internal.infra.AndroidFuture androidFuture) {
            try {
                androidFuture.complete(this.mImms.createInputContentUriToken(this.mToken, uri, str).asBinder());
            } catch (java.lang.Throwable th) {
                androidFuture.completeExceptionally(th);
            }
        }

        public void reportFullscreenModeAsync(boolean z) {
            this.mImms.reportFullscreenMode(this.mToken, z);
        }

        public void setInputMethod(java.lang.String str, com.android.internal.infra.AndroidFuture androidFuture) {
            try {
                this.mImms.setInputMethod(this.mToken, str);
                androidFuture.complete((java.lang.Object) null);
            } catch (java.lang.Throwable th) {
                androidFuture.completeExceptionally(th);
            }
        }

        public void setInputMethodAndSubtype(java.lang.String str, android.view.inputmethod.InputMethodSubtype inputMethodSubtype, com.android.internal.infra.AndroidFuture androidFuture) {
            try {
                this.mImms.setInputMethodAndSubtype(this.mToken, str, inputMethodSubtype);
                androidFuture.complete((java.lang.Object) null);
            } catch (java.lang.Throwable th) {
                androidFuture.completeExceptionally(th);
            }
        }

        public void hideMySoftInput(@android.annotation.NonNull android.view.inputmethod.ImeTracker.Token token, int i, int i2, com.android.internal.infra.AndroidFuture androidFuture) {
            try {
                this.mImms.hideMySoftInput(this.mToken, token, i, i2);
                androidFuture.complete((java.lang.Object) null);
            } catch (java.lang.Throwable th) {
                androidFuture.completeExceptionally(th);
            }
        }

        public void showMySoftInput(@android.annotation.NonNull android.view.inputmethod.ImeTracker.Token token, int i, int i2, com.android.internal.infra.AndroidFuture androidFuture) {
            try {
                this.mImms.showMySoftInput(this.mToken, token, i, i2);
                androidFuture.complete((java.lang.Object) null);
            } catch (java.lang.Throwable th) {
                androidFuture.completeExceptionally(th);
            }
        }

        public void updateStatusIconAsync(java.lang.String str, int i) {
            this.mImms.updateStatusIcon(this.mToken, str, i);
        }

        public void switchToPreviousInputMethod(com.android.internal.infra.AndroidFuture androidFuture) {
            try {
                androidFuture.complete(java.lang.Boolean.valueOf(this.mImms.switchToPreviousInputMethod(this.mToken)));
            } catch (java.lang.Throwable th) {
                androidFuture.completeExceptionally(th);
            }
        }

        public void switchToNextInputMethod(boolean z, com.android.internal.infra.AndroidFuture androidFuture) {
            try {
                androidFuture.complete(java.lang.Boolean.valueOf(this.mImms.switchToNextInputMethod(this.mToken, z)));
            } catch (java.lang.Throwable th) {
                androidFuture.completeExceptionally(th);
            }
        }

        public void shouldOfferSwitchingToNextInputMethod(com.android.internal.infra.AndroidFuture androidFuture) {
            try {
                androidFuture.complete(java.lang.Boolean.valueOf(this.mImms.shouldOfferSwitchingToNextInputMethod(this.mToken)));
            } catch (java.lang.Throwable th) {
                androidFuture.completeExceptionally(th);
            }
        }

        public void notifyUserActionAsync() {
            this.mImms.notifyUserAction(this.mToken);
        }

        public void applyImeVisibilityAsync(android.os.IBinder iBinder, boolean z, @android.annotation.NonNull android.view.inputmethod.ImeTracker.Token token) {
            this.mImms.applyImeVisibility(this.mToken, iBinder, z, token);
        }

        public void onStylusHandwritingReady(int i, int i2) {
            this.mImms.onStylusHandwritingReady(i, i2);
        }

        public void resetStylusHandwriting(int i) {
            this.mImms.resetStylusHandwriting(i);
        }

        public void switchKeyboardLayoutAsync(int i) {
            synchronized (com.android.server.inputmethod.ImfLock.class) {
                try {
                    if (this.mImms.calledWithValidTokenLocked(this.mToken)) {
                        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                        try {
                            this.mImms.switchKeyboardLayoutLocked(i);
                        } finally {
                            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        }
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }
}
