package ru.mirea.fedotov.dialog;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;

public class MyDateDialogFragment extends DatePickerDialog {
    @RequiresApi(api = Build.VERSION_CODES.N)
    public MyDateDialogFragment(@NonNull Context context) {
        super(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public MyDateDialogFragment(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    public MyDateDialogFragment(@NonNull Context context, @Nullable OnDateSetListener listener, int year, int month, int dayOfMonth) {
        super(context, listener, year, month, dayOfMonth);
    }

    public MyDateDialogFragment(@NonNull Context context, int themeResId, @Nullable OnDateSetListener listener, int year, int monthOfYear, int dayOfMonth) {
        super(context, themeResId, listener, year, monthOfYear, dayOfMonth);
    }

}
