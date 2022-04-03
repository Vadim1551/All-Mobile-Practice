package ru.mirea.fedotov.dialog;

import android.app.ProgressDialog;
import android.content.Context;

import androidx.fragment.app.DialogFragment;

public class MyProgressDialogFragment extends ProgressDialog {
    public MyProgressDialogFragment(Context context) {
        super(context);
    }

    public MyProgressDialogFragment(Context context, int theme) {
        super(context, theme);
    }
}
