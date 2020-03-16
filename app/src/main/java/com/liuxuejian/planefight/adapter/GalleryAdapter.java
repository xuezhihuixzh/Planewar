 package com.liuxuejian.planefight.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.liuxuejian.planefight.R;
import com.liuxuejian.planefight.entity.Advance_Score;
import com.liuxuejian.planefight.entity.Endless_Score;
import com.liuxuejian.planefight.entity.Hell_Score;

public class GalleryAdapter extends BaseAdapter {
	private String[] num;
	private Context mcontext;
	public String numtemp;
	private List<Endless_Score> eslist;
	private List<Advance_Score> aslist;
	private List<Hell_Score> hslist;
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return num.length;
	}

	public GalleryAdapter(Context mcontext, String[] num,List<Endless_Score> eslist,List<Advance_Score> aslist,List<Hell_Score> hslist) {
		super();
		this.num = num;
		this.mcontext = mcontext;
		this.eslist = eslist;
		this.aslist =aslist;
		this.hslist =hslist;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return num[position];
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		convertView = View.inflate(mcontext, R.layout.galleryview, null);
		ListView gallerylv = (ListView) convertView
				.findViewById(R.id.gallerylv);
		TextView modetv = (TextView) convertView.findViewById(R.id.modetv);
		if (position==0) {
			modetv.setText(num[position]);
			gallerylv.setAdapter(new lvadapter1(eslist, mcontext));
		}else if(position==1){
			modetv.setText(num[position]);
			gallerylv.setAdapter(new lvadapter2(aslist, mcontext));
		}else if(position==2){
			modetv.setText(num[position]);
			gallerylv.setAdapter(new lvadapter3(hslist, mcontext));
		}
		
		return convertView;
	}
}
