package ru.mirea.fedotov.firebaseauth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {
    private EditText emailText;
    private EditText passwordText;
    private TextView statusText;
    private FirebaseAuth auth;
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        emailText = findViewById(R.id.email);
        passwordText = findViewById(R.id.pass);
        statusText = findViewById(R.id.statusTV);
        auth = FirebaseAuth.getInstance();
    }
    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = auth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            statusText.setText(getString(R.string.emailpassword_status_fmt,
                    user.getEmail(), user.isEmailVerified()));
            findViewById(R.id.email).setVisibility(View.GONE);
            findViewById(R.id.pass).setVisibility(View.GONE);
            findViewById(R.id.signInBtn).setVisibility(View.GONE);
            findViewById(R.id.createBtn).setVisibility(View.GONE);
            findViewById(R.id.signOutBtn).setVisibility(View.VISIBLE);

        } else {
            statusText.setText(R.string.signed_out);
            findViewById(R.id.pass).setVisibility(View.VISIBLE);
            findViewById(R.id.email).setVisibility(View.VISIBLE);
            findViewById(R.id.signInBtn).setVisibility(View.VISIBLE);
            findViewById(R.id.createBtn).setVisibility(View.VISIBLE);
            findViewById(R.id.signOutBtn).setVisibility(View.VISIBLE);
        }
    }

    private boolean validateForm() {
        boolean valid = true;

        String email = emailText.getText().toString();
        if (TextUtils.isEmpty(email)) {
            emailText.setError("Required.");
            valid = false;
        } else {
            emailText.setError(null);
        }

        String password = passwordText.getText().toString();
        if (TextUtils.isEmpty(password)) {
            passwordText.setError("Required.");
            valid = false;
        } else {
            passwordText.setError(null);
        }
        return valid;
    }

    private void createAccount(String email, String password) {
        Log.d(TAG, "createAccount:" + email);
        if (!validateForm()) {
            return;
        }
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, task -> {
            if (task.isSuccessful()) {
                Log.d(TAG, "createUserWithEmail:success");
                FirebaseUser user = auth.getCurrentUser();
                updateUI(user);
            } else {
                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                Toast.makeText(MainActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                updateUI(null);
            }
        });
    }

    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);
        if (!validateForm()) {
            return;
        }
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = auth.getCurrentUser();
                            updateUI(user);
                        } else {
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                        if (!task.isSuccessful()) {
                            statusText.setText(R.string.auth_failed);
                        }
                    }
                });
    }
    private void signOut() {
        auth.signOut();
        updateUI(null);
    }

    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.createBtn) {
            createAccount(emailText.getText().toString(),
                    passwordText.getText().toString());
        } else if (i == R.id.signInBtn) {
            signIn(emailText.getText().toString(),
                    passwordText.getText().toString());
        } else if (i == R.id.signOutBtn){
            signOut();
        }
    }

}