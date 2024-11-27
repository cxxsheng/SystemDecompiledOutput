package android.os;

/* loaded from: classes3.dex */
public interface IUserManager extends android.os.IInterface {
    void addUserRestrictionsListener(android.os.IUserRestrictionsListener iUserRestrictionsListener) throws android.os.RemoteException;

    boolean canAddMoreManagedProfiles(int i, boolean z) throws android.os.RemoteException;

    boolean canAddMoreProfilesToUser(java.lang.String str, int i, boolean z) throws android.os.RemoteException;

    boolean canAddMoreUsersOfType(java.lang.String str) throws android.os.RemoteException;

    boolean canHaveRestrictedProfile(int i) throws android.os.RemoteException;

    void clearSeedAccountData(int i) throws android.os.RemoteException;

    android.content.pm.UserInfo createProfileForUserEvenWhenDisallowedWithThrow(java.lang.String str, java.lang.String str2, int i, int i2, java.lang.String[] strArr) throws android.os.RemoteException;

    android.content.pm.UserInfo createProfileForUserWithThrow(java.lang.String str, java.lang.String str2, int i, int i2, java.lang.String[] strArr) throws android.os.RemoteException;

    android.content.pm.UserInfo createRestrictedProfileWithThrow(java.lang.String str, int i) throws android.os.RemoteException;

    android.os.UserHandle createUserWithAttributes(java.lang.String str, java.lang.String str2, int i, android.graphics.Bitmap bitmap, java.lang.String str3, java.lang.String str4, android.os.PersistableBundle persistableBundle) throws android.os.RemoteException;

    android.content.pm.UserInfo createUserWithThrow(java.lang.String str, java.lang.String str2, int i) throws android.os.RemoteException;

    void evictCredentialEncryptionKey(int i) throws android.os.RemoteException;

    android.os.Bundle getApplicationRestrictions(java.lang.String str) throws android.os.RemoteException;

    android.os.Bundle getApplicationRestrictionsForUser(java.lang.String str, int i) throws android.os.RemoteException;

    int getBootUser() throws android.os.RemoteException;

    int getCommunalProfileId() throws android.os.RemoteException;

    int getCredentialOwnerProfile(int i) throws android.os.RemoteException;

    android.os.Bundle getDefaultGuestRestrictions() throws android.os.RemoteException;

    java.util.List<android.content.pm.UserInfo> getGuestUsers() throws android.os.RemoteException;

    int getMainDisplayIdAssignedToUser() throws android.os.RemoteException;

    int getMainUserId() throws android.os.RemoteException;

    java.lang.String[] getPreInstallableSystemPackages(java.lang.String str) throws android.os.RemoteException;

    int getPreviousFullUserToEnterForeground() throws android.os.RemoteException;

    android.content.pm.UserInfo getPrimaryUser() throws android.os.RemoteException;

    int[] getProfileIds(int i, boolean z) throws android.os.RemoteException;

    int[] getProfileIdsExcludingHidden(int i, boolean z) throws android.os.RemoteException;

    int getProfileLabelResId(int i) throws android.os.RemoteException;

    android.content.pm.UserInfo getProfileParent(int i) throws android.os.RemoteException;

    int getProfileParentId(int i) throws android.os.RemoteException;

    java.lang.String getProfileType(int i) throws android.os.RemoteException;

    java.util.List<android.content.pm.UserInfo> getProfiles(int i, boolean z) throws android.os.RemoteException;

    int getRemainingCreatableProfileCount(java.lang.String str, int i) throws android.os.RemoteException;

    int getRemainingCreatableUserCount(java.lang.String str) throws android.os.RemoteException;

    java.lang.String getSeedAccountName(int i) throws android.os.RemoteException;

    android.os.PersistableBundle getSeedAccountOptions(int i) throws android.os.RemoteException;

    java.lang.String getSeedAccountType(int i) throws android.os.RemoteException;

    java.lang.String getUserAccount(int i) throws android.os.RemoteException;

    int getUserBadgeColorResId(int i) throws android.os.RemoteException;

    int getUserBadgeDarkColorResId(int i) throws android.os.RemoteException;

    int getUserBadgeLabelResId(int i) throws android.os.RemoteException;

    int getUserBadgeNoBackgroundResId(int i) throws android.os.RemoteException;

    int getUserBadgeResId(int i) throws android.os.RemoteException;

    long getUserCreationTime(int i) throws android.os.RemoteException;

    int getUserHandle(int i) throws android.os.RemoteException;

    android.os.ParcelFileDescriptor getUserIcon(int i) throws android.os.RemoteException;

    int getUserIconBadgeResId(int i) throws android.os.RemoteException;

    android.content.pm.UserInfo getUserInfo(int i) throws android.os.RemoteException;

    java.lang.String getUserName() throws android.os.RemoteException;

    android.content.pm.UserProperties getUserPropertiesCopy(int i) throws android.os.RemoteException;

    int getUserRestrictionSource(java.lang.String str, int i) throws android.os.RemoteException;

    java.util.List<android.os.UserManager.EnforcingUser> getUserRestrictionSources(java.lang.String str, int i) throws android.os.RemoteException;

    android.os.Bundle getUserRestrictions(int i) throws android.os.RemoteException;

    int getUserSerialNumber(int i) throws android.os.RemoteException;

    long getUserStartRealtime() throws android.os.RemoteException;

    int getUserStatusBarIconResId(int i) throws android.os.RemoteException;

    int getUserSwitchability(int i) throws android.os.RemoteException;

    long getUserUnlockRealtime() throws android.os.RemoteException;

    java.util.List<android.content.pm.UserInfo> getUsers(boolean z, boolean z2, boolean z3) throws android.os.RemoteException;

    int[] getVisibleUsers() throws android.os.RemoteException;

    boolean hasBadge(int i) throws android.os.RemoteException;

    boolean hasBaseUserRestriction(java.lang.String str, int i) throws android.os.RemoteException;

    boolean hasRestrictedProfiles(int i) throws android.os.RemoteException;

    boolean hasUserRestriction(java.lang.String str, int i) throws android.os.RemoteException;

    boolean hasUserRestrictionOnAnyUser(java.lang.String str) throws android.os.RemoteException;

    boolean isAdminUser(int i) throws android.os.RemoteException;

    boolean isDemoUser(int i) throws android.os.RemoteException;

    boolean isForegroundUserAdmin() throws android.os.RemoteException;

    boolean isHeadlessSystemUserMode() throws android.os.RemoteException;

    boolean isPreCreated(int i) throws android.os.RemoteException;

    boolean isQuietModeEnabled(int i) throws android.os.RemoteException;

    boolean isRestricted(int i) throws android.os.RemoteException;

    boolean isSameProfileGroup(int i, int i2) throws android.os.RemoteException;

    boolean isSettingRestrictedForUser(java.lang.String str, int i, java.lang.String str2, int i2) throws android.os.RemoteException;

    boolean isUserForeground(int i) throws android.os.RemoteException;

    boolean isUserNameSet(int i) throws android.os.RemoteException;

    boolean isUserOfType(int i, java.lang.String str) throws android.os.RemoteException;

    boolean isUserRunning(int i) throws android.os.RemoteException;

    boolean isUserSwitcherEnabled(boolean z, int i) throws android.os.RemoteException;

    boolean isUserTypeEnabled(java.lang.String str) throws android.os.RemoteException;

    boolean isUserUnlocked(int i) throws android.os.RemoteException;

    boolean isUserUnlockingOrUnlocked(int i) throws android.os.RemoteException;

    boolean isUserVisible(int i) throws android.os.RemoteException;

    boolean markGuestForDeletion(int i) throws android.os.RemoteException;

