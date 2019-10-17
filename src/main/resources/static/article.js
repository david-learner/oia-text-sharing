var article;
var sequence;

window.onload = function () {
    // 메인블록 추가/제거 버튼에 온클릭 메소드 붙이기
    var addMainBlockBtns = document.querySelectorAll("[name=add-main-block-btn]");
    var removeMainBlockBtns = document.querySelectorAll("[name=remove-main-block-btn]");
    addOnClickToBlockBtns(addMainBlockBtns, addMainBlock);
    addOnClickToBlockBtns(removeMainBlockBtns, removeMainBlock);

    // 서브블록 추가/제거 버튼에 온클릭 메소드 붙이기
    var addSubBlockBtns = document.querySelectorAll("[name=add-sub-block-btn]");
    var removeSubBlockBtns = document.querySelectorAll("[name=remove-sub-block-btn]");
    addOnClickToBlockBtns(addSubBlockBtns, addSubBlock);
    addOnClickToBlockBtns(removeSubBlockBtns, removeSubBlock);

    // path(/articles/id 또는 /articles/id/share)를 전달해서 article data 요청
    getArticle(window.location.pathname, fitToContent);
};

function fitToContent() {
    var subBlockContentElements = document.querySelectorAll("[name=sub-block-content]");
    subBlockContentElements.forEach((function (element) {
        element.style.cssText = "height:" + element.scrollHeight + "px"
    }));
}

function addMainBlock(event) {
    // 외부문서에서 노드를 복사해온다 deep이 true면 자식노드까지 모두 복사해온다
    var mainBlockElement = document.importNode(document.getElementById("main-block-template").content, true);
    var observationElement = document.importNode(document.getElementById("sub-block-observation-template").content, true);
    var interpretationElement = document.importNode(document.getElementById("sub-block-interpretation-template").content, true);
    var applicationElement = document.importNode(document.getElementById("sub-block-application-template").content, true);

    // 각 서브블록에 seq-id를 할당
    var observationSubBlock = observationElement.querySelector("[name=sub-block]");
    observationSubBlock.dataset.blockSeqId = getSequence();
    var interpretationSubBlock = interpretationElement.querySelector("[name=sub-block]");
    interpretationSubBlock.dataset.blockSeqId = getSequence();
    var applicationSubBlock = applicationElement.querySelector("[name=sub-block]");
    applicationSubBlock.dataset.blockSeqId = getSequence();
    // 서브블록 앞뒤 seq-id 할당
    observationSubBlock.dataset.prevBlockSeqId = '0';
    observationSubBlock.dataset.nextBlockSeqId = interpretationSubBlock.dataset.blockSeqId;
    interpretationSubBlock.dataset.prevBlockSeqId = observationSubBlock.dataset.blockSeqId;
    interpretationSubBlock.dataset.nextBlockSeqId = applicationSubBlock.dataset.blockSeqId;
    applicationSubBlock.dataset.prevBlockSeqId = interpretationSubBlock.dataset.blockSeqId;
    applicationSubBlock.dataset.nextBlockSeqId = '0';

    // 메인블록 앞뒤 seq-id를 할당
    var clickedMainBlock = event.target.closest("[name=main-block]");
    var mainBlock = mainBlockElement.querySelector("[name=main-block]");
    mainBlock.dataset.blockSeqId = getSequence();
    clickedMainBlock.dataset.nextBlockSeqId = mainBlock.dataset.blockSeqId;
    // prevBlockSeqId
    mainBlock.dataset.prevBlockSeqId = clickedMainBlock.dataset.blockSeqId;
    // nextBlockSeqId
    var nextMainBlock = event.target.closest(".main-block").nextElementSibling;
    if (nextMainBlock != null) {
        mainBlock.dataset.nextBlockSeqId = nextMainBlock.dataset.blockSeqId;
        // nextBlock의 prevBlockSeqId 수정
        nextMainBlock.dataset.prevBlockSeqId = mainBlock.dataset.blockSeqId;
    }

    // pages할당 : 왜 여기서 할당해도 rendering될 때는 값이 세팅되지 않는지 모르겠다
    // mainBlock.querySelector("[name=end-page]").value = 1; // 이 구문은 동작하지 않는다
    mainBlock.querySelector("[name=start-page]").setAttribute('value', '1');
    mainBlock.querySelector("[name=end-page]").setAttribute('value', '1');

    // 서브블록들을 메인블록 아래에 붙인다
    mainBlock.append(observationElement);
    mainBlock.append(interpretationElement);
    mainBlock.append(applicationElement);

    // 왜 append 한 다음에는 dataset에 접근할 수 없는가?
    // observationElement.querySelector("[name=sub-block]").dataset.blockSeqId = getSequence();

    // node는 innerHTML 속성을 가지지 않으므로 임의로 부모 element를 만들고 이를 사용하여 innerHTML을 사용한다
    var tempParentForNode = document.createElement("div");
    tempParentForNode.appendChild(mainBlockElement);

    // 클릭된 플러스 버튼이 속한 메인블록 아래에 메인블록이 추가된다
    clickedMainBlock.insertAdjacentHTML('afterend', tempParentForNode.innerHTML);

    // 새롭게 생성된 메인블록의 메인블록 생성/제거 버튼에 onClick 메소드 붙이기
    var createdMainBlock = clickedMainBlock.nextElementSibling;
    addOnClickToBlockBtns(createdMainBlock.querySelectorAll("[name=add-main-block-btn]"), addMainBlock);
    addOnClickToBlockBtns(createdMainBlock.querySelectorAll("[name=remove-main-block-btn]"), removeMainBlock);

    // 새롭게 생성된 메인블록의 서브블록 생성/제거 버튼에 onClick 메소드 붙이기
    addOnClickToBlockBtns(createdMainBlock.querySelectorAll("[name=add-sub-block-btn]"), addSubBlock);
    addOnClickToBlockBtns(createdMainBlock.querySelectorAll("[name=remove-sub-block-btn]"), removeSubBlock);

    // 생성된 서브블록에 keydown 이벤트 붙이기
    createdMainBlock.querySelectorAll("[name=sub-block-content]").forEach(function (value) {
        value.addEventListener("keydown", expandTextArea);
    });
}

