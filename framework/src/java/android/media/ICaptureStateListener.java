package android.media;

/* loaded from: classes2.dex */
public interface ICaptureStateListener extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.media.ICaptureStateListener";

    void setCaptureState(boolean z) throws android.os.RemoteException;

    public static class Default implements android.media.ICaptureStateListener {
        @Override // android.media.ICaptureStateListener
        public void setCaptureState(boolean z) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.media.ICaptureStateListener {
        static final int TRANSACTION_setCaptureState = 1;

        public Stub() {
            attachInterface(this, android.media.ICaptureStateListener.DESCRIPTOR);
        }

        public static android.media.ICaptureStateListener asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.media.ICaptureStateListener.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.media.ICaptureStateListener)) {
                return (android.media.ICaptureStateListener) queryLocalInterface;
            }
            return new android.media.ICaptureStateListener.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, android.os.Parcel parcel, android.os.Parcel parcel2, int i2) throws android.os.RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(android.media.ICaptureStateListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.media.ICaptureStateListener.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setCaptureState(readBoolean);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.media.ICaptureStateListener {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.media.ICaptureStateListener.DESCRIPTOR;
            }

            @Override // android.media.ICaptureStateListener
            public void setCaptureState(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.ICaptureStateListener.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
