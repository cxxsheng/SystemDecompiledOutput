package android.hardware.vibrator;

/* loaded from: classes2.dex */
public interface IVibratorManager extends android.os.IInterface {
    public static final int CAP_MIXED_TRIGGER_COMPOSE = 64;
    public static final int CAP_MIXED_TRIGGER_ON = 16;
    public static final int CAP_MIXED_TRIGGER_PERFORM = 32;
    public static final int CAP_PREPARE_COMPOSE = 8;
    public static final int CAP_PREPARE_ON = 2;
    public static final int CAP_PREPARE_PERFORM = 4;
    public static final int CAP_SYNC = 1;
    public static final int CAP_TRIGGER_CALLBACK = 128;
    public static final java.lang.String DESCRIPTOR = "android$hardware$vibrator$IVibratorManager".replace('$', '.');
    public static final java.lang.String HASH = "ea8742d6993e1a82917da38b9938e537aa7fcb54";
    public static final int VERSION = 2;

    void cancelSynced() throws android.os.RemoteException;

    int getCapabilities() throws android.os.RemoteException;

    java.lang.String getInterfaceHash() throws android.os.RemoteException;

    int getInterfaceVersion() throws android.os.RemoteException;

    android.hardware.vibrator.IVibrator getVibrator(int i) throws android.os.RemoteException;

    int[] getVibratorIds() throws android.os.RemoteException;

    void prepareSynced(int[] iArr) throws android.os.RemoteException;

    void triggerSynced(android.hardware.vibrator.IVibratorCallback iVibratorCallback) throws android.os.RemoteException;

    public static class Default implements android.hardware.vibrator.IVibratorManager {
        @Override // android.hardware.vibrator.IVibratorManager
        public int getCapabilities() throws android.os.RemoteException {
            return 0;
        }

        @Override // android.hardware.vibrator.IVibratorManager
        public int[] getVibratorIds() throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.vibrator.IVibratorManager
        public android.hardware.vibrator.IVibrator getVibrator(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.vibrator.IVibratorManager
        public void prepareSynced(int[] iArr) throws android.os.RemoteException {
        }

        @Override // android.hardware.vibrator.IVibratorManager
        public void triggerSynced(android.hardware.vibrator.IVibratorCallback iVibratorCallback) throws android.os.RemoteException {
        }

        @Override // android.hardware.vibrator.IVibratorManager
        public void cancelSynced() throws android.os.RemoteException {
        }

        @Override // android.hardware.vibrator.IVibratorManager
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.vibrator.IVibratorManager
        public java.lang.String getInterfaceHash() {
            return "";
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.vibrator.IVibratorManager {
        static final int TRANSACTION_cancelSynced = 6;
        static final int TRANSACTION_getCapabilities = 1;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_getVibrator = 3;
        static final int TRANSACTION_getVibratorIds = 2;
        static final int TRANSACTION_prepareSynced = 4;
        static final int TRANSACTION_triggerSynced = 5;

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static android.hardware.vibrator.IVibratorManager asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.vibrator.IVibratorManager)) {
                return (android.hardware.vibrator.IVibratorManager) queryLocalInterface;
            }
            return new android.hardware.vibrator.IVibratorManager.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, android.os.Parcel parcel, android.os.Parcel parcel2, int i2) throws android.os.RemoteException {
            java.lang.String str = DESCRIPTOR;
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(str);
            }
            if (i == 1598968902) {
                parcel2.writeString(str);
                return true;
            }
            if (i == 16777215) {
                parcel2.writeNoException();
                parcel2.writeInt(getInterfaceVersion());
                return true;
            }
            if (i == 16777214) {
                parcel2.writeNoException();
                parcel2.writeString(getInterfaceHash());
                return true;
            }
            switch (i) {
                case 1:
                    int capabilities = getCapabilities();
                    parcel2.writeNoException();
                    parcel2.writeInt(capabilities);
                    return true;
                case 2:
                    int[] vibratorIds = getVibratorIds();
                    parcel2.writeNoException();
                    parcel2.writeIntArray(vibratorIds);
                    return true;
                case 3:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.hardware.vibrator.IVibrator vibrator = getVibrator(readInt);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(vibrator);
                    return true;
                case 4:
                    int[] createIntArray = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    prepareSynced(createIntArray);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    android.hardware.vibrator.IVibratorCallback asInterface = android.hardware.vibrator.IVibratorCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    triggerSynced(asInterface);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    cancelSynced();
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.hardware.vibrator.IVibratorManager {
            private android.os.IBinder mRemote;
            private int mCachedVersion = -1;
            private java.lang.String mCachedHash = "-1";

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return DESCRIPTOR;
            }

            @Override // android.hardware.vibrator.IVibratorManager
            public int getCapabilities() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(1, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method getCapabilities is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.vibrator.IVibratorManager
            public int[] getVibratorIds() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(2, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method getVibratorIds is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.vibrator.IVibratorManager
            public android.hardware.vibrator.IVibrator getVibrator(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(3, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method getVibrator is unimplemented.");
                    }
                    obtain2.readException();
                    return android.hardware.vibrator.IVibrator.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.vibrator.IVibratorManager
            public void prepareSynced(int[] iArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeIntArray(iArr);
                    if (!this.mRemote.transact(4, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method prepareSynced is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.vibrator.IVibratorManager
            public void triggerSynced(android.hardware.vibrator.IVibratorCallback iVibratorCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeStrongInterface(iVibratorCallback);
                    if (!this.mRemote.transact(5, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method triggerSynced is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.vibrator.IVibratorManager
            public void cancelSynced() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(6, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method cancelSynced is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.vibrator.IVibratorManager
            public int getInterfaceVersion() throws android.os.RemoteException {
                if (this.mCachedVersion == -1) {
                    android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                    android.os.Parcel obtain2 = android.os.Parcel.obtain();
                    try {
                        obtain.writeInterfaceToken(DESCRIPTOR);
                        this.mRemote.transact(16777215, obtain, obtain2, 0);
                        obtain2.readException();
                        this.mCachedVersion = obtain2.readInt();
                    } finally {
                        obtain2.recycle();
                        obtain.recycle();
                    }
                }
                return this.mCachedVersion;
            }

            @Override // android.hardware.vibrator.IVibratorManager
            public synchronized java.lang.String getInterfaceHash() throws android.os.RemoteException {
                if ("-1".equals(this.mCachedHash)) {
                    android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                    android.os.Parcel obtain2 = android.os.Parcel.obtain();
                    try {
                        obtain.writeInterfaceToken(DESCRIPTOR);
                        this.mRemote.transact(16777214, obtain, obtain2, 0);
                        obtain2.readException();
                        this.mCachedHash = obtain2.readString();
                        obtain2.recycle();
                        obtain.recycle();
                    } catch (java.lang.Throwable th) {
                        obtain2.recycle();
                        obtain.recycle();
                        throw th;
                    }
                }
                return this.mCachedHash;
            }
        }
    }
}
