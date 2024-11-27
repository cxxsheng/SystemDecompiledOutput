package android.media.tv;

/* loaded from: classes2.dex */
public interface ITvInputServiceCallback extends android.os.IInterface {
    void addHardwareInput(int i, android.media.tv.TvInputInfo tvInputInfo) throws android.os.RemoteException;

    void addHdmiInput(int i, android.media.tv.TvInputInfo tvInputInfo) throws android.os.RemoteException;

    void removeHardwareInput(java.lang.String str) throws android.os.RemoteException;

    public static class Default implements android.media.tv.ITvInputServiceCallback {
        @Override // android.media.tv.ITvInputServiceCallback
        public void addHardwareInput(int i, android.media.tv.TvInputInfo tvInputInfo) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputServiceCallback
        public void addHdmiInput(int i, android.media.tv.TvInputInfo tvInputInfo) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputServiceCallback
        public void removeHardwareInput(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.media.tv.ITvInputServiceCallback {
        public static final java.lang.String DESCRIPTOR = "android.media.tv.ITvInputServiceCallback";
        static final int TRANSACTION_addHardwareInput = 1;
        static final int TRANSACTION_addHdmiInput = 2;
        static final int TRANSACTION_removeHardwareInput = 3;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.media.tv.ITvInputServiceCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.media.tv.ITvInputServiceCallback)) {
                return (android.media.tv.ITvInputServiceCallback) queryLocalInterface;
            }
            return new android.media.tv.ITvInputServiceCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "addHardwareInput";
                case 2:
                    return "addHdmiInput";
                case 3:
                    return "removeHardwareInput";
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
                    android.media.tv.TvInputInfo tvInputInfo = (android.media.tv.TvInputInfo) parcel.readTypedObject(android.media.tv.TvInputInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    addHardwareInput(readInt, tvInputInfo);
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    android.media.tv.TvInputInfo tvInputInfo2 = (android.media.tv.TvInputInfo) parcel.readTypedObject(android.media.tv.TvInputInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    addHdmiInput(readInt2, tvInputInfo2);
                    return true;
                case 3:
                    java.lang.String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    removeHardwareInput(readString);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.media.tv.ITvInputServiceCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.media.tv.ITvInputServiceCallback.Stub.DESCRIPTOR;
            }

            @Override // android.media.tv.ITvInputServiceCallback
            public void addHardwareInput(int i, android.media.tv.TvInputInfo tvInputInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputServiceCallback.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(tvInputInfo, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputServiceCallback
            public void addHdmiInput(int i, android.media.tv.TvInputInfo tvInputInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputServiceCallback.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(tvInputInfo, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputServiceCallback
            public void removeHardwareInput(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputServiceCallback.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }
    }
}
