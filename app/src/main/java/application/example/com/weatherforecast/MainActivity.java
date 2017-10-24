package application.example.com.weatherforecast;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private TextView mWeatherTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
         * Using findViewById, we get a reference to our TextView from xml. This allows us to
         * do things like set the text of the TextView.
         */
            mWeatherTextView = (TextView) findViewById(R.id.tv_weather_data);



            LoadWeatherData();
        }

    private void LoadWeatherData(){


        String location= SunshinePreferences.getPreferredWeatherLocation(this);
        new networktask().execute(location);
    }
    public class networktask extends AsyncTask<String,Void,String[]> {

        @Override
        protected String[] doInBackground(String... params) {
  /* If there's no zip code, there's nothing to look up. */
            if (params.length == 0) {
                return null;
            }

            String location = params[0];
            URL weatherRequestUrl = NetworkUtils.buildUrl(location);

            try {
                String jsonWeatherResponse = NetworkUtils
                        .getResponseFromHttpUrl(weatherRequestUrl);

                String[] simpleJsonWeatherData = JsonUtils
                        .getSimpleWeatherStringsFromJson(MainActivity.this, jsonWeatherResponse);

                return simpleJsonWeatherData;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

        }

        // TODO (7) Override the onPostExecute method to display the results of the network request

        @Override
        protected void onPostExecute(String[] weatherdata) {
            if(weatherdata!=null){
                for(String weatherstring : weatherdata){
                    mWeatherTextView.append(weatherstring + "\n\n\n");
                }
            }
        }
    }




}








