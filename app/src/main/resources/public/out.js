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
$(function () {
    var body = {
        username: "test1",
        password: "test2"
    };
    sendRequest("post", "login", body, function (status, response) { return console.log(status + "/" + response); });
    //renderer= new Render.Renderer();
    //renderer.start();
});
function sendRequest(method, head, body, callback) {
    var httpRequest = new XMLHttpRequest();
    httpRequest.open(method, head + "?" + encodeURI(JSON.stringify(body)));
    httpRequest.onreadystatechange = function () {
        if (this.readyState == 4) {
            //let response = JSON.parse(this.responseText);
            callback(this.status, this.responseText);
        }
    };
    httpRequest.timeout = 5000;
    httpRequest.ontimeout = function () {
        alert("Connection lost.");
    };
    httpRequest.send();
}
//# sourceMappingURL=out.js.map