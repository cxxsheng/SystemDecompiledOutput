package android.content.pm;

/* loaded from: classes.dex */
public final class ShortcutInfo implements android.os.Parcelable {
    private static final java.lang.String ANDROID_PACKAGE_NAME = "android";
    public static final int CLONE_REMOVE_FOR_APP_PREDICTION = 9;
    public static final int CLONE_REMOVE_FOR_CREATOR = 9;
    public static final int CLONE_REMOVE_FOR_LAUNCHER = 27;
    public static final int CLONE_REMOVE_FOR_LAUNCHER_APPROVAL = 26;
    private static final int CLONE_REMOVE_ICON = 1;
    private static final int CLONE_REMOVE_INTENT = 2;
    public static final int CLONE_REMOVE_NON_KEY_INFO = 4;
    public static final int CLONE_REMOVE_PERSON = 16;
    public static final int CLONE_REMOVE_RES_NAMES = 8;
    public static final android.os.Parcelable.Creator<android.content.pm.ShortcutInfo> CREATOR = new android.os.Parcelable.Creator<android.content.pm.ShortcutInfo>() { // from class: android.content.pm.ShortcutInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.pm.ShortcutInfo createFromParcel(android.os.Parcel parcel) {
            return new android.content.pm.ShortcutInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.pm.ShortcutInfo[] newArray(int i) {
            return new android.content.pm.ShortcutInfo[i];
        }
    };
    public static final int DISABLED_REASON_APP_CHANGED = 2;
    public static final int DISABLED_REASON_BACKUP_NOT_SUPPORTED = 101;
    public static final int DISABLED_REASON_BY_APP = 1;
    public static final int DISABLED_REASON_NOT_DISABLED = 0;
    public static final int DISABLED_REASON_OTHER_RESTORE_ISSUE = 103;
    private static final int DISABLED_REASON_RESTORE_ISSUE_START = 100;
    public static final int DISABLED_REASON_SIGNATURE_MISMATCH = 102;
    public static final int DISABLED_REASON_UNKNOWN = 3;
    public static final int DISABLED_REASON_VERSION_LOWER = 100;
    public static final int FLAG_ADAPTIVE_BITMAP = 512;
    public static final int FLAG_CACHED_ALL = 1610629120;
    public static final int FLAG_CACHED_BUBBLES = 1073741824;
    public static final int FLAG_CACHED_NOTIFICATIONS = 16384;
    public static final int FLAG_CACHED_PEOPLE_TILE = 536870912;
    public static final int FLAG_DISABLED = 64;
    public static final int FLAG_DYNAMIC = 1;
    public static final int FLAG_HAS_ICON_FILE = 8;
    public static final int FLAG_HAS_ICON_RES = 4;
    public static final int FLAG_HAS_ICON_URI = 32768;
    public static final int FLAG_ICON_FILE_PENDING_SAVE = 2048;
    public static final int FLAG_IMMUTABLE = 256;
    public static final int FLAG_KEY_FIELDS_ONLY = 16;
    public static final int FLAG_LONG_LIVED = 8192;
    public static final int FLAG_MANIFEST = 32;
    public static final int FLAG_PINNED = 2;
    public static final int FLAG_RETURNED_BY_SERVICE = 1024;
    public static final int FLAG_SHADOW = 4096;
    public static final int FLAG_STRINGS_RESOLVED = 128;
    private static final int IMPLICIT_RANK_MASK = Integer.MAX_VALUE;
    public static final int MAX_ID_LENGTH = 1000;
    public static final int RANK_CHANGED_BIT = Integer.MIN_VALUE;
    public static final int RANK_NOT_SET = Integer.MAX_VALUE;
    private static final java.lang.String RES_TYPE_STRING = "string";
    public static final java.lang.String SHORTCUT_CATEGORY_CONVERSATION = "android.shortcut.conversation";
    public static final int SURFACE_LAUNCHER = 1;
    static final java.lang.String TAG = "Shortcut";
    public static final int VERSION_CODE_UNKNOWN = -1;
    private android.content.ComponentName mActivity;
    private java.lang.String mBitmapPath;
    private java.util.Map<java.lang.String, java.util.Map<java.lang.String, java.util.List<java.lang.String>>> mCapabilityBindings;
    private android.util.ArraySet<java.lang.String> mCategories;
    private java.lang.CharSequence mDisabledMessage;
    private int mDisabledMessageResId;
    private java.lang.String mDisabledMessageResName;
    private int mDisabledReason;
    private int mExcludedSurfaces;
    private android.os.PersistableBundle mExtras;
    private int mFlags;
    private android.graphics.drawable.Icon mIcon;
    private int mIconResId;
    private java.lang.String mIconResName;
    private java.lang.String mIconUri;
    private final java.lang.String mId;
    private int mImplicitRank;
    private android.os.PersistableBundle[] mIntentPersistableExtrases;
    private android.content.Intent[] mIntents;
    private long mLastChangedTimestamp;
    private android.content.LocusId mLocusId;
    private final java.lang.String mPackageName;
    private android.app.Person[] mPersons;
    private int mRank;
    private java.lang.String mStartingThemeResName;
    private java.lang.CharSequence mText;
    private int mTextResId;
    private java.lang.String mTextResName;
    private java.lang.CharSequence mTitle;
    private int mTitleResId;
    private java.lang.String mTitleResName;
    private final int mUserId;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface CloneFlags {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface DisabledReason {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ShortcutFlags {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Surface {
    }

    public static java.lang.String getDisabledReasonDebugString(int i) {
        switch (i) {
            case 0:
                return "[Not disabled]";
            case 1:
                return "[Disabled: by app]";
            case 2:
                return "[Disabled: app changed]";
            case 100:
                return "[Disabled: lower version]";
            case 101:
                return "[Disabled: backup not supported]";
            case 102:
                return "[Disabled: signature mismatch]";
            case 103:
                return "[Disabled: unknown restore issue]";
            default:
                return "[Disabled: unknown reason:" + i + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END;
        }
    }

    public static java.lang.String getDisabledReasonForRestoreIssue(android.content.Context context, int i) {
        android.content.res.Resources resources = context.getResources();
        switch (i) {
            case 3:
                return resources.getString(com.android.internal.R.string.shortcut_disabled_reason_unknown);
            case 100:
                return resources.getString(com.android.internal.R.string.shortcut_restored_on_lower_version);
            case 101:
                return resources.getString(com.android.internal.R.string.shortcut_restore_not_supported);
            case 102:
                return resources.getString(com.android.internal.R.string.shortcut_restore_signature_mismatch);
            case 103:
                return resources.getString(com.android.internal.R.string.shortcut_restore_unknown_issue);
            default:
                return null;
        }
    }

    public static boolean isDisabledForRestoreIssue(int i) {
        return i >= 100;
    }

    private ShortcutInfo(android.content.pm.ShortcutInfo.Builder builder) {
        this.mUserId = builder.mContext.getUserId();
        this.mId = getSafeId((java.lang.String) com.android.internal.util.Preconditions.checkStringNotEmpty(builder.mId, "Shortcut ID must be provided"));
        this.mPackageName = builder.mContext.getPackageName();
        this.mActivity = builder.mActivity;
        this.mIcon = builder.mIcon;
        this.mTitle = builder.mTitle;
        this.mTitleResId = builder.mTitleResId;
        this.mText = builder.mText;
        this.mTextResId = builder.mTextResId;
        this.mDisabledMessage = builder.mDisabledMessage;
        this.mDisabledMessageResId = builder.mDisabledMessageResId;
        this.mCategories = cloneCategories(builder.mCategories);
        this.mIntents = cloneIntents(builder.mIntents);
        fixUpIntentExtras();
        this.mPersons = clonePersons(builder.mPersons);
        if (builder.mIsLongLived) {
            setLongLived();
        }
        this.mExcludedSurfaces = builder.mExcludedSurfaces;
        this.mRank = builder.mRank;
        this.mExtras = builder.mExtras;
        this.mLocusId = builder.mLocusId;
        this.mCapabilityBindings = cloneCapabilityBindings(builder.mCapabilityBindings);
        this.mStartingThemeResName = builder.mStartingThemeResId != 0 ? builder.mContext.getResources().getResourceName(builder.mStartingThemeResId) : null;
        updateTimestamp();
    }

    private void fixUpIntentExtras() {
        if (this.mIntents == null) {
            this.mIntentPersistableExtrases = null;
            return;
        }
        this.mIntentPersistableExtrases = new android.os.PersistableBundle[this.mIntents.length];
        for (int i = 0; i < this.mIntents.length; i++) {
            android.content.Intent intent = this.mIntents[i];
            android.os.Bundle extras = intent.getExtras();
            if (extras == null) {
                this.mIntentPersistableExtrases[i] = null;
            } else {
                this.mIntentPersistableExtrases[i] = new android.os.PersistableBundle(extras);
                intent.replaceExtras((android.os.Bundle) null);
            }
        }
    }

    private static android.util.ArraySet<java.lang.String> cloneCategories(java.util.Set<java.lang.String> set) {
        if (set == null) {
            return null;
        }
        android.util.ArraySet<java.lang.String> arraySet = new android.util.ArraySet<>(set.size());
        for (java.lang.String str : set) {
            if (!android.text.TextUtils.isEmpty(str)) {
                arraySet.add(str.toString().intern());
            }
        }
        return arraySet;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static android.content.Intent[] cloneIntents(android.content.Intent[] intentArr) {
        if (intentArr == null) {
            return null;
        }
        int length = intentArr.length;
        android.content.Intent[] intentArr2 = new android.content.Intent[length];
        for (int i = 0; i < length; i++) {
            if (intentArr[i] != null) {
                intentArr2[i] = new android.content.Intent(intentArr[i]);
            }
        }
        return intentArr2;
    }

    private static android.os.PersistableBundle[] clonePersistableBundle(android.os.PersistableBundle[] persistableBundleArr) {
        if (persistableBundleArr == null) {
            return null;
        }
        int length = persistableBundleArr.length;
        android.os.PersistableBundle[] persistableBundleArr2 = new android.os.PersistableBundle[length];
        for (int i = 0; i < length; i++) {
            if (persistableBundleArr[i] != null) {
                persistableBundleArr2[i] = new android.os.PersistableBundle(persistableBundleArr[i]);
            }
        }
        return persistableBundleArr2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static android.app.Person[] clonePersons(android.app.Person[] personArr) {
        if (personArr == null) {
            return null;
        }
        int length = personArr.length;
        android.app.Person[] personArr2 = new android.app.Person[length];
        for (int i = 0; i < length; i++) {
            if (personArr[i] != null) {
                personArr2[i] = personArr[i].toBuilder().setIcon(null).build();
            }
        }
        return personArr2;
    }

    private static java.lang.String getSafeId(java.lang.String str) {
        if (str.length() > 1000) {
            return str.substring(0, 1000);
        }
        return str;
    }

    public void enforceMandatoryFields(boolean z) {
        com.android.internal.util.Preconditions.checkStringNotEmpty(this.mId, "Shortcut ID must be provided");
        if (!z) {
            java.util.Objects.requireNonNull(this.mActivity, "Activity must be provided");
        }
        if (this.mTitle == null && this.mTitleResId == 0) {
            throw new java.lang.IllegalArgumentException("Short label must be provided");
        }
        java.util.Objects.requireNonNull(this.mIntents, "Shortcut Intent must be provided");
        com.android.internal.util.Preconditions.checkArgument(this.mIntents.length > 0, "Shortcut Intent must be provided");
    }

    private ShortcutInfo(android.content.pm.ShortcutInfo shortcutInfo, int i) {
        this.mUserId = shortcutInfo.mUserId;
        this.mId = shortcutInfo.mId;
        this.mPackageName = shortcutInfo.mPackageName;
        this.mActivity = shortcutInfo.mActivity;
        this.mFlags = shortcutInfo.mFlags;
        this.mLastChangedTimestamp = shortcutInfo.mLastChangedTimestamp;
        this.mDisabledReason = shortcutInfo.mDisabledReason;
        this.mLocusId = shortcutInfo.mLocusId;
        this.mExcludedSurfaces = shortcutInfo.mExcludedSurfaces;
        this.mIconResId = shortcutInfo.mIconResId;
        if ((i & 4) == 0) {
            if ((i & 1) == 0) {
                this.mIcon = shortcutInfo.mIcon;
                this.mBitmapPath = shortcutInfo.mBitmapPath;
                this.mIconUri = shortcutInfo.mIconUri;
            }
            this.mTitle = shortcutInfo.mTitle;
            this.mTitleResId = shortcutInfo.mTitleResId;
            this.mText = shortcutInfo.mText;
            this.mTextResId = shortcutInfo.mTextResId;
            this.mDisabledMessage = shortcutInfo.mDisabledMessage;
            this.mDisabledMessageResId = shortcutInfo.mDisabledMessageResId;
            this.mCategories = cloneCategories(shortcutInfo.mCategories);
            if ((i & 16) == 0) {
                this.mPersons = clonePersons(shortcutInfo.mPersons);
            }
            if ((i & 2) == 0) {
                this.mIntents = cloneIntents(shortcutInfo.mIntents);
                this.mIntentPersistableExtrases = clonePersistableBundle(shortcutInfo.mIntentPersistableExtrases);
            }
            this.mRank = shortcutInfo.mRank;
            this.mExtras = shortcutInfo.mExtras;
            if ((i & 8) == 0) {
                this.mTitleResName = shortcutInfo.mTitleResName;
                this.mTextResName = shortcutInfo.mTextResName;
                this.mDisabledMessageResName = shortcutInfo.mDisabledMessageResName;
                this.mIconResName = shortcutInfo.mIconResName;
            }
        } else {
            this.mFlags |= 16;
        }
        this.mCapabilityBindings = cloneCapabilityBindings(shortcutInfo.mCapabilityBindings);
        this.mStartingThemeResName = shortcutInfo.mStartingThemeResName;
    }

    public static android.content.pm.ShortcutInfo createFromGenericDocument(android.content.Context context, android.app.appsearch.GenericDocument genericDocument) {
        java.util.Objects.requireNonNull(context);
        java.util.Objects.requireNonNull(genericDocument);
        return createFromGenericDocument(context.getUserId(), genericDocument);
    }

    public static android.content.pm.ShortcutInfo createFromGenericDocument(int i, android.app.appsearch.GenericDocument genericDocument) {
        return new android.content.pm.AppSearchShortcutInfo(genericDocument).toShortcutInfo(i);
    }

    private java.lang.CharSequence getResourceString(android.content.res.Resources resources, int i, java.lang.CharSequence charSequence) {
        try {
            return resources.getString(i);
        } catch (android.content.res.Resources.NotFoundException e) {
            android.util.Log.e("Shortcut", "Resource for ID=" + i + " not found in package " + this.mPackageName);
            return charSequence;
        }
    }

    public void resolveResourceStrings(android.content.res.Resources resources) {
        this.mFlags |= 128;
        if (this.mTitleResId == 0 && this.mTextResId == 0 && this.mDisabledMessageResId == 0) {
            return;
        }
        if (this.mTitleResId != 0) {
            this.mTitle = getResourceString(resources, this.mTitleResId, this.mTitle);
        }
        if (this.mTextResId != 0) {
            this.mText = getResourceString(resources, this.mTextResId, this.mText);
        }
        if (this.mDisabledMessageResId != 0) {
            this.mDisabledMessage = getResourceString(resources, this.mDisabledMessageResId, this.mDisabledMessage);
        }
    }

    public static java.lang.String lookUpResourceName(android.content.res.Resources resources, int i, boolean z, java.lang.String str) {
        if (i == 0) {
            return null;
        }
        try {
            java.lang.String resourceName = resources.getResourceName(i);
            if ("android".equals(getResourcePackageName(resourceName))) {
                return java.lang.String.valueOf(i);
            }
            return z ? getResourceTypeAndEntryName(resourceName) : getResourceEntryName(resourceName);
        } catch (android.content.res.Resources.NotFoundException e) {
            android.util.Log.e("Shortcut", "Resource name for ID=" + i + " not found in package " + str + ". Resource IDs may change when the application is upgraded, and the system may not be able to find the correct resource.");
            return null;
        }
    }

    public static java.lang.String getResourcePackageName(java.lang.String str) {
        int indexOf = str.indexOf(58);
        if (indexOf < 0) {
            return null;
        }
        return str.substring(0, indexOf);
    }

    public static java.lang.String getResourceTypeName(java.lang.String str) {
        int i;
        int indexOf;
        int indexOf2 = str.indexOf(58);
        if (indexOf2 < 0 || (indexOf = str.indexOf(47, (i = indexOf2 + 1))) < 0) {
            return null;
        }
        return str.substring(i, indexOf);
    }

    public static java.lang.String getResourceTypeAndEntryName(java.lang.String str) {
        int indexOf = str.indexOf(58);
        if (indexOf < 0) {
            return null;
        }
        return str.substring(indexOf + 1);
    }

    public static java.lang.String getResourceEntryName(java.lang.String str) {
        int indexOf = str.indexOf(47);
        if (indexOf < 0) {
            return null;
        }
        return str.substring(indexOf + 1);
    }

    public static int lookUpResourceId(android.content.res.Resources resources, java.lang.String str, java.lang.String str2, java.lang.String str3) {
        try {
            if (str == null) {
                return 0;
            }
            try {
                return java.lang.Integer.parseInt(str);
            } catch (java.lang.NumberFormatException e) {
                return resources.getIdentifier(str, str2, str3);
            }
        } catch (android.content.res.Resources.NotFoundException e2) {
            android.util.Log.e("Shortcut", "Resource ID for name=" + str + " not found in package " + str3);
            return 0;
        }
    }

    public void lookupAndFillInResourceNames(android.content.res.Resources resources) {
        if (this.mTitleResId == 0 && this.mTextResId == 0 && this.mDisabledMessageResId == 0 && this.mIconResId == 0) {
            return;
        }
        this.mTitleResName = lookUpResourceName(resources, this.mTitleResId, false, this.mPackageName);
        this.mTextResName = lookUpResourceName(resources, this.mTextResId, false, this.mPackageName);
        this.mDisabledMessageResName = lookUpResourceName(resources, this.mDisabledMessageResId, false, this.mPackageName);
        this.mIconResName = lookUpResourceName(resources, this.mIconResId, true, this.mPackageName);
    }

    public void lookupAndFillInResourceIds(android.content.res.Resources resources) {
        if (this.mTitleResName == null && this.mTextResName == null && this.mDisabledMessageResName == null && this.mIconResName == null) {
            return;
        }
        this.mTitleResId = lookUpResourceId(resources, this.mTitleResName, RES_TYPE_STRING, this.mPackageName);
        this.mTextResId = lookUpResourceId(resources, this.mTextResName, RES_TYPE_STRING, this.mPackageName);
        this.mDisabledMessageResId = lookUpResourceId(resources, this.mDisabledMessageResName, RES_TYPE_STRING, this.mPackageName);
        this.mIconResId = lookUpResourceId(resources, this.mIconResName, null, this.mPackageName);
    }

    public android.content.pm.ShortcutInfo clone(int i) {
        return new android.content.pm.ShortcutInfo(this, i);
    }

    public void ensureUpdatableWith(android.content.pm.ShortcutInfo shortcutInfo, boolean z) {
        if (z) {
            com.android.internal.util.Preconditions.checkState(isVisibleToPublisher(), "[Framework BUG] Invisible shortcuts can't be updated");
        }
        com.android.internal.util.Preconditions.checkState(this.mUserId == shortcutInfo.mUserId, "Owner User ID must match");
        com.android.internal.util.Preconditions.checkState(this.mId.equals(shortcutInfo.mId), "ID must match");
        com.android.internal.util.Preconditions.checkState(this.mPackageName.equals(shortcutInfo.mPackageName), "Package name must match");
        if (isVisibleToPublisher()) {
            com.android.internal.util.Preconditions.checkState(!isImmutable(), "Target ShortcutInfo is immutable");
        }
    }

    public void copyNonNullFieldsFrom(android.content.pm.ShortcutInfo shortcutInfo) {
        ensureUpdatableWith(shortcutInfo, true);
        if (shortcutInfo.mActivity != null) {
            this.mActivity = shortcutInfo.mActivity;
        }
        if (shortcutInfo.mIcon != null) {
            this.mIcon = shortcutInfo.mIcon;
            this.mIconResId = 0;
            this.mIconResName = null;
            this.mBitmapPath = null;
            this.mIconUri = null;
        }
        if (shortcutInfo.mTitle != null) {
            this.mTitle = shortcutInfo.mTitle;
            this.mTitleResId = 0;
            this.mTitleResName = null;
        } else if (shortcutInfo.mTitleResId != 0) {
            this.mTitle = null;
            this.mTitleResId = shortcutInfo.mTitleResId;
            this.mTitleResName = null;
        }
        if (shortcutInfo.mText != null) {
            this.mText = shortcutInfo.mText;
            this.mTextResId = 0;
            this.mTextResName = null;
        } else if (shortcutInfo.mTextResId != 0) {
            this.mText = null;
            this.mTextResId = shortcutInfo.mTextResId;
            this.mTextResName = null;
        }
        if (shortcutInfo.mDisabledMessage != null) {
            this.mDisabledMessage = shortcutInfo.mDisabledMessage;
            this.mDisabledMessageResId = 0;
            this.mDisabledMessageResName = null;
        } else if (shortcutInfo.mDisabledMessageResId != 0) {
            this.mDisabledMessage = null;
            this.mDisabledMessageResId = shortcutInfo.mDisabledMessageResId;
            this.mDisabledMessageResName = null;
        }
        if (shortcutInfo.mCategories != null) {
            this.mCategories = cloneCategories(shortcutInfo.mCategories);
        }
        if (shortcutInfo.mPersons != null) {
            this.mPersons = clonePersons(shortcutInfo.mPersons);
        }
        if (shortcutInfo.mIntents != null) {
            this.mIntents = cloneIntents(shortcutInfo.mIntents);
            this.mIntentPersistableExtrases = clonePersistableBundle(shortcutInfo.mIntentPersistableExtrases);
        }
        if (shortcutInfo.mRank != Integer.MAX_VALUE) {
            this.mRank = shortcutInfo.mRank;
        }
        if (shortcutInfo.mExtras != null) {
            this.mExtras = shortcutInfo.mExtras;
        }
        if (shortcutInfo.mLocusId != null) {
            this.mLocusId = shortcutInfo.mLocusId;
        }
        if (shortcutInfo.mStartingThemeResName != null && !shortcutInfo.mStartingThemeResName.isEmpty()) {
            this.mStartingThemeResName = shortcutInfo.mStartingThemeResName;
        }
        if (shortcutInfo.mCapabilityBindings != null) {
            this.mCapabilityBindings = cloneCapabilityBindings(shortcutInfo.mCapabilityBindings);
        }
    }

    public static android.graphics.drawable.Icon validateIcon(android.graphics.drawable.Icon icon) {
        switch (icon.getType()) {
            case 1:
            case 2:
            case 4:
            case 5:
            case 6:
                if (icon.hasTint()) {
                    throw new java.lang.IllegalArgumentException("Icons with tints are not supported");
                }
                return icon;
            case 3:
            default:
                throw getInvalidIconException();
        }
    }

    public static java.lang.IllegalArgumentException getInvalidIconException() {
        return new java.lang.IllegalArgumentException("Unsupported icon type: only the bitmap and resource types are supported");
    }

    public static class Builder {
        private android.content.ComponentName mActivity;
        private java.util.Map<java.lang.String, java.util.Map<java.lang.String, java.util.List<java.lang.String>>> mCapabilityBindings;
        private java.util.Set<java.lang.String> mCategories;
        private final android.content.Context mContext;
        private java.lang.CharSequence mDisabledMessage;
        private int mDisabledMessageResId;
        private int mExcludedSurfaces;
        private android.os.PersistableBundle mExtras;
        private android.graphics.drawable.Icon mIcon;
        private java.lang.String mId;
        private android.content.Intent[] mIntents;
        private boolean mIsLongLived;
        private android.content.LocusId mLocusId;
        private android.app.Person[] mPersons;
        private int mRank = Integer.MAX_VALUE;
        private int mStartingThemeResId;
        private java.lang.CharSequence mText;
        private int mTextResId;
        private java.lang.CharSequence mTitle;
        private int mTitleResId;

        @java.lang.Deprecated
        public Builder(android.content.Context context) {
            this.mContext = context;
        }

        @java.lang.Deprecated
        public android.content.pm.ShortcutInfo.Builder setId(java.lang.String str) {
            this.mId = (java.lang.String) com.android.internal.util.Preconditions.checkStringNotEmpty(str, "id cannot be empty");
            return this;
        }

        public Builder(android.content.Context context, java.lang.String str) {
            this.mContext = context;
            this.mId = (java.lang.String) com.android.internal.util.Preconditions.checkStringNotEmpty(str, "id cannot be empty");
        }

        public android.content.pm.ShortcutInfo.Builder setLocusId(android.content.LocusId locusId) {
            this.mLocusId = (android.content.LocusId) java.util.Objects.requireNonNull(locusId, "locusId cannot be null");
            return this;
        }

        public android.content.pm.ShortcutInfo.Builder setActivity(android.content.ComponentName componentName) {
            this.mActivity = (android.content.ComponentName) java.util.Objects.requireNonNull(componentName, "activity cannot be null");
            return this;
        }

        public android.content.pm.ShortcutInfo.Builder setIcon(android.graphics.drawable.Icon icon) {
            this.mIcon = android.content.pm.ShortcutInfo.validateIcon(icon);
            return this;
        }

        public android.content.pm.ShortcutInfo.Builder setStartingTheme(int i) {
            this.mStartingThemeResId = i;
            return this;
        }

        @java.lang.Deprecated
        public android.content.pm.ShortcutInfo.Builder setShortLabelResId(int i) {
            com.android.internal.util.Preconditions.checkState(this.mTitle == null, "shortLabel already set");
            this.mTitleResId = i;
            return this;
        }

        public android.content.pm.ShortcutInfo.Builder setShortLabel(java.lang.CharSequence charSequence) {
            com.android.internal.util.Preconditions.checkState(this.mTitleResId == 0, "shortLabelResId already set");
            this.mTitle = com.android.internal.util.Preconditions.checkStringNotEmpty(charSequence, "shortLabel cannot be empty");
            return this;
        }

        @java.lang.Deprecated
        public android.content.pm.ShortcutInfo.Builder setLongLabelResId(int i) {
            com.android.internal.util.Preconditions.checkState(this.mText == null, "longLabel already set");
            this.mTextResId = i;
            return this;
        }

        public android.content.pm.ShortcutInfo.Builder setLongLabel(java.lang.CharSequence charSequence) {
            com.android.internal.util.Preconditions.checkState(this.mTextResId == 0, "longLabelResId already set");
            this.mText = com.android.internal.util.Preconditions.checkStringNotEmpty(charSequence, "longLabel cannot be empty");
            return this;
        }

        @java.lang.Deprecated
        public android.content.pm.ShortcutInfo.Builder setTitle(java.lang.CharSequence charSequence) {
            return setShortLabel(charSequence);
        }

        @java.lang.Deprecated
        public android.content.pm.ShortcutInfo.Builder setTitleResId(int i) {
            return setShortLabelResId(i);
        }

        @java.lang.Deprecated
        public android.content.pm.ShortcutInfo.Builder setText(java.lang.CharSequence charSequence) {
            return setLongLabel(charSequence);
        }

        @java.lang.Deprecated
        public android.content.pm.ShortcutInfo.Builder setTextResId(int i) {
            return setLongLabelResId(i);
        }

        @java.lang.Deprecated
        public android.content.pm.ShortcutInfo.Builder setDisabledMessageResId(int i) {
            com.android.internal.util.Preconditions.checkState(this.mDisabledMessage == null, "disabledMessage already set");
            this.mDisabledMessageResId = i;
            return this;
        }

        public android.content.pm.ShortcutInfo.Builder setDisabledMessage(java.lang.CharSequence charSequence) {
            com.android.internal.util.Preconditions.checkState(this.mDisabledMessageResId == 0, "disabledMessageResId already set");
            this.mDisabledMessage = com.android.internal.util.Preconditions.checkStringNotEmpty(charSequence, "disabledMessage cannot be empty");
            return this;
        }

        public android.content.pm.ShortcutInfo.Builder setCategories(java.util.Set<java.lang.String> set) {
            this.mCategories = set;
            return this;
        }

        public android.content.pm.ShortcutInfo.Builder setIntent(android.content.Intent intent) {
            return setIntents(new android.content.Intent[]{intent});
        }

        public android.content.pm.ShortcutInfo.Builder setIntents(android.content.Intent[] intentArr) {
            java.util.Objects.requireNonNull(intentArr, "intents cannot be null");
            if (intentArr.length == 0) {
                throw new java.lang.IllegalArgumentException("intents cannot be empty");
            }
            for (android.content.Intent intent : intentArr) {
                java.util.Objects.requireNonNull(intent, "intents cannot contain null");
                java.util.Objects.requireNonNull(intent.getAction(), "intent's action must be set");
            }
            this.mIntents = android.content.pm.ShortcutInfo.cloneIntents(intentArr);
            return this;
        }

        public android.content.pm.ShortcutInfo.Builder setPerson(android.app.Person person) {
            return setPersons(new android.app.Person[]{person});
        }

        public android.content.pm.ShortcutInfo.Builder setPersons(android.app.Person[] personArr) {
            java.util.Objects.requireNonNull(personArr, "persons cannot be null");
            if (personArr.length == 0) {
                throw new java.lang.IllegalArgumentException("persons cannot be empty");
            }
            for (android.app.Person person : personArr) {
                java.util.Objects.requireNonNull(person, "persons cannot contain null");
            }
            this.mPersons = android.content.pm.ShortcutInfo.clonePersons(personArr);
            return this;
        }

        public android.content.pm.ShortcutInfo.Builder setLongLived(boolean z) {
            this.mIsLongLived = z;
            return this;
        }

        public android.content.pm.ShortcutInfo.Builder setRank(int i) {
            com.android.internal.util.Preconditions.checkArgument(i >= 0, "Rank cannot be negative or bigger than MAX_RANK");
            this.mRank = i;
            return this;
        }

        public android.content.pm.ShortcutInfo.Builder setExtras(android.os.PersistableBundle persistableBundle) {
            this.mExtras = persistableBundle;
            return this;
        }

        public android.content.pm.ShortcutInfo.Builder addCapabilityBinding(android.content.pm.Capability capability, android.content.pm.CapabilityParams capabilityParams) {
            java.util.Objects.requireNonNull(capability);
            if (this.mCapabilityBindings == null) {
                this.mCapabilityBindings = new android.util.ArrayMap(1);
            }
            if (!this.mCapabilityBindings.containsKey(capability.getName())) {
                this.mCapabilityBindings.put(capability.getName(), new android.util.ArrayMap(0));
            }
            if (capabilityParams == null) {
                return this;
            }
            this.mCapabilityBindings.get(capability.getName()).put(capabilityParams.getName(), capabilityParams.getValues());
            return this;
        }

        public android.content.pm.ShortcutInfo.Builder setExcludedFromSurfaces(int i) {
            this.mExcludedSurfaces = i;
            return this;
        }

        public android.content.pm.ShortcutInfo build() {
            return new android.content.pm.ShortcutInfo(this);
        }
    }

    public java.lang.String getId() {
        return this.mId;
    }

    public android.content.LocusId getLocusId() {
        return this.mLocusId;
    }

    public java.lang.String getPackage() {
        return this.mPackageName;
    }

    public android.content.ComponentName getActivity() {
        return this.mActivity;
    }

    public void setActivity(android.content.ComponentName componentName) {
        this.mActivity = componentName;
    }

    public android.graphics.drawable.Icon getIcon() {
        return this.mIcon;
    }

    public java.lang.String getStartingThemeResName() {
        return this.mStartingThemeResName;
    }

    @java.lang.Deprecated
    public java.lang.CharSequence getTitle() {
        return this.mTitle;
    }

    @java.lang.Deprecated
    public int getTitleResId() {
        return this.mTitleResId;
    }

    @java.lang.Deprecated
    public java.lang.CharSequence getText() {
        return this.mText;
    }

    @java.lang.Deprecated
    public int getTextResId() {
        return this.mTextResId;
    }

    public java.lang.CharSequence getShortLabel() {
        return this.mTitle;
    }

    public int getShortLabelResourceId() {
        return this.mTitleResId;
    }

    public java.lang.CharSequence getLongLabel() {
        return this.mText;
    }

    public java.lang.CharSequence getLabel() {
        java.lang.CharSequence longLabel = getLongLabel();
        if (android.text.TextUtils.isEmpty(longLabel)) {
            return getShortLabel();
        }
        return longLabel;
    }

    public int getLongLabelResourceId() {
        return this.mTextResId;
    }

    public java.lang.CharSequence getDisabledMessage() {
        return this.mDisabledMessage;
    }

    public int getDisabledMessageResourceId() {
        return this.mDisabledMessageResId;
    }

    public void setDisabledReason(int i) {
        this.mDisabledReason = i;
    }

    public int getDisabledReason() {
        return this.mDisabledReason;
    }

    public java.util.Set<java.lang.String> getCategories() {
        return this.mCategories;
    }

    public android.content.Intent getIntent() {
        if (this.mIntents == null || this.mIntents.length == 0) {
            return null;
        }
        int length = this.mIntents.length - 1;
        return setIntentExtras(new android.content.Intent(this.mIntents[length]), this.mIntentPersistableExtrases[length]);
    }

    public android.content.Intent[] getIntents() {
        if (this.mIntents == null) {
            return null;
        }
        int length = this.mIntents.length;
        android.content.Intent[] intentArr = new android.content.Intent[length];
        for (int i = 0; i < length; i++) {
            intentArr[i] = new android.content.Intent(this.mIntents[i]);
            setIntentExtras(intentArr[i], this.mIntentPersistableExtrases[i]);
        }
        return intentArr;
    }

    public android.content.Intent[] getIntentsNoExtras() {
        return this.mIntents;
    }

    @android.annotation.SystemApi
    public android.app.Person[] getPersons() {
        return clonePersons(this.mPersons);
    }

    public android.os.PersistableBundle[] getIntentPersistableExtrases() {
        return this.mIntentPersistableExtrases;
    }

    public int getRank() {
        return this.mRank;
    }

    public boolean hasRank() {
        return this.mRank != Integer.MAX_VALUE;
    }

    public void setRank(int i) {
        this.mRank = i;
    }

    public void clearImplicitRankAndRankChangedFlag() {
        this.mImplicitRank = 0;
    }

    public void setImplicitRank(int i) {
        this.mImplicitRank = (i & Integer.MAX_VALUE) | (this.mImplicitRank & Integer.MIN_VALUE);
    }

    public int getImplicitRank() {
        return this.mImplicitRank & Integer.MAX_VALUE;
    }

    public void setRankChanged() {
        this.mImplicitRank |= Integer.MIN_VALUE;
    }

    public boolean isRankChanged() {
        return (this.mImplicitRank & Integer.MIN_VALUE) != 0;
    }

    public android.os.PersistableBundle getExtras() {
        return this.mExtras;
    }

    public int getUserId() {
        return this.mUserId;
    }

    public android.os.UserHandle getUserHandle() {
        return android.os.UserHandle.of(this.mUserId);
    }

    public long getLastChangedTimestamp() {
        return this.mLastChangedTimestamp;
    }

    public int getFlags() {
        return this.mFlags;
    }

    public void replaceFlags(int i) {
        this.mFlags = i;
    }

    public void addFlags(int i) {
        this.mFlags = i | this.mFlags;
    }

    public void clearFlags(int i) {
        this.mFlags = (~i) & this.mFlags;
    }

    public boolean hasFlags(int i) {
        return (this.mFlags & i) == i;
    }

    public boolean isReturnedByServer() {
        return hasFlags(1024);
    }

    public void setReturnedByServer() {
        addFlags(1024);
    }

    public boolean isLongLived() {
        return hasFlags(8192);
    }

    public void setLongLived() {
        addFlags(8192);
    }

    public void setCached(int i) {
        addFlags(i);
    }

    public boolean isCached() {
        return (getFlags() & FLAG_CACHED_ALL) != 0;
    }

    public boolean isDynamic() {
        return hasFlags(1);
    }

    public boolean isPinned() {
        return hasFlags(2);
    }

    public boolean isDeclaredInManifest() {
        return hasFlags(32);
    }

    @java.lang.Deprecated
    public boolean isManifestShortcut() {
        return isDeclaredInManifest();
    }

    public boolean isFloating() {
        return ((!isPinned() && !isCached()) || isDynamic() || isManifestShortcut()) ? false : true;
    }

    public boolean isOriginallyFromManifest() {
        return hasFlags(256);
    }

    public boolean isDynamicVisible() {
        return isDynamic() && isVisibleToPublisher();
    }

    public boolean isPinnedVisible() {
        return isPinned() && isVisibleToPublisher();
    }

    public boolean isManifestVisible() {
        return isDeclaredInManifest() && isVisibleToPublisher();
    }

    public boolean isNonManifestVisible() {
        return !isDeclaredInManifest() && isVisibleToPublisher() && (isPinned() || isCached() || isDynamic());
    }

    public boolean isImmutable() {
        return hasFlags(256);
    }

    public boolean isEnabled() {
        return !hasFlags(64);
    }

    public boolean isAlive() {
        return hasFlags(2) || hasFlags(1) || hasFlags(32) || isCached();
    }

    public boolean usesQuota() {
        return hasFlags(1) || hasFlags(32);
    }

    public boolean hasIconResource() {
        return hasFlags(4);
    }

    public boolean hasIconUri() {
        return hasFlags(32768);
    }

    public boolean hasStringResources() {
        return (this.mTitleResId == 0 && this.mTextResId == 0 && this.mDisabledMessageResId == 0) ? false : true;
    }

    public boolean hasAnyResources() {
        return hasIconResource() || hasStringResources();
    }

    public boolean hasIconFile() {
        return hasFlags(8);
    }

    public boolean hasAdaptiveBitmap() {
        return hasFlags(512);
    }

    public boolean isIconPendingSave() {
        return hasFlags(2048);
    }

    public void setIconPendingSave() {
        addFlags(2048);
    }

    public void clearIconPendingSave() {
        clearFlags(2048);
    }

    public boolean isVisibleToPublisher() {
        return !isDisabledForRestoreIssue(this.mDisabledReason);
    }

    public boolean hasKeyFieldsOnly() {
        return hasFlags(16);
    }

    public boolean hasStringResourcesResolved() {
        return hasFlags(128);
    }

    public void updateTimestamp() {
        this.mLastChangedTimestamp = java.lang.System.currentTimeMillis();
    }

    public void setTimestamp(long j) {
        this.mLastChangedTimestamp = j;
    }

    public void clearIcon() {
        this.mIcon = null;
    }

    public void setIconResourceId(int i) {
        if (this.mIconResId != i) {
            this.mIconResName = null;
        }
        this.mIconResId = i;
    }

    public int getIconResourceId() {
        return this.mIconResId;
    }

    public void setIconUri(java.lang.String str) {
        this.mIconUri = str;
    }

    public java.lang.String getIconUri() {
        return this.mIconUri;
    }

    public java.lang.String getBitmapPath() {
        return this.mBitmapPath;
    }

    public void setBitmapPath(java.lang.String str) {
        this.mBitmapPath = str;
    }

    public void setDisabledMessageResId(int i) {
        if (this.mDisabledMessageResId != i) {
            this.mDisabledMessageResName = null;
        }
        this.mDisabledMessageResId = i;
        this.mDisabledMessage = null;
    }

    public void setDisabledMessage(java.lang.String str) {
        this.mDisabledMessage = str;
        this.mDisabledMessageResId = 0;
        this.mDisabledMessageResName = null;
    }

    public java.lang.String getTitleResName() {
        return this.mTitleResName;
    }

    public void setTitleResName(java.lang.String str) {
        this.mTitleResName = str;
    }

    public java.lang.String getTextResName() {
        return this.mTextResName;
    }

    public void setTextResName(java.lang.String str) {
        this.mTextResName = str;
    }

    public java.lang.String getDisabledMessageResName() {
        return this.mDisabledMessageResName;
    }

    public void setDisabledMessageResName(java.lang.String str) {
        this.mDisabledMessageResName = str;
    }

    public java.lang.String getIconResName() {
        return this.mIconResName;
    }

    public void setIconResName(java.lang.String str) {
        this.mIconResName = str;
    }

    public void setIntents(android.content.Intent[] intentArr) throws java.lang.IllegalArgumentException {
        java.util.Objects.requireNonNull(intentArr);
        com.android.internal.util.Preconditions.checkArgument(intentArr.length > 0);
        this.mIntents = cloneIntents(intentArr);
        fixUpIntentExtras();
    }

    public static android.content.Intent setIntentExtras(android.content.Intent intent, android.os.PersistableBundle persistableBundle) {
        if (persistableBundle == null) {
            intent.replaceExtras((android.os.Bundle) null);
        } else {
            intent.replaceExtras(new android.os.Bundle(persistableBundle));
        }
        return intent;
    }

    public void setCategories(java.util.Set<java.lang.String> set) {
        this.mCategories = cloneCategories(set);
    }

    public boolean isExcludedFromSurfaces(int i) {
        return (i & this.mExcludedSurfaces) != 0;
    }

    public int getExcludedFromSurfaces() {
        return this.mExcludedSurfaces;
    }

    public java.util.Map<java.lang.String, java.util.Map<java.lang.String, java.util.List<java.lang.String>>> getCapabilityBindingsInternal() {
        return cloneCapabilityBindings(this.mCapabilityBindings);
    }

    private static java.util.Map<java.lang.String, java.util.Map<java.lang.String, java.util.List<java.lang.String>>> cloneCapabilityBindings(java.util.Map<java.lang.String, java.util.Map<java.lang.String, java.util.List<java.lang.String>>> map) {
        android.util.ArrayMap arrayMap;
        if (map == null) {
            return null;
        }
        android.util.ArrayMap arrayMap2 = new android.util.ArrayMap();
        for (java.lang.String str : map.keySet()) {
            java.util.Map<java.lang.String, java.util.List<java.lang.String>> map2 = map.get(str);
            if (map2 == null) {
                arrayMap = null;
            } else {
                arrayMap = new android.util.ArrayMap(map2.size());
                for (java.lang.String str2 : map2.keySet()) {
                    arrayMap.put(str2, java.util.Collections.unmodifiableList(map2.get(str2)));
                }
            }
            arrayMap2.put(str, java.util.Collections.unmodifiableMap(arrayMap));
        }
        return java.util.Collections.unmodifiableMap(arrayMap2);
    }

    public java.util.List<android.content.pm.Capability> getCapabilities() {
        if (this.mCapabilityBindings == null) {
            return new java.util.ArrayList(0);
        }
        return (java.util.List) this.mCapabilityBindings.keySet().stream().map(new java.util.function.Function() { // from class: android.content.pm.ShortcutInfo$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                return new android.content.pm.Capability((java.lang.String) obj);
            }
        }).collect(java.util.stream.Collectors.toList());
    }

    public java.util.List<android.content.pm.CapabilityParams> getCapabilityParams(android.content.pm.Capability capability) {
        java.util.Objects.requireNonNull(capability);
        if (this.mCapabilityBindings == null) {
            return new java.util.ArrayList(0);
        }
        java.util.Map<java.lang.String, java.util.List<java.lang.String>> map = this.mCapabilityBindings.get(capability.getName());
        if (map == null) {
            return new java.util.ArrayList(0);
        }
        java.util.ArrayList arrayList = new java.util.ArrayList(map.size());
        for (java.lang.String str : map.keySet()) {
            java.util.List<java.lang.String> list = map.get(str);
            java.lang.String str2 = list.get(0);
            java.util.List<java.lang.String> emptyList = list.size() == 1 ? java.util.Collections.emptyList() : list.subList(1, list.size());
            android.content.pm.CapabilityParams.Builder builder = new android.content.pm.CapabilityParams.Builder(str, str2);
            java.util.Iterator<java.lang.String> it = emptyList.iterator();
            while (it.hasNext()) {
                builder = builder.addAlias(it.next());
            }
            arrayList.add(builder.build());
        }
        return arrayList;
    }

    private ShortcutInfo(android.os.Parcel parcel) {
        java.lang.ClassLoader classLoader = getClass().getClassLoader();
        this.mUserId = parcel.readInt();
        this.mId = getSafeId((java.lang.String) com.android.internal.util.Preconditions.checkStringNotEmpty(parcel.readString8(), "Shortcut ID must be provided"));
        this.mPackageName = parcel.readString8();
        this.mActivity = (android.content.ComponentName) parcel.readParcelable(classLoader, android.content.ComponentName.class);
        this.mFlags = parcel.readInt();
        this.mIconResId = parcel.readInt();
        this.mLastChangedTimestamp = parcel.readLong();
        this.mDisabledReason = parcel.readInt();
        if (parcel.readInt() == 0) {
            return;
        }
        this.mIcon = (android.graphics.drawable.Icon) parcel.readParcelable(classLoader, android.graphics.drawable.Icon.class);
        this.mTitle = parcel.readCharSequence();
        this.mTitleResId = parcel.readInt();
        this.mText = parcel.readCharSequence();
        this.mTextResId = parcel.readInt();
        this.mDisabledMessage = parcel.readCharSequence();
        this.mDisabledMessageResId = parcel.readInt();
        this.mIntents = (android.content.Intent[]) parcel.readParcelableArray(classLoader, android.content.Intent.class);
        this.mIntentPersistableExtrases = (android.os.PersistableBundle[]) parcel.readParcelableArray(classLoader, android.os.PersistableBundle.class);
        this.mRank = parcel.readInt();
        this.mExtras = (android.os.PersistableBundle) parcel.readParcelable(classLoader, android.os.PersistableBundle.class);
        this.mBitmapPath = parcel.readString8();
        this.mIconResName = parcel.readString8();
        this.mTitleResName = parcel.readString8();
        this.mTextResName = parcel.readString8();
        this.mDisabledMessageResName = parcel.readString8();
        int readInt = parcel.readInt();
        if (readInt == 0) {
            this.mCategories = null;
        } else {
            this.mCategories = new android.util.ArraySet<>(readInt);
            for (int i = 0; i < readInt; i++) {
                this.mCategories.add(parcel.readString8().intern());
            }
        }
        this.mPersons = (android.app.Person[]) parcel.readParcelableArray(classLoader, android.app.Person.class);
        this.mLocusId = (android.content.LocusId) parcel.readParcelable(classLoader, android.content.LocusId.class);
        this.mIconUri = parcel.readString8();
        this.mStartingThemeResName = parcel.readString8();
        this.mExcludedSurfaces = parcel.readInt();
        java.util.HashMap readHashMap = parcel.readHashMap(null, java.lang.String.class, java.util.HashMap.class);
        if (readHashMap != null && !readHashMap.isEmpty()) {
            final android.util.ArrayMap arrayMap = new android.util.ArrayMap(readHashMap.size());
            java.util.Objects.requireNonNull(arrayMap);
            readHashMap.forEach(new java.util.function.BiConsumer() { // from class: android.content.pm.ShortcutInfo$$ExternalSyntheticLambda1
                @Override // java.util.function.BiConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                    arrayMap.put((java.lang.String) obj, (java.util.Map) obj2);
                }
            });
            this.mCapabilityBindings = cloneCapabilityBindings(arrayMap);
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mUserId);
        parcel.writeString8(this.mId);
        parcel.writeString8(this.mPackageName);
        parcel.writeParcelable(this.mActivity, i);
        parcel.writeInt(this.mFlags);
        parcel.writeInt(this.mIconResId);
        parcel.writeLong(this.mLastChangedTimestamp);
        parcel.writeInt(this.mDisabledReason);
        if (hasKeyFieldsOnly()) {
            parcel.writeInt(0);
            return;
        }
        parcel.writeInt(1);
        parcel.writeParcelable(this.mIcon, i);
        parcel.writeCharSequence(this.mTitle);
        parcel.writeInt(this.mTitleResId);
        parcel.writeCharSequence(this.mText);
        parcel.writeInt(this.mTextResId);
        parcel.writeCharSequence(this.mDisabledMessage);
        parcel.writeInt(this.mDisabledMessageResId);
        parcel.writeParcelableArray(this.mIntents, i);
        parcel.writeParcelableArray(this.mIntentPersistableExtrases, i);
        parcel.writeInt(this.mRank);
        parcel.writeParcelable(this.mExtras, i);
        parcel.writeString8(this.mBitmapPath);
        parcel.writeString8(this.mIconResName);
        parcel.writeString8(this.mTitleResName);
        parcel.writeString8(this.mTextResName);
        parcel.writeString8(this.mDisabledMessageResName);
        if (this.mCategories != null) {
            int size = this.mCategories.size();
            parcel.writeInt(size);
            for (int i2 = 0; i2 < size; i2++) {
                parcel.writeString8(this.mCategories.valueAt(i2));
            }
        } else {
            parcel.writeInt(0);
        }
        parcel.writeParcelableArray(this.mPersons, i);
        parcel.writeParcelable(this.mLocusId, i);
        parcel.writeString8(this.mIconUri);
        parcel.writeString8(this.mStartingThemeResName);
        parcel.writeInt(this.mExcludedSurfaces);
        parcel.writeMap(this.mCapabilityBindings);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public java.lang.String toString() {
        return toStringInner(true, false, null);
    }

    public java.lang.String toInsecureString() {
        return toStringInner(false, true, null);
    }

    public java.lang.String toDumpString(java.lang.String str) {
        return toStringInner(false, true, str);
    }

    private void addIndentOrComma(java.lang.StringBuilder sb, java.lang.String str) {
        if (str != null) {
            sb.append("\n  ");
            sb.append(str);
        } else {
            sb.append(", ");
        }
    }

    private java.lang.String toStringInner(boolean z, boolean z2, java.lang.String str) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        if (str != null) {
            sb.append(str);
        }
        sb.append("ShortcutInfo {");
        sb.append("id=");
        sb.append(z ? "***" : this.mId);
        sb.append(", flags=0x");
        sb.append(java.lang.Integer.toHexString(this.mFlags));
        sb.append(" [");
        if ((this.mFlags & 4096) != 0) {
            sb.append("Sdw");
        }
        if (!isEnabled()) {
            sb.append(android.content.pm.AppSearchShortcutInfo.IS_DISABLED);
        }
        if (isImmutable()) {
            sb.append(android.content.pm.AppSearchShortcutInfo.IS_IMMUTABLE);
        }
        if (isManifestShortcut()) {
            sb.append(android.content.pm.AppSearchShortcutInfo.IS_MANIFEST);
        }
        if (isDynamic()) {
            sb.append(android.content.pm.AppSearchShortcutInfo.IS_DYNAMIC);
        }
        if (isPinned()) {
            sb.append("Pin");
        }
        if (hasIconFile()) {
            sb.append("Ic-f");
        }
        if (isIconPendingSave()) {
            sb.append("Pens");
        }
        if (hasIconResource()) {
            sb.append("Ic-r");
        }
        if (hasIconUri()) {
            sb.append("Ic-u");
        }
        if (hasAdaptiveBitmap()) {
            sb.append("Ic-a");
        }
        if (hasKeyFieldsOnly()) {
            sb.append("Key");
        }
        if (hasStringResourcesResolved()) {
            sb.append("Str");
        }
        if (isReturnedByServer()) {
            sb.append("Rets");
        }
        if (isLongLived()) {
            sb.append("Liv");
        }
        if (isExcludedFromSurfaces(1)) {
            sb.append("Hid-L");
        }
        sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END);
        addIndentOrComma(sb, str);
        sb.append("packageName=");
        sb.append(this.mPackageName);
        addIndentOrComma(sb, str);
        sb.append("activity=");
        sb.append(this.mActivity);
        addIndentOrComma(sb, str);
        sb.append("shortLabel=");
        sb.append(z ? "***" : this.mTitle);
        sb.append(", resId=");
        sb.append(this.mTitleResId);
        sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_START);
        sb.append(this.mTitleResName);
        sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END);
        addIndentOrComma(sb, str);
        sb.append("longLabel=");
        sb.append(z ? "***" : this.mText);
        sb.append(", resId=");
        sb.append(this.mTextResId);
        sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_START);
        sb.append(this.mTextResName);
        sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END);
        addIndentOrComma(sb, str);
        sb.append("disabledMessage=");
        sb.append(z ? "***" : this.mDisabledMessage);
        sb.append(", resId=");
        sb.append(this.mDisabledMessageResId);
        sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_START);
        sb.append(this.mDisabledMessageResName);
        sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END);
        addIndentOrComma(sb, str);
        sb.append("disabledReason=");
        sb.append(getDisabledReasonDebugString(this.mDisabledReason));
        if (this.mStartingThemeResName != null && !this.mStartingThemeResName.isEmpty()) {
            addIndentOrComma(sb, str);
            sb.append("SplashScreenThemeResName=");
            sb.append(this.mStartingThemeResName);
        }
        addIndentOrComma(sb, str);
        sb.append("categories=");
        sb.append(this.mCategories);
        addIndentOrComma(sb, str);
        sb.append("persons=");
        sb.append(java.util.Arrays.toString(this.mPersons));
        addIndentOrComma(sb, str);
        sb.append("icon=");
        sb.append(this.mIcon);
        addIndentOrComma(sb, str);
        sb.append("rank=");
        sb.append(this.mRank);
        sb.append(", timestamp=");
        sb.append(this.mLastChangedTimestamp);
        addIndentOrComma(sb, str);
        sb.append("intents=");
        if (this.mIntents == null) {
            sb.append("null");
        } else if (z) {
            sb.append("size:");
            sb.append(this.mIntents.length);
        } else {
            int length = this.mIntents.length;
            sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_START);
            java.lang.String str2 = "";
            int i = 0;
            while (i < length) {
                sb.append(str2);
                sb.append(this.mIntents[i]);
                sb.append("/");
                sb.append(this.mIntentPersistableExtrases[i]);
                i++;
                str2 = ", ";
            }
            sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END);
        }
        addIndentOrComma(sb, str);
        sb.append("extras=");
        sb.append(this.mExtras);
        if (z2) {
            addIndentOrComma(sb, str);
            sb.append("iconRes=");
            sb.append(this.mIconResId);
            sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_START);
            sb.append(this.mIconResName);
            sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END);
            sb.append(", bitmapPath=");
            sb.append(this.mBitmapPath);
            sb.append(", iconUri=");
            sb.append(this.mIconUri);
        }
        if (this.mLocusId != null) {
            sb.append("locusId=");
            sb.append(this.mLocusId);
        }
        sb.append("}");
        return sb.toString();
    }

    public ShortcutInfo(int i, java.lang.String str, java.lang.String str2, android.content.ComponentName componentName, android.graphics.drawable.Icon icon, java.lang.CharSequence charSequence, int i2, java.lang.String str3, java.lang.CharSequence charSequence2, int i3, java.lang.String str4, java.lang.CharSequence charSequence3, int i4, java.lang.String str5, java.util.Set<java.lang.String> set, android.content.Intent[] intentArr, int i5, android.os.PersistableBundle persistableBundle, long j, int i6, int i7, java.lang.String str6, java.lang.String str7, java.lang.String str8, int i8, android.app.Person[] personArr, android.content.LocusId locusId, java.lang.String str9, java.util.Map<java.lang.String, java.util.Map<java.lang.String, java.util.List<java.lang.String>>> map) {
        this.mUserId = i;
        this.mId = str;
        this.mPackageName = str2;
        this.mActivity = componentName;
        this.mIcon = icon;
        this.mTitle = charSequence;
        this.mTitleResId = i2;
        this.mTitleResName = str3;
        this.mText = charSequence2;
        this.mTextResId = i3;
        this.mTextResName = str4;
        this.mDisabledMessage = charSequence3;
        this.mDisabledMessageResId = i4;
        this.mDisabledMessageResName = str5;
        this.mCategories = cloneCategories(set);
        this.mIntents = cloneIntents(intentArr);
        fixUpIntentExtras();
        this.mRank = i5;
        this.mExtras = persistableBundle;
        this.mLastChangedTimestamp = j;
        this.mFlags = i6;
        this.mIconResId = i7;
        this.mIconResName = str6;
        this.mBitmapPath = str7;
        this.mIconUri = str8;
        this.mDisabledReason = i8;
        this.mPersons = personArr;
        this.mLocusId = locusId;
        this.mStartingThemeResName = str9;
        this.mCapabilityBindings = cloneCapabilityBindings(map);
    }
}
