package android.app.admin;

/* loaded from: classes.dex */
public final class PasswordMetrics implements android.os.Parcelable {
    private static final int CHAR_DIGIT = 2;
    private static final int CHAR_LOWER_CASE = 0;
    private static final int CHAR_SYMBOL = 3;
    private static final int CHAR_UPPER_CASE = 1;
    public static final android.os.Parcelable.Creator<android.app.admin.PasswordMetrics> CREATOR = new android.os.Parcelable.Creator<android.app.admin.PasswordMetrics>() { // from class: android.app.admin.PasswordMetrics.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.admin.PasswordMetrics createFromParcel(android.os.Parcel parcel) {
            return new android.app.admin.PasswordMetrics(parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.admin.PasswordMetrics[] newArray(int i) {
            return new android.app.admin.PasswordMetrics[i];
        }
    };
    public static final int MAX_ALLOWED_SEQUENCE = 3;
    private static final java.lang.String TAG = "PasswordMetrics";
    public int credType;
    public int length;
    public int letters;
    public int lowerCase;
    public int nonLetter;
    public int nonNumeric;
    public int numeric;
    public int seqLength;
    public int symbols;
    public int upperCase;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    private @interface CharacterCatagory {
    }

    public PasswordMetrics(int i) {
        this.length = 0;
        this.letters = 0;
        this.upperCase = 0;
        this.lowerCase = 0;
        this.numeric = 0;
        this.symbols = 0;
        this.nonLetter = 0;
        this.nonNumeric = 0;
        this.seqLength = Integer.MAX_VALUE;
        this.credType = i;
    }

    public PasswordMetrics(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10) {
        this.length = 0;
        this.letters = 0;
        this.upperCase = 0;
        this.lowerCase = 0;
        this.numeric = 0;
        this.symbols = 0;
        this.nonLetter = 0;
        this.nonNumeric = 0;
        this.seqLength = Integer.MAX_VALUE;
        this.credType = i;
        this.length = i2;
        this.letters = i3;
        this.upperCase = i4;
        this.lowerCase = i5;
        this.numeric = i6;
        this.symbols = i7;
        this.nonLetter = i8;
        this.nonNumeric = i9;
        this.seqLength = i10;
    }

    private PasswordMetrics(android.app.admin.PasswordMetrics passwordMetrics) {
        this(passwordMetrics.credType, passwordMetrics.length, passwordMetrics.letters, passwordMetrics.upperCase, passwordMetrics.lowerCase, passwordMetrics.numeric, passwordMetrics.symbols, passwordMetrics.nonLetter, passwordMetrics.nonNumeric, passwordMetrics.seqLength);
    }

    public static int sanitizeComplexityLevel(int i) {
        switch (i) {
            case 0:
            case 65536:
            case 196608:
            case 327680:
                return i;
            default:
                android.util.Log.w(TAG, "Invalid password complexity used: " + i);
                return 0;
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.credType);
        parcel.writeInt(this.length);
        parcel.writeInt(this.letters);
        parcel.writeInt(this.upperCase);
        parcel.writeInt(this.lowerCase);
        parcel.writeInt(this.numeric);
        parcel.writeInt(this.symbols);
        parcel.writeInt(this.nonLetter);
        parcel.writeInt(this.nonNumeric);
        parcel.writeInt(this.seqLength);
    }

    public static android.app.admin.PasswordMetrics computeForCredential(com.android.internal.widget.LockscreenCredential lockscreenCredential) {
        if (lockscreenCredential.isPassword() || lockscreenCredential.isPin()) {
            return computeForPasswordOrPin(lockscreenCredential.getCredential(), lockscreenCredential.isPin());
        }
        if (lockscreenCredential.isPattern()) {
            android.app.admin.PasswordMetrics passwordMetrics = new android.app.admin.PasswordMetrics(1);
            passwordMetrics.length = lockscreenCredential.size();
            return passwordMetrics;
        }
        if (lockscreenCredential.isNone()) {
            return new android.app.admin.PasswordMetrics(-1);
        }
        throw new java.lang.IllegalArgumentException("Unknown credential type " + lockscreenCredential.getType());
    }

    private static android.app.admin.PasswordMetrics computeForPasswordOrPin(byte[] bArr, boolean z) {
        int length = bArr.length;
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        int i6 = 0;
        int i7 = 0;
        for (byte b : bArr) {
            switch (categoryChar((char) b)) {
                case 0:
                    i++;
                    i3++;
                    i7++;
                    break;
                case 1:
                    i++;
                    i2++;
                    i7++;
                    break;
                case 2:
                    i4++;
                    i6++;
                    break;
                case 3:
                    i5++;
                    i6++;
                    i7++;
                    break;
            }
        }
        return new android.app.admin.PasswordMetrics(z ? 3 : 4, length, i, i2, i3, i4, i5, i6, i7, maxLengthSequence(bArr));
    }

    public static int maxLengthSequence(byte[] bArr) {
        if (bArr.length == 0) {
            return 0;
        }
        char c = (char) bArr[0];
        int categoryChar = categoryChar(c);
        int i = 0;
        int i2 = 0;
        boolean z = false;
        int i3 = 0;
        int i4 = 1;
        while (i4 < bArr.length) {
            char c2 = (char) bArr[i4];
            int categoryChar2 = categoryChar(c2);
            int i5 = c2 - c;
            if (categoryChar2 != categoryChar || java.lang.Math.abs(i5) > maxDiffCategory(categoryChar)) {
                i = java.lang.Math.max(i, i4 - i2);
                z = false;
                i2 = i4;
                categoryChar = categoryChar2;
            } else {
                if (z && i5 != i3) {
                    i = java.lang.Math.max(i, i4 - i2);
                    i2 = i4 - 1;
                }
                i3 = i5;
                z = true;
            }
            i4++;
            c = c2;
        }
        return java.lang.Math.max(i, bArr.length - i2);
    }

    private static int categoryChar(char c) {
        if ('a' <= c && c <= 'z') {
            return 0;
        }
        if ('A' > c || c > 'Z') {
            return ('0' > c || c > '9') ? 3 : 2;
        }
        return 1;
    }

    private static int maxDiffCategory(int i) {
        switch (i) {
            case 0:
            case 1:
                return 1;
            case 2:
                return 10;
            default:
                return 0;
        }
    }

    public static android.app.admin.PasswordMetrics merge(java.util.List<android.app.admin.PasswordMetrics> list) {
        android.app.admin.PasswordMetrics passwordMetrics = new android.app.admin.PasswordMetrics(-1);
        java.util.Iterator<android.app.admin.PasswordMetrics> it = list.iterator();
        while (it.hasNext()) {
            passwordMetrics.maxWith(it.next());
        }
        return passwordMetrics;
    }

    public void maxWith(android.app.admin.PasswordMetrics passwordMetrics) {
        this.credType = java.lang.Math.max(this.credType, passwordMetrics.credType);
        if (this.credType != 4 && this.credType != 3) {
            return;
        }
        this.length = java.lang.Math.max(this.length, passwordMetrics.length);
        this.letters = java.lang.Math.max(this.letters, passwordMetrics.letters);
        this.upperCase = java.lang.Math.max(this.upperCase, passwordMetrics.upperCase);
        this.lowerCase = java.lang.Math.max(this.lowerCase, passwordMetrics.lowerCase);
        this.numeric = java.lang.Math.max(this.numeric, passwordMetrics.numeric);
        this.symbols = java.lang.Math.max(this.symbols, passwordMetrics.symbols);
        this.nonLetter = java.lang.Math.max(this.nonLetter, passwordMetrics.nonLetter);
        this.nonNumeric = java.lang.Math.max(this.nonNumeric, passwordMetrics.nonNumeric);
        this.seqLength = java.lang.Math.min(this.seqLength, passwordMetrics.seqLength);
    }

    public static int complexityLevelToMinQuality(int i) {
        switch (i) {
            case 65536:
                return 65536;
            case 196608:
            case 327680:
                return 196608;
            default:
                return 0;
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    private static abstract class ComplexityBucket {
        public static final android.app.admin.PasswordMetrics.ComplexityBucket BUCKET_HIGH;
        public static final android.app.admin.PasswordMetrics.ComplexityBucket BUCKET_NONE;
        int mComplexityLevel;
        public static final android.app.admin.PasswordMetrics.ComplexityBucket BUCKET_MEDIUM = new android.app.admin.PasswordMetrics.ComplexityBucket.AnonymousClass2("BUCKET_MEDIUM", 1, 196608);
        public static final android.app.admin.PasswordMetrics.ComplexityBucket BUCKET_LOW = new android.app.admin.PasswordMetrics.ComplexityBucket.AnonymousClass3("BUCKET_LOW", 2, 65536);
        private static final /* synthetic */ android.app.admin.PasswordMetrics.ComplexityBucket[] $VALUES = $values();

        abstract boolean allowsCredType(int i);

        abstract boolean canHaveSequence();

        abstract int getMinimumLength(boolean z);

        private static /* synthetic */ android.app.admin.PasswordMetrics.ComplexityBucket[] $values() {
            return new android.app.admin.PasswordMetrics.ComplexityBucket[]{BUCKET_HIGH, BUCKET_MEDIUM, BUCKET_LOW, BUCKET_NONE};
        }

        public static android.app.admin.PasswordMetrics.ComplexityBucket valueOf(java.lang.String str) {
            return (android.app.admin.PasswordMetrics.ComplexityBucket) java.lang.Enum.valueOf(android.app.admin.PasswordMetrics.ComplexityBucket.class, str);
        }

        public static android.app.admin.PasswordMetrics.ComplexityBucket[] values() {
            return (android.app.admin.PasswordMetrics.ComplexityBucket[]) $VALUES.clone();
        }

        /* renamed from: android.app.admin.PasswordMetrics$ComplexityBucket$1, reason: invalid class name */
        enum AnonymousClass1 extends android.app.admin.PasswordMetrics.ComplexityBucket {
            private AnonymousClass1(java.lang.String str, int i, int i2) {
                super(str, i, i2);
            }

            @Override // android.app.admin.PasswordMetrics.ComplexityBucket
            boolean canHaveSequence() {
                return false;
            }

            @Override // android.app.admin.PasswordMetrics.ComplexityBucket
            int getMinimumLength(boolean z) {
                return z ? 6 : 8;
            }

            @Override // android.app.admin.PasswordMetrics.ComplexityBucket
            boolean allowsCredType(int i) {
                return i == 4 || i == 3;
            }
        }

        static {
            int i = 0;
            BUCKET_HIGH = new android.app.admin.PasswordMetrics.ComplexityBucket.AnonymousClass1("BUCKET_HIGH", i, 327680);
            BUCKET_NONE = new android.app.admin.PasswordMetrics.ComplexityBucket.AnonymousClass4("BUCKET_NONE", 3, i);
        }

        /* renamed from: android.app.admin.PasswordMetrics$ComplexityBucket$2, reason: invalid class name */
        enum AnonymousClass2 extends android.app.admin.PasswordMetrics.ComplexityBucket {
            private AnonymousClass2(java.lang.String str, int i, int i2) {
                super(str, i, i2);
            }

            @Override // android.app.admin.PasswordMetrics.ComplexityBucket
            boolean canHaveSequence() {
                return false;
            }

            @Override // android.app.admin.PasswordMetrics.ComplexityBucket
            int getMinimumLength(boolean z) {
                return 4;
            }

            @Override // android.app.admin.PasswordMetrics.ComplexityBucket
            boolean allowsCredType(int i) {
                return i == 4 || i == 3;
            }
        }

        /* renamed from: android.app.admin.PasswordMetrics$ComplexityBucket$3, reason: invalid class name */
        enum AnonymousClass3 extends android.app.admin.PasswordMetrics.ComplexityBucket {
            private AnonymousClass3(java.lang.String str, int i, int i2) {
                super(str, i, i2);
            }

            @Override // android.app.admin.PasswordMetrics.ComplexityBucket
            boolean canHaveSequence() {
                return true;
            }

            @Override // android.app.admin.PasswordMetrics.ComplexityBucket
            int getMinimumLength(boolean z) {
                return 0;
            }

            @Override // android.app.admin.PasswordMetrics.ComplexityBucket
            boolean allowsCredType(int i) {
                return i != -1;
            }
        }

        /* renamed from: android.app.admin.PasswordMetrics$ComplexityBucket$4, reason: invalid class name */
        enum AnonymousClass4 extends android.app.admin.PasswordMetrics.ComplexityBucket {
            private AnonymousClass4(java.lang.String str, int i, int i2) {
                super(str, i, i2);
            }

            @Override // android.app.admin.PasswordMetrics.ComplexityBucket
            boolean canHaveSequence() {
                return true;
            }

            @Override // android.app.admin.PasswordMetrics.ComplexityBucket
            int getMinimumLength(boolean z) {
                return 0;
            }

            @Override // android.app.admin.PasswordMetrics.ComplexityBucket
            boolean allowsCredType(int i) {
                return true;
            }
        }

        private ComplexityBucket(java.lang.String str, int i, int i2) {
            this.mComplexityLevel = i2;
        }

        static android.app.admin.PasswordMetrics.ComplexityBucket forComplexity(int i) {
            for (android.app.admin.PasswordMetrics.ComplexityBucket complexityBucket : values()) {
                if (complexityBucket.mComplexityLevel == i) {
                    return complexityBucket;
                }
            }
            throw new java.lang.IllegalArgumentException("Invalid complexity level: " + i);
        }
    }

    private boolean satisfiesBucket(android.app.admin.PasswordMetrics.ComplexityBucket complexityBucket) {
        if (!complexityBucket.allowsCredType(this.credType)) {
            return false;
        }
        if (this.credType != 4 && this.credType != 3) {
            return true;
        }
        if (complexityBucket.canHaveSequence() || this.seqLength <= 3) {
            return this.length >= complexityBucket.getMinimumLength(this.nonNumeric > 0);
        }
        return false;
    }

    public int determineComplexity() {
        for (android.app.admin.PasswordMetrics.ComplexityBucket complexityBucket : android.app.admin.PasswordMetrics.ComplexityBucket.values()) {
            if (satisfiesBucket(complexityBucket)) {
                return complexityBucket.mComplexityLevel;
            }
        }
        throw new java.lang.IllegalStateException("Failed to figure out complexity for a given metrics");
    }

    public static java.util.List<com.android.internal.widget.PasswordValidationError> validateCredential(android.app.admin.PasswordMetrics passwordMetrics, int i, com.android.internal.widget.LockscreenCredential lockscreenCredential) {
        if (lockscreenCredential.hasInvalidChars()) {
            return java.util.Collections.singletonList(new com.android.internal.widget.PasswordValidationError(2, 0));
        }
        return validatePasswordMetrics(passwordMetrics, i, computeForCredential(lockscreenCredential));
    }

    public static java.util.List<com.android.internal.widget.PasswordValidationError> validatePasswordMetrics(android.app.admin.PasswordMetrics passwordMetrics, int i, android.app.admin.PasswordMetrics passwordMetrics2) {
        android.app.admin.PasswordMetrics.ComplexityBucket forComplexity = android.app.admin.PasswordMetrics.ComplexityBucket.forComplexity(i);
        if (passwordMetrics2.credType < passwordMetrics.credType || !forComplexity.allowsCredType(passwordMetrics2.credType)) {
            return java.util.Collections.singletonList(new com.android.internal.widget.PasswordValidationError(1, 0));
        }
        if (passwordMetrics2.credType == 1) {
            if (passwordMetrics2.length != 0 && passwordMetrics2.length < 4) {
                return java.util.Collections.singletonList(new com.android.internal.widget.PasswordValidationError(3, 4));
            }
            return java.util.Collections.emptyList();
        }
        if (passwordMetrics2.credType == -1) {
            return java.util.Collections.emptyList();
        }
        if (passwordMetrics2.credType == 3 && passwordMetrics2.nonNumeric > 0) {
            return java.util.Collections.singletonList(new com.android.internal.widget.PasswordValidationError(2, 0));
        }
        java.util.ArrayList arrayList = new java.util.ArrayList();
        if (passwordMetrics2.length > 64) {
            arrayList.add(new com.android.internal.widget.PasswordValidationError(5, 64));
        }
        android.app.admin.PasswordMetrics applyComplexity = applyComplexity(passwordMetrics, passwordMetrics2.credType == 3, forComplexity);
        applyComplexity.length = java.lang.Math.min(64, java.lang.Math.max(applyComplexity.length, 4));
        applyComplexity.removeOverlapping();
        comparePasswordMetrics(applyComplexity, forComplexity, passwordMetrics2, arrayList);
        return arrayList;
    }

    private static void comparePasswordMetrics(android.app.admin.PasswordMetrics passwordMetrics, android.app.admin.PasswordMetrics.ComplexityBucket complexityBucket, android.app.admin.PasswordMetrics passwordMetrics2, java.util.ArrayList<com.android.internal.widget.PasswordValidationError> arrayList) {
        int minimumLength;
        if (passwordMetrics2.length < passwordMetrics.length) {
            arrayList.add(new com.android.internal.widget.PasswordValidationError(3, passwordMetrics.length));
        }
        if (passwordMetrics2.nonNumeric == 0 && passwordMetrics.nonNumeric == 0 && passwordMetrics.letters == 0 && passwordMetrics.lowerCase == 0 && passwordMetrics.upperCase == 0 && passwordMetrics.symbols == 0 && (minimumLength = complexityBucket.getMinimumLength(false)) > passwordMetrics.length && minimumLength > passwordMetrics.numeric && passwordMetrics2.length < minimumLength) {
            arrayList.add(new com.android.internal.widget.PasswordValidationError(4, minimumLength));
        }
        if (passwordMetrics2.letters < passwordMetrics.letters) {
            arrayList.add(new com.android.internal.widget.PasswordValidationError(7, passwordMetrics.letters));
        }
        if (passwordMetrics2.upperCase < passwordMetrics.upperCase) {
            arrayList.add(new com.android.internal.widget.PasswordValidationError(8, passwordMetrics.upperCase));
        }
        if (passwordMetrics2.lowerCase < passwordMetrics.lowerCase) {
            arrayList.add(new com.android.internal.widget.PasswordValidationError(9, passwordMetrics.lowerCase));
        }
        if (passwordMetrics2.numeric < passwordMetrics.numeric) {
            arrayList.add(new com.android.internal.widget.PasswordValidationError(10, passwordMetrics.numeric));
        }
        if (passwordMetrics2.symbols < passwordMetrics.symbols) {
            arrayList.add(new com.android.internal.widget.PasswordValidationError(11, passwordMetrics.symbols));
        }
        if (passwordMetrics2.nonLetter < passwordMetrics.nonLetter) {
            arrayList.add(new com.android.internal.widget.PasswordValidationError(12, passwordMetrics.nonLetter));
        }
        if (passwordMetrics2.nonNumeric < passwordMetrics.nonNumeric) {
            arrayList.add(new com.android.internal.widget.PasswordValidationError(13, passwordMetrics.nonNumeric));
        }
        if (passwordMetrics2.seqLength > passwordMetrics.seqLength) {
            arrayList.add(new com.android.internal.widget.PasswordValidationError(6, 0));
        }
    }

    private void removeOverlapping() {
        int i = this.upperCase + this.lowerCase;
        int i2 = this.numeric + this.symbols;
        int max = java.lang.Math.max(this.letters, i);
        int i3 = this.symbols + max;
        int max2 = java.lang.Math.max(max + java.lang.Math.max(this.nonLetter, i2), this.numeric + java.lang.Math.max(this.nonNumeric, i3));
        if (i >= this.letters) {
            this.letters = 0;
        }
        if (i2 >= this.nonLetter) {
            this.nonLetter = 0;
        }
        if (i3 >= this.nonNumeric) {
            this.nonNumeric = 0;
        }
        if (max2 >= this.length) {
            this.length = 0;
        }
    }

    public static android.app.admin.PasswordMetrics applyComplexity(android.app.admin.PasswordMetrics passwordMetrics, boolean z, int i) {
        return applyComplexity(passwordMetrics, z, android.app.admin.PasswordMetrics.ComplexityBucket.forComplexity(i));
    }

    private static android.app.admin.PasswordMetrics applyComplexity(android.app.admin.PasswordMetrics passwordMetrics, boolean z, android.app.admin.PasswordMetrics.ComplexityBucket complexityBucket) {
        android.app.admin.PasswordMetrics passwordMetrics2 = new android.app.admin.PasswordMetrics(passwordMetrics);
        if (!complexityBucket.canHaveSequence()) {
            passwordMetrics2.seqLength = java.lang.Math.min(passwordMetrics2.seqLength, 3);
        }
        passwordMetrics2.length = java.lang.Math.max(passwordMetrics2.length, complexityBucket.getMinimumLength(!z));
        return passwordMetrics2;
    }

    public static boolean isNumericOnly(java.lang.String str) {
        if (str.length() == 0) {
            return false;
        }
        for (int i = 0; i < str.length(); i++) {
            if (categoryChar(str.charAt(i)) != 2) {
                return false;
            }
        }
        return true;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.app.admin.PasswordMetrics passwordMetrics = (android.app.admin.PasswordMetrics) obj;
        if (this.credType == passwordMetrics.credType && this.length == passwordMetrics.length && this.letters == passwordMetrics.letters && this.upperCase == passwordMetrics.upperCase && this.lowerCase == passwordMetrics.lowerCase && this.numeric == passwordMetrics.numeric && this.symbols == passwordMetrics.symbols && this.nonLetter == passwordMetrics.nonLetter && this.nonNumeric == passwordMetrics.nonNumeric && this.seqLength == passwordMetrics.seqLength) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(this.credType), java.lang.Integer.valueOf(this.length), java.lang.Integer.valueOf(this.letters), java.lang.Integer.valueOf(this.upperCase), java.lang.Integer.valueOf(this.lowerCase), java.lang.Integer.valueOf(this.numeric), java.lang.Integer.valueOf(this.symbols), java.lang.Integer.valueOf(this.nonLetter), java.lang.Integer.valueOf(this.nonNumeric), java.lang.Integer.valueOf(this.seqLength));
    }
}
