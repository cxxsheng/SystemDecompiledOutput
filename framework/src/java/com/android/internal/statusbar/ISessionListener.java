package com.android.internal.statusbar;

/* loaded from: classes5.dex */
public interface ISessionListener extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "com.android.internal.statusbar.ISessionListener";

    void onSessionEnded(int i, com.android.internal.logging.InstanceId instanceId) throws android.os.RemoteException;

    void onSessionStarted(int i, com.android.internal.logging.InstanceId instanceId) throws android.os.RemoteException;

    public static class Default implements com.android.internal.statusbar.ISessionListener {
        @Override // com.android.internal.statusbar.ISessionListener
        public void onSessionStarted(int i, com.android.internal.logging.InstanceId instanceId) throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.ISessionListener
        public void onSessionEnded(int i, com.android.internal.logging.InstanceId instanceId) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements com.android.internal.statusbar.ISessionListener {
        static final int TRANSACTION_onSessionEnded = 2;
        static final int TRANSACTION_onSessionStarted = 1;

        public Stub() {
            attachInterface(this, com.android.internal.statusbar.ISessionListener.DESCRIPTOR);
        }

        public static com.android.internal.statusbar.ISessionListener asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(com.android.internal.statusbar.ISessionListener.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof com.android.internal.statusbar.ISessionListener)) {
                return (com.android.internal.statusbar.ISessionListener) queryLocalInterface;
            }
            return new com.android.internal.statusbar.ISessionListener.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onSessionStarted";
                case 2:
                    return "onSessionEnded";
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
                parcel.enforceInterface(com.android.internal.statusbar.ISessionListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(com.android.internal.statusbar.ISessionListener.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    com.android.internal.logging.InstanceId instanceId = (com.android.internal.logging.InstanceId) parcel.readTypedObject(com.android.internal.logging.InstanceId.CREATOR);
                    parcel.enforceNoDataAvail();
                    onSessionStarted(readInt, instanceId);
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    com.android.internal.logging.InstanceId instanceId2 = (com.android.internal.logging.InstanceId) parcel.readTypedObject(com.android.internal.logging.InstanceId.CREATOR);
                    parcel.enforceNoDataAvail();
                    onSessionEnded(readInt2, instanceId2);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements com.android.internal.statusbar.ISessionListener {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return com.android.internal.statusbar.ISessionListener.DESCRIPTOR;
            }

            @Override // com.android.internal.statusbar.ISessionListener
            public void onSessionStarted(int i, com.android.internal.logging.InstanceId instanceId) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.ISessionListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(instanceId, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.ISessionListener
            public void onSessionEnded(int i, com.android.internal.logging.InstanceId instanceId) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.ISessionListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(instanceId, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }
    }
}
