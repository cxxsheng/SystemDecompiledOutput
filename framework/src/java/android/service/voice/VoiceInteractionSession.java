package android.service.voice;

/* loaded from: classes3.dex */
public class VoiceInteractionSession implements android.view.KeyEvent.Callback, android.content.ComponentCallbacks2 {
    static final boolean DEBUG = false;
    public static final java.lang.String KEY_FOREGROUND_ACTIVITIES = "android.service.voice.FOREGROUND_ACTIVITIES";
    public static final java.lang.String KEY_SHOW_SESSION_ID = "android.service.voice.SHOW_SESSION_ID";
    static final int MSG_CANCEL = 7;
    static final int MSG_CLOSE_SYSTEM_DIALOGS = 102;
    static final int MSG_DESTROY = 103;
    static final int MSG_HANDLE_ASSIST = 104;
    static final int MSG_HANDLE_SCREENSHOT = 105;
    static final int MSG_HIDE = 107;
    static final int MSG_NOTIFY_VISIBLE_ACTIVITY_INFO_CHANGED = 109;
    static final int MSG_ON_LOCKSCREEN_SHOWN = 108;
    static final int MSG_REGISTER_VISIBLE_ACTIVITY_CALLBACK = 110;
    static final int MSG_SHOW = 106;
    static final int MSG_START_ABORT_VOICE = 4;
    static final int MSG_START_COMMAND = 5;
    static final int MSG_START_COMPLETE_VOICE = 3;
    static final int MSG_START_CONFIRMATION = 1;
    static final int MSG_START_PICK_OPTION = 2;
    static final int MSG_SUPPORTS_COMMANDS = 6;
    static final int MSG_TASK_FINISHED = 101;
    static final int MSG_TASK_STARTED = 100;
    static final int MSG_UNREGISTER_VISIBLE_ACTIVITY_CALLBACK = 111;
    public static final int SHOW_SOURCE_ACTIVITY = 16;
    public static final int SHOW_SOURCE_APPLICATION = 8;
    public static final int SHOW_SOURCE_ASSIST_GESTURE = 4;
    public static final int SHOW_SOURCE_AUTOMOTIVE_SYSTEM_UI = 128;
    public static final int SHOW_SOURCE_NOTIFICATION = 64;
    public static final int SHOW_SOURCE_PUSH_TO_TALK = 32;
    public static final int SHOW_WITH_ASSIST = 1;
    public static final int SHOW_WITH_SCREENSHOT = 2;
    static final java.lang.String TAG = "VoiceInteractionSession";
    public static final int VOICE_INTERACTION_ACTIVITY_EVENT_PAUSE = 3;
    public static final int VOICE_INTERACTION_ACTIVITY_EVENT_RESUME = 2;
    public static final int VOICE_INTERACTION_ACTIVITY_EVENT_START = 1;
    public static final int VOICE_INTERACTION_ACTIVITY_EVENT_STOP = 4;
    final android.util.ArrayMap<android.os.IBinder, android.service.voice.VoiceInteractionSession.Request> mActiveRequests;
    final android.service.voice.VoiceInteractionSession.MyCallbacks mCallbacks;
    android.widget.FrameLayout mContentFrame;
    final android.content.Context mContext;
    final android.view.KeyEvent.DispatcherState mDispatcherState;
    final com.android.internal.os.HandlerCaller mHandlerCaller;
    boolean mInShowWindow;
    android.view.LayoutInflater mInflater;
    boolean mInitialized;
    final android.view.ViewTreeObserver.OnComputeInternalInsetsListener mInsetsComputer;
    final com.android.internal.app.IVoiceInteractor mInteractor;
    android.os.ICancellationSignal mKillCallback;
    final java.util.Map<android.service.voice.VoiceInteractionSession.SafeResultListener, java.util.function.Consumer<android.os.Bundle>> mRemoteCallbacks;
    android.view.View mRootView;
    final android.service.voice.IVoiceInteractionSession mSession;
    com.android.internal.app.IVoiceInteractionManagerService mSystemService;
    int mTheme;
    android.content.res.TypedArray mThemeAttrs;
    final android.service.voice.VoiceInteractionSession.Insets mTmpInsets;
    android.os.IBinder mToken;
    boolean mUiEnabled;
    private final java.util.Map<android.service.voice.VoiceInteractionSession.VisibleActivityCallback, java.util.concurrent.Executor> mVisibleActivityCallbacks;
    private final java.util.List<android.service.voice.VisibleActivityInfo> mVisibleActivityInfos;
    final java.lang.ref.WeakReference<android.service.voice.VoiceInteractionSession> mWeakRef;
    android.service.voice.VoiceInteractionWindow mWindow;
    boolean mWindowAdded;
    boolean mWindowVisible;
    boolean mWindowWasVisible;

    public static final class Insets {
        public static final int TOUCHABLE_INSETS_CONTENT = 1;
        public static final int TOUCHABLE_INSETS_FRAME = 0;
        public static final int TOUCHABLE_INSETS_REGION = 3;
        public int touchableInsets;
        public final android.graphics.Rect contentInsets = new android.graphics.Rect();
        public final android.graphics.Region touchableRegion = new android.graphics.Region();
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface VoiceInteractionActivityEventType {
    }

    public static class Request {
        final com.android.internal.app.IVoiceInteractorCallback mCallback;
        final java.lang.String mCallingPackage;
        final int mCallingUid;
        final android.os.Bundle mExtras;
        final com.android.internal.app.IVoiceInteractorRequest mInterface = new com.android.internal.app.IVoiceInteractorRequest.Stub() { // from class: android.service.voice.VoiceInteractionSession.Request.1
            @Override // com.android.internal.app.IVoiceInteractorRequest
            public void cancel() throws android.os.RemoteException {
                android.service.voice.VoiceInteractionSession voiceInteractionSession = android.service.voice.VoiceInteractionSession.Request.this.mSession.get();
                if (voiceInteractionSession != null) {
                    voiceInteractionSession.mHandlerCaller.sendMessage(voiceInteractionSession.mHandlerCaller.obtainMessageO(7, android.service.voice.VoiceInteractionSession.Request.this));
                }
            }
        };
        final java.lang.ref.WeakReference<android.service.voice.VoiceInteractionSession> mSession;

        Request(java.lang.String str, int i, com.android.internal.app.IVoiceInteractorCallback iVoiceInteractorCallback, android.service.voice.VoiceInteractionSession voiceInteractionSession, android.os.Bundle bundle) {
            this.mCallingPackage = str;
            this.mCallingUid = i;
            this.mCallback = iVoiceInteractorCallback;
            this.mSession = voiceInteractionSession.mWeakRef;
            this.mExtras = bundle;
        }

        public int getCallingUid() {
            return this.mCallingUid;
        }

        public java.lang.String getCallingPackage() {
            return this.mCallingPackage;
        }

        public android.os.Bundle getExtras() {
            return this.mExtras;
        }

        public boolean isActive() {
            android.service.voice.VoiceInteractionSession voiceInteractionSession = this.mSession.get();
            if (voiceInteractionSession == null) {
                return false;
            }
            return voiceInteractionSession.isRequestActive(this.mInterface.asBinder());
        }

        void finishRequest() {
            android.service.voice.VoiceInteractionSession voiceInteractionSession = this.mSession.get();
            if (voiceInteractionSession == null) {
                throw new java.lang.IllegalStateException("VoiceInteractionSession has been destroyed");
            }
            android.service.voice.VoiceInteractionSession.Request removeRequest = voiceInteractionSession.removeRequest(this.mInterface.asBinder());
            if (removeRequest == null) {
                throw new java.lang.IllegalStateException("Request not active: " + this);
            }
            if (removeRequest != this) {
                throw new java.lang.IllegalStateException("Current active request " + removeRequest + " not same as calling request " + this);
            }
        }

        public void cancel() {
            try {
                finishRequest();
                this.mCallback.deliverCancel(this.mInterface);
            } catch (android.os.RemoteException e) {
            }
        }

        public java.lang.String toString() {
            java.lang.StringBuilder sb = new java.lang.StringBuilder(128);
            android.util.DebugUtils.buildShortClassTag(this, sb);
            sb.append(" ");
            sb.append(this.mInterface.asBinder());
            sb.append(" pkg=");
            sb.append(this.mCallingPackage);
            sb.append(" uid=");
            android.os.UserHandle.formatUid(sb, this.mCallingUid);
            sb.append('}');
            return sb.toString();
        }

