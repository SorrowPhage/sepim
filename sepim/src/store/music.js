import axios from "axios";

export default {
    namespaced: true,
    actions: {
        getMusicList(context) {
            axios.post("http://localhost:8080/api/music/list").then(res=>{
                if (res.data.flag === "music_get_list_success") {
                    context.commit("GET_MUSICLIST",res.data.data);
                }
            })
        },
        getMusicListByMusicListId(context,value) {
            axios.post("http://localhost:8080/api/list/only", {id: value}).then(res => {
                context.commit("GET_MUSICLIST",res.data.data);
            },Error=>{
                console.log(this.$route.query.id);
            });
        },
        getMusicLrc(context,value) {
            axios.post("http://localhost:8080/api/music/lrc",{id:value}).then(res=>{
                // console.log(res.data.data);
                if (res.data.flag === "400") {
                    context.commit("GET_MUSICLRC", {"00:00": "无歌词"});
                } else {
                    let lrc = res.data.data;
                    let Obj = {};
                    let reg = /\[(.*?)](.*)/g;
                    lrc.replace(reg, (a, b, c) => {
                        b = b.slice(0, 5) + "";
                        Obj[b] = c
                    });
                    console.log(Obj);
                    context.commit("GET_MUSICLRC", Obj);
                }
            })
        },
    },
    mutations: {
        GET_MUSICLIST(state, value) {
            state.musicList = value;
        },
        GET_MUSICLRC(state,value) {
            state.lrc = value;
        },
        getMusicSrc(state,url) {
            state.musicSrc = url;
        },
        getAudio(state,audio) {
            state.audio = audio;
        },
        changePlayAndPauseButtonState(state, value) {
            state.isPlay = value;
        },
        changePlay(state, music) {
            state.singer = music.singer;
            state.musicSrc = music.musicSrc;
            state.musicName = music.musicName;
            state.pictureUrl = music.pictureUrl;
            // state.lrc = music.lrc
            state.id = music.id;
        },
    },
    state: {
        'musicList': [],
        'musicSrc': 'http://localhost:8088/upload/sepim/music/phage.mp3',
        'audio': {},
        'musicName': 'Misty Mystery',
        'singer': 'GARNET CROW',
        'pictureUrl': 'http://localhost:8088/upload/sepim/music/picture/2882585126.jpg',
        'isPlay': false,
        'lrc': '',
        'id': '1',
    },
    getters: {
        musicSrc(state) {
            if (state.musicSrc===''||state.musicSrc === null) return "http://localhost:8088/upload/sepim/music/phage.mp3"
            else return state.musicSrc
        },
    }
}