    android.content.pm.UserInfo preCreateUserWithThrow(java.lang.String str) throws android.os.RemoteException;

    boolean removeUser(int i) throws android.os.RemoteException;

    boolean removeUserEvenWhenDisallowed(int i) throws android.os.RemoteException;

    int removeUserWhenPossible(int i, boolean z) throws android.os.RemoteException;

    boolean requestQuietModeEnabled(java.lang.String str, boolean z, int i, android.content.IntentSender intentSender, int i2) throws android.os.RemoteException;

    void revokeUserAdmin(int i) throws android.os.RemoteException;

    void setApplicationRestrictions(java.lang.String str, android.os.Bundle bundle, int i) throws android.os.RemoteException;

    void setBootUser(int i) throws android.os.RemoteException;

    void setDefaultGuestRestrictions(android.os.Bundle bundle) throws android.os.RemoteException;

    void setSeedAccountData(int i, java.lang.String str, java.lang.String str2, android.os.PersistableBundle persistableBundle, boolean z) throws android.os.RemoteException;

    void setUserAccount(int i, java.lang.String str) throws android.os.RemoteException;

    void setUserAdmin(int i) throws android.os.RemoteException;

    void setUserEnabled(int i) throws android.os.RemoteException;

    boolean setUserEphemeral(int i, boolean z) throws android.os.RemoteException;

    void setUserIcon(int i, android.graphics.Bitmap bitmap) throws android.os.RemoteException;

    void setUserName(int i, java.lang.String str) throws android.os.RemoteException;

    void setUserRestriction(java.lang.String str, boolean z, int i) throws android.os.RemoteException;

