package com.example.HealthyCampus.common.data.source.callback;

import android.support.annotation.NonNull;

import com.example.HealthyCampus.common.data.form.RequestForm;
import com.example.HealthyCampus.common.network.vo.AddressListVo;
import com.example.HealthyCampus.common.network.vo.DefaultResponseVo;
import com.example.HealthyCampus.common.network.vo.RequestFriendVo;

import java.util.ArrayList;

public interface FriendDataSource {


    interface GetAllFriend {

        void onDataNotAvailable(Throwable throwable) throws Exception;

        void onDataAvailable(ArrayList<AddressListVo> addressLists) throws Exception;

        void finish() throws Exception;
    }


    void allFriend(@NonNull GetAllFriend callback);



    interface GetRequestFriend {

        void onDataNotAvailable(Throwable throwable) throws Exception;

        void onDataAvailable(ArrayList<RequestFriendVo> requestFriendVos) throws Exception;

        void finish();
    }


    void requestFriends( @NonNull GetRequestFriend callback);


    interface ClearRequestFriends {

        void onDataNotAvailable(Throwable throwable) throws Exception;

        void onDataAvailable(DefaultResponseVo defaultResponseVo) throws Exception;
    }

    void clearRequestFriends( @NonNull ClearRequestFriends callback);

    interface SendRequestFriend {

        void onDataNotAvailable(Throwable throwable);

        void onDataAvailable(DefaultResponseVo defaultResponseVo);
    }

    void sendRequestFriend(@NonNull RequestForm requestForm, @NonNull SendRequestFriend callback);

    interface SaveRequestFriend {

        void onDataNotAvailable(Throwable throwable) throws Exception;

        void onDataAvailable(DefaultResponseVo defaultResponseVo) throws Exception;
    }

    void saveRequestFriend(@NonNull RequestForm requestForm, @NonNull SaveRequestFriend callback);

    interface RefuseRequestFriend {

        void onDataNotAvailable(Throwable throwable) throws Exception;

        void onDataAvailable(DefaultResponseVo defaultResponseVo) throws Exception;
    }

    void refuseRequestFriend(@NonNull RequestForm requestForm, @NonNull RefuseRequestFriend callback);


}
