package android.telephony.ims;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public final class ImsSuppServiceNotification implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.telephony.ims.ImsSuppServiceNotification> CREATOR = new android.os.Parcelable.Creator<android.telephony.ims.ImsSuppServiceNotification>() { // from class: android.telephony.ims.ImsSuppServiceNotification.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.ims.ImsSuppServiceNotification createFromParcel(android.os.Parcel parcel) {
            return new android.telephony.ims.ImsSuppServiceNotification(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.ims.ImsSuppServiceNotification[] newArray(int i) {
            return new android.telephony.ims.ImsSuppServiceNotification[i];
        }
    };
    private static final java.lang.String TAG = "ImsSuppServiceNotification";
    public final int code;
    public final java.lang.String[] history;
    public final int index;
    public final int notificationType;
    public final java.lang.String number;
    public final int type;

    public ImsSuppServiceNotification(int i, int i2, int i3, int i4, java.lang.String str, java.lang.String[] strArr) {
        this.notificationType = i;
        this.code = i2;
        this.index = i3;
        this.type = i4;
        this.number = str;
        this.history = strArr;
    }

    public ImsSuppServiceNotification(android.os.Parcel parcel) {
        this.notificationType = parcel.readInt();
        this.code = parcel.readInt();
        this.index = parcel.readInt();
        this.type = parcel.readInt();
        this.number = parcel.readString();
        this.history = parcel.createStringArray();
    }

    public java.lang.String toString() {
        return "{ notificationType=" + this.notificationType + ", code=" + this.code + ", index=" + this.index + ", type=" + this.type + ", number=" + this.number + ", history=" + java.util.Arrays.toString(this.history) + " }";
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.notificationType);
        parcel.writeInt(this.code);
        parcel.writeInt(this.index);
        parcel.writeInt(this.type);
        parcel.writeString(this.number);
        parcel.writeStringArray(this.history);
    }
}
