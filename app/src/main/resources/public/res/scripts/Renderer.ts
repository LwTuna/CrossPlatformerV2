module Render{
    export class Renderer{
        private canvas:HTMLCanvasElement;
        private context: CanvasRenderingContext2D;
        private drawInterval;
        constructor() {
            this.canvas = document.getElementById("#mainCanvas") as HTMLCanvasElement;
            this.context = this.canvas.getContext("2d");
        }

        draw(){
            this.context.fillRect(0,0,100,100);
        }

        start(){
            this.drawInterval = setInterval(()=>{this.draw();},16);
        }
    }

}