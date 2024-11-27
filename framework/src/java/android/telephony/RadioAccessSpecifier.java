package android.telephony;

/* loaded from: classes3.dex */
public final class RadioAccessSpecifier implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.telephony.RadioAccessSpecifier> CREATOR = new android.os.Parcelable.Creator<android.telephony.RadioAccessSpecifier>() { // from class: android.telephony.RadioAccessSpecifier.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.RadioAccessSpecifier createFromParcel(android.os.Parcel parcel) {
            return new android.telephony.RadioAccessSpecifier(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.RadioAccessSpecifier[] newArray(int i) {
            return new android.telephony.RadioAccessSpecifier[i];
        }
    };
    private int[] mBands;
    private int[] mChannels;
    private int mRadioAccessNetwork;

    public RadioAccessSpecifier(int i, int[] iArr, int[] iArr2) {
        this.mRadioAccessNetwork = i;
        if (iArr != null) {
            this.mBands = (int[]) iArr.clone();
        } else {
            this.mBands = null;
        }
        if (iArr2 != null) {
            this.mChannels = (int[]) iArr2.clone();
        } else {
            this.mChannels = null;
        }
    }

    public int getRadioAccessNetwork() {
        return this.mRadioAccessNetwork;
    }

    public int[] getBands() {
        if (this.mBands == null) {
            return null;
        }
        return (int[]) this.mBands.clone();
    }

    public int[] getChannels() {
        if (this.mChannels == null) {
            return null;
        }
        return (int[]) this.mChannels.clone();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mRadioAccessNetwork);
        parcel.writeIntArray(this.mBands);
        parcel.writeIntArray(this.mChannels);
    }

    private RadioAccessSpecifier(android.os.Parcel parcel) {
        this.mRadioAccessNetwork = parcel.readInt();
        this.mBands = parcel.createIntArray();
        this.mChannels = parcel.createIntArray();
    }

    public boolean equals(java.lang.Object obj) {
        try {
            android.telephony.RadioAccessSpecifier radioAccessSpecifier = (android.telephony.RadioAccessSpecifier) obj;
            return obj != null && this.mRadioAccessNetwork == radioAccessSpecifier.mRadioAccessNetwork && java.util.Arrays.equals(this.mBands, radioAccessSpecifier.mBands) && java.util.Arrays.equals(this.mChannels, radioAccessSpecifier.mChannels);
        } catch (java.lang.ClassCastException e) {
            return false;
        }
    }

    public int hashCode() {
        return (this.mRadioAccessNetwork * 31) + (java.util.Arrays.hashCode(this.mBands) * 37) + (java.util.Arrays.hashCode(this.mChannels) * 39);
    }

    public java.lang.String toString() {
        return "RadioAccessSpecifier[mRadioAccessNetwork=" + android.telephony.AccessNetworkConstants.AccessNetworkType.toString(this.mRadioAccessNetwork) + ", mBands=" + java.util.Arrays.toString(this.mBands) + ", mChannels=" + java.util.Arrays.toString(this.mChannels) + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END;
    }
}
