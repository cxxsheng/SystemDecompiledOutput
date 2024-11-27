package android.telephony.gsm;

@java.lang.Deprecated
/* loaded from: classes3.dex */
public class GsmCellLocation extends android.telephony.CellLocation {
    private int mCid;
    private int mLac;
    private int mPsc;

    public GsmCellLocation() {
        this.mLac = -1;
        this.mCid = -1;
        this.mPsc = -1;
    }

    public GsmCellLocation(android.os.Bundle bundle) {
        this.mLac = bundle.getInt(android.provider.Telephony.CellBroadcasts.LAC, -1);
        this.mCid = bundle.getInt("cid", -1);
        this.mPsc = bundle.getInt("psc", -1);
    }

    public int getLac() {
        return this.mLac;
    }

    public int getCid() {
        return this.mCid;
    }

    public int getPsc() {
        return this.mPsc;
    }

    @Override // android.telephony.CellLocation
    public void setStateInvalid() {
        this.mLac = -1;
        this.mCid = -1;
        this.mPsc = -1;
    }

    public void setLacAndCid(int i, int i2) {
        this.mLac = i;
        this.mCid = i2;
    }

    public void setPsc(int i) {
        this.mPsc = i;
    }

    public int hashCode() {
        return this.mLac ^ this.mCid;
    }

    public boolean equals(java.lang.Object obj) {
        try {
            android.telephony.gsm.GsmCellLocation gsmCellLocation = (android.telephony.gsm.GsmCellLocation) obj;
            return obj != null && equalsHandlesNulls(java.lang.Integer.valueOf(this.mLac), java.lang.Integer.valueOf(gsmCellLocation.mLac)) && equalsHandlesNulls(java.lang.Integer.valueOf(this.mCid), java.lang.Integer.valueOf(gsmCellLocation.mCid)) && equalsHandlesNulls(java.lang.Integer.valueOf(this.mPsc), java.lang.Integer.valueOf(gsmCellLocation.mPsc));
        } catch (java.lang.ClassCastException e) {
            return false;
        }
    }

    public java.lang.String toString() {
        return android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_START + this.mLac + "," + this.mCid + "," + this.mPsc + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END;
    }

    private static boolean equalsHandlesNulls(java.lang.Object obj, java.lang.Object obj2) {
        return obj == null ? obj2 == null : obj.equals(obj2);
    }

    @Override // android.telephony.CellLocation
    public void fillInNotifierBundle(android.os.Bundle bundle) {
        bundle.putInt(android.provider.Telephony.CellBroadcasts.LAC, this.mLac);
        bundle.putInt("cid", this.mCid);
        bundle.putInt("psc", this.mPsc);
    }

    @Override // android.telephony.CellLocation
    public boolean isEmpty() {
        return this.mLac == -1 && this.mCid == -1 && this.mPsc == -1;
    }
}
