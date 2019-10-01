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

    // path(/articles/id)를 전달해서 article data 요청
    getArticle(window.location.pathname);
};

function addMainBlock(event) {
    var mainBlockTemplate = document.getElementById("default-main-block-template");

    // 외부문서에서 노드를 복사해온다 deep이 true면 자식노드까지 모두 복사해온다
    var mainBlockElement = document.importNode(mainBlockTemplate.content, true);
    var observationElement = document.importNode(document.getElementById("sub-block-observation-template2").content, true);
    var interpretationElement = document.importNode(document.getElementById("sub-block-interpretation-template2").content, true);
    var applicationElement = document.importNode(document.getElementById("sub-block-application-template2").content, true);

    // 서브블록을 메인블록 아래에 붙인다
    var mainBlock = mainBlockElement.querySelector("[name=main-block]");
    mainBlock.append(observationElement);
    mainBlock.append(interpretationElement);
    mainBlock.append(applicationElement);

    var tempParentForNode = document.createElement("div");
    tempParentForNode.appendChild(mainBlockElement);

    // 클릭된 플러스 버튼이 속한 메인블록 아래에 메인블록이 추가된다
    var clickedMainBlock = event.target.closest(".main-block");
    consoleLog(clickedMainBlock);
    clickedMainBlock.insertAdjacentHTML('afterend', tempParentForNode.innerHTML);

    // 새롭게 생성된 메인블록의 메인블록 생성/제거 버튼에 onClick 메소드 붙이기
    var createdMainBlock = clickedMainBlock.nextElementSibling;
    addOnClickToBlockBtns(createdMainBlock.querySelectorAll("[name=add-main-block-btn]"), addMainBlock);
    addOnClickToBlockBtns(createdMainBlock.querySelectorAll("[name=remove-main-block-btn]"), removeMainBlock);

    // 새롭게 생성된 메인블록의 서브블록 생성/제거 버튼에 onClick 메소드 붙이기
    addOnClickToBlockBtns(createdMainBlock.querySelectorAll("[name=add-sub-block-btn]"), addSubBlock);
    addOnClickToBlockBtns(createdMainBlock.querySelectorAll("[name=remove-sub-block-btn]"), removeSubBlock);
}

function removeMainBlock(event) {
    var mainBlocks = document.querySelectorAll(".main-block");

    if (mainBlocks.length === 1) {
        alert("메인블록이 1개일 때는 삭제할 수 없습니다");
        return 0;
    }

    var clickedMainBlock = event.target.closest(".main-block");
    clickedMainBlock.classList.remove("show");
    setInterval(function () {
        clickedMainBlock.remove();
    }, 300)
}

function addSubBlock(event) {
    var subBlockTemplate;
    var clickedSubBlock = event.target.closest("[name=observation], [name=interpretation], [name=application]");
    var clickedSubBlockName = clickedSubBlock.getAttribute("name");
    if (clickedSubBlockName === "observation") {
        subBlockTemplate = document.getElementById("sub-block-observation-template").innerHTML;
    }
    if (clickedSubBlockName === "interpretation") {
        subBlockTemplate = document.getElementById("sub-block-interpretation-template").innerHTML;
    }
    if (clickedSubBlockName === "application") {
        subBlockTemplate = document.getElementById("sub-block-application-template").innerHTML;
    }
    clickedSubBlock.insertAdjacentHTML("afterend", subBlockTemplate);

    var createdSubBlock = clickedSubBlock.nextElementSibling;
    addOnClickToBlockBtns(createdSubBlock.querySelectorAll("[name=add-sub-block-btn]"), addSubBlock);
    addOnClickToBlockBtns(createdSubBlock.querySelectorAll("[name=remove-sub-block-btn]"), removeSubBlock);
}

function removeSubBlock(event) {
    var clickedSubBlock = event.target.closest("[name=observation], [name=interpretation], [name=application]");
    // mainBlock은 main-block-info라는 자식을 기본으로 가지고 있다. 따라서 항상 chidren.length는 1이상이다
    var mainBlockChildrenLength = event.target.closest("[name=main-block]").children.length;
    if (mainBlockChildrenLength <= 2) {
        removeMainBlock(event);
    }
    if (mainBlockChildrenLength > 2) {
        clickedSubBlock.remove();
    }
}

function addOnClickToBlockBtns(blockBtns, onClickMethod) {
    for (var index = 0; index < blockBtns.length; index++) {
        var blockBtn = blockBtns[index]
        blockBtn.onclick = onClickMethod;
    }
}

function save(callback) {
    // JSON에 들어갈 객체
    var token = window.location.pathname.split("/");
    var articleId = token[token.length -1];
    var articleInfo = {
        title: document.querySelector("#title").value
    }
    var mainBlocks = [];
    var content = {mainBlocks: mainBlocks};
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
            // UpperCase로 바꿔준 이유는 BE에서 ContentCategory에 해당하는 Enum의 요소 이름이 UpperCase로 되어 있기 때문에.
            var subBlockContentCategory = subBlocksElement[subBlockIndex].dataset.contentCategory.toUpperCase();
            var subBlock = {
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

    $.ajax({
        type: 'POST',
        contentType: 'application/json',
        dataType: 'json',
        data: JSON.stringify(article),
        url: '/api/articles/save',
    }).done(callback);
}

function getArticle(articlePath) {
    $.ajax({
        type: 'GET',
        dataType: 'json',
        url: '/api' + articlePath,
    }).done(function (data) {
        // consoleLog(data);
        ConvertJsonToArticle(data);
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
    // createMainBlock(null);
}

function createMainBlock(mainBlock) {
    // #article-content 아래에 mainBlock들이 위치한다
    var articleContent = document.querySelector('#article-content');

    // template을 불러와 해당 html 정보를 node로 바꾼다
    var mainBlockTemplate = document.querySelector('#main-block-template2');
    // 외부문서에서 노드를 복사해온다 deep이 true면 자식노드까지 모두 복사해온다
    var mainBlockElement = document.importNode(mainBlockTemplate.content, true);
    // mainblock에 seq-id를 넣어준다
    mainBlockElement.querySelector("[name=main-block]").dataset.blockSeqId = mainBlock.sequenceId;
    // var mainBlockId = mainBlockElement.querySelector("input[name='main-block-id']");
    // mainBlockId.value = mainBlock.id;

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

    var category = subBlock['category'];
    var template = {};
    var nodes = {};
    var subBlockContent = {};
    if (category === "OBSERVATION") {
        template = document.querySelector('#sub-block-observation-template2');
    }
    if (category === "INTERPRETATION") {
        template = document.querySelector('#sub-block-interpretation-template2');
    }
    if (category === "APPLICATION") {
        template = document.querySelector('#sub-block-application-template2');
    }

    nodes = document.importNode(template.content, true);
    // sequenceId를 subBlock의 data-block-seq-id에 할당
    nodes.querySelector("[name=sub-block]").dataset.blockSeqId = subBlock.sequenceId;
    // subBlock의 content 항목에 content할당
    subBlockContent = nodes.querySelector("textarea[name='sub-block-content']");
    subBlockContent.innerHTML = subBlock['content'];
    addOnClickToBlockBtns(nodes.querySelectorAll("[name=add-sub-block-btn]"), addSubBlock);
    addOnClickToBlockBtns(nodes.querySelectorAll("[name=remove-sub-block-btn]"), removeSubBlock);

    mainBlock.appendChild(nodes);

    return mainBlockElement;
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

function consoleLog(data) {
    console.log(data);
}