package com.android.internal.inputmethod;

/* loaded from: classes4.dex */
public interface IInputMethod extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "com.android.internal.inputmethod.IInputMethod";

    void bindInput(android.view.inputmethod.InputBinding inputBinding) throws android.os.RemoteException;

    void canStartStylusHandwriting(int i, com.android.internal.inputmethod.IConnectionlessHandwritingCallback iConnectionlessHandwritingCallback, android.view.inputmethod.CursorAnchorInfo cursorAnchorInfo, boolean z) throws android.os.RemoteException;

    void changeInputMethodSubtype(android.view.inputmethod.InputMethodSubtype inputMethodSubtype) throws android.os.RemoteException;

    void commitHandwritingDelegationTextIfAvailable() throws android.os.RemoteException;

    void createSession(android.view.InputChannel inputChannel, com.android.internal.inputmethod.IInputMethodSessionCallback iInputMethodSessionCallback) throws android.os.RemoteException;

    void discardHandwritingDelegationText() throws android.os.RemoteException;

    void finishStylusHandwriting() throws android.os.RemoteException;

    void hideSoftInput(android.os.IBinder iBinder, android.view.inputmethod.ImeTracker.Token token, int i, android.os.ResultReceiver resultReceiver) throws android.os.RemoteException;

    void initInkWindow() throws android.os.RemoteException;

    void initializeInternal(com.android.internal.inputmethod.IInputMethod.InitParams initParams) throws android.os.RemoteException;

    void onCreateInlineSuggestionsRequest(com.android.internal.inputmethod.InlineSuggestionsRequestInfo inlineSuggestionsRequestInfo, com.android.internal.inputmethod.IInlineSuggestionsRequestCallback iInlineSuggestionsRequestCallback) throws android.os.RemoteException;

    void onNavButtonFlagsChanged(int i) throws android.os.RemoteException;

    void removeStylusHandwritingWindow() throws android.os.RemoteException;

    void setSessionEnabled(com.android.internal.inputmethod.IInputMethodSession iInputMethodSession, boolean z) throws android.os.RemoteException;

    void setStylusWindowIdleTimeoutForTest(long j) throws android.os.RemoteException;

    void showSoftInput(android.os.IBinder iBinder, android.view.inputmethod.ImeTracker.Token token, int i, android.os.ResultReceiver resultReceiver) throws android.os.RemoteException;

    void startInput(com.android.internal.inputmethod.IInputMethod.StartInputParams startInputParams) throws android.os.RemoteException;

    void startStylusHandwriting(int i, android.view.InputChannel inputChannel, java.util.List<android.view.MotionEvent> list) throws android.os.RemoteException;

    void unbindInput() throws android.os.RemoteException;

    void updateEditorToolType(int i) throws android.os.RemoteException;

    public static class Default implements com.android.internal.inputmethod.IInputMethod {
        @Override // com.android.internal.inputmethod.IInputMethod
        public void initializeInternal(com.android.internal.inputmethod.IInputMethod.InitParams initParams) throws android.os.RemoteException {
        }

        @Override // com.android.internal.inputmethod.IInputMethod
        public void onCreateInlineSuggestionsRequest(com.android.internal.inputmethod.InlineSuggestionsRequestInfo inlineSuggestionsRequestInfo, com.android.internal.inputmethod.IInlineSuggestionsRequestCallback iInlineSuggestionsRequestCallback) throws android.os.RemoteException {
        }

        @Override // com.android.internal.inputmethod.IInputMethod
        public void bindInput(android.view.inputmethod.InputBinding inputBinding) throws android.os.RemoteException {
        }

        @Override // com.android.internal.inputmethod.IInputMethod
        public void unbindInput() throws android.os.RemoteException {
        }

        @Override // com.android.internal.inputmethod.IInputMethod
        public void startInput(com.android.internal.inputmethod.IInputMethod.StartInputParams startInputParams) throws android.os.RemoteException {
        }

        @Override // com.android.internal.inputmethod.IInputMethod
        public void onNavButtonFlagsChanged(int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.inputmethod.IInputMethod
        public void createSession(android.view.InputChannel inputChannel, com.android.internal.inputmethod.IInputMethodSessionCallback iInputMethodSessionCallback) throws android.os.RemoteException {
        }

        @Override // com.android.internal.inputmethod.IInputMethod
        public void setSessionEnabled(com.android.internal.inputmethod.IInputMethodSession iInputMethodSession, boolean z) throws android.os.RemoteException {
        }

        @Override // com.android.internal.inputmethod.IInputMethod
        public void showSoftInput(android.os.IBinder iBinder, android.view.inputmethod.ImeTracker.Token token, int i, android.os.ResultReceiver resultReceiver) throws android.os.RemoteException {
        }

        @Override // com.android.internal.inputmethod.IInputMethod
        public void hideSoftInput(android.os.IBinder iBinder, android.view.inputmethod.ImeTracker.Token token, int i, android.os.ResultReceiver resultReceiver) throws android.os.RemoteException {
        }

        @Override // com.android.internal.inputmethod.IInputMethod
        public void updateEditorToolType(int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.inputmethod.IInputMethod
        public void changeInputMethodSubtype(android.view.inputmethod.InputMethodSubtype inputMethodSubtype) throws android.os.RemoteException {
        }

        @Override // com.android.internal.inputmethod.IInputMethod
        public void canStartStylusHandwriting(int i, com.android.internal.inputmethod.IConnectionlessHandwritingCallback iConnectionlessHandwritingCallback, android.view.inputmethod.CursorAnchorInfo cursorAnchorInfo, boolean z) throws android.os.RemoteException {
        }

        @Override // com.android.internal.inputmethod.IInputMethod
        public void startStylusHandwriting(int i, android.view.InputChannel inputChannel, java.util.List<android.view.MotionEvent> list) throws android.os.RemoteException {
        }

        @Override // com.android.internal.inputmethod.IInputMethod
        public void commitHandwritingDelegationTextIfAvailable() throws android.os.RemoteException {
        }

        @Override // com.android.internal.inputmethod.IInputMethod
        public void discardHandwritingDelegationText() throws android.os.RemoteException {
        }

        @Override // com.android.internal.inputmethod.IInputMethod
        public void initInkWindow() throws android.os.RemoteException {
        }

        @Override // com.android.internal.inputmethod.IInputMethod
        public void finishStylusHandwriting() throws android.os.RemoteException {
        }

        @Override // com.android.internal.inputmethod.IInputMethod
        public void removeStylusHandwritingWindow() throws android.os.RemoteException {
        }

        @Override // com.android.internal.inputmethod.IInputMethod
        public void setStylusWindowIdleTimeoutForTest(long j) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements com.android.internal.inputmethod.IInputMethod {
        static final int TRANSACTION_bindInput = 3;
        static final int TRANSACTION_canStartStylusHandwriting = 13;
        static final int TRANSACTION_changeInputMethodSubtype = 12;
        static final int TRANSACTION_commitHandwritingDelegationTextIfAvailable = 15;
        static final int TRANSACTION_createSession = 7;
        static final int TRANSACTION_discardHandwritingDelegationText = 16;
        static final int TRANSACTION_finishStylusHandwriting = 18;
        static final int TRANSACTION_hideSoftInput = 10;
        static final int TRANSACTION_initInkWindow = 17;
        static final int TRANSACTION_initializeInternal = 1;
        static final int TRANSACTION_onCreateInlineSuggestionsRequest = 2;
        static final int TRANSACTION_onNavButtonFlagsChanged = 6;
        static final int TRANSACTION_removeStylusHandwritingWindow = 19;
        static final int TRANSACTION_setSessionEnabled = 8;
        static final int TRANSACTION_setStylusWindowIdleTimeoutForTest = 20;
        static final int TRANSACTION_showSoftInput = 9;
        static final int TRANSACTION_startInput = 5;
        static final int TRANSACTION_startStylusHandwriting = 14;
        static final int TRANSACTION_unbindInput = 4;
        static final int TRANSACTION_updateEditorToolType = 11;

        public Stub() {
            attachInterface(this, com.android.internal.inputmethod.IInputMethod.DESCRIPTOR);
        }

        public static com.android.internal.inputmethod.IInputMethod asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(com.android.internal.inputmethod.IInputMethod.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof com.android.internal.inputmethod.IInputMethod)) {
                return (com.android.internal.inputmethod.IInputMethod) queryLocalInterface;
            }
            return new com.android.internal.inputmethod.IInputMethod.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "initializeInternal";
                case 2:
                    return "onCreateInlineSuggestionsRequest";
                case 3:
                    return "bindInput";
                case 4:
                    return "unbindInput";
                case 5:
                    return "startInput";
                case 6:
                    return "onNavButtonFlagsChanged";
                case 7:
                    return "createSession";
                case 8:
                    return "setSessionEnabled";
                case 9:
                    return "showSoftInput";
                case 10:
                    return "hideSoftInput";
                case 11:
                    return "updateEditorToolType";
                case 12:
                    return "changeInputMethodSubtype";
                case 13:
                    return "canStartStylusHandwriting";
                case 14:
                    return "startStylusHandwriting";
                case 15:
                    return "commitHandwritingDelegationTextIfAvailable";
                case 16:
                    return "discardHandwritingDelegationText";
                case 17:
                    return "initInkWindow";
                case 18:
                    return "finishStylusHandwriting";
                case 19:
                    return "removeStylusHandwritingWindow";
                case 20:
                    return "setStylusWindowIdleTimeoutForTest";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public java.lang.String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, android.os.Parcel parcel, android.os.Parcel parcel2, int i2) throws android.os.RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(com.android.internal.inputmethod.IInputMethod.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(com.android.internal.inputmethod.IInputMethod.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    com.android.internal.inputmethod.IInputMethod.InitParams initParams = (com.android.internal.inputmethod.IInputMethod.InitParams) parcel.readTypedObject(com.android.internal.inputmethod.IInputMethod.InitParams.CREATOR);
                    parcel.enforceNoDataAvail();
                    initializeInternal(initParams);
                    return true;
                case 2:
                    com.android.internal.inputmethod.InlineSuggestionsRequestInfo inlineSuggestionsRequestInfo = (com.android.internal.inputmethod.InlineSuggestionsRequestInfo) parcel.readTypedObject(com.android.internal.inputmethod.InlineSuggestionsRequestInfo.CREATOR);
                    com.android.internal.inputmethod.IInlineSuggestionsRequestCallback asInterface = com.android.internal.inputmethod.IInlineSuggestionsRequestCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    onCreateInlineSuggestionsRequest(inlineSuggestionsRequestInfo, asInterface);
                    return true;
                case 3:
                    android.view.inputmethod.InputBinding inputBinding = (android.view.inputmethod.InputBinding) parcel.readTypedObject(android.view.inputmethod.InputBinding.CREATOR);
                    parcel.enforceNoDataAvail();
                    bindInput(inputBinding);
                    return true;
                case 4:
                    unbindInput();
                    return true;
                case 5:
                    com.android.internal.inputmethod.IInputMethod.StartInputParams startInputParams = (com.android.internal.inputmethod.IInputMethod.StartInputParams) parcel.readTypedObject(com.android.internal.inputmethod.IInputMethod.StartInputParams.CREATOR);
                    parcel.enforceNoDataAvail();
                    startInput(startInputParams);
                    return true;
                case 6:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onNavButtonFlagsChanged(readInt);
                    return true;
                case 7:
                    android.view.InputChannel inputChannel = (android.view.InputChannel) parcel.readTypedObject(android.view.InputChannel.CREATOR);
                    com.android.internal.inputmethod.IInputMethodSessionCallback asInterface2 = com.android.internal.inputmethod.IInputMethodSessionCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    createSession(inputChannel, asInterface2);
                    return true;
                case 8:
                    com.android.internal.inputmethod.IInputMethodSession asInterface3 = com.android.internal.inputmethod.IInputMethodSession.Stub.asInterface(parcel.readStrongBinder());
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setSessionEnabled(asInterface3, readBoolean);
                    return true;
                case 9:
                    android.os.IBinder readStrongBinder = parcel.readStrongBinder();
                    android.view.inputmethod.ImeTracker.Token token = (android.view.inputmethod.ImeTracker.Token) parcel.readTypedObject(android.view.inputmethod.ImeTracker.Token.CREATOR);
                    int readInt2 = parcel.readInt();
                    android.os.ResultReceiver resultReceiver = (android.os.ResultReceiver) parcel.readTypedObject(android.os.ResultReceiver.CREATOR);
                    parcel.enforceNoDataAvail();
                    showSoftInput(readStrongBinder, token, readInt2, resultReceiver);
                    return true;
                case 10:
                    android.os.IBinder readStrongBinder2 = parcel.readStrongBinder();
                    android.view.inputmethod.ImeTracker.Token token2 = (android.view.inputmethod.ImeTracker.Token) parcel.readTypedObject(android.view.inputmethod.ImeTracker.Token.CREATOR);
                    int readInt3 = parcel.readInt();
                    android.os.ResultReceiver resultReceiver2 = (android.os.ResultReceiver) parcel.readTypedObject(android.os.ResultReceiver.CREATOR);
                    parcel.enforceNoDataAvail();
                    hideSoftInput(readStrongBinder2, token2, readInt3, resultReceiver2);
                    return true;
                case 11:
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    updateEditorToolType(readInt4);
                    return true;
                case 12:
                    android.view.inputmethod.InputMethodSubtype inputMethodSubtype = (android.view.inputmethod.InputMethodSubtype) parcel.readTypedObject(android.view.inputmethod.InputMethodSubtype.CREATOR);
                    parcel.enforceNoDataAvail();
                    changeInputMethodSubtype(inputMethodSubtype);
                    return true;
                case 13:
                    int readInt5 = parcel.readInt();
                    com.android.internal.inputmethod.IConnectionlessHandwritingCallback asInterface4 = com.android.internal.inputmethod.IConnectionlessHandwritingCallback.Stub.asInterface(parcel.readStrongBinder());
                    android.view.inputmethod.CursorAnchorInfo cursorAnchorInfo = (android.view.inputmethod.CursorAnchorInfo) parcel.readTypedObject(android.view.inputmethod.CursorAnchorInfo.CREATOR);
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    canStartStylusHandwriting(readInt5, asInterface4, cursorAnchorInfo, readBoolean2);
                    return true;
                case 14:
                    int readInt6 = parcel.readInt();
                    android.view.InputChannel inputChannel2 = (android.view.InputChannel) parcel.readTypedObject(android.view.InputChannel.CREATOR);
                    java.util.ArrayList createTypedArrayList = parcel.createTypedArrayList(android.view.MotionEvent.CREATOR);
                    parcel.enforceNoDataAvail();
                    startStylusHandwriting(readInt6, inputChannel2, createTypedArrayList);
                    return true;
                case 15:
                    commitHandwritingDelegationTextIfAvailable();
                    return true;
                case 16:
                    discardHandwritingDelegationText();
                    return true;
                case 17:
                    initInkWindow();
                    return true;
                case 18:
                    finishStylusHandwriting();
                    return true;
                case 19:
                    removeStylusHandwritingWindow();
                    return true;
                case 20:
                    long readLong = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    setStylusWindowIdleTimeoutForTest(readLong);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements com.android.internal.inputmethod.IInputMethod {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return com.android.internal.inputmethod.IInputMethod.DESCRIPTOR;
            }

            @Override // com.android.internal.inputmethod.IInputMethod
            public void initializeInternal(com.android.internal.inputmethod.IInputMethod.InitParams initParams) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.inputmethod.IInputMethod.DESCRIPTOR);
                    obtain.writeTypedObject(initParams, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethod
            public void onCreateInlineSuggestionsRequest(com.android.internal.inputmethod.InlineSuggestionsRequestInfo inlineSuggestionsRequestInfo, com.android.internal.inputmethod.IInlineSuggestionsRequestCallback iInlineSuggestionsRequestCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.inputmethod.IInputMethod.DESCRIPTOR);
                    obtain.writeTypedObject(inlineSuggestionsRequestInfo, 0);
                    obtain.writeStrongInterface(iInlineSuggestionsRequestCallback);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethod
            public void bindInput(android.view.inputmethod.InputBinding inputBinding) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.inputmethod.IInputMethod.DESCRIPTOR);
                    obtain.writeTypedObject(inputBinding, 0);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethod
            public void unbindInput() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.inputmethod.IInputMethod.DESCRIPTOR);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethod
            public void startInput(com.android.internal.inputmethod.IInputMethod.StartInputParams startInputParams) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.inputmethod.IInputMethod.DESCRIPTOR);
                    obtain.writeTypedObject(startInputParams, 0);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethod
            public void onNavButtonFlagsChanged(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.inputmethod.IInputMethod.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethod
            public void createSession(android.view.InputChannel inputChannel, com.android.internal.inputmethod.IInputMethodSessionCallback iInputMethodSessionCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.inputmethod.IInputMethod.DESCRIPTOR);
                    obtain.writeTypedObject(inputChannel, 0);
                    obtain.writeStrongInterface(iInputMethodSessionCallback);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethod
            public void setSessionEnabled(com.android.internal.inputmethod.IInputMethodSession iInputMethodSession, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.inputmethod.IInputMethod.DESCRIPTOR);
                    obtain.writeStrongInterface(iInputMethodSession);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethod
            public void showSoftInput(android.os.IBinder iBinder, android.view.inputmethod.ImeTracker.Token token, int i, android.os.ResultReceiver resultReceiver) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.inputmethod.IInputMethod.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(token, 0);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(resultReceiver, 0);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethod
            public void hideSoftInput(android.os.IBinder iBinder, android.view.inputmethod.ImeTracker.Token token, int i, android.os.ResultReceiver resultReceiver) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.inputmethod.IInputMethod.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(token, 0);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(resultReceiver, 0);
                    this.mRemote.transact(10, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethod
            public void updateEditorToolType(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.inputmethod.IInputMethod.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(11, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethod
            public void changeInputMethodSubtype(android.view.inputmethod.InputMethodSubtype inputMethodSubtype) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.inputmethod.IInputMethod.DESCRIPTOR);
                    obtain.writeTypedObject(inputMethodSubtype, 0);
                    this.mRemote.transact(12, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethod
            public void canStartStylusHandwriting(int i, com.android.internal.inputmethod.IConnectionlessHandwritingCallback iConnectionlessHandwritingCallback, android.view.inputmethod.CursorAnchorInfo cursorAnchorInfo, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.inputmethod.IInputMethod.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iConnectionlessHandwritingCallback);
                    obtain.writeTypedObject(cursorAnchorInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(13, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethod
            public void startStylusHandwriting(int i, android.view.InputChannel inputChannel, java.util.List<android.view.MotionEvent> list) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.inputmethod.IInputMethod.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(inputChannel, 0);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(14, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethod
            public void commitHandwritingDelegationTextIfAvailable() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.inputmethod.IInputMethod.DESCRIPTOR);
                    this.mRemote.transact(15, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethod
            public void discardHandwritingDelegationText() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.inputmethod.IInputMethod.DESCRIPTOR);
                    this.mRemote.transact(16, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethod
            public void initInkWindow() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.inputmethod.IInputMethod.DESCRIPTOR);
                    this.mRemote.transact(17, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethod
            public void finishStylusHandwriting() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.inputmethod.IInputMethod.DESCRIPTOR);
                    this.mRemote.transact(18, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethod
            public void removeStylusHandwritingWindow() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.inputmethod.IInputMethod.DESCRIPTOR);
                    this.mRemote.transact(19, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethod
            public void setStylusWindowIdleTimeoutForTest(long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.inputmethod.IInputMethod.DESCRIPTOR);
                    obtain.writeLong(j);
                    this.mRemote.transact(20, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 19;
        }
    }

    public static class InitParams implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<com.android.internal.inputmethod.IInputMethod.InitParams> CREATOR = new android.os.Parcelable.Creator<com.android.internal.inputmethod.IInputMethod.InitParams>() { // from class: com.android.internal.inputmethod.IInputMethod.InitParams.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public com.android.internal.inputmethod.IInputMethod.InitParams createFromParcel(android.os.Parcel parcel) {
                com.android.internal.inputmethod.IInputMethod.InitParams initParams = new com.android.internal.inputmethod.IInputMethod.InitParams();
                initParams.readFromParcel(parcel);
                return initParams;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public com.android.internal.inputmethod.IInputMethod.InitParams[] newArray(int i) {
                return new com.android.internal.inputmethod.IInputMethod.InitParams[i];
            }
        };
        public int navigationBarFlags = 0;
        public com.android.internal.inputmethod.IInputMethodPrivilegedOperations privilegedOperations;
        public android.os.IBinder token;

        @Override // android.os.Parcelable
        public final void writeToParcel(android.os.Parcel parcel, int i) {
            int dataPosition = parcel.dataPosition();
            parcel.writeInt(0);
            parcel.writeStrongBinder(this.token);
            parcel.writeStrongInterface(this.privilegedOperations);
            parcel.writeInt(this.navigationBarFlags);
            int dataPosition2 = parcel.dataPosition();
            parcel.setDataPosition(dataPosition);
            parcel.writeInt(dataPosition2 - dataPosition);
            parcel.setDataPosition(dataPosition2);
        }

        public final void readFromParcel(android.os.Parcel parcel) {
            int dataPosition = parcel.dataPosition();
            int readInt = parcel.readInt();
            try {
                if (readInt < 4) {
                    throw new android.os.BadParcelableException("Parcelable too small");
                }
                if (parcel.dataPosition() - dataPosition >= readInt) {
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                    }
                    parcel.setDataPosition(dataPosition + readInt);
                    return;
                }
                this.token = parcel.readStrongBinder();
                if (parcel.dataPosition() - dataPosition >= readInt) {
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                    }
                    parcel.setDataPosition(dataPosition + readInt);
                    return;
                }
                this.privilegedOperations = com.android.internal.inputmethod.IInputMethodPrivilegedOperations.Stub.asInterface(parcel.readStrongBinder());
                if (parcel.dataPosition() - dataPosition >= readInt) {
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                    }
                    parcel.setDataPosition(dataPosition + readInt);
                } else {
                    this.navigationBarFlags = parcel.readInt();
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                    }
                    parcel.setDataPosition(dataPosition + readInt);
                }
            } catch (java.lang.Throwable th) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                throw th;
            }
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }
    }

    public static class StartInputParams implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<com.android.internal.inputmethod.IInputMethod.StartInputParams> CREATOR = new android.os.Parcelable.Creator<com.android.internal.inputmethod.IInputMethod.StartInputParams>() { // from class: com.android.internal.inputmethod.IInputMethod.StartInputParams.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public com.android.internal.inputmethod.IInputMethod.StartInputParams createFromParcel(android.os.Parcel parcel) {
                com.android.internal.inputmethod.IInputMethod.StartInputParams startInputParams = new com.android.internal.inputmethod.IInputMethod.StartInputParams();
                startInputParams.readFromParcel(parcel);
                return startInputParams;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public com.android.internal.inputmethod.IInputMethod.StartInputParams[] newArray(int i) {
                return new com.android.internal.inputmethod.IInputMethod.StartInputParams[i];
            }
        };
        public android.view.inputmethod.EditorInfo editorInfo;
        public android.window.ImeOnBackInvokedDispatcher imeDispatcher;
        public com.android.internal.inputmethod.IRemoteInputConnection remoteInputConnection;
        public android.os.IBinder startInputToken;
        public boolean restarting = false;
        public int navigationBarFlags = 0;

        @Override // android.os.Parcelable
        public final void writeToParcel(android.os.Parcel parcel, int i) {
            int dataPosition = parcel.dataPosition();
            parcel.writeInt(0);
            parcel.writeStrongBinder(this.startInputToken);
            parcel.writeStrongInterface(this.remoteInputConnection);
            parcel.writeTypedObject(this.editorInfo, i);
            parcel.writeBoolean(this.restarting);
            parcel.writeInt(this.navigationBarFlags);
            parcel.writeTypedObject(this.imeDispatcher, i);
            int dataPosition2 = parcel.dataPosition();
            parcel.setDataPosition(dataPosition);
            parcel.writeInt(dataPosition2 - dataPosition);
            parcel.setDataPosition(dataPosition2);
        }

        public final void readFromParcel(android.os.Parcel parcel) {
            int dataPosition = parcel.dataPosition();
            int readInt = parcel.readInt();
            try {
                if (readInt < 4) {
                    throw new android.os.BadParcelableException("Parcelable too small");
                }
                if (parcel.dataPosition() - dataPosition >= readInt) {
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                    }
                    parcel.setDataPosition(dataPosition + readInt);
                    return;
                }
                this.startInputToken = parcel.readStrongBinder();
                if (parcel.dataPosition() - dataPosition >= readInt) {
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                    }
                    parcel.setDataPosition(dataPosition + readInt);
                    return;
                }
                this.remoteInputConnection = com.android.internal.inputmethod.IRemoteInputConnection.Stub.asInterface(parcel.readStrongBinder());
                if (parcel.dataPosition() - dataPosition >= readInt) {
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                    }
                    parcel.setDataPosition(dataPosition + readInt);
                    return;
                }
                this.editorInfo = (android.view.inputmethod.EditorInfo) parcel.readTypedObject(android.view.inputmethod.EditorInfo.CREATOR);
                if (parcel.dataPosition() - dataPosition >= readInt) {
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                    }
                    parcel.setDataPosition(dataPosition + readInt);
                    return;
                }
                this.restarting = parcel.readBoolean();
                if (parcel.dataPosition() - dataPosition >= readInt) {
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                    }
                    parcel.setDataPosition(dataPosition + readInt);
                    return;
                }
                this.navigationBarFlags = parcel.readInt();
                if (parcel.dataPosition() - dataPosition >= readInt) {
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                    }
                    parcel.setDataPosition(dataPosition + readInt);
                } else {
                    this.imeDispatcher = (android.window.ImeOnBackInvokedDispatcher) parcel.readTypedObject(android.window.ImeOnBackInvokedDispatcher.CREATOR);
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                    }
                    parcel.setDataPosition(dataPosition + readInt);
                }
            } catch (java.lang.Throwable th) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                throw th;
            }
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return describeContents(this.editorInfo) | 0 | describeContents(this.imeDispatcher);
        }

        private int describeContents(java.lang.Object obj) {
            if (obj == null || !(obj instanceof android.os.Parcelable)) {
                return 0;
            }
            return ((android.os.Parcelable) obj).describeContents();
        }
    }
}
