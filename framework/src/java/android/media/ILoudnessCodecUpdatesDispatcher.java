package android.media;

/* loaded from: classes2.dex */
public interface ILoudnessCodecUpdatesDispatcher extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.media.ILoudnessCodecUpdatesDispatcher";

    void dispatchLoudnessCodecParameterChange(int i, android.os.PersistableBundle persistableBundle) throws android.os.RemoteException;

    public static class Default implements android.media.ILoudnessCodecUpdatesDispatcher {
        @Override // android.media.ILoudnessCodecUpdatesDispatcher
        public void dispatchLoudnessCodecParameterChange(int i, android.os.PersistableBundle persistableBundle) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.media.ILoudnessCodecUpdatesDispatcher {
        static final int TRANSACTION_dispatchLoudnessCodecParameterChange = 1;

        public Stub() {
            attachInterface(this, android.media.ILoudnessCodecUpdatesDispatcher.DESCRIPTOR);
        }

        public static android.media.ILoudnessCodecUpdatesDispatcher asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.media.ILoudnessCodecUpdatesDispatcher.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.media.ILoudnessCodecUpdatesDispatcher)) {
                return (android.media.ILoudnessCodecUpdatesDispatcher) queryLocalInterface;
            }
            return new android.media.ILoudnessCodecUpdatesDispatcher.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "dispatchLoudnessCodecParameterChange";
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
                parcel.enforceInterface(android.media.ILoudnessCodecUpdatesDispatcher.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.media.ILoudnessCodecUpdatesDispatcher.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    android.os.PersistableBundle persistableBundle = (android.os.PersistableBundle) parcel.readTypedObject(android.os.PersistableBundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    dispatchLoudnessCodecParameterChange(readInt, persistableBundle);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.media.ILoudnessCodecUpdatesDispatcher {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.media.ILoudnessCodecUpdatesDispatcher.DESCRIPTOR;
            }

            @Override // android.media.ILoudnessCodecUpdatesDispatcher
            public void dispatchLoudnessCodecParameterChange(int i, android.os.PersistableBundle persistableBundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.ILoudnessCodecUpdatesDispatcher.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(persistableBundle, 0);
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
