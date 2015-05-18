package com.ggexjob.qrcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

	public boolean DEBUG = false;
	ArrayList<String> instructions = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

        scanNow(null);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void scanNow(View view)
	{
            //Timer.getInstance().startTimer();
			Intent intent = new Intent("com.google.zxing.client.android.SCAN");
			intent.putExtra("com.google.zxing.client.android.SCAN.SCAN_MODE", "QR_CODE_MODE");
			startActivityForResult(intent, 0);
	}

	public void onActivityResult(int requestCode, int resultCode, Intent intent)
	{
        //Timer.getInstance().stopTimer();
        //Timer.getInstance().logElapsedTime("Scan and Decode");

		if(requestCode == 0)
		{
			if(resultCode == RESULT_OK)
			{
				String qrCode = intent.getStringExtra("SCAN_RESULT");
				//String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
				// Handle successful scan

                //qrCode = qrCode.replace("-", "\n");
				//instructions = new ArrayList<String>(Arrays.asList(qrCode.split(";")));

				//Intent showResult = new Intent(this, ScreenSlidePagerActivity.class);
				//showResult.putStringArrayListExtra("INSTRUCTIONS", instructions);
				//startActivity(showResult);

                if(isNetworkAvailable()) {
/*
                    DownloadProductTask downloadProductTask = new DownloadProductTask(qrCode);

                    try {

                        Products product = downloadProductTask.execute().get();
*/
                        Intent showResult = new Intent(this, ScreenSlidePagerActivity.class);
/*
                        List<Components> componentList = product.getComponents();
                        List<Instructions> instructionList = product.getInstructions();
                        ArrayList<String> componentStringList = new ArrayList<>();
                        ArrayList<String> instructionStringList = new ArrayList<>();

                        for (Components component : componentList)
                            componentStringList.add(component.getName());

                        for (Instructions instruction : instructionList)
                            instructionStringList.add(instruction.getInstruction());
*/

                        showResult.putExtra("QR_CODE", qrCode);
                   /*     showResult.putExtra("PRODUCT_NAME", product.getName());
                        showResult.putStringArrayListExtra("COMPONENTS", componentStringList);
                        showResult.putStringArrayListExtra("INSTRUCTIONS", instructionStringList);
*/
                        startActivity(showResult);
/*
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }*/
                }
                else
                {
                    Toast.makeText(this, "Please Connect to the Internet", Toast.LENGTH_SHORT).show();
                }
            }
			else if(resultCode == RESULT_CANCELED)
			{
				// Handle cancel
				if(DEBUG) Log.i("xZing", "Cancelled");
			}
		}
	}

    // http://stackoverflow.com/questions/4238921/detect-whether-there-is-an-internet-connection-available-on-android
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
