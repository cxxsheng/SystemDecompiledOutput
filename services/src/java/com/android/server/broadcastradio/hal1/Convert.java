package com.android.server.broadcastradio.hal1;

/* loaded from: classes.dex */
class Convert {
    private static final java.lang.String TAG = "BcRadio1Srv.Convert";

    Convert() {
    }

    @android.annotation.NonNull
    static java.lang.String[][] stringMapToNative(@android.annotation.Nullable java.util.Map<java.lang.String, java.lang.String> map) {
        if (map == null) {
            android.util.Slog.v(TAG, "map is null, returning zero-elements array");
            return (java.lang.String[][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) java.lang.String.class, 0, 0);
        }
        java.util.Set<java.util.Map.Entry<java.lang.String, java.lang.String>> entrySet = map.entrySet();
        java.lang.String[][] strArr = (java.lang.String[][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) java.lang.String.class, entrySet.size(), 2);
        int i = 0;
        for (java.util.Map.Entry<java.lang.String, java.lang.String> entry : entrySet) {
            strArr[i][0] = entry.getKey();
            strArr[i][1] = entry.getValue();
            i++;
        }
        android.util.Slog.v(TAG, "converted " + i + " element(s)");
        return strArr;
    }
}
