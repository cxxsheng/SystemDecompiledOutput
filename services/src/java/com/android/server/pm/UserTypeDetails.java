package com.android.server.pm;

/* loaded from: classes2.dex */
public final class UserTypeDetails {
    public static final int UNLIMITED_NUMBER_OF_USERS = -1;

    @android.annotation.Nullable
    private final int[] mBadgeColors;

    @android.annotation.Nullable
    private final int[] mBadgeLabels;
    private final int mBadgeNoBackground;
    private final int mBadgePlain;
    private final int mBaseType;

    @android.annotation.Nullable
    private final int[] mDarkThemeBadgeColors;

    @android.annotation.Nullable
    private final java.util.List<com.android.server.pm.DefaultCrossProfileIntentFilter> mDefaultCrossProfileIntentFilters;

    @android.annotation.Nullable
    private final android.os.Bundle mDefaultRestrictions;

    @android.annotation.Nullable
    private final android.os.Bundle mDefaultSecureSettings;

    @android.annotation.Nullable
    private final android.os.Bundle mDefaultSystemSettings;
    private final int mDefaultUserInfoPropertyFlags;

    @android.annotation.NonNull
    private final android.content.pm.UserProperties mDefaultUserProperties;
    private final boolean mEnabled;
    private final int mIconBadge;

    @android.annotation.Nullable
    private final int[] mLabels;
    private final int mMaxAllowed;
    private final int mMaxAllowedPerParent;

    @android.annotation.NonNull
    private final java.lang.String mName;
    private final int mStatusBarIcon;

    private UserTypeDetails(@android.annotation.NonNull java.lang.String str, boolean z, int i, int i2, int i3, @android.annotation.Nullable int[] iArr, int i4, int i5, int i6, int i7, int i8, @android.annotation.Nullable int[] iArr2, @android.annotation.Nullable int[] iArr3, @android.annotation.Nullable int[] iArr4, @android.annotation.Nullable android.os.Bundle bundle, @android.annotation.Nullable android.os.Bundle bundle2, @android.annotation.Nullable android.os.Bundle bundle3, @android.annotation.Nullable java.util.List<com.android.server.pm.DefaultCrossProfileIntentFilter> list, @android.annotation.NonNull android.content.pm.UserProperties userProperties) {
        this.mName = str;
        this.mEnabled = z;
        this.mMaxAllowed = i;
        this.mMaxAllowedPerParent = i4;
        this.mBaseType = i2;
        this.mDefaultUserInfoPropertyFlags = i3;
        this.mDefaultRestrictions = bundle;
        this.mDefaultSystemSettings = bundle2;
        this.mDefaultSecureSettings = bundle3;
        this.mDefaultCrossProfileIntentFilters = list;
        this.mIconBadge = i5;
        this.mBadgePlain = i6;
        this.mBadgeNoBackground = i7;
        this.mStatusBarIcon = i8;
        this.mLabels = iArr;
        this.mBadgeLabels = iArr2;
        this.mBadgeColors = iArr3;
        this.mDarkThemeBadgeColors = iArr4;
        this.mDefaultUserProperties = userProperties;
    }

    public java.lang.String getName() {
        return this.mName;
    }

    public boolean isEnabled() {
        return this.mEnabled;
    }

    public int getMaxAllowed() {
        return this.mMaxAllowed;
    }

    public int getMaxAllowedPerParent() {
        return this.mMaxAllowedPerParent;
    }

    public int getDefaultUserInfoFlags() {
        return this.mDefaultUserInfoPropertyFlags | this.mBaseType;
    }

    public int getLabel(int i) {
        if (this.mLabels == null || this.mLabels.length == 0 || i < 0) {
            return 0;
        }
        return this.mLabels[java.lang.Math.min(i, this.mLabels.length - 1)];
    }

    public boolean hasBadge() {
        return this.mIconBadge != 0;
    }

    public int getIconBadge() {
        return this.mIconBadge;
    }

    public int getBadgePlain() {
        return this.mBadgePlain;
    }

    public int getBadgeNoBackground() {
        return this.mBadgeNoBackground;
    }

    public int getStatusBarIcon() {
        return this.mStatusBarIcon;
    }

    public int getBadgeLabel(int i) {
        if (this.mBadgeLabels == null || this.mBadgeLabels.length == 0 || i < 0) {
            return 0;
        }
        return this.mBadgeLabels[java.lang.Math.min(i, this.mBadgeLabels.length - 1)];
    }

    public int getBadgeColor(int i) {
        if (this.mBadgeColors == null || this.mBadgeColors.length == 0 || i < 0) {
            return 0;
        }
        return this.mBadgeColors[java.lang.Math.min(i, this.mBadgeColors.length - 1)];
    }

