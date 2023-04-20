package com.sepim.springboot.controller;

import com.sepim.springboot.entity.Music;
import com.sepim.springboot.entity.MusicList;
import com.sepim.springboot.entity.ResultData;
import com.sepim.springboot.service.MusicListService;
import com.sepim.springboot.service.MusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author: SorrowPhage
 * @date: 2023/2/21
 */
@RestController
public class MusicController {
    @Autowired
    private MusicService musicService;

    @Autowired
    private ResultData resultData;

    @Autowired
    private MusicListService musicListService;

    @PostMapping("/music/upload")
    public ResultData uploadMusic(@RequestBody Music music) {
        // resultData = musicService.saveMusic(file);
        System.out.println("test");
        resultData.setData(music);
        return resultData;
    }


    @PostMapping("/music/list")
    public ResultData getMusicList() {
        resultData = musicService.getMusicList();
        return resultData;
    }

    @PostMapping("/mlist/get")
    public ResultData getUserMusicList(@RequestBody MusicList musicList) {
        resultData = musicListService.getUserMusicList(musicList.getOwner());
        return resultData;
    }

    @PostMapping("/list/update")
    public ResultData updateMusicListInformation(@RequestParam(value = "file") MultipartFile file,
                                                 @RequestParam(value = "id") String id,
                                                 @RequestParam(value = "name") String name,
                                                 @RequestParam(value = "describe") String describe) {
        resultData = musicListService.updateMusicListInformation(file, id, name, describe);
        return resultData;
    }

    @PostMapping("/list/only")
    public ResultData getMusicListByMusicListId(@RequestBody Music music) {
        resultData = musicService.getMusicListByMusicListId(music.getId());
        return resultData;
    }

    @PostMapping("/music/lrc")
    public ResultData getMusicLrc(@RequestBody Music music) {
        resultData = musicService.getMusicLrc(music.getId());
        return resultData;
    }

    @PostMapping("/list/name")
    public ResultData getMusicListName(@RequestBody Music music) {
        resultData = musicListService.getMusicListName(music.getId());
        return resultData;
    }
}

