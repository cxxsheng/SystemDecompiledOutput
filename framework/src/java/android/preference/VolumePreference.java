package android.preference;

@java.lang.Deprecated
/* loaded from: classes3.dex */
public class VolumePreference extends android.preference.SeekBarDialogPreference implements android.preference.PreferenceManager.OnActivityStopListener, android.view.View.OnKeyListener, android.preference.SeekBarVolumizer.Callback {
    private android.preference.SeekBarVolumizer mSeekBarVolumizer;
    private int mStreamType;

    public static class VolumeStore {
        public int volume = -1;
        public int originalVolume = -1;
    }

    public VolumePreference(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.VolumePreference, i, i2);
        this.mStreamType = obtainStyledAttributes.getInt(0, 0);
        obtainStyledAttributes.recycle();
    }

    public VolumePreference(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public VolumePreference(android.content.Context context, android.util.AttributeSet attributeSet) {
        this(context, attributeSet, com.android.internal.R.attr.seekBarDialogPreferenceStyle);
    }

    public VolumePreference(android.content.Context context) {
        this(context, null);
    }

    public void setStreamType(int i) {
        this.mStreamType = i;
    }

    @Override // android.preference.SeekBarDialogPreference, android.preference.DialogPreference
    protected void onBindDialogView(android.view.View view) {
        super.onBindDialogView(view);
        android.widget.SeekBar seekBar = (android.widget.SeekBar) view.findViewById(com.android.internal.R.id.seekbar);
        this.mSeekBarVolumizer = new android.preference.SeekBarVolumizer(getContext(), this.mStreamType, null, this);
        this.mSeekBarVolumizer.start();
        this.mSeekBarVolumizer.setSeekBar(seekBar);
        getPreferenceManager().registerOnActivityStopListener(this);
        view.setOnKeyListener(this);
        view.setFocusableInTouchMode(true);
        view.requestFocus();
    }

    @Override // android.preference.Preference
    public boolean onKey(android.view.View view, int i, android.view.KeyEvent keyEvent) {
        if (this.mSeekBarVolumizer == null) {
            return true;
        }
        boolean z = keyEvent.getAction() == 0;
        switch (i) {
            case 24:
                if (z) {
                    this.mSeekBarVolumizer.changeVolumeBy(1);
                }
                return true;
            case 25:
                if (z) {
                    this.mSeekBarVolumizer.changeVolumeBy(-1);
                }
                return true;
            case 164:
                if (z) {
                    this.mSeekBarVolumizer.muteVolume();
                }
                return true;
            default:
                return false;
        }
    }

    @Override // android.preference.DialogPreference
    protected void onDialogClosed(boolean z) {
        super.onDialogClosed(z);
        if (!z && this.mSeekBarVolumizer != null) {
            this.mSeekBarVolumizer.revertVolume();
        }
        cleanup();
    }

    @Override // android.preference.PreferenceManager.OnActivityStopListener
    public void onActivityStop() {
        if (this.mSeekBarVolumizer != null) {
            this.mSeekBarVolumizer.stopSample();
        }
    }

    private void cleanup() {
        getPreferenceManager().unregisterOnActivityStopListener(this);
        if (this.mSeekBarVolumizer != null) {
            android.app.Dialog dialog = getDialog();
            if (dialog != null && dialog.isShowing()) {
                android.view.View findViewById = dialog.getWindow().getDecorView().findViewById(com.android.internal.R.id.seekbar);
                if (findViewById != null) {
                    findViewById.setOnKeyListener(null);
                }
                this.mSeekBarVolumizer.revertVolume();
            }
            this.mSeekBarVolumizer.stop();
            this.mSeekBarVolumizer = null;
        }
    }

    @Override // android.preference.SeekBarVolumizer.Callback
    public void onSampleStarting(android.preference.SeekBarVolumizer seekBarVolumizer) {
        if (this.mSeekBarVolumizer != null && seekBarVolumizer != this.mSeekBarVolumizer) {
            this.mSeekBarVolumizer.stopSample();
        }
    }

    @Override // android.preference.SeekBarVolumizer.Callback
    public void onProgressChanged(android.widget.SeekBar seekBar, int i, boolean z) {
    }

    @Override // android.preference.SeekBarVolumizer.Callback
    public void onMuted(boolean z, boolean z2) {
    }

    @Override // android.preference.SeekBarVolumizer.Callback
    public void onStartTrackingTouch(android.preference.SeekBarVolumizer seekBarVolumizer) {
    }

    @Override // android.preference.DialogPreference, android.preference.Preference
    protected android.os.Parcelable onSaveInstanceState() {
        android.os.Parcelable onSaveInstanceState = super.onSaveInstanceState();
        if (isPersistent()) {
            return onSaveInstanceState;
        }
        android.preference.VolumePreference.SavedState savedState = new android.preference.VolumePreference.SavedState(onSaveInstanceState);
        if (this.mSeekBarVolumizer != null) {
            this.mSeekBarVolumizer.onSaveInstanceState(savedState.getVolumeStore());
        }
        return savedState;
    }

    @Override // android.preference.DialogPreference, android.preference.Preference
    protected void onRestoreInstanceState(android.os.Parcelable parcelable) {
        if (parcelable == null || !parcelable.getClass().equals(android.preference.VolumePreference.SavedState.class)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        android.preference.VolumePreference.SavedState savedState = (android.preference.VolumePreference.SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        if (this.mSeekBarVolumizer != null) {
            this.mSeekBarVolumizer.onRestoreInstanceState(savedState.getVolumeStore());
        }
    }

    private static class SavedState extends android.preference.Preference.BaseSavedState {
        public static final android.os.Parcelable.Creator<android.preference.VolumePreference.SavedState> CREATOR = new android.os.Parcelable.Creator<android.preference.VolumePreference.SavedState>() { // from class: android.preference.VolumePreference.SavedState.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.preference.VolumePreference.SavedState createFromParcel(android.os.Parcel parcel) {
                return new android.preference.VolumePreference.SavedState(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.preference.VolumePreference.SavedState[] newArray(int i) {
                return new android.preference.VolumePreference.SavedState[i];
            }
        };
        android.preference.VolumePreference.VolumeStore mVolumeStore;

        public SavedState(android.os.Parcel parcel) {
            super(parcel);
            this.mVolumeStore = new android.preference.VolumePreference.VolumeStore();
            this.mVolumeStore.volume = parcel.readInt();
            this.mVolumeStore.originalVolume = parcel.readInt();
        }

        @Override // android.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.mVolumeStore.volume);
            parcel.writeInt(this.mVolumeStore.originalVolume);
        }

        android.preference.VolumePreference.VolumeStore getVolumeStore() {
            return this.mVolumeStore;
        }

        public SavedState(android.os.Parcelable parcelable) {
            super(parcelable);
            this.mVolumeStore = new android.preference.VolumePreference.VolumeStore();
        }
    }
}
