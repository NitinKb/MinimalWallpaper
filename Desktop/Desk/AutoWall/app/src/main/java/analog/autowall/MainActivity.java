package analog.autowall;

import android.app.WallpaperManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.pedromassango.doubleclick.DoubleClick;
import com.pedromassango.doubleclick.DoubleClickListener;
import com.squareup.picasso.Picasso;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    WallpaperManager wallpaperManager;

    TextView textView;

    ImageView imageView;

    String furl = null;

    String num;

    String [] wallurl;

    int n = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        wallpaperManager =  WallpaperManager.getInstance(MainActivity.this);


        textView = findViewById(R.id.txtv);
        imageView = findViewById(R.id.imgv);

        wallurl = new String[]{
                "https://wallpapershome.com/images/pages/pic_h/15026.jpg",
                "https://wallpapershome.com/images/pages/pic_h/18612.jpg",
                "https://wallpapershome.com/images/pages/pic_h/20473.jpg",
                "https://wallpapershome.com/images/pages/ico_h/17179.jpg",
                "https://wallpapershome.com/images/pages/pic_h/18949.jpg",
                "https://wallpapershome.com/images/pages/pic_h/16877.jpg",
                "https://wallpapershome.com/images/pages/pic_h/16351.jpg",
                "https://wallpapershome.com/images/pages/pic_h/6027.jpg",
                "https://wallpapershome.com/images/pages/pic_h/1160.jpg",
                "https://wallpapershome.com/images/pages/pic_h/16879.jpg",
                "https://wallpapershome.com/images/pages/pic_h/17375.jpg",
                "https://wallpapershome.com/images/pages/pic_h/5699.jpg",
                "https://wallpapershome.com/images/pages/pic_h/20322.jpg",
                "https://wallpapershome.com/images/pages/pic_h/19538.jpg",
                "https://wallpapershome.com/images/pages/pic_h/21387.jpeg"
        };


        SharedPreferences pref = getSharedPreferences("wallindex",MODE_PRIVATE);
        n = pref.getInt("index",0);

        Picasso.get()
                .load(wallurl[n])
                .into(imageView);
        imageView.setOnClickListener(new DoubleClick(new DoubleClickListener() {
            @Override
            public void onSingleClick(View view)
            {
                downloadFile(wallurl[n]);
            }

            @Override
            public void onDoubleClick(View view) {

            }
        }));

    }
    void downloadFile(String fileUrl) {

        Glide.with(this)
                .asBitmap()
                .load(Uri.parse(fileUrl))
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {

                        try{
                            WallpaperManager.getInstance(getApplicationContext()).setBitmap(resource);

                            textView.setText("Wallpaper Saved");
                            n = n+1;

                            SharedPreferences.Editor editor = getSharedPreferences("wallindex",MODE_PRIVATE).edit();
                            editor.putInt("index",n).commit();
                            Log.d("indexvalue",Integer.toString(n));


                            finish();
                        }catch (Exception e){}


                    }
                });
    }


}
