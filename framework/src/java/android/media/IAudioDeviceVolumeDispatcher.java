package android.media;

/* loaded from: classes2.dex */
public interface IAudioDeviceVolumeDispatcher extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.media.IAudioDeviceVolumeDispatcher";

    void dispatchDeviceVolumeAdjusted(android.media.AudioDeviceAttributes audioDeviceAttributes, android.media.VolumeInfo volumeInfo, int i, int i2) throws android.os.RemoteException;

    void dispatchDeviceVolumeChanged(android.media.AudioDeviceAttributes audioDeviceAttributes, android.media.VolumeInfo volumeInfo) throws android.os.RemoteException;

    public static class Default implements android.media.IAudioDeviceVolumeDispatcher {
        @Override // android.media.IAudioDeviceVolumeDispatcher
        public void dispatchDeviceVolumeChanged(android.media.AudioDeviceAttributes audioDeviceAttributes, android.media.VolumeInfo volumeInfo) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioDeviceVolumeDispatcher
        public void dispatchDeviceVolumeAdjusted(android.media.AudioDeviceAttributes audioDeviceAttributes, android.media.VolumeInfo volumeInfo, int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.media.IAudioDeviceVolumeDispatcher {
        static final int TRANSACTION_dispatchDeviceVolumeAdjusted = 2;
        static final int TRANSACTION_dispatchDeviceVolumeChanged = 1;

        public Stub() {
            attachInterface(this, android.media.IAudioDeviceVolumeDispatcher.DESCRIPTOR);
        }

        public static android.media.IAudioDeviceVolumeDispatcher asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.media.IAudioDeviceVolumeDispatcher.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.media.IAudioDeviceVolumeDispatcher)) {
                return (android.media.IAudioDeviceVolumeDispatcher) queryLocalInterface;
            }
            return new android.media.IAudioDeviceVolumeDispatcher.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "dispatchDeviceVolumeChanged";
                case 2:
                    return "dispatchDeviceVolumeAdjusted";
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
                parcel.enforceInterface(android.media.IAudioDeviceVolumeDispatcher.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.media.IAudioDeviceVolumeDispatcher.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.media.AudioDeviceAttributes audioDeviceAttributes = (android.media.AudioDeviceAttributes) parcel.readTypedObject(android.media.AudioDeviceAttributes.CREATOR);
                    android.media.VolumeInfo volumeInfo = (android.media.VolumeInfo) parcel.readTypedObject(android.media.VolumeInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    dispatchDeviceVolumeChanged(audioDeviceAttributes, volumeInfo);
                    return true;
                case 2:
                    android.media.AudioDeviceAttributes audioDeviceAttributes2 = (android.media.AudioDeviceAttributes) parcel.readTypedObject(android.media.AudioDeviceAttributes.CREATOR);
                    android.media.VolumeInfo volumeInfo2 = (android.media.VolumeInfo) parcel.readTypedObject(android.media.VolumeInfo.CREATOR);
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    dispatchDeviceVolumeAdjusted(audioDeviceAttributes2, volumeInfo2, readInt, readInt2);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.media.IAudioDeviceVolumeDispatcher {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.media.IAudioDeviceVolumeDispatcher.DESCRIPTOR;
            }

            @Override // android.media.IAudioDeviceVolumeDispatcher
            public void dispatchDeviceVolumeChanged(android.media.AudioDeviceAttributes audioDeviceAttributes, android.media.VolumeInfo volumeInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.IAudioDeviceVolumeDispatcher.DESCRIPTOR);
                    obtain.writeTypedObject(audioDeviceAttributes, 0);
                    obtain.writeTypedObject(volumeInfo, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioDeviceVolumeDispatcher
            public void dispatchDeviceVolumeAdjusted(android.media.AudioDeviceAttributes audioDeviceAttributes, android.media.VolumeInfo volumeInfo, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.IAudioDeviceVolumeDispatcher.DESCRIPTOR);
                    obtain.writeTypedObject(audioDeviceAttributes, 0);
                    obtain.writeTypedObject(volumeInfo, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
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
