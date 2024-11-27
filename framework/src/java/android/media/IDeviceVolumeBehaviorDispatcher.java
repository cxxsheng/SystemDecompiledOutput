package android.media;

/* loaded from: classes2.dex */
public interface IDeviceVolumeBehaviorDispatcher extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.media.IDeviceVolumeBehaviorDispatcher";

    void dispatchDeviceVolumeBehaviorChanged(android.media.AudioDeviceAttributes audioDeviceAttributes, int i) throws android.os.RemoteException;

    public static class Default implements android.media.IDeviceVolumeBehaviorDispatcher {
        @Override // android.media.IDeviceVolumeBehaviorDispatcher
        public void dispatchDeviceVolumeBehaviorChanged(android.media.AudioDeviceAttributes audioDeviceAttributes, int i) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.media.IDeviceVolumeBehaviorDispatcher {
        static final int TRANSACTION_dispatchDeviceVolumeBehaviorChanged = 1;

        public Stub() {
            attachInterface(this, android.media.IDeviceVolumeBehaviorDispatcher.DESCRIPTOR);
        }

        public static android.media.IDeviceVolumeBehaviorDispatcher asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.media.IDeviceVolumeBehaviorDispatcher.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.media.IDeviceVolumeBehaviorDispatcher)) {
                return (android.media.IDeviceVolumeBehaviorDispatcher) queryLocalInterface;
            }
            return new android.media.IDeviceVolumeBehaviorDispatcher.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "dispatchDeviceVolumeBehaviorChanged";
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
                parcel.enforceInterface(android.media.IDeviceVolumeBehaviorDispatcher.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.media.IDeviceVolumeBehaviorDispatcher.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.media.AudioDeviceAttributes audioDeviceAttributes = (android.media.AudioDeviceAttributes) parcel.readTypedObject(android.media.AudioDeviceAttributes.CREATOR);
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    dispatchDeviceVolumeBehaviorChanged(audioDeviceAttributes, readInt);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.media.IDeviceVolumeBehaviorDispatcher {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.media.IDeviceVolumeBehaviorDispatcher.DESCRIPTOR;
            }

            @Override // android.media.IDeviceVolumeBehaviorDispatcher
            public void dispatchDeviceVolumeBehaviorChanged(android.media.AudioDeviceAttributes audioDeviceAttributes, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.IDeviceVolumeBehaviorDispatcher.DESCRIPTOR);
                    obtain.writeTypedObject(audioDeviceAttributes, 0);
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
