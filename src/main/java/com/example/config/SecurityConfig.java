package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter { // ←セキュリティ設定用は必ず継承
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// 認可の設定
		http.authorizeRequests()
				.antMatchers("/loginForm").permitAll() // /loginFormは、全ユーザからのアクセスを許可
				.anyRequest().authenticated(); // 許可した項目以外はアクセス拒否で、認証を求める

		//ログイン処理
		http.formLogin()
				.loginProcessingUrl("/login") // ログイン処理のパス /loginにPOSTメソッドでアクセスして認証される
				.loginPage("/loginForm") // ログインページの指定
				.usernameParameter("email") // ログイン用フォームで利用する部品名(ユーザー名)
				.passwordParameter("password") // ログイン用フォームで利用する部品名(パスワード)
				.defaultSuccessUrl("/library", true) // ログイン成功時のリダイレクト先のパス
				.failureUrl("/loginForm?error"); // ログイン失敗時のリダイレクト先のパス

		//ログアウト処理
		http.logout()
				.logoutUrl("/logout") //ログアウト処理のパス
				.logoutSuccessUrl("/loginForm"); //ログアウト成功後のパス
	}
	

	@Override
	// WebSecurity型の引数を持ったconfigure()を追加します
	public void configure(WebSecurity web) throws Exception {
		/** 以下のファイルパス配下のディレクトリ、ファイルすべてを認証・認可の対象から除外します
		    src/main/resources/static/css/
		    src/main/resources/static/js/
		    src/main/resources/static/images/
		*/
		web.ignoring().antMatchers("/css/**", "/js/**");
	}
	
	//ハッシュ(暗号化)
  @Bean
  public PasswordEncoder passwordEncoder() {
      return new BCryptPasswordEncoder();
  }
}
