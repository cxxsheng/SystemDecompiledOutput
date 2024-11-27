package com.android.server;

/* loaded from: classes.dex */
public class HardwarePropertiesManagerService extends android.os.IHardwarePropertiesManager.Stub {
    private static final java.lang.String TAG = "HardwarePropertiesManagerService";
    private final android.app.AppOpsManager mAppOps;
    private final android.content.Context mContext;
    private final java.lang.Object mLock = new java.lang.Object();

    private static native android.os.CpuUsageInfo[] nativeGetCpuUsages();

    private static native float[] nativeGetDeviceTemperatures(int i, int i2);

    private static native float[] nativeGetFanSpeeds();

    private static native void nativeInit();

    public HardwarePropertiesManagerService(android.content.Context context) {
        this.mContext = context;
        this.mAppOps = (android.app.AppOpsManager) this.mContext.getSystemService("appops");
        synchronized (this.mLock) {
            nativeInit();
        }
    }

    public float[] getDeviceTemperatures(java.lang.String str, int i, int i2) throws java.lang.SecurityException {
        float[] nativeGetDeviceTemperatures;
        enforceHardwarePropertiesRetrievalAllowed(str);
        synchronized (this.mLock) {
            nativeGetDeviceTemperatures = nativeGetDeviceTemperatures(i, i2);
        }
        return nativeGetDeviceTemperatures;
    }

    public android.os.CpuUsageInfo[] getCpuUsages(java.lang.String str) throws java.lang.SecurityException {
        android.os.CpuUsageInfo[] nativeGetCpuUsages;
        enforceHardwarePropertiesRetrievalAllowed(str);
        synchronized (this.mLock) {
            nativeGetCpuUsages = nativeGetCpuUsages();
        }
        return nativeGetCpuUsages;
    }

    public float[] getFanSpeeds(java.lang.String str) throws java.lang.SecurityException {
        float[] nativeGetFanSpeeds;
        enforceHardwarePropertiesRetrievalAllowed(str);
        synchronized (this.mLock) {
            nativeGetFanSpeeds = nativeGetFanSpeeds();
        }
        return nativeGetFanSpeeds;
    }

    private java.lang.String getCallingPackageName() {
        android.content.pm.PackageManager packageManager = this.mContext.getPackageManager();
        int callingUid = android.os.Binder.getCallingUid();
        java.lang.String[] packagesForUid = packageManager.getPackagesForUid(callingUid);
        if (packagesForUid != null && packagesForUid.length > 0) {
            return packagesForUid[0];
        }
        java.lang.String nameForUid = packageManager.getNameForUid(callingUid);
        if (nameForUid != null) {
            return nameForUid;
        }
        return java.lang.String.valueOf(callingUid);
    }

    private void dumpTempValues(java.lang.String str, java.io.PrintWriter printWriter, int i, java.lang.String str2) {
        dumpTempValues(str, printWriter, i, str2, "temperatures: ", 0);
        dumpTempValues(str, printWriter, i, str2, "throttling temperatures: ", 1);
        dumpTempValues(str, printWriter, i, str2, "shutdown temperatures: ", 2);
        dumpTempValues(str, printWriter, i, str2, "vr throttling temperatures: ", 3);
    }

    private void dumpTempValues(java.lang.String str, java.io.PrintWriter printWriter, int i, java.lang.String str2, java.lang.String str3, int i2) {
        printWriter.println(str2 + str3 + java.util.Arrays.toString(getDeviceTemperatures(str, i, i2)));
    }

    protected void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        if (com.android.internal.util.DumpUtils.checkDumpPermission(this.mContext, TAG, printWriter)) {
            printWriter.println("****** Dump of HardwarePropertiesManagerService ******");
            java.lang.String callingPackageName = getCallingPackageName();
            dumpTempValues(callingPackageName, printWriter, 0, "CPU ");
            dumpTempValues(callingPackageName, printWriter, 1, "GPU ");
            dumpTempValues(callingPackageName, printWriter, 2, "Battery ");
            dumpTempValues(callingPackageName, printWriter, 3, "Skin ");
            printWriter.println("Fan speed: " + java.util.Arrays.toString(getFanSpeeds(callingPackageName)) + "\n");
            android.os.CpuUsageInfo[] cpuUsages = getCpuUsages(callingPackageName);
            for (int i = 0; i < cpuUsages.length; i++) {
                printWriter.println("Cpu usage of core: " + i + ", active = " + cpuUsages[i].getActive() + ", total = " + cpuUsages[i].getTotal());
            }
            printWriter.println("****** End of HardwarePropertiesManagerService dump ******");
        }
    }

    private void enforceHardwarePropertiesRetrievalAllowed(java.lang.String str) throws java.lang.SecurityException {
        this.mAppOps.checkPackage(android.os.Binder.getCallingUid(), str);
        int userId = android.os.UserHandle.getUserId(android.os.Binder.getCallingUid());
        com.android.server.vr.VrManagerInternal vrManagerInternal = (com.android.server.vr.VrManagerInternal) com.android.server.LocalServices.getService(com.android.server.vr.VrManagerInternal.class);
        if (!((android.app.admin.DevicePolicyManager) this.mContext.getSystemService(android.app.admin.DevicePolicyManager.class)).isDeviceOwnerApp(str) && this.mContext.checkCallingOrSelfPermission("android.permission.DEVICE_POWER") != 0) {
            if (vrManagerInternal == null || !vrManagerInternal.isCurrentVrListener(str, userId)) {
                throw new java.lang.SecurityException("The caller is neither a device owner, nor holding the DEVICE_POWER permission, nor the current VrListener.");
            }
        }
    }
}
