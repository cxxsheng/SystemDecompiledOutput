package android.content;

/* loaded from: classes.dex */
public class SyncInfo implements android.os.Parcelable {
    public final android.accounts.Account account;
    public final java.lang.String authority;
    public final int authorityId;
    public final long startTime;
    private static final android.accounts.Account REDACTED_ACCOUNT = new android.accounts.Account("*****", "*****");
    public static final android.os.Parcelable.Creator<android.content.SyncInfo> CREATOR = new android.os.Parcelable.Creator<android.content.SyncInfo>() { // from class: android.content.SyncInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.SyncInfo createFromParcel(android.os.Parcel parcel) {
            return new android.content.SyncInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.SyncInfo[] newArray(int i) {
            return new android.content.SyncInfo[i];
        }
    };

    public static android.content.SyncInfo createAccountRedacted(int i, java.lang.String str, long j) {
        return new android.content.SyncInfo(i, REDACTED_ACCOUNT, str, j);
    }

    public SyncInfo(int i, android.accounts.Account account, java.lang.String str, long j) {
        this.authorityId = i;
        this.account = account;
        this.authority = str;
        this.startTime = j;
    }

    public SyncInfo(android.content.SyncInfo syncInfo) {
        this.authorityId = syncInfo.authorityId;
        this.account = new android.accounts.Account(syncInfo.account.name, syncInfo.account.type);
        this.authority = syncInfo.authority;
        this.startTime = syncInfo.startTime;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.authorityId);
        parcel.writeParcelable(this.account, i);
        parcel.writeString(this.authority);
        parcel.writeLong(this.startTime);
    }

    SyncInfo(android.os.Parcel parcel) {
        this.authorityId = parcel.readInt();
        this.account = (android.accounts.Account) parcel.readParcelable(android.accounts.Account.class.getClassLoader(), android.accounts.Account.class);
        this.authority = parcel.readString();
        this.startTime = parcel.readLong();
    }
}