    public int getDarkThemeBadgeColor(int i) {
        if (this.mDarkThemeBadgeColors == null || this.mDarkThemeBadgeColors.length == 0 || i < 0) {
            return getBadgeColor(i);
        }
        return this.mDarkThemeBadgeColors[java.lang.Math.min(i, this.mDarkThemeBadgeColors.length - 1)];
    }

    @android.annotation.NonNull
    public android.content.pm.UserProperties getDefaultUserPropertiesReference() {
        return this.mDefaultUserProperties;
    }

    public boolean isProfile() {
        return (this.mBaseType & 4096) != 0;
    }

    public boolean isFull() {
        return (this.mBaseType & 1024) != 0;
    }

    public boolean isSystem() {
        return (this.mBaseType & 2048) != 0;
    }

    @android.annotation.NonNull
    android.os.Bundle getDefaultRestrictions() {
        return com.android.server.BundleUtils.clone(this.mDefaultRestrictions);
    }

    public void addDefaultRestrictionsTo(@android.annotation.NonNull android.os.Bundle bundle) {
        com.android.server.pm.UserRestrictionsUtils.merge(bundle, this.mDefaultRestrictions);
    }

    @android.annotation.NonNull
    android.os.Bundle getDefaultSystemSettings() {
        return com.android.server.BundleUtils.clone(this.mDefaultSystemSettings);
    }

    @android.annotation.NonNull
    android.os.Bundle getDefaultSecureSettings() {
        return com.android.server.BundleUtils.clone(this.mDefaultSecureSettings);
    }

    @android.annotation.NonNull
    java.util.List<com.android.server.pm.DefaultCrossProfileIntentFilter> getDefaultCrossProfileIntentFilters() {
        if (this.mDefaultCrossProfileIntentFilters != null) {
            return new java.util.ArrayList(this.mDefaultCrossProfileIntentFilters);
        }
        return java.util.Collections.emptyList();
    }

    public void dump(java.io.PrintWriter printWriter, java.lang.String str) {
        printWriter.print(str);
        printWriter.print("mName: ");
        printWriter.println(this.mName);
        printWriter.print(str);
        printWriter.print("mBaseType: ");
        printWriter.println(android.content.pm.UserInfo.flagsToString(this.mBaseType));
        printWriter.print(str);
        printWriter.print("mEnabled: ");
        printWriter.println(this.mEnabled);
        printWriter.print(str);
        printWriter.print("mMaxAllowed: ");
        printWriter.println(this.mMaxAllowed);
        printWriter.print(str);
        printWriter.print("mMaxAllowedPerParent: ");
        printWriter.println(this.mMaxAllowedPerParent);
        printWriter.print(str);
        printWriter.print("mDefaultUserInfoFlags: ");
        printWriter.println(android.content.pm.UserInfo.flagsToString(this.mDefaultUserInfoPropertyFlags));
        this.mDefaultUserProperties.println(printWriter, str);
        java.lang.String str2 = str + "    ";
        if (isSystem()) {
            printWriter.print(str);
            printWriter.println("config_defaultFirstUserRestrictions: ");
            try {
                android.os.Bundle bundle = new android.os.Bundle();
                for (java.lang.String str3 : android.content.res.Resources.getSystem().getStringArray(android.R.array.config_defaultAmbientContextServices)) {
                    if (com.android.server.pm.UserRestrictionsUtils.isValidRestriction(str3)) {
                        bundle.putBoolean(str3, true);
                    }
                }
                com.android.server.pm.UserRestrictionsUtils.dumpRestrictions(printWriter, str2, bundle);
            } catch (android.content.res.Resources.NotFoundException e) {
                printWriter.print(str2);
                printWriter.println("none - resource not found");
            }
        } else {
            printWriter.print(str);
            printWriter.println("mDefaultRestrictions: ");
            com.android.server.pm.UserRestrictionsUtils.dumpRestrictions(printWriter, str2, this.mDefaultRestrictions);
        }
        printWriter.print(str);
        printWriter.print("mIconBadge: ");
        printWriter.println(this.mIconBadge);
        printWriter.print(str);
        printWriter.print("mBadgePlain: ");
        printWriter.println(this.mBadgePlain);
        printWriter.print(str);
        printWriter.print("mBadgeNoBackground: ");
        printWriter.println(this.mBadgeNoBackground);
        printWriter.print(str);
        printWriter.print("mStatusBarIcon: ");
        printWriter.println(this.mStatusBarIcon);
        printWriter.print(str);
        printWriter.print("mBadgeLabels.length: ");
        printWriter.println(this.mBadgeLabels != null ? java.lang.Integer.valueOf(this.mBadgeLabels.length) : "0(null)");
        printWriter.print(str);
        printWriter.print("mBadgeColors.length: ");
        printWriter.println(this.mBadgeColors != null ? java.lang.Integer.valueOf(this.mBadgeColors.length) : "0(null)");
        printWriter.print(str);
        printWriter.print("mDarkThemeBadgeColors.length: ");
        printWriter.println(this.mDarkThemeBadgeColors != null ? java.lang.Integer.valueOf(this.mDarkThemeBadgeColors.length) : "0(null)");
        printWriter.print(str);
        printWriter.print("mLabels.length: ");
        printWriter.println(this.mLabels != null ? java.lang.Integer.valueOf(this.mLabels.length) : "0(null)");
    }

