package android.media;

/* loaded from: classes2.dex */
public interface ISoundDoseCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.media.ISoundDoseCallback";

    void onMomentaryExposure(float f, int i) throws android.os.RemoteException;

    void onNewCsdValue(float f, android.media.SoundDoseRecord[] soundDoseRecordArr) throws android.os.RemoteException;

    public static class Default implements android.media.ISoundDoseCallback {
        @Override // android.media.ISoundDoseCallback
        public void onMomentaryExposure(float f, int i) throws android.os.RemoteException {
        }

        @Override // android.media.ISoundDoseCallback
        public void onNewCsdValue(float f, android.media.SoundDoseRecord[] soundDoseRecordArr) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.media.ISoundDoseCallback {
        static final int TRANSACTION_onMomentaryExposure = 1;
        static final int TRANSACTION_onNewCsdValue = 2;

        public Stub() {
            attachInterface(this, android.media.ISoundDoseCallback.DESCRIPTOR);
        }

        public static android.media.ISoundDoseCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.media.ISoundDoseCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.media.ISoundDoseCallback)) {
                return (android.media.ISoundDoseCallback) queryLocalInterface;
            }
            return new android.media.ISoundDoseCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, android.os.Parcel parcel, android.os.Parcel parcel2, int i2) throws android.os.RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(android.media.ISoundDoseCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.media.ISoundDoseCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    float readFloat = parcel.readFloat();
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onMomentaryExposure(readFloat, readInt);
                    return true;
                case 2:
                    float readFloat2 = parcel.readFloat();
                    android.media.SoundDoseRecord[] soundDoseRecordArr = (android.media.SoundDoseRecord[]) parcel.createTypedArray(android.media.SoundDoseRecord.CREATOR);
                    parcel.enforceNoDataAvail();
                    onNewCsdValue(readFloat2, soundDoseRecordArr);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.media.ISoundDoseCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.media.ISoundDoseCallback.DESCRIPTOR;
            }

            @Override // android.media.ISoundDoseCallback
            public void onMomentaryExposure(float f, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.ISoundDoseCallback.DESCRIPTOR);
                    obtain.writeFloat(f);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.ISoundDoseCallback
            public void onNewCsdValue(float f, android.media.SoundDoseRecord[] soundDoseRecordArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.ISoundDoseCallback.DESCRIPTOR);
                    obtain.writeFloat(f);
                    obtain.writeTypedArray(soundDoseRecordArr, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
