$(document).ready(function () {
    getMessages();
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

function move(id){
    location.href= `/detail/${id}`
}
function loginPage(){
    location.href='/login'
}
function addHTML(id, username, title, contents, modifiedAt) {
    let tempHtml = `<a onclick="move(${id})">
                    <div class="card">
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
                                ${title}                            
                            </div>
                            </a>
                    </div>`;
    $('#cards-box').append(tempHtml);
}
