package android.accessibilityservice;

/* loaded from: classes.dex */
public interface IAccessibilityServiceClient extends android.os.IInterface {
    void bindInput() throws android.os.RemoteException;

    void clearAccessibilityCache() throws android.os.RemoteException;

    void createImeSession(com.android.internal.inputmethod.IAccessibilityInputMethodSessionCallback iAccessibilityInputMethodSessionCallback) throws android.os.RemoteException;

    void init(android.accessibilityservice.IAccessibilityServiceConnection iAccessibilityServiceConnection, int i, android.os.IBinder iBinder) throws android.os.RemoteException;

    void onAccessibilityButtonAvailabilityChanged(boolean z) throws android.os.RemoteException;

    void onAccessibilityButtonClicked(int i) throws android.os.RemoteException;

    void onAccessibilityEvent(android.view.accessibility.AccessibilityEvent accessibilityEvent, boolean z) throws android.os.RemoteException;

    void onFingerprintCapturingGesturesChanged(boolean z) throws android.os.RemoteException;

    void onFingerprintGesture(int i) throws android.os.RemoteException;

    void onGesture(android.accessibilityservice.AccessibilityGestureEvent accessibilityGestureEvent) throws android.os.RemoteException;

    void onInterrupt() throws android.os.RemoteException;

    void onKeyEvent(android.view.KeyEvent keyEvent, int i) throws android.os.RemoteException;

    void onMagnificationChanged(int i, android.graphics.Region region, android.accessibilityservice.MagnificationConfig magnificationConfig) throws android.os.RemoteException;

    void onMotionEvent(android.view.MotionEvent motionEvent) throws android.os.RemoteException;

    void onPerformGestureResult(int i, boolean z) throws android.os.RemoteException;

    void onSoftKeyboardShowModeChanged(int i) throws android.os.RemoteException;

    void onSystemActionsChanged() throws android.os.RemoteException;

    void onTouchStateChanged(int i, int i2) throws android.os.RemoteException;

    void setImeSessionEnabled(com.android.internal.inputmethod.IAccessibilityInputMethodSession iAccessibilityInputMethodSession, boolean z) throws android.os.RemoteException;

    void startInput(com.android.internal.inputmethod.IRemoteAccessibilityInputConnection iRemoteAccessibilityInputConnection, android.view.inputmethod.EditorInfo editorInfo, boolean z) throws android.os.RemoteException;

    void unbindInput() throws android.os.RemoteException;

    public static class Default implements android.accessibilityservice.IAccessibilityServiceClient {
        @Override // android.accessibilityservice.IAccessibilityServiceClient
        public void init(android.accessibilityservice.IAccessibilityServiceConnection iAccessibilityServiceConnection, int i, android.os.IBinder iBinder) throws android.os.RemoteException {
        }

        @Override // android.accessibilityservice.IAccessibilityServiceClient
        public void onAccessibilityEvent(android.view.accessibility.AccessibilityEvent accessibilityEvent, boolean z) throws android.os.RemoteException {
        }

        @Override // android.accessibilityservice.IAccessibilityServiceClient
        public void onInterrupt() throws android.os.RemoteException {
        }

        @Override // android.accessibilityservice.IAccessibilityServiceClient
        public void onGesture(android.accessibilityservice.AccessibilityGestureEvent accessibilityGestureEvent) throws android.os.RemoteException {
        }

        @Override // android.accessibilityservice.IAccessibilityServiceClient
        public void clearAccessibilityCache() throws android.os.RemoteException {
        }

        @Override // android.accessibilityservice.IAccessibilityServiceClient
        public void onKeyEvent(android.view.KeyEvent keyEvent, int i) throws android.os.RemoteException {
        }

        @Override // android.accessibilityservice.IAccessibilityServiceClient
        public void onMagnificationChanged(int i, android.graphics.Region region, android.accessibilityservice.MagnificationConfig magnificationConfig) throws android.os.RemoteException {
        }

        @Override // android.accessibilityservice.IAccessibilityServiceClient
        public void onMotionEvent(android.view.MotionEvent motionEvent) throws android.os.RemoteException {
        }

