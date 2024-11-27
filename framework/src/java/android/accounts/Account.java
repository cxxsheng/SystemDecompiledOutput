package android.accounts;

/* loaded from: classes.dex */
public class Account implements android.os.Parcelable {
    private static final java.lang.String TAG = "Account";
    private final java.lang.String accessId;
    private java.lang.String mSafeName;
    public final java.lang.String name;
    public final java.lang.String type;
    private static final java.util.Set<android.accounts.Account> sAccessedAccounts = new android.util.ArraySet();
    public static final android.os.Parcelable.Creator<android.accounts.Account> CREATOR = new android.os.Parcelable.Creator<android.accounts.Account>() { // from class: android.accounts.Account.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.accounts.Account createFromParcel(android.os.Parcel parcel) {
            return new android.accounts.Account(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.accounts.Account[] newArray(int i) {
            return new android.accounts.Account[i];
        }
    };

    public boolean equals(java.lang.Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof android.accounts.Account)) {
            return false;
        }
        android.accounts.Account account = (android.accounts.Account) obj;
        return this.name.equals(account.name) && this.type.equals(account.type);
    }

    public int hashCode() {
        return ((527 + this.name.hashCode()) * 31) + this.type.hashCode();
    }

    public Account(java.lang.String str, java.lang.String str2) {
        this(str, str2, null);
    }

    public Account(android.accounts.Account account, java.lang.String str) {
        this(account.name, account.type, str);
    }

    public Account(java.lang.String str, java.lang.String str2, java.lang.String str3) {
        if (android.text.TextUtils.isEmpty(str)) {
            throw new java.lang.IllegalArgumentException("the name must not be empty: " + str);
        }
        if (android.text.TextUtils.isEmpty(str2)) {
            throw new java.lang.IllegalArgumentException("the type must not be empty: " + str2);
        }
        this.name = str;
        this.type = str2;
        this.accessId = str3;
    }

    public Account(android.os.Parcel parcel) {
        this.name = parcel.readString();
        this.type = parcel.readString();
        if (android.text.TextUtils.isEmpty(this.name)) {
            throw new android.os.BadParcelableException("the name must not be empty: " + this.name);
        }
        if (android.text.TextUtils.isEmpty(this.type)) {
            throw new android.os.BadParcelableException("the type must not be empty: " + this.type);
        }
        this.accessId = parcel.readString();
        if (this.accessId != null) {
            synchronized (sAccessedAccounts) {
                if (sAccessedAccounts.add(this)) {
                    onAccountAccessed(this.accessId);
                }
            }
        }
    }

    private static void onAccountAccessed(java.lang.String str) {
        try {
            android.accounts.IAccountManager.Stub.asInterface(android.os.ServiceManager.getService("account")).onAccountAccessed(str);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error noting account access", e);
        }
    }

    private static void onAccountAccessed$ravenwood(java.lang.String str) {
    }

    public java.lang.String getAccessId() {
        return this.accessId;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.name);
        parcel.writeString(this.type);
        parcel.writeString(this.accessId);
    }

    public java.lang.String toString() {
        return "Account {name=" + this.name + ", type=" + this.type + "}";
    }

    public java.lang.String toSafeString() {
        if (this.mSafeName == null) {
            this.mSafeName = toSafeName(this.name, com.android.internal.transition.EpicenterTranslateClipReveal.StateProperty.TARGET_X);
        }
        return "Account {name=" + this.mSafeName + ", type=" + this.type + "}";
    }

    public static java.lang.String toSafeName(java.lang.String str, char c) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder(64);
        int length = str.length();
        for (int i = 0; i < length; i++) {
            char charAt = str.charAt(i);
            if (java.lang.Character.isLetterOrDigit(charAt)) {
                sb.append(c);
            } else {
                sb.append(charAt);
            }
        }
        return sb.toString();
    }
}
