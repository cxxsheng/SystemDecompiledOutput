package com.android.internal.view;

/* loaded from: classes5.dex */
public interface IInputMethodManager extends android.os.IInterface {
    boolean acceptStylusHandwritingDelegation(com.android.internal.inputmethod.IInputMethodClient iInputMethodClient, int i, java.lang.String str, java.lang.String str2, int i2) throws android.os.RemoteException;

    void acceptStylusHandwritingDelegationAsync(com.android.internal.inputmethod.IInputMethodClient iInputMethodClient, int i, java.lang.String str, java.lang.String str2, int i2, com.android.internal.inputmethod.IBooleanListener iBooleanListener) throws android.os.RemoteException;

    void addClient(com.android.internal.inputmethod.IInputMethodClient iInputMethodClient, com.android.internal.inputmethod.IRemoteInputConnection iRemoteInputConnection, int i) throws android.os.RemoteException;

    void addVirtualStylusIdForTestSession(com.android.internal.inputmethod.IInputMethodClient iInputMethodClient) throws android.os.RemoteException;

    android.view.inputmethod.InputMethodInfo getCurrentInputMethodInfoAsUser(int i) throws android.os.RemoteException;

    android.view.inputmethod.InputMethodSubtype getCurrentInputMethodSubtype(int i) throws android.os.RemoteException;

    java.util.List<android.view.inputmethod.InputMethodInfo> getEnabledInputMethodList(int i) throws android.os.RemoteException;

    java.util.List<android.view.inputmethod.InputMethodSubtype> getEnabledInputMethodSubtypeList(java.lang.String str, boolean z, int i) throws android.os.RemoteException;

    com.android.internal.inputmethod.IImeTracker getImeTrackerService() throws android.os.RemoteException;

    java.util.List<android.view.inputmethod.InputMethodInfo> getInputMethodList(int i, int i2) throws android.os.RemoteException;

    int getInputMethodWindowVisibleHeight(com.android.internal.inputmethod.IInputMethodClient iInputMethodClient) throws android.os.RemoteException;

    android.view.inputmethod.InputMethodSubtype getLastInputMethodSubtype(int i) throws android.os.RemoteException;

    boolean hideSoftInput(com.android.internal.inputmethod.IInputMethodClient iInputMethodClient, android.os.IBinder iBinder, android.view.inputmethod.ImeTracker.Token token, int i, android.os.ResultReceiver resultReceiver, int i2) throws android.os.RemoteException;

    void hideSoftInputFromServerForTest() throws android.os.RemoteException;

    boolean isImeTraceEnabled() throws android.os.RemoteException;

    boolean isInputMethodPickerShownForTest() throws android.os.RemoteException;

    boolean isStylusHandwritingAvailableAsUser(int i, boolean z) throws android.os.RemoteException;

