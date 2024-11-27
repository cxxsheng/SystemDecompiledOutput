package android.media;

/* loaded from: classes2.dex */
public interface IRingtonePlayer extends android.os.IInterface {
    java.lang.String getTitle(android.net.Uri uri) throws android.os.RemoteException;

    boolean isPlaying(android.os.IBinder iBinder) throws android.os.RemoteException;

    android.os.ParcelFileDescriptor openRingtone(android.net.Uri uri) throws android.os.RemoteException;

    void play(android.os.IBinder iBinder, android.net.Uri uri, android.media.AudioAttributes audioAttributes, float f, boolean z) throws android.os.RemoteException;

    void playAsync(android.net.Uri uri, android.os.UserHandle userHandle, boolean z, android.media.AudioAttributes audioAttributes, float f) throws android.os.RemoteException;

    void playWithVolumeShaping(android.os.IBinder iBinder, android.net.Uri uri, android.media.AudioAttributes audioAttributes, float f, boolean z, android.media.VolumeShaper.Configuration configuration) throws android.os.RemoteException;

    void setPlaybackProperties(android.os.IBinder iBinder, float f, boolean z, boolean z2) throws android.os.RemoteException;

    void stop(android.os.IBinder iBinder) throws android.os.RemoteException;

    void stopAsync() throws android.os.RemoteException;

    public static class Default implements android.media.IRingtonePlayer {
        @Override // android.media.IRingtonePlayer
        public void play(android.os.IBinder iBinder, android.net.Uri uri, android.media.AudioAttributes audioAttributes, float f, boolean z) throws android.os.RemoteException {
        }

        @Override // android.media.IRingtonePlayer
        public void playWithVolumeShaping(android.os.IBinder iBinder, android.net.Uri uri, android.media.AudioAttributes audioAttributes, float f, boolean z, android.media.VolumeShaper.Configuration configuration) throws android.os.RemoteException {
        }

        @Override // android.media.IRingtonePlayer
        public void stop(android.os.IBinder iBinder) throws android.os.RemoteException {
        }

        @Override // android.media.IRingtonePlayer
        public boolean isPlaying(android.os.IBinder iBinder) throws android.os.RemoteException {
            return false;
        }

        @Override // android.media.IRingtonePlayer
        public void setPlaybackProperties(android.os.IBinder iBinder, float f, boolean z, boolean z2) throws android.os.RemoteException {
        }

        @Override // android.media.IRingtonePlayer
        public void playAsync(android.net.Uri uri, android.os.UserHandle userHandle, boolean z, android.media.AudioAttributes audioAttributes, float f) throws android.os.RemoteException {
        }

        @Override // android.media.IRingtonePlayer
        public void stopAsync() throws android.os.RemoteException {
        }

        @Override // android.media.IRingtonePlayer
        public java.lang.String getTitle(android.net.Uri uri) throws android.os.RemoteException {
            return null;
        }

