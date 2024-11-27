package com.android.internal.policy;

/* loaded from: classes5.dex */
public interface IKeyguardLockedStateListener extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "com.android.internal.policy.IKeyguardLockedStateListener";

    void onKeyguardLockedStateChanged(boolean z) throws android.os.RemoteException;

    public static class Default implements com.android.internal.policy.IKeyguardLockedStateListener {
        @Override // com.android.internal.policy.IKeyguardLockedStateListener
        public void onKeyguardLockedStateChanged(boolean z) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements com.android.internal.policy.IKeyguardLockedStateListener {
        static final int TRANSACTION_onKeyguardLockedStateChanged = 1;

        public Stub() {
            attachInterface(this, com.android.internal.policy.IKeyguardLockedStateListener.DESCRIPTOR);
        }

        public static com.android.internal.policy.IKeyguardLockedStateListener asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(com.android.internal.policy.IKeyguardLockedStateListener.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof com.android.internal.policy.IKeyguardLockedStateListener)) {
                return (com.android.internal.policy.IKeyguardLockedStateListener) queryLocalInterface;
            }
            return new com.android.internal.policy.IKeyguardLockedStateListener.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onKeyguardLockedStateChanged";
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
                parcel.enforceInterface(com.android.internal.policy.IKeyguardLockedStateListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(com.android.internal.policy.IKeyguardLockedStateListener.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onKeyguardLockedStateChanged(readBoolean);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements com.android.internal.policy.IKeyguardLockedStateListener {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return com.android.internal.policy.IKeyguardLockedStateListener.DESCRIPTOR;
            }

            @Override // com.android.internal.policy.IKeyguardLockedStateListener
            public void onKeyguardLockedStateChanged(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.policy.IKeyguardLockedStateListener.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }
    }
}
