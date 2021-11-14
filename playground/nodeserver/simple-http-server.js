const net = require('net');
const http = require('http');

// ------------------------------------ http server ------------------------------
const hostname = '127.0.0.1';
const port = 3000;

let resources = ['java', 'kotlin', 'js', 'c', 'lisp', 'shell/bash']
function toHtmlList(arr) { // I am prob committing some sort of js sin here, but most answers on stack overflow don't work with node so I have to write it myself. Sorry!
  let list = "<ul>"
  arr.forEach((item, _1, _2) => {
    list += `\n<li>${item}</li>`
  })
  return list += "</ul>"
}

const httpServer = http.createServer((req, res) => {
  function handleRequests(body) {
    console.log(body)
    switch (req.method) {
      case "GET":
        res.statusCode = 200;
        res.setHeader('Content-Type', 'text/html');
        res.end(`
      <h1>Welcome!</h1>
      <p>this is a simple http server written in node js</p>
      <h2>current list:</h2>
      ${toHtmlList(resources)}
      <h2>usage:</h2>
      <p>use http method PUT and DELETE to modify the list (make sure to use plain text instead of JSON!)</p>
      <p>
      <br>PUT [new item]
      <br>DELETE [item to delete] - this will delete all occurrences of given string
      </p>
      `);
        break;

      case "PUT":
        resources.push(data)
        res.statusCode = 200;
        res.setHeader('Content-Type', 'text/plain');
        res.end(`${data}`);
        break;

      case "POST":
        res.statusCode = 400;
        res.setHeader('Content-Type', 'text/plain');
        res.end(`not implemented!`);
        break;

      case "DELETE":
        resources = resources.filter((value, _1, _2) => value !== data)
        res.statusCode = 200;
        res.setHeader('Content-Type', 'text/plain');
        res.end(`${data}`);
        break;

    }
  }
  // grabing request body [https://nodejs.dev/learn/get-http-request-body-data-using-nodejs]
  // alternative syntax also available
  let data = '';
  req.on('data', chunk => {
    data += chunk;
  })
  req.on('end', () => {
    handleRequests(data) // even more sins?
  })

});

httpServer.listen(port, hostname, () => {
  console.log(`http server running at http://${hostname}:${port}/`);
});

// ------------------------------------ basic server -----------------------------
const hostname2 = '127.0.0.1';
const port2 = 3001;

const server = net.createServer((socket) => {
  socket.on("data", (buffer) => {
    console.log(buffer.toString('utf-8'))
  })
  socket.on("end", () => {
    console.log("goodbye")
  })
})

server.listen(port2, hostname2, () => {
  console.log(`basic server running at http://${hostname2}:${port2}/`);
  console.log('you might want to use telnet to connect to this address')
});