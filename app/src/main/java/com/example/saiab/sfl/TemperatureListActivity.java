package com.example.saiab.sfl;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TemperatureListActivity extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_temperature_list, container, false);

        String temperatureValuesRequestURL = "https://io.adafruit.com/api/feeds/lmReadings/data?x-aio-key=b3fdabcd0489420a987639d96a2329d5";
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        final ArrayList<String> arrayList = new ArrayList<>();
        final ListView temperaturesList = rootView.findViewById(R.id.temperaturesList);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, temperatureValuesRequestURL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for(int i=0; i<20; i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        arrayList.add(jsonObject.getString("value"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, arrayList);
                temperaturesList.setAdapter(arrayAdapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(jsonArrayRequest);

        return rootView;
    }
}
