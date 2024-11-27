package android.frameworks.vibrator;

/* loaded from: classes.dex */
public interface IVibratorControlService extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android$frameworks$vibrator$IVibratorControlService".replace('$', '.');
    public static final java.lang.String HASH = "eb095ed3034973273898ca9e37bbc72566392b8a";
    public static final int VERSION = 1;

    void clearVibrationParams(int i, android.frameworks.vibrator.IVibratorController iVibratorController) throws android.os.RemoteException;

    java.lang.String getInterfaceHash() throws android.os.RemoteException;

    int getInterfaceVersion() throws android.os.RemoteException;

    void onRequestVibrationParamsComplete(android.os.IBinder iBinder, android.frameworks.vibrator.VibrationParam[] vibrationParamArr) throws android.os.RemoteException;

    void registerVibratorController(android.frameworks.vibrator.IVibratorController iVibratorController) throws android.os.RemoteException;

    void setVibrationParams(android.frameworks.vibrator.VibrationParam[] vibrationParamArr, android.frameworks.vibrator.IVibratorController iVibratorController) throws android.os.RemoteException;

    void unregisterVibratorController(android.frameworks.vibrator.IVibratorController iVibratorController) throws android.os.RemoteException;

    public static class Default implements android.frameworks.vibrator.IVibratorControlService {
        @Override // android.frameworks.vibrator.IVibratorControlService
        public void registerVibratorController(android.frameworks.vibrator.IVibratorController iVibratorController) throws android.os.RemoteException {
        }

        @Override // android.frameworks.vibrator.IVibratorControlService
        public void unregisterVibratorController(android.frameworks.vibrator.IVibratorController iVibratorController) throws android.os.RemoteException {
        }

        @Override // android.frameworks.vibrator.IVibratorControlService
        public void setVibrationParams(android.frameworks.vibrator.VibrationParam[] vibrationParamArr, android.frameworks.vibrator.IVibratorController iVibratorController) throws android.os.RemoteException {
        }

        @Override // android.frameworks.vibrator.IVibratorControlService
        public void clearVibrationParams(int i, android.frameworks.vibrator.IVibratorController iVibratorController) throws android.os.RemoteException {
        }

        @Override // android.frameworks.vibrator.IVibratorControlService
        public void onRequestVibrationParamsComplete(android.os.IBinder iBinder, android.frameworks.vibrator.VibrationParam[] vibrationParamArr) throws android.os.RemoteException {
        }

        @Override // android.frameworks.vibrator.IVibratorControlService
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.frameworks.vibrator.IVibratorControlService
        public java.lang.String getInterfaceHash() {
            return "";
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.frameworks.vibrator.IVibratorControlService {
        static final int TRANSACTION_clearVibrationParams = 4;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_onRequestVibrationParamsComplete = 5;
        static final int TRANSACTION_registerVibratorController = 1;
        static final int TRANSACTION_setVibrationParams = 3;
        static final int TRANSACTION_unregisterVibratorController = 2;

        public Stub() {
            markVintfStability();
            attachInterface(this, android.frameworks.vibrator.IVibratorControlService.DESCRIPTOR);
        }

        public static android.frameworks.vibrator.IVibratorControlService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.frameworks.vibrator.IVibratorControlService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.frameworks.vibrator.IVibratorControlService)) {
                return (android.frameworks.vibrator.IVibratorControlService) queryLocalInterface;
            }
            return new android.frameworks.vibrator.IVibratorControlService.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "registerVibratorController";
                case 2:
                    return "unregisterVibratorController";
                case 3:
                    return "setVibrationParams";
                case 4:
                    return "clearVibrationParams";
                case 5:
                    return "onRequestVibrationParamsComplete";
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
            java.lang.String str = android.frameworks.vibrator.IVibratorControlService.DESCRIPTOR;
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
                    android.frameworks.vibrator.IVibratorController asInterface = android.frameworks.vibrator.IVibratorController.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerVibratorController(asInterface);
                    return true;
                case 2:
                    android.frameworks.vibrator.IVibratorController asInterface2 = android.frameworks.vibrator.IVibratorController.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterVibratorController(asInterface2);
                    return true;
                case 3:
                    android.frameworks.vibrator.VibrationParam[] vibrationParamArr = (android.frameworks.vibrator.VibrationParam[]) parcel.createTypedArray(android.frameworks.vibrator.VibrationParam.CREATOR);
                    android.frameworks.vibrator.IVibratorController asInterface3 = android.frameworks.vibrator.IVibratorController.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setVibrationParams(vibrationParamArr, asInterface3);
                    return true;
                case 4:
                    int readInt = parcel.readInt();
                    android.frameworks.vibrator.IVibratorController asInterface4 = android.frameworks.vibrator.IVibratorController.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    clearVibrationParams(readInt, asInterface4);
                    return true;
                case 5:
                    android.os.IBinder readStrongBinder = parcel.readStrongBinder();
                    android.frameworks.vibrator.VibrationParam[] vibrationParamArr2 = (android.frameworks.vibrator.VibrationParam[]) parcel.createTypedArray(android.frameworks.vibrator.VibrationParam.CREATOR);
                    parcel.enforceNoDataAvail();
                    onRequestVibrationParamsComplete(readStrongBinder, vibrationParamArr2);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.frameworks.vibrator.IVibratorControlService {
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
                return android.frameworks.vibrator.IVibratorControlService.DESCRIPTOR;
            }

            @Override // android.frameworks.vibrator.IVibratorControlService
            public void registerVibratorController(android.frameworks.vibrator.IVibratorController iVibratorController) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.frameworks.vibrator.IVibratorControlService.DESCRIPTOR);
                    obtain.writeStrongInterface(iVibratorController);
                    if (!this.mRemote.transact(1, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method registerVibratorController is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.frameworks.vibrator.IVibratorControlService
            public void unregisterVibratorController(android.frameworks.vibrator.IVibratorController iVibratorController) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.frameworks.vibrator.IVibratorControlService.DESCRIPTOR);
                    obtain.writeStrongInterface(iVibratorController);
                    if (!this.mRemote.transact(2, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method unregisterVibratorController is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.frameworks.vibrator.IVibratorControlService
            public void setVibrationParams(android.frameworks.vibrator.VibrationParam[] vibrationParamArr, android.frameworks.vibrator.IVibratorController iVibratorController) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.frameworks.vibrator.IVibratorControlService.DESCRIPTOR);
                    obtain.writeTypedArray(vibrationParamArr, 0);
                    obtain.writeStrongInterface(iVibratorController);
                    if (!this.mRemote.transact(3, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method setVibrationParams is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.frameworks.vibrator.IVibratorControlService
            public void clearVibrationParams(int i, android.frameworks.vibrator.IVibratorController iVibratorController) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.frameworks.vibrator.IVibratorControlService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iVibratorController);
                    if (!this.mRemote.transact(4, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method clearVibrationParams is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.frameworks.vibrator.IVibratorControlService
            public void onRequestVibrationParamsComplete(android.os.IBinder iBinder, android.frameworks.vibrator.VibrationParam[] vibrationParamArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.frameworks.vibrator.IVibratorControlService.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedArray(vibrationParamArr, 0);
                    if (!this.mRemote.transact(5, obtain, null, 1)) {
                        throw new android.os.RemoteException("Method onRequestVibrationParamsComplete is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.frameworks.vibrator.IVibratorControlService
            public int getInterfaceVersion() throws android.os.RemoteException {
                if (this.mCachedVersion == -1) {
                    android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                    android.os.Parcel obtain2 = android.os.Parcel.obtain();
                    try {
                        obtain.writeInterfaceToken(android.frameworks.vibrator.IVibratorControlService.DESCRIPTOR);
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

            @Override // android.frameworks.vibrator.IVibratorControlService
            public synchronized java.lang.String getInterfaceHash() throws android.os.RemoteException {
                try {
                    if ("-1".equals(this.mCachedHash)) {
                        android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                        android.os.Parcel obtain2 = android.os.Parcel.obtain();
                        try {
                            obtain.writeInterfaceToken(android.frameworks.vibrator.IVibratorControlService.DESCRIPTOR);
                            this.mRemote.transact(android.frameworks.vibrator.IVibratorControlService.Stub.TRANSACTION_getInterfaceHash, obtain, obtain2, 0);
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
