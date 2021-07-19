let renderer ;

$(function (){
    renderer= new Render.Renderer();
    renderer.start();
});


function testRequest(){
    console.log("Started Client");
    $.ajax({
        type:'POST',
        url:'ping'
    }).done(function (response) {
        console.log(response);
    });
}


