package com.android.server.wm;

/* loaded from: classes3.dex */
class AnimatingActivityRegistry {
    private boolean mEndingDeferredFinish;
    private android.util.ArraySet<com.android.server.wm.ActivityRecord> mAnimatingActivities = new android.util.ArraySet<>();
    private android.util.ArrayMap<com.android.server.wm.ActivityRecord, java.lang.Runnable> mFinishedTokens = new android.util.ArrayMap<>();
    private java.util.ArrayList<java.lang.Runnable> mTmpRunnableList = new java.util.ArrayList<>();

    AnimatingActivityRegistry() {
    }

    void notifyStarting(com.android.server.wm.ActivityRecord activityRecord) {
        this.mAnimatingActivities.add(activityRecord);
    }

    void notifyFinished(com.android.server.wm.ActivityRecord activityRecord) {
        this.mAnimatingActivities.remove(activityRecord);
        this.mFinishedTokens.remove(activityRecord);
        if (this.mAnimatingActivities.isEmpty()) {
            endDeferringFinished();
        }
    }

    boolean notifyAboutToFinish(com.android.server.wm.ActivityRecord activityRecord, java.lang.Runnable runnable) {
        if (!this.mAnimatingActivities.remove(activityRecord)) {
            return false;
        }
        if (this.mAnimatingActivities.isEmpty()) {
            endDeferringFinished();
            return false;
        }
        this.mFinishedTokens.put(activityRecord, runnable);
        return true;
    }

    private void endDeferringFinished() {
        if (this.mEndingDeferredFinish) {
            return;
        }
        try {
            this.mEndingDeferredFinish = true;
            for (int size = this.mFinishedTokens.size() - 1; size >= 0; size--) {
                this.mTmpRunnableList.add(this.mFinishedTokens.valueAt(size));
            }
            this.mFinishedTokens.clear();
            for (int size2 = this.mTmpRunnableList.size() - 1; size2 >= 0; size2--) {
                this.mTmpRunnableList.get(size2).run();
            }
            this.mTmpRunnableList.clear();
            this.mEndingDeferredFinish = false;
        } catch (java.lang.Throwable th) {
            this.mEndingDeferredFinish = false;
            throw th;
        }
    }

    void dump(java.io.PrintWriter printWriter, java.lang.String str, java.lang.String str2) {
        if (!this.mAnimatingActivities.isEmpty() || !this.mFinishedTokens.isEmpty()) {
            printWriter.print(str2);
            printWriter.println(str);
            java.lang.String str3 = str2 + "  ";
            printWriter.print(str3);
            printWriter.print("mAnimatingActivities=");
            printWriter.println(this.mAnimatingActivities);
            printWriter.print(str3);
            printWriter.print("mFinishedTokens=");
            printWriter.println(this.mFinishedTokens);
        }
    }
}
