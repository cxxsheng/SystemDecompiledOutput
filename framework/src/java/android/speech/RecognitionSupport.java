package android.speech;

/* loaded from: classes3.dex */
public final class RecognitionSupport implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.speech.RecognitionSupport> CREATOR = new android.os.Parcelable.Creator<android.speech.RecognitionSupport>() { // from class: android.speech.RecognitionSupport.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.speech.RecognitionSupport[] newArray(int i) {
            return new android.speech.RecognitionSupport[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.speech.RecognitionSupport createFromParcel(android.os.Parcel parcel) {
            return new android.speech.RecognitionSupport(parcel);
        }
    };
    private java.util.List<java.lang.String> mInstalledOnDeviceLanguages;
    private java.util.List<java.lang.String> mOnlineLanguages;
    private java.util.List<java.lang.String> mPendingOnDeviceLanguages;
    private java.util.List<java.lang.String> mSupportedOnDeviceLanguages;

    RecognitionSupport(java.util.List<java.lang.String> list, java.util.List<java.lang.String> list2, java.util.List<java.lang.String> list3, java.util.List<java.lang.String> list4) {
        this.mInstalledOnDeviceLanguages = java.util.List.of();
        this.mPendingOnDeviceLanguages = java.util.List.of();
        this.mSupportedOnDeviceLanguages = java.util.List.of();
        this.mOnlineLanguages = java.util.List.of();
        this.mInstalledOnDeviceLanguages = list;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mInstalledOnDeviceLanguages);
        this.mPendingOnDeviceLanguages = list2;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mPendingOnDeviceLanguages);
        this.mSupportedOnDeviceLanguages = list3;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mSupportedOnDeviceLanguages);
        this.mOnlineLanguages = list4;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mOnlineLanguages);
    }

    public java.util.List<java.lang.String> getInstalledOnDeviceLanguages() {
        return this.mInstalledOnDeviceLanguages;
    }

    public java.util.List<java.lang.String> getPendingOnDeviceLanguages() {
        return this.mPendingOnDeviceLanguages;
    }

    public java.util.List<java.lang.String> getSupportedOnDeviceLanguages() {
        return this.mSupportedOnDeviceLanguages;
    }

    public java.util.List<java.lang.String> getOnlineLanguages() {
        return this.mOnlineLanguages;
    }

    public java.lang.String toString() {
        return "RecognitionSupport { installedOnDeviceLanguages = " + this.mInstalledOnDeviceLanguages + ", pendingOnDeviceLanguages = " + this.mPendingOnDeviceLanguages + ", supportedOnDeviceLanguages = " + this.mSupportedOnDeviceLanguages + ", onlineLanguages = " + this.mOnlineLanguages + " }";
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.speech.RecognitionSupport recognitionSupport = (android.speech.RecognitionSupport) obj;
        if (java.util.Objects.equals(this.mInstalledOnDeviceLanguages, recognitionSupport.mInstalledOnDeviceLanguages) && java.util.Objects.equals(this.mPendingOnDeviceLanguages, recognitionSupport.mPendingOnDeviceLanguages) && java.util.Objects.equals(this.mSupportedOnDeviceLanguages, recognitionSupport.mSupportedOnDeviceLanguages) && java.util.Objects.equals(this.mOnlineLanguages, recognitionSupport.mOnlineLanguages)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return ((((((java.util.Objects.hashCode(this.mInstalledOnDeviceLanguages) + 31) * 31) + java.util.Objects.hashCode(this.mPendingOnDeviceLanguages)) * 31) + java.util.Objects.hashCode(this.mSupportedOnDeviceLanguages)) * 31) + java.util.Objects.hashCode(this.mOnlineLanguages);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeStringList(this.mInstalledOnDeviceLanguages);
        parcel.writeStringList(this.mPendingOnDeviceLanguages);
        parcel.writeStringList(this.mSupportedOnDeviceLanguages);
        parcel.writeStringList(this.mOnlineLanguages);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    RecognitionSupport(android.os.Parcel parcel) {
        this.mInstalledOnDeviceLanguages = java.util.List.of();
        this.mPendingOnDeviceLanguages = java.util.List.of();
        this.mSupportedOnDeviceLanguages = java.util.List.of();
        this.mOnlineLanguages = java.util.List.of();
        java.util.ArrayList arrayList = new java.util.ArrayList();
        parcel.readStringList(arrayList);
        java.util.ArrayList arrayList2 = new java.util.ArrayList();
        parcel.readStringList(arrayList2);
        java.util.ArrayList arrayList3 = new java.util.ArrayList();
        parcel.readStringList(arrayList3);
        java.util.ArrayList arrayList4 = new java.util.ArrayList();
        parcel.readStringList(arrayList4);
        this.mInstalledOnDeviceLanguages = arrayList;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mInstalledOnDeviceLanguages);
        this.mPendingOnDeviceLanguages = arrayList2;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mPendingOnDeviceLanguages);
        this.mSupportedOnDeviceLanguages = arrayList3;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mSupportedOnDeviceLanguages);
        this.mOnlineLanguages = arrayList4;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mOnlineLanguages);
    }

    public static final class Builder {
        private long mBuilderFieldsSet = 0;
        private java.util.List<java.lang.String> mInstalledOnDeviceLanguages;
        private java.util.List<java.lang.String> mOnlineLanguages;
        private java.util.List<java.lang.String> mPendingOnDeviceLanguages;
        private java.util.List<java.lang.String> mSupportedOnDeviceLanguages;

        public android.speech.RecognitionSupport.Builder setInstalledOnDeviceLanguages(java.util.List<java.lang.String> list) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 1;
            this.mInstalledOnDeviceLanguages = list;
            return this;
        }

        public android.speech.RecognitionSupport.Builder addInstalledOnDeviceLanguage(java.lang.String str) {
            if (this.mInstalledOnDeviceLanguages == null) {
                setInstalledOnDeviceLanguages(new java.util.ArrayList());
            }
            this.mInstalledOnDeviceLanguages.add(str);
            return this;
        }

        public android.speech.RecognitionSupport.Builder setPendingOnDeviceLanguages(java.util.List<java.lang.String> list) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 2;
            this.mPendingOnDeviceLanguages = list;
            return this;
        }

        public android.speech.RecognitionSupport.Builder addPendingOnDeviceLanguage(java.lang.String str) {
            if (this.mPendingOnDeviceLanguages == null) {
                setPendingOnDeviceLanguages(new java.util.ArrayList());
            }
            this.mPendingOnDeviceLanguages.add(str);
            return this;
        }

        public android.speech.RecognitionSupport.Builder setSupportedOnDeviceLanguages(java.util.List<java.lang.String> list) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 4;
            this.mSupportedOnDeviceLanguages = list;
            return this;
        }

        public android.speech.RecognitionSupport.Builder addSupportedOnDeviceLanguage(java.lang.String str) {
            if (this.mSupportedOnDeviceLanguages == null) {
                setSupportedOnDeviceLanguages(new java.util.ArrayList());
            }
            this.mSupportedOnDeviceLanguages.add(str);
            return this;
        }

        public android.speech.RecognitionSupport.Builder setOnlineLanguages(java.util.List<java.lang.String> list) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 8;
            this.mOnlineLanguages = list;
            return this;
        }

        public android.speech.RecognitionSupport.Builder addOnlineLanguage(java.lang.String str) {
            if (this.mOnlineLanguages == null) {
                setOnlineLanguages(new java.util.ArrayList());
            }
            this.mOnlineLanguages.add(str);
            return this;
        }

        public android.speech.RecognitionSupport build() {
            checkNotUsed();
            this.mBuilderFieldsSet |= 16;
            if ((this.mBuilderFieldsSet & 1) == 0) {
                this.mInstalledOnDeviceLanguages = java.util.List.of();
            }
            if ((this.mBuilderFieldsSet & 2) == 0) {
                this.mPendingOnDeviceLanguages = java.util.List.of();
            }
            if ((this.mBuilderFieldsSet & 4) == 0) {
                this.mSupportedOnDeviceLanguages = java.util.List.of();
            }
            if ((this.mBuilderFieldsSet & 8) == 0) {
                this.mOnlineLanguages = java.util.List.of();
            }
            return new android.speech.RecognitionSupport(this.mInstalledOnDeviceLanguages, this.mPendingOnDeviceLanguages, this.mSupportedOnDeviceLanguages, this.mOnlineLanguages);
        }

        private void checkNotUsed() {
            if ((this.mBuilderFieldsSet & 16) != 0) {
                throw new java.lang.IllegalStateException("This Builder should not be reused. Use a new Builder instance instead");
            }
        }
    }

    @java.lang.Deprecated
    private void __metadata() {
    }
}
