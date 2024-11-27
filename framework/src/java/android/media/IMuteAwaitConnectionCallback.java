package android.media;

/* loaded from: classes2.dex */
public interface IMuteAwaitConnectionCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.media.IMuteAwaitConnectionCallback";

    void dispatchOnMutedUntilConnection(android.media.AudioDeviceAttributes audioDeviceAttributes, int[] iArr) throws android.os.RemoteException;

    void dispatchOnUnmutedEvent(int i, android.media.AudioDeviceAttributes audioDeviceAttributes, int[] iArr) throws android.os.RemoteException;

    public static class Default implements android.media.IMuteAwaitConnectionCallback {
        @Override // android.media.IMuteAwaitConnectionCallback
        public void dispatchOnMutedUntilConnection(android.media.AudioDeviceAttributes audioDeviceAttributes, int[] iArr) throws android.os.RemoteException {
        }

        @Override // android.media.IMuteAwaitConnectionCallback
        public void dispatchOnUnmutedEvent(int i, android.media.AudioDeviceAttributes audioDeviceAttributes, int[] iArr) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.media.IMuteAwaitConnectionCallback {
        static final int TRANSACTION_dispatchOnMutedUntilConnection = 1;
        static final int TRANSACTION_dispatchOnUnmutedEvent = 2;

        public Stub() {
            attachInterface(this, android.media.IMuteAwaitConnectionCallback.DESCRIPTOR);
        }

        public static android.media.IMuteAwaitConnectionCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.media.IMuteAwaitConnectionCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.media.IMuteAwaitConnectionCallback)) {
                return (android.media.IMuteAwaitConnectionCallback) queryLocalInterface;
            }
            return new android.media.IMuteAwaitConnectionCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "dispatchOnMutedUntilConnection";
                case 2:
                    return "dispatchOnUnmutedEvent";
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
                parcel.enforceInterface(android.media.IMuteAwaitConnectionCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.media.IMuteAwaitConnectionCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.media.AudioDeviceAttributes audioDeviceAttributes = (android.media.AudioDeviceAttributes) parcel.readTypedObject(android.media.AudioDeviceAttributes.CREATOR);
                    int[] createIntArray = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    dispatchOnMutedUntilConnection(audioDeviceAttributes, createIntArray);
                    return true;
                case 2:
                    int readInt = parcel.readInt();
                    android.media.AudioDeviceAttributes audioDeviceAttributes2 = (android.media.AudioDeviceAttributes) parcel.readTypedObject(android.media.AudioDeviceAttributes.CREATOR);
                    int[] createIntArray2 = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    dispatchOnUnmutedEvent(readInt, audioDeviceAttributes2, createIntArray2);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.media.IMuteAwaitConnectionCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.media.IMuteAwaitConnectionCallback.DESCRIPTOR;
            }

            @Override // android.media.IMuteAwaitConnectionCallback
            public void dispatchOnMutedUntilConnection(android.media.AudioDeviceAttributes audioDeviceAttributes, int[] iArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.IMuteAwaitConnectionCallback.DESCRIPTOR);
                    obtain.writeTypedObject(audioDeviceAttributes, 0);
                    obtain.writeIntArray(iArr);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IMuteAwaitConnectionCallback
            public void dispatchOnUnmutedEvent(int i, android.media.AudioDeviceAttributes audioDeviceAttributes, int[] iArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.IMuteAwaitConnectionCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(audioDeviceAttributes, 0);
                    obtain.writeIntArray(iArr);
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
