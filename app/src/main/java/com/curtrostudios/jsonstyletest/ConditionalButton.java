package com.curtrostudios.jsonstyletest;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by CurTro Studios on 10/4/2016.
 */

public class ConditionalButton extends AppCompatButton {

    public int txtColor;
    public float txtSize;
    public static int choice;
    public static String cChoice;
    public ProgressDialog pDialog;

    public ConditionalButton(Context context, int condID) {
        super(context);
        choice = condID;
        //setCondition(cond);
        new LoadCondition(condID).execute();
    }

    public ConditionalButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        //setCondition(choice);
        //getCondition(choice);
        new LoadCondition(choice).execute();
    }

    private class LoadCondition extends AsyncTask<String, Void, Integer>{
        int value;

        public LoadCondition(int newChoice){
            value = newChoice;
            pDialog = new ProgressDialog(getContext());
        }

        @Override
        protected void onPreExecute() {
            pDialog.show();
            super.onPreExecute();
        }

        @Override
        protected Integer doInBackground(String... strings) {
            return value;
        }

        @Override
        protected void onPostExecute(Integer mInt) {
            getCondition(mInt);
            pDialog.dismiss();
        }
    }

    public void getCondition(final int sid) {
        JsonArrayRequest condRequest = new JsonArrayRequest(AppConfig.URL_COND_REQUEST + sid,new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("ConditionResponse", response.toString());

                        try {
                            for (int i = 0; i < response.length(); i++) {
                                String cSize = response.getJSONObject(i).getString("condition_size");
                                String cWidth = response.getJSONObject(i).getString("condition_width");
                                String cHeight = response.getJSONObject(i).getString("condition_height");
                                String cColor = response.getJSONObject(i).getString("condition_color");

                                txtColor = Integer.valueOf(cColor);
                                txtSize = Float.valueOf(cSize);
                                int w = Integer.valueOf(cWidth);
                                int h = Integer.valueOf(cHeight);

                                textColor(txtColor);
                                textSize(txtSize);
                                setLayoutParams(h,w);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(AppConfig.JSON_REQ, "Error: " + error.getMessage());

            }
        });

        VolleyController.getInstance().addToRequestQueue(condRequest, AppConfig.JSON_REQ);
    }

    public void setCondition(int condition){
        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset());
            JSONArray jsArry = obj.getJSONArray("conditions");

            JSONObject jsCond = jsArry.getJSONObject(condition);
            Log.d("Details-->", jsCond.toString());
            String textSize_value = jsCond.getString("condition_size");
            String textColor_value = jsCond.getString("condition_color");
            String width_value = jsCond.getString("condition_width");
            String height_value = jsCond.getString("condition_height");
            String mText = jsCond.getString("condition_text");

            txtColor = Integer.valueOf(textColor_value);
            txtSize = Float.valueOf(textSize_value);
            int w = Integer.valueOf(width_value);
            int h = Integer.valueOf(height_value);

            textColor(txtColor);
            textSize(txtSize);
            setLayoutParams(h,w);
            this.setText(mText);


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getResources().getAssets().open("style.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public void textColor(int color) {
        this.setTextColor(color);
    }

    public void textSize(float size) {
        this.setTextSize(size);
    }

    public void setLayoutParams(int height, int width){
        this.setLayoutParams(new RelativeLayout.LayoutParams(width, height));
    }

    public static void setConditon(int newChoice){
        choice=newChoice;
    }

}
