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

    // create();
};

function addMainBlock(event) {
    var mainBlockTemplate = document.getElementById("main-block-template").innerHTML;
    // 클릭된 플러스 버튼이 속한 메인블록 아래에 메인블록이 추가된다
    var clickedMainBlock = event.target.closest(".main-block");
    console.log(clickedMainBlock);
    clickedMainBlock.insertAdjacentHTML('afterend', mainBlockTemplate);

    // 인터벌 걸지 않고 그냥 show 클래스를 추가하면 css의 transition이 먹지 않는다 왜..
    setInterval(function () {
        createdMainBlock.classList.add("show");
    }, 1)

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
        alert("메인블록이 1개일 때는 삭제할 수 없습니다")
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
    clickedSubBlock.remove();
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

function create() {
    $.ajax({
        type: 'GET',
        dataType: 'json',
        url: '/api/articles/new',
    }).done(function (data) {
        consoleLog(data);
    });
}

function consoleLog(data) {
    console.log(data);
}