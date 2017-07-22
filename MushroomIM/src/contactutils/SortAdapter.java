package contactutils;

import java.util.List;

import com.example.mushroomim.R;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SortAdapter extends BaseAdapter {
	private Context context;
	private List<PersonBean> persons;
	private LayoutInflater inflater;

	public SortAdapter(Context context, List<PersonBean> persons) {
		this.context = context;
		this.persons = persons;
		this.inflater = LayoutInflater.from(context);

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return persons.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return persons.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewholder = null;
		PersonBean person = persons.get(position);
		if (convertView == null) {
			viewholder = new ViewHolder();
			convertView = inflater.inflate(R.layout.list_item, null);
			viewholder.tv_tag = (TextView) convertView
					.findViewById(R.id.tv_lv_item_tag);
			viewholder.tv_name = (TextView) convertView
					.findViewById(R.id.tv_lv_item_name);
			viewholder.iv_lv_item_head =  (ImageView) convertView
					.findViewById(R.id.iv_lv_item_head);
			convertView.setTag(viewholder);
		} else {
			viewholder = (ViewHolder) convertView.getTag();
		}
		// ��ȡ����ĸ��assiiֵ
		int selection = person.getFirstPinYin().charAt(0);
		// ͨ������ĸ��assiiֵ���ж��Ƿ���ʾ��ĸ
		int positionForSelection = getPositionForSelection(selection);
		if (position == positionForSelection) {// ���˵����Ҫ��ʾ��ĸ
			viewholder.tv_tag.setVisibility(View.VISIBLE);
			viewholder.tv_tag.setText(person.getFirstPinYin());
		} else {
			viewholder.tv_tag.setVisibility(View.GONE);

		}
		viewholder.tv_name.setText(person.getName());
		if (person.getPortraitUri()!=null&&!person.getPortraitUri().equals("")) {
			Uri uri  =Uri.parse(person.getPortraitUri());
			viewholder.iv_lv_item_head.setImageURI(uri);
		}
		return convertView;
	}

	public int getPositionForSelection(int selection) {
		for (int i = 0; i < persons.size(); i++) {
			String Fpinyin = persons.get(i).getFirstPinYin();
			char first = Fpinyin.toUpperCase().charAt(0);
			if (first == selection) {
				return i;
			}
		}
		return -1;

	}

	class ViewHolder {
		ImageView iv_lv_item_head;
		TextView tv_tag;
		TextView tv_name;
	}

}
