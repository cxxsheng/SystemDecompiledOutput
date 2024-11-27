package android.media;

/* loaded from: classes2.dex */
public interface INativeSpatializerCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.media.INativeSpatializerCallback";

    void onLevelChanged(byte b) throws android.os.RemoteException;

    void onOutputChanged(int i) throws android.os.RemoteException;

    public static class Default implements android.media.INativeSpatializerCallback {
        @Override // android.media.INativeSpatializerCallback
        public void onLevelChanged(byte b) throws android.os.RemoteException {
        }

        @Override // android.media.INativeSpatializerCallback
        public void onOutputChanged(int i) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.media.INativeSpatializerCallback {
        static final int TRANSACTION_onLevelChanged = 1;
        static final int TRANSACTION_onOutputChanged = 2;

        public Stub() {
            attachInterface(this, android.media.INativeSpatializerCallback.DESCRIPTOR);
        }

        public static android.media.INativeSpatializerCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.media.INativeSpatializerCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.media.INativeSpatializerCallback)) {
                return (android.media.INativeSpatializerCallback) queryLocalInterface;
            }
            return new android.media.INativeSpatializerCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, android.os.Parcel parcel, android.os.Parcel parcel2, int i2) throws android.os.RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(android.media.INativeSpatializerCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.media.INativeSpatializerCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    byte readByte = parcel.readByte();
                    parcel.enforceNoDataAvail();
                    onLevelChanged(readByte);
                    return true;
                case 2:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onOutputChanged(readInt);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.media.INativeSpatializerCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.media.INativeSpatializerCallback.DESCRIPTOR;
            }

            @Override // android.media.INativeSpatializerCallback
            public void onLevelChanged(byte b) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.INativeSpatializerCallback.DESCRIPTOR);
                    obtain.writeByte(b);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.INativeSpatializerCallback
            public void onOutputChanged(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.INativeSpatializerCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
