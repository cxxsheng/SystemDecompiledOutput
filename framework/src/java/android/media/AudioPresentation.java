package android.media;

/* loaded from: classes2.dex */
public final class AudioPresentation implements android.os.Parcelable {
    public static final int CONTENT_COMMENTARY = 5;
    public static final int CONTENT_DIALOG = 4;
    public static final int CONTENT_EMERGENCY = 6;
    public static final int CONTENT_HEARING_IMPAIRED = 3;
    public static final int CONTENT_MAIN = 0;
    public static final int CONTENT_MUSIC_AND_EFFECTS = 1;
    public static final int CONTENT_UNKNOWN = -1;
    public static final int CONTENT_VISUALLY_IMPAIRED = 2;
    public static final int CONTENT_VOICEOVER = 7;
    public static final android.os.Parcelable.Creator<android.media.AudioPresentation> CREATOR = new android.os.Parcelable.Creator<android.media.AudioPresentation>() { // from class: android.media.AudioPresentation.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.AudioPresentation createFromParcel(android.os.Parcel parcel) {
            return new android.media.AudioPresentation(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.AudioPresentation[] newArray(int i) {
            return new android.media.AudioPresentation[i];
        }
    };
    public static final int MASTERED_FOR_3D = 3;
    public static final int MASTERED_FOR_HEADPHONE = 4;
    public static final int MASTERED_FOR_STEREO = 1;
    public static final int MASTERED_FOR_SURROUND = 2;
    public static final int MASTERING_NOT_INDICATED = 0;
    public static final int PRESENTATION_ID_UNKNOWN = -1;
    public static final int PROGRAM_ID_UNKNOWN = -1;
    private final boolean mAudioDescriptionAvailable;
    private final boolean mDialogueEnhancementAvailable;
    private final java.util.HashMap<android.icu.util.ULocale, java.lang.String> mLabels;
    private final android.icu.util.ULocale mLanguage;
    private final int mMasteringIndication;
    private final int mPresentationId;
    private final int mProgramId;
    private final boolean mSpokenSubtitlesAvailable;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ContentClassifier {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface MasteringIndicationType {
    }

    private AudioPresentation(int i, int i2, android.icu.util.ULocale uLocale, int i3, boolean z, boolean z2, boolean z3, java.util.Map<android.icu.util.ULocale, java.lang.String> map) {
        this.mPresentationId = i;
        this.mProgramId = i2;
        this.mLanguage = uLocale;
        this.mMasteringIndication = i3;
        this.mAudioDescriptionAvailable = z;
        this.mSpokenSubtitlesAvailable = z2;
        this.mDialogueEnhancementAvailable = z3;
        this.mLabels = new java.util.HashMap<>(map);
    }

    private AudioPresentation(android.os.Parcel parcel) {
        this.mPresentationId = parcel.readInt();
        this.mProgramId = parcel.readInt();
        this.mLanguage = (android.icu.util.ULocale) parcel.readSerializable(android.icu.util.ULocale.class.getClassLoader(), android.icu.util.ULocale.class);
        this.mMasteringIndication = parcel.readInt();
        this.mAudioDescriptionAvailable = parcel.readBoolean();
        this.mSpokenSubtitlesAvailable = parcel.readBoolean();
        this.mDialogueEnhancementAvailable = parcel.readBoolean();
        this.mLabels = (java.util.HashMap) parcel.readSerializable(java.util.HashMap.class.getClassLoader(), java.util.HashMap.class);
    }

    public int getPresentationId() {
        return this.mPresentationId;
    }

    public int getProgramId() {
        return this.mProgramId;
    }

    public java.util.Map<java.util.Locale, java.lang.String> getLabels() {
        java.util.HashMap hashMap = new java.util.HashMap(this.mLabels.size());
        for (java.util.Map.Entry<android.icu.util.ULocale, java.lang.String> entry : this.mLabels.entrySet()) {
            hashMap.put(entry.getKey().toLocale(), entry.getValue());
        }
        return hashMap;
    }

    private java.util.Map<android.icu.util.ULocale, java.lang.String> getULabels() {
        return this.mLabels;
    }

    public java.util.Locale getLocale() {
        return this.mLanguage.toLocale();
    }

    private android.icu.util.ULocale getULocale() {
        return this.mLanguage;
    }

    public int getMasteringIndication() {
        return this.mMasteringIndication;
    }

    public boolean hasAudioDescription() {
        return this.mAudioDescriptionAvailable;
    }

    public boolean hasSpokenSubtitles() {
        return this.mSpokenSubtitlesAvailable;
    }

    public boolean hasDialogueEnhancement() {
        return this.mDialogueEnhancementAvailable;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof android.media.AudioPresentation)) {
            return false;
        }
        android.media.AudioPresentation audioPresentation = (android.media.AudioPresentation) obj;
        return this.mPresentationId == audioPresentation.getPresentationId() && this.mProgramId == audioPresentation.getProgramId() && this.mLanguage.equals(audioPresentation.getULocale()) && this.mMasteringIndication == audioPresentation.getMasteringIndication() && this.mAudioDescriptionAvailable == audioPresentation.hasAudioDescription() && this.mSpokenSubtitlesAvailable == audioPresentation.hasSpokenSubtitles() && this.mDialogueEnhancementAvailable == audioPresentation.hasDialogueEnhancement() && this.mLabels.equals(audioPresentation.getULabels());
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(this.mPresentationId), java.lang.Integer.valueOf(this.mProgramId), java.lang.Integer.valueOf(this.mLanguage.hashCode()), java.lang.Integer.valueOf(this.mMasteringIndication), java.lang.Boolean.valueOf(this.mAudioDescriptionAvailable), java.lang.Boolean.valueOf(this.mSpokenSubtitlesAvailable), java.lang.Boolean.valueOf(this.mDialogueEnhancementAvailable), java.lang.Integer.valueOf(this.mLabels.hashCode()));
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append(getClass().getSimpleName() + " ");
        sb.append("{ presentation id=" + this.mPresentationId);
        sb.append(", program id=" + this.mProgramId);
        sb.append(", language=" + this.mLanguage);
        sb.append(", labels=" + this.mLabels);
        sb.append(", mastering indication=" + this.mMasteringIndication);
        sb.append(", audio description=" + this.mAudioDescriptionAvailable);
        sb.append(", spoken subtitles=" + this.mSpokenSubtitlesAvailable);
        sb.append(", dialogue enhancement=" + this.mDialogueEnhancementAvailable);
        sb.append(" }");
        return sb.toString();
    }

    public static final class Builder {
        private final int mPresentationId;
        private int mProgramId = -1;
        private android.icu.util.ULocale mLanguage = new android.icu.util.ULocale("");
        private int mMasteringIndication = 0;
        private boolean mAudioDescriptionAvailable = false;
        private boolean mSpokenSubtitlesAvailable = false;
        private boolean mDialogueEnhancementAvailable = false;
        private java.util.HashMap<android.icu.util.ULocale, java.lang.String> mLabels = new java.util.HashMap<>();

        public Builder(int i) {
            this.mPresentationId = i;
        }

        public android.media.AudioPresentation.Builder setProgramId(int i) {
            this.mProgramId = i;
            return this;
        }

        public android.media.AudioPresentation.Builder setLocale(android.icu.util.ULocale uLocale) {
            this.mLanguage = uLocale;
            return this;
        }

        public android.media.AudioPresentation.Builder setMasteringIndication(int i) {
            if (i != 0 && i != 1 && i != 2 && i != 3 && i != 4) {
                throw new java.lang.IllegalArgumentException("Unknown mastering indication: " + i);
            }
            this.mMasteringIndication = i;
            return this;
        }

        public android.media.AudioPresentation.Builder setLabels(java.util.Map<android.icu.util.ULocale, java.lang.CharSequence> map) {
            this.mLabels.clear();
            for (java.util.Map.Entry<android.icu.util.ULocale, java.lang.CharSequence> entry : map.entrySet()) {
                this.mLabels.put(entry.getKey(), entry.getValue().toString());
            }
            return this;
        }

        public android.media.AudioPresentation.Builder setHasAudioDescription(boolean z) {
            this.mAudioDescriptionAvailable = z;
            return this;
        }

        public android.media.AudioPresentation.Builder setHasSpokenSubtitles(boolean z) {
            this.mSpokenSubtitlesAvailable = z;
            return this;
        }

        public android.media.AudioPresentation.Builder setHasDialogueEnhancement(boolean z) {
            this.mDialogueEnhancementAvailable = z;
            return this;
        }

        public android.media.AudioPresentation build() {
            return new android.media.AudioPresentation(this.mPresentationId, this.mProgramId, this.mLanguage, this.mMasteringIndication, this.mAudioDescriptionAvailable, this.mSpokenSubtitlesAvailable, this.mDialogueEnhancementAvailable, this.mLabels);
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(getPresentationId());
        parcel.writeInt(getProgramId());
        parcel.writeSerializable(getULocale());
        parcel.writeInt(getMasteringIndication());
        parcel.writeBoolean(hasAudioDescription());
        parcel.writeBoolean(hasSpokenSubtitles());
        parcel.writeBoolean(hasDialogueEnhancement());
        parcel.writeSerializable(this.mLabels);
    }
}
