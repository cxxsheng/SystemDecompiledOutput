package android.text;

/* loaded from: classes3.dex */
public abstract class SegmentFinder {
    public static final int DONE = -1;

    public abstract int nextEndBoundary(int i);

    public abstract int nextStartBoundary(int i);

    public abstract int previousEndBoundary(int i);

    public abstract int previousStartBoundary(int i);

    public static class PrescribedSegmentFinder extends android.text.SegmentFinder {
        private final int[] mSegments;

        public PrescribedSegmentFinder(int[] iArr) {
            checkSegmentsValid(iArr);
            this.mSegments = iArr;
        }

        @Override // android.text.SegmentFinder
        public int previousStartBoundary(int i) {
            return findPrevious(i, true);
        }

        @Override // android.text.SegmentFinder
        public int previousEndBoundary(int i) {
            return findPrevious(i, false);
        }

        @Override // android.text.SegmentFinder
        public int nextStartBoundary(int i) {
            return findNext(i, true);
        }

        @Override // android.text.SegmentFinder
        public int nextEndBoundary(int i) {
            return findNext(i, false);
        }

        private int findNext(int i, boolean z) {
            int i2;
            if (i < 0 || this.mSegments.length < 1 || i > this.mSegments[this.mSegments.length - 1]) {
                return -1;
            }
            if (i < this.mSegments[0]) {
                int[] iArr = this.mSegments;
                return z ? iArr[0] : iArr[1];
            }
            int binarySearch = java.util.Arrays.binarySearch(this.mSegments, i);
            if (binarySearch >= 0) {
                int i3 = binarySearch + 1;
                if (i3 < this.mSegments.length && this.mSegments[i3] == i) {
                    binarySearch = i3;
                }
                i2 = binarySearch + 1;
            } else {
                i2 = -(binarySearch + 1);
            }
            if (i2 >= this.mSegments.length) {
                return -1;
            }
            if (z != (i2 % 2 == 0)) {
                int i4 = i2 + 1;
                if (i4 < this.mSegments.length) {
                    return this.mSegments[i4];
                }
                return -1;
            }
            return this.mSegments[i2];
        }

        private int findPrevious(int i, boolean z) {
            int i2;
            if (this.mSegments.length >= 1) {
                if (i >= this.mSegments[0]) {
                    if (i > this.mSegments[this.mSegments.length - 1]) {
                        int[] iArr = this.mSegments;
                        return z ? iArr[this.mSegments.length - 2] : iArr[this.mSegments.length - 1];
                    }
                    int binarySearch = java.util.Arrays.binarySearch(this.mSegments, i);
                    if (binarySearch >= 0) {
                        if (binarySearch > 0) {
                            int i3 = binarySearch - 1;
                            if (this.mSegments[i3] == i) {
                                binarySearch = i3;
                            }
                        }
                        i2 = binarySearch - 1;
                    } else {
                        i2 = (-(binarySearch + 1)) - 1;
                    }
                    if (i2 < 0) {
                        return -1;
                    }
                    if (z != (i2 % 2 == 0)) {
                        if (i2 > 0) {
                            return this.mSegments[i2 - 1];
                        }
                        return -1;
                    }
                    return this.mSegments[i2];
                }
            }
            return -1;
        }

        private static void checkSegmentsValid(int[] iArr) {
            java.util.Objects.requireNonNull(iArr);
            com.android.internal.util.Preconditions.checkArgument(iArr.length % 2 == 0, "the length of segments must be even");
            if (iArr.length == 0) {
                return;
            }
            int i = Integer.MIN_VALUE;
            for (int i2 = 0; i2 < iArr.length; i2 += 2) {
                if (iArr[i2] < i) {
                    throw new java.lang.IllegalArgumentException("segments can't overlap");
                }
                int i3 = i2 + 1;
                if (iArr[i2] >= iArr[i3]) {
                    throw new java.lang.IllegalArgumentException("the segment range can't be empty");
                }
                i = iArr[i3];
            }
        }
    }
}
