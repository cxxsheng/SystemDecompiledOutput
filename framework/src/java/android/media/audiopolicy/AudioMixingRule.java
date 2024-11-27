package android.media.audiopolicy;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public class AudioMixingRule implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.media.audiopolicy.AudioMixingRule> CREATOR = new android.os.Parcelable.Creator<android.media.audiopolicy.AudioMixingRule>() { // from class: android.media.audiopolicy.AudioMixingRule.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.audiopolicy.AudioMixingRule createFromParcel(android.os.Parcel parcel) {
            android.media.audiopolicy.AudioMixingRule.Builder builder = new android.media.audiopolicy.AudioMixingRule.Builder();
            builder.allowPrivilegedPlaybackCapture(parcel.readBoolean());
            builder.voiceCommunicationCaptureAllowed(parcel.readBoolean());
            builder.setTargetMixRole(parcel.readInt());
            int readInt = parcel.readInt();
            for (int i = 0; i < readInt; i++) {
                builder.addRuleInternal(android.media.audiopolicy.AudioMixingRule.AudioMixMatchCriterion.CREATOR.createFromParcel(parcel));
            }
            return builder.build();
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.audiopolicy.AudioMixingRule[] newArray(int i) {
            return new android.media.audiopolicy.AudioMixingRule[i];
        }
    };
    public static final int MIX_ROLE_INJECTOR = 1;
    public static final int MIX_ROLE_PLAYERS = 0;
    public static final int RULE_EXCLUDE_ATTRIBUTE_CAPTURE_PRESET = 32770;
    public static final int RULE_EXCLUDE_ATTRIBUTE_USAGE = 32769;
    public static final int RULE_EXCLUDE_AUDIO_SESSION_ID = 32784;
    public static final int RULE_EXCLUDE_UID = 32772;
    public static final int RULE_EXCLUDE_USERID = 32776;
    private static final int RULE_EXCLUSION_MASK = 32768;
    public static final int RULE_MATCH_ATTRIBUTE_CAPTURE_PRESET = 2;
    public static final int RULE_MATCH_ATTRIBUTE_USAGE = 1;
    public static final int RULE_MATCH_AUDIO_SESSION_ID = 16;
    public static final int RULE_MATCH_UID = 4;
    public static final int RULE_MATCH_USERID = 8;
    private boolean mAllowPrivilegedPlaybackCapture;
    private final java.util.ArrayList<android.media.audiopolicy.AudioMixingRule.AudioMixMatchCriterion> mCriteria;
    private final int mTargetMixType;
    private boolean mVoiceCommunicationCaptureAllowed;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface MixRole {
    }

    private AudioMixingRule(int i, java.util.Collection<android.media.audiopolicy.AudioMixingRule.AudioMixMatchCriterion> collection, boolean z, boolean z2) {
        this.mAllowPrivilegedPlaybackCapture = false;
        this.mVoiceCommunicationCaptureAllowed = false;
        this.mCriteria = new java.util.ArrayList<>(collection);
        this.mTargetMixType = i;
        this.mAllowPrivilegedPlaybackCapture = z;
        this.mVoiceCommunicationCaptureAllowed = z2;
    }

    public static final class AudioMixMatchCriterion implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.media.audiopolicy.AudioMixingRule.AudioMixMatchCriterion> CREATOR = new android.os.Parcelable.Creator<android.media.audiopolicy.AudioMixingRule.AudioMixMatchCriterion>() { // from class: android.media.audiopolicy.AudioMixingRule.AudioMixMatchCriterion.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.media.audiopolicy.AudioMixingRule.AudioMixMatchCriterion createFromParcel(android.os.Parcel parcel) {
                return new android.media.audiopolicy.AudioMixingRule.AudioMixMatchCriterion(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.media.audiopolicy.AudioMixingRule.AudioMixMatchCriterion[] newArray(int i) {
                return new android.media.audiopolicy.AudioMixingRule.AudioMixMatchCriterion[i];
            }
        };
        final android.media.AudioAttributes mAttr;
        final int mIntProp;
        final int mRule;

        public AudioMixMatchCriterion(android.media.AudioAttributes audioAttributes, int i) {
            this.mAttr = audioAttributes;
            this.mIntProp = Integer.MIN_VALUE;
            this.mRule = i;
        }

        public AudioMixMatchCriterion(java.lang.Integer num, int i) {
            this.mAttr = null;
            this.mIntProp = num.intValue();
            this.mRule = i;
        }

        private AudioMixMatchCriterion(android.os.Parcel parcel) {
            java.util.Objects.requireNonNull(parcel);
            this.mRule = parcel.readInt();
            switch (this.mRule & (-32769)) {
                case 1:
                case 2:
                    this.mAttr = android.media.AudioAttributes.CREATOR.createFromParcel(parcel);
                    this.mIntProp = Integer.MIN_VALUE;
                    return;
                case 4:
                case 8:
                case 16:
                    this.mIntProp = parcel.readInt();
                    this.mAttr = null;
                    return;
                default:
                    parcel.readInt();
                    throw new java.lang.IllegalArgumentException("Illegal rule value " + this.mRule + " in parcel");
            }
        }

        public int hashCode() {
            return java.util.Objects.hash(this.mAttr, java.lang.Integer.valueOf(this.mIntProp), java.lang.Integer.valueOf(this.mRule));
        }

        public boolean equals(java.lang.Object obj) {
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            if (obj == this) {
                return true;
            }
            android.media.audiopolicy.AudioMixingRule.AudioMixMatchCriterion audioMixMatchCriterion = (android.media.audiopolicy.AudioMixingRule.AudioMixMatchCriterion) obj;
            if (this.mRule != audioMixMatchCriterion.mRule || this.mIntProp != audioMixMatchCriterion.mIntProp || !java.util.Objects.equals(this.mAttr, audioMixMatchCriterion.mAttr)) {
                return false;
            }
            return true;
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeInt(this.mRule);
            int i2 = this.mRule & (-32769);
            switch (i2) {
                case 1:
                case 2:
                    this.mAttr.writeToParcel(parcel, 1);
                    break;
                case 4:
                case 8:
                case 16:
                    parcel.writeInt(this.mIntProp);
                    break;
                default:
                    android.util.Log.e("AudioMixMatchCriterion", "Unknown match rule" + i2 + " when writing to Parcel");
                    parcel.writeInt(-1);
                    break;
            }
        }

        public android.media.AudioAttributes getAudioAttributes() {
            return this.mAttr;
        }

        public int getIntProp() {
            return this.mIntProp;
        }

        public int getRule() {
            return this.mRule;
        }
    }

    boolean isAffectingUsage(int i) {
        java.util.Iterator<android.media.audiopolicy.AudioMixingRule.AudioMixMatchCriterion> it = this.mCriteria.iterator();
        while (it.hasNext()) {
            android.media.audiopolicy.AudioMixingRule.AudioMixMatchCriterion next = it.next();
            if ((next.mRule & 1) != 0 && next.mAttr != null && next.mAttr.getSystemUsage() == i) {
                return true;
            }
        }
        return false;
    }

    boolean containsMatchAttributeRuleForUsage(int i) {
        java.util.Iterator<android.media.audiopolicy.AudioMixingRule.AudioMixMatchCriterion> it = this.mCriteria.iterator();
        while (it.hasNext()) {
            android.media.audiopolicy.AudioMixingRule.AudioMixMatchCriterion next = it.next();
            if (next.mRule == 1 && next.mAttr != null && next.mAttr.getSystemUsage() == i) {
                return true;
            }
        }
        return false;
    }

    int getTargetMixType() {
        return this.mTargetMixType;
    }

    public int getTargetMixRole() {
        return this.mTargetMixType == 1 ? 1 : 0;
    }

    public java.util.ArrayList<android.media.audiopolicy.AudioMixingRule.AudioMixMatchCriterion> getCriteria() {
        return this.mCriteria;
    }

    public boolean allowPrivilegedMediaPlaybackCapture() {
        return this.mAllowPrivilegedPlaybackCapture;
    }

    public boolean voiceCommunicationCaptureAllowed() {
        return this.mVoiceCommunicationCaptureAllowed;
    }

    public void setVoiceCommunicationCaptureAllowed(boolean z) {
        this.mVoiceCommunicationCaptureAllowed = z;
    }

    public boolean isForCallRedirection() {
        java.util.Iterator<android.media.audiopolicy.AudioMixingRule.AudioMixMatchCriterion> it = this.mCriteria.iterator();
        while (it.hasNext()) {
            android.media.audiopolicy.AudioMixingRule.AudioMixMatchCriterion next = it.next();
            if (next.mAttr != null && next.mAttr.isForCallRedirection() && ((next.mRule == 1 && (next.mAttr.getUsage() == 2 || next.mAttr.getUsage() == 3)) || (next.mRule == 2 && next.mAttr.getCapturePreset() == 7))) {
                return true;
            }
        }
        return false;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.media.audiopolicy.AudioMixingRule audioMixingRule = (android.media.audiopolicy.AudioMixingRule) obj;
        if (this.mTargetMixType == audioMixingRule.mTargetMixType && java.util.Objects.equals(this.mCriteria, audioMixingRule.mCriteria) && this.mAllowPrivilegedPlaybackCapture == audioMixingRule.mAllowPrivilegedPlaybackCapture && this.mVoiceCommunicationCaptureAllowed == audioMixingRule.mVoiceCommunicationCaptureAllowed) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(this.mTargetMixType), this.mCriteria, java.lang.Boolean.valueOf(this.mAllowPrivilegedPlaybackCapture), java.lang.Boolean.valueOf(this.mVoiceCommunicationCaptureAllowed));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isValidSystemApiRule(int i) {
        switch (i) {
            case 1:
            case 2:
            case 4:
            case 8:
            case 16:
                return true;
            default:
                return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isValidAttributesSystemApiRule(int i) {
        switch (i) {
            case 1:
            case 2:
                return true;
            default:
                return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isValidRule(int i) {
        switch (i & (-32769)) {
            case 1:
            case 2:
            case 4:
            case 8:
            case 16:
                return true;
            default:
                return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isPlayerRule(int i) {
        switch (i & (-32769)) {
            case 1:
            case 8:
                return true;
            default:
                return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isRecorderRule(int i) {
        switch (i & (-32769)) {
            case 2:
                return true;
            default:
                return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isAudioAttributeRule(int i) {
        switch (i) {
            case 1:
            case 2:
                return true;
            default:
                return false;
        }
    }

    public static class Builder {
        private int mTargetMixType = -1;
        private boolean mAllowPrivilegedMediaPlaybackCapture = false;
        private boolean mVoiceCommunicationCaptureAllowed = false;
        private final java.util.Set<android.media.audiopolicy.AudioMixingRule.AudioMixMatchCriterion> mCriteria = new java.util.HashSet();

        public android.media.audiopolicy.AudioMixingRule.Builder addRule(android.media.AudioAttributes audioAttributes, int i) throws java.lang.IllegalArgumentException {
            if (!android.media.audiopolicy.AudioMixingRule.isValidAttributesSystemApiRule(i)) {
                throw new java.lang.IllegalArgumentException("Illegal rule value " + i);
            }
            return checkAddRuleObjInternal(i, audioAttributes);
        }

        public android.media.audiopolicy.AudioMixingRule.Builder excludeRule(android.media.AudioAttributes audioAttributes, int i) throws java.lang.IllegalArgumentException {
            if (!android.media.audiopolicy.AudioMixingRule.isValidAttributesSystemApiRule(i)) {
                throw new java.lang.IllegalArgumentException("Illegal rule value " + i);
            }
            return checkAddRuleObjInternal(i | 32768, audioAttributes);
        }

        public android.media.audiopolicy.AudioMixingRule.Builder addMixRule(int i, java.lang.Object obj) throws java.lang.IllegalArgumentException {
            if (!android.media.audiopolicy.AudioMixingRule.isValidSystemApiRule(i)) {
                throw new java.lang.IllegalArgumentException("Illegal rule value " + i);
            }
            return checkAddRuleObjInternal(i, obj);
        }

        public android.media.audiopolicy.AudioMixingRule.Builder excludeMixRule(int i, java.lang.Object obj) throws java.lang.IllegalArgumentException {
            if (!android.media.audiopolicy.AudioMixingRule.isValidSystemApiRule(i)) {
                throw new java.lang.IllegalArgumentException("Illegal rule value " + i);
            }
            return checkAddRuleObjInternal(i | 32768, obj);
        }

        public android.media.audiopolicy.AudioMixingRule.Builder allowPrivilegedPlaybackCapture(boolean z) {
            this.mAllowPrivilegedMediaPlaybackCapture = z;
            return this;
        }

        public android.media.audiopolicy.AudioMixingRule.Builder voiceCommunicationCaptureAllowed(boolean z) {
            this.mVoiceCommunicationCaptureAllowed = z;
            return this;
        }

        public android.media.audiopolicy.AudioMixingRule.Builder setTargetMixRole(int i) {
            if (i != 0 && i != 1) {
                throw new java.lang.IllegalArgumentException("Illegal argument for mix role");
            }
            if (this.mCriteria.stream().map(new java.util.function.Function() { // from class: android.media.audiopolicy.AudioMixingRule$Builder$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final java.lang.Object apply(java.lang.Object obj) {
                    return java.lang.Integer.valueOf(((android.media.audiopolicy.AudioMixingRule.AudioMixMatchCriterion) obj).getRule());
                }
            }).anyMatch(i == 0 ? new java.util.function.Predicate() { // from class: android.media.audiopolicy.AudioMixingRule$Builder$$ExternalSyntheticLambda1
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    boolean isRecorderRule;
                    isRecorderRule = android.media.audiopolicy.AudioMixingRule.isRecorderRule(((java.lang.Integer) obj).intValue());
                    return isRecorderRule;
                }
            } : new java.util.function.Predicate() { // from class: android.media.audiopolicy.AudioMixingRule$Builder$$ExternalSyntheticLambda2
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    boolean isPlayerRule;
                    isPlayerRule = android.media.audiopolicy.AudioMixingRule.isPlayerRule(((java.lang.Integer) obj).intValue());
                    return isPlayerRule;
                }
            })) {
                throw new java.lang.IllegalArgumentException("Target mix role is not compatible with mix rules.");
            }
            this.mTargetMixType = i != 1 ? 0 : 1;
            return this;
        }

        private android.media.audiopolicy.AudioMixingRule.Builder checkAddRuleObjInternal(int i, java.lang.Object obj) throws java.lang.IllegalArgumentException {
            if (obj == null) {
                throw new java.lang.IllegalArgumentException("Illegal null argument for mixing rule");
            }
            if (!android.media.audiopolicy.AudioMixingRule.isValidRule(i)) {
                throw new java.lang.IllegalArgumentException("Illegal rule value " + i);
            }
            if (android.media.audiopolicy.AudioMixingRule.isAudioAttributeRule((-32769) & i)) {
                if (!(obj instanceof android.media.AudioAttributes)) {
                    throw new java.lang.IllegalArgumentException("Invalid AudioAttributes argument");
                }
                return addRuleInternal(new android.media.audiopolicy.AudioMixingRule.AudioMixMatchCriterion((android.media.AudioAttributes) obj, i));
            }
            if (!(obj instanceof java.lang.Integer)) {
                throw new java.lang.IllegalArgumentException("Invalid Integer argument");
            }
            return addRuleInternal(new android.media.audiopolicy.AudioMixingRule.AudioMixMatchCriterion((java.lang.Integer) obj, i));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public android.media.audiopolicy.AudioMixingRule.Builder addRuleInternal(android.media.audiopolicy.AudioMixingRule.AudioMixMatchCriterion audioMixMatchCriterion) throws java.lang.IllegalArgumentException {
            int i = audioMixMatchCriterion.mRule;
            if (this.mTargetMixType == -1) {
                if (android.media.audiopolicy.AudioMixingRule.isPlayerRule(i)) {
                    this.mTargetMixType = 0;
                } else if (android.media.audiopolicy.AudioMixingRule.isRecorderRule(i)) {
                    this.mTargetMixType = 1;
                }
            } else if ((android.media.audiopolicy.AudioMixingRule.isPlayerRule(i) && this.mTargetMixType != 0) || (android.media.audiopolicy.AudioMixingRule.isRecorderRule(i) && this.mTargetMixType != 1)) {
                throw new java.lang.IllegalArgumentException("Incompatible rule for mix");
            }
            synchronized (this.mCriteria) {
                final int i2 = i ^ 32768;
                if (this.mCriteria.stream().anyMatch(new java.util.function.Predicate() { // from class: android.media.audiopolicy.AudioMixingRule$Builder$$ExternalSyntheticLambda3
                    @Override // java.util.function.Predicate
                    public final boolean test(java.lang.Object obj) {
                        return android.media.audiopolicy.AudioMixingRule.Builder.lambda$addRuleInternal$0(i2, (android.media.audiopolicy.AudioMixingRule.AudioMixMatchCriterion) obj);
                    }
                })) {
                    throw new java.lang.IllegalArgumentException("AudioMixingRule cannot contain RULE_MATCH_* and RULE_EXCLUDE_* for the same dimension.");
                }
                this.mCriteria.add(audioMixMatchCriterion);
            }
            return this;
        }

        static /* synthetic */ boolean lambda$addRuleInternal$0(int i, android.media.audiopolicy.AudioMixingRule.AudioMixMatchCriterion audioMixMatchCriterion) {
            return audioMixMatchCriterion.mRule == i;
        }

        public android.media.audiopolicy.AudioMixingRule build() {
            if (this.mCriteria.isEmpty()) {
                throw new java.lang.IllegalArgumentException("Cannot build AudioMixingRule with no rules.");
            }
            return new android.media.audiopolicy.AudioMixingRule(this.mTargetMixType == -1 ? 0 : this.mTargetMixType, this.mCriteria, this.mAllowPrivilegedMediaPlaybackCapture, this.mVoiceCommunicationCaptureAllowed);
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeBoolean(this.mAllowPrivilegedPlaybackCapture);
        parcel.writeBoolean(this.mVoiceCommunicationCaptureAllowed);
        parcel.writeInt(this.mTargetMixType);
        parcel.writeInt(this.mCriteria.size());
        java.util.Iterator<android.media.audiopolicy.AudioMixingRule.AudioMixMatchCriterion> it = this.mCriteria.iterator();
        while (it.hasNext()) {
            it.next().writeToParcel(parcel, i);
        }
    }
}
