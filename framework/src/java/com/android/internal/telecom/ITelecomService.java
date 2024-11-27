package com.android.internal.telecom;

/* loaded from: classes5.dex */
public interface ITelecomService extends android.os.IInterface {
    void acceptHandover(android.net.Uri uri, int i, android.telecom.PhoneAccountHandle phoneAccountHandle, java.lang.String str) throws android.os.RemoteException;

    void acceptRingingCall(java.lang.String str) throws android.os.RemoteException;

    void acceptRingingCallWithVideoState(java.lang.String str, int i) throws android.os.RemoteException;

    void addCall(android.telecom.CallAttributes callAttributes, com.android.internal.telecom.ICallEventCallback iCallEventCallback, java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    void addNewIncomingCall(android.telecom.PhoneAccountHandle phoneAccountHandle, android.os.Bundle bundle, java.lang.String str) throws android.os.RemoteException;

    void addNewIncomingConference(android.telecom.PhoneAccountHandle phoneAccountHandle, android.os.Bundle bundle, java.lang.String str) throws android.os.RemoteException;

    void addNewUnknownCall(android.telecom.PhoneAccountHandle phoneAccountHandle, android.os.Bundle bundle) throws android.os.RemoteException;

    void addOrRemoveTestCallCompanionApp(java.lang.String str, boolean z) throws android.os.RemoteException;

    void cancelMissedCallsNotification(java.lang.String str) throws android.os.RemoteException;

    int cleanupOrphanPhoneAccounts() throws android.os.RemoteException;

    void cleanupStuckCalls() throws android.os.RemoteException;

    void clearAccounts(java.lang.String str) throws android.os.RemoteException;

    android.content.Intent createLaunchEmergencyDialerIntent(java.lang.String str) throws android.os.RemoteException;

    android.content.Intent createManageBlockedNumbersIntent(java.lang.String str) throws android.os.RemoteException;

    android.telecom.TelecomAnalytics dumpCallAnalytics() throws android.os.RemoteException;

    boolean enablePhoneAccount(android.telecom.PhoneAccountHandle phoneAccountHandle, boolean z) throws android.os.RemoteException;

    boolean endCall(java.lang.String str) throws android.os.RemoteException;

    android.net.Uri getAdnUriForPhoneAccount(android.telecom.PhoneAccountHandle phoneAccountHandle, java.lang.String str) throws android.os.RemoteException;

    android.content.pm.ParceledListSlice<android.telecom.PhoneAccountHandle> getAllPhoneAccountHandles() throws android.os.RemoteException;

    android.content.pm.ParceledListSlice<android.telecom.PhoneAccount> getAllPhoneAccounts() throws android.os.RemoteException;

    int getAllPhoneAccountsCount() throws android.os.RemoteException;

    android.content.pm.ParceledListSlice<android.telecom.PhoneAccountHandle> getCallCapablePhoneAccounts(boolean z, java.lang.String str, java.lang.String str2, boolean z2) throws android.os.RemoteException;

    int getCallState() throws android.os.RemoteException;

    int getCallStateUsingPackage(java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    int getCurrentTtyMode(java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    java.lang.String getDefaultDialerPackage(java.lang.String str) throws android.os.RemoteException;

    java.lang.String getDefaultDialerPackageForUser(int i) throws android.os.RemoteException;

    android.telecom.PhoneAccountHandle getDefaultOutgoingPhoneAccount(java.lang.String str, java.lang.String str2, java.lang.String str3) throws android.os.RemoteException;

    android.content.ComponentName getDefaultPhoneApp() throws android.os.RemoteException;

    java.lang.String getLine1Number(android.telecom.PhoneAccountHandle phoneAccountHandle, java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    android.content.pm.ParceledListSlice<android.telecom.PhoneAccountHandle> getOwnSelfManagedPhoneAccounts(java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    android.telecom.PhoneAccount getPhoneAccount(android.telecom.PhoneAccountHandle phoneAccountHandle, java.lang.String str) throws android.os.RemoteException;

    android.content.pm.ParceledListSlice<android.telecom.PhoneAccountHandle> getPhoneAccountsForPackage(java.lang.String str) throws android.os.RemoteException;

    android.content.pm.ParceledListSlice<android.telecom.PhoneAccountHandle> getPhoneAccountsSupportingScheme(java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    android.content.pm.ParceledListSlice<android.telecom.PhoneAccount> getRegisteredPhoneAccounts(java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    android.content.pm.ParceledListSlice<android.telecom.PhoneAccountHandle> getSelfManagedPhoneAccounts(java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    android.telecom.PhoneAccountHandle getSimCallManager(int i, java.lang.String str) throws android.os.RemoteException;

    android.telecom.PhoneAccountHandle getSimCallManagerForUser(int i, java.lang.String str) throws android.os.RemoteException;

    java.lang.String getSystemDialerPackage(java.lang.String str) throws android.os.RemoteException;

    android.telecom.PhoneAccountHandle getUserSelectedOutgoingPhoneAccount(java.lang.String str) throws android.os.RemoteException;

    java.lang.String getVoiceMailNumber(android.telecom.PhoneAccountHandle phoneAccountHandle, java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    void handleCallIntent(android.content.Intent intent, java.lang.String str) throws android.os.RemoteException;

    boolean handlePinMmi(java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    boolean handlePinMmiForPhoneAccount(android.telecom.PhoneAccountHandle phoneAccountHandle, java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    boolean hasManageOngoingCallsPermission(java.lang.String str) throws android.os.RemoteException;

    boolean isInCall(java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    boolean isInEmergencyCall() throws android.os.RemoteException;

    boolean isInManagedCall(java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    boolean isInSelfManagedCall(java.lang.String str, android.os.UserHandle userHandle, java.lang.String str2, boolean z) throws android.os.RemoteException;

    boolean isIncomingCallPermitted(android.telecom.PhoneAccountHandle phoneAccountHandle, java.lang.String str) throws android.os.RemoteException;

    boolean isNonUiInCallServiceBound(java.lang.String str) throws android.os.RemoteException;

    boolean isOutgoingCallPermitted(android.telecom.PhoneAccountHandle phoneAccountHandle, java.lang.String str) throws android.os.RemoteException;

    boolean isRinging(java.lang.String str) throws android.os.RemoteException;

    boolean isTtySupported(java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    boolean isVoiceMailNumber(android.telecom.PhoneAccountHandle phoneAccountHandle, java.lang.String str, java.lang.String str2, java.lang.String str3) throws android.os.RemoteException;

    void placeCall(android.net.Uri uri, android.os.Bundle bundle, java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    void registerPhoneAccount(android.telecom.PhoneAccount phoneAccount, java.lang.String str) throws android.os.RemoteException;

    void requestLogMark(java.lang.String str) throws android.os.RemoteException;

    void resetCarMode() throws android.os.RemoteException;

    boolean setDefaultDialer(java.lang.String str) throws android.os.RemoteException;

    void setSystemDialer(android.content.ComponentName componentName) throws android.os.RemoteException;

    void setTestCallDiagnosticService(java.lang.String str) throws android.os.RemoteException;

    void setTestDefaultCallRedirectionApp(java.lang.String str) throws android.os.RemoteException;

    void setTestDefaultCallScreeningApp(java.lang.String str) throws android.os.RemoteException;

    void setTestDefaultDialer(java.lang.String str) throws android.os.RemoteException;

    void setTestEmergencyPhoneAccountPackageNameFilter(java.lang.String str) throws android.os.RemoteException;

    void setTestPhoneAcctSuggestionComponent(java.lang.String str) throws android.os.RemoteException;

    void setUserSelectedOutgoingPhoneAccount(android.telecom.PhoneAccountHandle phoneAccountHandle) throws android.os.RemoteException;

    void showInCallScreen(boolean z, java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    void silenceRinger(java.lang.String str) throws android.os.RemoteException;

    void startConference(java.util.List<android.net.Uri> list, android.os.Bundle bundle, java.lang.String str) throws android.os.RemoteException;

    void stopBlockSuppression() throws android.os.RemoteException;

    void unregisterPhoneAccount(android.telecom.PhoneAccountHandle phoneAccountHandle, java.lang.String str) throws android.os.RemoteException;

    void waitOnHandlers() throws android.os.RemoteException;

    public static class Default implements com.android.internal.telecom.ITelecomService {
        @Override // com.android.internal.telecom.ITelecomService
        public void showInCallScreen(boolean z, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.ITelecomService
        public android.telecom.PhoneAccountHandle getDefaultOutgoingPhoneAccount(java.lang.String str, java.lang.String str2, java.lang.String str3) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telecom.ITelecomService
        public android.telecom.PhoneAccountHandle getUserSelectedOutgoingPhoneAccount(java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telecom.ITelecomService
        public void setUserSelectedOutgoingPhoneAccount(android.telecom.PhoneAccountHandle phoneAccountHandle) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.ITelecomService
        public android.content.pm.ParceledListSlice<android.telecom.PhoneAccountHandle> getCallCapablePhoneAccounts(boolean z, java.lang.String str, java.lang.String str2, boolean z2) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telecom.ITelecomService
        public android.content.pm.ParceledListSlice<android.telecom.PhoneAccountHandle> getSelfManagedPhoneAccounts(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telecom.ITelecomService
        public android.content.pm.ParceledListSlice<android.telecom.PhoneAccountHandle> getOwnSelfManagedPhoneAccounts(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telecom.ITelecomService
        public android.content.pm.ParceledListSlice<android.telecom.PhoneAccountHandle> getPhoneAccountsSupportingScheme(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telecom.ITelecomService
        public android.content.pm.ParceledListSlice<android.telecom.PhoneAccountHandle> getPhoneAccountsForPackage(java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telecom.ITelecomService
        public android.telecom.PhoneAccount getPhoneAccount(android.telecom.PhoneAccountHandle phoneAccountHandle, java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telecom.ITelecomService
        public android.content.pm.ParceledListSlice<android.telecom.PhoneAccount> getRegisteredPhoneAccounts(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telecom.ITelecomService
        public int getAllPhoneAccountsCount() throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.telecom.ITelecomService
        public android.content.pm.ParceledListSlice<android.telecom.PhoneAccount> getAllPhoneAccounts() throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telecom.ITelecomService
        public android.content.pm.ParceledListSlice<android.telecom.PhoneAccountHandle> getAllPhoneAccountHandles() throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telecom.ITelecomService
        public android.telecom.PhoneAccountHandle getSimCallManager(int i, java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telecom.ITelecomService
        public android.telecom.PhoneAccountHandle getSimCallManagerForUser(int i, java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telecom.ITelecomService
        public void registerPhoneAccount(android.telecom.PhoneAccount phoneAccount, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.ITelecomService
        public void unregisterPhoneAccount(android.telecom.PhoneAccountHandle phoneAccountHandle, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.ITelecomService
        public void clearAccounts(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.ITelecomService
        public boolean isVoiceMailNumber(android.telecom.PhoneAccountHandle phoneAccountHandle, java.lang.String str, java.lang.String str2, java.lang.String str3) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telecom.ITelecomService
        public java.lang.String getVoiceMailNumber(android.telecom.PhoneAccountHandle phoneAccountHandle, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telecom.ITelecomService
        public java.lang.String getLine1Number(android.telecom.PhoneAccountHandle phoneAccountHandle, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telecom.ITelecomService
        public android.content.ComponentName getDefaultPhoneApp() throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telecom.ITelecomService
        public java.lang.String getDefaultDialerPackage(java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telecom.ITelecomService
        public java.lang.String getDefaultDialerPackageForUser(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telecom.ITelecomService
        public java.lang.String getSystemDialerPackage(java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telecom.ITelecomService
        public android.telecom.TelecomAnalytics dumpCallAnalytics() throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telecom.ITelecomService
        public void silenceRinger(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.ITelecomService
        public boolean isInCall(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telecom.ITelecomService
        public boolean hasManageOngoingCallsPermission(java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telecom.ITelecomService
        public boolean isInManagedCall(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telecom.ITelecomService
        public boolean isRinging(java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telecom.ITelecomService
        public int getCallState() throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.telecom.ITelecomService
        public int getCallStateUsingPackage(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.telecom.ITelecomService
        public boolean endCall(java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telecom.ITelecomService
        public void acceptRingingCall(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.ITelecomService
        public void acceptRingingCallWithVideoState(java.lang.String str, int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.ITelecomService
        public void cancelMissedCallsNotification(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.ITelecomService
        public boolean handlePinMmi(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telecom.ITelecomService
        public boolean handlePinMmiForPhoneAccount(android.telecom.PhoneAccountHandle phoneAccountHandle, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telecom.ITelecomService
        public android.net.Uri getAdnUriForPhoneAccount(android.telecom.PhoneAccountHandle phoneAccountHandle, java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telecom.ITelecomService
        public boolean isTtySupported(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telecom.ITelecomService
        public int getCurrentTtyMode(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.telecom.ITelecomService
        public void addNewIncomingCall(android.telecom.PhoneAccountHandle phoneAccountHandle, android.os.Bundle bundle, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.ITelecomService
        public void addNewIncomingConference(android.telecom.PhoneAccountHandle phoneAccountHandle, android.os.Bundle bundle, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.ITelecomService
        public void addNewUnknownCall(android.telecom.PhoneAccountHandle phoneAccountHandle, android.os.Bundle bundle) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.ITelecomService
        public void startConference(java.util.List<android.net.Uri> list, android.os.Bundle bundle, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.ITelecomService
        public void placeCall(android.net.Uri uri, android.os.Bundle bundle, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.ITelecomService
        public boolean enablePhoneAccount(android.telecom.PhoneAccountHandle phoneAccountHandle, boolean z) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telecom.ITelecomService
        public boolean setDefaultDialer(java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telecom.ITelecomService
        public void stopBlockSuppression() throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.ITelecomService
        public android.content.Intent createManageBlockedNumbersIntent(java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telecom.ITelecomService
        public android.content.Intent createLaunchEmergencyDialerIntent(java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.telecom.ITelecomService
        public boolean isIncomingCallPermitted(android.telecom.PhoneAccountHandle phoneAccountHandle, java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telecom.ITelecomService
        public boolean isOutgoingCallPermitted(android.telecom.PhoneAccountHandle phoneAccountHandle, java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telecom.ITelecomService
        public void waitOnHandlers() throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.ITelecomService
        public void acceptHandover(android.net.Uri uri, int i, android.telecom.PhoneAccountHandle phoneAccountHandle, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.ITelecomService
        public void setTestEmergencyPhoneAccountPackageNameFilter(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.ITelecomService
        public boolean isInEmergencyCall() throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telecom.ITelecomService
        public void handleCallIntent(android.content.Intent intent, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.ITelecomService
        public void cleanupStuckCalls() throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.ITelecomService
        public int cleanupOrphanPhoneAccounts() throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.telecom.ITelecomService
        public boolean isNonUiInCallServiceBound(java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telecom.ITelecomService
        public void resetCarMode() throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.ITelecomService
        public void setTestDefaultCallRedirectionApp(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.ITelecomService
        public void requestLogMark(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.ITelecomService
        public void setTestPhoneAcctSuggestionComponent(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.ITelecomService
        public void setTestDefaultCallScreeningApp(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.ITelecomService
        public void addOrRemoveTestCallCompanionApp(java.lang.String str, boolean z) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.ITelecomService
        public void setSystemDialer(android.content.ComponentName componentName) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.ITelecomService
        public void setTestDefaultDialer(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.ITelecomService
        public void setTestCallDiagnosticService(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.ITelecomService
        public boolean isInSelfManagedCall(java.lang.String str, android.os.UserHandle userHandle, java.lang.String str2, boolean z) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.telecom.ITelecomService
        public void addCall(android.telecom.CallAttributes callAttributes, com.android.internal.telecom.ICallEventCallback iCallEventCallback, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements com.android.internal.telecom.ITelecomService {
        public static final java.lang.String DESCRIPTOR = "com.android.internal.telecom.ITelecomService";
        static final int TRANSACTION_acceptHandover = 57;
        static final int TRANSACTION_acceptRingingCall = 36;
        static final int TRANSACTION_acceptRingingCallWithVideoState = 37;
        static final int TRANSACTION_addCall = 74;
        static final int TRANSACTION_addNewIncomingCall = 44;
        static final int TRANSACTION_addNewIncomingConference = 45;
        static final int TRANSACTION_addNewUnknownCall = 46;
        static final int TRANSACTION_addOrRemoveTestCallCompanionApp = 69;
        static final int TRANSACTION_cancelMissedCallsNotification = 38;
        static final int TRANSACTION_cleanupOrphanPhoneAccounts = 62;
        static final int TRANSACTION_cleanupStuckCalls = 61;
        static final int TRANSACTION_clearAccounts = 19;
        static final int TRANSACTION_createLaunchEmergencyDialerIntent = 53;
        static final int TRANSACTION_createManageBlockedNumbersIntent = 52;
        static final int TRANSACTION_dumpCallAnalytics = 27;
        static final int TRANSACTION_enablePhoneAccount = 49;
        static final int TRANSACTION_endCall = 35;
        static final int TRANSACTION_getAdnUriForPhoneAccount = 41;
        static final int TRANSACTION_getAllPhoneAccountHandles = 14;
        static final int TRANSACTION_getAllPhoneAccounts = 13;
        static final int TRANSACTION_getAllPhoneAccountsCount = 12;
        static final int TRANSACTION_getCallCapablePhoneAccounts = 5;
        static final int TRANSACTION_getCallState = 33;
        static final int TRANSACTION_getCallStateUsingPackage = 34;
        static final int TRANSACTION_getCurrentTtyMode = 43;
        static final int TRANSACTION_getDefaultDialerPackage = 24;
        static final int TRANSACTION_getDefaultDialerPackageForUser = 25;
        static final int TRANSACTION_getDefaultOutgoingPhoneAccount = 2;
        static final int TRANSACTION_getDefaultPhoneApp = 23;
        static final int TRANSACTION_getLine1Number = 22;
        static final int TRANSACTION_getOwnSelfManagedPhoneAccounts = 7;
        static final int TRANSACTION_getPhoneAccount = 10;
        static final int TRANSACTION_getPhoneAccountsForPackage = 9;
        static final int TRANSACTION_getPhoneAccountsSupportingScheme = 8;
        static final int TRANSACTION_getRegisteredPhoneAccounts = 11;
        static final int TRANSACTION_getSelfManagedPhoneAccounts = 6;
        static final int TRANSACTION_getSimCallManager = 15;
        static final int TRANSACTION_getSimCallManagerForUser = 16;
        static final int TRANSACTION_getSystemDialerPackage = 26;
        static final int TRANSACTION_getUserSelectedOutgoingPhoneAccount = 3;
        static final int TRANSACTION_getVoiceMailNumber = 21;
        static final int TRANSACTION_handleCallIntent = 60;
        static final int TRANSACTION_handlePinMmi = 39;
        static final int TRANSACTION_handlePinMmiForPhoneAccount = 40;
        static final int TRANSACTION_hasManageOngoingCallsPermission = 30;
        static final int TRANSACTION_isInCall = 29;
        static final int TRANSACTION_isInEmergencyCall = 59;
        static final int TRANSACTION_isInManagedCall = 31;
        static final int TRANSACTION_isInSelfManagedCall = 73;
        static final int TRANSACTION_isIncomingCallPermitted = 54;
        static final int TRANSACTION_isNonUiInCallServiceBound = 63;
        static final int TRANSACTION_isOutgoingCallPermitted = 55;
        static final int TRANSACTION_isRinging = 32;
        static final int TRANSACTION_isTtySupported = 42;
        static final int TRANSACTION_isVoiceMailNumber = 20;
        static final int TRANSACTION_placeCall = 48;
        static final int TRANSACTION_registerPhoneAccount = 17;
        static final int TRANSACTION_requestLogMark = 66;
        static final int TRANSACTION_resetCarMode = 64;
        static final int TRANSACTION_setDefaultDialer = 50;
        static final int TRANSACTION_setSystemDialer = 70;
        static final int TRANSACTION_setTestCallDiagnosticService = 72;
        static final int TRANSACTION_setTestDefaultCallRedirectionApp = 65;
        static final int TRANSACTION_setTestDefaultCallScreeningApp = 68;
        static final int TRANSACTION_setTestDefaultDialer = 71;
        static final int TRANSACTION_setTestEmergencyPhoneAccountPackageNameFilter = 58;
        static final int TRANSACTION_setTestPhoneAcctSuggestionComponent = 67;
        static final int TRANSACTION_setUserSelectedOutgoingPhoneAccount = 4;
        static final int TRANSACTION_showInCallScreen = 1;
        static final int TRANSACTION_silenceRinger = 28;
        static final int TRANSACTION_startConference = 47;
        static final int TRANSACTION_stopBlockSuppression = 51;
        static final int TRANSACTION_unregisterPhoneAccount = 18;
        static final int TRANSACTION_waitOnHandlers = 56;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static com.android.internal.telecom.ITelecomService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof com.android.internal.telecom.ITelecomService)) {
                return (com.android.internal.telecom.ITelecomService) queryLocalInterface;
            }
            return new com.android.internal.telecom.ITelecomService.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "showInCallScreen";
                case 2:
                    return "getDefaultOutgoingPhoneAccount";
                case 3:
                    return "getUserSelectedOutgoingPhoneAccount";
                case 4:
                    return "setUserSelectedOutgoingPhoneAccount";
                case 5:
                    return "getCallCapablePhoneAccounts";
                case 6:
                    return "getSelfManagedPhoneAccounts";
                case 7:
                    return "getOwnSelfManagedPhoneAccounts";
                case 8:
                    return "getPhoneAccountsSupportingScheme";
                case 9:
                    return "getPhoneAccountsForPackage";
                case 10:
                    return "getPhoneAccount";
                case 11:
                    return "getRegisteredPhoneAccounts";
                case 12:
                    return "getAllPhoneAccountsCount";
                case 13:
                    return "getAllPhoneAccounts";
                case 14:
                    return "getAllPhoneAccountHandles";
                case 15:
                    return "getSimCallManager";
                case 16:
                    return "getSimCallManagerForUser";
                case 17:
                    return "registerPhoneAccount";
                case 18:
                    return "unregisterPhoneAccount";
                case 19:
                    return "clearAccounts";
                case 20:
                    return "isVoiceMailNumber";
                case 21:
                    return "getVoiceMailNumber";
                case 22:
                    return "getLine1Number";
                case 23:
                    return "getDefaultPhoneApp";
                case 24:
                    return "getDefaultDialerPackage";
                case 25:
                    return "getDefaultDialerPackageForUser";
                case 26:
                    return "getSystemDialerPackage";
                case 27:
                    return "dumpCallAnalytics";
                case 28:
                    return "silenceRinger";
                case 29:
                    return "isInCall";
                case 30:
                    return "hasManageOngoingCallsPermission";
                case 31:
                    return "isInManagedCall";
                case 32:
                    return "isRinging";
                case 33:
                    return "getCallState";
                case 34:
                    return "getCallStateUsingPackage";
                case 35:
                    return "endCall";
                case 36:
                    return "acceptRingingCall";
                case 37:
                    return "acceptRingingCallWithVideoState";
                case 38:
                    return "cancelMissedCallsNotification";
                case 39:
                    return "handlePinMmi";
                case 40:
                    return "handlePinMmiForPhoneAccount";
                case 41:
                    return "getAdnUriForPhoneAccount";
                case 42:
                    return "isTtySupported";
                case 43:
                    return "getCurrentTtyMode";
                case 44:
                    return "addNewIncomingCall";
                case 45:
                    return "addNewIncomingConference";
                case 46:
                    return "addNewUnknownCall";
                case 47:
                    return "startConference";
                case 48:
                    return "placeCall";
                case 49:
                    return "enablePhoneAccount";
                case 50:
                    return "setDefaultDialer";
                case 51:
                    return "stopBlockSuppression";
                case 52:
                    return "createManageBlockedNumbersIntent";
                case 53:
                    return "createLaunchEmergencyDialerIntent";
                case 54:
                    return "isIncomingCallPermitted";
                case 55:
                    return "isOutgoingCallPermitted";
                case 56:
                    return "waitOnHandlers";
                case 57:
                    return "acceptHandover";
                case 58:
                    return "setTestEmergencyPhoneAccountPackageNameFilter";
                case 59:
                    return "isInEmergencyCall";
                case 60:
                    return "handleCallIntent";
                case 61:
                    return "cleanupStuckCalls";
                case 62:
                    return "cleanupOrphanPhoneAccounts";
                case 63:
                    return "isNonUiInCallServiceBound";
                case 64:
                    return "resetCarMode";
                case 65:
                    return "setTestDefaultCallRedirectionApp";
                case 66:
                    return "requestLogMark";
                case 67:
                    return "setTestPhoneAcctSuggestionComponent";
                case 68:
                    return "setTestDefaultCallScreeningApp";
                case 69:
                    return "addOrRemoveTestCallCompanionApp";
                case 70:
                    return "setSystemDialer";
                case 71:
                    return "setTestDefaultDialer";
                case 72:
                    return "setTestCallDiagnosticService";
                case 73:
                    return "isInSelfManagedCall";
                case 74:
                    return "addCall";
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
                    boolean readBoolean = parcel.readBoolean();
                    java.lang.String readString = parcel.readString();
                    java.lang.String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    showInCallScreen(readBoolean, readString, readString2);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    java.lang.String readString3 = parcel.readString();
                    java.lang.String readString4 = parcel.readString();
                    java.lang.String readString5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.telecom.PhoneAccountHandle defaultOutgoingPhoneAccount = getDefaultOutgoingPhoneAccount(readString3, readString4, readString5);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(defaultOutgoingPhoneAccount, 1);
                    return true;
                case 3:
                    java.lang.String readString6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.telecom.PhoneAccountHandle userSelectedOutgoingPhoneAccount = getUserSelectedOutgoingPhoneAccount(readString6);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(userSelectedOutgoingPhoneAccount, 1);
                    return true;
                case 4:
                    android.telecom.PhoneAccountHandle phoneAccountHandle = (android.telecom.PhoneAccountHandle) parcel.readTypedObject(android.telecom.PhoneAccountHandle.CREATOR);
                    parcel.enforceNoDataAvail();
                    setUserSelectedOutgoingPhoneAccount(phoneAccountHandle);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    boolean readBoolean2 = parcel.readBoolean();
                    java.lang.String readString7 = parcel.readString();
                    java.lang.String readString8 = parcel.readString();
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    android.content.pm.ParceledListSlice<android.telecom.PhoneAccountHandle> callCapablePhoneAccounts = getCallCapablePhoneAccounts(readBoolean2, readString7, readString8, readBoolean3);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(callCapablePhoneAccounts, 1);
                    return true;
                case 6:
                    java.lang.String readString9 = parcel.readString();
                    java.lang.String readString10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.content.pm.ParceledListSlice<android.telecom.PhoneAccountHandle> selfManagedPhoneAccounts = getSelfManagedPhoneAccounts(readString9, readString10);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(selfManagedPhoneAccounts, 1);
                    return true;
                case 7:
                    java.lang.String readString11 = parcel.readString();
                    java.lang.String readString12 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.content.pm.ParceledListSlice<android.telecom.PhoneAccountHandle> ownSelfManagedPhoneAccounts = getOwnSelfManagedPhoneAccounts(readString11, readString12);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(ownSelfManagedPhoneAccounts, 1);
                    return true;
                case 8:
                    java.lang.String readString13 = parcel.readString();
                    java.lang.String readString14 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.content.pm.ParceledListSlice<android.telecom.PhoneAccountHandle> phoneAccountsSupportingScheme = getPhoneAccountsSupportingScheme(readString13, readString14);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(phoneAccountsSupportingScheme, 1);
                    return true;
                case 9:
                    java.lang.String readString15 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.content.pm.ParceledListSlice<android.telecom.PhoneAccountHandle> phoneAccountsForPackage = getPhoneAccountsForPackage(readString15);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(phoneAccountsForPackage, 1);
                    return true;
                case 10:
                    android.telecom.PhoneAccountHandle phoneAccountHandle2 = (android.telecom.PhoneAccountHandle) parcel.readTypedObject(android.telecom.PhoneAccountHandle.CREATOR);
                    java.lang.String readString16 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.telecom.PhoneAccount phoneAccount = getPhoneAccount(phoneAccountHandle2, readString16);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(phoneAccount, 1);
                    return true;
                case 11:
                    java.lang.String readString17 = parcel.readString();
                    java.lang.String readString18 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.content.pm.ParceledListSlice<android.telecom.PhoneAccount> registeredPhoneAccounts = getRegisteredPhoneAccounts(readString17, readString18);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(registeredPhoneAccounts, 1);
                    return true;
                case 12:
                    int allPhoneAccountsCount = getAllPhoneAccountsCount();
                    parcel2.writeNoException();
                    parcel2.writeInt(allPhoneAccountsCount);
                    return true;
                case 13:
                    android.content.pm.ParceledListSlice<android.telecom.PhoneAccount> allPhoneAccounts = getAllPhoneAccounts();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(allPhoneAccounts, 1);
                    return true;
                case 14:
                    android.content.pm.ParceledListSlice<android.telecom.PhoneAccountHandle> allPhoneAccountHandles = getAllPhoneAccountHandles();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(allPhoneAccountHandles, 1);
                    return true;
                case 15:
                    int readInt = parcel.readInt();
                    java.lang.String readString19 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.telecom.PhoneAccountHandle simCallManager = getSimCallManager(readInt, readString19);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(simCallManager, 1);
                    return true;
                case 16:
                    int readInt2 = parcel.readInt();
                    java.lang.String readString20 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.telecom.PhoneAccountHandle simCallManagerForUser = getSimCallManagerForUser(readInt2, readString20);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(simCallManagerForUser, 1);
                    return true;
                case 17:
                    android.telecom.PhoneAccount phoneAccount2 = (android.telecom.PhoneAccount) parcel.readTypedObject(android.telecom.PhoneAccount.CREATOR);
                    java.lang.String readString21 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    registerPhoneAccount(phoneAccount2, readString21);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    android.telecom.PhoneAccountHandle phoneAccountHandle3 = (android.telecom.PhoneAccountHandle) parcel.readTypedObject(android.telecom.PhoneAccountHandle.CREATOR);
                    java.lang.String readString22 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    unregisterPhoneAccount(phoneAccountHandle3, readString22);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    java.lang.String readString23 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    clearAccounts(readString23);
                    parcel2.writeNoException();
                    return true;
                case 20:
                    android.telecom.PhoneAccountHandle phoneAccountHandle4 = (android.telecom.PhoneAccountHandle) parcel.readTypedObject(android.telecom.PhoneAccountHandle.CREATOR);
                    java.lang.String readString24 = parcel.readString();
                    java.lang.String readString25 = parcel.readString();
                    java.lang.String readString26 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isVoiceMailNumber = isVoiceMailNumber(phoneAccountHandle4, readString24, readString25, readString26);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isVoiceMailNumber);
                    return true;
                case 21:
                    android.telecom.PhoneAccountHandle phoneAccountHandle5 = (android.telecom.PhoneAccountHandle) parcel.readTypedObject(android.telecom.PhoneAccountHandle.CREATOR);
                    java.lang.String readString27 = parcel.readString();
                    java.lang.String readString28 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    java.lang.String voiceMailNumber = getVoiceMailNumber(phoneAccountHandle5, readString27, readString28);
                    parcel2.writeNoException();
                    parcel2.writeString(voiceMailNumber);
                    return true;
                case 22:
                    android.telecom.PhoneAccountHandle phoneAccountHandle6 = (android.telecom.PhoneAccountHandle) parcel.readTypedObject(android.telecom.PhoneAccountHandle.CREATOR);
                    java.lang.String readString29 = parcel.readString();
                    java.lang.String readString30 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    java.lang.String line1Number = getLine1Number(phoneAccountHandle6, readString29, readString30);
                    parcel2.writeNoException();
                    parcel2.writeString(line1Number);
                    return true;
                case 23:
                    android.content.ComponentName defaultPhoneApp = getDefaultPhoneApp();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(defaultPhoneApp, 1);
                    return true;
                case 24:
                    java.lang.String readString31 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    java.lang.String defaultDialerPackage = getDefaultDialerPackage(readString31);
                    parcel2.writeNoException();
                    parcel2.writeString(defaultDialerPackage);
                    return true;
                case 25:
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.lang.String defaultDialerPackageForUser = getDefaultDialerPackageForUser(readInt3);
                    parcel2.writeNoException();
                    parcel2.writeString(defaultDialerPackageForUser);
                    return true;
                case 26:
                    java.lang.String readString32 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    java.lang.String systemDialerPackage = getSystemDialerPackage(readString32);
                    parcel2.writeNoException();
                    parcel2.writeString(systemDialerPackage);
                    return true;
                case 27:
                    android.telecom.TelecomAnalytics dumpCallAnalytics = dumpCallAnalytics();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(dumpCallAnalytics, 1);
                    return true;
                case 28:
                    java.lang.String readString33 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    silenceRinger(readString33);
                    parcel2.writeNoException();
                    return true;
                case 29:
                    java.lang.String readString34 = parcel.readString();
                    java.lang.String readString35 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isInCall = isInCall(readString34, readString35);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isInCall);
                    return true;
                case 30:
                    java.lang.String readString36 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean hasManageOngoingCallsPermission = hasManageOngoingCallsPermission(readString36);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasManageOngoingCallsPermission);
                    return true;
                case 31:
                    java.lang.String readString37 = parcel.readString();
                    java.lang.String readString38 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isInManagedCall = isInManagedCall(readString37, readString38);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isInManagedCall);
                    return true;
                case 32:
                    java.lang.String readString39 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isRinging = isRinging(readString39);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isRinging);
                    return true;
                case 33:
                    int callState = getCallState();
                    parcel2.writeNoException();
                    parcel2.writeInt(callState);
                    return true;
                case 34:
                    java.lang.String readString40 = parcel.readString();
                    java.lang.String readString41 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int callStateUsingPackage = getCallStateUsingPackage(readString40, readString41);
                    parcel2.writeNoException();
                    parcel2.writeInt(callStateUsingPackage);
                    return true;
                case 35:
                    java.lang.String readString42 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean endCall = endCall(readString42);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(endCall);
                    return true;
                case 36:
                    java.lang.String readString43 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    acceptRingingCall(readString43);
                    parcel2.writeNoException();
                    return true;
                case 37:
                    java.lang.String readString44 = parcel.readString();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    acceptRingingCallWithVideoState(readString44, readInt4);
                    parcel2.writeNoException();
                    return true;
                case 38:
                    java.lang.String readString45 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    cancelMissedCallsNotification(readString45);
                    parcel2.writeNoException();
                    return true;
                case 39:
                    java.lang.String readString46 = parcel.readString();
                    java.lang.String readString47 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean handlePinMmi = handlePinMmi(readString46, readString47);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(handlePinMmi);
                    return true;
                case 40:
                    android.telecom.PhoneAccountHandle phoneAccountHandle7 = (android.telecom.PhoneAccountHandle) parcel.readTypedObject(android.telecom.PhoneAccountHandle.CREATOR);
                    java.lang.String readString48 = parcel.readString();
                    java.lang.String readString49 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean handlePinMmiForPhoneAccount = handlePinMmiForPhoneAccount(phoneAccountHandle7, readString48, readString49);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(handlePinMmiForPhoneAccount);
                    return true;
                case 41:
                    android.telecom.PhoneAccountHandle phoneAccountHandle8 = (android.telecom.PhoneAccountHandle) parcel.readTypedObject(android.telecom.PhoneAccountHandle.CREATOR);
                    java.lang.String readString50 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.net.Uri adnUriForPhoneAccount = getAdnUriForPhoneAccount(phoneAccountHandle8, readString50);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(adnUriForPhoneAccount, 1);
                    return true;
                case 42:
                    java.lang.String readString51 = parcel.readString();
                    java.lang.String readString52 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isTtySupported = isTtySupported(readString51, readString52);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isTtySupported);
                    return true;
                case 43:
                    java.lang.String readString53 = parcel.readString();
                    java.lang.String readString54 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int currentTtyMode = getCurrentTtyMode(readString53, readString54);
                    parcel2.writeNoException();
                    parcel2.writeInt(currentTtyMode);
                    return true;
                case 44:
                    android.telecom.PhoneAccountHandle phoneAccountHandle9 = (android.telecom.PhoneAccountHandle) parcel.readTypedObject(android.telecom.PhoneAccountHandle.CREATOR);
                    android.os.Bundle bundle = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    java.lang.String readString55 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    addNewIncomingCall(phoneAccountHandle9, bundle, readString55);
                    parcel2.writeNoException();
                    return true;
                case 45:
                    android.telecom.PhoneAccountHandle phoneAccountHandle10 = (android.telecom.PhoneAccountHandle) parcel.readTypedObject(android.telecom.PhoneAccountHandle.CREATOR);
                    android.os.Bundle bundle2 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    java.lang.String readString56 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    addNewIncomingConference(phoneAccountHandle10, bundle2, readString56);
                    parcel2.writeNoException();
                    return true;
                case 46:
                    android.telecom.PhoneAccountHandle phoneAccountHandle11 = (android.telecom.PhoneAccountHandle) parcel.readTypedObject(android.telecom.PhoneAccountHandle.CREATOR);
                    android.os.Bundle bundle3 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    addNewUnknownCall(phoneAccountHandle11, bundle3);
                    parcel2.writeNoException();
                    return true;
                case 47:
                    java.util.ArrayList createTypedArrayList = parcel.createTypedArrayList(android.net.Uri.CREATOR);
                    android.os.Bundle bundle4 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    java.lang.String readString57 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    startConference(createTypedArrayList, bundle4, readString57);
                    parcel2.writeNoException();
                    return true;
                case 48:
                    android.net.Uri uri = (android.net.Uri) parcel.readTypedObject(android.net.Uri.CREATOR);
                    android.os.Bundle bundle5 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    java.lang.String readString58 = parcel.readString();
                    java.lang.String readString59 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    placeCall(uri, bundle5, readString58, readString59);
                    parcel2.writeNoException();
                    return true;
                case 49:
                    android.telecom.PhoneAccountHandle phoneAccountHandle12 = (android.telecom.PhoneAccountHandle) parcel.readTypedObject(android.telecom.PhoneAccountHandle.CREATOR);
                    boolean readBoolean4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean enablePhoneAccount = enablePhoneAccount(phoneAccountHandle12, readBoolean4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(enablePhoneAccount);
                    return true;
                case 50:
                    java.lang.String readString60 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean defaultDialer = setDefaultDialer(readString60);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(defaultDialer);
                    return true;
                case 51:
                    stopBlockSuppression();
                    parcel2.writeNoException();
                    return true;
                case 52:
                    java.lang.String readString61 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.content.Intent createManageBlockedNumbersIntent = createManageBlockedNumbersIntent(readString61);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(createManageBlockedNumbersIntent, 1);
                    return true;
                case 53:
                    java.lang.String readString62 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.content.Intent createLaunchEmergencyDialerIntent = createLaunchEmergencyDialerIntent(readString62);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(createLaunchEmergencyDialerIntent, 1);
                    return true;
                case 54:
                    android.telecom.PhoneAccountHandle phoneAccountHandle13 = (android.telecom.PhoneAccountHandle) parcel.readTypedObject(android.telecom.PhoneAccountHandle.CREATOR);
                    java.lang.String readString63 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isIncomingCallPermitted = isIncomingCallPermitted(phoneAccountHandle13, readString63);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isIncomingCallPermitted);
                    return true;
                case 55:
                    android.telecom.PhoneAccountHandle phoneAccountHandle14 = (android.telecom.PhoneAccountHandle) parcel.readTypedObject(android.telecom.PhoneAccountHandle.CREATOR);
                    java.lang.String readString64 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isOutgoingCallPermitted = isOutgoingCallPermitted(phoneAccountHandle14, readString64);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isOutgoingCallPermitted);
                    return true;
                case 56:
                    waitOnHandlers();
                    parcel2.writeNoException();
                    return true;
                case 57:
                    android.net.Uri uri2 = (android.net.Uri) parcel.readTypedObject(android.net.Uri.CREATOR);
                    int readInt5 = parcel.readInt();
                    android.telecom.PhoneAccountHandle phoneAccountHandle15 = (android.telecom.PhoneAccountHandle) parcel.readTypedObject(android.telecom.PhoneAccountHandle.CREATOR);
                    java.lang.String readString65 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    acceptHandover(uri2, readInt5, phoneAccountHandle15, readString65);
                    parcel2.writeNoException();
                    return true;
                case 58:
                    java.lang.String readString66 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setTestEmergencyPhoneAccountPackageNameFilter(readString66);
                    parcel2.writeNoException();
                    return true;
                case 59:
                    boolean isInEmergencyCall = isInEmergencyCall();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isInEmergencyCall);
                    return true;
                case 60:
                    android.content.Intent intent = (android.content.Intent) parcel.readTypedObject(android.content.Intent.CREATOR);
                    java.lang.String readString67 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    handleCallIntent(intent, readString67);
                    parcel2.writeNoException();
                    return true;
                case 61:
                    cleanupStuckCalls();
                    parcel2.writeNoException();
                    return true;
                case 62:
                    int cleanupOrphanPhoneAccounts = cleanupOrphanPhoneAccounts();
                    parcel2.writeNoException();
                    parcel2.writeInt(cleanupOrphanPhoneAccounts);
                    return true;
                case 63:
                    java.lang.String readString68 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isNonUiInCallServiceBound = isNonUiInCallServiceBound(readString68);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isNonUiInCallServiceBound);
                    return true;
                case 64:
                    resetCarMode();
                    parcel2.writeNoException();
                    return true;
                case 65:
                    java.lang.String readString69 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setTestDefaultCallRedirectionApp(readString69);
                    parcel2.writeNoException();
                    return true;
                case 66:
                    java.lang.String readString70 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    requestLogMark(readString70);
                    parcel2.writeNoException();
                    return true;
                case 67:
                    java.lang.String readString71 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setTestPhoneAcctSuggestionComponent(readString71);
                    parcel2.writeNoException();
                    return true;
                case 68:
                    java.lang.String readString72 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setTestDefaultCallScreeningApp(readString72);
                    parcel2.writeNoException();
                    return true;
                case 69:
                    java.lang.String readString73 = parcel.readString();
                    boolean readBoolean5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    addOrRemoveTestCallCompanionApp(readString73, readBoolean5);
                    parcel2.writeNoException();
                    return true;
                case 70:
                    android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    setSystemDialer(componentName);
                    parcel2.writeNoException();
                    return true;
                case 71:
                    java.lang.String readString74 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setTestDefaultDialer(readString74);
                    parcel2.writeNoException();
                    return true;
                case 72:
                    java.lang.String readString75 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setTestCallDiagnosticService(readString75);
                    parcel2.writeNoException();
                    return true;
                case 73:
                    java.lang.String readString76 = parcel.readString();
                    android.os.UserHandle userHandle = (android.os.UserHandle) parcel.readTypedObject(android.os.UserHandle.CREATOR);
                    java.lang.String readString77 = parcel.readString();
                    boolean readBoolean6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean isInSelfManagedCall = isInSelfManagedCall(readString76, userHandle, readString77, readBoolean6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isInSelfManagedCall);
                    return true;
                case 74:
                    android.telecom.CallAttributes callAttributes = (android.telecom.CallAttributes) parcel.readTypedObject(android.telecom.CallAttributes.CREATOR);
                    com.android.internal.telecom.ICallEventCallback asInterface = com.android.internal.telecom.ICallEventCallback.Stub.asInterface(parcel.readStrongBinder());
                    java.lang.String readString78 = parcel.readString();
                    java.lang.String readString79 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    addCall(callAttributes, asInterface, readString78, readString79);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements com.android.internal.telecom.ITelecomService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return com.android.internal.telecom.ITelecomService.Stub.DESCRIPTOR;
            }

            @Override // com.android.internal.telecom.ITelecomService
            public void showInCallScreen(boolean z, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.ITelecomService.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public android.telecom.PhoneAccountHandle getDefaultOutgoingPhoneAccount(java.lang.String str, java.lang.String str2, java.lang.String str3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.ITelecomService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.telecom.PhoneAccountHandle) obtain2.readTypedObject(android.telecom.PhoneAccountHandle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public android.telecom.PhoneAccountHandle getUserSelectedOutgoingPhoneAccount(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.ITelecomService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.telecom.PhoneAccountHandle) obtain2.readTypedObject(android.telecom.PhoneAccountHandle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public void setUserSelectedOutgoingPhoneAccount(android.telecom.PhoneAccountHandle phoneAccountHandle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.ITelecomService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(phoneAccountHandle, 0);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public android.content.pm.ParceledListSlice<android.telecom.PhoneAccountHandle> getCallCapablePhoneAccounts(boolean z, java.lang.String str, java.lang.String str2, boolean z2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.ITelecomService.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeBoolean(z2);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.pm.ParceledListSlice) obtain2.readTypedObject(android.content.pm.ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public android.content.pm.ParceledListSlice<android.telecom.PhoneAccountHandle> getSelfManagedPhoneAccounts(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.ITelecomService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.pm.ParceledListSlice) obtain2.readTypedObject(android.content.pm.ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public android.content.pm.ParceledListSlice<android.telecom.PhoneAccountHandle> getOwnSelfManagedPhoneAccounts(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.ITelecomService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.pm.ParceledListSlice) obtain2.readTypedObject(android.content.pm.ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public android.content.pm.ParceledListSlice<android.telecom.PhoneAccountHandle> getPhoneAccountsSupportingScheme(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.ITelecomService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.pm.ParceledListSlice) obtain2.readTypedObject(android.content.pm.ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public android.content.pm.ParceledListSlice<android.telecom.PhoneAccountHandle> getPhoneAccountsForPackage(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.ITelecomService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.pm.ParceledListSlice) obtain2.readTypedObject(android.content.pm.ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public android.telecom.PhoneAccount getPhoneAccount(android.telecom.PhoneAccountHandle phoneAccountHandle, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.ITelecomService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(phoneAccountHandle, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.telecom.PhoneAccount) obtain2.readTypedObject(android.telecom.PhoneAccount.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public android.content.pm.ParceledListSlice<android.telecom.PhoneAccount> getRegisteredPhoneAccounts(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.ITelecomService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.pm.ParceledListSlice) obtain2.readTypedObject(android.content.pm.ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public int getAllPhoneAccountsCount() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.ITelecomService.Stub.DESCRIPTOR);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public android.content.pm.ParceledListSlice<android.telecom.PhoneAccount> getAllPhoneAccounts() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.ITelecomService.Stub.DESCRIPTOR);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.pm.ParceledListSlice) obtain2.readTypedObject(android.content.pm.ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public android.content.pm.ParceledListSlice<android.telecom.PhoneAccountHandle> getAllPhoneAccountHandles() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.ITelecomService.Stub.DESCRIPTOR);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.pm.ParceledListSlice) obtain2.readTypedObject(android.content.pm.ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public android.telecom.PhoneAccountHandle getSimCallManager(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.ITelecomService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.telecom.PhoneAccountHandle) obtain2.readTypedObject(android.telecom.PhoneAccountHandle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public android.telecom.PhoneAccountHandle getSimCallManagerForUser(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.ITelecomService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.telecom.PhoneAccountHandle) obtain2.readTypedObject(android.telecom.PhoneAccountHandle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public void registerPhoneAccount(android.telecom.PhoneAccount phoneAccount, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.ITelecomService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(phoneAccount, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public void unregisterPhoneAccount(android.telecom.PhoneAccountHandle phoneAccountHandle, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.ITelecomService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(phoneAccountHandle, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public void clearAccounts(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.ITelecomService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public boolean isVoiceMailNumber(android.telecom.PhoneAccountHandle phoneAccountHandle, java.lang.String str, java.lang.String str2, java.lang.String str3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.ITelecomService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(phoneAccountHandle, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public java.lang.String getVoiceMailNumber(android.telecom.PhoneAccountHandle phoneAccountHandle, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.ITelecomService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(phoneAccountHandle, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public java.lang.String getLine1Number(android.telecom.PhoneAccountHandle phoneAccountHandle, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.ITelecomService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(phoneAccountHandle, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public android.content.ComponentName getDefaultPhoneApp() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.ITelecomService.Stub.DESCRIPTOR);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.ComponentName) obtain2.readTypedObject(android.content.ComponentName.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public java.lang.String getDefaultDialerPackage(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.ITelecomService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public java.lang.String getDefaultDialerPackageForUser(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.ITelecomService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public java.lang.String getSystemDialerPackage(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.ITelecomService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public android.telecom.TelecomAnalytics dumpCallAnalytics() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.ITelecomService.Stub.DESCRIPTOR);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.telecom.TelecomAnalytics) obtain2.readTypedObject(android.telecom.TelecomAnalytics.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public void silenceRinger(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.ITelecomService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public boolean isInCall(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.ITelecomService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public boolean hasManageOngoingCallsPermission(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.ITelecomService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public boolean isInManagedCall(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.ITelecomService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public boolean isRinging(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.ITelecomService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public int getCallState() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.ITelecomService.Stub.DESCRIPTOR);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public int getCallStateUsingPackage(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.ITelecomService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public boolean endCall(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.ITelecomService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public void acceptRingingCall(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.ITelecomService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public void acceptRingingCallWithVideoState(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.ITelecomService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(37, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public void cancelMissedCallsNotification(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.ITelecomService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(38, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public boolean handlePinMmi(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.ITelecomService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(39, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public boolean handlePinMmiForPhoneAccount(android.telecom.PhoneAccountHandle phoneAccountHandle, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.ITelecomService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(phoneAccountHandle, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(40, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public android.net.Uri getAdnUriForPhoneAccount(android.telecom.PhoneAccountHandle phoneAccountHandle, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.ITelecomService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(phoneAccountHandle, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(41, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.net.Uri) obtain2.readTypedObject(android.net.Uri.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public boolean isTtySupported(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.ITelecomService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(42, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public int getCurrentTtyMode(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.ITelecomService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(43, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public void addNewIncomingCall(android.telecom.PhoneAccountHandle phoneAccountHandle, android.os.Bundle bundle, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.ITelecomService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(phoneAccountHandle, 0);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(44, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public void addNewIncomingConference(android.telecom.PhoneAccountHandle phoneAccountHandle, android.os.Bundle bundle, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.ITelecomService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(phoneAccountHandle, 0);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(45, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public void addNewUnknownCall(android.telecom.PhoneAccountHandle phoneAccountHandle, android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.ITelecomService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(phoneAccountHandle, 0);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(46, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public void startConference(java.util.List<android.net.Uri> list, android.os.Bundle bundle, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.ITelecomService.Stub.DESCRIPTOR);
                    obtain.writeTypedList(list, 0);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(47, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public void placeCall(android.net.Uri uri, android.os.Bundle bundle, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.ITelecomService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(48, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public boolean enablePhoneAccount(android.telecom.PhoneAccountHandle phoneAccountHandle, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.ITelecomService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(phoneAccountHandle, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(49, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public boolean setDefaultDialer(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.ITelecomService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(50, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public void stopBlockSuppression() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.ITelecomService.Stub.DESCRIPTOR);
                    this.mRemote.transact(51, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public android.content.Intent createManageBlockedNumbersIntent(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.ITelecomService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(52, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.Intent) obtain2.readTypedObject(android.content.Intent.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public android.content.Intent createLaunchEmergencyDialerIntent(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.ITelecomService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(53, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.Intent) obtain2.readTypedObject(android.content.Intent.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public boolean isIncomingCallPermitted(android.telecom.PhoneAccountHandle phoneAccountHandle, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.ITelecomService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(phoneAccountHandle, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(54, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public boolean isOutgoingCallPermitted(android.telecom.PhoneAccountHandle phoneAccountHandle, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.ITelecomService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(phoneAccountHandle, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(55, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public void waitOnHandlers() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.ITelecomService.Stub.DESCRIPTOR);
                    this.mRemote.transact(56, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public void acceptHandover(android.net.Uri uri, int i, android.telecom.PhoneAccountHandle phoneAccountHandle, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.ITelecomService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(phoneAccountHandle, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(57, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public void setTestEmergencyPhoneAccountPackageNameFilter(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.ITelecomService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(58, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public boolean isInEmergencyCall() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.ITelecomService.Stub.DESCRIPTOR);
                    this.mRemote.transact(59, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public void handleCallIntent(android.content.Intent intent, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.ITelecomService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(60, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public void cleanupStuckCalls() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.ITelecomService.Stub.DESCRIPTOR);
                    this.mRemote.transact(61, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public int cleanupOrphanPhoneAccounts() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.ITelecomService.Stub.DESCRIPTOR);
                    this.mRemote.transact(62, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public boolean isNonUiInCallServiceBound(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.ITelecomService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(63, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public void resetCarMode() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.ITelecomService.Stub.DESCRIPTOR);
                    this.mRemote.transact(64, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public void setTestDefaultCallRedirectionApp(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.ITelecomService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(65, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public void requestLogMark(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.ITelecomService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(66, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public void setTestPhoneAcctSuggestionComponent(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.ITelecomService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(67, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public void setTestDefaultCallScreeningApp(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.ITelecomService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(68, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public void addOrRemoveTestCallCompanionApp(java.lang.String str, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.ITelecomService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(69, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public void setSystemDialer(android.content.ComponentName componentName) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.ITelecomService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(70, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public void setTestDefaultDialer(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.ITelecomService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(71, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public void setTestCallDiagnosticService(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.ITelecomService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(72, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public boolean isInSelfManagedCall(java.lang.String str, android.os.UserHandle userHandle, java.lang.String str2, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.ITelecomService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(userHandle, 0);
                    obtain.writeString(str2);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(73, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public void addCall(android.telecom.CallAttributes callAttributes, com.android.internal.telecom.ICallEventCallback iCallEventCallback, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.ITelecomService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(callAttributes, 0);
                    obtain.writeStrongInterface(iCallEventCallback);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(74, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 73;
        }
    }
}
