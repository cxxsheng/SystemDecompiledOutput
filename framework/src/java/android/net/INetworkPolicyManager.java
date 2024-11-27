package android.net;

/* loaded from: classes2.dex */
public interface INetworkPolicyManager extends android.os.IInterface {
    void addUidPolicy(int i, int i2) throws android.os.RemoteException;

    void applyRestore(byte[] bArr, int i) throws android.os.RemoteException;

    void factoryReset(java.lang.String str) throws android.os.RemoteException;

    byte[] getBackupPayload(int i) throws android.os.RemoteException;

    int getMultipathPreference(android.net.Network network) throws android.os.RemoteException;

    android.net.NetworkPolicy[] getNetworkPolicies(java.lang.String str) throws android.os.RemoteException;

    boolean getRestrictBackground() throws android.os.RemoteException;

    int getRestrictBackgroundByCaller() throws android.os.RemoteException;

    int getRestrictBackgroundStatus(int i) throws android.os.RemoteException;

    android.telephony.SubscriptionPlan getSubscriptionPlan(android.net.NetworkTemplate networkTemplate) throws android.os.RemoteException;

    android.telephony.SubscriptionPlan[] getSubscriptionPlans(int i, java.lang.String str) throws android.os.RemoteException;

    java.lang.String getSubscriptionPlansOwner(int i) throws android.os.RemoteException;

    int getUidPolicy(int i) throws android.os.RemoteException;

    int[] getUidsWithPolicy(int i) throws android.os.RemoteException;

    boolean isUidNetworkingBlocked(int i, boolean z) throws android.os.RemoteException;

    boolean isUidRestrictedOnMeteredNetworks(int i) throws android.os.RemoteException;

    void notifyDenylistChanged(int[] iArr, int[] iArr2) throws android.os.RemoteException;

    void notifyStatsProviderWarningOrLimitReached() throws android.os.RemoteException;

    void registerListener(android.net.INetworkPolicyListener iNetworkPolicyListener) throws android.os.RemoteException;

    void removeUidPolicy(int i, int i2) throws android.os.RemoteException;

    void setDeviceIdleMode(boolean z) throws android.os.RemoteException;

    void setNetworkPolicies(android.net.NetworkPolicy[] networkPolicyArr) throws android.os.RemoteException;

    void setRestrictBackground(boolean z) throws android.os.RemoteException;

    void setSubscriptionOverride(int i, int i2, int i3, int[] iArr, long j, java.lang.String str) throws android.os.RemoteException;

    void setSubscriptionPlans(int i, android.telephony.SubscriptionPlan[] subscriptionPlanArr, long j, java.lang.String str) throws android.os.RemoteException;

    void setUidPolicy(int i, int i2) throws android.os.RemoteException;

    void setWifiMeteredOverride(java.lang.String str, int i) throws android.os.RemoteException;

    void snoozeLimit(android.net.NetworkTemplate networkTemplate) throws android.os.RemoteException;

    void unregisterListener(android.net.INetworkPolicyListener iNetworkPolicyListener) throws android.os.RemoteException;

