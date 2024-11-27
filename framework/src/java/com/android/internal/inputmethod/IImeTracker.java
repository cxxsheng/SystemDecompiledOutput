package com.android.internal.inputmethod;

/* loaded from: classes4.dex */
public interface IImeTracker extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "com.android.internal.inputmethod.IImeTracker";

    boolean hasPendingImeVisibilityRequests() throws android.os.RemoteException;

    void onCancelled(android.view.inputmethod.ImeTracker.Token token, int i) throws android.os.RemoteException;

    void onFailed(android.view.inputmethod.ImeTracker.Token token, int i) throws android.os.RemoteException;

    void onHidden(android.view.inputmethod.ImeTracker.Token token) throws android.os.RemoteException;

    void onProgress(android.os.IBinder iBinder, int i) throws android.os.RemoteException;

    void onShown(android.view.inputmethod.ImeTracker.Token token) throws android.os.RemoteException;

    android.view.inputmethod.ImeTracker.Token onStart(java.lang.String str, int i, int i2, int i3, int i4, boolean z) throws android.os.RemoteException;

    public static class Default implements com.android.internal.inputmethod.IImeTracker {
        @Override // com.android.internal.inputmethod.IImeTracker
        public android.view.inputmethod.ImeTracker.Token onStart(java.lang.String str, int i, int i2, int i3, int i4, boolean z) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.inputmethod.IImeTracker
        public void onProgress(android.os.IBinder iBinder, int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.inputmethod.IImeTracker
        public void onFailed(android.view.inputmethod.ImeTracker.Token token, int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.inputmethod.IImeTracker
        public void onCancelled(android.view.inputmethod.ImeTracker.Token token, int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.inputmethod.IImeTracker
        public void onShown(android.view.inputmethod.ImeTracker.Token token) throws android.os.RemoteException {
        }

        @Override // com.android.internal.inputmethod.IImeTracker
        public void onHidden(android.view.inputmethod.ImeTracker.Token token) throws android.os.RemoteException {
        }

        @Override // com.android.internal.inputmethod.IImeTracker
        public boolean hasPendingImeVisibilityRequests() throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements com.android.internal.inputmethod.IImeTracker {
        static final int TRANSACTION_hasPendingImeVisibilityRequests = 7;
        static final int TRANSACTION_onCancelled = 4;
        static final int TRANSACTION_onFailed = 3;
        static final int TRANSACTION_onHidden = 6;
        static final int TRANSACTION_onProgress = 2;
        static final int TRANSACTION_onShown = 5;
        static final int TRANSACTION_onStart = 1;
        private final android.os.PermissionEnforcer mEnforcer;

        public Stub(android.os.PermissionEnforcer permissionEnforcer) {
            attachInterface(this, com.android.internal.inputmethod.IImeTracker.DESCRIPTOR);
            if (permissionEnforcer == null) {
                throw new java.lang.IllegalArgumentException("enforcer cannot be null");
            }
            this.mEnforcer = permissionEnforcer;
        }

        @java.lang.Deprecated
        public Stub() {
            this(android.os.PermissionEnforcer.fromContext(android.app.ActivityThread.currentActivityThread().getSystemContext()));
        }

        public static com.android.internal.inputmethod.IImeTracker asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(com.android.internal.inputmethod.IImeTracker.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof com.android.internal.inputmethod.IImeTracker)) {
                return (com.android.internal.inputmethod.IImeTracker) queryLocalInterface;
            }
            return new com.android.internal.inputmethod.IImeTracker.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onStart";
                case 2:
                    return "onProgress";
                case 3:
                    return "onFailed";
                case 4:
                    return "onCancelled";
                case 5:
                    return "onShown";
                case 6:
                    return "onHidden";
                case 7:
                    return "hasPendingImeVisibilityRequests";
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
                parcel.enforceInterface(com.android.internal.inputmethod.IImeTracker.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(com.android.internal.inputmethod.IImeTracker.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    java.lang.String readString = parcel.readString();
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    android.view.inputmethod.ImeTracker.Token onStart = onStart(readString, readInt, readInt2, readInt3, readInt4, readBoolean);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(onStart, 1);
                    return true;
                case 2:
                    android.os.IBinder readStrongBinder = parcel.readStrongBinder();
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onProgress(readStrongBinder, readInt5);
                    return true;
                case 3:
                    android.view.inputmethod.ImeTracker.Token token = (android.view.inputmethod.ImeTracker.Token) parcel.readTypedObject(android.view.inputmethod.ImeTracker.Token.CREATOR);
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onFailed(token, readInt6);
                    return true;
                case 4:
                    android.view.inputmethod.ImeTracker.Token token2 = (android.view.inputmethod.ImeTracker.Token) parcel.readTypedObject(android.view.inputmethod.ImeTracker.Token.CREATOR);
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onCancelled(token2, readInt7);
                    return true;
                case 5:
                    android.view.inputmethod.ImeTracker.Token token3 = (android.view.inputmethod.ImeTracker.Token) parcel.readTypedObject(android.view.inputmethod.ImeTracker.Token.CREATOR);
                    parcel.enforceNoDataAvail();
                    onShown(token3);
                    return true;
                case 6:
                    android.view.inputmethod.ImeTracker.Token token4 = (android.view.inputmethod.ImeTracker.Token) parcel.readTypedObject(android.view.inputmethod.ImeTracker.Token.CREATOR);
                    parcel.enforceNoDataAvail();
                    onHidden(token4);
                    return true;
                case 7:
                    boolean hasPendingImeVisibilityRequests = hasPendingImeVisibilityRequests();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasPendingImeVisibilityRequests);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements com.android.internal.inputmethod.IImeTracker {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return com.android.internal.inputmethod.IImeTracker.DESCRIPTOR;
            }

            @Override // com.android.internal.inputmethod.IImeTracker
            public android.view.inputmethod.ImeTracker.Token onStart(java.lang.String str, int i, int i2, int i3, int i4, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.inputmethod.IImeTracker.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.view.inputmethod.ImeTracker.Token) obtain2.readTypedObject(android.view.inputmethod.ImeTracker.Token.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IImeTracker
            public void onProgress(android.os.IBinder iBinder, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.inputmethod.IImeTracker.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IImeTracker
            public void onFailed(android.view.inputmethod.ImeTracker.Token token, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.inputmethod.IImeTracker.DESCRIPTOR);
                    obtain.writeTypedObject(token, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IImeTracker
            public void onCancelled(android.view.inputmethod.ImeTracker.Token token, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.inputmethod.IImeTracker.DESCRIPTOR);
                    obtain.writeTypedObject(token, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IImeTracker
            public void onShown(android.view.inputmethod.ImeTracker.Token token) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.inputmethod.IImeTracker.DESCRIPTOR);
                    obtain.writeTypedObject(token, 0);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IImeTracker
            public void onHidden(android.view.inputmethod.ImeTracker.Token token) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.inputmethod.IImeTracker.DESCRIPTOR);
                    obtain.writeTypedObject(token, 0);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IImeTracker
            public boolean hasPendingImeVisibilityRequests() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.inputmethod.IImeTracker.DESCRIPTOR);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        protected void hasPendingImeVisibilityRequests_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.TEST_INPUT_METHOD, getCallingPid(), getCallingUid());
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 6;
        }
    }
}
