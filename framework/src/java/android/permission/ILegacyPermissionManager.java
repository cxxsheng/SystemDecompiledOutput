package android.permission;

/* loaded from: classes3.dex */
public interface ILegacyPermissionManager extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.permission.ILegacyPermissionManager";

    int checkDeviceIdentifierAccess(java.lang.String str, java.lang.String str2, java.lang.String str3, int i, int i2) throws android.os.RemoteException;

    int checkPhoneNumberAccess(java.lang.String str, java.lang.String str2, java.lang.String str3, int i, int i2) throws android.os.RemoteException;

    void grantDefaultPermissionsToActiveLuiApp(java.lang.String str, int i) throws android.os.RemoteException;

    void grantDefaultPermissionsToCarrierServiceApp(java.lang.String str, int i) throws android.os.RemoteException;

    void grantDefaultPermissionsToEnabledCarrierApps(java.lang.String[] strArr, int i) throws android.os.RemoteException;

    void grantDefaultPermissionsToEnabledImsServices(java.lang.String[] strArr, int i) throws android.os.RemoteException;

    void grantDefaultPermissionsToEnabledTelephonyDataServices(java.lang.String[] strArr, int i) throws android.os.RemoteException;

    void revokeDefaultPermissionsFromDisabledTelephonyDataServices(java.lang.String[] strArr, int i) throws android.os.RemoteException;

    void revokeDefaultPermissionsFromLuiApps(java.lang.String[] strArr, int i) throws android.os.RemoteException;

    public static class Default implements android.permission.ILegacyPermissionManager {
        @Override // android.permission.ILegacyPermissionManager
        public int checkDeviceIdentifierAccess(java.lang.String str, java.lang.String str2, java.lang.String str3, int i, int i2) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.permission.ILegacyPermissionManager
        public int checkPhoneNumberAccess(java.lang.String str, java.lang.String str2, java.lang.String str3, int i, int i2) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.permission.ILegacyPermissionManager
        public void grantDefaultPermissionsToEnabledCarrierApps(java.lang.String[] strArr, int i) throws android.os.RemoteException {
        }

        @Override // android.permission.ILegacyPermissionManager
        public void grantDefaultPermissionsToEnabledImsServices(java.lang.String[] strArr, int i) throws android.os.RemoteException {
        }

        @Override // android.permission.ILegacyPermissionManager
        public void grantDefaultPermissionsToEnabledTelephonyDataServices(java.lang.String[] strArr, int i) throws android.os.RemoteException {
        }

        @Override // android.permission.ILegacyPermissionManager
        public void revokeDefaultPermissionsFromDisabledTelephonyDataServices(java.lang.String[] strArr, int i) throws android.os.RemoteException {
        }

        @Override // android.permission.ILegacyPermissionManager
        public void grantDefaultPermissionsToActiveLuiApp(java.lang.String str, int i) throws android.os.RemoteException {
        }

        @Override // android.permission.ILegacyPermissionManager
        public void revokeDefaultPermissionsFromLuiApps(java.lang.String[] strArr, int i) throws android.os.RemoteException {
        }

        @Override // android.permission.ILegacyPermissionManager
        public void grantDefaultPermissionsToCarrierServiceApp(java.lang.String str, int i) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.permission.ILegacyPermissionManager {
        static final int TRANSACTION_checkDeviceIdentifierAccess = 1;
        static final int TRANSACTION_checkPhoneNumberAccess = 2;
        static final int TRANSACTION_grantDefaultPermissionsToActiveLuiApp = 7;
        static final int TRANSACTION_grantDefaultPermissionsToCarrierServiceApp = 9;
        static final int TRANSACTION_grantDefaultPermissionsToEnabledCarrierApps = 3;
        static final int TRANSACTION_grantDefaultPermissionsToEnabledImsServices = 4;
        static final int TRANSACTION_grantDefaultPermissionsToEnabledTelephonyDataServices = 5;
        static final int TRANSACTION_revokeDefaultPermissionsFromDisabledTelephonyDataServices = 6;
        static final int TRANSACTION_revokeDefaultPermissionsFromLuiApps = 8;

        public Stub() {
            attachInterface(this, android.permission.ILegacyPermissionManager.DESCRIPTOR);
        }

        public static android.permission.ILegacyPermissionManager asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.permission.ILegacyPermissionManager.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.permission.ILegacyPermissionManager)) {
                return (android.permission.ILegacyPermissionManager) queryLocalInterface;
            }
            return new android.permission.ILegacyPermissionManager.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "checkDeviceIdentifierAccess";
                case 2:
                    return "checkPhoneNumberAccess";
                case 3:
                    return "grantDefaultPermissionsToEnabledCarrierApps";
                case 4:
                    return "grantDefaultPermissionsToEnabledImsServices";
                case 5:
                    return "grantDefaultPermissionsToEnabledTelephonyDataServices";
                case 6:
                    return "revokeDefaultPermissionsFromDisabledTelephonyDataServices";
                case 7:
                    return "grantDefaultPermissionsToActiveLuiApp";
                case 8:
                    return "revokeDefaultPermissionsFromLuiApps";
                case 9:
                    return "grantDefaultPermissionsToCarrierServiceApp";
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
                parcel.enforceInterface(android.permission.ILegacyPermissionManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.permission.ILegacyPermissionManager.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    java.lang.String readString = parcel.readString();
                    java.lang.String readString2 = parcel.readString();
                    java.lang.String readString3 = parcel.readString();
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int checkDeviceIdentifierAccess = checkDeviceIdentifierAccess(readString, readString2, readString3, readInt, readInt2);
                    parcel2.writeNoException();
                    parcel2.writeInt(checkDeviceIdentifierAccess);
                    return true;
                case 2:
                    java.lang.String readString4 = parcel.readString();
                    java.lang.String readString5 = parcel.readString();
                    java.lang.String readString6 = parcel.readString();
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int checkPhoneNumberAccess = checkPhoneNumberAccess(readString4, readString5, readString6, readInt3, readInt4);
                    parcel2.writeNoException();
                    parcel2.writeInt(checkPhoneNumberAccess);
                    return true;
                case 3:
                    java.lang.String[] createStringArray = parcel.createStringArray();
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    grantDefaultPermissionsToEnabledCarrierApps(createStringArray, readInt5);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    java.lang.String[] createStringArray2 = parcel.createStringArray();
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    grantDefaultPermissionsToEnabledImsServices(createStringArray2, readInt6);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    java.lang.String[] createStringArray3 = parcel.createStringArray();
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    grantDefaultPermissionsToEnabledTelephonyDataServices(createStringArray3, readInt7);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    java.lang.String[] createStringArray4 = parcel.createStringArray();
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    revokeDefaultPermissionsFromDisabledTelephonyDataServices(createStringArray4, readInt8);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    java.lang.String readString7 = parcel.readString();
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    grantDefaultPermissionsToActiveLuiApp(readString7, readInt9);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    java.lang.String[] createStringArray5 = parcel.createStringArray();
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    revokeDefaultPermissionsFromLuiApps(createStringArray5, readInt10);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    java.lang.String readString8 = parcel.readString();
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    grantDefaultPermissionsToCarrierServiceApp(readString8, readInt11);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.permission.ILegacyPermissionManager {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.permission.ILegacyPermissionManager.DESCRIPTOR;
            }

            @Override // android.permission.ILegacyPermissionManager
            public int checkDeviceIdentifierAccess(java.lang.String str, java.lang.String str2, java.lang.String str3, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.permission.ILegacyPermissionManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.permission.ILegacyPermissionManager
            public int checkPhoneNumberAccess(java.lang.String str, java.lang.String str2, java.lang.String str3, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.permission.ILegacyPermissionManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.permission.ILegacyPermissionManager
            public void grantDefaultPermissionsToEnabledCarrierApps(java.lang.String[] strArr, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.permission.ILegacyPermissionManager.DESCRIPTOR);
                    obtain.writeStringArray(strArr);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.permission.ILegacyPermissionManager
            public void grantDefaultPermissionsToEnabledImsServices(java.lang.String[] strArr, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.permission.ILegacyPermissionManager.DESCRIPTOR);
                    obtain.writeStringArray(strArr);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.permission.ILegacyPermissionManager
            public void grantDefaultPermissionsToEnabledTelephonyDataServices(java.lang.String[] strArr, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.permission.ILegacyPermissionManager.DESCRIPTOR);
                    obtain.writeStringArray(strArr);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.permission.ILegacyPermissionManager
            public void revokeDefaultPermissionsFromDisabledTelephonyDataServices(java.lang.String[] strArr, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.permission.ILegacyPermissionManager.DESCRIPTOR);
                    obtain.writeStringArray(strArr);
                    obtain.writeInt(i);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.permission.ILegacyPermissionManager
            public void grantDefaultPermissionsToActiveLuiApp(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.permission.ILegacyPermissionManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.permission.ILegacyPermissionManager
            public void revokeDefaultPermissionsFromLuiApps(java.lang.String[] strArr, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.permission.ILegacyPermissionManager.DESCRIPTOR);
                    obtain.writeStringArray(strArr);
                    obtain.writeInt(i);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.permission.ILegacyPermissionManager
            public void grantDefaultPermissionsToCarrierServiceApp(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.permission.ILegacyPermissionManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
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
