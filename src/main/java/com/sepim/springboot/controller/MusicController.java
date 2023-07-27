package com.sepim.springboot.controller;

import com.sepim.springboot.entity.Music;
import com.sepim.springboot.entity.MusicList;
import com.sepim.springboot.entity.ResultData;
import com.sepim.springboot.service.MusicListService;
import com.sepim.springboot.service.MusicService;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class MusicController {

    private final MusicService musicService;


    private final MusicListService musicListService;

    @PostMapping("/music/upload")
    public ResultData uploadMusic(@RequestBody Music music) {
        ResultData resultData = new ResultData();
        resultData.setData(music);
        return resultData;
    }


    @PostMapping("/music/list")
    public ResultData getMusicList() {
        return musicService.getMusicList();
    }

    @PostMapping("/mlist/get")
    public ResultData getUserMusicList(@RequestBody MusicList musicList) {
        return musicListService.getUserMusicList(musicList.getOwner());
    }

    @PostMapping("/list/update")
    public ResultData updateMusicListInformation(@RequestParam(value = "file") MultipartFile file,
                                                 @RequestParam(value = "id") String id,
                                                 @RequestParam(value = "name") String name,
                                                 @RequestParam(value = "describe") String describe) {
        return musicListService.updateMusicListInformation(file, id, name, describe);
    }

    @PostMapping("/list/only")
    public ResultData getMusicListByMusicListId(@RequestBody Music music) {
        return musicService.getMusicListByMusicListId(music.getId());
    }

    @PostMapping("/music/lrc")
    public ResultData getMusicLrc(@RequestBody Music music) {
        return musicService.getMusicLrc(music.getId());
    }

    @PostMapping("/list/name")
    public ResultData getMusicListName(@RequestBody Music music) {
        return musicListService.getMusicListName(music.getId());
    }
}

