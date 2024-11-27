package android.accessibilityservice;

/* loaded from: classes.dex */
public final class GestureDescription {
    private static final long MAX_GESTURE_DURATION_MS = 60000;
    private static final int MAX_STROKE_COUNT = 20;
    private final int mDisplayId;
    private final java.util.List<android.accessibilityservice.GestureDescription.StrokeDescription> mStrokes;
    private final float[] mTempPos;

    public static int getMaxStrokeCount() {
        return 20;
    }

    public static long getMaxGestureDuration() {
        return 60000L;
    }

    private GestureDescription() {
        this(new java.util.ArrayList());
    }

    private GestureDescription(java.util.List<android.accessibilityservice.GestureDescription.StrokeDescription> list) {
        this(list, 0);
    }

    private GestureDescription(java.util.List<android.accessibilityservice.GestureDescription.StrokeDescription> list, int i) {
        this.mStrokes = new java.util.ArrayList();
        this.mTempPos = new float[2];
        this.mStrokes.addAll(list);
        this.mDisplayId = i;
    }

    public int getStrokeCount() {
        return this.mStrokes.size();
    }

    public android.accessibilityservice.GestureDescription.StrokeDescription getStroke(int i) {
        return this.mStrokes.get(i);
    }

    public int getDisplayId() {
        return this.mDisplayId;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public long getNextKeyPointAtLeast(long j) {
        long j2 = Long.MAX_VALUE;
        for (int i = 0; i < this.mStrokes.size(); i++) {
            long j3 = this.mStrokes.get(i).mStartTime;
            if (j3 < j2 && j3 >= j) {
                j2 = j3;
            }
            long j4 = this.mStrokes.get(i).mEndTime;
            if (j4 < j2 && j4 >= j) {
                j2 = j4;
            }
        }
        if (j2 == Long.MAX_VALUE) {
            return -1L;
        }
        return j2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getPointsForTime(long j, android.accessibilityservice.GestureDescription.TouchPoint[] touchPointArr) {
        int i = 0;
        for (int i2 = 0; i2 < this.mStrokes.size(); i2++) {
            android.accessibilityservice.GestureDescription.StrokeDescription strokeDescription = this.mStrokes.get(i2);
            if (strokeDescription.hasPointForTime(j)) {
                touchPointArr[i].mStrokeId = strokeDescription.getId();
                touchPointArr[i].mContinuedStrokeId = strokeDescription.getContinuedStrokeId();
                touchPointArr[i].mIsStartOfPath = strokeDescription.getContinuedStrokeId() < 0 && j == strokeDescription.mStartTime;
                touchPointArr[i].mIsEndOfPath = !strokeDescription.willContinue() && j == strokeDescription.mEndTime;
                strokeDescription.getPosForTime(j, this.mTempPos);
                touchPointArr[i].mX = java.lang.Math.round(this.mTempPos[0]);
                touchPointArr[i].mY = java.lang.Math.round(this.mTempPos[1]);
                i++;
            }
        }
        return i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static long getTotalDuration(java.util.List<android.accessibilityservice.GestureDescription.StrokeDescription> list) {
        long j = Long.MIN_VALUE;
        for (int i = 0; i < list.size(); i++) {
            j = java.lang.Math.max(j, list.get(i).mEndTime);
        }
        return java.lang.Math.max(j, 0L);
    }

    public static class Builder {
        private final java.util.List<android.accessibilityservice.GestureDescription.StrokeDescription> mStrokes = new java.util.ArrayList();
        private int mDisplayId = 0;

        public android.accessibilityservice.GestureDescription.Builder addStroke(android.accessibilityservice.GestureDescription.StrokeDescription strokeDescription) {
            if (this.mStrokes.size() >= 20) {
                throw new java.lang.IllegalStateException("Attempting to add too many strokes to a gesture. Maximum is 20, got " + this.mStrokes.size());
            }
            this.mStrokes.add(strokeDescription);
            if (android.accessibilityservice.GestureDescription.getTotalDuration(this.mStrokes) > 60000) {
                this.mStrokes.remove(strokeDescription);
                throw new java.lang.IllegalStateException("Gesture would exceed maximum duration with new stroke");
            }
            return this;
        }

        public android.accessibilityservice.GestureDescription.Builder setDisplayId(int i) {
            this.mDisplayId = i;
            return this;
        }

        public android.accessibilityservice.GestureDescription build() {
            if (this.mStrokes.size() == 0) {
                throw new java.lang.IllegalStateException("Gestures must have at least one stroke");
            }
            return new android.accessibilityservice.GestureDescription(this.mStrokes, this.mDisplayId);
        }
    }

    public static class StrokeDescription {
        private static final int INVALID_STROKE_ID = -1;
        static int sIdCounter;
        boolean mContinued;
        int mContinuedStrokeId;
        long mEndTime;
        int mId;
        android.graphics.Path mPath;
        private android.graphics.PathMeasure mPathMeasure;
        long mStartTime;
        float[] mTapLocation;
        private float mTimeToLengthConversion;

        public StrokeDescription(android.graphics.Path path, long j, long j2) {
            this(path, j, j2, false);
        }

        public StrokeDescription(android.graphics.Path path, long j, long j2, boolean z) {
            this.mContinuedStrokeId = -1;
            this.mContinued = z;
            com.android.internal.util.Preconditions.checkArgument(j2 > 0, "Duration must be positive");
            com.android.internal.util.Preconditions.checkArgument(j >= 0, "Start time must not be negative");
            com.android.internal.util.Preconditions.checkArgument(!path.isEmpty(), "Path is empty");
            android.graphics.RectF rectF = new android.graphics.RectF();
            path.computeBounds(rectF, false);
            com.android.internal.util.Preconditions.checkArgument(rectF.bottom >= 0.0f && rectF.top >= 0.0f && rectF.right >= 0.0f && rectF.left >= 0.0f, "Path bounds must not be negative");
            this.mPath = new android.graphics.Path(path);
            this.mPathMeasure = new android.graphics.PathMeasure(path, false);
            if (this.mPathMeasure.getLength() == 0.0f) {
                android.graphics.Path path2 = new android.graphics.Path(path);
                path2.lineTo(-1.0f, -1.0f);
                this.mTapLocation = new float[2];
                new android.graphics.PathMeasure(path2, false).getPosTan(0.0f, this.mTapLocation, null);
            }
            if (this.mPathMeasure.nextContour()) {
                throw new java.lang.IllegalArgumentException("Path has more than one contour");
            }
            this.mPathMeasure.setPath(this.mPath, false);
            this.mStartTime = j;
            this.mEndTime = j + j2;
            this.mTimeToLengthConversion = getLength() / j2;
            int i = sIdCounter;
            sIdCounter = i + 1;
            this.mId = i;
        }

        public android.graphics.Path getPath() {
            return new android.graphics.Path(this.mPath);
        }

        public long getStartTime() {
            return this.mStartTime;
        }

        public long getDuration() {
            return this.mEndTime - this.mStartTime;
        }

        public int getId() {
            return this.mId;
        }

        public android.accessibilityservice.GestureDescription.StrokeDescription continueStroke(android.graphics.Path path, long j, long j2, boolean z) {
            if (!this.mContinued) {
                throw new java.lang.IllegalStateException("Only strokes marked willContinue can be continued");
            }
            android.accessibilityservice.GestureDescription.StrokeDescription strokeDescription = new android.accessibilityservice.GestureDescription.StrokeDescription(path, j, j2, z);
            strokeDescription.mContinuedStrokeId = this.mId;
            return strokeDescription;
        }

        public boolean willContinue() {
            return this.mContinued;
        }

        public int getContinuedStrokeId() {
            return this.mContinuedStrokeId;
        }

        float getLength() {
            return this.mPathMeasure.getLength();
        }

        boolean getPosForTime(long j, float[] fArr) {
            if (this.mTapLocation != null) {
                fArr[0] = this.mTapLocation[0];
                fArr[1] = this.mTapLocation[1];
                return true;
            }
            if (j == this.mEndTime) {
                return this.mPathMeasure.getPosTan(getLength(), fArr, null);
            }
            return this.mPathMeasure.getPosTan(this.mTimeToLengthConversion * (j - this.mStartTime), fArr, null);
        }

        boolean hasPointForTime(long j) {
            return j >= this.mStartTime && j <= this.mEndTime;
        }
    }

    public static class TouchPoint implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.accessibilityservice.GestureDescription.TouchPoint> CREATOR = new android.os.Parcelable.Creator<android.accessibilityservice.GestureDescription.TouchPoint>() { // from class: android.accessibilityservice.GestureDescription.TouchPoint.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.accessibilityservice.GestureDescription.TouchPoint createFromParcel(android.os.Parcel parcel) {
                return new android.accessibilityservice.GestureDescription.TouchPoint(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.accessibilityservice.GestureDescription.TouchPoint[] newArray(int i) {
                return new android.accessibilityservice.GestureDescription.TouchPoint[i];
            }
        };
        private static final int FLAG_IS_END_OF_PATH = 2;
        private static final int FLAG_IS_START_OF_PATH = 1;
        public int mContinuedStrokeId;
        public boolean mIsEndOfPath;
        public boolean mIsStartOfPath;
        public int mStrokeId;
        public float mX;
        public float mY;

        public TouchPoint() {
        }

        public TouchPoint(android.accessibilityservice.GestureDescription.TouchPoint touchPoint) {
            copyFrom(touchPoint);
        }

        public TouchPoint(android.os.Parcel parcel) {
            this.mStrokeId = parcel.readInt();
            this.mContinuedStrokeId = parcel.readInt();
            int readInt = parcel.readInt();
            this.mIsStartOfPath = (readInt & 1) != 0;
            this.mIsEndOfPath = (readInt & 2) != 0;
            this.mX = parcel.readFloat();
            this.mY = parcel.readFloat();
        }

        public void copyFrom(android.accessibilityservice.GestureDescription.TouchPoint touchPoint) {
            this.mStrokeId = touchPoint.mStrokeId;
            this.mContinuedStrokeId = touchPoint.mContinuedStrokeId;
            this.mIsStartOfPath = touchPoint.mIsStartOfPath;
            this.mIsEndOfPath = touchPoint.mIsEndOfPath;
            this.mX = touchPoint.mX;
            this.mY = touchPoint.mY;
        }

        public java.lang.String toString() {
            return "TouchPoint{mStrokeId=" + this.mStrokeId + ", mContinuedStrokeId=" + this.mContinuedStrokeId + ", mIsStartOfPath=" + this.mIsStartOfPath + ", mIsEndOfPath=" + this.mIsEndOfPath + ", mX=" + this.mX + ", mY=" + this.mY + '}';
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeInt(this.mStrokeId);
            parcel.writeInt(this.mContinuedStrokeId);
            parcel.writeInt((this.mIsStartOfPath ? 1 : 0) | (this.mIsEndOfPath ? 2 : 0));
            parcel.writeFloat(this.mX);
            parcel.writeFloat(this.mY);
        }
    }

    public static class GestureStep implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.accessibilityservice.GestureDescription.GestureStep> CREATOR = new android.os.Parcelable.Creator<android.accessibilityservice.GestureDescription.GestureStep>() { // from class: android.accessibilityservice.GestureDescription.GestureStep.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.accessibilityservice.GestureDescription.GestureStep createFromParcel(android.os.Parcel parcel) {
                return new android.accessibilityservice.GestureDescription.GestureStep(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.accessibilityservice.GestureDescription.GestureStep[] newArray(int i) {
                return new android.accessibilityservice.GestureDescription.GestureStep[i];
            }
        };
        public int numTouchPoints;
        public long timeSinceGestureStart;
        public android.accessibilityservice.GestureDescription.TouchPoint[] touchPoints;

        public GestureStep(long j, int i, android.accessibilityservice.GestureDescription.TouchPoint[] touchPointArr) {
            this.timeSinceGestureStart = j;
            this.numTouchPoints = i;
            this.touchPoints = new android.accessibilityservice.GestureDescription.TouchPoint[i];
            for (int i2 = 0; i2 < i; i2++) {
                this.touchPoints[i2] = new android.accessibilityservice.GestureDescription.TouchPoint(touchPointArr[i2]);
            }
        }

        public GestureStep(android.os.Parcel parcel) {
            this.timeSinceGestureStart = parcel.readLong();
            android.os.Parcelable[] parcelableArr = (android.os.Parcelable[]) parcel.readParcelableArray(android.accessibilityservice.GestureDescription.TouchPoint.class.getClassLoader(), android.accessibilityservice.GestureDescription.TouchPoint.class);
            this.numTouchPoints = parcelableArr == null ? 0 : parcelableArr.length;
            this.touchPoints = new android.accessibilityservice.GestureDescription.TouchPoint[this.numTouchPoints];
            for (int i = 0; i < this.numTouchPoints; i++) {
                this.touchPoints[i] = (android.accessibilityservice.GestureDescription.TouchPoint) parcelableArr[i];
            }
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeLong(this.timeSinceGestureStart);
            parcel.writeParcelableArray(this.touchPoints, i);
        }
    }

    public static class MotionEventGenerator {
        private static android.accessibilityservice.GestureDescription.TouchPoint[] sCurrentTouchPoints;

        public static java.util.List<android.accessibilityservice.GestureDescription.GestureStep> getGestureStepsFromGestureDescription(android.accessibilityservice.GestureDescription gestureDescription, int i) {
            java.util.ArrayList arrayList = new java.util.ArrayList();
            android.accessibilityservice.GestureDescription.TouchPoint[] currentTouchPoints = getCurrentTouchPoints(gestureDescription.getStrokeCount());
            long nextKeyPointAtLeast = gestureDescription.getNextKeyPointAtLeast(0L);
            int i2 = 0;
            long j = 0;
            while (nextKeyPointAtLeast >= 0) {
                if (i2 != 0) {
                    nextKeyPointAtLeast = java.lang.Math.min(nextKeyPointAtLeast, j + i);
                }
                j = nextKeyPointAtLeast;
                i2 = gestureDescription.getPointsForTime(j, currentTouchPoints);
                arrayList.add(new android.accessibilityservice.GestureDescription.GestureStep(j, i2, currentTouchPoints));
                nextKeyPointAtLeast = gestureDescription.getNextKeyPointAtLeast(1 + j);
            }
            return arrayList;
        }

        private static android.accessibilityservice.GestureDescription.TouchPoint[] getCurrentTouchPoints(int i) {
            if (sCurrentTouchPoints == null || sCurrentTouchPoints.length < i) {
                sCurrentTouchPoints = new android.accessibilityservice.GestureDescription.TouchPoint[i];
                for (int i2 = 0; i2 < i; i2++) {
                    sCurrentTouchPoints[i2] = new android.accessibilityservice.GestureDescription.TouchPoint();
                }
            }
            return sCurrentTouchPoints;
        }
    }
}
