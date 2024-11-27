package android.media.tv;

/* loaded from: classes2.dex */
public interface ITvInputHardwareCallback extends android.os.IInterface {
    void onReleased() throws android.os.RemoteException;

    void onStreamConfigChanged(android.media.tv.TvStreamConfig[] tvStreamConfigArr) throws android.os.RemoteException;

    public static class Default implements android.media.tv.ITvInputHardwareCallback {
        @Override // android.media.tv.ITvInputHardwareCallback
        public void onReleased() throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputHardwareCallback
        public void onStreamConfigChanged(android.media.tv.TvStreamConfig[] tvStreamConfigArr) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.media.tv.ITvInputHardwareCallback {
        public static final java.lang.String DESCRIPTOR = "android.media.tv.ITvInputHardwareCallback";
        static final int TRANSACTION_onReleased = 1;
        static final int TRANSACTION_onStreamConfigChanged = 2;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.media.tv.ITvInputHardwareCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.media.tv.ITvInputHardwareCallback)) {
                return (android.media.tv.ITvInputHardwareCallback) queryLocalInterface;
            }
            return new android.media.tv.ITvInputHardwareCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onReleased";
                case 2:
                    return "onStreamConfigChanged";
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
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    onReleased();
                    return true;
                case 2:
                    android.media.tv.TvStreamConfig[] tvStreamConfigArr = (android.media.tv.TvStreamConfig[]) parcel.createTypedArray(android.media.tv.TvStreamConfig.CREATOR);
                    parcel.enforceNoDataAvail();
                    onStreamConfigChanged(tvStreamConfigArr);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.media.tv.ITvInputHardwareCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.media.tv.ITvInputHardwareCallback.Stub.DESCRIPTOR;
            }

            @Override // android.media.tv.ITvInputHardwareCallback
            public void onReleased() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputHardwareCallback.Stub.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputHardwareCallback
            public void onStreamConfigChanged(android.media.tv.TvStreamConfig[] tvStreamConfigArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputHardwareCallback.Stub.DESCRIPTOR);
                    obtain.writeTypedArray(tvStreamConfigArr, 0);
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
