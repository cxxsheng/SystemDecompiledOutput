package android.app;

/* loaded from: classes.dex */
public final class VoiceInteractor {
    static final boolean DEBUG = false;
    public static final java.lang.String KEY_CANCELLATION_SIGNAL = "key_cancellation_signal";
    static final int MSG_ABORT_VOICE_RESULT = 4;
    static final int MSG_CANCEL_RESULT = 6;
    static final int MSG_COMMAND_RESULT = 5;
    static final int MSG_COMPLETE_VOICE_RESULT = 3;
    static final int MSG_CONFIRMATION_RESULT = 1;
    static final int MSG_PICK_OPTION_RESULT = 2;
    static final android.app.VoiceInteractor.Request[] NO_REQUESTS = new android.app.VoiceInteractor.Request[0];
    static final java.lang.String TAG = "VoiceInteractor";
    android.app.Activity mActivity;
    android.content.Context mContext;
    final com.android.internal.os.HandlerCaller mHandlerCaller;
    com.android.internal.app.IVoiceInteractor mInteractor;
    boolean mRetaining;
    final com.android.internal.os.HandlerCaller.Callback mHandlerCallerCallback = new com.android.internal.os.HandlerCaller.Callback() { // from class: android.app.VoiceInteractor.1
        @Override // com.android.internal.os.HandlerCaller.Callback
        public void executeMessage(android.os.Message message) {
            com.android.internal.os.SomeArgs someArgs = (com.android.internal.os.SomeArgs) message.obj;
            switch (message.what) {
                case 1:
                    android.app.VoiceInteractor.Request pullRequest = android.app.VoiceInteractor.this.pullRequest((com.android.internal.app.IVoiceInteractorRequest) someArgs.arg1, true);
                    if (pullRequest != null) {
                        ((android.app.VoiceInteractor.ConfirmationRequest) pullRequest).onConfirmationResult(message.arg1 != 0, (android.os.Bundle) someArgs.arg2);
                        pullRequest.clear();
                        break;
                    }
                    break;
                case 2:
                    boolean z = message.arg1 != 0;
                    android.app.VoiceInteractor.Request pullRequest2 = android.app.VoiceInteractor.this.pullRequest((com.android.internal.app.IVoiceInteractorRequest) someArgs.arg1, z);
                    if (pullRequest2 != null) {
                        ((android.app.VoiceInteractor.PickOptionRequest) pullRequest2).onPickOptionResult(z, (android.app.VoiceInteractor.PickOptionRequest.Option[]) someArgs.arg2, (android.os.Bundle) someArgs.arg3);
                        if (z) {
                            pullRequest2.clear();
                            break;
                        }
                    }
                    break;
                case 3:
                    android.app.VoiceInteractor.Request pullRequest3 = android.app.VoiceInteractor.this.pullRequest((com.android.internal.app.IVoiceInteractorRequest) someArgs.arg1, true);
                    if (pullRequest3 != null) {
                        ((android.app.VoiceInteractor.CompleteVoiceRequest) pullRequest3).onCompleteResult((android.os.Bundle) someArgs.arg2);
                        pullRequest3.clear();
                        break;
                    }
                    break;
                case 4:
                    android.app.VoiceInteractor.Request pullRequest4 = android.app.VoiceInteractor.this.pullRequest((com.android.internal.app.IVoiceInteractorRequest) someArgs.arg1, true);
                    if (pullRequest4 != null) {
                        ((android.app.VoiceInteractor.AbortVoiceRequest) pullRequest4).onAbortResult((android.os.Bundle) someArgs.arg2);
                        pullRequest4.clear();
                        break;
                    }
                    break;
                case 5:
                    boolean z2 = message.arg1 != 0;
                    android.app.VoiceInteractor.Request pullRequest5 = android.app.VoiceInteractor.this.pullRequest((com.android.internal.app.IVoiceInteractorRequest) someArgs.arg1, z2);
                    if (pullRequest5 != null) {
                        ((android.app.VoiceInteractor.CommandRequest) pullRequest5).onCommandResult(message.arg1 != 0, (android.os.Bundle) someArgs.arg2);
                        if (z2) {
                            pullRequest5.clear();
                            break;
                        }
                    }
                    break;
                case 6:
                    android.app.VoiceInteractor.Request pullRequest6 = android.app.VoiceInteractor.this.pullRequest((com.android.internal.app.IVoiceInteractorRequest) someArgs.arg1, true);
                    if (pullRequest6 != null) {
                        pullRequest6.onCancel();
                        pullRequest6.clear();
                        break;
                    }
                    break;
            }
        }
    };
    final com.android.internal.app.IVoiceInteractorCallback.Stub mCallback = new com.android.internal.app.IVoiceInteractorCallback.Stub() { // from class: android.app.VoiceInteractor.2
        @Override // com.android.internal.app.IVoiceInteractorCallback
        public void deliverConfirmationResult(com.android.internal.app.IVoiceInteractorRequest iVoiceInteractorRequest, boolean z, android.os.Bundle bundle) {
            android.app.VoiceInteractor.this.mHandlerCaller.sendMessage(android.app.VoiceInteractor.this.mHandlerCaller.obtainMessageIOO(1, z ? 1 : 0, iVoiceInteractorRequest, bundle));
        }

        @Override // com.android.internal.app.IVoiceInteractorCallback
        public void deliverPickOptionResult(com.android.internal.app.IVoiceInteractorRequest iVoiceInteractorRequest, boolean z, android.app.VoiceInteractor.PickOptionRequest.Option[] optionArr, android.os.Bundle bundle) {
            android.app.VoiceInteractor.this.mHandlerCaller.sendMessage(android.app.VoiceInteractor.this.mHandlerCaller.obtainMessageIOOO(2, z ? 1 : 0, iVoiceInteractorRequest, optionArr, bundle));
        }

        @Override // com.android.internal.app.IVoiceInteractorCallback
        public void deliverCompleteVoiceResult(com.android.internal.app.IVoiceInteractorRequest iVoiceInteractorRequest, android.os.Bundle bundle) {
            android.app.VoiceInteractor.this.mHandlerCaller.sendMessage(android.app.VoiceInteractor.this.mHandlerCaller.obtainMessageOO(3, iVoiceInteractorRequest, bundle));
        }

        @Override // com.android.internal.app.IVoiceInteractorCallback
        public void deliverAbortVoiceResult(com.android.internal.app.IVoiceInteractorRequest iVoiceInteractorRequest, android.os.Bundle bundle) {
            android.app.VoiceInteractor.this.mHandlerCaller.sendMessage(android.app.VoiceInteractor.this.mHandlerCaller.obtainMessageOO(4, iVoiceInteractorRequest, bundle));
        }

        @Override // com.android.internal.app.IVoiceInteractorCallback
        public void deliverCommandResult(com.android.internal.app.IVoiceInteractorRequest iVoiceInteractorRequest, boolean z, android.os.Bundle bundle) {
            android.app.VoiceInteractor.this.mHandlerCaller.sendMessage(android.app.VoiceInteractor.this.mHandlerCaller.obtainMessageIOO(5, z ? 1 : 0, iVoiceInteractorRequest, bundle));
        }

        @Override // com.android.internal.app.IVoiceInteractorCallback
        public void deliverCancel(com.android.internal.app.IVoiceInteractorRequest iVoiceInteractorRequest) {
            android.app.VoiceInteractor.this.mHandlerCaller.sendMessage(android.app.VoiceInteractor.this.mHandlerCaller.obtainMessageOO(6, iVoiceInteractorRequest, null));
        }

        @Override // com.android.internal.app.IVoiceInteractorCallback
        public void destroy() {
            android.app.VoiceInteractor.this.mHandlerCaller.getHandler().sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new android.app.VoiceInteractor$2$$ExternalSyntheticLambda0(), android.app.VoiceInteractor.this));
        }
    };
    final android.util.ArrayMap<android.os.IBinder, android.app.VoiceInteractor.Request> mActiveRequests = new android.util.ArrayMap<>();
    final android.util.ArrayMap<java.lang.Runnable, java.util.concurrent.Executor> mOnDestroyCallbacks = new android.util.ArrayMap<>();

    public static abstract class Request {
        android.app.Activity mActivity;
        android.content.Context mContext;
        java.lang.String mName;
        com.android.internal.app.IVoiceInteractorRequest mRequestInterface;

        abstract com.android.internal.app.IVoiceInteractorRequest submit(com.android.internal.app.IVoiceInteractor iVoiceInteractor, java.lang.String str, com.android.internal.app.IVoiceInteractorCallback iVoiceInteractorCallback) throws android.os.RemoteException;

        Request() {
        }

        public java.lang.String getName() {
            return this.mName;
        }

        public void cancel() {
            if (this.mRequestInterface == null) {
                throw new java.lang.IllegalStateException("Request " + this + " is no longer active");
            }
            try {
                this.mRequestInterface.cancel();
            } catch (android.os.RemoteException e) {
                android.util.Log.w(android.app.VoiceInteractor.TAG, "Voice interactor has died", e);
            }
        }

        public android.content.Context getContext() {
            return this.mContext;
        }

        public android.app.Activity getActivity() {
            return this.mActivity;
        }

        public void onCancel() {
        }

        public void onAttached(android.app.Activity activity) {
        }

        public void onDetached() {
        }

        public java.lang.String toString() {
            java.lang.StringBuilder sb = new java.lang.StringBuilder(128);
            android.util.DebugUtils.buildShortClassTag(this, sb);
            sb.append(" ");
            sb.append(getRequestTypeName());
            sb.append(" name=");
            sb.append(this.mName);
            sb.append('}');
            return sb.toString();
        }

        void dump(java.lang.String str, java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
            printWriter.print(str);
            printWriter.print("mRequestInterface=");
            printWriter.println(this.mRequestInterface.asBinder());
            printWriter.print(str);
            printWriter.print("mActivity=");
            printWriter.println(this.mActivity);
            printWriter.print(str);
            printWriter.print("mName=");
            printWriter.println(this.mName);
        }

        java.lang.String getRequestTypeName() {
            return "Request";
        }

        void clear() {
            this.mRequestInterface = null;
            this.mContext = null;
            this.mActivity = null;
            this.mName = null;
        }
    }

    public static class ConfirmationRequest extends android.app.VoiceInteractor.Request {
        final android.os.Bundle mExtras;
        final android.app.VoiceInteractor.Prompt mPrompt;

        public ConfirmationRequest(android.app.VoiceInteractor.Prompt prompt, android.os.Bundle bundle) {
            this.mPrompt = prompt;
            this.mExtras = bundle;
        }

        public ConfirmationRequest(java.lang.CharSequence charSequence, android.os.Bundle bundle) {
            this.mPrompt = charSequence != null ? new android.app.VoiceInteractor.Prompt(charSequence) : null;
            this.mExtras = bundle;
        }

        public void onConfirmationResult(boolean z, android.os.Bundle bundle) {
        }

        @Override // android.app.VoiceInteractor.Request
        void dump(java.lang.String str, java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
            super.dump(str, fileDescriptor, printWriter, strArr);
            printWriter.print(str);
            printWriter.print("mPrompt=");
            printWriter.println(this.mPrompt);
            if (this.mExtras != null) {
                printWriter.print(str);
                printWriter.print("mExtras=");
                printWriter.println(this.mExtras);
            }
        }

        @Override // android.app.VoiceInteractor.Request
        java.lang.String getRequestTypeName() {
            return "Confirmation";
        }

        @Override // android.app.VoiceInteractor.Request
        com.android.internal.app.IVoiceInteractorRequest submit(com.android.internal.app.IVoiceInteractor iVoiceInteractor, java.lang.String str, com.android.internal.app.IVoiceInteractorCallback iVoiceInteractorCallback) throws android.os.RemoteException {
            return iVoiceInteractor.startConfirmation(str, iVoiceInteractorCallback, this.mPrompt, this.mExtras);
        }
    }

    public static class PickOptionRequest extends android.app.VoiceInteractor.Request {
        final android.os.Bundle mExtras;
        final android.app.VoiceInteractor.PickOptionRequest.Option[] mOptions;
        final android.app.VoiceInteractor.Prompt mPrompt;

        public static final class Option implements android.os.Parcelable {
            public static final android.os.Parcelable.Creator<android.app.VoiceInteractor.PickOptionRequest.Option> CREATOR = new android.os.Parcelable.Creator<android.app.VoiceInteractor.PickOptionRequest.Option>() { // from class: android.app.VoiceInteractor.PickOptionRequest.Option.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public android.app.VoiceInteractor.PickOptionRequest.Option createFromParcel(android.os.Parcel parcel) {
                    return new android.app.VoiceInteractor.PickOptionRequest.Option(parcel);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public android.app.VoiceInteractor.PickOptionRequest.Option[] newArray(int i) {
                    return new android.app.VoiceInteractor.PickOptionRequest.Option[i];
                }
            };
            android.os.Bundle mExtras;
            final int mIndex;
            final java.lang.CharSequence mLabel;
            java.util.ArrayList<java.lang.CharSequence> mSynonyms;

            public Option(java.lang.CharSequence charSequence) {
                this.mLabel = charSequence;
                this.mIndex = -1;
            }

            public Option(java.lang.CharSequence charSequence, int i) {
                this.mLabel = charSequence;
                this.mIndex = i;
            }

            public android.app.VoiceInteractor.PickOptionRequest.Option addSynonym(java.lang.CharSequence charSequence) {
                if (this.mSynonyms == null) {
                    this.mSynonyms = new java.util.ArrayList<>();
                }
                this.mSynonyms.add(charSequence);
                return this;
            }

            public java.lang.CharSequence getLabel() {
                return this.mLabel;
            }

            public int getIndex() {
                return this.mIndex;
            }

            public int countSynonyms() {
                if (this.mSynonyms != null) {
                    return this.mSynonyms.size();
                }
                return 0;
            }

            public java.lang.CharSequence getSynonymAt(int i) {
                if (this.mSynonyms != null) {
                    return this.mSynonyms.get(i);
                }
                return null;
            }

            public void setExtras(android.os.Bundle bundle) {
                this.mExtras = bundle;
            }

            public android.os.Bundle getExtras() {
                return this.mExtras;
            }

            Option(android.os.Parcel parcel) {
                this.mLabel = parcel.readCharSequence();
                this.mIndex = parcel.readInt();
                this.mSynonyms = parcel.readCharSequenceList();
                this.mExtras = parcel.readBundle();
            }

            @Override // android.os.Parcelable
            public int describeContents() {
                return 0;
            }

            @Override // android.os.Parcelable
            public void writeToParcel(android.os.Parcel parcel, int i) {
                parcel.writeCharSequence(this.mLabel);
                parcel.writeInt(this.mIndex);
                parcel.writeCharSequenceList(this.mSynonyms);
                parcel.writeBundle(this.mExtras);
            }
        }

        public PickOptionRequest(android.app.VoiceInteractor.Prompt prompt, android.app.VoiceInteractor.PickOptionRequest.Option[] optionArr, android.os.Bundle bundle) {
            this.mPrompt = prompt;
            this.mOptions = optionArr;
            this.mExtras = bundle;
        }

        public PickOptionRequest(java.lang.CharSequence charSequence, android.app.VoiceInteractor.PickOptionRequest.Option[] optionArr, android.os.Bundle bundle) {
            this.mPrompt = charSequence != null ? new android.app.VoiceInteractor.Prompt(charSequence) : null;
            this.mOptions = optionArr;
            this.mExtras = bundle;
        }

        public void onPickOptionResult(boolean z, android.app.VoiceInteractor.PickOptionRequest.Option[] optionArr, android.os.Bundle bundle) {
        }

        @Override // android.app.VoiceInteractor.Request
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
                    printWriter.println(option.mLabel);
                    printWriter.print(str);
                    printWriter.print("    mIndex=");
                    printWriter.println(option.mIndex);
                    if (option.mSynonyms != null && option.mSynonyms.size() > 0) {
                        printWriter.print(str);
                        printWriter.println("    Synonyms:");
                        for (int i2 = 0; i2 < option.mSynonyms.size(); i2++) {
                            printWriter.print(str);
                            printWriter.print("      #");
                            printWriter.print(i2);
                            printWriter.print(": ");
                            printWriter.println(option.mSynonyms.get(i2));
                        }
                    }
                    if (option.mExtras != null) {
                        printWriter.print(str);
                        printWriter.print("    mExtras=");
                        printWriter.println(option.mExtras);
                    }
                }
            }
            if (this.mExtras != null) {
                printWriter.print(str);
                printWriter.print("mExtras=");
                printWriter.println(this.mExtras);
            }
        }

        @Override // android.app.VoiceInteractor.Request
        java.lang.String getRequestTypeName() {
            return "PickOption";
        }

        @Override // android.app.VoiceInteractor.Request
        com.android.internal.app.IVoiceInteractorRequest submit(com.android.internal.app.IVoiceInteractor iVoiceInteractor, java.lang.String str, com.android.internal.app.IVoiceInteractorCallback iVoiceInteractorCallback) throws android.os.RemoteException {
            return iVoiceInteractor.startPickOption(str, iVoiceInteractorCallback, this.mPrompt, this.mOptions, this.mExtras);
        }
    }

    public static class CompleteVoiceRequest extends android.app.VoiceInteractor.Request {
        final android.os.Bundle mExtras;
        final android.app.VoiceInteractor.Prompt mPrompt;

        public CompleteVoiceRequest(android.app.VoiceInteractor.Prompt prompt, android.os.Bundle bundle) {
            this.mPrompt = prompt;
            this.mExtras = bundle;
        }

        public CompleteVoiceRequest(java.lang.CharSequence charSequence, android.os.Bundle bundle) {
            this.mPrompt = charSequence != null ? new android.app.VoiceInteractor.Prompt(charSequence) : null;
            this.mExtras = bundle;
        }

        public void onCompleteResult(android.os.Bundle bundle) {
        }

        @Override // android.app.VoiceInteractor.Request
        void dump(java.lang.String str, java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
            super.dump(str, fileDescriptor, printWriter, strArr);
            printWriter.print(str);
            printWriter.print("mPrompt=");
            printWriter.println(this.mPrompt);
            if (this.mExtras != null) {
                printWriter.print(str);
                printWriter.print("mExtras=");
                printWriter.println(this.mExtras);
            }
        }

        @Override // android.app.VoiceInteractor.Request
        java.lang.String getRequestTypeName() {
            return "CompleteVoice";
        }

        @Override // android.app.VoiceInteractor.Request
        com.android.internal.app.IVoiceInteractorRequest submit(com.android.internal.app.IVoiceInteractor iVoiceInteractor, java.lang.String str, com.android.internal.app.IVoiceInteractorCallback iVoiceInteractorCallback) throws android.os.RemoteException {
            return iVoiceInteractor.startCompleteVoice(str, iVoiceInteractorCallback, this.mPrompt, this.mExtras);
        }
    }

    public static class AbortVoiceRequest extends android.app.VoiceInteractor.Request {
        final android.os.Bundle mExtras;
        final android.app.VoiceInteractor.Prompt mPrompt;

        public AbortVoiceRequest(android.app.VoiceInteractor.Prompt prompt, android.os.Bundle bundle) {
            this.mPrompt = prompt;
            this.mExtras = bundle;
        }

        public AbortVoiceRequest(java.lang.CharSequence charSequence, android.os.Bundle bundle) {
            this.mPrompt = charSequence != null ? new android.app.VoiceInteractor.Prompt(charSequence) : null;
            this.mExtras = bundle;
        }

        public void onAbortResult(android.os.Bundle bundle) {
        }

        @Override // android.app.VoiceInteractor.Request
        void dump(java.lang.String str, java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
            super.dump(str, fileDescriptor, printWriter, strArr);
            printWriter.print(str);
            printWriter.print("mPrompt=");
            printWriter.println(this.mPrompt);
            if (this.mExtras != null) {
                printWriter.print(str);
                printWriter.print("mExtras=");
                printWriter.println(this.mExtras);
            }
        }

        @Override // android.app.VoiceInteractor.Request
        java.lang.String getRequestTypeName() {
            return "AbortVoice";
        }

        @Override // android.app.VoiceInteractor.Request
        com.android.internal.app.IVoiceInteractorRequest submit(com.android.internal.app.IVoiceInteractor iVoiceInteractor, java.lang.String str, com.android.internal.app.IVoiceInteractorCallback iVoiceInteractorCallback) throws android.os.RemoteException {
            return iVoiceInteractor.startAbortVoice(str, iVoiceInteractorCallback, this.mPrompt, this.mExtras);
        }
    }

    public static class CommandRequest extends android.app.VoiceInteractor.Request {
        final android.os.Bundle mArgs;
        final java.lang.String mCommand;

        public CommandRequest(java.lang.String str, android.os.Bundle bundle) {
            this.mCommand = str;
            this.mArgs = bundle;
        }

        public void onCommandResult(boolean z, android.os.Bundle bundle) {
        }

        @Override // android.app.VoiceInteractor.Request
        void dump(java.lang.String str, java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
            super.dump(str, fileDescriptor, printWriter, strArr);
            printWriter.print(str);
            printWriter.print("mCommand=");
            printWriter.println(this.mCommand);
            if (this.mArgs != null) {
                printWriter.print(str);
                printWriter.print("mArgs=");
                printWriter.println(this.mArgs);
            }
        }

        @Override // android.app.VoiceInteractor.Request
        java.lang.String getRequestTypeName() {
            return "Command";
        }

        @Override // android.app.VoiceInteractor.Request
        com.android.internal.app.IVoiceInteractorRequest submit(com.android.internal.app.IVoiceInteractor iVoiceInteractor, java.lang.String str, com.android.internal.app.IVoiceInteractorCallback iVoiceInteractorCallback) throws android.os.RemoteException {
            return iVoiceInteractor.startCommand(str, iVoiceInteractorCallback, this.mCommand, this.mArgs);
        }
    }

    public static class Prompt implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.app.VoiceInteractor.Prompt> CREATOR = new android.os.Parcelable.Creator<android.app.VoiceInteractor.Prompt>() { // from class: android.app.VoiceInteractor.Prompt.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.app.VoiceInteractor.Prompt createFromParcel(android.os.Parcel parcel) {
                return new android.app.VoiceInteractor.Prompt(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.app.VoiceInteractor.Prompt[] newArray(int i) {
                return new android.app.VoiceInteractor.Prompt[i];
            }
        };
        private final java.lang.CharSequence mVisualPrompt;
        private final java.lang.CharSequence[] mVoicePrompts;

        public Prompt(java.lang.CharSequence[] charSequenceArr, java.lang.CharSequence charSequence) {
            if (charSequenceArr == null) {
                throw new java.lang.NullPointerException("voicePrompts must not be null");
            }
            if (charSequenceArr.length == 0) {
                throw new java.lang.IllegalArgumentException("voicePrompts must not be empty");
            }
            if (charSequence == null) {
                throw new java.lang.NullPointerException("visualPrompt must not be null");
            }
            this.mVoicePrompts = charSequenceArr;
            this.mVisualPrompt = charSequence;
        }

        public Prompt(java.lang.CharSequence charSequence) {
            this.mVoicePrompts = new java.lang.CharSequence[]{charSequence};
            this.mVisualPrompt = charSequence;
        }

        public java.lang.CharSequence getVoicePromptAt(int i) {
            return this.mVoicePrompts[i];
        }

        public int countVoicePrompts() {
            return this.mVoicePrompts.length;
        }

        public java.lang.CharSequence getVisualPrompt() {
            return this.mVisualPrompt;
        }

        public java.lang.String toString() {
            java.lang.StringBuilder sb = new java.lang.StringBuilder(128);
            android.util.DebugUtils.buildShortClassTag(this, sb);
            if (this.mVisualPrompt != null && this.mVoicePrompts != null && this.mVoicePrompts.length == 1 && this.mVisualPrompt.equals(this.mVoicePrompts[0])) {
                sb.append(" ");
                sb.append(this.mVisualPrompt);
            } else {
                if (this.mVisualPrompt != null) {
                    sb.append(" visual=");
                    sb.append(this.mVisualPrompt);
                }
                if (this.mVoicePrompts != null) {
                    sb.append(", voice=");
                    for (int i = 0; i < this.mVoicePrompts.length; i++) {
                        if (i > 0) {
                            sb.append(" | ");
                        }
                        sb.append(this.mVoicePrompts[i]);
                    }
                }
            }
            sb.append('}');
            return sb.toString();
        }

        Prompt(android.os.Parcel parcel) {
            this.mVoicePrompts = parcel.readCharSequenceArray();
            this.mVisualPrompt = parcel.readCharSequence();
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeCharSequenceArray(this.mVoicePrompts);
            parcel.writeCharSequence(this.mVisualPrompt);
        }
    }

    VoiceInteractor(com.android.internal.app.IVoiceInteractor iVoiceInteractor, android.content.Context context, android.app.Activity activity, android.os.Looper looper) {
        this.mInteractor = iVoiceInteractor;
        this.mContext = context;
        this.mActivity = activity;
        this.mHandlerCaller = new com.android.internal.os.HandlerCaller(context, looper, this.mHandlerCallerCallback, true);
        try {
            this.mInteractor.setKillCallback(new android.app.VoiceInteractor.KillCallback(this));
        } catch (android.os.RemoteException e) {
        }
    }

    android.app.VoiceInteractor.Request pullRequest(com.android.internal.app.IVoiceInteractorRequest iVoiceInteractorRequest, boolean z) {
        android.app.VoiceInteractor.Request request;
        synchronized (this.mActiveRequests) {
            request = this.mActiveRequests.get(iVoiceInteractorRequest.asBinder());
            if (request != null && z) {
                this.mActiveRequests.remove(iVoiceInteractorRequest.asBinder());
            }
        }
        return request;
    }

    private java.util.ArrayList<android.app.VoiceInteractor.Request> makeRequestList() {
        int size = this.mActiveRequests.size();
        if (size < 1) {
            return null;
        }
        java.util.ArrayList<android.app.VoiceInteractor.Request> arrayList = new java.util.ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            arrayList.add(this.mActiveRequests.valueAt(i));
        }
        return arrayList;
    }

    void attachActivity(android.app.Activity activity) {
        this.mRetaining = false;
        if (this.mActivity == activity) {
            return;
        }
        this.mContext = activity;
        this.mActivity = activity;
        java.util.ArrayList<android.app.VoiceInteractor.Request> makeRequestList = makeRequestList();
        if (makeRequestList != null) {
            for (int i = 0; i < makeRequestList.size(); i++) {
                android.app.VoiceInteractor.Request request = makeRequestList.get(i);
                request.mContext = activity;
                request.mActivity = activity;
                request.onAttached(activity);
            }
        }
    }

    void retainInstance() {
        this.mRetaining = true;
    }

    void detachActivity() {
        java.util.ArrayList<android.app.VoiceInteractor.Request> makeRequestList = makeRequestList();
        if (makeRequestList != null) {
            for (int i = 0; i < makeRequestList.size(); i++) {
                android.app.VoiceInteractor.Request request = makeRequestList.get(i);
                request.onDetached();
                request.mActivity = null;
                request.mContext = null;
            }
        }
        if (!this.mRetaining) {
            java.util.ArrayList<android.app.VoiceInteractor.Request> makeRequestList2 = makeRequestList();
            if (makeRequestList2 != null) {
                for (int i2 = 0; i2 < makeRequestList2.size(); i2++) {
                    makeRequestList2.get(i2).cancel();
                }
            }
            this.mActiveRequests.clear();
        }
        this.mContext = null;
        this.mActivity = null;
    }

    void destroy() {
        for (int size = this.mActiveRequests.size() - 1; size >= 0; size--) {
            android.app.VoiceInteractor.Request valueAt = this.mActiveRequests.valueAt(size);
            this.mActiveRequests.removeAt(size);
            valueAt.cancel();
        }
        for (int size2 = this.mOnDestroyCallbacks.size() - 1; size2 >= 0; size2--) {
            this.mOnDestroyCallbacks.valueAt(size2).execute(this.mOnDestroyCallbacks.keyAt(size2));
            this.mOnDestroyCallbacks.removeAt(size2);
        }
        this.mInteractor = null;
        if (this.mActivity != null) {
            this.mActivity.setVoiceInteractor(null);
        }
    }

    public boolean submitRequest(android.app.VoiceInteractor.Request request) {
        return submitRequest(request, null);
    }

    public boolean submitRequest(android.app.VoiceInteractor.Request request, java.lang.String str) {
        if (isDestroyed()) {
            android.util.Log.w(TAG, "Cannot interact with a destroyed voice interactor");
            return false;
        }
        try {
            if (request.mRequestInterface != null) {
                throw new java.lang.IllegalStateException("Given " + request + " is already active");
            }
            com.android.internal.app.IVoiceInteractorRequest submit = request.submit(this.mInteractor, this.mContext.getOpPackageName(), this.mCallback);
            request.mRequestInterface = submit;
            request.mContext = this.mContext;
            request.mActivity = this.mActivity;
            request.mName = str;
            synchronized (this.mActiveRequests) {
                this.mActiveRequests.put(submit.asBinder(), request);
            }
            return true;
        } catch (android.os.RemoteException e) {
            android.util.Log.w(TAG, "Remove voice interactor service died", e);
            return false;
        }
    }

    public android.app.VoiceInteractor.Request[] getActiveRequests() {
        if (isDestroyed()) {
            android.util.Log.w(TAG, "Cannot interact with a destroyed voice interactor");
            return null;
        }
        synchronized (this.mActiveRequests) {
            int size = this.mActiveRequests.size();
            if (size <= 0) {
                return NO_REQUESTS;
            }
            android.app.VoiceInteractor.Request[] requestArr = new android.app.VoiceInteractor.Request[size];
            for (int i = 0; i < size; i++) {
                requestArr[i] = this.mActiveRequests.valueAt(i);
            }
            return requestArr;
        }
    }

    public android.app.VoiceInteractor.Request getActiveRequest(java.lang.String str) {
        int i;
        if (isDestroyed()) {
            android.util.Log.w(TAG, "Cannot interact with a destroyed voice interactor");
            return null;
        }
        synchronized (this.mActiveRequests) {
            int size = this.mActiveRequests.size();
            while (i < size) {
                android.app.VoiceInteractor.Request valueAt = this.mActiveRequests.valueAt(i);
                i = (str != valueAt.getName() && (str == null || !str.equals(valueAt.getName()))) ? i + 1 : 0;
                return valueAt;
            }
            return null;
        }
    }

    public boolean[] supportsCommands(java.lang.String[] strArr) {
        if (isDestroyed()) {
            android.util.Log.w(TAG, "Cannot interact with a destroyed voice interactor");
            return new boolean[strArr.length];
        }
        try {
            return this.mInteractor.supportsCommands(this.mContext.getOpPackageName(), strArr);
        } catch (android.os.RemoteException e) {
            throw new java.lang.RuntimeException("Voice interactor has died", e);
        }
    }

    public boolean isDestroyed() {
        return this.mInteractor == null;
    }

    public boolean registerOnDestroyedCallback(java.util.concurrent.Executor executor, java.lang.Runnable runnable) {
        java.util.Objects.requireNonNull(executor);
        java.util.Objects.requireNonNull(runnable);
        if (isDestroyed()) {
            android.util.Log.w(TAG, "Cannot interact with a destroyed voice interactor");
            return false;
        }
        this.mOnDestroyCallbacks.put(runnable, executor);
        return true;
    }

    public boolean unregisterOnDestroyedCallback(java.lang.Runnable runnable) {
        java.util.Objects.requireNonNull(runnable);
        if (!isDestroyed()) {
            return this.mOnDestroyCallbacks.remove(runnable) != null;
        }
        android.util.Log.w(TAG, "Cannot interact with a destroyed voice interactor");
        return false;
    }

    public void notifyDirectActionsChanged() {
        if (isDestroyed()) {
            android.util.Log.w(TAG, "Cannot interact with a destroyed voice interactor");
            return;
        }
        try {
            this.mInteractor.notifyDirectActionsChanged(this.mActivity.getTaskId(), this.mActivity.getAssistToken());
        } catch (android.os.RemoteException e) {
            android.util.Log.w(TAG, "Voice interactor has died", e);
        }
    }

    public java.lang.String getPackageName() {
        java.lang.String str;
        if (this.mActivity != null && this.mInteractor != null) {
            try {
                str = android.app.ActivityTaskManager.getService().getVoiceInteractorPackageName(this.mInteractor.asBinder());
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        } else {
            str = null;
        }
        return str == null ? "" : str;
    }

    void dump(java.lang.String str, java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        java.lang.String str2 = str + "    ";
        if (this.mActiveRequests.size() > 0) {
            printWriter.print(str);
            printWriter.println("Active voice requests:");
            for (int i = 0; i < this.mActiveRequests.size(); i++) {
                android.app.VoiceInteractor.Request valueAt = this.mActiveRequests.valueAt(i);
                printWriter.print(str);
                printWriter.print("  #");
                printWriter.print(i);
                printWriter.print(": ");
                printWriter.println(valueAt);
                valueAt.dump(str2, fileDescriptor, printWriter, strArr);
            }
        }
        printWriter.print(str);
        printWriter.println("VoiceInteractor misc state:");
        printWriter.print(str);
        printWriter.print("  mInteractor=");
        printWriter.println(this.mInteractor.asBinder());
        printWriter.print(str);
        printWriter.print("  mActivity=");
        printWriter.println(this.mActivity);
    }

    private static final class KillCallback extends android.os.ICancellationSignal.Stub {
        private final java.lang.ref.WeakReference<android.app.VoiceInteractor> mInteractor;

        KillCallback(android.app.VoiceInteractor voiceInteractor) {
            this.mInteractor = new java.lang.ref.WeakReference<>(voiceInteractor);
        }

        @Override // android.os.ICancellationSignal
        public void cancel() {
            android.app.VoiceInteractor voiceInteractor = this.mInteractor.get();
            if (voiceInteractor != null) {
                voiceInteractor.mHandlerCaller.getHandler().sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new android.app.VoiceInteractor$2$$ExternalSyntheticLambda0(), voiceInteractor));
            }
        }
    }
}
