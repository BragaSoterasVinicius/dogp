
function tick(){
    const clockface = document.getElementById("daytime")
    const calendarface = document.getElementById("yeartime")

    let d = new Date();
    calendarface.innerHTML = ("0" + (d.getDay() + 1)).slice(-2)+'/'+("0" + (d.getMonth() + 1)).slice(-2)+'/'+d.getFullYear();
    clockface.innerHTML = ("0" + (d.getHours())).slice(-2)+':'+("0" + (d.getMinutes())).slice(-2)+':'+("0" + (d.getSeconds())).slice(-2);
}
//como fazer o tick rodar no segundo zero e, então, esperar um segundo para ser executado novamente?
setInterval(tick, 1000);

// Function to simulate button click on keypress
document.addEventListener("keydown", function(event) {
    // Check if the 'Enter' key is pressed
    if (event.key == "ArrowRight") {
        document.getElementById("nextPoste").click();
    }
    if (event.key == "ArrowLeft") {
        document.getElementById("lastPoste").click();
    }

});