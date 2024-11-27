package com.android.modules.utils;

/* loaded from: classes5.dex */
public interface ISynchronousResultReceiver extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "com.android.modules.utils.ISynchronousResultReceiver";

    void send(com.android.modules.utils.SynchronousResultReceiver.Result result) throws android.os.RemoteException;

    public static class Default implements com.android.modules.utils.ISynchronousResultReceiver {
        @Override // com.android.modules.utils.ISynchronousResultReceiver
        public void send(com.android.modules.utils.SynchronousResultReceiver.Result result) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements com.android.modules.utils.ISynchronousResultReceiver {
        static final int TRANSACTION_send = 1;

        public Stub() {
            attachInterface(this, com.android.modules.utils.ISynchronousResultReceiver.DESCRIPTOR);
        }

        public static com.android.modules.utils.ISynchronousResultReceiver asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(com.android.modules.utils.ISynchronousResultReceiver.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof com.android.modules.utils.ISynchronousResultReceiver)) {
                return (com.android.modules.utils.ISynchronousResultReceiver) queryLocalInterface;
            }
            return new com.android.modules.utils.ISynchronousResultReceiver.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "send";
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
                parcel.enforceInterface(com.android.modules.utils.ISynchronousResultReceiver.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(com.android.modules.utils.ISynchronousResultReceiver.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    send((com.android.modules.utils.SynchronousResultReceiver.Result) parcel.readTypedObject(com.android.modules.utils.SynchronousResultReceiver.Result.CREATOR));
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements com.android.modules.utils.ISynchronousResultReceiver {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return com.android.modules.utils.ISynchronousResultReceiver.DESCRIPTOR;
            }

            @Override // com.android.modules.utils.ISynchronousResultReceiver
            public void send(com.android.modules.utils.SynchronousResultReceiver.Result result) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.modules.utils.ISynchronousResultReceiver.DESCRIPTOR);
                    obtain.writeTypedObject(result, 0);
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
