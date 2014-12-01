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

        //Set the xml view that will be used for this activity
        setContentView(R.layout.activity_circle);

        //Obtain the instance of the red circle View
        mRedCircle = findViewById(R.id.red_circle);

        //add an event listener to the download button
        findViewById(R.id.download).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadImage();
            }
        });
    }

    @Override
    //This event is raised by the android system when a touch on the screen happens.
    //event.getAction has an UP and DOWN
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            //obtain the coordinated of the touch
            int x = (int) event.getX();
            int y = (int) event.getY();

            //ViewPropertAnimator can be used to animate any property on the control
            ViewPropertyAnimator animator = mRedCircle.animate();

            //animate the x coord
            animator.x(x - (mRedCircle.getWidth() / 2))
                    //animate the y coord
                    .y(y - mRedCircle.getWidth() / 2)
                    //apply and easing function
                    .setInterpolator(new AccelerateInterpolator(2f));

            //animate the scale property
            animator.scaleX(1f);

            //run the animation
            animator.start();
        }
        else if (event.getAction() == MotionEvent.ACTION_DOWN){
            //ViewPropertAnimator can be used to animate any property on the control
            ViewPropertyAnimator animator = mRedCircle.animate();

            animator
                    //animate the scale position
                    .scaleX(2f)
                    //Apply an easing function
                    .setInterpolator(new AccelerateDecelerateInterpolator()).start();
        }

        return false;
    }

    //Download a new image from the internet
    void loadImage(){
        ImageDownloadTask imageDownloadTask = new ImageDownloadTask();
        imageDownloadTask.execute("http://img3.wikia.nocookie.net/__cb20131201085621/es.gta/images/thumb/2/25/Android-logo.png/1024px-Android-logo.png");
    }

    //Async task is used to run some code in a new thread
    class ImageDownloadTask extends AsyncTask<String, Void, BitmapDrawable> {
        @Override
        //Code to run in the new thread
        protected BitmapDrawable doInBackground(String... strings) {
            String imageUrl = strings[0];
            BitmapDrawable downloadedImage = null;
            try {
                //Download the image from the internet
                InputStream inputStream = new URL(imageUrl).openStream();
                downloadedImage = new BitmapDrawable(getResources(), inputStream);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return downloadedImage;
        }

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        @Override
        //Will execute back on the UI thread
        protected void onPostExecute(BitmapDrawable b) {
            mRedCircle.setBackground(b);
        }
    }
}
