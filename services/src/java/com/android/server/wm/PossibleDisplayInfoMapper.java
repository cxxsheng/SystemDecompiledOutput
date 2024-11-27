package com.android.server.wm;

/* loaded from: classes3.dex */
public class PossibleDisplayInfoMapper {
    private static final boolean DEBUG = false;
    private static final java.lang.String TAG = "PossibleDisplayInfoMapper";
    private final android.util.SparseArray<java.util.Set<android.view.DisplayInfo>> mDisplayInfos = new android.util.SparseArray<>();
    private final android.hardware.display.DisplayManagerInternal mDisplayManagerInternal;

    PossibleDisplayInfoMapper(android.hardware.display.DisplayManagerInternal displayManagerInternal) {
        this.mDisplayManagerInternal = displayManagerInternal;
    }

    public java.util.List<android.view.DisplayInfo> getPossibleDisplayInfos(int i) {
        updatePossibleDisplayInfos(i);
        if (!this.mDisplayInfos.contains(i)) {
            return new java.util.ArrayList();
        }
        return java.util.List.copyOf(this.mDisplayInfos.get(i));
    }

    public void updatePossibleDisplayInfos(int i) {
        updateDisplayInfos(this.mDisplayManagerInternal.getPossibleDisplayInfo(i));
    }

    public void removePossibleDisplayInfos(int i) {
        this.mDisplayInfos.remove(i);
    }

    private void updateDisplayInfos(java.util.Set<android.view.DisplayInfo> set) {
        this.mDisplayInfos.clear();
        for (android.view.DisplayInfo displayInfo : set) {
            java.util.Set<android.view.DisplayInfo> set2 = this.mDisplayInfos.get(displayInfo.displayId, new android.util.ArraySet());
            set2.add(displayInfo);
            this.mDisplayInfos.put(displayInfo.displayId, set2);
        }
    }
}
