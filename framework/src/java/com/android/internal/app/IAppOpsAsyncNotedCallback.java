package com.android.internal.app;

/* loaded from: classes4.dex */
public interface IAppOpsAsyncNotedCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "com.android.internal.app.IAppOpsAsyncNotedCallback";

    void opNoted(android.app.AsyncNotedAppOp asyncNotedAppOp) throws android.os.RemoteException;

    public static class Default implements com.android.internal.app.IAppOpsAsyncNotedCallback {
        @Override // com.android.internal.app.IAppOpsAsyncNotedCallback
        public void opNoted(android.app.AsyncNotedAppOp asyncNotedAppOp) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements com.android.internal.app.IAppOpsAsyncNotedCallback {
        static final int TRANSACTION_opNoted = 1;

        public Stub() {
            attachInterface(this, com.android.internal.app.IAppOpsAsyncNotedCallback.DESCRIPTOR);
        }

        public static com.android.internal.app.IAppOpsAsyncNotedCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(com.android.internal.app.IAppOpsAsyncNotedCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof com.android.internal.app.IAppOpsAsyncNotedCallback)) {
                return (com.android.internal.app.IAppOpsAsyncNotedCallback) queryLocalInterface;
            }
            return new com.android.internal.app.IAppOpsAsyncNotedCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "opNoted";
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
                parcel.enforceInterface(com.android.internal.app.IAppOpsAsyncNotedCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(com.android.internal.app.IAppOpsAsyncNotedCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.app.AsyncNotedAppOp asyncNotedAppOp = (android.app.AsyncNotedAppOp) parcel.readTypedObject(android.app.AsyncNotedAppOp.CREATOR);
                    parcel.enforceNoDataAvail();
                    opNoted(asyncNotedAppOp);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements com.android.internal.app.IAppOpsAsyncNotedCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return com.android.internal.app.IAppOpsAsyncNotedCallback.DESCRIPTOR;
            }

            @Override // com.android.internal.app.IAppOpsAsyncNotedCallback
            public void opNoted(android.app.AsyncNotedAppOp asyncNotedAppOp) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.app.IAppOpsAsyncNotedCallback.DESCRIPTOR);
                    obtain.writeTypedObject(asyncNotedAppOp, 0);
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