        void dump(java.lang.String str, java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
            printWriter.print(str);
            printWriter.print("mInterface=");
            printWriter.println(this.mInterface.asBinder());
            printWriter.print(str);
            printWriter.print("mCallingPackage=");
            printWriter.print(this.mCallingPackage);
            printWriter.print(" mCallingUid=");
            android.os.UserHandle.formatUid(printWriter, this.mCallingUid);
            printWriter.println();
            printWriter.print(str);
            printWriter.print("mCallback=");
            printWriter.println(this.mCallback.asBinder());
            if (this.mExtras != null) {
                printWriter.print(str);
                printWriter.print("mExtras=");
                printWriter.println(this.mExtras);
            }
        }
    }

    public static final class ConfirmationRequest extends android.service.voice.VoiceInteractionSession.Request {
        final android.app.VoiceInteractor.Prompt mPrompt;

        ConfirmationRequest(java.lang.String str, int i, com.android.internal.app.IVoiceInteractorCallback iVoiceInteractorCallback, android.service.voice.VoiceInteractionSession voiceInteractionSession, android.app.VoiceInteractor.Prompt prompt, android.os.Bundle bundle) {
            super(str, i, iVoiceInteractorCallback, voiceInteractionSession, bundle);
            this.mPrompt = prompt;
        }

        public android.app.VoiceInteractor.Prompt getVoicePrompt() {
            return this.mPrompt;
        }

        @java.lang.Deprecated
        public java.lang.CharSequence getPrompt() {
            if (this.mPrompt != null) {
                return this.mPrompt.getVoicePromptAt(0);
            }
            return null;
        }

        public void sendConfirmationResult(boolean z, android.os.Bundle bundle) {
            try {
                finishRequest();
                this.mCallback.deliverConfirmationResult(this.mInterface, z, bundle);
            } catch (android.os.RemoteException e) {
            }
        }

        @Override // android.service.voice.VoiceInteractionSession.Request
        void dump(java.lang.String str, java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
            super.dump(str, fileDescriptor, printWriter, strArr);
            printWriter.print(str);
            printWriter.print("mPrompt=");
            printWriter.println(this.mPrompt);
        }
    }

    public static final class PickOptionRequest extends android.service.voice.VoiceInteractionSession.Request {
        final android.app.VoiceInteractor.PickOptionRequest.Option[] mOptions;
        final android.app.VoiceInteractor.Prompt mPrompt;

        PickOptionRequest(java.lang.String str, int i, com.android.internal.app.IVoiceInteractorCallback iVoiceInteractorCallback, android.service.voice.VoiceInteractionSession voiceInteractionSession, android.app.VoiceInteractor.Prompt prompt, android.app.VoiceInteractor.PickOptionRequest.Option[] optionArr, android.os.Bundle bundle) {
            super(str, i, iVoiceInteractorCallback, voiceInteractionSession, bundle);
            this.mPrompt = prompt;
            this.mOptions = optionArr;
        }

        public android.app.VoiceInteractor.Prompt getVoicePrompt() {
            return this.mPrompt;
        }

        @java.lang.Deprecated
        public java.lang.CharSequence getPrompt() {
            if (this.mPrompt != null) {
                return this.mPrompt.getVoicePromptAt(0);
            }
            return null;
        }

        public android.app.VoiceInteractor.PickOptionRequest.Option[] getOptions() {
            return this.mOptions;
        }

        void sendPickOptionResult(boolean z, android.app.VoiceInteractor.PickOptionRequest.Option[] optionArr, android.os.Bundle bundle) {
            if (z) {
                try {
                    finishRequest();
                } catch (android.os.RemoteException e) {
                    return;
                }
            }
            this.mCallback.deliverPickOptionResult(this.mInterface, z, optionArr, bundle);
        }

        public void sendIntermediatePickOptionResult(android.app.VoiceInteractor.PickOptionRequest.Option[] optionArr, android.os.Bundle bundle) {
            sendPickOptionResult(false, optionArr, bundle);
        }

        public void sendPickOptionResult(android.app.VoiceInteractor.PickOptionRequest.Option[] optionArr, android.os.Bundle bundle) {
            sendPickOptionResult(true, optionArr, bundle);
        }

        @Override // android.service.voice.VoiceInteractionSession.Request
        void dump(java.lang.String str, java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
            super.dump(str, fileDescriptor, printWriter, strArr);
            printWriter.print(str);
            printWriter.print("mPrompt=");
            printWriter.println(this.mPrompt);
            if (this.mOptions != null) {
                printWriter.print(str);
                printWriter.println("Options:");
                for (int i = 0; i < this.mOptions.length; i++) {
                    android.app.VoiceInteractor.PickOptionRequest.Option option = this.mOptions[i];
                    printWriter.print(str);
                    printWriter.print("  #");
                    printWriter.print(i);
                    printWriter.println(":");
                    printWriter.print(str);
                    printWriter.print("    mLabel=");
                    printWriter.println(option.getLabel());
                    printWriter.print(str);
                    printWriter.print("    mIndex=");
                    printWriter.println(option.getIndex());
                    if (option.countSynonyms() > 0) {
                        printWriter.print(str);
                        printWriter.println("    Synonyms:");
                        for (int i2 = 0; i2 < option.countSynonyms(); i2++) {
                            printWriter.print(str);
                            printWriter.print("      #");
                            printWriter.print(i2);
                            printWriter.print(": ");
                            printWriter.println(option.getSynonymAt(i2));
                        }
                    }
                    if (option.getExtras() != null) {
                        printWriter.print(str);
                        printWriter.print("    mExtras=");
                        printWriter.println(option.getExtras());
                    }
                }
            }
        }
    }

    public static final class CompleteVoiceRequest extends android.service.voice.VoiceInteractionSession.Request {
        final android.app.VoiceInteractor.Prompt mPrompt;

        CompleteVoiceRequest(java.lang.String str, int i, com.android.internal.app.IVoiceInteractorCallback iVoiceInteractorCallback, android.service.voice.VoiceInteractionSession voiceInteractionSession, android.app.VoiceInteractor.Prompt prompt, android.os.Bundle bundle) {
            super(str, i, iVoiceInteractorCallback, voiceInteractionSession, bundle);
            this.mPrompt = prompt;
        }

        public android.app.VoiceInteractor.Prompt getVoicePrompt() {
            return this.mPrompt;
        }

        @java.lang.Deprecated
        public java.lang.CharSequence getMessage() {
            if (this.mPrompt != null) {
                return this.mPrompt.getVoicePromptAt(0);
            }
            return null;
        }

        public void sendCompleteResult(android.os.Bundle bundle) {
            try {
                finishRequest();
                this.mCallback.deliverCompleteVoiceResult(this.mInterface, bundle);
            } catch (android.os.RemoteException e) {
            }
        }

        @Override // android.service.voice.VoiceInteractionSession.Request
        void dump(java.lang.String str, java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
            super.dump(str, fileDescriptor, printWriter, strArr);
            printWriter.print(str);
            printWriter.print("mPrompt=");
            printWriter.println(this.mPrompt);
        }
    }

    public static final class AbortVoiceRequest extends android.service.voice.VoiceInteractionSession.Request {
        final android.app.VoiceInteractor.Prompt mPrompt;

        AbortVoiceRequest(java.lang.String str, int i, com.android.internal.app.IVoiceInteractorCallback iVoiceInteractorCallback, android.service.voice.VoiceInteractionSession voiceInteractionSession, android.app.VoiceInteractor.Prompt prompt, android.os.Bundle bundle) {
            super(str, i, iVoiceInteractorCallback, voiceInteractionSession, bundle);
            this.mPrompt = prompt;
        }

        public android.app.VoiceInteractor.Prompt getVoicePrompt() {
            return this.mPrompt;
        }

        @java.lang.Deprecated
        public java.lang.CharSequence getMessage() {
            if (this.mPrompt != null) {
                return this.mPrompt.getVoicePromptAt(0);
            }
            return null;
        }

        public void sendAbortResult(android.os.Bundle bundle) {
            try {
                finishRequest();
                this.mCallback.deliverAbortVoiceResult(this.mInterface, bundle);
            } catch (android.os.RemoteException e) {
            }
        }

        @Override // android.service.voice.VoiceInteractionSession.Request
        void dump(java.lang.String str, java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
            super.dump(str, fileDescriptor, printWriter, strArr);
            printWriter.print(str);
            printWriter.print("mPrompt=");
            printWriter.println(this.mPrompt);
        }
    }

