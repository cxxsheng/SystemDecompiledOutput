package android.media;

/* loaded from: classes2.dex */
public interface ICommunicationDeviceDispatcher extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.media.ICommunicationDeviceDispatcher";

    void dispatchCommunicationDeviceChanged(int i) throws android.os.RemoteException;

    public static class Default implements android.media.ICommunicationDeviceDispatcher {
        @Override // android.media.ICommunicationDeviceDispatcher
        public void dispatchCommunicationDeviceChanged(int i) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.media.ICommunicationDeviceDispatcher {
        static final int TRANSACTION_dispatchCommunicationDeviceChanged = 1;

        public Stub() {
            attachInterface(this, android.media.ICommunicationDeviceDispatcher.DESCRIPTOR);
        }

        public static android.media.ICommunicationDeviceDispatcher asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.media.ICommunicationDeviceDispatcher.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.media.ICommunicationDeviceDispatcher)) {
                return (android.media.ICommunicationDeviceDispatcher) queryLocalInterface;
            }
            return new android.media.ICommunicationDeviceDispatcher.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "dispatchCommunicationDeviceChanged";
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
                parcel.enforceInterface(android.media.ICommunicationDeviceDispatcher.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.media.ICommunicationDeviceDispatcher.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    dispatchCommunicationDeviceChanged(readInt);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.media.ICommunicationDeviceDispatcher {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.media.ICommunicationDeviceDispatcher.DESCRIPTOR;
            }

            @Override // android.media.ICommunicationDeviceDispatcher
            public void dispatchCommunicationDeviceChanged(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.ICommunicationDeviceDispatcher.DESCRIPTOR);
                    obtain.writeInt(i);
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
