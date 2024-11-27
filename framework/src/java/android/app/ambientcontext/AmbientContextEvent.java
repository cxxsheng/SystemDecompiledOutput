package android.app.ambientcontext;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class AmbientContextEvent implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.app.ambientcontext.AmbientContextEvent> CREATOR;
    public static final int EVENT_BACK_DOUBLE_TAP = 3;
    public static final int EVENT_COUGH = 1;
    public static final int EVENT_SNORE = 2;
    public static final int EVENT_UNKNOWN = 0;
    public static final int EVENT_VENDOR_WEARABLE_START = 100000;
    public static final java.lang.String KEY_VENDOR_WEARABLE_EVENT_NAME = "wearable_event_name";
    public static final int LEVEL_HIGH = 5;
    public static final int LEVEL_LOW = 1;
    public static final int LEVEL_MEDIUM = 3;
    public static final int LEVEL_MEDIUM_HIGH = 4;
    public static final int LEVEL_MEDIUM_LOW = 2;
    public static final int LEVEL_UNKNOWN = 0;
    static com.android.internal.util.Parcelling<java.time.Instant> sParcellingForEndTime;
    static com.android.internal.util.Parcelling<java.time.Instant> sParcellingForStartTime;
    private final int mConfidenceLevel;
    private final int mDensityLevel;
    private final java.time.Instant mEndTime;
    private final int mEventType;
    private final java.time.Instant mStartTime;
    private final android.os.PersistableBundle mVendorData;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Event {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface EventCode {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Level {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface LevelValue {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int defaultEventType() {
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static java.time.Instant defaultStartTime() {
        return java.time.Instant.MIN;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static java.time.Instant defaultEndTime() {
        return java.time.Instant.MAX;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int defaultConfidenceLevel() {
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int defaultDensityLevel() {
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static android.os.PersistableBundle defaultVendorData() {
        return new android.os.PersistableBundle();
    }

    public static java.lang.String eventToString(int i) {
        switch (i) {
            case 0:
                return "EVENT_UNKNOWN";
            case 1:
                return "EVENT_COUGH";
            case 2:
                return "EVENT_SNORE";
            case 3:
                return "EVENT_BACK_DOUBLE_TAP";
            case 100000:
                return "EVENT_VENDOR_WEARABLE_START";
            default:
                return java.lang.Integer.toHexString(i);
        }
    }

    public static java.lang.String levelToString(int i) {
        switch (i) {
            case 0:
                return "LEVEL_UNKNOWN";
            case 1:
                return "LEVEL_LOW";
            case 2:
                return "LEVEL_MEDIUM_LOW";
            case 3:
                return "LEVEL_MEDIUM";
            case 4:
                return "LEVEL_MEDIUM_HIGH";
            case 5:
                return "LEVEL_HIGH";
            default:
                return java.lang.Integer.toHexString(i);
        }
    }

    AmbientContextEvent(int i, java.time.Instant instant, java.time.Instant instant2, int i2, int i3, android.os.PersistableBundle persistableBundle) {
        this.mEventType = i;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<? extends java.lang.annotation.Annotation>) android.app.ambientcontext.AmbientContextEvent.EventCode.class, (java.lang.annotation.Annotation) null, this.mEventType);
        this.mStartTime = instant;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mStartTime);
        this.mEndTime = instant2;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mEndTime);
        this.mConfidenceLevel = i2;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<? extends java.lang.annotation.Annotation>) android.app.ambientcontext.AmbientContextEvent.LevelValue.class, (java.lang.annotation.Annotation) null, this.mConfidenceLevel);
        this.mDensityLevel = i3;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<? extends java.lang.annotation.Annotation>) android.app.ambientcontext.AmbientContextEvent.LevelValue.class, (java.lang.annotation.Annotation) null, this.mDensityLevel);
        this.mVendorData = persistableBundle;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mVendorData);
    }

    public int getEventType() {
        return this.mEventType;
    }

    public java.time.Instant getStartTime() {
        return this.mStartTime;
    }

    public java.time.Instant getEndTime() {
        return this.mEndTime;
    }

    public int getConfidenceLevel() {
        return this.mConfidenceLevel;
    }

    public int getDensityLevel() {
        return this.mDensityLevel;
    }

    public android.os.PersistableBundle getVendorData() {
        return this.mVendorData;
    }

    public java.lang.String toString() {
        return "AmbientContextEvent { eventType = " + this.mEventType + ", startTime = " + this.mStartTime + ", endTime = " + this.mEndTime + ", confidenceLevel = " + this.mConfidenceLevel + ", densityLevel = " + this.mDensityLevel + ", vendorData = " + this.mVendorData + " }";
    }

    static {
        sParcellingForStartTime = com.android.internal.util.Parcelling.Cache.get(com.android.internal.util.Parcelling.BuiltIn.ForInstant.class);
        if (sParcellingForStartTime == null) {
            sParcellingForStartTime = com.android.internal.util.Parcelling.Cache.put(new com.android.internal.util.Parcelling.BuiltIn.ForInstant());
        }
        sParcellingForEndTime = com.android.internal.util.Parcelling.Cache.get(com.android.internal.util.Parcelling.BuiltIn.ForInstant.class);
        if (sParcellingForEndTime == null) {
            sParcellingForEndTime = com.android.internal.util.Parcelling.Cache.put(new com.android.internal.util.Parcelling.BuiltIn.ForInstant());
        }
        CREATOR = new android.os.Parcelable.Creator<android.app.ambientcontext.AmbientContextEvent>() { // from class: android.app.ambientcontext.AmbientContextEvent.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.app.ambientcontext.AmbientContextEvent[] newArray(int i) {
                return new android.app.ambientcontext.AmbientContextEvent[i];
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.app.ambientcontext.AmbientContextEvent createFromParcel(android.os.Parcel parcel) {
                return new android.app.ambientcontext.AmbientContextEvent(parcel);
            }
        };
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mEventType);
        sParcellingForStartTime.parcel(this.mStartTime, parcel, i);
        sParcellingForEndTime.parcel(this.mEndTime, parcel, i);
        parcel.writeInt(this.mConfidenceLevel);
        parcel.writeInt(this.mDensityLevel);
        parcel.writeTypedObject(this.mVendorData, i);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    AmbientContextEvent(android.os.Parcel parcel) {
        int readInt = parcel.readInt();
        java.time.Instant unparcel = sParcellingForStartTime.unparcel(parcel);
        java.time.Instant unparcel2 = sParcellingForEndTime.unparcel(parcel);
        int readInt2 = parcel.readInt();
        int readInt3 = parcel.readInt();
        android.os.PersistableBundle persistableBundle = (android.os.PersistableBundle) parcel.readTypedObject(android.os.PersistableBundle.CREATOR);
        this.mEventType = readInt;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<? extends java.lang.annotation.Annotation>) android.app.ambientcontext.AmbientContextEvent.EventCode.class, (java.lang.annotation.Annotation) null, this.mEventType);
        this.mStartTime = unparcel;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mStartTime);
        this.mEndTime = unparcel2;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mEndTime);
        this.mConfidenceLevel = readInt2;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<? extends java.lang.annotation.Annotation>) android.app.ambientcontext.AmbientContextEvent.LevelValue.class, (java.lang.annotation.Annotation) null, this.mConfidenceLevel);
        this.mDensityLevel = readInt3;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<? extends java.lang.annotation.Annotation>) android.app.ambientcontext.AmbientContextEvent.LevelValue.class, (java.lang.annotation.Annotation) null, this.mDensityLevel);
        this.mVendorData = persistableBundle;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mVendorData);
    }

    public static final class Builder {
        private long mBuilderFieldsSet = 0;
        private int mConfidenceLevel;
        private int mDensityLevel;
        private java.time.Instant mEndTime;
        private int mEventType;
        private java.time.Instant mStartTime;
        private android.os.PersistableBundle mVendorData;

        public android.app.ambientcontext.AmbientContextEvent.Builder setEventType(int i) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 1;
            this.mEventType = i;
            return this;
        }

        public android.app.ambientcontext.AmbientContextEvent.Builder setStartTime(java.time.Instant instant) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 2;
            this.mStartTime = instant;
            return this;
        }

        public android.app.ambientcontext.AmbientContextEvent.Builder setEndTime(java.time.Instant instant) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 4;
            this.mEndTime = instant;
            return this;
        }

        public android.app.ambientcontext.AmbientContextEvent.Builder setConfidenceLevel(int i) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 8;
            this.mConfidenceLevel = i;
            return this;
        }

        public android.app.ambientcontext.AmbientContextEvent.Builder setDensityLevel(int i) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 16;
            this.mDensityLevel = i;
            return this;
        }

        public android.app.ambientcontext.AmbientContextEvent.Builder setVendorData(android.os.PersistableBundle persistableBundle) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 32;
            this.mVendorData = persistableBundle;
            return this;
        }

        public android.app.ambientcontext.AmbientContextEvent build() {
            checkNotUsed();
            this.mBuilderFieldsSet |= 64;
            if ((this.mBuilderFieldsSet & 1) == 0) {
                this.mEventType = android.app.ambientcontext.AmbientContextEvent.defaultEventType();
            }
            if ((this.mBuilderFieldsSet & 2) == 0) {
                this.mStartTime = android.app.ambientcontext.AmbientContextEvent.defaultStartTime();
            }
            if ((this.mBuilderFieldsSet & 4) == 0) {
                this.mEndTime = android.app.ambientcontext.AmbientContextEvent.defaultEndTime();
            }
            if ((this.mBuilderFieldsSet & 8) == 0) {
                this.mConfidenceLevel = android.app.ambientcontext.AmbientContextEvent.defaultConfidenceLevel();
            }
            if ((this.mBuilderFieldsSet & 16) == 0) {
                this.mDensityLevel = android.app.ambientcontext.AmbientContextEvent.defaultDensityLevel();
            }
            if ((this.mBuilderFieldsSet & 32) == 0) {
                this.mVendorData = android.app.ambientcontext.AmbientContextEvent.defaultVendorData();
            }
            return new android.app.ambientcontext.AmbientContextEvent(this.mEventType, this.mStartTime, this.mEndTime, this.mConfidenceLevel, this.mDensityLevel, this.mVendorData);
        }

        private void checkNotUsed() {
            if ((this.mBuilderFieldsSet & 64) != 0) {
                throw new java.lang.IllegalStateException("This Builder should not be reused. Use a new Builder instance instead");
            }
        }
    }

    @java.lang.Deprecated
    private void __metadata() {
    }
}
