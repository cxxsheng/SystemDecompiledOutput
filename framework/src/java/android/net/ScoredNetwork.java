package android.net;

@android.annotation.SystemApi
@java.lang.Deprecated
/* loaded from: classes2.dex */
public class ScoredNetwork implements android.os.Parcelable {
    public static final java.lang.String ATTRIBUTES_KEY_BADGING_CURVE = "android.net.attributes.key.BADGING_CURVE";
    public static final java.lang.String ATTRIBUTES_KEY_HAS_CAPTIVE_PORTAL = "android.net.attributes.key.HAS_CAPTIVE_PORTAL";
    public static final java.lang.String ATTRIBUTES_KEY_RANKING_SCORE_OFFSET = "android.net.attributes.key.RANKING_SCORE_OFFSET";
    public static final android.os.Parcelable.Creator<android.net.ScoredNetwork> CREATOR = new android.os.Parcelable.Creator<android.net.ScoredNetwork>() { // from class: android.net.ScoredNetwork.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.net.ScoredNetwork createFromParcel(android.os.Parcel parcel) {
            return new android.net.ScoredNetwork(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.net.ScoredNetwork[] newArray(int i) {
            return new android.net.ScoredNetwork[i];
        }
    };
    public final android.os.Bundle attributes;
    public final boolean meteredHint;
    public final android.net.NetworkKey networkKey;
    public final android.net.RssiCurve rssiCurve;

    public ScoredNetwork(android.net.NetworkKey networkKey, android.net.RssiCurve rssiCurve) {
        this(networkKey, rssiCurve, false);
    }

    public ScoredNetwork(android.net.NetworkKey networkKey, android.net.RssiCurve rssiCurve, boolean z) {
        this(networkKey, rssiCurve, z, null);
    }

    public ScoredNetwork(android.net.NetworkKey networkKey, android.net.RssiCurve rssiCurve, boolean z, android.os.Bundle bundle) {
        this.networkKey = networkKey;
        this.rssiCurve = rssiCurve;
        this.meteredHint = z;
        this.attributes = bundle;
    }

    private ScoredNetwork(android.os.Parcel parcel) {
        this.networkKey = android.net.NetworkKey.CREATOR.createFromParcel(parcel);
        if (parcel.readByte() == 1) {
            this.rssiCurve = android.net.RssiCurve.CREATOR.createFromParcel(parcel);
        } else {
            this.rssiCurve = null;
        }
        this.meteredHint = parcel.readByte() == 1;
        this.attributes = parcel.readBundle();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        this.networkKey.writeToParcel(parcel, i);
        if (this.rssiCurve != null) {
            parcel.writeByte((byte) 1);
            this.rssiCurve.writeToParcel(parcel, i);
        } else {
            parcel.writeByte((byte) 0);
        }
        parcel.writeByte(this.meteredHint ? (byte) 1 : (byte) 0);
        parcel.writeBundle(this.attributes);
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.net.ScoredNetwork scoredNetwork = (android.net.ScoredNetwork) obj;
        if (java.util.Objects.equals(this.networkKey, scoredNetwork.networkKey) && java.util.Objects.equals(this.rssiCurve, scoredNetwork.rssiCurve) && java.util.Objects.equals(java.lang.Boolean.valueOf(this.meteredHint), java.lang.Boolean.valueOf(scoredNetwork.meteredHint)) && bundleEquals(this.attributes, scoredNetwork.attributes)) {
            return true;
        }
        return false;
    }

    private boolean bundleEquals(android.os.Bundle bundle, android.os.Bundle bundle2) {
        if (bundle == bundle2) {
            return true;
        }
        if (bundle == null || bundle2 == null || bundle.size() != bundle2.size()) {
            return false;
        }
        for (java.lang.String str : bundle.keySet()) {
            if (!java.util.Objects.equals(bundle.get(str), bundle2.get(str))) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        return java.util.Objects.hash(this.networkKey, this.rssiCurve, java.lang.Boolean.valueOf(this.meteredHint), this.attributes);
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder("ScoredNetwork{networkKey=" + this.networkKey + ", rssiCurve=" + this.rssiCurve + ", meteredHint=" + this.meteredHint);
        if (this.attributes != null && !this.attributes.isEmpty()) {
            sb.append(", attributes=" + this.attributes);
        }
        sb.append('}');
        return sb.toString();
    }

    public boolean hasRankingScore() {
        return this.rssiCurve != null || (this.attributes != null && this.attributes.containsKey(ATTRIBUTES_KEY_RANKING_SCORE_OFFSET));
    }

    public int calculateRankingScore(int i) throws java.lang.UnsupportedOperationException {
        int i2;
        if (!hasRankingScore()) {
            throw new java.lang.UnsupportedOperationException("Either rssiCurve or rankingScoreOffset is required to calculate the ranking score");
        }
        if (this.attributes == null) {
            i2 = 0;
        } else {
            i2 = this.attributes.getInt(ATTRIBUTES_KEY_RANKING_SCORE_OFFSET, 0) + 0;
        }
        int lookupScore = this.rssiCurve != null ? this.rssiCurve.lookupScore(i) << 8 : 0;
        try {
            return java.lang.Math.addExact(lookupScore, i2);
        } catch (java.lang.ArithmeticException e) {
            return lookupScore < 0 ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        }
    }

    public int calculateBadge(int i) {
        if (this.attributes != null && this.attributes.containsKey(ATTRIBUTES_KEY_BADGING_CURVE)) {
            return ((android.net.RssiCurve) this.attributes.getParcelable(ATTRIBUTES_KEY_BADGING_CURVE, android.net.RssiCurve.class)).lookupScore(i);
        }
        return 0;
    }
}
