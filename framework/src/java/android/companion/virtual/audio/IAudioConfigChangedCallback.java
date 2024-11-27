package android.companion.virtual.audio;

/* loaded from: classes.dex */
public interface IAudioConfigChangedCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.companion.virtual.audio.IAudioConfigChangedCallback";

    void onPlaybackConfigChanged(java.util.List<android.media.AudioPlaybackConfiguration> list) throws android.os.RemoteException;

    void onRecordingConfigChanged(java.util.List<android.media.AudioRecordingConfiguration> list) throws android.os.RemoteException;

    public static class Default implements android.companion.virtual.audio.IAudioConfigChangedCallback {
        @Override // android.companion.virtual.audio.IAudioConfigChangedCallback
        public void onPlaybackConfigChanged(java.util.List<android.media.AudioPlaybackConfiguration> list) throws android.os.RemoteException {
        }

        @Override // android.companion.virtual.audio.IAudioConfigChangedCallback
        public void onRecordingConfigChanged(java.util.List<android.media.AudioRecordingConfiguration> list) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.companion.virtual.audio.IAudioConfigChangedCallback {
        static final int TRANSACTION_onPlaybackConfigChanged = 1;
        static final int TRANSACTION_onRecordingConfigChanged = 2;

        public Stub() {
            attachInterface(this, android.companion.virtual.audio.IAudioConfigChangedCallback.DESCRIPTOR);
        }

        public static android.companion.virtual.audio.IAudioConfigChangedCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.companion.virtual.audio.IAudioConfigChangedCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.companion.virtual.audio.IAudioConfigChangedCallback)) {
                return (android.companion.virtual.audio.IAudioConfigChangedCallback) queryLocalInterface;
            }
            return new android.companion.virtual.audio.IAudioConfigChangedCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onPlaybackConfigChanged";
                case 2:
                    return "onRecordingConfigChanged";
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
                parcel.enforceInterface(android.companion.virtual.audio.IAudioConfigChangedCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.companion.virtual.audio.IAudioConfigChangedCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    java.util.ArrayList createTypedArrayList = parcel.createTypedArrayList(android.media.AudioPlaybackConfiguration.CREATOR);
                    parcel.enforceNoDataAvail();
                    onPlaybackConfigChanged(createTypedArrayList);
                    return true;
                case 2:
                    java.util.ArrayList createTypedArrayList2 = parcel.createTypedArrayList(android.media.AudioRecordingConfiguration.CREATOR);
                    parcel.enforceNoDataAvail();
                    onRecordingConfigChanged(createTypedArrayList2);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.companion.virtual.audio.IAudioConfigChangedCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.companion.virtual.audio.IAudioConfigChangedCallback.DESCRIPTOR;
            }

            @Override // android.companion.virtual.audio.IAudioConfigChangedCallback
            public void onPlaybackConfigChanged(java.util.List<android.media.AudioPlaybackConfiguration> list) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.companion.virtual.audio.IAudioConfigChangedCallback.DESCRIPTOR);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.companion.virtual.audio.IAudioConfigChangedCallback
            public void onRecordingConfigChanged(java.util.List<android.media.AudioRecordingConfiguration> list) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.companion.virtual.audio.IAudioConfigChangedCallback.DESCRIPTOR);
                    obtain.writeTypedList(list, 0);
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
