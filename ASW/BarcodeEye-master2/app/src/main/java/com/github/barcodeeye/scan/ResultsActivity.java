package com.github.barcodeeye.scan;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.PendingIntent;
import android.app.PendingIntent.CanceledException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.github.barcodeeye.R;
import com.github.barcodeeye.Timer;
import com.github.barcodeeye.scan.api.CardPresenter;
import com.google.android.glass.view.WindowUtils;
import com.google.android.glass.widget.CardScrollView;

public class ResultsActivity extends Activity {

    private static final String TAG = ResultsActivity.class.getSimpleName();
    private static final String EXTRA_CARDS = "EXTRA_CARDS";
    private static final String COMP_POS = "COMP_POS";
    private static final String INSTR_POS = "INSTR_POS";
    private static final String EXTRA_IMAGE_URI = "EXTRA_IMAGE_URI";

    private int componentPosition = 0;
    private int instructionPosition = 0;

    private final List<CardPresenter> mCardPresenters = new ArrayList<CardPresenter>();
    private CardScrollView mCardScrollView;

    public static Intent newIntent(Context context,
            List<CardPresenter> cardResults, int compPos, int instrPos) {

        Intent intent = new Intent(context, ResultsActivity.class);
        if (cardResults != null) {
            intent.putExtra(EXTRA_CARDS,
                    cardResults.toArray(new CardPresenter[cardResults.size()]));
            intent.putExtra(COMP_POS, compPos);
            intent.putExtra(INSTR_POS, instrPos);
        }

        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        if (savedInstanceState != null) {
            readExtras(intent.getExtras());
        } else if (intent != null && intent.getExtras() != null) {
            readExtras(intent.getExtras());
        } else {
            Log.e(TAG, "No extras were present");
            finish();
            return;
        }

        if (mCardPresenters.size() == 0) {
            Log.w(TAG, "There were no cards to display");
            finish();
            return;
        }

        getWindow().requestFeature(WindowUtils.FEATURE_VOICE_COMMANDS);

        mCardScrollView = new CardScrollView(this);
        mCardScrollView.setAdapter(new CardScrollViewAdapter(this,
                mCardPresenters));
        mCardScrollView.activate();
        mCardScrollView.setOnItemClickListener(mOnItemClickListener);

        setContentView(mCardScrollView);
        Timer.getInstance().stopTimer();
        Timer.getInstance().logElapsedTime("Presentation");
    }

    @Override
    public boolean onCreatePanelMenu(int featureId, Menu menu) {
        if (featureId == WindowUtils.FEATURE_VOICE_COMMANDS) {
            getMenuInflater().inflate(R.menu.main, menu);
            return true;
        }
        // Pass through to super to setup touch menu.
        return super.onCreatePanelMenu(featureId, menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        if (featureId == WindowUtils.FEATURE_VOICE_COMMANDS) {
            switch (item.getItemId()) {
                case R.id.next_menu_item:
                    mCardScrollView.setSelection(mCardScrollView.getSelectedItemPosition()+1);
                    break;
                case R.id.previous_menu_item:
                    mCardScrollView.setSelection(mCardScrollView.getSelectedItemPosition()-1);
                    break;
                case R.id.scan_menu_item:
                    super.onBackPressed();
                    break;
                case R.id.components_menu_item:
                    mCardScrollView.setSelection(componentPosition);
                    break;
                case R.id.instructions_menu_item:
                    mCardScrollView.setSelection(instructionPosition);
                    break;
                default:
                    return true;
            }
            return true;
        }
        // Good practice to pass through to super if not handled
        return super.onMenuItemSelected(featureId, item);
    }

    private void readExtras(Bundle extras) {
        Parcelable[] parcelCardsArray = extras.getParcelableArray(EXTRA_CARDS);
        for (int i = 0; i < parcelCardsArray.length; i++) {
            mCardPresenters.add((CardPresenter) parcelCardsArray[i]);
        }
        componentPosition = extras.getInt(COMP_POS);
        instructionPosition = extras.getInt(INSTR_POS);

    }

    private final OnItemClickListener mOnItemClickListener = new OnItemClickListener()
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
            CardPresenter cardPresenter = mCardPresenters.get(position);
            PendingIntent pendingIntent = cardPresenter.getPendingIntent();
            if (pendingIntent != null)
            {
                try
                {
                    pendingIntent.send();
                }
                catch (CanceledException e)
                {
                    Log.w(TAG, e.getMessage());
                }
            }
            else
            {
                Log.w(TAG, "No PendingIntent attached to card!");
            }
        }
    };
}