        @Override // android.accessibilityservice.IAccessibilityServiceClient
        public void onTouchStateChanged(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.accessibilityservice.IAccessibilityServiceClient
        public void onSoftKeyboardShowModeChanged(int i) throws android.os.RemoteException {
        }

        @Override // android.accessibilityservice.IAccessibilityServiceClient
        public void onPerformGestureResult(int i, boolean z) throws android.os.RemoteException {
        }

        @Override // android.accessibilityservice.IAccessibilityServiceClient
        public void onFingerprintCapturingGesturesChanged(boolean z) throws android.os.RemoteException {
        }

        @Override // android.accessibilityservice.IAccessibilityServiceClient
        public void onFingerprintGesture(int i) throws android.os.RemoteException {
        }

        @Override // android.accessibilityservice.IAccessibilityServiceClient
        public void onAccessibilityButtonClicked(int i) throws android.os.RemoteException {
        }

        @Override // android.accessibilityservice.IAccessibilityServiceClient
        public void onAccessibilityButtonAvailabilityChanged(boolean z) throws android.os.RemoteException {
        }

        @Override // android.accessibilityservice.IAccessibilityServiceClient
        public void onSystemActionsChanged() throws android.os.RemoteException {
        }

        @Override // android.accessibilityservice.IAccessibilityServiceClient
        public void createImeSession(com.android.internal.inputmethod.IAccessibilityInputMethodSessionCallback iAccessibilityInputMethodSessionCallback) throws android.os.RemoteException {
        }

        @Override // android.accessibilityservice.IAccessibilityServiceClient
        public void setImeSessionEnabled(com.android.internal.inputmethod.IAccessibilityInputMethodSession iAccessibilityInputMethodSession, boolean z) throws android.os.RemoteException {
        }

        @Override // android.accessibilityservice.IAccessibilityServiceClient
        public void bindInput() throws android.os.RemoteException {
        }

        @Override // android.accessibilityservice.IAccessibilityServiceClient
        public void unbindInput() throws android.os.RemoteException {
        }

        @Override // android.accessibilityservice.IAccessibilityServiceClient
        public void startInput(com.android.internal.inputmethod.IRemoteAccessibilityInputConnection iRemoteAccessibilityInputConnection, android.view.inputmethod.EditorInfo editorInfo, boolean z) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.accessibilityservice.IAccessibilityServiceClient {
        public static final java.lang.String DESCRIPTOR = "android.accessibilityservice.IAccessibilityServiceClient";
        static final int TRANSACTION_bindInput = 19;
        static final int TRANSACTION_clearAccessibilityCache = 5;
        static final int TRANSACTION_createImeSession = 17;
        static final int TRANSACTION_init = 1;
        static final int TRANSACTION_onAccessibilityButtonAvailabilityChanged = 15;
        static final int TRANSACTION_onAccessibilityButtonClicked = 14;
        static final int TRANSACTION_onAccessibilityEvent = 2;
        static final int TRANSACTION_onFingerprintCapturingGesturesChanged = 12;
        static final int TRANSACTION_onFingerprintGesture = 13;
        static final int TRANSACTION_onGesture = 4;
        static final int TRANSACTION_onInterrupt = 3;
        static final int TRANSACTION_onKeyEvent = 6;
        static final int TRANSACTION_onMagnificationChanged = 7;
        static final int TRANSACTION_onMotionEvent = 8;
        static final int TRANSACTION_onPerformGestureResult = 11;
        static final int TRANSACTION_onSoftKeyboardShowModeChanged = 10;
        static final int TRANSACTION_onSystemActionsChanged = 16;
        static final int TRANSACTION_onTouchStateChanged = 9;
        static final int TRANSACTION_setImeSessionEnabled = 18;
        static final int TRANSACTION_startInput = 21;
        static final int TRANSACTION_unbindInput = 20;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.accessibilityservice.IAccessibilityServiceClient asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.accessibilityservice.IAccessibilityServiceClient)) {
                return (android.accessibilityservice.IAccessibilityServiceClient) queryLocalInterface;
            }
            return new android.accessibilityservice.IAccessibilityServiceClient.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "init";
                case 2:
                    return "onAccessibilityEvent";
                case 3:
                    return "onInterrupt";
                case 4:
                    return "onGesture";
                case 5:
                    return "clearAccessibilityCache";
                case 6:
                    return "onKeyEvent";
                case 7:
                    return "onMagnificationChanged";
                case 8:
                    return "onMotionEvent";
                case 9:
                    return "onTouchStateChanged";
                case 10:
                    return "onSoftKeyboardShowModeChanged";
                case 11:
                    return "onPerformGestureResult";
                case 12:
                    return "onFingerprintCapturingGesturesChanged";
                case 13:
                    return "onFingerprintGesture";
                case 14:
                    return "onAccessibilityButtonClicked";
                case 15:
                    return "onAccessibilityButtonAvailabilityChanged";
                case 16:
                    return "onSystemActionsChanged";
                case 17:
                    return "createImeSession";
                case 18:
                    return "setImeSessionEnabled";
                case 19:
                    return "bindInput";
                case 20:
                    return "unbindInput";
                case 21:
                    return "startInput";
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
                    android.accessibilityservice.IAccessibilityServiceConnection asInterface = android.accessibilityservice.IAccessibilityServiceConnection.Stub.asInterface(parcel.readStrongBinder());
                    int readInt = parcel.readInt();
                    android.os.IBinder readStrongBinder = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    init(asInterface, readInt, readStrongBinder);
                    return true;
                case 2:
                    android.view.accessibility.AccessibilityEvent accessibilityEvent = (android.view.accessibility.AccessibilityEvent) parcel.readTypedObject(android.view.accessibility.AccessibilityEvent.CREATOR);
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onAccessibilityEvent(accessibilityEvent, readBoolean);
                    return true;
                case 3:
                    onInterrupt();
                    return true;
                case 4:
                    android.accessibilityservice.AccessibilityGestureEvent accessibilityGestureEvent = (android.accessibilityservice.AccessibilityGestureEvent) parcel.readTypedObject(android.accessibilityservice.AccessibilityGestureEvent.CREATOR);
                    parcel.enforceNoDataAvail();
                    onGesture(accessibilityGestureEvent);
                    return true;
                case 5:
                    clearAccessibilityCache();
                    return true;
                case 6:
                    android.view.KeyEvent keyEvent = (android.view.KeyEvent) parcel.readTypedObject(android.view.KeyEvent.CREATOR);
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onKeyEvent(keyEvent, readInt2);
                    return true;
                case 7:
                    int readInt3 = parcel.readInt();
                    android.graphics.Region region = (android.graphics.Region) parcel.readTypedObject(android.graphics.Region.CREATOR);
                    android.accessibilityservice.MagnificationConfig magnificationConfig = (android.accessibilityservice.MagnificationConfig) parcel.readTypedObject(android.accessibilityservice.MagnificationConfig.CREATOR);
                    parcel.enforceNoDataAvail();
                    onMagnificationChanged(readInt3, region, magnificationConfig);
                    return true;
                case 8:
                    android.view.MotionEvent motionEvent = (android.view.MotionEvent) parcel.readTypedObject(android.view.MotionEvent.CREATOR);
                    parcel.enforceNoDataAvail();
                    onMotionEvent(motionEvent);
                    return true;
                case 9:
                    int readInt4 = parcel.readInt();
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onTouchStateChanged(readInt4, readInt5);
                    return true;
                case 10:
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onSoftKeyboardShowModeChanged(readInt6);
                    return true;
                case 11:
                    int readInt7 = parcel.readInt();
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onPerformGestureResult(readInt7, readBoolean2);
                    return true;
                case 12:
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onFingerprintCapturingGesturesChanged(readBoolean3);
                    return true;
                case 13:
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onFingerprintGesture(readInt8);
                    return true;
                case 14:
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onAccessibilityButtonClicked(readInt9);
                    return true;
                case 15:
                    boolean readBoolean4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onAccessibilityButtonAvailabilityChanged(readBoolean4);
                    return true;
                case 16:
                    onSystemActionsChanged();
                    return true;
                case 17:
                    com.android.internal.inputmethod.IAccessibilityInputMethodSessionCallback asInterface2 = com.android.internal.inputmethod.IAccessibilityInputMethodSessionCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    createImeSession(asInterface2);
                    return true;
                case 18:
                    com.android.internal.inputmethod.IAccessibilityInputMethodSession asInterface3 = com.android.internal.inputmethod.IAccessibilityInputMethodSession.Stub.asInterface(parcel.readStrongBinder());
                    boolean readBoolean5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setImeSessionEnabled(asInterface3, readBoolean5);
                    return true;
                case 19:
                    bindInput();
                    return true;
                case 20:
                    unbindInput();
                    return true;
                case 21:
                    com.android.internal.inputmethod.IRemoteAccessibilityInputConnection asInterface4 = com.android.internal.inputmethod.IRemoteAccessibilityInputConnection.Stub.asInterface(parcel.readStrongBinder());
                    android.view.inputmethod.EditorInfo editorInfo = (android.view.inputmethod.EditorInfo) parcel.readTypedObject(android.view.inputmethod.EditorInfo.CREATOR);
                    boolean readBoolean6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    startInput(asInterface4, editorInfo, readBoolean6);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.accessibilityservice.IAccessibilityServiceClient {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.accessibilityservice.IAccessibilityServiceClient.Stub.DESCRIPTOR;
            }

