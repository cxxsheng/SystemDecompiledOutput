package android.hardware.camera2.impl;

/* loaded from: classes.dex */
public class FrameNumberTracker {
    private static final java.lang.String TAG = "FrameNumberTracker";
    private long[] mCompletedFrameNumber = new long[3];
    private final java.util.LinkedList<java.lang.Long>[] mPendingFrameNumbersWithOtherType = new java.util.LinkedList[3];
    private final java.util.LinkedList<java.lang.Long>[] mPendingFrameNumbers = new java.util.LinkedList[3];
    private final java.util.TreeMap<java.lang.Long, java.lang.Integer> mFutureErrorMap = new java.util.TreeMap<>();
    private final java.util.HashMap<java.lang.Long, java.util.List<android.hardware.camera2.CaptureResult>> mPartialResults = new java.util.HashMap<>();

    public FrameNumberTracker() {
        for (int i = 0; i < 3; i++) {
            this.mCompletedFrameNumber[i] = -1;
            this.mPendingFrameNumbersWithOtherType[i] = new java.util.LinkedList<>();
            this.mPendingFrameNumbers[i] = new java.util.LinkedList<>();
        }
    }

    private void update() {
        java.util.Iterator<java.util.Map.Entry<java.lang.Long, java.lang.Integer>> it = this.mFutureErrorMap.entrySet().iterator();
        while (it.hasNext()) {
            java.util.Map.Entry<java.lang.Long, java.lang.Integer> next = it.next();
            long longValue = next.getKey().longValue();
            int intValue = next.getValue().intValue();
            java.lang.Boolean bool = false;
            if (longValue == this.mCompletedFrameNumber[intValue] + 1) {
                bool = true;
            }
            if (this.mPendingFrameNumbers[intValue].isEmpty()) {
                int i = 1;
                while (true) {
                    if (i >= 3) {
                        break;
                    }
                    int i2 = (intValue + i) % 3;
                    if (this.mPendingFrameNumbersWithOtherType[i2].isEmpty() || longValue != this.mPendingFrameNumbersWithOtherType[i2].element().longValue()) {
                        i++;
                    } else {
                        this.mPendingFrameNumbersWithOtherType[i2].remove();
                        bool = true;
                        break;
                    }
                }
                if (!bool.booleanValue()) {
                    int i3 = (intValue + 1) % 3;
                    int i4 = (intValue + 2) % 3;
                    if (this.mPendingFrameNumbersWithOtherType[i3].isEmpty() && this.mPendingFrameNumbersWithOtherType[i4].isEmpty()) {
                        long max = java.lang.Math.max(this.mCompletedFrameNumber[i3], this.mCompletedFrameNumber[i4]) + 1;
                        if (max > this.mCompletedFrameNumber[intValue] + 1 && max == longValue) {
                            bool = true;
                        }
                    }
                }
            } else if (longValue == this.mPendingFrameNumbers[intValue].element().longValue()) {
                this.mPendingFrameNumbers[intValue].remove();
                bool = true;
            }
            if (bool.booleanValue()) {
                this.mCompletedFrameNumber[intValue] = longValue;
                this.mPartialResults.remove(java.lang.Long.valueOf(longValue));
                it.remove();
            }
        }
    }

    public void updateTracker(long j, boolean z, int i) {
        if (z) {
            this.mFutureErrorMap.put(java.lang.Long.valueOf(j), java.lang.Integer.valueOf(i));
        } else {
            try {
                updateCompletedFrameNumber(j, i);
            } catch (java.lang.IllegalArgumentException e) {
                android.util.Log.e(TAG, e.getMessage());
            }
        }
        update();
    }

    public void updateTracker(long j, android.hardware.camera2.CaptureResult captureResult, boolean z, int i) {
        if (!z) {
            updateTracker(j, false, i);
            return;
        }
        if (captureResult == null) {
            return;
        }
        java.util.List<android.hardware.camera2.CaptureResult> list = this.mPartialResults.get(java.lang.Long.valueOf(j));
        if (list == null) {
            list = new java.util.ArrayList<>();
            this.mPartialResults.put(java.lang.Long.valueOf(j), list);
        }
        list.add(captureResult);
    }

    public java.util.List<android.hardware.camera2.CaptureResult> popPartialResults(long j) {
        return this.mPartialResults.remove(java.lang.Long.valueOf(j));
    }

    public long getCompletedFrameNumber() {
        return this.mCompletedFrameNumber[0];
    }

    public long getCompletedReprocessFrameNumber() {
        return this.mCompletedFrameNumber[1];
    }

    public long getCompletedZslStillFrameNumber() {
        return this.mCompletedFrameNumber[2];
    }

    private void updateCompletedFrameNumber(long j, int i) throws java.lang.IllegalArgumentException {
        java.util.LinkedList<java.lang.Long> linkedList;
        java.util.LinkedList<java.lang.Long> linkedList2;
        if (j <= this.mCompletedFrameNumber[i]) {
            throw new java.lang.IllegalArgumentException("frame number " + j + " is a repeat");
        }
        int i2 = (i + 1) % 3;
        int i3 = (i + 2) % 3;
        long max = java.lang.Math.max(this.mCompletedFrameNumber[i2], this.mCompletedFrameNumber[i3]);
        if (j < max) {
            if (!this.mPendingFrameNumbers[i].isEmpty()) {
                java.lang.Long element = this.mPendingFrameNumbers[i].element();
                if (j != element.longValue()) {
                    if (j < element.longValue()) {
                        throw new java.lang.IllegalArgumentException("frame number " + j + " is a repeat");
                    }
                    throw new java.lang.IllegalArgumentException("frame number " + j + " comes out of order. Expecting " + element);
                }
                this.mPendingFrameNumbers[i].remove();
            } else {
                int indexOf = this.mPendingFrameNumbersWithOtherType[i2].indexOf(java.lang.Long.valueOf(j));
                int indexOf2 = this.mPendingFrameNumbersWithOtherType[i3].indexOf(java.lang.Long.valueOf(j));
                boolean z = indexOf != -1;
                if (!(z ^ (indexOf2 != -1))) {
                    throw new java.lang.IllegalArgumentException("frame number " + j + " is a repeat or invalid");
                }
                if (z) {
                    linkedList2 = this.mPendingFrameNumbersWithOtherType[i2];
                    linkedList = this.mPendingFrameNumbers[i3];
                } else {
                    java.util.LinkedList<java.lang.Long> linkedList3 = this.mPendingFrameNumbersWithOtherType[i3];
                    linkedList = this.mPendingFrameNumbers[i2];
                    linkedList2 = linkedList3;
                    indexOf = indexOf2;
                }
                for (int i4 = 0; i4 < indexOf; i4++) {
                    linkedList.add(linkedList2.removeFirst());
                }
                linkedList2.remove();
            }
        } else {
            long max2 = java.lang.Math.max(max, this.mCompletedFrameNumber[i]);
            while (true) {
                max2++;
                if (max2 >= j) {
                    break;
                } else {
                    this.mPendingFrameNumbersWithOtherType[i].add(java.lang.Long.valueOf(max2));
                }
            }
        }
        this.mCompletedFrameNumber[i] = j;
    }
}
