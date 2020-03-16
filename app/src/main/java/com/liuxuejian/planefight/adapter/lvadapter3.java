package com.liuxuejian.planefight.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.liuxuejian.planefight.R;
import com.liuxuejian.planefight.entity.Hell_Score;

public class lvadapter3 extends BaseAdapter {
	private List<Hell_Score> hslist;
	private Context context;

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return hslist.size();
	}

	public lvadapter3(List<Hell_Score> hslist, Context context) {
		super();
		this.hslist = hslist;
		this.context = context;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		convertView = View.inflate(context, R.layout.list_item, null);
		TextView postiontv = (TextView) convertView.findViewById(R.id.postiontv);
		TextView nametv = (TextView) convertView.findViewById(R.id.nametv);
		TextView scoretv = (TextView) convertView.findViewById(R.id.scoretv);
		postiontv.setText(position+1+"");
		nametv.setText(hslist.get(position).getPlayer_name().toString());
		scoretv.setText(hslist.get(position).getScore()+"");
		return convertView;
	}
}
