package android.media.session;

/* loaded from: classes2.dex */
public final class PlaybackState implements android.os.Parcelable {
    public static final long ACTION_FAST_FORWARD = 64;
    public static final long ACTION_PAUSE = 2;
    public static final long ACTION_PLAY = 4;
    public static final long ACTION_PLAY_FROM_MEDIA_ID = 1024;
    public static final long ACTION_PLAY_FROM_SEARCH = 2048;
    public static final long ACTION_PLAY_FROM_URI = 8192;
    public static final long ACTION_PLAY_PAUSE = 512;
    public static final long ACTION_PREPARE = 16384;
    public static final long ACTION_PREPARE_FROM_MEDIA_ID = 32768;
    public static final long ACTION_PREPARE_FROM_SEARCH = 65536;
    public static final long ACTION_PREPARE_FROM_URI = 131072;
    public static final long ACTION_REWIND = 8;
    public static final long ACTION_SEEK_TO = 256;
    public static final long ACTION_SET_PLAYBACK_SPEED = 4194304;
    public static final long ACTION_SET_RATING = 128;
    public static final long ACTION_SKIP_TO_NEXT = 32;
    public static final long ACTION_SKIP_TO_PREVIOUS = 16;
    public static final long ACTION_SKIP_TO_QUEUE_ITEM = 4096;
    public static final long ACTION_STOP = 1;
    public static final android.os.Parcelable.Creator<android.media.session.PlaybackState> CREATOR = new android.os.Parcelable.Creator<android.media.session.PlaybackState>() { // from class: android.media.session.PlaybackState.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.session.PlaybackState createFromParcel(android.os.Parcel parcel) {
            return new android.media.session.PlaybackState(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.session.PlaybackState[] newArray(int i) {
            return new android.media.session.PlaybackState[i];
        }
    };
    public static final long PLAYBACK_POSITION_UNKNOWN = -1;
    public static final int STATE_BUFFERING = 6;
    public static final int STATE_CONNECTING = 8;
    public static final int STATE_ERROR = 7;
    public static final int STATE_FAST_FORWARDING = 4;
    public static final int STATE_NONE = 0;
    public static final int STATE_PAUSED = 2;
    public static final int STATE_PLAYBACK_SUPPRESSED = 12;
    public static final int STATE_PLAYING = 3;
    public static final int STATE_REWINDING = 5;
    public static final int STATE_SKIPPING_TO_NEXT = 10;
    public static final int STATE_SKIPPING_TO_PREVIOUS = 9;
    public static final int STATE_SKIPPING_TO_QUEUE_ITEM = 11;
    public static final int STATE_STOPPED = 1;
    private static final java.lang.String TAG = "PlaybackState";
    private final long mActions;
    private final long mActiveItemId;
    private final long mBufferedPosition;
    private java.util.List<android.media.session.PlaybackState.CustomAction> mCustomActions;
    private final java.lang.CharSequence mErrorMessage;
    private final android.os.Bundle mExtras;
    private final long mPosition;
    private final float mSpeed;
    private final int mState;
    private final long mUpdateTime;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Actions {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface State {
    }

    private PlaybackState(int i, long j, long j2, float f, long j3, long j4, java.util.List<android.media.session.PlaybackState.CustomAction> list, long j5, java.lang.CharSequence charSequence, android.os.Bundle bundle) {
        this.mState = i;
        this.mPosition = j;
        this.mSpeed = f;
        this.mUpdateTime = j2;
        this.mBufferedPosition = j3;
        this.mActions = j4;
        this.mCustomActions = new java.util.ArrayList(list);
        this.mActiveItemId = j5;
        this.mErrorMessage = charSequence;
        this.mExtras = bundle;
    }

    private PlaybackState(android.os.Parcel parcel) {
        this.mState = parcel.readInt();
        this.mPosition = parcel.readLong();
        this.mSpeed = parcel.readFloat();
        this.mUpdateTime = parcel.readLong();
        this.mBufferedPosition = parcel.readLong();
        this.mActions = parcel.readLong();
        this.mCustomActions = parcel.createTypedArrayList(android.media.session.PlaybackState.CustomAction.CREATOR);
        this.mActiveItemId = parcel.readLong();
        this.mErrorMessage = android.text.TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.mExtras = parcel.readBundle();
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder("PlaybackState {");
        sb.append("state=").append(getStringForStateInt(this.mState)).append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_START).append(this.mState).append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        sb.append(", position=").append(this.mPosition);
        sb.append(", buffered position=").append(this.mBufferedPosition);
        sb.append(", speed=").append(this.mSpeed);
        sb.append(", updated=").append(this.mUpdateTime);
        sb.append(", actions=").append(this.mActions);
        sb.append(", custom actions=").append(this.mCustomActions);
        sb.append(", active item id=").append(this.mActiveItemId);
        sb.append(", error=").append(this.mErrorMessage);
        sb.append("}");
        return sb.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mState);
        parcel.writeLong(this.mPosition);
        parcel.writeFloat(this.mSpeed);
        parcel.writeLong(this.mUpdateTime);
        parcel.writeLong(this.mBufferedPosition);
        parcel.writeLong(this.mActions);
        parcel.writeTypedList(this.mCustomActions);
        parcel.writeLong(this.mActiveItemId);
        android.text.TextUtils.writeToParcel(this.mErrorMessage, parcel, 0);
        parcel.writeBundle(this.mExtras);
    }

