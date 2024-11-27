package android.media;

/* loaded from: classes2.dex */
public interface ISpatializerHeadToSoundStagePoseCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.media.ISpatializerHeadToSoundStagePoseCallback";

    void dispatchPoseChanged(float[] fArr) throws android.os.RemoteException;

    public static class Default implements android.media.ISpatializerHeadToSoundStagePoseCallback {
        @Override // android.media.ISpatializerHeadToSoundStagePoseCallback
        public void dispatchPoseChanged(float[] fArr) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.media.ISpatializerHeadToSoundStagePoseCallback {
        static final int TRANSACTION_dispatchPoseChanged = 1;

        public Stub() {
            attachInterface(this, android.media.ISpatializerHeadToSoundStagePoseCallback.DESCRIPTOR);
        }

        public static android.media.ISpatializerHeadToSoundStagePoseCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.media.ISpatializerHeadToSoundStagePoseCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.media.ISpatializerHeadToSoundStagePoseCallback)) {
                return (android.media.ISpatializerHeadToSoundStagePoseCallback) queryLocalInterface;
            }
            return new android.media.ISpatializerHeadToSoundStagePoseCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "dispatchPoseChanged";
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
                parcel.enforceInterface(android.media.ISpatializerHeadToSoundStagePoseCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.media.ISpatializerHeadToSoundStagePoseCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    float[] createFloatArray = parcel.createFloatArray();
                    parcel.enforceNoDataAvail();
                    dispatchPoseChanged(createFloatArray);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.media.ISpatializerHeadToSoundStagePoseCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.media.ISpatializerHeadToSoundStagePoseCallback.DESCRIPTOR;
            }

            @Override // android.media.ISpatializerHeadToSoundStagePoseCallback
            public void dispatchPoseChanged(float[] fArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.ISpatializerHeadToSoundStagePoseCallback.DESCRIPTOR);
                    obtain.writeFloatArray(fArr);
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
