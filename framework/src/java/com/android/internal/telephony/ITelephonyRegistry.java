package com.android.internal.telephony;

/* loaded from: classes5.dex */
public interface ITelephonyRegistry extends android.os.IInterface {
    void addCarrierConfigChangeListener(com.android.internal.telephony.ICarrierConfigChangeListener iCarrierConfigChangeListener, java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    void addCarrierPrivilegesCallback(int i, com.android.internal.telephony.ICarrierPrivilegesCallback iCarrierPrivilegesCallback, java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    void addOnOpportunisticSubscriptionsChangedListener(java.lang.String str, java.lang.String str2, com.android.internal.telephony.IOnSubscriptionsChangedListener iOnSubscriptionsChangedListener) throws android.os.RemoteException;

    void addOnSubscriptionsChangedListener(java.lang.String str, java.lang.String str2, com.android.internal.telephony.IOnSubscriptionsChangedListener iOnSubscriptionsChangedListener) throws android.os.RemoteException;

    void listenWithEventList(boolean z, boolean z2, int i, java.lang.String str, java.lang.String str2, com.android.internal.telephony.IPhoneStateListener iPhoneStateListener, int[] iArr, boolean z3) throws android.os.RemoteException;

    void notifyActiveDataSubIdChanged(int i) throws android.os.RemoteException;

    void notifyAllowedNetworkTypesChanged(int i, int i2, int i3, long j) throws android.os.RemoteException;

    void notifyBarringInfoChanged(int i, int i2, android.telephony.BarringInfo barringInfo) throws android.os.RemoteException;

    void notifyCallForwardingChanged(boolean z) throws android.os.RemoteException;

    void notifyCallForwardingChangedForSubscriber(int i, boolean z) throws android.os.RemoteException;

    void notifyCallQualityChanged(android.telephony.CallQuality callQuality, int i, int i2, int i3) throws android.os.RemoteException;

    void notifyCallState(int i, int i2, int i3, java.lang.String str) throws android.os.RemoteException;

    void notifyCallStateForAllSubs(int i, java.lang.String str) throws android.os.RemoteException;

    void notifyCallbackModeStarted(int i, int i2, int i3) throws android.os.RemoteException;

    void notifyCallbackModeStopped(int i, int i2, int i3, int i4) throws android.os.RemoteException;

    void notifyCarrierConfigChanged(int i, int i2, int i3, int i4) throws android.os.RemoteException;

    void notifyCarrierNetworkChange(boolean z) throws android.os.RemoteException;

    void notifyCarrierNetworkChangeWithSubId(int i, boolean z) throws android.os.RemoteException;

    void notifyCarrierPrivilegesChanged(int i, java.util.List<java.lang.String> list, int[] iArr) throws android.os.RemoteException;

    void notifyCarrierServiceChanged(int i, java.lang.String str, int i2) throws android.os.RemoteException;

    void notifyCellInfo(java.util.List<android.telephony.CellInfo> list) throws android.os.RemoteException;

    void notifyCellInfoForSubscriber(int i, java.util.List<android.telephony.CellInfo> list) throws android.os.RemoteException;

    void notifyCellLocationForSubscriber(int i, android.telephony.CellIdentity cellIdentity) throws android.os.RemoteException;

    void notifyDataActivityForSubscriber(int i, int i2) throws android.os.RemoteException;

    void notifyDataActivityForSubscriberWithSlot(int i, int i2, int i3) throws android.os.RemoteException;

    void notifyDataConnectionForSubscriber(int i, int i2, android.telephony.PreciseDataConnectionState preciseDataConnectionState) throws android.os.RemoteException;

    void notifyDataEnabled(int i, int i2, boolean z, int i3) throws android.os.RemoteException;

    void notifyDisconnectCause(int i, int i2, int i3, int i4) throws android.os.RemoteException;

    void notifyDisplayInfoChanged(int i, int i2, android.telephony.TelephonyDisplayInfo telephonyDisplayInfo) throws android.os.RemoteException;

    void notifyEmergencyNumberList(int i, int i2) throws android.os.RemoteException;

    void notifyImsDisconnectCause(int i, android.telephony.ims.ImsReasonInfo imsReasonInfo) throws android.os.RemoteException;

    void notifyLinkCapacityEstimateChanged(int i, int i2, java.util.List<android.telephony.LinkCapacityEstimate> list) throws android.os.RemoteException;

    void notifyMediaQualityStatusChanged(int i, int i2, android.telephony.ims.MediaQualityStatus mediaQualityStatus) throws android.os.RemoteException;

    void notifyMessageWaitingChangedForPhoneId(int i, int i2, boolean z) throws android.os.RemoteException;

    void notifyOemHookRawEventForSubscriber(int i, int i2, byte[] bArr) throws android.os.RemoteException;

    void notifyOpportunisticSubscriptionInfoChanged() throws android.os.RemoteException;

    void notifyOutgoingEmergencyCall(int i, int i2, android.telephony.emergency.EmergencyNumber emergencyNumber) throws android.os.RemoteException;

    void notifyOutgoingEmergencySms(int i, int i2, android.telephony.emergency.EmergencyNumber emergencyNumber) throws android.os.RemoteException;

    void notifyPhoneCapabilityChanged(android.telephony.PhoneCapability phoneCapability) throws android.os.RemoteException;

    void notifyPhysicalChannelConfigForSubscriber(int i, int i2, java.util.List<android.telephony.PhysicalChannelConfig> list) throws android.os.RemoteException;

    void notifyPreciseCallState(int i, int i2, int[] iArr, java.lang.String[] strArr, int[] iArr2, int[] iArr3) throws android.os.RemoteException;

    void notifyRadioPowerStateChanged(int i, int i2, int i3) throws android.os.RemoteException;

    void notifyRegistrationFailed(int i, int i2, android.telephony.CellIdentity cellIdentity, java.lang.String str, int i3, int i4, int i5) throws android.os.RemoteException;

    void notifyServiceStateForPhoneId(int i, int i2, android.telephony.ServiceState serviceState) throws android.os.RemoteException;

    void notifySignalStrengthForPhoneId(int i, int i2, android.telephony.SignalStrength signalStrength) throws android.os.RemoteException;

    void notifySimActivationStateChangedForPhoneId(int i, int i2, int i3, int i4) throws android.os.RemoteException;

    void notifySimultaneousCellularCallingSubscriptionsChanged(int[] iArr) throws android.os.RemoteException;

    void notifySrvccStateChanged(int i, int i2) throws android.os.RemoteException;

    void notifySubscriptionInfoChanged() throws android.os.RemoteException;

    void notifyUserMobileDataStateChangedForPhoneId(int i, int i2, boolean z) throws android.os.RemoteException;

    void removeCarrierConfigChangeListener(com.android.internal.telephony.ICarrierConfigChangeListener iCarrierConfigChangeListener, java.lang.String str) throws android.os.RemoteException;

    void removeCarrierPrivilegesCallback(com.android.internal.telephony.ICarrierPrivilegesCallback iCarrierPrivilegesCallback, java.lang.String str) throws android.os.RemoteException;

    void removeOnSubscriptionsChangedListener(java.lang.String str, com.android.internal.telephony.IOnSubscriptionsChangedListener iOnSubscriptionsChangedListener) throws android.os.RemoteException;

    public static class Default implements com.android.internal.telephony.ITelephonyRegistry {
        @Override // com.android.internal.telephony.ITelephonyRegistry
        public void addOnSubscriptionsChangedListener(java.lang.String str, java.lang.String str2, com.android.internal.telephony.IOnSubscriptionsChangedListener iOnSubscriptionsChangedListener) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephonyRegistry
        public void addOnOpportunisticSubscriptionsChangedListener(java.lang.String str, java.lang.String str2, com.android.internal.telephony.IOnSubscriptionsChangedListener iOnSubscriptionsChangedListener) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephonyRegistry
        public void removeOnSubscriptionsChangedListener(java.lang.String str, com.android.internal.telephony.IOnSubscriptionsChangedListener iOnSubscriptionsChangedListener) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephonyRegistry
        public void listenWithEventList(boolean z, boolean z2, int i, java.lang.String str, java.lang.String str2, com.android.internal.telephony.IPhoneStateListener iPhoneStateListener, int[] iArr, boolean z3) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephonyRegistry
        public void notifyCallStateForAllSubs(int i, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephonyRegistry
        public void notifyCallState(int i, int i2, int i3, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephonyRegistry
        public void notifyServiceStateForPhoneId(int i, int i2, android.telephony.ServiceState serviceState) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephonyRegistry
        public void notifySignalStrengthForPhoneId(int i, int i2, android.telephony.SignalStrength signalStrength) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephonyRegistry
        public void notifyMessageWaitingChangedForPhoneId(int i, int i2, boolean z) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephonyRegistry
        public void notifyCallForwardingChanged(boolean z) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephonyRegistry
        public void notifyCallForwardingChangedForSubscriber(int i, boolean z) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephonyRegistry
        public void notifyDataActivityForSubscriber(int i, int i2) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephonyRegistry
        public void notifyDataActivityForSubscriberWithSlot(int i, int i2, int i3) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephonyRegistry
        public void notifyDataConnectionForSubscriber(int i, int i2, android.telephony.PreciseDataConnectionState preciseDataConnectionState) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephonyRegistry
        public void notifyCellLocationForSubscriber(int i, android.telephony.CellIdentity cellIdentity) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephonyRegistry
        public void notifyCellInfo(java.util.List<android.telephony.CellInfo> list) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephonyRegistry
        public void notifyPreciseCallState(int i, int i2, int[] iArr, java.lang.String[] strArr, int[] iArr2, int[] iArr3) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephonyRegistry
        public void notifyDisconnectCause(int i, int i2, int i3, int i4) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephonyRegistry
        public void notifyCellInfoForSubscriber(int i, java.util.List<android.telephony.CellInfo> list) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephonyRegistry
        public void notifySrvccStateChanged(int i, int i2) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephonyRegistry
        public void notifySimActivationStateChangedForPhoneId(int i, int i2, int i3, int i4) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephonyRegistry
        public void notifyOemHookRawEventForSubscriber(int i, int i2, byte[] bArr) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephonyRegistry
        public void notifySubscriptionInfoChanged() throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephonyRegistry
        public void notifyOpportunisticSubscriptionInfoChanged() throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephonyRegistry
        public void notifyCarrierNetworkChange(boolean z) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephonyRegistry
        public void notifyCarrierNetworkChangeWithSubId(int i, boolean z) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephonyRegistry
        public void notifyUserMobileDataStateChangedForPhoneId(int i, int i2, boolean z) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephonyRegistry
        public void notifyDisplayInfoChanged(int i, int i2, android.telephony.TelephonyDisplayInfo telephonyDisplayInfo) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephonyRegistry
        public void notifyPhoneCapabilityChanged(android.telephony.PhoneCapability phoneCapability) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephonyRegistry
        public void notifyActiveDataSubIdChanged(int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephonyRegistry
        public void notifyRadioPowerStateChanged(int i, int i2, int i3) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephonyRegistry
        public void notifyEmergencyNumberList(int i, int i2) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephonyRegistry
        public void notifyOutgoingEmergencyCall(int i, int i2, android.telephony.emergency.EmergencyNumber emergencyNumber) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephonyRegistry
        public void notifyOutgoingEmergencySms(int i, int i2, android.telephony.emergency.EmergencyNumber emergencyNumber) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephonyRegistry
        public void notifyCallQualityChanged(android.telephony.CallQuality callQuality, int i, int i2, int i3) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephonyRegistry
        public void notifyMediaQualityStatusChanged(int i, int i2, android.telephony.ims.MediaQualityStatus mediaQualityStatus) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephonyRegistry
        public void notifyImsDisconnectCause(int i, android.telephony.ims.ImsReasonInfo imsReasonInfo) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephonyRegistry
        public void notifyRegistrationFailed(int i, int i2, android.telephony.CellIdentity cellIdentity, java.lang.String str, int i3, int i4, int i5) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephonyRegistry
        public void notifyBarringInfoChanged(int i, int i2, android.telephony.BarringInfo barringInfo) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephonyRegistry
        public void notifyPhysicalChannelConfigForSubscriber(int i, int i2, java.util.List<android.telephony.PhysicalChannelConfig> list) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephonyRegistry
        public void notifyDataEnabled(int i, int i2, boolean z, int i3) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephonyRegistry
        public void notifyAllowedNetworkTypesChanged(int i, int i2, int i3, long j) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephonyRegistry
        public void notifyLinkCapacityEstimateChanged(int i, int i2, java.util.List<android.telephony.LinkCapacityEstimate> list) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephonyRegistry
        public void notifySimultaneousCellularCallingSubscriptionsChanged(int[] iArr) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephonyRegistry
        public void addCarrierPrivilegesCallback(int i, com.android.internal.telephony.ICarrierPrivilegesCallback iCarrierPrivilegesCallback, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephonyRegistry
        public void removeCarrierPrivilegesCallback(com.android.internal.telephony.ICarrierPrivilegesCallback iCarrierPrivilegesCallback, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephonyRegistry
        public void notifyCarrierPrivilegesChanged(int i, java.util.List<java.lang.String> list, int[] iArr) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephonyRegistry
        public void notifyCarrierServiceChanged(int i, java.lang.String str, int i2) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephonyRegistry
        public void addCarrierConfigChangeListener(com.android.internal.telephony.ICarrierConfigChangeListener iCarrierConfigChangeListener, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephonyRegistry
        public void removeCarrierConfigChangeListener(com.android.internal.telephony.ICarrierConfigChangeListener iCarrierConfigChangeListener, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephonyRegistry
        public void notifyCarrierConfigChanged(int i, int i2, int i3, int i4) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephonyRegistry
        public void notifyCallbackModeStarted(int i, int i2, int i3) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.ITelephonyRegistry
        public void notifyCallbackModeStopped(int i, int i2, int i3, int i4) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements com.android.internal.telephony.ITelephonyRegistry {
        public static final java.lang.String DESCRIPTOR = "com.android.internal.telephony.ITelephonyRegistry";
        static final int TRANSACTION_addCarrierConfigChangeListener = 49;
        static final int TRANSACTION_addCarrierPrivilegesCallback = 45;
        static final int TRANSACTION_addOnOpportunisticSubscriptionsChangedListener = 2;
        static final int TRANSACTION_addOnSubscriptionsChangedListener = 1;
        static final int TRANSACTION_listenWithEventList = 4;
        static final int TRANSACTION_notifyActiveDataSubIdChanged = 30;
        static final int TRANSACTION_notifyAllowedNetworkTypesChanged = 42;
        static final int TRANSACTION_notifyBarringInfoChanged = 39;
        static final int TRANSACTION_notifyCallForwardingChanged = 10;
        static final int TRANSACTION_notifyCallForwardingChangedForSubscriber = 11;
        static final int TRANSACTION_notifyCallQualityChanged = 35;
        static final int TRANSACTION_notifyCallState = 6;
        static final int TRANSACTION_notifyCallStateForAllSubs = 5;
        static final int TRANSACTION_notifyCallbackModeStarted = 52;
        static final int TRANSACTION_notifyCallbackModeStopped = 53;
        static final int TRANSACTION_notifyCarrierConfigChanged = 51;
        static final int TRANSACTION_notifyCarrierNetworkChange = 25;
        static final int TRANSACTION_notifyCarrierNetworkChangeWithSubId = 26;
        static final int TRANSACTION_notifyCarrierPrivilegesChanged = 47;
        static final int TRANSACTION_notifyCarrierServiceChanged = 48;
        static final int TRANSACTION_notifyCellInfo = 16;
        static final int TRANSACTION_notifyCellInfoForSubscriber = 19;
        static final int TRANSACTION_notifyCellLocationForSubscriber = 15;
        static final int TRANSACTION_notifyDataActivityForSubscriber = 12;
        static final int TRANSACTION_notifyDataActivityForSubscriberWithSlot = 13;
        static final int TRANSACTION_notifyDataConnectionForSubscriber = 14;
        static final int TRANSACTION_notifyDataEnabled = 41;
        static final int TRANSACTION_notifyDisconnectCause = 18;
        static final int TRANSACTION_notifyDisplayInfoChanged = 28;
        static final int TRANSACTION_notifyEmergencyNumberList = 32;
        static final int TRANSACTION_notifyImsDisconnectCause = 37;
        static final int TRANSACTION_notifyLinkCapacityEstimateChanged = 43;
        static final int TRANSACTION_notifyMediaQualityStatusChanged = 36;
        static final int TRANSACTION_notifyMessageWaitingChangedForPhoneId = 9;
        static final int TRANSACTION_notifyOemHookRawEventForSubscriber = 22;
        static final int TRANSACTION_notifyOpportunisticSubscriptionInfoChanged = 24;
        static final int TRANSACTION_notifyOutgoingEmergencyCall = 33;
        static final int TRANSACTION_notifyOutgoingEmergencySms = 34;
        static final int TRANSACTION_notifyPhoneCapabilityChanged = 29;
        static final int TRANSACTION_notifyPhysicalChannelConfigForSubscriber = 40;
        static final int TRANSACTION_notifyPreciseCallState = 17;
        static final int TRANSACTION_notifyRadioPowerStateChanged = 31;
        static final int TRANSACTION_notifyRegistrationFailed = 38;
        static final int TRANSACTION_notifyServiceStateForPhoneId = 7;
        static final int TRANSACTION_notifySignalStrengthForPhoneId = 8;
        static final int TRANSACTION_notifySimActivationStateChangedForPhoneId = 21;
        static final int TRANSACTION_notifySimultaneousCellularCallingSubscriptionsChanged = 44;
        static final int TRANSACTION_notifySrvccStateChanged = 20;
        static final int TRANSACTION_notifySubscriptionInfoChanged = 23;
        static final int TRANSACTION_notifyUserMobileDataStateChangedForPhoneId = 27;
        static final int TRANSACTION_removeCarrierConfigChangeListener = 50;
        static final int TRANSACTION_removeCarrierPrivilegesCallback = 46;
        static final int TRANSACTION_removeOnSubscriptionsChangedListener = 3;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static com.android.internal.telephony.ITelephonyRegistry asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof com.android.internal.telephony.ITelephonyRegistry)) {
                return (com.android.internal.telephony.ITelephonyRegistry) queryLocalInterface;
            }
            return new com.android.internal.telephony.ITelephonyRegistry.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "addOnSubscriptionsChangedListener";
                case 2:
                    return "addOnOpportunisticSubscriptionsChangedListener";
                case 3:
                    return "removeOnSubscriptionsChangedListener";
                case 4:
                    return "listenWithEventList";
                case 5:
                    return "notifyCallStateForAllSubs";
                case 6:
                    return "notifyCallState";
                case 7:
                    return "notifyServiceStateForPhoneId";
                case 8:
                    return "notifySignalStrengthForPhoneId";
                case 9:
                    return "notifyMessageWaitingChangedForPhoneId";
                case 10:
                    return "notifyCallForwardingChanged";
                case 11:
                    return "notifyCallForwardingChangedForSubscriber";
                case 12:
                    return "notifyDataActivityForSubscriber";
                case 13:
                    return "notifyDataActivityForSubscriberWithSlot";
                case 14:
                    return "notifyDataConnectionForSubscriber";
                case 15:
                    return "notifyCellLocationForSubscriber";
                case 16:
                    return "notifyCellInfo";
                case 17:
                    return "notifyPreciseCallState";
                case 18:
                    return "notifyDisconnectCause";
                case 19:
                    return "notifyCellInfoForSubscriber";
                case 20:
                    return "notifySrvccStateChanged";
                case 21:
                    return "notifySimActivationStateChangedForPhoneId";
                case 22:
                    return "notifyOemHookRawEventForSubscriber";
                case 23:
                    return "notifySubscriptionInfoChanged";
                case 24:
                    return "notifyOpportunisticSubscriptionInfoChanged";
                case 25:
                    return "notifyCarrierNetworkChange";
                case 26:
                    return "notifyCarrierNetworkChangeWithSubId";
                case 27:
                    return "notifyUserMobileDataStateChangedForPhoneId";
                case 28:
                    return "notifyDisplayInfoChanged";
                case 29:
                    return "notifyPhoneCapabilityChanged";
                case 30:
                    return "notifyActiveDataSubIdChanged";
                case 31:
                    return "notifyRadioPowerStateChanged";
                case 32:
                    return "notifyEmergencyNumberList";
                case 33:
                    return "notifyOutgoingEmergencyCall";
                case 34:
                    return "notifyOutgoingEmergencySms";
                case 35:
                    return "notifyCallQualityChanged";
                case 36:
                    return "notifyMediaQualityStatusChanged";
                case 37:
                    return "notifyImsDisconnectCause";
                case 38:
                    return "notifyRegistrationFailed";
                case 39:
                    return "notifyBarringInfoChanged";
                case 40:
                    return "notifyPhysicalChannelConfigForSubscriber";
                case 41:
                    return "notifyDataEnabled";
                case 42:
                    return "notifyAllowedNetworkTypesChanged";
                case 43:
                    return "notifyLinkCapacityEstimateChanged";
                case 44:
                    return "notifySimultaneousCellularCallingSubscriptionsChanged";
                case 45:
                    return "addCarrierPrivilegesCallback";
                case 46:
                    return "removeCarrierPrivilegesCallback";
                case 47:
                    return "notifyCarrierPrivilegesChanged";
                case 48:
                    return "notifyCarrierServiceChanged";
                case 49:
                    return "addCarrierConfigChangeListener";
                case 50:
                    return "removeCarrierConfigChangeListener";
                case 51:
                    return "notifyCarrierConfigChanged";
                case 52:
                    return "notifyCallbackModeStarted";
                case 53:
                    return "notifyCallbackModeStopped";
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
                    com.android.internal.telephony.IOnSubscriptionsChangedListener asInterface = com.android.internal.telephony.IOnSubscriptionsChangedListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    addOnSubscriptionsChangedListener(readString, readString2, asInterface);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    java.lang.String readString3 = parcel.readString();
                    java.lang.String readString4 = parcel.readString();
                    com.android.internal.telephony.IOnSubscriptionsChangedListener asInterface2 = com.android.internal.telephony.IOnSubscriptionsChangedListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    addOnOpportunisticSubscriptionsChangedListener(readString3, readString4, asInterface2);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    java.lang.String readString5 = parcel.readString();
                    com.android.internal.telephony.IOnSubscriptionsChangedListener asInterface3 = com.android.internal.telephony.IOnSubscriptionsChangedListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    removeOnSubscriptionsChangedListener(readString5, asInterface3);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    boolean readBoolean = parcel.readBoolean();
                    boolean readBoolean2 = parcel.readBoolean();
                    int readInt = parcel.readInt();
                    java.lang.String readString6 = parcel.readString();
                    java.lang.String readString7 = parcel.readString();
                    com.android.internal.telephony.IPhoneStateListener asInterface4 = com.android.internal.telephony.IPhoneStateListener.Stub.asInterface(parcel.readStrongBinder());
                    int[] createIntArray = parcel.createIntArray();
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    listenWithEventList(readBoolean, readBoolean2, readInt, readString6, readString7, asInterface4, createIntArray, readBoolean3);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    int readInt2 = parcel.readInt();
                    java.lang.String readString8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    notifyCallStateForAllSubs(readInt2, readString8);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    int readInt5 = parcel.readInt();
                    java.lang.String readString9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    notifyCallState(readInt3, readInt4, readInt5, readString9);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    int readInt6 = parcel.readInt();
                    int readInt7 = parcel.readInt();
                    android.telephony.ServiceState serviceState = (android.telephony.ServiceState) parcel.readTypedObject(android.telephony.ServiceState.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyServiceStateForPhoneId(readInt6, readInt7, serviceState);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    int readInt8 = parcel.readInt();
                    int readInt9 = parcel.readInt();
                    android.telephony.SignalStrength signalStrength = (android.telephony.SignalStrength) parcel.readTypedObject(android.telephony.SignalStrength.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifySignalStrengthForPhoneId(readInt8, readInt9, signalStrength);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    int readInt10 = parcel.readInt();
                    int readInt11 = parcel.readInt();
                    boolean readBoolean4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    notifyMessageWaitingChangedForPhoneId(readInt10, readInt11, readBoolean4);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    boolean readBoolean5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    notifyCallForwardingChanged(readBoolean5);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    int readInt12 = parcel.readInt();
                    boolean readBoolean6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    notifyCallForwardingChangedForSubscriber(readInt12, readBoolean6);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    int readInt13 = parcel.readInt();
                    int readInt14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyDataActivityForSubscriber(readInt13, readInt14);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    int readInt15 = parcel.readInt();
                    int readInt16 = parcel.readInt();
                    int readInt17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyDataActivityForSubscriberWithSlot(readInt15, readInt16, readInt17);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    int readInt18 = parcel.readInt();
                    int readInt19 = parcel.readInt();
                    android.telephony.PreciseDataConnectionState preciseDataConnectionState = (android.telephony.PreciseDataConnectionState) parcel.readTypedObject(android.telephony.PreciseDataConnectionState.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyDataConnectionForSubscriber(readInt18, readInt19, preciseDataConnectionState);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    int readInt20 = parcel.readInt();
                    android.telephony.CellIdentity cellIdentity = (android.telephony.CellIdentity) parcel.readTypedObject(android.telephony.CellIdentity.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyCellLocationForSubscriber(readInt20, cellIdentity);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    java.util.ArrayList createTypedArrayList = parcel.createTypedArrayList(android.telephony.CellInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyCellInfo(createTypedArrayList);
                    parcel2.writeNoException();
                    return true;
                case 17:
                    int readInt21 = parcel.readInt();
                    int readInt22 = parcel.readInt();
                    int[] createIntArray2 = parcel.createIntArray();
                    java.lang.String[] createStringArray = parcel.createStringArray();
                    int[] createIntArray3 = parcel.createIntArray();
                    int[] createIntArray4 = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    notifyPreciseCallState(readInt21, readInt22, createIntArray2, createStringArray, createIntArray3, createIntArray4);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    int readInt23 = parcel.readInt();
                    int readInt24 = parcel.readInt();
                    int readInt25 = parcel.readInt();
                    int readInt26 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyDisconnectCause(readInt23, readInt24, readInt25, readInt26);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    int readInt27 = parcel.readInt();
                    java.util.ArrayList createTypedArrayList2 = parcel.createTypedArrayList(android.telephony.CellInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyCellInfoForSubscriber(readInt27, createTypedArrayList2);
                    parcel2.writeNoException();
                    return true;
                case 20:
                    int readInt28 = parcel.readInt();
                    int readInt29 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifySrvccStateChanged(readInt28, readInt29);
                    parcel2.writeNoException();
                    return true;
                case 21:
                    int readInt30 = parcel.readInt();
                    int readInt31 = parcel.readInt();
                    int readInt32 = parcel.readInt();
                    int readInt33 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifySimActivationStateChangedForPhoneId(readInt30, readInt31, readInt32, readInt33);
                    parcel2.writeNoException();
                    return true;
                case 22:
                    int readInt34 = parcel.readInt();
                    int readInt35 = parcel.readInt();
                    byte[] createByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    notifyOemHookRawEventForSubscriber(readInt34, readInt35, createByteArray);
                    parcel2.writeNoException();
                    return true;
                case 23:
                    notifySubscriptionInfoChanged();
                    parcel2.writeNoException();
                    return true;
                case 24:
                    notifyOpportunisticSubscriptionInfoChanged();
                    parcel2.writeNoException();
                    return true;
                case 25:
                    boolean readBoolean7 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    notifyCarrierNetworkChange(readBoolean7);
                    parcel2.writeNoException();
                    return true;
                case 26:
                    int readInt36 = parcel.readInt();
                    boolean readBoolean8 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    notifyCarrierNetworkChangeWithSubId(readInt36, readBoolean8);
                    parcel2.writeNoException();
                    return true;
                case 27:
                    int readInt37 = parcel.readInt();
                    int readInt38 = parcel.readInt();
                    boolean readBoolean9 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    notifyUserMobileDataStateChangedForPhoneId(readInt37, readInt38, readBoolean9);
                    parcel2.writeNoException();
                    return true;
                case 28:
                    int readInt39 = parcel.readInt();
                    int readInt40 = parcel.readInt();
                    android.telephony.TelephonyDisplayInfo telephonyDisplayInfo = (android.telephony.TelephonyDisplayInfo) parcel.readTypedObject(android.telephony.TelephonyDisplayInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyDisplayInfoChanged(readInt39, readInt40, telephonyDisplayInfo);
                    parcel2.writeNoException();
                    return true;
                case 29:
                    android.telephony.PhoneCapability phoneCapability = (android.telephony.PhoneCapability) parcel.readTypedObject(android.telephony.PhoneCapability.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyPhoneCapabilityChanged(phoneCapability);
                    parcel2.writeNoException();
                    return true;
                case 30:
                    int readInt41 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyActiveDataSubIdChanged(readInt41);
                    parcel2.writeNoException();
                    return true;
                case 31:
                    int readInt42 = parcel.readInt();
                    int readInt43 = parcel.readInt();
                    int readInt44 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyRadioPowerStateChanged(readInt42, readInt43, readInt44);
                    parcel2.writeNoException();
                    return true;
                case 32:
                    int readInt45 = parcel.readInt();
                    int readInt46 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyEmergencyNumberList(readInt45, readInt46);
                    parcel2.writeNoException();
                    return true;
                case 33:
                    int readInt47 = parcel.readInt();
                    int readInt48 = parcel.readInt();
                    android.telephony.emergency.EmergencyNumber emergencyNumber = (android.telephony.emergency.EmergencyNumber) parcel.readTypedObject(android.telephony.emergency.EmergencyNumber.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyOutgoingEmergencyCall(readInt47, readInt48, emergencyNumber);
                    parcel2.writeNoException();
                    return true;
                case 34:
                    int readInt49 = parcel.readInt();
                    int readInt50 = parcel.readInt();
                    android.telephony.emergency.EmergencyNumber emergencyNumber2 = (android.telephony.emergency.EmergencyNumber) parcel.readTypedObject(android.telephony.emergency.EmergencyNumber.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyOutgoingEmergencySms(readInt49, readInt50, emergencyNumber2);
                    parcel2.writeNoException();
                    return true;
                case 35:
                    android.telephony.CallQuality callQuality = (android.telephony.CallQuality) parcel.readTypedObject(android.telephony.CallQuality.CREATOR);
                    int readInt51 = parcel.readInt();
                    int readInt52 = parcel.readInt();
                    int readInt53 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyCallQualityChanged(callQuality, readInt51, readInt52, readInt53);
                    parcel2.writeNoException();
                    return true;
                case 36:
                    int readInt54 = parcel.readInt();
                    int readInt55 = parcel.readInt();
                    android.telephony.ims.MediaQualityStatus mediaQualityStatus = (android.telephony.ims.MediaQualityStatus) parcel.readTypedObject(android.telephony.ims.MediaQualityStatus.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyMediaQualityStatusChanged(readInt54, readInt55, mediaQualityStatus);
                    parcel2.writeNoException();
                    return true;
                case 37:
                    int readInt56 = parcel.readInt();
                    android.telephony.ims.ImsReasonInfo imsReasonInfo = (android.telephony.ims.ImsReasonInfo) parcel.readTypedObject(android.telephony.ims.ImsReasonInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyImsDisconnectCause(readInt56, imsReasonInfo);
                    parcel2.writeNoException();
                    return true;
                case 38:
                    int readInt57 = parcel.readInt();
                    int readInt58 = parcel.readInt();
                    android.telephony.CellIdentity cellIdentity2 = (android.telephony.CellIdentity) parcel.readTypedObject(android.telephony.CellIdentity.CREATOR);
                    java.lang.String readString10 = parcel.readString();
                    int readInt59 = parcel.readInt();
                    int readInt60 = parcel.readInt();
                    int readInt61 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyRegistrationFailed(readInt57, readInt58, cellIdentity2, readString10, readInt59, readInt60, readInt61);
                    parcel2.writeNoException();
                    return true;
                case 39:
                    int readInt62 = parcel.readInt();
                    int readInt63 = parcel.readInt();
                    android.telephony.BarringInfo barringInfo = (android.telephony.BarringInfo) parcel.readTypedObject(android.telephony.BarringInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyBarringInfoChanged(readInt62, readInt63, barringInfo);
                    parcel2.writeNoException();
                    return true;
                case 40:
                    int readInt64 = parcel.readInt();
                    int readInt65 = parcel.readInt();
                    java.util.ArrayList createTypedArrayList3 = parcel.createTypedArrayList(android.telephony.PhysicalChannelConfig.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyPhysicalChannelConfigForSubscriber(readInt64, readInt65, createTypedArrayList3);
                    parcel2.writeNoException();
                    return true;
                case 41:
                    int readInt66 = parcel.readInt();
                    int readInt67 = parcel.readInt();
                    boolean readBoolean10 = parcel.readBoolean();
                    int readInt68 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyDataEnabled(readInt66, readInt67, readBoolean10, readInt68);
                    parcel2.writeNoException();
                    return true;
                case 42:
                    int readInt69 = parcel.readInt();
                    int readInt70 = parcel.readInt();
                    int readInt71 = parcel.readInt();
                    long readLong = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    notifyAllowedNetworkTypesChanged(readInt69, readInt70, readInt71, readLong);
                    parcel2.writeNoException();
                    return true;
                case 43:
                    int readInt72 = parcel.readInt();
                    int readInt73 = parcel.readInt();
                    java.util.ArrayList createTypedArrayList4 = parcel.createTypedArrayList(android.telephony.LinkCapacityEstimate.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyLinkCapacityEstimateChanged(readInt72, readInt73, createTypedArrayList4);
                    parcel2.writeNoException();
                    return true;
                case 44:
                    int[] createIntArray5 = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    notifySimultaneousCellularCallingSubscriptionsChanged(createIntArray5);
                    parcel2.writeNoException();
                    return true;
                case 45:
                    int readInt74 = parcel.readInt();
                    com.android.internal.telephony.ICarrierPrivilegesCallback asInterface5 = com.android.internal.telephony.ICarrierPrivilegesCallback.Stub.asInterface(parcel.readStrongBinder());
                    java.lang.String readString11 = parcel.readString();
                    java.lang.String readString12 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    addCarrierPrivilegesCallback(readInt74, asInterface5, readString11, readString12);
                    parcel2.writeNoException();
                    return true;
                case 46:
                    com.android.internal.telephony.ICarrierPrivilegesCallback asInterface6 = com.android.internal.telephony.ICarrierPrivilegesCallback.Stub.asInterface(parcel.readStrongBinder());
                    java.lang.String readString13 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    removeCarrierPrivilegesCallback(asInterface6, readString13);
                    parcel2.writeNoException();
                    return true;
                case 47:
                    int readInt75 = parcel.readInt();
                    java.util.ArrayList<java.lang.String> createStringArrayList = parcel.createStringArrayList();
                    int[] createIntArray6 = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    notifyCarrierPrivilegesChanged(readInt75, createStringArrayList, createIntArray6);
                    parcel2.writeNoException();
                    return true;
                case 48:
                    int readInt76 = parcel.readInt();
                    java.lang.String readString14 = parcel.readString();
                    int readInt77 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyCarrierServiceChanged(readInt76, readString14, readInt77);
                    parcel2.writeNoException();
                    return true;
                case 49:
                    com.android.internal.telephony.ICarrierConfigChangeListener asInterface7 = com.android.internal.telephony.ICarrierConfigChangeListener.Stub.asInterface(parcel.readStrongBinder());
                    java.lang.String readString15 = parcel.readString();
                    java.lang.String readString16 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    addCarrierConfigChangeListener(asInterface7, readString15, readString16);
                    parcel2.writeNoException();
                    return true;
                case 50:
                    com.android.internal.telephony.ICarrierConfigChangeListener asInterface8 = com.android.internal.telephony.ICarrierConfigChangeListener.Stub.asInterface(parcel.readStrongBinder());
                    java.lang.String readString17 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    removeCarrierConfigChangeListener(asInterface8, readString17);
                    parcel2.writeNoException();
                    return true;
                case 51:
                    int readInt78 = parcel.readInt();
                    int readInt79 = parcel.readInt();
                    int readInt80 = parcel.readInt();
                    int readInt81 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyCarrierConfigChanged(readInt78, readInt79, readInt80, readInt81);
                    parcel2.writeNoException();
                    return true;
                case 52:
                    int readInt82 = parcel.readInt();
                    int readInt83 = parcel.readInt();
                    int readInt84 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyCallbackModeStarted(readInt82, readInt83, readInt84);
                    parcel2.writeNoException();
                    return true;
                case 53:
                    int readInt85 = parcel.readInt();
                    int readInt86 = parcel.readInt();
                    int readInt87 = parcel.readInt();
                    int readInt88 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyCallbackModeStopped(readInt85, readInt86, readInt87, readInt88);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements com.android.internal.telephony.ITelephonyRegistry {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return com.android.internal.telephony.ITelephonyRegistry.Stub.DESCRIPTOR;
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void addOnSubscriptionsChangedListener(java.lang.String str, java.lang.String str2, com.android.internal.telephony.IOnSubscriptionsChangedListener iOnSubscriptionsChangedListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephonyRegistry.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeStrongInterface(iOnSubscriptionsChangedListener);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void addOnOpportunisticSubscriptionsChangedListener(java.lang.String str, java.lang.String str2, com.android.internal.telephony.IOnSubscriptionsChangedListener iOnSubscriptionsChangedListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephonyRegistry.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeStrongInterface(iOnSubscriptionsChangedListener);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void removeOnSubscriptionsChangedListener(java.lang.String str, com.android.internal.telephony.IOnSubscriptionsChangedListener iOnSubscriptionsChangedListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephonyRegistry.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iOnSubscriptionsChangedListener);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void listenWithEventList(boolean z, boolean z2, int i, java.lang.String str, java.lang.String str2, com.android.internal.telephony.IPhoneStateListener iPhoneStateListener, int[] iArr, boolean z3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephonyRegistry.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeStrongInterface(iPhoneStateListener);
                    obtain.writeIntArray(iArr);
                    obtain.writeBoolean(z3);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyCallStateForAllSubs(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephonyRegistry.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyCallState(int i, int i2, int i3, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephonyRegistry.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeString(str);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyServiceStateForPhoneId(int i, int i2, android.telephony.ServiceState serviceState) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephonyRegistry.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(serviceState, 0);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifySignalStrengthForPhoneId(int i, int i2, android.telephony.SignalStrength signalStrength) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephonyRegistry.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(signalStrength, 0);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyMessageWaitingChangedForPhoneId(int i, int i2, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephonyRegistry.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyCallForwardingChanged(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephonyRegistry.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyCallForwardingChangedForSubscriber(int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephonyRegistry.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyDataActivityForSubscriber(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephonyRegistry.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyDataActivityForSubscriberWithSlot(int i, int i2, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephonyRegistry.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyDataConnectionForSubscriber(int i, int i2, android.telephony.PreciseDataConnectionState preciseDataConnectionState) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephonyRegistry.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(preciseDataConnectionState, 0);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyCellLocationForSubscriber(int i, android.telephony.CellIdentity cellIdentity) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephonyRegistry.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(cellIdentity, 0);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyCellInfo(java.util.List<android.telephony.CellInfo> list) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephonyRegistry.Stub.DESCRIPTOR);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyPreciseCallState(int i, int i2, int[] iArr, java.lang.String[] strArr, int[] iArr2, int[] iArr3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephonyRegistry.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeIntArray(iArr);
                    obtain.writeStringArray(strArr);
                    obtain.writeIntArray(iArr2);
                    obtain.writeIntArray(iArr3);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyDisconnectCause(int i, int i2, int i3, int i4) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephonyRegistry.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyCellInfoForSubscriber(int i, java.util.List<android.telephony.CellInfo> list) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephonyRegistry.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifySrvccStateChanged(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephonyRegistry.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifySimActivationStateChangedForPhoneId(int i, int i2, int i3, int i4) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephonyRegistry.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyOemHookRawEventForSubscriber(int i, int i2, byte[] bArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephonyRegistry.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifySubscriptionInfoChanged() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephonyRegistry.Stub.DESCRIPTOR);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyOpportunisticSubscriptionInfoChanged() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephonyRegistry.Stub.DESCRIPTOR);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyCarrierNetworkChange(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephonyRegistry.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyCarrierNetworkChangeWithSubId(int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephonyRegistry.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyUserMobileDataStateChangedForPhoneId(int i, int i2, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephonyRegistry.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyDisplayInfoChanged(int i, int i2, android.telephony.TelephonyDisplayInfo telephonyDisplayInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephonyRegistry.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(telephonyDisplayInfo, 0);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyPhoneCapabilityChanged(android.telephony.PhoneCapability phoneCapability) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephonyRegistry.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(phoneCapability, 0);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyActiveDataSubIdChanged(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephonyRegistry.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyRadioPowerStateChanged(int i, int i2, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephonyRegistry.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyEmergencyNumberList(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephonyRegistry.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyOutgoingEmergencyCall(int i, int i2, android.telephony.emergency.EmergencyNumber emergencyNumber) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephonyRegistry.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(emergencyNumber, 0);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyOutgoingEmergencySms(int i, int i2, android.telephony.emergency.EmergencyNumber emergencyNumber) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephonyRegistry.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(emergencyNumber, 0);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyCallQualityChanged(android.telephony.CallQuality callQuality, int i, int i2, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephonyRegistry.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(callQuality, 0);
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

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyMediaQualityStatusChanged(int i, int i2, android.telephony.ims.MediaQualityStatus mediaQualityStatus) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephonyRegistry.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(mediaQualityStatus, 0);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyImsDisconnectCause(int i, android.telephony.ims.ImsReasonInfo imsReasonInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephonyRegistry.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(imsReasonInfo, 0);
                    this.mRemote.transact(37, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyRegistrationFailed(int i, int i2, android.telephony.CellIdentity cellIdentity, java.lang.String str, int i3, int i4, int i5) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephonyRegistry.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(cellIdentity, 0);
                    obtain.writeString(str);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    obtain.writeInt(i5);
                    this.mRemote.transact(38, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyBarringInfoChanged(int i, int i2, android.telephony.BarringInfo barringInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephonyRegistry.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(barringInfo, 0);
                    this.mRemote.transact(39, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyPhysicalChannelConfigForSubscriber(int i, int i2, java.util.List<android.telephony.PhysicalChannelConfig> list) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephonyRegistry.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(40, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyDataEnabled(int i, int i2, boolean z, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephonyRegistry.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i3);
                    this.mRemote.transact(41, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyAllowedNetworkTypesChanged(int i, int i2, int i3, long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephonyRegistry.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeLong(j);
                    this.mRemote.transact(42, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyLinkCapacityEstimateChanged(int i, int i2, java.util.List<android.telephony.LinkCapacityEstimate> list) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephonyRegistry.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(43, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifySimultaneousCellularCallingSubscriptionsChanged(int[] iArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephonyRegistry.Stub.DESCRIPTOR);
                    obtain.writeIntArray(iArr);
                    this.mRemote.transact(44, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void addCarrierPrivilegesCallback(int i, com.android.internal.telephony.ICarrierPrivilegesCallback iCarrierPrivilegesCallback, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephonyRegistry.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iCarrierPrivilegesCallback);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(45, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void removeCarrierPrivilegesCallback(com.android.internal.telephony.ICarrierPrivilegesCallback iCarrierPrivilegesCallback, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephonyRegistry.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iCarrierPrivilegesCallback);
                    obtain.writeString(str);
                    this.mRemote.transact(46, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyCarrierPrivilegesChanged(int i, java.util.List<java.lang.String> list, int[] iArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephonyRegistry.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStringList(list);
                    obtain.writeIntArray(iArr);
                    this.mRemote.transact(47, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyCarrierServiceChanged(int i, java.lang.String str, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephonyRegistry.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeInt(i2);
                    this.mRemote.transact(48, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void addCarrierConfigChangeListener(com.android.internal.telephony.ICarrierConfigChangeListener iCarrierConfigChangeListener, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephonyRegistry.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iCarrierConfigChangeListener);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(49, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void removeCarrierConfigChangeListener(com.android.internal.telephony.ICarrierConfigChangeListener iCarrierConfigChangeListener, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephonyRegistry.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iCarrierConfigChangeListener);
                    obtain.writeString(str);
                    this.mRemote.transact(50, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyCarrierConfigChanged(int i, int i2, int i3, int i4) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephonyRegistry.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    this.mRemote.transact(51, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyCallbackModeStarted(int i, int i2, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephonyRegistry.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(52, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITelephonyRegistry
            public void notifyCallbackModeStopped(int i, int i2, int i3, int i4) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.ITelephonyRegistry.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    this.mRemote.transact(53, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 52;
        }
    }
}
