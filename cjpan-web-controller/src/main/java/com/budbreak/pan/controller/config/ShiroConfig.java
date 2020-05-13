package com.budbreak.pan.controller.config;


import com.budbreak.pan.controller.config.filter.AnyRolesAuthorizationFilter;
import com.budbreak.pan.controller.config.filter.JwtAuthFilter;
import com.budbreak.pan.service.pan.shiro.DbShiroRealm;
import com.budbreak.pan.service.pan.shiro.JWTShiroRealm;
import org.apache.shiro.authc.Authenticator;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authc.pam.FirstSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.mgt.SessionStorageEvaluator;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.mgt.DefaultWebSessionStorageEvaluator;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * shiro配置类
 */
@Configuration
public class ShiroConfig {
    /**
     * 注册shiro的Filter，拦截请求
     */
    @Bean
    public FilterRegistrationBean<Filter> filterRegistrationBean() throws Exception {
        FilterRegistrationBean<Filter> filterRegistration = new FilterRegistrationBean<Filter>();
        //设置过滤器为Shiro过滤器
        filterRegistration.setFilter((Filter) shiroFilter(getSecurityManager()).getObject());
        filterRegistration.addInitParameter("targetFilterLifecycle", "true");
        filterRegistration.setAsyncSupported(true);
        filterRegistration.setEnabled(true);
        filterRegistration.setDispatcherTypes(DispatcherType.REQUEST, DispatcherType.ASYNC);

        return filterRegistration;
    }

    @Bean
    public DefaultWebSecurityManager getSecurityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealms(Arrays.asList(jwtShiroRealm(), dbShiroRealm(hashedCredentialsMatcher())));
        return securityManager;
    }

    /**
     * LifecycleBeanPostProcessor，这是个DestructionAwareBeanPostProcessor的子类，
     * 负责org.apache.shiro.util.Initializable类型bean的生命周期的，初始化和销毁。
     * 主要是AuthorizingRealm类的子类，以及EhCacheManager类。
     */
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

//    /**
//     *  解决注解不生效的问题
//     * @return
//     */
//    @Bean
//    public static DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator(){
//        return new DefaultAdvisorAutoProxyCreator();
//    }

    @Bean
    @DependsOn({"lifecycleBeanPostProcessor"})
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(getSecurityManager());
        return authorizationAttributeSourceAdvisor;
    }


    /**
     * 加密
     *
     * @return
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        credentialsMatcher.setHashAlgorithmName("MD5");
        credentialsMatcher.setHashIterations(10);
        return credentialsMatcher;
    }

    /**
     * 多realm配置--验证用户帐号--多个Realm进行验证-初始化Authenticator
     *
     * @return
     */
    @Bean
    public Authenticator authenticator() {
        ModularRealmAuthenticator authenticator = new ModularRealmAuthenticator();
        //设置两个Realm，一个用于用户登录验证和访问权限获取；一个用于jwt token的认证
        authenticator.setRealms(Arrays.asList(jwtShiroRealm(), dbShiroRealm(hashedCredentialsMatcher())));
        //设置一个验证通过则通过
        authenticator.setAuthenticationStrategy(new FirstSuccessfulStrategy());
        return authenticator;
    }

    /**
     * 禁用session, 不保存用户登录状态。保证每次请求都重新认证。
     * 需要注意的是，如果用户代码里调用Subject.getSession()还是可以用session，如果要完全禁用，要配合下面的noSessionCreation的Filter来实现
     */
    @Bean
    protected SessionStorageEvaluator sessionStorageEvaluator() {
        DefaultWebSessionStorageEvaluator sessionStorageEvaluator = new DefaultWebSessionStorageEvaluator();
        sessionStorageEvaluator.setSessionStorageEnabled(false);
        return sessionStorageEvaluator;
    }

    /**
     * 用于用户名密码登录时认证的realm
     */
    @Bean("dbRealm")
    public Realm dbShiroRealm(HashedCredentialsMatcher matcher) {
        DbShiroRealm myShiroRealm = new DbShiroRealm();
        myShiroRealm.setCredentialsMatcher(matcher);
        return myShiroRealm;
    }

    /**
     * 用于JWT token认证的realm
     */
    @Bean("jwtRealm")
    public Realm jwtShiroRealm() {
        JWTShiroRealm myShiroRealm = new JWTShiroRealm();
        return myShiroRealm;
    }

    /**
     * 设置过滤器，将自定义的Filter加入
     *
     * @param securityManager
     */
    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        factoryBean.setSecurityManager(securityManager);
        Map<String, Filter> filterMap = factoryBean.getFilters();
        filterMap.put("authcToken", createAuthFilter());
        filterMap.put("anyRole", createRolesFilter());
        factoryBean.setFilters(filterMap);
        factoryBean.setLoginUrl("/");
        factoryBean.setSuccessUrl("/old");
        factoryBean.setFilterChainDefinitionMap(shiroFilterChainDefinition().getFilterChainMap());
        return factoryBean;
    }

    @Bean
    protected ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();
        chainDefinition.addPathDefinition("/api/v1/pan/user/login", "noSessionCreation,anon");
        chainDefinition.addPathDefinition("/", "noSessionCreation,anon");
        chainDefinition.addPathDefinition("/api/v1/pan/user/sendEmail", "noSessionCreation,anon");
        chainDefinition.addPathDefinition("/api/v1/pan/user/forget", "noSessionCreation,anon");
        chainDefinition.addPathDefinition("/shareCallBack", "noSessionCreation,anon");
        chainDefinition.addPathDefinition("/sharefile", "noSessionCreation,anon");
        chainDefinition.addPathDefinition("/sharefileSecret", "noSessionCreation,anon");
        chainDefinition.addPathDefinition("/share", "noSessionCreation,anon");
        chainDefinition.addPathDefinition("/api/v1/pan/user/register", "noSessionCreation,anon");
        chainDefinition.addPathDefinition("/api/v1/link/secret/shareToMyPan", "noSessionCreation,anon");
        chainDefinition.addPathDefinition("/api/v1/pan/user/quit", "noSessionCreation,anon");
        chainDefinition.addPathDefinition("/data/**", "anon");
        chainDefinition.addPathDefinition("/static/**", "anon");
        chainDefinition.addPathDefinition("/images/**", "anon");
        chainDefinition.addPathDefinition("/favicon.ico", "anon");
        chainDefinition.addPathDefinition("/js/**", "anon");
        chainDefinition.addPathDefinition("/css/**", "anon");
        chainDefinition.addPathDefinition("/bootstrap/**", "anon");
        chainDefinition.addPathDefinition("/**", "user");
        return chainDefinition;
    }

    /**
     * 注意不要加@Bean注解，不然spring会自动注册成filter
     */
    protected JwtAuthFilter createAuthFilter() {
        return new JwtAuthFilter();
    }

    /**
     * 注意不要加@Bean注解，不然spring会自动注册成filter
     */
    protected AnyRolesAuthorizationFilter createRolesFilter() {
        return new AnyRolesAuthorizationFilter();
    }

}
