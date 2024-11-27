package com.android.server.pm;

/* loaded from: classes2.dex */
public class InstructionSets {
    private static final java.lang.String PREFERRED_INSTRUCTION_SET = dalvik.system.VMRuntime.getInstructionSet(android.os.Build.SUPPORTED_ABIS[0]);

    public static java.lang.String[] getAppDexInstructionSets(java.lang.String str, java.lang.String str2) {
        if (str != null) {
            if (str2 != null) {
                return new java.lang.String[]{dalvik.system.VMRuntime.getInstructionSet(str), dalvik.system.VMRuntime.getInstructionSet(str2)};
            }
            return new java.lang.String[]{dalvik.system.VMRuntime.getInstructionSet(str)};
        }
        return new java.lang.String[]{getPreferredInstructionSet()};
    }

    public static java.lang.String getPreferredInstructionSet() {
        return PREFERRED_INSTRUCTION_SET;
    }

    public static java.lang.String getDexCodeInstructionSet(java.lang.String str) {
        java.lang.String str2 = android.os.SystemProperties.get("ro.dalvik.vm.isa." + str);
        return android.text.TextUtils.isEmpty(str2) ? str : str2;
    }

    public static java.lang.String[] getDexCodeInstructionSets(java.lang.String[] strArr) {
        android.util.ArraySet arraySet = new android.util.ArraySet(strArr.length);
        for (java.lang.String str : strArr) {
            arraySet.add(getDexCodeInstructionSet(str));
        }
        return (java.lang.String[]) arraySet.toArray(new java.lang.String[arraySet.size()]);
    }

    public static java.lang.String[] getAllDexCodeInstructionSets() {
        int length = android.os.Build.SUPPORTED_ABIS.length;
        java.lang.String[] strArr = new java.lang.String[length];
        for (int i = 0; i < length; i++) {
            strArr[i] = dalvik.system.VMRuntime.getInstructionSet(android.os.Build.SUPPORTED_ABIS[i]);
        }
        return getDexCodeInstructionSets(strArr);
    }

    public static java.util.List<java.lang.String> getAllInstructionSets() {
        java.lang.String[] strArr = android.os.Build.SUPPORTED_ABIS;
        java.util.ArrayList arrayList = new java.util.ArrayList(strArr.length);
        for (java.lang.String str : strArr) {
            java.lang.String instructionSet = dalvik.system.VMRuntime.getInstructionSet(str);
            if (!arrayList.contains(instructionSet)) {
                arrayList.add(instructionSet);
            }
        }
        return arrayList;
    }

    public static java.lang.String getPrimaryInstructionSet(com.android.server.pm.PackageAbiHelper.Abis abis) {
        if (abis.primary == null) {
            return getPreferredInstructionSet();
        }
        return dalvik.system.VMRuntime.getInstructionSet(abis.primary);
    }
}
