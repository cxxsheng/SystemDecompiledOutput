package android.media.audio.common;

/* loaded from: classes2.dex */
public class AudioPortMixExt implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.media.audio.common.AudioPortMixExt> CREATOR = new android.os.Parcelable.Creator<android.media.audio.common.AudioPortMixExt>() { // from class: android.media.audio.common.AudioPortMixExt.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.audio.common.AudioPortMixExt createFromParcel(android.os.Parcel parcel) {
            android.media.audio.common.AudioPortMixExt audioPortMixExt = new android.media.audio.common.AudioPortMixExt();
            audioPortMixExt.readFromParcel(parcel);
            return audioPortMixExt;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.audio.common.AudioPortMixExt[] newArray(int i) {
            return new android.media.audio.common.AudioPortMixExt[i];
        }
    };
    public android.media.audio.common.AudioPortMixExtUseCase usecase;
    public int handle = 0;
    public int maxOpenStreamCount = 0;
    public int maxActiveStreamCount = 0;
    public int recommendedMuteDurationMs = 0;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.handle);
        parcel.writeTypedObject(this.usecase, i);
        parcel.writeInt(this.maxOpenStreamCount);
        parcel.writeInt(this.maxActiveStreamCount);
        parcel.writeInt(this.recommendedMuteDurationMs);
        int dataPosition2 = parcel.dataPosition();
        parcel.setDataPosition(dataPosition);
        parcel.writeInt(dataPosition2 - dataPosition);
        parcel.setDataPosition(dataPosition2);
    }

    public final void readFromParcel(android.os.Parcel parcel) {
        int dataPosition = parcel.dataPosition();
        int readInt = parcel.readInt();
        try {
            if (readInt < 4) {
                throw new android.os.BadParcelableException("Parcelable too small");
            }
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.handle = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.usecase = (android.media.audio.common.AudioPortMixExtUseCase) parcel.readTypedObject(android.media.audio.common.AudioPortMixExtUseCase.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.maxOpenStreamCount = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.maxActiveStreamCount = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.recommendedMuteDurationMs = parcel.readInt();
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            }
        } catch (java.lang.Throwable th) {
            if (dataPosition > Integer.MAX_VALUE - readInt) {
                throw new android.os.BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(dataPosition + readInt);
            throw th;
        }
    }

    public java.lang.String toString() {
        java.util.StringJoiner stringJoiner = new java.util.StringJoiner(", ", "{", "}");
        stringJoiner.add("handle: " + this.handle);
        stringJoiner.add("usecase: " + java.util.Objects.toString(this.usecase));
        stringJoiner.add("maxOpenStreamCount: " + this.maxOpenStreamCount);
        stringJoiner.add("maxActiveStreamCount: " + this.maxActiveStreamCount);
        stringJoiner.add("recommendedMuteDurationMs: " + this.recommendedMuteDurationMs);
        return "AudioPortMixExt" + stringJoiner.toString();
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof android.media.audio.common.AudioPortMixExt)) {
            return false;
        }
        android.media.audio.common.AudioPortMixExt audioPortMixExt = (android.media.audio.common.AudioPortMixExt) obj;
        if (java.util.Objects.deepEquals(java.lang.Integer.valueOf(this.handle), java.lang.Integer.valueOf(audioPortMixExt.handle)) && java.util.Objects.deepEquals(this.usecase, audioPortMixExt.usecase) && java.util.Objects.deepEquals(java.lang.Integer.valueOf(this.maxOpenStreamCount), java.lang.Integer.valueOf(audioPortMixExt.maxOpenStreamCount)) && java.util.Objects.deepEquals(java.lang.Integer.valueOf(this.maxActiveStreamCount), java.lang.Integer.valueOf(audioPortMixExt.maxActiveStreamCount)) && java.util.Objects.deepEquals(java.lang.Integer.valueOf(this.recommendedMuteDurationMs), java.lang.Integer.valueOf(audioPortMixExt.recommendedMuteDurationMs))) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Arrays.deepHashCode(java.util.Arrays.asList(java.lang.Integer.valueOf(this.handle), this.usecase, java.lang.Integer.valueOf(this.maxOpenStreamCount), java.lang.Integer.valueOf(this.maxActiveStreamCount), java.lang.Integer.valueOf(this.recommendedMuteDurationMs)).toArray());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.usecase) | 0;
    }

    private int describeContents(java.lang.Object obj) {
        if (obj == null || !(obj instanceof android.os.Parcelable)) {
            return 0;
        }
        return ((android.os.Parcelable) obj).describeContents();
    }
}
