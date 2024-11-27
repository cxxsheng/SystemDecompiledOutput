package android.os;

/* loaded from: classes3.dex */
public abstract class CombinedVibration implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.os.CombinedVibration> CREATOR = new android.os.Parcelable.Creator<android.os.CombinedVibration>() { // from class: android.os.CombinedVibration.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.os.CombinedVibration createFromParcel(android.os.Parcel parcel) {
            int readInt = parcel.readInt();
            if (readInt == 1) {
                return new android.os.CombinedVibration.Mono(parcel);
            }
            if (readInt == 2) {
                return new android.os.CombinedVibration.Stereo(parcel);
            }
            if (readInt == 3) {
                return new android.os.CombinedVibration.Sequential(parcel);
            }
            throw new java.lang.IllegalStateException("Unexpected combined vibration event type token in parcel.");
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.os.CombinedVibration[] newArray(int i) {
            return new android.os.CombinedVibration[i];
        }
    };
    private static final int PARCEL_TOKEN_MONO = 1;
    private static final int PARCEL_TOKEN_SEQUENTIAL = 3;
    private static final int PARCEL_TOKEN_STEREO = 2;

    public interface VibratorAdapter {
        android.os.VibrationEffect adaptToVibrator(int i, android.os.VibrationEffect vibrationEffect);

        int[] getAvailableVibratorIds();
    }

    public abstract android.os.CombinedVibration adapt(android.os.CombinedVibration.VibratorAdapter vibratorAdapter);

    public abstract long getDuration();

    public abstract boolean hasVibrator(int i);

    public abstract java.lang.String toDebugString();

    public abstract <ParamT> android.os.CombinedVibration transform(android.os.VibrationEffect.Transformation<ParamT> transformation, ParamT paramt);

    public abstract void validate();

    CombinedVibration() {
    }

    public static android.os.CombinedVibration createParallel(android.os.VibrationEffect vibrationEffect) {
        android.os.CombinedVibration.Mono mono = new android.os.CombinedVibration.Mono(vibrationEffect);
        mono.validate();
        return mono;
    }

    public static android.os.CombinedVibration.ParallelCombination startParallel() {
        return new android.os.CombinedVibration.ParallelCombination();
    }

    public static android.os.CombinedVibration.SequentialCombination startSequential() {
        return new android.os.CombinedVibration.SequentialCombination();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean isHapticFeedbackCandidate() {
        return false;
    }

    public static final class ParallelCombination {
        private final android.util.SparseArray<android.os.VibrationEffect> mEffects = new android.util.SparseArray<>();

        ParallelCombination() {
        }

        public android.os.CombinedVibration.ParallelCombination addVibrator(int i, android.os.VibrationEffect vibrationEffect) {
            this.mEffects.put(i, vibrationEffect);
            return this;
        }

        public android.os.CombinedVibration combine() {
            if (this.mEffects.size() == 0) {
                throw new java.lang.IllegalStateException("Combination must have at least one element to combine.");
            }
            android.os.CombinedVibration.Stereo stereo = new android.os.CombinedVibration.Stereo(this.mEffects);
            stereo.validate();
            return stereo;
        }
    }

    public static final class SequentialCombination {
        private final java.util.ArrayList<android.os.CombinedVibration> mEffects = new java.util.ArrayList<>();
        private final java.util.ArrayList<java.lang.Integer> mDelays = new java.util.ArrayList<>();

        SequentialCombination() {
        }

        public android.os.CombinedVibration.SequentialCombination addNext(int i, android.os.VibrationEffect vibrationEffect) {
            return addNext(i, vibrationEffect, 0);
        }

        public android.os.CombinedVibration.SequentialCombination addNext(int i, android.os.VibrationEffect vibrationEffect, int i2) {
            return addNext(android.os.CombinedVibration.startParallel().addVibrator(i, vibrationEffect).combine(), i2);
        }

        public android.os.CombinedVibration.SequentialCombination addNext(android.os.CombinedVibration combinedVibration) {
            return addNext(combinedVibration, 0);
        }

        public android.os.CombinedVibration.SequentialCombination addNext(android.os.CombinedVibration combinedVibration, int i) {
            if (combinedVibration instanceof android.os.CombinedVibration.Sequential) {
                android.os.CombinedVibration.Sequential sequential = (android.os.CombinedVibration.Sequential) combinedVibration;
                int size = this.mDelays.size();
                this.mEffects.addAll(sequential.getEffects());
                this.mDelays.addAll(sequential.getDelays());
                this.mDelays.set(size, java.lang.Integer.valueOf(i + this.mDelays.get(size).intValue()));
            } else {
                this.mEffects.add(combinedVibration);
                this.mDelays.add(java.lang.Integer.valueOf(i));
            }
            return this;
        }

        public android.os.CombinedVibration combine() {
            if (this.mEffects.size() == 0) {
                throw new java.lang.IllegalStateException("Combination must have at least one element to combine.");
            }
            android.os.CombinedVibration.Sequential sequential = new android.os.CombinedVibration.Sequential(this.mEffects, this.mDelays);
            sequential.validate();
            return sequential;
        }
    }

    public static final class Mono extends android.os.CombinedVibration {
        public static final android.os.Parcelable.Creator<android.os.CombinedVibration.Mono> CREATOR = new android.os.Parcelable.Creator<android.os.CombinedVibration.Mono>() { // from class: android.os.CombinedVibration.Mono.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.os.CombinedVibration.Mono createFromParcel(android.os.Parcel parcel) {
                parcel.readInt();
                return new android.os.CombinedVibration.Mono(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.os.CombinedVibration.Mono[] newArray(int i) {
                return new android.os.CombinedVibration.Mono[i];
            }
        };
        private final android.os.VibrationEffect mEffect;

        Mono(android.os.Parcel parcel) {
            this.mEffect = android.os.VibrationEffect.CREATOR.createFromParcel(parcel);
        }

        Mono(android.os.VibrationEffect vibrationEffect) {
            this.mEffect = vibrationEffect;
        }

        public android.os.VibrationEffect getEffect() {
            return this.mEffect;
        }

        @Override // android.os.CombinedVibration
        public long getDuration() {
            return this.mEffect.getDuration();
        }

        @Override // android.os.CombinedVibration
        public boolean isHapticFeedbackCandidate() {
            return this.mEffect.isHapticFeedbackCandidate();
        }

        @Override // android.os.CombinedVibration
        public void validate() {
            this.mEffect.validate();
        }

        @Override // android.os.CombinedVibration
        public <ParamT> android.os.CombinedVibration transform(android.os.VibrationEffect.Transformation<ParamT> transformation, ParamT paramt) {
            android.os.VibrationEffect transform = transformation.transform(this.mEffect, paramt);
            if (this.mEffect.equals(transform)) {
                return this;
            }
            return android.os.CombinedVibration.createParallel(transform);
        }

        @Override // android.os.CombinedVibration
        public android.os.CombinedVibration adapt(android.os.CombinedVibration.VibratorAdapter vibratorAdapter) {
            android.os.CombinedVibration.ParallelCombination startParallel = android.os.CombinedVibration.startParallel();
            boolean z = true;
            for (int i : vibratorAdapter.getAvailableVibratorIds()) {
                android.os.VibrationEffect adaptToVibrator = vibratorAdapter.adaptToVibrator(i, this.mEffect);
                startParallel.addVibrator(i, adaptToVibrator);
                z &= this.mEffect.equals(adaptToVibrator);
            }
            if (z) {
                return this;
            }
            return startParallel.combine();
        }

        @Override // android.os.CombinedVibration
        public boolean hasVibrator(int i) {
            return true;
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof android.os.CombinedVibration.Mono)) {
                return false;
            }
            return this.mEffect.equals(((android.os.CombinedVibration.Mono) obj).mEffect);
        }

        public int hashCode() {
            return java.util.Objects.hash(this.mEffect);
        }

        public java.lang.String toString() {
            return "Mono{mEffect=" + this.mEffect + '}';
        }

        @Override // android.os.CombinedVibration
        public java.lang.String toDebugString() {
            return this.mEffect.toDebugString();
        }

        @Override // android.os.CombinedVibration, android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeInt(1);
            this.mEffect.writeToParcel(parcel, i);
        }
    }

    public static final class Stereo extends android.os.CombinedVibration {
        public static final android.os.Parcelable.Creator<android.os.CombinedVibration.Stereo> CREATOR = new android.os.Parcelable.Creator<android.os.CombinedVibration.Stereo>() { // from class: android.os.CombinedVibration.Stereo.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.os.CombinedVibration.Stereo createFromParcel(android.os.Parcel parcel) {
                parcel.readInt();
                return new android.os.CombinedVibration.Stereo(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.os.CombinedVibration.Stereo[] newArray(int i) {
                return new android.os.CombinedVibration.Stereo[i];
            }
        };
        private final android.util.SparseArray<android.os.VibrationEffect> mEffects;

        Stereo(android.os.Parcel parcel) {
            int readInt = parcel.readInt();
            this.mEffects = new android.util.SparseArray<>(readInt);
            for (int i = 0; i < readInt; i++) {
                this.mEffects.put(parcel.readInt(), android.os.VibrationEffect.CREATOR.createFromParcel(parcel));
            }
        }

        Stereo(android.util.SparseArray<android.os.VibrationEffect> sparseArray) {
            this.mEffects = new android.util.SparseArray<>(sparseArray.size());
            for (int i = 0; i < sparseArray.size(); i++) {
                this.mEffects.put(sparseArray.keyAt(i), sparseArray.valueAt(i));
            }
        }

        public android.util.SparseArray<android.os.VibrationEffect> getEffects() {
            return this.mEffects;
        }

        @Override // android.os.CombinedVibration
        public long getDuration() {
            long j = Long.MIN_VALUE;
            boolean z = false;
            for (int i = 0; i < this.mEffects.size(); i++) {
                long duration = this.mEffects.valueAt(i).getDuration();
                if (duration == Long.MAX_VALUE) {
                    return duration;
                }
                j = java.lang.Math.max(j, duration);
                z |= duration < 0;
            }
            if (z) {
                return -1L;
            }
            return j;
        }

        @Override // android.os.CombinedVibration
        public boolean isHapticFeedbackCandidate() {
            for (int i = 0; i < this.mEffects.size(); i++) {
                if (!this.mEffects.valueAt(i).isHapticFeedbackCandidate()) {
                    return false;
                }
            }
            return true;
        }

        @Override // android.os.CombinedVibration
        public void validate() {
            com.android.internal.util.Preconditions.checkArgument(this.mEffects.size() > 0, "There should be at least one effect set for a combined effect");
            for (int i = 0; i < this.mEffects.size(); i++) {
                this.mEffects.valueAt(i).validate();
            }
        }

        @Override // android.os.CombinedVibration
        public <ParamT> android.os.CombinedVibration transform(android.os.VibrationEffect.Transformation<ParamT> transformation, ParamT paramt) {
            android.os.CombinedVibration.ParallelCombination startParallel = android.os.CombinedVibration.startParallel();
            boolean z = true;
            for (int i = 0; i < this.mEffects.size(); i++) {
                int keyAt = this.mEffects.keyAt(i);
                android.os.VibrationEffect valueAt = this.mEffects.valueAt(i);
                android.os.VibrationEffect transform = transformation.transform(valueAt, paramt);
                startParallel.addVibrator(keyAt, transform);
                z &= valueAt.equals(transform);
            }
            if (z) {
                return this;
            }
            return startParallel.combine();
        }

        @Override // android.os.CombinedVibration
        public android.os.CombinedVibration adapt(android.os.CombinedVibration.VibratorAdapter vibratorAdapter) {
            android.os.CombinedVibration.ParallelCombination startParallel = android.os.CombinedVibration.startParallel();
            boolean z = true;
            for (int i = 0; i < this.mEffects.size(); i++) {
                int keyAt = this.mEffects.keyAt(i);
                android.os.VibrationEffect valueAt = this.mEffects.valueAt(i);
                android.os.VibrationEffect adaptToVibrator = vibratorAdapter.adaptToVibrator(keyAt, valueAt);
                startParallel.addVibrator(keyAt, adaptToVibrator);
                z &= valueAt.equals(adaptToVibrator);
            }
            if (z) {
                return this;
            }
            return startParallel.combine();
        }

        @Override // android.os.CombinedVibration
        public boolean hasVibrator(int i) {
            return this.mEffects.indexOfKey(i) >= 0;
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof android.os.CombinedVibration.Stereo)) {
                return false;
            }
            android.os.CombinedVibration.Stereo stereo = (android.os.CombinedVibration.Stereo) obj;
            if (this.mEffects.size() != stereo.mEffects.size()) {
                return false;
            }
            for (int i = 0; i < this.mEffects.size(); i++) {
                if (!this.mEffects.valueAt(i).equals(stereo.mEffects.get(this.mEffects.keyAt(i)))) {
                    return false;
                }
            }
            return true;
        }

        public int hashCode() {
            return this.mEffects.contentHashCode();
        }

        public java.lang.String toString() {
            return "Stereo{mEffects=" + this.mEffects + '}';
        }

        @Override // android.os.CombinedVibration
        public java.lang.String toDebugString() {
            java.util.StringJoiner stringJoiner = new java.util.StringJoiner(",", "Stereo{", "}");
            for (int i = 0; i < this.mEffects.size(); i++) {
                stringJoiner.add(java.lang.String.format(java.util.Locale.ROOT, "vibrator(id=%d): %s", java.lang.Integer.valueOf(this.mEffects.keyAt(i)), this.mEffects.valueAt(i).toDebugString()));
            }
            return stringJoiner.toString();
        }

        @Override // android.os.CombinedVibration, android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeInt(2);
            parcel.writeInt(this.mEffects.size());
            for (int i2 = 0; i2 < this.mEffects.size(); i2++) {
                parcel.writeInt(this.mEffects.keyAt(i2));
                this.mEffects.valueAt(i2).writeToParcel(parcel, i);
            }
        }
    }

    public static final class Sequential extends android.os.CombinedVibration {
        public static final android.os.Parcelable.Creator<android.os.CombinedVibration.Sequential> CREATOR = new android.os.Parcelable.Creator<android.os.CombinedVibration.Sequential>() { // from class: android.os.CombinedVibration.Sequential.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.os.CombinedVibration.Sequential createFromParcel(android.os.Parcel parcel) {
                parcel.readInt();
                return new android.os.CombinedVibration.Sequential(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.os.CombinedVibration.Sequential[] newArray(int i) {
                return new android.os.CombinedVibration.Sequential[i];
            }
        };
        private static final long MAX_HAPTIC_FEEDBACK_SEQUENCE_SIZE = 3;
        private final java.util.List<java.lang.Integer> mDelays;
        private final java.util.List<android.os.CombinedVibration> mEffects;

        Sequential(android.os.Parcel parcel) {
            int readInt = parcel.readInt();
            this.mEffects = new java.util.ArrayList(readInt);
            this.mDelays = new java.util.ArrayList(readInt);
            for (int i = 0; i < readInt; i++) {
                this.mDelays.add(java.lang.Integer.valueOf(parcel.readInt()));
                this.mEffects.add(android.os.CombinedVibration.CREATOR.createFromParcel(parcel));
            }
        }

        Sequential(java.util.List<android.os.CombinedVibration> list, java.util.List<java.lang.Integer> list2) {
            this.mEffects = new java.util.ArrayList(list);
            this.mDelays = new java.util.ArrayList(list2);
        }

        public java.util.List<android.os.CombinedVibration> getEffects() {
            return this.mEffects;
        }

        public java.util.List<java.lang.Integer> getDelays() {
            return this.mDelays;
        }

        @Override // android.os.CombinedVibration
        public long getDuration() {
            int size = this.mEffects.size();
            long j = 0;
            boolean z = false;
            long j2 = 0;
            for (int i = 0; i < size; i++) {
                long duration = this.mEffects.get(i).getDuration();
                if (duration == Long.MAX_VALUE) {
                    return duration;
                }
                j2 += duration;
                z |= duration < 0;
            }
            if (z) {
                return -1L;
            }
            for (int i2 = 0; i2 < size; i2++) {
                j += this.mDelays.get(i2).intValue();
            }
            return j2 + j;
        }

        @Override // android.os.CombinedVibration
        public boolean isHapticFeedbackCandidate() {
            int size = this.mEffects.size();
            if (size > 3) {
                return false;
            }
            for (int i = 0; i < size; i++) {
                if (!this.mEffects.get(i).isHapticFeedbackCandidate()) {
                    return false;
                }
            }
            return true;
        }

        @Override // android.os.CombinedVibration
        public void validate() {
            com.android.internal.util.Preconditions.checkArgument(this.mEffects.size() > 0, "There should be at least one effect set for a combined effect");
            com.android.internal.util.Preconditions.checkArgument(this.mEffects.size() == this.mDelays.size(), "Effect and delays should have equal length");
            int size = this.mEffects.size();
            for (int i = 0; i < size; i++) {
                if (this.mDelays.get(i).intValue() < 0) {
                    throw new java.lang.IllegalArgumentException("Delays must all be >= 0 (delays=" + this.mDelays + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
                }
            }
            for (int i2 = 0; i2 < size; i2++) {
                android.os.CombinedVibration combinedVibration = this.mEffects.get(i2);
                if (combinedVibration instanceof android.os.CombinedVibration.Sequential) {
                    throw new java.lang.IllegalArgumentException("There should be no nested sequential effects in a combined effect");
                }
                combinedVibration.validate();
            }
        }

        @Override // android.os.CombinedVibration
        public <ParamT> android.os.CombinedVibration transform(android.os.VibrationEffect.Transformation<ParamT> transformation, ParamT paramt) {
            android.os.CombinedVibration.SequentialCombination startSequential = android.os.CombinedVibration.startSequential();
            boolean z = true;
            for (int i = 0; i < this.mEffects.size(); i++) {
                android.os.CombinedVibration combinedVibration = this.mEffects.get(i);
                android.os.CombinedVibration transform = combinedVibration.transform(transformation, paramt);
                startSequential.addNext(transform, this.mDelays.get(i).intValue());
                z &= combinedVibration.equals(transform);
            }
            if (z) {
                return this;
            }
            return startSequential.combine();
        }

        @Override // android.os.CombinedVibration
        public android.os.CombinedVibration adapt(android.os.CombinedVibration.VibratorAdapter vibratorAdapter) {
            android.os.CombinedVibration.SequentialCombination startSequential = android.os.CombinedVibration.startSequential();
            boolean z = true;
            for (int i = 0; i < this.mEffects.size(); i++) {
                android.os.CombinedVibration combinedVibration = this.mEffects.get(i);
                android.os.CombinedVibration adapt = combinedVibration.adapt(vibratorAdapter);
                startSequential.addNext(adapt, this.mDelays.get(i).intValue());
                z &= combinedVibration.equals(adapt);
            }
            if (z) {
                return this;
            }
            return startSequential.combine();
        }

        @Override // android.os.CombinedVibration
        public boolean hasVibrator(int i) {
            int size = this.mEffects.size();
            for (int i2 = 0; i2 < size; i2++) {
                if (this.mEffects.get(i2).hasVibrator(i)) {
                    return true;
                }
            }
            return false;
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof android.os.CombinedVibration.Sequential)) {
                return false;
            }
            android.os.CombinedVibration.Sequential sequential = (android.os.CombinedVibration.Sequential) obj;
            return this.mDelays.equals(sequential.mDelays) && this.mEffects.equals(sequential.mEffects);
        }

        public int hashCode() {
            return java.util.Objects.hash(this.mEffects, this.mDelays);
        }

        public java.lang.String toString() {
            return "Sequential{mEffects=" + this.mEffects + ", mDelays=" + this.mDelays + '}';
        }

        @Override // android.os.CombinedVibration
        public java.lang.String toDebugString() {
            java.util.StringJoiner stringJoiner = new java.util.StringJoiner(",", "Sequential{", "}");
            for (int i = 0; i < this.mEffects.size(); i++) {
                stringJoiner.add(java.lang.String.format(java.util.Locale.ROOT, "delayMs=%d, effect=%s", this.mDelays.get(i), this.mEffects.get(i).toDebugString()));
            }
            return stringJoiner.toString();
        }

        @Override // android.os.CombinedVibration, android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeInt(3);
            parcel.writeInt(this.mEffects.size());
            for (int i2 = 0; i2 < this.mEffects.size(); i2++) {
                parcel.writeInt(this.mDelays.get(i2).intValue());
                this.mEffects.get(i2).writeToParcel(parcel, i);
            }
        }
    }
}
