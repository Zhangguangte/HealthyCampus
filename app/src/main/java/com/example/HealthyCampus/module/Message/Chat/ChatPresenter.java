package com.example.HealthyCampus.module.Message.Chat;


import android.util.Log;
import android.widget.ImageView;

import com.example.HealthyCampus.common.data.Bean.ChatItemBean;
import com.example.HealthyCampus.common.data.form.ChatForm;
import com.example.HealthyCampus.common.data.form.RequestForm;
import com.example.HealthyCampus.common.data.source.callback.MessageDataSource;
import com.example.HealthyCampus.common.data.source.repository.MessageRepository;
import com.example.HealthyCampus.common.helper.SPHelper;
import com.example.HealthyCampus.common.network.vo.MessageListVo;
import com.example.HealthyCampus.common.network.vo.UserVo;
import com.example.HealthyCampus.common.utils.DateUtils;
import com.example.HealthyCampus.common.utils.PictureUtil;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.HealthyCampus.common.network.NetworkManager.createLogInterceptor;

public class ChatPresenter extends ChatContract.Presenter {

    @Override
    public void onStart() {

    }

    @Override
    protected void allChatByRoomId(RequestForm requestForm) {
        MessageRepository.getInstance().allChatByRoomId(requestForm, new MessageDataSource.MessageAllChat() {
            @Override
            public void onDataNotAvailable(Throwable throwable) {
                getView().showError(throwable);
            }

            @Override
            public void onDataAvailable(List<MessageListVo> messageListVos) {
                List<ChatItemBean> chatItemBeans = changeItemBean(messageListVos);
                getView().showRecyclerView(chatItemBeans);
            }
        });
    }

    @Override
    protected void allChatByUid(RequestForm requestForm) {
        MessageRepository.getInstance().allChatByUid(requestForm, new MessageDataSource.MessageAllChat() {
            @Override
            public void onDataNotAvailable(Throwable throwable) {
                getView().showError(throwable);
            }

            @Override
            public void onDataAvailable(List<MessageListVo> messageListVos) {
                List<ChatItemBean> chatItemBeans = changeItemBean(messageListVos);
                try {
                    getView().showRecyclerView(chatItemBeans);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void searchRoomid(RequestForm requestForm) {
        MessageRepository.getInstance().searchRoomid(requestForm, new MessageDataSource.MessageSearchRoomid() {
            @Override
            public void onDataNotAvailable(Throwable throwable) {
                getView().finish();
            }

            @Override
            public void onDataAvailable(MessageListVo messageListVo) {
                try {
                    getView().setRoomId(messageListVo.getRoom_id());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                allChatByUid(requestForm);
            }
        });
    }


    @Override
    protected List<ChatItemBean> changeItemBean(List<MessageListVo> messageListVos) {
        Collections.reverse(messageListVos);
        List<ChatItemBean> chatItemBeans = new ArrayList<ChatItemBean>();
        ChatItemBean chatItemBean = null;
        String userid = SPHelper.getString(SPHelper.USER_ID);
        for (MessageListVo messageListVo : messageListVos) {
            chatItemBean = new ChatItemBean(messageListVo, userid);
            chatItemBeans.add(chatItemBean);
        }
        return chatItemBeans;
    }

    @Override
    protected void insertText(String content, String roomId) {
        ChatForm chatForm = encapsulation(content, roomId, "WORDS");
        saveContent(chatForm);
    }

    @Override
    protected void insertRecord(String content, String roomId, String filePath) {
        ChatForm chatForm = encapsulation(content, roomId, "VOICE");
        chatForm.setFile_path(filePath);
        saveContent(chatForm);
    }

    @Override
    protected void insertPicture(String content, String roomId, String filePath) {
        ChatForm chatForm = encapsulation(content, roomId, "PICTURE");
        chatForm.setFile_path(filePath);
        saveContent(chatForm);
    }

    @Override
    protected void insertMap(String content, String roomId, String filePath) {
        ChatForm chatForm = encapsulation(content, roomId, "MAP");
        chatForm.setFile_path(filePath);
        saveContent(chatForm);
    }

    @Override
    protected void insertCard(String account, String roomId) {
        ChatForm chatForm = encapsulation("", roomId, "CARD");
        chatForm.setUser_id(account);       //用户账号
        saveCard(chatForm);
    }

    @Override
    protected void upPicture(String path, String name, String account) {
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(createLogInterceptor()).build(); //请求日志拦截;
        MultipartBody.Builder mul = new MultipartBody.Builder();
        RequestBody body;
        String bitmapStr = PictureUtil.imageToBase64(path.replace("file://", ""));
        mul.addFormDataPart(name, bitmapStr);
        body = mul.build();
        Request request = new Request.Builder().url("http://192.168.0.105:8095/GETE/UpImage").post(body)
                .addHeader("account", SPHelper.getString(SPHelper.USER_ID)).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("ChatPresenter123456", "SavePicture:不可以可以3");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.v("ChatPresenter123456", "SavePicture:可以可以3");
            }
        });
    }

    @Override
    protected void loadPicture(String belongId, String filename, ImageView sivPicture) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("belongId", belongId);
        map.put("filename", filename);
        Gson gson = new Gson();
        String json = gson.toJson(map);
        RequestBody body = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded; charset=utf-8"), json);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(createLogInterceptor()).build(); //请求日志拦截;
        Request request = new Request.Builder().url("http://192.168.0.105:8095/GETE/LoadImage").post(body)
                .addHeader("account", SPHelper.getString(SPHelper.USER_ID)).build();
        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                try {
                    getView().loadPictureFail(sivPicture);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    try {
                        String result = new String(response.body().bytes());
                        getView().loadPictureSuccess(sivPicture, result);
                        PictureUtil.saveBmpToSd(PictureUtil.stringToBitmap(result), filename, 100, true);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }


//    @Override
//    protected void upPicture(Drawable drawable) {
//        BitmapDrawable bitmap = (BitmapDrawable) drawable;  //获取图片
//        String bitmapStr = PictureUtil.bitmapToString(getView().getContext(), bitmap);  //将图片转化成字符串
//        MessageRepository.getInstance().upPicture(bitmapStr, new MessageDataSource.MessageUpPicture() {
//            @Override
//            public void onDataNotAvailable(Throwable throwable) {
//                getView().showError(throwable);
//            }
//
//            @Override
//            public void onDataAvailable() {
//            }
//        });
//    }

    private ChatForm encapsulation(String content, String roomId, String type) {
        ChatForm chatForm = new ChatForm();
        chatForm.setCreate_time(DateUtils.getStringDate());
        chatForm.setRoom_id(roomId);
        chatForm.setType(type);
        chatForm.setContent(content);
        return chatForm;
    }

    private void saveContent(ChatForm chatForm) {
        MessageRepository.getInstance().insertContent(chatForm, new MessageDataSource.MessageAddContent() {
            @Override
            public void onDataNotAvailable(Throwable throwable) {
                getView().showError(throwable);
            }

            @Override
            public void onDataAvailable() {
            }
        });
    }

    private void saveCard(ChatForm chatForm) {
        MessageRepository.getInstance().insertCard(chatForm, new MessageDataSource.MessageAddCard() {
            @Override
            public void onDataNotAvailable(Throwable throwable) {
                getView().showError(throwable);
            }

            @Override
            public void onDataAvailable(UserVo userVo) {
                getView().addCardItem(userVo);
            }
        });
    }
}