    public static final class CommandRequest extends android.service.voice.VoiceInteractionSession.Request {
        final java.lang.String mCommand;

        CommandRequest(java.lang.String str, int i, com.android.internal.app.IVoiceInteractorCallback iVoiceInteractorCallback, android.service.voice.VoiceInteractionSession voiceInteractionSession, java.lang.String str2, android.os.Bundle bundle) {
            super(str, i, iVoiceInteractorCallback, voiceInteractionSession, bundle);
            this.mCommand = str2;
        }

        public java.lang.String getCommand() {
            return this.mCommand;
        }

        void sendCommandResult(boolean z, android.os.Bundle bundle) {
            if (z) {
                try {
                    finishRequest();
                } catch (android.os.RemoteException e) {
                    return;
                }
            }
            this.mCallback.deliverCommandResult(this.mInterface, z, bundle);
        }

        public void sendIntermediateResult(android.os.Bundle bundle) {
            sendCommandResult(false, bundle);
        }

        public void sendResult(android.os.Bundle bundle) {
            sendCommandResult(true, bundle);
        }

        @Override // android.service.voice.VoiceInteractionSession.Request
        void dump(java.lang.String str, java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
            super.dump(str, fileDescriptor, printWriter, strArr);
            printWriter.print(str);
            printWriter.print("mCommand=");
            printWriter.println(this.mCommand);
        }
    }

    class MyCallbacks implements com.android.internal.os.HandlerCaller.Callback, android.service.voice.VoiceInteractionWindow.Callback {
        MyCallbacks() {
        }

        @Override // com.android.internal.os.HandlerCaller.Callback
        public void executeMessage(android.os.Message message) {
            com.android.internal.os.SomeArgs someArgs = null;
            switch (message.what) {
                case 1:
                    android.service.voice.VoiceInteractionSession.this.onRequestConfirmation((android.service.voice.VoiceInteractionSession.ConfirmationRequest) message.obj);
                    break;
                case 2:
                    android.service.voice.VoiceInteractionSession.this.onRequestPickOption((android.service.voice.VoiceInteractionSession.PickOptionRequest) message.obj);
                    break;
                case 3:
                    android.service.voice.VoiceInteractionSession.this.onRequestCompleteVoice((android.service.voice.VoiceInteractionSession.CompleteVoiceRequest) message.obj);
                    break;
                case 4:
                    android.service.voice.VoiceInteractionSession.this.onRequestAbortVoice((android.service.voice.VoiceInteractionSession.AbortVoiceRequest) message.obj);
                    break;
                case 5:
                    android.service.voice.VoiceInteractionSession.this.onRequestCommand((android.service.voice.VoiceInteractionSession.CommandRequest) message.obj);
                    break;
                case 6:
                    com.android.internal.os.SomeArgs someArgs2 = (com.android.internal.os.SomeArgs) message.obj;
                    someArgs2.arg1 = android.service.voice.VoiceInteractionSession.this.onGetSupportedCommands((java.lang.String[]) someArgs2.arg1);
                    someArgs2.complete();
                    break;
                case 7:
                    android.service.voice.VoiceInteractionSession.this.onCancelRequest((android.service.voice.VoiceInteractionSession.Request) message.obj);
                    break;
                case 100:
                    android.service.voice.VoiceInteractionSession.this.onTaskStarted((android.content.Intent) message.obj, message.arg1);
                    break;
                case 101:
                    android.service.voice.VoiceInteractionSession.this.onTaskFinished((android.content.Intent) message.obj, message.arg1);
                    break;
                case 102:
                    android.service.voice.VoiceInteractionSession.this.onCloseSystemDialogs();
                    break;
                case 103:
                    android.service.voice.VoiceInteractionSession.this.doDestroy();
                    break;
                case 104:
                    someArgs = (com.android.internal.os.SomeArgs) message.obj;
                    android.service.voice.VoiceInteractionSession.this.doOnHandleAssist(someArgs.argi1, (android.os.IBinder) someArgs.arg5, (android.os.Bundle) someArgs.arg1, (android.app.assist.AssistStructure) someArgs.arg2, (java.lang.Throwable) someArgs.arg3, (android.app.assist.AssistContent) someArgs.arg4, someArgs.argi5, someArgs.argi6);
                    break;
                case 105:
                    android.service.voice.VoiceInteractionSession.this.onHandleScreenshot((android.graphics.Bitmap) message.obj);
                    break;
                case 106:
                    someArgs = (com.android.internal.os.SomeArgs) message.obj;
                    android.service.voice.VoiceInteractionSession.this.doShow((android.os.Bundle) someArgs.arg1, message.arg1, (com.android.internal.app.IVoiceInteractionSessionShowCallback) someArgs.arg2);
                    break;
                case 107:
                    android.service.voice.VoiceInteractionSession.this.doHide();
                    break;
                case 108:
                    android.service.voice.VoiceInteractionSession.this.onLockscreenShown();
                    break;
                case 109:
                    android.service.voice.VoiceInteractionSession.this.doNotifyVisibleActivityInfoChanged((android.service.voice.VisibleActivityInfo) message.obj, message.arg1);
                    break;
                case 110:
                    someArgs = (com.android.internal.os.SomeArgs) message.obj;
                    android.service.voice.VoiceInteractionSession.this.doRegisterVisibleActivityCallback((java.util.concurrent.Executor) someArgs.arg1, (android.service.voice.VoiceInteractionSession.VisibleActivityCallback) someArgs.arg2);
                    break;
                case 111:
                    android.service.voice.VoiceInteractionSession.this.doUnregisterVisibleActivityCallback((android.service.voice.VoiceInteractionSession.VisibleActivityCallback) message.obj);
                    break;
            }
            if (someArgs != null) {
                someArgs.recycle();
            }
        }

        @Override // android.service.voice.VoiceInteractionWindow.Callback
        public void onBackPressed() {
            android.service.voice.VoiceInteractionSession.this.onBackPressed();
        }
    }

    public VoiceInteractionSession(android.content.Context context) {
        this(context, new android.os.Handler());
    }

