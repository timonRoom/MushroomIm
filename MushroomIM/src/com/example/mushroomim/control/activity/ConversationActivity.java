package com.example.mushroomim.control.activity;

import java.util.Collection;

import org.xutils.DbManager;

import com.example.mushroomim.R;
import com.example.mushroomim.Utils.DbManger;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.TypingMessage.TypingStatus;
import io.rong.imlib.model.Conversation.ConversationType;

public class ConversationActivity  extends FragmentActivity{
	private ImageView img_back;
	private TextView tv_usernikename;
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.conversation);
		img_back = (ImageView) findViewById(R.id.img_back);
		tv_usernikename = (TextView) findViewById(R.id.tv_usernikename);
		inittitle();
		img_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		RongIMClient.setTypingStatusListener(new RongIMClient.TypingStatusListener() {
//		    @Override
//		    public void onTypingStatusChanged(Conversation.ConversationType type, String targetId, Collection<TypingStatus> typingStatusSet) {
//		        //������״̬�ĻỰ���ͺ�targetID�뵱ǰ�Ựһ��ʱ������Ҫ��ʾ
//		        if (type.equals(mConversationType) && targetId.equals(mTargetId)) {
//		            //count��ʾ��ǰ�Ự������������û�������Ŀǰֻ֧�ֵ��ģ������жϴ���0�Ϳ��Ը�����ʾ��
//		            int count = typingStatusSet.size();
//		            if (count > 0) {
//		                Iterator iterator = typingStatusSet.iterator();
//		                TypingStatus status = (TypingStatus) iterator.next();
//		                String objectName = status.getTypingContentType();
//
//		                MessageTag textTag = TextMessage.class.getAnnotation(MessageTag.class);
//		                MessageTag voiceTag = VoiceMessage.class.getAnnotation(MessageTag.class);
//		                //ƥ��Է�������������ı���Ϣ����������Ϣ
//		                if (objectName.equals(textTag.value())) {
//		                    //��ʾ���Է��������롱
//		                    mHandler.sendEmptyMessage(SET_TEXT_TYPING_TITLE);
//		                } else if (objectName.equals(voiceTag.value())) {
//		                    //��ʾ"�Է����ڽ���"
//		                    mHandler.sendEmptyMessage(SET_VOICE_TYPING_TITLE);
//		                }
//		            } else {
//		                //��ǰ�Ựû���û��������룬����������ʾԭ������
//		                mHandler.sendEmptyMessage(SET_TARGETID_TITLE);
//		            }
//		        }
//		    }

			@Override
			public void onTypingStatusChanged(ConversationType type, String targetId, Collection<TypingStatus> typingStatusSet) {
				int count = typingStatusSet.size();
				if (count>0) {
					tv_usernikename.setText("(�Է���������)");
					Log.e("onTypingStatusChanged", lasttitle+" (�Է���������)");
				}else {
					tv_usernikename.setText(lasttitle);
				}
			}
		});
	}
	String lasttitle = "";
	private void inittitle() {
		String targetId;
		String title;
//		if (getIntent().getBooleanExtra("isnotification", false)) {
//			targetId = getIntent().getStringExtra("id");
//			title = getIntent().getStringExtra("title");
//		}else {
			 targetId = getIntent().getData().getQueryParameter("targetId");
			 title = getIntent().getData().getQueryParameter("title");
//		}
//		String nikename = getIntent().getData().getQueryParameter("title");
		
		if (targetId!=null) {
			if (title==null||title.equals("")) {
				
				String nikename = new DbManger(getApplicationContext()).querynikename(targetId);
				if (nikename==null||nikename.equals("")) {
					lasttitle = targetId;
				}else {
					lasttitle = nikename;
				}
			}else {
				lasttitle = title;
			}
		}
		tv_usernikename.setText(lasttitle);
	}
}
