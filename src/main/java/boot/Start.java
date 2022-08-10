package boot;


import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import entity.Comment;
import org.junit.Test;
import process.Music163process;
import us.codecraft.webmagic.Spider;
import utils.NetEaseMusicUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
/*
* 测试用例
* songid：1332722587
* userid：6329437
* */
public class Start {
    private static final int ONE_PAGE=20;

    public static void main(String[] args) {
       // readKey();
//        JSONObject ob = JSONObject.parseObject(NetEaseMusicUtils.crawlAjaxUrl("1332722587",0));
//        String s= (String) JSONPath.eval(ob,"$.comments[0].beReplied[0].content");
//        System.out.println(s);
        Spider spider = Spider.create(new Music163process());

    }

    public static String readKey(){
        System.out.print("输入歌曲ID: ");
        Scanner scanner = new Scanner(System.in);
        String songId = scanner.nextLine();
        System.out.print("输入用户ID: ");
        int userId = scanner.nextInt();
        if(songId.length() != 0 && String.valueOf(userId) != null){
            System.out.println(userId+" "+songId);

//            todo
            getComments(userId,songId);

        }else {
            System.out.println("输入无效");
        }


        return "";
    }

//    @Test
//    public void s(){
//        int count = 80;
//        int pages = count%20==0?count/20:count/20+1;
//        System.out.println(pages);
//    }

    public static void getComments(int userId, String songId){
        int offest=0;
        int commentCount;
        String getCommentsCount = NetEaseMusicUtils.crawlAjaxUrl(songId,offest);
        JSONObject obj = JSONObject.parseObject(getCommentsCount);
        commentCount = (int)JSONPath.eval(obj,"$.total");
//        计算页数 用于显示爬取处理进度
        int pages = commentCount%ONE_PAGE==0?commentCount/ONE_PAGE:commentCount/ONE_PAGE+1;
        int total=1;
        List<Comment> res =new ArrayList<>();
        ///判断userId 是否存在
        //boolean judge=false;
        for (;offest<commentCount;offest=offest+ONE_PAGE) {
            JSONObject comments = JSONObject.parseObject(NetEaseMusicUtils.crawlAjaxUrl("1332722587",offest));
//            String progress = " "+total+"/"+pages+" ";
            System.out.println(" "+total+"/"+pages+" ");
            //获取comments长度
            int comments_lenght = JSONPath.size(comments,"$.comments");
            for (int i=0;i<comments_lenght;i++){
                boolean judge=JSONPath.containsValue(comments,"$.comments["+i+"].user.userId",userId);
                if(judge){
                    System.out.println("匹配成功");
                    Comment comment = new Comment();
                    comment.setUserId((Integer) JSONPath.eval(comments,"$.comments["+i+"].user.userId"));
                    comment.setNickname((String) JSONPath.eval(comments,"$.comments["+i+"].user.nickname"));
                    comment.setContent((String) JSONPath.eval(comments,"$.comments["+i+"].content"));
                    comment.setBeReplied((String) JSONPath.eval(comments,"$.comments["+i+"].beReplied[0].content"));
                    res.add(comment);
                }
            }
            try {
                Thread.sleep(1500);
                total++;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for(int i=0 ;i<res.size();i++){
            System.out.println(res.get(i));
        }

    }
}