    public VoiceInteractionSession(android.content.Context context, android.os.Handler handler) {
        this.mDispatcherState = new android.view.KeyEvent.DispatcherState();
        this.mTheme = 0;
        this.mUiEnabled = true;
        this.mActiveRequests = new android.util.ArrayMap<>();
        this.mTmpInsets = new android.service.voice.VoiceInteractionSession.Insets();
        this.mWeakRef = new java.lang.ref.WeakReference<>(this);
        this.mRemoteCallbacks = new android.util.ArrayMap();
        this.mVisibleActivityCallbacks = new android.util.ArrayMap();
        this.mVisibleActivityInfos = new java.util.ArrayList();
        this.mInteractor = new com.android.internal.app.IVoiceInteractor.Stub() { // from class: android.service.voice.VoiceInteractionSession.1
            @Override // com.android.internal.app.IVoiceInteractor
            public com.android.internal.app.IVoiceInteractorRequest startConfirmation(java.lang.String str, com.android.internal.app.IVoiceInteractorCallback iVoiceInteractorCallback, android.app.VoiceInteractor.Prompt prompt, android.os.Bundle bundle) {
                android.service.voice.VoiceInteractionSession.ConfirmationRequest confirmationRequest = new android.service.voice.VoiceInteractionSession.ConfirmationRequest(str, android.os.Binder.getCallingUid(), iVoiceInteractorCallback, android.service.voice.VoiceInteractionSession.this, prompt, bundle);
                android.service.voice.VoiceInteractionSession.this.addRequest(confirmationRequest);
                android.service.voice.VoiceInteractionSession.this.mHandlerCaller.sendMessage(android.service.voice.VoiceInteractionSession.this.mHandlerCaller.obtainMessageO(1, confirmationRequest));
                return confirmationRequest.mInterface;
            }

            @Override // com.android.internal.app.IVoiceInteractor
            public com.android.internal.app.IVoiceInteractorRequest startPickOption(java.lang.String str, com.android.internal.app.IVoiceInteractorCallback iVoiceInteractorCallback, android.app.VoiceInteractor.Prompt prompt, android.app.VoiceInteractor.PickOptionRequest.Option[] optionArr, android.os.Bundle bundle) {
                android.service.voice.VoiceInteractionSession.PickOptionRequest pickOptionRequest = new android.service.voice.VoiceInteractionSession.PickOptionRequest(str, android.os.Binder.getCallingUid(), iVoiceInteractorCallback, android.service.voice.VoiceInteractionSession.this, prompt, optionArr, bundle);
                android.service.voice.VoiceInteractionSession.this.addRequest(pickOptionRequest);
                android.service.voice.VoiceInteractionSession.this.mHandlerCaller.sendMessage(android.service.voice.VoiceInteractionSession.this.mHandlerCaller.obtainMessageO(2, pickOptionRequest));
                return pickOptionRequest.mInterface;
            }

            @Override // com.android.internal.app.IVoiceInteractor
            public com.android.internal.app.IVoiceInteractorRequest startCompleteVoice(java.lang.String str, com.android.internal.app.IVoiceInteractorCallback iVoiceInteractorCallback, android.app.VoiceInteractor.Prompt prompt, android.os.Bundle bundle) {
                android.service.voice.VoiceInteractionSession.CompleteVoiceRequest completeVoiceRequest = new android.service.voice.VoiceInteractionSession.CompleteVoiceRequest(str, android.os.Binder.getCallingUid(), iVoiceInteractorCallback, android.service.voice.VoiceInteractionSession.this, prompt, bundle);
                android.service.voice.VoiceInteractionSession.this.addRequest(completeVoiceRequest);
                android.service.voice.VoiceInteractionSession.this.mHandlerCaller.sendMessage(android.service.voice.VoiceInteractionSession.this.mHandlerCaller.obtainMessageO(3, completeVoiceRequest));
                return completeVoiceRequest.mInterface;
            }

            @Override // com.android.internal.app.IVoiceInteractor
            public com.android.internal.app.IVoiceInteractorRequest startAbortVoice(java.lang.String str, com.android.internal.app.IVoiceInteractorCallback iVoiceInteractorCallback, android.app.VoiceInteractor.Prompt prompt, android.os.Bundle bundle) {
                android.service.voice.VoiceInteractionSession.AbortVoiceRequest abortVoiceRequest = new android.service.voice.VoiceInteractionSession.AbortVoiceRequest(str, android.os.Binder.getCallingUid(), iVoiceInteractorCallback, android.service.voice.VoiceInteractionSession.this, prompt, bundle);
                android.service.voice.VoiceInteractionSession.this.addRequest(abortVoiceRequest);
                android.service.voice.VoiceInteractionSession.this.mHandlerCaller.sendMessage(android.service.voice.VoiceInteractionSession.this.mHandlerCaller.obtainMessageO(4, abortVoiceRequest));
                return abortVoiceRequest.mInterface;
            }

            @Override // com.android.internal.app.IVoiceInteractor
            public com.android.internal.app.IVoiceInteractorRequest startCommand(java.lang.String str, com.android.internal.app.IVoiceInteractorCallback iVoiceInteractorCallback, java.lang.String str2, android.os.Bundle bundle) {
                android.service.voice.VoiceInteractionSession.CommandRequest commandRequest = new android.service.voice.VoiceInteractionSession.CommandRequest(str, android.os.Binder.getCallingUid(), iVoiceInteractorCallback, android.service.voice.VoiceInteractionSession.this, str2, bundle);
                android.service.voice.VoiceInteractionSession.this.addRequest(commandRequest);
                android.service.voice.VoiceInteractionSession.this.mHandlerCaller.sendMessage(android.service.voice.VoiceInteractionSession.this.mHandlerCaller.obtainMessageO(5, commandRequest));
                return commandRequest.mInterface;
            }

            @Override // com.android.internal.app.IVoiceInteractor
            public boolean[] supportsCommands(java.lang.String str, java.lang.String[] strArr) {
                com.android.internal.os.SomeArgs sendMessageAndWait = android.service.voice.VoiceInteractionSession.this.mHandlerCaller.sendMessageAndWait(android.service.voice.VoiceInteractionSession.this.mHandlerCaller.obtainMessageIOO(6, 0, strArr, null));
                if (sendMessageAndWait != null) {
                    boolean[] zArr = (boolean[]) sendMessageAndWait.arg1;
                    sendMessageAndWait.recycle();
                    return zArr;
                }
                return new boolean[strArr.length];
            }

            @Override // com.android.internal.app.IVoiceInteractor
            public void notifyDirectActionsChanged(int i, android.os.IBinder iBinder) {
                android.service.voice.VoiceInteractionSession.this.mHandlerCaller.getHandler().sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.BiConsumer() { // from class: android.service.voice.VoiceInteractionSession$1$$ExternalSyntheticLambda0
                    @Override // java.util.function.BiConsumer
                    public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                        ((android.service.voice.VoiceInteractionSession) obj).onDirectActionsInvalidated((android.service.voice.VoiceInteractionSession.ActivityId) obj2);
                    }
                }, android.service.voice.VoiceInteractionSession.this, new android.service.voice.VoiceInteractionSession.ActivityId(i, iBinder)));
            }