    boolean someUserHasAccount(java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    boolean someUserHasSeedAccount(java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    public static class Default implements android.os.IUserManager {
        @Override // android.os.IUserManager
        public int getCredentialOwnerProfile(int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.os.IUserManager
        public int getProfileParentId(int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.os.IUserManager
        public android.content.pm.UserInfo createUserWithThrow(java.lang.String str, java.lang.String str2, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IUserManager
        public android.content.pm.UserInfo preCreateUserWithThrow(java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IUserManager
        public android.content.pm.UserInfo createProfileForUserWithThrow(java.lang.String str, java.lang.String str2, int i, int i2, java.lang.String[] strArr) throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IUserManager
        public android.content.pm.UserInfo createRestrictedProfileWithThrow(java.lang.String str, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IUserManager
        public java.lang.String[] getPreInstallableSystemPackages(java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IUserManager
        public void setUserEnabled(int i) throws android.os.RemoteException {
        }

        @Override // android.os.IUserManager
        public void setUserAdmin(int i) throws android.os.RemoteException {
        }

        @Override // android.os.IUserManager
        public void revokeUserAdmin(int i) throws android.os.RemoteException {
        }

        @Override // android.os.IUserManager
        public void evictCredentialEncryptionKey(int i) throws android.os.RemoteException {
        }

        @Override // android.os.IUserManager
        public boolean removeUser(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.IUserManager
        public boolean removeUserEvenWhenDisallowed(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.IUserManager
        public void setUserName(int i, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.os.IUserManager
        public void setUserIcon(int i, android.graphics.Bitmap bitmap) throws android.os.RemoteException {
        }

        @Override // android.os.IUserManager
        public android.os.ParcelFileDescriptor getUserIcon(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IUserManager
        public android.content.pm.UserInfo getPrimaryUser() throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IUserManager
        public int getMainUserId() throws android.os.RemoteException {
            return 0;
        }

        @Override // android.os.IUserManager
        public int getCommunalProfileId() throws android.os.RemoteException {
            return 0;
        }

        @Override // android.os.IUserManager
        public int getPreviousFullUserToEnterForeground() throws android.os.RemoteException {
            return 0;
        }

        @Override // android.os.IUserManager
        public java.util.List<android.content.pm.UserInfo> getUsers(boolean z, boolean z2, boolean z3) throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IUserManager
        public java.util.List<android.content.pm.UserInfo> getProfiles(int i, boolean z) throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IUserManager
        public int[] getProfileIds(int i, boolean z) throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IUserManager
        public boolean isUserTypeEnabled(java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.IUserManager
        public boolean canAddMoreUsersOfType(java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.IUserManager
        public int getRemainingCreatableUserCount(java.lang.String str) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.os.IUserManager
        public int getRemainingCreatableProfileCount(java.lang.String str, int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.os.IUserManager
        public boolean canAddMoreProfilesToUser(java.lang.String str, int i, boolean z) throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.IUserManager
        public boolean canAddMoreManagedProfiles(int i, boolean z) throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.IUserManager
        public android.content.pm.UserInfo getProfileParent(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IUserManager
        public boolean isSameProfileGroup(int i, int i2) throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.IUserManager
        public boolean isHeadlessSystemUserMode() throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.IUserManager
        public boolean isUserOfType(int i, java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.IUserManager
        public android.content.pm.UserInfo getUserInfo(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IUserManager
        public android.content.pm.UserProperties getUserPropertiesCopy(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IUserManager
        public java.lang.String getUserAccount(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IUserManager
        public void setUserAccount(int i, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.os.IUserManager
        public long getUserCreationTime(int i) throws android.os.RemoteException {
            return 0L;
        }

        @Override // android.os.IUserManager
        public int getUserSwitchability(int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.os.IUserManager
        public boolean isUserSwitcherEnabled(boolean z, int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.IUserManager
        public boolean isRestricted(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.IUserManager
        public boolean canHaveRestrictedProfile(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.IUserManager
        public int getUserSerialNumber(int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.os.IUserManager
        public int getUserHandle(int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.os.IUserManager
        public int getUserRestrictionSource(java.lang.String str, int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.os.IUserManager
        public java.util.List<android.os.UserManager.EnforcingUser> getUserRestrictionSources(java.lang.String str, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IUserManager
        public android.os.Bundle getUserRestrictions(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IUserManager
        public boolean hasBaseUserRestriction(java.lang.String str, int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.IUserManager
        public boolean hasUserRestriction(java.lang.String str, int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.IUserManager
        public boolean hasUserRestrictionOnAnyUser(java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.IUserManager
        public boolean isSettingRestrictedForUser(java.lang.String str, int i, java.lang.String str2, int i2) throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.IUserManager
        public void addUserRestrictionsListener(android.os.IUserRestrictionsListener iUserRestrictionsListener) throws android.os.RemoteException {
        }

        @Override // android.os.IUserManager
        public void setUserRestriction(java.lang.String str, boolean z, int i) throws android.os.RemoteException {
        }

        @Override // android.os.IUserManager
        public void setApplicationRestrictions(java.lang.String str, android.os.Bundle bundle, int i) throws android.os.RemoteException {
        }

        @Override // android.os.IUserManager
        public android.os.Bundle getApplicationRestrictions(java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IUserManager
        public android.os.Bundle getApplicationRestrictionsForUser(java.lang.String str, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IUserManager
        public void setDefaultGuestRestrictions(android.os.Bundle bundle) throws android.os.RemoteException {
        }

        @Override // android.os.IUserManager
        public android.os.Bundle getDefaultGuestRestrictions() throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IUserManager
        public int removeUserWhenPossible(int i, boolean z) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.os.IUserManager
        public boolean markGuestForDeletion(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.IUserManager
        public java.util.List<android.content.pm.UserInfo> getGuestUsers() throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IUserManager
        public boolean isQuietModeEnabled(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.IUserManager
        public android.os.UserHandle createUserWithAttributes(java.lang.String str, java.lang.String str2, int i, android.graphics.Bitmap bitmap, java.lang.String str3, java.lang.String str4, android.os.PersistableBundle persistableBundle) throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IUserManager
        public void setSeedAccountData(int i, java.lang.String str, java.lang.String str2, android.os.PersistableBundle persistableBundle, boolean z) throws android.os.RemoteException {
        }

        @Override // android.os.IUserManager
        public java.lang.String getSeedAccountName(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IUserManager
        public java.lang.String getSeedAccountType(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IUserManager
        public android.os.PersistableBundle getSeedAccountOptions(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IUserManager
        public void clearSeedAccountData(int i) throws android.os.RemoteException {
        }

        @Override // android.os.IUserManager
        public boolean someUserHasSeedAccount(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.IUserManager
        public boolean someUserHasAccount(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.IUserManager
        public java.lang.String getProfileType(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IUserManager
        public boolean isDemoUser(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.IUserManager
        public boolean isAdminUser(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.IUserManager
        public boolean isPreCreated(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.IUserManager
        public android.content.pm.UserInfo createProfileForUserEvenWhenDisallowedWithThrow(java.lang.String str, java.lang.String str2, int i, int i2, java.lang.String[] strArr) throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IUserManager
        public boolean isUserUnlockingOrUnlocked(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.IUserManager
        public int getUserIconBadgeResId(int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.os.IUserManager
        public int getUserBadgeResId(int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.os.IUserManager
        public int getUserBadgeNoBackgroundResId(int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.os.IUserManager
        public int getUserBadgeLabelResId(int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.os.IUserManager
        public int getUserBadgeColorResId(int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.os.IUserManager
        public int getUserBadgeDarkColorResId(int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.os.IUserManager
        public int getUserStatusBarIconResId(int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.os.IUserManager
        public boolean hasBadge(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.IUserManager
        public int getProfileLabelResId(int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.os.IUserManager
        public boolean isUserUnlocked(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.IUserManager
        public boolean isUserRunning(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.IUserManager
        public boolean isUserForeground(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.IUserManager
        public boolean isUserVisible(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.IUserManager
        public int[] getVisibleUsers() throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IUserManager
        public int getMainDisplayIdAssignedToUser() throws android.os.RemoteException {
            return 0;
        }

        @Override // android.os.IUserManager
        public boolean isForegroundUserAdmin() throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.IUserManager
        public boolean isUserNameSet(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.IUserManager
        public boolean hasRestrictedProfiles(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.IUserManager
        public boolean requestQuietModeEnabled(java.lang.String str, boolean z, int i, android.content.IntentSender intentSender, int i2) throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.IUserManager
        public java.lang.String getUserName() throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IUserManager
        public long getUserStartRealtime() throws android.os.RemoteException {
            return 0L;
        }

        @Override // android.os.IUserManager
        public long getUserUnlockRealtime() throws android.os.RemoteException {
            return 0L;
        }

        @Override // android.os.IUserManager
        public boolean setUserEphemeral(int i, boolean z) throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.IUserManager
        public void setBootUser(int i) throws android.os.RemoteException {
        }

        @Override // android.os.IUserManager
        public int getBootUser() throws android.os.RemoteException {
            return 0;
        }

        @Override // android.os.IUserManager
        public int[] getProfileIdsExcludingHidden(int i, boolean z) throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.os.IUserManager {
        public static final java.lang.String DESCRIPTOR = "android.os.IUserManager";
        static final int TRANSACTION_addUserRestrictionsListener = 52;
        static final int TRANSACTION_canAddMoreManagedProfiles = 29;
        static final int TRANSACTION_canAddMoreProfilesToUser = 28;
        static final int TRANSACTION_canAddMoreUsersOfType = 25;
        static final int TRANSACTION_canHaveRestrictedProfile = 42;
        static final int TRANSACTION_clearSeedAccountData = 68;
        static final int TRANSACTION_createProfileForUserEvenWhenDisallowedWithThrow = 75;
        static final int TRANSACTION_createProfileForUserWithThrow = 5;
        static final int TRANSACTION_createRestrictedProfileWithThrow = 6;
        static final int TRANSACTION_createUserWithAttributes = 63;
        static final int TRANSACTION_createUserWithThrow = 3;
        static final int TRANSACTION_evictCredentialEncryptionKey = 11;
        static final int TRANSACTION_getApplicationRestrictions = 55;
        static final int TRANSACTION_getApplicationRestrictionsForUser = 56;
        static final int TRANSACTION_getBootUser = 101;
        static final int TRANSACTION_getCommunalProfileId = 19;
        static final int TRANSACTION_getCredentialOwnerProfile = 1;
        static final int TRANSACTION_getDefaultGuestRestrictions = 58;
        static final int TRANSACTION_getGuestUsers = 61;
        static final int TRANSACTION_getMainDisplayIdAssignedToUser = 91;
        static final int TRANSACTION_getMainUserId = 18;
        static final int TRANSACTION_getPreInstallableSystemPackages = 7;
        static final int TRANSACTION_getPreviousFullUserToEnterForeground = 20;
        static final int TRANSACTION_getPrimaryUser = 17;
        static final int TRANSACTION_getProfileIds = 23;
        static final int TRANSACTION_getProfileIdsExcludingHidden = 102;
        static final int TRANSACTION_getProfileLabelResId = 85;
        static final int TRANSACTION_getProfileParent = 30;
        static final int TRANSACTION_getProfileParentId = 2;
        static final int TRANSACTION_getProfileType = 71;
        static final int TRANSACTION_getProfiles = 22;
        static final int TRANSACTION_getRemainingCreatableProfileCount = 27;
        static final int TRANSACTION_getRemainingCreatableUserCount = 26;
        static final int TRANSACTION_getSeedAccountName = 65;
        static final int TRANSACTION_getSeedAccountOptions = 67;
        static final int TRANSACTION_getSeedAccountType = 66;
        static final int TRANSACTION_getUserAccount = 36;
        static final int TRANSACTION_getUserBadgeColorResId = 81;
        static final int TRANSACTION_getUserBadgeDarkColorResId = 82;
        static final int TRANSACTION_getUserBadgeLabelResId = 80;
        static final int TRANSACTION_getUserBadgeNoBackgroundResId = 79;
        static final int TRANSACTION_getUserBadgeResId = 78;
        static final int TRANSACTION_getUserCreationTime = 38;
        static final int TRANSACTION_getUserHandle = 44;
        static final int TRANSACTION_getUserIcon = 16;
        static final int TRANSACTION_getUserIconBadgeResId = 77;
        static final int TRANSACTION_getUserInfo = 34;
        static final int TRANSACTION_getUserName = 96;
        static final int TRANSACTION_getUserPropertiesCopy = 35;
        static final int TRANSACTION_getUserRestrictionSource = 45;
        static final int TRANSACTION_getUserRestrictionSources = 46;
        static final int TRANSACTION_getUserRestrictions = 47;
        static final int TRANSACTION_getUserSerialNumber = 43;
        static final int TRANSACTION_getUserStartRealtime = 97;
        static final int TRANSACTION_getUserStatusBarIconResId = 83;
        static final int TRANSACTION_getUserSwitchability = 39;
        static final int TRANSACTION_getUserUnlockRealtime = 98;
        static final int TRANSACTION_getUsers = 21;
        static final int TRANSACTION_getVisibleUsers = 90;
        static final int TRANSACTION_hasBadge = 84;
        static final int TRANSACTION_hasBaseUserRestriction = 48;
        static final int TRANSACTION_hasRestrictedProfiles = 94;
        static final int TRANSACTION_hasUserRestriction = 49;
        static final int TRANSACTION_hasUserRestrictionOnAnyUser = 50;
        static final int TRANSACTION_isAdminUser = 73;
        static final int TRANSACTION_isDemoUser = 72;
        static final int TRANSACTION_isForegroundUserAdmin = 92;
        static final int TRANSACTION_isHeadlessSystemUserMode = 32;
        static final int TRANSACTION_isPreCreated = 74;
        static final int TRANSACTION_isQuietModeEnabled = 62;
        static final int TRANSACTION_isRestricted = 41;
        static final int TRANSACTION_isSameProfileGroup = 31;
        static final int TRANSACTION_isSettingRestrictedForUser = 51;
        static final int TRANSACTION_isUserForeground = 88;
        static final int TRANSACTION_isUserNameSet = 93;
        static final int TRANSACTION_isUserOfType = 33;
        static final int TRANSACTION_isUserRunning = 87;
        static final int TRANSACTION_isUserSwitcherEnabled = 40;
        static final int TRANSACTION_isUserTypeEnabled = 24;
        static final int TRANSACTION_isUserUnlocked = 86;
        static final int TRANSACTION_isUserUnlockingOrUnlocked = 76;
        static final int TRANSACTION_isUserVisible = 89;
        static final int TRANSACTION_markGuestForDeletion = 60;
        static final int TRANSACTION_preCreateUserWithThrow = 4;
        static final int TRANSACTION_removeUser = 12;
        static final int TRANSACTION_removeUserEvenWhenDisallowed = 13;
        static final int TRANSACTION_removeUserWhenPossible = 59;
        static final int TRANSACTION_requestQuietModeEnabled = 95;
        static final int TRANSACTION_revokeUserAdmin = 10;
        static final int TRANSACTION_setApplicationRestrictions = 54;
        static final int TRANSACTION_setBootUser = 100;
        static final int TRANSACTION_setDefaultGuestRestrictions = 57;
        static final int TRANSACTION_setSeedAccountData = 64;
        static final int TRANSACTION_setUserAccount = 37;
        static final int TRANSACTION_setUserAdmin = 9;
        static final int TRANSACTION_setUserEnabled = 8;
        static final int TRANSACTION_setUserEphemeral = 99;
        static final int TRANSACTION_setUserIcon = 15;
        static final int TRANSACTION_setUserName = 14;
        static final int TRANSACTION_setUserRestriction = 53;
        static final int TRANSACTION_someUserHasAccount = 70;
        static final int TRANSACTION_someUserHasSeedAccount = 69;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.os.IUserManager asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.os.IUserManager)) {
                return (android.os.IUserManager) queryLocalInterface;
            }
            return new android.os.IUserManager.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getCredentialOwnerProfile";
                case 2:
                    return "getProfileParentId";
                case 3:
                    return "createUserWithThrow";
                case 4:
                    return "preCreateUserWithThrow";
                case 5:
                    return "createProfileForUserWithThrow";
                case 6:
                    return "createRestrictedProfileWithThrow";
                case 7:
                    return "getPreInstallableSystemPackages";
                case 8:
                    return "setUserEnabled";
                case 9:
                    return "setUserAdmin";
                case 10:
                    return "revokeUserAdmin";
                case 11:
                    return "evictCredentialEncryptionKey";
                case 12:
                    return "removeUser";
                case 13:
                    return "removeUserEvenWhenDisallowed";
                case 14:
                    return "setUserName";
                case 15:
                    return "setUserIcon";
                case 16:
                    return "getUserIcon";
                case 17:
                    return "getPrimaryUser";
                case 18:
                    return "getMainUserId";
                case 19:
                    return "getCommunalProfileId";
                case 20:
                    return "getPreviousFullUserToEnterForeground";
                case 21:
                    return "getUsers";
                case 22:
                    return "getProfiles";
                case 23:
                    return "getProfileIds";
                case 24:
                    return "isUserTypeEnabled";
                case 25:
                    return "canAddMoreUsersOfType";
                case 26:
                    return "getRemainingCreatableUserCount";
                case 27:
                    return "getRemainingCreatableProfileCount";
                case 28:
                    return "canAddMoreProfilesToUser";
                case 29:
                    return "canAddMoreManagedProfiles";
                case 30:
                    return "getProfileParent";
                case 31:
                    return "isSameProfileGroup";
                case 32:
                    return "isHeadlessSystemUserMode";
                case 33:
                    return "isUserOfType";
                case 34:
                    return "getUserInfo";
                case 35:
                    return "getUserPropertiesCopy";
                case 36:
                    return "getUserAccount";
                case 37:
                    return "setUserAccount";
                case 38:
                    return "getUserCreationTime";
                case 39:
                    return "getUserSwitchability";
                case 40:
                    return "isUserSwitcherEnabled";
                case 41:
                    return "isRestricted";
                case 42:
                    return "canHaveRestrictedProfile";
                case 43:
                    return "getUserSerialNumber";
                case 44:
                    return "getUserHandle";
                case 45:
                    return "getUserRestrictionSource";
                case 46:
                    return "getUserRestrictionSources";
                case 47:
                    return "getUserRestrictions";
                case 48:
                    return "hasBaseUserRestriction";
                case 49:
                    return "hasUserRestriction";
                case 50:
                    return "hasUserRestrictionOnAnyUser";
                case 51:
                    return "isSettingRestrictedForUser";
                case 52:
                    return "addUserRestrictionsListener";
                case 53:
                    return "setUserRestriction";
                case 54:
                    return "setApplicationRestrictions";
                case 55:
                    return "getApplicationRestrictions";
                case 56:
                    return "getApplicationRestrictionsForUser";
                case 57:
                    return "setDefaultGuestRestrictions";
                case 58:
                    return "getDefaultGuestRestrictions";
                case 59:
                    return "removeUserWhenPossible";
                case 60:
                    return "markGuestForDeletion";
                case 61:
                    return "getGuestUsers";
                case 62:
                    return "isQuietModeEnabled";
                case 63:
                    return "createUserWithAttributes";
                case 64:
                    return "setSeedAccountData";
                case 65:
                    return "getSeedAccountName";
                case 66:
                    return "getSeedAccountType";
                case 67:
                    return "getSeedAccountOptions";
                case 68:
                    return "clearSeedAccountData";
                case 69:
                    return "someUserHasSeedAccount";
                case 70:
                    return "someUserHasAccount";
                case 71:
                    return "getProfileType";
                case 72:
                    return "isDemoUser";
                case 73:
                    return "isAdminUser";
                case 74:
                    return "isPreCreated";
                case 75:
                    return "createProfileForUserEvenWhenDisallowedWithThrow";
                case 76:
                    return "isUserUnlockingOrUnlocked";
                case 77:
                    return "getUserIconBadgeResId";
                case 78:
                    return "getUserBadgeResId";
                case 79:
                    return "getUserBadgeNoBackgroundResId";
                case 80:
                    return "getUserBadgeLabelResId";
                case 81:
                    return "getUserBadgeColorResId";
                case 82:
                    return "getUserBadgeDarkColorResId";
                case 83:
                    return "getUserStatusBarIconResId";
                case 84:
                    return "hasBadge";
                case 85:
                    return "getProfileLabelResId";
                case 86:
                    return "isUserUnlocked";
                case 87:
                    return "isUserRunning";
                case 88:
                    return "isUserForeground";
                case 89:
                    return "isUserVisible";
                case 90:
                    return "getVisibleUsers";
                case 91:
                    return "getMainDisplayIdAssignedToUser";
                case 92:
                    return "isForegroundUserAdmin";
                case 93:
                    return "isUserNameSet";
                case 94:
                    return "hasRestrictedProfiles";
                case 95:
                    return "requestQuietModeEnabled";
                case 96:
                    return "getUserName";
                case 97:
                    return "getUserStartRealtime";
                case 98:
                    return "getUserUnlockRealtime";
                case 99:
                    return "setUserEphemeral";
                case 100:
                    return "setBootUser";
                case 101:
                    return "getBootUser";
                case 102:
                    return "getProfileIdsExcludingHidden";
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
                    parcel.enforceNoDataAvail();
                    int credentialOwnerProfile = getCredentialOwnerProfile(readInt);
                    parcel2.writeNoException();
                    parcel2.writeInt(credentialOwnerProfile);
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int profileParentId = getProfileParentId(readInt2);
                    parcel2.writeNoException();
                    parcel2.writeInt(profileParentId);
                    return true;
                case 3:
                    java.lang.String readString = parcel.readString();
                    java.lang.String readString2 = parcel.readString();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.content.pm.UserInfo createUserWithThrow = createUserWithThrow(readString, readString2, readInt3);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(createUserWithThrow, 1);
                    return true;
                case 4:
                    java.lang.String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.content.pm.UserInfo preCreateUserWithThrow = preCreateUserWithThrow(readString3);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(preCreateUserWithThrow, 1);
                    return true;
                case 5:
                    java.lang.String readString4 = parcel.readString();
                    java.lang.String readString5 = parcel.readString();
                    int readInt4 = parcel.readInt();
                    int readInt5 = parcel.readInt();
                    java.lang.String[] createStringArray = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    android.content.pm.UserInfo createProfileForUserWithThrow = createProfileForUserWithThrow(readString4, readString5, readInt4, readInt5, createStringArray);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(createProfileForUserWithThrow, 1);
                    return true;
                case 6:
                    java.lang.String readString6 = parcel.readString();
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.content.pm.UserInfo createRestrictedProfileWithThrow = createRestrictedProfileWithThrow(readString6, readInt6);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(createRestrictedProfileWithThrow, 1);
                    return true;
                case 7:
                    java.lang.String readString7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    java.lang.String[] preInstallableSystemPackages = getPreInstallableSystemPackages(readString7);
                    parcel2.writeNoException();
                    parcel2.writeStringArray(preInstallableSystemPackages);
                    return true;
                case 8:
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setUserEnabled(readInt7);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setUserAdmin(readInt8);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    revokeUserAdmin(readInt9);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    evictCredentialEncryptionKey(readInt10);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean removeUser = removeUser(readInt11);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(removeUser);
                    return true;
                case 13:
                    int readInt12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean removeUserEvenWhenDisallowed = removeUserEvenWhenDisallowed(readInt12);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(removeUserEvenWhenDisallowed);
                    return true;
                case 14:
                    int readInt13 = parcel.readInt();
                    java.lang.String readString8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setUserName(readInt13, readString8);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    int readInt14 = parcel.readInt();
                    android.graphics.Bitmap bitmap = (android.graphics.Bitmap) parcel.readTypedObject(android.graphics.Bitmap.CREATOR);
                    parcel.enforceNoDataAvail();
                    setUserIcon(readInt14, bitmap);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    int readInt15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.os.ParcelFileDescriptor userIcon = getUserIcon(readInt15);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(userIcon, 1);
                    return true;
                case 17:
                    android.content.pm.UserInfo primaryUser = getPrimaryUser();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(primaryUser, 1);
                    return true;
                case 18:
                    int mainUserId = getMainUserId();
                    parcel2.writeNoException();
                    parcel2.writeInt(mainUserId);
                    return true;
                case 19:
                    int communalProfileId = getCommunalProfileId();
                    parcel2.writeNoException();
                    parcel2.writeInt(communalProfileId);
                    return true;
                case 20:
                    int previousFullUserToEnterForeground = getPreviousFullUserToEnterForeground();
                    parcel2.writeNoException();
                    parcel2.writeInt(previousFullUserToEnterForeground);
                    return true;
                case 21:
                    boolean readBoolean = parcel.readBoolean();
                    boolean readBoolean2 = parcel.readBoolean();
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    java.util.List<android.content.pm.UserInfo> users = getUsers(readBoolean, readBoolean2, readBoolean3);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(users, 1);
                    return true;
                case 22:
                    int readInt16 = parcel.readInt();
                    boolean readBoolean4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    java.util.List<android.content.pm.UserInfo> profiles = getProfiles(readInt16, readBoolean4);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(profiles, 1);
                    return true;
                case 23:
                    int readInt17 = parcel.readInt();
                    boolean readBoolean5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int[] profileIds = getProfileIds(readInt17, readBoolean5);
                    parcel2.writeNoException();
                    parcel2.writeIntArray(profileIds);
                    return true;
                case 24:
                    java.lang.String readString9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isUserTypeEnabled = isUserTypeEnabled(readString9);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isUserTypeEnabled);
                    return true;
                case 25:
                    java.lang.String readString10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean canAddMoreUsersOfType = canAddMoreUsersOfType(readString10);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(canAddMoreUsersOfType);
                    return true;
                case 26:
                    java.lang.String readString11 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int remainingCreatableUserCount = getRemainingCreatableUserCount(readString11);
                    parcel2.writeNoException();
                    parcel2.writeInt(remainingCreatableUserCount);
                    return true;
                case 27:
                    java.lang.String readString12 = parcel.readString();
                    int readInt18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int remainingCreatableProfileCount = getRemainingCreatableProfileCount(readString12, readInt18);
                    parcel2.writeNoException();
                    parcel2.writeInt(remainingCreatableProfileCount);
                    return true;
                case 28:
                    java.lang.String readString13 = parcel.readString();
                    int readInt19 = parcel.readInt();
                    boolean readBoolean6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean canAddMoreProfilesToUser = canAddMoreProfilesToUser(readString13, readInt19, readBoolean6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(canAddMoreProfilesToUser);
                    return true;
                case 29:
                    int readInt20 = parcel.readInt();
                    boolean readBoolean7 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean canAddMoreManagedProfiles = canAddMoreManagedProfiles(readInt20, readBoolean7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(canAddMoreManagedProfiles);
                    return true;
                case 30:
                    int readInt21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.content.pm.UserInfo profileParent = getProfileParent(readInt21);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(profileParent, 1);
                    return true;
                case 31:
                    int readInt22 = parcel.readInt();
                    int readInt23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isSameProfileGroup = isSameProfileGroup(readInt22, readInt23);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSameProfileGroup);
                    return true;
                case 32:
                    boolean isHeadlessSystemUserMode = isHeadlessSystemUserMode();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isHeadlessSystemUserMode);
                    return true;
                case 33:
                    int readInt24 = parcel.readInt();
                    java.lang.String readString14 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isUserOfType = isUserOfType(readInt24, readString14);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isUserOfType);
                    return true;
                case 34:
                    int readInt25 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.content.pm.UserInfo userInfo = getUserInfo(readInt25);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(userInfo, 1);
                    return true;
                case 35:
                    int readInt26 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.content.pm.UserProperties userPropertiesCopy = getUserPropertiesCopy(readInt26);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(userPropertiesCopy, 1);
                    return true;
                case 36:
                    int readInt27 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.lang.String userAccount = getUserAccount(readInt27);
                    parcel2.writeNoException();
                    parcel2.writeString(userAccount);
                    return true;
                case 37:
                    int readInt28 = parcel.readInt();
                    java.lang.String readString15 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setUserAccount(readInt28, readString15);
                    parcel2.writeNoException();
                    return true;
                case 38:
                    int readInt29 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    long userCreationTime = getUserCreationTime(readInt29);
                    parcel2.writeNoException();
                    parcel2.writeLong(userCreationTime);
                    return true;
                case 39:
                    int readInt30 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int userSwitchability = getUserSwitchability(readInt30);
                    parcel2.writeNoException();
                    parcel2.writeInt(userSwitchability);
                    return true;
                case 40:
                    boolean readBoolean8 = parcel.readBoolean();
                    int readInt31 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isUserSwitcherEnabled = isUserSwitcherEnabled(readBoolean8, readInt31);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isUserSwitcherEnabled);
                    return true;
                case 41:
                    int readInt32 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isRestricted = isRestricted(readInt32);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isRestricted);
                    return true;
                case 42:
                    int readInt33 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean canHaveRestrictedProfile = canHaveRestrictedProfile(readInt33);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(canHaveRestrictedProfile);
                    return true;
                case 43:
                    int readInt34 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int userSerialNumber = getUserSerialNumber(readInt34);
                    parcel2.writeNoException();
                    parcel2.writeInt(userSerialNumber);
                    return true;
                case 44:
                    int readInt35 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int userHandle = getUserHandle(readInt35);
                    parcel2.writeNoException();
                    parcel2.writeInt(userHandle);
                    return true;
                case 45:
                    java.lang.String readString16 = parcel.readString();
                    int readInt36 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int userRestrictionSource = getUserRestrictionSource(readString16, readInt36);
                    parcel2.writeNoException();
                    parcel2.writeInt(userRestrictionSource);
                    return true;
                case 46:
                    java.lang.String readString17 = parcel.readString();
                    int readInt37 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.util.List<android.os.UserManager.EnforcingUser> userRestrictionSources = getUserRestrictionSources(readString17, readInt37);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(userRestrictionSources, 1);
                    return true;
                case 47:
                    int readInt38 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.os.Bundle userRestrictions = getUserRestrictions(readInt38);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(userRestrictions, 1);
                    return true;
                case 48:
                    java.lang.String readString18 = parcel.readString();
                    int readInt39 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean hasBaseUserRestriction = hasBaseUserRestriction(readString18, readInt39);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasBaseUserRestriction);
                    return true;
                case 49:
                    java.lang.String readString19 = parcel.readString();
                    int readInt40 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean hasUserRestriction = hasUserRestriction(readString19, readInt40);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasUserRestriction);
                    return true;
                case 50:
                    java.lang.String readString20 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean hasUserRestrictionOnAnyUser = hasUserRestrictionOnAnyUser(readString20);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasUserRestrictionOnAnyUser);
                    return true;
                case 51:
                    java.lang.String readString21 = parcel.readString();
                    int readInt41 = parcel.readInt();
                    java.lang.String readString22 = parcel.readString();
                    int readInt42 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isSettingRestrictedForUser = isSettingRestrictedForUser(readString21, readInt41, readString22, readInt42);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSettingRestrictedForUser);
                    return true;
                case 52:
                    android.os.IUserRestrictionsListener asInterface = android.os.IUserRestrictionsListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    addUserRestrictionsListener(asInterface);
                    parcel2.writeNoException();
                    return true;
                case 53:
                    java.lang.String readString23 = parcel.readString();
                    boolean readBoolean9 = parcel.readBoolean();
                    int readInt43 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setUserRestriction(readString23, readBoolean9, readInt43);
                    parcel2.writeNoException();
                    return true;
                case 54:
                    java.lang.String readString24 = parcel.readString();
                    android.os.Bundle bundle = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    int readInt44 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setApplicationRestrictions(readString24, bundle, readInt44);
                    parcel2.writeNoException();
                    return true;
                case 55:
                    java.lang.String readString25 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.os.Bundle applicationRestrictions = getApplicationRestrictions(readString25);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(applicationRestrictions, 1);
                    return true;
                case 56:
                    java.lang.String readString26 = parcel.readString();
                    int readInt45 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.os.Bundle applicationRestrictionsForUser = getApplicationRestrictionsForUser(readString26, readInt45);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(applicationRestrictionsForUser, 1);
                    return true;
                case 57:
                    android.os.Bundle bundle2 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    setDefaultGuestRestrictions(bundle2);
                    parcel2.writeNoException();
                    return true;
                case 58:
                    android.os.Bundle defaultGuestRestrictions = getDefaultGuestRestrictions();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(defaultGuestRestrictions, 1);
                    return true;
                case 59:
                    int readInt46 = parcel.readInt();
                    boolean readBoolean10 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int removeUserWhenPossible = removeUserWhenPossible(readInt46, readBoolean10);
                    parcel2.writeNoException();
                    parcel2.writeInt(removeUserWhenPossible);
                    return true;
                case 60:
                    int readInt47 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean markGuestForDeletion = markGuestForDeletion(readInt47);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(markGuestForDeletion);
                    return true;
                case 61:
                    java.util.List<android.content.pm.UserInfo> guestUsers = getGuestUsers();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(guestUsers, 1);
                    return true;
                case 62:
                    int readInt48 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isQuietModeEnabled = isQuietModeEnabled(readInt48);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isQuietModeEnabled);
                    return true;
                case 63:
                    java.lang.String readString27 = parcel.readString();
                    java.lang.String readString28 = parcel.readString();
                    int readInt49 = parcel.readInt();
                    android.graphics.Bitmap bitmap2 = (android.graphics.Bitmap) parcel.readTypedObject(android.graphics.Bitmap.CREATOR);
                    java.lang.String readString29 = parcel.readString();
                    java.lang.String readString30 = parcel.readString();
                    android.os.PersistableBundle persistableBundle = (android.os.PersistableBundle) parcel.readTypedObject(android.os.PersistableBundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    android.os.UserHandle createUserWithAttributes = createUserWithAttributes(readString27, readString28, readInt49, bitmap2, readString29, readString30, persistableBundle);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(createUserWithAttributes, 1);
                    return true;
                case 64:
                    int readInt50 = parcel.readInt();
                    java.lang.String readString31 = parcel.readString();
                    java.lang.String readString32 = parcel.readString();
                    android.os.PersistableBundle persistableBundle2 = (android.os.PersistableBundle) parcel.readTypedObject(android.os.PersistableBundle.CREATOR);
                    boolean readBoolean11 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setSeedAccountData(readInt50, readString31, readString32, persistableBundle2, readBoolean11);
                    parcel2.writeNoException();
                    return true;
                case 65:
                    int readInt51 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.lang.String seedAccountName = getSeedAccountName(readInt51);
                    parcel2.writeNoException();
                    parcel2.writeString(seedAccountName);
                    return true;
                case 66:
                    int readInt52 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.lang.String seedAccountType = getSeedAccountType(readInt52);
                    parcel2.writeNoException();
                    parcel2.writeString(seedAccountType);
                    return true;
                case 67:
                    int readInt53 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.os.PersistableBundle seedAccountOptions = getSeedAccountOptions(readInt53);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(seedAccountOptions, 1);
                    return true;
                case 68:
                    int readInt54 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    clearSeedAccountData(readInt54);
                    parcel2.writeNoException();
                    return true;
                case 69:
                    java.lang.String readString33 = parcel.readString();
                    java.lang.String readString34 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean someUserHasSeedAccount = someUserHasSeedAccount(readString33, readString34);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(someUserHasSeedAccount);
                    return true;
                case 70:
                    java.lang.String readString35 = parcel.readString();
                    java.lang.String readString36 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean someUserHasAccount = someUserHasAccount(readString35, readString36);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(someUserHasAccount);
                    return true;
                case 71:
                    int readInt55 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.lang.String profileType = getProfileType(readInt55);
                    parcel2.writeNoException();
                    parcel2.writeString(profileType);
                    return true;
                case 72:
                    int readInt56 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isDemoUser = isDemoUser(readInt56);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isDemoUser);
                    return true;
                case 73:
                    int readInt57 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isAdminUser = isAdminUser(readInt57);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isAdminUser);
                    return true;
                case 74:
                    int readInt58 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isPreCreated = isPreCreated(readInt58);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isPreCreated);
                    return true;
                case 75:
                    java.lang.String readString37 = parcel.readString();
                    java.lang.String readString38 = parcel.readString();
                    int readInt59 = parcel.readInt();
                    int readInt60 = parcel.readInt();
                    java.lang.String[] createStringArray2 = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    android.content.pm.UserInfo createProfileForUserEvenWhenDisallowedWithThrow = createProfileForUserEvenWhenDisallowedWithThrow(readString37, readString38, readInt59, readInt60, createStringArray2);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(createProfileForUserEvenWhenDisallowedWithThrow, 1);
                    return true;
                case 76:
                    int readInt61 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isUserUnlockingOrUnlocked = isUserUnlockingOrUnlocked(readInt61);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isUserUnlockingOrUnlocked);
                    return true;
                case 77:
                    int readInt62 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int userIconBadgeResId = getUserIconBadgeResId(readInt62);
                    parcel2.writeNoException();
                    parcel2.writeInt(userIconBadgeResId);
                    return true;
                case 78:
                    int readInt63 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int userBadgeResId = getUserBadgeResId(readInt63);
                    parcel2.writeNoException();
                    parcel2.writeInt(userBadgeResId);
                    return true;
                case 79:
                    int readInt64 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int userBadgeNoBackgroundResId = getUserBadgeNoBackgroundResId(readInt64);
                    parcel2.writeNoException();
                    parcel2.writeInt(userBadgeNoBackgroundResId);
                    return true;
                case 80:
                    int readInt65 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int userBadgeLabelResId = getUserBadgeLabelResId(readInt65);
                    parcel2.writeNoException();
                    parcel2.writeInt(userBadgeLabelResId);
                    return true;
                case 81:
                    int readInt66 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int userBadgeColorResId = getUserBadgeColorResId(readInt66);
                    parcel2.writeNoException();
                    parcel2.writeInt(userBadgeColorResId);
                    return true;
                case 82:
                    int readInt67 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int userBadgeDarkColorResId = getUserBadgeDarkColorResId(readInt67);
                    parcel2.writeNoException();
                    parcel2.writeInt(userBadgeDarkColorResId);
                    return true;
                case 83:
                    int readInt68 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int userStatusBarIconResId = getUserStatusBarIconResId(readInt68);
                    parcel2.writeNoException();
                    parcel2.writeInt(userStatusBarIconResId);
                    return true;
                case 84:
                    int readInt69 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean hasBadge = hasBadge(readInt69);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasBadge);
                    return true;
                case 85:
                    int readInt70 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int profileLabelResId = getProfileLabelResId(readInt70);
                    parcel2.writeNoException();
                    parcel2.writeInt(profileLabelResId);
                    return true;
                case 86:
                    int readInt71 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isUserUnlocked = isUserUnlocked(readInt71);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isUserUnlocked);
                    return true;
                case 87:
                    int readInt72 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isUserRunning = isUserRunning(readInt72);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isUserRunning);
                    return true;
                case 88:
                    int readInt73 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isUserForeground = isUserForeground(readInt73);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isUserForeground);
                    return true;
                case 89:
                    int readInt74 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isUserVisible = isUserVisible(readInt74);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isUserVisible);
                    return true;
                case 90:
                    int[] visibleUsers = getVisibleUsers();
                    parcel2.writeNoException();
                    parcel2.writeIntArray(visibleUsers);
                    return true;
                case 91:
                    int mainDisplayIdAssignedToUser = getMainDisplayIdAssignedToUser();
                    parcel2.writeNoException();
                    parcel2.writeInt(mainDisplayIdAssignedToUser);
                    return true;
                case 92:
                    boolean isForegroundUserAdmin = isForegroundUserAdmin();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isForegroundUserAdmin);
                    return true;
                case 93:
                    int readInt75 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isUserNameSet = isUserNameSet(readInt75);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isUserNameSet);
                    return true;
                case 94:
                    int readInt76 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean hasRestrictedProfiles = hasRestrictedProfiles(readInt76);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasRestrictedProfiles);
                    return true;
                case 95:
                    java.lang.String readString39 = parcel.readString();
                    boolean readBoolean12 = parcel.readBoolean();
                    int readInt77 = parcel.readInt();
                    android.content.IntentSender intentSender = (android.content.IntentSender) parcel.readTypedObject(android.content.IntentSender.CREATOR);
                    int readInt78 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean requestQuietModeEnabled = requestQuietModeEnabled(readString39, readBoolean12, readInt77, intentSender, readInt78);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(requestQuietModeEnabled);
                    return true;
                case 96:
                    java.lang.String userName = getUserName();
                    parcel2.writeNoException();
                    parcel2.writeString(userName);
                    return true;
                case 97:
                    long userStartRealtime = getUserStartRealtime();
                    parcel2.writeNoException();
                    parcel2.writeLong(userStartRealtime);
                    return true;
                case 98:
                    long userUnlockRealtime = getUserUnlockRealtime();
                    parcel2.writeNoException();
                    parcel2.writeLong(userUnlockRealtime);
                    return true;
                case 99:
                    int readInt79 = parcel.readInt();
                    boolean readBoolean13 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean userEphemeral = setUserEphemeral(readInt79, readBoolean13);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(userEphemeral);
                    return true;
                case 100:
                    int readInt80 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setBootUser(readInt80);
                    parcel2.writeNoException();
                    return true;
                case 101:
                    int bootUser = getBootUser();
                    parcel2.writeNoException();
                    parcel2.writeInt(bootUser);
                    return true;
                case 102:
                    int readInt81 = parcel.readInt();
                    boolean readBoolean14 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int[] profileIdsExcludingHidden = getProfileIdsExcludingHidden(readInt81, readBoolean14);
                    parcel2.writeNoException();
                    parcel2.writeIntArray(profileIdsExcludingHidden);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.os.IUserManager {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.os.IUserManager.Stub.DESCRIPTOR;
            }

            @Override // android.os.IUserManager
            public int getCredentialOwnerProfile(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IUserManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public int getProfileParentId(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IUserManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public android.content.pm.UserInfo createUserWithThrow(java.lang.String str, java.lang.String str2, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IUserManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.pm.UserInfo) obtain2.readTypedObject(android.content.pm.UserInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public android.content.pm.UserInfo preCreateUserWithThrow(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IUserManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.pm.UserInfo) obtain2.readTypedObject(android.content.pm.UserInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public android.content.pm.UserInfo createProfileForUserWithThrow(java.lang.String str, java.lang.String str2, int i, int i2, java.lang.String[] strArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IUserManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeStringArray(strArr);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.pm.UserInfo) obtain2.readTypedObject(android.content.pm.UserInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public android.content.pm.UserInfo createRestrictedProfileWithThrow(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IUserManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.pm.UserInfo) obtain2.readTypedObject(android.content.pm.UserInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public java.lang.String[] getPreInstallableSystemPackages(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IUserManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public void setUserEnabled(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IUserManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public void setUserAdmin(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IUserManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public void revokeUserAdmin(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IUserManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public void evictCredentialEncryptionKey(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IUserManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public boolean removeUser(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IUserManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public boolean removeUserEvenWhenDisallowed(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IUserManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public void setUserName(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IUserManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public void setUserIcon(int i, android.graphics.Bitmap bitmap) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IUserManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(bitmap, 0);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public android.os.ParcelFileDescriptor getUserIcon(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IUserManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.os.ParcelFileDescriptor) obtain2.readTypedObject(android.os.ParcelFileDescriptor.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public android.content.pm.UserInfo getPrimaryUser() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IUserManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.pm.UserInfo) obtain2.readTypedObject(android.content.pm.UserInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public int getMainUserId() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IUserManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public int getCommunalProfileId() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IUserManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public int getPreviousFullUserToEnterForeground() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IUserManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public java.util.List<android.content.pm.UserInfo> getUsers(boolean z, boolean z2, boolean z3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IUserManager.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    obtain.writeBoolean(z3);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.content.pm.UserInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public java.util.List<android.content.pm.UserInfo> getProfiles(int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IUserManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.content.pm.UserInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public int[] getProfileIds(int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IUserManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public boolean isUserTypeEnabled(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IUserManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public boolean canAddMoreUsersOfType(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IUserManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public int getRemainingCreatableUserCount(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IUserManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public int getRemainingCreatableProfileCount(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IUserManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public boolean canAddMoreProfilesToUser(java.lang.String str, int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IUserManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public boolean canAddMoreManagedProfiles(int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IUserManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public android.content.pm.UserInfo getProfileParent(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IUserManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.pm.UserInfo) obtain2.readTypedObject(android.content.pm.UserInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public boolean isSameProfileGroup(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IUserManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public boolean isHeadlessSystemUserMode() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IUserManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public boolean isUserOfType(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IUserManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public android.content.pm.UserInfo getUserInfo(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IUserManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.pm.UserInfo) obtain2.readTypedObject(android.content.pm.UserInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public android.content.pm.UserProperties getUserPropertiesCopy(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IUserManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.pm.UserProperties) obtain2.readTypedObject(android.content.pm.UserProperties.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public java.lang.String getUserAccount(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IUserManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public void setUserAccount(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IUserManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(37, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public long getUserCreationTime(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IUserManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(38, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public int getUserSwitchability(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IUserManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(39, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public boolean isUserSwitcherEnabled(boolean z, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IUserManager.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(40, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public boolean isRestricted(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IUserManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(41, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public boolean canHaveRestrictedProfile(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IUserManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(42, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public int getUserSerialNumber(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IUserManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(43, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public int getUserHandle(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IUserManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(44, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public int getUserRestrictionSource(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IUserManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(45, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public java.util.List<android.os.UserManager.EnforcingUser> getUserRestrictionSources(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IUserManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(46, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.os.UserManager.EnforcingUser.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public android.os.Bundle getUserRestrictions(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IUserManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(47, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.os.Bundle) obtain2.readTypedObject(android.os.Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public boolean hasBaseUserRestriction(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IUserManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(48, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public boolean hasUserRestriction(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IUserManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(49, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public boolean hasUserRestrictionOnAnyUser(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IUserManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(50, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public boolean isSettingRestrictedForUser(java.lang.String str, int i, java.lang.String str2, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IUserManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    obtain.writeInt(i2);
                    this.mRemote.transact(51, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public void addUserRestrictionsListener(android.os.IUserRestrictionsListener iUserRestrictionsListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IUserManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iUserRestrictionsListener);
                    this.mRemote.transact(52, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public void setUserRestriction(java.lang.String str, boolean z, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IUserManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(53, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public void setApplicationRestrictions(java.lang.String str, android.os.Bundle bundle, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IUserManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(54, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public android.os.Bundle getApplicationRestrictions(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IUserManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(55, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.os.Bundle) obtain2.readTypedObject(android.os.Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public android.os.Bundle getApplicationRestrictionsForUser(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IUserManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(56, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.os.Bundle) obtain2.readTypedObject(android.os.Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public void setDefaultGuestRestrictions(android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IUserManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(57, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public android.os.Bundle getDefaultGuestRestrictions() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IUserManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(58, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.os.Bundle) obtain2.readTypedObject(android.os.Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public int removeUserWhenPossible(int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IUserManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(59, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public boolean markGuestForDeletion(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IUserManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(60, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public java.util.List<android.content.pm.UserInfo> getGuestUsers() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IUserManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(61, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.content.pm.UserInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public boolean isQuietModeEnabled(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IUserManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(62, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public android.os.UserHandle createUserWithAttributes(java.lang.String str, java.lang.String str2, int i, android.graphics.Bitmap bitmap, java.lang.String str3, java.lang.String str4, android.os.PersistableBundle persistableBundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IUserManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(bitmap, 0);
                    obtain.writeString(str3);
                    obtain.writeString(str4);
                    obtain.writeTypedObject(persistableBundle, 0);
                    this.mRemote.transact(63, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.os.UserHandle) obtain2.readTypedObject(android.os.UserHandle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public void setSeedAccountData(int i, java.lang.String str, java.lang.String str2, android.os.PersistableBundle persistableBundle, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IUserManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(persistableBundle, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(64, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public java.lang.String getSeedAccountName(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IUserManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(65, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public java.lang.String getSeedAccountType(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IUserManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(66, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public android.os.PersistableBundle getSeedAccountOptions(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IUserManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(67, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.os.PersistableBundle) obtain2.readTypedObject(android.os.PersistableBundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public void clearSeedAccountData(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IUserManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(68, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public boolean someUserHasSeedAccount(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IUserManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(69, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public boolean someUserHasAccount(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IUserManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(70, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public java.lang.String getProfileType(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IUserManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(71, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public boolean isDemoUser(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IUserManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(72, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public boolean isAdminUser(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IUserManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(73, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public boolean isPreCreated(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IUserManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(74, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public android.content.pm.UserInfo createProfileForUserEvenWhenDisallowedWithThrow(java.lang.String str, java.lang.String str2, int i, int i2, java.lang.String[] strArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IUserManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeStringArray(strArr);
                    this.mRemote.transact(75, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.pm.UserInfo) obtain2.readTypedObject(android.content.pm.UserInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public boolean isUserUnlockingOrUnlocked(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IUserManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(76, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public int getUserIconBadgeResId(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IUserManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(77, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public int getUserBadgeResId(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IUserManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(78, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public int getUserBadgeNoBackgroundResId(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IUserManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(79, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public int getUserBadgeLabelResId(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IUserManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(80, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public int getUserBadgeColorResId(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IUserManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(81, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public int getUserBadgeDarkColorResId(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IUserManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(82, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public int getUserStatusBarIconResId(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IUserManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(83, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public boolean hasBadge(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IUserManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(84, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public int getProfileLabelResId(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IUserManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(85, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public boolean isUserUnlocked(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IUserManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(86, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public boolean isUserRunning(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IUserManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(87, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public boolean isUserForeground(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IUserManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(88, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public boolean isUserVisible(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IUserManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(89, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public int[] getVisibleUsers() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IUserManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(90, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public int getMainDisplayIdAssignedToUser() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IUserManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(91, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public boolean isForegroundUserAdmin() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IUserManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(92, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public boolean isUserNameSet(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IUserManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(93, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public boolean hasRestrictedProfiles(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IUserManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(94, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public boolean requestQuietModeEnabled(java.lang.String str, boolean z, int i, android.content.IntentSender intentSender, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IUserManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(intentSender, 0);
                    obtain.writeInt(i2);
                    this.mRemote.transact(95, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public java.lang.String getUserName() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IUserManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(96, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public long getUserStartRealtime() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IUserManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(97, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public long getUserUnlockRealtime() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IUserManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(98, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public boolean setUserEphemeral(int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IUserManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(99, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public void setBootUser(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IUserManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(100, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public int getBootUser() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IUserManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(101, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUserManager
            public int[] getProfileIdsExcludingHidden(int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IUserManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(102, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 101;
        }
    }
}
