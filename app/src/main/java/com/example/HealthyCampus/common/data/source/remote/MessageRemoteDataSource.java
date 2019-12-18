package com.example.HealthyCampus.common.data.source.remote;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.HealthyCampus.common.data.form.ChatForm;
import com.example.HealthyCampus.common.data.form.RequestForm;
import com.example.HealthyCampus.common.data.source.callback.MessageDataSource;
import com.example.HealthyCampus.common.network.NetworkManager;
import com.example.HealthyCampus.common.network.vo.DefaultResponseVo;
import com.example.HealthyCampus.common.network.vo.MessageListVo;
import com.example.HealthyCampus.common.network.vo.NoticeVo;
import com.example.HealthyCampus.common.network.vo.UserVo;
import com.example.HealthyCampus.common.utils.PictureUtil;

import java.util.List;

import retrofit2.http.Body;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class MessageRemoteDataSource implements MessageDataSource {

    private static MessageRemoteDataSource INSTANCE = null;


    public MessageRemoteDataSource() {
    }

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
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        Log.e("UserRemoteDa" + "123456", "register:7");
                    }
                })
                .subscribe(new Action1<List<MessageListVo>>() {
                    @Override
                    public void call(List<MessageListVo> messageListVos) {
                        try {
                            Log.e("ChatListPresenter" + "123456", "allchat success");
                            for (MessageListVo messageListVo : messageListVos) {
                                Log.e("ChatListPresenter" + "123456", "messageListVo.toString" + messageListVo.toString());
                            }
                            Log.e("ChatListPresenter" + "123456", "messageListVo.toString" + messageListVos.size());

                            callback.onDataAvailable(messageListVos);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        try {
                            Log.e("UserRemoteDa" + "123456", "register:9");
                            callback.onDataNotAvailable(throwable);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    public void allChatByRoomId(RequestForm requestForm, @NonNull MessageAllChat callback) {
        NetworkManager.getInstance().getMessageApi()
                .allChatByRoomId(requestForm)
                .subscribeOn(Schedulers.newThread())//子线程访问网络
                .observeOn(AndroidSchedulers.mainThread())//回调到主线程
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        Log.e("MessageRemoteDa" + "123456", "allChatByRoomId:7");
                    }
                })
                .subscribe(new Action1<List<MessageListVo>>() {
                    @Override
                    public void call(List<MessageListVo> messageListVos) {
                        try {
                            callback.onDataAvailable(messageListVos);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e("MessageRemoteDa" + "123456", "allChatByRoomId:9");
                        try {
                            callback.onDataNotAvailable(throwable);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    public void searchRoomid(RequestForm requestForm, @NonNull MessageSearchRoomid callback) {
        NetworkManager.getInstance().getMessageApi()
                .searchRoomid(requestForm)
                .subscribeOn(Schedulers.newThread())//子线程访问网络
                .observeOn(AndroidSchedulers.mainThread())//回调到主线程
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        Log.e("MessageRemoteDa" + "123456", "searchRoomid:7");
                    }
                })
                .subscribe(new Action1<MessageListVo>() {
                    @Override
                    public void call(MessageListVo messageListVo) {
                        Log.e("MessageRemoteDa" + "123456", "searchRoomid:8");
                        callback.onDataAvailable(messageListVo);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e("MessageRemoteDa" + "123456", "searchRoomid:9");
                        try {
                            callback.onDataNotAvailable(throwable);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }


    @Override
    public void allChatByUid(@NonNull RequestForm requestForm, @NonNull MessageAllChat callback) {
        NetworkManager.getInstance().getMessageApi()
                .allChatByUid(requestForm)
                .subscribeOn(Schedulers.newThread())//子线程访问网络
                .observeOn(AndroidSchedulers.mainThread())//回调到主线程
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        Log.e("MessageRemoteDa" + "123456", "allChatByUid:7");
                    }
                })
                .subscribe(new Action1<List<MessageListVo>>() {
                    @Override
                    public void call(List<MessageListVo> messageListVos) {
                        Log.e("MessageRemoteDa" + "123456", "allChatByUid:8");
                        try {
                            callback.onDataAvailable(messageListVos);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e("MessageRemoteDa" + "123456", "allChatByUid:9");
                        try {
                            callback.onDataNotAvailable(throwable);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    public void insertContent(@NonNull ChatForm chatForm, @NonNull MessageAddContent callback) {
        NetworkManager.getInstance().getMessageApi()
                .insertContent(chatForm)
                .subscribeOn(Schedulers.newThread())//子线程访问网络
                .observeOn(AndroidSchedulers.mainThread())//回调到主线程
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        Log.e("MessageRemoteDa" + "123456", "insertText:7");
                    }
                })
                .subscribe(new Action1<DefaultResponseVo>() {
                    @Override
                    public void call(DefaultResponseVo defaultResponseVo) {
                        Log.e("MessageRemoteDa" + "123456", "insertText success");
                        callback.onDataAvailable();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e("MessageRemoteDa" + "123456", "insertText:9");
                        try {
                            callback.onDataNotAvailable(throwable);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    public void insertCard(@NonNull ChatForm chatForm, @NonNull MessageAddCard callback) {
        NetworkManager.getInstance().getMessageApi()
                .insertCard(chatForm)
                .subscribeOn(Schedulers.newThread())//子线程访问网络
                .observeOn(AndroidSchedulers.mainThread())//回调到主线程
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                    }
                })
                .subscribe(new Action1<UserVo>() {
                    @Override
                    public void call(UserVo userVo) {
                        try {
                            callback.onDataAvailable(userVo);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        try {
                            Log.e("MessageRemoteDa" + "123456", "insertText:9");
                            callback.onDataNotAvailable(throwable);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    public void upPicture(@NonNull String bitmapStr, @NonNull MessageUpPicture callback) {
        NetworkManager.getUploadApi()
                .upPicture(PictureUtil.upImage())
                .subscribeOn(Schedulers.newThread())//子线程访问网络
                .observeOn(AndroidSchedulers.mainThread())//回调到主线程
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        Log.e("MessageRemoteDa" + "123456", "upPicture:7");
                    }
                })
                .subscribe(new Action1<DefaultResponseVo>() {
                    @Override
                    public void call(DefaultResponseVo defaultResponseVo) {
                        Log.e("MessageRemoteDa" + "123456", "upPicture success");
                        callback.onDataAvailable();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e("MessageRemoteDa" + "123456", "upPicture:9");
                        callback.onDataNotAvailable(throwable);
                    }
                });
    }

    @Override
    public void getAllNotice(@NonNull RequestForm requestForm,@NonNull MessageAllNotice callback) {
        NetworkManager.getInstance().getMessageApi()
                .getAllNotice(requestForm)
                .subscribeOn(Schedulers.newThread())//子线程访问网络
                .observeOn(AndroidSchedulers.mainThread())//回调到主线程
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        Log.e("MessageRemoteDa" + "123456", "getAllNotice:7");
                    }
                })
                .subscribe(new Action1<List<NoticeVo>>() {
                    @Override
                    public void call(List<NoticeVo> noticeVos) {
                        Log.e("MessageRemoteDa" + "123456", "getAllNotice success");
                        try {
                            callback.onDataAvailable(noticeVos);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e("MessageRemoteDa" + "123456", "getAllNotice:9");
                        try {
                            callback.onDataNotAvailable(throwable);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    public void clearNotice(@NonNull MessagClearNotice callback) {
        NetworkManager.getInstance().getMessageApi()
                .clearNotice()
                .subscribeOn(Schedulers.newThread())//子线程访问网络
                .observeOn(AndroidSchedulers.mainThread())//回调到主线程
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        Log.e("MessageRemoteDa" + "123456", "getAllNotice:7");
                    }
                })
                .subscribe(new Action1<DefaultResponseVo>() {
                    @Override
                    public void call(DefaultResponseVo defaultResponseVo ) {
                        Log.e("MessageRemoteDa" + "123456", "getAllNotice success");
                        try {
                            callback.onDataAvailable();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e("MessageRemoteDa" + "123456", "getAllNotice:9");
                        try {
                            callback.onDataNotAvailable(throwable);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    public void deleteNotice(@NonNull RequestForm requestForm, @NonNull MessageDeleteNotice callback) {
        NetworkManager.getInstance().getMessageApi()
                .deleteNotice(requestForm)
                .subscribeOn(Schedulers.newThread())//子线程访问网络
                .observeOn(AndroidSchedulers.mainThread())//回调到主线程
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                    }
                })
                .subscribe(new Action1<DefaultResponseVo>() {
                    @Override
                    public void call(DefaultResponseVo defaultResponseVo) {
                        try {
                            callback.onDataAvailable();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        try {
                            Log.e("MessageRemoteDa" + "123456", "deleteNotice:9");
                            callback.onDataNotAvailable(throwable);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    public void lookNotice(@NonNull RequestForm requestForm, @NonNull MessageLookNotice callback) {
        NetworkManager.getInstance().getMessageApi()
                .lookNotice(requestForm)
                .subscribeOn(Schedulers.newThread())//子线程访问网络
                .observeOn(AndroidSchedulers.mainThread())//回调到主线程
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                    }
                })
                .subscribe(new Action1<DefaultResponseVo>() {
                    @Override
                    public void call(DefaultResponseVo defaultResponseVo) {
                        try {
                            callback.onDataAvailable();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        try {
                            Log.e("MessageRemoteDa" + "123456", "lookNotice:9");
                            callback.onDataNotAvailable(throwable);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

}
