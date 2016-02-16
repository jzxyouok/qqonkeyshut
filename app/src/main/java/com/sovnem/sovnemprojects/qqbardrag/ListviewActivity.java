package com.sovnem.sovnemprojects.qqbardrag;

import java.util.ArrayList;
import java.util.Random;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sovnem.qqbardrag.R;
import com.sovnem.sovnemprojects.qqbardrag.SnotView.DragCallback;

public class ListviewActivity extends Activity implements OnScrollListener {
	ArrayList<Message> msgs;
	SnotView holderView;
	private TextView tView;
	private int exHeight;
	private int[] location = new int[2];
	private volatile boolean isIn;
	private int firstVisiable;
	private int visiableCout;
	private ListView listView;
	private MsgAdapter adapter;
	private TextView textView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listview);
		initData();
		initView();
	}

	private void initView() {
		holderView = (SnotView) findViewById(R.id.holderview);
		holderView.bringToFront();
		listView = (ListView) findViewById(R.id.listview);
		textView = (TextView) findViewById(R.id.tCount);
		adapter = new MsgAdapter();
		listView.setAdapter(adapter);

		listView.setOnScrollListener(this);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Toast.makeText(ListviewActivity.this, "点击了" + position, Toast.LENGTH_LONG).show();
			}
		});
	}

	private void initData() {
		msgs = new ArrayList<ListviewActivity.Message>();
		int[] colors = new int[] { Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW };
		Random random = new Random();
		for (int i = 0; i < 20; i++) {
			Message message = new Message();
			message.content = "Wood:what are you 弄啥类？";
			message.msgCout = random.nextInt(20) + 1;
			message.bgClr = colors[i % colors.length];
			msgs.add(message);
		}
	}

	private void getHeights() {
		exHeight = (int) ExtraHeightUtil.getExtraHeight(this);
	}

	private boolean isClearing;

	/*
	 * 要做的事情:
	 * 
	 * 1,遍历得到触摸的view
	 */
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		if (isClearing) {
			return true;
		}
		getHeights();
		float x = ev.getX();
		float y = ev.getY();
		if (ev.getAction() == MotionEvent.ACTION_DOWN) {
			int i = 0;
			if (!isIn) {
				tView = textView;
				tView.getLocationInWindow(location);
				if (x > location[0] && x < location[0] + tView.getWidth() && y > location[1] && y < location[1] + tView.getHeight()) {
					isIn = true;
					tView.setVisibility(View.GONE);
					holderView.setDragCallback(new DragCallback() {

						@Override
						public void onRemoveSnot() {
							isIn = false;
							clearAllMsgNums();
						}

						@Override
						public void onFree() {
							isIn = false;
							tView.setVisibility(View.VISIBLE);
						}
					});
				} else
					while (i < visiableCout) {
						final int position = (i + firstVisiable);
						tView = (TextView) listView.findViewWithTag("" + position);

						tView.getLocationInWindow(location);
						if (x > location[0] && x < location[0] + tView.getWidth() && y > location[1] && y < location[1] + tView.getHeight()) {
							isIn = true;
							tView.setVisibility(View.GONE);
							holderView.setDragCallback(new DragCallback() {

								@Override
								public void onRemoveSnot() {
									msgs.get(position).msgCout = 0;
									isIn = false;
								}

								@Override
								public void onFree() {
									isIn = false;
									tView.setVisibility(View.VISIBLE);
								}
							});
							break;
						}
						i++;
					}
			}

		}
		if (isIn) {
			holderView.handlerTvTouchEvent2(ev, tView, exHeight);
			return true;
		}

		return super.dispatchTouchEvent(ev);
	}

	int j;

	/**
	 * 爆破所有的消息数
	 */
	protected void clearAllMsgNums() {
		isClearing = true;
		j = 0;
		tView = (TextView) listView.findViewWithTag("" + (j + firstVisiable));
		tView.getLocationOnScreen(location);
		float boomX = location[0] + tView.getWidth() / 2;
		float boomY = location[1] + tView.getHeight() / 2 - exHeight;
		holderView.setDragCallback(new DragCallback() {

			@Override
			public void onRemoveSnot() {
				Log.i("info", "爆破：" + j);
				j++;
				if (j >= visiableCout) {
					for (int i = 0; i < msgs.size(); i++) {
						Message m = msgs.get(i);
						m.msgCout = 0;
					}
					isClearing = false;
					adapter.notifyDataSetChanged();
				}
				tView = (TextView) listView.findViewWithTag("" + (j + firstVisiable));
				if (tView == null) {
					Log.i("info", "找不到:" + j);
					isClearing = false;
					return;
				}
				tView.getLocationOnScreen(location);
				float boomX = location[0] + tView.getWidth() / 2;
				float boomY = location[1] + tView.getHeight() / 2 - exHeight;
				holderView.boomAt(boomX, boomY, tView.getVisibility());
				tView.setVisibility(View.GONE);

			}

			@Override
			public void onFree() {

			}
		});
		holderView.boomAt(boomX, boomY, tView.getVisibility());
		tView.setVisibility(View.GONE);

	}

	class Message {
		String content;
		int msgCout;
		int bgClr;
	}

	class MsgAdapter extends BaseAdapter {
		public MsgAdapter() {
		}

		@Override
		public int getCount() {
			return msgs.size();
		}

		@Override
		public Object getItem(int position) {
			return msgs.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@SuppressLint("ViewHolder")
		@Override
		public View getView(int position, View c, ViewGroup parent) {
			TextView tView1, tView2;
			c = View.inflate(ListviewActivity.this, R.layout.qqitem, null);
			tView1 = (TextView) c.findViewById(R.id.textView1);
			tView2 = (TextView) c.findViewById(R.id.textView2);

			Message msg = msgs.get(position);
			tView1.setText(msg.content);
			tView2.setTag("" + position);

			if (0 != msg.msgCout) {
				tView2.setVisibility(View.VISIBLE);

				tView2.setText(msg.msgCout > 100 ? ("99+") : msg.msgCout + "");
			} else {
				tView2.setVisibility(View.GONE);
			}
			return c;
		}

	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {

	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		firstVisiable = firstVisibleItem;
		visiableCout = visibleItemCount;

	}

}
