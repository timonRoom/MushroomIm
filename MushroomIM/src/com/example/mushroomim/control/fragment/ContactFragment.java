package com.example.mushroomim.control.fragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.example.mushroomim.R;
import com.example.mushroomim.Utils.DbManger;
import com.example.mushroomim.Utils.SpUtils;
import com.example.mushroomim.control.activity.MainActivity;
import com.example.mushroomim.mode.ContactModel;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import contactutils.PersonBean;
import contactutils.PinyinUtils;
import contactutils.SideBar;
import contactutils.SideBar.OnTouchingLetterChangedListener;
import io.rong.imkit.RongIM;

public class ContactFragment extends Fragment implements OnClickListener {
	@Override
	public void onResume() {
		super.onResume();
		initview();
	}
	private AutoCompleteTextView et_contact_context;
	private ImageView iv_search_contact;
//	private ListView lv_contact;
	private String userId;
	
	
	private ListView listView;
	private contactutils.SortAdapter sortadapter;
	private contactutils.SideBar sidebar;
	private TextView dialog;
	public static  String DATANAME_CONTACT = "contact";
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = View.inflate(getActivity(), R.layout.contactfragment, null);
		et_contact_context = (AutoCompleteTextView) view.findViewById(R.id.et_contact_context);
		iv_search_contact = (ImageView) view.findViewById(R.id.iv_search_contact);
		iv_search_contact.setOnClickListener(this);
		sidebar =(SideBar) view.findViewById(R.id.sidebar);
		listView = (ListView) view.findViewById(R.id.listview);
		dialog = (TextView) view.findViewById(R.id.dialog);

		sidebar.setTextView(dialog);
		// 设置字母导航触摸监听
		sidebar.setOnTouchingLetterChangedListener(new OnTouchingLetterChangedListener() {
			@Override
			public void onTouchingLetterChanged(String s) {
				// TODO Auto-generated method stub
				// 该字母首次出现的位置
				int position = sortadapter.getPositionForSelection(s.charAt(0));

				if (position != -1) {
					listView.setSelection(position);
				}
			}
		});
//		lv_contact = (ListView) view.findViewById(R.id.lv_contact);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				Log.e("onItemClick", arg2+"");
				String id = listarray.get(arg2).getUserId();
				Log.e("onItemClick", id);
				RongIM.getInstance().startPrivateChat(getActivity(), id, listarray.get(arg2).getName());
				Log.e("onItemClick", id);
			}
		});
		userId = MainActivity.userId;
		initview();
		return view;
	}
	private 	String [] searlist;
	private void initview() {
		String[] contacts = new SpUtils().searContactInfo(getActivity(),userId,DATANAME_CONTACT);
		searlist = new String[contacts.length];
		if (contacts==null) {
			Toast.makeText(getActivity(), "无联系人信息", Toast.LENGTH_SHORT).show();
//			Log.e("contacts", contacts.toString());
		}else {
			listarray = getData(contacts);
			Collections.sort(listarray, new contactutils.PinyinComparator());
			// 数据在放在adapter之前需要排序
			setlistview(listarray);
			for (int i = 0; i < listarray.size(); i++) {
				searlist[i]=listarray.get(i).getName();
			}
//			ArrayAdapter<String > adapter =  new ArrayAdapter<String>(getActivity(), R.layout.contact_list_item,
//					R.id.tv_item_contact_name, searlist);
			ArrayAdapter<String > adapter =  new ArrayAdapter<String>(getActivity(), R.layout.contact_list_item, searlist);
			et_contact_context.setAdapter(adapter);
//			getlistdata(contacts);
//			Log.e("cModels", cModels.toString());
//			listadapter ladapter = new listadapter();
//			lv_contact.setAdapter(ladapter);
		}
	}
private void setlistview(List<PersonBean> data) {
	sortadapter = new contactutils.SortAdapter(getContext(), data);
	listView.setAdapter(sortadapter);
	}
	//	private List<ContactModel> cModels;
//	private ContactModel getlistdata(String[] contacts) {
//		cModels = new ArrayList<ContactModel>();
//		ContactModel model = new ContactModel();
//		for (int i = 0; i < contacts.length; i++) {
//			model.setUserId(contacts[i]);
//			String queryportraitUri = new DbManger(getActivity()).queryportraitUri(contacts[i]);
//			String querynikename = new DbManger(getActivity()).querynikename(contacts[i]);
//			model.setNikename(querynikename);
//			model.setPortraitUri(queryportraitUri);
//			cModels.add(model);
//			model = new ContactModel();
//		}
//		return model;
//	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_search_contact:
			int result = -1;
			for (int i = 0; i < searlist.length; i++) {
				if (searlist[i].equals(et_contact_context.getText().toString())) {
					result = i;
				}
			}
			if (result==-1) {
				Toast.makeText(getActivity(), "未找到该好友", Toast.LENGTH_SHORT).show();
			}else {
				List<contactutils.PersonBean> resultlist = new ArrayList<PersonBean>();
				for (int i = 0; i < listarray.size(); i++) {
					if (i==0) {
						resultlist.add(listarray.get(result));
					}
				}
				setlistview(resultlist);
			}
			break;
		}
	}
	
	List<PersonBean> listarray;
	private List<contactutils.PersonBean> getData(String[] data) {
		listarray= new ArrayList<PersonBean>();
		for (int i = 0; i < data.length; i++) {
			String querynikename = new DbManger(getActivity()).querynikename(data[i]);
			String name = data[i];
			String queryportraitUri = new DbManger(getActivity()).queryportraitUri(data[i]);
			if (querynikename!=null&&!querynikename.equals("")) {
				name = querynikename;
			}
			String pinyin = PinyinUtils.getPingYin(name);
			String Fpinyin = pinyin.substring(0, 1).toUpperCase();
			PersonBean person = new PersonBean();
			person.setName(name);
			person.setUserId(data[i]);
			person.setPinYin(pinyin);
			person.setPortraitUri(queryportraitUri);
			// 正则表达式，判断首字母是否是英文字母
			if (Fpinyin.matches("[A-Z]")) {
				person.setFirstPinYin(Fpinyin);
			} else {
				person.setFirstPinYin("#");
			}

			listarray.add(person);
		}
		return listarray;

	}
	/*
	class listadapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return cModels.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			viewHolder vHolder = null;
			View view;
			if (convertView==null) {
				view = View.inflate(getActivity(), R.layout.contact_list_item, null);
				vHolder = new viewHolder();
				vHolder.iv_item_touxian = ((ImageView) view.findViewById(R.id.iv_item_touxian));
				vHolder.tv_item_contact_name  = ((TextView) view.findViewById(R.id.tv_item_contact_name));
				view.setTag(vHolder);
			}else {
				view = convertView;
				view = (View) view.getTag();
			}
			if (!cModels.get(position).getPortraitUri().equals("")) {
				Uri uri = Uri.parse(cModels.get(position).getPortraitUri());
				vHolder.iv_item_touxian.setImageURI(uri);
			}
			if (!cModels.get(position).getNikename().equals("")) {
				Uri uri = Uri.parse(cModels.get(position).getPortraitUri());
				vHolder.tv_item_contact_name.setText(cModels.get(position).getNikename());
			}else {
				vHolder.tv_item_contact_name.setText(cModels.get(position).getUserId());
			}
			return view;
		}
		class viewHolder{
			private ImageView iv_item_touxian;
			private TextView tv_item_contact_name;
		}
		
	}*/
}
