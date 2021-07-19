let renderer:Render.Renderer;

let websocket:WebSocket;

$(function (){


    createWebsocketConnection();


    //renderer= new Render.Renderer();
    //renderer.start();
});


function createWebsocketConnection(){
    websocket = new WebSocket("ws://"+location.host+"/game");
    websocket.onopen = function () {
        console.log("Connected!");
        let body = {
            key: "login",
            username:"test2",
            password:"test3"
        };


        websocket.send(JSON.stringify(body));
    };
    websocket.onmessage = ev => {
        console.log(ev);
    };
}



