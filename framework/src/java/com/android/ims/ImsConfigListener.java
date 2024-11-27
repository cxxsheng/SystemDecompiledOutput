package com.android.ims;

/* loaded from: classes4.dex */
public interface ImsConfigListener extends android.os.IInterface {
    void onGetFeatureResponse(int i, int i2, int i3, int i4) throws android.os.RemoteException;

    void onGetVideoQuality(int i, int i2) throws android.os.RemoteException;

    void onSetFeatureResponse(int i, int i2, int i3, int i4) throws android.os.RemoteException;

    void onSetVideoQuality(int i) throws android.os.RemoteException;

    public static class Default implements com.android.ims.ImsConfigListener {
        @Override // com.android.ims.ImsConfigListener
        public void onGetFeatureResponse(int i, int i2, int i3, int i4) throws android.os.RemoteException {
        }

        @Override // com.android.ims.ImsConfigListener
        public void onSetFeatureResponse(int i, int i2, int i3, int i4) throws android.os.RemoteException {
        }

        @Override // com.android.ims.ImsConfigListener
        public void onGetVideoQuality(int i, int i2) throws android.os.RemoteException {
        }

        @Override // com.android.ims.ImsConfigListener
        public void onSetVideoQuality(int i) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements com.android.ims.ImsConfigListener {
        public static final java.lang.String DESCRIPTOR = "com.android.ims.ImsConfigListener";
        static final int TRANSACTION_onGetFeatureResponse = 1;
        static final int TRANSACTION_onGetVideoQuality = 3;
        static final int TRANSACTION_onSetFeatureResponse = 2;
        static final int TRANSACTION_onSetVideoQuality = 4;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static com.android.ims.ImsConfigListener asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof com.android.ims.ImsConfigListener)) {
                return (com.android.ims.ImsConfigListener) queryLocalInterface;
            }
            return new com.android.ims.ImsConfigListener.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onGetFeatureResponse";
                case 2:
                    return "onSetFeatureResponse";
                case 3:
                    return "onGetVideoQuality";
                case 4:
                    return "onSetVideoQuality";
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
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onGetFeatureResponse(readInt, readInt2, readInt3, readInt4);
                    return true;
                case 2:
                    int readInt5 = parcel.readInt();
                    int readInt6 = parcel.readInt();
                    int readInt7 = parcel.readInt();
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onSetFeatureResponse(readInt5, readInt6, readInt7, readInt8);
                    return true;
                case 3:
                    int readInt9 = parcel.readInt();
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onGetVideoQuality(readInt9, readInt10);
                    return true;
                case 4:
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onSetVideoQuality(readInt11);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements com.android.ims.ImsConfigListener {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return com.android.ims.ImsConfigListener.Stub.DESCRIPTOR;
            }

            @Override // com.android.ims.ImsConfigListener
            public void onGetFeatureResponse(int i, int i2, int i3, int i4) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.ims.ImsConfigListener.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.ImsConfigListener
            public void onSetFeatureResponse(int i, int i2, int i3, int i4) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.ims.ImsConfigListener.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.ImsConfigListener
            public void onGetVideoQuality(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.ims.ImsConfigListener.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.ImsConfigListener
            public void onSetVideoQuality(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.ims.ImsConfigListener.Stub.DESCRIPTOR);
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
