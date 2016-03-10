package com.russell.calmmusic.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.russell.calmmusic.R;
import com.russell.calmmusic.activities.ScreenSlidePagerActivity;
import com.russell.calmmusic.tools.Constants;
import com.russell.calmmusic.tools.GetFromWXActivity;
import com.russell.calmmusic.tools.ShowFromWXActivity;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.ShowMessageFromWX;
import com.tencent.mm.sdk.modelmsg.WXAppExtendObject;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class WXEntryActivity extends AppCompatActivity implements IWXAPIEventHandler {

    private static final String APP_ID = "wx1b42c40fb2baf654";  /**微信app_id*/
    private IWXAPI api;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wxentry);
        regToWx();  /**注册微信api*/
    }

    private void regToWx() {
        api = WXAPIFactory.createWXAPI(this, APP_ID, false);
        api.registerApp(APP_ID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        WXWebpageObject musicObject = new WXWebpageObject();
        musicObject.webpageUrl = "http://douban.fm/";
        WXMediaMessage mediaMessage = new WXMediaMessage();
        mediaMessage.mediaObject = musicObject;
        mediaMessage.title = "豆瓣";
        mediaMessage.description = "豆瓣";
//        Bitmap thumb = BitmapFactory.decodeResource(getResources(),R.drawable.send_music_thumb);
//        mediaMessage.thumbData = Util.bmpToByteArray(thumb, true);
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("web");
        req.message = mediaMessage;
        req.scene = SendMessageToWX.Req.WXSceneTimeline;
//        WXTextObject wxTextObject = new WXTextObject();
//        wxTextObject.text = "aaaaa";
//        WXMediaMessage wxMediaMessage = new WXMediaMessage();
//        wxMediaMessage.mediaObject = wxTextObject;
//        wxMediaMessage.description = "aaaaa";
//        SendMessageToWX.Req req = new SendMessageToWX.Req();
//        req.transaction = String.valueOf(System.currentTimeMillis());
//        req.message = wxMediaMessage;
        api.sendReq(req);
        finish();
    }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq baseReq) {
        /**
         * 请求的回调
         */
        switch (baseReq.getType()) {
            case ConstantsAPI.COMMAND_GETMESSAGE_FROM_WX:
                goToGetMsg();
                Toast.makeText(this, "COMMAND_GETMESSAGE_FROM_WX---1", Toast.LENGTH_SHORT).show();
                break;
            case ConstantsAPI.COMMAND_SHOWMESSAGE_FROM_WX:
                goToShowMsg((ShowMessageFromWX.Req) baseReq);
                Toast.makeText(this, "COMMAND_SHOWMESSAGE_FROM_WX---2", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    @Override
    public void onResp(BaseResp baseResp) {
        /**
         * 响应的回调
         */
        int result = 0;
        switch (baseResp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                result = R.string.errcode_success;
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                result = R.string.errcode_cancel;
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                result = R.string.errcode_deny;
                break;
            default:
                result = R.string.errcode_unknown;
                break;
        }
        Toast.makeText(this, result, Toast.LENGTH_LONG).show();
        startActivity(new Intent(this, ScreenSlidePagerActivity.class));
        finish();
    }

    private void goToGetMsg() {
        Intent intent = new Intent(this, GetFromWXActivity.class);
        intent.putExtras(getIntent());
        startActivity(intent);
        finish();
    }

    private void goToShowMsg(ShowMessageFromWX.Req showReq) {
        WXMediaMessage wxMsg = showReq.message;
        WXAppExtendObject obj = (WXAppExtendObject) wxMsg.mediaObject;

        StringBuffer msg = new StringBuffer(); // 组织一个待显示的消息内容
        msg.append("description: ");
        msg.append(wxMsg.description);
        msg.append("\n");
        msg.append("extInfo: ");
        msg.append(obj.extInfo);
        msg.append("\n");
        msg.append("filePath: ");
        msg.append(obj.filePath);

        Intent intent = new Intent(this, ShowFromWXActivity.class);
        intent.putExtra(Constants.ShowMsgActivity.STitle, wxMsg.title);
        intent.putExtra(Constants.ShowMsgActivity.SMessage, msg.toString());
        intent.putExtra(Constants.ShowMsgActivity.BAThumbData, wxMsg.thumbData);
        startActivity(intent);
        finish();
    }
}
