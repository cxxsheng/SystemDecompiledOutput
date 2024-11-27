package android.media;

/* loaded from: classes2.dex */
public final class Rating implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.media.Rating> CREATOR = new android.os.Parcelable.Creator<android.media.Rating>() { // from class: android.media.Rating.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.Rating createFromParcel(android.os.Parcel parcel) {
            return new android.media.Rating(parcel.readInt(), parcel.readFloat());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.Rating[] newArray(int i) {
            return new android.media.Rating[i];
        }
    };
    public static final int RATING_3_STARS = 3;
    public static final int RATING_4_STARS = 4;
    public static final int RATING_5_STARS = 5;
    public static final int RATING_HEART = 1;
    public static final int RATING_NONE = 0;
    private static final float RATING_NOT_RATED = -1.0f;
    public static final int RATING_PERCENTAGE = 6;
    public static final int RATING_THUMB_UP_DOWN = 2;
    private static final java.lang.String TAG = "Rating";
    private final int mRatingStyle;
    private final float mRatingValue;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface StarStyle {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Style {
    }

    private Rating(int i, float f) {
        this.mRatingStyle = i;
        this.mRatingValue = f;
    }

    public java.lang.String toString() {
        return "Rating:style=" + this.mRatingStyle + " rating=" + (this.mRatingValue < 0.0f ? "unrated" : java.lang.String.valueOf(this.mRatingValue));
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return this.mRatingStyle;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mRatingStyle);
        parcel.writeFloat(this.mRatingValue);
    }

    public static android.media.Rating newUnratedRating(int i) {
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                return new android.media.Rating(i, -1.0f);
            default:
                return null;
        }
    }

    public static android.media.Rating newHeartRating(boolean z) {
        return new android.media.Rating(1, z ? 1.0f : 0.0f);
    }

    public static android.media.Rating newThumbRating(boolean z) {
        return new android.media.Rating(2, z ? 1.0f : 0.0f);
    }

    public static android.media.Rating newStarRating(int i, float f) {
        float f2;
        switch (i) {
            case 3:
                f2 = 3.0f;
                break;
            case 4:
                f2 = 4.0f;
                break;
            case 5:
                f2 = 5.0f;
                break;
            default:
                android.util.Log.e(TAG, "Invalid rating style (" + i + ") for a star rating");
                return null;
        }
        if (f >= 0.0f && f <= f2) {
            return new android.media.Rating(i, f);
        }
        android.util.Log.e(TAG, "Trying to set out of range star-based rating");
        return null;
    }

    public static android.media.Rating newPercentageRating(float f) {
        if (f >= 0.0f && f <= 100.0f) {
            return new android.media.Rating(6, f);
        }
        android.util.Log.e(TAG, "Invalid percentage-based rating value");
        return null;
    }

    public boolean isRated() {
        return this.mRatingValue >= 0.0f;
    }

    public int getRatingStyle() {
        return this.mRatingStyle;
    }

    public boolean hasHeart() {
        return this.mRatingStyle == 1 && this.mRatingValue == 1.0f;
    }

    public boolean isThumbUp() {
        return this.mRatingStyle == 2 && this.mRatingValue == 1.0f;
    }

    public float getStarRating() {
        switch (this.mRatingStyle) {
            case 3:
            case 4:
            case 5:
                if (isRated()) {
                    return this.mRatingValue;
                }
            default:
                return -1.0f;
        }
    }

    public float getPercentRating() {
        if (this.mRatingStyle != 6 || !isRated()) {
            return -1.0f;
        }
        return this.mRatingValue;
    }
}
