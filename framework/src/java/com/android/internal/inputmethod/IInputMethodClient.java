package com.android.internal.inputmethod;

/* loaded from: classes4.dex */
public interface IInputMethodClient extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "com.android.internal.inputmethod.IInputMethodClient";

    void onBindAccessibilityService(com.android.internal.inputmethod.InputBindResult inputBindResult, int i) throws android.os.RemoteException;

    void onBindMethod(com.android.internal.inputmethod.InputBindResult inputBindResult) throws android.os.RemoteException;

    void onStartInputResult(com.android.internal.inputmethod.InputBindResult inputBindResult, int i) throws android.os.RemoteException;

    void onUnbindAccessibilityService(int i, int i2) throws android.os.RemoteException;

    void onUnbindMethod(int i, int i2) throws android.os.RemoteException;

    void reportFullscreenMode(boolean z) throws android.os.RemoteException;

    void scheduleStartInputIfNecessary(boolean z) throws android.os.RemoteException;

    void setActive(boolean z, boolean z2) throws android.os.RemoteException;

    void setImeTraceEnabled(boolean z) throws android.os.RemoteException;

    void setInteractive(boolean z, boolean z2) throws android.os.RemoteException;

    void throwExceptionFromSystem(java.lang.String str) throws android.os.RemoteException;

    public static class Default implements com.android.internal.inputmethod.IInputMethodClient {
        @Override // com.android.internal.inputmethod.IInputMethodClient
        public void onBindMethod(com.android.internal.inputmethod.InputBindResult inputBindResult) throws android.os.RemoteException {
        }

        @Override // com.android.internal.inputmethod.IInputMethodClient
        public void onStartInputResult(com.android.internal.inputmethod.InputBindResult inputBindResult, int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.inputmethod.IInputMethodClient
        public void onBindAccessibilityService(com.android.internal.inputmethod.InputBindResult inputBindResult, int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.inputmethod.IInputMethodClient
        public void onUnbindMethod(int i, int i2) throws android.os.RemoteException {
        }

        @Override // com.android.internal.inputmethod.IInputMethodClient
        public void onUnbindAccessibilityService(int i, int i2) throws android.os.RemoteException {
        }

        @Override // com.android.internal.inputmethod.IInputMethodClient
        public void setActive(boolean z, boolean z2) throws android.os.RemoteException {
        }

        @Override // com.android.internal.inputmethod.IInputMethodClient
        public void setInteractive(boolean z, boolean z2) throws android.os.RemoteException {
        }

        @Override // com.android.internal.inputmethod.IInputMethodClient
        public void scheduleStartInputIfNecessary(boolean z) throws android.os.RemoteException {
        }

        @Override // com.android.internal.inputmethod.IInputMethodClient
        public void reportFullscreenMode(boolean z) throws android.os.RemoteException {
        }

        @Override // com.android.internal.inputmethod.IInputMethodClient
        public void setImeTraceEnabled(boolean z) throws android.os.RemoteException {
        }

        @Override // com.android.internal.inputmethod.IInputMethodClient
        public void throwExceptionFromSystem(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements com.android.internal.inputmethod.IInputMethodClient {
        static final int TRANSACTION_onBindAccessibilityService = 3;
        static final int TRANSACTION_onBindMethod = 1;
        static final int TRANSACTION_onStartInputResult = 2;
        static final int TRANSACTION_onUnbindAccessibilityService = 5;
        static final int TRANSACTION_onUnbindMethod = 4;
        static final int TRANSACTION_reportFullscreenMode = 9;
        static final int TRANSACTION_scheduleStartInputIfNecessary = 8;
        static final int TRANSACTION_setActive = 6;
        static final int TRANSACTION_setImeTraceEnabled = 10;
        static final int TRANSACTION_setInteractive = 7;
        static final int TRANSACTION_throwExceptionFromSystem = 11;

        public Stub() {
            attachInterface(this, com.android.internal.inputmethod.IInputMethodClient.DESCRIPTOR);
        }

        public static com.android.internal.inputmethod.IInputMethodClient asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(com.android.internal.inputmethod.IInputMethodClient.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof com.android.internal.inputmethod.IInputMethodClient)) {
                return (com.android.internal.inputmethod.IInputMethodClient) queryLocalInterface;
            }
            return new com.android.internal.inputmethod.IInputMethodClient.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onBindMethod";
                case 2:
                    return "onStartInputResult";
                case 3:
                    return "onBindAccessibilityService";
                case 4:
                    return "onUnbindMethod";
                case 5:
                    return "onUnbindAccessibilityService";
                case 6:
                    return "setActive";
                case 7:
                    return "setInteractive";
                case 8:
                    return "scheduleStartInputIfNecessary";
                case 9:
                    return "reportFullscreenMode";
                case 10:
                    return "setImeTraceEnabled";
                case 11:
                    return "throwExceptionFromSystem";
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
                parcel.enforceInterface(com.android.internal.inputmethod.IInputMethodClient.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(com.android.internal.inputmethod.IInputMethodClient.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    com.android.internal.inputmethod.InputBindResult inputBindResult = (com.android.internal.inputmethod.InputBindResult) parcel.readTypedObject(com.android.internal.inputmethod.InputBindResult.CREATOR);
                    parcel.enforceNoDataAvail();
                    onBindMethod(inputBindResult);
                    return true;
                case 2:
                    com.android.internal.inputmethod.InputBindResult inputBindResult2 = (com.android.internal.inputmethod.InputBindResult) parcel.readTypedObject(com.android.internal.inputmethod.InputBindResult.CREATOR);
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onStartInputResult(inputBindResult2, readInt);
                    return true;
                case 3:
                    com.android.internal.inputmethod.InputBindResult inputBindResult3 = (com.android.internal.inputmethod.InputBindResult) parcel.readTypedObject(com.android.internal.inputmethod.InputBindResult.CREATOR);
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onBindAccessibilityService(inputBindResult3, readInt2);
                    return true;
                case 4:
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onUnbindMethod(readInt3, readInt4);
                    return true;
                case 5:
                    int readInt5 = parcel.readInt();
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onUnbindAccessibilityService(readInt5, readInt6);
                    return true;
                case 6:
                    boolean readBoolean = parcel.readBoolean();
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setActive(readBoolean, readBoolean2);
                    return true;
                case 7:
                    boolean readBoolean3 = parcel.readBoolean();
                    boolean readBoolean4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setInteractive(readBoolean3, readBoolean4);
                    return true;
                case 8:
                    boolean readBoolean5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    scheduleStartInputIfNecessary(readBoolean5);
                    return true;
                case 9:
                    boolean readBoolean6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    reportFullscreenMode(readBoolean6);
                    return true;
                case 10:
                    boolean readBoolean7 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setImeTraceEnabled(readBoolean7);
                    return true;
                case 11:
                    java.lang.String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    throwExceptionFromSystem(readString);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements com.android.internal.inputmethod.IInputMethodClient {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return com.android.internal.inputmethod.IInputMethodClient.DESCRIPTOR;
            }

            @Override // com.android.internal.inputmethod.IInputMethodClient
            public void onBindMethod(com.android.internal.inputmethod.InputBindResult inputBindResult) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.inputmethod.IInputMethodClient.DESCRIPTOR);
                    obtain.writeTypedObject(inputBindResult, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethodClient
            public void onStartInputResult(com.android.internal.inputmethod.InputBindResult inputBindResult, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.inputmethod.IInputMethodClient.DESCRIPTOR);
                    obtain.writeTypedObject(inputBindResult, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethodClient
            public void onBindAccessibilityService(com.android.internal.inputmethod.InputBindResult inputBindResult, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.inputmethod.IInputMethodClient.DESCRIPTOR);
                    obtain.writeTypedObject(inputBindResult, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethodClient
            public void onUnbindMethod(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.inputmethod.IInputMethodClient.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethodClient
            public void onUnbindAccessibilityService(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.inputmethod.IInputMethodClient.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethodClient
            public void setActive(boolean z, boolean z2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.inputmethod.IInputMethodClient.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethodClient
            public void setInteractive(boolean z, boolean z2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.inputmethod.IInputMethodClient.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethodClient
            public void scheduleStartInputIfNecessary(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.inputmethod.IInputMethodClient.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethodClient
            public void reportFullscreenMode(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.inputmethod.IInputMethodClient.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethodClient
            public void setImeTraceEnabled(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.inputmethod.IInputMethodClient.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(10, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethodClient
            public void throwExceptionFromSystem(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.inputmethod.IInputMethodClient.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(11, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 10;
        }
    }
}