            @Override // android.accessibilityservice.IAccessibilityServiceClient
            public void init(android.accessibilityservice.IAccessibilityServiceConnection iAccessibilityServiceConnection, int i, android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.accessibilityservice.IAccessibilityServiceClient.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iAccessibilityServiceConnection);
                    obtain.writeInt(i);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceClient
            public void onAccessibilityEvent(android.view.accessibility.AccessibilityEvent accessibilityEvent, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.accessibilityservice.IAccessibilityServiceClient.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(accessibilityEvent, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceClient
            public void onInterrupt() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.accessibilityservice.IAccessibilityServiceClient.Stub.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceClient
            public void onGesture(android.accessibilityservice.AccessibilityGestureEvent accessibilityGestureEvent) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.accessibilityservice.IAccessibilityServiceClient.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(accessibilityGestureEvent, 0);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceClient
            public void clearAccessibilityCache() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.accessibilityservice.IAccessibilityServiceClient.Stub.DESCRIPTOR);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceClient
            public void onKeyEvent(android.view.KeyEvent keyEvent, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.accessibilityservice.IAccessibilityServiceClient.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(keyEvent, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceClient
            public void onMagnificationChanged(int i, android.graphics.Region region, android.accessibilityservice.MagnificationConfig magnificationConfig) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.accessibilityservice.IAccessibilityServiceClient.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(region, 0);
                    obtain.writeTypedObject(magnificationConfig, 0);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceClient
            public void onMotionEvent(android.view.MotionEvent motionEvent) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.accessibilityservice.IAccessibilityServiceClient.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(motionEvent, 0);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceClient
            public void onTouchStateChanged(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.accessibilityservice.IAccessibilityServiceClient.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceClient
            public void onSoftKeyboardShowModeChanged(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.accessibilityservice.IAccessibilityServiceClient.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(10, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceClient
            public void onPerformGestureResult(int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.accessibilityservice.IAccessibilityServiceClient.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(11, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceClient
            public void onFingerprintCapturingGesturesChanged(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.accessibilityservice.IAccessibilityServiceClient.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(12, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceClient
            public void onFingerprintGesture(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.accessibilityservice.IAccessibilityServiceClient.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(13, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceClient
            public void onAccessibilityButtonClicked(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.accessibilityservice.IAccessibilityServiceClient.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(14, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceClient
            public void onAccessibilityButtonAvailabilityChanged(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.accessibilityservice.IAccessibilityServiceClient.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(15, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceClient
            public void onSystemActionsChanged() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.accessibilityservice.IAccessibilityServiceClient.Stub.DESCRIPTOR);
                    this.mRemote.transact(16, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceClient
            public void createImeSession(com.android.internal.inputmethod.IAccessibilityInputMethodSessionCallback iAccessibilityInputMethodSessionCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.accessibilityservice.IAccessibilityServiceClient.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iAccessibilityInputMethodSessionCallback);
                    this.mRemote.transact(17, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceClient
            public void setImeSessionEnabled(com.android.internal.inputmethod.IAccessibilityInputMethodSession iAccessibilityInputMethodSession, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.accessibilityservice.IAccessibilityServiceClient.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iAccessibilityInputMethodSession);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(18, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceClient
            public void bindInput() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.accessibilityservice.IAccessibilityServiceClient.Stub.DESCRIPTOR);
                    this.mRemote.transact(19, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceClient
            public void unbindInput() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.accessibilityservice.IAccessibilityServiceClient.Stub.DESCRIPTOR);
                    this.mRemote.transact(20, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceClient
            public void startInput(com.android.internal.inputmethod.IRemoteAccessibilityInputConnection iRemoteAccessibilityInputConnection, android.view.inputmethod.EditorInfo editorInfo, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.accessibilityservice.IAccessibilityServiceClient.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iRemoteAccessibilityInputConnection);
                    obtain.writeTypedObject(editorInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(21, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 20;
        }
    }
}
