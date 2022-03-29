$(document).ready(function () {
    getMessages();
    // getUsername();
})
//
// function getUsername(){
//     $('#title-box').empty();
//     $.ajax({
//         type: 'GET',
//         url: '/username',
//         success: function (response){
//             for(let i = 0; i<response.length; i++){
//                 let data = response[i];
//                 let title = data.username;
//                 addTitle(title);
//             }
//
//         }
//     })
// }
// function addTitle(title){
//     let tempHtml = `<h2>${title}의 게시판</h2>`
//     $('#title-box').append(tempHtml);
// }


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
    let tempHtml = `<a href ="detail.html?id=${id}">
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