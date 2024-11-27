package android.media.audio.common;

/* loaded from: classes2.dex */
public class AudioHalEngineConfig implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.media.audio.common.AudioHalEngineConfig> CREATOR = new android.os.Parcelable.Creator<android.media.audio.common.AudioHalEngineConfig>() { // from class: android.media.audio.common.AudioHalEngineConfig.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.audio.common.AudioHalEngineConfig createFromParcel(android.os.Parcel parcel) {
            android.media.audio.common.AudioHalEngineConfig audioHalEngineConfig = new android.media.audio.common.AudioHalEngineConfig();
            audioHalEngineConfig.readFromParcel(parcel);
            return audioHalEngineConfig;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.audio.common.AudioHalEngineConfig[] newArray(int i) {
            return new android.media.audio.common.AudioHalEngineConfig[i];
        }
    };
    public android.media.audio.common.AudioHalEngineConfig.CapSpecificConfig capSpecificConfig;
    public int defaultProductStrategyId = -1;
    public android.media.audio.common.AudioHalProductStrategy[] productStrategies;
    public android.media.audio.common.AudioHalVolumeGroup[] volumeGroups;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.defaultProductStrategyId);
        parcel.writeTypedArray(this.productStrategies, i);
        parcel.writeTypedArray(this.volumeGroups, i);
        parcel.writeTypedObject(this.capSpecificConfig, i);
        int dataPosition2 = parcel.dataPosition();
        parcel.setDataPosition(dataPosition);
        parcel.writeInt(dataPosition2 - dataPosition);
        parcel.setDataPosition(dataPosition2);
    }

    public final void readFromParcel(android.os.Parcel parcel) {
        int dataPosition = parcel.dataPosition();
        int readInt = parcel.readInt();
        try {
            if (readInt < 4) {
                throw new android.os.BadParcelableException("Parcelable too small");
            }
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.defaultProductStrategyId = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.productStrategies = (android.media.audio.common.AudioHalProductStrategy[]) parcel.createTypedArray(android.media.audio.common.AudioHalProductStrategy.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.volumeGroups = (android.media.audio.common.AudioHalVolumeGroup[]) parcel.createTypedArray(android.media.audio.common.AudioHalVolumeGroup.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.capSpecificConfig = (android.media.audio.common.AudioHalEngineConfig.CapSpecificConfig) parcel.readTypedObject(android.media.audio.common.AudioHalEngineConfig.CapSpecificConfig.CREATOR);
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            }
        } catch (java.lang.Throwable th) {
            if (dataPosition > Integer.MAX_VALUE - readInt) {
                throw new android.os.BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(dataPosition + readInt);
            throw th;
        }
    }

    public java.lang.String toString() {
        java.util.StringJoiner stringJoiner = new java.util.StringJoiner(", ", "{", "}");
        stringJoiner.add("defaultProductStrategyId: " + this.defaultProductStrategyId);
        stringJoiner.add("productStrategies: " + java.util.Arrays.toString(this.productStrategies));
        stringJoiner.add("volumeGroups: " + java.util.Arrays.toString(this.volumeGroups));
        stringJoiner.add("capSpecificConfig: " + java.util.Objects.toString(this.capSpecificConfig));
        return "AudioHalEngineConfig" + stringJoiner.toString();
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof android.media.audio.common.AudioHalEngineConfig)) {
            return false;
        }
        android.media.audio.common.AudioHalEngineConfig audioHalEngineConfig = (android.media.audio.common.AudioHalEngineConfig) obj;
        if (java.util.Objects.deepEquals(java.lang.Integer.valueOf(this.defaultProductStrategyId), java.lang.Integer.valueOf(audioHalEngineConfig.defaultProductStrategyId)) && java.util.Objects.deepEquals(this.productStrategies, audioHalEngineConfig.productStrategies) && java.util.Objects.deepEquals(this.volumeGroups, audioHalEngineConfig.volumeGroups) && java.util.Objects.deepEquals(this.capSpecificConfig, audioHalEngineConfig.capSpecificConfig)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Arrays.deepHashCode(java.util.Arrays.asList(java.lang.Integer.valueOf(this.defaultProductStrategyId), this.productStrategies, this.volumeGroups, this.capSpecificConfig).toArray());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.productStrategies) | 0 | describeContents(this.volumeGroups) | describeContents(this.capSpecificConfig);
    }

    private int describeContents(java.lang.Object obj) {
        if (obj == null) {
            return 0;
        }
        if (obj instanceof java.lang.Object[]) {
            int i = 0;
            for (java.lang.Object obj2 : (java.lang.Object[]) obj) {
                i |= describeContents(obj2);
            }
            return i;
        }
        if (!(obj instanceof android.os.Parcelable)) {
            return 0;
        }
        return ((android.os.Parcelable) obj).describeContents();
    }

    public static class CapSpecificConfig implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.media.audio.common.AudioHalEngineConfig.CapSpecificConfig> CREATOR = new android.os.Parcelable.Creator<android.media.audio.common.AudioHalEngineConfig.CapSpecificConfig>() { // from class: android.media.audio.common.AudioHalEngineConfig.CapSpecificConfig.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.media.audio.common.AudioHalEngineConfig.CapSpecificConfig createFromParcel(android.os.Parcel parcel) {
                android.media.audio.common.AudioHalEngineConfig.CapSpecificConfig capSpecificConfig = new android.media.audio.common.AudioHalEngineConfig.CapSpecificConfig();
                capSpecificConfig.readFromParcel(parcel);
                return capSpecificConfig;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.media.audio.common.AudioHalEngineConfig.CapSpecificConfig[] newArray(int i) {
                return new android.media.audio.common.AudioHalEngineConfig.CapSpecificConfig[i];
            }
        };
        public android.media.audio.common.AudioHalCapCriterion[] criteria;
        public android.media.audio.common.AudioHalCapCriterionType[] criterionTypes;

        @Override // android.os.Parcelable
        public final int getStability() {
            return 1;
        }

        @Override // android.os.Parcelable
        public final void writeToParcel(android.os.Parcel parcel, int i) {
            int dataPosition = parcel.dataPosition();
            parcel.writeInt(0);
            parcel.writeTypedArray(this.criteria, i);
            parcel.writeTypedArray(this.criterionTypes, i);
            int dataPosition2 = parcel.dataPosition();
            parcel.setDataPosition(dataPosition);
            parcel.writeInt(dataPosition2 - dataPosition);
            parcel.setDataPosition(dataPosition2);
        }

        public final void readFromParcel(android.os.Parcel parcel) {
            int dataPosition = parcel.dataPosition();
            int readInt = parcel.readInt();
            try {
                if (readInt < 4) {
                    throw new android.os.BadParcelableException("Parcelable too small");
                }
                if (parcel.dataPosition() - dataPosition >= readInt) {
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                    }
                    parcel.setDataPosition(dataPosition + readInt);
                    return;
                }
                this.criteria = (android.media.audio.common.AudioHalCapCriterion[]) parcel.createTypedArray(android.media.audio.common.AudioHalCapCriterion.CREATOR);
                if (parcel.dataPosition() - dataPosition >= readInt) {
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                    }
                    parcel.setDataPosition(dataPosition + readInt);
                } else {
                    this.criterionTypes = (android.media.audio.common.AudioHalCapCriterionType[]) parcel.createTypedArray(android.media.audio.common.AudioHalCapCriterionType.CREATOR);
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                    }
                    parcel.setDataPosition(dataPosition + readInt);
                }
            } catch (java.lang.Throwable th) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                throw th;
            }
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return describeContents(this.criteria) | 0 | describeContents(this.criterionTypes);
        }

        private int describeContents(java.lang.Object obj) {
            if (obj == null) {
                return 0;
            }
            if (obj instanceof java.lang.Object[]) {
                int i = 0;
                for (java.lang.Object obj2 : (java.lang.Object[]) obj) {
                    i |= describeContents(obj2);
                }
                return i;
            }
            if (!(obj instanceof android.os.Parcelable)) {
                return 0;
            }
            return ((android.os.Parcelable) obj).describeContents();
        }
    }
}
