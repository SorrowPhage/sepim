package com.sepim.springboot;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sepim.springboot.entity.*;
import com.sepim.springboot.mapper.ChatMessageMapper;
import com.sepim.springboot.mapper.MusicListMapper;
import com.sepim.springboot.mapper.MusicMapper;
import com.sepim.springboot.service.*;
import com.sepim.springboot.utils.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.util.List;

@SpringBootTest( webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT )
class SepimApplicationTests {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserService userService;

    @Autowired
    private MusicListMapper mapper;

    @Autowired
    private MusicMapper musicMapper;

    @Autowired
    private MusicListService musicListService;

    @Autowired
    private ChatMessageMapper chatMessageMapper;

    @Autowired
    private ChatMessageService chatMessageService;

    @Test
    void setChatMessageMapperTest() {
        // List<ChatMessage> chatMessages = chatMessageMapper.getChatMessage("user", "sp2645354");
        // for (ChatMessage message : chatMessages) {
        //     System.out.println(message);
        // }
        //
        // ChatMessage chatMessage = new ChatMessage();
        // chatMessage.setFromId("user");
        // chatMessage.setToId("sp2645354");
        // // chatMessage.setSendTime(new Timestamp(System.currentTimeMillis()));
        // chatMessage.setContent("这是测试");
        // chatMessage.setIsLatest(1);
        // chatMessage.setType(0);
        // int i = chatMessageMapper.addChatMessage(chatMessage);
        // System.out.println(i);

        // int i = chatMessageMapper.updateIsLatest("user", "sp2645354");
        int s = chatMessageMapper.getNoReadChatMessage("sp2645354");
        System.out.println("xxx"+s);
    }

    @Test
    void test2() {
        System.out.println(musicListService.getUserMusicList("user"));
    }

    @Test
    void testChat() {
        ResultData user = chatMessageService.getChatList("user");
        System.out.println(user.getData());
    }

    @Test
    void getMusicList() {

        // List<MusicList> musicLists = new ArrayList<>();
        // musicLists = mapper.getUserMusicList("user");
        // System.out.println(musicLists);
        MusicList musicList = new MusicList();
        musicList.setId("1");
        musicList.setName("Test");
        musicList.setDescribe("这是测试");
        mapper.updateMusicListInformation(musicList);
    }

    @Test
    void testJson() {
        String a= " {\"fromId\":\"user\",\"toId\":\"sp2645354\",\"content\":\"bb\"} ";
        // ChatMessage chatMessage = JSON.parseObject(a, ChatMessage.class);
        // System.out.println(chatMessage);
        JSONObject jsonObject = JSONObject.parseObject(a);
        System.out.println(jsonObject.getString("toId"));
    }

    @Test
    void getMusicLIstByMusicListId() {
        // List<Music> musicListByMusicListId = musicMapper.getMusicListByMusicListId("1");
        // System.out.println(musicListByMusicListId);
        System.out.println(musicMapper.getMusicById("4"));
    }

    @Test
    void contextLoads() {
        Email email = new Email();
        email.setTos("2250270287@qq.com");
        email.setSubject("注册");
        email.setContent("验证码：" + VerCodeGenerateUtil.generateVerCode());
        System.out.println(email.getTos());
        System.out.println(email.getContent());
        System.out.println(emailService);
        // emailService.send(email);
    }

    @Test
    void testRegister() {
        User user = new User();
        user.setId(AccountGenerateUtil.generateAccount());
        user.setUsername("sorrow");
        user.setPassword("12346");
        // user.setType("user");
        userService.save(user);
    }

    @Test
    void fareTest() {
        // System.out.println(FareUtils.checkFace());
    }

    // @Autowired
    // private MySessionUtil mySessionService;
    //
    // @Test
    // void testSession() {
    //     User user = new User();
    //     mySessionService.setSession("key", user);
    //     System.out.println(mySessionService.getSession("key"));
    // }

    // @Autowired
    // private eHolidayService leaveService;
    //
    // @Test
    // void testleave() {
    //     Holiday leave = new Holiday();
    //     leave.setId("1529078451565883393");
    //     leave.setUserId("sp0078127");
    //     leave.setUsername("HelloWord");
    //     leave.setReason("病假");
    //     leave.setLeaveTime("2022-05-24 20:22:23");
    //     leave.setReleaseTime("2022-05-25 00:00:00");
    //     leave.setState("待审核");
    //     // System.out.println(leave);
    //     leaveService.leave(leave);
    // }

    @Test
    void testLeaves() {
        // List<Holiday> list = (List<Holiday>) leaveService.leaveList("sp0078127").getData();
        // System.out.println(list.size());
    }

    // @Autowired
    // private TokenService tokenService;
    // @Autowired
    // private InterceptService interceptService;
    // @Test
    // void testToken() {
    //     User user = new User();
    //     user.setId("admin");
    //     user.setPassword("123456");
    //     String token = tokenService.getToken(user);
    //     System.out.println(interceptService.verToken(token));
    // }

    @Test
    void testReadMd() {
        String mdFile = FileUploadUtil.readMdFile("E:\\ProgrammingSoftware\\apache-tomcat-10.0.12\\webapps\\upload\\sepim\\md\\file\\2dd6fad661cf4ee7855378d34b2f6b02Test.md");
        System.out.println(mdFile);
    }

    @Autowired
    private CommentService commentService;

    @Test
    void testComment() {
        List<Comment> comments = commentService.getComments("1534717945514106882");
        comments.forEach(System.out::println);
    }

    @Autowired
    private RedisUtil redisUtil;

    @Test
    void test(){
        // redisUtil.set("user","myValue");
        // System.out.println(redisUtil.get("user"));  //myValue
        System.out.println(redisUtil.ping());
    }
}