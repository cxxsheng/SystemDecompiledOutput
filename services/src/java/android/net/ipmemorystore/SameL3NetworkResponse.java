package android.net.ipmemorystore;

/* loaded from: classes.dex */
public class SameL3NetworkResponse {
    public static final int NETWORK_DIFFERENT = 2;
    public static final int NETWORK_NEVER_CONNECTED = 3;
    public static final int NETWORK_SAME = 1;
    public final float confidence;

    @android.annotation.NonNull
    public final java.lang.String l2Key1;

    @android.annotation.NonNull
    public final java.lang.String l2Key2;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface NetworkSameness {
    }

    public final int getNetworkSameness() {
        if (this.confidence > 1.0d || this.confidence < 0.0d) {
            return 3;
        }
        return ((double) this.confidence) > 0.5d ? 1 : 2;
    }

    public SameL3NetworkResponse(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2, float f) {
        this.l2Key1 = str;
        this.l2Key2 = str2;
        this.confidence = f;
    }

    @com.android.internal.annotations.VisibleForTesting
    public SameL3NetworkResponse(@android.annotation.NonNull android.net.ipmemorystore.SameL3NetworkResponseParcelable sameL3NetworkResponseParcelable) {
        this(sameL3NetworkResponseParcelable.l2Key1, sameL3NetworkResponseParcelable.l2Key2, sameL3NetworkResponseParcelable.confidence);
    }

    @android.annotation.NonNull
    public android.net.ipmemorystore.SameL3NetworkResponseParcelable toParcelable() {
        android.net.ipmemorystore.SameL3NetworkResponseParcelable sameL3NetworkResponseParcelable = new android.net.ipmemorystore.SameL3NetworkResponseParcelable();
        sameL3NetworkResponseParcelable.l2Key1 = this.l2Key1;
        sameL3NetworkResponseParcelable.l2Key2 = this.l2Key2;
        sameL3NetworkResponseParcelable.confidence = this.confidence;
        return sameL3NetworkResponseParcelable;
    }

    public boolean equals(@android.annotation.Nullable java.lang.Object obj) {
        if (!(obj instanceof android.net.ipmemorystore.SameL3NetworkResponse)) {
            return false;
        }
        android.net.ipmemorystore.SameL3NetworkResponse sameL3NetworkResponse = (android.net.ipmemorystore.SameL3NetworkResponse) obj;
        return this.l2Key1.equals(sameL3NetworkResponse.l2Key1) && this.l2Key2.equals(sameL3NetworkResponse.l2Key2) && this.confidence == sameL3NetworkResponse.confidence;
    }

    public int hashCode() {
        return java.util.Objects.hash(this.l2Key1, this.l2Key2, java.lang.Float.valueOf(this.confidence));
    }

    public java.lang.String toString() {
        switch (getNetworkSameness()) {
            case 1:
                return "\"" + this.l2Key1 + "\" same L3 network as \"" + this.l2Key2 + "\"";
            case 2:
                return "\"" + this.l2Key1 + "\" different L3 network from \"" + this.l2Key2 + "\"";
            case 3:
                return "\"" + this.l2Key1 + "\" can't be tested against \"" + this.l2Key2 + "\"";
            default:
                return "Buggy sameness value ? \"" + this.l2Key1 + "\", \"" + this.l2Key2 + "\"";
        }
    }
}
