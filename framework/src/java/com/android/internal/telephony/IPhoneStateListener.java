package com.android.internal.telephony;

/* loaded from: classes5.dex */
public interface IPhoneStateListener extends android.os.IInterface {
    void onActiveDataSubIdChanged(int i) throws android.os.RemoteException;

    void onAllowedNetworkTypesChanged(int i, long j) throws android.os.RemoteException;

    void onBarringInfoChanged(android.telephony.BarringInfo barringInfo) throws android.os.RemoteException;

    void onCallBackModeStarted(int i) throws android.os.RemoteException;

    void onCallBackModeStopped(int i, int i2) throws android.os.RemoteException;

    void onCallDisconnectCauseChanged(int i, int i2) throws android.os.RemoteException;

    void onCallForwardingIndicatorChanged(boolean z) throws android.os.RemoteException;

    void onCallStateChanged(int i) throws android.os.RemoteException;

    void onCallStatesChanged(java.util.List<android.telephony.CallState> list) throws android.os.RemoteException;

    void onCarrierNetworkChange(boolean z) throws android.os.RemoteException;

    void onCellInfoChanged(java.util.List<android.telephony.CellInfo> list) throws android.os.RemoteException;

    void onCellLocationChanged(android.telephony.CellIdentity cellIdentity) throws android.os.RemoteException;

    void onDataActivationStateChanged(int i) throws android.os.RemoteException;

    void onDataActivity(int i) throws android.os.RemoteException;

    void onDataConnectionRealTimeInfoChanged(android.telephony.DataConnectionRealTimeInfo dataConnectionRealTimeInfo) throws android.os.RemoteException;

    void onDataConnectionStateChanged(int i, int i2) throws android.os.RemoteException;

    void onDataEnabledChanged(boolean z, int i) throws android.os.RemoteException;

    void onDisplayInfoChanged(android.telephony.TelephonyDisplayInfo telephonyDisplayInfo) throws android.os.RemoteException;

    void onEmergencyNumberListChanged(java.util.Map map) throws android.os.RemoteException;

    void onImsCallDisconnectCauseChanged(android.telephony.ims.ImsReasonInfo imsReasonInfo) throws android.os.RemoteException;

    void onLegacyCallStateChanged(int i, java.lang.String str) throws android.os.RemoteException;

    void onLinkCapacityEstimateChanged(java.util.List<android.telephony.LinkCapacityEstimate> list) throws android.os.RemoteException;

    void onMediaQualityStatusChanged(android.telephony.ims.MediaQualityStatus mediaQualityStatus) throws android.os.RemoteException;

    void onMessageWaitingIndicatorChanged(boolean z) throws android.os.RemoteException;

    void onOemHookRawEvent(byte[] bArr) throws android.os.RemoteException;

    void onOutgoingEmergencyCall(android.telephony.emergency.EmergencyNumber emergencyNumber, int i) throws android.os.RemoteException;

    void onOutgoingEmergencySms(android.telephony.emergency.EmergencyNumber emergencyNumber, int i) throws android.os.RemoteException;

    void onPhoneCapabilityChanged(android.telephony.PhoneCapability phoneCapability) throws android.os.RemoteException;

    void onPhysicalChannelConfigChanged(java.util.List<android.telephony.PhysicalChannelConfig> list) throws android.os.RemoteException;

    void onPreciseCallStateChanged(android.telephony.PreciseCallState preciseCallState) throws android.os.RemoteException;

    void onPreciseDataConnectionStateChanged(android.telephony.PreciseDataConnectionState preciseDataConnectionState) throws android.os.RemoteException;

    void onRadioPowerStateChanged(int i) throws android.os.RemoteException;

    void onRegistrationFailed(android.telephony.CellIdentity cellIdentity, java.lang.String str, int i, int i2, int i3) throws android.os.RemoteException;

    void onServiceStateChanged(android.telephony.ServiceState serviceState) throws android.os.RemoteException;

