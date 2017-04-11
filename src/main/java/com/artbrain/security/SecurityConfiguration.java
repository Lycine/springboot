package com.artbrain.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;


@Configurable
@EnableGlobalMethodSecurity(prePostEnabled = true)//允许进入页面方法前检验
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyAuthenticationProvider provider;//自定义验证
//  @Autowired
//  private UserDetailsService userDetailsService;//自定义用户服务

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/resources/**", "/webjars/**", "/img/**","/static/**").permitAll()//无需访问权限
                //login page and registration end-point
                .antMatchers("/pass**","/pass/**").permitAll()
                .antMatchers("/student/**").hasAuthority("ROLE_STUDENT")//admin角色访问权限
                .antMatchers("/admin/**").hasAuthority("ROLE_ADMIN")//admin角色访问权限
                .antMatchers("/teacher/**").hasAuthority("ROLE_TEACHER")//user角色访问权限
                .anyRequest()//all others request authentication
                .authenticated()
                .and()
                .formLogin().loginPage("/pass?signIn&signInFirst")
//                .failureUrl("/pass?signIn&signInFailed") //配置登录失败URL
                .failureHandler(new LoginFailureHandler())
                .loginProcessingUrl("/pass/signIn")
                .usernameParameter("username")//email,loginname,phonenumber
                .passwordParameter("password")
                .successHandler(new LoginSuccessHandler())
                .permitAll()
                .and()
                .logout().logoutUrl("/logout").logoutSuccessUrl("/pass?signIn&logoutSuccess");
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        //将验证过程交给自定义验证工具
        auth.authenticationProvider(provider);
    }
}