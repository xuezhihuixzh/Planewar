package com.liuxuejian.planefight.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.liuxuejian.planefight.R;
import com.liuxuejian.planefight.entity.Endless_Score;

public class lvadapter1 extends BaseAdapter {
	private List<Endless_Score> eslist;
	private Context context;

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return eslist.size();
	}

	public lvadapter1(List<Endless_Score> eslist, Context context) {
		super();
		this.eslist = eslist;
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
		nametv.setText(eslist.get(position).getPlayer_name().toString());
		scoretv.setText(eslist.get(position).getScore()+"");
		return convertView;
	}
}