function removeMainBlock(event) {
    var mainBlocks = document.querySelectorAll("[name=main-block]");

    if (mainBlocks.length === 1) {
        alert("메인블록이 1개일 때는 삭제할 수 없습니다");
        return 0;
    }

    var clickedMainBlock = event.target.closest("[name=main-block]");
    resetBlockSeqId(clickedMainBlock);

    clickedMainBlock.classList.remove("show");
    setInterval(function () {
        clickedMainBlock.remove();
    }, 300)
}

function addSubBlock(event) {
    var subBlockTemplate;
    var clickedSubBlock = event.target.closest("[name=sub-block]");

    var clickedSubBlockName = clickedSubBlock.dataset.contentCategory;
    if (clickedSubBlockName === "observation") {
        subBlockTemplate = document.importNode(document.getElementById("sub-block-observation-template").content, true);
    }
    if (clickedSubBlockName === "interpretation") {
        subBlockTemplate = document.importNode(document.getElementById("sub-block-interpretation-template").content, true);
    }
    if (clickedSubBlockName === "application") {
        subBlockTemplate = document.importNode(document.getElementById("sub-block-application-template").content, true);
    }

    // 각 블록 seq-id 할당
    var createdSubBlock = subBlockTemplate.querySelector("[name=sub-block]");
    createdSubBlock.dataset.blockSeqId = getSequence();
    // todo 할당된 블록 앞뒤로 reset seq-id
    clickedSubBlock.dataset.nextBlockSeqId = createdSubBlock.dataset.blockSeqId;
    createdSubBlock.dataset.prevBlockSeqId = clickedSubBlock.dataset.blockSeqId;
    var nextSubBlock = clickedSubBlock.nextElementSibling;
    if (nextSubBlock == null) {
        createdSubBlock.dataset.nextBlockSeqId = '0';
    }
    if (nextSubBlock != null) {
        nextSubBlock.dataset.prevBlockSeqId = createdSubBlock.dataset.blockSeqId;
        createdSubBlock.dataset.nextBlockSeqId = nextSubBlock.dataset.blockSeqId;
    }

    // 임시 부모 element를 통해 innerHTML 사용
    var tempParentElementForNode = document.createElement("div");
    tempParentElementForNode.appendChild(subBlockTemplate);

    clickedSubBlock.insertAdjacentHTML("afterend", tempParentElementForNode.innerHTML);

    var createdSubBlock = clickedSubBlock.nextElementSibling;
    addOnClickToBlockBtns(createdSubBlock.querySelectorAll("[name=add-sub-block-btn]"), addSubBlock);
    addOnClickToBlockBtns(createdSubBlock.querySelectorAll("[name=remove-sub-block-btn]"), removeSubBlock);
    createdSubBlock.querySelector("[name=sub-block-content]").addEventListener("keydown", expandTextArea);
}

function removeSubBlock(event) {
    var clickedSubBlock = event.target.closest("[name=sub-block]");
    // mainBlock은 main-block-info라는 자식을 기본으로 가지고 있다. 따라서 항상 chidren.length는 1이상이다
    var mainBlockChildrenLength = event.target.closest("[name=main-block]").children.length;
    if (mainBlockChildrenLength <= 2) {
        removeMainBlock(event);
    }
    if (mainBlockChildrenLength > 2) {
        resetBlockSeqId(clickedSubBlock);
        clickedSubBlock.remove();
    }
}

