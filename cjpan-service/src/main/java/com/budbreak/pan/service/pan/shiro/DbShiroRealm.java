package com.budbreak.pan.service.pan.shiro;


import com.budbreak.pan.service.pan.UserService;
import com.budbreak.pan.vo.pan.UserVO;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class DbShiroRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    private final Logger log = LoggerFactory.getLogger(DbShiroRealm.class);

    /**
     * 带token的时候底层调用此方法这个方法返回false,带tonken的时候只是进行jwtrealm验证
     * 登陆时subject.login(token) 此方法返回true，shiroRealm验证通过
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof UsernamePasswordToken;
    }

    /**
     * 这一步我们根据token给的用户名，去数据库查出加密过用户密码，然后把加密后的密码和盐值一起发给shiro，让它做比对
     *
     * @return
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken userPasswordToken = (UsernamePasswordToken) token;
        String username = userPasswordToken.getUsername();
        UserVO user = userService.selectUserByUserName(username);
        if (user == null) {
            throw new AuthenticationException("用户名或者密码错误");
        }
        return new SimpleAuthenticationInfo(user, user.getPassword(), ByteSource.Util.bytes(user.getAlias()), "dbRealm");
    }


    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        //没有做权限
//        User user = (User) principals.getPrimaryPrincipal();
//        List<String> roles = user.getRoles();
//        if(roles == null) {
//            roles = userService.getUserRoles(user.getUserId());
//            user.setRoles(roles);
//        }
//        if (roles != null)
//            simpleAuthorizationInfo.addRoles(roles);

        return simpleAuthorizationInfo;
    }


}
