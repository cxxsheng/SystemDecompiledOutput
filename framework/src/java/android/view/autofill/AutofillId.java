package android.view.autofill;

/* loaded from: classes4.dex */
public final class AutofillId implements android.os.Parcelable {
    private static final int FLAG_HAS_SESSION = 4;
    private static final int FLAG_IS_VIRTUAL_INT = 1;
    private static final int FLAG_IS_VIRTUAL_LONG = 2;
    public static final int NO_SESSION = 0;
    private int mFlags;
    private int mSessionId;
    private final int mViewId;
    private final int mVirtualIntId;
    private final long mVirtualLongId;
    public static final android.view.autofill.AutofillId NO_AUTOFILL_ID = new android.view.autofill.AutofillId(0);
    public static final android.os.Parcelable.Creator<android.view.autofill.AutofillId> CREATOR = new android.os.Parcelable.Creator<android.view.autofill.AutofillId>() { // from class: android.view.autofill.AutofillId.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.autofill.AutofillId createFromParcel(android.os.Parcel parcel) {
            int readInt = parcel.readInt();
            int readInt2 = parcel.readInt();
            int readInt3 = (readInt2 & 4) != 0 ? parcel.readInt() : 0;
            if ((readInt2 & 1) != 0) {
                return new android.view.autofill.AutofillId(readInt2, readInt, parcel.readInt(), readInt3);
            }
            if ((readInt2 & 2) != 0) {
                return new android.view.autofill.AutofillId(readInt2, readInt, parcel.readLong(), readInt3);
            }
            return new android.view.autofill.AutofillId(readInt2, readInt, -1L, readInt3);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.autofill.AutofillId[] newArray(int i) {
            return new android.view.autofill.AutofillId[i];
        }
    };

    public AutofillId(int i) {
        this(0, i, -1L, 0);
    }

    public AutofillId(android.view.autofill.AutofillId autofillId, int i) {
        this(1, autofillId.mViewId, i, 0);
    }

    public AutofillId(int i, int i2) {
        this(1, i, i2, 0);
    }

    public AutofillId(android.view.autofill.AutofillId autofillId, long j, int i) {
        this(6, autofillId.mViewId, j, i);
    }

    private AutofillId(int i, int i2, long j, int i3) {
        this.mFlags = i;
        this.mViewId = i2;
        this.mVirtualIntId = (i & 1) != 0 ? (int) j : -1;
        this.mVirtualLongId = (i & 2) == 0 ? -1L : j;
        this.mSessionId = i3;
    }

    public static android.view.autofill.AutofillId create(android.view.View view, int i) {
        java.util.Objects.requireNonNull(view);
        return new android.view.autofill.AutofillId(view.getAutofillId(), i);
    }

    public static android.view.autofill.AutofillId withoutSession(android.view.autofill.AutofillId autofillId) {
        return new android.view.autofill.AutofillId(autofillId.mFlags & (-5), autofillId.mViewId, (autofillId.mFlags & 2) != 0 ? autofillId.mVirtualLongId : autofillId.mVirtualIntId, 0);
    }

    public int getViewId() {
        return this.mViewId;
    }

    public int getVirtualChildIntId() {
        return this.mVirtualIntId;
    }

    public long getVirtualChildLongId() {
        return this.mVirtualLongId;
    }

    public boolean isVirtualInt() {
        return (this.mFlags & 1) != 0;
    }

    public boolean isVirtualLong() {
        return (this.mFlags & 2) != 0;
    }

    public boolean isNonVirtual() {
        return (isVirtualInt() || isVirtualLong()) ? false : true;
    }

    public boolean hasSession() {
        return (this.mFlags & 4) != 0;
    }

    public int getSessionId() {
        return this.mSessionId;
    }

    public void setSessionId(int i) {
        this.mFlags |= 4;
        this.mSessionId = i;
    }

    public void resetSessionId() {
        this.mFlags &= -5;
        this.mSessionId = 0;
    }

    public int hashCode() {
        return ((((((this.mViewId + 31) * 31) + this.mVirtualIntId) * 31) + ((int) (this.mVirtualLongId ^ (this.mVirtualLongId >>> 32)))) * 31) + this.mSessionId;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.view.autofill.AutofillId autofillId = (android.view.autofill.AutofillId) obj;
        if (this.mViewId == autofillId.mViewId && this.mVirtualIntId == autofillId.mVirtualIntId && this.mVirtualLongId == autofillId.mVirtualLongId && this.mSessionId == autofillId.mSessionId) {
            return true;
        }
        return false;
    }

    public boolean equalsIgnoreSession(android.view.autofill.AutofillId autofillId) {
        if (this == autofillId) {
            return true;
        }
        if (autofillId != null && this.mViewId == autofillId.mViewId && this.mVirtualIntId == autofillId.mVirtualIntId && this.mVirtualLongId == autofillId.mVirtualLongId) {
            return true;
        }
        return false;
    }

    public java.lang.String toString() {
        java.lang.StringBuilder append = new java.lang.StringBuilder().append(this.mViewId);
        if (isVirtualInt()) {
            append.append(com.android.internal.accessibility.common.ShortcutConstants.SERVICES_SEPARATOR).append(this.mVirtualIntId);
        } else if (isVirtualLong()) {
            append.append(com.android.internal.accessibility.common.ShortcutConstants.SERVICES_SEPARATOR).append(this.mVirtualLongId);
        }
        if (hasSession()) {
            append.append('@').append(this.mSessionId);
        }
        return append.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mViewId);
        parcel.writeInt(this.mFlags);
        if (hasSession()) {
            parcel.writeInt(this.mSessionId);
        }
        if (isVirtualInt()) {
            parcel.writeInt(this.mVirtualIntId);
        } else if (isVirtualLong()) {
            parcel.writeLong(this.mVirtualLongId);
        }
    }
}
