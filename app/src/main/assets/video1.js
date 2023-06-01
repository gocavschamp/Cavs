
var isPlayOnWeb=false;

var interval = setInterval(function videoTagFinder() {

var videoTags = document.getElementsByTagName("video");

for (var index =0; index < videoTags.length; index++) {

var videoTag = videoTags[index];

if (videoTag.getAttribute("marked") =="1") {

continue;

}

videoTag.addEventListener("webkitbeginfullscreen", onEnterFullScreenEvent,true);

videoTag.addEventListener("webkitendfullscreen", onExitFullScreenEvent,true);

videoTag.addEventListener("play", onVideoPlayEvent,true);

videoTag.addEventListener("pause", onVideoPauseEvent,true);

videoTag.setAttribute("position",index)

videoTag.setAttribute("marked","1");

}

function onEnterFullScreenEvent() {

}

function onExitFullScreenEvent() {

}

function onVideoPlayEvent() {

if(!isPlayOnWeb){

checkIsNeedUsePlayerOfApp(this)

}

}

function onVideoPauseEvent() {

}

function checkIsNeedUsePlayerOfApp(videoTag) {

var i = setInterval(function () {
     notifyAppPlayerToPlay(videoTag);

          videoTag.pause();

clearInterval(i)

        },200)

}

/**

* notify app to play video

* @param videoTag

*/

    function notifyAppPlayerToPlay(videoTag) {

var videoSrc = videoTag.currentSrc;

var sourceArr =new Array();

var sources = videoTag.getElementsByTagName("source");

if (sources !=null && sources.length >0) {

for (var index =0; index < sources.length; index++) {

var sourceInfo = {

'url': sources[index].getAttribute("src"),

'media': sources[index].getAttribute("media"),

'type': sources[index].getAttribute("type")

};

sourceArr.push(sourceInfo);

}

}

var videoInfo = {

'videoUrl': videoSrc,

'source': sourceArr,

'title': document.title,

'url': document.URL,

'position':videoTag.getAttribute('position')

};

// 调用native方法

window.JSToNative.webViewPlayVideoAtURL(JSON.stringify(videoInfo));

}

},300)

function setPlayOnWeb(){

isPlayOnWeb=true;

}

!function(){
    var iframeTags = document.getElementsByTagName("iframe");
    var ifmParent;
    var parentHeight = 250;
    for (var index = 0; index < iframeTags.length; index++) {
        var iframeTag = iframeTags[index];
        if(iframeTag.offsetHeight >500){
            canPlay = false;
            break;
        }
        if(iframeTag.src != "" && iframeTag.offsetHeight>150){
            var url = window.location.href;
            ifmParent = iframeTag.parentNode;
            if(url.search('http://www.haitum.cn') != -1){
                ifmParent = iframeTag.parentNode.parentNode;
            }
            if(ifmParent.style.paddingTop){
                ifmParent.style.paddingTop="0%";
            }
            parentHeight = iframeTag.offsetHeight
            iframeTag.remove();
            break;
        }
    }
    ifmParent.innerHTML = "<div id='player_area' style='background:#000; position:relative;width:100%; height:"+parentHeight+"px;'><img id='playimg_sg_webvideo' src='http://xxxx.webvideo.play' height='100px' width='100px' style='height:100px; width:100px; position:absolute;top:50%; left:50%;transform: translate(-50%,-50%); '/> </div>";
      var playIcon = document.getElementById("playimg_sg_webvideo");
      playIcon.onclick = function () {
          window.JSToNative.iFramePlayVideo();
      }
}()
