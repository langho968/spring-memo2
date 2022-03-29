$(document).ready(function () {
    // HTML 문서를 로드할 때마다 실행합니다.
    getMessages();
})

function getMessages() {
    $('#cards-box2').empty();
    let idVal = location.href.split("id=")[1];
    console.log(idVal);
    $.ajax({
        type: "GET",
        url: `/api/memos/detail/${idVal}`,
        success: function (response) {
            let id = response["id"];
            let username = response["username"];
            let title = response["title"];
            let contents = response["contents"];
            let modifiedAt = response["modifiedAt"];
            addHTML(id, username, title, contents, modifiedAt);
        }
    })
}
function addHTML(id, username, title, contents, modifiedAt){
    let tempHtml = `<div class="card">
                        <div class="metadata">
                            <div class="date">
                                ${modifiedAt}
                            </div>
                            <div id="${id}-username" class="username">
                                ${username}
                            </div>
                        </div>
                        <div class="contents">
                            <div class="title">
                                ${title}
                            </div>
                            <div id="${id}-contents" class="text">
                                ${contents}
                            </div>
                            <div id="${id}-editarea" class="edit">
                                <textarea id="${id}-textarea" class="te-edit" name="" id="" cols="30" rows="5"></textarea>
                            </div>
                        </div>
                        <div class="footer">
                            <img id="${id}-edit" class="icon-start-edit" src="../images/edit.png" alt="" onclick="editPost('${id}')">
                            <img id="${id}-delete" class="icon-delete" src="../images/delete.png" alt="" onclick="deleteOne('${id}')">
                            <img id="${id}-submit" class="icon-end-edit" src="../images/done.png" alt="" onclick="submitEdit('${id}')">
                        </div>
                    </div>`;
    $('#cards-box2').append(tempHtml);
}
function editPost(id) {
    showEdits(id);
    let contents = $(`#${id}-contents`).text().trim();
    $(`#${id}-textarea`).val(contents);
}

function showEdits(id) {
    $(`#${id}-editarea`).show();
    $(`#${id}-submit`).show();
    $(`#${id}-delete`).show();

    $(`#${id}-contents`).hide();
    $(`#${id}-edit`).hide();
}

function hideEdits(id) {
    $(`#${id}-editarea`).hide();
    $(`#${id}-submit`).hide();
    $(`#${id}-delete`).hide();

    $(`#${id}-contents`).show();
    $(`#${id}-edit`).show();
}
function submitEdit(id) {

    let username = $(`#${id}-username`).text().trim();
    let contents = $(`#${id}-textarea`).val().trim();
    if (isValidContents(contents) === false) {
        return;
    }
    let data = {'username': username,  'contents':contents};
    console.log(data);
    $.ajax({
        type: "PUT",
        url: `/api/memos/${id}`,
        data: JSON.stringify(data),
        contentType: "application/json",
        success: function (response) {
            alert('게시글 변경에 성공하였습니다.');
            //수정된 정보를 가져오기 위해 다시 한번 detail함수에 해당 id 값을 넣었다.
            //하지 않으면 수정된 정보를 안띄워줌
            window.location.reload();
        }
    });
}

function deleteOne(id) {
    $.ajax({
        type: "DELETE",
        url: `/api/memos/${id}`,
        success: function (response) {
            alert('게시글 삭제에 성공하였습니다.');
            window.location.href="index.html";
        }
    })
}
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