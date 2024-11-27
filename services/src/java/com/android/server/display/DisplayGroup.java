package com.android.server.display;

/* loaded from: classes.dex */
public class DisplayGroup {
    private int mChangeCount;
    private final java.util.List<com.android.server.display.LogicalDisplay> mDisplays = new java.util.ArrayList();
    private final int mGroupId;

    DisplayGroup(int i) {
        this.mGroupId = i;
    }

    int getGroupId() {
        return this.mGroupId;
    }

    void addDisplayLocked(com.android.server.display.LogicalDisplay logicalDisplay) {
        if (!containsLocked(logicalDisplay)) {
            this.mChangeCount++;
            this.mDisplays.add(logicalDisplay);
        }
    }

    boolean containsLocked(com.android.server.display.LogicalDisplay logicalDisplay) {
        return this.mDisplays.contains(logicalDisplay);
    }

    boolean removeDisplayLocked(com.android.server.display.LogicalDisplay logicalDisplay) {
        this.mChangeCount++;
        return this.mDisplays.remove(logicalDisplay);
    }

    boolean isEmptyLocked() {
        return this.mDisplays.isEmpty();
    }

    int getChangeCountLocked() {
        return this.mChangeCount;
    }

    int getSizeLocked() {
        return this.mDisplays.size();
    }

    int getIdLocked(int i) {
        return this.mDisplays.get(i).getDisplayIdLocked();
    }
}
