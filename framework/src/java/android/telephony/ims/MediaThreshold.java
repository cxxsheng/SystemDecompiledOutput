package android.telephony.ims;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public final class MediaThreshold implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.telephony.ims.MediaThreshold> CREATOR = new android.os.Parcelable.Creator<android.telephony.ims.MediaThreshold>() { // from class: android.telephony.ims.MediaThreshold.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.ims.MediaThreshold createFromParcel(android.os.Parcel parcel) {
            return new android.telephony.ims.MediaThreshold(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.ims.MediaThreshold[] newArray(int i) {
            return new android.telephony.ims.MediaThreshold[i];
        }
    };
    private final long[] mRtpInactivityTimeMillis;
    private final int[] mRtpJitter;
    private final int[] mRtpPacketLossRate;

    @android.annotation.SystemApi
    public int[] getThresholdsRtpPacketLossRate() {
        return this.mRtpPacketLossRate;
    }

    public int[] getThresholdsRtpJitterMillis() {
        return this.mRtpJitter;
    }

    public long[] getThresholdsRtpInactivityTimeMillis() {
        return this.mRtpInactivityTimeMillis;
    }

    private MediaThreshold(int[] iArr, int[] iArr2, long[] jArr) {
        this.mRtpPacketLossRate = iArr;
        this.mRtpJitter = iArr2;
        this.mRtpInactivityTimeMillis = jArr;
    }

    private MediaThreshold(android.os.Parcel parcel) {
        this.mRtpPacketLossRate = parcel.createIntArray();
        this.mRtpJitter = parcel.createIntArray();
        this.mRtpInactivityTimeMillis = parcel.createLongArray();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeIntArray(this.mRtpPacketLossRate);
        parcel.writeIntArray(this.mRtpJitter);
        parcel.writeLongArray(this.mRtpInactivityTimeMillis);
    }

    public static boolean isValidRtpPacketLossRate(int i) {
        return i >= 0 && i <= 100;
    }

    public static boolean isValidJitterMillis(int i) {
        return i >= 0 && i <= 10000;
    }

    public static boolean isValidRtpInactivityTimeMillis(long j) {
        return j >= 0 && j <= 60000;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.telephony.ims.MediaThreshold mediaThreshold = (android.telephony.ims.MediaThreshold) obj;
        if (java.util.Arrays.equals(this.mRtpPacketLossRate, mediaThreshold.mRtpPacketLossRate) && java.util.Arrays.equals(this.mRtpJitter, mediaThreshold.mRtpJitter) && java.util.Arrays.equals(this.mRtpInactivityTimeMillis, mediaThreshold.mRtpInactivityTimeMillis)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(java.util.Arrays.hashCode(this.mRtpPacketLossRate)), java.lang.Integer.valueOf(java.util.Arrays.hashCode(this.mRtpJitter)), java.lang.Integer.valueOf(java.util.Arrays.hashCode(this.mRtpInactivityTimeMillis)));
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("MediaThreshold{mRtpPacketLossRate=");
        for (int i : this.mRtpPacketLossRate) {
            sb.append(" ").append(i);
        }
        sb.append(", mRtpJitter=");
        for (int i2 : this.mRtpJitter) {
            sb.append(" ").append(i2);
        }
        sb.append(", mRtpInactivityTimeMillis=");
        for (long j : this.mRtpInactivityTimeMillis) {
            sb.append(" ").append(j);
        }
        sb.append("}");
        return sb.toString();
    }

    public static final class Builder {
        private int[] mRtpPacketLossRate = null;
        private int[] mRtpJitter = null;
        private long[] mRtpInactivityTimeMillis = null;

        public android.telephony.ims.MediaThreshold.Builder setThresholdsRtpPacketLossRate(int[] iArr) {
            if (iArr.length > 0) {
                java.util.TreeSet treeSet = new java.util.TreeSet();
                int i = 0;
                for (int i2 : iArr) {
                    java.lang.Integer valueOf = java.lang.Integer.valueOf(i2);
                    if (android.telephony.ims.MediaThreshold.isValidRtpPacketLossRate(valueOf.intValue())) {
                        treeSet.add(valueOf);
                    }
                }
                int[] iArr2 = new int[treeSet.size()];
                java.util.Iterator it = treeSet.iterator();
                while (it.hasNext()) {
                    iArr2[i] = ((java.lang.Integer) it.next()).intValue();
                    i++;
                }
                this.mRtpPacketLossRate = iArr2;
            } else {
                this.mRtpPacketLossRate = iArr;
            }
            return this;
        }

        public android.telephony.ims.MediaThreshold.Builder setThresholdsRtpJitterMillis(int[] iArr) {
            if (iArr.length > 0) {
                java.util.TreeSet treeSet = new java.util.TreeSet();
                int i = 0;
                for (int i2 : iArr) {
                    java.lang.Integer valueOf = java.lang.Integer.valueOf(i2);
                    if (android.telephony.ims.MediaThreshold.isValidJitterMillis(valueOf.intValue())) {
                        treeSet.add(valueOf);
                    }
                }
                int[] iArr2 = new int[treeSet.size()];
                java.util.Iterator it = treeSet.iterator();
                while (it.hasNext()) {
                    iArr2[i] = ((java.lang.Integer) it.next()).intValue();
                    i++;
                }
                this.mRtpJitter = iArr2;
            } else {
                this.mRtpJitter = iArr;
            }
            return this;
        }

        public android.telephony.ims.MediaThreshold.Builder setThresholdsRtpInactivityTimeMillis(long[] jArr) {
            if (jArr.length > 0) {
                java.util.TreeSet treeSet = new java.util.TreeSet();
                int i = 0;
                for (long j : jArr) {
                    java.lang.Long valueOf = java.lang.Long.valueOf(j);
                    if (android.telephony.ims.MediaThreshold.isValidRtpInactivityTimeMillis(valueOf.longValue())) {
                        treeSet.add(valueOf);
                    }
                }
                long[] jArr2 = new long[treeSet.size()];
                java.util.Iterator it = treeSet.iterator();
                while (it.hasNext()) {
                    jArr2[i] = ((java.lang.Long) it.next()).longValue();
                    i++;
                }
                this.mRtpInactivityTimeMillis = jArr2;
            } else {
                this.mRtpInactivityTimeMillis = jArr;
            }
            return this;
        }

        public android.telephony.ims.MediaThreshold build() {
            this.mRtpPacketLossRate = this.mRtpPacketLossRate != null ? this.mRtpPacketLossRate : new int[0];
            this.mRtpJitter = this.mRtpJitter != null ? this.mRtpJitter : new int[0];
            this.mRtpInactivityTimeMillis = this.mRtpInactivityTimeMillis != null ? this.mRtpInactivityTimeMillis : new long[0];
            return new android.telephony.ims.MediaThreshold(this.mRtpPacketLossRate, this.mRtpJitter, this.mRtpInactivityTimeMillis);
        }
    }
}
