package android.view.textclassifier;

/* loaded from: classes4.dex */
public final class SelectionEvent implements android.os.Parcelable {
    public static final int ACTION_ABANDON = 107;
    public static final int ACTION_COPY = 101;
    public static final int ACTION_CUT = 103;
    public static final int ACTION_DRAG = 106;
    public static final int ACTION_OTHER = 108;
    public static final int ACTION_OVERTYPE = 100;
    public static final int ACTION_PASTE = 102;
    public static final int ACTION_RESET = 201;
    public static final int ACTION_SELECT_ALL = 200;
    public static final int ACTION_SHARE = 104;
    public static final int ACTION_SMART_SHARE = 105;
    public static final android.os.Parcelable.Creator<android.view.textclassifier.SelectionEvent> CREATOR = new android.os.Parcelable.Creator<android.view.textclassifier.SelectionEvent>() { // from class: android.view.textclassifier.SelectionEvent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.textclassifier.SelectionEvent createFromParcel(android.os.Parcel parcel) {
            return new android.view.textclassifier.SelectionEvent(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.textclassifier.SelectionEvent[] newArray(int i) {
            return new android.view.textclassifier.SelectionEvent[i];
        }
    };
    public static final int EVENT_AUTO_SELECTION = 5;
    public static final int EVENT_SELECTION_MODIFIED = 2;
    public static final int EVENT_SELECTION_STARTED = 1;
    public static final int EVENT_SMART_SELECTION_MULTI = 4;
    public static final int EVENT_SMART_SELECTION_SINGLE = 3;
    public static final int INVOCATION_LINK = 2;
    public static final int INVOCATION_MANUAL = 1;
    public static final int INVOCATION_UNKNOWN = 0;
    static final java.lang.String NO_SIGNATURE = "";
    private final int mAbsoluteEnd;
    private final int mAbsoluteStart;
    private long mDurationSincePreviousEvent;
    private long mDurationSinceSessionStart;
    private int mEnd;
    private java.lang.String mEntityType;
    private int mEventIndex;
    private long mEventTime;
    private int mEventType;
    private int mInvocationMethod;
    private java.lang.String mPackageName;
    private java.lang.String mResultId;
    private android.view.textclassifier.TextClassificationSessionId mSessionId;
    private int mSmartEnd;
    private int mSmartStart;
    private int mStart;
    private android.view.textclassifier.SystemTextClassifierMetadata mSystemTcMetadata;
    private java.lang.String mWidgetType;
    private java.lang.String mWidgetVersion;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ActionType {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface EventType {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface InvocationMethod {
    }

    SelectionEvent(int i, int i2, int i3, java.lang.String str, int i4, java.lang.String str2) {
        this.mPackageName = "";
        this.mWidgetType = "unknown";
        com.android.internal.util.Preconditions.checkArgument(i2 >= i, "end cannot be less than start");
        this.mAbsoluteStart = i;
        this.mAbsoluteEnd = i2;
        this.mEventType = i3;
        this.mEntityType = (java.lang.String) java.util.Objects.requireNonNull(str);
        this.mResultId = str2;
        this.mInvocationMethod = i4;
    }

    private SelectionEvent(android.os.Parcel parcel) {
        this.mPackageName = "";
        this.mWidgetType = "unknown";
        this.mAbsoluteStart = parcel.readInt();
        this.mAbsoluteEnd = parcel.readInt();
        this.mEventType = parcel.readInt();
        this.mEntityType = parcel.readString();
        this.mWidgetVersion = parcel.readInt() > 0 ? parcel.readString() : null;
        this.mPackageName = parcel.readString();
        this.mWidgetType = parcel.readString();
        this.mInvocationMethod = parcel.readInt();
        this.mResultId = parcel.readString();
        this.mEventTime = parcel.readLong();
        this.mDurationSinceSessionStart = parcel.readLong();
        this.mDurationSincePreviousEvent = parcel.readLong();
        this.mEventIndex = parcel.readInt();
        this.mSessionId = parcel.readInt() > 0 ? android.view.textclassifier.TextClassificationSessionId.CREATOR.createFromParcel(parcel) : null;
        this.mStart = parcel.readInt();
        this.mEnd = parcel.readInt();
        this.mSmartStart = parcel.readInt();
        this.mSmartEnd = parcel.readInt();
        this.mSystemTcMetadata = (android.view.textclassifier.SystemTextClassifierMetadata) parcel.readParcelable(null, android.view.textclassifier.SystemTextClassifierMetadata.class);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mAbsoluteStart);
        parcel.writeInt(this.mAbsoluteEnd);
        parcel.writeInt(this.mEventType);
        parcel.writeString(this.mEntityType);
        parcel.writeInt(this.mWidgetVersion != null ? 1 : 0);
        if (this.mWidgetVersion != null) {
            parcel.writeString(this.mWidgetVersion);
        }
        parcel.writeString(this.mPackageName);
        parcel.writeString(this.mWidgetType);
        parcel.writeInt(this.mInvocationMethod);
        parcel.writeString(this.mResultId);
        parcel.writeLong(this.mEventTime);
        parcel.writeLong(this.mDurationSinceSessionStart);
        parcel.writeLong(this.mDurationSincePreviousEvent);
        parcel.writeInt(this.mEventIndex);
        parcel.writeInt(this.mSessionId == null ? 0 : 1);
        if (this.mSessionId != null) {
            this.mSessionId.writeToParcel(parcel, i);
        }
        parcel.writeInt(this.mStart);
        parcel.writeInt(this.mEnd);
        parcel.writeInt(this.mSmartStart);
        parcel.writeInt(this.mSmartEnd);
        parcel.writeParcelable(this.mSystemTcMetadata, i);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static android.view.textclassifier.SelectionEvent createSelectionStartedEvent(int i, int i2) {
        return new android.view.textclassifier.SelectionEvent(i2, i2 + 1, 1, "", i, "");
    }

    public static android.view.textclassifier.SelectionEvent createSelectionModifiedEvent(int i, int i2) {
        com.android.internal.util.Preconditions.checkArgument(i2 >= i, "end cannot be less than start");
        return new android.view.textclassifier.SelectionEvent(i, i2, 2, "", 0, "");
    }

    public static android.view.textclassifier.SelectionEvent createSelectionModifiedEvent(int i, int i2, android.view.textclassifier.TextClassification textClassification) {
        java.lang.String str;
        com.android.internal.util.Preconditions.checkArgument(i2 >= i, "end cannot be less than start");
        java.util.Objects.requireNonNull(textClassification);
        if (textClassification.getEntityCount() > 0) {
            str = textClassification.getEntity(0);
        } else {
            str = "";
        }
        return new android.view.textclassifier.SelectionEvent(i, i2, 2, str, 0, textClassification.getId());
    }

    public static android.view.textclassifier.SelectionEvent createSelectionModifiedEvent(int i, int i2, android.view.textclassifier.TextSelection textSelection) {
        java.lang.String str;
        com.android.internal.util.Preconditions.checkArgument(i2 >= i, "end cannot be less than start");
        java.util.Objects.requireNonNull(textSelection);
        if (textSelection.getEntityCount() > 0) {
            str = textSelection.getEntity(0);
        } else {
            str = "";
        }
        return new android.view.textclassifier.SelectionEvent(i, i2, 5, str, 0, textSelection.getId());
    }

    public static android.view.textclassifier.SelectionEvent createSelectionActionEvent(int i, int i2, int i3) {
        com.android.internal.util.Preconditions.checkArgument(i2 >= i, "end cannot be less than start");
        checkActionType(i3);
        return new android.view.textclassifier.SelectionEvent(i, i2, i3, "", 0, "");
    }

    public static android.view.textclassifier.SelectionEvent createSelectionActionEvent(int i, int i2, int i3, android.view.textclassifier.TextClassification textClassification) {
        java.lang.String str;
        com.android.internal.util.Preconditions.checkArgument(i2 >= i, "end cannot be less than start");
        java.util.Objects.requireNonNull(textClassification);
        checkActionType(i3);
        if (textClassification.getEntityCount() > 0) {
            str = textClassification.getEntity(0);
        } else {
            str = "";
        }
        return new android.view.textclassifier.SelectionEvent(i, i2, i3, str, 0, textClassification.getId());
    }

    private static void checkActionType(int i) throws java.lang.IllegalArgumentException {
        switch (i) {
            case 100:
            case 101:
            case 102:
            case 103:
            case 104:
            case 105:
            case 106:
            case 107:
            case 108:
            case 200:
            case 201:
                return;
            default:
                throw new java.lang.IllegalArgumentException(java.lang.String.format(java.util.Locale.US, "%d is not an eventType", java.lang.Integer.valueOf(i)));
        }
    }

    int getAbsoluteStart() {
        return this.mAbsoluteStart;
    }

    int getAbsoluteEnd() {
        return this.mAbsoluteEnd;
    }

    public int getEventType() {
        return this.mEventType;
    }

    public void setEventType(int i) {
        this.mEventType = i;
    }

    public java.lang.String getEntityType() {
        return this.mEntityType;
    }

    void setEntityType(java.lang.String str) {
        this.mEntityType = (java.lang.String) java.util.Objects.requireNonNull(str);
    }

    public java.lang.String getPackageName() {
        return this.mPackageName;
    }

    void setSystemTextClassifierMetadata(android.view.textclassifier.SystemTextClassifierMetadata systemTextClassifierMetadata) {
        this.mSystemTcMetadata = systemTextClassifierMetadata;
    }

    public android.view.textclassifier.SystemTextClassifierMetadata getSystemTextClassifierMetadata() {
        return this.mSystemTcMetadata;
    }

    public java.lang.String getWidgetType() {
        return this.mWidgetType;
    }

    public java.lang.String getWidgetVersion() {
        return this.mWidgetVersion;
    }

    public void setTextClassificationSessionContext(android.view.textclassifier.TextClassificationContext textClassificationContext) {
        this.mPackageName = textClassificationContext.getPackageName();
        this.mWidgetType = textClassificationContext.getWidgetType();
        this.mWidgetVersion = textClassificationContext.getWidgetVersion();
        this.mSystemTcMetadata = textClassificationContext.getSystemTextClassifierMetadata();
    }

    public int getInvocationMethod() {
        return this.mInvocationMethod;
    }

    public void setInvocationMethod(int i) {
        this.mInvocationMethod = i;
    }

    public java.lang.String getResultId() {
        return this.mResultId;
    }

    android.view.textclassifier.SelectionEvent setResultId(java.lang.String str) {
        this.mResultId = str;
        return this;
    }

    public long getEventTime() {
        return this.mEventTime;
    }

    android.view.textclassifier.SelectionEvent setEventTime(long j) {
        this.mEventTime = j;
        return this;
    }

    public long getDurationSinceSessionStart() {
        return this.mDurationSinceSessionStart;
    }

    android.view.textclassifier.SelectionEvent setDurationSinceSessionStart(long j) {
        this.mDurationSinceSessionStart = j;
        return this;
    }

    public long getDurationSincePreviousEvent() {
        return this.mDurationSincePreviousEvent;
    }

    android.view.textclassifier.SelectionEvent setDurationSincePreviousEvent(long j) {
        this.mDurationSincePreviousEvent = j;
        return this;
    }

    public int getEventIndex() {
        return this.mEventIndex;
    }

    public android.view.textclassifier.SelectionEvent setEventIndex(int i) {
        this.mEventIndex = i;
        return this;
    }

    public android.view.textclassifier.TextClassificationSessionId getSessionId() {
        return this.mSessionId;
    }

    public android.view.textclassifier.SelectionEvent setSessionId(android.view.textclassifier.TextClassificationSessionId textClassificationSessionId) {
        this.mSessionId = textClassificationSessionId;
        return this;
    }

    public int getStart() {
        return this.mStart;
    }

    public android.view.textclassifier.SelectionEvent setStart(int i) {
        this.mStart = i;
        return this;
    }

    public int getEnd() {
        return this.mEnd;
    }

    public android.view.textclassifier.SelectionEvent setEnd(int i) {
        this.mEnd = i;
        return this;
    }

    public int getSmartStart() {
        return this.mSmartStart;
    }

    public android.view.textclassifier.SelectionEvent setSmartStart(int i) {
        this.mSmartStart = i;
        return this;
    }

    public int getSmartEnd() {
        return this.mSmartEnd;
    }

    public android.view.textclassifier.SelectionEvent setSmartEnd(int i) {
        this.mSmartEnd = i;
        return this;
    }

    boolean isTerminal() {
        return isTerminal(this.mEventType);
    }

    public static boolean isTerminal(int i) {
        switch (i) {
            case 100:
            case 101:
            case 102:
            case 103:
            case 104:
            case 105:
            case 106:
            case 107:
            case 108:
                return true;
            default:
                return false;
        }
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(this.mAbsoluteStart), java.lang.Integer.valueOf(this.mAbsoluteEnd), java.lang.Integer.valueOf(this.mEventType), this.mEntityType, this.mWidgetVersion, this.mPackageName, this.mWidgetType, java.lang.Integer.valueOf(this.mInvocationMethod), this.mResultId, java.lang.Long.valueOf(this.mEventTime), java.lang.Long.valueOf(this.mDurationSinceSessionStart), java.lang.Long.valueOf(this.mDurationSincePreviousEvent), java.lang.Integer.valueOf(this.mEventIndex), this.mSessionId, java.lang.Integer.valueOf(this.mStart), java.lang.Integer.valueOf(this.mEnd), java.lang.Integer.valueOf(this.mSmartStart), java.lang.Integer.valueOf(this.mSmartEnd), this.mSystemTcMetadata);
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof android.view.textclassifier.SelectionEvent)) {
            return false;
        }
        android.view.textclassifier.SelectionEvent selectionEvent = (android.view.textclassifier.SelectionEvent) obj;
        return this.mAbsoluteStart == selectionEvent.mAbsoluteStart && this.mAbsoluteEnd == selectionEvent.mAbsoluteEnd && this.mEventType == selectionEvent.mEventType && java.util.Objects.equals(this.mEntityType, selectionEvent.mEntityType) && java.util.Objects.equals(this.mWidgetVersion, selectionEvent.mWidgetVersion) && java.util.Objects.equals(this.mPackageName, selectionEvent.mPackageName) && java.util.Objects.equals(this.mWidgetType, selectionEvent.mWidgetType) && this.mInvocationMethod == selectionEvent.mInvocationMethod && java.util.Objects.equals(this.mResultId, selectionEvent.mResultId) && this.mEventTime == selectionEvent.mEventTime && this.mDurationSinceSessionStart == selectionEvent.mDurationSinceSessionStart && this.mDurationSincePreviousEvent == selectionEvent.mDurationSincePreviousEvent && this.mEventIndex == selectionEvent.mEventIndex && java.util.Objects.equals(this.mSessionId, selectionEvent.mSessionId) && this.mStart == selectionEvent.mStart && this.mEnd == selectionEvent.mEnd && this.mSmartStart == selectionEvent.mSmartStart && this.mSmartEnd == selectionEvent.mSmartEnd && this.mSystemTcMetadata == selectionEvent.mSystemTcMetadata;
    }

