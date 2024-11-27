package android.permission;

/* loaded from: classes3.dex */
public interface IPermissionManager extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.permission.IPermissionManager";

    boolean addAllowlistedRestrictedPermission(java.lang.String str, java.lang.String str2, int i, int i2) throws android.os.RemoteException;

    void addOnPermissionsChangeListener(android.permission.IOnPermissionsChangeListener iOnPermissionsChangeListener) throws android.os.RemoteException;

    boolean addPermission(android.content.pm.PermissionInfo permissionInfo, boolean z) throws android.os.RemoteException;

    int checkPermission(java.lang.String str, java.lang.String str2, java.lang.String str3, int i) throws android.os.RemoteException;

    int checkUidPermission(int i, java.lang.String str, int i2) throws android.os.RemoteException;

    android.content.pm.ParceledListSlice getAllPermissionGroups(int i) throws android.os.RemoteException;

    java.util.Map<java.lang.String, android.permission.PermissionManager.PermissionState> getAllPermissionStates(java.lang.String str, java.lang.String str2, int i) throws android.os.RemoteException;

    java.util.List<java.lang.String> getAllowlistedRestrictedPermissions(java.lang.String str, int i, int i2) throws android.os.RemoteException;

    java.util.List<java.lang.String> getAutoRevokeExemptionGrantedPackages(int i) throws android.os.RemoteException;

    java.util.List<java.lang.String> getAutoRevokeExemptionRequestedPackages(int i) throws android.os.RemoteException;

    int getPermissionFlags(java.lang.String str, java.lang.String str2, java.lang.String str3, int i) throws android.os.RemoteException;

    android.content.pm.PermissionGroupInfo getPermissionGroupInfo(java.lang.String str, int i) throws android.os.RemoteException;

    android.content.pm.PermissionInfo getPermissionInfo(java.lang.String str, java.lang.String str2, int i) throws android.os.RemoteException;

    java.util.List<android.content.pm.permission.SplitPermissionInfoParcelable> getSplitPermissions() throws android.os.RemoteException;

    void grantRuntimePermission(java.lang.String str, java.lang.String str2, java.lang.String str3, int i) throws android.os.RemoteException;

    boolean isAutoRevokeExempted(java.lang.String str, int i) throws android.os.RemoteException;

    boolean isPermissionRevokedByPolicy(java.lang.String str, java.lang.String str2, int i, int i2) throws android.os.RemoteException;

    boolean isRegisteredAttributionSource(android.content.AttributionSourceState attributionSourceState) throws android.os.RemoteException;

    android.content.pm.ParceledListSlice queryPermissionsByGroup(java.lang.String str, int i) throws android.os.RemoteException;

    android.os.IBinder registerAttributionSource(android.content.AttributionSourceState attributionSourceState) throws android.os.RemoteException;

    boolean removeAllowlistedRestrictedPermission(java.lang.String str, java.lang.String str2, int i, int i2) throws android.os.RemoteException;

    void removeOnPermissionsChangeListener(android.permission.IOnPermissionsChangeListener iOnPermissionsChangeListener) throws android.os.RemoteException;

    void removePermission(java.lang.String str) throws android.os.RemoteException;

    void revokePostNotificationPermissionWithoutKillForTest(java.lang.String str, int i) throws android.os.RemoteException;

    void revokeRuntimePermission(java.lang.String str, java.lang.String str2, java.lang.String str3, int i, java.lang.String str4) throws android.os.RemoteException;

    boolean setAutoRevokeExempted(java.lang.String str, boolean z, int i) throws android.os.RemoteException;

    boolean shouldShowRequestPermissionRationale(java.lang.String str, java.lang.String str2, int i, int i2) throws android.os.RemoteException;

    void startOneTimePermissionSession(java.lang.String str, int i, int i2, long j, long j2) throws android.os.RemoteException;

    void stopOneTimePermissionSession(java.lang.String str, int i) throws android.os.RemoteException;

    void updatePermissionFlags(java.lang.String str, java.lang.String str2, int i, int i2, boolean z, java.lang.String str3, int i3) throws android.os.RemoteException;

    void updatePermissionFlagsForAllApps(int i, int i2, int i3) throws android.os.RemoteException;

    public static class Default implements android.permission.IPermissionManager {
        @Override // android.permission.IPermissionManager
        public android.content.pm.ParceledListSlice getAllPermissionGroups(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.permission.IPermissionManager
        public android.content.pm.PermissionGroupInfo getPermissionGroupInfo(java.lang.String str, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.permission.IPermissionManager
        public android.content.pm.PermissionInfo getPermissionInfo(java.lang.String str, java.lang.String str2, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.permission.IPermissionManager
        public android.content.pm.ParceledListSlice queryPermissionsByGroup(java.lang.String str, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.permission.IPermissionManager
        public boolean addPermission(android.content.pm.PermissionInfo permissionInfo, boolean z) throws android.os.RemoteException {
            return false;
        }

        @Override // android.permission.IPermissionManager
        public void removePermission(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.permission.IPermissionManager
        public int getPermissionFlags(java.lang.String str, java.lang.String str2, java.lang.String str3, int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.permission.IPermissionManager
        public void updatePermissionFlags(java.lang.String str, java.lang.String str2, int i, int i2, boolean z, java.lang.String str3, int i3) throws android.os.RemoteException {
        }

        @Override // android.permission.IPermissionManager
        public void updatePermissionFlagsForAllApps(int i, int i2, int i3) throws android.os.RemoteException {
        }

        @Override // android.permission.IPermissionManager
        public void addOnPermissionsChangeListener(android.permission.IOnPermissionsChangeListener iOnPermissionsChangeListener) throws android.os.RemoteException {
        }

        @Override // android.permission.IPermissionManager
        public void removeOnPermissionsChangeListener(android.permission.IOnPermissionsChangeListener iOnPermissionsChangeListener) throws android.os.RemoteException {
        }

        @Override // android.permission.IPermissionManager
        public java.util.List<java.lang.String> getAllowlistedRestrictedPermissions(java.lang.String str, int i, int i2) throws android.os.RemoteException {
            return null;
        }

        @Override // android.permission.IPermissionManager
        public boolean addAllowlistedRestrictedPermission(java.lang.String str, java.lang.String str2, int i, int i2) throws android.os.RemoteException {
            return false;
        }

        @Override // android.permission.IPermissionManager
        public boolean removeAllowlistedRestrictedPermission(java.lang.String str, java.lang.String str2, int i, int i2) throws android.os.RemoteException {
            return false;
        }

        @Override // android.permission.IPermissionManager
        public void grantRuntimePermission(java.lang.String str, java.lang.String str2, java.lang.String str3, int i) throws android.os.RemoteException {
        }

        @Override // android.permission.IPermissionManager
        public void revokeRuntimePermission(java.lang.String str, java.lang.String str2, java.lang.String str3, int i, java.lang.String str4) throws android.os.RemoteException {
        }

        @Override // android.permission.IPermissionManager
        public void revokePostNotificationPermissionWithoutKillForTest(java.lang.String str, int i) throws android.os.RemoteException {
        }

        @Override // android.permission.IPermissionManager
        public boolean shouldShowRequestPermissionRationale(java.lang.String str, java.lang.String str2, int i, int i2) throws android.os.RemoteException {
            return false;
        }

        @Override // android.permission.IPermissionManager
        public boolean isPermissionRevokedByPolicy(java.lang.String str, java.lang.String str2, int i, int i2) throws android.os.RemoteException {
            return false;
        }

        @Override // android.permission.IPermissionManager
        public java.util.List<android.content.pm.permission.SplitPermissionInfoParcelable> getSplitPermissions() throws android.os.RemoteException {
            return null;
        }

        @Override // android.permission.IPermissionManager
        public void startOneTimePermissionSession(java.lang.String str, int i, int i2, long j, long j2) throws android.os.RemoteException {
        }

        @Override // android.permission.IPermissionManager
        public void stopOneTimePermissionSession(java.lang.String str, int i) throws android.os.RemoteException {
        }

        @Override // android.permission.IPermissionManager
        public java.util.List<java.lang.String> getAutoRevokeExemptionRequestedPackages(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.permission.IPermissionManager
        public java.util.List<java.lang.String> getAutoRevokeExemptionGrantedPackages(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.permission.IPermissionManager
        public boolean setAutoRevokeExempted(java.lang.String str, boolean z, int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.permission.IPermissionManager
        public boolean isAutoRevokeExempted(java.lang.String str, int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.permission.IPermissionManager
        public android.os.IBinder registerAttributionSource(android.content.AttributionSourceState attributionSourceState) throws android.os.RemoteException {
            return null;
        }

        @Override // android.permission.IPermissionManager
        public boolean isRegisteredAttributionSource(android.content.AttributionSourceState attributionSourceState) throws android.os.RemoteException {
            return false;
        }

        @Override // android.permission.IPermissionManager
        public int checkPermission(java.lang.String str, java.lang.String str2, java.lang.String str3, int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.permission.IPermissionManager
        public int checkUidPermission(int i, java.lang.String str, int i2) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.permission.IPermissionManager
        public java.util.Map<java.lang.String, android.permission.PermissionManager.PermissionState> getAllPermissionStates(java.lang.String str, java.lang.String str2, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.permission.IPermissionManager {
        static final int TRANSACTION_addAllowlistedRestrictedPermission = 13;
        static final int TRANSACTION_addOnPermissionsChangeListener = 10;
        static final int TRANSACTION_addPermission = 5;
        static final int TRANSACTION_checkPermission = 29;
        static final int TRANSACTION_checkUidPermission = 30;
        static final int TRANSACTION_getAllPermissionGroups = 1;
        static final int TRANSACTION_getAllPermissionStates = 31;
        static final int TRANSACTION_getAllowlistedRestrictedPermissions = 12;
        static final int TRANSACTION_getAutoRevokeExemptionGrantedPackages = 24;
        static final int TRANSACTION_getAutoRevokeExemptionRequestedPackages = 23;
        static final int TRANSACTION_getPermissionFlags = 7;
        static final int TRANSACTION_getPermissionGroupInfo = 2;
        static final int TRANSACTION_getPermissionInfo = 3;
        static final int TRANSACTION_getSplitPermissions = 20;
        static final int TRANSACTION_grantRuntimePermission = 15;
        static final int TRANSACTION_isAutoRevokeExempted = 26;
        static final int TRANSACTION_isPermissionRevokedByPolicy = 19;
        static final int TRANSACTION_isRegisteredAttributionSource = 28;
        static final int TRANSACTION_queryPermissionsByGroup = 4;
        static final int TRANSACTION_registerAttributionSource = 27;
        static final int TRANSACTION_removeAllowlistedRestrictedPermission = 14;
        static final int TRANSACTION_removeOnPermissionsChangeListener = 11;
        static final int TRANSACTION_removePermission = 6;
        static final int TRANSACTION_revokePostNotificationPermissionWithoutKillForTest = 17;
        static final int TRANSACTION_revokeRuntimePermission = 16;
        static final int TRANSACTION_setAutoRevokeExempted = 25;
        static final int TRANSACTION_shouldShowRequestPermissionRationale = 18;
        static final int TRANSACTION_startOneTimePermissionSession = 21;
        static final int TRANSACTION_stopOneTimePermissionSession = 22;
        static final int TRANSACTION_updatePermissionFlags = 8;
        static final int TRANSACTION_updatePermissionFlagsForAllApps = 9;
        private final android.os.PermissionEnforcer mEnforcer;

        public Stub(android.os.PermissionEnforcer permissionEnforcer) {
            attachInterface(this, android.permission.IPermissionManager.DESCRIPTOR);
            if (permissionEnforcer == null) {
                throw new java.lang.IllegalArgumentException("enforcer cannot be null");
            }
            this.mEnforcer = permissionEnforcer;
        }

        @java.lang.Deprecated
        public Stub() {
            this(android.os.PermissionEnforcer.fromContext(android.app.ActivityThread.currentActivityThread().getSystemContext()));
        }

        public static android.permission.IPermissionManager asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.permission.IPermissionManager.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.permission.IPermissionManager)) {
                return (android.permission.IPermissionManager) queryLocalInterface;
            }
            return new android.permission.IPermissionManager.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getAllPermissionGroups";
                case 2:
                    return "getPermissionGroupInfo";
                case 3:
                    return "getPermissionInfo";
                case 4:
                    return "queryPermissionsByGroup";
                case 5:
                    return "addPermission";
                case 6:
                    return "removePermission";
                case 7:
                    return "getPermissionFlags";
                case 8:
                    return "updatePermissionFlags";
                case 9:
                    return "updatePermissionFlagsForAllApps";
                case 10:
                    return "addOnPermissionsChangeListener";
                case 11:
                    return "removeOnPermissionsChangeListener";
                case 12:
                    return "getAllowlistedRestrictedPermissions";
                case 13:
                    return "addAllowlistedRestrictedPermission";
                case 14:
                    return "removeAllowlistedRestrictedPermission";
                case 15:
                    return "grantRuntimePermission";
                case 16:
                    return "revokeRuntimePermission";
                case 17:
                    return "revokePostNotificationPermissionWithoutKillForTest";
                case 18:
                    return "shouldShowRequestPermissionRationale";
                case 19:
                    return "isPermissionRevokedByPolicy";
                case 20:
                    return "getSplitPermissions";
                case 21:
                    return "startOneTimePermissionSession";
                case 22:
                    return "stopOneTimePermissionSession";
                case 23:
                    return "getAutoRevokeExemptionRequestedPackages";
                case 24:
                    return "getAutoRevokeExemptionGrantedPackages";
                case 25:
                    return "setAutoRevokeExempted";
                case 26:
                    return "isAutoRevokeExempted";
                case 27:
                    return "registerAttributionSource";
                case 28:
                    return "isRegisteredAttributionSource";
                case 29:
                    return "checkPermission";
                case 30:
                    return "checkUidPermission";
                case 31:
                    return "getAllPermissionStates";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public java.lang.String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, android.os.Parcel parcel, final android.os.Parcel parcel2, int i2) throws android.os.RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(android.permission.IPermissionManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.permission.IPermissionManager.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.content.pm.ParceledListSlice allPermissionGroups = getAllPermissionGroups(readInt);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(allPermissionGroups, 1);
                    return true;
                case 2:
                    java.lang.String readString = parcel.readString();
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.content.pm.PermissionGroupInfo permissionGroupInfo = getPermissionGroupInfo(readString, readInt2);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(permissionGroupInfo, 1);
                    return true;
                case 3:
                    java.lang.String readString2 = parcel.readString();
                    java.lang.String readString3 = parcel.readString();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.content.pm.PermissionInfo permissionInfo = getPermissionInfo(readString2, readString3, readInt3);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(permissionInfo, 1);
                    return true;
                case 4:
                    java.lang.String readString4 = parcel.readString();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.content.pm.ParceledListSlice queryPermissionsByGroup = queryPermissionsByGroup(readString4, readInt4);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(queryPermissionsByGroup, 1);
                    return true;
                case 5:
                    android.content.pm.PermissionInfo permissionInfo2 = (android.content.pm.PermissionInfo) parcel.readTypedObject(android.content.pm.PermissionInfo.CREATOR);
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean addPermission = addPermission(permissionInfo2, readBoolean);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(addPermission);
                    return true;
                case 6:
                    java.lang.String readString5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    removePermission(readString5);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    java.lang.String readString6 = parcel.readString();
                    java.lang.String readString7 = parcel.readString();
                    java.lang.String readString8 = parcel.readString();
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int permissionFlags = getPermissionFlags(readString6, readString7, readString8, readInt5);
                    parcel2.writeNoException();
                    parcel2.writeInt(permissionFlags);
                    return true;
                case 8:
                    java.lang.String readString9 = parcel.readString();
                    java.lang.String readString10 = parcel.readString();
                    int readInt6 = parcel.readInt();
                    int readInt7 = parcel.readInt();
                    boolean readBoolean2 = parcel.readBoolean();
                    java.lang.String readString11 = parcel.readString();
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    updatePermissionFlags(readString9, readString10, readInt6, readInt7, readBoolean2, readString11, readInt8);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    int readInt9 = parcel.readInt();
                    int readInt10 = parcel.readInt();
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    updatePermissionFlagsForAllApps(readInt9, readInt10, readInt11);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    android.permission.IOnPermissionsChangeListener asInterface = android.permission.IOnPermissionsChangeListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    addOnPermissionsChangeListener(asInterface);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    android.permission.IOnPermissionsChangeListener asInterface2 = android.permission.IOnPermissionsChangeListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    removeOnPermissionsChangeListener(asInterface2);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    java.lang.String readString12 = parcel.readString();
                    int readInt12 = parcel.readInt();
                    int readInt13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.util.List<java.lang.String> allowlistedRestrictedPermissions = getAllowlistedRestrictedPermissions(readString12, readInt12, readInt13);
                    parcel2.writeNoException();
                    parcel2.writeStringList(allowlistedRestrictedPermissions);
                    return true;
                case 13:
                    java.lang.String readString13 = parcel.readString();
                    java.lang.String readString14 = parcel.readString();
                    int readInt14 = parcel.readInt();
                    int readInt15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean addAllowlistedRestrictedPermission = addAllowlistedRestrictedPermission(readString13, readString14, readInt14, readInt15);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(addAllowlistedRestrictedPermission);
                    return true;
                case 14:
                    java.lang.String readString15 = parcel.readString();
                    java.lang.String readString16 = parcel.readString();
                    int readInt16 = parcel.readInt();
                    int readInt17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean removeAllowlistedRestrictedPermission = removeAllowlistedRestrictedPermission(readString15, readString16, readInt16, readInt17);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(removeAllowlistedRestrictedPermission);
                    return true;
                case 15:
                    java.lang.String readString17 = parcel.readString();
                    java.lang.String readString18 = parcel.readString();
                    java.lang.String readString19 = parcel.readString();
                    int readInt18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    grantRuntimePermission(readString17, readString18, readString19, readInt18);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    java.lang.String readString20 = parcel.readString();
                    java.lang.String readString21 = parcel.readString();
                    java.lang.String readString22 = parcel.readString();
                    int readInt19 = parcel.readInt();
                    java.lang.String readString23 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    revokeRuntimePermission(readString20, readString21, readString22, readInt19, readString23);
                    parcel2.writeNoException();
                    return true;
                case 17:
                    java.lang.String readString24 = parcel.readString();
                    int readInt20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    revokePostNotificationPermissionWithoutKillForTest(readString24, readInt20);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    java.lang.String readString25 = parcel.readString();
                    java.lang.String readString26 = parcel.readString();
                    int readInt21 = parcel.readInt();
                    int readInt22 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean shouldShowRequestPermissionRationale = shouldShowRequestPermissionRationale(readString25, readString26, readInt21, readInt22);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(shouldShowRequestPermissionRationale);
                    return true;
                case 19:
                    java.lang.String readString27 = parcel.readString();
                    java.lang.String readString28 = parcel.readString();
                    int readInt23 = parcel.readInt();
                    int readInt24 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isPermissionRevokedByPolicy = isPermissionRevokedByPolicy(readString27, readString28, readInt23, readInt24);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isPermissionRevokedByPolicy);
                    return true;
                case 20:
                    java.util.List<android.content.pm.permission.SplitPermissionInfoParcelable> splitPermissions = getSplitPermissions();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(splitPermissions, 1);
                    return true;
                case 21:
                    java.lang.String readString29 = parcel.readString();
                    int readInt25 = parcel.readInt();
                    int readInt26 = parcel.readInt();
                    long readLong = parcel.readLong();
                    long readLong2 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    startOneTimePermissionSession(readString29, readInt25, readInt26, readLong, readLong2);
                    parcel2.writeNoException();
                    return true;
                case 22:
                    java.lang.String readString30 = parcel.readString();
                    int readInt27 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    stopOneTimePermissionSession(readString30, readInt27);
                    parcel2.writeNoException();
                    return true;
                case 23:
                    int readInt28 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.util.List<java.lang.String> autoRevokeExemptionRequestedPackages = getAutoRevokeExemptionRequestedPackages(readInt28);
                    parcel2.writeNoException();
                    parcel2.writeStringList(autoRevokeExemptionRequestedPackages);
                    return true;
                case 24:
                    int readInt29 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.util.List<java.lang.String> autoRevokeExemptionGrantedPackages = getAutoRevokeExemptionGrantedPackages(readInt29);
                    parcel2.writeNoException();
                    parcel2.writeStringList(autoRevokeExemptionGrantedPackages);
                    return true;
                case 25:
                    java.lang.String readString31 = parcel.readString();
                    boolean readBoolean3 = parcel.readBoolean();
                    int readInt30 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean autoRevokeExempted = setAutoRevokeExempted(readString31, readBoolean3, readInt30);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(autoRevokeExempted);
                    return true;
                case 26:
                    java.lang.String readString32 = parcel.readString();
                    int readInt31 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isAutoRevokeExempted = isAutoRevokeExempted(readString32, readInt31);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isAutoRevokeExempted);
                    return true;
                case 27:
                    android.content.AttributionSourceState attributionSourceState = (android.content.AttributionSourceState) parcel.readTypedObject(android.content.AttributionSourceState.CREATOR);
                    parcel.enforceNoDataAvail();
                    android.os.IBinder registerAttributionSource = registerAttributionSource(attributionSourceState);
                    parcel2.writeNoException();
                    parcel2.writeStrongBinder(registerAttributionSource);
                    return true;
                case 28:
                    android.content.AttributionSourceState attributionSourceState2 = (android.content.AttributionSourceState) parcel.readTypedObject(android.content.AttributionSourceState.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isRegisteredAttributionSource = isRegisteredAttributionSource(attributionSourceState2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isRegisteredAttributionSource);
                    return true;
                case 29:
                    java.lang.String readString33 = parcel.readString();
                    java.lang.String readString34 = parcel.readString();
                    java.lang.String readString35 = parcel.readString();
                    int readInt32 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int checkPermission = checkPermission(readString33, readString34, readString35, readInt32);
                    parcel2.writeNoException();
                    parcel2.writeInt(checkPermission);
                    return true;
                case 30:
                    int readInt33 = parcel.readInt();
                    java.lang.String readString36 = parcel.readString();
                    int readInt34 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int checkUidPermission = checkUidPermission(readInt33, readString36, readInt34);
                    parcel2.writeNoException();
                    parcel2.writeInt(checkUidPermission);
                    return true;
                case 31:
                    java.lang.String readString37 = parcel.readString();
                    java.lang.String readString38 = parcel.readString();
                    int readInt35 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.util.Map<java.lang.String, android.permission.PermissionManager.PermissionState> allPermissionStates = getAllPermissionStates(readString37, readString38, readInt35);
                    parcel2.writeNoException();
                    if (allPermissionStates == null) {
                        parcel2.writeInt(-1);
                    } else {
                        parcel2.writeInt(allPermissionStates.size());
                        allPermissionStates.forEach(new java.util.function.BiConsumer() { // from class: android.permission.IPermissionManager$Stub$$ExternalSyntheticLambda0
                            @Override // java.util.function.BiConsumer
                            public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                                android.permission.IPermissionManager.Stub.lambda$onTransact$0(android.os.Parcel.this, (java.lang.String) obj, (android.permission.PermissionManager.PermissionState) obj2);
                            }
                        });
                    }
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        static /* synthetic */ void lambda$onTransact$0(android.os.Parcel parcel, java.lang.String str, android.permission.PermissionManager.PermissionState permissionState) {
            parcel.writeString(str);
            parcel.writeTypedObject(permissionState, 1);
        }

        /* JADX INFO: Access modifiers changed from: private */
        static class Proxy implements android.permission.IPermissionManager {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.permission.IPermissionManager.DESCRIPTOR;
            }

            @Override // android.permission.IPermissionManager
            public android.content.pm.ParceledListSlice getAllPermissionGroups(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.permission.IPermissionManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.pm.ParceledListSlice) obtain2.readTypedObject(android.content.pm.ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.permission.IPermissionManager
            public android.content.pm.PermissionGroupInfo getPermissionGroupInfo(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.permission.IPermissionManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.pm.PermissionGroupInfo) obtain2.readTypedObject(android.content.pm.PermissionGroupInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.permission.IPermissionManager
            public android.content.pm.PermissionInfo getPermissionInfo(java.lang.String str, java.lang.String str2, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.permission.IPermissionManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.pm.PermissionInfo) obtain2.readTypedObject(android.content.pm.PermissionInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.permission.IPermissionManager
            public android.content.pm.ParceledListSlice queryPermissionsByGroup(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.permission.IPermissionManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.pm.ParceledListSlice) obtain2.readTypedObject(android.content.pm.ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.permission.IPermissionManager
            public boolean addPermission(android.content.pm.PermissionInfo permissionInfo, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.permission.IPermissionManager.DESCRIPTOR);
                    obtain.writeTypedObject(permissionInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.permission.IPermissionManager
            public void removePermission(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.permission.IPermissionManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.permission.IPermissionManager
            public int getPermissionFlags(java.lang.String str, java.lang.String str2, java.lang.String str3, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.permission.IPermissionManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeInt(i);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.permission.IPermissionManager
            public void updatePermissionFlags(java.lang.String str, java.lang.String str2, int i, int i2, boolean z, java.lang.String str3, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.permission.IPermissionManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeBoolean(z);
                    obtain.writeString(str3);
                    obtain.writeInt(i3);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.permission.IPermissionManager
            public void updatePermissionFlagsForAllApps(int i, int i2, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.permission.IPermissionManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.permission.IPermissionManager
            public void addOnPermissionsChangeListener(android.permission.IOnPermissionsChangeListener iOnPermissionsChangeListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.permission.IPermissionManager.DESCRIPTOR);
                    obtain.writeStrongInterface(iOnPermissionsChangeListener);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.permission.IPermissionManager
            public void removeOnPermissionsChangeListener(android.permission.IOnPermissionsChangeListener iOnPermissionsChangeListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.permission.IPermissionManager.DESCRIPTOR);
                    obtain.writeStrongInterface(iOnPermissionsChangeListener);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.permission.IPermissionManager
            public java.util.List<java.lang.String> getAllowlistedRestrictedPermissions(java.lang.String str, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.permission.IPermissionManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.permission.IPermissionManager
            public boolean addAllowlistedRestrictedPermission(java.lang.String str, java.lang.String str2, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.permission.IPermissionManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.permission.IPermissionManager
            public boolean removeAllowlistedRestrictedPermission(java.lang.String str, java.lang.String str2, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.permission.IPermissionManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.permission.IPermissionManager
            public void grantRuntimePermission(java.lang.String str, java.lang.String str2, java.lang.String str3, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.permission.IPermissionManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeInt(i);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.permission.IPermissionManager
            public void revokeRuntimePermission(java.lang.String str, java.lang.String str2, java.lang.String str3, int i, java.lang.String str4) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.permission.IPermissionManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeInt(i);
                    obtain.writeString(str4);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.permission.IPermissionManager
            public void revokePostNotificationPermissionWithoutKillForTest(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.permission.IPermissionManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.permission.IPermissionManager
            public boolean shouldShowRequestPermissionRationale(java.lang.String str, java.lang.String str2, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.permission.IPermissionManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.permission.IPermissionManager
            public boolean isPermissionRevokedByPolicy(java.lang.String str, java.lang.String str2, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.permission.IPermissionManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.permission.IPermissionManager
            public java.util.List<android.content.pm.permission.SplitPermissionInfoParcelable> getSplitPermissions() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.permission.IPermissionManager.DESCRIPTOR);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.content.pm.permission.SplitPermissionInfoParcelable.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.permission.IPermissionManager
            public void startOneTimePermissionSession(java.lang.String str, int i, int i2, long j, long j2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.permission.IPermissionManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeLong(j);
                    obtain.writeLong(j2);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.permission.IPermissionManager
            public void stopOneTimePermissionSession(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.permission.IPermissionManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.permission.IPermissionManager
            public java.util.List<java.lang.String> getAutoRevokeExemptionRequestedPackages(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.permission.IPermissionManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.permission.IPermissionManager
            public java.util.List<java.lang.String> getAutoRevokeExemptionGrantedPackages(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.permission.IPermissionManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.permission.IPermissionManager
            public boolean setAutoRevokeExempted(java.lang.String str, boolean z, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.permission.IPermissionManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.permission.IPermissionManager
            public boolean isAutoRevokeExempted(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.permission.IPermissionManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.permission.IPermissionManager
            public android.os.IBinder registerAttributionSource(android.content.AttributionSourceState attributionSourceState) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.permission.IPermissionManager.DESCRIPTOR);
                    obtain.writeTypedObject(attributionSourceState, 0);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readStrongBinder();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.permission.IPermissionManager
            public boolean isRegisteredAttributionSource(android.content.AttributionSourceState attributionSourceState) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.permission.IPermissionManager.DESCRIPTOR);
                    obtain.writeTypedObject(attributionSourceState, 0);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.permission.IPermissionManager
            public int checkPermission(java.lang.String str, java.lang.String str2, java.lang.String str3, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.permission.IPermissionManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeInt(i);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.permission.IPermissionManager
            public int checkUidPermission(int i, java.lang.String str, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.permission.IPermissionManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeInt(i2);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.permission.IPermissionManager
            public java.util.Map<java.lang.String, android.permission.PermissionManager.PermissionState> getAllPermissionStates(java.lang.String str, java.lang.String str2, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                final android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.permission.IPermissionManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                    int readInt = obtain2.readInt();
                    final java.util.HashMap hashMap = readInt < 0 ? null : new java.util.HashMap();
                    java.util.stream.IntStream.range(0, readInt).forEach(new java.util.function.IntConsumer() { // from class: android.permission.IPermissionManager$Stub$Proxy$$ExternalSyntheticLambda0
                        @Override // java.util.function.IntConsumer
                        public final void accept(int i2) {
                            hashMap.put(r0.readString(), (android.permission.PermissionManager.PermissionState) android.os.Parcel.this.readTypedObject(android.permission.PermissionManager.PermissionState.CREATOR));
                        }
                    });
                    return hashMap;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        protected void startOneTimePermissionSession_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MANAGE_ONE_TIME_PERMISSION_SESSIONS, getCallingPid(), getCallingUid());
        }

        protected void stopOneTimePermissionSession_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MANAGE_ONE_TIME_PERMISSION_SESSIONS, getCallingPid(), getCallingUid());
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 30;
        }
    }
}