function resetBlockSeqId(clickedBlock) {
    var prevBlock = clickedBlock.previousElementSibling;
    var nextBlock = clickedBlock.nextElementSibling;

    if (prevBlock == null) {
        nextBlock.dataset.prevBlockSeqId = clickedBlock.dataset.prevBlockSeqId;
    }
    if (nextBlock == null) {
        prevBlock.dataset.nextBlockSeqId = clickedBlock.dataset.nextBlockSeqId
    }

    if (prevBlock != null) {
        prevBlock.dataset.nextBlockSeqId = clickedBlock.dataset.nextBlockSeqId;
    }

    if (nextBlock != null) {
        nextBlock.dataset.prevBlockSeqId = clickedBlock.dataset.prevBlockSeqId;
    }
}

function addOnClickToBlockBtns(blockBtns, onClickMethod) {
    for (var index = 0; index < blockBtns.length; index++) {
        var blockBtn = blockBtns[index]
        blockBtn.onclick = onClickMethod;
    }
}

function share(callback) {
    // /articles/1/share
    var shareLinkRequestUrl = "/api" + window.location.pathname + "/share";
    $.ajax({
        type: 'POST',
        url: shareLinkRequestUrl,
    }).done(function (data) {
        var temp = document.createElement("input");
        // http://localhost:8080 + /articles/1/share?key=[encodedString]
        temp.value = window.location.origin + data;
        document.body.appendChild(temp);
        temp.select();
        if(document.execCommand("copy")) {
            document.body.removeChild(temp);
            alert("공유 주소가 복사되었습니다");
        }
    });
}

function save(callback) {
    // JSON에 들어갈 객체
    var token = window.location.pathname.split("/");
    var articleId = token[token.length - 1];
    var articleInfo = {
        title: document.querySelector("#title").value
    }
    var mainBlocks = [];
    var content = {
        sequence: sequence,
        mainBlocks: mainBlocks
    };
    var article = {
        id: articleId,
        articleInfo: articleInfo,
        content: content
    }

    // Element들을 찾아서 데이터 뽑아내서 객체로 만들기
    var articleContent = document.querySelector("#article-content");
    var mainBlocksElement = articleContent.querySelectorAll("[name=main-block]");

    for (var mainBlockIndex = 0; mainBlockIndex < mainBlocksElement.length; mainBlockIndex++) {
        var subBlocksElement = mainBlocksElement[mainBlockIndex].querySelectorAll("[name=sub-block]");

        var subBlocks = [];
        for (var subBlockIndex = 0; subBlockIndex < subBlocksElement.length; subBlockIndex++) {
            var subBlockContent = subBlocksElement[subBlockIndex].querySelector("[name=sub-block-content]").value;
            var subBlockSeqId = subBlocksElement[subBlockIndex].dataset.blockSeqId;
            var prevBlockSeqId = subBlocksElement[subBlockIndex].dataset.prevBlockSeqId;
            var nextBlockSeqId = subBlocksElement[subBlockIndex].dataset.nextBlockSeqId;
            // UpperCase로 바꿔준 이유는 BE에서 ContentCategory에 해당하는 Enum의 요소 이름이 UpperCase로 되어 있기 때문에.
            var subBlockContentCategory = subBlocksElement[subBlockIndex].dataset.contentCategory.toUpperCase();
            var subBlock = {
                sequenceId: subBlockSeqId,
                pointers: {
                    prevPointer: prevBlockSeqId,
                    nextPointer: nextBlockSeqId
                },
                category: subBlockContentCategory,
                content: subBlockContent
            }
            subBlocks.push(subBlock);
        }
        var mainBlock = {
            subBlocks: subBlocks
        };
        mainBlocks.push(mainBlock);
    }

    consoleLog(JSON.stringify(article));

    var apiSaveRequestUrl = window.location.origin + "/api" + window.location.pathname + "/save";
    $.ajax({
        type: 'POST',
        contentType: 'application/json',
        dataType: 'json',
        data: JSON.stringify(article),
        url: apiSaveRequestUrl,
    }).done(callback);
}

function getArticle(articlePath, callback) {
    $.ajax({
        type: 'GET',
        dataType: 'json',
        url: '/api' + articlePath,
    }).done(function (data) {
        ConvertJsonToArticle(data);
        callback();
    }).fail(function (jqXHR, textStatus, errorThrown) {
        alert(jqXHR.responseText);
    });
}