    public static final class Builder {
        private int mBaseType;
        private java.lang.String mName;
        private int mMaxAllowed = -1;
        private int mMaxAllowedPerParent = -1;
        private int mDefaultUserInfoPropertyFlags = 0;

        @android.annotation.Nullable
        private android.os.Bundle mDefaultRestrictions = null;

        @android.annotation.Nullable
        private android.os.Bundle mDefaultSystemSettings = null;

        @android.annotation.Nullable
        private android.os.Bundle mDefaultSecureSettings = null;

        @android.annotation.Nullable
        private java.util.List<com.android.server.pm.DefaultCrossProfileIntentFilter> mDefaultCrossProfileIntentFilters = null;
        private int mEnabled = 1;

        @android.annotation.Nullable
        private int[] mLabels = null;

        @android.annotation.Nullable
        private int[] mBadgeLabels = null;

        @android.annotation.Nullable
        private int[] mBadgeColors = null;

        @android.annotation.Nullable
        private int[] mDarkThemeBadgeColors = null;
        private int mIconBadge = 0;
        private int mBadgePlain = 0;
        private int mBadgeNoBackground = 0;
        private int mStatusBarIcon = 0;

        @android.annotation.Nullable
        private android.content.pm.UserProperties mDefaultUserProperties = null;

        public com.android.server.pm.UserTypeDetails.Builder setName(java.lang.String str) {
            this.mName = str;
            return this;
        }

        public com.android.server.pm.UserTypeDetails.Builder setEnabled(int i) {
            this.mEnabled = i;
            return this;
        }

        public com.android.server.pm.UserTypeDetails.Builder setMaxAllowed(int i) {
            this.mMaxAllowed = i;
            return this;
        }

        public com.android.server.pm.UserTypeDetails.Builder setMaxAllowedPerParent(int i) {
            this.mMaxAllowedPerParent = i;
            return this;
        }

        public com.android.server.pm.UserTypeDetails.Builder setBaseType(int i) {
            this.mBaseType = i;
            return this;
        }

        public com.android.server.pm.UserTypeDetails.Builder setDefaultUserInfoPropertyFlags(int i) {
            this.mDefaultUserInfoPropertyFlags = i;
            return this;
        }

        public com.android.server.pm.UserTypeDetails.Builder setBadgeLabels(int... iArr) {
            this.mBadgeLabels = iArr;
            return this;
        }

        public com.android.server.pm.UserTypeDetails.Builder setBadgeColors(int... iArr) {
            this.mBadgeColors = iArr;
            return this;
        }

        public com.android.server.pm.UserTypeDetails.Builder setDarkThemeBadgeColors(int... iArr) {
            this.mDarkThemeBadgeColors = iArr;
            return this;
        }

        public com.android.server.pm.UserTypeDetails.Builder setIconBadge(int i) {
            this.mIconBadge = i;
            return this;
        }

        public com.android.server.pm.UserTypeDetails.Builder setBadgePlain(int i) {
            this.mBadgePlain = i;
            return this;
        }

        public com.android.server.pm.UserTypeDetails.Builder setBadgeNoBackground(int i) {
            this.mBadgeNoBackground = i;
            return this;
        }

        public com.android.server.pm.UserTypeDetails.Builder setStatusBarIcon(int i) {
            this.mStatusBarIcon = i;
            return this;
        }

        public com.android.server.pm.UserTypeDetails.Builder setLabels(int... iArr) {
            this.mLabels = iArr;
            return this;
        }

        public com.android.server.pm.UserTypeDetails.Builder setDefaultRestrictions(@android.annotation.Nullable android.os.Bundle bundle) {
            this.mDefaultRestrictions = bundle;
            return this;
        }

        public com.android.server.pm.UserTypeDetails.Builder setDefaultSystemSettings(@android.annotation.Nullable android.os.Bundle bundle) {
            this.mDefaultSystemSettings = bundle;
            return this;
        }

