function addMainBlock() {
    var templateHtml = document.getElementById("main-block-template").innerHTML;
    var articleContent = document.getElementById("article-content");
    articleContent.insertAdjacentHTML('beforeend', templateHtml);
}