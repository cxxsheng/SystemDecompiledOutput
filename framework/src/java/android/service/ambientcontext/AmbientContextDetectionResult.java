package android.service.ambientcontext;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public final class AmbientContextDetectionResult implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.service.ambientcontext.AmbientContextDetectionResult> CREATOR = new android.os.Parcelable.Creator<android.service.ambientcontext.AmbientContextDetectionResult>() { // from class: android.service.ambientcontext.AmbientContextDetectionResult.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.ambientcontext.AmbientContextDetectionResult[] newArray(int i) {
            return new android.service.ambientcontext.AmbientContextDetectionResult[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.ambientcontext.AmbientContextDetectionResult createFromParcel(android.os.Parcel parcel) {
            return new android.service.ambientcontext.AmbientContextDetectionResult(parcel);
        }
    };
    public static final java.lang.String RESULT_RESPONSE_BUNDLE_KEY = "android.app.ambientcontext.AmbientContextDetectionResultBundleKey";
    private final java.util.List<android.app.ambientcontext.AmbientContextEvent> mEvents;
    private final java.lang.String mPackageName;

    AmbientContextDetectionResult(java.util.List<android.app.ambientcontext.AmbientContextEvent> list, java.lang.String str) {
        this.mEvents = list;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mEvents);
        this.mPackageName = str;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mPackageName);
    }

    public java.util.List<android.app.ambientcontext.AmbientContextEvent> getEvents() {
        return this.mEvents;
    }

    public java.lang.String getPackageName() {
        return this.mPackageName;
    }

    public java.lang.String toString() {
        return "AmbientContextEventResponse { events = " + this.mEvents + ", packageName = " + this.mPackageName + " }";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeByte((byte) 0);
        parcel.writeParcelableList(this.mEvents, i);
        parcel.writeString(this.mPackageName);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    AmbientContextDetectionResult(android.os.Parcel parcel) {
        parcel.readByte();
        java.util.ArrayList arrayList = new java.util.ArrayList();
        parcel.readParcelableList(arrayList, android.app.ambientcontext.AmbientContextEvent.class.getClassLoader(), android.app.ambientcontext.AmbientContextEvent.class);
        java.lang.String readString = parcel.readString();
        this.mEvents = arrayList;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mEvents);
        this.mPackageName = readString;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mPackageName);
    }

    public static final class Builder {
        private long mBuilderFieldsSet = 0;
        private java.util.ArrayList<android.app.ambientcontext.AmbientContextEvent> mEvents;
        private java.lang.String mPackageName;

        public Builder(java.lang.String str) {
            java.util.Objects.requireNonNull(str);
            this.mPackageName = str;
        }

        public android.service.ambientcontext.AmbientContextDetectionResult.Builder addEvent(android.app.ambientcontext.AmbientContextEvent ambientContextEvent) {
            checkNotUsed();
            if (this.mEvents == null) {
                this.mBuilderFieldsSet |= 1;
                this.mEvents = new java.util.ArrayList<>();
            }
            this.mEvents.add(ambientContextEvent);
            return this;
        }

        public android.service.ambientcontext.AmbientContextDetectionResult.Builder addEvents(java.util.List<android.app.ambientcontext.AmbientContextEvent> list) {
            checkNotUsed();
            if (this.mEvents == null) {
                this.mBuilderFieldsSet |= 1;
                this.mEvents = new java.util.ArrayList<>();
            }
            this.mEvents.addAll(list);
            return this;
        }

        public android.service.ambientcontext.AmbientContextDetectionResult.Builder clearEvents() {
            checkNotUsed();
            if (this.mEvents != null) {
                this.mEvents.clear();
            }
            return this;
        }

        public android.service.ambientcontext.AmbientContextDetectionResult build() {
            checkNotUsed();
            this.mBuilderFieldsSet |= 2;
            if ((this.mBuilderFieldsSet & 1) == 0) {
                this.mEvents = new java.util.ArrayList<>();
            }
            return new android.service.ambientcontext.AmbientContextDetectionResult(this.mEvents, this.mPackageName);
        }

        private void checkNotUsed() {
            if ((this.mBuilderFieldsSet & 2) != 0) {
                throw new java.lang.IllegalStateException("This Builder should not be reused. Use a new Builder instance instead");
            }
        }
    }
}
