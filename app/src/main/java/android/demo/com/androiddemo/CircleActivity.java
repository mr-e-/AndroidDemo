package android.demo.com.androiddemo;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;

import java.io.InputStream;
import java.net.URL;

/**
 * Created by jkelly on 11/29/2014.
 */
public class CircleActivity extends Activity {

    View mRedCircle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle);

        mRedCircle = findViewById(R.id.red_circle);

        findViewById(R.id.download).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadImage();
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            int x = (int) event.getX();
            int y = (int) event.getY();

            ViewPropertyAnimator animator = mRedCircle.animate();

            if (event.getAction() == MotionEvent.ACTION_UP) {
                animator.x(x - (mRedCircle.getWidth() / 2))
                        .y(y - mRedCircle.getWidth() / 2)
                        .setInterpolator(new AccelerateInterpolator(2f));
            }

            animator.scaleX(1f);

            animator.start();
        }
        else if (event.getAction() == MotionEvent.ACTION_DOWN){
            ViewPropertyAnimator animator = mRedCircle.animate();
            animator.scaleX(2f).setInterpolator(new AccelerateDecelerateInterpolator()).start();
        }

        return false;
    }

    void loadImage(){
        ImageDownloadTask imageDownloadTask = new ImageDownloadTask();
        imageDownloadTask.execute("http://img3.wikia.nocookie.net/__cb20131201085621/es.gta/images/thumb/2/25/Android-logo.png/1024px-Android-logo.png");
    }

    class ImageDownloadTask extends AsyncTask<String, Void, BitmapDrawable> {
        @Override
        protected BitmapDrawable doInBackground(String... strings) {
            String imageUrl = strings[0];
            BitmapDrawable downloadedImage = null;
            try {
                InputStream inputStream = new URL(imageUrl).openStream();
                downloadedImage = new BitmapDrawable(getResources(), inputStream);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return downloadedImage;
        }

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        @Override
        protected void onPostExecute(BitmapDrawable b) {
            mRedCircle.setBackground(b);
        }
    }
}
