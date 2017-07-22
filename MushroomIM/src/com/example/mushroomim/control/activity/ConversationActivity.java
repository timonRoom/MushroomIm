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
//		        //当输入状态的会话类型和targetID与当前会话一致时，才需要显示
//		        if (type.equals(mConversationType) && targetId.equals(mTargetId)) {
//		            //count表示当前会话中正在输入的用户数量，目前只支持单聊，所以判断大于0就可以给予显示了
//		            int count = typingStatusSet.size();
//		            if (count > 0) {
//		                Iterator iterator = typingStatusSet.iterator();
//		                TypingStatus status = (TypingStatus) iterator.next();
//		                String objectName = status.getTypingContentType();
//
//		                MessageTag textTag = TextMessage.class.getAnnotation(MessageTag.class);
//		                MessageTag voiceTag = VoiceMessage.class.getAnnotation(MessageTag.class);
//		                //匹配对方正在输入的是文本消息还是语音消息
//		                if (objectName.equals(textTag.value())) {
//		                    //显示“对方正在输入”
//		                    mHandler.sendEmptyMessage(SET_TEXT_TYPING_TITLE);
//		                } else if (objectName.equals(voiceTag.value())) {
//		                    //显示"对方正在讲话"
//		                    mHandler.sendEmptyMessage(SET_VOICE_TYPING_TITLE);
//		                }
//		            } else {
//		                //当前会话没有用户正在输入，标题栏仍显示原来标题
//		                mHandler.sendEmptyMessage(SET_TARGETID_TITLE);
//		            }
//		        }
//		    }

			@Override
			public void onTypingStatusChanged(ConversationType type, String targetId, Collection<TypingStatus> typingStatusSet) {
				int count = typingStatusSet.size();
				if (count>0) {
					tv_usernikename.setText("(对方正在输入)");
					Log.e("onTypingStatusChanged", lasttitle+" (对方正在输入)");
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
