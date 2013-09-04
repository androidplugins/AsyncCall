package com.fetcher.content;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import com.fetcher.content.servercall.ContentFetcher;

public class MyActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    public void getCall(View view){
        makeCall("http://www.google.com/", "GET");
    }
    public void postCall(View view){
        makeCall("http://www.ideaboardz.com/points.json?point[section_id]=" + 2, "POST");
    }
    public void getForUrl(View view){
        EditText editText = (EditText) findViewById(R.id.url);
        String url = String.valueOf(editText.getText());
        makeCall("http://" + url, "GET");
    }
    public void postForUrl(View view){
        EditText editText = (EditText) findViewById(R.id.url);
        String url = String.valueOf(editText.getText());
        makeCall("http://" + url, "POST");
    }

    private void makeCall(String url, String requestType) {
        ProgressDialog dialog = ProgressDialog.show(MyActivity.this, null, "Fetching details from "+url+" using" + requestType, true);
        final Callback<String> callback = requestCallback(dialog);
        ContentFetcher contentFetcher = new ContentFetcher(callback, requestType);
        contentFetcher.execute(url);
        dialog.setCancelable(true);
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                finish();
            }
        });
        dialog.show();
    }

    private Callback<String> requestCallback(final ProgressDialog dialog) {
        return new Callback<String>() {
            @Override
            public void execute(String result) {
                dialog.dismiss();
                AlertDialog.Builder builder =
                        new AlertDialog.Builder(MyActivity.this)
                                .setTitle("got responce as" + result)
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.dismiss();
                                    }
                                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        };
    }

}
