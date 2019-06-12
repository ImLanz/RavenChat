package com.ravenchat;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.quickblox.auth.session.QBSettings;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.users.QBUsers;
import com.quickblox.users.model.QBUser;
import com.rengwuxian.materialedittext.MaterialEditText;

public class MainActivity extends AppCompatActivity {

    static final String APP_ID = "74855";
    static final String AUTH_KEY = "3QbahqQXx2sZGf-";
    static final String AUTH_SECRET = "xKJNzLErtZHgaX6";
    static final String ACCOUNT_KEY = "xBzPyQZ1Q6ysCfgk7xdM";

    Button btnLogin, btnSignup;
    EditText edtUsername, edtPassword;
    TextView txtSlogan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeFreamwork();

        txtSlogan = (TextView)findViewById( R.id.txtSlogan );

        Typeface face = Typeface.createFromAsset( getAssets(), "Fonts/beyond_the_mountains.ttf" );
        txtSlogan.setTypeface( face );

        btnLogin = (Button)findViewById(R.id.main_btnSignIn);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = edtUsername.getText().toString();
                final String password = edtPassword.getText().toString();

                QBUser qbUser = new QBUser(username, password);

                QBUsers.signIn(qbUser).performAsync(new QBEntityCallback<QBUser>() {
                    @Override
                    public void onSuccess(QBUser qbUser, Bundle bundle) {
                        Toast.makeText(getBaseContext(), "Sesion iniciada con exito!", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(MainActivity.this, ChatDialogsActivity.class);
                        intent.putExtra("username", username);
                        intent.putExtra("password", password);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onError(QBResponseException e) {
                        Toast.makeText(getBaseContext(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        btnSignup = (Button)findViewById(R.id.main_btnSignUp);
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SignUpActivity.class));
            }
        });

        edtUsername = (MaterialEditText)findViewById(R.id.main_edtUsername);
        edtPassword = (MaterialEditText)findViewById(R.id.main_edtPassword);
    }

    public void initializeFreamwork() {
        QBSettings.getInstance().init(getApplicationContext(), APP_ID, AUTH_KEY, AUTH_SECRET);
        QBSettings.getInstance().setAccountKey(ACCOUNT_KEY);
    }
}
