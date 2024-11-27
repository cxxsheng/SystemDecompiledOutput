package android.content.pm;

/* loaded from: classes.dex */
public class VerifierDeviceIdentity implements android.os.Parcelable {
    private static final int GROUP_SIZE = 4;
    private static final int LONG_SIZE = 13;
    private static final char SEPARATOR = '-';
    private final long mIdentity;
    private final java.lang.String mIdentityString;
    private static final char[] ENCODE = {android.text.format.DateFormat.CAPITAL_AM_PM, 'B', 'C', 'D', android.text.format.DateFormat.DAY, 'F', 'G', 'H', 'I', 'J', 'K', android.text.format.DateFormat.STANDALONE_MONTH, android.text.format.DateFormat.MONTH, android.telephony.PhoneNumberUtils.WILD, 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '2', '3', '4', '5', '6', '7'};
    public static final android.os.Parcelable.Creator<android.content.pm.VerifierDeviceIdentity> CREATOR = new android.os.Parcelable.Creator<android.content.pm.VerifierDeviceIdentity>() { // from class: android.content.pm.VerifierDeviceIdentity.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.pm.VerifierDeviceIdentity createFromParcel(android.os.Parcel parcel) {
            return new android.content.pm.VerifierDeviceIdentity(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.pm.VerifierDeviceIdentity[] newArray(int i) {
            return new android.content.pm.VerifierDeviceIdentity[i];
        }
    };

    public VerifierDeviceIdentity(long j) {
        this.mIdentity = j;
        this.mIdentityString = encodeBase32(j);
    }

    private VerifierDeviceIdentity(android.os.Parcel parcel) {
        long readLong = parcel.readLong();
        this.mIdentity = readLong;
        this.mIdentityString = encodeBase32(readLong);
    }

    public static android.content.pm.VerifierDeviceIdentity generate() {
        return generate(new java.security.SecureRandom());
    }

    public static android.content.pm.VerifierDeviceIdentity generate(java.util.Random random) {
        return new android.content.pm.VerifierDeviceIdentity(random.nextLong());
    }

    private static final java.lang.String encodeBase32(long j) {
        char[] cArr = ENCODE;
        int i = 16;
        char[] cArr2 = new char[16];
        for (int i2 = 0; i2 < 13; i2++) {
            if (i2 > 0 && i2 % 4 == 1) {
                i--;
                cArr2[i] = SEPARATOR;
            }
            int i3 = (int) (31 & j);
            j >>>= 5;
            i--;
            cArr2[i] = cArr[i3];
        }
        return java.lang.String.valueOf(cArr2);
    }

    private static final long decodeBase32(byte[] bArr) throws java.lang.IllegalArgumentException {
        int i;
        long j = 0;
        int i2 = 0;
        for (byte b : bArr) {
            if (65 <= b && b <= 90) {
                i = b - 65;
            } else if (50 <= b && b <= 55) {
                i = b - 24;
            } else if (b == 45) {
                continue;
            } else if (97 <= b && b <= 122) {
                i = b - 97;
            } else if (b == 48) {
                i = 14;
            } else if (b == 49) {
                i = 8;
            } else {
                throw new java.lang.IllegalArgumentException("base base-32 character: " + ((int) b));
            }
            j = (j << 5) | i;
            i2++;
            if (i2 == 1) {
                if ((i & 15) != i) {
                    throw new java.lang.IllegalArgumentException("illegal start character; will overflow");
                }
            } else if (i2 > 13) {
                throw new java.lang.IllegalArgumentException("too long; should have 13 characters");
            }
        }
        if (i2 != 13) {
            throw new java.lang.IllegalArgumentException("too short; should have 13 characters");
        }
        return j;
    }

    public int hashCode() {
        return (int) this.mIdentity;
    }

    public boolean equals(java.lang.Object obj) {
        return (obj instanceof android.content.pm.VerifierDeviceIdentity) && this.mIdentity == ((android.content.pm.VerifierDeviceIdentity) obj).mIdentity;
    }

    public java.lang.String toString() {
        return this.mIdentityString;
    }

    public static android.content.pm.VerifierDeviceIdentity parse(java.lang.String str) throws java.lang.IllegalArgumentException {
        try {
            return new android.content.pm.VerifierDeviceIdentity(decodeBase32(str.getBytes("US-ASCII")));
        } catch (java.io.UnsupportedEncodingException e) {
            throw new java.lang.IllegalArgumentException("bad base-32 characters in input");
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeLong(this.mIdentity);
    }
}
