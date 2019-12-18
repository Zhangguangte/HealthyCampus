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

    //根据房间号查询信息，用于-》消息界面
    @Override
    protected void allChatByRoomId(RequestForm requestForm) {
        MessageRepository.getInstance().allChatByRoomId(requestForm, new MessageDataSource.MessageAllChat() {
            @Override
            public void onDataNotAvailable(Throwable throwable) throws Exception {
                getView().showError(throwable);
            }

            @Override
            public void onDataAvailable(List<MessageListVo> messageListVos) throws Exception {
                List<ChatItemBean> chatItemBeans = changeItemBean(messageListVos);
                getView().showRecyclerView(chatItemBeans);
            }
        });
    }

    //根据用户ID查询房间号，再查询消息，用于-》联系人界面
    @Override
    protected void allChatByUid(RequestForm requestForm) {
        MessageRepository.getInstance().allChatByUid(requestForm, new MessageDataSource.MessageAllChat() {
            @Override
            public void onDataNotAvailable(Throwable throwable) throws Exception {
                getView().showError(throwable);
            }

            @Override
            public void onDataAvailable(List<MessageListVo> messageListVos) {
                try {
                    List<ChatItemBean> chatItemBeans = changeItemBean(messageListVos);
                    getView().showRecyclerView(chatItemBeans);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    // 查询房间号,用于-》联系人界面
    @Override
    protected void searchRoomid(RequestForm requestForm) {
        MessageRepository.getInstance().searchRoomid(requestForm, new MessageDataSource.MessageSearchRoomid() {
            @Override
            public void onDataNotAvailable(Throwable throwable) throws Exception {
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

    //封装数据，转化为聊天Item
    @Override
    protected List<ChatItemBean> changeItemBean(List<MessageListVo> messageListVos) {
        String userid = SPHelper.getString(SPHelper.USER_ID);

        Collections.reverse(messageListVos);        //倒叙查询数据
        List<ChatItemBean> chatItemBeans = new ArrayList<ChatItemBean>();
        ChatItemBean chatItemBean = null;
        int itemSize = 8 - messageListVos.size();            /*用于判断是否数据超过屏幕。不合理，因为目前手机为魅族MX6，适合8个文本Item，不同手机大小不一。
                                                                预方案：测量手机高度，计算item总高度，进行比对
                                                                目的：用于优化聊天显示
                                                                测试点：1.数据超过一屏幕，消息显示为底部
                                                                测试点：2.数据不一屏幕，消息显示位于顶部
                                                             */
        for (MessageListVo messageListVo : messageListVos) {
            chatItemBean = new ChatItemBean(messageListVo, userid);
            chatItemBeans.add(chatItemBean);
            if (messageListVo.getType().equals("PICTURE"))  /*图片Item差不多有两个文本Item大小
                                                              缺陷：图片我还没有设置为自适应大小
                                                            */
                itemSize--;
        }
        SPHelper.setBoolean(SPHelper.IS_EXCEED_SCEEN, !(itemSize > 0));
        return chatItemBeans;
    }


    //添加内容:文本、录音、图片、地图、文件
    @Override
    protected void insertContent(String content, String roomId, String filePath, String type,int position) {
        ChatForm chatForm = encapsulation(content, roomId, filePath, type);
        chatForm.setFile_path(filePath);
        saveContent(chatForm,position);
    }

    //添加卡片
    @Override
    protected void insertCard(String account, String roomId) {
        ChatForm chatForm = encapsulation("", roomId, "", "CARD");
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

    private ChatForm encapsulation(String content, String roomId, String filePath, String type) {
        ChatForm chatForm = new ChatForm();
        chatForm.setCreate_time(DateUtils.getStringDate());
        chatForm.setRoom_id(roomId);
        chatForm.setType(type);
        chatForm.setContent(content);
        chatForm.setFile_path(filePath);
        chatForm.setSentStatus("SENDING");
        return chatForm;
    }

    private void saveContent(ChatForm chatForm,int position) {
        MessageRepository.getInstance().insertContent(chatForm, new MessageDataSource.MessageAddContent() {
            @Override
            public void onDataNotAvailable(Throwable throwable) throws Exception {
                getView().showError(throwable);
                getView().saveFail(position);
            }

            @Override
            public void onDataAvailable() {
                getView().saveSuccess(position);
            }
        });
    }

    private void saveCard(ChatForm chatForm) {
        MessageRepository.getInstance().insertCard(chatForm, new MessageDataSource.MessageAddCard() {
            @Override
            public void onDataNotAvailable(Throwable throwable) throws Exception {
                getView().showError(throwable);
            }

            @Override
            public void onDataAvailable(UserVo userVo) throws Exception {
                getView().addCardItem(userVo);
            }
        });
    }
}