    void onSignalStrengthChanged(int i) throws android.os.RemoteException;

    void onSignalStrengthsChanged(android.telephony.SignalStrength signalStrength) throws android.os.RemoteException;

    void onSimultaneousCallingStateChanged(int[] iArr) throws android.os.RemoteException;

    void onSrvccStateChanged(int i) throws android.os.RemoteException;

    void onUserMobileDataStateChanged(boolean z) throws android.os.RemoteException;

    void onVoiceActivationStateChanged(int i) throws android.os.RemoteException;

    public static class Default implements com.android.internal.telephony.IPhoneStateListener {
        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onServiceStateChanged(android.telephony.ServiceState serviceState) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onSignalStrengthChanged(int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onMessageWaitingIndicatorChanged(boolean z) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onCallForwardingIndicatorChanged(boolean z) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onCellLocationChanged(android.telephony.CellIdentity cellIdentity) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onLegacyCallStateChanged(int i, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onCallStateChanged(int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onDataConnectionStateChanged(int i, int i2) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onDataActivity(int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onSignalStrengthsChanged(android.telephony.SignalStrength signalStrength) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onCellInfoChanged(java.util.List<android.telephony.CellInfo> list) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onPreciseCallStateChanged(android.telephony.PreciseCallState preciseCallState) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onPreciseDataConnectionStateChanged(android.telephony.PreciseDataConnectionState preciseDataConnectionState) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onDataConnectionRealTimeInfoChanged(android.telephony.DataConnectionRealTimeInfo dataConnectionRealTimeInfo) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onSrvccStateChanged(int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onVoiceActivationStateChanged(int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onDataActivationStateChanged(int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onOemHookRawEvent(byte[] bArr) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onCarrierNetworkChange(boolean z) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onUserMobileDataStateChanged(boolean z) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onDisplayInfoChanged(android.telephony.TelephonyDisplayInfo telephonyDisplayInfo) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onPhoneCapabilityChanged(android.telephony.PhoneCapability phoneCapability) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onActiveDataSubIdChanged(int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onRadioPowerStateChanged(int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onCallStatesChanged(java.util.List<android.telephony.CallState> list) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onEmergencyNumberListChanged(java.util.Map map) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onOutgoingEmergencyCall(android.telephony.emergency.EmergencyNumber emergencyNumber, int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onOutgoingEmergencySms(android.telephony.emergency.EmergencyNumber emergencyNumber, int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onCallDisconnectCauseChanged(int i, int i2) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onImsCallDisconnectCauseChanged(android.telephony.ims.ImsReasonInfo imsReasonInfo) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onRegistrationFailed(android.telephony.CellIdentity cellIdentity, java.lang.String str, int i, int i2, int i3) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onBarringInfoChanged(android.telephony.BarringInfo barringInfo) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onPhysicalChannelConfigChanged(java.util.List<android.telephony.PhysicalChannelConfig> list) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onDataEnabledChanged(boolean z, int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onAllowedNetworkTypesChanged(int i, long j) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onLinkCapacityEstimateChanged(java.util.List<android.telephony.LinkCapacityEstimate> list) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onMediaQualityStatusChanged(android.telephony.ims.MediaQualityStatus mediaQualityStatus) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onCallBackModeStarted(int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onCallBackModeStopped(int i, int i2) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telephony.IPhoneStateListener
        public void onSimultaneousCallingStateChanged(int[] iArr) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements com.android.internal.telephony.IPhoneStateListener {
        public static final java.lang.String DESCRIPTOR = "com.android.internal.telephony.IPhoneStateListener";
        static final int TRANSACTION_onActiveDataSubIdChanged = 23;
        static final int TRANSACTION_onAllowedNetworkTypesChanged = 35;
        static final int TRANSACTION_onBarringInfoChanged = 32;
        static final int TRANSACTION_onCallBackModeStarted = 38;
        static final int TRANSACTION_onCallBackModeStopped = 39;
        static final int TRANSACTION_onCallDisconnectCauseChanged = 29;
        static final int TRANSACTION_onCallForwardingIndicatorChanged = 4;
        static final int TRANSACTION_onCallStateChanged = 7;
        static final int TRANSACTION_onCallStatesChanged = 25;
        static final int TRANSACTION_onCarrierNetworkChange = 19;
        static final int TRANSACTION_onCellInfoChanged = 11;
        static final int TRANSACTION_onCellLocationChanged = 5;
        static final int TRANSACTION_onDataActivationStateChanged = 17;
        static final int TRANSACTION_onDataActivity = 9;
        static final int TRANSACTION_onDataConnectionRealTimeInfoChanged = 14;
        static final int TRANSACTION_onDataConnectionStateChanged = 8;
        static final int TRANSACTION_onDataEnabledChanged = 34;
        static final int TRANSACTION_onDisplayInfoChanged = 21;
        static final int TRANSACTION_onEmergencyNumberListChanged = 26;
        static final int TRANSACTION_onImsCallDisconnectCauseChanged = 30;
        static final int TRANSACTION_onLegacyCallStateChanged = 6;
        static final int TRANSACTION_onLinkCapacityEstimateChanged = 36;
        static final int TRANSACTION_onMediaQualityStatusChanged = 37;
        static final int TRANSACTION_onMessageWaitingIndicatorChanged = 3;
        static final int TRANSACTION_onOemHookRawEvent = 18;
        static final int TRANSACTION_onOutgoingEmergencyCall = 27;
        static final int TRANSACTION_onOutgoingEmergencySms = 28;
        static final int TRANSACTION_onPhoneCapabilityChanged = 22;
        static final int TRANSACTION_onPhysicalChannelConfigChanged = 33;
        static final int TRANSACTION_onPreciseCallStateChanged = 12;
        static final int TRANSACTION_onPreciseDataConnectionStateChanged = 13;
        static final int TRANSACTION_onRadioPowerStateChanged = 24;
        static final int TRANSACTION_onRegistrationFailed = 31;
        static final int TRANSACTION_onServiceStateChanged = 1;
        static final int TRANSACTION_onSignalStrengthChanged = 2;
        static final int TRANSACTION_onSignalStrengthsChanged = 10;
        static final int TRANSACTION_onSimultaneousCallingStateChanged = 40;
        static final int TRANSACTION_onSrvccStateChanged = 15;
        static final int TRANSACTION_onUserMobileDataStateChanged = 20;
        static final int TRANSACTION_onVoiceActivationStateChanged = 16;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static com.android.internal.telephony.IPhoneStateListener asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof com.android.internal.telephony.IPhoneStateListener)) {
                return (com.android.internal.telephony.IPhoneStateListener) queryLocalInterface;
            }
            return new com.android.internal.telephony.IPhoneStateListener.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onServiceStateChanged";
                case 2:
                    return "onSignalStrengthChanged";
                case 3:
                    return "onMessageWaitingIndicatorChanged";
                case 4:
                    return "onCallForwardingIndicatorChanged";
                case 5:
                    return "onCellLocationChanged";
                case 6:
                    return "onLegacyCallStateChanged";
                case 7:
                    return "onCallStateChanged";
                case 8:
                    return "onDataConnectionStateChanged";
                case 9:
                    return "onDataActivity";
                case 10:
                    return "onSignalStrengthsChanged";
                case 11:
                    return "onCellInfoChanged";
                case 12:
                    return "onPreciseCallStateChanged";
                case 13:
                    return "onPreciseDataConnectionStateChanged";
                case 14:
                    return "onDataConnectionRealTimeInfoChanged";
                case 15:
                    return "onSrvccStateChanged";
                case 16:
                    return "onVoiceActivationStateChanged";
                case 17:
                    return "onDataActivationStateChanged";
                case 18:
                    return "onOemHookRawEvent";
                case 19:
                    return "onCarrierNetworkChange";
                case 20:
                    return "onUserMobileDataStateChanged";
                case 21:
                    return "onDisplayInfoChanged";
                case 22:
                    return "onPhoneCapabilityChanged";
                case 23:
                    return "onActiveDataSubIdChanged";
                case 24:
                    return "onRadioPowerStateChanged";
                case 25:
                    return "onCallStatesChanged";
                case 26:
                    return "onEmergencyNumberListChanged";
                case 27:
                    return "onOutgoingEmergencyCall";
                case 28:
                    return "onOutgoingEmergencySms";
                case 29:
                    return "onCallDisconnectCauseChanged";
                case 30:
                    return "onImsCallDisconnectCauseChanged";
                case 31:
                    return "onRegistrationFailed";
                case 32:
                    return "onBarringInfoChanged";
                case 33:
                    return "onPhysicalChannelConfigChanged";
                case 34:
                    return "onDataEnabledChanged";
                case 35:
                    return "onAllowedNetworkTypesChanged";
                case 36:
                    return "onLinkCapacityEstimateChanged";
                case 37:
                    return "onMediaQualityStatusChanged";
                case 38:
                    return "onCallBackModeStarted";
                case 39:
                    return "onCallBackModeStopped";
                case 40:
                    return "onSimultaneousCallingStateChanged";
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
                    android.telephony.ServiceState serviceState = (android.telephony.ServiceState) parcel.readTypedObject(android.telephony.ServiceState.CREATOR);
                    parcel.enforceNoDataAvail();
                    onServiceStateChanged(serviceState);
                    return true;
                case 2:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onSignalStrengthChanged(readInt);
                    return true;
                case 3:
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onMessageWaitingIndicatorChanged(readBoolean);
                    return true;
                case 4:
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onCallForwardingIndicatorChanged(readBoolean2);
                    return true;
                case 5:
                    android.telephony.CellIdentity cellIdentity = (android.telephony.CellIdentity) parcel.readTypedObject(android.telephony.CellIdentity.CREATOR);
                    parcel.enforceNoDataAvail();
                    onCellLocationChanged(cellIdentity);
                    return true;
                case 6:
                    int readInt2 = parcel.readInt();
                    java.lang.String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onLegacyCallStateChanged(readInt2, readString);
                    return true;
                case 7:
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onCallStateChanged(readInt3);
                    return true;
                case 8:
                    int readInt4 = parcel.readInt();
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onDataConnectionStateChanged(readInt4, readInt5);
                    return true;
                case 9:
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onDataActivity(readInt6);
                    return true;
                case 10:
                    android.telephony.SignalStrength signalStrength = (android.telephony.SignalStrength) parcel.readTypedObject(android.telephony.SignalStrength.CREATOR);
                    parcel.enforceNoDataAvail();
                    onSignalStrengthsChanged(signalStrength);
                    return true;
                case 11:
                    java.util.ArrayList createTypedArrayList = parcel.createTypedArrayList(android.telephony.CellInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    onCellInfoChanged(createTypedArrayList);
                    return true;
                case 12:
                    android.telephony.PreciseCallState preciseCallState = (android.telephony.PreciseCallState) parcel.readTypedObject(android.telephony.PreciseCallState.CREATOR);
                    parcel.enforceNoDataAvail();
                    onPreciseCallStateChanged(preciseCallState);
                    return true;
                case 13:
                    android.telephony.PreciseDataConnectionState preciseDataConnectionState = (android.telephony.PreciseDataConnectionState) parcel.readTypedObject(android.telephony.PreciseDataConnectionState.CREATOR);
                    parcel.enforceNoDataAvail();
                    onPreciseDataConnectionStateChanged(preciseDataConnectionState);
                    return true;
                case 14:
                    android.telephony.DataConnectionRealTimeInfo dataConnectionRealTimeInfo = (android.telephony.DataConnectionRealTimeInfo) parcel.readTypedObject(android.telephony.DataConnectionRealTimeInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    onDataConnectionRealTimeInfoChanged(dataConnectionRealTimeInfo);
                    return true;
                case 15:
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onSrvccStateChanged(readInt7);
                    return true;
                case 16:
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onVoiceActivationStateChanged(readInt8);
                    return true;
                case 17:
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onDataActivationStateChanged(readInt9);
                    return true;
                case 18:
                    byte[] createByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    onOemHookRawEvent(createByteArray);
                    return true;
                case 19:
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onCarrierNetworkChange(readBoolean3);
                    return true;
                case 20:
                    boolean readBoolean4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onUserMobileDataStateChanged(readBoolean4);
                    return true;
                case 21:
                    android.telephony.TelephonyDisplayInfo telephonyDisplayInfo = (android.telephony.TelephonyDisplayInfo) parcel.readTypedObject(android.telephony.TelephonyDisplayInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    onDisplayInfoChanged(telephonyDisplayInfo);
                    return true;
                case 22:
                    android.telephony.PhoneCapability phoneCapability = (android.telephony.PhoneCapability) parcel.readTypedObject(android.telephony.PhoneCapability.CREATOR);
                    parcel.enforceNoDataAvail();
                    onPhoneCapabilityChanged(phoneCapability);
                    return true;
                case 23:
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onActiveDataSubIdChanged(readInt10);
                    return true;
                case 24:
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onRadioPowerStateChanged(readInt11);
                    return true;
                case 25:
                    java.util.ArrayList createTypedArrayList2 = parcel.createTypedArrayList(android.telephony.CallState.CREATOR);
                    parcel.enforceNoDataAvail();
                    onCallStatesChanged(createTypedArrayList2);
                    return true;
                case 26:
                    java.util.HashMap readHashMap = parcel.readHashMap(getClass().getClassLoader());
                    parcel.enforceNoDataAvail();
                    onEmergencyNumberListChanged(readHashMap);
                    return true;
                case 27:
                    android.telephony.emergency.EmergencyNumber emergencyNumber = (android.telephony.emergency.EmergencyNumber) parcel.readTypedObject(android.telephony.emergency.EmergencyNumber.CREATOR);
                    int readInt12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onOutgoingEmergencyCall(emergencyNumber, readInt12);
                    return true;
                case 28:
                    android.telephony.emergency.EmergencyNumber emergencyNumber2 = (android.telephony.emergency.EmergencyNumber) parcel.readTypedObject(android.telephony.emergency.EmergencyNumber.CREATOR);
                    int readInt13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onOutgoingEmergencySms(emergencyNumber2, readInt13);
                    return true;
                case 29:
                    int readInt14 = parcel.readInt();
                    int readInt15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onCallDisconnectCauseChanged(readInt14, readInt15);
                    return true;
                case 30:
                    android.telephony.ims.ImsReasonInfo imsReasonInfo = (android.telephony.ims.ImsReasonInfo) parcel.readTypedObject(android.telephony.ims.ImsReasonInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    onImsCallDisconnectCauseChanged(imsReasonInfo);
                    return true;
                case 31:
                    android.telephony.CellIdentity cellIdentity2 = (android.telephony.CellIdentity) parcel.readTypedObject(android.telephony.CellIdentity.CREATOR);
                    java.lang.String readString2 = parcel.readString();
                    int readInt16 = parcel.readInt();
                    int readInt17 = parcel.readInt();
                    int readInt18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onRegistrationFailed(cellIdentity2, readString2, readInt16, readInt17, readInt18);
                    return true;
                case 32:
                    android.telephony.BarringInfo barringInfo = (android.telephony.BarringInfo) parcel.readTypedObject(android.telephony.BarringInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    onBarringInfoChanged(barringInfo);
                    return true;
                case 33:
                    java.util.ArrayList createTypedArrayList3 = parcel.createTypedArrayList(android.telephony.PhysicalChannelConfig.CREATOR);
                    parcel.enforceNoDataAvail();
                    onPhysicalChannelConfigChanged(createTypedArrayList3);
                    return true;
                case 34:
                    boolean readBoolean5 = parcel.readBoolean();
                    int readInt19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onDataEnabledChanged(readBoolean5, readInt19);
                    return true;
                case 35:
                    int readInt20 = parcel.readInt();
                    long readLong = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    onAllowedNetworkTypesChanged(readInt20, readLong);
                    return true;
                case 36:
                    java.util.ArrayList createTypedArrayList4 = parcel.createTypedArrayList(android.telephony.LinkCapacityEstimate.CREATOR);
                    parcel.enforceNoDataAvail();
                    onLinkCapacityEstimateChanged(createTypedArrayList4);
                    return true;
                case 37:
                    android.telephony.ims.MediaQualityStatus mediaQualityStatus = (android.telephony.ims.MediaQualityStatus) parcel.readTypedObject(android.telephony.ims.MediaQualityStatus.CREATOR);
                    parcel.enforceNoDataAvail();
                    onMediaQualityStatusChanged(mediaQualityStatus);
                    return true;
                case 38:
                    int readInt21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onCallBackModeStarted(readInt21);
                    return true;
                case 39:
                    int readInt22 = parcel.readInt();
                    int readInt23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onCallBackModeStopped(readInt22, readInt23);
                    return true;
                case 40:
                    int[] createIntArray = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    onSimultaneousCallingStateChanged(createIntArray);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements com.android.internal.telephony.IPhoneStateListener {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return com.android.internal.telephony.IPhoneStateListener.Stub.DESCRIPTOR;
            }

            @Override // com.android.internal.telephony.IPhoneStateListener
            public void onServiceStateChanged(android.telephony.ServiceState serviceState) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.IPhoneStateListener.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(serviceState, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneStateListener
            public void onSignalStrengthChanged(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.IPhoneStateListener.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneStateListener
            public void onMessageWaitingIndicatorChanged(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.IPhoneStateListener.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneStateListener
            public void onCallForwardingIndicatorChanged(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.IPhoneStateListener.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneStateListener
            public void onCellLocationChanged(android.telephony.CellIdentity cellIdentity) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.IPhoneStateListener.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(cellIdentity, 0);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneStateListener
            public void onLegacyCallStateChanged(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.IPhoneStateListener.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneStateListener
            public void onCallStateChanged(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.IPhoneStateListener.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneStateListener
            public void onDataConnectionStateChanged(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.IPhoneStateListener.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneStateListener
            public void onDataActivity(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.IPhoneStateListener.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneStateListener
            public void onSignalStrengthsChanged(android.telephony.SignalStrength signalStrength) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.IPhoneStateListener.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(signalStrength, 0);
                    this.mRemote.transact(10, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneStateListener
            public void onCellInfoChanged(java.util.List<android.telephony.CellInfo> list) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.IPhoneStateListener.Stub.DESCRIPTOR);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(11, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneStateListener
            public void onPreciseCallStateChanged(android.telephony.PreciseCallState preciseCallState) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.IPhoneStateListener.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(preciseCallState, 0);
                    this.mRemote.transact(12, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneStateListener
            public void onPreciseDataConnectionStateChanged(android.telephony.PreciseDataConnectionState preciseDataConnectionState) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.IPhoneStateListener.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(preciseDataConnectionState, 0);
                    this.mRemote.transact(13, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneStateListener
            public void onDataConnectionRealTimeInfoChanged(android.telephony.DataConnectionRealTimeInfo dataConnectionRealTimeInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.IPhoneStateListener.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(dataConnectionRealTimeInfo, 0);
                    this.mRemote.transact(14, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneStateListener
            public void onSrvccStateChanged(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.IPhoneStateListener.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(15, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneStateListener
            public void onVoiceActivationStateChanged(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.IPhoneStateListener.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(16, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneStateListener
            public void onDataActivationStateChanged(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.IPhoneStateListener.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(17, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneStateListener
            public void onOemHookRawEvent(byte[] bArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.IPhoneStateListener.Stub.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(18, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneStateListener
            public void onCarrierNetworkChange(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.IPhoneStateListener.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(19, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneStateListener
            public void onUserMobileDataStateChanged(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.IPhoneStateListener.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(20, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneStateListener
            public void onDisplayInfoChanged(android.telephony.TelephonyDisplayInfo telephonyDisplayInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.IPhoneStateListener.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(telephonyDisplayInfo, 0);
                    this.mRemote.transact(21, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneStateListener
            public void onPhoneCapabilityChanged(android.telephony.PhoneCapability phoneCapability) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.IPhoneStateListener.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(phoneCapability, 0);
                    this.mRemote.transact(22, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneStateListener
            public void onActiveDataSubIdChanged(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.IPhoneStateListener.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(23, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneStateListener
            public void onRadioPowerStateChanged(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.IPhoneStateListener.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(24, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneStateListener
            public void onCallStatesChanged(java.util.List<android.telephony.CallState> list) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.IPhoneStateListener.Stub.DESCRIPTOR);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(25, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneStateListener
            public void onEmergencyNumberListChanged(java.util.Map map) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.IPhoneStateListener.Stub.DESCRIPTOR);
                    obtain.writeMap(map);
                    this.mRemote.transact(26, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneStateListener
            public void onOutgoingEmergencyCall(android.telephony.emergency.EmergencyNumber emergencyNumber, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.IPhoneStateListener.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(emergencyNumber, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(27, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneStateListener
            public void onOutgoingEmergencySms(android.telephony.emergency.EmergencyNumber emergencyNumber, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.IPhoneStateListener.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(emergencyNumber, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(28, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneStateListener
            public void onCallDisconnectCauseChanged(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.IPhoneStateListener.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(29, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneStateListener
            public void onImsCallDisconnectCauseChanged(android.telephony.ims.ImsReasonInfo imsReasonInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.IPhoneStateListener.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(imsReasonInfo, 0);
                    this.mRemote.transact(30, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneStateListener
            public void onRegistrationFailed(android.telephony.CellIdentity cellIdentity, java.lang.String str, int i, int i2, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.IPhoneStateListener.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(cellIdentity, 0);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(31, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneStateListener
            public void onBarringInfoChanged(android.telephony.BarringInfo barringInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.IPhoneStateListener.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(barringInfo, 0);
                    this.mRemote.transact(32, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneStateListener
            public void onPhysicalChannelConfigChanged(java.util.List<android.telephony.PhysicalChannelConfig> list) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.IPhoneStateListener.Stub.DESCRIPTOR);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(33, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneStateListener
            public void onDataEnabledChanged(boolean z, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.IPhoneStateListener.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(34, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneStateListener
            public void onAllowedNetworkTypesChanged(int i, long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.IPhoneStateListener.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    this.mRemote.transact(35, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneStateListener
            public void onLinkCapacityEstimateChanged(java.util.List<android.telephony.LinkCapacityEstimate> list) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.IPhoneStateListener.Stub.DESCRIPTOR);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(36, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneStateListener
            public void onMediaQualityStatusChanged(android.telephony.ims.MediaQualityStatus mediaQualityStatus) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.IPhoneStateListener.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(mediaQualityStatus, 0);
                    this.mRemote.transact(37, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneStateListener
            public void onCallBackModeStarted(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.IPhoneStateListener.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(38, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneStateListener
            public void onCallBackModeStopped(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.IPhoneStateListener.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(39, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.IPhoneStateListener
            public void onSimultaneousCallingStateChanged(int[] iArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telephony.IPhoneStateListener.Stub.DESCRIPTOR);
                    obtain.writeIntArray(iArr);
                    this.mRemote.transact(40, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 39;
        }
    }
}
