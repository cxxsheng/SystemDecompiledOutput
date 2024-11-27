package com.android.server.appbinding;

/* loaded from: classes.dex */
public class AppBindingUtils {
    private static final java.lang.String TAG = "AppBindingUtils";

    private AppBindingUtils() {
    }

    @android.annotation.Nullable
    public static android.content.pm.ServiceInfo findService(@android.annotation.NonNull java.lang.String str, int i, java.lang.String str2, java.lang.String str3, java.lang.Class<?> cls, android.content.pm.IPackageManager iPackageManager, java.lang.StringBuilder sb) {
        java.lang.String simpleName = cls.getSimpleName();
        android.content.Intent intent = new android.content.Intent(str2);
        intent.setPackage(str);
        sb.setLength(0);
        try {
            android.content.pm.ParceledListSlice queryIntentServices = iPackageManager.queryIntentServices(intent, (java.lang.String) null, 0L, i);
            if (queryIntentServices == null || queryIntentServices.getList().size() == 0) {
                sb.append("Service with " + str2 + " not found.");
                return null;
            }
            java.util.List list = queryIntentServices.getList();
            if (list.size() > 1) {
                sb.append("More than one " + simpleName + "'s found in package " + str + ".  They'll all be ignored.");
                android.util.Log.e(TAG, sb.toString());
                return null;
            }
            android.content.pm.ServiceInfo serviceInfo = ((android.content.pm.ResolveInfo) list.get(0)).serviceInfo;
            if (!str3.equals(serviceInfo.permission)) {
                sb.append(simpleName + " " + serviceInfo.getComponentName().flattenToShortString() + " must be protected with " + str3 + ".");
                android.util.Log.e(TAG, sb.toString());
                return null;
            }
            return serviceInfo;
        } catch (android.os.RemoteException e) {
            return null;
        }
    }
}
