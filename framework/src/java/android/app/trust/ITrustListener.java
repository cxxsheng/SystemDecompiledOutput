package android.app.trust;

/* loaded from: classes.dex */
public interface ITrustListener extends android.os.IInterface {
    void onEnabledTrustAgentsChanged(int i) throws android.os.RemoteException;

    void onIsActiveUnlockRunningChanged(boolean z, int i) throws android.os.RemoteException;

    void onTrustChanged(boolean z, boolean z2, int i, int i2, java.util.List<java.lang.String> list) throws android.os.RemoteException;

    void onTrustError(java.lang.CharSequence charSequence) throws android.os.RemoteException;

    void onTrustManagedChanged(boolean z, int i) throws android.os.RemoteException;

    public static class Default implements android.app.trust.ITrustListener {
        @Override // android.app.trust.ITrustListener
        public void onEnabledTrustAgentsChanged(int i) throws android.os.RemoteException {
        }

        @Override // android.app.trust.ITrustListener
        public void onTrustChanged(boolean z, boolean z2, int i, int i2, java.util.List<java.lang.String> list) throws android.os.RemoteException {
        }

        @Override // android.app.trust.ITrustListener
        public void onTrustManagedChanged(boolean z, int i) throws android.os.RemoteException {
        }

        @Override // android.app.trust.ITrustListener
        public void onTrustError(java.lang.CharSequence charSequence) throws android.os.RemoteException {
        }

        @Override // android.app.trust.ITrustListener
        public void onIsActiveUnlockRunningChanged(boolean z, int i) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.app.trust.ITrustListener {
        public static final java.lang.String DESCRIPTOR = "android.app.trust.ITrustListener";
        static final int TRANSACTION_onEnabledTrustAgentsChanged = 1;
        static final int TRANSACTION_onIsActiveUnlockRunningChanged = 5;
        static final int TRANSACTION_onTrustChanged = 2;
        static final int TRANSACTION_onTrustError = 4;
        static final int TRANSACTION_onTrustManagedChanged = 3;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.app.trust.ITrustListener asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.app.trust.ITrustListener)) {
                return (android.app.trust.ITrustListener) queryLocalInterface;
            }
            return new android.app.trust.ITrustListener.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onEnabledTrustAgentsChanged";
                case 2:
                    return "onTrustChanged";
                case 3:
                    return "onTrustManagedChanged";
                case 4:
                    return "onTrustError";
                case 5:
                    return "onIsActiveUnlockRunningChanged";
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
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onEnabledTrustAgentsChanged(readInt);
                    return true;
                case 2:
                    boolean readBoolean = parcel.readBoolean();
                    boolean readBoolean2 = parcel.readBoolean();
                    int readInt2 = parcel.readInt();
                    int readInt3 = parcel.readInt();
                    java.util.ArrayList<java.lang.String> createStringArrayList = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    onTrustChanged(readBoolean, readBoolean2, readInt2, readInt3, createStringArrayList);
                    return true;
                case 3:
                    boolean readBoolean3 = parcel.readBoolean();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onTrustManagedChanged(readBoolean3, readInt4);
                    return true;
                case 4:
                    java.lang.CharSequence charSequence = (java.lang.CharSequence) parcel.readTypedObject(android.text.TextUtils.CHAR_SEQUENCE_CREATOR);
                    parcel.enforceNoDataAvail();
                    onTrustError(charSequence);
                    return true;
                case 5:
                    boolean readBoolean4 = parcel.readBoolean();
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onIsActiveUnlockRunningChanged(readBoolean4, readInt5);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.app.trust.ITrustListener {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.app.trust.ITrustListener.Stub.DESCRIPTOR;
            }

            @Override // android.app.trust.ITrustListener
            public void onEnabledTrustAgentsChanged(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.trust.ITrustListener.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.trust.ITrustListener
            public void onTrustChanged(boolean z, boolean z2, int i, int i2, java.util.List<java.lang.String> list) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.trust.ITrustListener.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeStringList(list);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.trust.ITrustListener
            public void onTrustManagedChanged(boolean z, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.trust.ITrustListener.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.trust.ITrustListener
            public void onTrustError(java.lang.CharSequence charSequence) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.trust.ITrustListener.Stub.DESCRIPTOR);
                    if (charSequence != null) {
                        obtain.writeInt(1);
                        android.text.TextUtils.writeToParcel(charSequence, obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.trust.ITrustListener
            public void onIsActiveUnlockRunningChanged(boolean z, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.trust.ITrustListener.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 4;
        }
    }
}
