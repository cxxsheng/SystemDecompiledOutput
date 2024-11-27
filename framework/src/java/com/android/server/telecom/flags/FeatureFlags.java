package com.android.server.telecom.flags;

/* loaded from: classes5.dex */
public interface FeatureFlags {
    boolean addCallUriForMissedCalls();

    boolean associatedUserRefactorForWorkProfile();

    boolean availableRoutesNeverUpdatedAfterSetSystemAudioState();

    boolean businessCallComposer();

    boolean callAudioCommunicationDeviceRefactor();

    boolean callDetailsIdChanges();

    boolean clearCommunicationDeviceAfterAudioOpsComplete();

    boolean communicationDeviceProtectedByLock();

    boolean earlyBindingToIncallService();

    boolean eccKeyguard();

    boolean enableCallSequencing();

    boolean ensureAudioModeUpdatesOnForegroundCallChange();

    boolean fixAudioFlickerForOutgoingCalls();

    boolean genAnomReportOnFocusTimeout();

    boolean getLastKnownCellIdentity();

    boolean getRegisteredPhoneAccounts();

    boolean ignoreAutoRouteToWatchDevice();

    boolean isNewOutgoingCallBroadcastUnblocking();

    boolean onlyUpdateTelephonyOnValidSubIds();

    boolean profileUserSupport();

    boolean resetMuteWhenEnteringQuiescentBtRoute();

    boolean separatelyBindToBtIncallService();

    boolean setAudioModeBeforeAbandonFocus();

    boolean setMuteState();

    boolean skipFilterPhoneAccountPerformDndFilter();

    boolean telecomLogExternalWearableCalls();

    boolean telecomResolveHiddenDependencies();

    boolean telecomSkipLogBasedOnExtra();

    boolean telephonyHasDefaultButTelecomDoesNot();

    boolean transactionalCsVerifier();

    boolean transactionalVideoState();

    boolean transitRouteBeforeAudioDisconnectBt();

    boolean updateRouteMaskWhenBtConnected();

    boolean updatedRcsCallCountTracking();

    boolean useActualAddressToEnterConnectingState();

    boolean useDeviceProvidedSerializedRingerVibration();

    boolean useImprovedListenerOrder();

    boolean useRefactoredAudioRouteSwitching();

    boolean voipAppActionsSupport();
}