        @Override // android.media.IRingtonePlayer
        public android.os.ParcelFileDescriptor openRingtone(android.net.Uri uri) throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.media.IRingtonePlayer {
        public static final java.lang.String DESCRIPTOR = "android.media.IRingtonePlayer";
        static final int TRANSACTION_getTitle = 8;
        static final int TRANSACTION_isPlaying = 4;
        static final int TRANSACTION_openRingtone = 9;
        static final int TRANSACTION_play = 1;
        static final int TRANSACTION_playAsync = 6;
        static final int TRANSACTION_playWithVolumeShaping = 2;
        static final int TRANSACTION_setPlaybackProperties = 5;
        static final int TRANSACTION_stop = 3;
        static final int TRANSACTION_stopAsync = 7;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.media.IRingtonePlayer asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.media.IRingtonePlayer)) {
                return (android.media.IRingtonePlayer) queryLocalInterface;
            }
            return new android.media.IRingtonePlayer.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return android.media.tv.interactive.TvInteractiveAppService.TIME_SHIFT_COMMAND_TYPE_PLAY;
                case 2:
                    return "playWithVolumeShaping";
                case 3:
                    return "stop";
                case 4:
                    return "isPlaying";
                case 5:
                    return "setPlaybackProperties";
                case 6:
                    return "playAsync";
                case 7:
                    return "stopAsync";
                case 8:
                    return "getTitle";
                case 9:
                    return "openRingtone";
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
                    android.os.IBinder readStrongBinder = parcel.readStrongBinder();
                    android.net.Uri uri = (android.net.Uri) parcel.readTypedObject(android.net.Uri.CREATOR);
                    android.media.AudioAttributes audioAttributes = (android.media.AudioAttributes) parcel.readTypedObject(android.media.AudioAttributes.CREATOR);
                    float readFloat = parcel.readFloat();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    play(readStrongBinder, uri, audioAttributes, readFloat, readBoolean);
                    return true;
                case 2:
                    android.os.IBinder readStrongBinder2 = parcel.readStrongBinder();
                    android.net.Uri uri2 = (android.net.Uri) parcel.readTypedObject(android.net.Uri.CREATOR);
                    android.media.AudioAttributes audioAttributes2 = (android.media.AudioAttributes) parcel.readTypedObject(android.media.AudioAttributes.CREATOR);
                    float readFloat2 = parcel.readFloat();
                    boolean readBoolean2 = parcel.readBoolean();
                    android.media.VolumeShaper.Configuration configuration = (android.media.VolumeShaper.Configuration) parcel.readTypedObject(android.media.VolumeShaper.Configuration.CREATOR);
                    parcel.enforceNoDataAvail();
                    playWithVolumeShaping(readStrongBinder2, uri2, audioAttributes2, readFloat2, readBoolean2, configuration);
                    return true;
                case 3:
                    android.os.IBinder readStrongBinder3 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    stop(readStrongBinder3);
                    return true;
                case 4:
                    android.os.IBinder readStrongBinder4 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    boolean isPlaying = isPlaying(readStrongBinder4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isPlaying);
                    return true;
                case 5:
                    android.os.IBinder readStrongBinder5 = parcel.readStrongBinder();
                    float readFloat3 = parcel.readFloat();
                    boolean readBoolean3 = parcel.readBoolean();
                    boolean readBoolean4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setPlaybackProperties(readStrongBinder5, readFloat3, readBoolean3, readBoolean4);
                    return true;
                case 6:
                    android.net.Uri uri3 = (android.net.Uri) parcel.readTypedObject(android.net.Uri.CREATOR);
                    android.os.UserHandle userHandle = (android.os.UserHandle) parcel.readTypedObject(android.os.UserHandle.CREATOR);
                    boolean readBoolean5 = parcel.readBoolean();
                    android.media.AudioAttributes audioAttributes3 = (android.media.AudioAttributes) parcel.readTypedObject(android.media.AudioAttributes.CREATOR);
                    float readFloat4 = parcel.readFloat();
                    parcel.enforceNoDataAvail();
                    playAsync(uri3, userHandle, readBoolean5, audioAttributes3, readFloat4);
                    return true;
                case 7:
                    stopAsync();
                    return true;
                case 8:
                    android.net.Uri uri4 = (android.net.Uri) parcel.readTypedObject(android.net.Uri.CREATOR);
                    parcel.enforceNoDataAvail();
                    java.lang.String title = getTitle(uri4);
                    parcel2.writeNoException();
                    parcel2.writeString(title);
                    return true;
                case 9:
                    android.net.Uri uri5 = (android.net.Uri) parcel.readTypedObject(android.net.Uri.CREATOR);
                    parcel.enforceNoDataAvail();
                    android.os.ParcelFileDescriptor openRingtone = openRingtone(uri5);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(openRingtone, 1);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.media.IRingtonePlayer {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.media.IRingtonePlayer.Stub.DESCRIPTOR;
            }

            @Override // android.media.IRingtonePlayer
            public void play(android.os.IBinder iBinder, android.net.Uri uri, android.media.AudioAttributes audioAttributes, float f, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.IRingtonePlayer.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeTypedObject(audioAttributes, 0);
                    obtain.writeFloat(f);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IRingtonePlayer
            public void playWithVolumeShaping(android.os.IBinder iBinder, android.net.Uri uri, android.media.AudioAttributes audioAttributes, float f, boolean z, android.media.VolumeShaper.Configuration configuration) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.IRingtonePlayer.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeTypedObject(audioAttributes, 0);
                    obtain.writeFloat(f);
                    obtain.writeBoolean(z);
                    obtain.writeTypedObject(configuration, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IRingtonePlayer
            public void stop(android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.IRingtonePlayer.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IRingtonePlayer
            public boolean isPlaying(android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IRingtonePlayer.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IRingtonePlayer
            public void setPlaybackProperties(android.os.IBinder iBinder, float f, boolean z, boolean z2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.IRingtonePlayer.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeFloat(f);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IRingtonePlayer
            public void playAsync(android.net.Uri uri, android.os.UserHandle userHandle, boolean z, android.media.AudioAttributes audioAttributes, float f) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.IRingtonePlayer.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeTypedObject(userHandle, 0);
                    obtain.writeBoolean(z);
                    obtain.writeTypedObject(audioAttributes, 0);
                    obtain.writeFloat(f);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IRingtonePlayer
            public void stopAsync() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.IRingtonePlayer.Stub.DESCRIPTOR);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IRingtonePlayer
            public java.lang.String getTitle(android.net.Uri uri) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IRingtonePlayer.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(uri, 0);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IRingtonePlayer
            public android.os.ParcelFileDescriptor openRingtone(android.net.Uri uri) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IRingtonePlayer.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(uri, 0);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.os.ParcelFileDescriptor) obtain2.readTypedObject(android.os.ParcelFileDescriptor.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 8;
        }
    }
}
