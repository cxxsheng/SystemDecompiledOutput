package android.os;

/* loaded from: classes.dex */
public interface IVoldMountCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.os.IVoldMountCallback";

    boolean onVolumeChecking(java.io.FileDescriptor fileDescriptor, java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    public static class Default implements android.os.IVoldMountCallback {
        @Override // android.os.IVoldMountCallback
        public boolean onVolumeChecking(java.io.FileDescriptor fileDescriptor, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.os.IVoldMountCallback {
        static final int TRANSACTION_onVolumeChecking = 1;

        public Stub() {
            attachInterface(this, android.os.IVoldMountCallback.DESCRIPTOR);
        }

        public static android.os.IVoldMountCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.os.IVoldMountCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.os.IVoldMountCallback)) {
                return (android.os.IVoldMountCallback) queryLocalInterface;
            }
            return new android.os.IVoldMountCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, android.os.Parcel parcel, android.os.Parcel parcel2, int i2) throws android.os.RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(android.os.IVoldMountCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.os.IVoldMountCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    java.io.FileDescriptor readRawFileDescriptor = parcel.readRawFileDescriptor();
                    java.lang.String readString = parcel.readString();
                    java.lang.String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean onVolumeChecking = onVolumeChecking(readRawFileDescriptor, readString, readString2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(onVolumeChecking);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.os.IVoldMountCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.os.IVoldMountCallback.DESCRIPTOR;
            }

            @Override // android.os.IVoldMountCallback
            public boolean onVolumeChecking(java.io.FileDescriptor fileDescriptor, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IVoldMountCallback.DESCRIPTOR);
                    obtain.writeRawFileDescriptor(fileDescriptor);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}