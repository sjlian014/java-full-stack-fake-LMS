const net = require('net');

// ------------------------------------ basic server -----------------------------
const hostname2 = '127.0.0.1';
const port2 = 3001;

const server = net.createServer((socket) => {
    socket.on("data", (buffer) => {
        let msg = buffer.toString().trim()
        switch (msg) {
            case "hello":
                socket.write("hi\n")
                break
            case "chromeOS":
                socket.write(":(\n")
                socket.destroy()
                break
            case "emacs":
                socket.write("best OS in the world!\n")
                break
            case "vim":
                socket.write("best editor in the world!\n")
                break
            default:
                socket.write("...what did you say?")
        }
    })
    socket.on("close", () => {
        console.log("*sign...*")
    })
})

server.listen(port2, hostname2, () => {
    console.log(`basic server running at http://${hostname2}:${port2}/`);
    console.log('you might want to use telnet to connect to this address')
});