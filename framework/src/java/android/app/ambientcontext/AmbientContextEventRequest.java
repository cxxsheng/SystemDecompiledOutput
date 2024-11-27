package android.app.ambientcontext;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class AmbientContextEventRequest implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.app.ambientcontext.AmbientContextEventRequest> CREATOR = new android.os.Parcelable.Creator<android.app.ambientcontext.AmbientContextEventRequest>() { // from class: android.app.ambientcontext.AmbientContextEventRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.ambientcontext.AmbientContextEventRequest[] newArray(int i) {
            return new android.app.ambientcontext.AmbientContextEventRequest[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.ambientcontext.AmbientContextEventRequest createFromParcel(android.os.Parcel parcel) {
            return new android.app.ambientcontext.AmbientContextEventRequest(parcel);
        }
    };
    private final java.util.Set<java.lang.Integer> mEventTypes;
    private final android.os.PersistableBundle mOptions;

    private AmbientContextEventRequest(java.util.Set<java.lang.Integer> set, android.os.PersistableBundle persistableBundle) {
        this.mEventTypes = set;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mEventTypes);
        com.android.internal.util.Preconditions.checkArgument(!set.isEmpty(), "eventTypes cannot be empty");
        java.util.Iterator<java.lang.Integer> it = set.iterator();
        while (it.hasNext()) {
            com.android.internal.util.AnnotationValidations.validate((java.lang.Class<? extends java.lang.annotation.Annotation>) android.app.ambientcontext.AmbientContextEvent.EventCode.class, (java.lang.annotation.Annotation) null, it.next().intValue());
        }
        this.mOptions = persistableBundle;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mOptions);
    }

    public java.util.Set<java.lang.Integer> getEventTypes() {
        return this.mEventTypes;
    }

    public android.os.PersistableBundle getOptions() {
        return this.mOptions;
    }

    public java.lang.String toString() {
        return "AmbientContextEventRequest { eventTypes = " + this.mEventTypes + ", options = " + this.mOptions + " }";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeArraySet(new android.util.ArraySet<>(this.mEventTypes));
        parcel.writeTypedObject(this.mOptions, i);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private AmbientContextEventRequest(android.os.Parcel parcel) {
        android.util.ArraySet<? extends java.lang.Object> readArraySet = parcel.readArraySet(java.lang.Integer.class.getClassLoader());
        android.os.PersistableBundle persistableBundle = (android.os.PersistableBundle) parcel.readTypedObject(android.os.PersistableBundle.CREATOR);
        this.mEventTypes = readArraySet;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mEventTypes);
        com.android.internal.util.Preconditions.checkArgument(!readArraySet.isEmpty(), "eventTypes cannot be empty");
        java.util.Iterator<? extends java.lang.Object> it = readArraySet.iterator();
        while (it.hasNext()) {
            com.android.internal.util.AnnotationValidations.validate((java.lang.Class<? extends java.lang.annotation.Annotation>) android.app.ambientcontext.AmbientContextEvent.EventCode.class, (java.lang.annotation.Annotation) null, ((java.lang.Integer) it.next()).intValue());
        }
        this.mOptions = persistableBundle;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mOptions);
    }

    public static final class Builder {
        private long mBuilderFieldsSet = 0;
        private java.util.Set<java.lang.Integer> mEventTypes;
        private android.os.PersistableBundle mOptions;

        public android.app.ambientcontext.AmbientContextEventRequest.Builder addEventType(int i) {
            checkNotUsed();
            if (this.mEventTypes == null) {
                this.mBuilderFieldsSet |= 1;
                this.mEventTypes = new java.util.HashSet();
            }
            this.mEventTypes.add(java.lang.Integer.valueOf(i));
            return this;
        }

        public android.app.ambientcontext.AmbientContextEventRequest.Builder setOptions(android.os.PersistableBundle persistableBundle) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 2;
            this.mOptions = persistableBundle;
            return this;
        }

        public android.app.ambientcontext.AmbientContextEventRequest build() {
            checkNotUsed();
            this.mBuilderFieldsSet |= 4;
            if ((this.mBuilderFieldsSet & 1) == 0) {
                this.mEventTypes = new java.util.HashSet();
            }
            if ((this.mBuilderFieldsSet & 2) == 0) {
                this.mOptions = new android.os.PersistableBundle();
            }
            return new android.app.ambientcontext.AmbientContextEventRequest(this.mEventTypes, this.mOptions);
        }

        private void checkNotUsed() {
            if ((this.mBuilderFieldsSet & 4) != 0) {
                throw new java.lang.IllegalStateException("This Builder should not be reused. Use a new Builder instance instead");
            }
        }
    }
}
