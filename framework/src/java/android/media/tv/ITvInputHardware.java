package android.media.tv;

/* loaded from: classes2.dex */
public interface ITvInputHardware extends android.os.IInterface {
    void overrideAudioSink(int i, java.lang.String str, int i2, int i3, int i4) throws android.os.RemoteException;

    void setStreamVolume(float f) throws android.os.RemoteException;

    boolean setSurface(android.view.Surface surface, android.media.tv.TvStreamConfig tvStreamConfig) throws android.os.RemoteException;

    public static class Default implements android.media.tv.ITvInputHardware {
        @Override // android.media.tv.ITvInputHardware
        public boolean setSurface(android.view.Surface surface, android.media.tv.TvStreamConfig tvStreamConfig) throws android.os.RemoteException {
            return false;
        }

        @Override // android.media.tv.ITvInputHardware
        public void setStreamVolume(float f) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputHardware
        public void overrideAudioSink(int i, java.lang.String str, int i2, int i3, int i4) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.media.tv.ITvInputHardware {
        public static final java.lang.String DESCRIPTOR = "android.media.tv.ITvInputHardware";
        static final int TRANSACTION_overrideAudioSink = 3;
        static final int TRANSACTION_setStreamVolume = 2;
        static final int TRANSACTION_setSurface = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.media.tv.ITvInputHardware asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.media.tv.ITvInputHardware)) {
                return (android.media.tv.ITvInputHardware) queryLocalInterface;
            }
            return new android.media.tv.ITvInputHardware.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "setSurface";
                case 2:
                    return "setStreamVolume";
                case 3:
                    return "overrideAudioSink";
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
                    android.view.Surface surface = (android.view.Surface) parcel.readTypedObject(android.view.Surface.CREATOR);
                    android.media.tv.TvStreamConfig tvStreamConfig = (android.media.tv.TvStreamConfig) parcel.readTypedObject(android.media.tv.TvStreamConfig.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean surface2 = setSurface(surface, tvStreamConfig);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(surface2);
                    return true;
                case 2:
                    float readFloat = parcel.readFloat();
                    parcel.enforceNoDataAvail();
                    setStreamVolume(readFloat);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    int readInt = parcel.readInt();
                    java.lang.String readString = parcel.readString();
                    int readInt2 = parcel.readInt();
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    overrideAudioSink(readInt, readString, readInt2, readInt3, readInt4);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.media.tv.ITvInputHardware {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.media.tv.ITvInputHardware.Stub.DESCRIPTOR;
            }

            @Override // android.media.tv.ITvInputHardware
            public boolean setSurface(android.view.Surface surface, android.media.tv.TvStreamConfig tvStreamConfig) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputHardware.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(surface, 0);
                    obtain.writeTypedObject(tvStreamConfig, 0);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputHardware
            public void setStreamVolume(float f) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputHardware.Stub.DESCRIPTOR);
                    obtain.writeFloat(f);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputHardware
            public void overrideAudioSink(int i, java.lang.String str, int i2, int i3, int i4) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputHardware.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }
    }
}
