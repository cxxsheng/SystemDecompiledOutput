package android.os;

/* loaded from: classes3.dex */
public interface INetworkManagementService extends android.os.IInterface {
    void allowProtect(int i) throws android.os.RemoteException;

    void clearInterfaceAddresses(java.lang.String str) throws android.os.RemoteException;

    void denyProtect(int i) throws android.os.RemoteException;

    void disableIpv6(java.lang.String str) throws android.os.RemoteException;

    void disableNat(java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    void enableIpv6(java.lang.String str) throws android.os.RemoteException;

    void enableNat(java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    android.net.InterfaceConfiguration getInterfaceConfig(java.lang.String str) throws android.os.RemoteException;

    boolean getIpForwardingEnabled() throws android.os.RemoteException;

    boolean isBandwidthControlEnabled() throws android.os.RemoteException;

    boolean isFirewallEnabled() throws android.os.RemoteException;

    boolean isNetworkRestricted(int i) throws android.os.RemoteException;

    boolean isTetheringStarted() throws android.os.RemoteException;

    java.lang.String[] listInterfaces() throws android.os.RemoteException;

    java.lang.String[] listTetheredInterfaces() throws android.os.RemoteException;

    void registerObserver(android.net.INetworkManagementEventObserver iNetworkManagementEventObserver) throws android.os.RemoteException;

    void removeInterfaceAlert(java.lang.String str) throws android.os.RemoteException;

    void removeInterfaceQuota(java.lang.String str) throws android.os.RemoteException;

    boolean setDataSaverModeEnabled(boolean z) throws android.os.RemoteException;

    void setFirewallChainEnabled(int i, boolean z) throws android.os.RemoteException;

    void setFirewallEnabled(boolean z) throws android.os.RemoteException;

    void setFirewallUidRule(int i, int i2, int i3) throws android.os.RemoteException;

    void setFirewallUidRules(int i, int[] iArr, int[] iArr2) throws android.os.RemoteException;

    void setIPv6AddrGenMode(java.lang.String str, int i) throws android.os.RemoteException;

    void setInterfaceAlert(java.lang.String str, long j) throws android.os.RemoteException;

    void setInterfaceConfig(java.lang.String str, android.net.InterfaceConfiguration interfaceConfiguration) throws android.os.RemoteException;

    void setInterfaceDown(java.lang.String str) throws android.os.RemoteException;

    void setInterfaceIpv6PrivacyExtensions(java.lang.String str, boolean z) throws android.os.RemoteException;

    void setInterfaceQuota(java.lang.String str, long j) throws android.os.RemoteException;

    void setInterfaceUp(java.lang.String str) throws android.os.RemoteException;

    void setIpForwardingEnabled(boolean z) throws android.os.RemoteException;

    void setUidCleartextNetworkPolicy(int i, int i2) throws android.os.RemoteException;

    void setUidOnMeteredNetworkAllowlist(int i, boolean z) throws android.os.RemoteException;

    void setUidOnMeteredNetworkDenylist(int i, boolean z) throws android.os.RemoteException;

    void shutdown() throws android.os.RemoteException;

    void startTethering(java.lang.String[] strArr) throws android.os.RemoteException;

    void stopTethering() throws android.os.RemoteException;

    void tetherInterface(java.lang.String str) throws android.os.RemoteException;

    void unregisterObserver(android.net.INetworkManagementEventObserver iNetworkManagementEventObserver) throws android.os.RemoteException;

    void untetherInterface(java.lang.String str) throws android.os.RemoteException;

    public static class Default implements android.os.INetworkManagementService {
        @Override // android.os.INetworkManagementService
        public void registerObserver(android.net.INetworkManagementEventObserver iNetworkManagementEventObserver) throws android.os.RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public void unregisterObserver(android.net.INetworkManagementEventObserver iNetworkManagementEventObserver) throws android.os.RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public java.lang.String[] listInterfaces() throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.INetworkManagementService
        public android.net.InterfaceConfiguration getInterfaceConfig(java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.INetworkManagementService
        public void setInterfaceConfig(java.lang.String str, android.net.InterfaceConfiguration interfaceConfiguration) throws android.os.RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public void clearInterfaceAddresses(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public void setInterfaceDown(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public void setInterfaceUp(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public void setInterfaceIpv6PrivacyExtensions(java.lang.String str, boolean z) throws android.os.RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public void disableIpv6(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public void enableIpv6(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public void setIPv6AddrGenMode(java.lang.String str, int i) throws android.os.RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public void shutdown() throws android.os.RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public boolean getIpForwardingEnabled() throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.INetworkManagementService
        public void setIpForwardingEnabled(boolean z) throws android.os.RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public void startTethering(java.lang.String[] strArr) throws android.os.RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public void stopTethering() throws android.os.RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public boolean isTetheringStarted() throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.INetworkManagementService
        public void tetherInterface(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public void untetherInterface(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public java.lang.String[] listTetheredInterfaces() throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.INetworkManagementService
        public void enableNat(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public void disableNat(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public void setInterfaceQuota(java.lang.String str, long j) throws android.os.RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public void removeInterfaceQuota(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public void setInterfaceAlert(java.lang.String str, long j) throws android.os.RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public void removeInterfaceAlert(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public void setUidOnMeteredNetworkDenylist(int i, boolean z) throws android.os.RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public void setUidOnMeteredNetworkAllowlist(int i, boolean z) throws android.os.RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public boolean setDataSaverModeEnabled(boolean z) throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.INetworkManagementService
        public void setUidCleartextNetworkPolicy(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public boolean isBandwidthControlEnabled() throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.INetworkManagementService
        public void setFirewallEnabled(boolean z) throws android.os.RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public boolean isFirewallEnabled() throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.INetworkManagementService
        public void setFirewallUidRule(int i, int i2, int i3) throws android.os.RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public void setFirewallUidRules(int i, int[] iArr, int[] iArr2) throws android.os.RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public void setFirewallChainEnabled(int i, boolean z) throws android.os.RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public void allowProtect(int i) throws android.os.RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public void denyProtect(int i) throws android.os.RemoteException {
        }

        @Override // android.os.INetworkManagementService
        public boolean isNetworkRestricted(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.os.INetworkManagementService {
        public static final java.lang.String DESCRIPTOR = "android.os.INetworkManagementService";
        static final int TRANSACTION_allowProtect = 38;
        static final int TRANSACTION_clearInterfaceAddresses = 6;
        static final int TRANSACTION_denyProtect = 39;
        static final int TRANSACTION_disableIpv6 = 10;
        static final int TRANSACTION_disableNat = 23;
        static final int TRANSACTION_enableIpv6 = 11;
        static final int TRANSACTION_enableNat = 22;
        static final int TRANSACTION_getInterfaceConfig = 4;
        static final int TRANSACTION_getIpForwardingEnabled = 14;
        static final int TRANSACTION_isBandwidthControlEnabled = 32;
        static final int TRANSACTION_isFirewallEnabled = 34;
        static final int TRANSACTION_isNetworkRestricted = 40;
        static final int TRANSACTION_isTetheringStarted = 18;
        static final int TRANSACTION_listInterfaces = 3;
        static final int TRANSACTION_listTetheredInterfaces = 21;
        static final int TRANSACTION_registerObserver = 1;
        static final int TRANSACTION_removeInterfaceAlert = 27;
        static final int TRANSACTION_removeInterfaceQuota = 25;
        static final int TRANSACTION_setDataSaverModeEnabled = 30;
        static final int TRANSACTION_setFirewallChainEnabled = 37;
        static final int TRANSACTION_setFirewallEnabled = 33;
        static final int TRANSACTION_setFirewallUidRule = 35;
        static final int TRANSACTION_setFirewallUidRules = 36;
        static final int TRANSACTION_setIPv6AddrGenMode = 12;
        static final int TRANSACTION_setInterfaceAlert = 26;
        static final int TRANSACTION_setInterfaceConfig = 5;
        static final int TRANSACTION_setInterfaceDown = 7;
        static final int TRANSACTION_setInterfaceIpv6PrivacyExtensions = 9;
        static final int TRANSACTION_setInterfaceQuota = 24;
        static final int TRANSACTION_setInterfaceUp = 8;
        static final int TRANSACTION_setIpForwardingEnabled = 15;
        static final int TRANSACTION_setUidCleartextNetworkPolicy = 31;
        static final int TRANSACTION_setUidOnMeteredNetworkAllowlist = 29;
        static final int TRANSACTION_setUidOnMeteredNetworkDenylist = 28;
        static final int TRANSACTION_shutdown = 13;
        static final int TRANSACTION_startTethering = 16;
        static final int TRANSACTION_stopTethering = 17;
        static final int TRANSACTION_tetherInterface = 19;
        static final int TRANSACTION_unregisterObserver = 2;
        static final int TRANSACTION_untetherInterface = 20;
        private final android.os.PermissionEnforcer mEnforcer;

        public Stub(android.os.PermissionEnforcer permissionEnforcer) {
            attachInterface(this, DESCRIPTOR);
            if (permissionEnforcer == null) {
                throw new java.lang.IllegalArgumentException("enforcer cannot be null");
            }
            this.mEnforcer = permissionEnforcer;
        }

        @java.lang.Deprecated
        public Stub() {
            this(android.os.PermissionEnforcer.fromContext(android.app.ActivityThread.currentActivityThread().getSystemContext()));
        }

        public static android.os.INetworkManagementService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.os.INetworkManagementService)) {
                return (android.os.INetworkManagementService) queryLocalInterface;
            }
            return new android.os.INetworkManagementService.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "registerObserver";
                case 2:
                    return "unregisterObserver";
                case 3:
                    return "listInterfaces";
                case 4:
                    return "getInterfaceConfig";
                case 5:
                    return "setInterfaceConfig";
                case 6:
                    return "clearInterfaceAddresses";
                case 7:
                    return "setInterfaceDown";
                case 8:
                    return "setInterfaceUp";
                case 9:
                    return "setInterfaceIpv6PrivacyExtensions";
                case 10:
                    return "disableIpv6";
                case 11:
                    return "enableIpv6";
                case 12:
                    return "setIPv6AddrGenMode";
                case 13:
                    return "shutdown";
                case 14:
                    return "getIpForwardingEnabled";
                case 15:
                    return "setIpForwardingEnabled";
                case 16:
                    return "startTethering";
                case 17:
                    return "stopTethering";
                case 18:
                    return "isTetheringStarted";
                case 19:
                    return "tetherInterface";
                case 20:
                    return "untetherInterface";
                case 21:
                    return "listTetheredInterfaces";
                case 22:
                    return "enableNat";
                case 23:
                    return "disableNat";
                case 24:
                    return "setInterfaceQuota";
                case 25:
                    return "removeInterfaceQuota";
                case 26:
                    return "setInterfaceAlert";
                case 27:
                    return "removeInterfaceAlert";
                case 28:
                    return "setUidOnMeteredNetworkDenylist";
                case 29:
                    return "setUidOnMeteredNetworkAllowlist";
                case 30:
                    return "setDataSaverModeEnabled";
                case 31:
                    return "setUidCleartextNetworkPolicy";
                case 32:
                    return "isBandwidthControlEnabled";
                case 33:
                    return "setFirewallEnabled";
                case 34:
                    return "isFirewallEnabled";
                case 35:
                    return "setFirewallUidRule";
                case 36:
                    return "setFirewallUidRules";
                case 37:
                    return "setFirewallChainEnabled";
                case 38:
                    return "allowProtect";
                case 39:
                    return "denyProtect";
                case 40:
                    return "isNetworkRestricted";
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
                    android.net.INetworkManagementEventObserver asInterface = android.net.INetworkManagementEventObserver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerObserver(asInterface);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    android.net.INetworkManagementEventObserver asInterface2 = android.net.INetworkManagementEventObserver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterObserver(asInterface2);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    java.lang.String[] listInterfaces = listInterfaces();
                    parcel2.writeNoException();
                    parcel2.writeStringArray(listInterfaces);
                    return true;
                case 4:
                    java.lang.String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.net.InterfaceConfiguration interfaceConfig = getInterfaceConfig(readString);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(interfaceConfig, 1);
                    return true;
                case 5:
                    java.lang.String readString2 = parcel.readString();
                    android.net.InterfaceConfiguration interfaceConfiguration = (android.net.InterfaceConfiguration) parcel.readTypedObject(android.net.InterfaceConfiguration.CREATOR);
                    parcel.enforceNoDataAvail();
                    setInterfaceConfig(readString2, interfaceConfiguration);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    java.lang.String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    clearInterfaceAddresses(readString3);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    java.lang.String readString4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setInterfaceDown(readString4);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    java.lang.String readString5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setInterfaceUp(readString5);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    java.lang.String readString6 = parcel.readString();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setInterfaceIpv6PrivacyExtensions(readString6, readBoolean);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    java.lang.String readString7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    disableIpv6(readString7);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    java.lang.String readString8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    enableIpv6(readString8);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    java.lang.String readString9 = parcel.readString();
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setIPv6AddrGenMode(readString9, readInt);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    shutdown();
                    parcel2.writeNoException();
                    return true;
                case 14:
                    boolean ipForwardingEnabled = getIpForwardingEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(ipForwardingEnabled);
                    return true;
                case 15:
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setIpForwardingEnabled(readBoolean2);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    java.lang.String[] createStringArray = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    startTethering(createStringArray);
                    parcel2.writeNoException();
                    return true;
                case 17:
                    stopTethering();
                    parcel2.writeNoException();
                    return true;
                case 18:
                    boolean isTetheringStarted = isTetheringStarted();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isTetheringStarted);
                    return true;
                case 19:
                    java.lang.String readString10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    tetherInterface(readString10);
                    parcel2.writeNoException();
                    return true;
                case 20:
                    java.lang.String readString11 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    untetherInterface(readString11);
                    parcel2.writeNoException();
                    return true;
                case 21:
                    java.lang.String[] listTetheredInterfaces = listTetheredInterfaces();
                    parcel2.writeNoException();
                    parcel2.writeStringArray(listTetheredInterfaces);
                    return true;
                case 22:
                    java.lang.String readString12 = parcel.readString();
                    java.lang.String readString13 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    enableNat(readString12, readString13);
                    parcel2.writeNoException();
                    return true;
                case 23:
                    java.lang.String readString14 = parcel.readString();
                    java.lang.String readString15 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    disableNat(readString14, readString15);
                    parcel2.writeNoException();
                    return true;
                case 24:
                    java.lang.String readString16 = parcel.readString();
                    long readLong = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    setInterfaceQuota(readString16, readLong);
                    parcel2.writeNoException();
                    return true;
                case 25:
                    java.lang.String readString17 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    removeInterfaceQuota(readString17);
                    parcel2.writeNoException();
                    return true;
                case 26:
                    java.lang.String readString18 = parcel.readString();
                    long readLong2 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    setInterfaceAlert(readString18, readLong2);
                    parcel2.writeNoException();
                    return true;
                case 27:
                    java.lang.String readString19 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    removeInterfaceAlert(readString19);
                    parcel2.writeNoException();
                    return true;
                case 28:
                    int readInt2 = parcel.readInt();
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setUidOnMeteredNetworkDenylist(readInt2, readBoolean3);
                    parcel2.writeNoException();
                    return true;
                case 29:
                    int readInt3 = parcel.readInt();
                    boolean readBoolean4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setUidOnMeteredNetworkAllowlist(readInt3, readBoolean4);
                    parcel2.writeNoException();
                    return true;
                case 30:
                    boolean readBoolean5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean dataSaverModeEnabled = setDataSaverModeEnabled(readBoolean5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(dataSaverModeEnabled);
                    return true;
                case 31:
                    int readInt4 = parcel.readInt();
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setUidCleartextNetworkPolicy(readInt4, readInt5);
                    parcel2.writeNoException();
                    return true;
                case 32:
                    boolean isBandwidthControlEnabled = isBandwidthControlEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isBandwidthControlEnabled);
                    return true;
                case 33:
                    boolean readBoolean6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setFirewallEnabled(readBoolean6);
                    parcel2.writeNoException();
                    return true;
                case 34:
                    boolean isFirewallEnabled = isFirewallEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isFirewallEnabled);
                    return true;
                case 35:
                    int readInt6 = parcel.readInt();
                    int readInt7 = parcel.readInt();
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setFirewallUidRule(readInt6, readInt7, readInt8);
                    parcel2.writeNoException();
                    return true;
                case 36:
                    int readInt9 = parcel.readInt();
                    int[] createIntArray = parcel.createIntArray();
                    int[] createIntArray2 = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    setFirewallUidRules(readInt9, createIntArray, createIntArray2);
                    parcel2.writeNoException();
                    return true;
                case 37:
                    int readInt10 = parcel.readInt();
                    boolean readBoolean7 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setFirewallChainEnabled(readInt10, readBoolean7);
                    parcel2.writeNoException();
                    return true;
                case 38:
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    allowProtect(readInt11);
                    parcel2.writeNoException();
                    return true;
                case 39:
                    int readInt12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    denyProtect(readInt12);
                    parcel2.writeNoException();
                    return true;
                case 40:
                    int readInt13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isNetworkRestricted = isNetworkRestricted(readInt13);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isNetworkRestricted);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.os.INetworkManagementService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.os.INetworkManagementService.Stub.DESCRIPTOR;
            }

            @Override // android.os.INetworkManagementService
            public void registerObserver(android.net.INetworkManagementEventObserver iNetworkManagementEventObserver) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.INetworkManagementService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iNetworkManagementEventObserver);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void unregisterObserver(android.net.INetworkManagementEventObserver iNetworkManagementEventObserver) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.INetworkManagementService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iNetworkManagementEventObserver);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public java.lang.String[] listInterfaces() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.INetworkManagementService.Stub.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public android.net.InterfaceConfiguration getInterfaceConfig(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.INetworkManagementService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.net.InterfaceConfiguration) obtain2.readTypedObject(android.net.InterfaceConfiguration.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void setInterfaceConfig(java.lang.String str, android.net.InterfaceConfiguration interfaceConfiguration) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.INetworkManagementService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(interfaceConfiguration, 0);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void clearInterfaceAddresses(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.INetworkManagementService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void setInterfaceDown(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.INetworkManagementService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void setInterfaceUp(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.INetworkManagementService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void setInterfaceIpv6PrivacyExtensions(java.lang.String str, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.INetworkManagementService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void disableIpv6(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.INetworkManagementService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void enableIpv6(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.INetworkManagementService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void setIPv6AddrGenMode(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.INetworkManagementService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void shutdown() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.INetworkManagementService.Stub.DESCRIPTOR);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public boolean getIpForwardingEnabled() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.INetworkManagementService.Stub.DESCRIPTOR);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void setIpForwardingEnabled(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.INetworkManagementService.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void startTethering(java.lang.String[] strArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.INetworkManagementService.Stub.DESCRIPTOR);
                    obtain.writeStringArray(strArr);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void stopTethering() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.INetworkManagementService.Stub.DESCRIPTOR);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public boolean isTetheringStarted() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.INetworkManagementService.Stub.DESCRIPTOR);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void tetherInterface(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.INetworkManagementService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void untetherInterface(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.INetworkManagementService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public java.lang.String[] listTetheredInterfaces() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.INetworkManagementService.Stub.DESCRIPTOR);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void enableNat(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.INetworkManagementService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void disableNat(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.INetworkManagementService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void setInterfaceQuota(java.lang.String str, long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.INetworkManagementService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void removeInterfaceQuota(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.INetworkManagementService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void setInterfaceAlert(java.lang.String str, long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.INetworkManagementService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void removeInterfaceAlert(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.INetworkManagementService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void setUidOnMeteredNetworkDenylist(int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.INetworkManagementService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void setUidOnMeteredNetworkAllowlist(int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.INetworkManagementService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public boolean setDataSaverModeEnabled(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.INetworkManagementService.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void setUidCleartextNetworkPolicy(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.INetworkManagementService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public boolean isBandwidthControlEnabled() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.INetworkManagementService.Stub.DESCRIPTOR);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void setFirewallEnabled(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.INetworkManagementService.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public boolean isFirewallEnabled() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.INetworkManagementService.Stub.DESCRIPTOR);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void setFirewallUidRule(int i, int i2, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.INetworkManagementService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void setFirewallUidRules(int i, int[] iArr, int[] iArr2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.INetworkManagementService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeIntArray(iArr);
                    obtain.writeIntArray(iArr2);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void setFirewallChainEnabled(int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.INetworkManagementService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(37, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void allowProtect(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.INetworkManagementService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(38, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public void denyProtect(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.INetworkManagementService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(39, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.INetworkManagementService
            public boolean isNetworkRestricted(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.INetworkManagementService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(40, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        protected void shutdown_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.SHUTDOWN, getCallingPid(), getCallingUid());
        }

        protected void setDataSaverModeEnabled_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.NETWORK_SETTINGS, getCallingPid(), getCallingUid());
        }

        protected void isNetworkRestricted_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.OBSERVE_NETWORK_POLICY, getCallingPid(), getCallingUid());
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 39;
        }
    }
}
