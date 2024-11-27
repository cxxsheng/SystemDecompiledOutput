package android.os;

/* loaded from: classes3.dex */
public interface IVibratorManagerService extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.os.IVibratorManagerService";

    void cancelVibrate(int i, android.os.IBinder iBinder) throws android.os.RemoteException;

    int[] getVibratorIds() throws android.os.RemoteException;

    android.os.VibratorInfo getVibratorInfo(int i) throws android.os.RemoteException;

    boolean isVibrating(int i) throws android.os.RemoteException;

    void performHapticFeedback(int i, int i2, java.lang.String str, int i3, boolean z, java.lang.String str2, boolean z2) throws android.os.RemoteException;

    boolean registerVibratorStateListener(int i, android.os.IVibratorStateListener iVibratorStateListener) throws android.os.RemoteException;

    boolean setAlwaysOnEffect(int i, java.lang.String str, int i2, android.os.CombinedVibration combinedVibration, android.os.VibrationAttributes vibrationAttributes) throws android.os.RemoteException;

    boolean unregisterVibratorStateListener(int i, android.os.IVibratorStateListener iVibratorStateListener) throws android.os.RemoteException;

    void vibrate(int i, int i2, java.lang.String str, android.os.CombinedVibration combinedVibration, android.os.VibrationAttributes vibrationAttributes, java.lang.String str2, android.os.IBinder iBinder) throws android.os.RemoteException;

    public static class Default implements android.os.IVibratorManagerService {
        @Override // android.os.IVibratorManagerService
        public int[] getVibratorIds() throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IVibratorManagerService
        public android.os.VibratorInfo getVibratorInfo(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IVibratorManagerService
        public boolean isVibrating(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.IVibratorManagerService
        public boolean registerVibratorStateListener(int i, android.os.IVibratorStateListener iVibratorStateListener) throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.IVibratorManagerService
        public boolean unregisterVibratorStateListener(int i, android.os.IVibratorStateListener iVibratorStateListener) throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.IVibratorManagerService
        public boolean setAlwaysOnEffect(int i, java.lang.String str, int i2, android.os.CombinedVibration combinedVibration, android.os.VibrationAttributes vibrationAttributes) throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.IVibratorManagerService
        public void vibrate(int i, int i2, java.lang.String str, android.os.CombinedVibration combinedVibration, android.os.VibrationAttributes vibrationAttributes, java.lang.String str2, android.os.IBinder iBinder) throws android.os.RemoteException {
        }

        @Override // android.os.IVibratorManagerService
        public void cancelVibrate(int i, android.os.IBinder iBinder) throws android.os.RemoteException {
        }

        @Override // android.os.IVibratorManagerService
        public void performHapticFeedback(int i, int i2, java.lang.String str, int i3, boolean z, java.lang.String str2, boolean z2) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.os.IVibratorManagerService {
        static final int TRANSACTION_cancelVibrate = 8;
        static final int TRANSACTION_getVibratorIds = 1;
        static final int TRANSACTION_getVibratorInfo = 2;
        static final int TRANSACTION_isVibrating = 3;
        static final int TRANSACTION_performHapticFeedback = 9;
        static final int TRANSACTION_registerVibratorStateListener = 4;
        static final int TRANSACTION_setAlwaysOnEffect = 6;
        static final int TRANSACTION_unregisterVibratorStateListener = 5;
        static final int TRANSACTION_vibrate = 7;
        private final android.os.PermissionEnforcer mEnforcer;

        public Stub(android.os.PermissionEnforcer permissionEnforcer) {
            attachInterface(this, android.os.IVibratorManagerService.DESCRIPTOR);
            if (permissionEnforcer == null) {
                throw new java.lang.IllegalArgumentException("enforcer cannot be null");
            }
            this.mEnforcer = permissionEnforcer;
        }

        @java.lang.Deprecated
        public Stub() {
            this(android.os.PermissionEnforcer.fromContext(android.app.ActivityThread.currentActivityThread().getSystemContext()));
        }

        public static android.os.IVibratorManagerService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.os.IVibratorManagerService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.os.IVibratorManagerService)) {
                return (android.os.IVibratorManagerService) queryLocalInterface;
            }
            return new android.os.IVibratorManagerService.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getVibratorIds";
                case 2:
                    return "getVibratorInfo";
                case 3:
                    return "isVibrating";
                case 4:
                    return "registerVibratorStateListener";
                case 5:
                    return "unregisterVibratorStateListener";
                case 6:
                    return "setAlwaysOnEffect";
                case 7:
                    return "vibrate";
                case 8:
                    return "cancelVibrate";
                case 9:
                    return "performHapticFeedback";
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
                parcel.enforceInterface(android.os.IVibratorManagerService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.os.IVibratorManagerService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int[] vibratorIds = getVibratorIds();
                    parcel2.writeNoException();
                    parcel2.writeIntArray(vibratorIds);
                    return true;
                case 2:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.os.VibratorInfo vibratorInfo = getVibratorInfo(readInt);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(vibratorInfo, 1);
                    return true;
                case 3:
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isVibrating = isVibrating(readInt2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isVibrating);
                    return true;
                case 4:
                    int readInt3 = parcel.readInt();
                    android.os.IVibratorStateListener asInterface = android.os.IVibratorStateListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean registerVibratorStateListener = registerVibratorStateListener(readInt3, asInterface);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(registerVibratorStateListener);
                    return true;
                case 5:
                    int readInt4 = parcel.readInt();
                    android.os.IVibratorStateListener asInterface2 = android.os.IVibratorStateListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean unregisterVibratorStateListener = unregisterVibratorStateListener(readInt4, asInterface2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(unregisterVibratorStateListener);
                    return true;
                case 6:
                    int readInt5 = parcel.readInt();
                    java.lang.String readString = parcel.readString();
                    int readInt6 = parcel.readInt();
                    android.os.CombinedVibration combinedVibration = (android.os.CombinedVibration) parcel.readTypedObject(android.os.CombinedVibration.CREATOR);
                    android.os.VibrationAttributes vibrationAttributes = (android.os.VibrationAttributes) parcel.readTypedObject(android.os.VibrationAttributes.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean alwaysOnEffect = setAlwaysOnEffect(readInt5, readString, readInt6, combinedVibration, vibrationAttributes);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(alwaysOnEffect);
                    return true;
                case 7:
                    int readInt7 = parcel.readInt();
                    int readInt8 = parcel.readInt();
                    java.lang.String readString2 = parcel.readString();
                    android.os.CombinedVibration combinedVibration2 = (android.os.CombinedVibration) parcel.readTypedObject(android.os.CombinedVibration.CREATOR);
                    android.os.VibrationAttributes vibrationAttributes2 = (android.os.VibrationAttributes) parcel.readTypedObject(android.os.VibrationAttributes.CREATOR);
                    java.lang.String readString3 = parcel.readString();
                    android.os.IBinder readStrongBinder = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    vibrate(readInt7, readInt8, readString2, combinedVibration2, vibrationAttributes2, readString3, readStrongBinder);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    int readInt9 = parcel.readInt();
                    android.os.IBinder readStrongBinder2 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    cancelVibrate(readInt9, readStrongBinder2);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    int readInt10 = parcel.readInt();
                    int readInt11 = parcel.readInt();
                    java.lang.String readString4 = parcel.readString();
                    int readInt12 = parcel.readInt();
                    boolean readBoolean = parcel.readBoolean();
                    java.lang.String readString5 = parcel.readString();
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    performHapticFeedback(readInt10, readInt11, readString4, readInt12, readBoolean, readString5, readBoolean2);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.os.IVibratorManagerService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.os.IVibratorManagerService.DESCRIPTOR;
            }

            @Override // android.os.IVibratorManagerService
            public int[] getVibratorIds() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IVibratorManagerService.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVibratorManagerService
            public android.os.VibratorInfo getVibratorInfo(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IVibratorManagerService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.os.VibratorInfo) obtain2.readTypedObject(android.os.VibratorInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVibratorManagerService
            public boolean isVibrating(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IVibratorManagerService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVibratorManagerService
            public boolean registerVibratorStateListener(int i, android.os.IVibratorStateListener iVibratorStateListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IVibratorManagerService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iVibratorStateListener);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVibratorManagerService
            public boolean unregisterVibratorStateListener(int i, android.os.IVibratorStateListener iVibratorStateListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IVibratorManagerService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iVibratorStateListener);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVibratorManagerService
            public boolean setAlwaysOnEffect(int i, java.lang.String str, int i2, android.os.CombinedVibration combinedVibration, android.os.VibrationAttributes vibrationAttributes) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IVibratorManagerService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(combinedVibration, 0);
                    obtain.writeTypedObject(vibrationAttributes, 0);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVibratorManagerService
            public void vibrate(int i, int i2, java.lang.String str, android.os.CombinedVibration combinedVibration, android.os.VibrationAttributes vibrationAttributes, java.lang.String str2, android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IVibratorManagerService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    obtain.writeTypedObject(combinedVibration, 0);
                    obtain.writeTypedObject(vibrationAttributes, 0);
                    obtain.writeString(str2);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVibratorManagerService
            public void cancelVibrate(int i, android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IVibratorManagerService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVibratorManagerService
            public void performHapticFeedback(int i, int i2, java.lang.String str, int i3, boolean z, java.lang.String str2, boolean z2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.os.IVibratorManagerService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    obtain.writeInt(i3);
                    obtain.writeBoolean(z);
                    obtain.writeString(str2);
                    obtain.writeBoolean(z2);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        protected void isVibrating_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.ACCESS_VIBRATOR_STATE, getCallingPid(), getCallingUid());
        }

        protected void registerVibratorStateListener_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.ACCESS_VIBRATOR_STATE, getCallingPid(), getCallingUid());
        }

        protected void unregisterVibratorStateListener_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.ACCESS_VIBRATOR_STATE, getCallingPid(), getCallingUid());
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 8;
        }
    }
}
