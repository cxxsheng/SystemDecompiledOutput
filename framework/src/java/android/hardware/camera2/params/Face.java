package android.hardware.camera2.params;

/* loaded from: classes.dex */
public final class Face {
    public static final int ID_UNSUPPORTED = -1;
    public static final int SCORE_MAX = 100;
    public static final int SCORE_MIN = 1;
    private android.graphics.Rect mBounds;
    private int mId;
    private android.graphics.Point mLeftEye;
    private android.graphics.Point mMouth;
    private android.graphics.Point mRightEye;
    private int mScore;

    public Face(android.graphics.Rect rect, int i, int i2, android.graphics.Point point, android.graphics.Point point2, android.graphics.Point point3) {
        init(rect, i, i2, point, point2, point3);
    }

    public Face(android.graphics.Rect rect, int i) {
        init(rect, i, -1, null, null, null);
    }

    private void init(android.graphics.Rect rect, int i, int i2, android.graphics.Point point, android.graphics.Point point2, android.graphics.Point point3) {
        checkNotNull("bounds", rect);
        checkScore(i);
        checkId(i2);
        if (i2 == -1) {
            checkNull("leftEyePosition", point);
            checkNull("rightEyePosition", point2);
            checkNull("mouthPosition", point3);
        }
        checkFace(point, point2, point3);
        this.mBounds = rect;
        this.mScore = i;
        this.mId = i2;
        this.mLeftEye = point;
        this.mRightEye = point2;
        this.mMouth = point3;
    }

    public android.graphics.Rect getBounds() {
        return this.mBounds;
    }

    public int getScore() {
        return this.mScore;
    }

    public int getId() {
        return this.mId;
    }

    public android.graphics.Point getLeftEyePosition() {
        return this.mLeftEye;
    }

    public android.graphics.Point getRightEyePosition() {
        return this.mRightEye;
    }

    public android.graphics.Point getMouthPosition() {
        return this.mMouth;
    }

    public java.lang.String toString() {
        return java.lang.String.format("{ bounds: %s, score: %s, id: %d, leftEyePosition: %s, rightEyePosition: %s, mouthPosition: %s }", this.mBounds, java.lang.Integer.valueOf(this.mScore), java.lang.Integer.valueOf(this.mId), this.mLeftEye, this.mRightEye, this.mMouth);
    }

    private static void checkNotNull(java.lang.String str, java.lang.Object obj) {
        if (obj == null) {
            throw new java.lang.IllegalArgumentException(str + " was required, but it was null");
        }
    }

