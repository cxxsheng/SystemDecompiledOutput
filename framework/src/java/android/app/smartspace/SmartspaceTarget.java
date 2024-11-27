package android.app.smartspace;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class SmartspaceTarget implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.app.smartspace.SmartspaceTarget> CREATOR = new android.os.Parcelable.Creator<android.app.smartspace.SmartspaceTarget>() { // from class: android.app.smartspace.SmartspaceTarget.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.smartspace.SmartspaceTarget createFromParcel(android.os.Parcel parcel) {
            return new android.app.smartspace.SmartspaceTarget(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.smartspace.SmartspaceTarget[] newArray(int i) {
            return new android.app.smartspace.SmartspaceTarget[i];
        }
    };
    public static final int FEATURE_ALARM = 7;
    public static final int FEATURE_BEDTIME_ROUTINE = 16;
    public static final int FEATURE_BLAZE_BUILD_PROGRESS = 40;
    public static final int FEATURE_CALENDAR = 2;
    public static final int FEATURE_COMMUTE_TIME = 3;
    public static final int FEATURE_CONSENT = 11;
    public static final int FEATURE_CROSS_DEVICE_TIMER = 32;
    public static final int FEATURE_DOORBELL = 30;
    public static final int FEATURE_DRIVING_MODE = 26;
    public static final int FEATURE_EARTHQUAKE_ALERT = 38;
    public static final int FEATURE_EARTHQUAKE_OCCURRED = 41;
    public static final int FEATURE_ETA_MONITORING = 18;
    public static final int FEATURE_FITNESS_TRACKING = 17;
    public static final int FEATURE_FLASHLIGHT = 28;
    public static final int FEATURE_FLIGHT = 4;
    public static final int FEATURE_GAS_STATION_PAYMENT = 24;
    public static final int FEATURE_HOLIDAY_ALARM = 34;
    public static final int FEATURE_LOYALTY_CARD = 14;
    public static final int FEATURE_MEDIA = 15;
    public static final int FEATURE_MEDIA_HEADS_UP = 36;
    public static final int FEATURE_MEDIA_RESUME = 31;
    public static final int FEATURE_MISSED_CALL = 19;
    public static final int FEATURE_ONBOARDING = 8;
    public static final int FEATURE_PACKAGE_TRACKING = 20;
    public static final int FEATURE_PAIRED_DEVICE_STATE = 25;
    public static final int FEATURE_REMINDER = 6;
    public static final int FEATURE_SAFETY_CHECK = 35;
    public static final int FEATURE_SEVERE_WEATHER_ALERT = 33;
    public static final int FEATURE_SHOPPING_LIST = 13;
    public static final int FEATURE_SLEEP_SUMMARY = 27;
    public static final int FEATURE_SPORTS = 9;
    public static final int FEATURE_STEP_COUNTING = 37;
    public static final int FEATURE_STEP_DATE = 39;
    public static final int FEATURE_STOCK_PRICE_CHANGE = 12;
    public static final int FEATURE_STOPWATCH = 22;
    public static final int FEATURE_TIMER = 21;
    public static final int FEATURE_TIME_TO_LEAVE = 29;
    public static final int FEATURE_TIPS = 5;
    public static final int FEATURE_UNDEFINED = 0;
    public static final int FEATURE_UPCOMING_ALARM = 23;
    public static final int FEATURE_WEATHER = 1;
    public static final int FEATURE_WEATHER_ALERT = 10;
    public static final int UI_TEMPLATE_CAROUSEL = 4;
    public static final int UI_TEMPLATE_COMBINED_CARDS = 6;
    public static final int UI_TEMPLATE_DEFAULT = 1;
    public static final int UI_TEMPLATE_HEAD_TO_HEAD = 5;
    public static final int UI_TEMPLATE_SUB_CARD = 7;
    public static final int UI_TEMPLATE_SUB_IMAGE = 2;
    public static final int UI_TEMPLATE_SUB_LIST = 3;
    public static final int UI_TEMPLATE_UNDEFINED = 0;
    private final java.util.List<android.app.smartspace.SmartspaceAction> mActionChips;
    private final java.lang.String mAssociatedSmartspaceTargetId;
    private final android.app.smartspace.SmartspaceAction mBaseAction;
    private final android.content.ComponentName mComponentName;
    private final long mCreationTimeMillis;
    private final long mExpiryTimeMillis;
    private final int mFeatureType;
    private final android.app.smartspace.SmartspaceAction mHeaderAction;
    private final java.util.List<android.app.smartspace.SmartspaceAction> mIconGrid;
    private final android.widget.RemoteViews mRemoteViews;
    private final float mScore;
    private final boolean mSensitive;
    private final boolean mShouldShowExpanded;
    private final android.net.Uri mSliceUri;
    private final java.lang.String mSmartspaceTargetId;
    private final java.lang.String mSourceNotificationKey;
    private final android.app.smartspace.uitemplatedata.BaseTemplateData mTemplateData;
    private final android.os.UserHandle mUserHandle;
    private final android.appwidget.AppWidgetProviderInfo mWidget;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface FeatureType {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface UiTemplateType {
    }

    private SmartspaceTarget(android.os.Parcel parcel) {
        this.mSmartspaceTargetId = parcel.readString();
        this.mHeaderAction = (android.app.smartspace.SmartspaceAction) parcel.readTypedObject(android.app.smartspace.SmartspaceAction.CREATOR);
        this.mBaseAction = (android.app.smartspace.SmartspaceAction) parcel.readTypedObject(android.app.smartspace.SmartspaceAction.CREATOR);
        this.mCreationTimeMillis = parcel.readLong();
        this.mExpiryTimeMillis = parcel.readLong();
        this.mScore = parcel.readFloat();
        this.mActionChips = parcel.createTypedArrayList(android.app.smartspace.SmartspaceAction.CREATOR);
        this.mIconGrid = parcel.createTypedArrayList(android.app.smartspace.SmartspaceAction.CREATOR);
        this.mFeatureType = parcel.readInt();
        this.mSensitive = parcel.readBoolean();
        this.mShouldShowExpanded = parcel.readBoolean();
        this.mSourceNotificationKey = parcel.readString();
        this.mComponentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
        this.mUserHandle = (android.os.UserHandle) parcel.readTypedObject(android.os.UserHandle.CREATOR);
        this.mAssociatedSmartspaceTargetId = parcel.readString();
        this.mSliceUri = (android.net.Uri) parcel.readTypedObject(android.net.Uri.CREATOR);
        this.mWidget = (android.appwidget.AppWidgetProviderInfo) parcel.readTypedObject(android.appwidget.AppWidgetProviderInfo.CREATOR);
        this.mTemplateData = (android.app.smartspace.uitemplatedata.BaseTemplateData) parcel.readParcelable(null, android.app.smartspace.uitemplatedata.BaseTemplateData.class);
        this.mRemoteViews = (android.widget.RemoteViews) parcel.readTypedObject(android.widget.RemoteViews.CREATOR);
    }

    private SmartspaceTarget(java.lang.String str, android.app.smartspace.SmartspaceAction smartspaceAction, android.app.smartspace.SmartspaceAction smartspaceAction2, long j, long j2, float f, java.util.List<android.app.smartspace.SmartspaceAction> list, java.util.List<android.app.smartspace.SmartspaceAction> list2, int i, boolean z, boolean z2, java.lang.String str2, android.content.ComponentName componentName, android.os.UserHandle userHandle, java.lang.String str3, android.net.Uri uri, android.appwidget.AppWidgetProviderInfo appWidgetProviderInfo, android.app.smartspace.uitemplatedata.BaseTemplateData baseTemplateData, android.widget.RemoteViews remoteViews) {
        this.mSmartspaceTargetId = str;
        this.mHeaderAction = smartspaceAction;
        this.mBaseAction = smartspaceAction2;
        this.mCreationTimeMillis = j;
        this.mExpiryTimeMillis = j2;
        this.mScore = f;
        this.mActionChips = list;
        this.mIconGrid = list2;
        this.mFeatureType = i;
        this.mSensitive = z;
        this.mShouldShowExpanded = z2;
        this.mSourceNotificationKey = str2;
        this.mComponentName = componentName;
        this.mUserHandle = userHandle;
        this.mAssociatedSmartspaceTargetId = str3;
        this.mSliceUri = uri;
        this.mWidget = appWidgetProviderInfo;
        this.mTemplateData = baseTemplateData;
        this.mRemoteViews = remoteViews;
    }

    public java.lang.String getSmartspaceTargetId() {
        return this.mSmartspaceTargetId;
    }

    public android.app.smartspace.SmartspaceAction getHeaderAction() {
        return this.mHeaderAction;
    }

    public android.app.smartspace.SmartspaceAction getBaseAction() {
        return this.mBaseAction;
    }

    public long getCreationTimeMillis() {
        return this.mCreationTimeMillis;
    }

    public long getExpiryTimeMillis() {
        return this.mExpiryTimeMillis;
    }

    public float getScore() {
        return this.mScore;
    }

    public java.util.List<android.app.smartspace.SmartspaceAction> getActionChips() {
        return this.mActionChips;
    }

    public java.util.List<android.app.smartspace.SmartspaceAction> getIconGrid() {
        return this.mIconGrid;
    }

    public int getFeatureType() {
        return this.mFeatureType;
    }

    public boolean isSensitive() {
        return this.mSensitive;
    }

    public boolean shouldShowExpanded() {
        return this.mShouldShowExpanded;
    }

    public java.lang.String getSourceNotificationKey() {
        return this.mSourceNotificationKey;
    }

    public android.content.ComponentName getComponentName() {
        return this.mComponentName;
    }

    public android.os.UserHandle getUserHandle() {
        return this.mUserHandle;
    }

    public java.lang.String getAssociatedSmartspaceTargetId() {
        return this.mAssociatedSmartspaceTargetId;
    }

    public android.net.Uri getSliceUri() {
        return this.mSliceUri;
    }

    public android.appwidget.AppWidgetProviderInfo getWidget() {
        return this.mWidget;
    }

    public android.app.smartspace.uitemplatedata.BaseTemplateData getTemplateData() {
        return this.mTemplateData;
    }

    public android.widget.RemoteViews getRemoteViews() {
        return this.mRemoteViews;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.mSmartspaceTargetId);
        parcel.writeTypedObject(this.mHeaderAction, i);
        parcel.writeTypedObject(this.mBaseAction, i);
        parcel.writeLong(this.mCreationTimeMillis);
        parcel.writeLong(this.mExpiryTimeMillis);
        parcel.writeFloat(this.mScore);
        parcel.writeTypedList(this.mActionChips);
        parcel.writeTypedList(this.mIconGrid);
        parcel.writeInt(this.mFeatureType);
        parcel.writeBoolean(this.mSensitive);
        parcel.writeBoolean(this.mShouldShowExpanded);
        parcel.writeString(this.mSourceNotificationKey);
        parcel.writeTypedObject(this.mComponentName, i);
        parcel.writeTypedObject(this.mUserHandle, i);
        parcel.writeString(this.mAssociatedSmartspaceTargetId);
        parcel.writeTypedObject(this.mSliceUri, i);
        parcel.writeTypedObject(this.mWidget, i);
        parcel.writeParcelable(this.mTemplateData, i);
        parcel.writeTypedObject(this.mRemoteViews, i);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public java.lang.String toString() {
        return "SmartspaceTarget{mSmartspaceTargetId='" + this.mSmartspaceTargetId + android.text.format.DateFormat.QUOTE + ", mHeaderAction=" + this.mHeaderAction + ", mBaseAction=" + this.mBaseAction + ", mCreationTimeMillis=" + this.mCreationTimeMillis + ", mExpiryTimeMillis=" + this.mExpiryTimeMillis + ", mScore=" + this.mScore + ", mActionChips=" + this.mActionChips + ", mIconGrid=" + this.mIconGrid + ", mFeatureType=" + this.mFeatureType + ", mSensitive=" + this.mSensitive + ", mShouldShowExpanded=" + this.mShouldShowExpanded + ", mSourceNotificationKey='" + this.mSourceNotificationKey + android.text.format.DateFormat.QUOTE + ", mComponentName=" + this.mComponentName + ", mUserHandle=" + this.mUserHandle + ", mAssociatedSmartspaceTargetId='" + this.mAssociatedSmartspaceTargetId + android.text.format.DateFormat.QUOTE + ", mSliceUri=" + this.mSliceUri + ", mWidget=" + this.mWidget + ", mTemplateData=" + this.mTemplateData + ", mRemoteViews=" + this.mRemoteViews + '}';
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.app.smartspace.SmartspaceTarget smartspaceTarget = (android.app.smartspace.SmartspaceTarget) obj;
        if (this.mCreationTimeMillis == smartspaceTarget.mCreationTimeMillis && this.mExpiryTimeMillis == smartspaceTarget.mExpiryTimeMillis && java.lang.Float.compare(smartspaceTarget.mScore, this.mScore) == 0 && this.mFeatureType == smartspaceTarget.mFeatureType && this.mSensitive == smartspaceTarget.mSensitive && this.mShouldShowExpanded == smartspaceTarget.mShouldShowExpanded && this.mSmartspaceTargetId.equals(smartspaceTarget.mSmartspaceTargetId) && java.util.Objects.equals(this.mHeaderAction, smartspaceTarget.mHeaderAction) && java.util.Objects.equals(this.mBaseAction, smartspaceTarget.mBaseAction) && java.util.Objects.equals(this.mActionChips, smartspaceTarget.mActionChips) && java.util.Objects.equals(this.mIconGrid, smartspaceTarget.mIconGrid) && java.util.Objects.equals(this.mSourceNotificationKey, smartspaceTarget.mSourceNotificationKey) && this.mComponentName.equals(smartspaceTarget.mComponentName) && this.mUserHandle.equals(smartspaceTarget.mUserHandle) && java.util.Objects.equals(this.mAssociatedSmartspaceTargetId, smartspaceTarget.mAssociatedSmartspaceTargetId) && java.util.Objects.equals(this.mSliceUri, smartspaceTarget.mSliceUri) && java.util.Objects.equals(this.mWidget, smartspaceTarget.mWidget) && java.util.Objects.equals(this.mTemplateData, smartspaceTarget.mTemplateData) && java.util.Objects.equals(this.mRemoteViews, smartspaceTarget.mRemoteViews)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Objects.hash(this.mSmartspaceTargetId, this.mHeaderAction, this.mBaseAction, java.lang.Long.valueOf(this.mCreationTimeMillis), java.lang.Long.valueOf(this.mExpiryTimeMillis), java.lang.Float.valueOf(this.mScore), this.mActionChips, this.mIconGrid, java.lang.Integer.valueOf(this.mFeatureType), java.lang.Boolean.valueOf(this.mSensitive), java.lang.Boolean.valueOf(this.mShouldShowExpanded), this.mSourceNotificationKey, this.mComponentName, this.mUserHandle, this.mAssociatedSmartspaceTargetId, this.mSliceUri, this.mWidget, this.mTemplateData, this.mRemoteViews);
    }

    @android.annotation.SystemApi
    public static final class Builder {
        private java.lang.String mAssociatedSmartspaceTargetId;
        private android.app.smartspace.SmartspaceAction mBaseAction;
        private final android.content.ComponentName mComponentName;
        private long mCreationTimeMillis;
        private long mExpiryTimeMillis;
        private int mFeatureType;
        private android.app.smartspace.SmartspaceAction mHeaderAction;
        private android.widget.RemoteViews mRemoteViews;
        private float mScore;
        private boolean mSensitive;
        private boolean mShouldShowExpanded;
        private android.net.Uri mSliceUri;
        private final java.lang.String mSmartspaceTargetId;
        private java.lang.String mSourceNotificationKey;
        private android.app.smartspace.uitemplatedata.BaseTemplateData mTemplateData;
        private final android.os.UserHandle mUserHandle;
        private android.appwidget.AppWidgetProviderInfo mWidget;
        private java.util.List<android.app.smartspace.SmartspaceAction> mActionChips = new java.util.ArrayList();
        private java.util.List<android.app.smartspace.SmartspaceAction> mIconGrid = new java.util.ArrayList();

        public Builder(java.lang.String str, android.content.ComponentName componentName, android.os.UserHandle userHandle) {
            this.mSmartspaceTargetId = str;
            this.mComponentName = componentName;
            this.mUserHandle = userHandle;
        }

        public android.app.smartspace.SmartspaceTarget.Builder setHeaderAction(android.app.smartspace.SmartspaceAction smartspaceAction) {
            this.mHeaderAction = smartspaceAction;
            return this;
        }

        public android.app.smartspace.SmartspaceTarget.Builder setBaseAction(android.app.smartspace.SmartspaceAction smartspaceAction) {
            this.mBaseAction = smartspaceAction;
            return this;
        }

        public android.app.smartspace.SmartspaceTarget.Builder setCreationTimeMillis(long j) {
            this.mCreationTimeMillis = j;
            return this;
        }

        public android.app.smartspace.SmartspaceTarget.Builder setExpiryTimeMillis(long j) {
            this.mExpiryTimeMillis = j;
            return this;
        }

        public android.app.smartspace.SmartspaceTarget.Builder setScore(float f) {
            this.mScore = f;
            return this;
        }

        public android.app.smartspace.SmartspaceTarget.Builder setActionChips(java.util.List<android.app.smartspace.SmartspaceAction> list) {
            this.mActionChips = list;
            return this;
        }

        public android.app.smartspace.SmartspaceTarget.Builder setIconGrid(java.util.List<android.app.smartspace.SmartspaceAction> list) {
            this.mIconGrid = list;
            return this;
        }

        public android.app.smartspace.SmartspaceTarget.Builder setFeatureType(int i) {
            this.mFeatureType = i;
            return this;
        }

        public android.app.smartspace.SmartspaceTarget.Builder setSensitive(boolean z) {
            this.mSensitive = z;
            return this;
        }

        public android.app.smartspace.SmartspaceTarget.Builder setShouldShowExpanded(boolean z) {
            this.mShouldShowExpanded = z;
            return this;
        }

        public android.app.smartspace.SmartspaceTarget.Builder setSourceNotificationKey(java.lang.String str) {
            this.mSourceNotificationKey = str;
            return this;
        }

        public android.app.smartspace.SmartspaceTarget.Builder setAssociatedSmartspaceTargetId(java.lang.String str) {
            this.mAssociatedSmartspaceTargetId = str;
            return this;
        }

        public android.app.smartspace.SmartspaceTarget.Builder setSliceUri(android.net.Uri uri) {
            this.mSliceUri = uri;
            return this;
        }

        public android.app.smartspace.SmartspaceTarget.Builder setWidget(android.appwidget.AppWidgetProviderInfo appWidgetProviderInfo) {
            if (this.mRemoteViews != null) {
                throw new java.lang.IllegalStateException("Widget providers and RemoteViews cannot be used at the same time.");
            }
            this.mWidget = appWidgetProviderInfo;
            return this;
        }

        public android.app.smartspace.SmartspaceTarget.Builder setTemplateData(android.app.smartspace.uitemplatedata.BaseTemplateData baseTemplateData) {
            this.mTemplateData = baseTemplateData;
            return this;
        }

        public android.app.smartspace.SmartspaceTarget.Builder setRemoteViews(android.widget.RemoteViews remoteViews) {
            if (this.mWidget != null) {
                throw new java.lang.IllegalStateException("Widget providers and RemoteViews cannot be used at the same time.");
            }
            this.mRemoteViews = remoteViews;
            return this;
        }

        public android.app.smartspace.SmartspaceTarget build() {
            if (this.mSmartspaceTargetId == null || this.mComponentName == null || this.mUserHandle == null) {
                throw new java.lang.IllegalStateException("Please assign a value to all @NonNull args.");
            }
            return new android.app.smartspace.SmartspaceTarget(this.mSmartspaceTargetId, this.mHeaderAction, this.mBaseAction, this.mCreationTimeMillis, this.mExpiryTimeMillis, this.mScore, this.mActionChips, this.mIconGrid, this.mFeatureType, this.mSensitive, this.mShouldShowExpanded, this.mSourceNotificationKey, this.mComponentName, this.mUserHandle, this.mAssociatedSmartspaceTargetId, this.mSliceUri, this.mWidget, this.mTemplateData, this.mRemoteViews);
        }
    }
}
