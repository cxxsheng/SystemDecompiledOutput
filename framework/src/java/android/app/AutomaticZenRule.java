package android.app;

/* loaded from: classes.dex */
public final class AutomaticZenRule implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.app.AutomaticZenRule> CREATOR = new android.os.Parcelable.Creator<android.app.AutomaticZenRule>() { // from class: android.app.AutomaticZenRule.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.AutomaticZenRule createFromParcel(android.os.Parcel parcel) {
            return new android.app.AutomaticZenRule(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.AutomaticZenRule[] newArray(int i) {
            return new android.app.AutomaticZenRule[i];
        }
    };
    private static final int DISABLED = 0;
    private static final int ENABLED = 1;
    public static final int FIELD_INTERRUPTION_FILTER = 2;
    public static final int FIELD_NAME = 1;
    public static final int MAX_DESC_LENGTH = 150;
    public static final int MAX_STRING_LENGTH = 1000;
    public static final int TYPE_BEDTIME = 3;
    public static final int TYPE_DRIVING = 4;
    public static final int TYPE_IMMERSIVE = 5;
    public static final int TYPE_MANAGED = 7;
    public static final int TYPE_OTHER = 0;
    public static final int TYPE_SCHEDULE_CALENDAR = 2;
    public static final int TYPE_SCHEDULE_TIME = 1;
    public static final int TYPE_THEATER = 6;
    public static final int TYPE_UNKNOWN = -1;
    private android.net.Uri conditionId;
    private android.content.ComponentName configurationActivity;
    private long creationTime;
    private boolean enabled;
    private int interruptionFilter;
    private boolean mAllowManualInvocation;
    private android.service.notification.ZenDeviceEffects mDeviceEffects;
    private int mIconResId;
    private boolean mModified;
    private java.lang.String mPkg;
    private java.lang.String mTriggerDescription;
    private int mType;
    private android.service.notification.ZenPolicy mZenPolicy;
    private java.lang.String name;
    private android.content.ComponentName owner;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ModifiableField {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Type {
    }

    @java.lang.Deprecated
    public AutomaticZenRule(java.lang.String str, android.content.ComponentName componentName, android.net.Uri uri, int i, boolean z) {
        this(str, componentName, null, uri, null, i, z);
    }

    public AutomaticZenRule(java.lang.String str, android.content.ComponentName componentName, android.content.ComponentName componentName2, android.net.Uri uri, android.service.notification.ZenPolicy zenPolicy, int i, boolean z) {
        this.mModified = false;
        this.mType = android.app.Flags.modesApi() ? -1 : 0;
        this.name = getTrimmedString(str);
        this.owner = getTrimmedComponentName(componentName);
        this.configurationActivity = getTrimmedComponentName(componentName2);
        this.conditionId = getTrimmedUri(uri);
        this.interruptionFilter = i;
        this.enabled = z;
        this.mZenPolicy = zenPolicy;
    }

    public AutomaticZenRule(java.lang.String str, android.content.ComponentName componentName, android.content.ComponentName componentName2, android.net.Uri uri, android.service.notification.ZenPolicy zenPolicy, int i, boolean z, long j) {
        this(str, componentName, componentName2, uri, zenPolicy, i, z);
        this.creationTime = j;
    }

    public AutomaticZenRule(android.os.Parcel parcel) {
        this.mModified = false;
        this.mType = android.app.Flags.modesApi() ? -1 : 0;
        this.enabled = parcel.readInt() == 1;
        if (parcel.readInt() == 1) {
            this.name = getTrimmedString(parcel.readString());
        }
        this.interruptionFilter = parcel.readInt();
        this.conditionId = getTrimmedUri((android.net.Uri) parcel.readParcelable(null, android.net.Uri.class));
        this.owner = getTrimmedComponentName((android.content.ComponentName) parcel.readParcelable(null, android.content.ComponentName.class));
        this.configurationActivity = getTrimmedComponentName((android.content.ComponentName) parcel.readParcelable(null, android.content.ComponentName.class));
        this.creationTime = parcel.readLong();
        this.mZenPolicy = (android.service.notification.ZenPolicy) parcel.readParcelable(null, android.service.notification.ZenPolicy.class);
        this.mModified = parcel.readInt() == 1;
        this.mPkg = parcel.readString();
        if (android.app.Flags.modesApi()) {
            this.mDeviceEffects = (android.service.notification.ZenDeviceEffects) parcel.readParcelable(null, android.service.notification.ZenDeviceEffects.class);
            this.mAllowManualInvocation = parcel.readBoolean();
            this.mIconResId = parcel.readInt();
            this.mTriggerDescription = getTrimmedString(parcel.readString(), 150);
            this.mType = parcel.readInt();
        }
    }

    public android.content.ComponentName getOwner() {
        return this.owner;
    }

    public android.content.ComponentName getConfigurationActivity() {
        return this.configurationActivity;
    }

    public android.net.Uri getConditionId() {
        return this.conditionId;
    }

    public int getInterruptionFilter() {
        return this.interruptionFilter;
    }

    public java.lang.String getName() {
        return this.name;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public boolean isModified() {
        return this.mModified;
    }

    public android.service.notification.ZenPolicy getZenPolicy() {
        if (this.mZenPolicy == null) {
            return null;
        }
        return this.mZenPolicy.copy();
    }

    public android.service.notification.ZenDeviceEffects getDeviceEffects() {
        return this.mDeviceEffects;
    }

    public long getCreationTime() {
        return this.creationTime;
    }

    public void setConditionId(android.net.Uri uri) {
        this.conditionId = getTrimmedUri(uri);
    }

    public void setInterruptionFilter(int i) {
        this.interruptionFilter = i;
    }

    public void setName(java.lang.String str) {
        this.name = getTrimmedString(str);
    }

    public void setEnabled(boolean z) {
        this.enabled = z;
    }

    public void setModified(boolean z) {
        this.mModified = z;
    }

    public void setZenPolicy(android.service.notification.ZenPolicy zenPolicy) {
        this.mZenPolicy = zenPolicy == null ? null : zenPolicy.copy();
    }

    public void setDeviceEffects(android.service.notification.ZenDeviceEffects zenDeviceEffects) {
        this.mDeviceEffects = zenDeviceEffects;
    }

    public void setConfigurationActivity(android.content.ComponentName componentName) {
        this.configurationActivity = getTrimmedComponentName(componentName);
    }

    public void setPackageName(java.lang.String str) {
        this.mPkg = str;
    }

    public java.lang.String getPackageName() {
        return this.mPkg;
    }

    public int getType() {
        return this.mType;
    }

    public void setType(int i) {
        this.mType = checkValidType(i);
    }

    public java.lang.String getTriggerDescription() {
        return this.mTriggerDescription;
    }

    public void setTriggerDescription(java.lang.String str) {
        this.mTriggerDescription = str;
    }

    public int getIconResId() {
        return this.mIconResId;
    }

    public void setIconResId(int i) {
        this.mIconResId = i;
    }

    public boolean isManualInvocationAllowed() {
        return this.mAllowManualInvocation;
    }

    public void setManualInvocationAllowed(boolean z) {
        this.mAllowManualInvocation = z;
    }

    public void validate() {
        if (android.app.Flags.modesApi()) {
            checkValidType(this.mType);
            if (this.mDeviceEffects != null) {
                this.mDeviceEffects.validate();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int checkValidType(int i) {
        com.android.internal.util.Preconditions.checkArgument(i >= -1 && i <= 7, "Rule type must be one of TYPE_UNKNOWN, TYPE_OTHER, TYPE_SCHEDULE_TIME, TYPE_SCHEDULE_CALENDAR, TYPE_BEDTIME, TYPE_DRIVING, TYPE_IMMERSIVE, TYPE_THEATER, or TYPE_MANAGED");
        return i;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.enabled ? 1 : 0);
        if (this.name != null) {
            parcel.writeInt(1);
            parcel.writeString(this.name);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeInt(this.interruptionFilter);
        parcel.writeParcelable(this.conditionId, 0);
        parcel.writeParcelable(this.owner, 0);
        parcel.writeParcelable(this.configurationActivity, 0);
        parcel.writeLong(this.creationTime);
        parcel.writeParcelable(this.mZenPolicy, 0);
        parcel.writeInt(this.mModified ? 1 : 0);
        parcel.writeString(this.mPkg);
        if (android.app.Flags.modesApi()) {
            parcel.writeParcelable(this.mDeviceEffects, 0);
            parcel.writeBoolean(this.mAllowManualInvocation);
            parcel.writeInt(this.mIconResId);
            parcel.writeString(this.mTriggerDescription);
            parcel.writeInt(this.mType);
        }
    }

    public java.lang.String toString() {
        java.lang.StringBuilder append = new java.lang.StringBuilder(android.app.AutomaticZenRule.class.getSimpleName()).append('[').append("enabled=").append(this.enabled).append(",name=").append(this.name).append(",interruptionFilter=").append(this.interruptionFilter).append(",pkg=").append(this.mPkg).append(",conditionId=").append(this.conditionId).append(",owner=").append(this.owner).append(",configActivity=").append(this.configurationActivity).append(",creationTime=").append(this.creationTime).append(",mZenPolicy=").append(this.mZenPolicy);
        if (android.app.Flags.modesApi()) {
            append.append(",deviceEffects=").append(this.mDeviceEffects).append(",allowManualInvocation=").append(this.mAllowManualInvocation).append(",iconResId=").append(this.mIconResId).append(",triggerDescription=").append(this.mTriggerDescription).append(",type=").append(this.mType);
        }
        return append.append(']').toString();
    }

    public static java.lang.String fieldsToString(int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        if ((i & 1) != 0) {
            arrayList.add("FIELD_NAME");
        }
        if ((i & 2) != 0) {
            arrayList.add("FIELD_INTERRUPTION_FILTER");
        }
        return "{" + java.lang.String.join(",", arrayList) + "}";
    }

    public boolean equals(java.lang.Object obj) {
        if (!(obj instanceof android.app.AutomaticZenRule)) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        android.app.AutomaticZenRule automaticZenRule = (android.app.AutomaticZenRule) obj;
        boolean z = automaticZenRule.enabled == this.enabled && automaticZenRule.mModified == this.mModified && java.util.Objects.equals(automaticZenRule.name, this.name) && automaticZenRule.interruptionFilter == this.interruptionFilter && java.util.Objects.equals(automaticZenRule.conditionId, this.conditionId) && java.util.Objects.equals(automaticZenRule.owner, this.owner) && java.util.Objects.equals(automaticZenRule.mZenPolicy, this.mZenPolicy) && java.util.Objects.equals(automaticZenRule.configurationActivity, this.configurationActivity) && java.util.Objects.equals(automaticZenRule.mPkg, this.mPkg) && automaticZenRule.creationTime == this.creationTime;
        if (android.app.Flags.modesApi()) {
            return z && java.util.Objects.equals(automaticZenRule.mDeviceEffects, this.mDeviceEffects) && automaticZenRule.mAllowManualInvocation == this.mAllowManualInvocation && automaticZenRule.mIconResId == this.mIconResId && java.util.Objects.equals(automaticZenRule.mTriggerDescription, this.mTriggerDescription) && automaticZenRule.mType == this.mType;
        }
        return z;
    }

    public int hashCode() {
        if (android.app.Flags.modesApi()) {
            return java.util.Objects.hash(java.lang.Boolean.valueOf(this.enabled), this.name, java.lang.Integer.valueOf(this.interruptionFilter), this.conditionId, this.owner, this.configurationActivity, this.mZenPolicy, this.mDeviceEffects, java.lang.Boolean.valueOf(this.mModified), java.lang.Long.valueOf(this.creationTime), this.mPkg, java.lang.Boolean.valueOf(this.mAllowManualInvocation), java.lang.Integer.valueOf(this.mIconResId), this.mTriggerDescription, java.lang.Integer.valueOf(this.mType));
        }
        return java.util.Objects.hash(java.lang.Boolean.valueOf(this.enabled), this.name, java.lang.Integer.valueOf(this.interruptionFilter), this.conditionId, this.owner, this.configurationActivity, this.mZenPolicy, java.lang.Boolean.valueOf(this.mModified), java.lang.Long.valueOf(this.creationTime), this.mPkg);
    }

    private static android.content.ComponentName getTrimmedComponentName(android.content.ComponentName componentName) {
        if (componentName == null) {
            return null;
        }
        return new android.content.ComponentName(getTrimmedString(componentName.getPackageName()), getTrimmedString(componentName.getClassName()));
    }

    private static java.lang.String getTrimmedString(java.lang.String str) {
        return getTrimmedString(str, 1000);
    }

    private static java.lang.String getTrimmedString(java.lang.String str, int i) {
        if (str != null && str.length() > i) {
            return str.substring(0, i);
        }
        return str;
    }

    private static android.net.Uri getTrimmedUri(android.net.Uri uri) {
        if (uri != null && uri.toString().length() > 1000) {
            return android.net.Uri.parse(getTrimmedString(uri.toString()));
        }
        return uri;
    }

    public static final class Builder {
        private boolean mAllowManualInvocation;
        private android.net.Uri mConditionId;
        private android.content.ComponentName mConfigurationActivity;
        private long mCreationTime;
        private java.lang.String mDescription;
        private android.service.notification.ZenDeviceEffects mDeviceEffects;
        private boolean mEnabled;
        private int mIconResId;
        private int mInterruptionFilter;
        private java.lang.String mName;
        private android.content.ComponentName mOwner;
        private java.lang.String mPkg;
        private android.service.notification.ZenPolicy mPolicy;
        private int mType;

        public Builder(android.app.AutomaticZenRule automaticZenRule) {
            this.mEnabled = true;
            this.mConfigurationActivity = null;
            this.mPolicy = null;
            this.mDeviceEffects = null;
            this.mName = automaticZenRule.getName();
            this.mOwner = automaticZenRule.getOwner();
            this.mConditionId = automaticZenRule.getConditionId();
            this.mInterruptionFilter = automaticZenRule.getInterruptionFilter();
            this.mEnabled = automaticZenRule.isEnabled();
            this.mConfigurationActivity = automaticZenRule.getConfigurationActivity();
            this.mPolicy = automaticZenRule.getZenPolicy();
            this.mDeviceEffects = automaticZenRule.getDeviceEffects();
            this.mType = automaticZenRule.getType();
            this.mDescription = automaticZenRule.getTriggerDescription();
            this.mIconResId = automaticZenRule.getIconResId();
            this.mAllowManualInvocation = automaticZenRule.isManualInvocationAllowed();
            this.mCreationTime = automaticZenRule.getCreationTime();
            this.mPkg = automaticZenRule.getPackageName();
        }

        public Builder(java.lang.String str, android.net.Uri uri) {
            this.mEnabled = true;
            this.mConfigurationActivity = null;
            this.mPolicy = null;
            this.mDeviceEffects = null;
            this.mName = (java.lang.String) java.util.Objects.requireNonNull(str);
            this.mConditionId = (android.net.Uri) java.util.Objects.requireNonNull(uri);
        }

        public android.app.AutomaticZenRule.Builder setName(java.lang.String str) {
            this.mName = str;
            return this;
        }

        public android.app.AutomaticZenRule.Builder setOwner(android.content.ComponentName componentName) {
            this.mOwner = componentName;
            return this;
        }

        public android.app.AutomaticZenRule.Builder setConditionId(android.net.Uri uri) {
            this.mConditionId = uri;
            return this;
        }

        public android.app.AutomaticZenRule.Builder setInterruptionFilter(int i) {
            this.mInterruptionFilter = i;
            return this;
        }

        public android.app.AutomaticZenRule.Builder setEnabled(boolean z) {
            this.mEnabled = z;
            return this;
        }

        public android.app.AutomaticZenRule.Builder setConfigurationActivity(android.content.ComponentName componentName) {
            this.mConfigurationActivity = componentName;
            return this;
        }

        public android.app.AutomaticZenRule.Builder setZenPolicy(android.service.notification.ZenPolicy zenPolicy) {
            this.mPolicy = zenPolicy;
            return this;
        }

        public android.app.AutomaticZenRule.Builder setDeviceEffects(android.service.notification.ZenDeviceEffects zenDeviceEffects) {
            this.mDeviceEffects = zenDeviceEffects;
            return this;
        }

        public android.app.AutomaticZenRule.Builder setType(int i) {
            this.mType = android.app.AutomaticZenRule.checkValidType(i);
            return this;
        }

        public android.app.AutomaticZenRule.Builder setTriggerDescription(java.lang.String str) {
            this.mDescription = str;
            return this;
        }

        public android.app.AutomaticZenRule.Builder setIconResId(int i) {
            this.mIconResId = i;
            return this;
        }

        public android.app.AutomaticZenRule.Builder setManualInvocationAllowed(boolean z) {
            this.mAllowManualInvocation = z;
            return this;
        }

        public android.app.AutomaticZenRule.Builder setCreationTime(long j) {
            this.mCreationTime = j;
            return this;
        }

        public android.app.AutomaticZenRule build() {
            android.app.AutomaticZenRule automaticZenRule = new android.app.AutomaticZenRule(this.mName, this.mOwner, this.mConfigurationActivity, this.mConditionId, this.mPolicy, this.mInterruptionFilter, this.mEnabled);
            automaticZenRule.mDeviceEffects = this.mDeviceEffects;
            automaticZenRule.creationTime = this.mCreationTime;
            automaticZenRule.mType = this.mType;
            automaticZenRule.mTriggerDescription = this.mDescription;
            automaticZenRule.mIconResId = this.mIconResId;
            automaticZenRule.mAllowManualInvocation = this.mAllowManualInvocation;
            automaticZenRule.setPackageName(this.mPkg);
            return automaticZenRule;
        }
    }
}
