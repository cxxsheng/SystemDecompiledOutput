package com.android.internal.telephony;

/* loaded from: classes5.dex */
public final class HbpcdUtils {
    private static final boolean DBG = false;
    private static final java.lang.String LOG_TAG = "HbpcdUtils";
    private android.content.ContentResolver resolver;

    public HbpcdUtils(android.content.Context context) {
        this.resolver = null;
        this.resolver = context.getContentResolver();
    }

    public int getMcc(int i, int i2, int i3, boolean z) {
        android.database.Cursor query = this.resolver.query(com.android.internal.telephony.HbpcdLookup.ArbitraryMccSidMatch.CONTENT_URI, new java.lang.String[]{"MCC"}, "SID=" + i, null, null);
        if (query != null) {
            if (query.getCount() == 1) {
                query.moveToFirst();
                int i4 = query.getInt(0);
                query.close();
                return i4;
            }
            query.close();
        }
        android.database.Cursor query2 = this.resolver.query(com.android.internal.telephony.HbpcdLookup.MccSidConflicts.CONTENT_URI, new java.lang.String[]{"MCC"}, "SID_Conflict=" + i + " and (((" + com.android.internal.telephony.HbpcdLookup.MccLookup.GMT_OFFSET_LOW + "<=" + i2 + ") and (" + i2 + "<=" + com.android.internal.telephony.HbpcdLookup.MccLookup.GMT_OFFSET_HIGH + ") and (0=" + i3 + ")) or ((" + com.android.internal.telephony.HbpcdLookup.MccLookup.GMT_DST_LOW + "<=" + i2 + ") and (" + i2 + "<=" + com.android.internal.telephony.HbpcdLookup.MccLookup.GMT_DST_HIGH + ") and (1=" + i3 + ")))", null, null);
        if (query2 != null) {
            int count = query2.getCount();
            if (count > 0) {
                if (count > 1) {
                    android.util.Log.w(LOG_TAG, "something wrong, get more results for 1 conflict SID: " + query2);
                }
                query2.moveToFirst();
                int i5 = z ? query2.getInt(0) : 0;
                query2.close();
                return i5;
            }
            query2.close();
        }
        android.database.Cursor query3 = this.resolver.query(com.android.internal.telephony.HbpcdLookup.MccSidRange.CONTENT_URI, new java.lang.String[]{"MCC"}, "SID_Range_Low<=" + i + " and " + com.android.internal.telephony.HbpcdLookup.MccSidRange.RANGE_HIGH + ">=" + i, null, null);
        if (query3 != null) {
            if (query3.getCount() > 0) {
                query3.moveToFirst();
                int i6 = query3.getInt(0);
                query3.close();
                return i6;
            }
            query3.close();
        }
        return 0;
    }

    public java.lang.String getIddByMcc(int i) {
        android.database.Cursor query = this.resolver.query(com.android.internal.telephony.HbpcdLookup.MccIdd.CONTENT_URI, new java.lang.String[]{com.android.internal.telephony.HbpcdLookup.MccIdd.IDD}, "MCC=" + i, null, null);
        java.lang.String str = "";
        if (query != null) {
            if (query.getCount() > 0) {
                query.moveToFirst();
                str = query.getString(0);
            }
            query.close();
        }
        return str;
    }
}
