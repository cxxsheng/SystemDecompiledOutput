package android.service.autofill;

/* loaded from: classes3.dex */
public abstract class InternalTransformation implements android.service.autofill.Transformation, android.os.Parcelable {
    private static final java.lang.String TAG = "InternalTransformation";

    abstract void apply(android.service.autofill.ValueFinder valueFinder, android.widget.RemoteViews remoteViews, int i) throws java.lang.Exception;

    public static boolean batchApply(android.service.autofill.ValueFinder valueFinder, android.widget.RemoteViews remoteViews, java.util.ArrayList<android.util.Pair<java.lang.Integer, android.service.autofill.InternalTransformation>> arrayList) {
        int size = arrayList.size();
        if (android.view.autofill.Helper.sDebug) {
            android.util.Log.d(TAG, "getPresentation(): applying " + size + " transformations");
        }
        for (int i = 0; i < size; i++) {
            android.util.Pair<java.lang.Integer, android.service.autofill.InternalTransformation> pair = arrayList.get(i);
            int intValue = pair.first.intValue();
            android.service.autofill.InternalTransformation internalTransformation = pair.second;
            if (android.view.autofill.Helper.sDebug) {
                android.util.Log.d(TAG, "#" + i + ": " + internalTransformation);
            }
            try {
                internalTransformation.apply(valueFinder, remoteViews, intValue);
            } catch (java.lang.Exception e) {
                android.util.Log.e(TAG, "Could not apply transformation " + internalTransformation + ": " + e.getClass());
                return false;
            }
        }
        return true;
    }
}
