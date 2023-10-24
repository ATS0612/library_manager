package com.example.service;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.entity.User;

// ログイン中のユーザーに関する次の情報を管理するためのクラス
// User.javaからデータをもってきている
public class LoginUser implements UserDetails {// 認証・認可に関係するメソッドが定義されたインターフェース

    // Userオブジェクト(Entityクラス)
    private final User user;

    // コンストラクタ
    public LoginUser(User user) {
        this.user = user;
    }

    // Entityクラスである、Userオブジェクト userのgetter
    public User getUser() {
        return this.user;
    }

    // ユーザーの認証に使用されるパスワードを返却する
    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    // ユーザーの認証に使用されるユーザー名を返却する (email)
    @Override
    public String getUsername() {
        return this.user.getEmail();
    }
    
// ----------------------------------------------------------------------------------    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.NO_AUTHORITIES;
    }

    // アカウントの有効期限の状態を判定する
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // アカウントのロック状態を判定する
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 資格情報の有効期限の状態を判定する
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 有効なユーザかを判定する
    @Override
    public boolean isEnabled() {
        return true;
    }
}
