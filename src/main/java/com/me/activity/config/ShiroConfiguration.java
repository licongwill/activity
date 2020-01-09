package com.me.activity.config;

import com.me.activity.realm.MeAuthorizingRealm;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.MemorySessionDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.mgt.DefaultWebSubjectFactory;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @program: activity
 * @description: shiro配置
 * @author: lic
 * @create: 2020-01-09 17:07
 **/
@Configuration
public class ShiroConfiguration {

    /**
    *@Description: AuthorizingRealm
    *@Param: []
    *@return: org.apache.shiro.realm.AuthorizingRealm
    *@Author: lic
    *@date: 2020/1/9
    */
    @Bean("authorizingRealm")
    @ConditionalOnMissingBean(AuthorizingRealm.class)
    public AuthorizingRealm authorizingRealm(){
        AuthorizingRealm authorizingRealm = new MeAuthorizingRealm();
        authorizingRealm.setAuthorizationCachingEnabled(true);
        authorizingRealm.setAuthenticationCachingEnabled(true);
        authorizingRealm.setAuthenticationCacheName("authenticationCache");
        authorizingRealm.setAuthorizationCacheName("authorizationCache");
        return authorizingRealm;
    }

    @Bean("cacheManager")
    public CacheManager cacheManager(){
        MemoryConstrainedCacheManager cacheManager = new MemoryConstrainedCacheManager();
        return cacheManager;
    }

    @Bean("sessionManager")
    public DefaultWebSessionManager sessionManager(CacheManager cacheManager){
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        MemorySessionDAO sessionDAO = new MemorySessionDAO();
        SimpleCookie simpleCookie = new SimpleCookie("sid");
        simpleCookie.setHttpOnly(true);
        sessionManager.setSessionIdCookie(simpleCookie);
        sessionManager.setSessionIdCookieEnabled(true);
        sessionManager.setCacheManager(cacheManager);
        sessionManager.setSessionDAO(sessionDAO);
        sessionManager.setDeleteInvalidSessions(true);
        sessionManager.setSessionValidationSchedulerEnabled(true);
        return sessionManager;
    }

    @Bean("securityManager")
    @DependsOn(value = {"authorizingRealm","sessionManager","cacheManager"})
    public DefaultWebSecurityManager securityManager( SessionManager sessionManager,
         AuthorizingRealm authorizingRealm,CacheManager cacheManager){
        DefaultWebSecurityManager webSecurityManager = new DefaultWebSecurityManager();
        DefaultWebSubjectFactory subjectFactory = new DefaultWebSubjectFactory();

        webSecurityManager.setSubjectFactory(subjectFactory);
        webSecurityManager.setSessionManager(sessionManager);
        webSecurityManager.setRealm(authorizingRealm);
        webSecurityManager.setSubjectFactory(subjectFactory);
        webSecurityManager.setCacheManager(cacheManager);
        return webSecurityManager;
    }

    @Bean
    @ConditionalOnMissingBean(FilterRegistrationBean.class)
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
        filterRegistration.setFilter(new DelegatingFilterProxy("shiroFilter"));
        //  该值缺省为false,表示生命周期由SpringApplicationContext管理,设置为true则表示由ServletContainer管理
        filterRegistration.addInitParameter("targetFilterLifecycle", "true");
        filterRegistration.setEnabled(true);
        filterRegistration.addUrlPatterns("/*");
        filterRegistration.setDispatcherTypes(DispatcherType.REQUEST, DispatcherType.FORWARD);
        return filterRegistration;
    }

    /**
     * @param shiroFilterFactoryBean
     */
    private void loadShiroFilterChain(ShiroFilterFactoryBean shiroFilterFactoryBean){
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        //默认不拦截静态资源
        filterChainDefinitionMap.put("/static/**", "anon");
        filterChainDefinitionMap.put("/callback", "anon");
        filterChainDefinitionMap.put("/logout","anon");
        //全部拦截
        filterChainDefinitionMap.put("/**","anon");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
    }

    /**
     * shiroFilter
     * @param securityManager
     * @param
     * @return
     */
    @Bean("shiroFilter")
    @DependsOn("securityManager")
    @ConditionalOnMissingBean(ShiroFilterFactoryBean.class)
    public ShiroFilterFactoryBean factory(DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // 必须设置 SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        loadShiroFilterChain(shiroFilterFactoryBean);
        Map<String, Filter> filters = new HashMap<>(4);
        shiroFilterFactoryBean.setFilters(filters);
        return shiroFilterFactoryBean;
    }



    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    @ConditionalOnMissingBean(DefaultAdvisorAutoProxyCreator.class)
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        // 强制使用cglib，防止重复代理和可能引起代理出错的问题
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }

    @Bean
    @ConditionalOnMissingBean(LifecycleBeanPostProcessor.class)
    public static LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    @DependsOn("securityManager")
    @ConditionalOnMissingBean(AuthorizationAttributeSourceAdvisor.class)
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

}
