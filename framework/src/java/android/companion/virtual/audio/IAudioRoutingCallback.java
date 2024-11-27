package android.companion.virtual.audio;

/* loaded from: classes.dex */
public interface IAudioRoutingCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.companion.virtual.audio.IAudioRoutingCallback";

    void onAppsNeedingAudioRoutingChanged(int[] iArr) throws android.os.RemoteException;

    public static class Default implements android.companion.virtual.audio.IAudioRoutingCallback {
        @Override // android.companion.virtual.audio.IAudioRoutingCallback
        public void onAppsNeedingAudioRoutingChanged(int[] iArr) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.companion.virtual.audio.IAudioRoutingCallback {
        static final int TRANSACTION_onAppsNeedingAudioRoutingChanged = 1;

        public Stub() {
            attachInterface(this, android.companion.virtual.audio.IAudioRoutingCallback.DESCRIPTOR);
        }

        public static android.companion.virtual.audio.IAudioRoutingCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.companion.virtual.audio.IAudioRoutingCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.companion.virtual.audio.IAudioRoutingCallback)) {
                return (android.companion.virtual.audio.IAudioRoutingCallback) queryLocalInterface;
            }
            return new android.companion.virtual.audio.IAudioRoutingCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onAppsNeedingAudioRoutingChanged";
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
                parcel.enforceInterface(android.companion.virtual.audio.IAudioRoutingCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.companion.virtual.audio.IAudioRoutingCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int[] createIntArray = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    onAppsNeedingAudioRoutingChanged(createIntArray);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.companion.virtual.audio.IAudioRoutingCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.companion.virtual.audio.IAudioRoutingCallback.DESCRIPTOR;
            }

            @Override // android.companion.virtual.audio.IAudioRoutingCallback
            public void onAppsNeedingAudioRoutingChanged(int[] iArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.companion.virtual.audio.IAudioRoutingCallback.DESCRIPTOR);
                    obtain.writeIntArray(iArr);
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
