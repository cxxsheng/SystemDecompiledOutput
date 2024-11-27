package com.android.internal.content;

/* loaded from: classes4.dex */
public class ReferrerIntent extends android.content.Intent {
    public static final android.os.Parcelable.Creator<com.android.internal.content.ReferrerIntent> CREATOR = new android.os.Parcelable.Creator<com.android.internal.content.ReferrerIntent>() { // from class: com.android.internal.content.ReferrerIntent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.android.internal.content.ReferrerIntent createFromParcel(android.os.Parcel parcel) {
            return new com.android.internal.content.ReferrerIntent(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.android.internal.content.ReferrerIntent[] newArray(int i) {
            return new com.android.internal.content.ReferrerIntent[i];
        }
    };
    public final android.os.IBinder mCallerToken;
    public final java.lang.String mReferrer;

    public ReferrerIntent(android.content.Intent intent, java.lang.String str) {
        this(intent, str, null);
    }

    public ReferrerIntent(android.content.Intent intent, java.lang.String str, android.os.IBinder iBinder) {
        super(intent);
        this.mReferrer = str;
        this.mCallerToken = iBinder;
    }

    @Override // android.content.Intent, android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeString(this.mReferrer);
        parcel.writeStrongBinder(this.mCallerToken);
    }

    ReferrerIntent(android.os.Parcel parcel) {
        readFromParcel(parcel);
        this.mReferrer = parcel.readString();
        this.mCallerToken = parcel.readStrongBinder();
    }

    public boolean equals(java.lang.Object obj) {
        if (obj == null || !(obj instanceof com.android.internal.content.ReferrerIntent)) {
            return false;
        }
        com.android.internal.content.ReferrerIntent referrerIntent = (com.android.internal.content.ReferrerIntent) obj;
        return filterEquals(referrerIntent) && java.util.Objects.equals(this.mReferrer, referrerIntent.mReferrer) && java.util.Objects.equals(this.mCallerToken, referrerIntent.mCallerToken);
    }

    public int hashCode() {
        return ((((527 + filterHashCode()) * 31) + java.util.Objects.hashCode(this.mReferrer)) * 31) + java.util.Objects.hashCode(this.mCallerToken);
    }
}
