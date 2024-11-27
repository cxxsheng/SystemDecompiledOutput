package android.service.notification;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public final class SnoozeCriterion implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.service.notification.SnoozeCriterion> CREATOR = new android.os.Parcelable.Creator<android.service.notification.SnoozeCriterion>() { // from class: android.service.notification.SnoozeCriterion.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.notification.SnoozeCriterion createFromParcel(android.os.Parcel parcel) {
            return new android.service.notification.SnoozeCriterion(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.notification.SnoozeCriterion[] newArray(int i) {
            return new android.service.notification.SnoozeCriterion[i];
        }
    };
    private final java.lang.CharSequence mConfirmation;
    private final java.lang.CharSequence mExplanation;
    private final java.lang.String mId;

    public SnoozeCriterion(java.lang.String str, java.lang.CharSequence charSequence, java.lang.CharSequence charSequence2) {
        this.mId = str;
        this.mExplanation = charSequence;
        this.mConfirmation = charSequence2;
    }

    protected SnoozeCriterion(android.os.Parcel parcel) {
        if (parcel.readByte() != 0) {
            this.mId = parcel.readString();
        } else {
            this.mId = null;
        }
        if (parcel.readByte() != 0) {
            this.mExplanation = parcel.readCharSequence();
        } else {
            this.mExplanation = null;
        }
        if (parcel.readByte() != 0) {
            this.mConfirmation = parcel.readCharSequence();
        } else {
            this.mConfirmation = null;
        }
    }

    public java.lang.String getId() {
        return this.mId;
    }

    public java.lang.CharSequence getExplanation() {
        return this.mExplanation;
    }

    public java.lang.CharSequence getConfirmation() {
        return this.mConfirmation;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        if (this.mId != null) {
            parcel.writeByte((byte) 1);
            parcel.writeString(this.mId);
        } else {
            parcel.writeByte((byte) 0);
        }
        if (this.mExplanation != null) {
            parcel.writeByte((byte) 1);
            parcel.writeCharSequence(this.mExplanation);
        } else {
            parcel.writeByte((byte) 0);
        }
        if (this.mConfirmation != null) {
            parcel.writeByte((byte) 1);
            parcel.writeCharSequence(this.mConfirmation);
        } else {
            parcel.writeByte((byte) 0);
        }
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.service.notification.SnoozeCriterion snoozeCriterion = (android.service.notification.SnoozeCriterion) obj;
        if (this.mId == null ? snoozeCriterion.mId != null : !this.mId.equals(snoozeCriterion.mId)) {
            return false;
        }
        if (this.mExplanation == null ? snoozeCriterion.mExplanation != null : !this.mExplanation.equals(snoozeCriterion.mExplanation)) {
            return false;
        }
        if (this.mConfirmation != null) {
            return this.mConfirmation.equals(snoozeCriterion.mConfirmation);
        }
        if (snoozeCriterion.mConfirmation == null) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return ((((this.mId != null ? this.mId.hashCode() : 0) * 31) + (this.mExplanation != null ? this.mExplanation.hashCode() : 0)) * 31) + (this.mConfirmation != null ? this.mConfirmation.hashCode() : 0);
    }
}