        public com.android.server.pm.UserTypeDetails.Builder setDefaultSecureSettings(@android.annotation.Nullable android.os.Bundle bundle) {
            this.mDefaultSecureSettings = bundle;
            return this;
        }

        public com.android.server.pm.UserTypeDetails.Builder setDefaultCrossProfileIntentFilters(@android.annotation.Nullable java.util.List<com.android.server.pm.DefaultCrossProfileIntentFilter> list) {
            this.mDefaultCrossProfileIntentFilters = list;
            return this;
        }

        public com.android.server.pm.UserTypeDetails.Builder setDefaultUserProperties(android.content.pm.UserProperties.Builder builder) {
            this.mDefaultUserProperties = builder.build();
            return this;
        }

        @android.annotation.NonNull
        public android.content.pm.UserProperties getDefaultUserProperties() {
            if (this.mDefaultUserProperties == null) {
                this.mDefaultUserProperties = new android.content.pm.UserProperties.Builder().build();
            }
            return this.mDefaultUserProperties;
        }

        int getBaseType() {
            return this.mBaseType;
        }

        public com.android.server.pm.UserTypeDetails createUserTypeDetails() {
            com.android.internal.util.Preconditions.checkArgument(this.mName != null, "Cannot create a UserTypeDetails with no name.");
            com.android.internal.util.Preconditions.checkArgument(hasValidBaseType(), "UserTypeDetails " + this.mName + " has invalid baseType: " + this.mBaseType);
            com.android.internal.util.Preconditions.checkArgument(hasValidPropertyFlags(), "UserTypeDetails " + this.mName + " has invalid flags: " + java.lang.Integer.toHexString(this.mDefaultUserInfoPropertyFlags));
            checkSystemAndMainUserPreconditions();
            if (hasBadge()) {
                com.android.internal.util.Preconditions.checkArgument((this.mBadgeLabels == null || this.mBadgeLabels.length == 0) ? false : true, "UserTypeDetails " + this.mName + " has badge but no badgeLabels.");
                com.android.internal.util.Preconditions.checkArgument((this.mBadgeColors == null || this.mBadgeColors.length == 0) ? false : true, "UserTypeDetails " + this.mName + " has badge but no badgeColors.");
            }
            if (!isProfile()) {
                com.android.internal.util.Preconditions.checkArgument(this.mDefaultCrossProfileIntentFilters == null || this.mDefaultCrossProfileIntentFilters.isEmpty(), "UserTypeDetails %s has a non empty defaultCrossProfileIntentFilters", new java.lang.Object[]{this.mName});
            }
            return new com.android.server.pm.UserTypeDetails(this.mName, this.mEnabled != 0, this.mMaxAllowed, this.mBaseType, this.mDefaultUserInfoPropertyFlags, this.mLabels, this.mMaxAllowedPerParent, this.mIconBadge, this.mBadgePlain, this.mBadgeNoBackground, this.mStatusBarIcon, this.mBadgeLabels, this.mBadgeColors, this.mDarkThemeBadgeColors == null ? this.mBadgeColors : this.mDarkThemeBadgeColors, this.mDefaultRestrictions, this.mDefaultSystemSettings, this.mDefaultSecureSettings, this.mDefaultCrossProfileIntentFilters, getDefaultUserProperties());
        }

        private boolean hasBadge() {
            return this.mIconBadge != 0;
        }

        private boolean isProfile() {
            return (this.mBaseType & 4096) != 0;
        }

        private boolean hasValidBaseType() {
            return this.mBaseType == 1024 || this.mBaseType == 4096 || this.mBaseType == 2048 || this.mBaseType == 3072;
        }

        private boolean hasValidPropertyFlags() {
            return (this.mDefaultUserInfoPropertyFlags & 7312) == 0;
        }

        private void checkSystemAndMainUserPreconditions() {
            com.android.internal.util.Preconditions.checkArgument(((this.mBaseType & 2048) != 0) == ((this.mDefaultUserInfoPropertyFlags & 1) != 0), "UserTypeDetails " + this.mName + " cannot be SYSTEM xor PRIMARY.");
            com.android.internal.util.Preconditions.checkArgument((this.mDefaultUserInfoPropertyFlags & 16384) == 0 || this.mMaxAllowed == 1, "UserTypeDetails " + this.mName + " must not sanction more than one MainUser.");
        }
    }

    public boolean isManagedProfile() {
        return android.os.UserManager.isUserTypeManagedProfile(this.mName);
    }

    public boolean isCommunalProfile() {
        return android.os.UserManager.isUserTypeCommunalProfile(this.mName);
    }

    public boolean isPrivateProfile() {
        return android.os.UserManager.isUserTypePrivateProfile(this.mName);
    }
}
