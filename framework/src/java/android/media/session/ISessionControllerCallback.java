package android.media.session;

/* loaded from: classes2.dex */
public interface ISessionControllerCallback extends android.os.IInterface {
    void onEvent(java.lang.String str, android.os.Bundle bundle) throws android.os.RemoteException;

    void onExtrasChanged(android.os.Bundle bundle) throws android.os.RemoteException;

    void onMetadataChanged(android.media.MediaMetadata mediaMetadata) throws android.os.RemoteException;

    void onPlaybackStateChanged(android.media.session.PlaybackState playbackState) throws android.os.RemoteException;

    void onQueueChanged(android.content.pm.ParceledListSlice parceledListSlice) throws android.os.RemoteException;

    void onQueueTitleChanged(java.lang.CharSequence charSequence) throws android.os.RemoteException;

    void onSessionDestroyed() throws android.os.RemoteException;

    void onVolumeInfoChanged(android.media.session.MediaController.PlaybackInfo playbackInfo) throws android.os.RemoteException;

    public static class Default implements android.media.session.ISessionControllerCallback {
        @Override // android.media.session.ISessionControllerCallback
        public void onEvent(java.lang.String str, android.os.Bundle bundle) throws android.os.RemoteException {
        }

        @Override // android.media.session.ISessionControllerCallback
        public void onSessionDestroyed() throws android.os.RemoteException {
        }

        @Override // android.media.session.ISessionControllerCallback
        public void onPlaybackStateChanged(android.media.session.PlaybackState playbackState) throws android.os.RemoteException {
        }

        @Override // android.media.session.ISessionControllerCallback
        public void onMetadataChanged(android.media.MediaMetadata mediaMetadata) throws android.os.RemoteException {
        }

        @Override // android.media.session.ISessionControllerCallback
        public void onQueueChanged(android.content.pm.ParceledListSlice parceledListSlice) throws android.os.RemoteException {
        }

        @Override // android.media.session.ISessionControllerCallback
        public void onQueueTitleChanged(java.lang.CharSequence charSequence) throws android.os.RemoteException {
        }

        @Override // android.media.session.ISessionControllerCallback
        public void onExtrasChanged(android.os.Bundle bundle) throws android.os.RemoteException {
        }

        @Override // android.media.session.ISessionControllerCallback
        public void onVolumeInfoChanged(android.media.session.MediaController.PlaybackInfo playbackInfo) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.media.session.ISessionControllerCallback {
        public static final java.lang.String DESCRIPTOR = "android.media.session.ISessionControllerCallback";
        static final int TRANSACTION_onEvent = 1;
        static final int TRANSACTION_onExtrasChanged = 7;
        static final int TRANSACTION_onMetadataChanged = 4;
        static final int TRANSACTION_onPlaybackStateChanged = 3;
        static final int TRANSACTION_onQueueChanged = 5;
        static final int TRANSACTION_onQueueTitleChanged = 6;
        static final int TRANSACTION_onSessionDestroyed = 2;
        static final int TRANSACTION_onVolumeInfoChanged = 8;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.media.session.ISessionControllerCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.media.session.ISessionControllerCallback)) {
                return (android.media.session.ISessionControllerCallback) queryLocalInterface;
            }
            return new android.media.session.ISessionControllerCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onEvent";
                case 2:
                    return "onSessionDestroyed";
                case 3:
                    return "onPlaybackStateChanged";
                case 4:
                    return "onMetadataChanged";
                case 5:
                    return "onQueueChanged";
                case 6:
                    return "onQueueTitleChanged";
                case 7:
                    return "onExtrasChanged";
                case 8:
                    return "onVolumeInfoChanged";
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
                    java.lang.String readString = parcel.readString();
                    android.os.Bundle bundle = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    onEvent(readString, bundle);
                    return true;
                case 2:
                    onSessionDestroyed();
                    return true;
                case 3:
                    android.media.session.PlaybackState playbackState = (android.media.session.PlaybackState) parcel.readTypedObject(android.media.session.PlaybackState.CREATOR);
                    parcel.enforceNoDataAvail();
                    onPlaybackStateChanged(playbackState);
                    return true;
                case 4:
                    android.media.MediaMetadata mediaMetadata = (android.media.MediaMetadata) parcel.readTypedObject(android.media.MediaMetadata.CREATOR);
                    parcel.enforceNoDataAvail();
                    onMetadataChanged(mediaMetadata);
                    return true;
                case 5:
                    android.content.pm.ParceledListSlice parceledListSlice = (android.content.pm.ParceledListSlice) parcel.readTypedObject(android.content.pm.ParceledListSlice.CREATOR);
                    parcel.enforceNoDataAvail();
                    onQueueChanged(parceledListSlice);
                    return true;
                case 6:
                    java.lang.CharSequence charSequence = (java.lang.CharSequence) parcel.readTypedObject(android.text.TextUtils.CHAR_SEQUENCE_CREATOR);
                    parcel.enforceNoDataAvail();
                    onQueueTitleChanged(charSequence);
                    return true;
                case 7:
                    android.os.Bundle bundle2 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    onExtrasChanged(bundle2);
                    return true;
                case 8:
                    android.media.session.MediaController.PlaybackInfo playbackInfo = (android.media.session.MediaController.PlaybackInfo) parcel.readTypedObject(android.media.session.MediaController.PlaybackInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    onVolumeInfoChanged(playbackInfo);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.media.session.ISessionControllerCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.media.session.ISessionControllerCallback.Stub.DESCRIPTOR;
            }

            @Override // android.media.session.ISessionControllerCallback
            public void onEvent(java.lang.String str, android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.session.ISessionControllerCallback.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionControllerCallback
            public void onSessionDestroyed() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.session.ISessionControllerCallback.Stub.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionControllerCallback
            public void onPlaybackStateChanged(android.media.session.PlaybackState playbackState) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.session.ISessionControllerCallback.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(playbackState, 0);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionControllerCallback
            public void onMetadataChanged(android.media.MediaMetadata mediaMetadata) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.session.ISessionControllerCallback.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(mediaMetadata, 0);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionControllerCallback
            public void onQueueChanged(android.content.pm.ParceledListSlice parceledListSlice) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.session.ISessionControllerCallback.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(parceledListSlice, 0);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionControllerCallback
            public void onQueueTitleChanged(java.lang.CharSequence charSequence) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.session.ISessionControllerCallback.Stub.DESCRIPTOR);
                    if (charSequence != null) {
                        obtain.writeInt(1);
                        android.text.TextUtils.writeToParcel(charSequence, obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionControllerCallback
            public void onExtrasChanged(android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.session.ISessionControllerCallback.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionControllerCallback
            public void onVolumeInfoChanged(android.media.session.MediaController.PlaybackInfo playbackInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.session.ISessionControllerCallback.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(playbackInfo, 0);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 7;
        }
    }
}
