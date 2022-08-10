package process;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

public class Music163process implements PageProcessor {
    private Site site = Site.me()
            .setRetryTimes(30)
            .setCharset("utf-8")
            .setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_2) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31");

    @Override
    public void process(Page page) {
        System.out.println("执行process");
    }

    @Override
    public Site getSite() {
        return site;
    }


}
