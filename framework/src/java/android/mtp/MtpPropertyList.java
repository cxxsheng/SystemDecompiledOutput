package android.mtp;

/* loaded from: classes2.dex */
class MtpPropertyList {
    private int mCode;
    private java.util.List<java.lang.Integer> mObjectHandles = new java.util.ArrayList();
    private java.util.List<java.lang.Integer> mPropertyCodes = new java.util.ArrayList();
    private java.util.List<java.lang.Integer> mDataTypes = new java.util.ArrayList();
    private java.util.List<java.lang.Long> mLongValues = new java.util.ArrayList();
    private java.util.List<java.lang.String> mStringValues = new java.util.ArrayList();

    public MtpPropertyList(int i) {
        this.mCode = i;
    }

    public void append(int i, int i2, int i3, long j) {
        this.mObjectHandles.add(java.lang.Integer.valueOf(i));
        this.mPropertyCodes.add(java.lang.Integer.valueOf(i2));
        this.mDataTypes.add(java.lang.Integer.valueOf(i3));
        this.mLongValues.add(java.lang.Long.valueOf(j));
        this.mStringValues.add(null);
    }

    public void append(int i, int i2, java.lang.String str) {
        this.mObjectHandles.add(java.lang.Integer.valueOf(i));
        this.mPropertyCodes.add(java.lang.Integer.valueOf(i2));
        this.mDataTypes.add(65535);
        this.mStringValues.add(str);
        this.mLongValues.add(0L);
    }

    public int getCode() {
        return this.mCode;
    }

    public int getCount() {
        return this.mObjectHandles.size();
    }

    public int[] getObjectHandles() {
        return this.mObjectHandles.stream().mapToInt(new android.app.admin.PreferentialNetworkServiceConfig$$ExternalSyntheticLambda2()).toArray();
    }

    public int[] getPropertyCodes() {
        return this.mPropertyCodes.stream().mapToInt(new android.app.admin.PreferentialNetworkServiceConfig$$ExternalSyntheticLambda2()).toArray();
    }

    public int[] getDataTypes() {
        return this.mDataTypes.stream().mapToInt(new android.app.admin.PreferentialNetworkServiceConfig$$ExternalSyntheticLambda2()).toArray();
    }

    public long[] getLongValues() {
        return this.mLongValues.stream().mapToLong(new java.util.function.ToLongFunction() { // from class: android.mtp.MtpPropertyList$$ExternalSyntheticLambda0
            @Override // java.util.function.ToLongFunction
            public final long applyAsLong(java.lang.Object obj) {
                return ((java.lang.Long) obj).longValue();
            }
        }).toArray();
    }

    public java.lang.String[] getStringValues() {
        return (java.lang.String[]) this.mStringValues.toArray(new java.lang.String[0]);
    }
}