function ConvertJsonToArticle(jsonData) {
    var articleInfo = jsonData['articleInfo'];
    var writerName = articleInfo.writer['name'];
    var date = articleInfo.dateTime;
    var title = articleInfo.title;
    var normalDateTime = convertToYYMMDDHHmm(date);
    document.querySelector('#writer-name').innerHTML = writerName;
    document.querySelector('#date').innerHTML = normalDateTime;
    document.querySelector('#title').value = title;

    var content = jsonData['content'];
    var mainBlocks = content['mainBlocks'];
    sequence = content['sequence'];
    mainBlocks.forEach(createMainBlock);
}

function createMainBlock(mainBlock) {
    // #article-content 아래에 mainBlock들이 위치한다
    var articleContent = document.querySelector('#article-content');
    // template을 불러와 해당 html 정보를 node로 바꾼다
    var mainBlockTemplate = document.getElementById('main-block-template');
    // 외부문서에서 노드를 복사해온다 deep이 true면 자식노드까지 모두 복사해온다
    var mainBlockElement = document.importNode(mainBlockTemplate.content, true);
    // mainblock에 seq-id를 넣어준다
    mainBlockElement.querySelector("[name=main-block]").dataset.blockSeqId = mainBlock.sequenceId;

    addOnClickToBlockBtns(mainBlockElement.querySelectorAll("[name=add-main-block-btn]"), addMainBlock);
    addOnClickToBlockBtns(mainBlockElement.querySelectorAll("[name=remove-main-block-btn]"), removeMainBlock);

    var subBlocks = mainBlock['subBlocks'];
    for (var index = 0; index < subBlocks.length; index++) {
        mainBlockElement = createSubBlock(subBlocks[index], mainBlockElement);
    }

    articleContent.appendChild(mainBlockElement);
}

function createSubBlock(subBlock, mainBlockElement) {
    var mainBlock = mainBlockElement.querySelector("div[name='main-block']");
    mainBlock.querySelector("[name=start-page]").value = subBlock['pages']['start'];
    mainBlock.querySelector("[name=end-page]").value = subBlock['pages']['end'];

    var category = subBlock['category'];
    var template = {};
    var nodes = {};
    var subBlockContent = {};
    if (category === "OBSERVATION") {
        template = document.querySelector('#sub-block-observation-template');
    }
    if (category === "INTERPRETATION") {
        template = document.querySelector('#sub-block-interpretation-template');
    }
    if (category === "APPLICATION") {
        template = document.querySelector('#sub-block-application-template');
    }

    nodes = document.importNode(template.content, true);

    // sequenceId를 subBlock의 data-block-seq-id에 할당
    var subBlockElement = nodes.querySelector("[name=sub-block]");
    var prevBlockSeqId = subBlock['pointers']['prevPointer'];
    if (prevBlockSeqId == null) {
        prevBlockSeqId = '0';
    }
    var nextBlockSeqId = subBlock['pointers']['nextPointer'];
    if (nextBlockSeqId == null) {
        nextBlockSeqId = '0';
    }
    subBlockElement.dataset.blockSeqId = subBlock.sequenceId;
    subBlockElement.dataset.prevBlockSeqId = prevBlockSeqId;
    subBlockElement.dataset.nextBlockSeqId = nextBlockSeqId;
    // subBlock의 content 항목에 content할당
    subBlockContent = nodes.querySelector("[name=sub-block-content]");
    subBlockContent.innerHTML = subBlock['content'];
    // auto expand textarea
    subBlockContent.addEventListener("keydown", expandTextArea);

    addOnClickToBlockBtns(nodes.querySelectorAll("[name=add-sub-block-btn]"), addSubBlock);
    addOnClickToBlockBtns(nodes.querySelectorAll("[name=remove-sub-block-btn]"), removeSubBlock);

    mainBlock.appendChild(nodes);

    return mainBlockElement;
}

function expandTextArea() {
    var el = this;
    setTimeout(function () {
        el.style.cssText = 'height:auto; padding:10';
        // for box-sizing other than "content-box" use:
        // el.style.cssText = '-moz-box-sizing:content-box';
        el.style.cssText = 'height:' + el.scrollHeight + 'px';
    }, 0);
}

function convertToYYMMDDHHmm(oldDateTime) {
    // YYYY-MM-DDTHH:mm:ss.oo -> YYYY-MM-DD HH:mm
    var newDateTime = new Date(oldDateTime);
    var normalFormDateTime =
        newDateTime.getFullYear() + '-'
        + (newDateTime.getMonth() + 1) + '-'
        + newDateTime.getDate() + ' '
        + newDateTime.getHours() + ':'
        + newDateTime.getMinutes();
    return normalFormDateTime;
}

function getSequence() {
    console.log("seq-" + sequence);
    return sequence++;
}

function consoleLog(data) {
    console.log(data);
}