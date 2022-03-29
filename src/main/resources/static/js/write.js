function isValidContents(contents) {
    if (contents == '') {
        alert('내용을 입력해주세요');
        return false;
    }
    if (contents.trim().length > 140) {
        alert('공백 포함 140자 이하로 입력해주세요');
        return false;
    }
    return true;
}
function writePost() {
    let contents = $('#contents').val();
    let title = $('#title').val();
    let username = $('#username').val();
    if(isValidContents(contents)==false){
        return;
    }
    if(isValidContents(title)==false) {
        return;
    }
    if(isValidContents(username)==false) {
        return;
    }
    let data = {'username':username, 'title':title, 'contents': contents};
    $.ajax({
        type: "POST",
        url: "/api/memos",
        contentType: "application/json",
        data: JSON.stringify(data),
        success: function (response) {
            alert('게시물이 성공적으로 작성되었습니다.');
            window.location.href = "index.html";
        }
    });
}