    public static class Default implements android.net.INetworkPolicyManager {
        @Override // android.net.INetworkPolicyManager
        public void setUidPolicy(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.net.INetworkPolicyManager
        public void addUidPolicy(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.net.INetworkPolicyManager
        public void removeUidPolicy(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.net.INetworkPolicyManager
        public int getUidPolicy(int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.net.INetworkPolicyManager
        public int[] getUidsWithPolicy(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.net.INetworkPolicyManager
        public void registerListener(android.net.INetworkPolicyListener iNetworkPolicyListener) throws android.os.RemoteException {
        }

        @Override // android.net.INetworkPolicyManager
        public void unregisterListener(android.net.INetworkPolicyListener iNetworkPolicyListener) throws android.os.RemoteException {
        }

        @Override // android.net.INetworkPolicyManager
        public void setNetworkPolicies(android.net.NetworkPolicy[] networkPolicyArr) throws android.os.RemoteException {
        }

        @Override // android.net.INetworkPolicyManager
        public android.net.NetworkPolicy[] getNetworkPolicies(java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.net.INetworkPolicyManager
        public void snoozeLimit(android.net.NetworkTemplate networkTemplate) throws android.os.RemoteException {
        }

        @Override // android.net.INetworkPolicyManager
        public void setRestrictBackground(boolean z) throws android.os.RemoteException {
        }

        @Override // android.net.INetworkPolicyManager
        public boolean getRestrictBackground() throws android.os.RemoteException {
            return false;
        }

        @Override // android.net.INetworkPolicyManager
        public int getRestrictBackgroundByCaller() throws android.os.RemoteException {
            return 0;
        }

        @Override // android.net.INetworkPolicyManager
        public int getRestrictBackgroundStatus(int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.net.INetworkPolicyManager
        public void setDeviceIdleMode(boolean z) throws android.os.RemoteException {
        }

        @Override // android.net.INetworkPolicyManager
        public void setWifiMeteredOverride(java.lang.String str, int i) throws android.os.RemoteException {
        }

        @Override // android.net.INetworkPolicyManager
        public int getMultipathPreference(android.net.Network network) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.net.INetworkPolicyManager
        public android.telephony.SubscriptionPlan getSubscriptionPlan(android.net.NetworkTemplate networkTemplate) throws android.os.RemoteException {
            return null;
        }

        @Override // android.net.INetworkPolicyManager
        public void notifyStatsProviderWarningOrLimitReached() throws android.os.RemoteException {
        }

        @Override // android.net.INetworkPolicyManager
        public android.telephony.SubscriptionPlan[] getSubscriptionPlans(int i, java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.net.INetworkPolicyManager
        public void setSubscriptionPlans(int i, android.telephony.SubscriptionPlan[] subscriptionPlanArr, long j, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.net.INetworkPolicyManager
        public java.lang.String getSubscriptionPlansOwner(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.net.INetworkPolicyManager
        public void setSubscriptionOverride(int i, int i2, int i3, int[] iArr, long j, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.net.INetworkPolicyManager
        public void factoryReset(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.net.INetworkPolicyManager
        public boolean isUidNetworkingBlocked(int i, boolean z) throws android.os.RemoteException {
            return false;
        }

        @Override // android.net.INetworkPolicyManager
        public boolean isUidRestrictedOnMeteredNetworks(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.net.INetworkPolicyManager
        public byte[] getBackupPayload(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.net.INetworkPolicyManager
        public void applyRestore(byte[] bArr, int i) throws android.os.RemoteException {
        }

        @Override // android.net.INetworkPolicyManager
        public void notifyDenylistChanged(int[] iArr, int[] iArr2) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.net.INetworkPolicyManager {
        public static final java.lang.String DESCRIPTOR = "android.net.INetworkPolicyManager";
        static final int TRANSACTION_addUidPolicy = 2;
        static final int TRANSACTION_applyRestore = 28;
        static final int TRANSACTION_factoryReset = 24;
        static final int TRANSACTION_getBackupPayload = 27;
        static final int TRANSACTION_getMultipathPreference = 17;
        static final int TRANSACTION_getNetworkPolicies = 9;
        static final int TRANSACTION_getRestrictBackground = 12;
        static final int TRANSACTION_getRestrictBackgroundByCaller = 13;
        static final int TRANSACTION_getRestrictBackgroundStatus = 14;
        static final int TRANSACTION_getSubscriptionPlan = 18;
        static final int TRANSACTION_getSubscriptionPlans = 20;
        static final int TRANSACTION_getSubscriptionPlansOwner = 22;
        static final int TRANSACTION_getUidPolicy = 4;
        static final int TRANSACTION_getUidsWithPolicy = 5;
        static final int TRANSACTION_isUidNetworkingBlocked = 25;
        static final int TRANSACTION_isUidRestrictedOnMeteredNetworks = 26;
        static final int TRANSACTION_notifyDenylistChanged = 29;
        static final int TRANSACTION_notifyStatsProviderWarningOrLimitReached = 19;
        static final int TRANSACTION_registerListener = 6;
        static final int TRANSACTION_removeUidPolicy = 3;
        static final int TRANSACTION_setDeviceIdleMode = 15;
        static final int TRANSACTION_setNetworkPolicies = 8;
        static final int TRANSACTION_setRestrictBackground = 11;
        static final int TRANSACTION_setSubscriptionOverride = 23;
        static final int TRANSACTION_setSubscriptionPlans = 21;
        static final int TRANSACTION_setUidPolicy = 1;
        static final int TRANSACTION_setWifiMeteredOverride = 16;
        static final int TRANSACTION_snoozeLimit = 10;
        static final int TRANSACTION_unregisterListener = 7;
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

        public static android.net.INetworkPolicyManager asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.net.INetworkPolicyManager)) {
                return (android.net.INetworkPolicyManager) queryLocalInterface;
            }
            return new android.net.INetworkPolicyManager.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "setUidPolicy";
                case 2:
                    return "addUidPolicy";
                case 3:
                    return "removeUidPolicy";
                case 4:
                    return "getUidPolicy";
                case 5:
                    return "getUidsWithPolicy";
                case 6:
                    return "registerListener";
                case 7:
                    return "unregisterListener";
                case 8:
                    return "setNetworkPolicies";
                case 9:
                    return "getNetworkPolicies";
                case 10:
                    return "snoozeLimit";
                case 11:
                    return "setRestrictBackground";
                case 12:
                    return "getRestrictBackground";
                case 13:
                    return "getRestrictBackgroundByCaller";
                case 14:
                    return "getRestrictBackgroundStatus";
                case 15:
                    return "setDeviceIdleMode";
                case 16:
                    return "setWifiMeteredOverride";
                case 17:
                    return "getMultipathPreference";
                case 18:
                    return "getSubscriptionPlan";
                case 19:
                    return "notifyStatsProviderWarningOrLimitReached";
                case 20:
                    return "getSubscriptionPlans";
                case 21:
                    return "setSubscriptionPlans";
                case 22:
                    return "getSubscriptionPlansOwner";
                case 23:
                    return "setSubscriptionOverride";
                case 24:
                    return "factoryReset";
                case 25:
                    return "isUidNetworkingBlocked";
                case 26:
                    return "isUidRestrictedOnMeteredNetworks";
                case 27:
                    return "getBackupPayload";
                case 28:
                    return "applyRestore";
                case 29:
                    return "notifyDenylistChanged";
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
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setUidPolicy(readInt, readInt2);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    addUidPolicy(readInt3, readInt4);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    int readInt5 = parcel.readInt();
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeUidPolicy(readInt5, readInt6);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int uidPolicy = getUidPolicy(readInt7);
                    parcel2.writeNoException();
                    parcel2.writeInt(uidPolicy);
                    return true;
                case 5:
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int[] uidsWithPolicy = getUidsWithPolicy(readInt8);
                    parcel2.writeNoException();
                    parcel2.writeIntArray(uidsWithPolicy);
                    return true;
                case 6:
                    android.net.INetworkPolicyListener asInterface = android.net.INetworkPolicyListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerListener(asInterface);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    android.net.INetworkPolicyListener asInterface2 = android.net.INetworkPolicyListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterListener(asInterface2);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    android.net.NetworkPolicy[] networkPolicyArr = (android.net.NetworkPolicy[]) parcel.createTypedArray(android.net.NetworkPolicy.CREATOR);
                    parcel.enforceNoDataAvail();
                    setNetworkPolicies(networkPolicyArr);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    java.lang.String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.net.NetworkPolicy[] networkPolicies = getNetworkPolicies(readString);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(networkPolicies, 1);
                    return true;
                case 10:
                    android.net.NetworkTemplate networkTemplate = (android.net.NetworkTemplate) parcel.readTypedObject(android.net.NetworkTemplate.CREATOR);
                    parcel.enforceNoDataAvail();
                    snoozeLimit(networkTemplate);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setRestrictBackground(readBoolean);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    boolean restrictBackground = getRestrictBackground();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(restrictBackground);
                    return true;
                case 13:
                    int restrictBackgroundByCaller = getRestrictBackgroundByCaller();
                    parcel2.writeNoException();
                    parcel2.writeInt(restrictBackgroundByCaller);
                    return true;
                case 14:
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int restrictBackgroundStatus = getRestrictBackgroundStatus(readInt9);
                    parcel2.writeNoException();
                    parcel2.writeInt(restrictBackgroundStatus);
                    return true;
                case 15:
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setDeviceIdleMode(readBoolean2);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    java.lang.String readString2 = parcel.readString();
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setWifiMeteredOverride(readString2, readInt10);
                    parcel2.writeNoException();
                    return true;
                case 17:
                    android.net.Network network = (android.net.Network) parcel.readTypedObject(android.net.Network.CREATOR);
                    parcel.enforceNoDataAvail();
                    int multipathPreference = getMultipathPreference(network);
                    parcel2.writeNoException();
                    parcel2.writeInt(multipathPreference);
                    return true;
                case 18:
                    android.net.NetworkTemplate networkTemplate2 = (android.net.NetworkTemplate) parcel.readTypedObject(android.net.NetworkTemplate.CREATOR);
                    parcel.enforceNoDataAvail();
                    android.telephony.SubscriptionPlan subscriptionPlan = getSubscriptionPlan(networkTemplate2);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(subscriptionPlan, 1);
                    return true;
                case 19:
                    notifyStatsProviderWarningOrLimitReached();
                    parcel2.writeNoException();
                    return true;
                case 20:
                    int readInt11 = parcel.readInt();
                    java.lang.String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.telephony.SubscriptionPlan[] subscriptionPlans = getSubscriptionPlans(readInt11, readString3);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(subscriptionPlans, 1);
                    return true;
                case 21:
                    int readInt12 = parcel.readInt();
                    android.telephony.SubscriptionPlan[] subscriptionPlanArr = (android.telephony.SubscriptionPlan[]) parcel.createTypedArray(android.telephony.SubscriptionPlan.CREATOR);
                    long readLong = parcel.readLong();
                    java.lang.String readString4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setSubscriptionPlans(readInt12, subscriptionPlanArr, readLong, readString4);
                    parcel2.writeNoException();
                    return true;
                case 22:
                    int readInt13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.lang.String subscriptionPlansOwner = getSubscriptionPlansOwner(readInt13);
                    parcel2.writeNoException();
                    parcel2.writeString(subscriptionPlansOwner);
                    return true;
                case 23:
                    int readInt14 = parcel.readInt();
                    int readInt15 = parcel.readInt();
                    int readInt16 = parcel.readInt();
                    int[] createIntArray = parcel.createIntArray();
                    long readLong2 = parcel.readLong();
                    java.lang.String readString5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setSubscriptionOverride(readInt14, readInt15, readInt16, createIntArray, readLong2, readString5);
                    parcel2.writeNoException();
                    return true;
                case 24:
                    java.lang.String readString6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    factoryReset(readString6);
                    parcel2.writeNoException();
                    return true;
                case 25:
                    int readInt17 = parcel.readInt();
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean isUidNetworkingBlocked = isUidNetworkingBlocked(readInt17, readBoolean3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isUidNetworkingBlocked);
                    return true;
                case 26:
                    int readInt18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isUidRestrictedOnMeteredNetworks = isUidRestrictedOnMeteredNetworks(readInt18);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isUidRestrictedOnMeteredNetworks);
                    return true;
                case 27:
                    int readInt19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    byte[] backupPayload = getBackupPayload(readInt19);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(backupPayload);
                    return true;
                case 28:
                    byte[] createByteArray = parcel.createByteArray();
                    int readInt20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    applyRestore(createByteArray, readInt20);
                    parcel2.writeNoException();
                    return true;
                case 29:
                    int[] createIntArray2 = parcel.createIntArray();
                    int[] createIntArray3 = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    notifyDenylistChanged(createIntArray2, createIntArray3);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.net.INetworkPolicyManager {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.net.INetworkPolicyManager.Stub.DESCRIPTOR;
            }

            @Override // android.net.INetworkPolicyManager
            public void setUidPolicy(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetworkPolicyManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetworkPolicyManager
            public void addUidPolicy(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetworkPolicyManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetworkPolicyManager
            public void removeUidPolicy(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetworkPolicyManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetworkPolicyManager
            public int getUidPolicy(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetworkPolicyManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetworkPolicyManager
            public int[] getUidsWithPolicy(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetworkPolicyManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetworkPolicyManager
            public void registerListener(android.net.INetworkPolicyListener iNetworkPolicyListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetworkPolicyManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iNetworkPolicyListener);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetworkPolicyManager
            public void unregisterListener(android.net.INetworkPolicyListener iNetworkPolicyListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetworkPolicyManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iNetworkPolicyListener);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetworkPolicyManager
            public void setNetworkPolicies(android.net.NetworkPolicy[] networkPolicyArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetworkPolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedArray(networkPolicyArr, 0);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetworkPolicyManager
            public android.net.NetworkPolicy[] getNetworkPolicies(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetworkPolicyManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.net.NetworkPolicy[]) obtain2.createTypedArray(android.net.NetworkPolicy.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetworkPolicyManager
            public void snoozeLimit(android.net.NetworkTemplate networkTemplate) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetworkPolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(networkTemplate, 0);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetworkPolicyManager
            public void setRestrictBackground(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetworkPolicyManager.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetworkPolicyManager
            public boolean getRestrictBackground() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetworkPolicyManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetworkPolicyManager
            public int getRestrictBackgroundByCaller() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetworkPolicyManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetworkPolicyManager
            public int getRestrictBackgroundStatus(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetworkPolicyManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetworkPolicyManager
            public void setDeviceIdleMode(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetworkPolicyManager.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetworkPolicyManager
            public void setWifiMeteredOverride(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetworkPolicyManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetworkPolicyManager
            public int getMultipathPreference(android.net.Network network) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetworkPolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(network, 0);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetworkPolicyManager
            public android.telephony.SubscriptionPlan getSubscriptionPlan(android.net.NetworkTemplate networkTemplate) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetworkPolicyManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(networkTemplate, 0);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.telephony.SubscriptionPlan) obtain2.readTypedObject(android.telephony.SubscriptionPlan.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetworkPolicyManager
            public void notifyStatsProviderWarningOrLimitReached() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetworkPolicyManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetworkPolicyManager
            public android.telephony.SubscriptionPlan[] getSubscriptionPlans(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetworkPolicyManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.telephony.SubscriptionPlan[]) obtain2.createTypedArray(android.telephony.SubscriptionPlan.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetworkPolicyManager
            public void setSubscriptionPlans(int i, android.telephony.SubscriptionPlan[] subscriptionPlanArr, long j, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetworkPolicyManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedArray(subscriptionPlanArr, 0);
                    obtain.writeLong(j);
                    obtain.writeString(str);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetworkPolicyManager
            public java.lang.String getSubscriptionPlansOwner(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetworkPolicyManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetworkPolicyManager
            public void setSubscriptionOverride(int i, int i2, int i3, int[] iArr, long j, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetworkPolicyManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeIntArray(iArr);
                    obtain.writeLong(j);
                    obtain.writeString(str);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetworkPolicyManager
            public void factoryReset(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetworkPolicyManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetworkPolicyManager
            public boolean isUidNetworkingBlocked(int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetworkPolicyManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetworkPolicyManager
            public boolean isUidRestrictedOnMeteredNetworks(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetworkPolicyManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetworkPolicyManager
            public byte[] getBackupPayload(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetworkPolicyManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetworkPolicyManager
            public void applyRestore(byte[] bArr, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetworkPolicyManager.Stub.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    obtain.writeInt(i);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.INetworkPolicyManager
            public void notifyDenylistChanged(int[] iArr, int[] iArr2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.net.INetworkPolicyManager.Stub.DESCRIPTOR);
                    obtain.writeIntArray(iArr);
                    obtain.writeIntArray(iArr2);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        protected void setUidPolicy_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MANAGE_NETWORK_POLICY, getCallingPid(), getCallingUid());
        }

        protected void addUidPolicy_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MANAGE_NETWORK_POLICY, getCallingPid(), getCallingUid());
        }

        protected void removeUidPolicy_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MANAGE_NETWORK_POLICY, getCallingPid(), getCallingUid());
        }

        protected void getUidPolicy_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MANAGE_NETWORK_POLICY, getCallingPid(), getCallingUid());
        }

        protected void getUidsWithPolicy_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MANAGE_NETWORK_POLICY, getCallingPid(), getCallingUid());
        }

        protected void setNetworkPolicies_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MANAGE_NETWORK_POLICY, getCallingPid(), getCallingUid());
        }

        protected void getNetworkPolicies_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MANAGE_NETWORK_POLICY, getCallingPid(), getCallingUid());
        }

        protected void snoozeLimit_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MANAGE_NETWORK_POLICY, getCallingPid(), getCallingUid());
        }

        protected void getRestrictBackground_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MANAGE_NETWORK_POLICY, getCallingPid(), getCallingUid());
        }

        protected void getRestrictBackgroundByCaller_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.ACCESS_NETWORK_STATE, getCallingPid(), getCallingUid());
        }

        protected void setDeviceIdleMode_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MANAGE_NETWORK_POLICY, getCallingPid(), getCallingUid());
        }

        protected void setWifiMeteredOverride_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MANAGE_NETWORK_POLICY, getCallingPid(), getCallingUid());
        }

        protected void factoryReset_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.NETWORK_SETTINGS, getCallingPid(), getCallingUid());
        }

        protected void isUidRestrictedOnMeteredNetworks_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.OBSERVE_NETWORK_POLICY, getCallingPid(), getCallingUid());
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 28;
        }
    }
}
