package com.android.server.telecom.flags;

/* loaded from: classes5.dex */
public class FakeFeatureFlagsImpl implements com.android.server.telecom.flags.FeatureFlags {
    private java.util.Map<java.lang.String, java.lang.Boolean> mFlagMap = new java.util.HashMap(java.util.Map.ofEntries(java.util.Map.entry(com.android.server.telecom.flags.Flags.FLAG_ADD_CALL_URI_FOR_MISSED_CALLS, false), java.util.Map.entry(com.android.server.telecom.flags.Flags.FLAG_ASSOCIATED_USER_REFACTOR_FOR_WORK_PROFILE, false), java.util.Map.entry(com.android.server.telecom.flags.Flags.FLAG_AVAILABLE_ROUTES_NEVER_UPDATED_AFTER_SET_SYSTEM_AUDIO_STATE, false), java.util.Map.entry(com.android.server.telecom.flags.Flags.FLAG_BUSINESS_CALL_COMPOSER, false), java.util.Map.entry(com.android.server.telecom.flags.Flags.FLAG_CALL_AUDIO_COMMUNICATION_DEVICE_REFACTOR, false), java.util.Map.entry(com.android.server.telecom.flags.Flags.FLAG_CALL_DETAILS_ID_CHANGES, false), java.util.Map.entry(com.android.server.telecom.flags.Flags.FLAG_CLEAR_COMMUNICATION_DEVICE_AFTER_AUDIO_OPS_COMPLETE, false), java.util.Map.entry(com.android.server.telecom.flags.Flags.FLAG_COMMUNICATION_DEVICE_PROTECTED_BY_LOCK, false), java.util.Map.entry(com.android.server.telecom.flags.Flags.FLAG_EARLY_BINDING_TO_INCALL_SERVICE, false), java.util.Map.entry(com.android.server.telecom.flags.Flags.FLAG_ECC_KEYGUARD, false), java.util.Map.entry(com.android.server.telecom.flags.Flags.FLAG_ENABLE_CALL_SEQUENCING, false), java.util.Map.entry(com.android.server.telecom.flags.Flags.FLAG_ENSURE_AUDIO_MODE_UPDATES_ON_FOREGROUND_CALL_CHANGE, false), java.util.Map.entry(com.android.server.telecom.flags.Flags.FLAG_FIX_AUDIO_FLICKER_FOR_OUTGOING_CALLS, false), java.util.Map.entry(com.android.server.telecom.flags.Flags.FLAG_GEN_ANOM_REPORT_ON_FOCUS_TIMEOUT, false), java.util.Map.entry(com.android.server.telecom.flags.Flags.FLAG_GET_LAST_KNOWN_CELL_IDENTITY, false), java.util.Map.entry(com.android.server.telecom.flags.Flags.FLAG_GET_REGISTERED_PHONE_ACCOUNTS, false), java.util.Map.entry(com.android.server.telecom.flags.Flags.FLAG_IGNORE_AUTO_ROUTE_TO_WATCH_DEVICE, false), java.util.Map.entry(com.android.server.telecom.flags.Flags.FLAG_IS_NEW_OUTGOING_CALL_BROADCAST_UNBLOCKING, false), java.util.Map.entry(com.android.server.telecom.flags.Flags.FLAG_ONLY_UPDATE_TELEPHONY_ON_VALID_SUB_IDS, false), java.util.Map.entry(com.android.server.telecom.flags.Flags.FLAG_PROFILE_USER_SUPPORT, false), java.util.Map.entry(com.android.server.telecom.flags.Flags.FLAG_RESET_MUTE_WHEN_ENTERING_QUIESCENT_BT_ROUTE, false), java.util.Map.entry(com.android.server.telecom.flags.Flags.FLAG_SEPARATELY_BIND_TO_BT_INCALL_SERVICE, false), java.util.Map.entry(com.android.server.telecom.flags.Flags.FLAG_SET_AUDIO_MODE_BEFORE_ABANDON_FOCUS, false), java.util.Map.entry(com.android.server.telecom.flags.Flags.FLAG_SET_MUTE_STATE, false), java.util.Map.entry(com.android.server.telecom.flags.Flags.FLAG_SKIP_FILTER_PHONE_ACCOUNT_PERFORM_DND_FILTER, false), java.util.Map.entry(com.android.server.telecom.flags.Flags.FLAG_TELECOM_LOG_EXTERNAL_WEARABLE_CALLS, false), java.util.Map.entry(com.android.server.telecom.flags.Flags.FLAG_TELECOM_RESOLVE_HIDDEN_DEPENDENCIES, false), java.util.Map.entry(com.android.server.telecom.flags.Flags.FLAG_TELECOM_SKIP_LOG_BASED_ON_EXTRA, false), java.util.Map.entry(com.android.server.telecom.flags.Flags.FLAG_TELEPHONY_HAS_DEFAULT_BUT_TELECOM_DOES_NOT, false), java.util.Map.entry(com.android.server.telecom.flags.Flags.FLAG_TRANSACTIONAL_CS_VERIFIER, false), java.util.Map.entry(com.android.server.telecom.flags.Flags.FLAG_TRANSACTIONAL_VIDEO_STATE, false), java.util.Map.entry(com.android.server.telecom.flags.Flags.FLAG_TRANSIT_ROUTE_BEFORE_AUDIO_DISCONNECT_BT, false), java.util.Map.entry(com.android.server.telecom.flags.Flags.FLAG_UPDATE_ROUTE_MASK_WHEN_BT_CONNECTED, false), java.util.Map.entry(com.android.server.telecom.flags.Flags.FLAG_UPDATED_RCS_CALL_COUNT_TRACKING, false), java.util.Map.entry(com.android.server.telecom.flags.Flags.FLAG_USE_ACTUAL_ADDRESS_TO_ENTER_CONNECTING_STATE, false), java.util.Map.entry(com.android.server.telecom.flags.Flags.FLAG_USE_DEVICE_PROVIDED_SERIALIZED_RINGER_VIBRATION, false), java.util.Map.entry(com.android.server.telecom.flags.Flags.FLAG_USE_IMPROVED_LISTENER_ORDER, false), java.util.Map.entry(com.android.server.telecom.flags.Flags.FLAG_USE_REFACTORED_AUDIO_ROUTE_SWITCHING, false), java.util.Map.entry(com.android.server.telecom.flags.Flags.FLAG_VOIP_APP_ACTIONS_SUPPORT, false)));
    private java.util.Set<java.lang.String> mReadOnlyFlagsSet = new java.util.HashSet(java.util.Arrays.asList(com.android.server.telecom.flags.Flags.FLAG_ADD_CALL_URI_FOR_MISSED_CALLS, com.android.server.telecom.flags.Flags.FLAG_ASSOCIATED_USER_REFACTOR_FOR_WORK_PROFILE, com.android.server.telecom.flags.Flags.FLAG_AVAILABLE_ROUTES_NEVER_UPDATED_AFTER_SET_SYSTEM_AUDIO_STATE, com.android.server.telecom.flags.Flags.FLAG_BUSINESS_CALL_COMPOSER, com.android.server.telecom.flags.Flags.FLAG_CALL_AUDIO_COMMUNICATION_DEVICE_REFACTOR, com.android.server.telecom.flags.Flags.FLAG_CALL_DETAILS_ID_CHANGES, com.android.server.telecom.flags.Flags.FLAG_CLEAR_COMMUNICATION_DEVICE_AFTER_AUDIO_OPS_COMPLETE, com.android.server.telecom.flags.Flags.FLAG_COMMUNICATION_DEVICE_PROTECTED_BY_LOCK, com.android.server.telecom.flags.Flags.FLAG_EARLY_BINDING_TO_INCALL_SERVICE, com.android.server.telecom.flags.Flags.FLAG_ECC_KEYGUARD, com.android.server.telecom.flags.Flags.FLAG_ENABLE_CALL_SEQUENCING, com.android.server.telecom.flags.Flags.FLAG_ENSURE_AUDIO_MODE_UPDATES_ON_FOREGROUND_CALL_CHANGE, com.android.server.telecom.flags.Flags.FLAG_FIX_AUDIO_FLICKER_FOR_OUTGOING_CALLS, com.android.server.telecom.flags.Flags.FLAG_GEN_ANOM_REPORT_ON_FOCUS_TIMEOUT, com.android.server.telecom.flags.Flags.FLAG_GET_LAST_KNOWN_CELL_IDENTITY, com.android.server.telecom.flags.Flags.FLAG_GET_REGISTERED_PHONE_ACCOUNTS, com.android.server.telecom.flags.Flags.FLAG_IGNORE_AUTO_ROUTE_TO_WATCH_DEVICE, com.android.server.telecom.flags.Flags.FLAG_IS_NEW_OUTGOING_CALL_BROADCAST_UNBLOCKING, com.android.server.telecom.flags.Flags.FLAG_ONLY_UPDATE_TELEPHONY_ON_VALID_SUB_IDS, com.android.server.telecom.flags.Flags.FLAG_PROFILE_USER_SUPPORT, com.android.server.telecom.flags.Flags.FLAG_RESET_MUTE_WHEN_ENTERING_QUIESCENT_BT_ROUTE, com.android.server.telecom.flags.Flags.FLAG_SEPARATELY_BIND_TO_BT_INCALL_SERVICE, com.android.server.telecom.flags.Flags.FLAG_SET_AUDIO_MODE_BEFORE_ABANDON_FOCUS, com.android.server.telecom.flags.Flags.FLAG_SET_MUTE_STATE, com.android.server.telecom.flags.Flags.FLAG_SKIP_FILTER_PHONE_ACCOUNT_PERFORM_DND_FILTER, com.android.server.telecom.flags.Flags.FLAG_TELECOM_LOG_EXTERNAL_WEARABLE_CALLS, com.android.server.telecom.flags.Flags.FLAG_TELECOM_RESOLVE_HIDDEN_DEPENDENCIES, com.android.server.telecom.flags.Flags.FLAG_TELECOM_SKIP_LOG_BASED_ON_EXTRA, com.android.server.telecom.flags.Flags.FLAG_TELEPHONY_HAS_DEFAULT_BUT_TELECOM_DOES_NOT, com.android.server.telecom.flags.Flags.FLAG_TRANSACTIONAL_CS_VERIFIER, com.android.server.telecom.flags.Flags.FLAG_TRANSACTIONAL_VIDEO_STATE, com.android.server.telecom.flags.Flags.FLAG_TRANSIT_ROUTE_BEFORE_AUDIO_DISCONNECT_BT, com.android.server.telecom.flags.Flags.FLAG_UPDATE_ROUTE_MASK_WHEN_BT_CONNECTED, com.android.server.telecom.flags.Flags.FLAG_UPDATED_RCS_CALL_COUNT_TRACKING, com.android.server.telecom.flags.Flags.FLAG_USE_ACTUAL_ADDRESS_TO_ENTER_CONNECTING_STATE, com.android.server.telecom.flags.Flags.FLAG_USE_DEVICE_PROVIDED_SERIALIZED_RINGER_VIBRATION, com.android.server.telecom.flags.Flags.FLAG_USE_IMPROVED_LISTENER_ORDER, com.android.server.telecom.flags.Flags.FLAG_USE_REFACTORED_AUDIO_ROUTE_SWITCHING, com.android.server.telecom.flags.Flags.FLAG_VOIP_APP_ACTIONS_SUPPORT, ""));

