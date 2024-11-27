package android.media.tv;

/* loaded from: classes2.dex */
public final class TableRequest extends android.media.tv.BroadcastInfoRequest implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.media.tv.TableRequest> CREATOR = new android.os.Parcelable.Creator<android.media.tv.TableRequest>() { // from class: android.media.tv.TableRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.tv.TableRequest createFromParcel(android.os.Parcel parcel) {
            parcel.readInt();
            return android.media.tv.TableRequest.createFromParcelBody(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.tv.TableRequest[] newArray(int i) {
            return new android.media.tv.TableRequest[i];
        }
    };
    private static final int REQUEST_TYPE = 2;
    public static final int TABLE_NAME_BAT = 4;
    public static final int TABLE_NAME_CAT = 2;
    public static final int TABLE_NAME_EIT = 6;
    public static final int TABLE_NAME_NIT = 3;
    public static final int TABLE_NAME_PAT = 0;
    public static final int TABLE_NAME_PMT = 1;
    public static final int TABLE_NAME_SDT = 5;
    public static final int TABLE_NAME_SIT = 9;
    public static final int TABLE_NAME_TDT = 7;
    public static final int TABLE_NAME_TOT = 8;
    private final int mTableId;
    private final int mTableName;
    private final int mVersion;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface TableName {
    }

    static android.media.tv.TableRequest createFromParcelBody(android.os.Parcel parcel) {
        return new android.media.tv.TableRequest(parcel);
    }

    public TableRequest(int i, int i2, int i3, int i4, int i5) {
        super(2, i, i2);
        this.mTableId = i3;
        this.mTableName = i4;
        this.mVersion = i5;
    }

    TableRequest(android.os.Parcel parcel) {
        super(2, parcel);
        this.mTableId = parcel.readInt();
        this.mTableName = parcel.readInt();
        this.mVersion = parcel.readInt();
    }

    public int getTableId() {
        return this.mTableId;
    }

    public int getTableName() {
        return this.mTableName;
    }

    public int getVersion() {
        return this.mVersion;
    }

    @Override // android.media.tv.BroadcastInfoRequest, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.media.tv.BroadcastInfoRequest, android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeInt(this.mTableId);
        parcel.writeInt(this.mTableName);
        parcel.writeInt(this.mVersion);
    }
}
