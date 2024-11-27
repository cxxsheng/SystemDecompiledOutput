package android.security.keymaster;

/* loaded from: classes3.dex */
public class ExportResult implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.security.keymaster.ExportResult> CREATOR = new android.os.Parcelable.Creator<android.security.keymaster.ExportResult>() { // from class: android.security.keymaster.ExportResult.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.security.keymaster.ExportResult createFromParcel(android.os.Parcel parcel) {
            return new android.security.keymaster.ExportResult(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.security.keymaster.ExportResult[] newArray(int i) {
            return new android.security.keymaster.ExportResult[i];
        }
    };
    public final byte[] exportData;
    public final int resultCode;

    public ExportResult(int i) {
        this.resultCode = i;
        this.exportData = new byte[0];
    }

    protected ExportResult(android.os.Parcel parcel) {
        this.resultCode = parcel.readInt();
        this.exportData = parcel.createByteArray();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.resultCode);
        parcel.writeByteArray(this.exportData);
    }
}
