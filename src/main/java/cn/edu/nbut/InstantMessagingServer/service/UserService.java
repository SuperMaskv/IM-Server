package cn.edu.nbut.InstantMessagingServer.service;

import io.netty.channel.Channel;

public interface UserService {
    /**
     * 根据用户名和用户密码来判断用户是否合法
     *
     * @param userName 用户名
     * @param userPwd  用户密码
     * @return 用户验证结果
     */
    boolean userAuth(String userName, String userPwd);

    /**
     * 根据用户名来判断用户是否已经登录
     *
     * @param userName 用户名
     * @return 如果用户已经登录，返回结果为 true, 反之为 false。
     */
    boolean isUserLogged(String userName);

    /**
     * 将新登录到系统的用户加入到 ConnectionMap 中，返回 token
     *
     * @param userName 用户名
     * @param channel  用户对应的 Channel
     * @return token
     */
    long addUserToConnectionMap(String userName, Channel channel);

    /**
     * 判断 ConnectionMap 中是否存在对应 token 的记录
     *
     * @param token 要被验证的 token
     * @return 如果 token 合法则返回 true
     */
    boolean validateToken(long token);

    /**
     * 根据用户名和 token 将用户从 ConnectionMap 中移除。
     * @param userName 用户名
     * @param token token
     */
    void removeUserFromConnectionMap(String userName, long token);
}
