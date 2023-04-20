<template>
    <div ref="search_video" class="center-search-container">
        <div class="center-search__bar" :class="{'center-search-bar-focus':isFocus===true}">
            <!--搜索-->
            <div class="nav-search-box" :class="{'nav-search-box-focus':isFocus===true}">
                <div class="nav-search-content">
                    <input v-model="search" class="nav-search-input" placeholder="Search" type="text" @focus="searchFocus"/>
                    <div class="nav-search-clean el-icon-error"></div>
                </div>
                <div class="nav-search-btn">
                    <i class="el-icon-search"></i>
                </div>
            </div>
            <!--历史与热门-->
            <div class="search-panel" v-show="isFocus">
                <div class="history">
                    <div class="header">
                        <div class="title">搜索历史</div>
                        <div class="clear">清空</div>
                    </div>
                    <div class="histories-wrap">
                        <div class="histories">
                            <div class="history-item ">
                                <div class="history-text">视频播放器制作</div>
                                <div class="close"></div>
                            </div>
                        </div>
                    </div>
                    <div class="history-fold-wrap">
                        <div class="fold-text">
                            展开更多
                        </div>
                    </div>
                </div>
                <div class="trending">
                    <div class="header">
                        <div class="title">热搜</div>
                    </div>
                    <div class="trendings-double">
                        <div class="trendings-col">
                            <div class="trending-item">
                                <div class="trendings-rank">1</div>
                                <div class="trending-text">sorrowphage</div>
                            </div>
                        </div>
                        <div class="trendings-col">
                            <div class="trending-item">
                                <div class="trendings-rank">2</div>
                                <div class="trending-text">sorrowphage</div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    name: "VideoSearchBar",
    data() {
        return{
            search: '',
            isFocus: false,
            
        };
    },
    methods: {
        searchFocus() {
            this.isFocus = true;
        },
        searchPanel(e) {
            if (!(this.$refs.search_video.contains(e.target) || this.$refs.search_video.contains(e.target))) this.isFocus = false;
        },
    },
    mounted() {
        document.addEventListener('click', this.searchPanel);
    },
    destroyed() {
        document.removeEventListener('click', this.searchPanel);
    }
}
</script>

<style scoped>
.center-search-container {
    flex: 1 auto;
    height: 38px;
    
}
.center-search__bar {
    position: relative;
    margin: 0 auto;
    min-width: 181px;
    max-width: 500px;
}
.center-search-bar-focus{
    box-shadow: 0 0 30px rgb(0 0 0 / 10%);
    border-radius: 8px;
}

.nav-search-box {
    display: flex;
    align-items: center;
    padding: 0 48px 0 4px;
    position: relative;
    z-index: 1;
    overflow: hidden;
    line-height: 38px;
    height: 40px;
    opacity: .9;
    transition: background-color .3s;
    background: #ffffff;
    border-radius: 8px;
}
.nav-search-box-focus {
    border-radius: 8px 8px 0 0;
    background: #ffffff;
}
.nav-search-content {
    display: flex;
    align-items: center;
    justify-content: space-between;
    position: relative;
    padding: 0 8px;
    width: 100%;
    height: 32px;
    border: 2px solid transparent;
    border-radius: 6px;
    background-color: #e3e5e7;
}
.nav-search-btn {
    z-index: 1;
    position: absolute;
    top: 3px;
    right: 7px;
    display: flex;
    align-items: center;
    justify-content: center;
    margin: 0;
    padding: 0;
    width: 32px;
    height: 32px;
    border: none;
    border-radius: 6px;
    color: #18191c;
    line-height: 32px;
    cursor: pointer;
    transition: background-color .3s;
}
.nav-search-btn:hover{
    background: #e3e5e7;
}
.nav-search-input {
    /*background: #e3e5e7;*/
    flex: 1;
    overflow: hidden;
    padding-right: 8px;
    border: none;
    outline: none;
    background-color: transparent;
    box-shadow: none;
    color: #61666d;
    font-size: 14px;
    line-height: 20px;
}

.nav-search-clean {
    width: 16px;
    height: 16px;
    right: 10px;
    cursor: pointer;
    /*visibility: hidden;*/
}
.search-panel {
    width: 100%;
    max-height: 612px;
    overflow-y: auto;
    background:#ffffff;
    /*border: 1px solid #e3e5e7;*/
    border-top: none;
    border-radius: 0 0 8px 8px;
    padding: 13px 0 16px;
    -webkit-font-smoothing: antialiased;
}
.header {
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding: 0 16px;
}
.title {
    height: 24px;
    font-size: 16px;
    line-height: 24px;
}
.clear {
    font-size: 12px;
    line-height: 15px;
    height: 15px;
    color: #9499a0;
    cursor: pointer;
}
.histories-wrap {
    padding: 0 16px;
    overflow: hidden;
    max-height: 172px;
}
.history{
    max-width: 498px;
}
.histories {
    display: flex;
    flex-wrap: wrap;
    margin-top: 12px;
    margin-right: -10px;
    margin-bottom: 4px;
}
.history-item {
    position: relative;
    box-sizing: border-box;
    height: 30px;
    padding: 7px 10px 8px;
    font-size: 12px;
    line-height: 15px;
    background:#f6f7f8;
    border-radius: 4px;
    margin-right: 10px;
    margin-bottom: 10px;
    cursor: pointer;
}
.history-text {
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
    max-width: 96px;
}
.close {
    display: none;
    box-sizing: border-box;
    position: absolute;
    width: 16px;
    height: 16px;
    top: -6px;
    right: -6px;
    padding: 2px;
}
.history-fold-wrap {
    display: flex;
    align-items: center;
    justify-content: center;
    width: 64px;
    margin: 0 auto 14px;
}
.fold-text {
    font-size: 12px;
    line-height: 15px;
    height: 15px;
    color: #9499a0;
    cursor: pointer;
}
.trending{
    max-width: 498px;
}
.trendings-double {
    display: flex;
}

.trendings-col {
    flex: 1;
}
.trendings-col:first-child {
    margin-right: 10px;
}
.trending-item {
    box-sizing: border-box;
    height: 38px;
    display: flex;
    align-items: center;
    cursor: pointer;
    padding-left: 16px;
}
.trendings-rank {
    width: 15px;
    min-width: 15px;
    height: 17px;
    line-height: 17px;
    text-align: center;
    font-size: 14px;
    margin-right: 7px;
    color: #9499a0;
}
.trending-text {
    font-size: 14px;
    line-height: 17px;
    height: 17px;
    margin-right: 6px;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
    letter-spacing: 0;
}
.trending-item:hover{
    background: rgba(148, 153, 160, 0.7);
}
</style>