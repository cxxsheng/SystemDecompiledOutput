package com.android.ims.internal;

/* loaded from: classes4.dex */
public interface IImsServiceFeatureCallback extends android.os.IInterface {
    void imsFeatureCreated(com.android.ims.ImsFeatureContainer imsFeatureContainer, int i) throws android.os.RemoteException;

    void imsFeatureRemoved(int i) throws android.os.RemoteException;

    void imsStatusChanged(int i, int i2) throws android.os.RemoteException;

    void updateCapabilities(long j) throws android.os.RemoteException;

    public static class Default implements com.android.ims.internal.IImsServiceFeatureCallback {
        @Override // com.android.ims.internal.IImsServiceFeatureCallback
        public void imsFeatureCreated(com.android.ims.ImsFeatureContainer imsFeatureContainer, int i) throws android.os.RemoteException {
        }

        @Override // com.android.ims.internal.IImsServiceFeatureCallback
        public void imsFeatureRemoved(int i) throws android.os.RemoteException {
        }

        @Override // com.android.ims.internal.IImsServiceFeatureCallback
        public void imsStatusChanged(int i, int i2) throws android.os.RemoteException {
        }

        @Override // com.android.ims.internal.IImsServiceFeatureCallback
        public void updateCapabilities(long j) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements com.android.ims.internal.IImsServiceFeatureCallback {
        public static final java.lang.String DESCRIPTOR = "com.android.ims.internal.IImsServiceFeatureCallback";
        static final int TRANSACTION_imsFeatureCreated = 1;
        static final int TRANSACTION_imsFeatureRemoved = 2;
        static final int TRANSACTION_imsStatusChanged = 3;
        static final int TRANSACTION_updateCapabilities = 4;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static com.android.ims.internal.IImsServiceFeatureCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof com.android.ims.internal.IImsServiceFeatureCallback)) {
                return (com.android.ims.internal.IImsServiceFeatureCallback) queryLocalInterface;
            }
            return new com.android.ims.internal.IImsServiceFeatureCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "imsFeatureCreated";
                case 2:
                    return "imsFeatureRemoved";
                case 3:
                    return "imsStatusChanged";
                case 4:
                    return "updateCapabilities";
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
                    com.android.ims.ImsFeatureContainer imsFeatureContainer = (com.android.ims.ImsFeatureContainer) parcel.readTypedObject(com.android.ims.ImsFeatureContainer.CREATOR);
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    imsFeatureCreated(imsFeatureContainer, readInt);
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    imsFeatureRemoved(readInt2);
                    return true;
                case 3:
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    imsStatusChanged(readInt3, readInt4);
                    return true;
                case 4:
                    long readLong = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    updateCapabilities(readLong);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements com.android.ims.internal.IImsServiceFeatureCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return com.android.ims.internal.IImsServiceFeatureCallback.Stub.DESCRIPTOR;
            }

            @Override // com.android.ims.internal.IImsServiceFeatureCallback
            public void imsFeatureCreated(com.android.ims.ImsFeatureContainer imsFeatureContainer, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsServiceFeatureCallback.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(imsFeatureContainer, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsServiceFeatureCallback
            public void imsFeatureRemoved(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsServiceFeatureCallback.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsServiceFeatureCallback
            public void imsStatusChanged(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsServiceFeatureCallback.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsServiceFeatureCallback
            public void updateCapabilities(long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsServiceFeatureCallback.Stub.DESCRIPTOR);
                    obtain.writeLong(j);
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
