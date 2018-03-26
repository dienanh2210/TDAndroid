package vn.javis.tourde.Login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import vn.javis.tourde.R;
import vn.javis.tourde.helpers.InputValidation;
import vn.javis.tourde.model.User;
import vn.javis.tourde.sql.DatabaseHelper;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private final AppCompatActivity Activity = RegisterActivity.this;
    private NestedScrollView nestedScrollView;
    private TextInputLayout mtextInputLayoutName;
    private TextInputLayout mtextInputLayoutEmail;
    private TextInputLayout mtextInputLayoutPassword;
    private TextInputLayout mtextInputLayoutConfirmPassword;
    private TextInputEditText mtextInputEditTextName;
    private TextInputEditText mtextInputEditTextEmail;
    private TextInputEditText mtextInputEditTextPassword;
    private TextInputEditText mtextInputEditTextConfirmPassword;
    private AppCompatButton mappCompatButtonRegister;
    private AppCompatTextView mappCompatTextViewLoginLink;
    private InputValidation minputValidation;
    private DatabaseHelper mdatabaseHelper;
    private User user;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_register);
        getSupportActionBar().hide();
        initViews();
        initListeners();
        initObjects();
    }
    private void initViews() {
        nestedScrollView = (NestedScrollView) findViewById( R.id.nestedScrollView);
       mtextInputLayoutName = (TextInputLayout) findViewById( R.id.textInputLayoutName);
        mtextInputLayoutEmail = (TextInputLayout) findViewById( R.id.textInputLayoutEmail);
        mtextInputLayoutPassword = (TextInputLayout) findViewById( R.id.textInputLayoutPassword);
        mtextInputLayoutConfirmPassword = (TextInputLayout) findViewById( R.id.textInputLayoutConfirmPassword);
        mtextInputEditTextName = (TextInputEditText) findViewById( R.id.textInputEditTextName);
        mtextInputEditTextEmail = (TextInputEditText) findViewById( R.id.textInputEditTextEmail);
        mtextInputEditTextPassword = (TextInputEditText) findViewById( R.id.textInputEditTextPassword);
        mtextInputEditTextConfirmPassword = (TextInputEditText) findViewById( R.id.textInputEditTextConfirmPassword);
        mappCompatButtonRegister = (AppCompatButton) findViewById(R.id.appCompatButtonRegister);
        mappCompatTextViewLoginLink = (AppCompatTextView) findViewById( R.id.appCompatTextViewLoginLink);
    }
    private void initListeners() {
        mappCompatButtonRegister.setOnClickListener(this);
        mappCompatTextViewLoginLink.setOnClickListener(this);
    }
    private void initObjects() {
        minputValidation = new InputValidation(Activity);
        mdatabaseHelper = new DatabaseHelper(Activity);
        user = new User();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.appCompatButtonRegister:
                postDataToSQLite();
                break;
            case R.id.appCompatTextViewLoginLink:
                finish();
                break;
        }
    }
    private void postDataToSQLite() {
        if (!minputValidation.isInputEditTextFilled(mtextInputEditTextName, mtextInputLayoutName, getString( R.string.error_message_name))) {
            return;
        }
        if (!minputValidation.isInputEditTextFilled(mtextInputEditTextEmail, mtextInputLayoutEmail, getString( R.string.error_message_email))) {
            return;
        }
        if (!minputValidation.isInputEditTextEmail(mtextInputEditTextEmail, mtextInputLayoutEmail, getString( R.string.error_message_email))) {
            return;
        }
        if (!minputValidation.isInputEditTextFilled(mtextInputEditTextPassword, mtextInputLayoutPassword, getString( R.string.error_message_password))) {
            return;
        }
        if (!minputValidation.isInputEditTextMatches(mtextInputEditTextPassword, mtextInputEditTextConfirmPassword,
                mtextInputLayoutConfirmPassword, getString( R.string.error_password_match))) {
            return;
        }
        if (!mdatabaseHelper.checkUser(mtextInputEditTextEmail.getText().toString().trim())) {
            user.setName(mtextInputEditTextName.getText().toString().trim());
            user.setEmail(mtextInputEditTextEmail.getText().toString().trim());
            user.setPassword(mtextInputEditTextPassword.getText().toString().trim());
            mdatabaseHelper.addUser(user);
            // Snack Bar to show success message that record saved successfully
            Snackbar.make(nestedScrollView, getString( R.string.success_message), Snackbar.LENGTH_LONG).show();
            emptyInputEditText();
        } else {
            // Snack Bar to show error message that record already exists
            Snackbar.make(nestedScrollView, getString( R.string.error_email_exists), Snackbar.LENGTH_LONG).show();
        }
    }
    private void emptyInputEditText() {
        mtextInputEditTextName.setText(null);
        mtextInputEditTextEmail.setText(null);
        mtextInputEditTextPassword.setText(null);
        mtextInputEditTextConfirmPassword.setText(null);
    }
}
