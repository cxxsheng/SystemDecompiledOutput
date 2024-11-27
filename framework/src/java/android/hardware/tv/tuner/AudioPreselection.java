package android.hardware.tv.tuner;

/* loaded from: classes2.dex */
public class AudioPreselection implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.tv.tuner.AudioPreselection> CREATOR = new android.os.Parcelable.Creator<android.hardware.tv.tuner.AudioPreselection>() { // from class: android.hardware.tv.tuner.AudioPreselection.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.tv.tuner.AudioPreselection createFromParcel(android.os.Parcel parcel) {
            android.hardware.tv.tuner.AudioPreselection audioPreselection = new android.hardware.tv.tuner.AudioPreselection();
            audioPreselection.readFromParcel(parcel);
            return audioPreselection;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.tv.tuner.AudioPreselection[] newArray(int i) {
            return new android.hardware.tv.tuner.AudioPreselection[i];
        }
    };
    public android.hardware.tv.tuner.AudioPreselectionLabel[] labels;
    public java.lang.String language;
    public int renderingIndication;
    public int preselectionId = 0;
    public boolean hasAudioDescription = false;
    public boolean hasSpokenSubtitles = false;
    public boolean hasDialogueEnhancement = false;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.preselectionId);
        parcel.writeTypedArray(this.labels, i);
        parcel.writeString(this.language);
        parcel.writeInt(this.renderingIndication);
        parcel.writeBoolean(this.hasAudioDescription);
        parcel.writeBoolean(this.hasSpokenSubtitles);
        parcel.writeBoolean(this.hasDialogueEnhancement);
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
            this.preselectionId = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.labels = (android.hardware.tv.tuner.AudioPreselectionLabel[]) parcel.createTypedArray(android.hardware.tv.tuner.AudioPreselectionLabel.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.language = parcel.readString();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.renderingIndication = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.hasAudioDescription = parcel.readBoolean();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.hasSpokenSubtitles = parcel.readBoolean();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.hasDialogueEnhancement = parcel.readBoolean();
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
        return describeContents(this.labels) | 0;
    }

    private int describeContents(java.lang.Object obj) {
        if (obj == null) {
            return 0;
        }
        if (obj instanceof java.lang.Object[]) {
            int i = 0;
            for (java.lang.Object obj2 : (java.lang.Object[]) obj) {
                i |= describeContents(obj2);
            }
            return i;
        }
        if (!(obj instanceof android.os.Parcelable)) {
            return 0;
        }
        return ((android.os.Parcelable) obj).describeContents();
    }
}