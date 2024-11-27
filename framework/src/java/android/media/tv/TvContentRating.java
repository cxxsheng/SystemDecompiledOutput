package android.media.tv;

/* loaded from: classes2.dex */
public final class TvContentRating {
    private static final java.lang.String DELIMITER = "/";
    public static final android.media.tv.TvContentRating UNRATED = new android.media.tv.TvContentRating("null", "null", "null", null);
    private final java.lang.String mDomain;
    private final int mHashCode;
    private final java.lang.String mRating;
    private final java.lang.String mRatingSystem;
    private final java.lang.String[] mSubRatings;

    public static android.media.tv.TvContentRating createRating(java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String... strArr) {
        if (android.text.TextUtils.isEmpty(str)) {
            throw new java.lang.IllegalArgumentException("domain cannot be empty");
        }
        if (android.text.TextUtils.isEmpty(str2)) {
            throw new java.lang.IllegalArgumentException("ratingSystem cannot be empty");
        }
        if (android.text.TextUtils.isEmpty(str3)) {
            throw new java.lang.IllegalArgumentException("rating cannot be empty");
        }
        return new android.media.tv.TvContentRating(str, str2, str3, strArr);
    }

    public static android.media.tv.TvContentRating unflattenFromString(java.lang.String str) {
        if (android.text.TextUtils.isEmpty(str)) {
            throw new java.lang.IllegalArgumentException("ratingString cannot be empty");
        }
        java.lang.String[] split = str.split(DELIMITER);
        if (split.length < 3) {
            throw new java.lang.IllegalArgumentException("Invalid rating string: " + str);
        }
        if (split.length > 3) {
            int length = split.length - 3;
            java.lang.String[] strArr = new java.lang.String[length];
            java.lang.System.arraycopy(split, 3, strArr, 0, length);
            return new android.media.tv.TvContentRating(split[0], split[1], split[2], strArr);
        }
        return new android.media.tv.TvContentRating(split[0], split[1], split[2], null);
    }

    private TvContentRating(java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String[] strArr) {
        this.mDomain = str;
        this.mRatingSystem = str2;
        this.mRating = str3;
        if (strArr == null || strArr.length == 0) {
            this.mSubRatings = null;
        } else {
            java.util.Arrays.sort(strArr);
            this.mSubRatings = strArr;
        }
        this.mHashCode = (java.util.Objects.hash(this.mDomain, this.mRating) * 31) + java.util.Arrays.hashCode(this.mSubRatings);
    }

    public java.lang.String getDomain() {
        return this.mDomain;
    }

    public java.lang.String getRatingSystem() {
        return this.mRatingSystem;
    }

    public java.lang.String getMainRating() {
        return this.mRating;
    }

    public java.util.List<java.lang.String> getSubRatings() {
        if (this.mSubRatings == null) {
            return null;
        }
        return java.util.Collections.unmodifiableList(java.util.Arrays.asList(this.mSubRatings));
    }

    public java.lang.String flattenToString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append(this.mDomain);
        sb.append(DELIMITER);
        sb.append(this.mRatingSystem);
        sb.append(DELIMITER);
        sb.append(this.mRating);
        if (this.mSubRatings != null) {
            for (java.lang.String str : this.mSubRatings) {
                sb.append(DELIMITER);
                sb.append(str);
            }
        }
        return sb.toString();
    }

    public final boolean contains(android.media.tv.TvContentRating tvContentRating) {
        com.android.internal.util.Preconditions.checkNotNull(tvContentRating);
        if (!tvContentRating.getMainRating().equals(this.mRating) || !tvContentRating.getDomain().equals(this.mDomain) || !tvContentRating.getRatingSystem().equals(this.mRatingSystem) || !tvContentRating.getMainRating().equals(this.mRating)) {
            return false;
        }
        java.util.List<java.lang.String> subRatings = getSubRatings();
        java.util.List<java.lang.String> subRatings2 = tvContentRating.getSubRatings();
        if (subRatings == null && subRatings2 == null) {
            return true;
        }
        if (subRatings == null && subRatings2 != null) {
            return false;
        }
        if (subRatings != null && subRatings2 == null) {
            return true;
        }
        return subRatings.containsAll(subRatings2);
    }

    public boolean equals(java.lang.Object obj) {
        if (!(obj instanceof android.media.tv.TvContentRating)) {
            return false;
        }
        android.media.tv.TvContentRating tvContentRating = (android.media.tv.TvContentRating) obj;
        if (this.mHashCode == tvContentRating.mHashCode && android.text.TextUtils.equals(this.mDomain, tvContentRating.mDomain) && android.text.TextUtils.equals(this.mRatingSystem, tvContentRating.mRatingSystem) && android.text.TextUtils.equals(this.mRating, tvContentRating.mRating)) {
            return java.util.Arrays.equals(this.mSubRatings, tvContentRating.mSubRatings);
        }
        return false;
    }

    public int hashCode() {
        return this.mHashCode;
    }
}
