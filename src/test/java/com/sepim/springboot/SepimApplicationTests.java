package com.sepim.springboot;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import com.alibaba.fastjson.JSONObject;
import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;
import com.sepim.springboot.controller.WebSocket;
import com.sepim.springboot.entity.*;
import com.sepim.springboot.entity.example.TestGeneratorExample;
import com.sepim.springboot.mapper.ChatMessageMapper;
import com.sepim.springboot.mapper.MusicListMapper;
import com.sepim.springboot.mapper.MusicMapper;
import com.sepim.springboot.mapper.TestGeneratorMapper;
import com.sepim.springboot.service.*;
import com.sepim.springboot.utils.*;
import lombok.extern.slf4j.Slf4j;
import net.dreamlu.mica.ip2region.core.Ip2regionSearcher;
import org.junit.jupiter.api.Test;
import org.omg.CORBA.UNKNOWN;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
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

    @Autowired
    private  TransferPythonService transferPythonService;

    @Autowired
    private CzpUserService czpUserService;

    @Autowired
    private CzpTribeService czpTribeService;

    @Test
    void testCzp() {
        // Map<String, String> param = new HashMap<>();
        // param.put("smallGroup", "5");
        // System.out.println(czpUserService.relationChatAll(param));

        List<CzpTribe> list = new ArrayList<>();
        CzpTribe czpTribe = new CzpTribe();
        czpTribe.setTribeArea("luba");
        czpTribe.setTribeName("");
        czpTribe.setCreateTime("");
        list.add(czpTribe);

        CzpTribe czpTribe1 = new CzpTribe();
        czpTribe1.setTribeArea("erli");
        czpTribe1.setTribeName("erli");
        czpTribe1.setCreateTime("1930");
        list.add(czpTribe1);

        czpTribeService.deleteData(list); //能全部删除
        // czpTribeService.deleteDataSimple(list); //有空值的无法删除



    }

    @Test
    void testQrCode() throws IOException, WriterException, NotFoundException {
        String content = "this first QrCode";
        QrCodeUtils.encode(content, null, "D:\\A-SpringBoot\\sepim\\qrcode\\test.jpg", false);
        String decode = QrCodeUtils.decode("D:\\A-SpringBoot\\sepim\\qrcode\\test.jpg");
        System.out.println(decode);
    }


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
        System.out.println("xxx" + s);
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
        String a = " {\"fromId\":\"user\",\"toId\":\"sp2645354\",\"content\":\"bb\"} ";
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
    void test() {
        // redisUtil.set("user","myValue");
        // System.out.println(redisUtil.get("user"));  //myValue
        System.out.println(redisUtil.ping());
    }

    @Autowired
    private TestGeneratorMapper testGeneratorMapper;

    @Test
    void generatorTest() {
        // TestGenerator testGenerator = new TestGenerator();
        // testGenerator.setId(1);
        // testGenerator.setName("测试");

        //针对单表进行查询的类
        // TestGeneratorExample testGeneratorExample = new TestGeneratorExample();
        //让name升序
        // testGeneratorExample.setOrderByClause("name asc");
        //选择不重复的记录
        // testGeneratorExample.setDistinct(true);

        //定义sql语句where后的查询条件
        // TestGeneratorExample.Criteria criteria = testGeneratorExample.createCriteria();
        // testGeneratorMapper.insert(testGenerator);
        // criteria.andNameEqualTo(testGenerator.getName());
        // criteria.andNameEqualTo("haha");
        // System.out.println(testGeneratorMapper.countByExample(testGeneratorExample));

        // testGeneratorMapper.updateByPrimaryKeySelective(testGenerator);
        // testGeneratorMapper.updateByPrimaryKey(testGenerator);
        // testGeneratorMapper.updateByExample(testGenerator, testGeneratorExample);

        // List<TestGenerator> list = testGeneratorMapper.selectByExample(testGeneratorExample);
        // for (TestGenerator generator : list) {
        //     System.out.println(generator);
        // }
        // list.stream().map(x -> {
        //     if ("测试".equals(x.getName())) {
        //         x.setName(x.getName() + "x");
        //     }
        //     return x;
        // }).filter(x -> x.getId() < 2).forEach(System.out::println);

        TestGeneratorExample example = new TestGeneratorExample();
        TestGeneratorExample.Criteria criteria = example.createCriteria();
        criteria.andNameEqualTo("测试");
        List<TestGenerator> list = testGeneratorMapper.selectByExample(example);
        for (TestGenerator testGenerator : list) {
            log.info(testGenerator.toString());
        }

    }

    @Test
    void testHutoolsDataUtil() {
        System.out.println(DateUtil.format(LocalDateTimeUtil.now(), "yyyy-MM-dd"));
    }



    @Test
    void pythonTest() {
        // System.out.println("==================");
        // transferPythonService.recommendationFolders("1");
        WebSocket webSocket = new WebSocket();
        webSocket.OnMessage("haha");
    }

    @Autowired
    private Ip2regionSearcher ip2regionSearcher;

    @Test
    void getLocation() {

        String ip = null;
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        log.info(ip);
        log.info("========================");
        String address = ip2regionSearcher.getAddress(ip);
        log.info(address);
    }

}