package cn.popo.news.common.utils;


import org.apdplat.word.analysis.CosineTextSimilarity;
import org.apdplat.word.analysis.TextSimilarity;

public class WordParticipleUtil {
    public static double similarityArticle(String content1, String content2) {

        TextSimilarity textSimilarity = new CosineTextSimilarity();
        double score1pk1 = textSimilarity.similarScore(content1, content2);
        return score1pk1;
    }

    /*public static void main(String[] args) {
        String s = "hpmcnmhhh<p>1950年朝鲜战争爆发，朝鲜战争是继第二次世界大战以来规模最大的一次局部战争，也是继二战以来最多国家参战的一场局部战争，" +
                "这场战争以中国人民志愿军的胜利而告终，以美国为首的“联合国军”被中国人民志愿军打回谈判桌上，并且战线稳定在三八线，美军司令亲口承认" +
                "：“我们输掉了这场战争”。</p><p></p><div><img src=\"https://p0.ssl.qhimgs4.com/t016245d3c0d66c2340.webp\"></div><p>朝鲜战" +
                "争从1950年6月开始，一直到1953年7月结束，整整持续了3年之久，在这3年的时间里面，中美双方在不到20万平方公里的朝鲜半岛上投入了超过300万" +
                "的军队进行战争，其中以美国为首的“联合国军”军队总数为120万，中朝联军人数为188万，其中中国人民志愿军140万。这场持续3年之久的战争结局大家" +
                "应该都知道了，但是对于战争双方的伤亡人数究竟是多少很少人知道，因为双方公布的伤亡人数悬殊很大。</p><p></p><div><img src=\"https://p0" +
                ".ssl.qhimgs4.com/t01b118355e0b1a7d4b.web</div><p>朝鲜战争结束之后，美国宣布“歼灭”中国人民志愿军达到96万人，如果把朝鲜军队算" +
                "进去的话，那么伤亡的总人数达到140万-150万，与之形成鲜明对比的是“联合国军”的伤亡，只有区区46万，其中美军更少，只有14万，韩军则有30万人伤亡" +
                "，其他盟军1万多人伤亡。中方在朝鲜战争结束之后统计总共伤亡52.5万，其中牺牲11.8万，负伤38万，失踪2.5万，在加上伤重不治的有3.5万，志愿军在朝" +
                "鲜战争总共牺牲了15万人，但近些年有权威发布中国人民志愿军在朝鲜战争的牺牲人数为18.3万人，这可能是最权威的数字了。</p><p></p><div><img src=\"" +
                "https://p0.ssl.qhimgs4.com/t01fa09eb837ea5a81f.webp\"></div><p>志愿军宣布歼";

        String s1 = "150万7月结束，整整持续了3年之久，在这3年的时间里面，中美双方在不到20万平方公里的朝鲜半岛上投入了超过300万\" +\n" +
                "                \"的军队进行战争，其中以美国为首的“联合国军”军队总数为120万，中朝联军人数为188万，其中中国人民志愿军140万。这场持续3年之久的战争结局大家\" +\n" +
                "                \"应该都知道了，但是对于战争双方的伤亡人数究竟是多少很少人知道，因为双方公布的伤亡人数悬殊很大。</p><p></p><div><img src=\\\"https://p0\" +\n" +
                "                \".ssl.qhimgs4.com/t01b118355e0b1a7d4b.web</div><p>朝鲜战争结束之后，美国宣布“歼灭”中国人民志愿军达到96万人，如果把朝鲜军队算\" +\n" +
                "                \"进去的话，那么伤亡的总人数达到140万-150万，与，";

        TextSimilarity textSimilarity = new CosineTextSimilarity();
        double score1pk2 = textSimilarity.similarScore(s, s1);
        System.out.println("ai: " + " 的相似度分值：" + score1pk2);
    }*/
}
