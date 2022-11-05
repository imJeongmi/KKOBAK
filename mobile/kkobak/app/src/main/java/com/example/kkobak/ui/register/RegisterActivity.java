package com.example.kkobak.ui.register;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.kkobak.R;
import com.example.kkobak.data.retrofit.api.RegisterApi;
import com.example.kkobak.data.retrofit.model.RegisterReq;
import com.example.kkobak.data.retrofit.model.RegisterRes;
import com.example.kkobak.data.room.dao.AccessTokenDao;
import com.example.kkobak.data.room.database.AccessTokenDatabase;
import com.example.kkobak.data.room.entity.AccessToken;
import com.example.kkobak.databinding.ActivityRegisterBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    ActivityRegisterBinding binding;
    Call<RegisterRes> call;
    AccessTokenDatabase db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register);

        binding.setMember(new Member("", "", "", ""));

//        binding.setMember(new Member("testEmail@naver.com", "01012341234", "testNick", "qwer123!"));

//        binding.getMember().setNickname(new ObservableField<String>( "나는바뀐 닉네임"));

        db = AccessTokenDatabase.getAppDatabase(this);
    }

    private RegisterReq convertMemberApi(Member member) {
        RegisterReq model = new RegisterReq();

        model.setEmail(member.email.get());
        model.setNickname(member.nickname.get());
        model.setHp(member.hp.get());
        model.setPassword(member.password.get());

//        AlertDialog.Builder msgBuilder = new AlertDialog.Builder(this)
//                .setTitle("model 변환 정보")
//                .setMessage("이메일: " + model.getEmail() + "\n"
//                + "닉네임: " + model.getNickname() + "\n"
//                + "전화번호: " + model.getHp() + "\n"
//                + "비밀번호: " + model.getPassword() +"\n");
//        AlertDialog msg = msgBuilder.create();
//        msg.show();

        return (model);
    }

    public void doRegist(View v) {
        call = RegisterApi.doRegistService().doRegist(convertMemberApi(binding.getMember()));
        call.enqueue(new Callback<RegisterRes>() {
            @Override
            public void onResponse(Call<RegisterRes> call, Response<RegisterRes> response) {
                if (response.code() == 200) {
                    new InsertAsyncTask(db.accessTokenDao()).execute(new AccessToken(response.body().getAccessToken()));

                    Intent intent = new Intent();
                    intent.putExtra("email", binding.getMember().email.get());
                    intent.putExtra("password", binding.getMember().password.get());
                    setResult(RESULT_OK, intent);

                    finish();

                }
                else {
                    Toast.makeText(RegisterActivity.this, "회원가입 실패", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<RegisterRes> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "fail", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static class InsertAsyncTask extends AsyncTask<AccessToken, Void, Void> {
        private final AccessTokenDao accessTokenDao;

        public InsertAsyncTask(AccessTokenDao accessTokenDao) {
            this.accessTokenDao = accessTokenDao;
        }

        @Override
        protected Void doInBackground(AccessToken... accessTokens) {
            accessTokenDao.deleteAll();
            accessTokenDao.insert(accessTokens[0]);
            return (null);
        }
    }
}
