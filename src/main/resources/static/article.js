var article;

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
    addOnClickToBlockBtns(removeSubBlockBtns,removeSubBlock);

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
    var articleInfo = {
        title: document.querySelector("#title").value,
        writer: null,
        date: document.querySelector("#date").value
    }

    $.ajax({
        type: 'POST',
        contentType: 'application/json',
        dataType: 'json',
        data: JSON.stringify(articleInfo),
        url: '/api/articles/save',
    }).done(callback);
}

function getArticle(articlePath) {
    $.ajax({
        type: 'GET',
        dataType: 'json',
        url: '/api' + articlePath,
    }).done(function (data) {
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
    // 복사된 노드들 중 main-block-id를 채워넣는다
    var mainBlockId = mainBlockElement.querySelector("input[name='main-block-id']");
    mainBlockId.value = mainBlock.id;

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
        + (newDateTime.getMonth()+1) +'-'
        + newDateTime.getDate() + ' '
        + newDateTime.getHours() +':'
        + newDateTime.getMinutes();
    return normalFormDateTime;
}

function consoleLog(data) {
    console.log(data);
}