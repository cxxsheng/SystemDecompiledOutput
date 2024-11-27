package android.service.trust;

/* loaded from: classes3.dex */
public interface ITrustAgentServiceCallback extends android.os.IInterface {
    void addEscrowToken(byte[] bArr, int i) throws android.os.RemoteException;

    void grantTrust(java.lang.CharSequence charSequence, long j, int i, com.android.internal.infra.AndroidFuture androidFuture) throws android.os.RemoteException;

    void isEscrowTokenActive(long j, int i) throws android.os.RemoteException;

    void lockUser() throws android.os.RemoteException;

    void onConfigureCompleted(boolean z, android.os.IBinder iBinder) throws android.os.RemoteException;

    void removeEscrowToken(long j, int i) throws android.os.RemoteException;

    void revokeTrust() throws android.os.RemoteException;

    void setManagingTrust(boolean z) throws android.os.RemoteException;

    void showKeyguardErrorMessage(java.lang.CharSequence charSequence) throws android.os.RemoteException;

    void unlockUserWithToken(long j, byte[] bArr, int i) throws android.os.RemoteException;

    public static class Default implements android.service.trust.ITrustAgentServiceCallback {
        @Override // android.service.trust.ITrustAgentServiceCallback
        public void grantTrust(java.lang.CharSequence charSequence, long j, int i, com.android.internal.infra.AndroidFuture androidFuture) throws android.os.RemoteException {
        }

        @Override // android.service.trust.ITrustAgentServiceCallback
        public void revokeTrust() throws android.os.RemoteException {
        }

        @Override // android.service.trust.ITrustAgentServiceCallback
        public void lockUser() throws android.os.RemoteException {
        }

        @Override // android.service.trust.ITrustAgentServiceCallback
        public void setManagingTrust(boolean z) throws android.os.RemoteException {
        }

        @Override // android.service.trust.ITrustAgentServiceCallback
        public void onConfigureCompleted(boolean z, android.os.IBinder iBinder) throws android.os.RemoteException {
        }

        @Override // android.service.trust.ITrustAgentServiceCallback
        public void addEscrowToken(byte[] bArr, int i) throws android.os.RemoteException {
        }

        @Override // android.service.trust.ITrustAgentServiceCallback
        public void isEscrowTokenActive(long j, int i) throws android.os.RemoteException {
        }

        @Override // android.service.trust.ITrustAgentServiceCallback
        public void removeEscrowToken(long j, int i) throws android.os.RemoteException {
        }

        @Override // android.service.trust.ITrustAgentServiceCallback
        public void unlockUserWithToken(long j, byte[] bArr, int i) throws android.os.RemoteException {
        }

