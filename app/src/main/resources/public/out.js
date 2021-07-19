var Render;
(function (Render) {
    var Renderer = /** @class */ (function () {
        function Renderer() {
            this.canvas = document.getElementById("#mainCanvas");
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
    renderer = new Render.Renderer();
    renderer.start();
});
function testRequest() {
    console.log("Started Client");
    $.ajax({
        type: 'POST',
        url: 'ping'
    }).done(function (response) {
        console.log(response);
    });
}
//# sourceMappingURL=out.js.map