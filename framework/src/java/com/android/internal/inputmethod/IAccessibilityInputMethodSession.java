package com.android.internal.inputmethod;

/* loaded from: classes4.dex */
public interface IAccessibilityInputMethodSession extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "com.android.internal.inputmethod.IAccessibilityInputMethodSession";

    void finishInput() throws android.os.RemoteException;

    void finishSession() throws android.os.RemoteException;

    void invalidateInput(android.view.inputmethod.EditorInfo editorInfo, com.android.internal.inputmethod.IRemoteAccessibilityInputConnection iRemoteAccessibilityInputConnection, int i) throws android.os.RemoteException;

    void updateSelection(int i, int i2, int i3, int i4, int i5, int i6) throws android.os.RemoteException;

    public static class Default implements com.android.internal.inputmethod.IAccessibilityInputMethodSession {
        @Override // com.android.internal.inputmethod.IAccessibilityInputMethodSession
        public void updateSelection(int i, int i2, int i3, int i4, int i5, int i6) throws android.os.RemoteException {
        }

        @Override // com.android.internal.inputmethod.IAccessibilityInputMethodSession
        public void finishInput() throws android.os.RemoteException {
        }

        @Override // com.android.internal.inputmethod.IAccessibilityInputMethodSession
        public void finishSession() throws android.os.RemoteException {
        }

        @Override // com.android.internal.inputmethod.IAccessibilityInputMethodSession
        public void invalidateInput(android.view.inputmethod.EditorInfo editorInfo, com.android.internal.inputmethod.IRemoteAccessibilityInputConnection iRemoteAccessibilityInputConnection, int i) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements com.android.internal.inputmethod.IAccessibilityInputMethodSession {
        static final int TRANSACTION_finishInput = 2;
        static final int TRANSACTION_finishSession = 3;
        static final int TRANSACTION_invalidateInput = 4;
        static final int TRANSACTION_updateSelection = 1;

        public Stub() {
            attachInterface(this, com.android.internal.inputmethod.IAccessibilityInputMethodSession.DESCRIPTOR);
        }

        public static com.android.internal.inputmethod.IAccessibilityInputMethodSession asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(com.android.internal.inputmethod.IAccessibilityInputMethodSession.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof com.android.internal.inputmethod.IAccessibilityInputMethodSession)) {
                return (com.android.internal.inputmethod.IAccessibilityInputMethodSession) queryLocalInterface;
            }
            return new com.android.internal.inputmethod.IAccessibilityInputMethodSession.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "updateSelection";
                case 2:
                    return "finishInput";
                case 3:
                    return "finishSession";
                case 4:
                    return "invalidateInput";
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
                parcel.enforceInterface(com.android.internal.inputmethod.IAccessibilityInputMethodSession.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(com.android.internal.inputmethod.IAccessibilityInputMethodSession.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    int readInt5 = parcel.readInt();
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    updateSelection(readInt, readInt2, readInt3, readInt4, readInt5, readInt6);
                    return true;
                case 2:
                    finishInput();
                    return true;
                case 3:
                    finishSession();
                    return true;
                case 4:
                    android.view.inputmethod.EditorInfo editorInfo = (android.view.inputmethod.EditorInfo) parcel.readTypedObject(android.view.inputmethod.EditorInfo.CREATOR);
                    com.android.internal.inputmethod.IRemoteAccessibilityInputConnection asInterface = com.android.internal.inputmethod.IRemoteAccessibilityInputConnection.Stub.asInterface(parcel.readStrongBinder());
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    invalidateInput(editorInfo, asInterface, readInt7);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements com.android.internal.inputmethod.IAccessibilityInputMethodSession {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return com.android.internal.inputmethod.IAccessibilityInputMethodSession.DESCRIPTOR;
            }

            @Override // com.android.internal.inputmethod.IAccessibilityInputMethodSession
            public void updateSelection(int i, int i2, int i3, int i4, int i5, int i6) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.inputmethod.IAccessibilityInputMethodSession.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    obtain.writeInt(i5);
                    obtain.writeInt(i6);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IAccessibilityInputMethodSession
            public void finishInput() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.inputmethod.IAccessibilityInputMethodSession.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IAccessibilityInputMethodSession
            public void finishSession() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.inputmethod.IAccessibilityInputMethodSession.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IAccessibilityInputMethodSession
            public void invalidateInput(android.view.inputmethod.EditorInfo editorInfo, com.android.internal.inputmethod.IRemoteAccessibilityInputConnection iRemoteAccessibilityInputConnection, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.inputmethod.IAccessibilityInputMethodSession.DESCRIPTOR);
                    obtain.writeTypedObject(editorInfo, 0);
                    obtain.writeStrongInterface(iRemoteAccessibilityInputConnection);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 3;
        }
    }
}
