package com.example.saiab.sfl;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    RequestQueue requestQueue;

    Switch fanSwitch;
    Switch lightSwitch;
    SeekBar fanSpeedBar;
    TextView temperatureValue;
    TextView fanSpeedValue;
    TextView fanStatusLastTime;
    TextView lightStatusLastTime;
    TextView temperatureLastTime;
    CardView temperatureCard;

    SimpleDateFormat simpleDateFormat;
    DateFormat dateFormat;
    Date date;

    public void setFanStatus(JSONObject fanStatusObject) {
        try {
            if(fanStatusObject.getString("last_value").equals("ON")) {
                fanSwitch.setChecked(true);
            }else {
                fanSwitch.setChecked(false);
            }
            date = dateFormat.parse(fanStatusObject.getString("last_value_at"));
            fanStatusLastTime.setText(simpleDateFormat.format(date));
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        fanSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String fanUpdateUrl = "";

                if(isChecked) {
                    fanUpdateUrl = "https://io.adafruit.com/api/groups/Default/send.json?x-aio-key=b3fdabcd0489420a987639d96a2329d5&fanStatus=ON";
                }else {
                    fanUpdateUrl = "https://io.adafruit.com/api/groups/Default/send.json?x-aio-key=b3fdabcd0489420a987639d96a2329d5&fanStatus=OFF";
                }

                JsonObjectRequest fanUpdate = new JsonObjectRequest(Request.Method.GET, fanUpdateUrl, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Fan Error: ", error.toString());
                    }
                });

                requestQueue.add(fanUpdate);
            }
        });
    }

    public void setLightStatus(JSONObject lightStatusObject) {
        try {
            if(lightStatusObject.getString("last_value").equals("ON")) {
                lightSwitch.setChecked(true);
            }else {
                lightSwitch.setChecked(false);
            }
            date = dateFormat.parse(lightStatusObject.getString("last_value_at"));
            lightStatusLastTime.setText(simpleDateFormat.format(date));
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        lightSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String lightUpdateUrl = "";

                if(isChecked) {
                    lightUpdateUrl = "https://io.adafruit.com/api/groups/Default/send.json?x-aio-key=b3fdabcd0489420a987639d96a2329d5&lightStatus=ON";
                }else {
                    lightUpdateUrl = "https://io.adafruit.com/api/groups/Default/send.json?x-aio-key=b3fdabcd0489420a987639d96a2329d5&lightStatus=OFF";
                }

                JsonObjectRequest lightUpdate = new JsonObjectRequest(Request.Method.GET, lightUpdateUrl, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Light Error: ", error.toString());
                    }
                });

                requestQueue.add(lightUpdate);
            }
        });
    }

    public void setFanSpeed(JSONObject fanSpeedObject) {
        try {
            fanSpeedValue.setText(Integer.toString(fanSpeedObject.getInt("last_value")));
            fanSpeedBar.setProgress(fanSpeedObject.getInt("last_value"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        fanSpeedBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                String fanSpeedData = "" + progress;

                fanSpeedValue.setText(fanSpeedData);

                String fanSpeedUpdateUrl = "https://io.adafruit.com/api/groups/Default/send.json?x-aio-key=b3fdabcd0489420a987639d96a2329d5&fanSpeed="+fanSpeedData;

                JsonObjectRequest fanSpeedUpdate = new JsonObjectRequest(Request.Method.GET, fanSpeedUpdateUrl, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Fan Speed Error: ", error.toString());
                    }
                });

                requestQueue.add(fanSpeedUpdate);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void setTemperatureValue(JSONObject temperatureObject) {
        try {

            String temperature = String.format("%.2f", temperatureObject.getDouble("last_value")) + "  <sup>o</sup>C";

            temperatureValue.setText(Html.fromHtml(temperature));

            date = dateFormat.parse(temperatureObject.getString("last_value_at"));
            temperatureLastTime.setText(simpleDateFormat.format(date));
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fanSwitch = findViewById(R.id.fanSwitch);
        lightSwitch = findViewById(R.id.lightSwitch);
        fanSpeedBar = findViewById(R.id.fanSpeedBar);
        temperatureValue = findViewById(R.id.temperatureValue);
        fanSpeedValue = findViewById(R.id.fanSpeedValue);
        fanStatusLastTime = findViewById(R.id.fanStatusLastTime);
        lightStatusLastTime = findViewById(R.id.lightStatusLastTime);
        temperatureLastTime = findViewById(R.id.temperatureLastTime);
        temperatureCard = findViewById(R.id.temperatureCard);

        dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX");
        simpleDateFormat = new SimpleDateFormat("d MMM yyyy 'at' hh:mm:ss a");

        final String requestURL = "https://io.adafruit.com/api/groups/Default/receive.json?x-aio-key=b3fdabcd0489420a987639d96a2329d5";
        requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, requestURL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray feeds = response.getJSONArray("feeds");

                    JSONObject fanStatusObject = feeds.getJSONObject(0);
                    setFanStatus(fanStatusObject);

                    JSONObject lightStatusObject = feeds.getJSONObject(1);
                    setLightStatus(lightStatusObject);

                    JSONObject fanSpeedObject = feeds.getJSONObject(2);
                    setFanSpeed(fanSpeedObject);

                    JSONObject temperatureObject = feeds.getJSONObject(3);
                    setTemperatureValue(temperatureObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("JSON OBJECT ERROR", error.toString());
            }
        });
        requestQueue.add(jsonObjectRequest);

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                JsonObjectRequest tempUpdateRequest = new JsonObjectRequest(Request.Method.GET, requestURL, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                    try {
                        JSONArray feeds = response.getJSONArray("feeds");

                        JSONObject tempBlock = feeds.getJSONObject(3);
                        String temperature = String.format("%.2f", tempBlock.getDouble("last_value")) + "  <sup>o</sup>C";

                        temperatureValue.setText(Html.fromHtml(temperature));

                        date = dateFormat.parse(tempBlock.getString("last_value_at"));
                        temperatureLastTime.setText(simpleDateFormat.format(date));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Temperature Error: ", error.toString());
                    }
                });

                requestQueue.add(tempUpdateRequest);

            }
        }, 0, 5000);

        temperatureCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TemperatureActivity.class));
            }
        });
    }
}
