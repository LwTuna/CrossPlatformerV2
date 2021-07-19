let renderer ;

$(function (){
    let body = {
        username: "test1",
        password:"test2"
    };

    sendRequest("post","login",body,(status, response) => console.log(status+"/"+response));
    //renderer= new Render.Renderer();
    //renderer.start();
});

function sendRequest(method:string,head:string,body,callback:(status:number,response:string)=>void) {
    const httpRequest = new XMLHttpRequest();
    httpRequest.open(method,head+"?"+encodeURI(JSON.stringify(body)));

    httpRequest.onreadystatechange = function () {

        if(this.readyState == 4){
            //let response = JSON.parse(this.responseText);
            callback(this.status,this.responseText);
        }
    };

    httpRequest.timeout = 5000;
    httpRequest.ontimeout = function () {
        alert("Connection lost.");
    };
    httpRequest.send();
}

