package android.net.shared;

/* loaded from: classes.dex */
public class Layer2Information {

    @android.annotation.Nullable
    public final android.net.MacAddress mBssid;

    @android.annotation.Nullable
    public final java.lang.String mCluster;

    @android.annotation.Nullable
    public final java.lang.String mL2Key;

    public Layer2Information(@android.annotation.Nullable java.lang.String str, @android.annotation.Nullable java.lang.String str2, @android.annotation.Nullable android.net.MacAddress macAddress) {
        this.mL2Key = str;
        this.mCluster = str2;
        this.mBssid = macAddress;
    }

    public java.lang.String toString() {
        java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer();
        stringBuffer.append("L2Key: ");
        stringBuffer.append(this.mL2Key);
        stringBuffer.append(", Cluster: ");
        stringBuffer.append(this.mCluster);
        stringBuffer.append(", bssid: ");
        stringBuffer.append(this.mBssid);
        return stringBuffer.toString();
    }

    public android.net.Layer2InformationParcelable toStableParcelable() {
        android.net.Layer2InformationParcelable layer2InformationParcelable = new android.net.Layer2InformationParcelable();
        layer2InformationParcelable.l2Key = this.mL2Key;
        layer2InformationParcelable.cluster = this.mCluster;
        layer2InformationParcelable.bssid = this.mBssid;
        return layer2InformationParcelable;
    }

    public static android.net.shared.Layer2Information fromStableParcelable(android.net.Layer2InformationParcelable layer2InformationParcelable) {
        if (layer2InformationParcelable == null) {
            return null;
        }
        return new android.net.shared.Layer2Information(layer2InformationParcelable.l2Key, layer2InformationParcelable.cluster, layer2InformationParcelable.bssid);
    }

    public boolean equals(java.lang.Object obj) {
        if (!(obj instanceof android.net.shared.Layer2Information)) {
            return false;
        }
        android.net.shared.Layer2Information layer2Information = (android.net.shared.Layer2Information) obj;
        return java.util.Objects.equals(this.mL2Key, layer2Information.mL2Key) && java.util.Objects.equals(this.mCluster, layer2Information.mCluster) && java.util.Objects.equals(this.mBssid, layer2Information.mBssid);
    }

    public int hashCode() {
        return java.util.Objects.hash(this.mL2Key, this.mCluster, this.mBssid);
    }
}