    void prepareStylusHandwritingDelegation(com.android.internal.inputmethod.IInputMethodClient iInputMethodClient, int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    void removeImeSurface() throws android.os.RemoteException;

    void removeImeSurfaceFromWindowAsync(android.os.IBinder iBinder) throws android.os.RemoteException;

    void reportPerceptibleAsync(android.os.IBinder iBinder, boolean z) throws android.os.RemoteException;

    void setAdditionalInputMethodSubtypes(java.lang.String str, android.view.inputmethod.InputMethodSubtype[] inputMethodSubtypeArr, int i) throws android.os.RemoteException;

    void setExplicitlyEnabledInputMethodSubtypes(java.lang.String str, int[] iArr, int i) throws android.os.RemoteException;

    void setStylusWindowIdleTimeoutForTest(com.android.internal.inputmethod.IInputMethodClient iInputMethodClient, long j) throws android.os.RemoteException;

    void showInputMethodPickerFromClient(com.android.internal.inputmethod.IInputMethodClient iInputMethodClient, int i) throws android.os.RemoteException;

    void showInputMethodPickerFromSystem(int i, int i2) throws android.os.RemoteException;

    boolean showSoftInput(com.android.internal.inputmethod.IInputMethodClient iInputMethodClient, android.os.IBinder iBinder, android.view.inputmethod.ImeTracker.Token token, int i, int i2, android.os.ResultReceiver resultReceiver, int i3) throws android.os.RemoteException;

    void startConnectionlessStylusHandwriting(com.android.internal.inputmethod.IInputMethodClient iInputMethodClient, int i, android.view.inputmethod.CursorAnchorInfo cursorAnchorInfo, java.lang.String str, java.lang.String str2, com.android.internal.inputmethod.IConnectionlessHandwritingCallback iConnectionlessHandwritingCallback) throws android.os.RemoteException;

    void startImeTrace() throws android.os.RemoteException;

    com.android.internal.inputmethod.InputBindResult startInputOrWindowGainedFocus(int i, com.android.internal.inputmethod.IInputMethodClient iInputMethodClient, android.os.IBinder iBinder, int i2, int i3, int i4, android.view.inputmethod.EditorInfo editorInfo, com.android.internal.inputmethod.IRemoteInputConnection iRemoteInputConnection, com.android.internal.inputmethod.IRemoteAccessibilityInputConnection iRemoteAccessibilityInputConnection, int i5, int i6, android.window.ImeOnBackInvokedDispatcher imeOnBackInvokedDispatcher) throws android.os.RemoteException;

    void startInputOrWindowGainedFocusAsync(int i, com.android.internal.inputmethod.IInputMethodClient iInputMethodClient, android.os.IBinder iBinder, int i2, int i3, int i4, android.view.inputmethod.EditorInfo editorInfo, com.android.internal.inputmethod.IRemoteInputConnection iRemoteInputConnection, com.android.internal.inputmethod.IRemoteAccessibilityInputConnection iRemoteAccessibilityInputConnection, int i5, int i6, android.window.ImeOnBackInvokedDispatcher imeOnBackInvokedDispatcher, int i7) throws android.os.RemoteException;

    void startProtoDump(byte[] bArr, int i, java.lang.String str) throws android.os.RemoteException;

    void startStylusHandwriting(com.android.internal.inputmethod.IInputMethodClient iInputMethodClient) throws android.os.RemoteException;

    void stopImeTrace() throws android.os.RemoteException;

    public static class Default implements com.android.internal.view.IInputMethodManager {
        @Override // com.android.internal.view.IInputMethodManager
        public void addClient(com.android.internal.inputmethod.IInputMethodClient iInputMethodClient, com.android.internal.inputmethod.IRemoteInputConnection iRemoteInputConnection, int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.view.IInputMethodManager
        public android.view.inputmethod.InputMethodInfo getCurrentInputMethodInfoAsUser(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.view.IInputMethodManager
        public java.util.List<android.view.inputmethod.InputMethodInfo> getInputMethodList(int i, int i2) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.view.IInputMethodManager
        public java.util.List<android.view.inputmethod.InputMethodInfo> getEnabledInputMethodList(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.view.IInputMethodManager
        public java.util.List<android.view.inputmethod.InputMethodSubtype> getEnabledInputMethodSubtypeList(java.lang.String str, boolean z, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.view.IInputMethodManager
        public android.view.inputmethod.InputMethodSubtype getLastInputMethodSubtype(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.view.IInputMethodManager
        public boolean showSoftInput(com.android.internal.inputmethod.IInputMethodClient iInputMethodClient, android.os.IBinder iBinder, android.view.inputmethod.ImeTracker.Token token, int i, int i2, android.os.ResultReceiver resultReceiver, int i3) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.view.IInputMethodManager
        public boolean hideSoftInput(com.android.internal.inputmethod.IInputMethodClient iInputMethodClient, android.os.IBinder iBinder, android.view.inputmethod.ImeTracker.Token token, int i, android.os.ResultReceiver resultReceiver, int i2) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.view.IInputMethodManager
        public void hideSoftInputFromServerForTest() throws android.os.RemoteException {
        }

        @Override // com.android.internal.view.IInputMethodManager
        public com.android.internal.inputmethod.InputBindResult startInputOrWindowGainedFocus(int i, com.android.internal.inputmethod.IInputMethodClient iInputMethodClient, android.os.IBinder iBinder, int i2, int i3, int i4, android.view.inputmethod.EditorInfo editorInfo, com.android.internal.inputmethod.IRemoteInputConnection iRemoteInputConnection, com.android.internal.inputmethod.IRemoteAccessibilityInputConnection iRemoteAccessibilityInputConnection, int i5, int i6, android.window.ImeOnBackInvokedDispatcher imeOnBackInvokedDispatcher) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.view.IInputMethodManager
        public void startInputOrWindowGainedFocusAsync(int i, com.android.internal.inputmethod.IInputMethodClient iInputMethodClient, android.os.IBinder iBinder, int i2, int i3, int i4, android.view.inputmethod.EditorInfo editorInfo, com.android.internal.inputmethod.IRemoteInputConnection iRemoteInputConnection, com.android.internal.inputmethod.IRemoteAccessibilityInputConnection iRemoteAccessibilityInputConnection, int i5, int i6, android.window.ImeOnBackInvokedDispatcher imeOnBackInvokedDispatcher, int i7) throws android.os.RemoteException {
        }

        @Override // com.android.internal.view.IInputMethodManager
        public void showInputMethodPickerFromClient(com.android.internal.inputmethod.IInputMethodClient iInputMethodClient, int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.view.IInputMethodManager
        public void showInputMethodPickerFromSystem(int i, int i2) throws android.os.RemoteException {
        }

        @Override // com.android.internal.view.IInputMethodManager
        public boolean isInputMethodPickerShownForTest() throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.view.IInputMethodManager
        public android.view.inputmethod.InputMethodSubtype getCurrentInputMethodSubtype(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.view.IInputMethodManager
        public void setAdditionalInputMethodSubtypes(java.lang.String str, android.view.inputmethod.InputMethodSubtype[] inputMethodSubtypeArr, int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.view.IInputMethodManager
        public void setExplicitlyEnabledInputMethodSubtypes(java.lang.String str, int[] iArr, int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.view.IInputMethodManager
        public int getInputMethodWindowVisibleHeight(com.android.internal.inputmethod.IInputMethodClient iInputMethodClient) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.view.IInputMethodManager
        public void reportPerceptibleAsync(android.os.IBinder iBinder, boolean z) throws android.os.RemoteException {
        }

        @Override // com.android.internal.view.IInputMethodManager
        public void removeImeSurface() throws android.os.RemoteException {
        }

        @Override // com.android.internal.view.IInputMethodManager
        public void removeImeSurfaceFromWindowAsync(android.os.IBinder iBinder) throws android.os.RemoteException {
        }

        @Override // com.android.internal.view.IInputMethodManager
        public void startProtoDump(byte[] bArr, int i, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // com.android.internal.view.IInputMethodManager
        public boolean isImeTraceEnabled() throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.view.IInputMethodManager
        public void startImeTrace() throws android.os.RemoteException {
        }

        @Override // com.android.internal.view.IInputMethodManager
        public void stopImeTrace() throws android.os.RemoteException {
        }

        @Override // com.android.internal.view.IInputMethodManager
        public void startStylusHandwriting(com.android.internal.inputmethod.IInputMethodClient iInputMethodClient) throws android.os.RemoteException {
        }

        @Override // com.android.internal.view.IInputMethodManager
        public void startConnectionlessStylusHandwriting(com.android.internal.inputmethod.IInputMethodClient iInputMethodClient, int i, android.view.inputmethod.CursorAnchorInfo cursorAnchorInfo, java.lang.String str, java.lang.String str2, com.android.internal.inputmethod.IConnectionlessHandwritingCallback iConnectionlessHandwritingCallback) throws android.os.RemoteException {
        }

        @Override // com.android.internal.view.IInputMethodManager
        public void prepareStylusHandwritingDelegation(com.android.internal.inputmethod.IInputMethodClient iInputMethodClient, int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
        }

        @Override // com.android.internal.view.IInputMethodManager
        public boolean acceptStylusHandwritingDelegation(com.android.internal.inputmethod.IInputMethodClient iInputMethodClient, int i, java.lang.String str, java.lang.String str2, int i2) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.view.IInputMethodManager
        public void acceptStylusHandwritingDelegationAsync(com.android.internal.inputmethod.IInputMethodClient iInputMethodClient, int i, java.lang.String str, java.lang.String str2, int i2, com.android.internal.inputmethod.IBooleanListener iBooleanListener) throws android.os.RemoteException {
        }

        @Override // com.android.internal.view.IInputMethodManager
        public boolean isStylusHandwritingAvailableAsUser(int i, boolean z) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.view.IInputMethodManager
        public void addVirtualStylusIdForTestSession(com.android.internal.inputmethod.IInputMethodClient iInputMethodClient) throws android.os.RemoteException {
        }

        @Override // com.android.internal.view.IInputMethodManager
        public void setStylusWindowIdleTimeoutForTest(com.android.internal.inputmethod.IInputMethodClient iInputMethodClient, long j) throws android.os.RemoteException {
        }

        @Override // com.android.internal.view.IInputMethodManager
        public com.android.internal.inputmethod.IImeTracker getImeTrackerService() throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements com.android.internal.view.IInputMethodManager {
        public static final java.lang.String DESCRIPTOR = "com.android.internal.view.IInputMethodManager";
        static final int TRANSACTION_acceptStylusHandwritingDelegation = 29;
        static final int TRANSACTION_acceptStylusHandwritingDelegationAsync = 30;
        static final int TRANSACTION_addClient = 1;
        static final int TRANSACTION_addVirtualStylusIdForTestSession = 32;
        static final int TRANSACTION_getCurrentInputMethodInfoAsUser = 2;
        static final int TRANSACTION_getCurrentInputMethodSubtype = 15;
        static final int TRANSACTION_getEnabledInputMethodList = 4;
        static final int TRANSACTION_getEnabledInputMethodSubtypeList = 5;
        static final int TRANSACTION_getImeTrackerService = 34;
        static final int TRANSACTION_getInputMethodList = 3;
        static final int TRANSACTION_getInputMethodWindowVisibleHeight = 18;
        static final int TRANSACTION_getLastInputMethodSubtype = 6;
        static final int TRANSACTION_hideSoftInput = 8;
        static final int TRANSACTION_hideSoftInputFromServerForTest = 9;
        static final int TRANSACTION_isImeTraceEnabled = 23;
        static final int TRANSACTION_isInputMethodPickerShownForTest = 14;
        static final int TRANSACTION_isStylusHandwritingAvailableAsUser = 31;
        static final int TRANSACTION_prepareStylusHandwritingDelegation = 28;
        static final int TRANSACTION_removeImeSurface = 20;
        static final int TRANSACTION_removeImeSurfaceFromWindowAsync = 21;
        static final int TRANSACTION_reportPerceptibleAsync = 19;
        static final int TRANSACTION_setAdditionalInputMethodSubtypes = 16;
        static final int TRANSACTION_setExplicitlyEnabledInputMethodSubtypes = 17;
        static final int TRANSACTION_setStylusWindowIdleTimeoutForTest = 33;
        static final int TRANSACTION_showInputMethodPickerFromClient = 12;
        static final int TRANSACTION_showInputMethodPickerFromSystem = 13;
        static final int TRANSACTION_showSoftInput = 7;
        static final int TRANSACTION_startConnectionlessStylusHandwriting = 27;
        static final int TRANSACTION_startImeTrace = 24;
        static final int TRANSACTION_startInputOrWindowGainedFocus = 10;
        static final int TRANSACTION_startInputOrWindowGainedFocusAsync = 11;
        static final int TRANSACTION_startProtoDump = 22;
        static final int TRANSACTION_startStylusHandwriting = 26;
        static final int TRANSACTION_stopImeTrace = 25;
        private final android.os.PermissionEnforcer mEnforcer;

        public Stub(android.os.PermissionEnforcer permissionEnforcer) {
            attachInterface(this, DESCRIPTOR);
            if (permissionEnforcer == null) {
                throw new java.lang.IllegalArgumentException("enforcer cannot be null");
            }
            this.mEnforcer = permissionEnforcer;
        }

        @java.lang.Deprecated
        public Stub() {
            this(android.os.PermissionEnforcer.fromContext(android.app.ActivityThread.currentActivityThread().getSystemContext()));
        }

        public static com.android.internal.view.IInputMethodManager asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof com.android.internal.view.IInputMethodManager)) {
                return (com.android.internal.view.IInputMethodManager) queryLocalInterface;
            }
            return new com.android.internal.view.IInputMethodManager.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "addClient";
                case 2:
                    return "getCurrentInputMethodInfoAsUser";
                case 3:
                    return "getInputMethodList";
                case 4:
                    return "getEnabledInputMethodList";
                case 5:
                    return "getEnabledInputMethodSubtypeList";
                case 6:
                    return "getLastInputMethodSubtype";
                case 7:
                    return "showSoftInput";
                case 8:
                    return "hideSoftInput";
                case 9:
                    return "hideSoftInputFromServerForTest";
                case 10:
                    return "startInputOrWindowGainedFocus";
                case 11:
                    return "startInputOrWindowGainedFocusAsync";
                case 12:
                    return "showInputMethodPickerFromClient";
                case 13:
                    return "showInputMethodPickerFromSystem";
                case 14:
                    return "isInputMethodPickerShownForTest";
                case 15:
                    return "getCurrentInputMethodSubtype";
                case 16:
                    return "setAdditionalInputMethodSubtypes";
                case 17:
                    return "setExplicitlyEnabledInputMethodSubtypes";
                case 18:
                    return "getInputMethodWindowVisibleHeight";
                case 19:
                    return "reportPerceptibleAsync";
                case 20:
                    return "removeImeSurface";
                case 21:
                    return "removeImeSurfaceFromWindowAsync";
                case 22:
                    return "startProtoDump";
                case 23:
                    return "isImeTraceEnabled";
                case 24:
                    return "startImeTrace";
                case 25:
                    return "stopImeTrace";
                case 26:
                    return "startStylusHandwriting";
                case 27:
                    return "startConnectionlessStylusHandwriting";
                case 28:
                    return "prepareStylusHandwritingDelegation";
                case 29:
                    return "acceptStylusHandwritingDelegation";
                case 30:
                    return "acceptStylusHandwritingDelegationAsync";
                case 31:
                    return "isStylusHandwritingAvailableAsUser";
                case 32:
                    return "addVirtualStylusIdForTestSession";
                case 33:
                    return "setStylusWindowIdleTimeoutForTest";
                case 34:
                    return "getImeTrackerService";
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
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    com.android.internal.inputmethod.IInputMethodClient asInterface = com.android.internal.inputmethod.IInputMethodClient.Stub.asInterface(parcel.readStrongBinder());
                    com.android.internal.inputmethod.IRemoteInputConnection asInterface2 = com.android.internal.inputmethod.IRemoteInputConnection.Stub.asInterface(parcel.readStrongBinder());
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    addClient(asInterface, asInterface2, readInt);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.view.inputmethod.InputMethodInfo currentInputMethodInfoAsUser = getCurrentInputMethodInfoAsUser(readInt2);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(currentInputMethodInfoAsUser, 1);
                    return true;
                case 3:
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.util.List<android.view.inputmethod.InputMethodInfo> inputMethodList = getInputMethodList(readInt3, readInt4);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(inputMethodList, 1);
                    return true;
                case 4:
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.util.List<android.view.inputmethod.InputMethodInfo> enabledInputMethodList = getEnabledInputMethodList(readInt5);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(enabledInputMethodList, 1);
                    return true;
                case 5:
                    java.lang.String readString = parcel.readString();
                    boolean readBoolean = parcel.readBoolean();
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.util.List<android.view.inputmethod.InputMethodSubtype> enabledInputMethodSubtypeList = getEnabledInputMethodSubtypeList(readString, readBoolean, readInt6);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(enabledInputMethodSubtypeList, 1);
                    return true;
                case 6:
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.view.inputmethod.InputMethodSubtype lastInputMethodSubtype = getLastInputMethodSubtype(readInt7);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(lastInputMethodSubtype, 1);
                    return true;
                case 7:
                    com.android.internal.inputmethod.IInputMethodClient asInterface3 = com.android.internal.inputmethod.IInputMethodClient.Stub.asInterface(parcel.readStrongBinder());
                    android.os.IBinder readStrongBinder = parcel.readStrongBinder();
                    android.view.inputmethod.ImeTracker.Token token = (android.view.inputmethod.ImeTracker.Token) parcel.readTypedObject(android.view.inputmethod.ImeTracker.Token.CREATOR);
                    int readInt8 = parcel.readInt();
                    int readInt9 = parcel.readInt();
                    android.os.ResultReceiver resultReceiver = (android.os.ResultReceiver) parcel.readTypedObject(android.os.ResultReceiver.CREATOR);
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean showSoftInput = showSoftInput(asInterface3, readStrongBinder, token, readInt8, readInt9, resultReceiver, readInt10);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(showSoftInput);
                    return true;
                case 8:
                    com.android.internal.inputmethod.IInputMethodClient asInterface4 = com.android.internal.inputmethod.IInputMethodClient.Stub.asInterface(parcel.readStrongBinder());
                    android.os.IBinder readStrongBinder2 = parcel.readStrongBinder();
                    android.view.inputmethod.ImeTracker.Token token2 = (android.view.inputmethod.ImeTracker.Token) parcel.readTypedObject(android.view.inputmethod.ImeTracker.Token.CREATOR);
                    int readInt11 = parcel.readInt();
                    android.os.ResultReceiver resultReceiver2 = (android.os.ResultReceiver) parcel.readTypedObject(android.os.ResultReceiver.CREATOR);
                    int readInt12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean hideSoftInput = hideSoftInput(asInterface4, readStrongBinder2, token2, readInt11, resultReceiver2, readInt12);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hideSoftInput);
                    return true;
                case 9:
                    hideSoftInputFromServerForTest();
                    parcel2.writeNoException();
                    return true;
                case 10:
                    int readInt13 = parcel.readInt();
                    com.android.internal.inputmethod.IInputMethodClient asInterface5 = com.android.internal.inputmethod.IInputMethodClient.Stub.asInterface(parcel.readStrongBinder());
                    android.os.IBinder readStrongBinder3 = parcel.readStrongBinder();
                    int readInt14 = parcel.readInt();
                    int readInt15 = parcel.readInt();
                    int readInt16 = parcel.readInt();
                    android.view.inputmethod.EditorInfo editorInfo = (android.view.inputmethod.EditorInfo) parcel.readTypedObject(android.view.inputmethod.EditorInfo.CREATOR);
                    com.android.internal.inputmethod.IRemoteInputConnection asInterface6 = com.android.internal.inputmethod.IRemoteInputConnection.Stub.asInterface(parcel.readStrongBinder());
                    com.android.internal.inputmethod.IRemoteAccessibilityInputConnection asInterface7 = com.android.internal.inputmethod.IRemoteAccessibilityInputConnection.Stub.asInterface(parcel.readStrongBinder());
                    int readInt17 = parcel.readInt();
                    int readInt18 = parcel.readInt();
                    android.window.ImeOnBackInvokedDispatcher imeOnBackInvokedDispatcher = (android.window.ImeOnBackInvokedDispatcher) parcel.readTypedObject(android.window.ImeOnBackInvokedDispatcher.CREATOR);
                    parcel.enforceNoDataAvail();
                    com.android.internal.inputmethod.InputBindResult startInputOrWindowGainedFocus = startInputOrWindowGainedFocus(readInt13, asInterface5, readStrongBinder3, readInt14, readInt15, readInt16, editorInfo, asInterface6, asInterface7, readInt17, readInt18, imeOnBackInvokedDispatcher);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(startInputOrWindowGainedFocus, 1);
                    return true;
                case 11:
                    int readInt19 = parcel.readInt();
                    com.android.internal.inputmethod.IInputMethodClient asInterface8 = com.android.internal.inputmethod.IInputMethodClient.Stub.asInterface(parcel.readStrongBinder());
                    android.os.IBinder readStrongBinder4 = parcel.readStrongBinder();
                    int readInt20 = parcel.readInt();
                    int readInt21 = parcel.readInt();
                    int readInt22 = parcel.readInt();
                    android.view.inputmethod.EditorInfo editorInfo2 = (android.view.inputmethod.EditorInfo) parcel.readTypedObject(android.view.inputmethod.EditorInfo.CREATOR);
                    com.android.internal.inputmethod.IRemoteInputConnection asInterface9 = com.android.internal.inputmethod.IRemoteInputConnection.Stub.asInterface(parcel.readStrongBinder());
                    com.android.internal.inputmethod.IRemoteAccessibilityInputConnection asInterface10 = com.android.internal.inputmethod.IRemoteAccessibilityInputConnection.Stub.asInterface(parcel.readStrongBinder());
                    int readInt23 = parcel.readInt();
                    int readInt24 = parcel.readInt();
                    android.window.ImeOnBackInvokedDispatcher imeOnBackInvokedDispatcher2 = (android.window.ImeOnBackInvokedDispatcher) parcel.readTypedObject(android.window.ImeOnBackInvokedDispatcher.CREATOR);
                    int readInt25 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    startInputOrWindowGainedFocusAsync(readInt19, asInterface8, readStrongBinder4, readInt20, readInt21, readInt22, editorInfo2, asInterface9, asInterface10, readInt23, readInt24, imeOnBackInvokedDispatcher2, readInt25);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    com.android.internal.inputmethod.IInputMethodClient asInterface11 = com.android.internal.inputmethod.IInputMethodClient.Stub.asInterface(parcel.readStrongBinder());
                    int readInt26 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    showInputMethodPickerFromClient(asInterface11, readInt26);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    int readInt27 = parcel.readInt();
                    int readInt28 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    showInputMethodPickerFromSystem(readInt27, readInt28);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    boolean isInputMethodPickerShownForTest = isInputMethodPickerShownForTest();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isInputMethodPickerShownForTest);
                    return true;
                case 15:
                    int readInt29 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.view.inputmethod.InputMethodSubtype currentInputMethodSubtype = getCurrentInputMethodSubtype(readInt29);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(currentInputMethodSubtype, 1);
                    return true;
                case 16:
                    java.lang.String readString2 = parcel.readString();
                    android.view.inputmethod.InputMethodSubtype[] inputMethodSubtypeArr = (android.view.inputmethod.InputMethodSubtype[]) parcel.createTypedArray(android.view.inputmethod.InputMethodSubtype.CREATOR);
                    int readInt30 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setAdditionalInputMethodSubtypes(readString2, inputMethodSubtypeArr, readInt30);
                    parcel2.writeNoException();
                    return true;
                case 17:
                    java.lang.String readString3 = parcel.readString();
                    int[] createIntArray = parcel.createIntArray();
                    int readInt31 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setExplicitlyEnabledInputMethodSubtypes(readString3, createIntArray, readInt31);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    com.android.internal.inputmethod.IInputMethodClient asInterface12 = com.android.internal.inputmethod.IInputMethodClient.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int inputMethodWindowVisibleHeight = getInputMethodWindowVisibleHeight(asInterface12);
                    parcel2.writeNoException();
                    parcel2.writeInt(inputMethodWindowVisibleHeight);
                    return true;
                case 19:
                    android.os.IBinder readStrongBinder5 = parcel.readStrongBinder();
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    reportPerceptibleAsync(readStrongBinder5, readBoolean2);
                    return true;
                case 20:
                    removeImeSurface();
                    parcel2.writeNoException();
                    return true;
                case 21:
                    android.os.IBinder readStrongBinder6 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    removeImeSurfaceFromWindowAsync(readStrongBinder6);
                    return true;
                case 22:
                    byte[] createByteArray = parcel.createByteArray();
                    int readInt32 = parcel.readInt();
                    java.lang.String readString4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    startProtoDump(createByteArray, readInt32, readString4);
                    parcel2.writeNoException();
                    return true;
                case 23:
                    boolean isImeTraceEnabled = isImeTraceEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isImeTraceEnabled);
                    return true;
                case 24:
                    startImeTrace();
                    parcel2.writeNoException();
                    return true;
                case 25:
                    stopImeTrace();
                    parcel2.writeNoException();
                    return true;
                case 26:
                    com.android.internal.inputmethod.IInputMethodClient asInterface13 = com.android.internal.inputmethod.IInputMethodClient.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    startStylusHandwriting(asInterface13);
                    parcel2.writeNoException();
                    return true;
                case 27:
                    com.android.internal.inputmethod.IInputMethodClient asInterface14 = com.android.internal.inputmethod.IInputMethodClient.Stub.asInterface(parcel.readStrongBinder());
                    int readInt33 = parcel.readInt();
                    android.view.inputmethod.CursorAnchorInfo cursorAnchorInfo = (android.view.inputmethod.CursorAnchorInfo) parcel.readTypedObject(android.view.inputmethod.CursorAnchorInfo.CREATOR);
                    java.lang.String readString5 = parcel.readString();
                    java.lang.String readString6 = parcel.readString();
                    com.android.internal.inputmethod.IConnectionlessHandwritingCallback asInterface15 = com.android.internal.inputmethod.IConnectionlessHandwritingCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    startConnectionlessStylusHandwriting(asInterface14, readInt33, cursorAnchorInfo, readString5, readString6, asInterface15);
                    return true;
                case 28:
                    com.android.internal.inputmethod.IInputMethodClient asInterface16 = com.android.internal.inputmethod.IInputMethodClient.Stub.asInterface(parcel.readStrongBinder());
                    int readInt34 = parcel.readInt();
                    java.lang.String readString7 = parcel.readString();
                    java.lang.String readString8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    prepareStylusHandwritingDelegation(asInterface16, readInt34, readString7, readString8);
                    parcel2.writeNoException();
                    return true;
                case 29:
                    com.android.internal.inputmethod.IInputMethodClient asInterface17 = com.android.internal.inputmethod.IInputMethodClient.Stub.asInterface(parcel.readStrongBinder());
                    int readInt35 = parcel.readInt();
                    java.lang.String readString9 = parcel.readString();
                    java.lang.String readString10 = parcel.readString();
                    int readInt36 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean acceptStylusHandwritingDelegation = acceptStylusHandwritingDelegation(asInterface17, readInt35, readString9, readString10, readInt36);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(acceptStylusHandwritingDelegation);
                    return true;
                case 30:
                    com.android.internal.inputmethod.IInputMethodClient asInterface18 = com.android.internal.inputmethod.IInputMethodClient.Stub.asInterface(parcel.readStrongBinder());
                    int readInt37 = parcel.readInt();
                    java.lang.String readString11 = parcel.readString();
                    java.lang.String readString12 = parcel.readString();
                    int readInt38 = parcel.readInt();
                    com.android.internal.inputmethod.IBooleanListener asInterface19 = com.android.internal.inputmethod.IBooleanListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    acceptStylusHandwritingDelegationAsync(asInterface18, readInt37, readString11, readString12, readInt38, asInterface19);
                    return true;
                case 31:
                    int readInt39 = parcel.readInt();
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean isStylusHandwritingAvailableAsUser = isStylusHandwritingAvailableAsUser(readInt39, readBoolean3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isStylusHandwritingAvailableAsUser);
                    return true;
                case 32:
                    com.android.internal.inputmethod.IInputMethodClient asInterface20 = com.android.internal.inputmethod.IInputMethodClient.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    addVirtualStylusIdForTestSession(asInterface20);
                    parcel2.writeNoException();
                    return true;
                case 33:
                    com.android.internal.inputmethod.IInputMethodClient asInterface21 = com.android.internal.inputmethod.IInputMethodClient.Stub.asInterface(parcel.readStrongBinder());
                    long readLong = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    setStylusWindowIdleTimeoutForTest(asInterface21, readLong);
                    parcel2.writeNoException();
                    return true;
                case 34:
                    com.android.internal.inputmethod.IImeTracker imeTrackerService = getImeTrackerService();
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(imeTrackerService);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements com.android.internal.view.IInputMethodManager {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return com.android.internal.view.IInputMethodManager.Stub.DESCRIPTOR;
            }

            @Override // com.android.internal.view.IInputMethodManager
            public void addClient(com.android.internal.inputmethod.IInputMethodClient iInputMethodClient, com.android.internal.inputmethod.IRemoteInputConnection iRemoteInputConnection, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.view.IInputMethodManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iInputMethodClient);
                    obtain.writeStrongInterface(iRemoteInputConnection);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public android.view.inputmethod.InputMethodInfo getCurrentInputMethodInfoAsUser(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.view.IInputMethodManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.view.inputmethod.InputMethodInfo) obtain2.readTypedObject(android.view.inputmethod.InputMethodInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public java.util.List<android.view.inputmethod.InputMethodInfo> getInputMethodList(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.view.IInputMethodManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.view.inputmethod.InputMethodInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public java.util.List<android.view.inputmethod.InputMethodInfo> getEnabledInputMethodList(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.view.IInputMethodManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.view.inputmethod.InputMethodInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public java.util.List<android.view.inputmethod.InputMethodSubtype> getEnabledInputMethodSubtypeList(java.lang.String str, boolean z, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.view.IInputMethodManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.view.inputmethod.InputMethodSubtype.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public android.view.inputmethod.InputMethodSubtype getLastInputMethodSubtype(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.view.IInputMethodManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.view.inputmethod.InputMethodSubtype) obtain2.readTypedObject(android.view.inputmethod.InputMethodSubtype.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public boolean showSoftInput(com.android.internal.inputmethod.IInputMethodClient iInputMethodClient, android.os.IBinder iBinder, android.view.inputmethod.ImeTracker.Token token, int i, int i2, android.os.ResultReceiver resultReceiver, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.view.IInputMethodManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iInputMethodClient);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(token, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(resultReceiver, 0);
                    obtain.writeInt(i3);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public boolean hideSoftInput(com.android.internal.inputmethod.IInputMethodClient iInputMethodClient, android.os.IBinder iBinder, android.view.inputmethod.ImeTracker.Token token, int i, android.os.ResultReceiver resultReceiver, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.view.IInputMethodManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iInputMethodClient);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(token, 0);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(resultReceiver, 0);
                    obtain.writeInt(i2);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public void hideSoftInputFromServerForTest() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.view.IInputMethodManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public com.android.internal.inputmethod.InputBindResult startInputOrWindowGainedFocus(int i, com.android.internal.inputmethod.IInputMethodClient iInputMethodClient, android.os.IBinder iBinder, int i2, int i3, int i4, android.view.inputmethod.EditorInfo editorInfo, com.android.internal.inputmethod.IRemoteInputConnection iRemoteInputConnection, com.android.internal.inputmethod.IRemoteAccessibilityInputConnection iRemoteAccessibilityInputConnection, int i5, int i6, android.window.ImeOnBackInvokedDispatcher imeOnBackInvokedDispatcher) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.view.IInputMethodManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iInputMethodClient);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    obtain.writeTypedObject(editorInfo, 0);
                    obtain.writeStrongInterface(iRemoteInputConnection);
                    obtain.writeStrongInterface(iRemoteAccessibilityInputConnection);
                    obtain.writeInt(i5);
                    obtain.writeInt(i6);
                    obtain.writeTypedObject(imeOnBackInvokedDispatcher, 0);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return (com.android.internal.inputmethod.InputBindResult) obtain2.readTypedObject(com.android.internal.inputmethod.InputBindResult.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public void startInputOrWindowGainedFocusAsync(int i, com.android.internal.inputmethod.IInputMethodClient iInputMethodClient, android.os.IBinder iBinder, int i2, int i3, int i4, android.view.inputmethod.EditorInfo editorInfo, com.android.internal.inputmethod.IRemoteInputConnection iRemoteInputConnection, com.android.internal.inputmethod.IRemoteAccessibilityInputConnection iRemoteAccessibilityInputConnection, int i5, int i6, android.window.ImeOnBackInvokedDispatcher imeOnBackInvokedDispatcher, int i7) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.view.IInputMethodManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iInputMethodClient);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    obtain.writeTypedObject(editorInfo, 0);
                    obtain.writeStrongInterface(iRemoteInputConnection);
                    obtain.writeStrongInterface(iRemoteAccessibilityInputConnection);
                    obtain.writeInt(i5);
                    obtain.writeInt(i6);
                    obtain.writeTypedObject(imeOnBackInvokedDispatcher, 0);
                    obtain.writeInt(i7);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public void showInputMethodPickerFromClient(com.android.internal.inputmethod.IInputMethodClient iInputMethodClient, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.view.IInputMethodManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iInputMethodClient);
                    obtain.writeInt(i);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public void showInputMethodPickerFromSystem(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.view.IInputMethodManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public boolean isInputMethodPickerShownForTest() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.view.IInputMethodManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public android.view.inputmethod.InputMethodSubtype getCurrentInputMethodSubtype(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.view.IInputMethodManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.view.inputmethod.InputMethodSubtype) obtain2.readTypedObject(android.view.inputmethod.InputMethodSubtype.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public void setAdditionalInputMethodSubtypes(java.lang.String str, android.view.inputmethod.InputMethodSubtype[] inputMethodSubtypeArr, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.view.IInputMethodManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedArray(inputMethodSubtypeArr, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public void setExplicitlyEnabledInputMethodSubtypes(java.lang.String str, int[] iArr, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.view.IInputMethodManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeIntArray(iArr);
                    obtain.writeInt(i);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public int getInputMethodWindowVisibleHeight(com.android.internal.inputmethod.IInputMethodClient iInputMethodClient) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.view.IInputMethodManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iInputMethodClient);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public void reportPerceptibleAsync(android.os.IBinder iBinder, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.view.IInputMethodManager.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(19, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public void removeImeSurface() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.view.IInputMethodManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public void removeImeSurfaceFromWindowAsync(android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.view.IInputMethodManager.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(21, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public void startProtoDump(byte[] bArr, int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.view.IInputMethodManager.Stub.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public boolean isImeTraceEnabled() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.view.IInputMethodManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public void startImeTrace() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.view.IInputMethodManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public void stopImeTrace() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.view.IInputMethodManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public void startStylusHandwriting(com.android.internal.inputmethod.IInputMethodClient iInputMethodClient) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.view.IInputMethodManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iInputMethodClient);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public void startConnectionlessStylusHandwriting(com.android.internal.inputmethod.IInputMethodClient iInputMethodClient, int i, android.view.inputmethod.CursorAnchorInfo cursorAnchorInfo, java.lang.String str, java.lang.String str2, com.android.internal.inputmethod.IConnectionlessHandwritingCallback iConnectionlessHandwritingCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.view.IInputMethodManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iInputMethodClient);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(cursorAnchorInfo, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeStrongInterface(iConnectionlessHandwritingCallback);
                    this.mRemote.transact(27, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public void prepareStylusHandwritingDelegation(com.android.internal.inputmethod.IInputMethodClient iInputMethodClient, int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.view.IInputMethodManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iInputMethodClient);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public boolean acceptStylusHandwritingDelegation(com.android.internal.inputmethod.IInputMethodClient iInputMethodClient, int i, java.lang.String str, java.lang.String str2, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.view.IInputMethodManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iInputMethodClient);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i2);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public void acceptStylusHandwritingDelegationAsync(com.android.internal.inputmethod.IInputMethodClient iInputMethodClient, int i, java.lang.String str, java.lang.String str2, int i2, com.android.internal.inputmethod.IBooleanListener iBooleanListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.view.IInputMethodManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iInputMethodClient);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i2);
                    obtain.writeStrongInterface(iBooleanListener);
                    this.mRemote.transact(30, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public boolean isStylusHandwritingAvailableAsUser(int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.view.IInputMethodManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public void addVirtualStylusIdForTestSession(com.android.internal.inputmethod.IInputMethodClient iInputMethodClient) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.view.IInputMethodManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iInputMethodClient);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public void setStylusWindowIdleTimeoutForTest(com.android.internal.inputmethod.IInputMethodClient iInputMethodClient, long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.view.IInputMethodManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iInputMethodClient);
                    obtain.writeLong(j);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public com.android.internal.inputmethod.IImeTracker getImeTrackerService() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.view.IInputMethodManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                    return com.android.internal.inputmethod.IImeTracker.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        protected void hideSoftInputFromServerForTest_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.TEST_INPUT_METHOD, getCallingPid(), getCallingUid());
        }

        protected void showInputMethodPickerFromSystem_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.WRITE_SECURE_SETTINGS, getCallingPid(), getCallingUid());
        }

        protected void isInputMethodPickerShownForTest_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.TEST_INPUT_METHOD, getCallingPid(), getCallingUid());
        }

        protected void removeImeSurface_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.INTERNAL_SYSTEM_WINDOW, getCallingPid(), getCallingUid());
        }

        protected void startImeTrace_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.CONTROL_UI_TRACING, getCallingPid(), getCallingUid());
        }

        protected void stopImeTrace_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.CONTROL_UI_TRACING, getCallingPid(), getCallingUid());
        }

        protected void addVirtualStylusIdForTestSession_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.TEST_INPUT_METHOD, getCallingPid(), getCallingUid());
        }

        protected void setStylusWindowIdleTimeoutForTest_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.TEST_INPUT_METHOD, getCallingPid(), getCallingUid());
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 33;
        }
    }
}
