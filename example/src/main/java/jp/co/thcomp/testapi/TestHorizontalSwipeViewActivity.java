package jp.co.thcomp.testapi;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import jp.co.thcomp.example.R;
import jp.co.thcomp.view.HorizontalSwipeView;

public class TestHorizontalSwipeViewActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		ListView contentListView = new ListView(this);
		contentListView.setAdapter(new ListItemAdapter());
		setContentView(contentListView);
	}

	private class ListItemAdapter extends BaseAdapter implements HorizontalSwipeView.OnHiddenLayoutStatusChangeListener {
		private ItemData[] itemDataArray = null;

		public ListItemAdapter(){
			itemDataArray = new ItemData[getCount()];
			for(int i=0, size=itemDataArray.length; i<size; i++){
				itemDataArray[i] = new ItemData();
			}
		}

		@Override
		public int getCount() {
			return 100;
		}

		@Override
		public Object getItem(int position) {
			return itemDataArray[position];
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			HorizontalSwipeView rootView = null;
			ItemData itemData = (ItemData)getItem(position);

			if(convertView == null){
				convertView = View.inflate(TestHorizontalSwipeViewActivity.this, R.layout.test_horizontalswipeview_item, null);
				rootView = (HorizontalSwipeView)convertView;
				rootView.setTag(itemData);
				rootView.setOnHiddenLayoutStatusChangeListener(this);
			} else{
				rootView = (HorizontalSwipeView)convertView;
				rootView.setTag(itemData);

				rootView.swipe(itemData.status, false);

				// need to call "reuseLayout"
				rootView.reuseLayout();
			}

			return convertView;
		}

		@Override
		public void onChangeHiddenLayoutStatus(HorizontalSwipeView view, HorizontalSwipeView.HiddenLayoutStatus status) {
			ItemData itemData = (ItemData)view.getTag();
			itemData.status = status;
		}
	};

	private static class ItemData{
		public HorizontalSwipeView.HiddenLayoutStatus status = HorizontalSwipeView.HiddenLayoutStatus.Close;
	}
}
