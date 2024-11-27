package android.media;

/* loaded from: classes2.dex */
public final class VolumePolicy implements android.os.Parcelable {
    public static final int A11Y_MODE_INDEPENDENT_A11Y_VOLUME = 1;
    public static final int A11Y_MODE_MEDIA_A11Y_VOLUME = 0;
    public final boolean doNotDisturbWhenSilent;
    public final int vibrateToSilentDebounce;
    public final boolean volumeDownToEnterSilent;
    public final boolean volumeUpToExitSilent;
    public static final android.media.VolumePolicy DEFAULT = new android.media.VolumePolicy(false, false, false, 400);
    public static final android.os.Parcelable.Creator<android.media.VolumePolicy> CREATOR = new android.os.Parcelable.Creator<android.media.VolumePolicy>() { // from class: android.media.VolumePolicy.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.VolumePolicy createFromParcel(android.os.Parcel parcel) {
            return new android.media.VolumePolicy(parcel.readInt() != 0, parcel.readInt() != 0, parcel.readInt() != 0, parcel.readInt());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.VolumePolicy[] newArray(int i) {
            return new android.media.VolumePolicy[i];
        }
    };

    public VolumePolicy(boolean z, boolean z2, boolean z3, int i) {
        this.volumeDownToEnterSilent = z;
        this.volumeUpToExitSilent = z2;
        this.doNotDisturbWhenSilent = z3;
        this.vibrateToSilentDebounce = i;
    }

    public java.lang.String toString() {
        return "VolumePolicy[volumeDownToEnterSilent=" + this.volumeDownToEnterSilent + ",volumeUpToExitSilent=" + this.volumeUpToExitSilent + ",doNotDisturbWhenSilent=" + this.doNotDisturbWhenSilent + ",vibrateToSilentDebounce=" + this.vibrateToSilentDebounce + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END;
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Boolean.valueOf(this.volumeDownToEnterSilent), java.lang.Boolean.valueOf(this.volumeUpToExitSilent), java.lang.Boolean.valueOf(this.doNotDisturbWhenSilent), java.lang.Integer.valueOf(this.vibrateToSilentDebounce));
    }

    public boolean equals(java.lang.Object obj) {
        if (!(obj instanceof android.media.VolumePolicy)) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        android.media.VolumePolicy volumePolicy = (android.media.VolumePolicy) obj;
        return volumePolicy.volumeDownToEnterSilent == this.volumeDownToEnterSilent && volumePolicy.volumeUpToExitSilent == this.volumeUpToExitSilent && volumePolicy.doNotDisturbWhenSilent == this.doNotDisturbWhenSilent && volumePolicy.vibrateToSilentDebounce == this.vibrateToSilentDebounce;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.volumeDownToEnterSilent ? 1 : 0);
        parcel.writeInt(this.volumeUpToExitSilent ? 1 : 0);
        parcel.writeInt(this.doNotDisturbWhenSilent ? 1 : 0);
        parcel.writeInt(this.vibrateToSilentDebounce);
    }
}
