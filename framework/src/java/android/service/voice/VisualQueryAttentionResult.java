package android.service.voice;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public final class VisualQueryAttentionResult implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.service.voice.VisualQueryAttentionResult> CREATOR = new android.os.Parcelable.Creator<android.service.voice.VisualQueryAttentionResult>() { // from class: android.service.voice.VisualQueryAttentionResult.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.voice.VisualQueryAttentionResult[] newArray(int i) {
            return new android.service.voice.VisualQueryAttentionResult[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.voice.VisualQueryAttentionResult createFromParcel(android.os.Parcel parcel) {
            return new android.service.voice.VisualQueryAttentionResult(parcel);
        }
    };
    public static final int INTERACTION_INTENTION_AUDIO_VISUAL = 0;
    public static final int INTERACTION_INTENTION_VISUAL_ACCESSIBILITY = 1;
    private final int mEngagementLevel;
    private final int mInteractionIntention;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface InteractionIntention {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int defaultInteractionIntention() {
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int defaultEngagementLevel() {
        return 100;
    }

    public android.service.voice.VisualQueryAttentionResult.Builder buildUpon() {
        return new android.service.voice.VisualQueryAttentionResult.Builder().setInteractionIntention(this.mInteractionIntention).setEngagementLevel(this.mEngagementLevel);
    }

    public static java.lang.String interactionIntentionToString(int i) {
        switch (i) {
            case 0:
                return "INTERACTION_INTENTION_AUDIO_VISUAL";
            case 1:
                return "INTERACTION_INTENTION_VISUAL_ACCESSIBILITY";
            default:
                return java.lang.Integer.toHexString(i);
        }
    }

    VisualQueryAttentionResult(int i, int i2) {
        this.mInteractionIntention = i;
        if (this.mInteractionIntention != 0 && this.mInteractionIntention != 1) {
            throw new java.lang.IllegalArgumentException("interactionIntention was " + this.mInteractionIntention + " but must be one of: INTERACTION_INTENTION_AUDIO_VISUAL(0), INTERACTION_INTENTION_VISUAL_ACCESSIBILITY(1" + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        }
        this.mEngagementLevel = i2;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.IntRange>) android.annotation.IntRange.class, (android.annotation.IntRange) null, this.mEngagementLevel, "from", 1L, "to", 100L);
    }

    public int getInteractionIntention() {
        return this.mInteractionIntention;
    }

    public int getEngagementLevel() {
        return this.mEngagementLevel;
    }

    public java.lang.String toString() {
        return "VisualQueryAttentionResult { interactionIntention = " + interactionIntentionToString(this.mInteractionIntention) + ", engagementLevel = " + this.mEngagementLevel + " }";
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.service.voice.VisualQueryAttentionResult visualQueryAttentionResult = (android.service.voice.VisualQueryAttentionResult) obj;
        if (this.mInteractionIntention == visualQueryAttentionResult.mInteractionIntention && this.mEngagementLevel == visualQueryAttentionResult.mEngagementLevel) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return ((this.mInteractionIntention + 31) * 31) + this.mEngagementLevel;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mInteractionIntention);
        parcel.writeInt(this.mEngagementLevel);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    VisualQueryAttentionResult(android.os.Parcel parcel) {
        int readInt = parcel.readInt();
        int readInt2 = parcel.readInt();
        this.mInteractionIntention = readInt;
        if (this.mInteractionIntention != 0 && this.mInteractionIntention != 1) {
            throw new java.lang.IllegalArgumentException("interactionIntention was " + this.mInteractionIntention + " but must be one of: INTERACTION_INTENTION_AUDIO_VISUAL(0), INTERACTION_INTENTION_VISUAL_ACCESSIBILITY(1" + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        }
        this.mEngagementLevel = readInt2;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.IntRange>) android.annotation.IntRange.class, (android.annotation.IntRange) null, this.mEngagementLevel, "from", 1L, "to", 100L);
    }

    public static final class Builder {
        private long mBuilderFieldsSet = 0;
        private int mEngagementLevel;
        private int mInteractionIntention;

        public android.service.voice.VisualQueryAttentionResult.Builder setInteractionIntention(int i) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 1;
            this.mInteractionIntention = i;
            return this;
        }

        public android.service.voice.VisualQueryAttentionResult.Builder setEngagementLevel(int i) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 2;
            this.mEngagementLevel = i;
            return this;
        }

        public android.service.voice.VisualQueryAttentionResult build() {
            checkNotUsed();
            this.mBuilderFieldsSet |= 4;
            if ((this.mBuilderFieldsSet & 1) == 0) {
                this.mInteractionIntention = android.service.voice.VisualQueryAttentionResult.defaultInteractionIntention();
            }
            if ((this.mBuilderFieldsSet & 2) == 0) {
                this.mEngagementLevel = android.service.voice.VisualQueryAttentionResult.defaultEngagementLevel();
            }
            return new android.service.voice.VisualQueryAttentionResult(this.mInteractionIntention, this.mEngagementLevel);
        }

        private void checkNotUsed() {
            if ((this.mBuilderFieldsSet & 4) != 0) {
                throw new java.lang.IllegalStateException("This Builder should not be reused. Use a new Builder instance instead");
            }
        }
    }

    @java.lang.Deprecated
    private void __metadata() {
    }
}
