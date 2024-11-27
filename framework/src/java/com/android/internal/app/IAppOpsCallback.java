package com.android.internal.app;

/* loaded from: classes4.dex */
public interface IAppOpsCallback extends android.os.IInterface {
    void opChanged(int i, int i2, java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    public static class Default implements com.android.internal.app.IAppOpsCallback {
        @Override // com.android.internal.app.IAppOpsCallback
        public void opChanged(int i, int i2, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements com.android.internal.app.IAppOpsCallback {
        public static final java.lang.String DESCRIPTOR = "com.android.internal.app.IAppOpsCallback";
        static final int TRANSACTION_opChanged = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static com.android.internal.app.IAppOpsCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof com.android.internal.app.IAppOpsCallback)) {
                return (com.android.internal.app.IAppOpsCallback) queryLocalInterface;
            }
            return new com.android.internal.app.IAppOpsCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "opChanged";
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
                    int readInt2 = parcel.readInt();
                    java.lang.String readString = parcel.readString();
                    java.lang.String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    opChanged(readInt, readInt2, readString, readString2);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements com.android.internal.app.IAppOpsCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return com.android.internal.app.IAppOpsCallback.Stub.DESCRIPTOR;
            }

            @Override // com.android.internal.app.IAppOpsCallback
            public void opChanged(int i, int i2, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IAppOpsCallback.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    obtain.writeString(str2);
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