    public java.lang.String toString() {
        return java.lang.String.format(java.util.Locale.US, "SelectionEvent {absoluteStart=%d, absoluteEnd=%d, eventType=%d, entityType=%s, widgetVersion=%s, packageName=%s, widgetType=%s, invocationMethod=%s, resultId=%s, eventTime=%d, durationSinceSessionStart=%d, durationSincePreviousEvent=%d, eventIndex=%d,sessionId=%s, start=%d, end=%d, smartStart=%d, smartEnd=%d, systemTcMetadata=%s}", java.lang.Integer.valueOf(this.mAbsoluteStart), java.lang.Integer.valueOf(this.mAbsoluteEnd), java.lang.Integer.valueOf(this.mEventType), this.mEntityType, this.mWidgetVersion, this.mPackageName, this.mWidgetType, java.lang.Integer.valueOf(this.mInvocationMethod), this.mResultId, java.lang.Long.valueOf(this.mEventTime), java.lang.Long.valueOf(this.mDurationSinceSessionStart), java.lang.Long.valueOf(this.mDurationSincePreviousEvent), java.lang.Integer.valueOf(this.mEventIndex), this.mSessionId, java.lang.Integer.valueOf(this.mStart), java.lang.Integer.valueOf(this.mEnd), java.lang.Integer.valueOf(this.mSmartStart), java.lang.Integer.valueOf(this.mSmartEnd), this.mSystemTcMetadata);
    }
}