    public int getState() {
        return this.mState;
    }

    public long getPosition() {
        return this.mPosition;
    }

    public long getBufferedPosition() {
        return this.mBufferedPosition;
    }

    public float getPlaybackSpeed() {
        return this.mSpeed;
    }

    public long getActions() {
        return this.mActions;
    }

    public java.util.List<android.media.session.PlaybackState.CustomAction> getCustomActions() {
        return this.mCustomActions;
    }

    public java.lang.CharSequence getErrorMessage() {
        return this.mErrorMessage;
    }

    public long getLastPositionUpdateTime() {
        return this.mUpdateTime;
    }

    public long getActiveQueueItemId() {
        return this.mActiveItemId;
    }

    public android.os.Bundle getExtras() {
        return this.mExtras;
    }

    public boolean isActive() {
        switch (this.mState) {
            case 3:
            case 4:
            case 5:
            case 6:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
                return true;
            case 7:
            default:
                return false;
        }
    }

    private static java.lang.String getStringForStateInt(int i) {
        switch (i) {
            case 0:
                return android.security.keystore.KeyProperties.DIGEST_NONE;
            case 1:
                return "STOPPED";
            case 2:
                return "PAUSED";
            case 3:
                return "PLAYING";
            case 4:
                return "FAST_FORWARDING";
            case 5:
                return "REWINDING";
            case 6:
                return "BUFFERING";
            case 7:
                return android.service.timezone.TimeZoneProviderService.TEST_COMMAND_RESULT_ERROR_KEY;
            case 8:
                return "CONNECTING";
            case 9:
                return "SKIPPING_TO_PREVIOUS";
            case 10:
                return "SKIPPING_TO_NEXT";
            case 11:
                return "SKIPPING_TO_QUEUE_ITEM";
            case 12:
                return "STATE_PLAYBACK_SUPPRESSED";
            default:
                return "UNKNOWN";
        }
    }

    public static final class CustomAction implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.media.session.PlaybackState.CustomAction> CREATOR = new android.os.Parcelable.Creator<android.media.session.PlaybackState.CustomAction>() { // from class: android.media.session.PlaybackState.CustomAction.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.media.session.PlaybackState.CustomAction createFromParcel(android.os.Parcel parcel) {
                return new android.media.session.PlaybackState.CustomAction(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.media.session.PlaybackState.CustomAction[] newArray(int i) {
                return new android.media.session.PlaybackState.CustomAction[i];
            }
        };
        private final java.lang.String mAction;
        private final android.os.Bundle mExtras;
        private final int mIcon;
        private final java.lang.CharSequence mName;

        private CustomAction(java.lang.String str, java.lang.CharSequence charSequence, int i, android.os.Bundle bundle) {
            this.mAction = str;
            this.mName = charSequence;
            this.mIcon = i;
            this.mExtras = bundle;
        }

