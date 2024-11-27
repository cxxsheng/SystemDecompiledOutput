package android.app.smartspace;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class SmartspaceTargetEvent implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.app.smartspace.SmartspaceTargetEvent> CREATOR = new android.os.Parcelable.Creator<android.app.smartspace.SmartspaceTargetEvent>() { // from class: android.app.smartspace.SmartspaceTargetEvent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.smartspace.SmartspaceTargetEvent createFromParcel(android.os.Parcel parcel) {
            return new android.app.smartspace.SmartspaceTargetEvent(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.smartspace.SmartspaceTargetEvent[] newArray(int i) {
            return new android.app.smartspace.SmartspaceTargetEvent[i];
        }
    };
    public static final int EVENT_TARGET_BLOCK = 5;
    public static final int EVENT_TARGET_DISMISS = 4;
    public static final int EVENT_TARGET_HIDDEN = 3;
    public static final int EVENT_TARGET_INTERACTION = 1;
    public static final int EVENT_TARGET_SHOWN = 2;
    public static final int EVENT_UI_SURFACE_HIDDEN = 7;
    public static final int EVENT_UI_SURFACE_SHOWN = 6;
    private final int mEventType;
    private final java.lang.String mSmartspaceActionId;
    private final android.app.smartspace.SmartspaceTarget mSmartspaceTarget;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface EventType {
    }

    private SmartspaceTargetEvent(android.app.smartspace.SmartspaceTarget smartspaceTarget, java.lang.String str, int i) {
        this.mSmartspaceTarget = smartspaceTarget;
        this.mSmartspaceActionId = str;
        this.mEventType = i;
    }

    private SmartspaceTargetEvent(android.os.Parcel parcel) {
        this.mSmartspaceTarget = (android.app.smartspace.SmartspaceTarget) parcel.readParcelable(null, android.app.smartspace.SmartspaceTarget.class);
        this.mSmartspaceActionId = parcel.readString();
        this.mEventType = parcel.readInt();
    }

    public android.app.smartspace.SmartspaceTarget getSmartspaceTarget() {
        return this.mSmartspaceTarget;
    }

    public java.lang.String getSmartspaceActionId() {
        return this.mSmartspaceActionId;
    }

    public int getEventType() {
        return this.mEventType;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeParcelable(this.mSmartspaceTarget, i);
        parcel.writeString(this.mSmartspaceActionId);
        parcel.writeInt(this.mEventType);
    }

    public java.lang.String toString() {
        return "SmartspaceTargetEvent{mSmartspaceTarget=" + this.mSmartspaceTarget + ", mSmartspaceActionId='" + this.mSmartspaceActionId + android.text.format.DateFormat.QUOTE + ", mEventType=" + this.mEventType + '}';
    }

    @android.annotation.SystemApi
    public static final class Builder {
        private final int mEventType;
        private java.lang.String mSmartspaceActionId;
        private android.app.smartspace.SmartspaceTarget mSmartspaceTarget;

        public Builder(int i) {
            this.mEventType = i;
        }

        public android.app.smartspace.SmartspaceTargetEvent.Builder setSmartspaceTarget(android.app.smartspace.SmartspaceTarget smartspaceTarget) {
            this.mSmartspaceTarget = smartspaceTarget;
            return this;
        }

        public android.app.smartspace.SmartspaceTargetEvent.Builder setSmartspaceActionId(java.lang.String str) {
            this.mSmartspaceActionId = str;
            return this;
        }

        public android.app.smartspace.SmartspaceTargetEvent build() {
            return new android.app.smartspace.SmartspaceTargetEvent(this.mSmartspaceTarget, this.mSmartspaceActionId, this.mEventType);
        }
    }
}
