package android.media;

/* loaded from: classes2.dex */
public class AudioMixMatchCriterion implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.media.AudioMixMatchCriterion> CREATOR = new android.os.Parcelable.Creator<android.media.AudioMixMatchCriterion>() { // from class: android.media.AudioMixMatchCriterion.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.AudioMixMatchCriterion createFromParcel(android.os.Parcel parcel) {
            android.media.AudioMixMatchCriterion audioMixMatchCriterion = new android.media.AudioMixMatchCriterion();
            audioMixMatchCriterion.readFromParcel(parcel);
            return audioMixMatchCriterion;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.AudioMixMatchCriterion[] newArray(int i) {
            return new android.media.AudioMixMatchCriterion[i];
        }
    };
    public boolean invert = false;
    public android.media.AudioMixMatchCriterionValue value;

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeBoolean(this.invert);
        parcel.writeTypedObject(this.value, i);
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
            this.invert = parcel.readBoolean();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.value = (android.media.AudioMixMatchCriterionValue) parcel.readTypedObject(android.media.AudioMixMatchCriterionValue.CREATOR);
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

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.value) | 0;
    }

    private int describeContents(java.lang.Object obj) {
        if (obj == null || !(obj instanceof android.os.Parcelable)) {
            return 0;
        }
        return ((android.os.Parcelable) obj).describeContents();
    }
}
