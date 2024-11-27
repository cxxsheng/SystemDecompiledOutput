package com.android.server.telecom.flags;

/* loaded from: classes5.dex */
public final class FeatureFlagsImpl implements com.android.server.telecom.flags.FeatureFlags {
    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean addCallUriForMissedCalls() {
        return true;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean associatedUserRefactorForWorkProfile() {
        return false;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean availableRoutesNeverUpdatedAfterSetSystemAudioState() {
        return true;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean businessCallComposer() {
        return false;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean callAudioCommunicationDeviceRefactor() {
        return false;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean callDetailsIdChanges() {
        return false;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean clearCommunicationDeviceAfterAudioOpsComplete() {
        return false;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean communicationDeviceProtectedByLock() {
        return false;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean earlyBindingToIncallService() {
        return true;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean eccKeyguard() {
        return true;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean enableCallSequencing() {
        return false;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean ensureAudioModeUpdatesOnForegroundCallChange() {
        return false;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean fixAudioFlickerForOutgoingCalls() {
        return false;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean genAnomReportOnFocusTimeout() {
        return false;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean getLastKnownCellIdentity() {
        return false;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean getRegisteredPhoneAccounts() {
        return false;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean ignoreAutoRouteToWatchDevice() {
        return true;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean isNewOutgoingCallBroadcastUnblocking() {
        return false;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean onlyUpdateTelephonyOnValidSubIds() {
        return false;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean profileUserSupport() {
        return false;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean resetMuteWhenEnteringQuiescentBtRoute() {
        return false;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean separatelyBindToBtIncallService() {
        return false;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean setAudioModeBeforeAbandonFocus() {
        return false;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean setMuteState() {
        return false;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean skipFilterPhoneAccountPerformDndFilter() {
        return true;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean telecomLogExternalWearableCalls() {
        return true;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean telecomResolveHiddenDependencies() {
        return false;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean telecomSkipLogBasedOnExtra() {
        return true;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean telephonyHasDefaultButTelecomDoesNot() {
        return false;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean transactionalCsVerifier() {
        return false;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean transactionalVideoState() {
        return false;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean transitRouteBeforeAudioDisconnectBt() {
        return false;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean updateRouteMaskWhenBtConnected() {
        return false;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean updatedRcsCallCountTracking() {
        return false;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean useActualAddressToEnterConnectingState() {
        return false;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean useDeviceProvidedSerializedRingerVibration() {
        return true;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean useImprovedListenerOrder() {
        return false;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean useRefactoredAudioRouteSwitching() {
        return false;
    }

    @Override // com.android.server.telecom.flags.FeatureFlags
    public boolean voipAppActionsSupport() {
        return false;
    }
}
