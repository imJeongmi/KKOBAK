package com.a104.kkobak.ui.member;

import androidx.databinding.ObservableField;

public class Member {
    ObservableField<String> email = new ObservableField<>();

    ObservableField<String> hp = new ObservableField<>();

    ObservableField<String> nickname = new ObservableField<>();

    ObservableField<String> password = new ObservableField<>();

    public Member(String email, String hp, String nickname, String password) {
        this.email.set(email);
        this.hp.set(hp);
        this.nickname.set(nickname);
        this.password.set(password);
    }

    public void setEmail(ObservableField<String> email) {
        this.email = email;
    }

    public void setHp(ObservableField<String> hp) {
        this.hp = hp;
    }

    public void setNickname(ObservableField<String> nickname) {
        this.nickname = nickname;
    }

    public void setPassword(ObservableField<String> password) {
        this.password = password;
    }

    public ObservableField<String> getEmail() {
        return email;
    }

    public ObservableField<String> getHp() {
        return hp;
    }

    public ObservableField<String> getNickname() {
        return nickname;
    }

    public ObservableField<String> getPassword() {
        return password;
    }

    public void onEmailChanged(CharSequence s, int start, int before, int count) {
        email.set(s.toString());
    }

    public void onNicknameChanged(CharSequence s, int start, int before, int count) {
        nickname.set(s.toString());
    }

    public void onPasswordChanged(CharSequence s, int start, int before, int count) {
        password.set(s.toString());
    }

    public void onHpChanged(CharSequence s, int start, int before, int count) {
        hp.set(s.toString());
    }
}