        private CustomAction(android.os.Parcel parcel) {
            this.mAction = parcel.readString();
            this.mName = android.text.TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
            this.mIcon = parcel.readInt();
            this.mExtras = parcel.readBundle();
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeString(this.mAction);
            android.text.TextUtils.writeToParcel(this.mName, parcel, i);
            parcel.writeInt(this.mIcon);
            parcel.writeBundle(this.mExtras);
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public java.lang.String getAction() {
            return this.mAction;
        }

        public java.lang.CharSequence getName() {
            return this.mName;
        }

        public int getIcon() {
            return this.mIcon;
        }

        public android.os.Bundle getExtras() {
            return this.mExtras;
        }

        public java.lang.String toString() {
            return "Action:mName='" + ((java.lang.Object) this.mName) + ", mIcon=" + this.mIcon + ", mExtras=" + this.mExtras;
        }

        public static final class Builder {
            private final java.lang.String mAction;
            private android.os.Bundle mExtras;
            private final int mIcon;
            private final java.lang.CharSequence mName;

            public Builder(java.lang.String str, java.lang.CharSequence charSequence, int i) {
                if (android.text.TextUtils.isEmpty(str)) {
                    throw new java.lang.IllegalArgumentException("You must specify an action to build a CustomAction.");
                }
                if (android.text.TextUtils.isEmpty(charSequence)) {
                    throw new java.lang.IllegalArgumentException("You must specify a name to build a CustomAction.");
                }
                if (i == 0) {
                    throw new java.lang.IllegalArgumentException("You must specify an icon resource id to build a CustomAction.");
                }
                this.mAction = str;
                this.mName = charSequence;
                this.mIcon = i;
            }

            public android.media.session.PlaybackState.CustomAction.Builder setExtras(android.os.Bundle bundle) {
                this.mExtras = bundle;
                return this;
            }

            public android.media.session.PlaybackState.CustomAction build() {
                return new android.media.session.PlaybackState.CustomAction(this.mAction, this.mName, this.mIcon, this.mExtras);
            }
        }
    }

    public static final class Builder {
        private long mActions;
        private long mActiveItemId;
        private long mBufferedPosition;
        private final java.util.List<android.media.session.PlaybackState.CustomAction> mCustomActions;
        private java.lang.CharSequence mErrorMessage;
        private android.os.Bundle mExtras;
        private long mPosition;
        private float mSpeed;
        private int mState;
        private long mUpdateTime;

        public Builder() {
            this.mCustomActions = new java.util.ArrayList();
            this.mActiveItemId = -1L;
        }

        public Builder(android.media.session.PlaybackState playbackState) {
            this.mCustomActions = new java.util.ArrayList();
            this.mActiveItemId = -1L;
            if (playbackState == null) {
                return;
            }
            this.mState = playbackState.mState;
            this.mPosition = playbackState.mPosition;
            this.mBufferedPosition = playbackState.mBufferedPosition;
            this.mSpeed = playbackState.mSpeed;
            this.mActions = playbackState.mActions;
            if (playbackState.mCustomActions != null) {
                this.mCustomActions.addAll(playbackState.mCustomActions);
            }
            this.mErrorMessage = playbackState.mErrorMessage;
            this.mUpdateTime = playbackState.mUpdateTime;
            this.mActiveItemId = playbackState.mActiveItemId;
            this.mExtras = playbackState.mExtras;
        }

        public android.media.session.PlaybackState.Builder setState(int i, long j, float f, long j2) {
            this.mState = i;
            this.mPosition = j;
            this.mUpdateTime = j2;
            this.mSpeed = f;
            return this;
        }

        public android.media.session.PlaybackState.Builder setState(int i, long j, float f) {
            return setState(i, j, f, android.os.SystemClock.elapsedRealtime());
        }

        public android.media.session.PlaybackState.Builder setActions(long j) {
            this.mActions = j;
            return this;
        }

        public android.media.session.PlaybackState.Builder addCustomAction(java.lang.String str, java.lang.String str2, int i) {
            return addCustomAction(new android.media.session.PlaybackState.CustomAction(str, str2, i, null));
        }

        public android.media.session.PlaybackState.Builder addCustomAction(android.media.session.PlaybackState.CustomAction customAction) {
            if (customAction == null) {
                throw new java.lang.IllegalArgumentException("You may not add a null CustomAction to PlaybackState.");
            }
            this.mCustomActions.add(customAction);
            return this;
        }

        public android.media.session.PlaybackState.Builder setBufferedPosition(long j) {
            this.mBufferedPosition = j;
            return this;
        }

        public android.media.session.PlaybackState.Builder setActiveQueueItemId(long j) {
            this.mActiveItemId = j;
            return this;
        }

        public android.media.session.PlaybackState.Builder setErrorMessage(java.lang.CharSequence charSequence) {
            this.mErrorMessage = charSequence;
            return this;
        }

        public android.media.session.PlaybackState.Builder setExtras(android.os.Bundle bundle) {
            this.mExtras = bundle;
            return this;
        }

        public android.media.session.PlaybackState build() {
            return new android.media.session.PlaybackState(this.mState, this.mPosition, this.mUpdateTime, this.mSpeed, this.mBufferedPosition, this.mActions, this.mCustomActions, this.mActiveItemId, this.mErrorMessage, this.mExtras);
        }
    }
}
