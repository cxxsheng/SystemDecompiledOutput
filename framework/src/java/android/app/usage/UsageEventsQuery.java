package android.app.usage;

/* loaded from: classes.dex */
public final class UsageEventsQuery implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.app.usage.UsageEventsQuery> CREATOR = new android.os.Parcelable.Creator<android.app.usage.UsageEventsQuery>() { // from class: android.app.usage.UsageEventsQuery.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.usage.UsageEventsQuery createFromParcel(android.os.Parcel parcel) {
            return new android.app.usage.UsageEventsQuery(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.usage.UsageEventsQuery[] newArray(int i) {
            return new android.app.usage.UsageEventsQuery[i];
        }
    };
    private final long mBeginTimeMillis;
    private final long mEndTimeMillis;
    private final int[] mEventTypes;
    private final java.lang.String[] mPackageNames;
    private final int mUserId;

    private UsageEventsQuery(android.app.usage.UsageEventsQuery.Builder builder) {
        this.mBeginTimeMillis = builder.mBeginTimeMillis;
        this.mEndTimeMillis = builder.mEndTimeMillis;
        this.mEventTypes = com.android.internal.util.ArrayUtils.convertToIntArray((android.util.ArraySet<java.lang.Integer>) builder.mEventTypes);
        this.mUserId = builder.mUserId;
        this.mPackageNames = (java.lang.String[]) builder.mPackageNames.toArray(new java.lang.String[builder.mPackageNames.size()]);
    }

    private UsageEventsQuery(android.os.Parcel parcel) {
        this.mBeginTimeMillis = parcel.readLong();
        this.mEndTimeMillis = parcel.readLong();
        this.mEventTypes = new int[parcel.readInt()];
        parcel.readIntArray(this.mEventTypes);
        this.mUserId = parcel.readInt();
        this.mPackageNames = new java.lang.String[parcel.readInt()];
        parcel.readStringArray(this.mPackageNames);
    }

    public long getBeginTimeMillis() {
        return this.mBeginTimeMillis;
    }

    public long getEndTimeMillis() {
        return this.mEndTimeMillis;
    }

    public int[] getEventTypes() {
        return java.util.Arrays.copyOf(this.mEventTypes, this.mEventTypes.length);
    }

    public int getUserId() {
        return this.mUserId;
    }

    public java.util.Set<java.lang.String> getPackageNames() {
        if (com.android.internal.util.ArrayUtils.isEmpty(this.mPackageNames)) {
            return java.util.Collections.emptySet();
        }
        java.util.HashSet hashSet = new java.util.HashSet();
        for (java.lang.String str : this.mPackageNames) {
            hashSet.add(str);
        }
        return hashSet;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeLong(this.mBeginTimeMillis);
        parcel.writeLong(this.mEndTimeMillis);
        parcel.writeInt(this.mEventTypes.length);
        parcel.writeIntArray(this.mEventTypes);
        parcel.writeInt(this.mUserId);
        parcel.writeInt(this.mPackageNames.length);
        parcel.writeStringArray(this.mPackageNames);
    }

    public static final class Builder {
        private final long mBeginTimeMillis;
        private final long mEndTimeMillis;
        private final android.util.ArraySet<java.lang.Integer> mEventTypes = new android.util.ArraySet<>();
        private int mUserId = -10000;
        private final android.util.ArraySet<java.lang.String> mPackageNames = new android.util.ArraySet<>();

        public Builder(long j, long j2) {
            if (j < 0 || j2 < j) {
                throw new java.lang.IllegalArgumentException("Invalid period");
            }
            this.mBeginTimeMillis = j;
            this.mEndTimeMillis = j2;
        }

        public android.app.usage.UsageEventsQuery build() {
            return new android.app.usage.UsageEventsQuery(this);
        }

        public android.app.usage.UsageEventsQuery.Builder setEventTypes(int... iArr) {
            if (iArr == null || iArr.length == 0) {
                throw new java.lang.NullPointerException("eventTypes is null or empty");
            }
            this.mEventTypes.clear();
            for (int i : iArr) {
                if (i < 0 || i > 31) {
                    throw new java.lang.IllegalArgumentException("Invalid usage event type: " + i);
                }
                this.mEventTypes.add(java.lang.Integer.valueOf(i));
            }
            return this;
        }

        public android.app.usage.UsageEventsQuery.Builder setUserId(int i) {
            this.mUserId = i;
            return this;
        }

        public android.app.usage.UsageEventsQuery.Builder setPackageNames(java.lang.String... strArr) {
            if (strArr == null || strArr.length == 0) {
                throw new java.lang.NullPointerException("pkgNames is null or empty");
            }
            this.mPackageNames.clear();
            for (int i = 0; i < strArr.length; i++) {
                if (!android.text.TextUtils.isEmpty(strArr[i])) {
                    this.mPackageNames.add(strArr[i]);
                }
            }
            return this;
        }
    }
}
