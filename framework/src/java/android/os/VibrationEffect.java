package android.os;

/* loaded from: classes3.dex */
public abstract class VibrationEffect implements android.os.Parcelable {
    public static final int DEFAULT_AMPLITUDE = -1;
    public static final int EFFECT_CLICK = 0;
    public static final int EFFECT_DOUBLE_CLICK = 1;
    public static final int EFFECT_HEAVY_CLICK = 5;
    public static final int EFFECT_POP = 4;
    public static final int EFFECT_STRENGTH_LIGHT = 0;
    public static final int EFFECT_STRENGTH_MEDIUM = 1;
    public static final int EFFECT_STRENGTH_STRONG = 2;
    public static final int EFFECT_TEXTURE_TICK = 21;
    public static final int EFFECT_THUD = 3;
    public static final int EFFECT_TICK = 2;
    public static final int MAX_AMPLITUDE = 255;
    private static final long MAX_HAPTIC_FEEDBACK_COMPOSITION_SIZE = 3;
    private static final long MAX_HAPTIC_FEEDBACK_DURATION = 1000;
    private static final float SCALE_GAMMA = 0.65f;
    public static final int[] RINGTONES = {6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};
    public static final android.os.Parcelable.Creator<android.os.VibrationEffect> CREATOR = new android.os.Parcelable.Creator<android.os.VibrationEffect>() { // from class: android.os.VibrationEffect.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.os.VibrationEffect createFromParcel(android.os.Parcel parcel) {
            return new android.os.VibrationEffect.Composed(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.os.VibrationEffect[] newArray(int i) {
            return new android.os.VibrationEffect[i];
        }
    };

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface EffectType {
    }

    public interface Transformation<ParamT> {
        android.os.VibrationEffect transform(android.os.VibrationEffect vibrationEffect, ParamT paramt);
    }

    public abstract android.os.VibrationEffect applyRepeatingIndefinitely(boolean z, int i);

    public abstract boolean areVibrationFeaturesSupported(android.os.VibratorInfo vibratorInfo);

    public abstract long[] computeCreateWaveformOffOnTimingsOrNull();

    public abstract long getDuration();

    public abstract <T extends android.os.VibrationEffect> T resolve(int i);

    public abstract <T extends android.os.VibrationEffect> T scale(float f);

    public abstract java.lang.String toDebugString();

    public abstract void validate();

    public static android.os.VibrationEffect createOneShot(long j, int i) {
        if (i != 0) {
            return createWaveform(new long[]{j}, new int[]{i}, -1);
        }
        throw new java.lang.IllegalArgumentException("amplitude must either be DEFAULT_AMPLITUDE, or between 1 and 255 inclusive (amplitude=" + i + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
    }

    public static android.os.VibrationEffect createWaveform(long[] jArr, int i) {
        int[] iArr = new int[jArr.length];
        for (int i2 = 0; i2 < jArr.length / 2; i2++) {
            iArr[(i2 * 2) + 1] = -1;
        }
        return createWaveform(jArr, iArr, i);
    }

    public static android.os.VibrationEffect createWaveform(long[] jArr, int[] iArr, int i) {
        if (jArr.length != iArr.length) {
            throw new java.lang.IllegalArgumentException("timing and amplitude arrays must be of equal length (timings.length=" + jArr.length + ", amplitudes.length=" + iArr.length + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        }
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (int i2 = 0; i2 < jArr.length; i2++) {
            arrayList.add(new android.os.vibrator.StepSegment(iArr[i2] == -1 ? -1.0f : iArr[i2] / 255.0f, 0.0f, (int) jArr[i2]));
        }
        android.os.VibrationEffect.Composed composed = new android.os.VibrationEffect.Composed(arrayList, i);
        composed.validate();
        return composed;
    }

    public static android.os.VibrationEffect createPredefined(int i) {
        return get(i, true);
    }

    public static android.os.VibrationEffect get(int i) {
        return get(i, true);
    }

    public static android.os.VibrationEffect get(int i, boolean z) {
        android.os.VibrationEffect.Composed composed = new android.os.VibrationEffect.Composed(new android.os.vibrator.PrebakedSegment(i, z, 1));
        composed.validate();
        return composed;
    }

    public static android.os.VibrationEffect get(android.net.Uri uri, android.content.Context context) {
        android.net.Uri uncanonicalize;
        java.lang.String[] stringArray = context.getResources().getStringArray(com.android.internal.R.array.config_ringtoneEffectUris);
        if (stringArray.length == 0) {
            return null;
        }
        android.content.ContentResolver contentResolver = context.getContentResolver();
        android.net.Uri uncanonicalize2 = contentResolver.uncanonicalize(uri);
        if (uncanonicalize2 != null) {
            uri = uncanonicalize2;
        }
        for (int i = 0; i < stringArray.length && i < RINGTONES.length; i++) {
            if (stringArray[i] != null && (uncanonicalize = contentResolver.uncanonicalize(android.net.Uri.parse(stringArray[i]))) != null && uncanonicalize.equals(uri)) {
                return get(RINGTONES[i]);
            }
        }
        return null;
    }

    public static android.os.VibrationEffect.Composition startComposition() {
        return new android.os.VibrationEffect.Composition();
    }

    public static android.os.VibrationEffect.WaveformBuilder startWaveform() {
        return new android.os.VibrationEffect.WaveformBuilder();
    }

    public static android.os.VibrationEffect.WaveformBuilder startWaveform(android.os.VibrationEffect.VibrationParameter vibrationParameter) {
        android.os.VibrationEffect.WaveformBuilder startWaveform = startWaveform();
        startWaveform.addTransition(java.time.Duration.ZERO, vibrationParameter);
        return startWaveform;
    }

    public static android.os.VibrationEffect.WaveformBuilder startWaveform(android.os.VibrationEffect.VibrationParameter vibrationParameter, android.os.VibrationEffect.VibrationParameter vibrationParameter2) {
        android.os.VibrationEffect.WaveformBuilder startWaveform = startWaveform();
        startWaveform.addTransition(java.time.Duration.ZERO, vibrationParameter, vibrationParameter2);
        return startWaveform;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean isHapticFeedbackCandidate() {
        return false;
    }

    public static float scale(float f, float f2) {
        float pow = android.util.MathUtils.pow(f2, 1.5384616f);
        if (f2 <= 1.0f) {
            return f * pow;
        }
        float pow2 = android.util.MathUtils.pow(f2, 4.0f - f2);
        float exp = android.util.MathUtils.exp(f * pow * pow2);
        float exp2 = android.util.MathUtils.exp(pow * pow2);
        return android.util.MathUtils.constrain(((exp2 + 1.0f) / (exp2 - 1.0f)) * ((exp - 1.0f) / (exp + 1.0f)), 0.0f, 1.0f);
    }

    public static float scaleLinearly(float f, float f2) {
        return android.util.MathUtils.constrain(f * f2, 0.0f, 1.0f);
    }

    public static java.lang.String effectIdToString(int i) {
        switch (i) {
            case 0:
                return "CLICK";
            case 1:
                return "DOUBLE_CLICK";
            case 2:
                return "TICK";
            case 3:
                return "THUD";
            case 4:
                return "POP";
            case 5:
                return "HEAVY_CLICK";
            case 21:
                return "TEXTURE_TICK";
            default:
                return java.lang.Integer.toString(i);
        }
    }

    public static java.lang.String effectStrengthToString(int i) {
        switch (i) {
            case 0:
                return "LIGHT";
            case 1:
                return "MEDIUM";
            case 2:
                return "STRONG";
            default:
                return java.lang.Integer.toString(i);
        }
    }

    public static final class Composed extends android.os.VibrationEffect {
        public static final android.os.Parcelable.Creator<android.os.VibrationEffect.Composed> CREATOR = new android.os.Parcelable.Creator<android.os.VibrationEffect.Composed>() { // from class: android.os.VibrationEffect.Composed.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.os.VibrationEffect.Composed createFromParcel(android.os.Parcel parcel) {
                return new android.os.VibrationEffect.Composed(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.os.VibrationEffect.Composed[] newArray(int i) {
                return new android.os.VibrationEffect.Composed[i];
            }
        };
        private final int mRepeatIndex;
        private final java.util.ArrayList<android.os.vibrator.VibrationEffectSegment> mSegments;

        Composed(android.os.Parcel parcel) {
            this(parcel.readArrayList(android.os.vibrator.VibrationEffectSegment.class.getClassLoader(), android.os.vibrator.VibrationEffectSegment.class), parcel.readInt());
        }

        Composed(android.os.vibrator.VibrationEffectSegment vibrationEffectSegment) {
            this(java.util.Arrays.asList(vibrationEffectSegment), -1);
        }

        public Composed(java.util.List<? extends android.os.vibrator.VibrationEffectSegment> list, int i) {
            this.mSegments = new java.util.ArrayList<>(list);
            this.mRepeatIndex = i;
        }

        public java.util.List<android.os.vibrator.VibrationEffectSegment> getSegments() {
            return this.mSegments;
        }

        public int getRepeatIndex() {
            return this.mRepeatIndex;
        }

        @Override // android.os.VibrationEffect
        public long[] computeCreateWaveformOffOnTimingsOrNull() {
            if (getRepeatIndex() >= 0) {
                return null;
            }
            java.util.List<android.os.vibrator.VibrationEffectSegment> segments = getSegments();
            long[] jArr = new long[segments.size() + 1];
            int i = 0;
            for (int i2 = 0; i2 < segments.size(); i2++) {
                android.os.vibrator.StepSegment castToValidStepSegmentForOffOnTimingsOrNull = castToValidStepSegmentForOffOnTimingsOrNull(segments.get(i2));
                if (castToValidStepSegmentForOffOnTimingsOrNull == null) {
                    return null;
                }
                if ((castToValidStepSegmentForOffOnTimingsOrNull.getAmplitude() == 0.0f) != (i % 2 == 0)) {
                    i++;
                }
                jArr[i] = jArr[i] + castToValidStepSegmentForOffOnTimingsOrNull.getDuration();
            }
            return java.util.Arrays.copyOf(jArr, i + 1);
        }

        @Override // android.os.VibrationEffect
        public void validate() {
            int size = this.mSegments.size();
            boolean z = false;
            for (int i = 0; i < size; i++) {
                android.os.vibrator.VibrationEffectSegment vibrationEffectSegment = this.mSegments.get(i);
                vibrationEffectSegment.validate();
                z |= vibrationEffectSegment.getDuration() != 0;
            }
            if (!z) {
                throw new java.lang.IllegalArgumentException("at least one timing must be non-zero (segments=" + this.mSegments + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
            }
            if (this.mRepeatIndex != -1) {
                com.android.internal.util.Preconditions.checkArgumentInRange(this.mRepeatIndex, 0, size - 1, "repeat index must be within the bounds of the segments (segments.length=" + size + ", index=" + this.mRepeatIndex + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
            }
        }

        @Override // android.os.VibrationEffect
        public long getDuration() {
            if (this.mRepeatIndex >= 0) {
                return Long.MAX_VALUE;
            }
            int size = this.mSegments.size();
            long j = 0;
            for (int i = 0; i < size; i++) {
                long duration = this.mSegments.get(i).getDuration();
                if (duration < 0) {
                    return duration;
                }
                j += duration;
            }
            return j;
        }

        @Override // android.os.VibrationEffect
        public boolean areVibrationFeaturesSupported(android.os.VibratorInfo vibratorInfo) {
            java.util.Iterator<android.os.vibrator.VibrationEffectSegment> it = this.mSegments.iterator();
            while (it.hasNext()) {
                if (!it.next().areVibrationFeaturesSupported(vibratorInfo)) {
                    return false;
                }
            }
            return true;
        }

        @Override // android.os.VibrationEffect
        public boolean isHapticFeedbackCandidate() {
            if (getDuration() > 1000) {
                return false;
            }
            int size = this.mSegments.size();
            if (size > 3) {
                return false;
            }
            long j = 0;
            for (int i = 0; i < size; i++) {
                if (!this.mSegments.get(i).isHapticFeedbackCandidate()) {
                    return false;
                }
                long duration = this.mSegments.get(i).getDuration();
                if (duration > 0) {
                    j += duration;
                }
            }
            return j <= 1000;
        }

        @Override // android.os.VibrationEffect
        public android.os.VibrationEffect.Composed resolve(int i) {
            int size = this.mSegments.size();
            java.util.ArrayList arrayList = new java.util.ArrayList(size);
            for (int i2 = 0; i2 < size; i2++) {
                arrayList.add(this.mSegments.get(i2).resolve(i));
            }
            if (arrayList.equals(this.mSegments)) {
                return this;
            }
            android.os.VibrationEffect.Composed composed = new android.os.VibrationEffect.Composed(arrayList, this.mRepeatIndex);
            composed.validate();
            return composed;
        }

        @Override // android.os.VibrationEffect
        public android.os.VibrationEffect.Composed scale(float f) {
            int size = this.mSegments.size();
            java.util.ArrayList arrayList = new java.util.ArrayList(size);
            for (int i = 0; i < size; i++) {
                arrayList.add(this.mSegments.get(i).scale(f));
            }
            if (arrayList.equals(this.mSegments)) {
                return this;
            }
            android.os.VibrationEffect.Composed composed = new android.os.VibrationEffect.Composed(arrayList, this.mRepeatIndex);
            composed.validate();
            return composed;
        }

        @Override // android.os.VibrationEffect
        public android.os.VibrationEffect.Composed applyRepeatingIndefinitely(boolean z, int i) {
            if ((this.mRepeatIndex >= 0) == z) {
                return this;
            }
            if (!z) {
                return new android.os.VibrationEffect.Composed(this.mSegments, -1);
            }
            if (i <= 0) {
                return new android.os.VibrationEffect.Composed(this.mSegments, 0);
            }
            java.util.ArrayList arrayList = new java.util.ArrayList(this.mSegments.size() + 1);
            arrayList.addAll(this.mSegments);
            arrayList.add(new android.os.vibrator.StepSegment(0.0f, 0.0f, i));
            return new android.os.VibrationEffect.Composed(arrayList, 0);
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof android.os.VibrationEffect.Composed)) {
                return false;
            }
            android.os.VibrationEffect.Composed composed = (android.os.VibrationEffect.Composed) obj;
            return this.mSegments.equals(composed.mSegments) && this.mRepeatIndex == composed.mRepeatIndex;
        }

        public int hashCode() {
            return java.util.Objects.hash(this.mSegments, java.lang.Integer.valueOf(this.mRepeatIndex));
        }

        public java.lang.String toString() {
            return "Composed{segments=" + this.mSegments + ", repeat=" + this.mRepeatIndex + "}";
        }

        @Override // android.os.VibrationEffect
        public java.lang.String toDebugString() {
            if (this.mSegments.size() == 1 && this.mRepeatIndex < 0) {
                return this.mSegments.get(0).toDebugString();
            }
            java.util.StringJoiner stringJoiner = new java.util.StringJoiner(",", android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_START, android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END);
            for (int i = 0; i < this.mSegments.size(); i++) {
                stringJoiner.add(this.mSegments.get(i).toDebugString());
            }
            if (this.mRepeatIndex >= 0) {
                return java.lang.String.format(java.util.Locale.ROOT, "%s, repeat=%d", stringJoiner, java.lang.Integer.valueOf(this.mRepeatIndex));
            }
            return stringJoiner.toString();
        }

        @Override // android.os.VibrationEffect, android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeList(this.mSegments);
            parcel.writeInt(this.mRepeatIndex);
        }

        private static android.os.vibrator.StepSegment castToValidStepSegmentForOffOnTimingsOrNull(android.os.vibrator.VibrationEffectSegment vibrationEffectSegment) {
            if (!(vibrationEffectSegment instanceof android.os.vibrator.StepSegment)) {
                return null;
            }
            android.os.vibrator.StepSegment stepSegment = (android.os.vibrator.StepSegment) vibrationEffectSegment;
            if (stepSegment.getFrequencyHz() != 0.0f) {
                return null;
            }
            float amplitude = stepSegment.getAmplitude();
            if (amplitude == 0.0f || amplitude == -1.0f) {
                return stepSegment;
            }
            return null;
        }
    }

    public static final class Composition {
        public static final int PRIMITIVE_CLICK = 1;
        public static final int PRIMITIVE_LOW_TICK = 8;
        public static final int PRIMITIVE_NOOP = 0;
        public static final int PRIMITIVE_QUICK_FALL = 6;
        public static final int PRIMITIVE_QUICK_RISE = 4;
        public static final int PRIMITIVE_SLOW_RISE = 5;
        public static final int PRIMITIVE_SPIN = 3;
        public static final int PRIMITIVE_THUD = 2;
        public static final int PRIMITIVE_TICK = 7;
        private final java.util.ArrayList<android.os.vibrator.VibrationEffectSegment> mSegments = new java.util.ArrayList<>();
        private int mRepeatIndex = -1;

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        public @interface PrimitiveType {
        }

        public static final class UnreachableAfterRepeatingIndefinitelyException extends java.lang.IllegalStateException {
            UnreachableAfterRepeatingIndefinitelyException() {
                super("Compositions ending in an indefinitely repeating effect can't be extended");
            }
        }

        Composition() {
        }

        public android.os.VibrationEffect.Composition addOffDuration(java.time.Duration duration) {
            int millis = (int) duration.toMillis();
            com.android.internal.util.Preconditions.checkArgumentNonnegative(millis, "Off period must be non-negative");
            if (millis > 0) {
                addSegment(new android.os.vibrator.StepSegment(0.0f, 0.0f, (int) duration.toMillis()));
            }
            return this;
        }

        public android.os.VibrationEffect.Composition addEffect(android.os.VibrationEffect vibrationEffect) {
            return addSegments(vibrationEffect);
        }

        public android.os.VibrationEffect.Composition repeatEffectIndefinitely(android.os.VibrationEffect vibrationEffect) {
            com.android.internal.util.Preconditions.checkArgument(vibrationEffect.getDuration() < Long.MAX_VALUE, "Can't repeat an indefinitely repeating effect. Consider addEffect instead.");
            int size = this.mSegments.size();
            addSegments(vibrationEffect);
            this.mRepeatIndex = size;
            return this;
        }

        public android.os.VibrationEffect.Composition addPrimitive(int i) {
            return addPrimitive(i, 1.0f);
        }

        public android.os.VibrationEffect.Composition addPrimitive(int i, float f) {
            return addPrimitive(i, f, 0);
        }

        public android.os.VibrationEffect.Composition addPrimitive(int i, float f, int i2) {
            android.os.vibrator.PrimitiveSegment primitiveSegment = new android.os.vibrator.PrimitiveSegment(i, f, i2);
            primitiveSegment.validate();
            return addSegment(primitiveSegment);
        }

        private android.os.VibrationEffect.Composition addSegment(android.os.vibrator.VibrationEffectSegment vibrationEffectSegment) {
            if (this.mRepeatIndex >= 0) {
                throw new android.os.VibrationEffect.Composition.UnreachableAfterRepeatingIndefinitelyException();
            }
            this.mSegments.add(vibrationEffectSegment);
            return this;
        }

        private android.os.VibrationEffect.Composition addSegments(android.os.VibrationEffect vibrationEffect) {
            if (this.mRepeatIndex >= 0) {
                throw new android.os.VibrationEffect.Composition.UnreachableAfterRepeatingIndefinitelyException();
            }
            android.os.VibrationEffect.Composed composed = (android.os.VibrationEffect.Composed) vibrationEffect;
            if (composed.getRepeatIndex() >= 0) {
                this.mRepeatIndex = this.mSegments.size() + composed.getRepeatIndex();
            }
            this.mSegments.addAll(composed.getSegments());
            return this;
        }

        public android.os.VibrationEffect compose() {
            if (this.mSegments.isEmpty()) {
                throw new java.lang.IllegalStateException("Composition must have at least one element to compose.");
            }
            android.os.VibrationEffect.Composed composed = new android.os.VibrationEffect.Composed(this.mSegments, this.mRepeatIndex);
            composed.validate();
            return composed;
        }

        public static java.lang.String primitiveToString(int i) {
            switch (i) {
                case 0:
                    return android.provider.TimeZoneRulesDataContract.Operation.TYPE_NO_OP;
                case 1:
                    return "CLICK";
                case 2:
                    return "THUD";
                case 3:
                    return "SPIN";
                case 4:
                    return "QUICK_RISE";
                case 5:
                    return "SLOW_RISE";
                case 6:
                    return "QUICK_FALL";
                case 7:
                    return "TICK";
                case 8:
                    return "LOW_TICK";
                default:
                    return java.lang.Integer.toString(i);
            }
        }
    }

    public static final class WaveformBuilder {
        private static final float EPSILON = 1.0E-5f;
        private java.util.ArrayList<android.os.vibrator.VibrationEffectSegment> mSegments = new java.util.ArrayList<>();
        private float mLastAmplitude = 0.0f;
        private float mLastFrequencyHz = 0.0f;

        WaveformBuilder() {
        }

        public android.os.VibrationEffect.WaveformBuilder addTransition(java.time.Duration duration, android.os.VibrationEffect.VibrationParameter vibrationParameter) {
            com.android.internal.util.Preconditions.checkNotNull(duration, "Duration is null");
            checkVibrationParameter(vibrationParameter, "targetParameter");
            addTransitionSegment(duration, extractTargetAmplitude(vibrationParameter, null), extractTargetFrequency(vibrationParameter, null));
            return this;
        }

        public android.os.VibrationEffect.WaveformBuilder addTransition(java.time.Duration duration, android.os.VibrationEffect.VibrationParameter vibrationParameter, android.os.VibrationEffect.VibrationParameter vibrationParameter2) {
            com.android.internal.util.Preconditions.checkNotNull(duration, "Duration is null");
            checkVibrationParameter(vibrationParameter, "targetParameter1");
            checkVibrationParameter(vibrationParameter2, "targetParameter2");
            com.android.internal.util.Preconditions.checkArgument(!java.util.Objects.equals(vibrationParameter.getClass(), vibrationParameter2.getClass()), "Parameter arguments must specify different parameter types");
            addTransitionSegment(duration, extractTargetAmplitude(vibrationParameter, vibrationParameter2), extractTargetFrequency(vibrationParameter, vibrationParameter2));
            return this;
        }

        public android.os.VibrationEffect.WaveformBuilder addSustain(java.time.Duration duration) {
            int millis = (int) duration.toMillis();
            com.android.internal.util.Preconditions.checkArgument(millis >= 1, "Sustain duration must be >= 1ms");
            this.mSegments.add(new android.os.vibrator.StepSegment(this.mLastAmplitude, this.mLastFrequencyHz, millis));
            return this;
        }

        public android.os.VibrationEffect build() {
            if (this.mSegments.isEmpty()) {
                throw new java.lang.IllegalStateException("WaveformBuilder must have at least one transition to build.");
            }
            android.os.VibrationEffect.Composed composed = new android.os.VibrationEffect.Composed(this.mSegments, -1);
            composed.validate();
            return composed;
        }

        private void checkVibrationParameter(android.os.VibrationEffect.VibrationParameter vibrationParameter, java.lang.String str) {
            com.android.internal.util.Preconditions.checkNotNull(vibrationParameter, "%s is null", str);
            com.android.internal.util.Preconditions.checkArgument((vibrationParameter instanceof android.os.VibrationEffect.AmplitudeVibrationParameter) || (vibrationParameter instanceof android.os.VibrationEffect.FrequencyVibrationParameter), "%s is a unknown parameter", str);
        }

        private float extractTargetAmplitude(android.os.VibrationEffect.VibrationParameter vibrationParameter, android.os.VibrationEffect.VibrationParameter vibrationParameter2) {
            if (vibrationParameter2 instanceof android.os.VibrationEffect.AmplitudeVibrationParameter) {
                return ((android.os.VibrationEffect.AmplitudeVibrationParameter) vibrationParameter2).amplitude;
            }
            if (vibrationParameter instanceof android.os.VibrationEffect.AmplitudeVibrationParameter) {
                return ((android.os.VibrationEffect.AmplitudeVibrationParameter) vibrationParameter).amplitude;
            }
            return this.mLastAmplitude;
        }

        private float extractTargetFrequency(android.os.VibrationEffect.VibrationParameter vibrationParameter, android.os.VibrationEffect.VibrationParameter vibrationParameter2) {
            if (vibrationParameter2 instanceof android.os.VibrationEffect.FrequencyVibrationParameter) {
                return ((android.os.VibrationEffect.FrequencyVibrationParameter) vibrationParameter2).frequencyHz;
            }
            if (vibrationParameter instanceof android.os.VibrationEffect.FrequencyVibrationParameter) {
                return ((android.os.VibrationEffect.FrequencyVibrationParameter) vibrationParameter).frequencyHz;
            }
            return this.mLastFrequencyHz;
        }

        private void addTransitionSegment(java.time.Duration duration, float f, float f2) {
            com.android.internal.util.Preconditions.checkNotNull(duration, "Duration is null");
            com.android.internal.util.Preconditions.checkArgument(!duration.isNegative(), "Transition duration must be non-negative");
            int millis = (int) duration.toMillis();
            if (millis > 0) {
                if (java.lang.Math.abs(this.mLastAmplitude - f) < EPSILON && java.lang.Math.abs(this.mLastFrequencyHz - f2) < EPSILON) {
                    this.mSegments.add(new android.os.vibrator.StepSegment(f, f2, millis));
                } else {
                    this.mSegments.add(new android.os.vibrator.RampSegment(this.mLastAmplitude, f, this.mLastFrequencyHz, f2, millis));
                }
            }
            this.mLastAmplitude = f;
            this.mLastFrequencyHz = f2;
        }
    }

    public static class VibrationParameter {
        VibrationParameter() {
        }

        public static android.os.VibrationEffect.VibrationParameter targetAmplitude(float f) {
            return new android.os.VibrationEffect.AmplitudeVibrationParameter(f);
        }

        public static android.os.VibrationEffect.VibrationParameter targetFrequency(float f) {
            return new android.os.VibrationEffect.FrequencyVibrationParameter(f);
        }
    }

    private static final class AmplitudeVibrationParameter extends android.os.VibrationEffect.VibrationParameter {
        public final float amplitude;

        AmplitudeVibrationParameter(float f) {
            com.android.internal.util.Preconditions.checkArgument(f >= 0.0f && f <= 1.0f, "Amplitude must be within [0,1]");
            this.amplitude = f;
        }
    }

    private static final class FrequencyVibrationParameter extends android.os.VibrationEffect.VibrationParameter {
        public final float frequencyHz;

        FrequencyVibrationParameter(float f) {
            com.android.internal.util.Preconditions.checkArgument(f >= 1.0f, "Frequency must be >= 1");
            com.android.internal.util.Preconditions.checkArgument(java.lang.Float.isFinite(f), "Frequency must be finite");
            this.frequencyHz = f;
        }
    }
}
