package android.media;

/* loaded from: classes2.dex */
public interface IPlayer extends android.os.IInterface {
    void applyVolumeShaper(android.media.VolumeShaperConfiguration volumeShaperConfiguration, android.media.VolumeShaperOperation volumeShaperOperation) throws android.os.RemoteException;

    void pause() throws android.os.RemoteException;

    void setPan(float f) throws android.os.RemoteException;

    void setStartDelayMs(int i) throws android.os.RemoteException;

    void setVolume(float f) throws android.os.RemoteException;

    void start() throws android.os.RemoteException;

    void stop() throws android.os.RemoteException;

    public static class Default implements android.media.IPlayer {
        @Override // android.media.IPlayer
        public void start() throws android.os.RemoteException {
        }

        @Override // android.media.IPlayer
        public void pause() throws android.os.RemoteException {
        }

        @Override // android.media.IPlayer
        public void stop() throws android.os.RemoteException {
        }

        @Override // android.media.IPlayer
        public void setVolume(float f) throws android.os.RemoteException {
        }

        @Override // android.media.IPlayer
        public void setPan(float f) throws android.os.RemoteException {
        }

        @Override // android.media.IPlayer
        public void setStartDelayMs(int i) throws android.os.RemoteException {
        }

        @Override // android.media.IPlayer
        public void applyVolumeShaper(android.media.VolumeShaperConfiguration volumeShaperConfiguration, android.media.VolumeShaperOperation volumeShaperOperation) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.media.IPlayer {
        public static final java.lang.String DESCRIPTOR = "android.media.IPlayer";
        static final int TRANSACTION_applyVolumeShaper = 7;
        static final int TRANSACTION_pause = 2;
        static final int TRANSACTION_setPan = 5;
        static final int TRANSACTION_setStartDelayMs = 6;
        static final int TRANSACTION_setVolume = 4;
        static final int TRANSACTION_start = 1;
        static final int TRANSACTION_stop = 3;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.media.IPlayer asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.media.IPlayer)) {
                return (android.media.IPlayer) queryLocalInterface;
            }
            return new android.media.IPlayer.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "start";
                case 2:
                    return android.media.tv.interactive.TvInteractiveAppService.TIME_SHIFT_COMMAND_TYPE_PAUSE;
                case 3:
                    return "stop";
                case 4:
                    return "setVolume";
                case 5:
                    return "setPan";
                case 6:
                    return "setStartDelayMs";
                case 7:
                    return "applyVolumeShaper";
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
                    start();
                    return true;
                case 2:
                    pause();
                    return true;
                case 3:
                    stop();
                    return true;
                case 4:
                    float readFloat = parcel.readFloat();
                    parcel.enforceNoDataAvail();
                    setVolume(readFloat);
                    return true;
                case 5:
                    float readFloat2 = parcel.readFloat();
                    parcel.enforceNoDataAvail();
                    setPan(readFloat2);
                    return true;
                case 6:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setStartDelayMs(readInt);
                    return true;
                case 7:
                    android.media.VolumeShaperConfiguration volumeShaperConfiguration = (android.media.VolumeShaperConfiguration) parcel.readTypedObject(android.media.VolumeShaperConfiguration.CREATOR);
                    android.media.VolumeShaperOperation volumeShaperOperation = (android.media.VolumeShaperOperation) parcel.readTypedObject(android.media.VolumeShaperOperation.CREATOR);
                    parcel.enforceNoDataAvail();
                    applyVolumeShaper(volumeShaperConfiguration, volumeShaperOperation);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.media.IPlayer {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.media.IPlayer.Stub.DESCRIPTOR;
            }

            @Override // android.media.IPlayer
            public void start() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.IPlayer.Stub.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IPlayer
            public void pause() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.IPlayer.Stub.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IPlayer
            public void stop() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.IPlayer.Stub.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IPlayer
            public void setVolume(float f) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.IPlayer.Stub.DESCRIPTOR);
                    obtain.writeFloat(f);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IPlayer
            public void setPan(float f) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.IPlayer.Stub.DESCRIPTOR);
                    obtain.writeFloat(f);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IPlayer
            public void setStartDelayMs(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.IPlayer.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IPlayer
            public void applyVolumeShaper(android.media.VolumeShaperConfiguration volumeShaperConfiguration, android.media.VolumeShaperOperation volumeShaperOperation) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.IPlayer.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(volumeShaperConfiguration, 0);
                    obtain.writeTypedObject(volumeShaperOperation, 0);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 6;
        }
    }
}
