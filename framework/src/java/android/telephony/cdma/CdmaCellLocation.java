package android.telephony.cdma;

@java.lang.Deprecated
/* loaded from: classes3.dex */
public class CdmaCellLocation extends android.telephony.CellLocation {
    public static final int INVALID_LAT_LONG = Integer.MAX_VALUE;
    private int mBaseStationId;
    private int mBaseStationLatitude;
    private int mBaseStationLongitude;
    private int mNetworkId;
    private int mSystemId;

    public CdmaCellLocation() {
        this.mBaseStationId = -1;
        this.mBaseStationLatitude = Integer.MAX_VALUE;
        this.mBaseStationLongitude = Integer.MAX_VALUE;
        this.mSystemId = -1;
        this.mNetworkId = -1;
        this.mBaseStationId = -1;
        this.mBaseStationLatitude = Integer.MAX_VALUE;
        this.mBaseStationLongitude = Integer.MAX_VALUE;
        this.mSystemId = -1;
        this.mNetworkId = -1;
    }

    public CdmaCellLocation(android.os.Bundle bundle) {
        this.mBaseStationId = -1;
        this.mBaseStationLatitude = Integer.MAX_VALUE;
        this.mBaseStationLongitude = Integer.MAX_VALUE;
        this.mSystemId = -1;
        this.mNetworkId = -1;
        this.mBaseStationId = bundle.getInt("baseStationId", this.mBaseStationId);
        this.mBaseStationLatitude = bundle.getInt("baseStationLatitude", this.mBaseStationLatitude);
        this.mBaseStationLongitude = bundle.getInt("baseStationLongitude", this.mBaseStationLongitude);
        this.mSystemId = bundle.getInt(android.content.Intent.EXTRA_SYSTEM_ID, this.mSystemId);
        this.mNetworkId = bundle.getInt(android.content.Intent.EXTRA_NETWORK_ID, this.mNetworkId);
    }

    public int getBaseStationId() {
        return this.mBaseStationId;
    }

    public int getBaseStationLatitude() {
        return this.mBaseStationLatitude;
    }

    public int getBaseStationLongitude() {
        return this.mBaseStationLongitude;
    }

    public int getSystemId() {
        return this.mSystemId;
    }

    public int getNetworkId() {
        return this.mNetworkId;
    }

    @Override // android.telephony.CellLocation
    public void setStateInvalid() {
        this.mBaseStationId = -1;
        this.mBaseStationLatitude = Integer.MAX_VALUE;
        this.mBaseStationLongitude = Integer.MAX_VALUE;
        this.mSystemId = -1;
        this.mNetworkId = -1;
    }

    public void setCellLocationData(int i, int i2, int i3) {
        this.mBaseStationId = i;
        this.mBaseStationLatitude = i2;
        this.mBaseStationLongitude = i3;
    }

    public void setCellLocationData(int i, int i2, int i3, int i4, int i5) {
        this.mBaseStationId = i;
        this.mBaseStationLatitude = i2;
        this.mBaseStationLongitude = i3;
        this.mSystemId = i4;
        this.mNetworkId = i5;
    }

    public int hashCode() {
        return (((this.mBaseStationId ^ this.mBaseStationLatitude) ^ this.mBaseStationLongitude) ^ this.mSystemId) ^ this.mNetworkId;
    }

    public boolean equals(java.lang.Object obj) {
        try {
            android.telephony.cdma.CdmaCellLocation cdmaCellLocation = (android.telephony.cdma.CdmaCellLocation) obj;
            return obj != null && equalsHandlesNulls(java.lang.Integer.valueOf(this.mBaseStationId), java.lang.Integer.valueOf(cdmaCellLocation.mBaseStationId)) && equalsHandlesNulls(java.lang.Integer.valueOf(this.mBaseStationLatitude), java.lang.Integer.valueOf(cdmaCellLocation.mBaseStationLatitude)) && equalsHandlesNulls(java.lang.Integer.valueOf(this.mBaseStationLongitude), java.lang.Integer.valueOf(cdmaCellLocation.mBaseStationLongitude)) && equalsHandlesNulls(java.lang.Integer.valueOf(this.mSystemId), java.lang.Integer.valueOf(cdmaCellLocation.mSystemId)) && equalsHandlesNulls(java.lang.Integer.valueOf(this.mNetworkId), java.lang.Integer.valueOf(cdmaCellLocation.mNetworkId));
        } catch (java.lang.ClassCastException e) {
            return false;
        }
    }

    public java.lang.String toString() {
        return android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_START + this.mBaseStationId + "," + com.android.telephony.Rlog.pii(com.android.internal.telephony.util.TelephonyUtils.IS_DEBUGGABLE, java.lang.Integer.valueOf(this.mBaseStationLatitude)) + "," + com.android.telephony.Rlog.pii(com.android.internal.telephony.util.TelephonyUtils.IS_DEBUGGABLE, java.lang.Integer.valueOf(this.mBaseStationLongitude)) + "," + this.mSystemId + "," + this.mNetworkId + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END;
    }

    private static boolean equalsHandlesNulls(java.lang.Object obj, java.lang.Object obj2) {
        return obj == null ? obj2 == null : obj.equals(obj2);
    }

    @Override // android.telephony.CellLocation
    public void fillInNotifierBundle(android.os.Bundle bundle) {
        bundle.putInt("baseStationId", this.mBaseStationId);
        bundle.putInt("baseStationLatitude", this.mBaseStationLatitude);
        bundle.putInt("baseStationLongitude", this.mBaseStationLongitude);
        bundle.putInt(android.content.Intent.EXTRA_SYSTEM_ID, this.mSystemId);
        bundle.putInt(android.content.Intent.EXTRA_NETWORK_ID, this.mNetworkId);
    }

    @Override // android.telephony.CellLocation
    public boolean isEmpty() {
        return this.mBaseStationId == -1 && this.mBaseStationLatitude == Integer.MAX_VALUE && this.mBaseStationLongitude == Integer.MAX_VALUE && this.mSystemId == -1 && this.mNetworkId == -1;
    }

    public static double convertQuartSecToDecDegrees(int i) {
        double d = i;
        if (java.lang.Double.isNaN(d) || i < -2592000 || i > 2592000) {
            throw new java.lang.IllegalArgumentException("Invalid coordiante value:" + i);
        }
        return d / 14400.0d;
    }
}