            @Override // com.android.internal.app.IVoiceInteractor
            public void setKillCallback(android.os.ICancellationSignal iCancellationSignal) {
                android.service.voice.VoiceInteractionSession.this.mKillCallback = iCancellationSignal;
            }
        };
        this.mSession = new android.service.voice.IVoiceInteractionSession.Stub() { // from class: android.service.voice.VoiceInteractionSession.2
            @Override // android.service.voice.IVoiceInteractionSession
            public void show(android.os.Bundle bundle, int i, com.android.internal.app.IVoiceInteractionSessionShowCallback iVoiceInteractionSessionShowCallback) {
                android.service.voice.VoiceInteractionSession.this.mHandlerCaller.sendMessage(android.service.voice.VoiceInteractionSession.this.mHandlerCaller.obtainMessageIOO(106, i, bundle, iVoiceInteractionSessionShowCallback));
            }

            @Override // android.service.voice.IVoiceInteractionSession
            public void hide() {
                android.service.voice.VoiceInteractionSession.this.mHandlerCaller.removeMessages(106);
                android.service.voice.VoiceInteractionSession.this.mHandlerCaller.sendMessage(android.service.voice.VoiceInteractionSession.this.mHandlerCaller.obtainMessage(107));
            }

            @Override // android.service.voice.IVoiceInteractionSession
            public void handleAssist(final int i, final android.os.IBinder iBinder, final android.os.Bundle bundle, final android.app.assist.AssistStructure assistStructure, final android.app.assist.AssistContent assistContent, final int i2, final int i3) {
                new java.lang.Thread("AssistStructure retriever") { // from class: android.service.voice.VoiceInteractionSession.2.1
                    @Override // java.lang.Thread, java.lang.Runnable
                    public void run() {
                        if (assistStructure != null) {
                            try {
                                assistStructure.ensureData();
                            } catch (java.lang.Throwable th) {
                                th = th;
                                android.util.Log.w(android.service.voice.VoiceInteractionSession.TAG, "Failure retrieving AssistStructure", th);
                            }
                        }
                        th = null;
                        com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
                        obtain.argi1 = i;
                        obtain.arg1 = bundle;
                        obtain.arg2 = th == null ? assistStructure : null;
                        obtain.arg3 = th;
                        obtain.arg4 = assistContent;
                        obtain.arg5 = iBinder;
                        obtain.argi5 = i2;
                        obtain.argi6 = i3;
                        android.service.voice.VoiceInteractionSession.this.mHandlerCaller.sendMessage(android.service.voice.VoiceInteractionSession.this.mHandlerCaller.obtainMessageO(104, obtain));
                    }
                }.start();
            }

            @Override // android.service.voice.IVoiceInteractionSession
            public void handleScreenshot(android.graphics.Bitmap bitmap) {
                android.service.voice.VoiceInteractionSession.this.mHandlerCaller.sendMessage(android.service.voice.VoiceInteractionSession.this.mHandlerCaller.obtainMessageO(105, bitmap));
            }

            @Override // android.service.voice.IVoiceInteractionSession
            public void taskStarted(android.content.Intent intent, int i) {
                android.service.voice.VoiceInteractionSession.this.mHandlerCaller.sendMessage(android.service.voice.VoiceInteractionSession.this.mHandlerCaller.obtainMessageIO(100, i, intent));
            }

            @Override // android.service.voice.IVoiceInteractionSession
            public void taskFinished(android.content.Intent intent, int i) {
                android.service.voice.VoiceInteractionSession.this.mHandlerCaller.sendMessage(android.service.voice.VoiceInteractionSession.this.mHandlerCaller.obtainMessageIO(101, i, intent));
            }

            @Override // android.service.voice.IVoiceInteractionSession
            public void closeSystemDialogs() {
                android.service.voice.VoiceInteractionSession.this.mHandlerCaller.sendMessage(android.service.voice.VoiceInteractionSession.this.mHandlerCaller.obtainMessage(102));
            }

            @Override // android.service.voice.IVoiceInteractionSession
            public void onLockscreenShown() {
                android.service.voice.VoiceInteractionSession.this.mHandlerCaller.sendMessage(android.service.voice.VoiceInteractionSession.this.mHandlerCaller.obtainMessage(108));
            }

            @Override // android.service.voice.IVoiceInteractionSession
            public void destroy() {
                android.service.voice.VoiceInteractionSession.this.mHandlerCaller.sendMessage(android.service.voice.VoiceInteractionSession.this.mHandlerCaller.obtainMessage(103));
            }

            @Override // android.service.voice.IVoiceInteractionSession
            public void notifyVisibleActivityInfoChanged(android.service.voice.VisibleActivityInfo visibleActivityInfo, int i) {
                android.service.voice.VoiceInteractionSession.this.mHandlerCaller.sendMessage(android.service.voice.VoiceInteractionSession.this.mHandlerCaller.obtainMessageIO(109, i, visibleActivityInfo));
            }
        };
        this.mCallbacks = new android.service.voice.VoiceInteractionSession.MyCallbacks();
        this.mInsetsComputer = new android.view.ViewTreeObserver.OnComputeInternalInsetsListener() { // from class: android.service.voice.VoiceInteractionSession.3
            @Override // android.view.ViewTreeObserver.OnComputeInternalInsetsListener
            public void onComputeInternalInsets(android.view.ViewTreeObserver.InternalInsetsInfo internalInsetsInfo) {
                android.service.voice.VoiceInteractionSession.this.onComputeInsets(android.service.voice.VoiceInteractionSession.this.mTmpInsets);
                internalInsetsInfo.contentInsets.set(android.service.voice.VoiceInteractionSession.this.mTmpInsets.contentInsets);
                internalInsetsInfo.visibleInsets.set(android.service.voice.VoiceInteractionSession.this.mTmpInsets.contentInsets);
                internalInsetsInfo.touchableRegion.set(android.service.voice.VoiceInteractionSession.this.mTmpInsets.touchableRegion);
                internalInsetsInfo.setTouchableInsets(android.service.voice.VoiceInteractionSession.this.mTmpInsets.touchableInsets);
            }
        };
        this.mHandlerCaller = new com.android.internal.os.HandlerCaller(context, handler.getLooper(), this.mCallbacks, true);
        this.mContext = createWindowContextIfNeeded(context);
    }

    public android.content.Context getContext() {
        return this.mContext;
    }

    private android.content.Context createWindowContextIfNeeded(android.content.Context context) {
        android.hardware.display.DisplayManager displayManager;
        try {
            if (!context.isUiContext() && (displayManager = (android.hardware.display.DisplayManager) context.getSystemService(android.hardware.display.DisplayManager.class)) != null) {
                return context.createWindowContext(displayManager.getDisplay(0), 2031, null);
            }
            return context;
        } catch (java.lang.RuntimeException e) {
            android.util.Log.w(TAG, "Fail to createWindowContext, Exception = " + e);
            return context;
        }
    }

    void addRequest(android.service.voice.VoiceInteractionSession.Request request) {
        synchronized (this) {
            this.mActiveRequests.put(request.mInterface.asBinder(), request);
        }
    }

    boolean isRequestActive(android.os.IBinder iBinder) {
        boolean containsKey;
        synchronized (this) {
            containsKey = this.mActiveRequests.containsKey(iBinder);
        }
        return containsKey;
    }

    android.service.voice.VoiceInteractionSession.Request removeRequest(android.os.IBinder iBinder) {
        android.service.voice.VoiceInteractionSession.Request remove;
        synchronized (this) {
            remove = this.mActiveRequests.remove(iBinder);
        }
        return remove;
    }

    void doCreate(com.android.internal.app.IVoiceInteractionManagerService iVoiceInteractionManagerService, android.os.IBinder iBinder) {
        this.mSystemService = iVoiceInteractionManagerService;
        this.mToken = iBinder;
        onCreate();
    }

    void doShow(android.os.Bundle bundle, int i, final com.android.internal.app.IVoiceInteractionSessionShowCallback iVoiceInteractionSessionShowCallback) {
        if (this.mInShowWindow) {
            android.util.Log.w(TAG, "Re-entrance in to showWindow");
            return;
        }
        try {
            this.mInShowWindow = true;
            onPrepareShow(bundle, i);
            if (!this.mWindowVisible) {
                ensureWindowAdded();
            }
            onShow(bundle, i);
            if (!this.mWindowVisible) {
                this.mWindowVisible = true;
                if (this.mUiEnabled) {
                    showWindow();
                }
            }
            if (iVoiceInteractionSessionShowCallback != null) {
                if (this.mUiEnabled) {
                    this.mRootView.invalidate();
                    this.mRootView.getViewTreeObserver().addOnPreDrawListener(new android.view.ViewTreeObserver.OnPreDrawListener() { // from class: android.service.voice.VoiceInteractionSession.4
                        @Override // android.view.ViewTreeObserver.OnPreDrawListener
                        public boolean onPreDraw() {
                            android.service.voice.VoiceInteractionSession.this.mRootView.getViewTreeObserver().removeOnPreDrawListener(this);
                            try {
                                iVoiceInteractionSessionShowCallback.onShown();
                                return true;
                            } catch (android.os.RemoteException e) {
                                android.util.Log.w(android.service.voice.VoiceInteractionSession.TAG, "Error calling onShown", e);
                                return true;
                            }
                        }
                    });
                } else {
                    try {
                        iVoiceInteractionSessionShowCallback.onShown();
                    } catch (android.os.RemoteException e) {
                        android.util.Log.w(TAG, "Error calling onShown", e);
                    }
                }
            }
        } finally {
            this.mWindowWasVisible = true;
            this.mInShowWindow = false;
        }
    }

    void doHide() {
        if (this.mWindowVisible) {
            ensureWindowHidden();
            this.mWindowVisible = false;
            onHide();
        }
    }

    void doDestroy() {
        onDestroy();
        if (this.mKillCallback != null) {
            try {
                this.mKillCallback.cancel();
            } catch (android.os.RemoteException e) {
            }
            this.mKillCallback = null;
        }
        if (this.mInitialized) {
            this.mRootView.getViewTreeObserver().removeOnComputeInternalInsetsListener(this.mInsetsComputer);
            if (this.mWindowAdded) {
                this.mWindow.dismiss();
                this.mWindowAdded = false;
            }
            this.mInitialized = false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doNotifyVisibleActivityInfoChanged(android.service.voice.VisibleActivityInfo visibleActivityInfo, int i) {
        if (this.mVisibleActivityCallbacks.isEmpty()) {
        }
        switch (i) {
            case 1:
                notifyVisibleActivityChanged(visibleActivityInfo, i);
                this.mVisibleActivityInfos.add(visibleActivityInfo);
                break;
            case 2:
                notifyVisibleActivityChanged(visibleActivityInfo, i);
                this.mVisibleActivityInfos.remove(visibleActivityInfo);
                break;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doRegisterVisibleActivityCallback(java.util.concurrent.Executor executor, final android.service.voice.VoiceInteractionSession.VisibleActivityCallback visibleActivityCallback) {
        if (this.mVisibleActivityCallbacks.containsKey(visibleActivityCallback)) {
            return;
        }
        int size = this.mVisibleActivityCallbacks.size();
        this.mVisibleActivityCallbacks.put(visibleActivityCallback, executor);
        if (size == 0) {
            try {
                this.mSystemService.startListeningVisibleActivityChanged(this.mToken);
                return;
            } catch (android.os.RemoteException e) {
                e.rethrowFromSystemServer();
                return;
            }
        }
        for (int i = 0; i < this.mVisibleActivityInfos.size(); i++) {
            final android.service.voice.VisibleActivityInfo visibleActivityInfo = this.mVisibleActivityInfos.get(i);
            executor.execute(new java.lang.Runnable() { // from class: android.service.voice.VoiceInteractionSession$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    android.service.voice.VoiceInteractionSession.VisibleActivityCallback.this.onVisible(visibleActivityInfo);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doUnregisterVisibleActivityCallback(android.service.voice.VoiceInteractionSession.VisibleActivityCallback visibleActivityCallback) {
        this.mVisibleActivityCallbacks.remove(visibleActivityCallback);
        if (this.mVisibleActivityCallbacks.size() == 0) {
            this.mVisibleActivityInfos.clear();
            try {
                this.mSystemService.stopListeningVisibleActivityChanged(this.mToken);
            } catch (android.os.RemoteException e) {
                e.rethrowFromSystemServer();
            }
        }
    }

    private void notifyVisibleActivityChanged(final android.service.voice.VisibleActivityInfo visibleActivityInfo, int i) {
        for (java.util.Map.Entry<android.service.voice.VoiceInteractionSession.VisibleActivityCallback, java.util.concurrent.Executor> entry : this.mVisibleActivityCallbacks.entrySet()) {
            final java.util.concurrent.Executor value = entry.getValue();
            final android.service.voice.VoiceInteractionSession.VisibleActivityCallback key = entry.getKey();
            switch (i) {
                case 1:
                    android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.service.voice.VoiceInteractionSession$$ExternalSyntheticLambda3
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            value.execute(new java.lang.Runnable() { // from class: android.service.voice.VoiceInteractionSession$$ExternalSyntheticLambda0
                                @Override // java.lang.Runnable
                                public final void run() {
                                    android.service.voice.VoiceInteractionSession.VisibleActivityCallback.this.onVisible(r2);
                                }
                            });
                        }
                    });
                    break;
                case 2:
                    android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.service.voice.VoiceInteractionSession$$ExternalSyntheticLambda4
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            value.execute(new java.lang.Runnable() { // from class: android.service.voice.VoiceInteractionSession$$ExternalSyntheticLambda2
                                @Override // java.lang.Runnable
                                public final void run() {
                                    android.service.voice.VoiceInteractionSession.VisibleActivityCallback.this.onInvisible(r2.getActivityId());
                                }
                            });
                        }
                    });
                    break;
            }
        }
    }

    void ensureWindowCreated() {
        if (this.mInitialized) {
            return;
        }
        if (!this.mUiEnabled) {
            throw new java.lang.IllegalStateException("setUiEnabled is false");
        }
        this.mInitialized = true;
        this.mInflater = (android.view.LayoutInflater) this.mContext.getSystemService(android.content.Context.LAYOUT_INFLATER_SERVICE);
        this.mWindow = new android.service.voice.VoiceInteractionWindow(this.mContext, TAG, this.mTheme, this.mCallbacks, this, this.mDispatcherState, 2031, 80, true);
        this.mWindow.getWindow().getAttributes().setFitInsetsTypes(0);
        this.mWindow.getWindow().addFlags(16843008);
        this.mThemeAttrs = this.mContext.obtainStyledAttributes(android.R.styleable.VoiceInteractionSession);
        this.mRootView = this.mInflater.inflate(com.android.internal.R.layout.voice_interaction_session, (android.view.ViewGroup) null);
        this.mRootView.setSystemUiVisibility(1792);
        this.mWindow.setContentView(this.mRootView);
        this.mRootView.getViewTreeObserver().addOnComputeInternalInsetsListener(this.mInsetsComputer);
        this.mContentFrame = (android.widget.FrameLayout) this.mRootView.findViewById(16908290);
        this.mWindow.getWindow().setLayout(-1, -1);
        this.mWindow.setToken(this.mToken);
    }

    void ensureWindowAdded() {
        if (this.mUiEnabled && !this.mWindowAdded) {
            this.mWindowAdded = true;
            ensureWindowCreated();
            android.view.View onCreateContentView = onCreateContentView();
            if (onCreateContentView != null) {
                setContentView(onCreateContentView);
            }
        }
    }

    void showWindow() {
        if (this.mWindow != null) {
            this.mWindow.show();
            try {
                this.mSystemService.setSessionWindowVisible(this.mToken, true);
            } catch (android.os.RemoteException e) {
                android.util.Log.w(TAG, "Failed to notify session window shown", e);
            }
        }
    }

    void ensureWindowHidden() {
        if (this.mWindow != null) {
            this.mWindow.hide();
            try {
                this.mSystemService.setSessionWindowVisible(this.mToken, false);
            } catch (android.os.RemoteException e) {
                android.util.Log.w(TAG, "Failed to notify session window hidden", e);
            }
        }
    }

    public void setDisabledShowContext(int i) {
        try {
            this.mSystemService.setDisabledShowContext(i);
        } catch (android.os.RemoteException e) {
        }
    }

    public int getDisabledShowContext() {
        try {
            return this.mSystemService.getDisabledShowContext();
        } catch (android.os.RemoteException e) {
            return 0;
        }
    }

    public int getUserDisabledShowContext() {
        try {
            return this.mSystemService.getUserDisabledShowContext();
        } catch (android.os.RemoteException e) {
            return 0;
        }
    }

    public void show(android.os.Bundle bundle, int i) {
        if (this.mToken == null) {
            throw new java.lang.IllegalStateException("Can't call before onCreate()");
        }
        try {
            this.mSystemService.showSessionFromSession(this.mToken, bundle, i, this.mContext.getAttributionTag());
        } catch (android.os.RemoteException e) {
        }
    }

    public void hide() {
        if (this.mToken == null) {
            throw new java.lang.IllegalStateException("Can't call before onCreate()");
        }
        try {
            this.mSystemService.hideSessionFromSession(this.mToken);
        } catch (android.os.RemoteException e) {
        }
    }

    public void setUiEnabled(boolean z) {
        if (this.mUiEnabled != z) {
            this.mUiEnabled = z;
            if (this.mWindowVisible) {
                if (z) {
                    ensureWindowAdded();
                    showWindow();
                } else {
                    ensureWindowHidden();
                }
            }
        }
    }

    public void setTheme(int i) {
        if (this.mWindow != null) {
            throw new java.lang.IllegalStateException("Must be called before onCreate()");
        }
        this.mTheme = i;
    }

    public void startVoiceActivity(android.content.Intent intent) {
        if (this.mToken == null) {
            throw new java.lang.IllegalStateException("Can't call before onCreate()");
        }
        try {
            intent.migrateExtraStreamToClipData(this.mContext);
            intent.prepareToLeaveProcess(this.mContext);
            android.app.Instrumentation.checkStartActivityResult(this.mSystemService.startVoiceActivity(this.mToken, intent, intent.resolveType(this.mContext.getContentResolver()), this.mContext.getAttributionTag()), intent);
        } catch (android.os.RemoteException e) {
        }
    }

    public void startAssistantActivity(android.content.Intent intent) {
        startAssistantActivity(intent, android.app.ActivityOptions.makeBasic().toBundle());
    }

    public void startAssistantActivity(android.content.Intent intent, android.os.Bundle bundle) {
        java.util.Objects.requireNonNull(bundle);
        if (this.mToken == null) {
            throw new java.lang.IllegalStateException("Can't call before onCreate()");
        }
        try {
            intent.migrateExtraStreamToClipData(this.mContext);
            intent.prepareToLeaveProcess(this.mContext);
            android.app.Instrumentation.checkStartActivityResult(this.mSystemService.startAssistantActivity(this.mToken, intent, intent.resolveType(this.mContext.getContentResolver()), this.mContext.getAttributionTag(), bundle), intent);
        } catch (android.os.RemoteException e) {
        }
    }

    public final void requestDirectActions(android.service.voice.VoiceInteractionSession.ActivityId activityId, final android.os.CancellationSignal cancellationSignal, final java.util.concurrent.Executor executor, final java.util.function.Consumer<java.util.List<android.app.DirectAction>> consumer) {
        android.os.RemoteCallback remoteCallback;
        java.util.Objects.requireNonNull(activityId);
        java.util.Objects.requireNonNull(executor);
        java.util.Objects.requireNonNull(consumer);
        if (this.mToken == null) {
            throw new java.lang.IllegalStateException("Can't call before onCreate()");
        }
        if (cancellationSignal != null) {
            cancellationSignal.throwIfCanceled();
        }
        if (cancellationSignal != null) {
            remoteCallback = new android.os.RemoteCallback(new android.os.RemoteCallback.OnResultListener() { // from class: android.service.voice.VoiceInteractionSession$$ExternalSyntheticLambda8
                @Override // android.os.RemoteCallback.OnResultListener
                public final void onResult(android.os.Bundle bundle) {
                    android.service.voice.VoiceInteractionSession.lambda$requestDirectActions$5(android.os.CancellationSignal.this, bundle);
                }
            });
        } else {
            remoteCallback = null;
        }
        try {
            this.mSystemService.requestDirectActions(this.mToken, activityId.getTaskId(), activityId.getAssistToken(), remoteCallback, new android.os.RemoteCallback(createSafeResultListener(new java.util.function.Consumer() { // from class: android.service.voice.VoiceInteractionSession$$ExternalSyntheticLambda9
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    android.service.voice.VoiceInteractionSession.lambda$requestDirectActions$7(executor, consumer, (android.os.Bundle) obj);
                }
            })));
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    static /* synthetic */ void lambda$requestDirectActions$5(android.os.CancellationSignal cancellationSignal, android.os.Bundle bundle) {
        android.os.IBinder binder;
        if (bundle != null && (binder = bundle.getBinder(android.app.VoiceInteractor.KEY_CANCELLATION_SIGNAL)) != null) {
            cancellationSignal.setRemote(android.os.ICancellationSignal.Stub.asInterface(binder));
        }
    }

    static /* synthetic */ void lambda$requestDirectActions$7(java.util.concurrent.Executor executor, final java.util.function.Consumer consumer, android.os.Bundle bundle) {
        final java.util.List emptyList;
        if (bundle == null) {
            emptyList = java.util.Collections.emptyList();
        } else {
            android.content.pm.ParceledListSlice parceledListSlice = (android.content.pm.ParceledListSlice) bundle.getParcelable(android.app.DirectAction.KEY_ACTIONS_LIST, android.content.pm.ParceledListSlice.class);
            if (parceledListSlice != null) {
                emptyList = parceledListSlice.getList();
                if (emptyList == null) {
                    emptyList = java.util.Collections.emptyList();
                }
            } else {
                emptyList = java.util.Collections.emptyList();
            }
        }
        executor.execute(new java.lang.Runnable() { // from class: android.service.voice.VoiceInteractionSession$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                consumer.accept(emptyList);
            }
        });
    }

    public void onDirectActionsInvalidated(android.service.voice.VoiceInteractionSession.ActivityId activityId) {
    }

    public final void performDirectAction(android.app.DirectAction directAction, android.os.Bundle bundle, final android.os.CancellationSignal cancellationSignal, final java.util.concurrent.Executor executor, final java.util.function.Consumer<android.os.Bundle> consumer) {
        android.os.RemoteCallback remoteCallback;
        if (this.mToken == null) {
            throw new java.lang.IllegalStateException("Can't call before onCreate()");
        }
        java.util.Objects.requireNonNull(executor);
        java.util.Objects.requireNonNull(consumer);
        if (cancellationSignal != null) {
            cancellationSignal.throwIfCanceled();
        }
        if (cancellationSignal != null) {
            remoteCallback = new android.os.RemoteCallback(createSafeResultListener(new java.util.function.Consumer() { // from class: android.service.voice.VoiceInteractionSession$$ExternalSyntheticLambda10
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    android.service.voice.VoiceInteractionSession.lambda$performDirectAction$8(android.os.CancellationSignal.this, (android.os.Bundle) obj);
                }
            }));
        } else {
            remoteCallback = null;
        }
        try {
            this.mSystemService.performDirectAction(this.mToken, directAction.getId(), bundle, directAction.getTaskId(), directAction.getActivityId(), remoteCallback, new android.os.RemoteCallback(createSafeResultListener(new java.util.function.Consumer() { // from class: android.service.voice.VoiceInteractionSession$$ExternalSyntheticLambda11
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    android.service.voice.VoiceInteractionSession.lambda$performDirectAction$11(executor, consumer, (android.os.Bundle) obj);
                }
            })));
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    static /* synthetic */ void lambda$performDirectAction$8(android.os.CancellationSignal cancellationSignal, android.os.Bundle bundle) {
        android.os.IBinder binder;
        if (bundle != null && (binder = bundle.getBinder(android.app.VoiceInteractor.KEY_CANCELLATION_SIGNAL)) != null) {
            cancellationSignal.setRemote(android.os.ICancellationSignal.Stub.asInterface(binder));
        }
    }

    static /* synthetic */ void lambda$performDirectAction$11(java.util.concurrent.Executor executor, final java.util.function.Consumer consumer, final android.os.Bundle bundle) {
        if (bundle != null) {
            executor.execute(new java.lang.Runnable() { // from class: android.service.voice.VoiceInteractionSession$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    consumer.accept(bundle);
                }
            });
        } else {
            executor.execute(new java.lang.Runnable() { // from class: android.service.voice.VoiceInteractionSession$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    consumer.accept(android.os.Bundle.EMPTY);
                }
            });
        }
    }

    public void setKeepAwake(boolean z) {
        if (this.mToken == null) {
            throw new java.lang.IllegalStateException("Can't call before onCreate()");
        }
        try {
            this.mSystemService.setKeepAwake(this.mToken, z);
        } catch (android.os.RemoteException e) {
        }
    }

    public void closeSystemDialogs() {
        if (this.mToken == null) {
            throw new java.lang.IllegalStateException("Can't call before onCreate()");
        }
        try {
            this.mSystemService.closeSystemDialogs(this.mToken);
        } catch (android.os.RemoteException e) {
        }
    }

    public android.view.LayoutInflater getLayoutInflater() {
        ensureWindowCreated();
        return this.mInflater;
    }

    public android.app.Dialog getWindow() {
        ensureWindowCreated();
        return this.mWindow;
    }

    public void finish() {
        if (this.mToken == null) {
            throw new java.lang.IllegalStateException("Can't call before onCreate()");
        }
        try {
            this.mSystemService.finish(this.mToken);
        } catch (android.os.RemoteException e) {
        }
    }

    public void onCreate() {
        doOnCreate();
    }

    private void doOnCreate() {
        this.mTheme = this.mTheme != 0 ? this.mTheme : com.android.internal.R.style.Theme_DeviceDefault_VoiceInteractionSession;
    }

    public void onPrepareShow(android.os.Bundle bundle, int i) {
    }

    public void onShow(android.os.Bundle bundle, int i) {
    }

    public void onHide() {
    }

    public void onDestroy() {
    }

    public android.view.View onCreateContentView() {
        return null;
    }

    public void setContentView(android.view.View view) {
        ensureWindowCreated();
        this.mContentFrame.removeAllViews();
        this.mContentFrame.addView(view, new android.widget.FrameLayout.LayoutParams(-1, -1));
        this.mContentFrame.requestApplyInsets();
    }

    void doOnHandleAssist(int i, android.os.IBinder iBinder, android.os.Bundle bundle, android.app.assist.AssistStructure assistStructure, java.lang.Throwable th, android.app.assist.AssistContent assistContent, int i2, int i3) {
        if (th != null) {
            onAssistStructureFailure(th);
        }
        onHandleAssist(new android.service.voice.VoiceInteractionSession.AssistState(new android.service.voice.VoiceInteractionSession.ActivityId(i, iBinder), bundle, assistStructure, assistContent, i2, i3));
    }

    public void onAssistStructureFailure(java.lang.Throwable th) {
    }

    @java.lang.Deprecated
    public void onHandleAssist(android.os.Bundle bundle, android.app.assist.AssistStructure assistStructure, android.app.assist.AssistContent assistContent) {
    }

    public void onHandleAssist(android.service.voice.VoiceInteractionSession.AssistState assistState) {
        if (assistState.getAssistData() == null && assistState.getAssistStructure() == null && assistState.getAssistContent() == null) {
            return;
        }
        if (assistState.getIndex() == 0) {
            onHandleAssist(assistState.getAssistData(), assistState.getAssistStructure(), assistState.getAssistContent());
        } else {
            onHandleAssistSecondary(assistState.getAssistData(), assistState.getAssistStructure(), assistState.getAssistContent(), assistState.getIndex(), assistState.getCount());
        }
    }

    @java.lang.Deprecated
    public void onHandleAssistSecondary(android.os.Bundle bundle, android.app.assist.AssistStructure assistStructure, android.app.assist.AssistContent assistContent, int i, int i2) {
    }

    public void onHandleScreenshot(android.graphics.Bitmap bitmap) {
    }

    @Override // android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, android.view.KeyEvent keyEvent) {
        return false;
    }

    @Override // android.view.KeyEvent.Callback
    public boolean onKeyLongPress(int i, android.view.KeyEvent keyEvent) {
        return false;
    }

    @Override // android.view.KeyEvent.Callback
    public boolean onKeyUp(int i, android.view.KeyEvent keyEvent) {
        return false;
    }

    @Override // android.view.KeyEvent.Callback
    public boolean onKeyMultiple(int i, int i2, android.view.KeyEvent keyEvent) {
        return false;
    }

    public void onBackPressed() {
        hide();
    }

    public void onCloseSystemDialogs() {
        hide();
    }

    public void onLockscreenShown() {
        hide();
    }

    @Override // android.content.ComponentCallbacks
    public void onConfigurationChanged(android.content.res.Configuration configuration) {
    }

    @Override // android.content.ComponentCallbacks
    public void onLowMemory() {
    }

    @Override // android.content.ComponentCallbacks2
    public void onTrimMemory(int i) {
    }

    public void onComputeInsets(android.service.voice.VoiceInteractionSession.Insets insets) {
        insets.contentInsets.left = 0;
        insets.contentInsets.bottom = 0;
        insets.contentInsets.right = 0;
        android.view.View decorView = getWindow().getWindow().getDecorView();
        insets.contentInsets.top = decorView.getHeight();
        insets.touchableInsets = 0;
        insets.touchableRegion.setEmpty();
    }

    public void onTaskStarted(android.content.Intent intent, int i) {
    }

    public void onTaskFinished(android.content.Intent intent, int i) {
        hide();
    }

    public boolean[] onGetSupportedCommands(java.lang.String[] strArr) {
        return new boolean[strArr.length];
    }

    public void onRequestConfirmation(android.service.voice.VoiceInteractionSession.ConfirmationRequest confirmationRequest) {
    }

    public void onRequestPickOption(android.service.voice.VoiceInteractionSession.PickOptionRequest pickOptionRequest) {
    }

    public void onRequestCompleteVoice(android.service.voice.VoiceInteractionSession.CompleteVoiceRequest completeVoiceRequest) {
    }

    public void onRequestAbortVoice(android.service.voice.VoiceInteractionSession.AbortVoiceRequest abortVoiceRequest) {
    }

    public void onRequestCommand(android.service.voice.VoiceInteractionSession.CommandRequest commandRequest) {
    }

    public void onCancelRequest(android.service.voice.VoiceInteractionSession.Request request) {
    }

    public final void registerVisibleActivityCallback(java.util.concurrent.Executor executor, android.service.voice.VoiceInteractionSession.VisibleActivityCallback visibleActivityCallback) {
        if (this.mToken == null) {
            throw new java.lang.IllegalStateException("Can't call before onCreate()");
        }
        java.util.Objects.requireNonNull(executor);
        java.util.Objects.requireNonNull(visibleActivityCallback);
        this.mHandlerCaller.sendMessage(this.mHandlerCaller.obtainMessageOO(110, executor, visibleActivityCallback));
    }

    public final void unregisterVisibleActivityCallback(android.service.voice.VoiceInteractionSession.VisibleActivityCallback visibleActivityCallback) {
        java.util.Objects.requireNonNull(visibleActivityCallback);
        this.mHandlerCaller.sendMessage(this.mHandlerCaller.obtainMessageO(111, visibleActivityCallback));
    }

    public void dump(java.lang.String str, java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        printWriter.print(str);
        printWriter.print("mToken=");
        printWriter.println(this.mToken);
        printWriter.print(str);
        printWriter.print("mTheme=#");
        printWriter.println(java.lang.Integer.toHexString(this.mTheme));
        printWriter.print(str);
        printWriter.print("mUiEnabled=");
        printWriter.println(this.mUiEnabled);
        printWriter.print(" mInitialized=");
        printWriter.println(this.mInitialized);
        printWriter.print(str);
        printWriter.print("mWindowAdded=");
        printWriter.print(this.mWindowAdded);
        printWriter.print(" mWindowVisible=");
        printWriter.println(this.mWindowVisible);
        printWriter.print(str);
        printWriter.print("mWindowWasVisible=");
        printWriter.print(this.mWindowWasVisible);
        printWriter.print(" mInShowWindow=");
        printWriter.println(this.mInShowWindow);
        if (this.mActiveRequests.size() > 0) {
            printWriter.print(str);
            printWriter.println("Active requests:");
            java.lang.String str2 = str + "    ";
            for (int i = 0; i < this.mActiveRequests.size(); i++) {
                android.service.voice.VoiceInteractionSession.Request valueAt = this.mActiveRequests.valueAt(i);
                printWriter.print(str);
                printWriter.print("  #");
                printWriter.print(i);
                printWriter.print(": ");
                printWriter.println(valueAt);
                valueAt.dump(str2, fileDescriptor, printWriter, strArr);
            }
        }
    }

    private android.service.voice.VoiceInteractionSession.SafeResultListener createSafeResultListener(java.util.function.Consumer<android.os.Bundle> consumer) {
        android.service.voice.VoiceInteractionSession.SafeResultListener safeResultListener;
        synchronized (this) {
            safeResultListener = new android.service.voice.VoiceInteractionSession.SafeResultListener(consumer, this);
            this.mRemoteCallbacks.put(safeResultListener, consumer);
        }
        return safeResultListener;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public java.util.function.Consumer<android.os.Bundle> removeSafeResultListener(android.service.voice.VoiceInteractionSession.SafeResultListener safeResultListener) {
        java.util.function.Consumer<android.os.Bundle> remove;
        synchronized (this) {
            remove = this.mRemoteCallbacks.remove(safeResultListener);
        }
        return remove;
    }

    public interface VisibleActivityCallback {
        default void onVisible(android.service.voice.VisibleActivityInfo visibleActivityInfo) {
        }

        default void onInvisible(android.service.voice.VoiceInteractionSession.ActivityId activityId) {
        }
    }

    public static final class AssistState {
        private final android.service.voice.VoiceInteractionSession.ActivityId mActivityId;
        private final android.app.assist.AssistContent mContent;
        private final int mCount;
        private final android.os.Bundle mData;
        private final int mIndex;
        private final android.app.assist.AssistStructure mStructure;

        AssistState(android.service.voice.VoiceInteractionSession.ActivityId activityId, android.os.Bundle bundle, android.app.assist.AssistStructure assistStructure, android.app.assist.AssistContent assistContent, int i, int i2) {
            this.mActivityId = activityId;
            this.mIndex = i;
            this.mCount = i2;
            this.mData = bundle;
            this.mStructure = assistStructure;
            this.mContent = assistContent;
        }

        public boolean isFocused() {
            return this.mIndex == 0;
        }

        public int getIndex() {
            return this.mIndex;
        }

        public int getCount() {
            return this.mCount;
        }

        public android.service.voice.VoiceInteractionSession.ActivityId getActivityId() {
            return this.mActivityId;
        }

        public android.os.Bundle getAssistData() {
            return this.mData;
        }

        public android.app.assist.AssistStructure getAssistStructure() {
            return this.mStructure;
        }

        public android.app.assist.AssistContent getAssistContent() {
            return this.mContent;
        }
    }

    public static class ActivityId {
        private final android.os.IBinder mAssistToken;
        private final int mTaskId;

        ActivityId(int i, android.os.IBinder iBinder) {
            this.mTaskId = i;
            this.mAssistToken = iBinder;
        }

        public int getTaskId() {
            return this.mTaskId;
        }

        public android.os.IBinder getAssistToken() {
            return this.mAssistToken;
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            android.service.voice.VoiceInteractionSession.ActivityId activityId = (android.service.voice.VoiceInteractionSession.ActivityId) obj;
            if (this.mTaskId != activityId.mTaskId) {
                return false;
            }
            if (this.mAssistToken != null) {
                return this.mAssistToken.equals(activityId.mAssistToken);
            }
            if (activityId.mAssistToken == null) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            return (this.mTaskId * 31) + (this.mAssistToken != null ? this.mAssistToken.hashCode() : 0);
        }
    }

    private static class SafeResultListener implements android.os.RemoteCallback.OnResultListener {
        private final java.lang.ref.WeakReference<android.service.voice.VoiceInteractionSession> mWeakSession;

        SafeResultListener(java.util.function.Consumer<android.os.Bundle> consumer, android.service.voice.VoiceInteractionSession voiceInteractionSession) {
            this.mWeakSession = new java.lang.ref.WeakReference<>(voiceInteractionSession);
        }

        @Override // android.os.RemoteCallback.OnResultListener
        public void onResult(android.os.Bundle bundle) {
            java.util.function.Consumer removeSafeResultListener;
            android.service.voice.VoiceInteractionSession voiceInteractionSession = this.mWeakSession.get();
            if (voiceInteractionSession != null && (removeSafeResultListener = voiceInteractionSession.removeSafeResultListener(this)) != null) {
                removeSafeResultListener.accept(bundle);
            }
        }
    }
}
