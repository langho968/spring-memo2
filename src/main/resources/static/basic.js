// 미리 작성된 영역 - 수정하지 않으셔도 됩니다.
// 사용자가 내용을 올바르게 입력하였는지 확인합니다.
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

// 수정 버튼을 눌렀을 때, 기존 작성 내용을 textarea 에 전달합니다.
// 숨길 버튼을 숨기고, 나타낼 버튼을 나타냅니다.
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
////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// 여기서부터 코드를 작성해주시면 됩니다.

$(document).ready(function () {
    // HTML 문서를 로드할 때마다 실행합니다.
    getMessages();
    // 저장해놓은 정보를 사용하기 위해 변수에 저장
    JSON.parse(localStorage.getItem("cast"))
    let id = JSON.parse(localStorage.getItem("cast")).id;
    let username = JSON.parse(localStorage.getItem("cast")).username;
    let title = JSON.parse(localStorage.getItem("cast")).title;
    let contents = JSON.parse(localStorage.getItem("cast")).contents;
    let modifiedAt = JSON.parse(localStorage.getItem("cast")).modifiedAt;
    //상세페이지에 내용을 붙여넣기 위해 addHTML함수에 변수 넣기
    addHTML1(id, username, title, contents, modifiedAt);
})

// 메모를 불러와서 보여줍니다.
function getMessages() {
    $('#cards-box').empty();
    $.ajax({
        type: 'GET',
        url: '/api/memos',
        success: function (response) {
            for(let i=0; i<response.length; i++){
                let memo = response[i];
                let id = memo.id;
                let username = memo.username;
                let title = memo.title;
                let contents = memo.contents;
                let modifiedAt= memo.modifiedAt;
                addHTML(id, username, title, contents, modifiedAt);
            }
        }
    })
}


function addHTML(id, username, title, contents, modifiedAt) {
    console.log(id, username, title, contents, modifiedAt);
    let tempHtml = `<div class="card">
                        <div class="metadata">
                            <div class="date">
                                ${modifiedAt}
                            </div>
                            <div id="${id}-username"  class="username">
                                ${username}
                            </div>
                        </div>
                        <div class="contents">
                             <div id="${id}-title" class="title">
                             <a  onclick="detail(${id})" >
                                ${title}
                                </a>
                            </div>
                    </div>`;
                    $('#cards-box').append(tempHtml);
}


function writePost() {
    let contents = $('#contents').val();
    let title = $('#title').val();
    if(isValidContents(contents)==false){
        return;
    }
    let username = $('#username').val();
    let data = {'username':username, 'title':title, 'contents': contents};
    $.ajax({
        type: "POST",
        url: "/api/memos",
        contentType: "application/json",
        data: JSON.stringify(data),
        success: function (response) {
            alert('게시물이 성공적으로 작성되었습니다.');
            window.location.reload();
        }
    });
}



// 상세 페이지 내용 가져오기
function detail(id) {
    $('#cards-box2').empty();
    $.ajax({
        type: "GET",
        url: `/api/memos/detail/${id}`,
        success: function (response) {
            console.log(response);
            let id = response["id"];
            let username = response["username"];
            let title = response["title"];
            let contents = response["contents"];
            let modifiedAt = response["modifiedAt"];
            var cast = {
                "id" : id,
                "username": username,
                "title": title,
                "contents": contents,
                "modifiedAt": modifiedAt
            };
            //index페이지에서 내용을 가져왔기 때문에 다른 페이지로 내용을 전달할려고
            // localStorage에 상세 페이지 내용을 저장하였다.
            localStorage.setItem("cast", JSON.stringify(cast));
            window.location.href = 'detail2.html';
        }
    })
}
//상세 페이지에 내용 띄우기
function addHTML1(id, username, title, contents, modifiedAt){
    $('#cards-box2').empty();
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
                            <img id="${id}-edit" class="icon-start-edit" src="images/edit.png" alt="" onclick="editPost('${id}')">
                            <img id="${id}-delete" class="icon-delete" src="images/delete.png" alt="" onclick="deleteOne('${id}')">
                            <img id="${id}-submit" class="icon-end-edit" src="images/done.png" alt="" onclick="submitEdit('${id}')">
                        </div>
                    </div>`;
    $('#cards-box2').append(tempHtml);
}
function submitEdit(id) {

    let username = $(`#${id}-username`).text().trim();
    let contents = $(`#${id}-textarea`).val().trim();
    if (isValidContents(contents) == false) {
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
            detail(id);
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