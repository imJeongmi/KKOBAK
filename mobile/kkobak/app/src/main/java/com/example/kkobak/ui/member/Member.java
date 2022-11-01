package com.example.kkobak.ui.member;

import androidx.databinding.ObservableField;

public class Member {
    ObservableField<String> email = new ObservableField<>();

    ObservableField<String> hp = new ObservableField<>();

    ObservableField<String> nickname = new ObservableField<>();

    ObservableField<String> password = new ObservableField<>();

    public Member(String email, String hp, String nickname, String password) {
        this.email.set(email);
        this.hp.set(hp);
        this.nickname.set(nickname);x
        this.password.set(password);
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

    public void setEmail(ObservableField<String> email) {
        this.email = email;
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
}
