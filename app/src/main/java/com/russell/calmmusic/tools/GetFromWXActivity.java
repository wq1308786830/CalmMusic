package com.russell.calmmusic.tools;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.russell.calmmusic.R;
import com.tencent.mm.sdk.modelmsg.GetMessageFromWX;
import com.tencent.mm.sdk.modelmsg.WXImageObject;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXMusicObject;
import com.tencent.mm.sdk.modelmsg.WXTextObject;
import com.tencent.mm.sdk.modelmsg.WXVideoObject;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class GetFromWXActivity extends Activity {

	private static final int THUMB_SIZE = 150;

	private IWXAPI api;
	private Bundle bundle;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// acquire wxapi
		api = WXAPIFactory.createWXAPI(this, Constants.APP_ID);
		bundle = getIntent().getExtras();

		setContentView(R.layout.get_from_wx);
		initView();
	}

	@Override
	public void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		bundle = intent.getExtras();
	}

	private void initView() {

		findViewById(R.id.get_text).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				final EditText editor = new EditText(GetFromWXActivity.this);
				editor.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
				editor.setText(R.string.share_text_default);
				MMAlert.showAlert(GetFromWXActivity.this, "share text", editor, getString(R.string.app_share), getString(R.string.app_cancel), new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						String text = editor.getText().toString();
						if (text == null || text.length() == 0) {
							return;
						}
						
						WXTextObject textObj = new WXTextObject();
						textObj.text = text;

						WXMediaMessage msg = new WXMediaMessage(textObj);
						msg.description = text;
						
						GetMessageFromWX.Resp resp = new GetMessageFromWX.Resp();
						resp.transaction = getTransaction();
						resp.message = msg;
						
						api.sendResp(resp);
						finish();
					}
				}, null);
			}
		});

		findViewById(R.id.get_img).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// respond with image message
				Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.send_img);
				WXImageObject imgObj = new WXImageObject(bmp);

				WXMediaMessage msg = new WXMediaMessage();
				msg.mediaObject = imgObj;

				Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, THUMB_SIZE, THUMB_SIZE, true);
				bmp.recycle();
				msg.thumbData = Util.bmpToByteArray(thumbBmp, true);

				GetMessageFromWX.Resp resp = new GetMessageFromWX.Resp();
				resp.transaction = getTransaction();
				resp.message = msg;
				
				api.sendResp(resp);
				finish();
			}
		});

		findViewById(R.id.get_music).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				WXMusicObject music = new WXMusicObject();
				music.musicUrl = "http://www.baidu.com";

				WXMediaMessage msg = new WXMediaMessage();
				msg.mediaObject = music;
				msg.title = "Music Title";
				msg.description = "Music Album";

				Bitmap thumb = BitmapFactory.decodeResource(getResources(), R.drawable.send_music_thumb);
				msg.thumbData = Util.bmpToByteArray(thumb, true);

				GetMessageFromWX.Resp resp = new GetMessageFromWX.Resp();
				resp.transaction = getTransaction();
				resp.message = msg;
				
				api.sendResp(resp);
				finish();
			}
		});

		findViewById(R.id.get_video).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				WXVideoObject video = new WXVideoObject();
				video.videoUrl = "http://www.baidu.com";

				WXMediaMessage msg = new WXMediaMessage(video);
				msg.title = "Video Title";
				msg.description = "Video Description";

				GetMessageFromWX.Resp resp = new GetMessageFromWX.Resp();
				resp.transaction = getTransaction();
				resp.message = msg;

				api.sendResp(resp);
				finish();
			}
		});

		findViewById(R.id.get_webpage).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				WXWebpageObject webpage = new WXWebpageObject();
				webpage.webpageUrl = "http://www.baidu.com";

				WXMediaMessage msg = new WXMediaMessage(webpage);
				msg.title = "WebPage Title";
				msg.description = "WebPage Description";

				GetMessageFromWX.Resp resp = new GetMessageFromWX.Resp();
				resp.transaction = getTransaction();
				resp.message = msg;
				
				api.sendResp(resp);
				finish();
			}
		});
	}

//	@Override
//	protected int getLayoutId() {
//		return R.layout.get_from_wx;
//	}
	private String getTransaction() {
		final GetMessageFromWX.Req req = new GetMessageFromWX.Req(bundle);
		return req.transaction;
	}
}
