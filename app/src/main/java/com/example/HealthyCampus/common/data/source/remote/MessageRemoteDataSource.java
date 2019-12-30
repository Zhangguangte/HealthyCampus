package com.example.HealthyCampus.common.data.source.remote;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.HealthyCampus.common.data.form.ChatForm;
import com.example.HealthyCampus.common.data.form.RequestForm;
import com.example.HealthyCampus.common.data.source.callback.MessageDataSource;
import com.example.HealthyCampus.common.network.NetworkManager;
import com.example.HealthyCampus.common.utils.PictureUtil;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MessageRemoteDataSource implements MessageDataSource {

    private static MessageRemoteDataSource INSTANCE = null;

    public static MessageRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MessageRemoteDataSource();
        }
        return INSTANCE;
    }


    @Override
    public void lastMessage(@NonNull MessageSearchMessage callback) {
        NetworkManager.getInstance().getMessageApi()
                .lastMessage()
                .subscribeOn(Schedulers.newThread())//子线程访问网络
                .observeOn(AndroidSchedulers.mainThread())//回调到主线程
                .doOnSubscribe(() -> Log.e("MessageRemoteDa" + "123456", "lastMessage:7"))
                .subscribe(messageListVos -> {
                    try {
                        Log.e("MessageRemoteDa" + "123456", "lastMessage success");
//                            for (MessageListVo messageListVo : messageListVos) {
//                                Log.e("MessageRemoteDa" + "123456", "lastMessage.toString" + messageListVo.toString());
//                            }
//                            Log.e("MessageRemoteDa" + "123456", "lastMessage.toString" + messageListVos.size());

                        callback.onDataAvailable(messageListVos);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, throwable -> {
                    try {
                        Log.e("MessageRemoteDa" + "123456", "register:9");
                        callback.onDataNotAvailable(throwable);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    @Override
    public void createRoom(RequestForm requestForm,@NonNull MessageCreateRoom callback) {
        NetworkManager.getInstance().getMessageApi()
                .createRoom(requestForm)
                .subscribeOn(Schedulers.newThread())//子线程访问网络
                .observeOn(AndroidSchedulers.mainThread())//回调到主线程
                .doOnSubscribe(() -> Log.e("MessageRemoteDa" + "123456", "createRoom:7"))
                .subscribe(messageListVo -> {
                    try {
                        Log.e("MessageRemoteDa" + "123456", "createRoom success");
//                        Log.e("MessageRemoteDa" + "123456", "roomId"+roomId);
//                            for (MessageListVo messageListVo : messageListVos) {
//                                Log.e("MessageRemoteDa" + "123456", "lastMessage.toString" + messageListVo.toString());
//                            }
//                            Log.e("MessageRemoteDa" + "123456", "lastMessage.toString" + messageListVos.size());

                        callback.onDataAvailable(messageListVo.getRoom_id());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, throwable -> {
                    try {
                        Log.e("MessageRemoteDa" + "123456", "createRoom:9");
                        callback.onDataNotAvailable(throwable);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    @Override
    public void deleteRoomId(RequestForm requestForm, @NonNull MessageDeleteRoom callback) {
        NetworkManager.getInstance().getMessageApi()
                .deleteRoomId(requestForm)
                .subscribeOn(Schedulers.newThread())//子线程访问网络
                .observeOn(AndroidSchedulers.mainThread())//回调到主线程
                .doOnSubscribe(() -> Log.e("MessageRemoteDa" + "123456", "deleteRoomId:7"))
                .subscribe(defaultResponseVo -> {
                    try {
                        Log.e("MessageRemoteDa" + "123456", "deleteRoomId success");
                        callback.onDataAvailable(defaultResponseVo);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, throwable -> {
                    try {
                        Log.e("MessageRemoteDa" + "123456", "deleteRoomId:9");
                        callback.onDataNotAvailable(throwable);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    @Override
    public void getDoctorRoom(@NonNull MessageGetRoom callback) {
        NetworkManager.getInstance().getMessageApi()
                .getDoctorRoom()
                .subscribeOn(Schedulers.newThread())//子线程访问网络
                .observeOn(AndroidSchedulers.mainThread())//回调到主线程
                .doOnSubscribe(() -> Log.e("MessageRemoteDa" + "123456", "getDoctorRoom:7"))
                .subscribe(messageListVo -> {
                    try {
                        Log.e("MessageRemoteDa" + "123456", "getDoctorRoom success");
                        callback.onDataAvailable(messageListVo);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, throwable -> {
                    try {
                        Log.e("MessageRemoteDa" + "123456", "getDoctorRoom:9");
                        callback.onDataNotAvailable(throwable);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    @Override
    public void allChatByRoomId(RequestForm requestForm, @NonNull MessageAllChat callback) {
        NetworkManager.getInstance().getMessageApi()
                .allChatByRoomId(requestForm)
                .subscribeOn(Schedulers.newThread())//子线程访问网络
                .observeOn(AndroidSchedulers.mainThread())//回调到主线程
                .doOnSubscribe(() -> Log.e("MessageRemoteDa" + "123456", "allChatByRoomId:7"))
                .subscribe(messageListVos -> {
                    try {
                        callback.onDataAvailable(messageListVos);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, throwable -> {
                    Log.e("MessageRemoteDa" + "123456", "allChatByRoomId:9");
                    try {
                        callback.onDataNotAvailable(throwable);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    @Override
    public void searchRoomid(RequestForm requestForm, @NonNull MessageSearchRoomid callback) {
        NetworkManager.getInstance().getMessageApi()
                .searchRoomid(requestForm)
                .subscribeOn(Schedulers.newThread())//子线程访问网络
                .observeOn(AndroidSchedulers.mainThread())//回调到主线程
                .doOnSubscribe(() -> Log.e("MessageRemoteDa" + "123456", "searchRoomid:7"))
                .subscribe(messageListVo -> {
                    Log.e("MessageRemoteDa" + "123456", "searchRoomid:8");
                    callback.onDataAvailable(messageListVo);
                }, throwable -> {
                    Log.e("MessageRemoteDa" + "123456", "searchRoomid:9");
                    try {
                        callback.onDataNotAvailable(throwable);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }


    @Override
    public void allChatByUid(@NonNull RequestForm requestForm, @NonNull MessageAllChat callback) {
        NetworkManager.getInstance().getMessageApi()
                .allChatByUid(requestForm)
                .subscribeOn(Schedulers.newThread())//子线程访问网络
                .observeOn(AndroidSchedulers.mainThread())//回调到主线程
                .doOnSubscribe(() -> Log.e("MessageRemoteDa" + "123456", "allChatByUid:7"))
                .subscribe(messageListVos -> {
                    Log.e("MessageRemoteDa" + "123456", "allChatByUid:8");
                    try {
                        callback.onDataAvailable(messageListVos);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, throwable -> {
                    Log.e("MessageRemoteDa" + "123456", "allChatByUid:9");
                    try {
                        callback.onDataNotAvailable(throwable);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    @Override
    public void insertContent(@NonNull ChatForm chatForm, @NonNull MessageAddContent callback) {
        NetworkManager.getInstance().getMessageApi()
                .insertContent(chatForm)
                .subscribeOn(Schedulers.newThread())//子线程访问网络
                .observeOn(AndroidSchedulers.mainThread())//回调到主线程
                .doOnSubscribe(() -> Log.e("MessageRemoteDa" + "123456", "insertText:7"))
                .subscribe(defaultResponseVo -> {
                    Log.e("MessageRemoteDa" + "123456", "insertText success");
                    callback.onDataAvailable();
                }, throwable -> {
                    Log.e("MessageRemoteDa" + "123456", "insertText:9");
                    try {
                        callback.onDataNotAvailable(throwable);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    @Override
    public void insertCard(@NonNull ChatForm chatForm, @NonNull MessageAddCard callback) {
        NetworkManager.getInstance().getMessageApi()
                .insertCard(chatForm)
                .subscribeOn(Schedulers.newThread())//子线程访问网络
                .observeOn(AndroidSchedulers.mainThread())//回调到主线程
                .doOnSubscribe(() -> {
                })
                .subscribe(userVo -> {
                    try {
                        callback.onDataAvailable(userVo);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, throwable -> {
                    try {
                        Log.e("MessageRemoteDa" + "123456", "insertText:9");
                        callback.onDataNotAvailable(throwable);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    @Override
    public void upPicture(@NonNull String bitmapStr, @NonNull MessageUpPicture callback) {
        NetworkManager.getUploadApi()
                .upPicture(PictureUtil.upImage())
                .subscribeOn(Schedulers.newThread())//子线程访问网络
                .observeOn(AndroidSchedulers.mainThread())//回调到主线程
                .doOnSubscribe(() -> Log.e("MessageRemoteDa" + "123456", "upPicture:7"))
                .subscribe(defaultResponseVo -> {
                    Log.e("MessageRemoteDa" + "123456", "upPicture success");
                    callback.onDataAvailable();
                }, throwable -> {
                    Log.e("MessageRemoteDa" + "123456", "upPicture:9");
                    callback.onDataNotAvailable(throwable);
                });
    }

    @Override
    public void getAllNotice(@NonNull RequestForm requestForm, @NonNull MessageAllNotice callback) {
        NetworkManager.getInstance().getMessageApi()
                .getAllNotice(requestForm)
                .subscribeOn(Schedulers.newThread())//子线程访问网络
                .observeOn(AndroidSchedulers.mainThread())//回调到主线程
                .doOnSubscribe(() -> Log.e("MessageRemoteDa" + "123456", "getAllNotice:7"))
                .subscribe(noticeVos -> {
                    Log.e("MessageRemoteDa" + "123456", "getAllNotice success");
                    try {
                        callback.onDataAvailable(noticeVos);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, throwable -> {
                    Log.e("MessageRemoteDa" + "123456", "getAllNotice:9");
                    try {
                        callback.onDataNotAvailable(throwable);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    @Override
    public void clearNotice(@NonNull MessagClearNotice callback) {
        NetworkManager.getInstance().getMessageApi()
                .clearNotice()
                .subscribeOn(Schedulers.newThread())//子线程访问网络
                .observeOn(AndroidSchedulers.mainThread())//回调到主线程
                .doOnSubscribe(() -> Log.e("MessageRemoteDa" + "123456", "getAllNotice:7"))
                .subscribe(defaultResponseVo -> {
                    Log.e("MessageRemoteDa" + "123456", "getAllNotice success");
                    try {
                        callback.onDataAvailable();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, throwable -> {
                    Log.e("MessageRemoteDa" + "123456", "getAllNotice:9");
                    try {
                        callback.onDataNotAvailable(throwable);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    @Override
    public void deleteNotice(@NonNull RequestForm requestForm, @NonNull MessageDeleteNotice callback) {
        NetworkManager.getInstance().getMessageApi()
                .deleteNotice(requestForm)
                .subscribeOn(Schedulers.newThread())//子线程访问网络
                .observeOn(AndroidSchedulers.mainThread())//回调到主线程
                .doOnSubscribe(() -> {
                })
                .subscribe(defaultResponseVo -> {
                    try {
                        callback.onDataAvailable();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, throwable -> {
                    try {
                        Log.e("MessageRemoteDa" + "123456", "deleteNotice:9");
                        callback.onDataNotAvailable(throwable);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    @Override
    public void lookNotice(@NonNull RequestForm requestForm, @NonNull MessageLookNotice callback) {
        NetworkManager.getInstance().getMessageApi()
                .lookNotice(requestForm)
                .subscribeOn(Schedulers.newThread())//子线程访问网络
                .observeOn(AndroidSchedulers.mainThread())//回调到主线程
                .doOnSubscribe(() -> {
                })
                .subscribe(defaultResponseVo -> {
                    try {
                        callback.onDataAvailable();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, throwable -> {
                    try {
                        Log.e("MessageRemoteDa" + "123456", "lookNotice:9");
                        callback.onDataNotAvailable(throwable);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

}
