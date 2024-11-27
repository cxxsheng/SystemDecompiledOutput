package android.content;

/* loaded from: classes.dex */
public class PeriodicSync implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.content.PeriodicSync> CREATOR = new android.os.Parcelable.Creator<android.content.PeriodicSync>() { // from class: android.content.PeriodicSync.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.PeriodicSync createFromParcel(android.os.Parcel parcel) {
            return new android.content.PeriodicSync(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.PeriodicSync[] newArray(int i) {
            return new android.content.PeriodicSync[i];
        }
    };
    public final android.accounts.Account account;
    public final java.lang.String authority;
    public final android.os.Bundle extras;
    public final long flexTime;
    public final long period;

    public PeriodicSync(android.accounts.Account account, java.lang.String str, android.os.Bundle bundle, long j) {
        this.account = account;
        this.authority = str;
        if (bundle == null) {
            this.extras = new android.os.Bundle();
        } else {
            this.extras = new android.os.Bundle(bundle);
        }
        this.period = j;
        this.flexTime = 0L;
    }

    public PeriodicSync(android.content.PeriodicSync periodicSync) {
        this.account = periodicSync.account;
        this.authority = periodicSync.authority;
        this.extras = new android.os.Bundle(periodicSync.extras);
        this.period = periodicSync.period;
        this.flexTime = periodicSync.flexTime;
    }

    public PeriodicSync(android.accounts.Account account, java.lang.String str, android.os.Bundle bundle, long j, long j2) {
        this.account = account;
        this.authority = str;
        this.extras = new android.os.Bundle(bundle);
        this.period = j;
        this.flexTime = j2;
    }

    private PeriodicSync(android.os.Parcel parcel) {
        this.account = (android.accounts.Account) parcel.readParcelable(null, android.accounts.Account.class);
        this.authority = parcel.readString();
        this.extras = parcel.readBundle();
        this.period = parcel.readLong();
        this.flexTime = parcel.readLong();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeParcelable(this.account, i);
        parcel.writeString(this.authority);
        parcel.writeBundle(this.extras);
        parcel.writeLong(this.period);
        parcel.writeLong(this.flexTime);
    }

    public boolean equals(java.lang.Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof android.content.PeriodicSync)) {
            return false;
        }
        android.content.PeriodicSync periodicSync = (android.content.PeriodicSync) obj;
        return this.account.equals(periodicSync.account) && this.authority.equals(periodicSync.authority) && this.period == periodicSync.period && syncExtrasEquals(this.extras, periodicSync.extras);
    }

    public static boolean syncExtrasEquals(android.os.Bundle bundle, android.os.Bundle bundle2) {
        if (bundle.size() != bundle2.size()) {
            return false;
        }
        if (bundle.isEmpty()) {
            return true;
        }
        for (java.lang.String str : bundle.keySet()) {
            if (!bundle2.containsKey(str) || !java.util.Objects.equals(bundle.get(str), bundle2.get(str))) {
                return false;
            }
        }
        return true;
    }

    public java.lang.String toString() {
        return "account: " + this.account + ", authority: " + this.authority + ". period: " + this.period + "s , flex: " + this.flexTime;
    }
}
