package android.app.admin;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class ManagedProfileProvisioningParams implements android.os.Parcelable {
    private static final java.lang.String ACCOUNT_TO_MIGRATE_PROVIDED_PARAM = "ACCOUNT_TO_MIGRATE_PROVIDED";
    public static final android.os.Parcelable.Creator<android.app.admin.ManagedProfileProvisioningParams> CREATOR = new android.os.Parcelable.Creator<android.app.admin.ManagedProfileProvisioningParams>() { // from class: android.app.admin.ManagedProfileProvisioningParams.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.admin.ManagedProfileProvisioningParams createFromParcel(android.os.Parcel parcel) {
            return new android.app.admin.ManagedProfileProvisioningParams((android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR), parcel.readString(), parcel.readString(), (android.accounts.Account) parcel.readTypedObject(android.accounts.Account.CREATOR), parcel.readBoolean(), parcel.readBoolean(), parcel.readBoolean(), parcel.readPersistableBundle());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.admin.ManagedProfileProvisioningParams[] newArray(int i) {
            return new android.app.admin.ManagedProfileProvisioningParams[i];
        }
    };
    private static final java.lang.String KEEP_MIGRATED_ACCOUNT_PARAM = "KEEP_MIGRATED_ACCOUNT";
    private static final java.lang.String LEAVE_ALL_SYSTEM_APPS_ENABLED_PARAM = "LEAVE_ALL_SYSTEM_APPS_ENABLED";
    private static final java.lang.String ORGANIZATION_OWNED_PROVISIONING_PARAM = "ORGANIZATION_OWNED_PROVISIONING";
    private final android.accounts.Account mAccountToMigrate;
    private final android.os.PersistableBundle mAdminExtras;
    private final boolean mKeepAccountOnMigration;
    private final boolean mLeaveAllSystemAppsEnabled;
    private final boolean mOrganizationOwnedProvisioning;
    private final java.lang.String mOwnerName;
    private final android.content.ComponentName mProfileAdminComponentName;
    private final java.lang.String mProfileName;

    private ManagedProfileProvisioningParams(android.content.ComponentName componentName, java.lang.String str, java.lang.String str2, android.accounts.Account account, boolean z, boolean z2, boolean z3, android.os.PersistableBundle persistableBundle) {
        this.mProfileAdminComponentName = (android.content.ComponentName) java.util.Objects.requireNonNull(componentName);
        this.mOwnerName = (java.lang.String) java.util.Objects.requireNonNull(str);
        this.mProfileName = str2;
        this.mAccountToMigrate = account;
        this.mLeaveAllSystemAppsEnabled = z;
        this.mOrganizationOwnedProvisioning = z2;
        this.mKeepAccountOnMigration = z3;
        this.mAdminExtras = persistableBundle;
    }

    public android.content.ComponentName getProfileAdminComponentName() {
        return this.mProfileAdminComponentName;
    }

    public java.lang.String getOwnerName() {
        return this.mOwnerName;
    }

    public java.lang.String getProfileName() {
        return this.mProfileName;
    }

    public android.accounts.Account getAccountToMigrate() {
        return this.mAccountToMigrate;
    }

    public boolean isLeaveAllSystemAppsEnabled() {
        return this.mLeaveAllSystemAppsEnabled;
    }

    public boolean isOrganizationOwnedProvisioning() {
        return this.mOrganizationOwnedProvisioning;
    }

    public boolean isKeepingAccountOnMigration() {
        return this.mKeepAccountOnMigration;
    }

    public android.os.PersistableBundle getAdminExtras() {
        return new android.os.PersistableBundle(this.mAdminExtras);
    }

    public void logParams(java.lang.String str) {
        java.util.Objects.requireNonNull(str);
        logParam(str, LEAVE_ALL_SYSTEM_APPS_ENABLED_PARAM, this.mLeaveAllSystemAppsEnabled);
        logParam(str, ORGANIZATION_OWNED_PROVISIONING_PARAM, this.mOrganizationOwnedProvisioning);
        logParam(str, KEEP_MIGRATED_ACCOUNT_PARAM, this.mKeepAccountOnMigration);
        logParam(str, ACCOUNT_TO_MIGRATE_PROVIDED_PARAM, this.mAccountToMigrate != null);
    }

    private void logParam(java.lang.String str, java.lang.String str2, boolean z) {
        android.app.admin.DevicePolicyEventLogger.createEvent(197).setStrings(str).setAdmin(this.mProfileAdminComponentName).setStrings(str2).setBoolean(z).write();
    }

    public static final class Builder {
        private android.accounts.Account mAccountToMigrate;
        private android.os.PersistableBundle mAdminExtras;
        private boolean mKeepingAccountOnMigration;
        private boolean mLeaveAllSystemAppsEnabled;
        private boolean mOrganizationOwnedProvisioning;
        private final java.lang.String mOwnerName;
        private final android.content.ComponentName mProfileAdminComponentName;
        private java.lang.String mProfileName;

        public Builder(android.content.ComponentName componentName, java.lang.String str) {
            java.util.Objects.requireNonNull(componentName);
            java.util.Objects.requireNonNull(str);
            this.mProfileAdminComponentName = componentName;
            this.mOwnerName = str;
        }

        public android.app.admin.ManagedProfileProvisioningParams.Builder setProfileName(java.lang.String str) {
            this.mProfileName = str;
            return this;
        }

        public android.app.admin.ManagedProfileProvisioningParams.Builder setAccountToMigrate(android.accounts.Account account) {
            this.mAccountToMigrate = account;
            return this;
        }

        public android.app.admin.ManagedProfileProvisioningParams.Builder setLeaveAllSystemAppsEnabled(boolean z) {
            this.mLeaveAllSystemAppsEnabled = z;
            return this;
        }

        public android.app.admin.ManagedProfileProvisioningParams.Builder setOrganizationOwnedProvisioning(boolean z) {
            this.mOrganizationOwnedProvisioning = z;
            return this;
        }

        public android.app.admin.ManagedProfileProvisioningParams.Builder setKeepingAccountOnMigration(boolean z) {
            this.mKeepingAccountOnMigration = z;
            return this;
        }

        public android.app.admin.ManagedProfileProvisioningParams.Builder setAdminExtras(android.os.PersistableBundle persistableBundle) {
            android.os.PersistableBundle persistableBundle2;
            if (persistableBundle != null) {
                persistableBundle2 = new android.os.PersistableBundle(persistableBundle);
            } else {
                persistableBundle2 = new android.os.PersistableBundle();
            }
            this.mAdminExtras = persistableBundle2;
            return this;
        }

        public android.app.admin.ManagedProfileProvisioningParams build() {
            return new android.app.admin.ManagedProfileProvisioningParams(this.mProfileAdminComponentName, this.mOwnerName, this.mProfileName, this.mAccountToMigrate, this.mLeaveAllSystemAppsEnabled, this.mOrganizationOwnedProvisioning, this.mKeepingAccountOnMigration, this.mAdminExtras != null ? this.mAdminExtras : new android.os.PersistableBundle());
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public java.lang.String toString() {
        return "ManagedProfileProvisioningParams{mProfileAdminComponentName=" + this.mProfileAdminComponentName + ", mOwnerName=" + this.mOwnerName + ", mProfileName=" + (this.mProfileName == null ? "null" : this.mProfileName) + ", mAccountToMigrate=" + (this.mAccountToMigrate != null ? this.mAccountToMigrate : "null") + ", mLeaveAllSystemAppsEnabled=" + this.mLeaveAllSystemAppsEnabled + ", mOrganizationOwnedProvisioning=" + this.mOrganizationOwnedProvisioning + ", mKeepAccountOnMigration=" + this.mKeepAccountOnMigration + ", mAdminExtras=" + this.mAdminExtras + '}';
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeTypedObject(this.mProfileAdminComponentName, i);
        parcel.writeString(this.mOwnerName);
        parcel.writeString(this.mProfileName);
        parcel.writeTypedObject(this.mAccountToMigrate, i);
        parcel.writeBoolean(this.mLeaveAllSystemAppsEnabled);
        parcel.writeBoolean(this.mOrganizationOwnedProvisioning);
        parcel.writeBoolean(this.mKeepAccountOnMigration);
        parcel.writePersistableBundle(this.mAdminExtras);
    }
}
