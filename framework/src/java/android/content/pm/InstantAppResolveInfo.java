package android.content.pm;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class InstantAppResolveInfo implements android.os.Parcelable {
    private static final java.lang.String SHA_ALGORITHM = "SHA-256";
    private final android.content.pm.InstantAppResolveInfo.InstantAppDigest mDigest;
    private final android.os.Bundle mExtras;
    private final java.util.List<android.content.pm.InstantAppIntentFilter> mFilters;
    private final java.lang.String mPackageName;
    private final boolean mShouldLetInstallerDecide;
    private final long mVersionCode;
    private static final byte[] EMPTY_DIGEST = new byte[0];
    public static final android.os.Parcelable.Creator<android.content.pm.InstantAppResolveInfo> CREATOR = new android.os.Parcelable.Creator<android.content.pm.InstantAppResolveInfo>() { // from class: android.content.pm.InstantAppResolveInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.pm.InstantAppResolveInfo createFromParcel(android.os.Parcel parcel) {
            return new android.content.pm.InstantAppResolveInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.pm.InstantAppResolveInfo[] newArray(int i) {
            return new android.content.pm.InstantAppResolveInfo[i];
        }
    };

    public InstantAppResolveInfo(android.content.pm.InstantAppResolveInfo.InstantAppDigest instantAppDigest, java.lang.String str, java.util.List<android.content.pm.InstantAppIntentFilter> list, int i) {
        this(instantAppDigest, str, list, i, null);
    }

    public InstantAppResolveInfo(android.content.pm.InstantAppResolveInfo.InstantAppDigest instantAppDigest, java.lang.String str, java.util.List<android.content.pm.InstantAppIntentFilter> list, long j, android.os.Bundle bundle) {
        this(instantAppDigest, str, list, j, bundle, false);
    }

    public InstantAppResolveInfo(java.lang.String str, java.lang.String str2, java.util.List<android.content.pm.InstantAppIntentFilter> list) {
        this(new android.content.pm.InstantAppResolveInfo.InstantAppDigest(str), str2, list, -1L, null);
    }

    public InstantAppResolveInfo(android.os.Bundle bundle) {
        this(android.content.pm.InstantAppResolveInfo.InstantAppDigest.UNDEFINED, null, null, -1L, bundle, true);
    }

    private InstantAppResolveInfo(android.content.pm.InstantAppResolveInfo.InstantAppDigest instantAppDigest, java.lang.String str, java.util.List<android.content.pm.InstantAppIntentFilter> list, long j, android.os.Bundle bundle, boolean z) {
        if ((str == null && list != null && list.size() != 0) || (str != null && (list == null || list.size() == 0))) {
            throw new java.lang.IllegalArgumentException();
        }
        this.mDigest = instantAppDigest;
        if (list != null) {
            this.mFilters = new java.util.ArrayList(list.size());
            this.mFilters.addAll(list);
        } else {
            this.mFilters = null;
        }
        this.mPackageName = str;
        this.mVersionCode = j;
        this.mExtras = bundle;
        this.mShouldLetInstallerDecide = z;
    }

    InstantAppResolveInfo(android.os.Parcel parcel) {
        this.mShouldLetInstallerDecide = parcel.readBoolean();
        this.mExtras = parcel.readBundle();
        if (this.mShouldLetInstallerDecide) {
            this.mDigest = android.content.pm.InstantAppResolveInfo.InstantAppDigest.UNDEFINED;
            this.mPackageName = null;
            this.mFilters = java.util.Collections.emptyList();
            this.mVersionCode = -1L;
            return;
        }
        this.mDigest = (android.content.pm.InstantAppResolveInfo.InstantAppDigest) parcel.readParcelable(null, android.content.pm.InstantAppResolveInfo.InstantAppDigest.class);
        this.mPackageName = parcel.readString();
        this.mFilters = new java.util.ArrayList();
        parcel.readTypedList(this.mFilters, android.content.pm.InstantAppIntentFilter.CREATOR);
        this.mVersionCode = parcel.readLong();
    }

    public boolean shouldLetInstallerDecide() {
        return this.mShouldLetInstallerDecide;
    }

    public byte[] getDigestBytes() {
        return this.mDigest.mDigestBytes.length > 0 ? this.mDigest.getDigestBytes()[0] : EMPTY_DIGEST;
    }

    public int getDigestPrefix() {
        return this.mDigest.getDigestPrefix()[0];
    }

    public java.lang.String getPackageName() {
        return this.mPackageName;
    }

    public java.util.List<android.content.pm.InstantAppIntentFilter> getIntentFilters() {
        return this.mFilters;
    }

    @java.lang.Deprecated
    public int getVersionCode() {
        return (int) (this.mVersionCode & (-1));
    }

    public long getLongVersionCode() {
        return this.mVersionCode;
    }

    public android.os.Bundle getExtras() {
        return this.mExtras;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeBoolean(this.mShouldLetInstallerDecide);
        parcel.writeBundle(this.mExtras);
        if (this.mShouldLetInstallerDecide) {
            return;
        }
        parcel.writeParcelable(this.mDigest, i);
        parcel.writeString(this.mPackageName);
        parcel.writeTypedList(this.mFilters);
        parcel.writeLong(this.mVersionCode);
    }

    @android.annotation.SystemApi
    public static final class InstantAppDigest implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.content.pm.InstantAppResolveInfo.InstantAppDigest> CREATOR;
        static final int DIGEST_MASK = -4096;
        public static final android.content.pm.InstantAppResolveInfo.InstantAppDigest UNDEFINED = new android.content.pm.InstantAppResolveInfo.InstantAppDigest(new byte[0][], new int[0]);
        private static java.util.Random sRandom;
        private final byte[][] mDigestBytes;
        private final int[] mDigestPrefix;
        private int[] mDigestPrefixSecure;

        static {
            sRandom = null;
            try {
                sRandom = java.security.SecureRandom.getInstance("SHA1PRNG");
            } catch (java.security.NoSuchAlgorithmException e) {
                sRandom = new java.util.Random();
            }
            CREATOR = new android.os.Parcelable.Creator<android.content.pm.InstantAppResolveInfo.InstantAppDigest>() { // from class: android.content.pm.InstantAppResolveInfo.InstantAppDigest.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public android.content.pm.InstantAppResolveInfo.InstantAppDigest createFromParcel(android.os.Parcel parcel) {
                    if (parcel.readBoolean()) {
                        return android.content.pm.InstantAppResolveInfo.InstantAppDigest.UNDEFINED;
                    }
                    return new android.content.pm.InstantAppResolveInfo.InstantAppDigest(parcel);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public android.content.pm.InstantAppResolveInfo.InstantAppDigest[] newArray(int i) {
                    return new android.content.pm.InstantAppResolveInfo.InstantAppDigest[i];
                }
            };
        }

        public InstantAppDigest(java.lang.String str) {
            this(str, -1);
        }

        public InstantAppDigest(java.lang.String str, int i) {
            if (str == null) {
                throw new java.lang.IllegalArgumentException();
            }
            this.mDigestBytes = generateDigest(str.toLowerCase(java.util.Locale.ENGLISH), i);
            this.mDigestPrefix = new int[this.mDigestBytes.length];
            for (int i2 = 0; i2 < this.mDigestBytes.length; i2++) {
                this.mDigestPrefix[i2] = (((this.mDigestBytes[i2][0] & 255) << 24) | ((this.mDigestBytes[i2][1] & 255) << 16) | ((this.mDigestBytes[i2][2] & 255) << 8) | ((this.mDigestBytes[i2][3] & 255) << 0)) & DIGEST_MASK;
            }
        }

        private InstantAppDigest(byte[][] bArr, int[] iArr) {
            this.mDigestPrefix = iArr;
            this.mDigestBytes = bArr;
        }

        private static byte[][] generateDigest(java.lang.String str, int i) {
            java.util.ArrayList arrayList = new java.util.ArrayList();
            try {
                java.security.MessageDigest messageDigest = java.security.MessageDigest.getInstance("SHA-256");
                if (i <= 0) {
                    arrayList.add(messageDigest.digest(str.getBytes()));
                } else {
                    int lastIndexOf = str.lastIndexOf(46, str.lastIndexOf(46) - 1);
                    if (lastIndexOf < 0) {
                        arrayList.add(messageDigest.digest(str.getBytes()));
                    } else {
                        arrayList.add(messageDigest.digest(str.substring(lastIndexOf + 1, str.length()).getBytes()));
                        for (int i2 = 1; lastIndexOf >= 0 && i2 < i; i2++) {
                            lastIndexOf = str.lastIndexOf(46, lastIndexOf - 1);
                            arrayList.add(messageDigest.digest(str.substring(lastIndexOf + 1, str.length()).getBytes()));
                        }
                    }
                }
                return (byte[][]) arrayList.toArray(new byte[arrayList.size()][]);
            } catch (java.security.NoSuchAlgorithmException e) {
                throw new java.lang.IllegalStateException("could not find digest algorithm");
            }
        }

        InstantAppDigest(android.os.Parcel parcel) {
            int readInt = parcel.readInt();
            if (readInt == -1) {
                this.mDigestBytes = null;
            } else {
                this.mDigestBytes = new byte[readInt][];
                for (int i = 0; i < readInt; i++) {
                    this.mDigestBytes[i] = parcel.createByteArray();
                }
            }
            this.mDigestPrefix = parcel.createIntArray();
            this.mDigestPrefixSecure = parcel.createIntArray();
        }

        public byte[][] getDigestBytes() {
            return this.mDigestBytes;
        }

        public int[] getDigestPrefix() {
            return this.mDigestPrefix;
        }

        public int[] getDigestPrefixSecure() {
            if (this == UNDEFINED) {
                return getDigestPrefix();
            }
            if (this.mDigestPrefixSecure == null) {
                int length = getDigestPrefix().length;
                int nextInt = length + 10 + sRandom.nextInt(10);
                this.mDigestPrefixSecure = java.util.Arrays.copyOf(getDigestPrefix(), nextInt);
                while (length < nextInt) {
                    this.mDigestPrefixSecure[length] = sRandom.nextInt() & DIGEST_MASK;
                    length++;
                }
                java.util.Arrays.sort(this.mDigestPrefixSecure);
            }
            return this.mDigestPrefixSecure;
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            boolean z = this == UNDEFINED;
            parcel.writeBoolean(z);
            if (z) {
                return;
            }
            if (this.mDigestBytes == null) {
                parcel.writeInt(-1);
            } else {
                parcel.writeInt(this.mDigestBytes.length);
                for (int i2 = 0; i2 < this.mDigestBytes.length; i2++) {
                    parcel.writeByteArray(this.mDigestBytes[i2]);
                }
            }
            parcel.writeIntArray(this.mDigestPrefix);
            parcel.writeIntArray(this.mDigestPrefixSecure);
        }
    }
}
