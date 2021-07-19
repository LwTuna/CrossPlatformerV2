var Render;
(function (Render) {
    var Renderer = /** @class */ (function () {
        function Renderer() {
            this.canvas = document.getElementById("mainCanvas");
            this.context = this.canvas.getContext("2d");
        }
        Renderer.prototype.draw = function () {
            this.context.fillRect(0, 0, 100, 100);
        };
        Renderer.prototype.start = function () {
            var _this = this;
            this.drawInterval = setInterval(function () { _this.draw(); }, 16);
        };
        return Renderer;
    }());
    Render.Renderer = Renderer;
})(Render || (Render = {}));
var renderer;
var websocket;
$(function () {
    createWebsocketConnection();
    //renderer= new Render.Renderer();
    //renderer.start();
});
function createWebsocketConnection() {
    websocket = new WebSocket("ws://" + location.host + "/game");
    websocket.onopen = function () {
        console.log("Connected!");
        var body = {
            key: "login",
            username: "test2",
            password: "test3"
        };
        websocket.send(JSON.stringify(body));
    };
    websocket.onmessage = function (ev) {
        console.log(ev);
    };
}
//# sourceMappingURL=out.js.map