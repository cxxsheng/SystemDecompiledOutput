package android.telephony;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public class TelephonyRegistryManager {
    private static final long LISTEN_CODE_CHANGE = 147600208;
    public static final int SIM_ACTIVATION_TYPE_DATA = 1;
    public static final int SIM_ACTIVATION_TYPE_VOICE = 0;
    private static final java.lang.String TAG = "TelephonyRegistryManager";
    private static final java.util.WeakHashMap<android.telephony.TelephonyManager.CarrierPrivilegesCallback, java.lang.ref.WeakReference<android.telephony.TelephonyRegistryManager.CarrierPrivilegesCallbackWrapper>> sCarrierPrivilegeCallbacks = new java.util.WeakHashMap<>();
    private static com.android.internal.telephony.ITelephonyRegistry sRegistry;
    private final android.content.Context mContext;
    private final java.util.concurrent.ConcurrentHashMap<android.telephony.SubscriptionManager.OnSubscriptionsChangedListener, com.android.internal.telephony.IOnSubscriptionsChangedListener> mSubscriptionChangedListenerMap = new java.util.concurrent.ConcurrentHashMap<>();
    private final java.util.concurrent.ConcurrentHashMap<android.telephony.SubscriptionManager.OnOpportunisticSubscriptionsChangedListener, com.android.internal.telephony.IOnSubscriptionsChangedListener> mOpportunisticSubscriptionChangedListenerMap = new java.util.concurrent.ConcurrentHashMap<>();
    private final java.util.concurrent.ConcurrentHashMap<android.telephony.CarrierConfigManager.CarrierConfigChangeListener, com.android.internal.telephony.ICarrierConfigChangeListener> mCarrierConfigChangeListenerMap = new java.util.concurrent.ConcurrentHashMap<>();

    public TelephonyRegistryManager(android.content.Context context) {
        this.mContext = context;
        if (sRegistry == null) {
            sRegistry = com.android.internal.telephony.ITelephonyRegistry.Stub.asInterface(android.os.ServiceManager.getService("telephony.registry"));
        }
    }

    public void addOnSubscriptionsChangedListener(android.telephony.SubscriptionManager.OnSubscriptionsChangedListener onSubscriptionsChangedListener, java.util.concurrent.Executor executor) {
        if (this.mSubscriptionChangedListenerMap.get(onSubscriptionsChangedListener) != null) {
            android.util.Log.d(TAG, "addOnSubscriptionsChangedListener listener already present");
            return;
        }
        android.telephony.TelephonyRegistryManager.AnonymousClass1 anonymousClass1 = new android.telephony.TelephonyRegistryManager.AnonymousClass1(executor, onSubscriptionsChangedListener);
        this.mSubscriptionChangedListenerMap.put(onSubscriptionsChangedListener, anonymousClass1);
        try {
            sRegistry.addOnSubscriptionsChangedListener(this.mContext.getOpPackageName(), this.mContext.getAttributionTag(), anonymousClass1);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    /* renamed from: android.telephony.TelephonyRegistryManager$1, reason: invalid class name */
    class AnonymousClass1 extends com.android.internal.telephony.IOnSubscriptionsChangedListener.Stub {
        final /* synthetic */ java.util.concurrent.Executor val$executor;
        final /* synthetic */ android.telephony.SubscriptionManager.OnSubscriptionsChangedListener val$listener;

        AnonymousClass1(java.util.concurrent.Executor executor, android.telephony.SubscriptionManager.OnSubscriptionsChangedListener onSubscriptionsChangedListener) {
            this.val$executor = executor;
            this.val$listener = onSubscriptionsChangedListener;
        }

        @Override // com.android.internal.telephony.IOnSubscriptionsChangedListener
        public void onSubscriptionsChanged() {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                java.util.concurrent.Executor executor = this.val$executor;
                final android.telephony.SubscriptionManager.OnSubscriptionsChangedListener onSubscriptionsChangedListener = this.val$listener;
                executor.execute(new java.lang.Runnable() { // from class: android.telephony.TelephonyRegistryManager$1$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.telephony.SubscriptionManager.OnSubscriptionsChangedListener.this.onSubscriptionsChanged();
                    }
                });
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public void removeOnSubscriptionsChangedListener(android.telephony.SubscriptionManager.OnSubscriptionsChangedListener onSubscriptionsChangedListener) {
        if (this.mSubscriptionChangedListenerMap.get(onSubscriptionsChangedListener) == null) {
            return;
        }
        try {
            sRegistry.removeOnSubscriptionsChangedListener(this.mContext.getOpPackageName(), this.mSubscriptionChangedListenerMap.get(onSubscriptionsChangedListener));
            this.mSubscriptionChangedListenerMap.remove(onSubscriptionsChangedListener);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void addOnOpportunisticSubscriptionsChangedListener(android.telephony.SubscriptionManager.OnOpportunisticSubscriptionsChangedListener onOpportunisticSubscriptionsChangedListener, java.util.concurrent.Executor executor) {
        if (this.mOpportunisticSubscriptionChangedListenerMap.get(onOpportunisticSubscriptionsChangedListener) != null) {
            android.util.Log.d(TAG, "addOnOpportunisticSubscriptionsChangedListener listener already present");
            return;
        }
        android.telephony.TelephonyRegistryManager.AnonymousClass2 anonymousClass2 = new android.telephony.TelephonyRegistryManager.AnonymousClass2(executor, onOpportunisticSubscriptionsChangedListener);
        this.mOpportunisticSubscriptionChangedListenerMap.put(onOpportunisticSubscriptionsChangedListener, anonymousClass2);
        try {
            sRegistry.addOnOpportunisticSubscriptionsChangedListener(this.mContext.getOpPackageName(), this.mContext.getAttributionTag(), anonymousClass2);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    /* renamed from: android.telephony.TelephonyRegistryManager$2, reason: invalid class name */
    class AnonymousClass2 extends com.android.internal.telephony.IOnSubscriptionsChangedListener.Stub {
        final /* synthetic */ java.util.concurrent.Executor val$executor;
        final /* synthetic */ android.telephony.SubscriptionManager.OnOpportunisticSubscriptionsChangedListener val$listener;

        AnonymousClass2(java.util.concurrent.Executor executor, android.telephony.SubscriptionManager.OnOpportunisticSubscriptionsChangedListener onOpportunisticSubscriptionsChangedListener) {
            this.val$executor = executor;
            this.val$listener = onOpportunisticSubscriptionsChangedListener;
        }

        @Override // com.android.internal.telephony.IOnSubscriptionsChangedListener
        public void onSubscriptionsChanged() {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                android.util.Log.d(android.telephony.TelephonyRegistryManager.TAG, "onOpportunisticSubscriptionsChanged callback received.");
                java.util.concurrent.Executor executor = this.val$executor;
                final android.telephony.SubscriptionManager.OnOpportunisticSubscriptionsChangedListener onOpportunisticSubscriptionsChangedListener = this.val$listener;
                executor.execute(new java.lang.Runnable() { // from class: android.telephony.TelephonyRegistryManager$2$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.telephony.SubscriptionManager.OnOpportunisticSubscriptionsChangedListener.this.onOpportunisticSubscriptionsChanged();
                    }
                });
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public void removeOnOpportunisticSubscriptionsChangedListener(android.telephony.SubscriptionManager.OnOpportunisticSubscriptionsChangedListener onOpportunisticSubscriptionsChangedListener) {
        if (this.mOpportunisticSubscriptionChangedListenerMap.get(onOpportunisticSubscriptionsChangedListener) == null) {
            return;
        }
        try {
            sRegistry.removeOnSubscriptionsChangedListener(this.mContext.getOpPackageName(), this.mOpportunisticSubscriptionChangedListenerMap.get(onOpportunisticSubscriptionsChangedListener));
            this.mOpportunisticSubscriptionChangedListenerMap.remove(onOpportunisticSubscriptionsChangedListener);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void listenFromListener(int i, boolean z, boolean z2, java.lang.String str, java.lang.String str2, android.telephony.PhoneStateListener phoneStateListener, int i2, boolean z3) {
        int intValue;
        if (phoneStateListener == null) {
            throw new java.lang.IllegalStateException("telephony service is null.");
        }
        try {
            int[] array = getEventsFromBitmask(i2).stream().mapToInt(new java.util.function.ToIntFunction() { // from class: android.telephony.TelephonyRegistryManager$$ExternalSyntheticLambda2
                @Override // java.util.function.ToIntFunction
                public final int applyAsInt(java.lang.Object obj) {
                    int intValue2;
                    intValue2 = ((java.lang.Integer) obj).intValue();
                    return intValue2;
                }
            }).toArray();
            if (android.compat.Compatibility.isChangeEnabled(LISTEN_CODE_CHANGE)) {
                phoneStateListener.mSubId = java.lang.Integer.valueOf(array.length == 0 ? -1 : i);
            } else if (phoneStateListener.mSubId != null) {
                intValue = phoneStateListener.mSubId.intValue();
                sRegistry.listenWithEventList(z, z2, intValue, str, str2, phoneStateListener.callback, array, z3);
            }
            intValue = i;
            sRegistry.listenWithEventList(z, z2, intValue, str, str2, phoneStateListener.callback, array, z3);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private void listenFromCallback(boolean z, boolean z2, int i, java.lang.String str, java.lang.String str2, android.telephony.TelephonyCallback telephonyCallback, int[] iArr, boolean z3) {
        try {
            sRegistry.listenWithEventList(z, z2, i, str, str2, telephonyCallback.callback, iArr, z3);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void notifyCarrierNetworkChange(boolean z) {
        try {
            sRegistry.notifyCarrierNetworkChange(z);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void notifyCarrierNetworkChange(int i, boolean z) {
        try {
            sRegistry.notifyCarrierNetworkChangeWithSubId(i, z);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void notifyCallStateChanged(int i, int i2, int i3, java.lang.String str) {
        try {
            sRegistry.notifyCallState(i, i2, i3, str);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public void notifyCallStateChangedForAllSubscriptions(int i, java.lang.String str) {
        try {
            sRegistry.notifyCallStateForAllSubs(i, str);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void notifySubscriptionInfoChanged() {
        try {
            sRegistry.notifySubscriptionInfoChanged();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void notifyOpportunisticSubscriptionInfoChanged() {
        try {
            sRegistry.notifyOpportunisticSubscriptionInfoChanged();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void notifyServiceStateChanged(int i, int i2, android.telephony.ServiceState serviceState) {
        try {
            sRegistry.notifyServiceStateForPhoneId(i, i2, serviceState);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void notifySignalStrengthChanged(int i, int i2, android.telephony.SignalStrength signalStrength) {
        try {
            sRegistry.notifySignalStrengthForPhoneId(i, i2, signalStrength);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void notifyMessageWaitingChanged(int i, int i2, boolean z) {
        try {
            sRegistry.notifyMessageWaitingChangedForPhoneId(i, i2, z);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void notifyCallForwardingChanged(int i, boolean z) {
        try {
            sRegistry.notifyCallForwardingChangedForSubscriber(i, z);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void notifyDataActivityChanged(int i, int i2) {
        try {
            sRegistry.notifyDataActivityForSubscriber(i, i2);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void notifyDataActivityChanged(int i, int i2, int i3) {
        try {
            sRegistry.notifyDataActivityForSubscriberWithSlot(i, i2, i3);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void notifyDataConnectionForSubscriber(int i, int i2, android.telephony.PreciseDataConnectionState preciseDataConnectionState) {
        try {
            sRegistry.notifyDataConnectionForSubscriber(i, i2, preciseDataConnectionState);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void notifyCallQualityChanged(int i, int i2, android.telephony.CallQuality callQuality, int i3) {
        try {
            sRegistry.notifyCallQualityChanged(callQuality, i, i2, i3);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void notifyMediaQualityStatusChanged(int i, int i2, android.telephony.ims.MediaQualityStatus mediaQualityStatus) {
        try {
            sRegistry.notifyMediaQualityStatusChanged(i, i2, mediaQualityStatus);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void notifyEmergencyNumberList(int i, int i2) {
        try {
            sRegistry.notifyEmergencyNumberList(i, i2);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public void notifyOutgoingEmergencyCall(int i, int i2, android.telephony.emergency.EmergencyNumber emergencyNumber) {
        try {
            sRegistry.notifyOutgoingEmergencyCall(i, i2, emergencyNumber);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void notifyOutgoingEmergencySms(int i, int i2, android.telephony.emergency.EmergencyNumber emergencyNumber) {
        try {
            sRegistry.notifyOutgoingEmergencySms(i, i2, emergencyNumber);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void notifyRadioPowerStateChanged(int i, int i2, int i3) {
        try {
            sRegistry.notifyRadioPowerStateChanged(i, i2, i3);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void notifyPhoneCapabilityChanged(android.telephony.PhoneCapability phoneCapability) {
        try {
            sRegistry.notifyPhoneCapabilityChanged(phoneCapability);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void notifyDataActivationStateChanged(int i, int i2, int i3) {
        try {
            sRegistry.notifySimActivationStateChangedForPhoneId(i, i2, 1, i3);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void notifyVoiceActivationStateChanged(int i, int i2, int i3) {
        try {
            sRegistry.notifySimActivationStateChangedForPhoneId(i, i2, 0, i3);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void notifyUserMobileDataStateChanged(int i, int i2, boolean z) {
        try {
            sRegistry.notifyUserMobileDataStateChangedForPhoneId(i, i2, z);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void notifyDisplayInfoChanged(int i, int i2, android.telephony.TelephonyDisplayInfo telephonyDisplayInfo) {
        try {
            sRegistry.notifyDisplayInfoChanged(i, i2, telephonyDisplayInfo);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void notifyImsDisconnectCause(int i, android.telephony.ims.ImsReasonInfo imsReasonInfo) {
        try {
            sRegistry.notifyImsDisconnectCause(i, imsReasonInfo);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void notifySrvccStateChanged(int i, int i2) {
        try {
            sRegistry.notifySrvccStateChanged(i, i2);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void notifyPreciseCallState(int i, int i2, int[] iArr, java.lang.String[] strArr, int[] iArr2, int[] iArr3) {
        try {
            sRegistry.notifyPreciseCallState(i, i2, iArr, strArr, iArr2, iArr3);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void notifyDisconnectCause(int i, int i2, int i3, int i4) {
        try {
            sRegistry.notifyDisconnectCause(i, i2, i3, i4);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void notifyCellLocation(int i, android.telephony.CellIdentity cellIdentity) {
        try {
            sRegistry.notifyCellLocationForSubscriber(i, cellIdentity);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void notifyCellInfoChanged(int i, java.util.List<android.telephony.CellInfo> list) {
        try {
            sRegistry.notifyCellInfoForSubscriber(i, list);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void notifyActiveDataSubIdChanged(int i) {
        try {
            sRegistry.notifyActiveDataSubIdChanged(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void notifyRegistrationFailed(int i, int i2, android.telephony.CellIdentity cellIdentity, java.lang.String str, int i3, int i4, int i5) {
        try {
            sRegistry.notifyRegistrationFailed(i, i2, cellIdentity, str, i3, i4, i5);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void notifyBarringInfoChanged(int i, int i2, android.telephony.BarringInfo barringInfo) {
        try {
            sRegistry.notifyBarringInfoChanged(i, i2, barringInfo);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void notifyPhysicalChannelConfigForSubscriber(int i, int i2, java.util.List<android.telephony.PhysicalChannelConfig> list) {
        try {
            sRegistry.notifyPhysicalChannelConfigForSubscriber(i, i2, list);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void notifyDataEnabled(int i, int i2, boolean z, int i3) {
        try {
            sRegistry.notifyDataEnabled(i, i2, z, i3);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void notifyAllowedNetworkTypesChanged(int i, int i2, int i3, long j) {
        try {
            sRegistry.notifyAllowedNetworkTypesChanged(i, i2, i3, j);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void notifyLinkCapacityEstimateChanged(int i, int i2, java.util.List<android.telephony.LinkCapacityEstimate> list) {
        try {
            sRegistry.notifyLinkCapacityEstimateChanged(i, i2, list);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void notifySimultaneousCellularCallingSubscriptionsChanged(java.util.Set<java.lang.Integer> set) {
        try {
            sRegistry.notifySimultaneousCellularCallingSubscriptionsChanged(set.stream().mapToInt(new java.util.function.ToIntFunction() { // from class: android.telephony.TelephonyRegistryManager$$ExternalSyntheticLambda1
                @Override // java.util.function.ToIntFunction
                public final int applyAsInt(java.lang.Object obj) {
                    int intValue;
                    intValue = ((java.lang.Integer) obj).intValue();
                    return intValue;
                }
            }).toArray());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public java.util.Set<java.lang.Integer> getEventsFromCallback(android.telephony.TelephonyCallback telephonyCallback) {
        android.util.ArraySet arraySet = new android.util.ArraySet();
        if (telephonyCallback instanceof android.telephony.TelephonyCallback.ServiceStateListener) {
            arraySet.add(1);
        }
        if (telephonyCallback instanceof android.telephony.TelephonyCallback.MessageWaitingIndicatorListener) {
            arraySet.add(3);
        }
        if (telephonyCallback instanceof android.telephony.TelephonyCallback.CallForwardingIndicatorListener) {
            arraySet.add(4);
        }
        if (telephonyCallback instanceof android.telephony.TelephonyCallback.CellLocationListener) {
            arraySet.add(5);
        }
        if (telephonyCallback instanceof android.telephony.TelephonyCallback.CallStateListener) {
            arraySet.add(6);
        }
        if (telephonyCallback instanceof android.telephony.TelephonyCallback.DataConnectionStateListener) {
            arraySet.add(7);
        }
        if (telephonyCallback instanceof android.telephony.TelephonyCallback.DataActivityListener) {
            arraySet.add(8);
        }
        if (telephonyCallback instanceof android.telephony.TelephonyCallback.SignalStrengthsListener) {
            arraySet.add(9);
        }
        if (telephonyCallback instanceof android.telephony.TelephonyCallback.CellInfoListener) {
            arraySet.add(11);
        }
        if (telephonyCallback instanceof android.telephony.TelephonyCallback.PreciseCallStateListener) {
            arraySet.add(12);
        }
        if (telephonyCallback instanceof android.telephony.TelephonyCallback.CallDisconnectCauseListener) {
            arraySet.add(26);
        }
        if (telephonyCallback instanceof android.telephony.TelephonyCallback.ImsCallDisconnectCauseListener) {
            arraySet.add(28);
        }
        if (telephonyCallback instanceof android.telephony.TelephonyCallback.PreciseDataConnectionStateListener) {
            arraySet.add(13);
        }
        if (telephonyCallback instanceof android.telephony.TelephonyCallback.SrvccStateListener) {
            arraySet.add(16);
        }
        if (telephonyCallback instanceof android.telephony.TelephonyCallback.VoiceActivationStateListener) {
            arraySet.add(18);
        }
        if (telephonyCallback instanceof android.telephony.TelephonyCallback.DataActivationStateListener) {
            arraySet.add(19);
        }
        if (telephonyCallback instanceof android.telephony.TelephonyCallback.UserMobileDataStateListener) {
            arraySet.add(20);
        }
        if (telephonyCallback instanceof android.telephony.TelephonyCallback.DisplayInfoListener) {
            arraySet.add(21);
        }
        if (telephonyCallback instanceof android.telephony.TelephonyCallback.EmergencyNumberListListener) {
            arraySet.add(25);
        }
        if (telephonyCallback instanceof android.telephony.TelephonyCallback.OutgoingEmergencyCallListener) {
            arraySet.add(29);
        }
        if (telephonyCallback instanceof android.telephony.TelephonyCallback.OutgoingEmergencySmsListener) {
            arraySet.add(30);
        }
        if (telephonyCallback instanceof android.telephony.TelephonyCallback.PhoneCapabilityListener) {
            arraySet.add(22);
        }
        if (telephonyCallback instanceof android.telephony.TelephonyCallback.ActiveDataSubscriptionIdListener) {
            arraySet.add(23);
        }
        if (telephonyCallback instanceof android.telephony.TelephonyCallback.RadioPowerStateListener) {
            arraySet.add(24);
        }
        if (telephonyCallback instanceof android.telephony.TelephonyCallback.CarrierNetworkListener) {
            arraySet.add(17);
        }
        if (telephonyCallback instanceof android.telephony.TelephonyCallback.RegistrationFailedListener) {
            arraySet.add(31);
        }
        if (telephonyCallback instanceof android.telephony.TelephonyCallback.CallAttributesListener) {
            arraySet.add(27);
        }
        if (telephonyCallback instanceof android.telephony.TelephonyCallback.BarringInfoListener) {
            arraySet.add(32);
        }
        if (telephonyCallback instanceof android.telephony.TelephonyCallback.PhysicalChannelConfigListener) {
            arraySet.add(33);
        }
        if (telephonyCallback instanceof android.telephony.TelephonyCallback.DataEnabledListener) {
            arraySet.add(34);
        }
        if (telephonyCallback instanceof android.telephony.TelephonyCallback.AllowedNetworkTypesListener) {
            arraySet.add(35);
        }
        if (telephonyCallback instanceof android.telephony.TelephonyCallback.LinkCapacityEstimateChangedListener) {
            arraySet.add(37);
        }
        if (telephonyCallback instanceof android.telephony.TelephonyCallback.MediaQualityStatusChangedListener) {
            arraySet.add(39);
        }
        if (telephonyCallback instanceof android.telephony.TelephonyCallback.EmergencyCallbackModeListener) {
            arraySet.add(40);
        }
        if (telephonyCallback instanceof android.telephony.TelephonyCallback.SimultaneousCellularCallingSupportListener) {
            arraySet.add(41);
        }
        return arraySet;
    }

    private java.util.Set<java.lang.Integer> getEventsFromBitmask(int i) {
        android.util.ArraySet arraySet = new android.util.ArraySet();
        if ((i & 1) != 0) {
            arraySet.add(1);
        }
        if ((i & 2) != 0) {
            arraySet.add(2);
        }
        if ((i & 4) != 0) {
            arraySet.add(3);
        }
        if ((i & 8) != 0) {
            arraySet.add(4);
        }
        if ((i & 16) != 0) {
            arraySet.add(5);
        }
        if ((i & 32) != 0) {
            arraySet.add(36);
        }
        if ((i & 64) != 0) {
            arraySet.add(7);
        }
        if ((i & 128) != 0) {
            arraySet.add(8);
        }
        if ((i & 256) != 0) {
            arraySet.add(9);
        }
        if ((i & 1024) != 0) {
            arraySet.add(11);
        }
        if ((i & 2048) != 0) {
            arraySet.add(12);
        }
        if ((i & 4096) != 0) {
            arraySet.add(13);
        }
        if ((i & 8192) != 0) {
            arraySet.add(14);
        }
        if ((32768 & i) != 0) {
            arraySet.add(15);
        }
        if ((i & 16384) != 0) {
            arraySet.add(16);
        }
        if ((65536 & i) != 0) {
            arraySet.add(17);
        }
        if ((131072 & i) != 0) {
            arraySet.add(18);
        }
        if ((262144 & i) != 0) {
            arraySet.add(19);
        }
        if ((524288 & i) != 0) {
            arraySet.add(20);
        }
        if ((1048576 & i) != 0) {
            arraySet.add(21);
        }
        if ((2097152 & i) != 0) {
            arraySet.add(22);
        }
        if ((4194304 & i) != 0) {
            arraySet.add(23);
        }
        if ((8388608 & i) != 0) {
            arraySet.add(24);
        }
        if ((16777216 & i) != 0) {
            arraySet.add(25);
        }
        if ((33554432 & i) != 0) {
            arraySet.add(26);
        }
        if ((67108864 & i) != 0) {
            arraySet.add(27);
        }
        if ((134217728 & i) != 0) {
            arraySet.add(28);
        }
        if ((268435456 & i) != 0) {
            arraySet.add(29);
        }
        if ((536870912 & i) != 0) {
            arraySet.add(30);
        }
        if ((1073741824 & i) != 0) {
            arraySet.add(31);
        }
        if ((i & Integer.MIN_VALUE) != 0) {
            arraySet.add(32);
        }
        return arraySet;
    }

    public void registerTelephonyCallback(boolean z, boolean z2, java.util.concurrent.Executor executor, int i, java.lang.String str, java.lang.String str2, android.telephony.TelephonyCallback telephonyCallback, boolean z3) {
        if (telephonyCallback != null) {
            telephonyCallback.init(executor);
            listenFromCallback(z, z2, i, str, str2, telephonyCallback, getEventsFromCallback(telephonyCallback).stream().mapToInt(new java.util.function.ToIntFunction() { // from class: android.telephony.TelephonyRegistryManager$$ExternalSyntheticLambda0
                @Override // java.util.function.ToIntFunction
                public final int applyAsInt(java.lang.Object obj) {
                    int intValue;
                    intValue = ((java.lang.Integer) obj).intValue();
                    return intValue;
                }
            }).toArray(), z3);
            return;
        }
        throw new java.lang.IllegalStateException("telephony service is null.");
    }

    public void unregisterTelephonyCallback(int i, java.lang.String str, java.lang.String str2, android.telephony.TelephonyCallback telephonyCallback, boolean z) {
        listenFromCallback(false, false, i, str, str2, telephonyCallback, new int[0], z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class CarrierPrivilegesCallbackWrapper extends com.android.internal.telephony.ICarrierPrivilegesCallback.Stub implements com.android.internal.listeners.ListenerExecutor {
        private final java.lang.ref.WeakReference<android.telephony.TelephonyManager.CarrierPrivilegesCallback> mCallback;
        private final java.util.concurrent.Executor mExecutor;

        CarrierPrivilegesCallbackWrapper(android.telephony.TelephonyManager.CarrierPrivilegesCallback carrierPrivilegesCallback, java.util.concurrent.Executor executor) {
            this.mCallback = new java.lang.ref.WeakReference<>(carrierPrivilegesCallback);
            this.mExecutor = executor;
        }

        @Override // com.android.internal.telephony.ICarrierPrivilegesCallback
        public void onCarrierPrivilegesChanged(java.util.List<java.lang.String> list, int[] iArr) {
            final java.util.Set copyOf = java.util.Set.copyOf(list);
            final java.util.Set set = (java.util.Set) java.util.Arrays.stream(iArr).boxed().collect(java.util.stream.Collectors.toSet());
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyRegistryManager$CarrierPrivilegesCallbackWrapper$$ExternalSyntheticLambda4
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    android.telephony.TelephonyRegistryManager.CarrierPrivilegesCallbackWrapper.this.lambda$onCarrierPrivilegesChanged$1(copyOf, set);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCarrierPrivilegesChanged$1(final java.util.Set set, final java.util.Set set2) throws java.lang.Exception {
            java.util.concurrent.Executor executor = this.mExecutor;
            java.lang.ref.WeakReference<android.telephony.TelephonyManager.CarrierPrivilegesCallback> weakReference = this.mCallback;
            java.util.Objects.requireNonNull(weakReference);
            executeSafely(executor, new android.telephony.TelephonyRegistryManager$CarrierPrivilegesCallbackWrapper$$ExternalSyntheticLambda1(weakReference), new com.android.internal.listeners.ListenerExecutor.ListenerOperation() { // from class: android.telephony.TelephonyRegistryManager$CarrierPrivilegesCallbackWrapper$$ExternalSyntheticLambda3
                @Override // com.android.internal.listeners.ListenerExecutor.ListenerOperation
                public final void operate(java.lang.Object obj) {
                    ((android.telephony.TelephonyManager.CarrierPrivilegesCallback) obj).onCarrierPrivilegesChanged(set, set2);
                }
            });
        }

        @Override // com.android.internal.telephony.ICarrierPrivilegesCallback
        public void onCarrierServiceChanged(final java.lang.String str, final int i) {
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyRegistryManager$CarrierPrivilegesCallbackWrapper$$ExternalSyntheticLambda0
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    android.telephony.TelephonyRegistryManager.CarrierPrivilegesCallbackWrapper.this.lambda$onCarrierServiceChanged$3(str, i);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCarrierServiceChanged$3(final java.lang.String str, final int i) throws java.lang.Exception {
            java.util.concurrent.Executor executor = this.mExecutor;
            java.lang.ref.WeakReference<android.telephony.TelephonyManager.CarrierPrivilegesCallback> weakReference = this.mCallback;
            java.util.Objects.requireNonNull(weakReference);
            executeSafely(executor, new android.telephony.TelephonyRegistryManager$CarrierPrivilegesCallbackWrapper$$ExternalSyntheticLambda1(weakReference), new com.android.internal.listeners.ListenerExecutor.ListenerOperation() { // from class: android.telephony.TelephonyRegistryManager$CarrierPrivilegesCallbackWrapper$$ExternalSyntheticLambda2
                @Override // com.android.internal.listeners.ListenerExecutor.ListenerOperation
                public final void operate(java.lang.Object obj) {
                    ((android.telephony.TelephonyManager.CarrierPrivilegesCallback) obj).onCarrierServiceChanged(str, i);
                }
            });
        }
    }

    public void addCarrierPrivilegesCallback(int i, java.util.concurrent.Executor executor, android.telephony.TelephonyManager.CarrierPrivilegesCallback carrierPrivilegesCallback) {
        if (carrierPrivilegesCallback == null || executor == null) {
            throw new java.lang.IllegalArgumentException("callback and executor must be non-null");
        }
        synchronized (sCarrierPrivilegeCallbacks) {
            java.lang.ref.WeakReference<android.telephony.TelephonyRegistryManager.CarrierPrivilegesCallbackWrapper> weakReference = sCarrierPrivilegeCallbacks.get(carrierPrivilegesCallback);
            if (weakReference != null && weakReference.get() != null) {
                android.util.Log.d(TAG, "addCarrierPrivilegesCallback: callback already registered");
                return;
            }
            android.telephony.TelephonyRegistryManager.CarrierPrivilegesCallbackWrapper carrierPrivilegesCallbackWrapper = new android.telephony.TelephonyRegistryManager.CarrierPrivilegesCallbackWrapper(carrierPrivilegesCallback, executor);
            sCarrierPrivilegeCallbacks.put(carrierPrivilegesCallback, new java.lang.ref.WeakReference<>(carrierPrivilegesCallbackWrapper));
            try {
                sRegistry.addCarrierPrivilegesCallback(i, carrierPrivilegesCallbackWrapper, this.mContext.getOpPackageName(), this.mContext.getAttributionTag());
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void removeCarrierPrivilegesCallback(android.telephony.TelephonyManager.CarrierPrivilegesCallback carrierPrivilegesCallback) {
        if (carrierPrivilegesCallback == null) {
            throw new java.lang.IllegalArgumentException("listener must be non-null");
        }
        synchronized (sCarrierPrivilegeCallbacks) {
            java.lang.ref.WeakReference<android.telephony.TelephonyRegistryManager.CarrierPrivilegesCallbackWrapper> remove = sCarrierPrivilegeCallbacks.remove(carrierPrivilegesCallback);
            if (remove == null) {
                return;
            }
            android.telephony.TelephonyRegistryManager.CarrierPrivilegesCallbackWrapper carrierPrivilegesCallbackWrapper = remove.get();
            if (carrierPrivilegesCallbackWrapper == null) {
                return;
            }
            try {
                sRegistry.removeCarrierPrivilegesCallback(carrierPrivilegesCallbackWrapper, this.mContext.getOpPackageName());
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void notifyCarrierPrivilegesChanged(int i, java.util.Set<java.lang.String> set, java.util.Set<java.lang.Integer> set2) {
        if (set == null || set2 == null) {
            throw new java.lang.IllegalArgumentException("privilegedPackageNames and privilegedUids must be non-null");
        }
        try {
            sRegistry.notifyCarrierPrivilegesChanged(i, java.util.List.copyOf(set), set2.stream().mapToInt(new android.media.AudioPort$$ExternalSyntheticLambda0()).toArray());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void notifyCarrierServiceChanged(int i, java.lang.String str, int i2) {
        try {
            sRegistry.notifyCarrierServiceChanged(i, str, i2);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void addCarrierConfigChangedListener(java.util.concurrent.Executor executor, android.telephony.CarrierConfigManager.CarrierConfigChangeListener carrierConfigChangeListener) {
        java.util.Objects.requireNonNull(executor, "Executor should be non-null.");
        java.util.Objects.requireNonNull(carrierConfigChangeListener, "Listener should be non-null.");
        if (this.mCarrierConfigChangeListenerMap.get(carrierConfigChangeListener) != null) {
            android.util.Log.e(TAG, "registerCarrierConfigChangeListener: listener already present");
            return;
        }
        android.telephony.TelephonyRegistryManager.AnonymousClass3 anonymousClass3 = new android.telephony.TelephonyRegistryManager.AnonymousClass3(executor, carrierConfigChangeListener);
        try {
            sRegistry.addCarrierConfigChangeListener(anonymousClass3, this.mContext.getOpPackageName(), this.mContext.getAttributionTag());
            this.mCarrierConfigChangeListenerMap.put(carrierConfigChangeListener, anonymousClass3);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    /* renamed from: android.telephony.TelephonyRegistryManager$3, reason: invalid class name */
    class AnonymousClass3 extends com.android.internal.telephony.ICarrierConfigChangeListener.Stub {
        final /* synthetic */ java.util.concurrent.Executor val$executor;
        final /* synthetic */ android.telephony.CarrierConfigManager.CarrierConfigChangeListener val$listener;

        AnonymousClass3(java.util.concurrent.Executor executor, android.telephony.CarrierConfigManager.CarrierConfigChangeListener carrierConfigChangeListener) {
            this.val$executor = executor;
            this.val$listener = carrierConfigChangeListener;
        }

        @Override // com.android.internal.telephony.ICarrierConfigChangeListener
        public void onCarrierConfigChanged(final int i, final int i2, final int i3, final int i4) {
            android.util.Log.d(android.telephony.TelephonyRegistryManager.TAG, "onCarrierConfigChanged call in ICarrierConfigChangeListener callback");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                java.util.concurrent.Executor executor = this.val$executor;
                final android.telephony.CarrierConfigManager.CarrierConfigChangeListener carrierConfigChangeListener = this.val$listener;
                executor.execute(new java.lang.Runnable() { // from class: android.telephony.TelephonyRegistryManager$3$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.telephony.CarrierConfigManager.CarrierConfigChangeListener.this.onCarrierConfigChanged(i, i2, i3, i4);
                    }
                });
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public void removeCarrierConfigChangedListener(android.telephony.CarrierConfigManager.CarrierConfigChangeListener carrierConfigChangeListener) {
        java.util.Objects.requireNonNull(carrierConfigChangeListener, "Listener should be non-null.");
        if (this.mCarrierConfigChangeListenerMap.get(carrierConfigChangeListener) == null) {
            android.util.Log.e(TAG, "removeCarrierConfigChangedListener: listener was not present");
            return;
        }
        try {
            sRegistry.removeCarrierConfigChangeListener(this.mCarrierConfigChangeListenerMap.get(carrierConfigChangeListener), this.mContext.getOpPackageName());
            this.mCarrierConfigChangeListenerMap.remove(carrierConfigChangeListener);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void notifyCarrierConfigChanged(int i, int i2, int i3, int i4) {
        if (!android.telephony.SubscriptionManager.isValidPhoneId(i)) {
            android.util.Log.e(TAG, "notifyCarrierConfigChanged, ignored: invalid slotIndex " + i);
            return;
        }
        try {
            sRegistry.notifyCarrierConfigChanged(i, i2, i3, i4);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void notifyCallBackModeStarted(int i, int i2, int i3) {
        try {
            android.util.Log.d(TAG, "notifyCallBackModeStarted:type=" + i3);
            sRegistry.notifyCallbackModeStarted(i, i2, i3);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void notifyCallbackModeStopped(int i, int i2, int i3, int i4) {
        try {
            android.util.Log.d(TAG, "notifyCallbackModeStopped:type=" + i3 + ", reason=" + i4);
            sRegistry.notifyCallbackModeStopped(i, i2, i3, i4);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
