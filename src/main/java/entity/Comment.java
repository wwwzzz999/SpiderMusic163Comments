package entity;

import lombok.Data;

@Data
public class Comment {
    String nickname;
    int userId;
    String beReplied;
    String content;

}
