package android.security.keymaster;

/* loaded from: classes3.dex */
public class KeymasterArguments implements android.os.Parcelable {
    public static final long UINT32_MAX_VALUE = 4294967295L;
    private static final long UINT32_RANGE = 4294967296L;
    private java.util.List<android.security.keymaster.KeymasterArgument> mArguments;
    private static final java.math.BigInteger UINT64_RANGE = java.math.BigInteger.ONE.shiftLeft(64);
    public static final java.math.BigInteger UINT64_MAX_VALUE = UINT64_RANGE.subtract(java.math.BigInteger.ONE);
    public static final android.os.Parcelable.Creator<android.security.keymaster.KeymasterArguments> CREATOR = new android.os.Parcelable.Creator<android.security.keymaster.KeymasterArguments>() { // from class: android.security.keymaster.KeymasterArguments.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.security.keymaster.KeymasterArguments createFromParcel(android.os.Parcel parcel) {
            return new android.security.keymaster.KeymasterArguments(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.security.keymaster.KeymasterArguments[] newArray(int i) {
            return new android.security.keymaster.KeymasterArguments[i];
        }
    };

    public KeymasterArguments() {
        this.mArguments = new java.util.ArrayList();
    }

    private KeymasterArguments(android.os.Parcel parcel) {
        this.mArguments = parcel.createTypedArrayList(android.security.keymaster.KeymasterArgument.CREATOR);
    }

    public void addEnum(int i, int i2) {
        int tagType = android.security.keymaster.KeymasterDefs.getTagType(i);
        if (tagType != 268435456 && tagType != 536870912) {
            throw new java.lang.IllegalArgumentException("Not an enum or repeating enum tag: " + i);
        }
        addEnumTag(i, i2);
    }

    public void addEnums(int i, int... iArr) {
        if (android.security.keymaster.KeymasterDefs.getTagType(i) != 536870912) {
            throw new java.lang.IllegalArgumentException("Not a repeating enum tag: " + i);
        }
        for (int i2 : iArr) {
            addEnumTag(i, i2);
        }
    }

    public int getEnum(int i, int i2) {
        if (android.security.keymaster.KeymasterDefs.getTagType(i) != 268435456) {
            throw new java.lang.IllegalArgumentException("Not an enum tag: " + i);
        }
        android.security.keymaster.KeymasterArgument argumentByTag = getArgumentByTag(i);
        if (argumentByTag == null) {
            return i2;
        }
        return getEnumTagValue(argumentByTag);
    }

    public java.util.List<java.lang.Integer> getEnums(int i) {
        if (android.security.keymaster.KeymasterDefs.getTagType(i) != 536870912) {
            throw new java.lang.IllegalArgumentException("Not a repeating enum tag: " + i);
        }
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (android.security.keymaster.KeymasterArgument keymasterArgument : this.mArguments) {
            if (keymasterArgument.tag == i) {
                arrayList.add(java.lang.Integer.valueOf(getEnumTagValue(keymasterArgument)));
            }
        }
        return arrayList;
    }

    private void addEnumTag(int i, int i2) {
        this.mArguments.add(new android.security.keymaster.KeymasterIntArgument(i, i2));
    }

    private int getEnumTagValue(android.security.keymaster.KeymasterArgument keymasterArgument) {
        return ((android.security.keymaster.KeymasterIntArgument) keymasterArgument).value;
    }

    public void addUnsignedInt(int i, long j) {
        int tagType = android.security.keymaster.KeymasterDefs.getTagType(i);
        if (tagType != 805306368 && tagType != 1073741824) {
            throw new java.lang.IllegalArgumentException("Not an int or repeating int tag: " + i);
        }
        if (j < 0 || j > 4294967295L) {
            throw new java.lang.IllegalArgumentException("Int tag value out of range: " + j);
        }
        this.mArguments.add(new android.security.keymaster.KeymasterIntArgument(i, (int) j));
    }

    public long getUnsignedInt(int i, long j) {
        if (android.security.keymaster.KeymasterDefs.getTagType(i) != 805306368) {
            throw new java.lang.IllegalArgumentException("Not an int tag: " + i);
        }
        if (getArgumentByTag(i) == null) {
            return j;
        }
        return ((android.security.keymaster.KeymasterIntArgument) r3).value & 4294967295L;
    }

    public void addUnsignedLong(int i, java.math.BigInteger bigInteger) {
        int tagType = android.security.keymaster.KeymasterDefs.getTagType(i);
        if (tagType != 1342177280 && tagType != -1610612736) {
            throw new java.lang.IllegalArgumentException("Not a long or repeating long tag: " + i);
        }
        addLongTag(i, bigInteger);
    }

    public java.util.List<java.math.BigInteger> getUnsignedLongs(int i) {
        if (android.security.keymaster.KeymasterDefs.getTagType(i) != -1610612736) {
            throw new java.lang.IllegalArgumentException("Tag is not a repeating long: " + i);
        }
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (android.security.keymaster.KeymasterArgument keymasterArgument : this.mArguments) {
            if (keymasterArgument.tag == i) {
                arrayList.add(getLongTagValue(keymasterArgument));
            }
        }
        return arrayList;
    }

    private void addLongTag(int i, java.math.BigInteger bigInteger) {
        if (bigInteger.signum() == -1 || bigInteger.compareTo(UINT64_MAX_VALUE) > 0) {
            throw new java.lang.IllegalArgumentException("Long tag value out of range: " + bigInteger);
        }
        this.mArguments.add(new android.security.keymaster.KeymasterLongArgument(i, bigInteger.longValue()));
    }

    private java.math.BigInteger getLongTagValue(android.security.keymaster.KeymasterArgument keymasterArgument) {
        return toUint64(((android.security.keymaster.KeymasterLongArgument) keymasterArgument).value);
    }

    public void addBoolean(int i) {
        if (android.security.keymaster.KeymasterDefs.getTagType(i) != 1879048192) {
            throw new java.lang.IllegalArgumentException("Not a boolean tag: " + i);
        }
        this.mArguments.add(new android.security.keymaster.KeymasterBooleanArgument(i));
    }

    public boolean getBoolean(int i) {
        if (android.security.keymaster.KeymasterDefs.getTagType(i) != 1879048192) {
            throw new java.lang.IllegalArgumentException("Not a boolean tag: " + i);
        }
        if (getArgumentByTag(i) == null) {
            return false;
        }
        return true;
    }

    public void addBytes(int i, byte[] bArr) {
        if (android.security.keymaster.KeymasterDefs.getTagType(i) != -1879048192) {
            throw new java.lang.IllegalArgumentException("Not a bytes tag: " + i);
        }
        if (bArr == null) {
            throw new java.lang.NullPointerException("value == nulll");
        }
        this.mArguments.add(new android.security.keymaster.KeymasterBlobArgument(i, bArr));
    }

    public byte[] getBytes(int i, byte[] bArr) {
        if (android.security.keymaster.KeymasterDefs.getTagType(i) != -1879048192) {
            throw new java.lang.IllegalArgumentException("Not a bytes tag: " + i);
        }
        android.security.keymaster.KeymasterArgument argumentByTag = getArgumentByTag(i);
        if (argumentByTag == null) {
            return bArr;
        }
        return ((android.security.keymaster.KeymasterBlobArgument) argumentByTag).blob;
    }

    public void addDate(int i, java.util.Date date) {
        if (android.security.keymaster.KeymasterDefs.getTagType(i) != 1610612736) {
            throw new java.lang.IllegalArgumentException("Not a date tag: " + i);
        }
        if (date == null) {
            throw new java.lang.NullPointerException("value == nulll");
        }
        if (date.getTime() < 0) {
            throw new java.lang.IllegalArgumentException("Date tag value out of range: " + date);
        }
        this.mArguments.add(new android.security.keymaster.KeymasterDateArgument(i, date));
    }

    public void addDateIfNotNull(int i, java.util.Date date) {
        if (android.security.keymaster.KeymasterDefs.getTagType(i) != 1610612736) {
            throw new java.lang.IllegalArgumentException("Not a date tag: " + i);
        }
        if (date != null) {
            addDate(i, date);
        }
    }

    public java.util.Date getDate(int i, java.util.Date date) {
        if (android.security.keymaster.KeymasterDefs.getTagType(i) != 1610612736) {
            throw new java.lang.IllegalArgumentException("Tag is not a date type: " + i);
        }
        android.security.keymaster.KeymasterArgument argumentByTag = getArgumentByTag(i);
        if (argumentByTag == null) {
            return date;
        }
        java.util.Date date2 = ((android.security.keymaster.KeymasterDateArgument) argumentByTag).date;
        if (date2.getTime() < 0) {
            throw new java.lang.IllegalArgumentException("Tag value too large. Tag: " + i);
        }
        return date2;
    }

    private android.security.keymaster.KeymasterArgument getArgumentByTag(int i) {
        for (android.security.keymaster.KeymasterArgument keymasterArgument : this.mArguments) {
            if (keymasterArgument.tag == i) {
                return keymasterArgument;
            }
        }
        return null;
    }

    public boolean containsTag(int i) {
        return getArgumentByTag(i) != null;
    }

    public int size() {
        return this.mArguments.size();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeTypedList(this.mArguments);
    }

    public void readFromParcel(android.os.Parcel parcel) {
        parcel.readTypedList(this.mArguments, android.security.keymaster.KeymasterArgument.CREATOR);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static java.math.BigInteger toUint64(long j) {
        if (j >= 0) {
            return java.math.BigInteger.valueOf(j);
        }
        return java.math.BigInteger.valueOf(j).add(UINT64_RANGE);
    }
}