    public FakeFeatureFlagsImpl() {
        resetAll();
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean addCallUriForMissedCalls() {
        return getValue(com.android.server.telecom.flags.Flags.FLAG_ADD_CALL_URI_FOR_MISSED_CALLS);
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean associatedUserRefactorForWorkProfile() {
        return getValue(com.android.server.telecom.flags.Flags.FLAG_ASSOCIATED_USER_REFACTOR_FOR_WORK_PROFILE);
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean availableRoutesNeverUpdatedAfterSetSystemAudioState() {
        return getValue(com.android.server.telecom.flags.Flags.FLAG_AVAILABLE_ROUTES_NEVER_UPDATED_AFTER_SET_SYSTEM_AUDIO_STATE);
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean businessCallComposer() {
        return getValue(com.android.server.telecom.flags.Flags.FLAG_BUSINESS_CALL_COMPOSER);
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean callAudioCommunicationDeviceRefactor() {
        return getValue(com.android.server.telecom.flags.Flags.FLAG_CALL_AUDIO_COMMUNICATION_DEVICE_REFACTOR);
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean callDetailsIdChanges() {
        return getValue(com.android.server.telecom.flags.Flags.FLAG_CALL_DETAILS_ID_CHANGES);
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean clearCommunicationDeviceAfterAudioOpsComplete() {
        return getValue(com.android.server.telecom.flags.Flags.FLAG_CLEAR_COMMUNICATION_DEVICE_AFTER_AUDIO_OPS_COMPLETE);
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean communicationDeviceProtectedByLock() {
        return getValue(com.android.server.telecom.flags.Flags.FLAG_COMMUNICATION_DEVICE_PROTECTED_BY_LOCK);
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean earlyBindingToIncallService() {
        return getValue(com.android.server.telecom.flags.Flags.FLAG_EARLY_BINDING_TO_INCALL_SERVICE);
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean eccKeyguard() {
        return getValue(com.android.server.telecom.flags.Flags.FLAG_ECC_KEYGUARD);
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean enableCallSequencing() {
        return getValue(com.android.server.telecom.flags.Flags.FLAG_ENABLE_CALL_SEQUENCING);
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean ensureAudioModeUpdatesOnForegroundCallChange() {
        return getValue(com.android.server.telecom.flags.Flags.FLAG_ENSURE_AUDIO_MODE_UPDATES_ON_FOREGROUND_CALL_CHANGE);
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean fixAudioFlickerForOutgoingCalls() {
        return getValue(com.android.server.telecom.flags.Flags.FLAG_FIX_AUDIO_FLICKER_FOR_OUTGOING_CALLS);
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean genAnomReportOnFocusTimeout() {
        return getValue(com.android.server.telecom.flags.Flags.FLAG_GEN_ANOM_REPORT_ON_FOCUS_TIMEOUT);
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean getLastKnownCellIdentity() {
        return getValue(com.android.server.telecom.flags.Flags.FLAG_GET_LAST_KNOWN_CELL_IDENTITY);
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean getRegisteredPhoneAccounts() {
        return getValue(com.android.server.telecom.flags.Flags.FLAG_GET_REGISTERED_PHONE_ACCOUNTS);
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean ignoreAutoRouteToWatchDevice() {
        return getValue(com.android.server.telecom.flags.Flags.FLAG_IGNORE_AUTO_ROUTE_TO_WATCH_DEVICE);
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean isNewOutgoingCallBroadcastUnblocking() {
        return getValue(com.android.server.telecom.flags.Flags.FLAG_IS_NEW_OUTGOING_CALL_BROADCAST_UNBLOCKING);
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean onlyUpdateTelephonyOnValidSubIds() {
        return getValue(com.android.server.telecom.flags.Flags.FLAG_ONLY_UPDATE_TELEPHONY_ON_VALID_SUB_IDS);
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean profileUserSupport() {
        return getValue(com.android.server.telecom.flags.Flags.FLAG_PROFILE_USER_SUPPORT);
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean resetMuteWhenEnteringQuiescentBtRoute() {
        return getValue(com.android.server.telecom.flags.Flags.FLAG_RESET_MUTE_WHEN_ENTERING_QUIESCENT_BT_ROUTE);
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean separatelyBindToBtIncallService() {
        return getValue(com.android.server.telecom.flags.Flags.FLAG_SEPARATELY_BIND_TO_BT_INCALL_SERVICE);
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean setAudioModeBeforeAbandonFocus() {
        return getValue(com.android.server.telecom.flags.Flags.FLAG_SET_AUDIO_MODE_BEFORE_ABANDON_FOCUS);
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean setMuteState() {
        return getValue(com.android.server.telecom.flags.Flags.FLAG_SET_MUTE_STATE);
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean skipFilterPhoneAccountPerformDndFilter() {
        return getValue(com.android.server.telecom.flags.Flags.FLAG_SKIP_FILTER_PHONE_ACCOUNT_PERFORM_DND_FILTER);
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean telecomLogExternalWearableCalls() {
        return getValue(com.android.server.telecom.flags.Flags.FLAG_TELECOM_LOG_EXTERNAL_WEARABLE_CALLS);
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean telecomResolveHiddenDependencies() {
        return getValue(com.android.server.telecom.flags.Flags.FLAG_TELECOM_RESOLVE_HIDDEN_DEPENDENCIES);
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean telecomSkipLogBasedOnExtra() {
        return getValue(com.android.server.telecom.flags.Flags.FLAG_TELECOM_SKIP_LOG_BASED_ON_EXTRA);
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean telephonyHasDefaultButTelecomDoesNot() {
        return getValue(com.android.server.telecom.flags.Flags.FLAG_TELEPHONY_HAS_DEFAULT_BUT_TELECOM_DOES_NOT);
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean transactionalCsVerifier() {
        return getValue(com.android.server.telecom.flags.Flags.FLAG_TRANSACTIONAL_CS_VERIFIER);
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean transactionalVideoState() {
        return getValue(com.android.server.telecom.flags.Flags.FLAG_TRANSACTIONAL_VIDEO_STATE);
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean transitRouteBeforeAudioDisconnectBt() {
        return getValue(com.android.server.telecom.flags.Flags.FLAG_TRANSIT_ROUTE_BEFORE_AUDIO_DISCONNECT_BT);
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean updateRouteMaskWhenBtConnected() {
        return getValue(com.android.server.telecom.flags.Flags.FLAG_UPDATE_ROUTE_MASK_WHEN_BT_CONNECTED);
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean updatedRcsCallCountTracking() {
        return getValue(com.android.server.telecom.flags.Flags.FLAG_UPDATED_RCS_CALL_COUNT_TRACKING);
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean useActualAddressToEnterConnectingState() {
        return getValue(com.android.server.telecom.flags.Flags.FLAG_USE_ACTUAL_ADDRESS_TO_ENTER_CONNECTING_STATE);
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean useDeviceProvidedSerializedRingerVibration() {
        return getValue(com.android.server.telecom.flags.Flags.FLAG_USE_DEVICE_PROVIDED_SERIALIZED_RINGER_VIBRATION);
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean useImprovedListenerOrder() {
        return getValue(com.android.server.telecom.flags.Flags.FLAG_USE_IMPROVED_LISTENER_ORDER);
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean useRefactoredAudioRouteSwitching() {
        return getValue(com.android.server.telecom.flags.Flags.FLAG_USE_REFACTORED_AUDIO_ROUTE_SWITCHING);
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean voipAppActionsSupport() {
        return getValue(com.android.server.telecom.flags.Flags.FLAG_VOIP_APP_ACTIONS_SUPPORT);
    }

    public void setFlag(java.lang.String str, boolean z) {
        if (!this.mFlagMap.containsKey(str)) {
            throw new java.lang.IllegalArgumentException("no such flag " + str);
        }
        this.mFlagMap.put(str, java.lang.Boolean.valueOf(z));
    }

    public void resetAll() {
        java.util.Iterator<java.util.Map.Entry<java.lang.String, java.lang.Boolean>> it = this.mFlagMap.entrySet().iterator();
        while (it.hasNext()) {
            it.next().setValue(null);
        }
    }

    public boolean isFlagReadOnlyOptimized(java.lang.String str) {
        if (this.mReadOnlyFlagsSet.contains(str) && isOptimizationEnabled()) {
            return true;
        }
        return false;
    }

    private boolean getValue(java.lang.String str) {
        java.lang.Boolean bool = this.mFlagMap.get(str);
        if (bool == null) {
            throw new java.lang.IllegalArgumentException(str + " is not set");
        }
        return bool.booleanValue();
    }

    private boolean isOptimizationEnabled() {
        return false;
    }
}
