package android.media;

/* loaded from: classes2.dex */
public interface ISpatializer extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.media.ISpatializer";

    byte getActualHeadTrackingMode() throws android.os.RemoteException;

    byte getLevel() throws android.os.RemoteException;

    int getOutput() throws android.os.RemoteException;

    void getParameter(int i, byte[] bArr) throws android.os.RemoteException;

    byte[] getSupportedHeadTrackingModes() throws android.os.RemoteException;

    byte[] getSupportedLevels() throws android.os.RemoteException;

    byte[] getSupportedModes() throws android.os.RemoteException;

    boolean isHeadTrackingSupported() throws android.os.RemoteException;

    void recenterHeadTracker() throws android.os.RemoteException;

    void registerHeadTrackingCallback(android.media.ISpatializerHeadTrackingCallback iSpatializerHeadTrackingCallback) throws android.os.RemoteException;

    void release() throws android.os.RemoteException;

    void setDesiredHeadTrackingMode(byte b) throws android.os.RemoteException;

    void setDisplayOrientation(float f) throws android.os.RemoteException;

    void setFoldState(boolean z) throws android.os.RemoteException;

    void setGlobalTransform(float[] fArr) throws android.os.RemoteException;

    void setHeadSensor(int i) throws android.os.RemoteException;

    void setHingeAngle(float f) throws android.os.RemoteException;

    void setLevel(byte b) throws android.os.RemoteException;

    void setParameter(int i, byte[] bArr) throws android.os.RemoteException;

    void setScreenSensor(int i) throws android.os.RemoteException;

    public static class Default implements android.media.ISpatializer {
        @Override // android.media.ISpatializer
        public void release() throws android.os.RemoteException {
        }

        @Override // android.media.ISpatializer
        public byte[] getSupportedLevels() throws android.os.RemoteException {
            return null;
        }

        @Override // android.media.ISpatializer
        public void setLevel(byte b) throws android.os.RemoteException {
        }

        @Override // android.media.ISpatializer
        public byte getLevel() throws android.os.RemoteException {
            return (byte) 0;
        }

        @Override // android.media.ISpatializer
        public boolean isHeadTrackingSupported() throws android.os.RemoteException {
            return false;
        }

        @Override // android.media.ISpatializer
        public byte[] getSupportedHeadTrackingModes() throws android.os.RemoteException {
            return null;
        }

        @Override // android.media.ISpatializer
        public void setDesiredHeadTrackingMode(byte b) throws android.os.RemoteException {
        }

        @Override // android.media.ISpatializer
        public byte getActualHeadTrackingMode() throws android.os.RemoteException {
            return (byte) 0;
        }

        @Override // android.media.ISpatializer
        public void recenterHeadTracker() throws android.os.RemoteException {
        }

        @Override // android.media.ISpatializer
        public void setGlobalTransform(float[] fArr) throws android.os.RemoteException {
        }

        @Override // android.media.ISpatializer
        public void setHeadSensor(int i) throws android.os.RemoteException {
        }

        @Override // android.media.ISpatializer
        public void setScreenSensor(int i) throws android.os.RemoteException {
        }

        @Override // android.media.ISpatializer
        public void setDisplayOrientation(float f) throws android.os.RemoteException {
        }

        @Override // android.media.ISpatializer
        public void setHingeAngle(float f) throws android.os.RemoteException {
        }

        @Override // android.media.ISpatializer
        public void setFoldState(boolean z) throws android.os.RemoteException {
        }

        @Override // android.media.ISpatializer
        public byte[] getSupportedModes() throws android.os.RemoteException {
            return null;
        }

        @Override // android.media.ISpatializer
        public void registerHeadTrackingCallback(android.media.ISpatializerHeadTrackingCallback iSpatializerHeadTrackingCallback) throws android.os.RemoteException {
        }

        @Override // android.media.ISpatializer
        public void setParameter(int i, byte[] bArr) throws android.os.RemoteException {
        }

        @Override // android.media.ISpatializer
        public void getParameter(int i, byte[] bArr) throws android.os.RemoteException {
        }

        @Override // android.media.ISpatializer
        public int getOutput() throws android.os.RemoteException {
            return 0;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.media.ISpatializer {
        static final int TRANSACTION_getActualHeadTrackingMode = 8;
        static final int TRANSACTION_getLevel = 4;
        static final int TRANSACTION_getOutput = 20;
        static final int TRANSACTION_getParameter = 19;
        static final int TRANSACTION_getSupportedHeadTrackingModes = 6;
        static final int TRANSACTION_getSupportedLevels = 2;
        static final int TRANSACTION_getSupportedModes = 16;
        static final int TRANSACTION_isHeadTrackingSupported = 5;
        static final int TRANSACTION_recenterHeadTracker = 9;
        static final int TRANSACTION_registerHeadTrackingCallback = 17;
        static final int TRANSACTION_release = 1;
        static final int TRANSACTION_setDesiredHeadTrackingMode = 7;
        static final int TRANSACTION_setDisplayOrientation = 13;
        static final int TRANSACTION_setFoldState = 15;
        static final int TRANSACTION_setGlobalTransform = 10;
        static final int TRANSACTION_setHeadSensor = 11;
        static final int TRANSACTION_setHingeAngle = 14;
        static final int TRANSACTION_setLevel = 3;
        static final int TRANSACTION_setParameter = 18;
        static final int TRANSACTION_setScreenSensor = 12;

        public Stub() {
            attachInterface(this, android.media.ISpatializer.DESCRIPTOR);
        }

        public static android.media.ISpatializer asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.media.ISpatializer.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.media.ISpatializer)) {
                return (android.media.ISpatializer) queryLocalInterface;
            }
            return new android.media.ISpatializer.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, android.os.Parcel parcel, android.os.Parcel parcel2, int i2) throws android.os.RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(android.media.ISpatializer.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.media.ISpatializer.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    release();
                    parcel2.writeNoException();
                    return true;
                case 2:
                    byte[] supportedLevels = getSupportedLevels();
                    parcel2.writeNoException();
                    parcel2.writeByteArray(supportedLevels);
                    return true;
                case 3:
                    byte readByte = parcel.readByte();
                    parcel.enforceNoDataAvail();
                    setLevel(readByte);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    byte level = getLevel();
                    parcel2.writeNoException();
                    parcel2.writeByte(level);
                    return true;
                case 5:
                    boolean isHeadTrackingSupported = isHeadTrackingSupported();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isHeadTrackingSupported);
                    return true;
                case 6:
                    byte[] supportedHeadTrackingModes = getSupportedHeadTrackingModes();
                    parcel2.writeNoException();
                    parcel2.writeByteArray(supportedHeadTrackingModes);
                    return true;
                case 7:
                    byte readByte2 = parcel.readByte();
                    parcel.enforceNoDataAvail();
                    setDesiredHeadTrackingMode(readByte2);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    byte actualHeadTrackingMode = getActualHeadTrackingMode();
                    parcel2.writeNoException();
                    parcel2.writeByte(actualHeadTrackingMode);
                    return true;
                case 9:
                    recenterHeadTracker();
                    parcel2.writeNoException();
                    return true;
                case 10:
                    float[] createFloatArray = parcel.createFloatArray();
                    parcel.enforceNoDataAvail();
                    setGlobalTransform(createFloatArray);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setHeadSensor(readInt);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setScreenSensor(readInt2);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    float readFloat = parcel.readFloat();
                    parcel.enforceNoDataAvail();
                    setDisplayOrientation(readFloat);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    float readFloat2 = parcel.readFloat();
                    parcel.enforceNoDataAvail();
                    setHingeAngle(readFloat2);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setFoldState(readBoolean);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    byte[] supportedModes = getSupportedModes();
                    parcel2.writeNoException();
                    parcel2.writeByteArray(supportedModes);
                    return true;
                case 17:
                    android.media.ISpatializerHeadTrackingCallback asInterface = android.media.ISpatializerHeadTrackingCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerHeadTrackingCallback(asInterface);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    int readInt3 = parcel.readInt();
                    byte[] createByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    setParameter(readInt3, createByteArray);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    int readInt4 = parcel.readInt();
                    byte[] createByteArray2 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    getParameter(readInt4, createByteArray2);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(createByteArray2);
                    return true;
                case 20:
                    int output = getOutput();
                    parcel2.writeNoException();
                    parcel2.writeInt(output);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.media.ISpatializer {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.media.ISpatializer.DESCRIPTOR;
            }

            @Override // android.media.ISpatializer
            public void release() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.ISpatializer.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.ISpatializer
            public byte[] getSupportedLevels() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.ISpatializer.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.ISpatializer
            public void setLevel(byte b) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.ISpatializer.DESCRIPTOR);
                    obtain.writeByte(b);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.ISpatializer
            public byte getLevel() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.ISpatializer.DESCRIPTOR);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readByte();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.ISpatializer
            public boolean isHeadTrackingSupported() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.ISpatializer.DESCRIPTOR);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.ISpatializer
            public byte[] getSupportedHeadTrackingModes() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.ISpatializer.DESCRIPTOR);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.ISpatializer
            public void setDesiredHeadTrackingMode(byte b) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.ISpatializer.DESCRIPTOR);
                    obtain.writeByte(b);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.ISpatializer
            public byte getActualHeadTrackingMode() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.ISpatializer.DESCRIPTOR);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readByte();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.ISpatializer
            public void recenterHeadTracker() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.ISpatializer.DESCRIPTOR);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.ISpatializer
            public void setGlobalTransform(float[] fArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.ISpatializer.DESCRIPTOR);
                    obtain.writeFloatArray(fArr);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.ISpatializer
            public void setHeadSensor(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.ISpatializer.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.ISpatializer
            public void setScreenSensor(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.ISpatializer.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.ISpatializer
            public void setDisplayOrientation(float f) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.ISpatializer.DESCRIPTOR);
                    obtain.writeFloat(f);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.ISpatializer
            public void setHingeAngle(float f) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.ISpatializer.DESCRIPTOR);
                    obtain.writeFloat(f);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.ISpatializer
            public void setFoldState(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.ISpatializer.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.ISpatializer
            public byte[] getSupportedModes() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.ISpatializer.DESCRIPTOR);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.ISpatializer
            public void registerHeadTrackingCallback(android.media.ISpatializerHeadTrackingCallback iSpatializerHeadTrackingCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.ISpatializer.DESCRIPTOR);
                    obtain.writeStrongInterface(iSpatializerHeadTrackingCallback);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.ISpatializer
            public void setParameter(int i, byte[] bArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.ISpatializer.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.ISpatializer
            public void getParameter(int i, byte[] bArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.ISpatializer.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                    obtain2.readByteArray(bArr);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.ISpatializer
            public int getOutput() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.ISpatializer.DESCRIPTOR);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
