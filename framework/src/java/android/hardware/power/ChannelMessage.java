package android.hardware.power;

/* loaded from: classes2.dex */
public class ChannelMessage implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.power.ChannelMessage> CREATOR = new android.os.Parcelable.Creator<android.hardware.power.ChannelMessage>() { // from class: android.hardware.power.ChannelMessage.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.power.ChannelMessage createFromParcel(android.os.Parcel parcel) {
            android.hardware.power.ChannelMessage channelMessage = new android.hardware.power.ChannelMessage();
            channelMessage.readFromParcel(parcel);
            return channelMessage;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.power.ChannelMessage[] newArray(int i) {
            return new android.hardware.power.ChannelMessage[i];
        }
    };
    public android.hardware.power.ChannelMessage.ChannelMessageContents data;
    public int sessionID = 0;
    public long timeStampNanos = 0;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.sessionID);
        parcel.writeLong(this.timeStampNanos);
        parcel.writeTypedObject(this.data, i);
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
            this.sessionID = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.timeStampNanos = parcel.readLong();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.data = (android.hardware.power.ChannelMessage.ChannelMessageContents) parcel.readTypedObject(android.hardware.power.ChannelMessage.ChannelMessageContents.CREATOR);
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
        return describeContents(this.data) | 0;
    }

    private int describeContents(java.lang.Object obj) {
        if (obj == null || !(obj instanceof android.os.Parcelable)) {
            return 0;
        }
        return ((android.os.Parcelable) obj).describeContents();
    }

    public static final class ChannelMessageContents implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.hardware.power.ChannelMessage.ChannelMessageContents> CREATOR = new android.os.Parcelable.Creator<android.hardware.power.ChannelMessage.ChannelMessageContents>() { // from class: android.hardware.power.ChannelMessage.ChannelMessageContents.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.hardware.power.ChannelMessage.ChannelMessageContents createFromParcel(android.os.Parcel parcel) {
                return new android.hardware.power.ChannelMessage.ChannelMessageContents(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.hardware.power.ChannelMessage.ChannelMessageContents[] newArray(int i) {
                return new android.hardware.power.ChannelMessage.ChannelMessageContents[i];
            }
        };
        public static final int hint = 2;
        public static final int mode = 3;
        public static final int reserved = 0;
        public static final int targetDuration = 1;
        public static final int workDuration = 4;
        private int _tag;
        private java.lang.Object _value;

        public @interface Tag {
            public static final byte hint = 2;
            public static final byte mode = 3;
            public static final byte reserved = 0;
            public static final byte targetDuration = 1;
            public static final byte workDuration = 4;
        }

        public ChannelMessageContents() {
            this._tag = 0;
            this._value = new long[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        }

        private ChannelMessageContents(android.os.Parcel parcel) {
            readFromParcel(parcel);
        }

        private ChannelMessageContents(int i, java.lang.Object obj) {
            this._tag = i;
            this._value = obj;
        }

        public int getTag() {
            return this._tag;
        }

        public static android.hardware.power.ChannelMessage.ChannelMessageContents reserved(long[] jArr) {
            return new android.hardware.power.ChannelMessage.ChannelMessageContents(0, jArr);
        }

        public long[] getReserved() {
            _assertTag(0);
            return (long[]) this._value;
        }

        public void setReserved(long[] jArr) {
            _set(0, jArr);
        }

        public static android.hardware.power.ChannelMessage.ChannelMessageContents targetDuration(long j) {
            return new android.hardware.power.ChannelMessage.ChannelMessageContents(1, java.lang.Long.valueOf(j));
        }

        public long getTargetDuration() {
            _assertTag(1);
            return ((java.lang.Long) this._value).longValue();
        }

        public void setTargetDuration(long j) {
            _set(1, java.lang.Long.valueOf(j));
        }

        public static android.hardware.power.ChannelMessage.ChannelMessageContents hint(int i) {
            return new android.hardware.power.ChannelMessage.ChannelMessageContents(2, java.lang.Integer.valueOf(i));
        }

        public int getHint() {
            _assertTag(2);
            return ((java.lang.Integer) this._value).intValue();
        }

        public void setHint(int i) {
            _set(2, java.lang.Integer.valueOf(i));
        }

        public static android.hardware.power.ChannelMessage.ChannelMessageContents mode(android.hardware.power.ChannelMessage.ChannelMessageContents.SessionModeSetter sessionModeSetter) {
            return new android.hardware.power.ChannelMessage.ChannelMessageContents(3, sessionModeSetter);
        }

        public android.hardware.power.ChannelMessage.ChannelMessageContents.SessionModeSetter getMode() {
            _assertTag(3);
            return (android.hardware.power.ChannelMessage.ChannelMessageContents.SessionModeSetter) this._value;
        }

        public void setMode(android.hardware.power.ChannelMessage.ChannelMessageContents.SessionModeSetter sessionModeSetter) {
            _set(3, sessionModeSetter);
        }

        public static android.hardware.power.ChannelMessage.ChannelMessageContents workDuration(android.hardware.power.WorkDurationFixedV1 workDurationFixedV1) {
            return new android.hardware.power.ChannelMessage.ChannelMessageContents(4, workDurationFixedV1);
        }

        public android.hardware.power.WorkDurationFixedV1 getWorkDuration() {
            _assertTag(4);
            return (android.hardware.power.WorkDurationFixedV1) this._value;
        }

        public void setWorkDuration(android.hardware.power.WorkDurationFixedV1 workDurationFixedV1) {
            _set(4, workDurationFixedV1);
        }

        @Override // android.os.Parcelable
        public final int getStability() {
            return 1;
        }

        @Override // android.os.Parcelable
        public final void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeInt(this._tag);
            switch (this._tag) {
                case 0:
                    parcel.writeFixedArray(getReserved(), i, 16);
                    break;
                case 1:
                    parcel.writeLong(getTargetDuration());
                    break;
                case 2:
                    parcel.writeInt(getHint());
                    break;
                case 3:
                    parcel.writeTypedObject(getMode(), i);
                    break;
                case 4:
                    parcel.writeTypedObject(getWorkDuration(), i);
                    break;
            }
        }

        public void readFromParcel(android.os.Parcel parcel) {
            int readInt = parcel.readInt();
            switch (readInt) {
                case 0:
                    _set(readInt, (long[]) parcel.createFixedArray(long[].class, 16));
                    return;
                case 1:
                    _set(readInt, java.lang.Long.valueOf(parcel.readLong()));
                    return;
                case 2:
                    _set(readInt, java.lang.Integer.valueOf(parcel.readInt()));
                    return;
                case 3:
                    _set(readInt, (android.hardware.power.ChannelMessage.ChannelMessageContents.SessionModeSetter) parcel.readTypedObject(android.hardware.power.ChannelMessage.ChannelMessageContents.SessionModeSetter.CREATOR));
                    return;
                case 4:
                    _set(readInt, (android.hardware.power.WorkDurationFixedV1) parcel.readTypedObject(android.hardware.power.WorkDurationFixedV1.CREATOR));
                    return;
                default:
                    throw new java.lang.IllegalArgumentException("union: unknown tag: " + readInt);
            }
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            switch (getTag()) {
                case 3:
                    return 0 | describeContents(getMode());
                case 4:
                    return 0 | describeContents(getWorkDuration());
                default:
                    return 0;
            }
        }

        private int describeContents(java.lang.Object obj) {
            if (obj == null || !(obj instanceof android.os.Parcelable)) {
                return 0;
            }
            return ((android.os.Parcelable) obj).describeContents();
        }

        private void _assertTag(int i) {
            if (getTag() != i) {
                throw new java.lang.IllegalStateException("bad access: " + _tagString(i) + ", " + _tagString(getTag()) + " is available.");
            }
        }

        private java.lang.String _tagString(int i) {
            switch (i) {
                case 0:
                    return "reserved";
                case 1:
                    return "targetDuration";
                case 2:
                    return "hint";
                case 3:
                    return "mode";
                case 4:
                    return "workDuration";
                default:
                    throw new java.lang.IllegalStateException("unknown field: " + i);
            }
        }

        private void _set(int i, java.lang.Object obj) {
            this._tag = i;
            this._value = obj;
        }

        public static class SessionModeSetter implements android.os.Parcelable {
            public static final android.os.Parcelable.Creator<android.hardware.power.ChannelMessage.ChannelMessageContents.SessionModeSetter> CREATOR = new android.os.Parcelable.Creator<android.hardware.power.ChannelMessage.ChannelMessageContents.SessionModeSetter>() { // from class: android.hardware.power.ChannelMessage.ChannelMessageContents.SessionModeSetter.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public android.hardware.power.ChannelMessage.ChannelMessageContents.SessionModeSetter createFromParcel(android.os.Parcel parcel) {
                    android.hardware.power.ChannelMessage.ChannelMessageContents.SessionModeSetter sessionModeSetter = new android.hardware.power.ChannelMessage.ChannelMessageContents.SessionModeSetter();
                    sessionModeSetter.readFromParcel(parcel);
                    return sessionModeSetter;
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public android.hardware.power.ChannelMessage.ChannelMessageContents.SessionModeSetter[] newArray(int i) {
                    return new android.hardware.power.ChannelMessage.ChannelMessageContents.SessionModeSetter[i];
                }
            };
            public boolean enabled = false;
            public int modeInt;

            @Override // android.os.Parcelable
            public final int getStability() {
                return 1;
            }

            @Override // android.os.Parcelable
            public final void writeToParcel(android.os.Parcel parcel, int i) {
                int dataPosition = parcel.dataPosition();
                parcel.writeInt(0);
                parcel.writeInt(this.modeInt);
                parcel.writeBoolean(this.enabled);
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
                    this.modeInt = parcel.readInt();
                    if (parcel.dataPosition() - dataPosition >= readInt) {
                        if (dataPosition > Integer.MAX_VALUE - readInt) {
                            throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                        }
                        parcel.setDataPosition(dataPosition + readInt);
                    } else {
                        this.enabled = parcel.readBoolean();
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
                return 0;
            }
        }
    }
}
