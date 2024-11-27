package com.android.internal.telephony;

/* loaded from: classes5.dex */
public interface ISub extends android.os.IInterface {
    int addSubInfo(java.lang.String str, java.lang.String str2, int i, int i2) throws android.os.RemoteException;

    void addSubscriptionsIntoGroup(int[] iArr, android.os.ParcelUuid parcelUuid, java.lang.String str) throws android.os.RemoteException;

    boolean canDisablePhysicalSubscription() throws android.os.RemoteException;

    android.os.ParcelUuid createSubscriptionGroup(int[] iArr, java.lang.String str) throws android.os.RemoteException;

    java.util.List<android.telephony.SubscriptionInfo> getAccessibleSubscriptionInfoList(java.lang.String str) throws android.os.RemoteException;

    int getActiveDataSubscriptionId() throws android.os.RemoteException;

    int[] getActiveSubIdList(boolean z) throws android.os.RemoteException;

    int getActiveSubInfoCount(java.lang.String str, java.lang.String str2, boolean z) throws android.os.RemoteException;

    int getActiveSubInfoCountMax() throws android.os.RemoteException;

    android.telephony.SubscriptionInfo getActiveSubscriptionInfo(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    android.telephony.SubscriptionInfo getActiveSubscriptionInfoForIccId(java.lang.String str, java.lang.String str2, java.lang.String str3) throws android.os.RemoteException;

    android.telephony.SubscriptionInfo getActiveSubscriptionInfoForSimSlotIndex(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    java.util.List<android.telephony.SubscriptionInfo> getActiveSubscriptionInfoList(java.lang.String str, java.lang.String str2, boolean z) throws android.os.RemoteException;

    java.util.List<android.telephony.SubscriptionInfo> getAllSubInfoList(java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    java.util.List<android.telephony.SubscriptionInfo> getAvailableSubscriptionInfoList(java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    int getDefaultDataSubId() throws android.os.RemoteException;

    int getDefaultSmsSubId() throws android.os.RemoteException;

    int getDefaultSmsSubIdAsUser(int i) throws android.os.RemoteException;

    int getDefaultSubId() throws android.os.RemoteException;

    int getDefaultSubIdAsUser(int i) throws android.os.RemoteException;

    int getDefaultVoiceSubId() throws android.os.RemoteException;

    int getDefaultVoiceSubIdAsUser(int i) throws android.os.RemoteException;

    int getEnabledSubscriptionId(int i) throws android.os.RemoteException;

    java.util.List<android.telephony.SubscriptionInfo> getOpportunisticSubscriptions(java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    int getPhoneId(int i) throws android.os.RemoteException;

    java.lang.String getPhoneNumber(int i, int i2, java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    java.lang.String getPhoneNumberFromFirstAvailableSource(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    int getPreferredDataSubscriptionId() throws android.os.RemoteException;

    int getSlotIndex(int i) throws android.os.RemoteException;

    int getSubId(int i) throws android.os.RemoteException;

    java.util.List<android.telephony.SubscriptionInfo> getSubscriptionInfoListAssociatedWithUser(android.os.UserHandle userHandle) throws android.os.RemoteException;

    java.lang.String getSubscriptionProperty(int i, java.lang.String str, java.lang.String str2, java.lang.String str3) throws android.os.RemoteException;

    android.os.UserHandle getSubscriptionUserHandle(int i) throws android.os.RemoteException;

    java.util.List<android.telephony.SubscriptionInfo> getSubscriptionsInGroup(android.os.ParcelUuid parcelUuid, java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    boolean isActiveSubId(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    boolean isSubscriptionAssociatedWithCallingUser(int i) throws android.os.RemoteException;

    boolean isSubscriptionAssociatedWithUser(int i, android.os.UserHandle userHandle) throws android.os.RemoteException;

    boolean isSubscriptionEnabled(int i) throws android.os.RemoteException;

    boolean removeSubInfo(java.lang.String str, int i) throws android.os.RemoteException;

    void removeSubscriptionsFromGroup(int[] iArr, android.os.ParcelUuid parcelUuid, java.lang.String str) throws android.os.RemoteException;

    void requestEmbeddedSubscriptionInfoListRefresh(int i) throws android.os.RemoteException;

    void restoreAllSimSpecificSettingsFromBackup(byte[] bArr) throws android.os.RemoteException;

    int setDataRoaming(int i, int i2) throws android.os.RemoteException;

    void setDefaultDataSubId(int i) throws android.os.RemoteException;

    void setDefaultSmsSubId(int i) throws android.os.RemoteException;

    void setDefaultVoiceSubId(int i) throws android.os.RemoteException;

    int setDeviceToDeviceStatusSharing(int i, int i2) throws android.os.RemoteException;

    int setDeviceToDeviceStatusSharingContacts(java.lang.String str, int i) throws android.os.RemoteException;

    int setDisplayNameUsingSrc(java.lang.String str, int i, int i2) throws android.os.RemoteException;

    int setDisplayNumber(java.lang.String str, int i) throws android.os.RemoteException;

    int setIconTint(int i, int i2) throws android.os.RemoteException;

    int setOpportunistic(boolean z, int i, java.lang.String str) throws android.os.RemoteException;

    void setPhoneNumber(int i, int i2, java.lang.String str, java.lang.String str2, java.lang.String str3) throws android.os.RemoteException;

    void setPreferredDataSubscriptionId(int i, boolean z, com.android.internal.telephony.ISetOpportunisticDataCallback iSetOpportunisticDataCallback) throws android.os.RemoteException;

    void setSubscriptionProperty(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    int setSubscriptionUserHandle(android.os.UserHandle userHandle, int i) throws android.os.RemoteException;

    void setTransferStatus(int i, int i2) throws android.os.RemoteException;

    void setUiccApplicationsEnabled(boolean z, int i) throws android.os.RemoteException;

    int setUsageSetting(int i, int i2, java.lang.String str) throws android.os.RemoteException;

    public static class Default implements com.android.internal.telephony.ISub {
        @Override // com.android.internal.telephony.ISub
        public java.util.List<android.telephony.SubscriptionInfo> getAllSubInfoList(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ISub
        public android.telephony.SubscriptionInfo getActiveSubscriptionInfo(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ISub
        public android.telephony.SubscriptionInfo getActiveSubscriptionInfoForIccId(java.lang.String str, java.lang.String str2, java.lang.String str3) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ISub
        public android.telephony.SubscriptionInfo getActiveSubscriptionInfoForSimSlotIndex(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ISub
        public java.util.List<android.telephony.SubscriptionInfo> getActiveSubscriptionInfoList(java.lang.String str, java.lang.String str2, boolean z) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ISub
        public int getActiveSubInfoCount(java.lang.String str, java.lang.String str2, boolean z) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ISub
        public int getActiveSubInfoCountMax() throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ISub
        public java.util.List<android.telephony.SubscriptionInfo> getAvailableSubscriptionInfoList(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ISub
        public java.util.List<android.telephony.SubscriptionInfo> getAccessibleSubscriptionInfoList(java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ISub
        public void requestEmbeddedSubscriptionInfoListRefresh(int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ISub
        public int addSubInfo(java.lang.String str, java.lang.String str2, int i, int i2) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ISub
        public boolean removeSubInfo(java.lang.String str, int i) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ISub
        public int setIconTint(int i, int i2) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ISub
        public int setDisplayNameUsingSrc(java.lang.String str, int i, int i2) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ISub
        public int setDisplayNumber(java.lang.String str, int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ISub
        public int setDataRoaming(int i, int i2) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ISub
        public int setOpportunistic(boolean z, int i, java.lang.String str) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ISub
        public android.os.ParcelUuid createSubscriptionGroup(int[] iArr, java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ISub
        public void setPreferredDataSubscriptionId(int i, boolean z, com.android.internal.telephony.ISetOpportunisticDataCallback iSetOpportunisticDataCallback) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ISub
        public int getPreferredDataSubscriptionId() throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ISub
        public java.util.List<android.telephony.SubscriptionInfo> getOpportunisticSubscriptions(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ISub
        public void removeSubscriptionsFromGroup(int[] iArr, android.os.ParcelUuid parcelUuid, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ISub
        public void addSubscriptionsIntoGroup(int[] iArr, android.os.ParcelUuid parcelUuid, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ISub
        public java.util.List<android.telephony.SubscriptionInfo> getSubscriptionsInGroup(android.os.ParcelUuid parcelUuid, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ISub
        public int getSlotIndex(int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ISub
        public int getSubId(int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ISub
        public int getDefaultSubId() throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ISub
        public int getDefaultSubIdAsUser(int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ISub
        public int getPhoneId(int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ISub
        public int getDefaultDataSubId() throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ISub
        public void setDefaultDataSubId(int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ISub
        public int getDefaultVoiceSubId() throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ISub
        public int getDefaultVoiceSubIdAsUser(int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ISub
        public void setDefaultVoiceSubId(int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ISub
        public int getDefaultSmsSubId() throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ISub
        public int getDefaultSmsSubIdAsUser(int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ISub
        public void setDefaultSmsSubId(int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ISub
        public int[] getActiveSubIdList(boolean z) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ISub
        public void setSubscriptionProperty(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ISub
        public java.lang.String getSubscriptionProperty(int i, java.lang.String str, java.lang.String str2, java.lang.String str3) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ISub
        public boolean isSubscriptionEnabled(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ISub
        public int getEnabledSubscriptionId(int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ISub
        public boolean isActiveSubId(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ISub
        public int getActiveDataSubscriptionId() throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ISub
        public boolean canDisablePhysicalSubscription() throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ISub
        public void setUiccApplicationsEnabled(boolean z, int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ISub
        public int setDeviceToDeviceStatusSharing(int i, int i2) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ISub
        public int setDeviceToDeviceStatusSharingContacts(java.lang.String str, int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ISub
        public java.lang.String getPhoneNumber(int i, int i2, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ISub
        public java.lang.String getPhoneNumberFromFirstAvailableSource(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ISub
        public void setPhoneNumber(int i, int i2, java.lang.String str, java.lang.String str2, java.lang.String str3) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ISub
        public int setUsageSetting(int i, int i2, java.lang.String str) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ISub
        public int setSubscriptionUserHandle(android.os.UserHandle userHandle, int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ISub
        public android.os.UserHandle getSubscriptionUserHandle(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ISub
        public boolean isSubscriptionAssociatedWithCallingUser(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ISub
        public boolean isSubscriptionAssociatedWithUser(int i, android.os.UserHandle userHandle) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ISub
        public java.util.List<android.telephony.SubscriptionInfo> getSubscriptionInfoListAssociatedWithUser(android.os.UserHandle userHandle) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ISub
        public void restoreAllSimSpecificSettingsFromBackup(byte[] bArr) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ISub
        public void setTransferStatus(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements com.android.internal.telephony.ISub {
        public static final java.lang.String DESCRIPTOR = "com.android.internal.telephony.ISub";
        static final int TRANSACTION_addSubInfo = 11;
        static final int TRANSACTION_addSubscriptionsIntoGroup = 23;
        static final int TRANSACTION_canDisablePhysicalSubscription = 45;
        static final int TRANSACTION_createSubscriptionGroup = 18;
        static final int TRANSACTION_getAccessibleSubscriptionInfoList = 9;
        static final int TRANSACTION_getActiveDataSubscriptionId = 44;
        static final int TRANSACTION_getActiveSubIdList = 38;
        static final int TRANSACTION_getActiveSubInfoCount = 6;
        static final int TRANSACTION_getActiveSubInfoCountMax = 7;
        static final int TRANSACTION_getActiveSubscriptionInfo = 2;
        static final int TRANSACTION_getActiveSubscriptionInfoForIccId = 3;
        static final int TRANSACTION_getActiveSubscriptionInfoForSimSlotIndex = 4;
        static final int TRANSACTION_getActiveSubscriptionInfoList = 5;
        static final int TRANSACTION_getAllSubInfoList = 1;
        static final int TRANSACTION_getAvailableSubscriptionInfoList = 8;
        static final int TRANSACTION_getDefaultDataSubId = 30;
        static final int TRANSACTION_getDefaultSmsSubId = 35;
        static final int TRANSACTION_getDefaultSmsSubIdAsUser = 36;
        static final int TRANSACTION_getDefaultSubId = 27;
        static final int TRANSACTION_getDefaultSubIdAsUser = 28;
        static final int TRANSACTION_getDefaultVoiceSubId = 32;
        static final int TRANSACTION_getDefaultVoiceSubIdAsUser = 33;
        static final int TRANSACTION_getEnabledSubscriptionId = 42;
        static final int TRANSACTION_getOpportunisticSubscriptions = 21;
        static final int TRANSACTION_getPhoneId = 29;
        static final int TRANSACTION_getPhoneNumber = 49;
        static final int TRANSACTION_getPhoneNumberFromFirstAvailableSource = 50;
        static final int TRANSACTION_getPreferredDataSubscriptionId = 20;
        static final int TRANSACTION_getSlotIndex = 25;
        static final int TRANSACTION_getSubId = 26;
        static final int TRANSACTION_getSubscriptionInfoListAssociatedWithUser = 57;
        static final int TRANSACTION_getSubscriptionProperty = 40;
        static final int TRANSACTION_getSubscriptionUserHandle = 54;
        static final int TRANSACTION_getSubscriptionsInGroup = 24;
        static final int TRANSACTION_isActiveSubId = 43;
        static final int TRANSACTION_isSubscriptionAssociatedWithCallingUser = 55;
        static final int TRANSACTION_isSubscriptionAssociatedWithUser = 56;
        static final int TRANSACTION_isSubscriptionEnabled = 41;
        static final int TRANSACTION_removeSubInfo = 12;
        static final int TRANSACTION_removeSubscriptionsFromGroup = 22;
        static final int TRANSACTION_requestEmbeddedSubscriptionInfoListRefresh = 10;
        static final int TRANSACTION_restoreAllSimSpecificSettingsFromBackup = 58;
        static final int TRANSACTION_setDataRoaming = 16;
        static final int TRANSACTION_setDefaultDataSubId = 31;
        static final int TRANSACTION_setDefaultSmsSubId = 37;
        static final int TRANSACTION_setDefaultVoiceSubId = 34;
        static final int TRANSACTION_setDeviceToDeviceStatusSharing = 47;
        static final int TRANSACTION_setDeviceToDeviceStatusSharingContacts = 48;
        static final int TRANSACTION_setDisplayNameUsingSrc = 14;
        static final int TRANSACTION_setDisplayNumber = 15;
        static final int TRANSACTION_setIconTint = 13;
        static final int TRANSACTION_setOpportunistic = 17;
        static final int TRANSACTION_setPhoneNumber = 51;
        static final int TRANSACTION_setPreferredDataSubscriptionId = 19;
        static final int TRANSACTION_setSubscriptionProperty = 39;
        static final int TRANSACTION_setSubscriptionUserHandle = 53;
        static final int TRANSACTION_setTransferStatus = 59;
        static final int TRANSACTION_setUiccApplicationsEnabled = 46;
        static final int TRANSACTION_setUsageSetting = 52;
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

        public static com.android.internal.telephony.ISub asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof com.android.internal.telephony.ISub)) {
                return (com.android.internal.telephony.ISub) queryLocalInterface;
            }
            return new com.android.internal.telephony.ISub.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getAllSubInfoList";
                case 2:
                    return "getActiveSubscriptionInfo";
                case 3:
                    return "getActiveSubscriptionInfoForIccId";
                case 4:
                    return "getActiveSubscriptionInfoForSimSlotIndex";
                case 5:
                    return "getActiveSubscriptionInfoList";
                case 6:
                    return "getActiveSubInfoCount";
                case 7:
                    return "getActiveSubInfoCountMax";
                case 8:
                    return "getAvailableSubscriptionInfoList";
                case 9:
                    return "getAccessibleSubscriptionInfoList";
                case 10:
                    return "requestEmbeddedSubscriptionInfoListRefresh";
                case 11:
                    return "addSubInfo";
                case 12:
                    return "removeSubInfo";
                case 13:
                    return "setIconTint";
                case 14:
                    return "setDisplayNameUsingSrc";
                case 15:
                    return "setDisplayNumber";
                case 16:
                    return "setDataRoaming";
                case 17:
                    return "setOpportunistic";
                case 18:
                    return "createSubscriptionGroup";
                case 19:
                    return "setPreferredDataSubscriptionId";
                case 20:
                    return "getPreferredDataSubscriptionId";
                case 21:
                    return "getOpportunisticSubscriptions";
                case 22:
                    return "removeSubscriptionsFromGroup";
                case 23:
                    return "addSubscriptionsIntoGroup";
                case 24:
                    return "getSubscriptionsInGroup";
                case 25:
                    return "getSlotIndex";
                case 26:
                    return "getSubId";
                case 27:
                    return "getDefaultSubId";
                case 28:
                    return "getDefaultSubIdAsUser";
                case 29:
                    return "getPhoneId";
                case 30:
                    return "getDefaultDataSubId";
                case 31:
                    return "setDefaultDataSubId";
                case 32:
                    return "getDefaultVoiceSubId";
                case 33:
                    return "getDefaultVoiceSubIdAsUser";
                case 34:
                    return "setDefaultVoiceSubId";
                case 35:
                    return "getDefaultSmsSubId";
                case 36:
                    return "getDefaultSmsSubIdAsUser";
                case 37:
                    return "setDefaultSmsSubId";
                case 38:
                    return "getActiveSubIdList";
                case 39:
                    return "setSubscriptionProperty";
                case 40:
                    return "getSubscriptionProperty";
                case 41:
                    return "isSubscriptionEnabled";
                case 42:
                    return "getEnabledSubscriptionId";
                case 43:
                    return "isActiveSubId";
                case 44:
                    return "getActiveDataSubscriptionId";
                case 45:
                    return "canDisablePhysicalSubscription";
                case 46:
                    return "setUiccApplicationsEnabled";
                case 47:
                    return "setDeviceToDeviceStatusSharing";
                case 48:
                    return "setDeviceToDeviceStatusSharingContacts";
                case 49:
                    return "getPhoneNumber";
                case 50:
                    return "getPhoneNumberFromFirstAvailableSource";
                case 51:
                    return "setPhoneNumber";
                case 52:
                    return "setUsageSetting";
                case 53:
                    return "setSubscriptionUserHandle";
                case 54:
                    return "getSubscriptionUserHandle";
                case 55:
                    return "isSubscriptionAssociatedWithCallingUser";
                case 56:
                    return "isSubscriptionAssociatedWithUser";
                case 57:
                    return "getSubscriptionInfoListAssociatedWithUser";
                case 58:
                    return "restoreAllSimSpecificSettingsFromBackup";
                case 59:
                    return "setTransferStatus";
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
                    java.lang.String readString = parcel.readString();
                    java.lang.String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    java.util.List<android.telephony.SubscriptionInfo> allSubInfoList = getAllSubInfoList(readString, readString2);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(allSubInfoList, 1);
                    return true;
                case 2:
                    int readInt = parcel.readInt();
                    java.lang.String readString3 = parcel.readString();
                    java.lang.String readString4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.telephony.SubscriptionInfo activeSubscriptionInfo = getActiveSubscriptionInfo(readInt, readString3, readString4);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(activeSubscriptionInfo, 1);
                    return true;
                case 3:
                    java.lang.String readString5 = parcel.readString();
                    java.lang.String readString6 = parcel.readString();
                    java.lang.String readString7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.telephony.SubscriptionInfo activeSubscriptionInfoForIccId = getActiveSubscriptionInfoForIccId(readString5, readString6, readString7);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(activeSubscriptionInfoForIccId, 1);
                    return true;
                case 4:
                    int readInt2 = parcel.readInt();
                    java.lang.String readString8 = parcel.readString();
                    java.lang.String readString9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.telephony.SubscriptionInfo activeSubscriptionInfoForSimSlotIndex = getActiveSubscriptionInfoForSimSlotIndex(readInt2, readString8, readString9);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(activeSubscriptionInfoForSimSlotIndex, 1);
                    return true;
                case 5:
                    java.lang.String readString10 = parcel.readString();
                    java.lang.String readString11 = parcel.readString();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    java.util.List<android.telephony.SubscriptionInfo> activeSubscriptionInfoList = getActiveSubscriptionInfoList(readString10, readString11, readBoolean);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(activeSubscriptionInfoList, 1);
                    return true;
                case 6:
                    java.lang.String readString12 = parcel.readString();
                    java.lang.String readString13 = parcel.readString();
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int activeSubInfoCount = getActiveSubInfoCount(readString12, readString13, readBoolean2);
                    parcel2.writeNoException();
                    parcel2.writeInt(activeSubInfoCount);
                    return true;
                case 7:
                    int activeSubInfoCountMax = getActiveSubInfoCountMax();
                    parcel2.writeNoException();
                    parcel2.writeInt(activeSubInfoCountMax);
                    return true;
                case 8:
                    java.lang.String readString14 = parcel.readString();
                    java.lang.String readString15 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    java.util.List<android.telephony.SubscriptionInfo> availableSubscriptionInfoList = getAvailableSubscriptionInfoList(readString14, readString15);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(availableSubscriptionInfoList, 1);
                    return true;
                case 9:
                    java.lang.String readString16 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    java.util.List<android.telephony.SubscriptionInfo> accessibleSubscriptionInfoList = getAccessibleSubscriptionInfoList(readString16);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(accessibleSubscriptionInfoList, 1);
                    return true;
                case 10:
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    requestEmbeddedSubscriptionInfoListRefresh(readInt3);
                    return true;
                case 11:
                    java.lang.String readString17 = parcel.readString();
                    java.lang.String readString18 = parcel.readString();
                    int readInt4 = parcel.readInt();
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int addSubInfo = addSubInfo(readString17, readString18, readInt4, readInt5);
                    parcel2.writeNoException();
                    parcel2.writeInt(addSubInfo);
                    return true;
                case 12:
                    java.lang.String readString19 = parcel.readString();
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean removeSubInfo = removeSubInfo(readString19, readInt6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(removeSubInfo);
                    return true;
                case 13:
                    int readInt7 = parcel.readInt();
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int iconTint = setIconTint(readInt7, readInt8);
                    parcel2.writeNoException();
                    parcel2.writeInt(iconTint);
                    return true;
                case 14:
                    java.lang.String readString20 = parcel.readString();
                    int readInt9 = parcel.readInt();
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int displayNameUsingSrc = setDisplayNameUsingSrc(readString20, readInt9, readInt10);
                    parcel2.writeNoException();
                    parcel2.writeInt(displayNameUsingSrc);
                    return true;
                case 15:
                    java.lang.String readString21 = parcel.readString();
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int displayNumber = setDisplayNumber(readString21, readInt11);
                    parcel2.writeNoException();
                    parcel2.writeInt(displayNumber);
                    return true;
                case 16:
                    int readInt12 = parcel.readInt();
                    int readInt13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int dataRoaming = setDataRoaming(readInt12, readInt13);
                    parcel2.writeNoException();
                    parcel2.writeInt(dataRoaming);
                    return true;
                case 17:
                    boolean readBoolean3 = parcel.readBoolean();
                    int readInt14 = parcel.readInt();
                    java.lang.String readString22 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int opportunistic = setOpportunistic(readBoolean3, readInt14, readString22);
                    parcel2.writeNoException();
                    parcel2.writeInt(opportunistic);
                    return true;
                case 18:
                    int[] createIntArray = parcel.createIntArray();
                    java.lang.String readString23 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.os.ParcelUuid createSubscriptionGroup = createSubscriptionGroup(createIntArray, readString23);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(createSubscriptionGroup, 1);
                    return true;
                case 19:
                    int readInt15 = parcel.readInt();
                    boolean readBoolean4 = parcel.readBoolean();
                    com.android.internal.telephony.ISetOpportunisticDataCallback asInterface = com.android.internal.telephony.ISetOpportunisticDataCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setPreferredDataSubscriptionId(readInt15, readBoolean4, asInterface);
                    parcel2.writeNoException();
                    return true;
                case 20:
                    int preferredDataSubscriptionId = getPreferredDataSubscriptionId();
                    parcel2.writeNoException();
                    parcel2.writeInt(preferredDataSubscriptionId);
                    return true;
                case 21:
                    java.lang.String readString24 = parcel.readString();
                    java.lang.String readString25 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    java.util.List<android.telephony.SubscriptionInfo> opportunisticSubscriptions = getOpportunisticSubscriptions(readString24, readString25);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(opportunisticSubscriptions, 1);
                    return true;
                case 22:
                    int[] createIntArray2 = parcel.createIntArray();
                    android.os.ParcelUuid parcelUuid = (android.os.ParcelUuid) parcel.readTypedObject(android.os.ParcelUuid.CREATOR);
                    java.lang.String readString26 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    removeSubscriptionsFromGroup(createIntArray2, parcelUuid, readString26);
                    parcel2.writeNoException();
                    return true;
                case 23:
                    int[] createIntArray3 = parcel.createIntArray();
                    android.os.ParcelUuid parcelUuid2 = (android.os.ParcelUuid) parcel.readTypedObject(android.os.ParcelUuid.CREATOR);
                    java.lang.String readString27 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    addSubscriptionsIntoGroup(createIntArray3, parcelUuid2, readString27);
                    parcel2.writeNoException();
                    return true;
                case 24:
                    android.os.ParcelUuid parcelUuid3 = (android.os.ParcelUuid) parcel.readTypedObject(android.os.ParcelUuid.CREATOR);
                    java.lang.String readString28 = parcel.readString();
                    java.lang.String readString29 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    java.util.List<android.telephony.SubscriptionInfo> subscriptionsInGroup = getSubscriptionsInGroup(parcelUuid3, readString28, readString29);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(subscriptionsInGroup, 1);
                    return true;
                case 25:
                    int readInt16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int slotIndex = getSlotIndex(readInt16);
                    parcel2.writeNoException();
                    parcel2.writeInt(slotIndex);
                    return true;
                case 26:
                    int readInt17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int subId = getSubId(readInt17);
                    parcel2.writeNoException();
                    parcel2.writeInt(subId);
                    return true;
                case 27:
                    int defaultSubId = getDefaultSubId();
                    parcel2.writeNoException();
                    parcel2.writeInt(defaultSubId);
                    return true;
                case 28:
                    int readInt18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int defaultSubIdAsUser = getDefaultSubIdAsUser(readInt18);
                    parcel2.writeNoException();
                    parcel2.writeInt(defaultSubIdAsUser);
                    return true;
                case 29:
                    int readInt19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int phoneId = getPhoneId(readInt19);
                    parcel2.writeNoException();
                    parcel2.writeInt(phoneId);
                    return true;
                case 30:
                    int defaultDataSubId = getDefaultDataSubId();
                    parcel2.writeNoException();
                    parcel2.writeInt(defaultDataSubId);
                    return true;
                case 31:
                    int readInt20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setDefaultDataSubId(readInt20);
                    parcel2.writeNoException();
                    return true;
                case 32:
                    int defaultVoiceSubId = getDefaultVoiceSubId();
                    parcel2.writeNoException();
                    parcel2.writeInt(defaultVoiceSubId);
                    return true;
                case 33:
                    int readInt21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int defaultVoiceSubIdAsUser = getDefaultVoiceSubIdAsUser(readInt21);
                    parcel2.writeNoException();
                    parcel2.writeInt(defaultVoiceSubIdAsUser);
                    return true;
                case 34:
                    int readInt22 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setDefaultVoiceSubId(readInt22);
                    parcel2.writeNoException();
                    return true;
                case 35:
                    int defaultSmsSubId = getDefaultSmsSubId();
                    parcel2.writeNoException();
                    parcel2.writeInt(defaultSmsSubId);
                    return true;
                case 36:
                    int readInt23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int defaultSmsSubIdAsUser = getDefaultSmsSubIdAsUser(readInt23);
                    parcel2.writeNoException();
                    parcel2.writeInt(defaultSmsSubIdAsUser);
                    return true;
                case 37:
                    int readInt24 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setDefaultSmsSubId(readInt24);
                    parcel2.writeNoException();
                    return true;
                case 38:
                    boolean readBoolean5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int[] activeSubIdList = getActiveSubIdList(readBoolean5);
                    parcel2.writeNoException();
                    parcel2.writeIntArray(activeSubIdList);
                    return true;
                case 39:
                    int readInt25 = parcel.readInt();
                    java.lang.String readString30 = parcel.readString();
                    java.lang.String readString31 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setSubscriptionProperty(readInt25, readString30, readString31);
                    parcel2.writeNoException();
                    return true;
                case 40:
                    int readInt26 = parcel.readInt();
                    java.lang.String readString32 = parcel.readString();
                    java.lang.String readString33 = parcel.readString();
                    java.lang.String readString34 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    java.lang.String subscriptionProperty = getSubscriptionProperty(readInt26, readString32, readString33, readString34);
                    parcel2.writeNoException();
                    parcel2.writeString(subscriptionProperty);
                    return true;
                case 41:
                    int readInt27 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isSubscriptionEnabled = isSubscriptionEnabled(readInt27);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSubscriptionEnabled);
                    return true;
                case 42:
                    int readInt28 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int enabledSubscriptionId = getEnabledSubscriptionId(readInt28);
                    parcel2.writeNoException();
                    parcel2.writeInt(enabledSubscriptionId);
                    return true;
                case 43:
                    int readInt29 = parcel.readInt();
                    java.lang.String readString35 = parcel.readString();
                    java.lang.String readString36 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isActiveSubId = isActiveSubId(readInt29, readString35, readString36);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isActiveSubId);
                    return true;
                case 44:
                    int activeDataSubscriptionId = getActiveDataSubscriptionId();
                    parcel2.writeNoException();
                    parcel2.writeInt(activeDataSubscriptionId);
                    return true;
                case 45:
                    boolean canDisablePhysicalSubscription = canDisablePhysicalSubscription();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(canDisablePhysicalSubscription);
                    return true;
                case 46:
                    boolean readBoolean6 = parcel.readBoolean();
                    int readInt30 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setUiccApplicationsEnabled(readBoolean6, readInt30);
                    parcel2.writeNoException();
                    return true;
                case 47:
                    int readInt31 = parcel.readInt();
                    int readInt32 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int deviceToDeviceStatusSharing = setDeviceToDeviceStatusSharing(readInt31, readInt32);
                    parcel2.writeNoException();
                    parcel2.writeInt(deviceToDeviceStatusSharing);
                    return true;
                case 48:
                    java.lang.String readString37 = parcel.readString();
                    int readInt33 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int deviceToDeviceStatusSharingContacts = setDeviceToDeviceStatusSharingContacts(readString37, readInt33);
                    parcel2.writeNoException();
                    parcel2.writeInt(deviceToDeviceStatusSharingContacts);
                    return true;
                case 49:
                    int readInt34 = parcel.readInt();
                    int readInt35 = parcel.readInt();
                    java.lang.String readString38 = parcel.readString();
                    java.lang.String readString39 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    java.lang.String phoneNumber = getPhoneNumber(readInt34, readInt35, readString38, readString39);
                    parcel2.writeNoException();
                    parcel2.writeString(phoneNumber);
                    return true;
                case 50:
                    int readInt36 = parcel.readInt();
                    java.lang.String readString40 = parcel.readString();
                    java.lang.String readString41 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    java.lang.String phoneNumberFromFirstAvailableSource = getPhoneNumberFromFirstAvailableSource(readInt36, readString40, readString41);
                    parcel2.writeNoException();
                    parcel2.writeString(phoneNumberFromFirstAvailableSource);
                    return true;
                case 51:
                    int readInt37 = parcel.readInt();
                    int readInt38 = parcel.readInt();
                    java.lang.String readString42 = parcel.readString();
                    java.lang.String readString43 = parcel.readString();
                    java.lang.String readString44 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setPhoneNumber(readInt37, readInt38, readString42, readString43, readString44);
                    parcel2.writeNoException();
                    return true;
                case 52:
                    int readInt39 = parcel.readInt();
                    int readInt40 = parcel.readInt();
                    java.lang.String readString45 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int usageSetting = setUsageSetting(readInt39, readInt40, readString45);
                    parcel2.writeNoException();
                    parcel2.writeInt(usageSetting);
                    return true;
                case 53:
                    android.os.UserHandle userHandle = (android.os.UserHandle) parcel.readTypedObject(android.os.UserHandle.CREATOR);
                    int readInt41 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int subscriptionUserHandle = setSubscriptionUserHandle(userHandle, readInt41);
                    parcel2.writeNoException();
                    parcel2.writeInt(subscriptionUserHandle);
                    return true;
                case 54:
                    int readInt42 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.os.UserHandle subscriptionUserHandle2 = getSubscriptionUserHandle(readInt42);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(subscriptionUserHandle2, 1);
                    return true;
                case 55:
                    int readInt43 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isSubscriptionAssociatedWithCallingUser = isSubscriptionAssociatedWithCallingUser(readInt43);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSubscriptionAssociatedWithCallingUser);
                    return true;
                case 56:
                    int readInt44 = parcel.readInt();
                    android.os.UserHandle userHandle2 = (android.os.UserHandle) parcel.readTypedObject(android.os.UserHandle.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isSubscriptionAssociatedWithUser = isSubscriptionAssociatedWithUser(readInt44, userHandle2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSubscriptionAssociatedWithUser);
                    return true;
                case 57:
                    android.os.UserHandle userHandle3 = (android.os.UserHandle) parcel.readTypedObject(android.os.UserHandle.CREATOR);
                    parcel.enforceNoDataAvail();
                    java.util.List<android.telephony.SubscriptionInfo> subscriptionInfoListAssociatedWithUser = getSubscriptionInfoListAssociatedWithUser(userHandle3);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(subscriptionInfoListAssociatedWithUser, 1);
                    return true;
                case 58:
                    byte[] createByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    restoreAllSimSpecificSettingsFromBackup(createByteArray);
                    parcel2.writeNoException();
                    return true;
                case 59:
                    int readInt45 = parcel.readInt();
                    int readInt46 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setTransferStatus(readInt45, readInt46);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements com.android.internal.telephony.ISub {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return com.android.internal.telephony.ISub.Stub.DESCRIPTOR;
            }

            @Override // com.android.internal.telephony.ISub
            public java.util.List<android.telephony.SubscriptionInfo> getAllSubInfoList(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ISub.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.telephony.SubscriptionInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public android.telephony.SubscriptionInfo getActiveSubscriptionInfo(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ISub.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.telephony.SubscriptionInfo) obtain2.readTypedObject(android.telephony.SubscriptionInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public android.telephony.SubscriptionInfo getActiveSubscriptionInfoForIccId(java.lang.String str, java.lang.String str2, java.lang.String str3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ISub.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.telephony.SubscriptionInfo) obtain2.readTypedObject(android.telephony.SubscriptionInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public android.telephony.SubscriptionInfo getActiveSubscriptionInfoForSimSlotIndex(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ISub.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.telephony.SubscriptionInfo) obtain2.readTypedObject(android.telephony.SubscriptionInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public java.util.List<android.telephony.SubscriptionInfo> getActiveSubscriptionInfoList(java.lang.String str, java.lang.String str2, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ISub.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.telephony.SubscriptionInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public int getActiveSubInfoCount(java.lang.String str, java.lang.String str2, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ISub.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public int getActiveSubInfoCountMax() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ISub.Stub.DESCRIPTOR);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public java.util.List<android.telephony.SubscriptionInfo> getAvailableSubscriptionInfoList(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ISub.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.telephony.SubscriptionInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public java.util.List<android.telephony.SubscriptionInfo> getAccessibleSubscriptionInfoList(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ISub.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.telephony.SubscriptionInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public void requestEmbeddedSubscriptionInfoListRefresh(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ISub.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(10, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public int addSubInfo(java.lang.String str, java.lang.String str2, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ISub.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public boolean removeSubInfo(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ISub.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public int setIconTint(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ISub.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public int setDisplayNameUsingSrc(java.lang.String str, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ISub.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public int setDisplayNumber(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ISub.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public int setDataRoaming(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ISub.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public int setOpportunistic(boolean z, int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ISub.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public android.os.ParcelUuid createSubscriptionGroup(int[] iArr, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ISub.Stub.DESCRIPTOR);
                    obtain.writeIntArray(iArr);
                    obtain.writeString(str);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.os.ParcelUuid) obtain2.readTypedObject(android.os.ParcelUuid.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public void setPreferredDataSubscriptionId(int i, boolean z, com.android.internal.telephony.ISetOpportunisticDataCallback iSetOpportunisticDataCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ISub.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    obtain.writeStrongInterface(iSetOpportunisticDataCallback);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public int getPreferredDataSubscriptionId() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ISub.Stub.DESCRIPTOR);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public java.util.List<android.telephony.SubscriptionInfo> getOpportunisticSubscriptions(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ISub.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.telephony.SubscriptionInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public void removeSubscriptionsFromGroup(int[] iArr, android.os.ParcelUuid parcelUuid, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ISub.Stub.DESCRIPTOR);
                    obtain.writeIntArray(iArr);
                    obtain.writeTypedObject(parcelUuid, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public void addSubscriptionsIntoGroup(int[] iArr, android.os.ParcelUuid parcelUuid, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ISub.Stub.DESCRIPTOR);
                    obtain.writeIntArray(iArr);
                    obtain.writeTypedObject(parcelUuid, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public java.util.List<android.telephony.SubscriptionInfo> getSubscriptionsInGroup(android.os.ParcelUuid parcelUuid, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ISub.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(parcelUuid, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.telephony.SubscriptionInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public int getSlotIndex(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ISub.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public int getSubId(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ISub.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public int getDefaultSubId() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ISub.Stub.DESCRIPTOR);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public int getDefaultSubIdAsUser(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ISub.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public int getPhoneId(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ISub.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public int getDefaultDataSubId() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ISub.Stub.DESCRIPTOR);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public void setDefaultDataSubId(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ISub.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public int getDefaultVoiceSubId() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ISub.Stub.DESCRIPTOR);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public int getDefaultVoiceSubIdAsUser(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ISub.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public void setDefaultVoiceSubId(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ISub.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public int getDefaultSmsSubId() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ISub.Stub.DESCRIPTOR);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public int getDefaultSmsSubIdAsUser(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ISub.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public void setDefaultSmsSubId(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ISub.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(37, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public int[] getActiveSubIdList(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ISub.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(38, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public void setSubscriptionProperty(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ISub.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(39, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public java.lang.String getSubscriptionProperty(int i, java.lang.String str, java.lang.String str2, java.lang.String str3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ISub.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    this.mRemote.transact(40, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public boolean isSubscriptionEnabled(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ISub.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(41, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public int getEnabledSubscriptionId(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ISub.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(42, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public boolean isActiveSubId(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ISub.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(43, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public int getActiveDataSubscriptionId() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ISub.Stub.DESCRIPTOR);
                    this.mRemote.transact(44, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public boolean canDisablePhysicalSubscription() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ISub.Stub.DESCRIPTOR);
                    this.mRemote.transact(45, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public void setUiccApplicationsEnabled(boolean z, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ISub.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(46, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public int setDeviceToDeviceStatusSharing(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ISub.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(47, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public int setDeviceToDeviceStatusSharingContacts(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ISub.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(48, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public java.lang.String getPhoneNumber(int i, int i2, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ISub.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(49, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public java.lang.String getPhoneNumberFromFirstAvailableSource(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ISub.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(50, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public void setPhoneNumber(int i, int i2, java.lang.String str, java.lang.String str2, java.lang.String str3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ISub.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    this.mRemote.transact(51, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public int setUsageSetting(int i, int i2, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ISub.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    this.mRemote.transact(52, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public int setSubscriptionUserHandle(android.os.UserHandle userHandle, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ISub.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(userHandle, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(53, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public android.os.UserHandle getSubscriptionUserHandle(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ISub.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(54, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.os.UserHandle) obtain2.readTypedObject(android.os.UserHandle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public boolean isSubscriptionAssociatedWithCallingUser(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ISub.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(55, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public boolean isSubscriptionAssociatedWithUser(int i, android.os.UserHandle userHandle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ISub.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(56, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public java.util.List<android.telephony.SubscriptionInfo> getSubscriptionInfoListAssociatedWithUser(android.os.UserHandle userHandle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ISub.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(57, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.telephony.SubscriptionInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public void restoreAllSimSpecificSettingsFromBackup(byte[] bArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ISub.Stub.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(58, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISub
            public void setTransferStatus(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ISub.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(59, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        protected void setTransferStatus_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.WRITE_EMBEDDED_SUBSCRIPTIONS, getCallingPid(), getCallingUid());
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 58;
        }
    }
}
