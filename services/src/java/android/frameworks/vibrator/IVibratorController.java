package android.frameworks.vibrator;

/* loaded from: classes.dex */
public interface IVibratorController extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android$frameworks$vibrator$IVibratorController".replace('$', '.');
    public static final java.lang.String HASH = "eb095ed3034973273898ca9e37bbc72566392b8a";
    public static final int VERSION = 1;

    java.lang.String getInterfaceHash() throws android.os.RemoteException;

    int getInterfaceVersion() throws android.os.RemoteException;

    void requestVibrationParams(int i, long j, android.os.IBinder iBinder) throws android.os.RemoteException;

    public static class Default implements android.frameworks.vibrator.IVibratorController {
        @Override // android.frameworks.vibrator.IVibratorController
        public void requestVibrationParams(int i, long j, android.os.IBinder iBinder) throws android.os.RemoteException {
        }

        @Override // android.frameworks.vibrator.IVibratorController
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.frameworks.vibrator.IVibratorController
        public java.lang.String getInterfaceHash() {
            return "";
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.frameworks.vibrator.IVibratorController {
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_requestVibrationParams = 1;

        public Stub() {
            markVintfStability();
            attachInterface(this, android.frameworks.vibrator.IVibratorController.DESCRIPTOR);
        }

        public static android.frameworks.vibrator.IVibratorController asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.frameworks.vibrator.IVibratorController.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.frameworks.vibrator.IVibratorController)) {
                return (android.frameworks.vibrator.IVibratorController) queryLocalInterface;
            }
            return new android.frameworks.vibrator.IVibratorController.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "requestVibrationParams";
                case TRANSACTION_getInterfaceHash /* 16777214 */:
                    return "getInterfaceHash";
                case 16777215:
                    return "getInterfaceVersion";
                default:
                    return null;
            }
        }

        public java.lang.String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, android.os.Parcel parcel, android.os.Parcel parcel2, int i2) throws android.os.RemoteException {
            java.lang.String str = android.frameworks.vibrator.IVibratorController.DESCRIPTOR;
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
            if (i == TRANSACTION_getInterfaceHash) {
                parcel2.writeNoException();
                parcel2.writeString(getInterfaceHash());
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    long readLong = parcel.readLong();
                    android.os.IBinder readStrongBinder = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    requestVibrationParams(readInt, readLong, readStrongBinder);
                    break;
            }
            return true;
        }

        private static class Proxy implements android.frameworks.vibrator.IVibratorController {
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
                return android.frameworks.vibrator.IVibratorController.DESCRIPTOR;
            }

            @Override // android.frameworks.vibrator.IVibratorController
            public void requestVibrationParams(int i, long j, android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.frameworks.vibrator.IVibratorController.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    obtain.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(1, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method requestVibrationParams is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.frameworks.vibrator.IVibratorController
            public int getInterfaceVersion() throws android.os.RemoteException {
                if (this.mCachedVersion == -1) {
                    android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                    android.os.Parcel obtain2 = android.os.Parcel.obtain();
                    try {
                        obtain.writeInterfaceToken(android.frameworks.vibrator.IVibratorController.DESCRIPTOR);
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

            @Override // android.frameworks.vibrator.IVibratorController
            public synchronized java.lang.String getInterfaceHash() throws android.os.RemoteException {
                try {
                    if ("-1".equals(this.mCachedHash)) {
                        android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                        android.os.Parcel obtain2 = android.os.Parcel.obtain();
                        try {
                            obtain.writeInterfaceToken(android.frameworks.vibrator.IVibratorController.DESCRIPTOR);
                            this.mRemote.transact(android.frameworks.vibrator.IVibratorController.Stub.TRANSACTION_getInterfaceHash, obtain, obtain2, 0);
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
                } catch (java.lang.Throwable th2) {
                    throw th2;
                }
                return this.mCachedHash;
            }
        }

        public int getMaxTransactionId() {
            return TRANSACTION_getInterfaceHash;
        }
    }
}
