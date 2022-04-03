package ru.mirea.fedotov.dialog;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private final Calendar calendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickShowDialog(View view) {
        MyDialogFragment dialogFragment = new MyDialogFragment();
        dialogFragment.show(getSupportFragmentManager(), "mirea");
    }

    public void onOkClicked() {
        Toast.makeText(getApplicationContext(), "Вы выбрали кнопку \"Иду дальше\"!",
                Toast.LENGTH_LONG).show();
    }
    public void onCancelClicked() {
        Toast.makeText(getApplicationContext(), "Вы выбрали кнопку \"Нет\"!",
                Toast.LENGTH_LONG).show();
    }
    public void onNeutralClicked() {
        Toast.makeText(getApplicationContext(), "Вы выбрали кнопку \"На паузе\"!",
                Toast.LENGTH_LONG).show();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void onClickTimeDialog(View view) {
        MyTimeDialogFragment myTimeDialogFragment = new MyTimeDialogFragment(
                this,
                (timePicker, i, i1) -> Toast.makeText(
                        getApplicationContext(),
                        "Вы выбрали время " + timePicker.getHour() + ":" + timePicker.getMinute(),
                        Toast.LENGTH_LONG).show(),
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true);
        myTimeDialogFragment.show();
    }

    public void onClickDateDialog(View view) {
        MyDateDialogFragment myDateDialogFragment = new MyDateDialogFragment(
                this,
                (datePicker, i, i1, i2) -> Toast.makeText(
                        getApplicationContext(),
                        "Вы выбрали дату " +
                                datePicker.getDayOfMonth() + "/" +
                                datePicker.getMonth() + "/" +
                                datePicker.getYear(),
                        Toast.LENGTH_LONG).show(),
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        myDateDialogFragment.show();
    }

    public void onClickProgressDialog(View view) throws InterruptedException {
        final int delayMilliseconds = 3000;

        MyProgressDialogFragment myProgressDialogFragment = new MyProgressDialogFragment(this);
        myProgressDialogFragment.setTitle("Progress Dialog");
        myProgressDialogFragment.setMessage("Loading...");
        myProgressDialogFragment.show();

        Handler progressDialogCanceller = new Handler();
        progressDialogCanceller.postDelayed(() -> myProgressDialogFragment.cancel(), delayMilliseconds);
    }

}