package id.ac.polinema.intentexercise;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class RegisterActivity extends AppCompatActivity implements Validator.ValidationListener {
    public static final String FULLNAME_KEY ="fullname";
    public static final String EMAIL_KEY ="email";
    public static final String PASSWORD_KEY="password";
    public static final String CONFIRMPASSWORD_KEY ="confirmpassword";
    public static final String HOMEPAGE_KEY="homepage";
    public static final String ABOUTYOU_KEY="aboutyou";

    private static final String TAG = RegisterActivity.class.getCanonicalName();
    private static final int GALLERY_REQUEST_CODE = 1;


    @NotEmpty
    private EditText FullName;
    @NotEmpty
    @com.mobsandgeeks.saripaar.annotation.Email
    private EditText Email;
    @NotEmpty
    @com.mobsandgeeks.saripaar.annotation.Password
    private EditText Password;
    @NotEmpty
    @com.mobsandgeeks.saripaar.annotation.ConfirmPassword
    private EditText ConfirmPassword;
    @NotEmpty
    private EditText HomePage;
    @NotEmpty
    private EditText AboutYou;
    protected Validator validator;
    private ImageView image_profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        validator = new Validator(this);
        validator.setValidationListener(this);

        FullName = findViewById(R.id.text_fullname);
        Email = findViewById(R.id.text_email);
        Password= findViewById(R.id.text_password);
        ConfirmPassword = findViewById(R.id.text_confirm_password);
        HomePage = findViewById(R.id.text_homepage);
        AboutYou = findViewById(R.id.text_about);
        image_profile=findViewById(R.id.image_profile);

    }

    @Override
    public void onValidationSucceeded() {
        String fullname = FullName.getText().toString();
        String email = Email.getText().toString();
        String password = Password.getText().toString();
        String confirmpassword = ConfirmPassword.getText().toString();
        String homepage = HomePage.getText().toString();
        String about = AboutYou.getText().toString();


        image_profile.setDrawingCacheEnabled(true);
        Bitmap b = image_profile.getDrawingCache();

        Intent intent = new Intent(RegisterActivity.this, ProfileActivity.class);

        intent.putExtra(FULLNAME_KEY,fullname);
        intent.putExtra(EMAIL_KEY,email);
        intent.putExtra(PASSWORD_KEY,password);
        intent.putExtra(CONFIRMPASSWORD_KEY,confirmpassword);
        intent.putExtra(HOMEPAGE_KEY, homepage);
        intent.putExtra(ABOUTYOU_KEY,about);
        intent.putExtra("Bitmap", b);

        startActivity(intent);
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);

            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED){
            return;
        }
        if (requestCode == GALLERY_REQUEST_CODE){
            if (data != null) {
                try {
                    Uri imageUri = data.getData();
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                    image_profile.setImageBitmap(bitmap);

                } catch (IOException e) {
                    Toast.makeText(this, "Can't load image", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, e.getMessage());
                }
            }
        }
    }


    public void handleFoto(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, GALLERY_REQUEST_CODE);
    }

    public void handlerok(View view) {

        validator.validate();
    }
}
