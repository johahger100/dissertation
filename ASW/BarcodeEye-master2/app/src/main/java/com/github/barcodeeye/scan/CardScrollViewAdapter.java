package com.github.barcodeeye.scan;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.github.barcodeeye.DownloadProductTask;
import com.github.barcodeeye.scan.api.CardPresenter;
import com.google.android.glass.widget.CardScrollAdapter;

    public class CardScrollViewAdapter extends CardScrollAdapter {

        private final Context mContext;
        private final List<CardPresenter> mCardPresenters;

        public CardScrollViewAdapter(Context context,
                List<CardPresenter> cardPresenter) {
            mContext = context;
            // TODO DownloadProductTask.execute(); pass ProductURL.getUrl as argument
            // call downloadprudocttask with mCardPresenters and this
            // instead of...
            mCardPresenters = cardPresenter;
        }

        public void replaceItem(CardPresenter oldCardPresenter, CardPresenter newCardPresenter) {
        	int pos = mCardPresenters.indexOf(oldCardPresenter);

            if(pos >= 0 && pos < mCardPresenters.size()) {
                try {
                    mCardPresenters.remove(pos);
                } catch (Exception e) {
                    Log.e("Error", e.getMessage());
                    e.printStackTrace();
                }
                mCardPresenters.add(pos, newCardPresenter);
            }
        }

        @Override
        public int getCount() {
            return mCardPresenters.size();
        }

        @Override
        public Object getItem(int position) {
            return mCardPresenters.get(position);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            CardPresenter cardPresenter = mCardPresenters.get(position);
            return cardPresenter.getCardView(mContext, this);
        }

        @Override
        public int getPosition(Object item) {
            return mCardPresenters.indexOf(item);
        }
    }