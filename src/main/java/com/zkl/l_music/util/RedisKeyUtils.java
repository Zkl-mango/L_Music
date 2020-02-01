package com.zkl.l_music.util;

public class RedisKeyUtils {

    //保存评论点赞数据的key
    public static final String MAP_KEY_COMMENT_LIKED = "MAP_COMMENT_LIKED";
    //保存评论被点赞数量的key
    public static final String MAP_KEY_COMMENT_LIKED_COUNT = "MAP_COMMENT_LIKED_COUNT";

    /**
     * 拼接被点赞的用户id和点赞的人的id作为key。格式 222222::333333
     * @param likedUserId 点赞的人id
     * @param commendId 评论的id
     * @return
     */
    public static String getLikedKey(String likedUserId,String commendId){
        StringBuilder builder = new StringBuilder();
        builder.append(likedUserId);
        builder.append("::");
        builder.append(commendId);
        return builder.toString();
    }
}
