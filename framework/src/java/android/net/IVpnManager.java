package android.net;

/* loaded from: classes2.dex */
public interface IVpnManager extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.net.IVpnManager";

    boolean addVpnAddress(java.lang.String str, int i) throws android.os.RemoteException;

    void deleteVpnProfile(java.lang.String str) throws android.os.RemoteException;

    android.os.ParcelFileDescriptor establishVpn(com.android.internal.net.VpnConfig vpnConfig) throws android.os.RemoteException;

    void factoryReset() throws android.os.RemoteException;

    com.android.internal.net.VpnProfile[] getAllLegacyVpns() throws android.os.RemoteException;

    java.lang.String getAlwaysOnVpnPackage(int i) throws android.os.RemoteException;

    java.util.List<java.lang.String> getAppExclusionList(int i, java.lang.String str) throws android.os.RemoteException;

    com.android.internal.net.LegacyVpnInfo getLegacyVpnInfo(int i) throws android.os.RemoteException;

    android.net.VpnProfileState getProvisionedVpnProfileState(java.lang.String str) throws android.os.RemoteException;

    com.android.internal.net.VpnConfig getVpnConfig(int i) throws android.os.RemoteException;

    java.util.List<java.lang.String> getVpnLockdownAllowlist(int i) throws android.os.RemoteException;

    boolean isAlwaysOnVpnPackageSupported(int i, java.lang.String str) throws android.os.RemoteException;

    boolean isCallerCurrentAlwaysOnVpnApp() throws android.os.RemoteException;

    boolean isCallerCurrentAlwaysOnVpnLockdownApp() throws android.os.RemoteException;

    boolean isVpnLockdownEnabled(int i) throws android.os.RemoteException;

    boolean prepareVpn(java.lang.String str, java.lang.String str2, int i) throws android.os.RemoteException;

    boolean provisionVpnProfile(com.android.internal.net.VpnProfile vpnProfile, java.lang.String str) throws android.os.RemoteException;

    boolean removeVpnAddress(java.lang.String str, int i) throws android.os.RemoteException;

    boolean setAlwaysOnVpnPackage(int i, java.lang.String str, boolean z, java.util.List<java.lang.String> list) throws android.os.RemoteException;

    boolean setAppExclusionList(int i, java.lang.String str, java.util.List<java.lang.String> list) throws android.os.RemoteException;

    boolean setUnderlyingNetworksForVpn(android.net.Network[] networkArr) throws android.os.RemoteException;

    void setVpnPackageAuthorization(java.lang.String str, int i, int i2) throws android.os.RemoteException;

    void startLegacyVpn(com.android.internal.net.VpnProfile vpnProfile) throws android.os.RemoteException;

    java.lang.String startVpnProfile(java.lang.String str) throws android.os.RemoteException;

    void stopVpnProfile(java.lang.String str) throws android.os.RemoteException;

    boolean updateLockdownVpn() throws android.os.RemoteException;

    public static class Default implements android.net.IVpnManager {
        @Override // android.net.IVpnManager
        public boolean prepareVpn(java.lang.String str, java.lang.String str2, int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.net.IVpnManager
        public void setVpnPackageAuthorization(java.lang.String str, int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.net.IVpnManager
        public android.os.ParcelFileDescriptor establishVpn(com.android.internal.net.VpnConfig vpnConfig) throws android.os.RemoteException {
            return null;
        }

        @Override // android.net.IVpnManager
        public boolean addVpnAddress(java.lang.String str, int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.net.IVpnManager
        public boolean removeVpnAddress(java.lang.String str, int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.net.IVpnManager
        public boolean setUnderlyingNetworksForVpn(android.net.Network[] networkArr) throws android.os.RemoteException {
            return false;
        }

        @Override // android.net.IVpnManager
        public boolean provisionVpnProfile(com.android.internal.net.VpnProfile vpnProfile, java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // android.net.IVpnManager
        public void deleteVpnProfile(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.net.IVpnManager
        public java.lang.String startVpnProfile(java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.net.IVpnManager
        public void stopVpnProfile(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.net.IVpnManager
        public android.net.VpnProfileState getProvisionedVpnProfileState(java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.net.IVpnManager
        public boolean setAppExclusionList(int i, java.lang.String str, java.util.List<java.lang.String> list) throws android.os.RemoteException {
            return false;
        }

        @Override // android.net.IVpnManager
        public java.util.List<java.lang.String> getAppExclusionList(int i, java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.net.IVpnManager
        public boolean isAlwaysOnVpnPackageSupported(int i, java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // android.net.IVpnManager
        public boolean setAlwaysOnVpnPackage(int i, java.lang.String str, boolean z, java.util.List<java.lang.String> list) throws android.os.RemoteException {
            return false;
        }

        @Override // android.net.IVpnManager
        public java.lang.String getAlwaysOnVpnPackage(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.net.IVpnManager
        public boolean isVpnLockdownEnabled(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.net.IVpnManager
        public java.util.List<java.lang.String> getVpnLockdownAllowlist(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.net.IVpnManager
        public boolean isCallerCurrentAlwaysOnVpnApp() throws android.os.RemoteException {
            return false;
        }

        @Override // android.net.IVpnManager
        public boolean isCallerCurrentAlwaysOnVpnLockdownApp() throws android.os.RemoteException {
            return false;
        }

        @Override // android.net.IVpnManager
        public void startLegacyVpn(com.android.internal.net.VpnProfile vpnProfile) throws android.os.RemoteException {
        }

        @Override // android.net.IVpnManager
        public com.android.internal.net.LegacyVpnInfo getLegacyVpnInfo(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.net.IVpnManager
        public com.android.internal.net.VpnProfile[] getAllLegacyVpns() throws android.os.RemoteException {
            return null;
        }

        @Override // android.net.IVpnManager
        public boolean updateLockdownVpn() throws android.os.RemoteException {
            return false;
        }

        @Override // android.net.IVpnManager
        public com.android.internal.net.VpnConfig getVpnConfig(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.net.IVpnManager
        public void factoryReset() throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.net.IVpnManager {
        static final int TRANSACTION_addVpnAddress = 4;
        static final int TRANSACTION_deleteVpnProfile = 8;
        static final int TRANSACTION_establishVpn = 3;
        static final int TRANSACTION_factoryReset = 26;
        static final int TRANSACTION_getAllLegacyVpns = 23;
        static final int TRANSACTION_getAlwaysOnVpnPackage = 16;
        static final int TRANSACTION_getAppExclusionList = 13;
        static final int TRANSACTION_getLegacyVpnInfo = 22;
        static final int TRANSACTION_getProvisionedVpnProfileState = 11;
        static final int TRANSACTION_getVpnConfig = 25;
        static final int TRANSACTION_getVpnLockdownAllowlist = 18;
        static final int TRANSACTION_isAlwaysOnVpnPackageSupported = 14;
        static final int TRANSACTION_isCallerCurrentAlwaysOnVpnApp = 19;
        static final int TRANSACTION_isCallerCurrentAlwaysOnVpnLockdownApp = 20;
        static final int TRANSACTION_isVpnLockdownEnabled = 17;
        static final int TRANSACTION_prepareVpn = 1;
        static final int TRANSACTION_provisionVpnProfile = 7;
        static final int TRANSACTION_removeVpnAddress = 5;
        static final int TRANSACTION_setAlwaysOnVpnPackage = 15;
        static final int TRANSACTION_setAppExclusionList = 12;
        static final int TRANSACTION_setUnderlyingNetworksForVpn = 6;
        static final int TRANSACTION_setVpnPackageAuthorization = 2;
        static final int TRANSACTION_startLegacyVpn = 21;
        static final int TRANSACTION_startVpnProfile = 9;
        static final int TRANSACTION_stopVpnProfile = 10;
        static final int TRANSACTION_updateLockdownVpn = 24;

        public Stub() {
            attachInterface(this, android.net.IVpnManager.DESCRIPTOR);
        }

        public static android.net.IVpnManager asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.net.IVpnManager.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.net.IVpnManager)) {
                return (android.net.IVpnManager) queryLocalInterface;
            }
            return new android.net.IVpnManager.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "prepareVpn";
                case 2:
                    return "setVpnPackageAuthorization";
                case 3:
                    return "establishVpn";
                case 4:
                    return "addVpnAddress";
                case 5:
                    return "removeVpnAddress";
                case 6:
                    return "setUnderlyingNetworksForVpn";
                case 7:
                    return "provisionVpnProfile";
                case 8:
                    return "deleteVpnProfile";
                case 9:
                    return "startVpnProfile";
                case 10:
                    return "stopVpnProfile";
                case 11:
                    return "getProvisionedVpnProfileState";
                case 12:
                    return "setAppExclusionList";
                case 13:
                    return "getAppExclusionList";
                case 14:
                    return "isAlwaysOnVpnPackageSupported";
                case 15:
                    return "setAlwaysOnVpnPackage";
                case 16:
                    return "getAlwaysOnVpnPackage";
                case 17:
                    return "isVpnLockdownEnabled";
                case 18:
                    return "getVpnLockdownAllowlist";
                case 19:
                    return "isCallerCurrentAlwaysOnVpnApp";
                case 20:
                    return "isCallerCurrentAlwaysOnVpnLockdownApp";
                case 21:
                    return "startLegacyVpn";
                case 22:
                    return "getLegacyVpnInfo";
                case 23:
                    return "getAllLegacyVpns";
                case 24:
                    return "updateLockdownVpn";
                case 25:
                    return "getVpnConfig";
                case 26:
                    return "factoryReset";
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
                parcel.enforceInterface(android.net.IVpnManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.net.IVpnManager.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    java.lang.String readString = parcel.readString();
                    java.lang.String readString2 = parcel.readString();
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean prepareVpn = prepareVpn(readString, readString2, readInt);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(prepareVpn);
                    return true;
                case 2:
                    java.lang.String readString3 = parcel.readString();
                    int readInt2 = parcel.readInt();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setVpnPackageAuthorization(readString3, readInt2, readInt3);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    com.android.internal.net.VpnConfig vpnConfig = (com.android.internal.net.VpnConfig) parcel.readTypedObject(com.android.internal.net.VpnConfig.CREATOR);
                    parcel.enforceNoDataAvail();
                    android.os.ParcelFileDescriptor establishVpn = establishVpn(vpnConfig);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(establishVpn, 1);
                    return true;
                case 4:
                    java.lang.String readString4 = parcel.readString();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean addVpnAddress = addVpnAddress(readString4, readInt4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(addVpnAddress);
                    return true;
                case 5:
                    java.lang.String readString5 = parcel.readString();
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean removeVpnAddress = removeVpnAddress(readString5, readInt5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(removeVpnAddress);
                    return true;
                case 6:
                    android.net.Network[] networkArr = (android.net.Network[]) parcel.createTypedArray(android.net.Network.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean underlyingNetworksForVpn = setUnderlyingNetworksForVpn(networkArr);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(underlyingNetworksForVpn);
                    return true;
                case 7:
                    com.android.internal.net.VpnProfile vpnProfile = (com.android.internal.net.VpnProfile) parcel.readTypedObject(com.android.internal.net.VpnProfile.CREATOR);
                    java.lang.String readString6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean provisionVpnProfile = provisionVpnProfile(vpnProfile, readString6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(provisionVpnProfile);
                    return true;
                case 8:
                    java.lang.String readString7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    deleteVpnProfile(readString7);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    java.lang.String readString8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    java.lang.String startVpnProfile = startVpnProfile(readString8);
                    parcel2.writeNoException();
                    parcel2.writeString(startVpnProfile);
                    return true;
                case 10:
                    java.lang.String readString9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    stopVpnProfile(readString9);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    java.lang.String readString10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.net.VpnProfileState provisionedVpnProfileState = getProvisionedVpnProfileState(readString10);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(provisionedVpnProfileState, 1);
                    return true;
                case 12:
                    int readInt6 = parcel.readInt();
                    java.lang.String readString11 = parcel.readString();
                    java.util.ArrayList<java.lang.String> createStringArrayList = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean appExclusionList = setAppExclusionList(readInt6, readString11, createStringArrayList);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(appExclusionList);
                    return true;
                case 13:
                    int readInt7 = parcel.readInt();
                    java.lang.String readString12 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    java.util.List<java.lang.String> appExclusionList2 = getAppExclusionList(readInt7, readString12);
                    parcel2.writeNoException();
                    parcel2.writeStringList(appExclusionList2);
                    return true;
                case 14:
                    int readInt8 = parcel.readInt();
                    java.lang.String readString13 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isAlwaysOnVpnPackageSupported = isAlwaysOnVpnPackageSupported(readInt8, readString13);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isAlwaysOnVpnPackageSupported);
                    return true;
                case 15:
                    int readInt9 = parcel.readInt();
                    java.lang.String readString14 = parcel.readString();
                    boolean readBoolean = parcel.readBoolean();
                    java.util.ArrayList<java.lang.String> createStringArrayList2 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean alwaysOnVpnPackage = setAlwaysOnVpnPackage(readInt9, readString14, readBoolean, createStringArrayList2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(alwaysOnVpnPackage);
                    return true;
                case 16:
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.lang.String alwaysOnVpnPackage2 = getAlwaysOnVpnPackage(readInt10);
                    parcel2.writeNoException();
                    parcel2.writeString(alwaysOnVpnPackage2);
                    return true;
                case 17:
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isVpnLockdownEnabled = isVpnLockdownEnabled(readInt11);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isVpnLockdownEnabled);
                    return true;
                case 18:
                    int readInt12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.util.List<java.lang.String> vpnLockdownAllowlist = getVpnLockdownAllowlist(readInt12);
                    parcel2.writeNoException();
                    parcel2.writeStringList(vpnLockdownAllowlist);
                    return true;
                case 19:
                    boolean isCallerCurrentAlwaysOnVpnApp = isCallerCurrentAlwaysOnVpnApp();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isCallerCurrentAlwaysOnVpnApp);
                    return true;
                case 20:
                    boolean isCallerCurrentAlwaysOnVpnLockdownApp = isCallerCurrentAlwaysOnVpnLockdownApp();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isCallerCurrentAlwaysOnVpnLockdownApp);
                    return true;
                case 21:
                    com.android.internal.net.VpnProfile vpnProfile2 = (com.android.internal.net.VpnProfile) parcel.readTypedObject(com.android.internal.net.VpnProfile.CREATOR);
                    parcel.enforceNoDataAvail();
                    startLegacyVpn(vpnProfile2);
                    parcel2.writeNoException();
                    return true;
                case 22:
                    int readInt13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    com.android.internal.net.LegacyVpnInfo legacyVpnInfo = getLegacyVpnInfo(readInt13);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(legacyVpnInfo, 1);
                    return true;
                case 23:
                    com.android.internal.net.VpnProfile[] allLegacyVpns = getAllLegacyVpns();
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(allLegacyVpns, 1);
                    return true;
                case 24:
                    boolean updateLockdownVpn = updateLockdownVpn();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(updateLockdownVpn);
                    return true;
                case 25:
                    int readInt14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    com.android.internal.net.VpnConfig vpnConfig2 = getVpnConfig(readInt14);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(vpnConfig2, 1);
                    return true;
                case 26:
                    factoryReset();
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.net.IVpnManager {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.net.IVpnManager.DESCRIPTOR;
            }

            @Override // android.net.IVpnManager
            public boolean prepareVpn(java.lang.String str, java.lang.String str2, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.IVpnManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public void setVpnPackageAuthorization(java.lang.String str, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.IVpnManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public android.os.ParcelFileDescriptor establishVpn(com.android.internal.net.VpnConfig vpnConfig) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.IVpnManager.DESCRIPTOR);
                    obtain.writeTypedObject(vpnConfig, 0);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.os.ParcelFileDescriptor) obtain2.readTypedObject(android.os.ParcelFileDescriptor.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public boolean addVpnAddress(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.IVpnManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public boolean removeVpnAddress(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.IVpnManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public boolean setUnderlyingNetworksForVpn(android.net.Network[] networkArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.IVpnManager.DESCRIPTOR);
                    obtain.writeTypedArray(networkArr, 0);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public boolean provisionVpnProfile(com.android.internal.net.VpnProfile vpnProfile, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.IVpnManager.DESCRIPTOR);
                    obtain.writeTypedObject(vpnProfile, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public void deleteVpnProfile(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.IVpnManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public java.lang.String startVpnProfile(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.IVpnManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public void stopVpnProfile(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.IVpnManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public android.net.VpnProfileState getProvisionedVpnProfileState(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.IVpnManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.net.VpnProfileState) obtain2.readTypedObject(android.net.VpnProfileState.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public boolean setAppExclusionList(int i, java.lang.String str, java.util.List<java.lang.String> list) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.IVpnManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeStringList(list);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public java.util.List<java.lang.String> getAppExclusionList(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.IVpnManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public boolean isAlwaysOnVpnPackageSupported(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.IVpnManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public boolean setAlwaysOnVpnPackage(int i, java.lang.String str, boolean z, java.util.List<java.lang.String> list) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.IVpnManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeStringList(list);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public java.lang.String getAlwaysOnVpnPackage(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.IVpnManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public boolean isVpnLockdownEnabled(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.IVpnManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public java.util.List<java.lang.String> getVpnLockdownAllowlist(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.IVpnManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public boolean isCallerCurrentAlwaysOnVpnApp() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.IVpnManager.DESCRIPTOR);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public boolean isCallerCurrentAlwaysOnVpnLockdownApp() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.IVpnManager.DESCRIPTOR);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public void startLegacyVpn(com.android.internal.net.VpnProfile vpnProfile) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.IVpnManager.DESCRIPTOR);
                    obtain.writeTypedObject(vpnProfile, 0);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public com.android.internal.net.LegacyVpnInfo getLegacyVpnInfo(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.IVpnManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                    return (com.android.internal.net.LegacyVpnInfo) obtain2.readTypedObject(com.android.internal.net.LegacyVpnInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public com.android.internal.net.VpnProfile[] getAllLegacyVpns() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.IVpnManager.DESCRIPTOR);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                    return (com.android.internal.net.VpnProfile[]) obtain2.createTypedArray(com.android.internal.net.VpnProfile.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public boolean updateLockdownVpn() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.IVpnManager.DESCRIPTOR);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public com.android.internal.net.VpnConfig getVpnConfig(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.IVpnManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                    return (com.android.internal.net.VpnConfig) obtain2.readTypedObject(com.android.internal.net.VpnConfig.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.IVpnManager
            public void factoryReset() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.IVpnManager.DESCRIPTOR);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 25;
        }
    }
}
