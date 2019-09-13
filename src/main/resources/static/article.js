window.onload = function () {
    // 페이지 로딩이 끝나면 메인블록 플러스 및 마이너스 버튼에 온클릭 메소드를 붙인다
    var addMainBlockBtns = document.querySelectorAll("[name=add-main-block]");
    addOnClickToMainBlockBtns(addMainBlockBtns, addMainBlock);

    var removeMainBlockBtns = document.querySelectorAll("[name=remove-main-block]");
    addOnClickToMainBlockBtns(removeMainBlockBtns, removeMainBlock);
};

function addMainBlock(event) {
    var templateHtml = document.getElementById("main-block-template").innerHTML;
    // 클릭된 플러스 버튼이 속한 메인블록 아래에 메인블록이 추가된다
    var clickedMainBlock = event.target.closest(".main-block");
    clickedMainBlock.insertAdjacentHTML('afterend', templateHtml);

    // 인터벌 걸지 않고 그냥 show 클래스를 추가하면 css의 transition이 먹지 않는다 왜..
    setInterval(function () {
        createdMainBlock.classList.add("show");
    }, 1)

    // 새롭게 생성된 메인블록
    var createdMainBlock = clickedMainBlock.nextElementSibling;
    var createdAddMainBlockBtn = createdMainBlock.querySelector("[name=add-main-block]");
    createdAddMainBlockBtn.onclick = addMainBlock;
}

function removeMainBlock(event) {
    var clickedMainBlock = event.target.closest(".main-block");
    clickedMainBlock.remove();
}

function addOnClickToMainBlockBtns(mainBlockBtns, onClickMethod) {
    for (var index = 0; index < mainBlockBtns.length; index++) {
        var mainBlockBtn = mainBlockBtns[index]
        mainBlockBtn.onclick = onClickMethod;
    }
}