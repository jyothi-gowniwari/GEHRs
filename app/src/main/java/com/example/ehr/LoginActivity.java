package com.example.ehr;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ehr.Model.LoginResponse;
import com.example.ehr.remote.APIService;
import com.example.ehr.remote.RetroClass;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends Activity {
    private TextView forgot_password, create_account;
    private TextInputEditText username, etpassword;
    private Button login;
    private String FName,LName;
    private ProgressDialog progressDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        username = findViewById(R.id.editTextUserName);
        etpassword = findViewById(R.id.editTextPassword);
        login = findViewById(R.id.login);
        create_account = findViewById(R.id.create_account);

        username.setHint(Html.fromHtml(username.getHint() + " " + "<font color=\"#ff0000\">" + "* " + "</font>"));
        etpassword.setHint(Html.fromHtml(etpassword.getHint() + " " + "<font color=\"#ff0000\">" + "* " + "</font>"));
        forgot_password = findViewById(R.id.forgotpassword);
        username.setText("9999999999");
        etpassword.setText("1234");
        forgot_password.setPaintFlags(forgot_password.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Forgot_password.class);
                startActivity(intent);
                finish();

            }
        });

        create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegistrationActivity.class);
                startActivity(intent);
                finish();

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                submitdata();


            }
        });
    }

    private void submitdata() {
        String mobile = username.getText().toString().trim();
        String password = etpassword.getText().toString().trim();
        String otp = "N";
        if (mobile.matches("")) {
            username.setError("Please Enter Username");
        } else if (password.matches("")) {
            etpassword.setError("Please Enter Password");
        } else {
            //  username1.setErrorEnabled(false);
            //password.setErrorEnabled(false);


            progressDialog = new ProgressDialog(LoginActivity.this);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Loading...");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setCancelable(false);
            progressDialog.show();
            final APIService service = RetroClass.getRetrofitInstance().create(APIService.class);
            Call<LoginResponse> call = service.GetLogin(mobile, password, otp);
            Log.wtf("URL Called", call.request().url() + "");
            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                    Log.e("response", new Gson().toJson(response.body()));
                    if (response.isSuccessful()) {
                        Log.e("response", new Gson().toJson(response.body()));

                        LoginResponse login = response.body();

                        String success = login.getStatus();

                        FName=login.getFName();
                        LName=login.getLName();

                        if (success.equals("true")) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(),login.getMessage(),Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getApplicationContext(), NavigationViewActivity.class);
                            intent.putExtra("FName",FName);
                            intent.putExtra("LName",LName);
                            startActivity(intent);
                            finish();
                        }
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    Log.d("error", t.getMessage());
                    progressDialog.dismiss();
                    //  llProgressBar.setVisibility(View.GONE);
                }
            });
        }
    }
}
