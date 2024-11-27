package android.media;

/* loaded from: classes2.dex */
public class AudioRoutesInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.media.AudioRoutesInfo> CREATOR = new android.os.Parcelable.Creator<android.media.AudioRoutesInfo>() { // from class: android.media.AudioRoutesInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.AudioRoutesInfo createFromParcel(android.os.Parcel parcel) {
            return new android.media.AudioRoutesInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.AudioRoutesInfo[] newArray(int i) {
            return new android.media.AudioRoutesInfo[i];
        }
    };
    public static final int MAIN_DOCK_SPEAKERS = 4;
    public static final int MAIN_HDMI = 8;
    public static final int MAIN_HEADPHONES = 2;
    public static final int MAIN_HEADSET = 1;
    public static final int MAIN_SPEAKER = 0;
    public static final int MAIN_USB = 16;
    public java.lang.CharSequence bluetoothName;
    public int mainType;

    public AudioRoutesInfo() {
        this.mainType = 0;
    }

    public AudioRoutesInfo(android.media.AudioRoutesInfo audioRoutesInfo) {
        this.mainType = 0;
        this.bluetoothName = audioRoutesInfo.bluetoothName;
        this.mainType = audioRoutesInfo.mainType;
    }

    AudioRoutesInfo(android.os.Parcel parcel) {
        this.mainType = 0;
        this.bluetoothName = android.text.TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.mainType = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public java.lang.String toString() {
        return getClass().getSimpleName() + "{ type=" + typeToString(this.mainType) + (android.text.TextUtils.isEmpty(this.bluetoothName) ? "" : ", bluetoothName=" + ((java.lang.Object) this.bluetoothName)) + " }";
    }

    private static java.lang.String typeToString(int i) {
        return i == 0 ? "SPEAKER" : (i & 1) != 0 ? "HEADSET" : (i & 2) != 0 ? "HEADPHONES" : (i & 4) != 0 ? "DOCK_SPEAKERS" : (i & 8) != 0 ? "HDMI" : (i & 16) != 0 ? "USB" : java.lang.Integer.toHexString(i);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        android.text.TextUtils.writeToParcel(this.bluetoothName, parcel, i);
        parcel.writeInt(this.mainType);
    }
}