        @Override // android.service.trust.ITrustAgentServiceCallback
        public void showKeyguardErrorMessage(java.lang.CharSequence charSequence) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.service.trust.ITrustAgentServiceCallback {
        public static final java.lang.String DESCRIPTOR = "android.service.trust.ITrustAgentServiceCallback";
        static final int TRANSACTION_addEscrowToken = 6;
        static final int TRANSACTION_grantTrust = 1;
        static final int TRANSACTION_isEscrowTokenActive = 7;
        static final int TRANSACTION_lockUser = 3;
        static final int TRANSACTION_onConfigureCompleted = 5;
        static final int TRANSACTION_removeEscrowToken = 8;
        static final int TRANSACTION_revokeTrust = 2;
        static final int TRANSACTION_setManagingTrust = 4;
        static final int TRANSACTION_showKeyguardErrorMessage = 10;
        static final int TRANSACTION_unlockUserWithToken = 9;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.service.trust.ITrustAgentServiceCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.service.trust.ITrustAgentServiceCallback)) {
                return (android.service.trust.ITrustAgentServiceCallback) queryLocalInterface;
            }
            return new android.service.trust.ITrustAgentServiceCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "grantTrust";
                case 2:
                    return "revokeTrust";
                case 3:
                    return "lockUser";
                case 4:
                    return "setManagingTrust";
                case 5:
                    return "onConfigureCompleted";
                case 6:
                    return "addEscrowToken";
                case 7:
                    return "isEscrowTokenActive";
                case 8:
                    return "removeEscrowToken";
                case 9:
                    return "unlockUserWithToken";
                case 10:
                    return "showKeyguardErrorMessage";
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
                    java.lang.CharSequence charSequence = (java.lang.CharSequence) parcel.readTypedObject(android.text.TextUtils.CHAR_SEQUENCE_CREATOR);
                    long readLong = parcel.readLong();
                    int readInt = parcel.readInt();
                    com.android.internal.infra.AndroidFuture androidFuture = (com.android.internal.infra.AndroidFuture) parcel.readTypedObject(com.android.internal.infra.AndroidFuture.CREATOR);
                    parcel.enforceNoDataAvail();
                    grantTrust(charSequence, readLong, readInt, androidFuture);
                    return true;
                case 2:
                    revokeTrust();
                    return true;
                case 3:
                    lockUser();
                    return true;
                case 4:
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setManagingTrust(readBoolean);
                    return true;
                case 5:
                    boolean readBoolean2 = parcel.readBoolean();
                    android.os.IBinder readStrongBinder = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    onConfigureCompleted(readBoolean2, readStrongBinder);
                    return true;
                case 6:
                    byte[] createByteArray = parcel.createByteArray();
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    addEscrowToken(createByteArray, readInt2);
                    return true;
                case 7:
                    long readLong2 = parcel.readLong();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    isEscrowTokenActive(readLong2, readInt3);
                    return true;
                case 8:
                    long readLong3 = parcel.readLong();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeEscrowToken(readLong3, readInt4);
                    return true;
                case 9:
                    long readLong4 = parcel.readLong();
                    byte[] createByteArray2 = parcel.createByteArray();
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    unlockUserWithToken(readLong4, createByteArray2, readInt5);
                    return true;
                case 10:
                    java.lang.CharSequence charSequence2 = (java.lang.CharSequence) parcel.readTypedObject(android.text.TextUtils.CHAR_SEQUENCE_CREATOR);
                    parcel.enforceNoDataAvail();
                    showKeyguardErrorMessage(charSequence2);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.service.trust.ITrustAgentServiceCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.service.trust.ITrustAgentServiceCallback.Stub.DESCRIPTOR;
            }

            @Override // android.service.trust.ITrustAgentServiceCallback
            public void grantTrust(java.lang.CharSequence charSequence, long j, int i, com.android.internal.infra.AndroidFuture androidFuture) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.trust.ITrustAgentServiceCallback.Stub.DESCRIPTOR);
                    if (charSequence != null) {
                        obtain.writeInt(1);
                        android.text.TextUtils.writeToParcel(charSequence, obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(androidFuture, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.trust.ITrustAgentServiceCallback
            public void revokeTrust() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.trust.ITrustAgentServiceCallback.Stub.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.trust.ITrustAgentServiceCallback
            public void lockUser() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.trust.ITrustAgentServiceCallback.Stub.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.trust.ITrustAgentServiceCallback
            public void setManagingTrust(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.trust.ITrustAgentServiceCallback.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.trust.ITrustAgentServiceCallback
            public void onConfigureCompleted(boolean z, android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.trust.ITrustAgentServiceCallback.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.trust.ITrustAgentServiceCallback
            public void addEscrowToken(byte[] bArr, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.trust.ITrustAgentServiceCallback.Stub.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    obtain.writeInt(i);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.trust.ITrustAgentServiceCallback
            public void isEscrowTokenActive(long j, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.trust.ITrustAgentServiceCallback.Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.trust.ITrustAgentServiceCallback
            public void removeEscrowToken(long j, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.trust.ITrustAgentServiceCallback.Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.trust.ITrustAgentServiceCallback
            public void unlockUserWithToken(long j, byte[] bArr, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.trust.ITrustAgentServiceCallback.Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeByteArray(bArr);
                    obtain.writeInt(i);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.trust.ITrustAgentServiceCallback
            public void showKeyguardErrorMessage(java.lang.CharSequence charSequence) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.trust.ITrustAgentServiceCallback.Stub.DESCRIPTOR);
                    if (charSequence != null) {
                        obtain.writeInt(1);
                        android.text.TextUtils.writeToParcel(charSequence, obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.mRemote.transact(10, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 9;
        }
    }
}