    private static void checkNull(java.lang.String str, java.lang.Object obj) {
        if (obj != null) {
            throw new java.lang.IllegalArgumentException(str + " was required to be null, but it wasn't");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void checkScore(int i) {
        if (i < 1 || i > 100) {
            throw new java.lang.IllegalArgumentException("Confidence out of range");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void checkId(int i) {
        if (i < 0 && i != -1) {
            throw new java.lang.IllegalArgumentException("Id out of range");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void checkFace(android.graphics.Point point, android.graphics.Point point2, android.graphics.Point point3) {
        if (point != null || point2 != null || point3 != null) {
            if (point == null || point2 == null || point3 == null) {
                throw new java.lang.IllegalArgumentException("If any of leftEyePosition, rightEyePosition, or mouthPosition are non-null, all three must be non-null.");
            }
        }
    }

    public static final class Builder {
        private static final long FIELD_BOUNDS = 2;
        private static final long FIELD_BUILT = 1;
        private static final long FIELD_ID = 8;
        private static final long FIELD_LEFT_EYE = 16;
        private static final long FIELD_MOUTH = 64;
        private static final java.lang.String FIELD_NAME_BOUNDS = "bounds";
        private static final java.lang.String FIELD_NAME_LEFT_EYE = "left eye";
        private static final java.lang.String FIELD_NAME_MOUTH = "mouth";
        private static final java.lang.String FIELD_NAME_RIGHT_EYE = "right eye";
        private static final java.lang.String FIELD_NAME_SCORE = "score";
        private static final long FIELD_RIGHT_EYE = 32;
        private static final long FIELD_SCORE = 4;
        private android.graphics.Rect mBounds;
        private long mBuilderFieldsSet;
        private int mId;
        private android.graphics.Point mLeftEye;
        private android.graphics.Point mMouth;
        private android.graphics.Point mRightEye;
        private int mScore;

        public Builder() {
            this.mBuilderFieldsSet = 0L;
            this.mBounds = null;
            this.mScore = 0;
            this.mId = -1;
            this.mLeftEye = null;
            this.mRightEye = null;
            this.mMouth = null;
        }

        public Builder(android.hardware.camera2.params.Face face) {
            this.mBuilderFieldsSet = 0L;
            this.mBounds = null;
            this.mScore = 0;
            this.mId = -1;
            this.mLeftEye = null;
            this.mRightEye = null;
            this.mMouth = null;
            this.mBounds = face.mBounds;
            this.mScore = face.mScore;
            this.mId = face.mId;
            this.mLeftEye = face.mLeftEye;
            this.mRightEye = face.mRightEye;
            this.mMouth = face.mMouth;
        }

        public android.hardware.camera2.params.Face.Builder setBounds(android.graphics.Rect rect) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 2;
            this.mBounds = rect;
            return this;
        }

        public android.hardware.camera2.params.Face.Builder setScore(int i) {
            checkNotUsed();
            android.hardware.camera2.params.Face.checkScore(i);
            this.mBuilderFieldsSet |= 4;
            this.mScore = i;
            return this;
        }

        public android.hardware.camera2.params.Face.Builder setId(int i) {
            checkNotUsed();
            android.hardware.camera2.params.Face.checkId(i);
            this.mBuilderFieldsSet |= 8;
            this.mId = i;
            return this;
        }

        public android.hardware.camera2.params.Face.Builder setLeftEyePosition(android.graphics.Point point) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 16;
            this.mLeftEye = point;
            return this;
        }

        public android.hardware.camera2.params.Face.Builder setRightEyePosition(android.graphics.Point point) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 32;
            this.mRightEye = point;
            return this;
        }

        public android.hardware.camera2.params.Face.Builder setMouthPosition(android.graphics.Point point) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 64;
            this.mMouth = point;
            return this;
        }

        public android.hardware.camera2.params.Face build() {
            checkNotUsed();
            checkFieldSet(2L, FIELD_NAME_BOUNDS);
            checkFieldSet(4L, FIELD_NAME_SCORE);
            if (this.mId == -1) {
                checkIdUnsupportedThenNull(this.mLeftEye, FIELD_NAME_LEFT_EYE);
                checkIdUnsupportedThenNull(this.mRightEye, FIELD_NAME_RIGHT_EYE);
                checkIdUnsupportedThenNull(this.mMouth, FIELD_NAME_MOUTH);
            }
            android.hardware.camera2.params.Face.checkFace(this.mLeftEye, this.mRightEye, this.mMouth);
            this.mBuilderFieldsSet |= 1;
            return new android.hardware.camera2.params.Face(this.mBounds, this.mScore, this.mId, this.mLeftEye, this.mRightEye, this.mMouth);
        }

        private void checkNotUsed() {
            if ((this.mBuilderFieldsSet & 1) != 0) {
                throw new java.lang.IllegalStateException("This Builder should not be reused. Use a new Builder instance instead");
            }
        }

        private void checkFieldSet(long j, java.lang.String str) {
            if ((j & this.mBuilderFieldsSet) == 0) {
                throw new java.lang.IllegalStateException("Field \"" + str + "\" must be set before building.");
            }
        }

        private void checkIdUnsupportedThenNull(java.lang.Object obj, java.lang.String str) {
            if (obj != null) {
                throw new java.lang.IllegalArgumentException("Field \"" + str + "\" must be unset or null if id is ID_UNSUPPORTED.");
            }
        }
    }
}
