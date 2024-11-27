package android.media;

/* loaded from: classes2.dex */
public interface ISpatializerHeadTrackingCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.media.ISpatializerHeadTrackingCallback";

    void onHeadToSoundStagePoseUpdated(float[] fArr) throws android.os.RemoteException;

    void onHeadTrackingModeChanged(byte b) throws android.os.RemoteException;

    public static class Default implements android.media.ISpatializerHeadTrackingCallback {
        @Override // android.media.ISpatializerHeadTrackingCallback
        public void onHeadTrackingModeChanged(byte b) throws android.os.RemoteException {
        }

        @Override // android.media.ISpatializerHeadTrackingCallback
        public void onHeadToSoundStagePoseUpdated(float[] fArr) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.media.ISpatializerHeadTrackingCallback {
        static final int TRANSACTION_onHeadToSoundStagePoseUpdated = 2;
        static final int TRANSACTION_onHeadTrackingModeChanged = 1;

        public Stub() {
            attachInterface(this, android.media.ISpatializerHeadTrackingCallback.DESCRIPTOR);
        }

        public static android.media.ISpatializerHeadTrackingCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.media.ISpatializerHeadTrackingCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.media.ISpatializerHeadTrackingCallback)) {
                return (android.media.ISpatializerHeadTrackingCallback) queryLocalInterface;
            }
            return new android.media.ISpatializerHeadTrackingCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, android.os.Parcel parcel, android.os.Parcel parcel2, int i2) throws android.os.RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(android.media.ISpatializerHeadTrackingCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.media.ISpatializerHeadTrackingCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    byte readByte = parcel.readByte();
                    parcel.enforceNoDataAvail();
                    onHeadTrackingModeChanged(readByte);
                    return true;
                case 2:
                    float[] createFloatArray = parcel.createFloatArray();
                    parcel.enforceNoDataAvail();
                    onHeadToSoundStagePoseUpdated(createFloatArray);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.media.ISpatializerHeadTrackingCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.media.ISpatializerHeadTrackingCallback.DESCRIPTOR;
            }

            @Override // android.media.ISpatializerHeadTrackingCallback
            public void onHeadTrackingModeChanged(byte b) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.ISpatializerHeadTrackingCallback.DESCRIPTOR);
                    obtain.writeByte(b);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.ISpatializerHeadTrackingCallback
            public void onHeadToSoundStagePoseUpdated(float[] fArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.ISpatializerHeadTrackingCallback.DESCRIPTOR);
                    obtain.writeFloatArray(fArr);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
