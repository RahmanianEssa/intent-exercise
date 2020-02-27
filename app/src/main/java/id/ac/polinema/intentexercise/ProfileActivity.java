package id.ac.polinema.intentexercise;

import androidx.appcompat.app.AppCompatActivity;

import android.app.SearchManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {

    private TextView FullNameText;
    private TextView EmailText;
    private TextView HomePageText;
    private TextView AboutYouText;
    private ImageView image_profile;
    String homepage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        FullNameText=findViewById(R.id.label_fullname);
        EmailText=findViewById(R.id.label_email);
        HomePageText=findViewById(R.id.label_homepage);
        AboutYouText=findViewById(R.id.label_about);
        image_profile=findViewById(R.id.image_profile);

        Bundle extras = getIntent().getExtras();

        if (extras!= null){
            String fullname = extras.getString(RegisterActivity.FULLNAME_KEY);
            FullNameText.setText(fullname);
            String email = extras.getString(RegisterActivity.EMAIL_KEY);
            EmailText.setText(email);
            homepage = extras.getString(RegisterActivity.HOMEPAGE_KEY);
            HomePageText.setText(homepage);
            String aboutyou = extras.getString(RegisterActivity.ABOUTYOU_KEY);
            AboutYouText.setText(aboutyou);

            Bitmap bitmap =(Bitmap) extras.get("Bitmap");
            image_profile.setImageBitmap(bitmap);
        }


    }

    public void handlevisit(View view) {
        Uri webpage = Uri.parse(homepage);
        if (!homepage.startsWith("https://") && !homepage.startsWith("http://")){
            homepage="http://" + homepage;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
    }
